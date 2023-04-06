-- phpMyAdmin SQL Dump
-- version 4.9.11
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : jeu. 06 avr. 2023 à 14:34
-- Version du serveur : 10.3.38-MariaDB-cll-lve
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `clapassonn_EMF_Shelf`
--

-- --------------------------------------------------------

--
-- Structure de la table `t_livres`
--

CREATE TABLE `t_livres` (
  `PK_livre` int(11) NOT NULL,
  `titre` varchar(200) NOT NULL,
  `auteur` varchar(100) NOT NULL,
  `description` longtext DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `disponible` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `t_livres`
--

INSERT INTO `t_livres` (`PK_livre`, `titre`, `auteur`, `description`, `image`, `disponible`) VALUES
(1, 'Le syndrome Magnéto', 'Benjamin Patinaud', '« Tout le monde aime les méchants. La culture populaire en a produit de toutes formes et toutes couleurs. Mais tous ne commettent pas leurs atrocités pour de viles raisons. Certains ne veulent pas détruire le monde : ils veulent le changer. Utopistes malencontreusement dystopiques, extrémistes plus ou moins bien intentionnés, libérateurs aux penchants totalitaires, terroristes se vivant comme résistants : ce livre leur est consacré. »', NULL, 0),
(2, 'Teen Titans - Beast Boy', 'Kami Garcia', 'Garfield Logan est en terminal. C\'est sa dernière année de lycée, et il est bien loin d\'avoir rempli les objectifs qu\'il s\'était fixé. Les régimes protéinés l\'ont à peine remplumé de quelques kilos, sa croissance est au point mort et sa voix n\'a toujours pas mué. En bref, Gar complexe. Tous les autres étudiants semblent avoir trouvé leur place, ont l\'air de savoir avec précision ce qu\'ils aiment, ce qu\'ils veulent et surtout ce qu\'ils sont mais, à dix-sept ans, Gar est encore bien loin de tout ça. Il est celui que personne ne remarque, et son crush, Alana, ne fait pas exception. Ses amis, Stella et Tank, ont du mal à comprendre ses obsessions et cette volonté tenace de devenir le prochain M. Populaire. Á trop se soucier du regard des autres, ne risque-t-il pas de se perdre en chemin ?', NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `t_reservations`
--

CREATE TABLE `t_reservations` (
  `PK_reservation` int(11) NOT NULL,
  `FK_livre` int(11) NOT NULL,
  `FK_compte` int(11) NOT NULL,
  `retour` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `t_reservations`
--

INSERT INTO `t_reservations` (`PK_reservation`, `FK_livre`, `FK_compte`, `retour`) VALUES
(18, 1, 1, '2023-04-01');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `t_livres`
--
ALTER TABLE `t_livres`
  ADD PRIMARY KEY (`PK_livre`);

--
-- Index pour la table `t_reservations`
--
ALTER TABLE `t_reservations`
  ADD PRIMARY KEY (`PK_reservation`),
  ADD KEY `fk_t_reservation_t_livres_idx` (`FK_livre`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `t_livres`
--
ALTER TABLE `t_livres`
  MODIFY `PK_livre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `t_reservations`
--
ALTER TABLE `t_reservations`
  MODIFY `PK_reservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `t_reservations`
--
ALTER TABLE `t_reservations`
  ADD CONSTRAINT `fk_t_reservation_t_livres` FOREIGN KEY (`FK_livre`) REFERENCES `t_livres` (`PK_livre`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
