-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 15-12-2018 a las 02:28:48
-- Versión del servidor: 5.7.23
-- Versión de PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `oblivion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `race`
--

DROP TABLE IF EXISTS `race`;
CREATE TABLE IF NOT EXISTS `race` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(16) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `race`
--

INSERT INTO `race` (`Id`, `Name`) VALUES
(4, 'Chomori'),
(1, 'Dunmer'),
(3, 'Humano'),
(2, 'Orco');

--
-- Disparadores `race`
--
DROP TRIGGER IF EXISTS `race_bi`;
DELIMITER $$
CREATE TRIGGER `race_bi` BEFORE INSERT ON `race` FOR EACH ROW IF NEW.Name='' THEN
    	SIGNAL SQLSTATE '45000' SET
        	MESSAGE_TEXT = 'El nombre de la raza no puede estar vacío.';
END IF
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `race_bu`;
DELIMITER $$
CREATE TRIGGER `race_bu` BEFORE UPDATE ON `race` FOR EACH ROW IF NEW.Name=''
    THEN SIGNAL SQLSTATE '45000' SET
    	MESSAGE_TEXT = 'El nombre de la raza no puede estar vacío.';
END IF
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único',
  `Id_Race` int(11) NOT NULL,
  `Name` varchar(16) NOT NULL COMMENT 'Nombre',
  `Age` int(11) NOT NULL COMMENT 'Edad',
  PRIMARY KEY (`Id`),
  KEY `Id_Race` (`Id_Race`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `student`
--

INSERT INTO `student` (`Id`, `Id_Race`, `Name`, `Age`) VALUES
(1, 1, 'Elderbar', 112),
(2, 2, 'Nom-Eg-Rayeh', 89),
(4, 4, 'Chomo', 345);

--
-- Disparadores `student`
--
DROP TRIGGER IF EXISTS `student_bi`;
DELIMITER $$
CREATE TRIGGER `student_bi` BEFORE INSERT ON `student` FOR EACH ROW IF NEW.Name='' THEN
	SIGNAL SQLSTATE '45000' SET
    	MESSAGE_TEXT = 'El nombre del estudiante no puede estar vacío.';
END IF
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `student_bu`;
DELIMITER $$
CREATE TRIGGER `student_bu` BEFORE UPDATE ON `student` FOR EACH ROW IF NEW.Name='' THEN
	SIGNAL SQLSTATE '45000' SET
    	MESSAGE_TEXT = 'El nombre del estudiante no puede estar vacío.';
END IF
$$
DELIMITER ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`Id_Race`) REFERENCES `race` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
