# 🏥 CabinetMedicalTp2SOA

## 🎯 Contexte

Projet réalisé dans le cadre du **Master IPS – Systèmes Distribués Basés sur les Microservices** à la **Faculté des Sciences de Rabat**.

Objectif : transformer une application monolithique d’un **Cabinet Médical** en **architecture orientée services (SOA)** avec un **ESB Apache Camel**.

Chaque fonctionnalité métier est isolée dans un service autonome et exposée via une API, tandis que l’ESB centralise les accès externes.

---

## 🏗️ Architecture du projet

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

## 🔹 Module `cabinet-repo`

- Contient uniquement les **entités JPA** et **repositories Spring Data JPA**.
- Aucun code métier.
- Base de données : **H2 en mémoire**.
- Configuration (`application.properties`) :

```properties
spring.application.name=cabinetMedicalTp2
spring.datasource.url=jdbc:h2:mem:cabinetMedicalSoaTp2DB
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
*'''*
