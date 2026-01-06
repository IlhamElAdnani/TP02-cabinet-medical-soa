# Cabinet Medical TP2 - Architecture SOA avec ESB (Apache Camel)

## Contexte
Ce projet est réalisé dans le cadre du TP2 du module **Systèmes Distribués Basés sur les Microservices** du Master IPS à la Faculté des Sciences de Rabat.  
L’objectif est de transformer une application monolithique en une **architecture orientée services (SOA)**, avec un **ESB (Apache Camel)** centralisant les accès externes.

Chaque service métier est autonome et expose sa propre API. Les services communiquent uniquement via l’ESB.

---

## Structure du projet

Projet Maven **multi-modules** :

| Module | Rôle |
|--------|------|
| `cabinetMedicalTp2SOA` | Projet parent |
| `cabinet-esb` | ESB Apache Camel exposant les APIs publiques |
| `cabinet-repo` | Entités JPA et Repositories partagés (CRUD) |
| `patient-service-api` | Gestion des patients |
| `medecin-service-api` | Gestion des médecins |
| `rendezvous-service-api` | Gestion des rendez-vous |
| `consultation-service-api` | Gestion des consultations |

---

## Modules principaux

### 1. cabinet-repo
- Contient uniquement les **entités JPA** et les **repositories Spring Data JPA**.
- Entités : `Patient`, `Medecin`, `RendezVous`, `Consultation`
- Repositories : `PatientRepository`, `MedecinRepository`, `RendezVousRepository`, `ConsultationRepository`
- Base de données utilisée : H2 (en mémoire)
- Configuration dans `application.properties` :

```properties
spring.application.name=cabinetMedicalTp2
spring.datasource.url=jdbc:h2:mem:cabinetMedicalSoaTp2DB
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true **```**

---

## 2. Services métiers

Chaque service suit la structure **Controller → Service → Repository**.

### patient-service-api
- **Port recommandé** : 8082  
- **API interne exposée** : `/internal/api/v1/patients`  
- **Règles métiers** : nom et téléphone obligatoires, date de naissance ≤ aujourd’hui  
- **Opérations** : CRUD sur les patients

### medecin-service-api
- **Port recommandé** : 8083  
- **API interne exposée** : `/internal/api/v1/medecins`  
- **Règles métiers** : nom, email (valide) et spécialité obligatoires  
- **Opérations** : CRUD sur les médecins

### rendezvous-service-api
- **Port recommandé** : 8084  
- **API interne exposée** : `/internal/api/v1/rendezvous`  
- **Règles métiers** : date future, patient et médecin existants, statut autorisé (`PLANIFIE`, `ANNULE`, `TERMINE`)  
- **Opérations** : CRUD + modification statut

### consultation-service-api
- **Port recommandé** : 8085  
- **API interne exposée** : `/internal/api/v1/consultations`  
- **Règles métiers** : rendez-vous existant, date consultation ≥ date rendez-vous, rapport ≥ 10 caractères  
- **Opérations** : CRUD sur les consultations

---

## 3. Module cabinet-esb

- **Port recommandé** : 8080  
- Point d’entrée unique pour les clients externes.  
- Routage des requêtes externes vers les services internes.  
- Dépendances : `spring-boot-starter-web`, `camel-spring-boot-starter`, `camel-http`, `camel-rest`, `camel-servlet`  

### Exemple de mapping ESB

| Domaine        | Méthode | API Externe                     | API Interne                                   |
|----------------|---------|--------------------------------|-----------------------------------------------|
| Patients       | GET     | /api/patients                  | /internal/api/v1/patients                     |
| Patients       | GET     | /api/patients/{id}             | /internal/api/v1/patients/{id}               |
| Patients       | POST    | /api/patients                  | /internal/api/v1/patients                     |
| Patients       | PUT     | /api/patients/{id}             | /internal/api/v1/patients/{id}               |
| Patients       | DELETE  | /api/patients/{id}             | /internal/api/v1/patients/{id}               |
| Médecins       | GET     | /api/medecins                  | /internal/api/v1/medecins                     |
| Médecins       | GET     | /api/medecins/{id}             | /internal/api/v1/medecins/{id}               |
| Médecins       | POST    | /api/medecins                  | /internal/api/v1/medecins                     |
| Médecins       | PUT     | /api/medecins/{id}             | /internal/api/v1/medecins/{id}               |
| Médecins       | DELETE  | /api/medecins/{id}             | /internal/api/v1/medecins/{id}               |
| Rendez-vous    | GET     | /api/rendezvous                | /internal/api/v1/rendezvous                  |
| Rendez-vous    | GET     | /api/rendezvous/{id}           | /internal/api/v1/rendezvous/{id}             |
| Rendez-vous    | GET     | /api/rendezvous/patient/{id}   | /internal/api/v1/rendezvous/patient/{id}     |
| Rendez-vous    | GET     | /api/rendezvous/medecin/{id}   | /internal/api/v1/rendezvous/medecin/{id}     |
| Rendez-vous    | POST    | /api/rendezvous                | /internal/api/v1/rendezvous                  |
| Rendez-vous    | PUT     | /api/rendezvous/{id}           | /internal/api/v1/rendezvous/{id}             |
| Rendez-vous    | PATCH   | /api/rendezvous/{id}/statut    | /internal/api/v1/rendezvous/{id}/statut      |
| Rendez-vous    | DELETE  | /api/rendezvous/{id}           | /internal/api/v1/rendezvous/{id}             |
| Consultations  | GET     | /api/consultations             | /internal/api/v1/consultations               |
| Consultations  | GET     | /api/consultations/{id}        | /internal/api/v1/consultations/{id}          |
| Consultations  | GET     | /api/consultations/rendezvous/{id} | /internal/api/v1/consultations/rendezvous/{id} |
| Consultations  | POST    | /api/consultations             | /internal/api/v1/consultations               |
| Consultations  | PUT     | /api/consultations/{id}        | /internal/api/v1/consultations/{id}          |
| Consultations  | DELETE  | /api/consultations/{id}        | /internal/api/v1/consultations/{id}          |

---

## Technologies utilisées
- Java 21  
- Spring Boot 3.5.9  
- Spring Data JPA  
- H2 Database  
- Apache Camel 4.6.0  
- Maven  
- Lombok  

---

## Lancement du projet

1. Cloner le projet :  
```bash
git clone <URL_DU_REPO>

