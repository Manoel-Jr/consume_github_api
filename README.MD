## Rodando Localmente via Docker
Para executar a aplicação por um build conteinerizado, lembrando que antes se faz **obrigatório** rodar o comando maven para realizar o build da aplicação e gerar o artefato **jar**.

```bash
$  mvn clean install -DskipTests=true
$  docker build -t info-github:1.0.0 .
$  docker run -d -p 8080:8080 --name info-github info-github:1.0.0
```

## Swagger Documentation API

- **path:** http://localhost:8080/info_github/api/v1/swagger-ui/index.html

## Tecnologia

- **Java:** Versão 17
- **Framework:** Spring boot 3xx

## Token github - Fine-grained 

Passo-passo para gerar um token e realizar mais de 5.000 solicitações por hora, segue a documentação oficial do github.

- **Managing your personal access tokens:** https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens