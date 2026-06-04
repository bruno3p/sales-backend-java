# 🏥 Documentação Técnica - Sales Backend Java (Clínica Médica)

Esta documentação fornece uma visão geral completa e detalhada da arquitetura, tecnologias, decisões de design e fluxo de funcionamento do nosso Backend.

---

## 🛠️ 1. O Que Usamos (Tech Stack)

Nossa aplicação foi construída utilizando as seguintes tecnologias e bibliotecas modernas:

* **Linguagem:** Java 21 (Última versão LTS, com suporte a records e pattern matching avançado).
* **Framework Principal:** Spring Boot 3.x (Ecossistema robusto para APIs RESTful).
* **Segurança:** Spring Security + JWT (JSON Web Tokens) para autenticação Stateless e criptografia BCrypt.
* **Banco de Dados:** H2 Database (Banco em memória, ideal para desenvolvimento rápido e testes sem dependências externas).
* **Testes:** JUnit 5 (Framework de testes) + Mockito (Para mockar dependências nos testes unitários) + Spring Boot Test.
* **ORM:** Spring Data JPA + Hibernate (Mapeamento objeto-relacional).
* **Build Tool:** Maven (Gerenciamento de dependências via `pom.xml`).
* **Deploy/Nuvem:** Render (PaaS) + Docker (Containerização via Dockerfile multi-stage).

---

## 🎯 2. O Que Fizemos (Principais Funcionalidades)

Transformamos uma API base em um servidor de produção seguro e testado. Nossos maiores marcos foram:

1. **Autenticação Segura (Login):** Implementamos um fluxo de login (`/api/auth/login`) que verifica e-mail e senha e retorna um token JWT assinado.
2. **Blindagem de Rotas (Spring Security):** Bloqueamos rotas sensíveis, exigindo o Token JWT no cabeçalho HTTP (`Authorization: Bearer <token>`).
3. **CORS à Prova de Balas:** Configuramos o Spring Security para aceitar requisições de Front-ends hospedados na Vercel (independente de ser o link oficial ou links dinâmicos de preview como `-beige`), garantindo que o fluxo não quebre no navegador por erro de "Network Error".
4. **Criptografia de Senhas:** Nenhuma senha é salva em texto puro. Ao cadastrar, a senha é criptografada com `BCryptPasswordEncoder`.
5. **População de Dados Iniciais (Seed):** Criamos um script (`data.sql`) que popula automaticamente o banco H2 com 20 médicos reais (diversas especialidades), pacientes e consultas. Todos com senhas Hasheadas, facilitando testes de interface.
6. **Testes Unitários (Cobertura):** Desenvolvemos testes que simulam o comportamento do sistema para garantir que não haja regressão (quebras no futuro).

---

## 📍 3. Onde Fizemos (Arquitetura em Camadas)

O projeto segue a arquitetura **MVC (Model-View-Controller)** com foco na separação de responsabilidades (Service Pattern).

### 📁 Configurações (`/config` e `/security`)
* **`SecurityConfig.java`**: O coração da segurança. Aqui definimos quem tem acesso ao quê. Liberamos as rotas de cadastro (`POST /api/doctors` e `/api/patients`), exigimos autenticação nas demais (`anyRequest().authenticated()`), adicionamos o `CorsConfigurationSource` (para liberar a Vercel) e configuramos o `BCrypt`.
* **`JwtUtil.java`**: Nossa "fábrica de chaves". Classe responsável por gerar o token JWT (usando uma chave secreta e assinando com o algoritmo HS256) e extrair o e-mail/roles dele em requisições futuras.
* **`JwtFilter.java`**: O "porteiro" da API. É um filtro que roda antes de cada requisição. Ele pega o token JWT do cabeçalho, valida, e se for genuíno, injeta o usuário no contexto de segurança do Spring.

