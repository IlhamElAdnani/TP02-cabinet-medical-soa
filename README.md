#  Cabinet Médical – Architecture SOA (Spring Boot)

##  Description
Ce projet implémente un système de gestion d’un cabinet médical basé sur une
architecture orientée services (SOA) en utilisant Spring Boot.

Il permet la gestion de :
-  Patients
-  Médecins
-  Rendez-vous
-  Consultations

---

##  Architecture du projet

Le projet est composé de plusieurs services indépendants :

cabinet-medical-soa
│
├── patient-service
├── medecin-service
├── rendezvous-service
├── consultation-service
└── cabinetrepo (models + repositories partagés)

##  Technologies utilisées
- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- H2 Database
- Lombok
- REST API
- Architecture SOA

---

##  Configuration

### Base de données
Chaque service utilise une base H2 en mémoire.

Exemple `application.properties` :

```properties
server.port=8083

spring.datasource.url=jdbc:h2:mem:rendezvous-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

---

##  Lancer le projet
###  Compiler le projet

Dans chaque service :

mvn clean install

---
###  Démarrer un service
mvn spring-boot:run

--- 
## Ports des services
Service	Port
Patient	8081
Médecin	8082
Rendez-Vous	8083
Consultation	8084
