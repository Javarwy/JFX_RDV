-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 20 jan. 2025 à 13:09
-- Version du serveur : 8.0.31
-- Version de PHP : 8.2.0

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
  `id_dossier` int NOT NULL,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `filliere` varchar(25) NOT NULL,
  `motivation` text NOT NULL,
  `ref_rdv` int NOT NULL,
  PRIMARY KEY (`id_dossier`),
  KEY `ref_rdv` (`ref_rdv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table sur les dossiers';

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id_etudiant` int NOT NULL,
  `prenomEtudiant` varchar(50) NOT NULL,
  `diplome` varchar(25) NOT NULL,
  `emailEtudiant` varchar(50) NOT NULL,
  `telephone` int NOT NULL,
  PRIMARY KEY (`id_etudiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table des étudiants';

-- --------------------------------------------------------

--
-- Structure de la table `fourniture`
--

DROP TABLE IF EXISTS `fourniture`;
CREATE TABLE IF NOT EXISTS `fourniture` (
  `id_fourniture` int NOT NULL,
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
  `id_rendezvous` int NOT NULL,
  `date_rendezvous` date NOT NULL,
  `heure_rendezvous` time NOT NULL,
  PRIMARY KEY (`id_rendezvous`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table Rendez Vous';

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id_salle` int NOT NULL,
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
  `id_utilisateur` int NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `role` varchar(20) NOT NULL,
  `fourni_com` int NOT NULL,
  PRIMARY KEY (`id_utilisateur`),
  KEY `fourni_com` (`fourni_com`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table de l''utilisateur';

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `dossier`
--
ALTER TABLE `dossier`
  ADD CONSTRAINT `dossier_on_rdv` FOREIGN KEY (`ref_rdv`) REFERENCES `dossier` (`id_dossier`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `salle`
--
ALTER TABLE `salle`
  ADD CONSTRAINT `salle_on_prof` FOREIGN KEY (`professeur_present`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `salle_on_rdv` FOREIGN KEY (`rdv_ref`) REFERENCES `salle` (`id_salle`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `util_on_fourn` FOREIGN KEY (`fourni_com`) REFERENCES `fourniture` (`id_fourniture`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
