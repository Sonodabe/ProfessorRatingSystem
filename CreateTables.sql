CREATE TABLE Professor (
PID        varchar NOT NULL,
PName    varchar NOT NULL,
ResearchArea    varchar,
Bio        varchar,
YearsWorked        varchar,
primary key(PID)
);

CREATE TABLE Student(
SName    varchar NOT NULL,
Major    varchar,
Username    varchar UNIQUE,
University    varchar NOT NULL,
SID        INTEGER PRIMARY KEY,
foreign key(University) references University(UName) ON DELETE CASCADE
);

CREATE TABLE Review (
SID        INTEGER NOT NULL,
PID        varchar NOT NULL,
Year        INTEGER,
CID        INTEGER NOT NULL,
Semester    varchar NOT NULL,
Engagement    INTEGER,
Fairness    INTEGER,
DifficultyWork    INTEGER,
EaseLearning    INTEGER,
TeachingStyle    INTEGER,
Comments    varchar,
foreign key(SID) references Student(SID) ON DELETE CASCADE,
foreign key(PID) references Professor(PID) ON DELETE CASCADE,
foreign key(CID) references Course(CNumber) ON DELETE CASCADE,
primary key(Semester,Year,SID,PID,CID)
);

CREATE TABLE Course (
CIdentifier        varchar,
CName        varchar NOT NULL,
University    varchar NOT NULL,
foreign key(University) references University(UName) ON DELETE CASCADE,
primary key(CIdentifier, University)
);

CREATE TABLE Teaches (
CNumber        integer NOT NULL,
PID        varchar NOT NULL,
foreign key(CNumber) references Course(CIdentifier) ON DELETE CASCADE,
foreign key(PID) references Professor(PID) ON DELETE CASCADE
);

CREATE TABLE University (
UName    varchar NOT NULL,
primary key(UName)
);