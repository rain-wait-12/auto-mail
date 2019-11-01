/*
Navicat MySQL Data Transfer

Source Server         : auto-mail
Source Server Version : 50713
Source Host           : 10.0.3.42:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-10-31 14:14:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auto_datasource
-- ----------------------------
DROP TABLE IF EXISTS `auto_datasource`;
CREATE TABLE `auto_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for auto_mail
-- ----------------------------
DROP TABLE IF EXISTS `auto_mail`;
CREATE TABLE `auto_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receivers` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for auto_work
-- ----------------------------
DROP TABLE IF EXISTS `auto_work`;
CREATE TABLE `auto_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_sql` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `work_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cron_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `source_id` int(11) DEFAULT NULL,
  `mail_id` int(11) DEFAULT NULL,
  `status` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
