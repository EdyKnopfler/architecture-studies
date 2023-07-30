# Estudo aprofundado em arquitetura

Agência de viagens com reserva de voos, hotéis e pagamento (simulação).

Mais detalhes em breve, por enquanto as ideias estão na pasta `doc`.

## Configuração do RabbitMQ

* Usuário e senha de administrador no `docker-compose.yml`
* Deve ser criado o exchange `architecture-studies`
* Cada binding deve identificar no _routing key_ o serviço

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

## Coordenando os serviços

A figura a seguir dá a ideia geral:

* Serviço coordenador controla a sessão do usuário
  * As pré-reservas feitas na sessão carregam o ID
* Setas inclinadas representam mensagens assíncronas
  * Do serviço coordenador para o cliente: WebSockets?
  * Entre os serviços no backend: RabbitMQ
 
![Coordenação entre serviços](https://raw.githubusercontent.com/EdyKnopfler/architecture-studies/main/doc/coordenacao-entre-servicos.png)