### 📁 Controladores (`/controller`)
Os controladores recebem as requisições HTTP do Front-end (Vercel) e chamam a camada de negócio.
* **`AuthController.java`**: Recebe o `LoginDTO`, valida no banco de dados e retorna o token JWT e os dados do usuário.
* **`DoctorController.java` & `PatientController.java`**: Controlam as operações CRUD (Criar, Ler, Atualizar, Deletar) de entidades.
* **`MedicalReportController.java`**: Controla os laudos médicos, com suporte à integração com IA (Gemini) para resumir os prontuários.

### 📁 Regra de Negócio (`/service`)
* **`DoctorService.java` & `PatientService.java`**: Onde a mágica acontece. Aqui nós codificamos a lógica de que "se um novo paciente se cadastrar, a senha deve passar no BCrypt antes de ser salva no repositório".
* **`AiService.java`**: Responsável por fazer chamadas externas para a API do Google Gemini.

### 📁 Entidades e Banco de Dados (`/model` e `/repository`)
* **Classes de Modelo**: `Doctor`, `Patient`, `Appointment`, representam as tabelas do Banco. Adicionamos nelas campos essenciais como `password`, `role` e `avatar`.
* **Repositórios**: Interfaces do `JpaRepository` que realizam as Queries sem precisarmos escrever SQL manualmente (Ex: `findByEmail()`).

---

## 🧪 4. Como Testamos (Unit Tests)

A confiabilidade é essencial. Implementamos testes na pasta `src/test/java/...`:

1. **Service Tests (`DoctorServiceTest`, `PatientServiceTest`):** 
   * **Como:** Usamos a anotação `@InjectMocks` para a classe a ser testada e `@Mock` para fingir (mockar) as respostas do banco de dados e do BCrypt.
   * **Por quê:** Isso garante que a lógica de "criptografar a senha no cadastro" ou "buscar um paciente pelo ID" funcione perfeitamente isolada, sem subir um banco de dados real (sendo super rápido).
   
2. **Controller Tests (`DoctorControllerTest`, etc):**
   * **Como:** Testamos as chamadas HTTP fingindo as respostas dos Services. Verificamos se o controlador devolve Status 200 (OK) ou 201 (Created) quando os dados estão corretos.

---

## 🚀 5. Como Fizemos o Deploy (Infraestrutura)

Para transformar nosso código local em um serviço na internet:

1. **`Dockerfile`**: Escrevemos um arquivo Docker no formato *Multi-stage Build*.
   * **Stage 1:** Usa a imagem do Maven para compilar o projeto (`mvn clean package -DskipTests`).
   * **Stage 2:** Usa uma imagem Java Alpine levíssima só para rodar o arquivo `.jar` final.
   * **Por quê?** Isso deixa a aplicação leve, rápida e isolada de problemas do servidor hospedeiro.
2. **Render (Nuvem)**: Conectamos a nuvem diretamente à branch `main` do GitHub.
   * Sempre que você envia código (Push) para a main, o Render baixa o código, roda o Dockerfile, injeta as **Variáveis de Ambiente** secretas (`GEMINI_API_KEY`, `JWT_SECRET`) de forma protegida e atualiza a URL no ar (`https://sales-backend-java.onrender.com`).

---

## 💡 6. Pra que fizemos tudo isso?

O objetivo principal desta engenharia foi criar um **Back-end de Produção Real**:
* **Segurança Profissional:** Impedimos que hackers roubem as senhas do banco (graças ao Hash do BCrypt) ou acessem dados sigilosos da clínica médica (graças ao bloqueio de rotas com JWT).
* **Independência Front/Back:** Ao criar uma API REST stateless com JWT, o Front-end (React/Vercel) e o Back-end (Java/Render) operam em servidores diferentes, se comunicam de forma escalável e o CORS configurado dinamicamente une as duas pontas.
* **Escalabilidade e Qualidade:** Com testes automatizados cobrindo os Services e Controllers, a equipe pode adicionar novas funcionalidades no futuro com a tranquilidade de que não vai quebrar os fluxos que já existem.
