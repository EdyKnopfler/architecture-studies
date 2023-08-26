# Estudo aprofundado em arquitetura

Agência de viagens com reserva de voos, hotéis e pagamento (simulação).

Mais detalhes em breve, por enquanto as ideias estão na pasta `doc`.

## Configuração do RabbitMQ

* Usuário e senha de administrador no `docker-compose.yml`
* Deve ser criado o exchange `architecture-studies`
* Cada binding deve identificar no _routing key_ o serviço

## Coordenando os serviços

A figura a seguir dá a ideia geral:

* Serviço coordenador controla a sessão do usuário
  * As pré-reservas feitas na sessão carregam o ID
* Setas inclinadas representam mensagens assíncronas
  * Do serviço coordenador para o cliente: WebSockets?
  * Entre os serviços no backend: RabbitMQ
 
![Coordenação entre serviços](https://raw.githubusercontent.com/EdyKnopfler/architecture-studies/main/doc/coordenacao-entre-servicos.png)

### Processo SAGAS

A confirmação do pagamento dispara um processo [SAGAS](https://dev.to/thiagosilva95/saga-pattern-para-microservices-2pb6) entre os serviços de comunicação com os supostos sistemas externos (voos, hotéis, pagamento, etc.):

* Cadeia de confirmações: serviço confirma a reserva e envia mensagem para o próximo
* Falha em uma confirmação envia uma mensagem para o anterior, disparando uma cadeia no serviço contrário
  * o final da cadeia de tratamento de falha deve enviar um pedido de cancelamento para o sistema de pagamento
* Cada serviço deve estar preparado para receber as mensagens como na figura abaixo

![Mensagens SAGAS](https://raw.githubusercontent.com/EdyKnopfler/architecture-studies/main/doc/planejamento-sagas.png)

## Serviço de timeout

Quando algum serviço de reserva (hotel ou passagem aérea) realiza uma _pré-reserva_ (o ato do usuário de assinalar na interface uma opção, antes que tenha feito pagamento), ele deve definir um tempo máximo para manter o recurso escolhido bloqueado. O serviço de timeouts permite agendar uma mensagem para ser enviada a qualquer serviço (inclusive de volta para o próprio solicitante) após um intervalo de tempo, que é uniforme para todos os serviços.

O intuito deste derviço é testar a ideia na máquina de desenvolvimento. Em produção, preferiria delegar o agendamento de tarefas para a infraestrutura se possível, e não ter que me preocupar em armazenar os agendamentos, recuperá-los em caso de falha, ter que lidar com a alocação da quantidade certa de instâncias...

O agendamento é realizado a partir do endpoint `/schedule-timeout`, que recebe um POST contendo:
* `itemId`: o id do item que deve ser cancelado
* `service`: nome do _routing key_ do RabbitMQ que aponta para a fila do serviço

```bash
curl -i localhost:8080/schedule-timeout -H 'Content-Type: application/json' -d '{"service": "hoteis", "itemId": 123456}'
```

O serviço deve estar escutando na fila para onde a _routing key_ está apontada (ref.: https://www.baeldung.com/java-rabbitmq-exchanges-queues-bindings).


## Questões sobre a garantia de consistência

É importante que a última etapa (pagamento), sendo completada com sucesso, cancele quaisquer timeouts programados -- não importa se já foram disparados e mesmo executados! A possibilidade de um timeout ser processado por algum outro serviço _antes_ da confirmação é real.

Talvez o tratamento do timeout seja o que mais aumenta a complexidade da arquitetura, mais até do que a cadeia de confirmações e reversões do SAGAS.

### Abordagem 1: somente confirmar se o timeout for cancelado com sucesso

Isso cria uma "sequência SAGAS" por si só na operação de confirmar:

1. Cancelamento do timeout
2. Realização do pagamento

Porém, o que acontece se o pagamento falhar? O timeout deve ser restaurado e voltar a valer.

### Abordagem 2: suspender a validade do timeout ao confirmar um pagamento

Refinando a abordagem 1 a partir da questão levantada, podemos pensar que o timeout deve ter um status, algo como **tempo correndo**, **disparado** ou **pausado** (deixando à parte _cancelado_, sendo que nesse caso ele talvez possa ser excluído). Assim, antes de realizar um pagamento o status deve ser atualizado para **pausado** e o sistema poderá tentar realizar essa última etapa.

O ato de pausar um timeout deve ter a concorrência controlada (facilmente com locks de linha no banco de dados, caso suportado) para não conflitar com o disparo: ou este acontece primeiro, ou a pausa. Se o disparo o correr primeiro, é tempo esgotado e não podemos confirmar o pagamento. Se a pausa ocorrer antes, o disparo é que deve falhar. É mais simples (menos complicado?) do que ter que garantir a reversão de timeouts já disparados e processados nos serviços.

Obviamente, em caso de falha na realização do pagamento o timeout pausado deve voltar a ter o tempo correndo -- inclusive sendo disparado se o tempo se esgotou durante o período de pausa.

