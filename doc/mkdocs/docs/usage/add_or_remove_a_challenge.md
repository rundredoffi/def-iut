# Ajouter ou Supprimer un défi

## Introduction

Dans ce document, nous allons voir comment ajouter un défi à la plateforme, en tant qu'administrateur.

## Ajouter un défi

### Protocole

Pour ajouter un défi, il faut suivre les étapes suivantes :

- Étape 1 : Se rendre dans le fichier : doc/database/scripts/insert_test.sql
- Étape 2 : Ajouter une ligne dans la table `Challenge` en utilisant les valeurs suivantes :
    - `ChallengeId` : Identifiant du défi (doit être unique)
    - `ChallengeName` : Nom du défi
    - `ChallengeDate` : Date d'ajout du défi
    - `ChallengeDifficulty` : Difficulté du défi (La liste des difficultés disponibles est dans `backend/src/main/java/defiut/backend/model/domain/ChallengeDifficulty.java`)
    - `ChallengeLanguage` : Langage du défi (La liste des langages dsponibles est dans `backend/src/main/java/defiut/backend/model/domain/ChallengeLanguage.java`)
    - `ChallengeDescription` : Description du défi
    - `ChallengeFlag` : Flag du défi
    - `ChallengeDocker` : 0 si le défi n'utilise pas de conteneur Docker, 1 sinon.
    - `ChallengePoints` : Nombre de points attribués pour la résolution du défi
- Étape 3 : Ajouter un ou plusieurs Tags dans la table `ChallengeTag` en utilisant les valeurs suivantes :
    - `ChallengeId` : Identifiant du tag (doit être unique)
    - `TagId` : ID du tag trouvable dans la table Tag
- Étape 4 : Créer la descritpion du défi (consignes, ressources...) dans un fichier `{ChallengeName}.md` dans le dossier `doc/mkdocs/docs/challenges/`, la description apparaîtra automatiquement sur la page du défi.

### Supprimer un défi

Pour supprimer un défi, il faut suivre les étapes suivantes :

- Étape 1 : Se rendre dans le fichier : doc/database/scripts/insert_test.sql
- Étape 2 : Supprimer la ligne correspondant au défi à supprimer dans la table `Challenge`
- Étape 3 : Supprimer les lignes correspondant au défi à supprimer dans la table `ChallengeTag`
- Étape 4 : Supprimer le fichier `{ChallengeName}.md` dans le dossier `doc/mkdocs/docs/challenges/`

## Conclusion

Vous savez comment ajouter ou supprimer un défi de la plateforme.