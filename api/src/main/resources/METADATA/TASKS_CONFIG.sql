-- MySQL dump 10.13  Distrib 5.6.51, for Linux (x86_64)
--
-- Host: localhost    Database: openmrs
-- ------------------------------------------------------
-- Server version	5.6.51

--
-- Dumping data for table `scheduler_task_config`
--

LOCK TABLES `scheduler_task_config` WRITE;
/*!40000 ALTER TABLE `scheduler_task_config` DISABLE KEYS */;
INSERT INTO `scheduler_task_config` VALUES (DEFAULT,'SmartNotifierLoadTask','Carrega a lista de pacientes elegiveis a serem notificados por SMS ou chamadas telefónicas','org.openmrs.module.smartnotifier.task.LoadPatientsEligibleToNotificationsTask','2025-03-25 10:00:00','MM/dd/yyyy HH:mm:ss',86400,1,0,1,'2025-03-25 21:24:54',1,'2025-03-26 20:58:32','7d1d74e2-efb5-475f-b7cf-4f249334ab2f','2025-03-25 21:53:35'),(DEFAULT,'SmartNotifierSendTask','Esta tarefa faz o envio dos pacientes pendentes para a VIAMO','org.openmrs.module.smartnotifier.task.SendPatientsToNotifyTask','2025-03-25 13:00:00','MM/dd/yyyy HH:mm:ss',86400,1,0,1,'2025-03-25 21:27:24',1,'2025-03-26 20:58:32','eb9a3112-e991-4488-b482-e32df01f2c17','2025-03-26 20:54:05');
/*!40000 ALTER TABLE `scheduler_task_config` ENABLE KEYS */;
UNLOCK TABLES;

