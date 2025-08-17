# Financial Control Application

Este é um sistema de controle financeiro pessoal desenvolvido com Spring Boot, focado em gerenciar receitas, despesas (dívidas) e categorias, além de oferecer funcionalidades de notificação via Kafka.

## Funcionalidades

*   **Gestão de Usuários:**
    *   Criação, listagem e recuperação de usuários.
    *   Autenticação de usuários via JWT.
*   **Gestão de Categorias:**
    *   Criação, listagem e recuperação de categorias para transações.
*   **Gestão de Transações:**
    *   Adição de receitas.
    *   Criação de dívidas (parceladas e recorrentes).
    *   Listagem de transações.
    *   Pagamento de parcelas de dívidas.
*   **Notificações de Dívidas:**
    *   Sistema de notificação de dívidas via Apache Kafka.
    *   Agendamento de notificações.
*   **API RESTful:**
    *   Endpoints para todas as funcionalidades de gestão.
    *   Documentação da API com Springdoc (Swagger UI).
*   **Segurança:**
    *   Autenticação e autorização baseadas em JWT (JSON Web Tokens).

## Tecnologias Utilizadas

*   **Backend:**
    *   Java 21+
    *   Spring Boot
    *   Spring Data MongoDB
    *   Spring Security (JWT)
    *   Apache Kafka (para mensageria)
    *   Springdoc OpenAPI (Swagger UI)
*   **Build Tool:**
    *   Gradle
*   **Containerização:**
    *   Docker
    *   Docker Compose
*   **Banco de Dados:**
    *   MongoDB (configurado via Docker Compose)
*   **Testes:**
    *   JUnit 5
    *   Testcontainers

## Configuração e Instalação

Para configurar e rodar o projeto localmente, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

*   Java Development Kit (JDK) 21 ou superior
*   Docker e Docker Compose

### 1. Clonar o Repositório

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd financial-control
```

### 2. Configurar o Ambiente com Docker Compose

O projeto utiliza Docker Compose para orquestrar o banco de dados MongoDB e o Apache Kafka (incluindo Zookeeper e AKHQ para gerenciamento).

```bash
docker-compose -f compose.yaml up -d
```

Este comando irá iniciar os serviços necessários em segundo plano. Você pode verificar o status dos containers com `docker-compose ps`.

A aplicação estará disponível em `http://localhost:8080` após a inicialização dos serviços.

## Uso

### Documentação da API (Swagger UI)

Após iniciar a aplicação, você pode acessar a documentação interativa da API (Swagger UI) em:

`http://localhost:8080/swagger-ui.html`

Aqui você encontrará todos os endpoints disponíveis, seus modelos de requisição/resposta e poderá testá-los diretamente.

### AKHQ (Kafka UI)

Para gerenciar seus tópicos e mensagens Kafka, você pode acessar o AKHQ em:

`http://localhost:8081`

---

**Nota:** Certifique-se de que as variáveis de ambiente ou configurações no `application.properties` estejam corretas para a conexão com o banco de dados e Kafka.