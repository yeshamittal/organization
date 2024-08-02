INSERT INTO EMPLOYEES (NAME_FIRST,
                       NAME_LAST)
VALUES('Djokovic', 'Serbia');

INSERT INTO EMPLOYEES (NAME_FIRST,
                       NAME_LAST)
VALUES('Monfils', 'France');


INSERT INTO DEPARTMENTS (NAME,
                       READ_ONLY, MANDATORY)
VALUES('Organization', true, true);

INSERT INTO DEPARTMENTS (NAME,
                         READ_ONLY, MANDATORY)
VALUES('Marketing', false, false);

INSERT INTO DEPARTMENTS (NAME,
                         READ_ONLY, MANDATORY)
VALUES('Sales', false, false);