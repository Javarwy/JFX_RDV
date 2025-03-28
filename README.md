# Introduction

Ce répertoire Github est dédié au projet LPRS en Java pour l'épreuve E5.  

Le contexte est le suivant :

> L’établissement souhaite développer une application lourde en Java permettant de gérer efficacement deux aspects clés de la rentrée :  
> - La gestion de stock des fournitures, indispensable pour assurer une organisation fluide du matériel pédagogique.  
> - Les dossiers d’inscription des nouveaux élèves, avec un suivi personnalisé et des rendez-vous organisés entre étudiants et professeurs.

Il existe trois rôles : **Secrétaire**, **professeur** et **gestionnaire de stock**.
 - **Secrétaire** : Crée les fiches étudiant et les dossiers d'inscription.
 - **Professeur** : Prend en charge les dossiers d'inscription, organise des rendez-vous avec, et crée des demandes de fournitures.
 - **Gestionnaire de stock** : Gère les fournitures, fournisseurs et les demandes de réapprovisionnement. Il accepte ou non les demandes de fournitures.

# Installation

*(À noter que l'IDE utilisé au cours de ce projet est IntelliJ IDEA. Le WAMP utilisé est Wampserver avec phpMyAdmin pour les bases de données.)*

1. Cloner le répertoire à l'endroit voulu via *git clone*.
2. Ouvrir le projet avec l'IDE et s'assurer que le projet a bien été chargé. (Si les dépendances Maven n'ont pas été chargées : cliquez sur le logo Maven puis cliquer sur « Sync/Reload All Maven Projects » (le premier bouton en partant de la gauche) et sélectionner l'une des deux options.).
3. Ouvrir phpMyAdmin, créer une base de données s'intitulant « jfx » et importer les données du fichier jfx.sql.
4. Pour lancer l'application, aller dans src/main/java/appli puis lancer StartApplication.java (clic droit sur le fichier puis « Run 'StartApplication.main()' ».

# Utilisation

Trois comptes (pour les trois rôles) sont déjà présents dans la base de données. Les identifiants sont les suivants :

| Rôle                         | Email | Mot de passe |
|------------------------------|-------|-------------|
| Professeur                   | a     | a           |
| Secrétaire                   | b     | b           |
| Gestionnaire de stock        | c     | c           |

Il est possible de créer un compte en renseignant le nom, le prénom, l'email, le mot de passe et le rôle de l'utilisateur.

## Utilisation : Professeur
 
Après connexion via un compte Professeur, trois boutons principaux s'affichent sur la page : **Dossiers d'inscription**, **Rendez-vous** et **Demande de fournitures**. Il est également possible de modifier les informations du compte (sauf le rôle).

- **Dossiers d'inscription** : Permet de consulter les dossiers d'inscription créés et de prendre un rendez-vous. Un tableau affichera tous les dossiers d'inscription créés. En cliquant sur l'un de ces dossiers, le bouton « Prendre rendez-vous » s'activera. (Si le dossier est déjà pris en charge, un message s'affichera et le bouton restera désactivé.)
  Cliquer sur « Prendre rendez-vous » affichera un formulaire vous demandant de sélectionner une salle disponible, une date et l'heure pour le rendez-vous. Après confirmation, vous serez redirigé vers la page « Dossiers d'inscription ». Votre rendez-vous sera enregistré et consultable sur la page « Rendez-vous ».
  
- **Rendez-vous** : Permet de consulter les rendez-vous effectués par le professeur connecté). Double-cliquez sur un rendez-vous pour afficher ses informations précises. En sélectionnant juste un rendez-vous, il vous sera possible de modifier la salle, la date et l'heure du rendez-vous ; ou de l'annuler.
  
- **Demande de fournitures** : Permet de consulter les demandes fournitures effectuées par le professeur connecté. En sélectionnant une demande, il est possible de l'annuler.
  Cliquer sur « Nouvelle demande de fournitures » affichera un formulaire vous demandant de sélectionner la fourniture, la quantité et la raison de cette demande. Il est possible d'ajouter plusieurs fournitures (ce qui créera plusieurs demandes en même temps) via le bouton « + », ou d'en enlever via le bouton « - ». Il ne reste plus qu'à envoyer la demande.

## Utilisation : Secrétaire
 
Après connexion via un compte Secrétaire, quatre boutons s'affichent sur la page : **Création de fiche étudiant**, **Liste des fiches étudiant**, **Création de dossier d'inscription** et **Liste des dossiers d'inscription**.

- **Création de fiche étudiant** : Permet de créer une fiche étudiant en renseignant le prénom, nom, diplôme, email et téléphone de l'étudiant dans le formulaire.
- **Liste des fiches étudiant** : Permet de consulter toutes les fiches étudiant créées.
- **Création de dossier d'inscription** : Permet de créer un dossier d'inscription. (Ne fonctionne pas pour l'instant)
- **Liste des dossiers d'inscription** : Permet de consulter tous les dossiers d'inscription créés.

## Utilisation : Gestionnaire de stock

Après connexion via un compte Gestionnaire de stock, deux boutons s'affichent sur la page : **Gestion de fournitures** et **Demande 2 fournitures**.

- **Gestion des fournitures** : Permet de consulter les demandes de fournitures, d'en ajouter, modifier et supprimer.
- **Demande 2 fournitures** : Redirige nulle part.

# Crédits
| Collaborateur | Partie(s)                               |
|---------------|-----------------------------------------|
| Aurélien A.   | Secrétaire                              |
| Raphaël C.    | Gestionnaire de stock                   |
| Raphaël M.    | Raccourci clavier sur la page connexion |
| Yohan V.      | Professeur                              |
