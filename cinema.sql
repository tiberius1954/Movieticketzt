BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "billhead" (
	"bid"	INTEGER,
	"cid"	INTEGER,
	"bdate"	TEXT,
	"total"	INTEGER,
	"billnumber"	TEXT,
	"showid"	INTEGER,
	PRIMARY KEY("bid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "billitem" (
	"itemid"	INTEGER,
	"seatno"	TEXT,
	"price"	TEXT,
	"bid"	INTEGER,
	PRIMARY KEY("itemid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "customer" (
	"cid"	INTEGER,
	"name"	TEXT,
	"phone"	TEXT,
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "movies" (
	"movieid"	INTEGER,
	"title"	TEXT DEFAULT ' ',
	"genre"	TEXT DEFAULT ' ',
	"year"	INTEGER DEFAULT ' ',
	"duration"	TEXT DEFAULT ' ',
	"poster"	TEXT DEFAULT '  ',
	"video"	TEXT DEFAULT ' ',
	PRIMARY KEY("movieid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "params" (
	"parid"	INTEGER,
	"name"	REAL DEFAULT ' ',
	"postal_code"	NUMERIC DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	"city"	TEXT DEFAULT ' ',
	"country"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"billnumber"	INTEGER DEFAULT 0,
	"prefix"	TEXT DEFAULT ' ',
	"currentyear"	TEXT DEFAULT ' ',
	PRIMARY KEY("parid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "prices" (
	"pid"	INTEGER,
	"price"	INTEGER,
	"movieid"	INTEGER,
	PRIMARY KEY("pid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "seatreservation" (
	"seatid"	INTEGER,
	"seatno"	TEXT,
	"reserved"	TEXT DEFAULT ' ',
	"showid"	INTEGER,
	"price"	INTEGER,
	"cid"	INTEGER,
	"date"	TEXT DEFAULT ' ',
	PRIMARY KEY("seatid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "shows" (
	"show_id"	INTEGER,
	"movieid"	INTEGER DEFAULT 0,
	"sdate"	TEXT DEFAULT ' ',
	"stime"	TEXT DEFAULT ' ',
	"status"	TEXT,
	PRIMARY KEY("show_id" AUTOINCREMENT)
);
INSERT INTO "billhead" ("bid","cid","bdate","total","billnumber","showid") VALUES (41,7,'2024-11-27',120,'CUR00002  ',3);
INSERT INTO "billhead" ("bid","cid","bdate","total","billnumber","showid") VALUES (42,8,'2024-11-27',120,'CUR00003  ',3);
INSERT INTO "billhead" ("bid","cid","bdate","total","billnumber","showid") VALUES (43,9,'2024-11-28',120,'CUR00004  ',3);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (117,'E11','30',38);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (118,'E10','30',38);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (119,'D9','30',38);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (120,'null','null',38);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (121,'G11','30',39);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (122,'G12','30',39);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (123,'G14','30',39);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (124,'null','null',39);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (125,'G1','30',40);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (126,'G2','30',40);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (127,'G4','30',40);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (128,'A1','30',41);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (129,'A2','30',41);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (130,'A3','30',41);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (131,'A4','30',41);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (132,'F11','30',42);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (133,'F12','30',42);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (134,'F13','30',42);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (135,'F14','30',42);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (136,'F1','30',43);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (137,'F2','30',43);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (138,'F3','30',43);
INSERT INTO "billitem" ("itemid","seatno","price","bid") VALUES (139,'F4','30',43);
INSERT INTO "customer" ("cid","name","phone") VALUES (7,'John Puskas','20111111');
INSERT INTO "customer" ("cid","name","phone") VALUES (8,'Alfred Nobel','20333333');
INSERT INTO "customer" ("cid","name","phone") VALUES (9,'Henry Ford','20444444');
INSERT INTO "movies" ("movieid","title","genre","year","duration","poster","video") VALUES (1,'Amazon','Drama',1990,'2','poster','video');
INSERT INTO "movies" ("movieid","title","genre","year","duration","poster","video") VALUES (2,'Titanic','Drama',1998,'2','','');
INSERT INTO "movies" ("movieid","title","genre","year","duration","poster","video") VALUES (3,'Love story','Drama',2000,'2','','');
INSERT INTO "params" ("parid","name","postal_code","address","city","country","phone","email","billnumber","prefix","currentyear") VALUES (1,'Curtain Call Cinema','W1D 7AG','25-29 Coventry Street Piccadilly','London',' ','655/123-1234','myfirm@gmail.com',4,'CUR','2024 ');
INSERT INTO "prices" ("pid","price","movieid") VALUES (1,30,2);
INSERT INTO "prices" ("pid","price","movieid") VALUES (2,50,1);
INSERT INTO "prices" ("pid","price","movieid") VALUES (3,80,3);
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (191,'A1','yes',3,30,7,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (192,'A2','yes',3,30,7,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (193,'A3','yes',3,30,7,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (194,'A4','yes',3,30,7,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (195,'F11','yes',3,30,8,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (196,'F12','yes',3,30,8,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (197,'F13','yes',3,30,8,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (198,'F14','yes',3,30,8,'2024-11-27');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (199,'F1','yes',3,30,9,'2024-11-28');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (200,'F2','yes',3,30,9,'2024-11-28');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (201,'F3','yes',3,30,9,'2024-11-28');
INSERT INTO "seatreservation" ("seatid","seatno","reserved","showid","price","cid","date") VALUES (202,'F4','yes',3,30,9,'2024-11-28');
INSERT INTO "shows" ("show_id","movieid","sdate","stime","status") VALUES (2,1,'2024-11-08','06:00',NULL);
INSERT INTO "shows" ("show_id","movieid","sdate","stime","status") VALUES (3,2,'2024-11-10','20:00',NULL);
INSERT INTO "shows" ("show_id","movieid","sdate","stime","status") VALUES (4,2,'2024-11-10','18:00',NULL);
COMMIT;
