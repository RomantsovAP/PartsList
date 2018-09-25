DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

USE test;

DROP TABLE IF EXISTS part;

CREATE TABLE part 
(
	id INT(8) NOT NULL AUTO_INCREMENT,
	title VARCHAR(50),
    enabled boolean,
	Amount INT,
	PRIMARY KEY (`id`)
);


INSERT INTO part  VALUES 
(1,"Motherboard",TRUE,10),
(2,"CPU",TRUE,10),
(3,"GPU",false,10),
(4,"RAM",TRUE,10),
(5,"HDD",false,10),
(6,"SDD",TRUE,10),
(7,"FDD",false,10),
(8,"Soundcard",false,10),
(9,"Keyboard",TRUE,10),
(10,"Mouse",TRUE,10),
(11,"TrackBall",false,10),
(12,"Monitor",true,10),
(13,"Speakers",false,10),
(14,"USB-Hub",false,10),
(15,"PSU",TRUE,10),
(16,"ComputerCase",TRUE,10),
(17,"ExternalHDD",false,10),
(18,"USBFlashDrive",false,10),
(19,"CPUColler",TRUE,10),
(20,"CardReader",false,10),
(21,"DVDDrive",false,10),
(22,"BlueRayDrive",false,10),
(23,"HeadSet",false,10),
(24,"SATACable",false,10),
(25,"HDMICable",false,10),
(26,"VGACable",TRUE,10),
(27,"PowerCable",TRUE,10),
(28,"USBController",false,10),
(29,"COMPortController",false,10),
(30,"WEBCamera",false,10),
(31,"WiFiRouter",false,10);


