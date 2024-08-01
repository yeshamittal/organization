CREATE SEQUENCE SEQ_EMPLOYEE
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE SEQ_DEPARTMENT
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE EMPLOYEES (
                        ID INT DEFAULT NEXTVAL('SEQ_EMPLOYEE') PRIMARY KEY,
                        FIRST_NAME VARCHAR(255) NOT NULL,
                        LAST_NAME VARCHAR(255) NOT NULL);

CREATE TABLE DEPARTMENTS (
                            ID INT DEFAULT NEXTVAL('SEQ_DEPARTMENT') PRIMARY KEY,
                            NAME VARCHAR(255) NOT NULL,
                            READ_ONLY BOOLEAN DEFAULT FALSE,
                            MANDATORY BOOLEAN DEFAULT FALSE
                         );

CREATE TABLE MAP_EMPLOYEE_DEPARTMENTS (
                                     ID_EMPLOYEE BIGINT,
                                     ID_DEPARTMENT BIGINT,
                                     PRIMARY KEY (ID_EMPLOYEE, ID_DEPARTMENT),
                                     FOREIGN KEY (ID_EMPLOYEE) REFERENCES EMPLOYEES(ID),
                                     FOREIGN KEY (ID_DEPARTMENT) REFERENCES DEPARTMENTS(ID)
                                  );
-- EMPLOYEE_ID_SEQ

