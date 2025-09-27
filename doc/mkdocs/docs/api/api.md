# Documentation de l'API

---

## Table des matières
- [Authentification & Gestion de session](#authentification--gestion-de-session)
    - [POST /api/users/login](#post-apiuserslogin)
    - [POST /api/users/logout](#post-apiuserslogout)
    - [POST /api/users](#post-apiusers)
    - [POST /api/user/update](#post-apiuserupdate)
    - [POST /api/user/delete](#post-apiuserdelete)
    - [POST /api/user](#post-apiuser)

- [Administration (requiert rôle ADMIN)](#administration-requiert-rôle-admin)
    - [POST /api/admin/users](#post-apiadminusers)
    - [POST /api/admin/user/update](#post-apiadminuserupdate)
    - [POST /api/admin/user/delete](#post-apiadminuserdelete)
    - [POST /api/logs](#post-apilogs)

- [Défis (Challenges)](#défis-challenges)
    - [GET /api/challenges](#get-apichallenges)
    - [POST /api/challenges/list](#post-apichallengeslist)
    - [POST /api/challenges/started](#post-apichallengesstarted)
    - [POST /api/challenges/completed](#post-apichallengescompleted)
    - [POST /api/challenges/{id}](#post-apichallengesid)
    - [POST /api/challenges/{id}/flag](#post-apichallengesidflag)
    - [GET /api/challenges/{id}/tags](#get-apichallengesidtags)

- [Scores & Badges](#scores--badges)
    - [GET /api/users/score](#get-apiusersscore)
    - [POST /api/users/points](#post-apiuserspoints)
    - [POST /api/users/badges](#post-apiusersbadges)
    - [POST /api/user (rappel)](#post-apiuser-rappel)

---

## Authentification & Gestion de session

### POST `/api/users/login`
- **Description :** Connexion d’un utilisateur (renvoie un token).
- **Corps de la requête :**
```json
{
  "email": "email@example.com",
  "password": "motdepasse"
}
```
- **Réponses :**
    - `200 OK` → Authentification réussie (token renvoyé)
    - `400 Bad Request` → Identifiants invalides

---

### POST `/api/users/logout`
- **Description :** Déconnexion de l’utilisateur connecté.
- **Corps de la requête :**
```json
{
  "token": "jeton_utilisateur"
}
```
- **Réponses :**
    - `200 OK` → Déconnexion réussie
    - `400 Bad Request` → Utilisateur non connecté

---

### POST `/api/users`
- **Description :** Création d’un nouvel utilisateur.
- **Corps de la requête :**
```json
{
  "name": "Ada",
  "email": "ada@example.com",
  "password": "motdepasse"
}
```
- **Réponses :**
    - `201 Created` → Utilisateur créé
    - `400 Bad Request` → Données invalides

---

### POST `/api/user/update`
- **Description :** Mise à jour du profil de l’utilisateur connecté.
- **Corps de la requête :**
```json
{
  "token": "jeton_utilisateur",
  "name": "Nouveau Nom",
  "email": "nouveau.email@example.com",
  "password": "nouveau_motdepasse"
}
```
- **Réponses :**
    - `200 OK` → Profil mis à jour
    - `400 Bad Request` → Utilisateur non trouvé

---

### POST `/api/user/delete`
- **Description :** Suppression du compte de l’utilisateur connecté.
- **Corps de la requête :**
```json
{
  "token": "jeton_utilisateur"
}
```
- **Réponses :**
    - `200 OK` → Compte supprimé
    - `400 Bad Request` → Utilisateur non trouvé

---

### POST `/api/user`
- **Description :** Récupération des informations de l’utilisateur connecté (inclut désormais les **awards**).
- **Corps de la requête :**
```json
{
  "token": "jeton_utilisateur"
}
```
- **Exemple de réponse :**
```json
{
  "id": 42,
  "name": "Ada",
  "email": "ada@example.com",
  "score": 1200,
  "awards": [
    {
      "title": "First Blood",
      "description": "Résoudre un premier défi",
      "color": "#FFD700",
      "date": "2025-09-09"
    },
    {
      "title": "Ada Lovelace",
      "description": "Être la première à résoudre un défi",
      "color": "#C0C0C0",
      "date": "2025-09-10"
    }
  ]
}
```

---

## Administration (requiert rôle `ADMIN`)

### POST `/api/admin/users`
- **Description :** Récupération de tous les utilisateurs.
- **Corps de la requête :**
```json
{
  "token": "jeton_admin"
}
```

---

### POST `/api/admin/user/update`
- **Description :** Mise à jour d’un utilisateur par un admin.
- **Corps de la requête :**
```json
{
  "token": "jeton_admin",
  "id": "123",
  "name": "Nouveau Nom",
  "email": "nouveau.email@example.com",
  "role": "USER",
  "score": 1000
}
```

---

### POST `/api/admin/user/delete`
- **Description :** Suppression d’un utilisateur par un admin.
- **Corps de la requête :**
```json
{
  "token": "jeton_admin",
  "id": "123"
}
```
- **Réponses :**
    - `200 OK` → Utilisateur supprimé
    - `400 Bad Request` → Utilisateur non trouvé

---

### POST `/api/logs`
- **Description :** Récupération des logs du système.
- **Corps de la requête :**
```json
{
  "token": "jeton_admin"
}
```

---

## Défis (Challenges)

### GET `/api/challenges`
- **Description :** Liste de tous les défis.

---

### POST `/api/challenges/list`
- **Description :** Liste des défis avec leur statut (`NOT STARTED`, `STARTED`, `COMPLETED`) pour un utilisateur.

---

### POST `/api/challenges/started`
- **Description :** Liste des défis commencés par l’utilisateur.

---

### POST `/api/challenges/completed`
- **Description :** Liste des défis complétés par l’utilisateur.

---

### POST `/api/challenges/{id}`
- **Description :** Détails d’un défi (inclut le statut).

---

### POST `/api/challenges/{id}/flag`
- **Description :** Vérification du flag pour un défi.

---

### GET `/api/challenges/{id}/tags`
- **Description :** Récupération des tags associés à un défi.

---

## Scores & Badges

### GET `/api/users/score`
- **Description :** Classement des utilisateurs + attribution des badges de podium.

---

### POST `/api/users/points`
- **Description :** Récupération des points d’un utilisateur.

---

### POST `/api/users/badges`
- **Description :** Récupération des badges de podium d’un utilisateur.

---

### POST `/api/user` (rappel)
- **Description :** Déjà listé plus haut, mais inclut aussi la **liste des awards personnalisés** gagnés par l’utilisateur.
