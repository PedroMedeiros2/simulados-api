
#  📚 Plataforma de Simulados



<div style="text-align: center;">

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen)
![JWT](https://img.shields.io/badge/JWT-Authentication-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

</div>

<p style="text-align: center;">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="50" height="50"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="spring" width="50" height="50"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original.svg" alt="postgresql" width="50" height="50"/>
</p>



##  📋 Sobre o Projeto



A **Plataforma de Simulados** é um backend desenvolvido em Java com Spring Boot, projetado para oferecer uma experiência completa de simulados e provas online. O sistema permite que usuários se cadastrem, façam login e realizem simulados personalizados, com geração aleatória de questões baseadas em critérios específicos.



###  🌟 Principais Funcionalidades



-  **Autenticação Segura**: Sistema completo de cadastro e login de usuários com JWT

-  **Gerenciamento de Questões**: CRUD completo para questões com múltiplas alternativas

-  **Simulados Personalizados**: Geração de provas aleatórias baseadas em critérios como disciplina e dificuldade

-  **Correção Automática**: Avaliação instantânea com cálculo de nota após submissão

-  **Histórico de Desempenho**: Registro completo dos simulados realizados por usuário



##  🚀 Tecnologias Utilizadas



-  **Java 17+**: Linguagem de programação principal

-  **Spring Boot**: Framework para desenvolvimento da aplicação

-  **Spring Security**: Implementação de autenticação e autorização com JWT

-  **Spring Data JPA**: Persistência de dados e operações com banco de dados

-  **PostgreSQL**: Banco de dados relacional


##  🔧 Instalação e Configuração



###  Pré-requisitos



-  JDK 17 ou superior

-  Maven 3.6+

-  PostgreSQL



###  Passos para Instalação



1.  **Clone o repositório**

```bash

git clone https://github.com/PedroMedeiros2/simulados-api.git

cd simulados-api

```



2.  **Configure o banco de dados**

Edite o arquivo `src/main/resources/application.properties` com suas configurações:

```properties

spring.datasource.url=jdbc:postgresql://localhost:5432/simulados

spring.datasource.username=seu_usuario

spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update

```



3.  **Compile e execute o projeto**

```bash

mvn clean install

mvn spring-boot:run

```


##  📊 Estrutura do Projeto



```

simulados-api/

├── src/

│ ├── main/

│ │ ├── java/io/github/pedromedeiros2/simulados_api/

│ │ │ ├── config/

│ │ │ │ └── security/ # Configurações de segurança e JWT

│ │ │ ├── controller/ # Endpoints da API REST

│ │ │ ├── dto/ # Objetos de transferência de dados

│ │ │ ├── model/ # Entidades JPA

│ │ │ ├── repository/ # Interfaces de acesso a dados

│ │ │ ├── service/ # Lógica de negócio

│ │ │ └── SimuladosApiApplication.java

│ │ └── resources/

│ │ └── application.properties # Configurações da aplicação

└── pom.xml # Dependências Maven

```



##  🔍 Endpoints Principais



###  Autenticação



-  **POST**  `/api/auth/signup` - Cadastro de novos usuários

-  **POST**  `/api/auth/signin` - Login e obtenção de token JWT



###  Questões



-  **GET**  `/api/questoes` - Listar todas as questões

-  **GET**  `/api/questoes/{id}` - Buscar questão por ID

-  **POST**  `/api/questoes` - Criar nova questão

-  **POST**  `/api/questoes/batch` - Cria novas questões em lote

-  **PUT**  `/api/questoes/{id}` - Atualizar questão existente

-  **DELETE**  `/api/questoes/{id}` - Remover questão



###  Simulados



-  **POST**  `/api/simulados/gerar` - Gerar simulado personalizado

-  **POST**  `/api/simulados/submeter` - Submeter respostas para correção

-  **GET**  `/api/simulados/{id}` - Visualizar detalhes de um simulado

-  **GET**  `/api/simulados/meu-historico` - Listar histórico de simulados do usuário



##  📝 Exemplos de Uso



###  Gerando um Simulado



`POST /api/simulados/gerar`

```json
{
  "filtros": [
    {
      "disciplina": "Matemática",
      "dificuldades": ["Difícil"],
      "numeroQuestoes": 3
    },
    {
      "disciplina": "Português",
      "dificuldades": ["Fácil", "Médio"],
      "numeroQuestoes": 7
    }
  ]
}

```



###  Submetendo Respostas

`POST /api/simulados/submeter`

```json
{

    "simuladoId":  "a67ac879-ecbb-4428-9bbf-09dd046ef6ef",

    "respostas":  {

        "936cf5b3-1aea-496b-ae34-085f427367f0":  "A",

        "61e875f6-0b74-4937-8234-b5c3b3594fdd":  "B",

        "1e2258c5-bcd7-455b-9af6-af21908b03a3":  "C",

        "d394633b-ad30-48ee-9f10-236d6bdb6fd9":  "D",

        "4a3b0976-0eca-4d79-86ed-17e1955742cc":  "E"
    }

}

```

  
---

<div style="text-align: center;">

<p>Desenvolvido para aprendizado e prática de Spring Boot e Java</p>

</div>