ALTER TABLE Game DROP CONSTRAINT FKGame615817;
ALTER TABLE Game DROP CONSTRAINT FKGame992736;
DROP TABLE IF EXISTS "User" CASCADE;
DROP TABLE IF EXISTS Game CASCADE;
DROP TABLE IF EXISTS Platform CASCADE;
CREATE TABLE "User" (Attribute SERIAL NOT NULL, ID int4 NOT NULL, Name varchar(255), Email varchar(255), Password varchar(255), PRIMARY KEY (Attribute));
CREATE TABLE Game (Attribute SERIAL NOT NULL, UserAttribute int4 NOT NULL, PlatformAttribute int4 NOT NULL, ID int4 NOT NULL, Name varchar(255), Year int4 NOT NULL, Price float8 NOT NULL, Description varchar(255), PRIMARY KEY (Attribute));
CREATE TABLE Platform (Attribute SERIAL NOT NULL, ID int4 NOT NULL, Name varchar(255), Year int4 NOT NULL, Description varchar(255), Manufacturer varchar(255), PRIMARY KEY (Attribute));
ALTER TABLE Game ADD CONSTRAINT FKGame615817 FOREIGN KEY (PlatformAttribute) REFERENCES Platform (Attribute);
ALTER TABLE Game ADD CONSTRAINT FKGame992736 FOREIGN KEY (UserAttribute) REFERENCES "User" (Attribute);
