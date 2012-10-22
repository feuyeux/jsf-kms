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

Date: 2012-10-21 19:37:53
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
-- Table structure for "kms"."kms_book"
-- ----------------------------
DROP TABLE "kms"."kms_book";
CREATE TABLE "kms"."kms_book" (
"bookid" varchar(255) NOT NULL,
"author" varchar(255),
"bookname" varchar(255),
"buytime" date,
"location" varchar(255),
"press" varchar(255),
"publishtime" date,
"knowledgeid" varchar(255) NOT NULL,
"userid" varchar(255) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of kms_book
-- ----------------------------

-- ----------------------------
-- Table structure for "kms"."kms_knowledge"
-- ----------------------------
DROP TABLE "kms"."kms_knowledge";
CREATE TABLE "kms"."kms_knowledge" (
"knowledgeid" varchar(255) NOT NULL,
"description" varchar(255),
"name" varchar(255),
"touchtime" date,
"userid" varchar(255) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of kms_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for "kms"."kms_user"
-- ----------------------------
DROP TABLE "kms"."kms_user";
CREATE TABLE "kms"."kms_user" (
"userid" varchar(255) NOT NULL,
"password" varchar(255),
"username" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of kms_user
-- ----------------------------
INSERT INTO "kms"."kms_user" VALUES ('kms_user_1350818969747', 'admin', 'admin');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table "kms"."kms_article"
-- ----------------------------
ALTER TABLE "kms"."kms_article" ADD PRIMARY KEY ("articleid");

-- ----------------------------
-- Primary Key structure for table "kms"."kms_book"
-- ----------------------------
ALTER TABLE "kms"."kms_book" ADD PRIMARY KEY ("bookid");

-- ----------------------------
-- Primary Key structure for table "kms"."kms_knowledge"
-- ----------------------------
ALTER TABLE "kms"."kms_knowledge" ADD PRIMARY KEY ("knowledgeid");

-- ----------------------------
-- Primary Key structure for table "kms"."kms_user"
-- ----------------------------
ALTER TABLE "kms"."kms_user" ADD PRIMARY KEY ("userid");

-- ----------------------------
-- Foreign Key structure for table "kms"."kms_article"
-- ----------------------------
ALTER TABLE "kms"."kms_article" ADD FOREIGN KEY ("knowledgeid") REFERENCES "kms"."kms_knowledge" ("knowledgeid") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "kms"."kms_article" ADD FOREIGN KEY ("userid") REFERENCES "kms"."kms_user" ("userid") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "kms"."kms_book"
-- ----------------------------
ALTER TABLE "kms"."kms_book" ADD FOREIGN KEY ("userid") REFERENCES "kms"."kms_user" ("userid") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "kms"."kms_book" ADD FOREIGN KEY ("knowledgeid") REFERENCES "kms"."kms_knowledge" ("knowledgeid") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "kms"."kms_knowledge"
-- ----------------------------
ALTER TABLE "kms"."kms_knowledge" ADD FOREIGN KEY ("userid") REFERENCES "kms"."kms_user" ("userid") ON DELETE NO ACTION ON UPDATE NO ACTION;
