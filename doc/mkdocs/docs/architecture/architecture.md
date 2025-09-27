# <a id="archi">Explication des choix</a>

## Backend

### Framework Spring Boot

Nous avons choisi Spring Boot pour le backend en raison de sa simplicité de configuration, de sa grande communauté et de son intégration aisée avec d'autres technologies. Il facilite le développement d'applications Java robustes et évolutives.

### Base de données MariaDB

Nous avons opté pour MariaDB comme système de gestion de base de données en raison de sa compatibilité avec MySQL, de sa performance, et de son utilisation répandue dans le développement web.

## Frontend

### Framework Vue.js

Vue.js a été choisi pour le frontend en raison de sa simplicité, de sa flexibilité et de sa facilité d'intégration avec d'autres bibliothèques ou projets. Il offre une approche progressive pour la construction d'interfaces utilisateur et s'intègre bien avec les projets existants.

## Arborescence du projet

```
.
├── backend/                        # Répertoire du backend
│   ├── Dockerfile                  # Dockerfile pour lancer le backend
│   ├── logs/                       # Logs de l'application
│   ├── pom.xml                     # Fichier de configuration de Maven
│   └── src/                        # Sources du backend
│       ├── main/
│       │   ├── java/
│       │   │   └── defiut/backend/ # Packages Java du backend   
│       │   │       ├── Application.java
│       │   │       ├── controller/ # Controllers de l'application
│       │   │       ├── dao/        # DAO des objets pour la base de données
│       │   │       ├── database/   # Connexion à la base de données
│       │   │       └── model/      # Modèles java des objets en bdd
│       │   └── resources/          # Fichiers de propriétés
│       └── test/java/defiut/backend/
│           ├── controller/         # Tests des controllers
│           ├── util/               # Tests des utilitaires
│           └── BackendApplicationTests.java  # Tests du backend
├── challenges/                     # Répertoire pour les challenges Docker
│   └── ImprovedMySQL/
│       ├── checker/
│       │   ├── check_max_connections.py
│       │   └── Dockerfile
│       ├── docker-compose.yml
│       ├── mysql/
│       │   ├── Dockerfile
│       │   └── init.sql
│       ├── start.sh
│       └── stop.sh
├── doc/                            # Répertoire de documentation
│   ├── api/
│   │   └── backend.vpp             # Visualparadigm de l'API backend
│   ├── database/
│   │   ├── class diagram.drawio    # Diagramme de classe
│   │   ├── scripts/
│   │   │   └── init.sql            # Script SQL d'initialisation
│   │   └── sr.txt                  # Fichier de spécifications
│   ├── mkdocs/
│   │   ├── Dockerfile
│   │   ├── docs/
│   │   │   ├── api/                # Documentation API       
│   │   │   ├── architecture/       # Documentation architecture
│   │   │   ├── challenges/         # Description des challenges
│   │   │   ├── déploiement/        # Instructions de déploiement du projet
│   │   │   ├── index.md
│   │   │   ├── misc/
│   │   │   └── usage/              # Démo et instructions d'utilisation
│   │   └── mkdocs.yml
│   └── utils/                      # Documents de conception et de défis
├── docker-compose.yml              # Fichier de composition Docker principal
├── frontend/                       # Répertoire du frontend
│   ├── babel.config.js
│   ├── default.conf
│   ├── Dockerfile
│   ├── jsconfig.json
│   ├── nginx.conf
│   ├── package.json
│   ├── package-lock.json
│   ├── public/
│   ├── src/                        # Dossier du projet
│   │   ├── assets/
│   │   │   ├── fonts/              # Polices d'écritures importées
│   │   │   ├── img/                # Images utilisées au sein de l'application
│   │   │   └── styles/             # Dossier de styles css correspondant aux pages de l'application 
│   │   ├── components/             # Composants utilisés dans les vues
│   │   ├── views/                  # Vues de l'application
│   │   ├── App.vue
│   │   └── main.js
│   └── vue.config.js
├── initdb/
│   └── init.sql                    # Script SQL pour la base de données
└── README.md                       # Fichier d'explications principal
```