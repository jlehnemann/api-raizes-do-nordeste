
# Projeto API Raízes do Nordeste Back-end


Projeto acadêmico para desenvolvimento de uma API Back-end para uma rede de lanchonetes nordestinas
Link do repositório no GitHub: https://github.com/jlehnemann/api-raizes-do-nordeste.git

## Tecnologias utilizadas
* Java 21
* Maven
* Spring Boot
* Spring Security
* JJWT
* Hibernate JPA
* PostgreSQL
* DBeaver
* Flyway
* Lombok
* SLF4J
* Postman
* OpenAPI/Swagger
* IntelliJ IDEA
* Git/GitHub
## Pré-requisitos de instalação
* Instalar o Java 21 (recomendado Temurin)
* Instalar o Maven
* Instalar o banco de dados PostgreSQL
* Instalar o aplicativo Postman
* Instalar o DBeaver (opcional, excelente GUI para administrar o PostgreSQL)
* Instalar o Git
## Dependências
As dependências do projeto são gerenciadas automaticamente pelo Maven 
através do arquivo `pom.xml` presente na raiz do repositório. 
Não é necessária nenhuma configuração adicional.
## Configuração do banco de dados PostgreSQL e o seed
Basta instalar o banco de dados! Como se trata de um projeto acadêmico, este foi pensado para trabalhar com o banco de dados 'postgres' criado automaticamente pelo próprio PostgreSQL para facilitar o processo de avaliação do projeto. Assim, também foram mantidos o usuário e senha originais (usuario: postgres ; senha: 12345). Não é necessário o application.properties do projeto, pois já vem configurado para uso com esse banco de dados.

> **Nota:** As credenciais do banco estão expostas no `application.properties` 
> intencionalmente para facilitar a avaliação acadêmica. Em produção, 
> estas seriam configuradas via variáveis de ambiente.

A migração dos seeds (localizados na pasta `/src/main/resources/db/migration`) do banco com dados pré-inseridos estão configurados pelo Flyway, este já incluído no arquivo pom.xml referindo as dependências do Maven para o Spring Boot.
## Como instalar e rodar a aplicação

Clonar o repositorio:
``` bash
git clone https://github.com/jlehnemann/api-raizes-do-nordeste.git
```

Formas de rodar a aplicação:

Opção 1 (com o IntelliJ IDEA):
* abrir a pasta do repositorio IntelliJ IDEA
* rodar `ApiRaizesDoNordesteApplication.java`
* o Flyway cria as tabelas e insere os dados automaticamente

Opção 2 (no Linux, sem IntelliJ IDEA):
* na pasta onde o repositorio foi clonado, rodar o comando:
``` bash
./mvnw spring-boot:run
```

Opção 3 (no Windows, sem IntelliJ IDEA):
* na pasta onde o repositorio foi clonado, rodar o comando:
``` bash
mvnw.cmd spring-boot:run
```

## Como acessar a documentação (Swagger/OpenAPI)
Após a aplicação estar rodando, acessar: 
http://localhost:8080/swagger-ui.html


## Credenciais de acesso
O banco de dados já vem populado com o seguinte usuário administrador:

| Perfil | Email | Senha |
|--------|-------|-------|
| Administrador | joaoadmin@raizesnordeste.com.br | Admin@123 |

> **Nota:** Os demais usuários de teste (gerente, atendentes e cliente) 
> estão disponíveis na coleção Postman, na pasta **Auth**, 
> com os endpoints de registro já configurados e na ordem sugerida de execução.
## Como rodar os testes (Postman)
* Na pasta /postman na raiz do repositório, existem dois arquivos:

variáveis de ambiente:
`API Raízes - tokens.postman_environment.json`

coleção de testes:
`API_Raízes.postman_collection.json`

* No Postman, acessar File -> Import, e importar os dois arquivos acima.
* Antes de rodar os testes, verificar e confirmar que as variáveis de ambiente estão ativas, através da aba lateral Environments.
> **Nota:** As variávies de ambiente do Postman armazenam, no momento do login de cada 
> um dos usuários, seus respectivos tokens de acesso, através de um pequeno script em JavaScript.
* Os testes estão numerados e organizados em pastas, e já vêm salvos na ordem sugerida para serem testados.
* Para rodar todos os testes em sequência e facilitar a avaliação, sugere-se clicar com o botão direito em cima da pasta do projeto no Postman, então em "Run". Na tela do Runner, desmarcar a opção "Stop run if an error occurs" e clicar em "Start Run".
## Logs para auditoria
A aplicação gera logs de auditoria automaticamente durante a execução. 
Os logs são salvos na pasta `/logs` na raiz do projeto.

Os seguintes eventos são registrados:
* Criação, cancelamento e entrega de pedidos
* Processamento de pagamentos
* Acúmulo e resgate de pontos de fidelidade
* Criação e desativação de unidades
* Criação e desativação de promoções
* Cadastro, atualizaação e desativação de produtos
* Entrada e saída de estoque
* Tentativas de acesso não autorizado
* Erros internos do servidor
