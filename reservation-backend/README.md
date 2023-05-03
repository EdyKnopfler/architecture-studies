# Backend para o sistema de reservas

Desenvolvido em Golang, serve ao frontend, mapeando requisições de usuário para chamadas aos (futuros) serviços.

Contêiner Docker com `docker-compose` saiŕa em breve, reduzindo a quantidade de passos manuais!

Instalação das dependências:

```bash
go get ./...
```

Requer a criação de chaves para autenticação:

```bash
openssl genrsa -out ./key 4096
openssl rsa -in ./key -pubout -out ./key.pub
```

Criadas as chaves, para rodar associe-as às seguintes variáveis de ambiente:

```bash
export API_PRIVATE_KEY=./key
export API_PUBLIC_KEY=./key.pub
```

Para rodar (porta opcional, 8080 é o default):

```bash
API_PORT=8080 go run cmd/main.go
```

Autenticando o usuário:

```bash
curl -v -X POST -d '{"email": "derso@minhacasa.com", "password": "123456"}' localhost:8080/login
```