-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: market_vendors
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gm_items`
--

DROP TABLE IF EXISTS `gm_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gm_items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gm_items`
--

LOCK TABLES `gm_items` WRITE;
/*!40000 ALTER TABLE `gm_items` DISABLE KEYS */;
INSERT INTO `gm_items` VALUES (1,'Beans'),(2,'Ground Nuts'),(3,'Rice'),(4,'Posho'),(5,'Water Melon'),(6,'Mangoes'),(7,'Fish'),(8,'Tomatoes'),(9,'Onions');
/*!40000 ALTER TABLE `gm_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gm_markets`
--

DROP TABLE IF EXISTS `gm_markets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gm_markets` (
  `market_id` int(11) NOT NULL AUTO_INCREMENT,
  `market` varchar(100) NOT NULL,
  PRIMARY KEY (`market_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gm_markets`
--

LOCK TABLES `gm_markets` WRITE;
/*!40000 ALTER TABLE `gm_markets` DISABLE KEYS */;
INSERT INTO `gm_markets` VALUES (1,'Kaleerwe'),(2,'Owino'),(3,'Nakawa'),(4,'Bugolobi');
/*!40000 ALTER TABLE `gm_markets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gm_units`
--

DROP TABLE IF EXISTS `gm_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gm_units` (
  `unit_id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(5) NOT NULL,
  PRIMARY KEY (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gm_units`
--

LOCK TABLES `gm_units` WRITE;
/*!40000 ALTER TABLE `gm_units` DISABLE KEYS */;
INSERT INTO `gm_units` VALUES (1,'KG'),(2,'BOX'),(3,'SACK'),(4,'PIECE');
/*!40000 ALTER TABLE `gm_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gm_vendor_items`
--

DROP TABLE IF EXISTS `gm_vendor_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gm_vendor_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `date` int(11) NOT NULL,
  `unit_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`),
  KEY `vendor_id` (`vendor_id`),
  KEY `unit_id` (`unit_id`),
  CONSTRAINT `gm_vendor_items_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `gm_items` (`item_id`),
  CONSTRAINT `gm_vendor_items_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `gm_vendors` (`vendor_id`),
  CONSTRAINT `gm_vendor_items_ibfk_3` FOREIGN KEY (`unit_id`) REFERENCES `gm_units` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gm_vendor_items`
--

LOCK TABLES `gm_vendor_items` WRITE;
/*!40000 ALTER TABLE `gm_vendor_items` DISABLE KEYS */;
INSERT INTO `gm_vendor_items` VALUES (1,1,2,40000,1433569930,1),(8,5,12,2000,1433692700,4),(9,6,12,50000,1433692712,3),(10,2,12,100000,1433692747,3),(11,6,12,1000,1433692779,4),(12,8,12,200,1433700244,4),(13,2,13,24000,1433719526,3),(14,6,13,2500,1433719551,4),(15,4,13,100000,1433719572,3),(16,9,13,0,1433719622,4),(17,7,13,2500,1433745904,2),(18,5,14,5600,1433747228,4),(19,6,14,2500,1433747244,4),(20,4,14,3000,1433747256,1),(21,1,15,60500,1433748017,3),(22,5,15,6800,1433748029,4),(23,9,15,8900,1433748044,2);
/*!40000 ALTER TABLE `gm_vendor_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gm_vendors`
--

DROP TABLE IF EXISTS `gm_vendors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gm_vendors` (
  `vendor_id` int(11) NOT NULL AUTO_INCREMENT,
  `market_id` int(11) NOT NULL,
  `vendor_name` varchar(100) NOT NULL,
  `stall_number` varchar(10) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `app_id` varchar(10) NOT NULL,
  `date` int(11) NOT NULL,
  PRIMARY KEY (`vendor_id`),
  KEY `market_id` (`market_id`),
  CONSTRAINT `gm_vendors_ibfk_1` FOREIGN KEY (`market_id`) REFERENCES `gm_markets` (`market_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gm_vendors`
--

LOCK TABLES `gm_vendors` WRITE;
/*!40000 ALTER TABLE `gm_vendors` DISABLE KEYS */;
INSERT INTO `gm_vendors` VALUES (2,1,'Blue Mobile','BL6690','4095','1234567890',1433534643),(3,2,'Niyetu','104','777795958','8QUN3KH9IM',1433595442),(4,2,'Niyetu','104','0777795958','2UE9QKS13M',1433596939),(5,2,'Niyetu','104','0777795959','A3DCBF9ZNE',1433596978),(6,2,'Niyetu','104','256751700060','ELMD563UR8',1433604858),(7,2,'hfk','adfkf','gsjdf','HEGCV3DWJL',1433605100),(8,2,'Joseph','ST089','256789918271','C9YQZHL8A2',1433607732),(9,3,'Flora','ST097','256703115152','AV1736H4M5',1433608619),(10,4,'Octave Traders','ST097574','256704336200','B3KHGPWOS1',1433609250),(11,1,'Extreme Destiny','ST0584','256780700050','U9KIJ517A3',1433655818),(12,2,'Yo Uganda','ST1112','256786786786','WVEK9NX7RH',1433691794),(13,3,'SOFT MOBILE LIMITED','STH575','256771918271','JDKYS9G16F',1433719504),(14,4,'Flora Mugena','ST6854','256712115152','S9GNQ3ZYIF',1433747203),(15,4,'Mutanga','REW7907','256781212212','PWZGK6BNAH',1433748004);
/*!40000 ALTER TABLE `gm_vendors` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-08 12:17:52
