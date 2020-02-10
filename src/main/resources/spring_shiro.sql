/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : spring_shiro

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 10/02/2020 15:25:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(1) NOT NULL,
  `parent_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '菜单', '/menu', 1, 0);
INSERT INTO `permission` VALUES (2, '文章管理', '', 1, 1);
INSERT INTO `permission` VALUES (3, '用户管理', '', 1, 1);
INSERT INTO `permission` VALUES (21, '添加文章', '/article/add', 1, 2);
INSERT INTO `permission` VALUES (22, '修改文章', '/article/update', 1, 2);
INSERT INTO `permission` VALUES (23, '删除文章', '/article/delete', 1, 2);
INSERT INTO `permission` VALUES (24, '添加文章-btn', '/article/add/btn', 2, 21);
INSERT INTO `permission` VALUES (25, '修改文章-btn', '/article/updete/btn', 2, 2);
INSERT INTO `permission` VALUES (31, '用户列表', '/user/list', 1, 3);
INSERT INTO `permission` VALUES (32, '删除用户', '/user/delete', 1, 3);
INSERT INTO `permission` VALUES (33, '删除用户-btn', '/user/list/btn', 2, 32);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员');
INSERT INTO `role` VALUES (2, 'author', '作者');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) NULL DEFAULT NULL,
  `permission_id` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 21);
INSERT INTO `role_permission` VALUES (5, 1, 22);
INSERT INTO `role_permission` VALUES (6, 1, 23);
INSERT INTO `role_permission` VALUES (7, 1, 31);
INSERT INTO `role_permission` VALUES (8, 1, 32);
INSERT INTO `role_permission` VALUES (20, 2, 1);
INSERT INTO `role_permission` VALUES (21, 2, 2);
INSERT INTO `role_permission` VALUES (22, 2, 3);
INSERT INTO `role_permission` VALUES (23, 2, 21);
INSERT INTO `role_permission` VALUES (24, 2, 22);
INSERT INTO `role_permission` VALUES (25, 2, 23);
INSERT INTO `role_permission` VALUES (26, 1, 24);
INSERT INTO `role_permission` VALUES (27, 1, 25);
INSERT INTO `role_permission` VALUES (28, 1, 33);
INSERT INTO `role_permission` VALUES (29, 2, 24);
INSERT INTO `role_permission` VALUES (30, 2, 25);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'chy', '057b8a9cf4bf877ec96a759e409a3315', 'e49937fba63b0ee52a4d15552bd9f327');
INSERT INTO `user` VALUES (2, 'user', 'a39808b8136d3f5f17ecb20de04d44f4', '5efd412c48bfd029a84df48dbf28be54');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (5, 8, 2);

SET FOREIGN_KEY_CHECKS = 1;
