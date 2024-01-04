BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "rezervacije" (
	"id_rezervacije"	INTEGER,
	"id_termina"	INTEGER NOT NULL,
	"jmbg"	INTEGER,
	PRIMARY KEY("id_rezervacije" AUTOINCREMENT),
	FOREIGN KEY("jmbg") REFERENCES "osoba"("jmbg"),
	FOREIGN KEY("id_termina") REFERENCES "termini"("id_termina")
);
CREATE TABLE IF NOT EXISTS "osoba" (
	"jmbg"	TEXT,
	"ime"	TEXT NOT NULL,
	"prezime"	TEXT NOT NULL,
	"godina_rodjenja"	INTEGER NOT NULL,
	"kontakt_telefon"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"doktor"	INTEGER NOT NULL,
	PRIMARY KEY("jmbg")
);
CREATE TABLE IF NOT EXISTS "termini" (
	"id_termina"	INTEGER,
	"dan"	INTEGER NOT NULL,
	"mjesec"	INTEGER NOT NULL,
	"sati"	INTEGER NOT NULL,
	"minute"	INTEGER NOT NULL,
	"opis_pregleda"	TEXT,
	"rezervisano"	INTEGER NOT NULL,
	PRIMARY KEY("id_termina" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "nalaz" (
	"id_nalaza"	INTEGER NOT NULL,
	"jmbg_pacijenta"	TEXT NOT NULL,
	"text_nalaza"	TEXT NOT NULL,
	PRIMARY KEY("id_nalaza" AUTOINCREMENT),
	FOREIGN KEY("jmbg_pacijenta") REFERENCES "osoba"("jmbg")
);
COMMIT;
