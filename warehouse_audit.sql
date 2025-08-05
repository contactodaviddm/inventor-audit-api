CREATE DATABASE  IF NOT EXISTS `warehouse_audit` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `warehouse_audit`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: warehouse_audit
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entity_name` varchar(100) NOT NULL,
  `operation` enum('INSERT','UPDATE','DELETE') NOT NULL,
  `data_before` json NOT NULL,
  `data_after` json NOT NULL,
  `user_id` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `usuario_fk2_idx` (`user_id`),
  CONSTRAINT `user_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (1,'User','INSERT','\"empty\"','{\"id\": 2, \"name\": \"Yune\", \"email\": \"yune@gmail.com\", \"lastName\": \"Bandera\", \"password\": \"123\", \"phoneNumber\": \"3169874859\", \"documentNumber\": 789}',1,'2025-08-02 20:29:33'),(2,'User','UPDATE','{\"id\": 2, \"name\": \"Yune\", \"email\": \"yune@gmail.com\", \"lastName\": \"Bandera\", \"password\": \"123\", \"phoneNumber\": \"3169874859\", \"documentNumber\": 789}','{\"id\": 2, \"name\": \"Yune\", \"email\": \"yune@gmail.com\", \"lastName\": \"Bandera\", \"password\": \"123\", \"phoneNumber\": \"3169874859\", \"documentNumber\": 10059874569}',1,'2025-08-02 20:50:22'),(3,'User','INSERT','\"empty\"','{\"id\": 3, \"name\": \"Prueba\", \"email\": \"33@gmail.com\", \"lastName\": \"Bandera\", \"password\": \"123\", \"phoneNumber\": \"5659874\", \"documentNumber\": 1005}',1,'2025-08-02 20:52:55'),(4,'User','DELETE','{\"id\": 3, \"name\": \"Prueba\", \"email\": \"33@gmail.com\", \"lastName\": \"Bandera\", \"password\": \"123\", \"phoneNumber\": \"5659874\", \"documentNumber\": 1005}','\"empty\"',1,'2025-08-02 20:53:13'),(5,'Brand','INSERT','\"empty\"','{\"id\": 1, \"name\": \"Acer\"}',1,'2025-08-02 21:45:23'),(7,'Brand','DELETE','{\"id\": 2, \"name\": \"Acer\"}','\"empty\"',1,'2025-08-02 21:45:34'),(8,'Brand','INSERT','\"empty\"','{\"id\": 3, \"name\": \"Samsung\"}',1,'2025-08-02 21:51:01'),(9,'Brand','INSERT','\"empty\"','{\"id\": 4, \"name\": \"Lenovo\"}',1,'2025-08-02 21:51:08'),(10,'Brand','INSERT','\"empty\"','{\"id\": 5, \"name\": \"LG\"}',1,'2025-08-02 21:51:14'),(16,'Brand','UPDATE','{\"id\": 3, \"name\": \"Samsung\"}','{\"id\": 3, \"name\": \"Epson\"}',1,'2025-08-02 22:28:34'),(17,'Brand','DELETE','{\"id\": 3, \"name\": \"Epson\"}','\"empty\"',1,'2025-08-02 22:28:52'),(18,'Category','INSERT','\"empty\"','{\"id\": 1, \"name\": \"Laptos\"}',1,'2025-08-02 22:59:25'),(19,'Category','INSERT','\"empty\"','{\"id\": 2, \"name\": \"Monitores\"}',1,'2025-08-02 22:59:35'),(20,'Category','INSERT','\"empty\"','{\"id\": 3, \"name\": \"Impresoras\"}',1,'2025-08-02 23:03:48'),(21,'Category','INSERT','\"empty\"','{\"id\": 4, \"name\": \"Accesorios\"}',1,'2025-08-02 23:04:01'),(22,'Category','INSERT','\"empty\"','{\"id\": 5, \"name\": \"Componentes Internos\"}',1,'2025-08-02 23:04:17'),(23,'Category','UPDATE','{\"id\": 5, \"name\": \"Componentes Internos\"}','{\"id\": 5, \"name\": \"Componentes Prueba\"}',1,'2025-08-02 23:10:27'),(24,'Category','DELETE','{\"id\": 5, \"name\": \"Componentes Prueba\"}','\"empty\"',1,'2025-08-02 23:11:07'),(25,'Category','UPDATE','{\"id\": 4, \"name\": \"Accesorios\"}','{\"id\": 4, \"name\": \"ACCESORIOS\"}',1,'2025-08-03 02:07:43'),(26,'Category','UPDATE','{\"id\": 4, \"name\": \"ACCESORIOS\"}','{\"id\": 4, \"name\": \"Accesorios\"}',1,'2025-08-03 02:07:50'),(27,'Category','UPDATE','{\"id\": 4, \"name\": \"Accesorios\"}','{\"id\": 4, \"name\": \"Bodega Central\"}',1,'2025-08-03 02:18:32'),(28,'Category','UPDATE','{\"id\": 4, \"name\": \"Bodega Central\"}','{\"id\": 4, \"name\": \"Accesorios\"}',1,'2025-08-03 02:19:31'),(29,'Warehouse','INSERT','\"empty\"','{\"id\": 1, \"name\": \"Accesorios\", \"address\": \"Cra 12 # 34-56\"}',1,'2025-08-03 02:20:19'),(30,'Warehouse','UPDATE','{\"id\": 1, \"name\": \"Accesorios\", \"address\": \"Cra 12 # 34-56\"}','{\"id\": 1, \"name\": \"Bodega Central\", \"address\": \"Cra 12 # 34-56\"}',1,'2025-08-03 02:20:59'),(31,'Warehouse','INSERT','\"empty\"','{\"id\": 2, \"name\": \"Bodega 2\", \"address\": \"Cra 12 # 34-56\"}',1,'2025-08-03 02:22:49'),(32,'Warehouse','DELETE','{\"id\": 2, \"name\": \"Bodega 2\", \"address\": \"Cra 12 # 34-56\"}','\"empty\"',1,'2025-08-03 02:22:59'),(33,'Product','INSERT','\"empty\"','{\"id\": 1, \"code\": \"P001\", \"name\": \"Mouse Inalámbrico\", \"brand\": {\"id\": 1, \"name\": \"Acer\"}, \"price\": 50000, \"shelf\": \"A1\", \"stock\": 150, \"status\": \"ACTIVO\", \"category\": {\"id\": 1, \"name\": \"Laptos\"}, \"warehouse\": {\"id\": 1, \"name\": \"Bodega Central\", \"address\": \"Cra 12 # 34-56\"}}',1,'2025-08-03 19:24:29'),(34,'Warehouse','INSERT','\"empty\"','{\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}',1,'2025-08-03 19:42:15'),(35,'Product','INSERT','\"empty\"','{\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 130000, \"shelf\": \"B3\", \"stock\": 85, \"status\": \"ACTIVO\", \"category\": {\"id\": 1, \"name\": \"Laptos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}',1,'2025-08-03 19:43:47'),(36,'Category','UPDATE','{\"id\": 4, \"name\": \"Accesorios\"}','{\"id\": 4, \"name\": \"Perifericos\"}',1,'2025-08-03 19:47:40'),(37,'Product','INSERT','\"empty\"','{\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 250000, \"shelf\": \"F2\", \"stock\": 15, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}',1,'2025-08-03 19:49:08'),(40,'Product','UPDATE','{\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 130000.0, \"shelf\": \"B3\", \"stock\": 85, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}','{\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 130000.0, \"shelf\": \"B3\", \"stock\": 105, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}',1,'2025-08-04 16:57:57'),(41,'Product','INSERT','\"empty\"','{\"id\": 4, \"code\": \"P009\", \"name\": \"Product Prueba\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 130000.0, \"shelf\": \"B3\", \"stock\": 105, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}',1,'2025-08-04 17:00:27'),(42,'Product','DELETE','{\"id\": 4, \"code\": \"P009\", \"name\": \"Product Prueba\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 130000.0, \"shelf\": \"B3\", \"stock\": 105, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}','\"empty\"',1,'2025-08-04 17:07:52'),(43,'Product','UPDATE','{\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 250000.0, \"shelf\": \"F2\", \"stock\": 15, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}','{\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"brand\": {\"id\": 5, \"name\": \"LG\"}, \"price\": 250000.0, \"shelf\": \"H2\", \"stock\": 15, \"status\": \"ACTIVO\", \"category\": {\"id\": 4, \"name\": \"Perifericos\"}, \"warehouse\": {\"id\": 3, \"name\": \"Bodega Aven. Los padros\", \"address\": \"Calle 9na # 55-23\"}}',1,'2025-08-04 17:12:23'),(54,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 105}, \"quantity\": 10}','{\"id\": 16, \"date\": \"2025-08-04T23:25:46.083603\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 95}, \"quantity\": 10}',1,'2025-08-05 04:25:46'),(55,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 95}, \"quantity\": 5}','{\"id\": 17, \"date\": \"2025-08-04T23:26:26.809573\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 90}, \"quantity\": 5}',1,'2025-08-05 04:26:27'),(56,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 90}, \"quantity\": 5}','{\"id\": 18, \"date\": \"2025-08-04T23:28:19.567854\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 85}, \"quantity\": 5}',1,'2025-08-05 04:28:20'),(57,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 85}, \"quantity\": 15}','{\"id\": 19, \"date\": \"2025-08-04T23:33:23.029297\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 2, \"code\": \"P002\", \"name\": \"Teclado Mecánico\", \"stock\": 100}, \"quantity\": 15}',1,'2025-08-05 04:33:23'),(58,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 15}, \"quantity\": 3}','{\"id\": 20, \"date\": \"2025-08-04T23:34:32.331060\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 18}, \"quantity\": 3}',1,'2025-08-05 04:34:32'),(59,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 18}, \"quantity\": 18}','{\"id\": 22, \"date\": \"2025-08-04T23:35:21.677197\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 0}, \"quantity\": 18}',1,'2025-08-05 04:35:22'),(60,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 0}, \"quantity\": 20}','{\"id\": 23, \"date\": \"2025-08-04T23:35:43.966509\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 20}, \"quantity\": 20}',1,'2025-08-05 04:35:44'),(61,'ProductMovements','INSERT','{\"id\": 23, \"date\": \"2025-08-04T23:35:44\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 20}, \"quantity\": 20}','{\"id\": 23, \"date\": \"2025-08-04T23:35:44\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 0}, \"quantity\": 20}',1,'2025-08-05 04:47:21'),(62,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 0}, \"quantity\": 20}','{\"id\": 24, \"date\": \"2025-08-04T23:49:55.811366\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 20}, \"quantity\": 20}',1,'2025-08-05 04:49:56'),(63,'ProductMovements','INSERT','{\"id\": 22, \"date\": \"2025-08-04T23:35:22\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 20}, \"quantity\": 18}','{\"id\": 22, \"date\": \"2025-08-04T23:35:22\", \"type\": \"SALIDA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 38}, \"quantity\": 18}',1,'2025-08-05 04:51:48'),(64,'ProductMovements','INSERT','{\"id\": 23, \"date\": \"2025-08-04T23:35:44\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 38}, \"quantity\": 20}','{\"id\": 23, \"date\": \"2025-08-04T23:35:44\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 18}, \"quantity\": 20}',1,'2025-08-05 04:52:57'),(65,'ProductMovements','INSERT','{\"id\": null, \"date\": \"null\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 18}, \"quantity\": 50}','{\"id\": 25, \"date\": \"2025-08-04T23:55:37.105285\", \"type\": \"ENTRADA\", \"user\": {\"id\": 1, \"name\": \"David\", \"document\": 1007333222, \"lastName\": \"DM\"}, \"product\": {\"id\": 3, \"code\": \"P003\", \"name\": \"Cámara de Seguridad IP\", \"stock\": 68}, \"quantity\": 50}',1,'2025-08-05 04:55:37');
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'Acer'),(4,'Lenovo'),(5,'LG');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Laptos'),(2,'Monitores'),(3,'Impresoras'),(4,'Perifericos');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `category_id` int NOT NULL,
  `brand_id` int NOT NULL,
  `stock` int NOT NULL,
  `warehouse_id` int NOT NULL,
  `shelf` varchar(10) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` enum('ACTIVO','AGOTADO') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_fk_idx` (`category_id`),
  KEY `marca_fk_idx` (`brand_id`),
  KEY `bodega_fk_idx` (`warehouse_id`),
  CONSTRAINT `brand_fk` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `category_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `warehouse_fk` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'P001','Mouse Inalámbrico',1,1,150,1,'A1',50000.00,'ACTIVO'),(2,'P002','Teclado Mecánico',4,5,100,3,'B3',130000.00,'ACTIVO'),(3,'P003','Cámara de Seguridad IP',4,5,68,3,'H2',250000.00,'ACTIVO');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_movements`
