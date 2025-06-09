CREATE DATABASE IF NOT EXISTS fault_system;
use fault_system;
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
`id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`create_time` bigint(11) UNSIGNED NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
`id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`type` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`location` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`status` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`user_ids` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`current` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`voltage` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`create_time` bigint(11) UNSIGNED NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `maintenance`;
CREATE TABLE `maintenance` (
`id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`username` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`mobile` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`address` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`mailbox` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`create_time` bigint(11) UNSIGNED NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
`id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`equipment_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`equipment_name` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`category_name` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`maintenance_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`maintenance_name` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`cause` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`create_time` bigint(11) UNSIGNED NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`username` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`password` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`identity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`create_time` bigint(11) UNSIGNED NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


create user 'fault_system'@'%' identified by '123456';
grant all privileges on fault_system.* to 'fault_system'@'%';
flush privileges;