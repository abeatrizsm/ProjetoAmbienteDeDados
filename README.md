# Biblioteca Universitária – Sistema de Gestão de Acervo e Empréstimos

Sistema desktop desenvolvido em **Java (Swing + JDBC)** para gerenciamento de livros, usuários, aquisições e empréstimos.  
Utiliza arquitetura em camadas, persistência via **MySQL** e interface construída com **JFrame + GUI Designer**.

Projeto desenvolvido como entrega acadêmica da disciplina **Ambiente de Dados**.

---

## Arquitetura do Projeto
---
BibliotecaUniversitaria/
│
├── src/
 ├── Assets/
 ├── Controller/
 ├── DAO/
 ├── DTO/
 ├── Globals/
 ├── Service/
 ├── View/
 └── Main/
---

---

## Tecnologias Utilizadas

### Linguagem e Interface
- Java 21+
- Swing (JFrame + GUI Designer)
- AWT/Swing Components

### Persistência e Banco
- JDBC (MySQL Connector/J)
- MySQL Workbench
- MySQL Server

### Arquitetura
- Padrão MVC em camadas:
  - **View** → interface gráfica
  - **Controller** → coordenação das regras de negócio
  - **Service** → validações e lógica central
  - **DAO** → comunicação com o banco
  - **DTO** → transporte de dados
  - **Globals** → estado global do usuário logado

---

## Funcionalidades

### Perfil Aluno
- Visualizar livros disponíveis
- Solicitar empréstimos
- Realizar devoluções
- Consultar histórico pessoal
- Sugerir novas aquisições

### Perfil Administrador
- Cadastrar, alterar e excluir livros
- Gerenciar empréstimos
- Registrar novas aquisições
- Acompanhar estatísticas e histórico geral
- Gerenciar usuários

---

## Modelagem das Principais Tabelas

### usuario
- id_usuario  
- nome  
- email  
- senha  
- tipo_usuario (aluno ou administrador)

### livro
- id_livro  
- titulo  
- autor  
- categoria  
- ano  
- quantidade  
- quantidade_disponivel  

### emprestimo
- id_emprestimo  
- id_usuario (FK)  
- id_livro (FK)  
- data_emprestimo  
- data_devolucao  
- status  

### aquisicoes
- id_aquisicao  
- id_usuario (FK)  
- titulo_solicitado  
- justificativa  
- data_solicitacao  

---

## Relacionamentos

- Usuário solicita empréstimos de livros  
- Administrador gerencia o acervo  
- Livros podem possuir vários empréstimos  
- Usuários podem solicitar aquisições  
- Empréstimo relaciona **(usuário ↔ livro)**  
- Aquisição relaciona **(usuário ↔ pedido)**  

---

## Execução do Sistema

1. Configure a conexão no arquivo **ConexaoDAO**:
private static final String URL = "jdbc:mysql://localhost:3306/BibliotecaUniversitaria";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";

2. Importe o **MySQL Connector/J** no IntelliJ (Project Structure → Dependencies).

3. Execute o arquivo **Main** para iniciar o sistema.

---

## Arquitetura Interna
**View → Controller → Service → DAO → MySQL**

Fluxo completo de validação, regra de negócio e persistência.

---

## Autora

Ana Beatriz Silveira Mendes  
4º Semestre – Ciências da Computação  
Universidade de Fortaleza (UNIFOR)

---

## Licença

Projeto desenvolvido exclusivamente para fins acadêmicos.
