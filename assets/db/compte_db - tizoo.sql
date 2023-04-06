-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema compteDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema compteDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `compteDB` DEFAULT CHARACTER SET utf8 ;
USE `compteDB` ;

-- -----------------------------------------------------
-- Table `compteDB`.`t_utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compteDB`.`t_utilisateur` (
  `pk_utilisateur` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(64) NOT NULL,
  `mot_de_passe` VARCHAR(256) NOT NULL,
  `administrateur` TINYINT(1) NOT NULL DEFAULT 0,
  `banni` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`pk_utilisateur`),
  UNIQUE INDEX `pk_utilisateur_UNIQUE` (`pk_utilisateur` ASC) VISIBLE,
  UNIQUE INDEX `nom_UNIQUE` (`nom` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
