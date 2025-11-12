# desafio-itau-vaga-99-junior

## Ferramentas necessárias para o uso desta aplicação

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

- **OBS.1: a JDK (Java) deve ser a versão 21 ou superior.**
- **OBS.2: o Docker é opcional caso queira rodar a aplicação por meio de um container.**

## Do que se trata este projeto?

É uma aplicação com a finalidade de registrar transações e também
calcular estatísticas em cima destas transações. 

## Como baixar a aplicação?

- Clique no botão verde `<> Code ▼` -> Clique em `HTTPS` -> Copie a URL.
- Abra o Git Bash -> Execute o comando `git clone <URL copiada>` -> Entre na pasta da aplicação.

## Como rodar a aplicação?

Há duas opções:

### Executar a aplicação via Docker

Abra um terminal dentro da pasta do seu projeto e siga as seguintes
instruções:

- Execute o comando `docker build -t docker-image`.
- Execute o comando `docker run -d -p 8080:8080 --name container-docker docker-image`.
- Depois de algum tempo, acesse a URL `localhost:8080/swagger`.

### Executar a aplicação via IDE

- Abra uma IDE (ex.: IntelliJ ou Eclipse) dentro da pasta do projeto.
- Execute a aplicação e acesse a URL `localhost:8080/swagger`.

## O que cada endpoint faz?

### POST /transacao

Este é o corpo da requisição:

```json
{
    "valor": 50.5,
    "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

Este JSON deve obedecer as seguintes regras:

- O campo `valor` só aceitará valores nulos ou 
positivos, caso contrário, será gerado um
código de status 422.
- O campo `dataHora` só aceitará datas no presente
ou passado, caso contrário, será gerado também 
um código de status 422.

Caso as regras acima seja cumpridas, a transação
será inserida em uma lista e o código de status
retornará 200.

### DELETE /transacao

Apagará todas as transações já inseridas na lista, 
retornado o código de status 204.

### GET /estatistica

É passado um valor inteiro X que determinará o cálculo das transações 
que ocorrem somente nos últimos X segundos. O código de status
retornará como 200.

Caso não seja informado nenhum número, o valor padrão será 60.

**OBS.: caso não haja transações nos últimos X segundos, todos
os campos do JSON da resposta retornarão como 0.**

Este é o corpo da resposta:

```json
{
    "count": 10,
    "sum": 1234.56,
    "avg": 123.456,
    "min": 12.34,
    "max": 123.56
}
```

Isto é o que cada campo significa:

- `count`: a quantidade de transações.
- `sum`: a soma do valor de todas as transações.
- `avg`: a média do valor de todas as transações.
- `min`: o menor valor entre todas as transações.
- `max`: o maior valor entre todas as transações.

**OBS.: se estiver executando a aplicação pela IDE, depois de calcular
as estatísticas irá aparecer a mensagem 'Tempo do cálculo das estatísticas (milissegundos): X'**