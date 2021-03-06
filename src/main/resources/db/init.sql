-- 用户
CREATE TABLE `users` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR (200) DEFAULT NULL,
	`loginName` VARCHAR (200) DEFAULT NULL,
	`password` VARCHAR (200) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 初始化用户信息
INSERT into `users` VALUES (1 , "管理员" , "admin" , "123456");
INSERT into `users` VALUES (2 , "用户1" , "test" , "123456");

-- 摄像机信息表
CREATE TABLE `camera` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`code` VARCHAR (255) DEFAULT NULL,
	`name` VARCHAR (255) DEFAULT NULL,
	`longitude` double(26,16) DEFAULT NULL,
	`latitude` double(26,16)  DEFAULT NULL,
	`localtion` VARCHAR (255) DEFAULT NULL,
	`toward` VARCHAR (255) DEFAULT NULL,
	`type` VARCHAR (255) DEFAULT NULL,
	`rodHeight` double(6,2) DEFAULT NULL,
	`rodLength` double(6,2) DEFAULT NULL,
	`remark` VARCHAR(1000) DEFAULT NULL,
	`userId` INT (11) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 图片信息
CREATE TABLE `camera_img` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`cameraId` INT (11) DEFAULT NULL,
	`img` VARCHAR (255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;