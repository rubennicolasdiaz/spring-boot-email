# 🚀 Spring Boot Email
[![Build Status](https://github.com/rubennicolasdiaz/spring-boot-email/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/rubennicolasdiaz/spring-boot-email/actions/workflows/build.yml)
[![Tests](https://img.shields.io/github/actions/workflow/status/rubennicolasdiaz/spring-boot-email/tests.yml?label=tests&logo=pytest&logoColor=white)](https://github.com/rubennicolasdiaz/spring-boot-email/actions)
![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen?logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)
![Status](https://img.shields.io/badge/Status-Active-success)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 📚 Índice

- [📖 Descripción](#-descripción)
- [⚙️ Tecnologías y Herramientas](#️-tecnologías-y-herramientas)
- [📦 Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [🧠 Funcionamiento](#-funcionamiento)
- [🔐 Seguridad](#-seguridad)
- [🌍 Configuración de CORS](#-configuración-de-cors)
- [🧪 Tests](#-tests)
- [🐳 Dockerización y Despliegue](#-dockerización-y-despliegue)
- [⚙️ Automatización (Mantener Activa la App)](#️-automatización-mantener-activa-la-app)
- [🎥 Vídeo explicativo en YouTube](#-vídeo-explicativo-en-youtube)
- [🧾 Licencia](#-licencia)
- [🧑‍💻 Autor](#%E2%80%8D-autor)

---

## 📖 Descripción

**Spring Boot Email** es una aplicación desarrollada con el framework de Java **Spring Boot** que permite recibir peticiones HTTP **POST** y **GET**, y reenviar los datos del formulario de mi web personal [rubennicolasdiaz.me/contacto.html](https://rubennicolasdiaz.me/contacto.html) a mi cuenta de correo de **Yahoo**.

---

## ⚙️ Tecnologías y Herramientas

- ☕ **Java 21**
- 🌱 **Spring Boot 3.5.5**
- 💾 **Maven**
- 🧪 **Mockito / JUnit 5** (Testing)
- 🐳 **Docker** (Empaquetar y Despliegue)
- 🔧 **GitHub Actions** (CI/CD)
- ☁️ **Fly.io** (Despliegue en la nube)

---

## 📦 Arquitectura del Proyecto

```text
src/
├── main/
│   ├── java/com/rubennicolasdiaz/springbootemail/
│   │   ├── SpringBootEmailApplication.java        # Clase principal (entry point)
│   │   ├── controller/
│   │   │   ├── EmailController.java               # Endpoint POST /send-email
│   │   │   └── HealthController.java              # Endpoint GET /health
│   │   ├── dto/
│   │   │   └── EmailDTO.java                      # Objeto para los datos del formulario
│   │   ├── service/
│   │   │   └── EmailService.java                  # Lógica de envío de correos
│   │   ├── config/
│   │   │   ├── EmailConfig.java                   # Configuración de SMTP y credenciales
│   │   │   └── CorsConfig.java                    # Configuración CORS
│   │   └── template/
│   │       └── EmailTemplate.java                 # Generación del HTML del correo (opcional)
│   └── resources/
│       ├── templates/
│       │   └── email-template.html                # Plantilla HTML del cuerpo del correo
│       ├── application.properties                 # Configuración general del proyecto
│       └── logback-spring.xml                     # (Opcional) Configuración de logs
└── test/
    └── java/com/rubennicolasdiaz/springbootemail/
        ├── controller/
        │   └── EmailControllerTest.java           # Test del endpoint /send-email
        ├── service/
        │   └── EmailServiceTest.java              # Test de la lógica de envío
        └── SpringBootEmailApplicationTests.java   # Test de carga del contexto
```

## 🧠 Funcionamiento

1. El usuario envía un formulario desde la web (`https://www.rubennicolasdiaz.me/contacto.html`).
2. La aplicación recibe una **petición HTTP POST** en el endpoint `/send-email`.
3. Los datos se encapsulan en un objeto `EmailDTO` con los campos:
   ```json
   { "name": "Nombre", "email": "email@mail.com", "subject": "Asunto", "message": "Mensaje" }
   ```
4. El servicio envía un correo a la cuenta configurada mediante SMTP (Yahoo).

## 🔐 Seguridad

Las credenciales (usuario y contraseña del correo) no se hardcodean ni se suben al repositorio.
Se gestionan mediante variables de entorno tanto en nuestra máquina local (IntelliJ) como en el entorno de despliegue.

## 🌍 Configuración de CORS

Se restringe el acceso a los endpoints para que solo puedan ser llamados desde el dominio:

https://rubennicolasdiaz.me

## 🧪 Tests

Los tests se encuentran en la carpeta:

src/test/java/...


Ejecutar los tests manualmente:

./mvnw test


Los tests se ejecutan automáticamente en cada commit/pull request mediante GitHub Actions.

## 🐳 Dockerización y Despliegue

Dockerfile base:

FROM eclipse-temurin:21-jdk
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


Para construir la imagen:

docker build -t spring-boot-email .


Para ejecutar localmente:

docker run -p 8080:8080 spring-boot-email


Desplegado en:

👉 https://spring-boot-email-rubennicolas.fly.dev

## ⚙️ Automatización (Mantener Activa la App)

Con GitHub Actions, se ejecuta un workflow cada 5 minutos para llamar al endpoint /health y evitar que el servicio desplegado en Fly.io entre en hibernación.

## 🎥 Vídeo explicativo en YouTube
▶️ [Reenvío datos de formulario a email con Spring Boot - Creada en framework Spring Boot con Java](https://www.youtube.com/watch?v=sfBY5qnMnDs)  


## 🧾 Licencia

Este proyecto se distribuye bajo licencia MIT.

## 🧑‍💻 Autor

**Rubén Nicolás Díaz**

🌐 [Portafolio](https://www.rubennicolasdiaz.me)  
💼 [LinkedIn](https://linkedin.com/in/rubennicolasdiaz)  
📫 [Email](mailto:ruben.nicolasdiaz@yahoo.com)

&copy; 2025 Rubén Nicolás Díaz
