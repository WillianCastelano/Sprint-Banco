MVP Banco – Sistema de Empréstimos

Este projeto é um MVP (Minimum Viable Product) de um sistema de gerenciamento de empréstimos bancários, desenvolvido em Java + Spring Boot, com persistência em H2 Database para testes locais.

A API permite:

Criar um novo empréstimo

Consultar por código de contrato

Listar empréstimos por CPF

Atualizar o status do empréstimo

Deletar empréstimo pelo número do contrato

🚀 Tecnologias utilizadas

Java 17+

Spring Boot

Spring Web

Spring Data JPA

Validation

Banco de dados H2 (modo teste)

Lombok, Maven.

Estrutura do Projeto:
src/main/java/com/banco/emprestimo
│
├── controller
│     └── EmprestimoController.java
├── service
│     └── EmprestimoService.java
├── model
│     ├── Emprestimo.java
│     └── StatusEmprestimo.java
├── dto
│     ├── EmprestimoDTO.java
│     └── AtualizarStatusRequest.java
└── repository
└── EmprestimoRepository.java

Configuração do Banco H2:
spring.application.name=MVP Banco

# --- H2 Database ---
spring.datasource.url=jdbc:h2:mem:emprestimo_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Console do H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

server.port=8080

Endpoints da API:
Criar emprestimo:
POST/emprestimos
{
"cpf": "12345678900",
"valorSolicitado": 2000,
"quantidadeParcelas": 10
}

Consultar por contrato:
GET /emprestimos/{codigoContrato}

Listar por CPF:
GET /emprestimos/cpf/{cpf}

Atualizar status:
PUT /emprestimos/status
{
"codigoContrato": "**************",
"status": "APROVADO"
}

Deletar por contrato:
DELETE /emprestimos/contrato/{codigoContrato}
