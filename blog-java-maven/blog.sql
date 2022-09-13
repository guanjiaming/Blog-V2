SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
                          `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                          `create_time` datetime(0) NOT NULL COMMENT '添加时间',
                          `update_time` datetime(0) NOT NULL COMMENT '最后修改时间',
                          `user_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表，一个标签可以被多个博客引用，一个博客也可以引用多个标签，多对多关系' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
                      `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                      `content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                      `create_time` datetime(0) NOT NULL COMMENT '添加时间',
                      `update_time` datetime(0) NOT NULL COMMENT '最后修改时间',
                      `user_id` bigint DEFAULT NULL,
                      PRIMARY KEY (`id`),
                      KEY `user_id` (`user_id`),
                      CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;;

DROP TABLE IF EXISTS `tb_tag_article`;
CREATE TABLE `tb_tag_article`  (
                                `tag_id` bigint(20) NOT NULL COMMENT '标签id',
                                `article_id` bigint(20) NOT NULL COMMENT '博客id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签和博客的中间表，两者是多对多关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                            `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
                            `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储',
                            `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
                            `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                            `update_time` datetime(0) NOT NULL COMMENT '更新时间',
                            `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码加密的salt值',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (58, 'uncleming', '小明', 'd4dd738b701ebfa767083bd84f5c48d0', '', NOW(),NOW(), 'd4dd738b701ebfa767083bd84f5c48d0');
INSERT INTO `tb_user` VALUES (null, 'admin', '管理员', '38a29d1c139396f5d81848e3182f0fb7', '', NOW(),NOW(), '38a29d1c139396f5d81848e3182f0fb7');

SET FOREIGN_KEY_CHECKS = 1;
