# Currency converter
Projeto que se trata de um conversor de moedas.

No momento ele só converte em cima da base do euro, devido o API externa de taxas só liberar essa base com uma conta free.

OBS: Se quiser apenas consultar os endpoints, pular para seção #Endpoints#, os mesmos já se encontram na AWS e já podem ser usados.

<br />

## Tecnologias usadas

[Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

[Postgres](https://www.postgresql.org/download/)

[Uma conta exchangeratesapi.io](https://exchangeratesapi.io/)

## Requerimento

[Link para criar conta no exchangeratesapi.io](https://exchangeratesapi.io/)

[Tutorial do zero para instalar Postgres](https://www.postgresql.org/download/)

<br /><br />

## Como usar em sua própria máquina 

Clone o projeto ou faça [download](https://github.com/klinsmannlopes/currencyConverter.git) dele:

```git
$ git clone https://github.com/klinsmannlopes/ton-serverless.git
```

Entre na pasta:

```sh
$ cd currencyConverter
```

Na raiz da pasta, realize o comando abaixo:

```sh
$ mvn install
```

Após isso você precisa configurar seu banco de dados:

[Configurar Postgres tutorial](https://www.devmedia.com.br/postgresql-tutorial/33025)

Comandos simples para criar a tabela o schema do banco, realizar esses comandos no database padrão do postgres

Criar schema:

```sql
    create schema currency;
```

Criar Tabela:

```sql
    create table transactions (
                                  id serial primary key,
                                  user_code varchar not null,
                                  origin_currency varchar not null,
                                  origin_value numeric not null,
                                  destiny_currency varchar not null,
                                  destiny_value numeric not null,
                                  rate_used numeric not null,
                                  creation_date timestamp
    );
```

Após isso, entrar  dentro do arquivo properties.properties, e colocar como o exemplo abaixo;

```
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/postgres?schema=currency
spring.r2dbc.username=Colocar seu username do banco
spring.r2dbc.password=Colocar seu password do banco
```
<br />

Com sua conta criada no exchangeratesapi.io, você precisa pegar a sua key

![Alt Text](https://media.giphy.com/media/ebko7iT5sdHN9Dwgy0/giphy.gif)

<br />

Quando pegar a sua key, e preciso colocar no atributo accessKey da classe java ExchangeRatesApi, exemplo abaixo:

![Alt Text](https://media.giphy.com/media/uZvgXci5Jrc7Fk3lBc/giphy.gif)

<br />


Com isso tudo feito, agora e só da o run da sua da aplicação em sua IDE:

<br /><br />

# Endpoints

### Para utilizar os endpoint você precisa usar um client, exemplos abaixo:

<br />

[POSTMAN](https://www.postman.com)

[SOAPUI](https://www.soapui.org)

<br />

- Endpoint para listar transações do usuário
    - GET
    - [https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/user/{ID}](https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/user/{ID}).
        - Exemplo:
            - [ID como 32afe227-188d-4a21-b966-54de42f295ba:](https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/user/32afe227-188d-4a21-b966-54de42f295ba)
            - JSON de resposta:

```json
[
  {
    "id": 1,
    "userCode": "8a818ea37425d515017425f6474605fb",
    "originCurrency": "EUR",
    "originValue": 10.0,
    "destinyCurrency": "BRL",
    "destinyValue": 64.99596,
    "rateUsed": 6.499596,
    "creationDate": "2021-05-24T21:33:35.098+00:00"
  },
  {
    "id": 2,
    "userCode": "8a818ea37425d515017425f6474605fb",
    "originCurrency": "EUR",
    "originValue": 170.0,
    "destinyCurrency": "BRL",
    "destinyValue": 1105.03689,
    "rateUsed": 6.500217,
    "creationDate": "2021-05-24T22:50:48.266+00:00"
  }
]
```
            
- Endpoint para salvar e converter valor da transação
    - POST
    - [https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/user](https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/user)
        - Exemplo:
            - Usar algum cliente para o endpoint (postman como exemplo)
            - Enviar o JSON abaixo no body:

```json
{
  "idUser": "8a818ea37425d515017425f6474605fb",
  "originValue": 170,
  "destinyCurrency": "BRL"
}
```

- Endpoint para consultar e realizar contagem de acessos
    - GET (todos utilizam o get)
        - Podemos utilizar esse endpoint para realizar essa funções em outras partes do site, apenas mudando os dados de input do endpoint
        - [https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/visits/{action}/{namespace}/{key}](https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/visits/get/ton.com/visits)
            - Obrigatórios
                - action
                    - Esse dado e para saber qual ação realizar
                        - 'get' para pegar o contador e 'hit' para incrementar o contador
                - namespace
                    - Esse dado deve ser exclusivo, é recomendável usar o domínio do seu site
                - Key
                    - Esse dado junto com o namespace, identifica qual contador buscar e qual realizar o increment da contagem.

    
    - Endpoints já configurados
        - Para facilitar os testes, já iniciei um contador
            - Buscar contador
                - GET
                - [https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/visits/get/ton.com/visits](https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/visits/get/ton.com/visits)

            - Realizar contagem
                - GET
                - [https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/visits/hit/ton.com/visits](https://ilejvt44e0.execute-api.us-east-2.amazonaws.com/dev/visits/hit/ton.com/visits)


## Após toda essa configuração e só partir pro abraço e aproveitar sua API, até a próxima.


![Alt Text](https://media.giphy.com/media/eEXxfHQJ0dWOrctI55/giphy.gif)