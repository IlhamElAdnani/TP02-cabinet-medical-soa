#  CabinetMedicalTp2SOA

##  Contexte

Projet réalisé dans le cadre du **Master IPS – Systèmes Distribués Basés sur les Microservices** à la **Faculté des Sciences de Rabat**.

Objectif : transformer une application monolithique d’un **Cabinet Médical** en **architecture orientée services (SOA)** avec un **ESB Apache Camel**.

Chaque fonctionnalité métier est isolée dans un service autonome et exposée via une API, tandis que l’ESB centralise les accès externes.

---

##  Architecture du projet

Maven **multi-modules** structuré comme suit :

| Module | Rôle | Port |
|--------|------|------|
| `cabinet-esb` | ESB Apache Camel – point d’entrée externe | 8080 |
| `cabinet-repo` | Modèle de données partagé et Repositories JPA | – |
| `patient-service-api` | Service Patient (API + règles métier) | 8082 |
| `medecin-service-api` | Service Médecin (API + règles métier) | 8083 |
| `rendezvous-service-api` | Service Rendez-vous (API + règles métier) | 8084 |
| `consultation-service-api` | Service Consultation (API + règles métier) | 8085 |

---

##  Module `cabinet-repo`

- Contient uniquement les **entités JPA** et **repositories Spring Data JPA**.
- Aucun code métier.
- Base de données : **H2 en mémoire**.
- Configuration (`application.properties`) :

properties
spring.application.name=cabinetMedicalTp2
spring.datasource.url=jdbc:h2:mem:cabinetMedicalSoaTp2DB
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true

##  Packages principaux

- `ma.fsr.soa.cabinetrepo.model` – Contient les entités JPA.
- `ma.fsr.soa.cabinetrepo.repository` – Contient les interfaces Spring Data JPA pour l’accès aux données.

---

##  Services métiers

###  Patient Service

- **Module** : `patient-service-api`  
- **API interne** : `/internal/api/v1/patients`  
- **Port** : 8082  
- **Règles métier** :  
  - Nom et téléphone obligatoires  
  - Date de naissance ≤ date actuelle  
  - Messages d’erreur personnalisés

###  Médecin Service

- **Module** : `medecin-service-api`  
- **API interne** : `/internal/api/v1/medecins`  
- **Port** : 8083  
- **Règles métier** :  
  - Nom, email et spécialité obligatoires  
  - Email valide  
  - Messages d’erreur personnalisés

###  Rendez-vous Service

- **Module** : `rendezvous-service-api`  
- **API interne** : `/internal/api/v1/rendezvous`  
- **Port** : 8084  
- **Règles métier** :  
  - Date du rendez-vous ≥ date actuelle  
  - Patient et médecin doivent exister  
  - Statuts autorisés : PLANIFIE, ANNULE, TERMINE

###  Consultation Service

- **Module** : `consultation-service-api`  
- **API interne** : `/internal/api/v1/consultations`  
- **Port** : 8085  
- **Règles métier** :  
  - Rendez-vous doit exister  
  - Date de consultation ≥ date du rendez-vous  
  - Rapport ≥ 10 caractères

---

##  ESB (cabinet-esb)

- **Module** : `cabinet-esb`  
- **Point d’entrée unique** : `/api/...`  
- **Port** : 8080  
- **Routage** : redirection des requêtes externes vers les services internes.

### Mapping API externe → interne

| Domaine      | Méthode | API Externe                     | API Interne                          |
|-------------|---------|---------------------------------|--------------------------------------|
| Patients    | GET     | `/api/patients`                 | `/internal/api/v1/patients`         |
| Patients    | GET     | `/api/patients/{id}`            | `/internal/api/v1/patients/{id}`    |
| Patients    | POST    | `/api/patients`                 | `/internal/api/v1/patients`         |
| Patients    | PUT     | `/api/patients/{id}`            | `/internal/api/v1/patients/{id}`    |
| Patients    | DELETE  | `/api/patients/{id}`            | `/internal/api/v1/patients/{id}`    |
| Médecins    | GET     | `/api/medecins`                 | `/internal/api/v1/medecins`         |
| Médecins    | POST    | `/api/medecins`                 | `/internal/api/v1/medecins`         |
| Rendez-vous | GET     | `/api/rendezvous`               | `/internal/api/v1/rendezvous`       |
| Rendez-vous | PATCH   | `/api/rendezvous/{id}/statut`   | `/internal/api/v1/rendezvous/{id}/statut` |
| Consultations | POST  | `/api/consultations`            | `/internal/api/v1/consultations`    |

> Le même principe est appliqué pour tous les endpoints des services.

---

##  Lancer le projet

1. Cloner le dépôt :
   
   git clone https://github.com/<votre-utilisateur>/CabinetMedicalTp2SOA.git
   cd CabinetMedicalTp2SOA
---

##  Technologies utilisées

- Java 21  
- Spring Boot 3.5.9  
- Spring Data JPA  
- H2 Database  
- Apache Camel 4.6.0  
- Maven  
- Lombok  

---

##  Lancement du projet

1. Cloner le projet :

git clone <URL_DU_REPO>

2. **Importer le projet** dans IntelliJ / Eclipse / VS Code comme projet **Maven multi-modules**.

3. **Démarrer chaque module service** individuellement (ports 8082-8085) ou via Maven :

mvn spring-boot:run -pl  `<nom-du-module>` 

Remplacer `<nom-du-module>` par le module à lancer, par exemple :  
`patient-service-api`, `medecin-service-api`, `rendezvous-service-api`, `consultation-service-api`.

Lancer le module **cabinet-esb** (port 8080) pour accéder aux APIs publiques.

---

##  Endpoints REST testés

### Patients

| Méthode | URL |
|---------|-----|
| GET     | `http://localhost:8080/api/patients` |
| POST    | `http://localhost:8080/api/patients` |
| PUT     | `http://localhost:8080/api/patients/{id}` |
| DELETE  | `http://localhost:8080/api/patients/{id}` |

### Médecins

| Méthode | URL |
|---------|-----|
| GET     | `http://localhost:8080/api/medecins` |
| POST    | `http://localhost:8080/api/medecins` |
| PUT     | `http://localhost:8080/api/medecins/{id}` |
| DELETE  | `http://localhost:8080/api/medecins/{id}` |

### Rendez-vous

| Méthode | URL |
|---------|-----|
| GET     | `http://localhost:8080/api/rendezvous` |
| PATCH   | `http://localhost:8080/api/rendezvous/{id}/statut` |

### Consultations

| Méthode | URL |
|---------|-----|
| GET     | `http://localhost:8080/api/consultations` |
| GET     | `http://localhost:8080/api/consultations/rendezvous/{id}` |
| POST    | `http://localhost:8080/api/consultations` |

---


##  Autrice

**Nom** : El Adnani Ilham  
**Master IPS – Faculté des Sciences de Rabat**

