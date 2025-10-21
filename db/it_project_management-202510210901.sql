-- MySQL dump 10.13  Distrib 8.4.6, for Win64 (x86_64)
--
-- Host: localhost    Database: it_project_management
-- ------------------------------------------------------
-- Server version	8.4.6

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender` varchar(20) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `chat_message_project_fk` (`project_id`),
  CONSTRAINT `chat_message_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
INSERT INTO `chat_message` VALUES (4,'user','Summarize the project status','2025-10-12 08:48:54',1),(5,'bot','⚠️ Không thể kết nối đến API chatbot.','2025-10-12 08:48:54',1),(6,'user','Summarize the project status','2025-10-12 08:48:59',2),(7,'bot','⚠️ Không thể kết nối đến API chatbot.','2025-10-12 08:48:59',2),(8,'user','Summarize the project status','2025-10-12 09:09:28',1),(9,'bot','⚠️ Không thể kết nối đến API chatbot.','2025-10-12 09:09:28',1),(10,'user','Summarize the project status','2025-10-12 10:35:14',1),(11,'bot','⚠️ Không thể kết nối đến API chatbot.','2025-10-12 10:35:14',1),(12,'user','Summarize the project status','2025-10-21 01:54:08',1),(13,'bot','⚠️ Không thể kết nối đến API chatbot.','2025-10-21 01:54:08',1);
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_in`
--

DROP TABLE IF EXISTS `check_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_in` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day` datetime DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `task_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn99m12o3e7ly0xqvtu8p3c3wk` (`task_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_in`
--

LOCK TABLES `check_in` WRITE;
/*!40000 ALTER TABLE `check_in` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feature`
--

DROP TABLE IF EXISTS `feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature` (
  `feature_id` int NOT NULL AUTO_INCREMENT,
  `feature_name` varchar(255) NOT NULL,
  `feature_description` varchar(255) DEFAULT NULL,
  `project_version_id` int NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `progress` int DEFAULT NULL,
  `estimated_end_date` date DEFAULT NULL,
  `enable` tinyint(1) DEFAULT '1',
  `status` enum('PROCESSING','COMPLETED','PENDING','POSTPONED') NOT NULL,
  PRIMARY KEY (`feature_id`),
  KEY `project_version_id` (`project_version_id`),
  CONSTRAINT `feature_ibfk_1` FOREIGN KEY (`project_version_id`) REFERENCES `project_version` (`project_version_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature`
--

LOCK TABLES `feature` WRITE;
/*!40000 ALTER TABLE `feature` DISABLE KEYS */;
INSERT INTO `feature` VALUES (1,'Card Management','Description for Card Management',1,'2023-01-01','2023-01-05',100,'2023-01-05',1,'COMPLETED'),(2,'Device Management','Description for Device Management',1,'2023-01-06','2023-01-10',10,'2023-01-10',1,'COMPLETED'),(3,'Transaction Management','Description for Transaction Management',1,'2023-02-01',NULL,50,NULL,1,'PROCESSING'),(4,'Page Navigation','Description for Page Navigation',2,'2023-02-01','2023-02-10',100,'2023-02-10',1,'COMPLETED'),(5,'Search Functionality','Description for Search Functionality',2,'2023-02-11','2023-02-20',10,'2023-02-20',1,'COMPLETED'),(6,'User Authentication','Description for User Authentication',2,'2023-02-21',NULL,50,NULL,1,'PROCESSING'),(7,'Student Registration','Description for Student Registration',3,'2023-03-01','2023-03-10',80,'2023-03-10',1,'PROCESSING'),(8,'Course Enrollment','Description for Course Enrollment',3,'2023-03-11','2023-03-15',20,'2023-03-15',1,'PENDING');
/*!40000 ALTER TABLE `feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issue` (
  `issue_id` int NOT NULL AUTO_INCREMENT,
  `task_id` int DEFAULT NULL,
  `issue_type` enum('REQUEST','COMMENT','NOTE','BUG') NOT NULL,
  `priority` enum('CRITICAL','HIGH','MEDIUM','LOW') DEFAULT NULL,
  `summary` varchar(255) NOT NULL,
  `reporter` int NOT NULL,
  `created_at` date DEFAULT NULL,
  PRIMARY KEY (`issue_id`),
  KEY `task_id` (`task_id`),
  KEY `reporter` (`reporter`),
  CONSTRAINT `issue_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `issue_ibfk_2` FOREIGN KEY (`reporter`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES (1,1,'REQUEST','HIGH','Issue 1 for Task 1',2,'2023-01-02'),(2,1,'COMMENT','LOW','Issue 2 for Task 1',3,'2023-01-02'),(3,1,'NOTE','MEDIUM','Issue 3 for Task 1',2,'2023-01-03'),(4,1,'BUG','CRITICAL','Issue 4 for Task 1',3,'2023-01-04'),(5,1,'REQUEST','MEDIUM','Issue 5 for Task 1',2,'2023-01-05'),(6,2,'COMMENT','LOW','Issue 1 for Task 2',3,'2023-01-03'),(7,2,'NOTE','MEDIUM','Issue 2 for Task 2',2,'2023-01-03'),(8,2,'BUG','HIGH','Issue 3 for Task 2',3,'2023-01-04'),(9,2,'REQUEST','MEDIUM','Issue 4 for Task 2',2,'2023-01-05'),(10,2,'COMMENT','LOW','Issue 5 for Task 2',3,'2023-01-06'),(11,3,'NOTE','MEDIUM','Issue 1 for Task 3',2,'2023-01-06'),(12,3,'BUG','HIGH','Issue 2 for Task 3',3,'2023-01-07'),(13,3,'REQUEST','LOW','Issue 3 for Task 3',2,'2023-01-08'),(14,3,'COMMENT','MEDIUM','Issue 4 for Task 3',3,'2023-01-09'),(15,3,'NOTE','HIGH','Issue 5 for Task 3',2,'2023-01-10'),(16,4,'BUG','MEDIUM','Issue 1 for Task 4',2,'2023-01-06'),(17,4,'REQUEST','LOW','Issue 2 for Task 4',3,'2023-01-07'),(18,4,'COMMENT','MEDIUM','Issue 3 for Task 4',2,'2023-01-08'),(19,4,'NOTE','HIGH','Issue 4 for Task 4',3,'2023-01-09'),(20,4,'BUG','MEDIUM','Issue 5 for Task 4',2,'2023-01-10'),(21,5,'REQUEST','MEDIUM','Issue 1 for Task 5',2,'2023-01-06'),(22,5,'COMMENT','LOW','Issue 2 for Task 5',3,'2023-01-07'),(23,5,'NOTE','HIGH','Issue 3 for Task 5',2,'2023-01-08'),(24,5,'BUG','MEDIUM','Issue 4 for Task 5',3,'2023-01-09'),(25,5,'REQUEST','LOW','Issue 5 for Task 5',2,'2023-01-10'),(26,6,'COMMENT','HIGH','Issue 1 for Task 6',3,'2023-01-07'),(27,6,'NOTE','MEDIUM','Issue 2 for Task 6',2,'2023-01-08'),(28,6,'BUG','LOW','Issue 3 for Task 6',3,'2023-01-09'),(29,6,'REQUEST','MEDIUM','Issue 4 for Task 6',2,'2023-01-10'),(30,6,'COMMENT','HIGH','Issue 5 for Task 6',3,'2023-01-11'),(31,7,'NOTE','LOW','Issue 1 for Task 7',2,'2023-01-08'),(32,7,'BUG','MEDIUM','Issue 2 for Task 7',3,'2023-01-09'),(33,7,'REQUEST','HIGH','Issue 3 for Task 7',2,'2023-01-10'),(34,7,'COMMENT','MEDIUM','Issue 4 for Task 7',3,'2023-01-11'),(35,7,'NOTE','LOW','Issue 5 for Task 7',2,'2023-01-12'),(36,8,'BUG','MEDIUM','Issue 1 for Task 8',3,'2023-01-09'),(37,8,'REQUEST','LOW','Issue 2 for Task 8',2,'2023-01-10'),(38,8,'COMMENT','MEDIUM','Issue 3 for Task 8',3,'2023-01-11'),(39,8,'NOTE','HIGH','Issue 4 for Task 8',2,'2023-01-12'),(40,8,'BUG','MEDIUM','Issue 5 for Task 8',3,'2023-01-13'),(41,9,'BUG','HIGH','Issue 4 for Task 9',3,'2023-01-13'),(42,9,'REQUEST','MEDIUM','Issue 5 for Task 9',2,'2023-01-14'),(43,10,'COMMENT','MEDIUM','Issue 1 for Task 10',3,'2023-01-11'),(44,10,'NOTE','LOW','Issue 2 for Task 10',2,'2023-01-12'),(45,10,'BUG','HIGH','Issue 3 for Task 10',3,'2023-01-13'),(46,10,'REQUEST','MEDIUM','Issue 4 for Task 10',2,'2023-01-14'),(47,10,'COMMENT','HIGH','Issue 5 for Task 10',3,'2023-01-15');
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwt_response`
--

DROP TABLE IF EXISTS `jwt_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwt_response` (
  `id` int NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwt_response`
--

LOCK TABLES `jwt_response` WRITE;
/*!40000 ALTER TABLE `jwt_response` DISABLE KEYS */;
/*!40000 ALTER TABLE `jwt_response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `noti`
--

DROP TABLE IF EXISTS `noti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `noti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text,
  `timestamp` datetime DEFAULT NULL,
  `unread` bit(1) DEFAULT NULL,
  `recipient` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlyr1iusr4egk3h3w1iiw3qxsa` (`recipient`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `noti`
--

LOCK TABLES `noti` WRITE;
/*!40000 ALTER TABLE `noti` DISABLE KEYS */;
INSERT INTO `noti` VALUES (1,'manager assigned task Implement Student Registration to you','2025-09-29 03:23:43',_binary '',4),(2,'manager assigned task Implement Student Registration to you','2025-09-29 03:23:51',_binary '',4),(3,'manager assigned task Implement Student Registration to you','2025-09-29 03:23:53',_binary '',5);
/*!40000 ALTER TABLE `noti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) NOT NULL,
  `project_description` varchar(255) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Transaction Management','This is Transaction Management',1),(2,'Student Management','Description for Student Management',1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_version`
--

DROP TABLE IF EXISTS `project_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_version` (
  `project_version_id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  `version` varchar(50) NOT NULL,
  `version_description` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `estimated_end_date` date DEFAULT NULL,
  `status` enum('PROCESSING','COMPLETED','PENDING','POSTPONED') NOT NULL,
  `progress` int DEFAULT NULL,
  `enable` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`project_version_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `project_version_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_version`
--

LOCK TABLES `project_version` WRITE;
/*!40000 ALTER TABLE `project_version` DISABLE KEYS */;
INSERT INTO `project_version` VALUES (1,1,'1.0','Basic Function for Transaction Management','2023-01-01','2023-01-10','2023-01-15','PROCESSING',100,1),(2,1,'2.0','Pageable and advance Function for Transaction Management','2023-02-01',NULL,NULL,'PROCESSING',50,1),(3,2,'1.0','Version 1.0 for Student Management','2023-03-01','2023-03-15','2023-03-20','PENDING',10,1);
/*!40000 ALTER TABLE `project_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) NOT NULL,
  `task_description` varchar(255) DEFAULT NULL,
  `feature_id` int NOT NULL,
  `assigned_to` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `estimated_end_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `progress` int DEFAULT NULL,
  `status` enum('PROCESSING','COMPLETED','PENDING','POSTPONED') NOT NULL,
  `priority` enum('CRITICAL','HIGH','MEDIUM','LOW') DEFAULT NULL,
  `enable` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`task_id`),
  KEY `feature_id` (`feature_id`),
  KEY `assigned_to` (`assigned_to`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`feature_id`),
  CONSTRAINT `task_ibfk_2` FOREIGN KEY (`assigned_to`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Add Card','Description for Task 1',1,4,'2023-01-01','2023-05-05','2023-01-02',100,'COMPLETED','HIGH',1),(2,'Delete Card','Description for Task 2',1,3,'2023-01-02','2023-05-20',NULL,100,'COMPLETED','MEDIUM',1),(3,'Update Card','Description for Task 3',1,5,'2023-01-06','2023-06-12',NULL,30,'POSTPONED','MEDIUM',1),(4,'Find All Card','Description for Task 4',1,5,'2023-01-06','2023-05-29',NULL,40,'PROCESSING','MEDIUM',1),(5,'Add Device','Description for Task 1',2,6,'2023-01-01','2023-06-10','2023-01-02',100,'COMPLETED','HIGH',1),(6,'Delete Device','Description for Task 2',2,4,'2023-01-02','2023-07-01','2023-01-03',100,'COMPLETED','MEDIUM',1),(7,'Update Device','Description for Task 3',2,3,'2023-01-06','2023-09-15','2023-01-03',80,'PROCESSING','MEDIUM',1),(8,'Find All Device','Description for Task 4',2,6,'2023-01-06','2023-06-18',NULL,60,'PROCESSING','MEDIUM',1),(9,'Add Transaction','Description for Task 1',3,5,'2023-01-01','2023-05-15','2023-01-02',100,'COMPLETED','HIGH',1),(10,'Delete Transaction','Description for Task 2',3,4,'2023-01-02','2023-06-30',NULL,100,'PROCESSING','MEDIUM',1),(11,'Update Transaction','Description for Task 3',3,NULL,'2023-01-06','2023-04-05',NULL,50,'PROCESSING','MEDIUM',1),(12,'Find All Transaction','Description for Task 4',3,5,'2023-01-06','2023-05-17',NULL,70,'PROCESSING','MEDIUM',1),(13,'Implement Page Navigation','Description for Task 1',4,3,'2023-02-01','2023-05-05','2023-02-02',100,'COMPLETED','HIGH',1),(14,'Refine Search Functionality','Description for Task 2',5,2,'2023-02-02','2023-05-20',NULL,100,'COMPLETED','MEDIUM',1),(15,'Set Up User Authentication','Description for Task 3',6,1,'2023-02-06','2023-06-12',NULL,30,'POSTPONED','MEDIUM',1),(16,'Test Page Navigation','Description for Task 4',4,1,'2023-02-06','2023-05-29',NULL,40,'PROCESSING','MEDIUM',1),(17,'Test Search Functionality','Description for Task 5',5,4,'2023-02-01','2023-06-10','2023-02-02',100,'COMPLETED','HIGH',1),(18,'Implement User Authentication','Description for Task 6',6,4,'2023-02-02','2023-07-01','2023-02-03',100,'COMPLETED','MEDIUM',1),(19,'Implement Student Registration','Description for Task 1',7,5,'2023-03-01','2023-03-05','2023-03-02',100,'COMPLETED','HIGH',1),(20,'Test Student Registration','Description for Task 2',7,3,'2023-03-02','2023-03-10',NULL,100,'COMPLETED','MEDIUM',1),(21,'Prepare Course Enrollment','Description for Task 3',8,4,'2023-03-11','2023-03-12',NULL,30,'POSTPONED','MEDIUM',1),(22,'Implement Course Enrollment','Description for Task 4',8,5,'2023-03-13','2023-03-15',NULL,40,'PROCESSING','MEDIUM',1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('USER','ADMIN','MANAGER') NOT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','ADMIN',1),(2,'manager','manager','MANAGER',1),(3,'user1','user1','USER',1),(4,'user2','user2','USER',1),(5,'user3','user3','USER',1),(6,'user4','user4','USER',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_project_version`
--

DROP TABLE IF EXISTS `user_project_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_project_version` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `project_version_id` int NOT NULL,
  `version_modification` tinyint(1) DEFAULT '0',
  `feature_modification` tinyint(1) DEFAULT '0',
  `task_modification` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `project_version_id` (`project_version_id`),
  CONSTRAINT `user_project_version_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_project_version_ibfk_2` FOREIGN KEY (`project_version_id`) REFERENCES `project_version` (`project_version_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_project_version`
--

LOCK TABLES `user_project_version` WRITE;
/*!40000 ALTER TABLE `user_project_version` DISABLE KEYS */;
INSERT INTO `user_project_version` VALUES (1,3,1,1,0,1),(2,4,2,1,1,0),(3,5,3,0,1,1),(4,6,1,0,0,0),(5,3,2,1,1,1),(6,2,2,1,1,0),(7,1,2,0,1,1),(8,4,3,0,1,1),(9,1,3,1,1,0),(10,2,3,1,0,1);
/*!40000 ALTER TABLE `user_project_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'it_project_management'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-21  9:01:21
