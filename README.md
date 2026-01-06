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
spring.h2.console.enabled=true '''

---
