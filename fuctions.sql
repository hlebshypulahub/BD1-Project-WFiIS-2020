CREATE OR REPLACE FUNCTION addFacility(varchar, facility_status)
    RETURNS BOOLEAN AS
$$
DECLARE
    nameAdd ALIAS FOR $1;
    statusAdd ALIAS FOR $2;
    existsCheck BOOLEAN;
    id          RECORD;
BEGIN
    SELECT EXISTS(SELECT f.name, fi.status
                  FROM facility f
                           JOIN facility_info fi ON f.id_facility = fi.id_facility
                  WHERE f.name = nameAdd
                    AND fi.status = statusAdd)
    INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN
        INSERT INTO facility (name) VALUES (nameAdd);
        SELECT id_facility INTO id FROM facility WHERE name = nameAdd;
        INSERT INTO facility_info (id_facility, status) VALUES (id.id_facility, statusAdd);
    ELSE
        RAISE EXCEPTION 'Obiekt o takim imieniu i statusie już istnieje!';
    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	



CREATE OR REPLACE FUNCTION addEmployee(firstNameAdd varchar, lastNameAdd varchar, usernameAdd varchar,
                                       passwordAdd varchar, roleAdd employee_role, dobAdd date, phoneAdd varchar,
                                       addressAdd varchar, positionAdd employee_position, categoryAdd employee_category,
                                       salaryAdd integer, ppeAdd date, courseHourseSumAdd integer)
    RETURNS BOOLEAN AS
$$
DECLARE
    existsCheck BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT ed.username
                  FROM employee_data ed
                  WHERE ed.username = usernameAdd)
    INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN

        WITH id
                 AS (INSERT INTO employee (last_name, first_name) VALUES (firstNameAdd, lastNameAdd) RETURNING id_employee)
        INSERT
        INTO employee_data (id_employee, username, password, role)
        SELECT id_employee, usernameAdd, passwordAdd, roleAdd
        FROM id;

        WITH id
                 AS (SELECT ed.id_employee
                     FROM employee_data ed WHERE ed.username = usernameAdd)
        INSERT
        INTO employee_info (id_employee, dob, phone, address, position, category, salary, ppe, course_hours_sum)
        SELECT id_employee,
               dobAdd,
               phoneAdd,
               addressAdd,
               positionAdd,
               categoryAdd,
               salaryAdd,
               ppeAdd,
               courseHourseSumAdd
        FROM id;

    ELSE
        RAISE EXCEPTION 'Nazwa użytkownika jest zajęta!';
    END IF;
    RETURN TRUE;
END ;
$$
    LANGUAGE 'plpgsql';
	
	
	
	

CREATE OR REPLACE FUNCTION addEmployeeToFacility(employeeId integer, facilityStatus facility_status, facilityName varchar)
    RETURNS BOOLEAN AS
$$
DECLARE
    existsCheck BOOLEAN;
    facilityId  INTEGER;
BEGIN
    SELECT (SELECT f.id_facility
    FROM facility f,
         facility_info fi
    WHERE f.name = facilityName
      AND fi.status = facilityStatus)
    INTO facilityId;
    SELECT EXISTS(SELECT ef.id_facility, ef.id_employee
                  FROM employee_facility ef
                  WHERE ef.id_facility = facilityId
                    AND ef.id_employee = employeeId)
    INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN
        INSERT INTO employee_facility (id_facility, id_employee) VALUES (facilityId, employeeId);
    ELSE
        RAISE EXCEPTION 'Ten pracownik już jest zarejestrowany na tym obiekcie!';
    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
CREATE OR REPLACE FUNCTION loginAndFillInfo(employeeUsername varchar, employeePassword varchar)
    RETURNS TABLE
            (
                id_emp    integer,
                role             employee_role,
                first_name       varchar,
                last_name        varchar,
                dob              date,
                category         employee_category,
                pos         employee_position,
                ppe              date,
                salary           integer,
                phone            varchar,
                address          varchar,
                course_hours_sum integer
            )
AS
$$
DECLARE
    existsCheck BOOLEAN;
    employeeId  INTEGER;
