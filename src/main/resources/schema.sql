CREATE SEQUENCE EMPLOYEE_ID_SEQ
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE DEPARTMENT_ID_SEQ
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE EMPLOYEES (
                        ID INT DEFAULT NEXTVAL('EMPLOYEE_ID_SEQ') PRIMARY KEY,
                        FIRST_NAME VARCHAR(255) NOT NULL,
                        LAST_NAME VARCHAR(255) NOT NULL);

CREATE TABLE DEPARTMENTS (
                            ID INT DEFAULT NEXTVAL('DEPARTMENT_ID_SEQ') PRIMARY KEY,
                            NAME VARCHAR(255) NOT NULL,
                            READ_ONLY BOOLEAN DEFAULT FALSE,
                            MANDATORY BOOLEAN DEFAULT FALSE
                         );

CREATE TABLE EMPLOYEE_DEPARTMENTS (
                                     EMPLOYEE_ID BIGINT,
                                     DEPARTMENT_ID BIGINT,
                                     PRIMARY KEY (EMPLOYEE_ID, DEPARTMENT_ID),
                                     FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEES(ID),
                                     FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENTS(ID)
                                  );


