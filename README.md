# Currency converter
Projeto que se trata de um conversor de moedas.

No momento ele só converte em cima da base do euro, devido o API externa de taxas só liberar essa base com uma conta free.

OBS: Se quiser apenas consultar os endpoints, pular para seção #Endpoints#, os mesmos já se encontram na nuvem e já podem ser usados.

<br />

## Tecnologias usadas

[Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

Decidi usar um framework reativo devido se tratar de um conversor de moedas,
<br>
do qual o cliente não pode esperar uma resposta enquanto o outro ainda esta em processamento de conversão.

[Postgres](https://www.postgresql.org/download/)

Existe N motivos pra usar o postgres, mas como estou trabalhando com dinheiro, preferi usar atomicidade do postgres,
Isso consiste em que a transação ou ela será executada em sua totalidade ou não será de modo nenhum,
assim não ocorre perigo da minha transação conter um dado sujo.<br>
Outro motivo do porque usar, é que quando trabalho com dinheiro eu sempre uso postgres devido sua estrutura de tabelas, fica mais organizado a transação linha a linha na tabela.

[Uma conta exchangeratesapi.io](https://exchangeratesapi.io/)

Aqui onde pego as taxas de conversões.

## Requerimento

[Link para criar conta no exchangeratesapi.io](https://exchangeratesapi.io/)

[Tutorial do zero para instalar Postgres](https://www.postgresql.org/download/)

## Design usados

- Factory
    - Utilizei desse método para indentificar qual implementação usar na hora da converção da moeda,<br>
      cada classe que implementa a classe génerica ConverterInterface,  possui um tipo moeda,<br>
      assim não e preciso usar N if's para indentificar qual classe usar para converter a moeda.
      <br><br>
- SOLID
    - Usei esses princípios para separar as camadas e classes java do projeto, com ele implementado,<br>
      fica mais fácil de criar novas implementações para a converção, já que agora e so plugar em vez de refatorar alguma parte do código.
      <br><br>
    - Um exemplo, e se eu quiser criar uma nova converção de moeda e cria taxas em cima dela e só plugar na interface ConverterInterface, e construir sua implementação.
      <br><br>
    - Assim fica mais fácil também de refatorar o código, já que agora se for mudar algo devido alguma nova regra ou nova funcionalidade, e só mexer na implementação especifica.  



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

Comandos simples para criar a tabela e o schema do banco, realizar esses comandos no database padrão do postgres

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
    - [https://currencyconverterjayatech.herokuapp.com/transactions/{user}](https://currencyconverterjayatech.herokuapp.com/transactions/{user}).
        - Exemplo:
            - [User como 8a818ea37425d515017425f6474605fb](https://currencyconverterjayatech.herokuapp.com/transactions/8a818ea37425d515017425f6474605fb)
            - JSON de resposta:

```json
[
  {
    "id": 1,
    "userCode": "8a818ea37425d515017425f6474605fb",
    "originCurrency": "EUR",
    "originValue": 10.0,
    "destinyCurrency": "BRL",
    "destinyValue": 65.06068,
    "rateUsed": 6.506068,
    "creationDate": "2021-05-24T23:38:19.816+00:00"
  },
  {
    "id": 2,
    "userCode": "8a818ea37425d515017425f6474605fb",
    "originCurrency": "EUR",
    "originValue": 10.0,
    "destinyCurrency": "USD",
    "destinyValue": 12.22515,
    "rateUsed": 1.222515,
    "creationDate": "2021-05-25T03:07:59.861+00:00"
  }
]
```

- Endpoint para salvar e converter valor da transação
    - POST
    - [https://currencyconverterjayatech.herokuapp.com/transactions](https://currencyconverterjayatech.herokuapp.com/transactions)
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

## Após toda essa configuração e só partir pro abraço e aproveitar sua API, até a próxima.


![Alt Text](https://media.giphy.com/media/eEXxfHQJ0dWOrctI55/giphy.gif)