BEGIN
    SELECT EXISTS(SELECT id_employee
                  FROM employee_data
                  WHERE username = employeeUsername
                    AND password = employeePassword)
    INTO existsCheck;
    IF (SELECT existsCheck = TRUE) THEN
        SELECT (SELECT id_employee
                FROM employee_data
                WHERE username = employeeUsername
                  AND password = employeePassword)
        INTO employeeId;
        RETURN QUERY SELECT * FROM employee_data_view edv WHERE edv.id_employee = employeeId;
    ELSE
        RAISE EXCEPTION 'Sprawdź poprawność username lub password!';
    END IF;
END;
$$
	LANGUAGE 'plpgsql';
	
	
	
	
	
	
CREATE OR REPLACE FUNCTION addCheckup(inspectionIdAdd integer, questionAdd checkup_question, answerAdd checkup_answer, dateAdd date, employeeIdAdd integer, descriptionAdd varchar)
    RETURNS BOOLEAN AS
$$
BEGIN
    IF (answerAdd = 'Nie') THEN

        WITH checkupId
                 AS (INSERT INTO checkup (id_inspection, question, answer) VALUES (inspectionIdAdd, questionAdd, answerAdd) RETURNING id_checkup)
        INSERT
        INTO violation (id_employee, id_checkup, correction_term, description)
        SELECT employeeIdAdd, id_checkup, dateAdd, descriptionAdd
        FROM checkupId;

    ELSE

        INSERT INTO checkup (id_inspection, question, answer) VALUES (inspectionIdAdd, questionAdd, answerAdd);

    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
	
	
CREATE OR REPLACE FUNCTION addHolidayForFacility(facilityIdAdd integer, employeeIdAdd integer, holidayNameAdd holiday_name,
                                                 holidayDateAdd date, holidayProceedsAdd float)
    RETURNS BOOLEAN AS
$$
DECLARE
    holidayId integer;
BEGIN
    INSERT INTO holiday (date, name) VALUES (holidayDateAdd, holidayNameAdd) RETURNING id_holiday INTO holidayId;
    INSERT INTO employee_holiday (id_holiday, id_employee) VALUES (holidayId, employeeIdAdd);
    INSERT INTO facility_holiday (id_holiday, id_facility, proceeds) VALUES (holidayId, facilityIdAdd, holidayProceedsAdd);
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
	
CREATE OR REPLACE FUNCTION deleteEmployee(employeeId integer)
    RETURNS BOOLEAN AS
$$
DECLARE
    existsCheck BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT id_employee
                  FROM employee
                  WHERE id_employee = employeeId)
    INTO existsCheck;
    IF (SELECT existsCheck = TRUE) THEN

        DELETE FROM violation WHERE id_employee = employeeId OR id_checkup IN (SELECT checkup.id_checkup FROM checkup WHERE id_inspection IN (SELECT id_inspection FROM inspection WHERE id_employee = employeeId));
        DELETE FROM checkup WHERE id_inspection IN (SELECT id_inspection FROM inspection WHERE id_employee = employeeId);
        DELETE FROM inspection WHERE id_employee = employeeId;
        DELETE FROM employee_facility WHERE id_employee = employeeId;
        DELETE FROM employee_holiday WHERE id_employee = employeeId;
        DELETE FROM employee_data WHERE id_employee = employeeId;
        DELETE FROM employee_info WHERE id_employee = employeeId;
        DELETE FROM employee_course WHERE id_employee = employeeId;
        DELETE FROM employee WHERE id_employee = employeeId;

    ELSE
        RAISE EXCEPTION 'Pracownik już nie istnieje w bazie!';
    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
CREATE OR REPLACE FUNCTION addCourse(employeeIdAdd integer, courseNameAdd varchar, courseHoursAdd integer)
    RETURNS INTEGER AS
$$
DECLARE
    courseId INTEGER;
    hours INTEGER;
BEGIN
    INSERT INTO course (name) VALUES (courseNameAdd) RETURNING id_course INTO courseId;
    INSERT INTO course_info (id_course, hours) VALUES (courseId, courseHoursAdd);
    INSERT INTO employee_course (id_employee, id_course) VALUES (employeeIdAdd, courseId);
    SELECT course_hours_sum INTO hours FROM employee_info WHERE id_employee = employeeIdAdd;
    hours = hours + courseHoursAdd;
    UPDATE employee_info SET course_hours_sum = hours WHERE id_employee = employeeIdAdd;
    RETURN hours;
END;
$$
    LANGUAGE 'plpgsql';