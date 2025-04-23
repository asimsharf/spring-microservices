mvn archetype:generate -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
cd common-lib
mvn clean install
mvn install:install-file -Dfile=target/common-lib-1.0-SNAPSHOT.jar \
  -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -Dversion=1.0-SNAPSHOT -Dpackaging=jar
mvn deploy:deploy-file -Dfile=target/common-lib-1.0-SNAPSHOT.jar \
  -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -Dversion=1.0-SNAPSHOT -Dpackaging=jar \
  -Durl=http://localhost:8081/repository/maven-releases/ \
  -DrepositoryId=snapshots-repo
mvn deploy:deploy-file -Dfile=target/common-lib-1.0-SNAPSHOT.jar \# spring-microservices


Absolutely! Here's a **clean, structured summary** of your **Spring Boot Microservices Setup (Without Docker)** — step by step — from `config-server` to `user-service`, including all key concepts, file contents, and configuration.

You can save this as your personal study/reference guide. 🧠📚

---

# 📘 Spring Boot Microservices Setup Guide (Local, No Docker)

---

## 🔧 TECHNOLOGY STACK

- Spring Boot 3.4.x
- Spring Cloud 2024.0.x
- Spring Cloud Config (for central config)
- Spring Cloud Eureka (for service registry/discovery)
- Spring Cloud Gateway (as API Gateway)
- H2 / MySQL for persistence
- Maven for build
- Java 17 or 21

---

## 🏗️ PROJECT STRUCTURE

```
spring-microservices/
├── config-server/         # Spring Cloud Config Server
├── discovery-server/      # Eureka Server
├── api-gateway/           # API Gateway using Spring Cloud Gateway
├── common-lib/            # Shared library (DTOs, utils, exceptions)
├── user-service/          # Microservice for user management
└── docker-compose.yml     # (optional later)
```

---

## ✅ STEP 1: CONFIG SERVER

### Dependencies:
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

### Main class:
```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

### `application.properties`:
```properties
server.port=8888
spring.application.name=config-server

spring.cloud.config.server.git.uri=https://github.com/YOUR-ORG/your-config-repo.git
spring.cloud.config.server.git.clone-on-start=true
```

---

## ✅ STEP 2: DISCOVERY SERVER (EUREKA)

### Dependencies:
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

### Main class:
```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

### `application.properties`:
```properties
server.port=8761
spring.application.name=discovery-server

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

---

## ✅ STEP 3: API GATEWAY

### Dependencies:
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### Main class:
```java
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

### `application.properties`:
```properties
server.port=8080
spring.application.name=api-gateway

eureka.client.service-url.defaultZone=http://localhost:8761/eureka

spring.cloud.gateway.routes[0].id=user-service
spring.cloud.gateway.routes[0].uri=lb://USER-SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/users/**
```

---

## ✅ STEP 4: COMMON LIB

### Create using Maven:
```bash
mvn archetype:generate -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

Contains:
- DTOs
- Exceptions
- Utility classes

Set as a JAR in its `pom.xml`:
```xml
<packaging>jar</packaging>
```

Add this library to other services as a dependency.

---

## ✅ STEP 5: USER SERVICE

### Dependencies:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
</dependency>
```

### Main class:
```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

### `bootstrap.properties`:
```properties
spring.application.name=user-service
spring.cloud.config.uri=http://localhost:8888
spring.config.import=configserver:
```

### `application.properties`:
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

## 🚀 HOW TO RUN EVERYTHING (LOCALLY)

### Order:
1. `config-server` → `./mvnw spring-boot:run`
2. `discovery-server` → `./mvnw spring-boot:run`
3. `api-gateway` → `./mvnw spring-boot:run`
4. `user-service` → `./mvnw spring-boot:run`

### Use Multiple Terminal Tabs or Run from IDE

---

## 📦 OPTIONAL: SHELL SCRIPT TO RUN ALL

```bash
#!/bin/bash

(cd config-server && ./mvnw spring-boot:run) &
sleep 5

(cd discovery-server && ./mvnw spring-boot:run) &
sleep 5

(cd api-gateway && ./mvnw spring-boot:run) &
sleep 5

(cd user-service && ./mvnw spring-boot:run) &
```

---

## 🧠 COMMON ERRORS & FIXES

| Error Message                              | Fix                                                                 |
|--------------------------------------------|----------------------------------------------------------------------|
| `EnableConfigServer cannot be resolved`     | Add `spring-cloud-config-server` dependency                         |
| `No spring.config.import`                   | Add `spring.config.import=configserver:` to bootstrap/properties    |
| `User service not showing in Eureka`        | Check app name and Eureka URL are correct                           |
| `Config Server can't find repo`             | Check Git URI and branch; use HTTPS or SSH                          |

---

Would you like this as a downloadable **PDF or Markdown file** too?