--

DROP TABLE IF EXISTS `product_movements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_movements` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  `quantity` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` enum('ENTRADA','SALIDA') NOT NULL,
  `status` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  PRIMARY KEY (`id`),
  KEY `producto_fk_idx` (`product_id`),
  KEY `usuario_fk_idx` (`user_id`),
  CONSTRAINT `product_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_movements`
--

LOCK TABLES `product_movements` WRITE;
/*!40000 ALTER TABLE `product_movements` DISABLE KEYS */;
INSERT INTO `product_movements` VALUES (16,2,1,10,'2025-08-05 04:25:46','SALIDA','ACTIVO'),(17,2,1,5,'2025-08-05 04:26:27','SALIDA','ACTIVO'),(18,2,1,5,'2025-08-05 04:28:20','SALIDA','ACTIVO'),(19,2,1,15,'2025-08-05 04:33:23','ENTRADA','ACTIVO'),(20,3,1,3,'2025-08-05 04:34:32','ENTRADA','ACTIVO'),(22,3,1,18,'2025-08-05 04:35:22','SALIDA','INACTIVO'),(23,3,1,20,'2025-08-05 04:35:44','ENTRADA','INACTIVO'),(24,3,1,20,'2025-08-05 04:49:56','ENTRADA','ACTIVO'),(25,3,1,50,'2025-08-05 04:55:37','ENTRADA','ACTIVO');
/*!40000 ALTER TABLE `product_movements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `document_number` bigint NOT NULL,
  `name` varchar(45) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `phone_number` varchar(25) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo_UNIQUE` (`email`),
  UNIQUE KEY `document_number_UNIQUE` (`document_number`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1007333222,'David','DM','3175719000','contacto.david.dm@gmail.com','123'),(2,10059874569,'Yune','Bandera','3169874859','yune@gmail.com','123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'Bodega Central','Cra 12 # 34-56'),(3,'Bodega Aven. Los padros','Calle 9na # 55-23');
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-05 12:50:49
