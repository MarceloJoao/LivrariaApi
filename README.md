# 📚 Library API

Sistema de gerenciamento de **Autores** e **Livros**, desenvolvido em **Spring Boot**, com frontend em **HTML, Bootstrap e JavaScript** e banco de dados executando em **Docker**.

O projeto permite cadastrar, editar, excluir e listar autores e livros, aplicando validações no **frontend e backend**.

---

## 🚀 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
  
### Banco de Dados
- PostgreSQL
- Docker / Docker Compose

### Frontend
- HTML5
- CSS3
- Bootstrap 5
- JavaScript (Fetch API)

---

## 📌 Funcionalidades

### 👤 Autor
- Cadastrar autor
- Editar autor
- Excluir autor
- Listar autores
- Validação de campos obrigatórios

### 📖 Livro
- Cadastrar livro
- Editar livro
- Excluir livro
- Listar livros
- Associação com autor
- Validação de ISBN único
- Validação de campos obrigatórios

---

## 🧠 Regras de Negócio
- Não é permitido cadastrar livros com **ISBN duplicado**
- Um livro deve obrigatoriamente possuir um autor
- Campos obrigatórios são validados no frontend e backend

## 🐳 Banco de Dados com Docker

O banco de dados PostgreSQL é executado via **Docker**.

## Clonar o repositório
git clone https://github.com/seu-usuario/library-api.git

## Entrar no projeto
cd library-api

## Subir o banco
docker-compose up -d

## Rodar o backend
./mvnw spring-boot:run

http://localhost:8080


# Endpoints da API

### Autor

| Método | Endpoint        | Descrição               |
| ------ | --------------- | ----------------------- |
| GET    | `/autores`      | Listar todos os autores |
| GET    | `/autores/{id}` | Buscar autor por ID     |
| POST   | `/autores`      | Cadastrar autor         |
| PUT    | `/autores/{id}` | Editar autor            |
| DELETE | `/autores/{id}` | Excluir autor           |

### Livro 

| Método | Endpoint       | Descrição              |
| ------ | -------------- | ---------------------- |
| GET    | `/livros`      | Listar todos os livros |
| GET    | `/livros/{id}` | Buscar livro por ID    |
| POST   | `/livros`      | Cadastrar livro        |
| PUT    | `/livros/{id}` | Editar livro           |
| DELETE | `/livros/{id}` | Excluir livro          |


