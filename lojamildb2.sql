-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: lojamildb
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `idCategoria` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) NOT NULL,
  `idDepartamento` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`),
  KEY `FKD4C701134EADDE0D` (`idDepartamento`),
  CONSTRAINT `FKD4C701134EADDE0D` FOREIGN KEY (`idDepartamento`) REFERENCES `departamento` (`idDepartamento`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Carros',1),(2,'Bonecas',1),(3,'Sony',2),(4,'Microsoft',2),(5,'Celulares',3),(6,'Tablets',3),(7,'Telefonia Fixa',3);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departamento`
--

DROP TABLE IF EXISTS `departamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departamento` (
  `idDepartamento` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) NOT NULL,
  PRIMARY KEY (`idDepartamento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamento`
--

LOCK TABLES `departamento` WRITE;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` VALUES (1,'Brinquedos'),(2,'Games'),(3,'Telefonia');
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `idPedido` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `finalizado` int(11) NOT NULL,
  `valorTotal` decimal(19,2) DEFAULT NULL,
  `idUsuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idPedido`),
  KEY `FK8E4203655AA1F137` (`idUsuario`),
  CONSTRAINT `FK8E4203655AA1F137` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (28,'2016-03-07',1,449.91,1),(29,'2016-03-08',1,499.99,1),(30,'2016-03-09',1,319.90,2),(31,'2016-03-09',1,315.00,1),(32,'2016-03-09',1,50.00,1),(33,'2016-03-09',1,749.98,1);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidoitem`
--

DROP TABLE IF EXISTS `pedidoitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidoitem` (
  `idPedidoItem` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `valor` decimal(19,2) DEFAULT NULL,
  `pedido_idPedido` bigint(20) DEFAULT NULL,
  `produto_idProduto` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idPedidoItem`),
  KEY `FKCD3AB818A1155409` (`pedido_idPedido`),
  KEY `FKCD3AB818E86ACF07` (`produto_idProduto`),
  CONSTRAINT `FKCD3AB818A1155409` FOREIGN KEY (`pedido_idPedido`) REFERENCES `pedido` (`idPedido`),
  CONSTRAINT `FKCD3AB818E86ACF07` FOREIGN KEY (`produto_idProduto`) REFERENCES `produto` (`idProduto`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidoitem`
--

LOCK TABLES `pedidoitem` WRITE;
/*!40000 ALTER TABLE `pedidoitem` DISABLE KEYS */;
INSERT INTO `pedidoitem` VALUES (26,3,149.97,28,2),(27,1,49.99,29,2),(28,3,150.00,29,1),(29,3,89.97,30,4),(30,1,49.99,30,2),(31,2,157.50,31,5),(32,1,50.00,32,1),(33,1,749.98,33,3);
/*!40000 ALTER TABLE `pedidoitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `idProduto` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(80) DEFAULT NULL,
  `dataCadastro` date DEFAULT NULL,
  `descricao` longtext,
  `precoCompra` decimal(19,2) DEFAULT NULL,
  `precoVenda` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `titulo` varchar(80) NOT NULL,
  `idSubCategoria` bigint(20) DEFAULT NULL,
  `imagem` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `FK50C666D923AFFBCB` (`idSubCategoria`),
  CONSTRAINT `FK50C666D923AFFBCB` FOREIGN KEY (`idSubCategoria`) REFERENCES `subcategoria` (`idSubCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'11','2016-03-05','Product 1 description',24.00,50.00,1,'Carrinho de fricçao 1:6 Azul',1,'car01.jpg'),(2,'12','2016-03-05','Product Two description of p2',11.23,49.99,1,'Motorola MotoG 3 geração',3,'motog3.jpg'),(3,'14','2016-03-06','Galaxy tap 7 processador dual core memoria etc.',479.99,749.98,1,'Galaxy Tab 7\" 2',11,'galaxy.jpg'),(4,'18','2016-03-06','Identificador de chamadas',14.99,29.99,1,'Identificador de chamadas',13,'identif.jpg'),(5,'24','2016-03-01','Boneca de pano Emilia Tamanho: 32cm 100% Algodão Fabricante: Mattel',48.45,78.75,1,'Boneca Emilia',6,NULL);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcategoria`
--

DROP TABLE IF EXISTS `subcategoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subcategoria` (
  `idSubCategoria` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) NOT NULL,
  `idCategoria` bigint(20) NOT NULL,
  PRIMARY KEY (`idSubCategoria`),
  KEY `FKE4029EF3674A8281` (`idCategoria`),
  CONSTRAINT `FKE4029EF3674A8281` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategoria`
--

LOCK TABLES `subcategoria` WRITE;
/*!40000 ALTER TABLE `subcategoria` DISABLE KEYS */;
INSERT INTO `subcategoria` VALUES (1,'Fricção',1),(2,'Controle Remoto',1),(3,'Android',5),(4,'WindowsPhone',5),(5,'IOS',5),(6,'Plástico\\Pano',2),(7,'PS3',3),(8,'PS4',3),(9,'One',4),(10,'360',4),(11,'7\"',6),(12,'10\"',6),(13,'Identificadores',7),(14,'Interativas',2);
/*!40000 ALTER TABLE `subcategoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `dataCadastro` date DEFAULT NULL,
  `login` varchar(20) NOT NULL,
  `nome` varchar(80) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(80) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'2016-03-04','rr','Cleiton','rr','toniclecb@gmail.com'),(2,'2016-03-08','aaa','aaa','aaaaaa','a@a.c');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-09 10:36:04
