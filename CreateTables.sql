CREATE TABLE Professor (
PID            INTEGER PRIMARY KEY,
PName        varchar NOT NULL,
ResearchArea         varchar,
Bio            varchar,
YearsWorked        INTEGER
);

CREATE TABLE Student(
SName    varchar NOT NULL,
Major        varchar,
Username    varchar UNIQUE,
University    varchar NOT NULL,
SID        INTEGER PRIMARY KEY,
foreign key(University) references University(UName) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Review (
SID            INTEGER NOT NULL,
PID            INTEGER NOT NULL,
Year            INTEGER NOT NULL,
CID            varchar NOT NULL,
Semester        varchar NOT NULL,
Engagement        INTEGER,
Fairness        INTEGER,
DifficultyWork        INTEGER,
EaseLearning        INTEGER,
TeachingStyle     INTEGER,
Comments        varchar,
primary key(Semester,Year,SID,PID,CID),
foreign key(SID) references Student(SID) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(PID) references Professor(PID) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(CID) references Course(CIdentifier) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Course (
CIdentifier        varchar not null,
CName        varchar NOT NULL,
University        varchar not null,
primary key(CIdentifier, University),
foreign key(University) references University(UName) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Teaches (
CNumber        varchar,
PID            INTEGER,
primary key(CNumber, PID),
foreign key(CNumber) references Course(CIdentifier) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(PID) references Professor(PID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE University (
UName    varchar NOT NULL,
primary key(UName)
);

INSERT INTO University values ('Miami University');
INSERT INTO University values('University of Kentucky');
INSERT INTO University values('Xavier University');

INSERT INTO Professor (PName, ResearchArea, Bio, YearsWorked) 
VALUES ('Mallory Ross', 'Artificial Intelligence', 'Graduated from WVU in 1991', 12);
INSERT INTO Professor (PName, ResearchArea, Bio, YearsWorked) 
VALUES ('Eric Smith', 'Big Data', 'Received his PhD from Harvard', 19);
INSERT INTO Professor (PName, ResearchArea, Bio, YearsWorked) 
VALUES ('Max Rowell', 'Computer Architecture', 'Researching at Miami University', 8);

INSERT INTO Student (SName, Major, Username, University)
VALUES ('Doug Blase', 'Sports Management', 'blasedd', 'University of Kentucky');
INSERT INTO Student (SName, Major, Username, University)
VALUES ('Samantha Wolf', 'Mathematics', 'wolfsp', 'Xavier University');
INSERT INTO Student (SName, Major, Username, University)
VALUES ('Brandon Sonoda', 'Psychology', 'sonodabe', 'Miami University');

INSERT INTO Course values('CSE 626', 'Computing in an Imaginary World', 'University of Kentucky');
INSERT INTO Course values('ISA 412', 'Advanced System Analytics and Stuff', 'Miami University');

INSERT INTO Teaches values('CSE 626', 2);
INSERT INTO Teaches values('ISA 412', 1);

INSERT INTO Review values(1, 2, 2015, 'CSE 626', 'Spring', 3, 1, 5, 2, 2, 'Hard class');
INSERT INTO Review values(3, 1, 2012, 'ISA 412', 'Fall', 2, 2, 4, 2, 1, 'Not my favorite class');



