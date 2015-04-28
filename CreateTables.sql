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
foreign key(University) references University(UName) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Review (
SID            INTEGER NOT NULL,
PID            varchar NOT NULL,
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


