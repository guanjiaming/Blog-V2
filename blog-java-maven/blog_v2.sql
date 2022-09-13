/*
 Navicat Premium Data Transfer

 Source Server         : sql
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : blog_v2

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 25/04/2022 21:25:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `user_id` bigint(20) DEFAULT NULL,
  `views` bigint(10) NOT NULL,
  `is_recommend` bit(1) NOT NULL,
  `is_publish` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文章表';

-- ----------------------------
-- Records of tb_article
-- ----------------------------
BEGIN;
INSERT INTO `tb_article` VALUES (3, 'sql迁移', '<div data-language=\"shell\" class=\"toastui-editor-ww-code-block\"><pre><code data-language=\"shell\">mysql -u root -p\nEnter password: ******\n show databases\n use blog\n source /usr/local/blog.sql\n quit\n Bye\n </code></pre></div>', '2022-02-07 20:10:30', '2022-02-07 20:10:30', 88, 0, b'0', b'1');
INSERT INTO `tb_article` VALUES (4, 'prism测试', '<div data-language=\"text\" class=\"toastui-editor-ww-code-block\"><pre><code>String str = \"Uncle Mine\";\ndouble d = 3.14;\nint i = 9;</code></pre></div>', '2022-02-28 21:22:31', '2022-04-09 15:34:56', 88, 0, b'0', b'1');
COMMIT;

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标签表，一个标签可以被多个博客引用，一个博客也可以引用多个标签，多对多关系';

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
BEGIN;
INSERT INTO `tb_tag` VALUES (3, 'HTML', '2022-02-07 19:54:41', '2022-02-07 19:54:41', 88);
INSERT INTO `tb_tag` VALUES (4, 'CSS3', '2022-02-07 19:54:49', '2022-02-07 19:54:49', 88);
INSERT INTO `tb_tag` VALUES (5, 'JavaScript', '2022-02-07 19:55:37', '2022-02-07 19:55:46', 88);
INSERT INTO `tb_tag` VALUES (6, 'Node', '2022-02-07 19:56:02', '2022-02-07 19:56:02', 88);
INSERT INTO `tb_tag` VALUES (7, 'Java', '2022-02-07 19:56:09', '2022-02-07 19:56:09', 88);
INSERT INTO `tb_tag` VALUES (8, 'Springboot', '2022-02-07 19:56:18', '2022-02-07 19:56:18', 88);
INSERT INTO `tb_tag` VALUES (9, 'MySql', '2022-02-07 19:56:33', '2022-02-07 19:56:33', 88);
INSERT INTO `tb_tag` VALUES (10, 'a', '2022-02-22 21:47:51', '2022-02-22 21:47:51', 88);
INSERT INTO `tb_tag` VALUES (11, 'aa', '2022-02-28 20:01:18', '2022-02-28 20:01:18', 88);
INSERT INTO `tb_tag` VALUES (12, 'gjm', '2022-02-28 20:20:49', '2022-02-28 20:20:49', 88);
COMMIT;

-- ----------------------------
-- Table structure for tb_tag_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag_article`;
CREATE TABLE `tb_tag_article` (
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  `article_id` bigint(20) NOT NULL COMMENT '博客id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标签和博客的中间表，两者是多对多关系';

-- ----------------------------
-- Records of tb_tag_article
-- ----------------------------
BEGIN;
INSERT INTO `tb_tag_article` VALUES (9, 3);
INSERT INTO `tb_tag_article` VALUES (4, 4);
INSERT INTO `tb_tag_article` VALUES (3, 4);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码加密的salt值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (2, 'admin', '管理员', '202cb962ac59075b964b07152d234b70', '', '2022-02-11 21:35:12', '2022-02-11 21:35:16', '123');
INSERT INTO `tb_user` VALUES (88, 'uncleming', '小明', 'd4dd738b701ebfa767083bd84f5c48d0', '', '2022-02-07 19:51:28', '2022-02-07 19:51:28', 'd4dd738b701ebfa767083bd84f5c48d0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
