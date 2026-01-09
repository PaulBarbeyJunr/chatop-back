# ChâTop - API Backend

API REST pour l'application de location immobilière ChâTop.

## Technologies

- Java 17
- Spring Boot 3.5.9
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Swagger/OpenAPI

## Prérequis

- Java 17+
- Maven 3.6+
- MySQL 8.0+

## Installation

### 1. Cloner le projet

```bash
git clone <repository-url>
cd chatop
```

### 2. Configurer la base de données

Créer une base de données MySQL :

```sql
CREATE DATABASE chatop;
```

### 3. Configuration

Les variables d'environnement suivantes peuvent être configurées :

| Variable | Description | Valeur par défaut |
|----------|-------------|-------------------|
| `DATABASE_URL` | URL de connexion MySQL | `jdbc:mysql://localhost:3306/chatop` |
| `DATABASE_USERNAME` | Utilisateur MySQL | `root` |
| `DATABASE_PASSWORD` | Mot de passe MySQL | *(vide)* |
| `JWT_SECRET` | Clé secrète JWT (min 64 caractères) | *(clé par défaut)* |

Vous pouvez soit :
- Définir les variables d'environnement système
- Modifier directement `src/main/resources/application.properties`

### 4. Lancer l'application

```bash
./mvnw spring-boot:run
```

L'API sera accessible sur : `http://localhost:3001/api`

## Documentation API

Swagger UI : `http://localhost:3001/api/swagger-ui.html`

## Endpoints

### Authentification

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register` | Inscription |
| POST | `/api/auth/login` | Connexion |
| GET | `/api/auth/me` | Utilisateur connecté |

### Utilisateurs

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/user/{id}` | Détails d'un utilisateur |

### Locations (Rentals)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/rentals` | Liste des locations |
| GET | `/api/rentals/{id}` | Détails d'une location |
| POST | `/api/rentals` | Créer une location |
| PUT | `/api/rentals/{id}` | Modifier une location |

### Messages

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/messages` | Envoyer un message |

## Architecture

```
src/main/java/com/chatop/
├── config/          # Configuration (Security, Web, OpenAPI)
├── controller/      # Contrôleurs REST
├── dto/             # Objets de transfert de données
├── model/           # Entités JPA
├── repository/      # Repositories Spring Data
├── security/        # JWT Filter et Service
└── service/         # Services métier
```

## Sécurité

- Authentification par JWT (Bearer Token)
- Mot de passe hashé avec BCrypt
- Endpoints publics : `/auth/register`, `/auth/login`, `/uploads/**`
- Tous les autres endpoints nécessitent un token JWT valide

## Upload d'images

Les images sont stockées dans le dossier `uploads/` à la racine du projet et servies via `/api/uploads/`.
