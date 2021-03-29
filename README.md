# Teste Forleven

API desenvolvida para teste de back end do Forleven.

## Inicialização
- **Java 11 instalado com o `PATH_VARIABLE` configurado e uma IDE de sua preferência.**

- **A aplicação está configurada para fazer uso do banco de dados em memória H2.**

Execute o comando: `mvn install` para instalar as dependências do maven.

Acesse a aplicação em `http://localhost:8080`

Acesso ao banco de dados: `http://localhost:8080/h2`

### Cadastro

- `/students` - GET - Exibe todos os dados cadastrados.
- `/students/{id}` - GET - Exibe as informações de um estudante específico.
- `/students` - POST - Cria um estudante.
- `/students/{id}` - PUT - Atualiza as informações de um estudante específico.
- `/students/{id}` - DELETE - Remove um estudante específico.

**Exemplo para adicionar nova entrada de aluno (JSON)**
```json
{ "name" : "cristofer",
"surname" : "muller",
"enrollment" : "12345",
"phone" : ["999999", "888888"] }
```

## Ferramentas

- Java
- Spring boot
- Spring Web   
- H2 database
