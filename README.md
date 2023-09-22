<h1 align="center">
  Place Service
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=Youtube&message=@DevCollab&color=8257E5&labelColor=000000" alt="@DevCollab" />
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Desafio&color=8257E5&labelColor=000000" alt="Desafio" />
</p>

API para gerenciar lugares (CRUD) que faz parte [desse desafio](https://github.com/RocketBus/quero-ser-clickbus/tree/master/testes/backend-developer) para pessoas desenvolvedoras backend que se candidatam para a ClickBus.

O projeto foi elaborado [nesse vídeo](https://youtu.be/SsWZ4O9iWuo).

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data + R2DBC](https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Slugify](https://github.com/slugify/slugify)

## Práticas adotadas

- SOLID
- Testes automatizados
- Consultas com filtros dinâmicos usando o Query By Example
- API reativa na web e na camada de banco
- Uso de DTOs para a API
- Injeção de Dependências
- Geração automática do Swagger com a OpenAPI 3
- Geração de slugs automática com o Slugify
- Auditoria sobre criação e atualização da entidade

## Como Executar

### Localmente
- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
```
java -jar place-service/target/place-service-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Usando Docker

- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Construir a imagem:
```
./mvnw spring-boot:build-image
```
- Executar o container:
```
docker run --name place-service -p 8080:8080  -d place-service:0.0.1-SNAPSHOT
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [postman](https://www.postman.com/):

- POST localhost:8080/places
```
Request:
{
    "name": "New One",
    "city": "New City",
    "state": "New State"
}

HTTP/1.1 201 CREATED
Content-Type: application/json

{
    "name": "New One",
    "slug": "new-one",
    "city": "New State",
    "state": "New City",
    "createdAt": "2023-09-22T18:19:54.1250719",
    "updatedAt": "2023-09-22T18:19:54.1250719"
}
```

- GET localhost:8080/places
```
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "name": "New One",
        "slug": "new-one",
        "city": "New State",
        "state": "New City",
        "createdAt": "2023-09-22T18:19:27.476613",
        "updatedAt": "2023-09-22T18:19:27.476613"
    }
]
```

- GET localhost:8080/places/1
```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "name": "New One",
    "slug": "new-one",
    "city": "New State",
    "state": "New City",
    "createdAt": "2023-09-22T18:19:27.476613",
    "updatedAt": "2023-09-22T18:19:27.476613"
} 
```

- GET localhost:8080/places?name=New One
```
HTTP/1.1 200 OK
Content-Type: application/json
transfer-encoding: chunked

[
    {
        "name": "New One",
        "slug": "new-one",
        "city": "New State",
        "state": "New City",
        "createdAt": "2023-09-22T18:19:27.476613",
        "updatedAt": "2023-09-22T18:19:27.476613"
    }
]
```

- PATCH localhost:8080/places/1
```
Request:
{
    "name": "New One Name",
    "city": "New City Name",
    "state": "New State Name"
}

HTTP/1.1 200 CREATED
Content-Type: application/json

{
    "name": "New One Name",
    "slug": "new-one-name",
    "city": "New State Name",
    "state": "New City Name",
    "createdAt": "2023-09-22T18:19:27.476613",
    "updatedAt": "2023-09-22T18:31:13.3329751"
}
```

- PUT localhost:8080/places/1
```
Request:
{
    "name": "New One Name",
    "city": "New City Name",
    "state": "New State Name"
}

HTTP/1.1 200 CREATED
Content-Type: application/json

{
    "name": "New One Name",
    "slug": "new-one-name",
    "city": "New State Name",
    "state": "New City Name",
    "createdAt": "2023-09-22T18:19:27.476613",
    "updatedAt": "2023-09-22T18:31:13.3329751"
}
```