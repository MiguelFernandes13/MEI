ALTER TABLE Game DROP CONSTRAINT FKGame89090;
ALTER TABLE Game DROP CONSTRAINT FKGame305333;
DROP TABLE IF EXISTS "User" CASCADE;
DROP TABLE IF EXISTS Game CASCADE;
DROP TABLE IF EXISTS Platform CASCADE;
CREATE TABLE "User" (ID SERIAL NOT NULL, Name varchar(255), Email varchar(255), Password varchar(255), PRIMARY KEY (ID));
CREATE TABLE Game (ID SERIAL NOT NULL, UserID int4 NOT NULL, PlatformID int4 NOT NULL, Name varchar(255), Year int4 NOT NULL, Price float8 NOT NULL, Description varchar(255), PRIMARY KEY (ID));
CREATE TABLE Platform (ID SERIAL NOT NULL, Name varchar(255), Year int4 NOT NULL, Description varchar(255), Manufacturer varchar(255), PRIMARY KEY (ID));
ALTER TABLE Game ADD CONSTRAINT FKGame89090 FOREIGN KEY (PlatformID) REFERENCES Platform (ID);
ALTER TABLE Game ADD CONSTRAINT FKGame305333 FOREIGN KEY (UserID) REFERENCES "User" (ID);
