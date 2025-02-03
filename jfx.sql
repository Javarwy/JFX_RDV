-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 01 fév. 2025 à 21:35
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `jfx`
--

-- --------------------------------------------------------

--
-- Structure de la table `dossier`
--

DROP TABLE IF EXISTS `dossier`;
CREATE TABLE IF NOT EXISTS `dossier` (
  `id_dossier` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `filliere` varchar(25) NOT NULL,
  `motivation` text NOT NULL,
  `ref_etudiant` int NOT NULL,
  PRIMARY KEY (`id_dossier`),
  KEY `ref_etudiant` (`ref_etudiant`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table sur les dossiers';

--
-- Déchargement des données de la table `dossier`
--

INSERT INTO `dossier` (`id_dossier`, `date`, `heure`, `filliere`, `motivation`, `ref_etudiant`) VALUES
(1, '2025-02-01', '21:41:44', 'BTS SIO option SLAM', 'Je suis motivé.', 1);

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id_etudiant` int NOT NULL AUTO_INCREMENT,
  `nomEtudiant` varchar(50) NOT NULL,
  `prenomEtudiant` varchar(50) NOT NULL,
  `diplome` varchar(25) NOT NULL,
  `emailEtudiant` varchar(50) NOT NULL,
  `telephone` varchar(10) NOT NULL,
  PRIMARY KEY (`id_etudiant`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table des étudiants';

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`id_etudiant`, `nomEtudiant`, `prenomEtudiant`, `diplome`, `emailEtudiant`, `telephone`) VALUES
(1, 'Etudiant', 'Etudiant', 'STI2D', 'etudiant@etudiant.fr', '0600000000');

-- --------------------------------------------------------

--
-- Structure de la table `fourniture`
--

DROP TABLE IF EXISTS `fourniture`;
CREATE TABLE IF NOT EXISTS `fourniture` (
  `id_fourniture` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL,
  `prix` int NOT NULL,
  `fournisseur` varchar(50) NOT NULL,
  PRIMARY KEY (`id_fourniture`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table des four';

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `id_rendezvous` int NOT NULL AUTO_INCREMENT,
  `date_rendezvous` date NOT NULL,
  `heure_rendezvous` time NOT NULL,
  `ref_dossier` int NOT NULL,
  PRIMARY KEY (`id_rendezvous`),
  KEY `ref_dossier` (`ref_dossier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table Rendez Vous';

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id_salle` int NOT NULL AUTO_INCREMENT,
  `nom_salle` varchar(20) NOT NULL,
  `occupe` tinyint(1) NOT NULL DEFAULT '0',
  `professeur_present` int NOT NULL,
  `rdv_ref` int NOT NULL,
  PRIMARY KEY (`id_salle`),
  KEY `professeur_present` (`professeur_present`),
  KEY `rdv_ref` (`rdv_ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table des salles';

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  `fourni_com` int DEFAULT NULL,
  PRIMARY KEY (`id_utilisateur`),
  KEY `fourni_com` (`fourni_com`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table de l''utilisateur';

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `nom`, `prenom`, `email`, `mdp`, `role`, `fourni_com`) VALUES
(1, 'a', 'a', 'a', '$2a$10$1ScMjQ21fUgXibYYIdMPf.zBdRIrGYkYVj5j..HpSKvZcUq1MWcbS', 'Professeur', NULL);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `dossier`
--
ALTER TABLE `dossier`
  ADD CONSTRAINT `etudiant_on_dossier` FOREIGN KEY (`ref_etudiant`) REFERENCES `etudiant` (`id_etudiant`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `dossier_on_rdv` FOREIGN KEY (`ref_dossier`) REFERENCES `dossier` (`id_dossier`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `salle`
--
ALTER TABLE `salle`
  ADD CONSTRAINT `salle_on_prof` FOREIGN KEY (`professeur_present`) REFERENCES `utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `salle_on_rdv` FOREIGN KEY (`rdv_ref`) REFERENCES `rendezvous` (`id_rendezvous`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `util_on_fourn` FOREIGN KEY (`fourni_com`) REFERENCES `fourniture` (`id_fourniture`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
