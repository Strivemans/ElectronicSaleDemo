CREATE DATABASE  IF NOT EXISTS `sales_sys` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sales_sys`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: sales_sys
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL DEFAULT 'null',
  `password` varchar(15) NOT NULL DEFAULT 'null',
  `saler_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (5,'yao','12345678',14),(7,'jack','jie15218423290',11),(8,'zzz','12345678',15),(9,'hao','12345678',12);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `after_sales`
--

DROP TABLE IF EXISTS `after_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `after_sales` (
  `after_id` int NOT NULL AUTO_INCREMENT,
  `after_problem` varchar(50) DEFAULT NULL COMMENT '售后问题',
  `after_state` varchar(20) NOT NULL DEFAULT '未处理',
  `client_id` int NOT NULL,
  `saler_id` int NOT NULL COMMENT '销售员id',
  `order_id` int DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`after_id`),
  KEY `afterAndSaler` (`saler_id`),
  KEY `afterAndGood` (`order_id`),
  KEY `AfterAndClient` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `after_sales`
--

LOCK TABLES `after_sales` WRITE;
/*!40000 ALTER TABLE `after_sales` DISABLE KEYS */;
INSERT INTO `after_sales` VALUES (10,'手机有问题','处理中',2,11,14,'2022-10-24 21:12:19',NULL),(11,'手机有问题','处理中',9,14,18,'2022-10-25 17:07:14',NULL),(12,'商品存在问题','处理中',1,14,29,'2022-11-01 09:35:51',NULL),(13,'商品有问题','处理中',9,14,30,'2022-11-02 11:56:49',NULL);
/*!40000 ALTER TABLE `after_sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `client_id` int NOT NULL AUTO_INCREMENT,
  `client_name` varchar(20) NOT NULL,
  `client_tel` varchar(20) NOT NULL,
  `client_rank` int NOT NULL,
  `client_address` varchar(50) DEFAULT NULL,
  `create_date` date NOT NULL DEFAULT '2022-10-14',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'张三','17659849278',3,'广东省肇庆市','2022-10-15'),(2,'李四','18759723846',3,'广东省东莞市','2022-10-14'),(3,'王五','18642975640',1,'广东省清远市','2022-10-14'),(5,'毛大星','18372947284',1,'广东省四会市','2022-10-18'),(6,'蔡徐坤','1234567',3,'ikun家族大本营','2022-10-14'),(7,'苏大康','18294820192',1,'广东省肇庆市','2022-10-14'),(8,'路飞','18275208421',1,'广东省广州市','2022-10-18'),(9,'吕布','18759723846',1,'广东省东莞市','2022-10-14'),(10,'刘备','28427109528',1,'广东省·广州市','2022-10-14'),(11,'Alex','1234567',1,'广东省肇庆市','2022-10-14');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good`
--

DROP TABLE IF EXISTS `good`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good` (
  `good_id` int NOT NULL AUTO_INCREMENT,
  `good_name` varchar(20) NOT NULL DEFAULT 'null' COMMENT '产品名称',
  `good_stock` int NOT NULL DEFAULT '0' COMMENT '产品库存',
  `good_inventory` int NOT NULL DEFAULT '0' COMMENT '产品销售量',
  `good_price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `good_profit` double DEFAULT NULL,
  `species_id` int NOT NULL COMMENT '分类id',
  PRIMARY KEY (`good_id`),
  KEY `goodAndgoodSpecies` (`species_id`),
  CONSTRAINT `goodAndgoodSpecies` FOREIGN KEY (`species_id`) REFERENCES `good_species` (`species_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good`
--

LOCK TABLES `good` WRITE;
/*!40000 ALTER TABLE `good` DISABLE KEYS */;
INSERT INTO `good` VALUES (1,'小米9',8,49,2999,15.5,1),(2,'华为MAX',37,10,3200,17.8,1),(7,'超大屏电视',188,9,2999,15.5,3),(8,'瑷玛斯电灯',138,5,157,4.5,7);
/*!40000 ALTER TABLE `good` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_order`
--

DROP TABLE IF EXISTS `good_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_order` (
  `go_id` int NOT NULL AUTO_INCREMENT COMMENT '产品和订单关联的id',
  `good_id` int NOT NULL COMMENT '产品id',
  `order_id` int NOT NULL COMMENT '订单id',
  `num` int NOT NULL DEFAULT '1',
  `stock_out` int DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`go_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_order`
--

LOCK TABLES `good_order` WRITE;
/*!40000 ALTER TABLE `good_order` DISABLE KEYS */;
INSERT INTO `good_order` VALUES (44,8,21,1,0,157),(52,1,14,1,0,2999),(53,2,18,2,0,6400),(61,1,27,6,0,17994),(62,2,27,2,0,6400),(63,7,27,3,0,8997),(64,8,27,2,0,314),(65,2,28,1,0,3200),(68,7,29,1,0,2999),(69,8,29,1,0,157),(72,7,30,1,0,2999),(73,1,30,2,0,5998);
/*!40000 ALTER TABLE `good_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_species`
--

DROP TABLE IF EXISTS `good_species`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_species` (
  `species_id` int NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `species_name` varchar(20) NOT NULL COMMENT '分类名字',
  PRIMARY KEY (`species_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_species`
--

LOCK TABLES `good_species` WRITE;
/*!40000 ALTER TABLE `good_species` DISABLE KEYS */;
INSERT INTO `good_species` VALUES (1,'智能手机'),(3,'电视机'),(4,'计算机'),(6,'电池'),(7,'电灯'),(8,'键盘'),(9,'显示器'),(10,'游戏手柄'),(11,'插座');
/*!40000 ALTER TABLE `good_species` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_state` varchar(10) NOT NULL DEFAULT '已支付',
  `order_money` double NOT NULL DEFAULT '0',
  `client_id` int NOT NULL DEFAULT '0',
  `saler_name` varchar(10) NOT NULL DEFAULT 'zs',
  `order_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (14,'已跟进',2549.15,2,'梁伟杰','2022-10-24 10:47:50'),(18,'已跟进',6400,9,'魏树尧','2022-10-25 05:05:44'),(21,'已支付',157,9,'梁伟杰','2022-10-27 19:58:50'),(27,'已支付',33705,8,'魏树尧','2021-10-29 23:19:14'),(28,'已支付',3200,11,'谢子豪','2022-10-30 08:24:33'),(29,'已跟进',2682.6,1,'魏树尧','2022-11-01 09:30:31'),(30,'已跟进',8997,9,'魏树尧','2022-11-02 11:53:22');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saler`
--

DROP TABLE IF EXISTS `saler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saler` (
  `saler_id` int NOT NULL AUTO_INCREMENT,
  `saler_name` varchar(20) NOT NULL,
  `address` varchar(255) NOT NULL DEFAULT '广东省肇庆市',
  `signatur` varchar(20) DEFAULT NULL,
  `account_id` int NOT NULL DEFAULT '0',
  `saler_tel` varchar(50) NOT NULL,
  `permission_id` int DEFAULT '2',
  `avatar_url` varchar(150) DEFAULT 'http://electronsysdemo.oss-cn-hangzhou.aliyuncs.com/2022/10/17/2ac04be74f4a475b953be8558e24d1e3用户.png',
  PRIMARY KEY (`saler_id`),
  KEY `salerAndPermission` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saler`
--

LOCK TABLES `saler` WRITE;
/*!40000 ALTER TABLE `saler` DISABLE KEYS */;
INSERT INTO `saler` VALUES (11,'梁伟杰','广东省肇庆市','目标已经更新',7,'15218423290',1,'http://electronsysdemo.oss-cn-hangzhou.aliyuncs.com/2022/10/24/bedcb073583346289c0fb03828a4cd6fexam.jpg'),(12,'谢子豪','广东省肇庆市',NULL,9,'18649285042',3,'http://electronsysdemo.oss-cn-hangzhou.aliyuncs.com/2022/10/17/2ac04be74f4a475b953be8558e24d1e3用户.png'),(14,'魏树尧','广东省东莞市',NULL,5,'17482950284',2,'http://electronsysdemo.oss-cn-hangzhou.aliyuncs.com/2022/10/17/2ac04be74f4a475b953be8558e24d1e3用户.png'),(15,'思想是','广东省肇庆市',NULL,8,'15218423290',2,'http://electronsysdemo.oss-cn-hangzhou.aliyuncs.com/2022/10/17/2ac04be74f4a475b953be8558e24d1e3用户.png');
/*!40000 ALTER TABLE `saler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saler_order`
--

DROP TABLE IF EXISTS `saler_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saler_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `saler_id` int NOT NULL DEFAULT '0',
  `order_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saler_order`
--

LOCK TABLES `saler_order` WRITE;
/*!40000 ALTER TABLE `saler_order` DISABLE KEYS */;
INSERT INTO `saler_order` VALUES (9,11,14),(12,14,18),(14,11,21),(21,14,27),(22,12,28),(23,14,29),(24,14,30);
/*!40000 ALTER TABLE `saler_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saler_profit`
--

DROP TABLE IF EXISTS `saler_profit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saler_profit` (
  `sprofit_id` int NOT NULL AUTO_INCREMENT,
  `saler_id` int DEFAULT NULL,
  `order_id` int NOT NULL DEFAULT '0',
  `profit` decimal(5,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`sprofit_id`),
  KEY `saler_id` (`saler_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saler_profit`
--

LOCK TABLES `saler_profit` WRITE;
/*!40000 ALTER TABLE `saler_profit` DISABLE KEYS */;
INSERT INTO `saler_profit` VALUES (7,11,10,0.00),(8,11,11,271.70),(17,11,21,15.70),(18,14,22,239.92),(23,11,14,239.92),(24,14,18,256.00),(25,12,25,239.92),(27,12,26,239.92),(29,14,27,751.54),(30,12,28,256.00),(32,14,29,255.62),(34,14,30,479.84);
/*!40000 ALTER TABLE `saler_profit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-07 15:53:08
