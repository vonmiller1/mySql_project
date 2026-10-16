# MyShop

Spring Boot e-commerce demo for customers who browse products, manage a cart, and pay with Razorpay, and for administrators who manage the catalogue.

## Overview

MyShop is a server-rendered online store built with Spring Boot and Thymeleaf. Customers register with email verification (OTP), sign in, add items to a session-backed cart, and complete checkout through Razorpay. Administrators manage products and upload images stored under `src/main/resources/static/images`. Persistence uses Spring Data JPA against MySQL with Hibernate `ddl-auto=update`.

## Features

- Customer signup with validation, OTP verification flow, and login/logout
- Product catalogue browsing and cart add/remove with stock checks
- Razorpay order creation and payment confirmation
- Order history for signed-in customers
- Admin dashboard: add, edit, delete products and image upload
- Bootstrap admin account endpoint (intended for initial setup only)
- Custom 404 and generic error pages
- SMTP helpers for OTP email (optional; sending is commented out in services)

## Tech stack

| Layer | Technology |
| --- | --- |
| Runtime | Java 17 |
| Framework | Spring Boot 3.2.1 (Web, Data JPA, Validation, Mail, Thymeleaf) |
| Build | Maven (wrapper included) |
| Database | MySQL |
| Payments | Razorpay Java SDK 1.4.4 |
| UI | Thymeleaf templates and static CSS |

## Prerequisites

- Java 17 or newer
- MySQL 8 (or compatible server)
- Razorpay test or live API keys for checkout
- SMTP credentials if you enable OTP email delivery

A separate Maven install is optional; use `./mvnw` or `mvnw.cmd`.

## Getting started

1. Clone the repository and open the project root.
2. Create a MySQL database (default name `myshop`) and a dedicated user.
3. Export the environment variables listed under [Configuration](#configuration).
4. Run the application (see [Running locally](#running-locally)).
5. For the first admin user, use the bootstrap route documented in the admin section of the codebase (`/admin/create-admin/{email}/{password}`) only in a trusted local environment, then sign in as admin.

Do not commit real passwords, SMTP app passwords, or payment keys to git.

## Running locally

On Windows:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/myshop?createDatabaseIfNotExist=true"
$env:SPRING_DATASOURCE_USERNAME = "your_mysql_user"
$env:SPRING_DATASOURCE_PASSWORD = "your_mysql_password"
$env:RAZORPAY_KEY_ID = "your_razorpay_key_id"
$env:RAZORPAY_KEY_SECRET = "your_razorpay_key_secret"
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```sh
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/myshop?createDatabaseIfNotExist=true'
export SPRING_DATASOURCE_USERNAME='your_mysql_user'
export SPRING_DATASOURCE_PASSWORD='your_mysql_password'
export RAZORPAY_KEY_ID='your_razorpay_key_id'
export RAZORPAY_KEY_SECRET='your_razorpay_key_secret'
./mvnw spring-boot:run
```

Open [http://localhost:3333](http://localhost:3333) (default port).

Package a runnable JAR:

```sh
./mvnw package
```

On Windows: `.\mvnw.cmd package`. The artifact is `target/my-shop-1.0.jar`; run with `java -jar target/my-shop-1.0.jar`.

## Testing

Unit/integration tests use Spring Boot Test. From the project root:

```sh
./mvnw test
```

On Windows: `.\mvnw.cmd test`. Tests are not executed as part of the optimization rollout workflow; run them locally when changing behavior.

## Project structure

| Path | Purpose |
| --- | --- |
| `src/main/java/com/demo/myshop/` | Application entry, controllers, services, DAOs, DTOs, helpers |
| `src/main/resources/templates/` | Thymeleaf pages and fragments |
| `src/main/resources/static/` | CSS and product images |
| `src/main/resources/application.properties` | Defaults and `${ENV}` placeholders |
| `src/test/java/` | Context load test |
| `Jenkinsfile`, `Jenkinsfilemysql` | Example CI/deploy pipelines |
| `mysql_install_file.sh` | Example MySQL setup script |

## Configuration

| Environment variable | Purpose | Default |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | JDBC URL | `jdbc:mysql://localhost:3306/myshop?createDatabaseIfNotExist=true` |
| `SPRING_DATASOURCE_USERNAME` | DB user | `root` |
| `SPRING_DATASOURCE_PASSWORD` | DB password | empty |
| `SERVER_PORT` | HTTP port | `3333` |
| `SPRING_JPA_SHOW_SQL` | Log SQL | `false` |
| `SPRING_MAIL_USERNAME` | SMTP user | empty |
| `SPRING_MAIL_PASSWORD` | SMTP password | empty |
| `RAZORPAY_KEY_ID` | Razorpay key ID | empty |
| `RAZORPAY_KEY_SECRET` | Razorpay secret | empty |
| `MYSHOP_PASSWORD_SECRET` | AES key material for stored passwords | dev default in properties (override in production) |

Previously hard-coded Razorpay and mail credentials in source or config should be treated as compromised; rotate them in the provider dashboards before production use.

## Deployment

The Jenkinsfiles and `mysql_install_file.sh` are environment-specific examples. Review sudo privileges, open ports, and secret storage before use. Do not expose MySQL to the public internet; restrict access to application hosts.

## Contributing

1. Fork the repository and create a feature branch from `main`.
2. Keep commits focused; use imperative messages (for example `fix(auth): …`).
3. Open a pull request with a short description of behavior and config changes.
4. Run `./mvnw test` locally before merging substantive changes.

## License

No `LICENSE` file is present in this repository. Add a license file if you intend to distribute or accept external contributions under specific terms.
