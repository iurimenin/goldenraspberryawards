# Golden Raspberry Awards API

Desafio técnico para calcular o menor e o maior intervalo entre vitórias consecutivas de produtores no Golden Raspberry Awards.

Na inicialização, os dados do `movielist.csv` são carregados automaticamente num banco H2 em memória — sem configuração extra.

## Tecnologias utilizadas

- Java 25 + Spring Boot
- Spring Data JPA + H2
- Maven

## Rodando o projeto

```bash
./mvnw spring-boot:run
```

Sobe em `http://localhost:8080`.

## Endpoint

```
GET /api/awards/intervals
```

Retorna os produtores com menor e maior intervalo entre duas vitórias consecutivas. Em caso de empate, todos são incluídos.

```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```

## Testes

Cobrem o carregamento dos dados e os cálculos de intervalo.

```bash
./mvnw test
```
