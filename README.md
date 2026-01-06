CabinetMedicalTp2SOA
🎯 Contexte

Ce projet est réalisé dans le cadre du Master IPS – Systèmes Distribués Basés sur les Microservices à la Faculté des Sciences de Rabat.
Il consiste à transformer une application monolithique d’un Cabinet Médical en une architecture orientée services (SOA) avec un ESB Apache Camel.

L’objectif est de créer des services métiers autonomes pour Patients, Médecins, Rendez-vous et Consultations, tout en centralisant les accès externes via un ESB.

🏗️ Architecture du projet

Le projet est un Maven multi-modules structuré comme suit :

Module	Rôle	Port recommandé
cabinet-esb	ESB Apache Camel, point d’entrée unique pour les clients externes	8080
cabinet-repo	Modèle de données partagé et Repositories Spring Data JPA	–
patient-service-api	Service métier Patient (API + règles métier)	8082
medecin-service-api	Service métier Médecin (API + règles métier)	8083
rendezvous-service-api	Service métier Rendez-vous (API + règles métier)	8084
consultation-service-api	Service métier Consultation (API + règles métier)	8085
🔹 Module cabinet-repo

Contient uniquement les entités JPA et les repositories Spring Data.

Aucun code métier n’y est implémenté.

Base de données : H2 (en mémoire).

Configuration (application.properties) :

spring.application.name=cabinetMedicalTp2
spring.datasource.url=jdbc:h2:mem:cabinetMedicalSoaTp2DB
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true


Packages :

ma.fsr.soa.cabinetrepo.model

ma.fsr.soa.cabinetrepo.repository

🔹 Services Métiers

Chaque service expose ses endpoints via un Controller REST interne et applique des règles métiers.

Patient Service (patient-service-api)

Endpoints internes : /internal/api/v1/patients

Ports : 8082

Exemple de règles :

Nom et téléphone obligatoires

Date de naissance ne peut pas être future

Messages d’erreur personnalisés

Médecin Service (medecin-service-api)

Endpoints internes : /internal/api/v1/medecins

Ports : 8083

Exemple de règles :

Nom, email et spécialité obligatoires

Email doit être valide

Messages d’erreur personnalisés

Rendez-vous Service (rendezvous-service-api)

Endpoints internes : /internal/api/v1/rendezvous

Ports : 8084

Exemple de règles :

Date du rendez-vous future

Patient et médecin doivent exister

Statuts autorisés : PLANIFIE, ANNULE, TERMINE

Consultation Service (consultation-service-api)

Endpoints internes : /internal/api/v1/consultations

Ports : 8085

Exemple de règles :

Rendez-vous doit exister

Date de consultation ≥ date du rendez-vous

Rapport minimum 10 caractères

🔹 ESB (cabinet-esb)

Point d’entrée unique pour toutes les requêtes externes.

Ports : 8080

Routage entre API externes et API internes :

Domaine	Méthode	API Externe	API Interne
Patients	GET	/api/patients	/internal/api/v1/patients
Patients	GET	/api/patients/{id}	/internal/api/v1/patients/{id}
Patients	POST	/api/patients	/internal/api/v1/patients
Patients	PUT	/api/patients/{id}	/internal/api/v1/patients/{id}
Patients	DELETE	/api/patients/{id}	/internal/api/v1/patients/{id}
...	...	...	...

Le même principe est appliqué pour Médecins, Rendez-vous et Consultations.

🚀 Lancer le projet

Cloner le projet :

git clone https://github.com/<votre-utilisateur>/CabinetMedicalTp2SOA.git
cd CabinetMedicalTp2SOA


Importer les modules dans IDE Spring Boot (IntelliJ/STS/VS Code).

Lancer chaque service métier individuellement (8082–8085) pour tester les endpoints internes.

Lancer le module ESB (8080) pour exposer les endpoints externes.

Accéder à la console H2 : http://localhost:8080/h2-console
