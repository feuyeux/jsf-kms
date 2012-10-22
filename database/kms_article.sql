/*
Navicat PGSQL Data Transfer

Source Server         : localhost
Source Server Version : 90103
Source Host           : localhost:5432
Source Database       : postgres
Source Schema         : kms

Target Server Type    : PGSQL
Target Server Version : 90103
File Encoding         : 65001

Date: 2012-10-21 19:36:46
*/


-- ----------------------------
-- Table structure for "kms"."kms_article"
-- ----------------------------
DROP TABLE "kms"."kms_article";
CREATE TABLE "kms"."kms_article" (
"articleid" varchar(255) NOT NULL,
"attachment" varchar(255),
"content" text NOT NULL,
"inserttime" date NOT NULL,
"summary" varchar(255),
"title" varchar(255) NOT NULL,
"knowledgeid" varchar(255) NOT NULL,
"userid" varchar(255) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of kms_article
-- ----------------------------

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table "kms"."kms_article"
-- ----------------------------
ALTER TABLE "kms"."kms_article" ADD PRIMARY KEY ("articleid");

-- ----------------------------
-- Foreign Key structure for table "kms"."kms_article"
-- ----------------------------
ALTER TABLE "kms"."kms_article" ADD FOREIGN KEY ("knowledgeid") REFERENCES "kms"."kms_knowledge" ("knowledgeid") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "kms"."kms_article" ADD FOREIGN KEY ("userid") REFERENCES "kms"."kms_user" ("userid") ON DELETE NO ACTION ON UPDATE NO ACTION;
