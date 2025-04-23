
# Spring Boot Microservices Architecture (With Config Server) – Complete Setup Guide


Everything is structured for **clarity and easy implementation** ✅

---

# 📘 Spring Boot Microservices Architecture (With Config Server) – Complete Setup Guide

> **Goal**: Create a microservices ecosystem using Spring Boot, Spring Cloud Config, Eureka, and Spring Cloud Gateway — all running locally, with proper separation of services.

---

## 🧱 PROJECT STRUCTURE

```
spring-microservices/
├── config-server/         # Central config server (Git-based)
├── discovery-server/      # Eureka registry server
├── api-gateway/           # Gateway to route API traffic
├── common-lib/            # Shared DTOs/utils (Java module, no Spring)
├── user-service/          # User microservice (template for others)
└── docker-compose.yml     # (optional later, not used now)
```

---

## 🛠️ STEP 1: CONFIG SERVER (`config-server/`)

### ✅ Dependencies
- Spring Web
- Spring Cloud Config Server

### ✅ Main Class
```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

### ✅ `application.properties`
```properties
server.port=8888
spring.application.name=config-server

spring.cloud.config.server.git.uri=https://github.com/YOUR_USERNAME/YOUR_CONFIG_REPO.git
spring.cloud.config.server.git.clone-on-start=true
```

> 🔐 Use HTTPS with token or SSH access if your repo is private.

---

## 🛠️ STEP 2: DISCOVERY SERVER (`discovery-server/`)

### ✅ Dependencies
- Spring Web
- Eureka Server

### ✅ Main Class
```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

### ✅ `application.properties`
```properties
server.port=8761
spring.application.name=discovery-server

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

---

## 🛠️ STEP 3: API GATEWAY (`api-gateway/`)

### ✅ Dependencies
- Spring Web
- Spring Cloud Gateway
- Eureka Discovery Client

### ✅ Main Class
```java
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

### ✅ `application.properties`
```properties
server.port=8080
spring.application.name=api-gateway

eureka.client.service-url.defaultZone=http://localhost:8761/eureka

spring.cloud.gateway.routes[0].id=user-service
spring.cloud.gateway.routes[0].uri=lb://USER-SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/users/**
```

---

## 🛠️ STEP 4: COMMON LIBRARY (`common-lib/`)

- Not generated via Spring Initializr
- Create with Maven archetype:
```bash
mvn archetype:generate -DgroupId=com.example -DartifactId=common-lib \
  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

### Used For:
- DTOs
- Shared exceptions
- Enums
- Utility classes

### In `pom.xml`
```xml
<packaging>jar</packaging>
```

Other services will include this module as a dependency.

---

## 🛠️ STEP 5: USER SERVICE (`user-service/`)

### ✅ Dependencies
- Spring Web
- Spring Data JPA
- Spring Boot DevTools (optional)
- H2 or MySQL
- Spring Cloud Config Client
- Eureka Discovery Client

### ✅ Main Class
```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

### ✅ `bootstrap.properties`
```properties
spring.application.name=user-service
spring.cloud.config.uri=http://localhost:8888
spring.config.import=configserver:
```

> This allows the service to pull config from the Git repo via Config Server.

### ✅ `application.properties`
```properties
server.port=9001

eureka.client.service-url.defaultZone=http://localhost:8761/eureka

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

---

## 🏃 HOW TO RUN (LOCALLY)

Run each service from terminal or your IDE, in the following order:

```bash
# 1. Config Server
cd config-server
./mvnw spring-boot:run

# 2. Eureka Discovery Server
cd ../discovery-server
./mvnw spring-boot:run

# 3. API Gateway
cd ../api-gateway
./mvnw spring-boot:run

# 4. User Service
cd ../user-service
./mvnw spring-boot:run
```

> Open:  
> - `http://localhost:8761` → Eureka dashboard  
> - `http://localhost:8080/users` → Proxy to user-service via gateway

---

## 🧠 TIPS

| ✅ Do This                        | Why                                                      |
|----------------------------------|-----------------------------------------------------------|
| Use `spring.config.import=configserver:` | Required in Spring Boot 3.1+ when using config server |
| Add services to Eureka           | So they can auto-discover each other                     |
| Use consistent app names         | Gateway routes use `spring.application.name`             |
| Create a Git-based config repo   | Example: `user-service.properties` inside `/user-service` folder |

---

## 📁 Example Git Repo Structure for Config Server

```
your-config-repo/
├── user-service.properties
├── order-service.properties
├── discovery-server.properties
├── api-gateway.properties
```

---

## ✅ DONE!

You now have:
- Centralized config
- Service discovery
- Gateway routing
- A running user microservice

---

Want this guide as a downloadable **PDF**, **Markdown**, or **codebase template**? I can generate it for you!