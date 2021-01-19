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
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE task_info (
                      id varchar(200) NOT NULL ,
                      job_handler varchar(200)  DEFAULT NULL,
                      cron varchar(100)  DEFAULT NULL,
                      param varchar(300)  DEFAULT NULL,
                      job_name varchar(100) DEFAULT NULL,
                      PRIMARY KEY (id)
                      );