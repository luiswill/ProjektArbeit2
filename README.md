# ProjektArbeit2
Information Retrieval Java programm Projektarbeit 2

Aufgabe 2.1 .version Kommando
----
SQLite 3.18.0 2017-03-28 18:48:43 424a0d380332858ee55bdebc4af3789f74e70a2b3ba1cf29d84b9b4bcf3e2e37

Aufgabe 2.2 Create Kommandos
----
CREATE TABLE DOCS(
DID INTEGER PRIMARY KEY NOT NULL,
TITLE TEXT NOT NULL,
URL TEXT NOT NULL);

CREATE TABLE TFS(
ID INTEGER NOT NULL,
TERM TEXT,
TF INTEGER);
