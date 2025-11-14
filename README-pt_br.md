[Read in English](#-english)

## 🇧🇷 Português
# JPA com Arquitetura Limpa

___
Esse projeto tem como objetivo demonstrar a implementação do **JPA** (_Java Persistence API_) seguindo os princípios da **Arquitetura Limpa**.<br>
A **Arquitetura Limpa** promove a **separação de responsabilidades** do código utilizando **camadas**, facilitando a manutenção, escalabilidade e testabilidade do sistema, protegendo e isolando as **regras de negócios** da aplicação dos detalhes externos.<br>
A biblioteca **JPA** é utilizada para gerenciar a persistência de dados em aplicações Java, permitindo o mapeamento entre objetos Java e tabelas de banco de dados relacionais, ela foi escolhida por ser uma biblioteca amplamente utilizada em projetos Java.

## Tecnologias

---
![Java-21](https://img.shields.io/badge/Java-21-gray.svg?style=for-the-badge&logo=openjdk&logoColor=&labelColor=red)
![Spring-3.5.7](https://img.shields.io/badge/spring-3.5.7-gray.svg?style=for-the-badge&logo=spring&logoColor=white&labelColor=%236DB33F)
<br><br>

## Índices

---
- [Instalação](#instalação)<br>
- [Execução](#execução)<br>
- [Endpoints](#endpoints)<br>
  <br>

## Instalação

---

1. Clone o repositório:
   ```bash
   git clone https://github.com/Guilsimi/clean_architechture_jpa.git
    ```
2. Acesse o diretório do projeto:
   ```bash
   cd {caminho_para_o_diretório}/clean_architechture_jpa
   ```

3. Instale as dependências do projeto utilizando o Maven.
   ```bash
   mvn clean install
   ```
<br>

## Execução

---
1. Execute a aplicação utilizando o Maven.
   ```bash
   mvn spring-boot:run
   ```

2. A aplicação estará disponível em`http://localhost:8080`.<br><br>
3. O banco de dados H2 pode ser acessado em `http://localhost:8080/h2-console`.<br>
    - URL do JDBC: `jdbc:h2:mem:testdb`<br>
    - Usuário: `sa`<br>
    - Senha: `password`<br>

<br>

## Endpoints

---

### **Criar Usuário**
- **URL:** `/users`
- **Método:** `POST`
- **Corpo da Requisição:**
  ```json
  {
    "firstName": "{ Nome do usuário }",
    "lastName": "{ Sobrenome do usuário }",
    "email": "{ Email do usuário } ",
    "password": "{ Senha do usuário } "
  }
  ```
- **Resposta:**
  ```http
  201 Created
  ```
<br>

### **Buscar Usuário pelo Email**
- **URL:** `/users/get?email={email}`
- **Método:** `GET`
  <br><br>
- **Resposta:**
  ```http
  200 OK
  ```
  ```json
  {
    "firstName": "{ Nome do usuário }",
    "lastName": "{ Sobrenome do usuário }",
    "email": "{ Email do usuário } "
  }
  ```
<br>

### **Atualizar Usuário**
- **URL:** `/users/update/{id}`
- **Método:** `PUT`
- **Corpo da Requisição:**
  ```json
  {
    "firstName": "{ Novo nome do usuário }",
    "lastName": "{ Novo sobrenome do usuário }",
    "password": "{ Nova senha do usuário } "
  }
  ```
- **Resposta:**
  ```http
  204 No Content
  ```
<br>

### **Deletar Usuário pelo Id**
- **URL:** `/users/delete/{id}`
- **Método:** `DELETE`
  <br><br>
- **Resposta:**
  ```http
  204 NO CONTENT
  ```