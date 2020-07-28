/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2016-12-13 15:15:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_code`;
CREATE TABLE `t_code` (
  `code` int(11) NOT NULL,
  `isStock` char(1) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_code
-- ----------------------------

-- ----------------------------
-- Table structure for `t_daily`
-- ----------------------------
DROP TABLE IF EXISTS `t_daily`;
CREATE TABLE `t_daily` (
  `i_code` bigint(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `d_open` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `d_close` double DEFAULT NULL,
  `low` double DEFAULT NULL,
  `volume` bigint(11) DEFAULT NULL,
  `amount` bigint(11) DEFAULT NULL,
  `d_change` double(11,0) DEFAULT NULL,
  `valueChange` double DEFAULT NULL,
  `precentChange` double DEFAULT NULL,
  `ma5` double DEFAULT NULL,
  `ma10` double DEFAULT NULL,
  `ma20` double DEFAULT NULL,
  `vma5` double DEFAULT NULL,
  `vma10` double DEFAULT NULL,
  `vma20` double DEFAULT NULL,
  `turnover` double DEFAULT NULL,
  `s_md5` varchar(255) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `insert_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `k` double DEFAULT NULL,
  `d` double DEFAULT NULL,
  `j` double DEFAULT NULL,
  `rsi6` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pk_code` (`i_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3451219 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_daily
-- ----------------------------

ALTER TABLE `t_forecast`
ADD COLUMN `riseWindow`  int NULL AFTER `bullishCrossLine`;

ALTER TABLE `t_forecast`
ADD COLUMN `hammerLine`  int NULL AFTER `bullishCrossLine`;

ALTER TABLE `t_code`
ADD COLUMN `status`  int NULL AFTER `name`;

ALTER TABLE `t_forecast`
ADD COLUMN `forwardThreeSoldiers`  int NULL AFTER `hammerLine`;

ALTER TABLE `t_daily`
DROP COLUMN `k`,
DROP COLUMN `d`,
DROP COLUMN `j`,
DROP COLUMN `rsi6`;

ALTER TABLE `t_daily`
ADD COLUMN `boll_upper`  double NULL AFTER `insert_time`,
ADD COLUMN `boll_lower`  double NULL AFTER `boll_upper`;

ALTER TABLE `t_forecast`
ADD COLUMN `MA5MA10GoldenCross`  int NULL AFTER `md5`;

ALTER TABLE `t_forecast`
ADD COLUMN `MA5MA20GoldenCross`  int NULL AFTER `md5`;

ALTER TABLE `t_forecast`
ADD COLUMN `MA10MA20GoldenCross`  int NULL AFTER `md5`;

ALTER TABLE `t_daily`
ADD COLUMN `boll_percentB`  double NULL AFTER `boll_lower`;

ALTER TABLE `t_daily`
ADD COLUMN `wr10`  double NULL AFTER `boll_lower`;

ALTER TABLE `t_daily`
ADD COLUMN `wr21`  double NULL AFTER `boll_lower`;

ALTER TABLE `t_daily`
ADD COLUMN `wr42`  double NULL AFTER `boll_lower`;

ALTER TABLE `t_forecast`
ADD COLUMN `passThroughMiddle`  int NULL AFTER `MA5MA20GoldenCross`;

ALTER TABLE `t_forecast`
ADD COLUMN `downThroughLower`  int NULL AFTER `passThroughMiddle`;

ALTER TABLE `t_forecast`
ADD COLUMN `wrMtop`  int NULL AFTER `downThroughLower`;

ALTER TABLE `t_daily`
ADD COLUMN `ma30`  double NULL AFTER `ma20`;

ALTER TABLE `t_forecast`
CHANGE COLUMN `MA10MA20GoldenCross` `MA5MA30GoldenCross`  int(11) NULL DEFAULT NULL AFTER `md5`;















