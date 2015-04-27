CREATE TABLE Professor (
PID            INTEGER PRIMARY KEY,
PName        varchar NOT NULL,
ResearchArea         varchar,
Bio            varchar,
YearsWorked        varchar
);

CREATE TABLE Student(
SName    varchar NOT NULL,
Major        varchar,
Username    varchar UNIQUE,
University    varchar NOT NULL,
SID        INTEGER PRIMARY KEY,
foreign key(University) references University(UName) ON DELETE CASCADE
);

CREATE TABLE Review (
SID            INTEGER,
PID            varchar ,
Year            INTEGER,
CID            INTEGER ,
Semester        varchar,
Engagement        INTEGER,
Fairness        INTEGER,
DifficultyWork        INTEGER,
EaseLearning        INTEGER,
TeachingStyle     INTEGER,
Comments        varchar,
primary key(Semester,Year,SID,PID,CID),
foreign key(SID) references Student(SID) ON DELETE CASCADE,
foreign key(PID) references Professor(PID) ON DELETE CASCADE,
foreign key(CID) references Course(CNumber) ON DELETE CASCADE
);

CREATE TABLE Course (
CIdentifier        varchar,
CName        varchar NOT NULL,
University        varchar,
primary key(CIdentifier, University),
foreign key(University) references University(UName) ON DELETE CASCADE
);

CREATE TABLE Teaches (
CNumber        varchar,
PID            varchar,
primary key(CNumber, PID),
foreign key(CNumber) references Course(CIdentifier) ON DELETE CASCADE,
foreign key(PID) references Professor(PID) ON DELETE CASCADE
);

CREATE TABLE University (
UName    varchar NOT NULL,
primary key(UName)
);


