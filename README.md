# Votação API 🗳️

API REST para gerenciamento de sessões de votação em assembleias cooperativas. Sistema desenvolvido em Spring Boot que permite cadastrar pautas, abrir sessões de votação, registrar votos e contabilizar resultados.

---

## 📋 Índice

- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Executando a Aplicação](#executando-a-aplicação)
- [Executando os Testes](#executando-os-testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Banco de Dados](#banco-de-dados)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades Implementadas](#funcionalidades-implementadas)
- [Troubleshooting](#troubleshooting)

---

## 🔧 Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

### Java 21
- **Versão mínima**: Java 21 (LTS)
- **Download**: [Oracle Java JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
- **Verificar instalação**:
  ```bash
  java -version
  ```

### Maven 3.8.1+
- **Versão mínima**: Maven 3.8.1
- **Download**: [Apache Maven](https://maven.apache.org/download.cgi)
- **Verificar instalação**:
  ```bash
  mvn -version
  ```

### Sistema Operacional
- Windows, macOS ou Linux
- Terminal/PowerShell com acesso a comandos básicos

---

## 📦 Instalação

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/desafio-votacao.git
cd desafio-votacao
```

### 2. Verificar Dependências

O projeto usa Maven Wrapper (`mvnw`), então você **não precisa instalar Maven** separadamente.

Para Windows:
```bash
mvnw --version
```

Para macOS/Linux:
```bash
./mvnw --version
```

### 3. Baixar Dependências

```bash
mvnw clean install
```

Este comando irá:
- ✅ Baixar todas as dependências do projeto
- ✅ Compilar o código
- ✅ Executar os testes unitários
- ✅ Empacotar a aplicação

---

## 🚀 Executando a Aplicação

### Opção 1: Com Maven (Recomendado)

```bash
mvnw spring-boot:run
```

A aplicação iniciará em: **http://localhost:8080**

### Opção 2: Executar o JAR Gerado

```bash
mvnw clean package
java -jar target/votacao-api-0.0.1-SNAPSHOT.jar
```

### Verificar se a Aplicação está Rodando

Acesse a documentação da API:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Console H2 Database:
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - URL: `jdbc:h2:file:./data/votacao`
  - User: `sa`
  - Password: (deixar em branco)

---

## 🧪 Executando os Testes

### Executar Todos os Testes

```bash
mvnw test
```

### Executar Testes com Saída Resumida

```bash
mvnw test -q
```

### Executar Testes Específicos

```bash
# Testes da Service de Validação de CPF
mvnw -Dtest=CpfValidacaoServiceTest test

# Testes da Service de Votos
mvnw -Dtest=VotoServiceTest test

# Testes de Performance
mvnw -Dtest=VotoPerformanceServiceTest test

# Testes de Sessão
mvnw -Dtest=SessaoServiceTest test

# Testes de Pauta
mvnw -Dtest=PautaServiceTest test
```

### Gerar Relatório de Cobertura de Testes

```bash
mvnw test jacoco:report
```

Relatório gerado em: `target/site/jacoco/index.html`

---

## 📁 Estrutura do Projeto

```
desafio-votacao/
├── src/
│   ├── main/
│   │   ├── java/br/com/votacao/api/
│   │   │   ├── VotacaoApiApplication.java          # Classe principal
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java              # Configuração Swagger/OpenAPI
│   │   │   ├── controller/
│   │   │   │   ├── v1/
│   │   │   │   │   ├── PautaController.java        # Endpoints de Pautas
│   │   │   │   │   ├── SessaoController.java       # Endpoints de Sessões
│   │   │   │   │   ├── VotoController.java         # Endpoints de Votos
│   │   │   │   │   └── CpfController.java          # Endpoints de Validação CPF
│   │   │   ├── service/
│   │   │   │   ├── PautaService.java               # Lógica de Pautas
│   │   │   │   ├── SessaoService.java              # Lógica de Sessões
│   │   │   │   ├── VotoService.java                # Lógica de Votos
│   │   │   │   └── CpfValidacaoService.java        # Validação e Fake Client CPF
│   │   │   ├── repository/
│   │   │   │   ├── PautaRepository.java            # Repositório de Pautas
│   │   │   │   ├── SessaoRepository.java           # Repositório de Sessões
│   │   │   │   └── VotoRepository.java             # Repositório de Votos
│   │   │   ├── entity/
│   │   │   │   ├── Pauta.java                      # Entidade Pauta
│   │   │   │   ├── Sessao.java                     # Entidade Sessão
│   │   │   │   └── Voto.java                       # Entidade Voto
│   │   │   ├── dto/
│   │   │   │   ├── PautaDTO.java
│   │   │   │   ├── SessaoDTO.java
│   │   │   │   ├── VotoDTO.java
│   │   │   │   ├── CpfValidacaoDTO.java
│   │   │   │   └── ResultadoVotacaoDTO.java
│   │   │   ├── enums/
│   │   │   │   └── VotoStatus.java                 # Enum: ABLE_TO_VOTE, UNABLE_TO_VOTE
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java     # Tratamento global de exceções
│   │   ├── resources/
│   │   │   └── application.properties              # Configurações da aplicação
│   ├── test/
│   │   ├── java/br/com/votacao/api/
│   │   │   ├── VotacaoApiApplicationTests.java
│   │   │   ├── service/
│   │   │   │   ├── CpfValidacaoServiceTest.java
│   │   │   │   ├── PautaServiceTest.java
│   │   │   │   ├── SessaoServiceTest.java
│   │   │   │   ├── VotoServiceTest.java
│   │   │   │   └── VotoPerformanceServiceTest.java
├── data/                                           # Banco H2 (gerado automaticamente)
├── pom.xml                                         # Dependências do Maven
├── mvnw                                            # Maven Wrapper (Linux/macOS)
├── mvnw.cmd                                        # Maven Wrapper (Windows)
└── README.md                                       # Este arquivo
```

---

## 📡 Endpoints da API

### 1. Pautas

#### Cadastrar Nova Pauta
```http
POST /api/v1/pautas
Content-Type: application/json

{
  "descricao": "Eleição do conselho administrativo"
}
```

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "descricao": "Eleição do conselho administrativo"
}
```

#### Listar Todas as Pautas
```http
GET /api/v1/pautas
```

---

### 2. Sessões de Votação

#### Abrir Sessão de Votação
```http
POST /api/v1/sessoes
Content-Type: application/json

{
  "pautaId": 1,
  "minutosExpiracao": 5
}
```

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "pautaId": 1,
  "dataAbertura": "2026-08-17T14:30:00",
  "dataFechamento": "2026-08-17T14:35:00",
  "ativa": true
}
```

#### Obter Sessão por ID
```http
GET /api/v1/sessoes/{id}
```

---

### 3. Votos

#### Registrar Voto
```http
POST /api/v1/votos
Content-Type: application/json

{
  "sessaoId": 1,
  "cpfAssociado": "11144477735",
  "voto": true
}
```

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "sessaoId": 1,
  "cpfAssociado": "11144477735",
  "voto": true
}
```

#### Obter Resultado da Votação
```http
GET /api/v1/votos/resultado/{sessaoId}
```

**Resposta (200 OK)**:
```json
{
  "pautaId": 1,
  "descricaoPauta": "Eleição do conselho administrativo",
  "totalVotos": 100,
  "votosSim": 65,
  "votosNao": 35,
  "resultado": "APROVADO"
}
```

---

### 4. Validação de CPF (Fake Client - Integração com Sistema Externo)

#### Validar CPF (Aleatório)
```http
GET /api/v1/cpf/validar/{cpf}
```

**Resposta - CPF ABLE_TO_VOTE (200 OK)**:
```json
{
  "status": "ABLE_TO_VOTE"
}
```

**Resposta - CPF UNABLE_TO_VOTE (404 Not Found)**:
```json
{
  "status": "UNABLE_TO_VOTE"
}
```

---

## 💾 Banco de Dados

### Configuração

O projeto utiliza **H2 Database** com persistência em arquivo:

- **Tipo**: Banco de dados relacional em memória com arquivo
- **URL**: `jdbc:h2:file:./data/votacao`
- **Usuário**: `sa`
- **Senha**: (vazia)
- **Arquivo de Dados**: `./data/votacao.mv.db`

### Schema Automático

As tabelas são criadas automaticamente na primeira execução:

```sql
CREATE TABLE pauta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  descricao VARCHAR(255) NOT NULL
);

CREATE TABLE sessao (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pauta_id BIGINT NOT NULL,
  data_abertura TIMESTAMP NOT NULL,
  data_fechamento TIMESTAMP NOT NULL,
  ativa BOOLEAN NOT NULL,
  FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);

CREATE TABLE votos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sessao_id BIGINT NOT NULL,
  cpf_associado VARCHAR(11) NOT NULL,
  voto BOOLEAN NOT NULL,
  UNIQUE KEY idx_sessao_cpf (sessao_id, cpf_associado),
  FOREIGN KEY (sessao_id) REFERENCES sessao(id)
);
```

### Acessar o Banco via Console

1. Acesse: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. Configure:
   - **JDBC URL**: `jdbc:h2:file:./data/votacao`
   - **User Name**: `sa`
   - **Password**: (deixar em branco)
3. Clique em "Connect"

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| **Java** | 21 LTS | Linguagem principal |
| **Spring Boot** | 4.1.0 | Framework web e IoC |
| **Spring Data JPA** | 4.1.0 | Persistência de dados |
| **H2 Database** | 2.4.240 | Banco de dados |
| **Hibernate** | 7.4.1 | ORM |
| **Lombok** | 1.18.30 | Redução de boilerplate |
| **SpringDoc OpenAPI** | 3.0.2 | Documentação Swagger |
| **JUnit 5** | 5.10.0 | Testes unitários |
| **Mockito** | 5.5.0 | Mock de dependências |
| **Maven** | 3.8.1+ | Gerenciador de dependências |

---

## ✨ Funcionalidades Implementadas

### Funcionalidades Principais ✅

- ✅ **Cadastro de Pautas**: Criar novas pautas para votação
- ✅ **Abertura de Sessões**: Iniciar sessão de votação com duração configurável (padrão: 1 minuto)
- ✅ **Registro de Votos**: Associados podem votar Sim/Não uma única vez por pauta
- ✅ **Contabilização de Resultados**: Resultado automático (APROVADO/REPROVADO)
- ✅ **Persistência de Dados**: Dados mantidos após restart da aplicação
- ✅ **Tratamento de Erros**: Respostas HTTP apropriadas para cada cenário
- ✅ **Validação de Entrada**: Validação de CPF e regras de negócio

### Tarefas Bônus Implementadas 🎁

#### Bônus 1: Integração com Sistemas Externos ✅
- ✅ **Fake Client de CPF**: Retorna validações aleatórias
- ✅ **Contrato REST**: 
  - CPF válido: `{ "status": "ABLE_TO_VOTE" }` (HTTP 200)
  - CPF inválido: `{ "status": "UNABLE_TO_VOTE" }` (HTTP 404)
- ✅ **Resultado Aleatório**: Mesmo CPF pode ter resultados diferentes em requisições diferentes
- ✅ **Separação de Responsabilidades**: 
  - `validarCpf()`: Validação real de formato e algoritmo
  - `validarCpfRandom()`: Simula integração com sistema externo

#### Bônus 2: Performance ✅
- ✅ **Teste de Performance**: Processa 10.000 votos em menos de 30 segundos
- ✅ **Índice Único**: `(sessao_id, cpf_associado)` para evitar duplicação
- ✅ **Queries Otimizadas**: Contagem de votos por tipo (Sim/Não)
- ✅ **Monitoramento**: Logs de tempo de execução

#### Bônus 3: Versionamento de API ✅
- ✅ **URL com Versão**: Todos os endpoints seguem padrão `/api/v1/...`
- ✅ **Pronto para Evolução**: Estrutura preparada para `/api/v2/...` no futuro
- ✅ **Backward Compatibility**: Facilita transição entre versões

---

## 🧪 Testes

### Cobertura de Testes

- **CpfValidacaoServiceTest**: 6 testes - Validação de CPF (formato, nulo, dígitos iguais, etc.)
- **PautaServiceTest**: Testes de operações com pautas
- **SessaoServiceTest**: Testes de abertura e verificação de sessões
- **VotoServiceTest**: 3 testes - Registro de voto, voto duplicado, sessão encerrada
- **VotoPerformanceServiceTest**: 2 testes - Performance com 10.000 votos, contagem eficiente

### Executar um Teste Específico

```bash
mvnw -Dtest=VotoServiceTest#testVotarComSucesso test
```

---

## 📝 Padrões e Boas Práticas

### Injeção de Dependência
- ✅ Utiliza constructor injection (recomendado pelo Spring)
- ✅ Evita field injection com `@Autowired`

### Tratamento de Exceções
- ✅ `GlobalExceptionHandler` para respostas consistentes
- ✅ `CpfInvalidoException` para erros de validação
- ✅ HTTP Status apropriados (200, 201, 400, 404, 500)

### Validação
- ✅ `@NotNull`, `@NotBlank` em DTOs
- ✅ Validação de regras de negócio na Service
- ✅ Mensagens de erro descritivas

### Testes
- ✅ Mocks com Mockito para isolamento
- ✅ Testes da camada de negócio (Service)
- ✅ Testes de performance

### Documentação
- ✅ Swagger/OpenAPI automático
- ✅ Comentários em código complexo
- ✅ README completo

---

## 📄 Licença

Este projeto foi desenvolvido como desafio técnico.

---