drop DATABASE IF EXISTS `kms`;
create DATABASE `kms`;
use kms;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `kms_user`
-- ----------------------------
DROP TABLE IF EXISTS `kms_user`;
CREATE TABLE `kms_user` (
  `userId` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of kms_user
-- ----------------------------
INSERT INTO `kms_user` VALUES ('kms_user_1300000000000', 'admin', 'admin');

-- ----------------------------
-- Table structure for `kms_article`
-- ----------------------------
DROP TABLE IF EXISTS `kms_article`;
CREATE TABLE `kms_article` (
  `articleId` varchar(255) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `content` longtext NOT NULL,
  `insertTime` date NOT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `knowledgeId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  PRIMARY KEY (`articleId`),
  KEY `FK3A8232084F1AD8DD` (`knowledgeId`),
  KEY `FK3A82320812B27EB` (`userId`),
  CONSTRAINT `FK3A82320812B27EB` FOREIGN KEY (`userId`) REFERENCES `kms_user` (`userId`),
  CONSTRAINT `FK3A8232084F1AD8DD` FOREIGN KEY (`knowledgeId`) REFERENCES `kms_knowledge` (`knowledgeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `kms_book`
-- ----------------------------
DROP TABLE IF EXISTS `kms_book`;
CREATE TABLE `kms_book` (
  `bookId` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `bookname` varchar(255) DEFAULT NULL,
  `buyTime` date DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `press` varchar(255) DEFAULT NULL,
  `publishTime` date DEFAULT NULL,
  `knowledgeId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  PRIMARY KEY (`bookId`),
  KEY `FKBACB3F174F1AD8DD` (`knowledgeId`),
  KEY `FKBACB3F1712B27EB` (`userId`),
  CONSTRAINT `FKBACB3F1712B27EB` FOREIGN KEY (`userId`) REFERENCES `kms_user` (`userId`),
  CONSTRAINT `FKBACB3F174F1AD8DD` FOREIGN KEY (`knowledgeId`) REFERENCES `kms_knowledge` (`knowledgeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `kms_knowledge`
-- ----------------------------
DROP TABLE IF EXISTS `kms_knowledge`;
CREATE TABLE `kms_knowledge` (
  `knowledgeId` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `touchTime` date DEFAULT NULL,
  `userId` varchar(255) NOT NULL,
  PRIMARY KEY (`knowledgeId`),
  KEY `FKDDC5D11012B27EB` (`userId`),
  CONSTRAINT `FKDDC5D11012B27EB` FOREIGN KEY (`userId`) REFERENCES `kms_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;