/*
 Navicat Premium Data Transfer

 Source Server         : 维修
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : 8.137.19.4:3306
 Source Schema         : fault_system

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 12/06/2025 00:22:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for KnowledgeBase
-- ----------------------------
DROP TABLE IF EXISTS `KnowledgeBase`;
CREATE TABLE `KnowledgeBase`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '序号',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `download` bigint NULL DEFAULT NULL COMMENT '下载量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updata_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `updater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件储存路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1932084509723152387 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of KnowledgeBase
-- ----------------------------
INSERT INTO `KnowledgeBase` VALUES (1932782620460249089, '作业封面', 0, '2025-06-11 20:51:01', '2025-06-11 20:51:01', 'admin', 'admin', '川大课程作业封面.pdf', 'static\\knowledge川大课程作业封面.pdf');
INSERT INTO `KnowledgeBase` VALUES (1932782743856672770, '实验报告模板', 1, '2025-06-11 20:51:30', '2025-06-11 20:51:30', 'admin', 'admin', '实验报告模板.pdf', 'static\\knowledge实验报告模板.pdf');

-- ----------------------------
-- Table structure for accessory
-- ----------------------------
DROP TABLE IF EXISTS `accessory`;
CREATE TABLE `accessory`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `accessory_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配件名称',
  `accessory_code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配件编号',
  `used_num` int NULL DEFAULT 0 COMMENT '使用数量',
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_accCode`(`accessory_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '配件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of accessory
-- ----------------------------
INSERT INTO `accessory` VALUES (3, '7mm螺丝', 'A0000003', 53, 57);
INSERT INTO `accessory` VALUES (6, '12.9mm螺丝', 'A0000006', 0, 300);
INSERT INTO `accessory` VALUES (8, '10mm螺丝', 'A0000001', 0, 100);

-- ----------------------------
-- Table structure for accessory_use_history
-- ----------------------------
DROP TABLE IF EXISTS `accessory_use_history`;
CREATE TABLE `accessory_use_history`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `accessory_id` int NULL DEFAULT NULL COMMENT '配件id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '配件使用记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of accessory_use_history
-- ----------------------------
INSERT INTO `accessory_use_history` VALUES (1, 2, '2025-06-11 19:20:00');
INSERT INTO `accessory_use_history` VALUES (2, 2, '2025-06-11 19:20:14');
INSERT INTO `accessory_use_history` VALUES (3, 2, '2025-06-11 19:23:10');
INSERT INTO `accessory_use_history` VALUES (4, 2, '2025-06-11 19:25:56');
INSERT INTO `accessory_use_history` VALUES (5, 3, '2025-06-11 20:36:49');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` bigint UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('4030a4351e784378b37100719d5335ea', '软件故障', 1715222323099);
INSERT INTO `category` VALUES ('41c2e41916c64f9cacdd6539d970a576', '其他故障', 1715222390748);
INSERT INTO `category` VALUES ('e00dbf4848b64ee490d0019919e121ee', '网络故障', 1715258014572);
INSERT INTO `category` VALUES ('ec34f4290ba1467cb22de52384a65dbb', '硬件故障', 1715222378173);

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `type` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `location` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `status` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `user_ids` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `current` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `voltage` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` bigint UNSIGNED NULL DEFAULT NULL,
  `latitude` decimal(9, 6) NULL DEFAULT NULL COMMENT '设备纬度',
  `longitude` decimal(9, 6) NULL DEFAULT NULL COMMENT '设备经度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('214de4b8d7604ea0a7d765bdebe8c97f', '设备5', '正常', '陕西省西安市', '正常', 'aabd71e3858a4695abbd97689a8d37f4', '', NULL, 1715235994082, 34.341568, 108.940174);
INSERT INTO `equipment` VALUES ('69e54bb0b7f645be9de2a79f2ee8c01a', '设备4', '维修中', '四川省成都市', '维修中', '7c8cded43f284c51ab997123f7641059,8de85a4a8d974bb693cd63a50684ad58', '203', '182', 1715235990654, 30.572269, 104.066541);
INSERT INTO `equipment` VALUES ('e091fd8243924680977bba8b6e6e5c70', '设备2', '维修中', '上海市', '维修中', '7c8cded43f284c51ab997123f7641059,8de85a4a8d974bb693cd63a50684ad58,0b2d87b0115c4fbda71336b53b895fa4', '338', '169', 1715235979938, 31.230416, 121.473701);
INSERT INTO `equipment` VALUES ('e743e32ba8a8476fb264668b4256054d', '设备1', '机器状态良好', '北京市', '故障', '7c8cded43f284c51ab997123f7641059', '297', '153', 1715154111859, 39.904202, 116.407394);
INSERT INTO `equipment` VALUES ('e828d08ebb5a4d059f5ecc826fc59743', '设备3', '机器状态良好', '广东省广东市', '正常', '7c8cded43f284c51ab997123f7641059,8de85a4a8d974bb693cd63a50684ad58,0b2d87b0115c4fbda71336b53b895fa4,af7862ff8d54452fa781c5a0ee84537b', '312', '135', 1715235987201, 23.129110, 113.264385);

-- ----------------------------
-- Table structure for kb
-- ----------------------------
DROP TABLE IF EXISTS `kb`;
CREATE TABLE `kb`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件url',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `download_times` bigint NULL DEFAULT 0 COMMENT '下载数量',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上传用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '维修知识库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb
-- ----------------------------
INSERT INTO `kb` VALUES (5, '电脑维修改机教程', '623b35c7e7664bdeb0693dcb521b8b0f.pdf', '2025-06-09 20:55:47', '2025-06-09 20:55:47', 0, '专业维修师1');
INSERT INTO `kb` VALUES (6, '高级钳工教程', 'eb0910319cb6489dab399a40aa9e3d3f.pdf', '2025-06-09 20:56:33', '2025-06-09 20:56:33', 1, '专业维修师1');
INSERT INTO `kb` VALUES (7, '维修大全', '8c9b076c9fc944d98e7625435c007235.pdf', '2025-06-09 21:03:43', '2025-06-09 21:03:43', 2, '专业维修师1');
INSERT INTO `kb` VALUES (8, '大大大', '0964af2729ed468880871bccabc87f80.pdf', '2025-06-09 21:04:55', '2025-06-09 21:04:55', 7, '专业维修师1');
INSERT INTO `kb` VALUES (9, '111（2.0）', '1535d8d6d86d428593f0983548df0f9e.pdf', '2025-06-09 22:15:32', '2025-06-09 22:15:32', 1, '专业维修师1');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `solution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `duration` decimal(10, 2) NULL DEFAULT NULL,
  `start_date` date NULL DEFAULT NULL,
  `end_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, 'user:王俊豪实施方案V1.1_2018-07-16', '完成', 3.00, '2018-07-09', '2018-07-18');
INSERT INTO `log` VALUES (2, '工作流程表单调研方案_丞明_2018-07-10.xlsx', '完成', 0.50, '2018-07-11', '2018-07-11');

-- ----------------------------
-- Table structure for maintenance
-- ----------------------------
DROP TABLE IF EXISTS `maintenance`;
CREATE TABLE `maintenance`  (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `username` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `mobile` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `address` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `mailbox` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` bigint UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of maintenance
-- ----------------------------
INSERT INTO `maintenance` VALUES ('0b2d87b0115c4fbda71336b53b895fa4', '测试人员2', '13145623455', '北京市东城区', '1359566330@qq.com', 1715222304558);
INSERT INTO `maintenance` VALUES ('3e21f9d535934e13994c159268cb59e7', '测试维修人员', '13800000000', '是的是的所', 't18762405182@126.com', 1749452507474);
INSERT INTO `maintenance` VALUES ('7c8cded43f284c51ab997123f7641059', '测试人员4', '13772625214', '北京市东城区', '1359566330@qq.com', 1715222312005);
INSERT INTO `maintenance` VALUES ('8de85a4a8d974bb693cd63a50684ad58', '测试人员3', '13772625214', '北京市东城区', '1359566330@qq.com', 1715222308924);
INSERT INTO `maintenance` VALUES ('aabd71e3858a4695abbd97689a8d37f4', '测试维修人员2', '13772625214', '北京市东城区', '1359566330@qq.com', 1715257830483);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `equipment_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `equipment_name` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `category_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `category_name` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `maintenance_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `maintenance_name` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `cause` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` bigint UNSIGNED NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `start_time` bigint UNSIGNED NULL DEFAULT NULL COMMENT '任务开始时间（维修人员接单时间）',
  `finish_time` bigint UNSIGNED NULL DEFAULT NULL COMMENT '任务完成时间',
  `timeout_minutes` int NOT NULL DEFAULT 60 COMMENT '超时阈值（分钟）',
  `status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '待分配' COMMENT '任务状态',
  `emergency_level` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '普通' COMMENT '紧急程度，紧急=管理员发起，普通=用户发起',
  `evaluate_content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户评价内容，仅普通工单有',
  `evaluate_star` int NULL DEFAULT NULL COMMENT '用户评价星级，1~5星，仅普通工单有',
  `evaluate_time` bigint NULL DEFAULT NULL COMMENT '评价时间，仅普通工单有',
  `initiator_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发起人用户ID，普通工单绑定用户，紧急工单不绑定',
  `is_timeout` tinyint(1) NULL DEFAULT NULL COMMENT '是否超时，0-未超时，1-超时'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('record-002', 'e091fd8243924680977bba8b6e6e5c70', '设备2', '3008e9f97e114d44acdbb08d08a63cee', 'e00dbf4848b64ee490d0019919e121ee', '网络故障', '0b2d87b0115c4fbda71336b53b895fa4', '测试人员2', '测试人员2于2025-06-08维修了设备2机器，故障原因:网络中断待修复', 1748946345000, '044215e375224d9c9acaa7c7f5e22fa0.png', 1748948145000, 1749646078881, 60, '已完成', '普通', '', NULL, NULL, '3008e9f97e114d44acdbb08d08a63cee', 1);
INSERT INTO `record` VALUES ('record-003', '214de4b8d7604ea0a7d765bdebe8c97f', '设备5', '3008e9f97e114d44acdbb08d08a63cee', 'ec34f4290ba1467cb22de52384a65dbb', '硬件故障', 'aabd71e3858a4695abbd97689a8d37f4', '测试人员2', '硬盘更换完成', 1748946345000, '044215e375224d9c9acaa7c7f5e22fa0.png', 1748946945000, 1748949345000, 60, '已完成', '普通', '很差', 1, 1749628653694, '3008e9f97e114d44acdbb08d08a63cee', 1);
INSERT INTO `record` VALUES ('record-004', 'e828d08ebb5a4d059f5ecc826fc59743', '设备3', '3008e9f97e114d44acdbb08d08a63cee', '41c2e41916c64f9cacdd6539d970a576', '其他故障', '8de85a4a8d974bb693cd63a50684ad58', '测试人员3', '未知故障待排查', 1748946345000, 'pic-004.jpg', 1748944545000, NULL, 60, '进行中', '普通', '', NULL, NULL, '8e7a0d04d9a84b46b46ee615cf925d7c', NULL);
INSERT INTO `record` VALUES ('record-006', 'e828d08ebb5a4d059f5ecc826fc59743', '设备3', '3008e9f97e114d44acdbb08d08a63cee', 'ec34f4290ba1467cb22de52384a65dbb', '硬件故障', '7c8cded43f284c51ab997123f7641059', '测试人员4', '测试人员4于2025-06-09维修了设备3机器，故障原因:测试人员4于2025-06-09维修了设备3机器，故障原因:主板损坏待更换', 1748268100000, 'pic-006.jpg', 1748268100000, NULL, 30, '已完成', '普通', '差', 2, 1749628653694, '8e7a0d04d9a84b46b46ee615cf925d7c', 0);
INSERT INTO `record` VALUES ('record-007', 'e091fd8243924680977bba8b6e6e5c70', '设备2', '3008e9f97e114d44acdbb08d08a63cee', '41c2e41916c64f9cacdd6539d970a576', '其他故障', '8de85a4a8d974bb693cd63a50684ad58', '测试人员3', '漏电', 1748268100000, 'pic-007.jpg', 1748268100000, 1748949345000, 60, '已完成', '普通', NULL, NULL, NULL, '3008e9f97e114d44acdbb08d08a63cee', NULL);
INSERT INTO `record` VALUES ('record-005', 'e828d08ebb5a4d059f5ecc826fc59743', '设备3', '8e7a0d04d9a84b46b46ee615cf925d7c', 'ec34f4290ba1467cb22de52384a65dbb', '硬件故障', '8de85a4a8d974bb693cd63a50684ad58', '测试人员3', '测试人员3于2025-06-11维修了设备3机器，故障原因:', 1749374719248, '', 1749631768606, NULL, 60, '进行中', '普通', NULL, NULL, NULL, '3008e9f97e114d44acdbb08d08a63cee', NULL);
INSERT INTO `record` VALUES ('record-001', 'e743e32ba8a8476fb264668b4256054d', '设备1', '3008e9f97e114d44acdbb08d08a63cee', 'e00dbf4848b64ee490d0019919e121ee', '网络故障', '1d0a647506c94eb5b410977225376170', '专业维修师1', '专业维修师1于2025-06-11维修了设备1机器，故障原因:专业维修师1于2025-06-09维修了设备1机器，故障原因:1212', 1749438404340, '5964fbe822ea4f438e4e3927ef17c35c.jpg', 1748268100000, NULL, 60, '已完成', '普通', '', NULL, NULL, '3008e9f97e114d44acdbb08d08a63cee', NULL);
INSERT INTO `record` VALUES ('record-008', 'e091fd8243924680977bba8b6e6e5c70', '设备2', '3008e9f97e114d44acdbb08d08a63cee', 'e00dbf4848b64ee490d0019919e121ee', '其他故障', '0b2d87b0115c4fbda71336b53b895fa4', '测试人员2', '待排查', 1748268100000, '044215e375224d9c9acaa7c7f5e22fa0.png', 1748948145000, 1749644550981, 60, '进行中', '普通', '', NULL, NULL, '3008e9f97e114d44acdbb08d08a63cee', 1);

-- ----------------------------
-- Table structure for script
-- ----------------------------
DROP TABLE IF EXISTS `script`;
CREATE TABLE `script`  (
  `C1` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of script
-- ----------------------------
INSERT INTO `script` VALUES ('create table category');
INSERT INTO `script` VALUES ('(');
INSERT INTO `script` VALUES ('    id          varchar(32)     not null');
INSERT INTO `script` VALUES ('        primary key,');
INSERT INTO `script` VALUES ('    name        varchar(2000)   null,');
INSERT INTO `script` VALUES ('    create_time bigint unsigned null');
INSERT INTO `script` VALUES (')');
INSERT INTO `script` VALUES ('    charset = utf8mb3');
INSERT INTO `script` VALUES ('    row_format = DYNAMIC;');
INSERT INTO `script` VALUES (NULL);
INSERT INTO `script` VALUES ('create table equipment');
INSERT INTO `script` VALUES ('(');
INSERT INTO `script` VALUES ('    id          varchar(32)     not null');
INSERT INTO `script` VALUES ('        primary key,');
INSERT INTO `script` VALUES ('    name        varchar(2000)   null,');
INSERT INTO `script` VALUES ('    type        varchar(2000)   null,');
INSERT INTO `script` VALUES ('    location    varchar(2000)   null,');
INSERT INTO `script` VALUES ('    status      varchar(2000)   null,');
INSERT INTO `script` VALUES ('    user_ids    varchar(2000)   null,');
INSERT INTO `script` VALUES ('    current     varchar(2000)   null,');
INSERT INTO `script` VALUES ('    voltage     varchar(2000)   null,');
INSERT INTO `script` VALUES ('    create_time bigint unsigned null');
INSERT INTO `script` VALUES (')');
INSERT INTO `script` VALUES ('    charset = utf8mb3');
INSERT INTO `script` VALUES ('    row_format = DYNAMIC;');
INSERT INTO `script` VALUES (NULL);
INSERT INTO `script` VALUES ('create table maintenance');
INSERT INTO `script` VALUES ('(');
INSERT INTO `script` VALUES ('    id          varchar(32)     not null');
INSERT INTO `script` VALUES ('        primary key,');
INSERT INTO `script` VALUES ('    username    varchar(2000)   null,');
INSERT INTO `script` VALUES ('    mobile      varchar(2000)   null,');
INSERT INTO `script` VALUES ('    address     varchar(2000)   null,');
INSERT INTO `script` VALUES ('    mailbox     varchar(2000)   null,');
INSERT INTO `script` VALUES ('    create_time bigint unsigned null');
INSERT INTO `script` VALUES (')');
INSERT INTO `script` VALUES ('    charset = utf8mb3');
INSERT INTO `script` VALUES ('    row_format = DYNAMIC;');
INSERT INTO `script` VALUES (NULL);
INSERT INTO `script` VALUES ('create table record');
INSERT INTO `script` VALUES ('(');
INSERT INTO `script` VALUES ('    id               varchar(32)     not null');
INSERT INTO `script` VALUES ('        primary key,');
INSERT INTO `script` VALUES ('    equipment_id     varchar(32)     not null,');
INSERT INTO `script` VALUES ('    equipment_name   varchar(2000)   null,');
INSERT INTO `script` VALUES ('    user_id          varchar(32)     not null,');
INSERT INTO `script` VALUES ('    category_id      varchar(32)     null,');
INSERT INTO `script` VALUES ('    category_name    varchar(2000)   null,');
INSERT INTO `script` VALUES ('    maintenance_id   varchar(32)     null,');
INSERT INTO `script` VALUES ('    maintenance_name varchar(2000)   null,');
INSERT INTO `script` VALUES ('    cause            varchar(2000)   null,');
INSERT INTO `script` VALUES ('    create_time      bigint unsigned null,');
INSERT INTO `script` VALUES ('    pic              varchar(255)    null');
INSERT INTO `script` VALUES (')');
INSERT INTO `script` VALUES ('    charset = utf8mb3');
INSERT INTO `script` VALUES ('    row_format = DYNAMIC;');
INSERT INTO `script` VALUES (NULL);
INSERT INTO `script` VALUES ('create table user');
INSERT INTO `script` VALUES ('(');
INSERT INTO `script` VALUES ('    id          varchar(32)     not null');
INSERT INTO `script` VALUES ('        primary key,');
INSERT INTO `script` VALUES ('    username    varchar(2000)   null,');
INSERT INTO `script` VALUES ('    password    varchar(2000)   null,');
INSERT INTO `script` VALUES ('    identity    varchar(32)     not null,');
INSERT INTO `script` VALUES ('    create_time bigint unsigned null,');
INSERT INTO `script` VALUES ('    mobile      varchar(255)    null,');
INSERT INTO `script` VALUES ('    email       varchar(255)    null,');
INSERT INTO `script` VALUES ('    qq          varchar(255)    null,');
INSERT INTO `script` VALUES ('    nickname    varchar(255)    null');
INSERT INTO `script` VALUES (')');
INSERT INTO `script` VALUES ('    charset = utf8mb3');
INSERT INTO `script` VALUES ('    row_format = DYNAMIC;');
INSERT INTO `script` VALUES (NULL);
INSERT INTO `script` VALUES (NULL);

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `department` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `position` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `hire_date` date NULL DEFAULT NULL,
  `performance_score` int NULL DEFAULT 0,
  `attendance_rate` decimal(5, 2) NULL DEFAULT 100.00,
  `last_evaluation` date NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '在职',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `stars` int NULL DEFAULT NULL,
  `recs` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('03d244d7245b473b8ffce8f6f168366f', '测试维修人员2', '维修部', '开发工程师', '2025-06-11', 1, 1.00, NULL, '在职', '', 1, 1);
INSERT INTO `staff` VALUES ('1001', '测试维修人员', '技术部', '开发工程师', '2022-03-01', 52, 98.50, '2025-06-11', '在职', '表现优秀但是缺乏团队协作力\n', 2, 6);
INSERT INTO `staff` VALUES ('1002', '测试人员4', '测试部', '测试员', '2021-07-15', 53, 96.00, '2025-06-11', '离职', '需提升沟通能力', 8, 2);
INSERT INTO `staff` VALUES ('1003', '测试人员3', '测试部', '测试员', '2020-11-20', 92, 99.00, '2024-05-01', '在职', '领导能力突出', 8, 2);
INSERT INTO `staff` VALUES ('2e81df01bfb64811a933c76f4eab0b31', '测试人员2', '测试部', '测试员', '2025-06-12', 75, 1.00, '2025-06-11', '在职', '', 25, 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `username` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `password` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `identity` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_time` bigint UNSIGNED NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1d0a647506c94eb5b410977225376170', '专业维修师1', 'Tt123456#', '维修师', 1749432437310, '13000000000', 't18762405180@126.com', NULL, NULL);
INSERT INTO `user` VALUES ('2ddc3bc10bc0498dbe672eca232e1ae1', '管理员2', 'Qwer1234@', '管理员', 1715606352354, '13144444444', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('3008e9f97e114d44acdbb08d08a63cee', '李鹏飞', 'Qwer1234@', '用户', 1715253063512, '13773395697', '1359566330@qq.com', '1359566330', 'xxx');
INSERT INTO `user` VALUES ('416d37671d3f4e4789a3494e5209f721', 'root', 'Qwe123456@', '管理员', 1715152846246, '13772625214', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('56082b18a22241d48b6314346d960902', NULL, '1', '管理员', 1749480877530, '1', '1', NULL, NULL);
INSERT INTO `user` VALUES ('8e03690a48ef4ad2bc2acd90555d555c', NULL, '0d36cc2afd6f8c1a373e18e3c6c1b43e', 'root', 1749012686254, '15929804729', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('8e7a0d04d9a84b46b46ee615cf925d7c', NULL, 'Qwe123456@', '用户', 1715245206423, '13155555555', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('ad7700b921e34ea5b809a3ca8be27e53', '管理员1', 'Qwer1234@', '管理员', 1715605902184, '18651901230', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('b46f12a1a2ec49bcbf07fa83106c229a', NULL, '3642333Xt!', '维修主管', 1749040794816, '13739344792', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('b4d841d53af344ec9f639b80231a0cbc', NULL, '1234567890Aa@', '用户', 1749398640398, '13145623452', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('b4d841d53af344ec9f639b80231a0cbd', '专业维修师2', '1234567890Aa@', '维修师', 1749398640398, '13145623455', 't18762405180@126.com', '1359566330', 'xxx');

-- ----------------------------
-- Table structure for weather
-- ----------------------------
DROP TABLE IF EXISTS `weather`;
CREATE TABLE `weather`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `temperature` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `humidity` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `wind` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `creator_id` int NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id` ASC) USING BTREE,
  INDEX `city`(`city` ASC) USING BTREE,
  INDEX `temperature`(`temperature` ASC) USING BTREE,
  INDEX `humidity`(`humidity` ASC) USING BTREE,
  INDEX `wind`(`wind` ASC) USING BTREE,
  INDEX `creator_id`(`creator_id` ASC) USING BTREE,
  INDEX `creator`(`creator` ASC) USING BTREE,
  INDEX `create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of weather
-- ----------------------------
INSERT INTO `weather` VALUES (1, '西安', '26', '20', '25', 1, '2', '2025-05-28 22:09:13');

-- ----------------------------
-- Table structure for work_order
-- ----------------------------
DROP TABLE IF EXISTS `work_order`;
CREATE TABLE `work_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `device_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '处理中' COMMENT '处理中→已完成',
  `create_time` datetime NULL DEFAULT NULL,
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `photo_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '故障图片',
  `maintenance_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '维修人员id',
  `material_used` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '材料使用',
  `work_order_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '维修时间 yyyy-MM-dd',
  `check_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '验收说明',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '维修工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of work_order
-- ----------------------------
INSERT INTO `work_order` VALUES (4, 'e743e32ba8a8476fb264668b4256054d', '设备1', '处理中', '2025-06-09 15:31:29', '2025-06-09 18:22:58', '964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '螺丝钉脱落已修复', 'description');
INSERT INTO `work_order` VALUES (5, 'e743e32ba8a8476fb264668b4256054d', '设备1', '处理中', '2025-06-09 15:36:08', '2025-06-09 18:23:00', '5964fbe822ea4f438e4e3927ef17c35c.jpg', 'b4d841d53af344ec9f639b80231a0cbd', NULL, '2025-06-09', NULL, NULL);
INSERT INTO `work_order` VALUES (6, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 15:36:16', '2025-06-09 18:23:03', '5964fbe822ea4f438e4e3927ef17c35c.jpg', 'b4d841d53af344ec9f639b80231a0cbd', NULL, '2025-06-09', NULL, NULL);
INSERT INTO `work_order` VALUES (8, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:46:51', NULL, '5964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '464611', NULL);
INSERT INTO `work_order` VALUES (9, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:48:56', NULL, '5964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '辅导费', NULL);
INSERT INTO `work_order` VALUES (10, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:51:38', NULL, '5964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '辅导费地方 ', NULL);
INSERT INTO `work_order` VALUES (11, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:51:38', NULL, 'blob:http://localhost:8080/c90191ec-99b6-46dc-ac88-13c13eeab561', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '辅导费地方 ', '123123');
INSERT INTO `work_order` VALUES (12, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:51:38', NULL, '5964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '辅导费地方 ', '123465424');
INSERT INTO `work_order` VALUES (13, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:51:38', NULL, '5964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '辅导费地方 ', '234234234234');
INSERT INTO `work_order` VALUES (14, 'e743e32ba8a8476fb264668b4256054d', '设备1', '已完成', '2025-06-09 22:51:38', NULL, '5964fbe822ea4f438e4e3927ef17c35c.jpg', '1d0a647506c94eb5b410977225376170', NULL, '2025-06-09', '辅导费地方 ', '123123123123123');

SET FOREIGN_KEY_CHECKS = 1;
