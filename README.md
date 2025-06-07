# SafeBank

## Pré-requisitos

-   Java (versão 17 ou superior)
-   Maven
-   Node.js e npm
-   Angular CLI
-   PostgreSQL

## Configuração e Execução

Siga os passos abaixo para configurar e executar o projeto em seu ambiente local.

### 1. Configuração do Banco de Dados

-   Certifique-se de que o **PostgreSQL** esteja em execução.
-   Crie um banco de dados com o nome `safe-bank-db`.
-   As configurações de conexão com o banco de dados podem ser encontradas e, se necessário, ajustadas no arquivo `safe-bank-backend/src/main/resources/application.properties`.

### 2. Executando o Backend

-   Abra um terminal e navegue até a pasta `safe-bank-backend`.
-   Execute o seguinte comando para iniciar a aplicação Spring Boot:

```bash
./mvnw spring-boot:run


## Configuração e Execução

### Registrar novo cliente
curl -X POST http://localhost:8080/api/auth/registrar \
-H "Content-Type: application/json" \
-d '{
    "name": "Seu Nome",
    "cpf": "12345678901",
    "password": "sua-senha-super-secreta",
    "phoneNumber": "5511999999999"
}'

### Fazer login para obter um token
curl -X POST http://localhost:8080/api/auth/logar \
-H "Content-Type: application/json" \
-d '{
    "cpf": "12345678901",
    "password": "sua-senha-super-secreta"
}'

### Gerenciar conta
#### Criar uma nova conta (requer autenticação):
curl -X POST http://localhost:8080/api/contas \
-H "Authorization: Bearer SEU_TOKEN_JWT"

#### Listar contas do cliente autenticado
curl -X GET http://localhost:8080/api/contas \
-H "Authorization: Bearer SEU_TOKEN_JWT"

## Transações
curl -X POST http://localhost:8080/api/transacoes/deposito \
-H "Content-Type: application/json" \
-H "Authorization: Bearer SEU_TOKEN_JWT" \
-d '{
    "accountNumber": "NUMERO_DA_CONTA",
    "value": 100.00
}'

## Saque
curl -X POST http://localhost:8080/api/transacoes/saque \
-H "Content-Type: application/json" \
-H "Authorization: Bearer SEU_TOKEN_JWT" \
-d '{
    "accountNumber": "NUMERO_DA_CONTA",
    "value": 50.00
}'

## Obter extrato
curl -X GET http://localhost:8080/api/transacoes/extrato/NUMERO_DA_CONTA \
-H "Authorization: Bearer SEU_TOKEN_JWT"
