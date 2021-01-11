
CREATE SEQUENCE public.supplier_id_supplier_seq;

CREATE TABLE public.supplier (
                id_supplier INTEGER NOT NULL DEFAULT nextval('public.supplier_id_supplier_seq'),
                name VARCHAR(50),
                CONSTRAINT supplier_pk PRIMARY KEY (id_supplier)
);


ALTER SEQUENCE public.supplier_id_supplier_seq OWNED BY public.supplier.id_supplier;

CREATE SEQUENCE public.storage_id_storage_seq;

CREATE TABLE public.storage (
                id_storage INTEGER NOT NULL DEFAULT nextval('public.storage_id_storage_seq'),
                supplier INTEGER NOT NULL,
                name VARCHAR(30),
                email VARCHAR(30),
                CONSTRAINT storage_pk PRIMARY KEY (id_storage)
);


ALTER SEQUENCE public.storage_id_storage_seq OWNED BY public.storage.id_storage;

CREATE SEQUENCE public.storage_phone_id_phone_seq;

CREATE TABLE public.storage_phone (
                id_phone INTEGER NOT NULL DEFAULT nextval('public.storage_phone_id_phone_seq'),
                id_storage INTEGER NOT NULL,
                number VARCHAR(20),
                CONSTRAINT storage_phone_pk PRIMARY KEY (id_phone)
);


ALTER SEQUENCE public.storage_phone_id_phone_seq OWNED BY public.storage_phone.id_phone;

CREATE SEQUENCE public.supplier_employee_id_employee_seq;

CREATE TABLE public.supplier_employee (
                id_employee INTEGER NOT NULL DEFAULT nextval('public.supplier_employee_id_employee_seq'),
                supplier INTEGER NOT NULL,
                first_name VARCHAR(20) NOT NULL,
                last_name VARCHAR(20) NOT NULL,
                position employee_position,
                email VARCHAR(30),
                CONSTRAINT supplier_employee_pk PRIMARY KEY (id_employee)
);


ALTER SEQUENCE public.supplier_employee_id_employee_seq OWNED BY public.supplier_employee.id_employee;

CREATE SEQUENCE public.supplier_employee_phone_id_phone_seq;

CREATE TABLE public.supplier_employee_phone (
                id_phone INTEGER NOT NULL DEFAULT nextval('public.supplier_employee_phone_id_phone_seq'),
                id_employee INTEGER NOT NULL,
                number VARCHAR(20),
                CONSTRAINT supplier_employee_phone_pk PRIMARY KEY (id_phone)
);


ALTER SEQUENCE public.supplier_employee_phone_id_phone_seq OWNED BY public.supplier_employee_phone.id_phone;

CREATE SEQUENCE public.facility_id_facility_seq;

CREATE TABLE public.facility (
                id_facility INTEGER NOT NULL DEFAULT nextval('public.facility_id_facility_seq'),
                name VARCHAR(20),
                CONSTRAINT facility_pk PRIMARY KEY (id_facility)
);


ALTER SEQUENCE public.facility_id_facility_seq OWNED BY public.facility.id_facility;

CREATE TABLE public.facility_storage (
                id_facility INTEGER NOT NULL,
                id_storage INTEGER NOT NULL,
                delivery_day day_of_week,
                CONSTRAINT facility_storage_pk PRIMARY KEY (id_facility, id_storage)
);


CREATE TABLE public.record (
                id_storage INTEGER NOT NULL,
                id_facility INTEGER NOT NULL,
                delivery_date DATE,
                payment_delay INTEGER
);


CREATE SEQUENCE public.device_id_device_seq;

CREATE TABLE public.device (
                id_device INTEGER NOT NULL DEFAULT nextval('public.device_id_device_seq'),
                id_facility INTEGER NOT NULL,
                name VARCHAR(30),
                error REAL,
                verification_date DATE,
                next_verification_date DATE,
                location VARCHAR(30),
                series VARCHAR(10),
                temperature_range numrange,
                humidity_range numrange,
                CONSTRAINT device_pk PRIMARY KEY (id_device)
);


ALTER SEQUENCE public.device_id_device_seq OWNED BY public.device.id_device;

CREATE SEQUENCE public.premises_id_premises_seq;

CREATE TABLE public.premises (
                id_premises INTEGER NOT NULL DEFAULT nextval('public.premises_id_premises_seq'),
                id_facility INTEGER NOT NULL,
                area REAL,
                owner VARCHAR(50),
                contract_number VARCHAR(20),
                start_date DATE,
                expiration_date DATE,
                payment_term DATE,
                CONSTRAINT premises_pk PRIMARY KEY (id_premises)
);


ALTER SEQUENCE public.premises_id_premises_seq OWNED BY public.premises.id_premises;

CREATE SEQUENCE public.asset_id_asset_seq;

CREATE TABLE public.asset (
                id_asset INTEGER NOT NULL DEFAULT nextval('public.asset_id_asset_seq'),
                id_facility INTEGER NOT NULL,
                name asset_name,
                number VARCHAR(15),
                CONSTRAINT asset_pk PRIMARY KEY (id_asset)
);


ALTER SEQUENCE public.asset_id_asset_seq OWNED BY public.asset.id_asset;

CREATE SEQUENCE public.phone_id_phone_seq;

CREATE TABLE public.phone (
                id_phone INTEGER NOT NULL DEFAULT nextval('public.phone_id_phone_seq'),
                id_facility INTEGER NOT NULL,
                number VARCHAR(20),
                CONSTRAINT phone_pk PRIMARY KEY (id_phone)
);


ALTER SEQUENCE public.phone_id_phone_seq OWNED BY public.phone.id_phone;

CREATE TABLE public.facility_info (
                id_facility INTEGER NOT NULL,
                address VARCHAR(100),
                status facility_status,
                code VARCHAR(3),
                schedule VARCHAR(50),
                email VARCHAR(30),
                CONSTRAINT facility_info_pk PRIMARY KEY (id_facility)
);


CREATE SEQUENCE public.holiday_id_holiday_seq;

CREATE TABLE public.holiday (
                id_holiday INTEGER NOT NULL DEFAULT nextval('public.holiday_id_holiday_seq'),
                date DATE,
                name holiday_name,
                CONSTRAINT holiday_pk PRIMARY KEY (id_holiday)
);


ALTER SEQUENCE public.holiday_id_holiday_seq OWNED BY public.holiday.id_holiday;

CREATE TABLE public.facility_holiday (
                id_holiday INTEGER NOT NULL,
                id_facility INTEGER NOT NULL,
                proceeds REAL,
                CONSTRAINT facility_holiday_pk PRIMARY KEY (id_holiday, id_facility)
);


CREATE SEQUENCE public.course_id_course_seq;

CREATE TABLE public.course (
                id_course INTEGER NOT NULL DEFAULT nextval('public.course_id_course_seq'),
                name VARCHAR(50),
                CONSTRAINT course_pk PRIMARY KEY (id_course)
);


ALTER SEQUENCE public.course_id_course_seq OWNED BY public.course.id_course;

CREATE TABLE public.course_info (
                id_course INTEGER NOT NULL,
                description VARCHAR(100),
                hours INTEGER,
                start_date DATE,
                end_date DATE,
                CONSTRAINT course_info_pk PRIMARY KEY (id_course)
);


CREATE SEQUENCE public.edu_id_edu_seq;

CREATE TABLE public.edu (
                id_edu INTEGER NOT NULL DEFAULT nextval('public.edu_id_edu_seq'),
                name VARCHAR(50),
                CONSTRAINT edu_pk PRIMARY KEY (id_edu)
);


ALTER SEQUENCE public.edu_id_edu_seq OWNED BY public.edu.id_edu;

CREATE SEQUENCE public.employee_id_employee_seq;

CREATE TABLE public.employee (
                id_employee INTEGER NOT NULL DEFAULT nextval('public.employee_id_employee_seq'),
                last_name VARCHAR(20) NOT NULL,
                first_name VARCHAR(20) NOT NULL,
                CONSTRAINT employee_pk PRIMARY KEY (id_employee)
);


ALTER SEQUENCE public.employee_id_employee_seq OWNED BY public.employee.id_employee;

CREATE SEQUENCE public.inspection_id_inspection_seq;

CREATE TABLE public.inspection (
                id_inspection INTEGER NOT NULL DEFAULT nextval('public.inspection_id_inspection_seq'),
                id_employee INTEGER NOT NULL,
                id_facility INTEGER NOT NULL,
                date DATE,
                description VARCHAR(100),
                CONSTRAINT inspection_pk PRIMARY KEY (id_inspection)
);


ALTER SEQUENCE public.inspection_id_inspection_seq OWNED BY public.inspection.id_inspection;

CREATE SEQUENCE public.checkup_id_checkup_seq;

CREATE TABLE public.checkup (
                id_checkup INTEGER NOT NULL DEFAULT nextval('public.checkup_id_checkup_seq'),
                id_inspection INTEGER NOT NULL,
                question checkup_question NOT NULL,
                answer checkup_answer NOT NULL,
                CONSTRAINT checkup_pk PRIMARY KEY (id_checkup)
);


ALTER SEQUENCE public.checkup_id_checkup_seq OWNED BY public.checkup.id_checkup;

CREATE SEQUENCE public.violation_id_violation_seq;

CREATE TABLE public.violation (
                id_violation INTEGER NOT NULL DEFAULT nextval('public.violation_id_violation_seq'),
                id_employee INTEGER NOT NULL,
                id_checkup INTEGER NOT NULL,
                description VARCHAR(100),
                correction_term DATE,
                correction_date DATE,
                CONSTRAINT violation_pk PRIMARY KEY (id_violation)
);


ALTER SEQUENCE public.violation_id_violation_seq OWNED BY public.violation.id_violation;

CREATE TABLE public.employee_facility (
                id_facility INTEGER NOT NULL,
                id_employee INTEGER NOT NULL,
                CONSTRAINT employee_facility_pk PRIMARY KEY (id_facility, id_employee)
);


CREATE SEQUENCE public.contract_id_contract_seq;

CREATE TABLE public.contract (
                id_contract INTEGER NOT NULL DEFAULT nextval('public.contract_id_contract_seq'),
                id_facility INTEGER NOT NULL,
                id_employee INTEGER NOT NULL,
                type contract_type,
                start_date DATE,
                expiration_date DATE,
                CONSTRAINT contract_pk PRIMARY KEY (id_contract, id_facility, id_employee)
);


ALTER SEQUENCE public.contract_id_contract_seq OWNED BY public.contract.id_contract;

CREATE TABLE public.employee_holiday (
                id_holiday INTEGER NOT NULL,
                id_employee INTEGER NOT NULL,
                CONSTRAINT employee_holiday_pk PRIMARY KEY (id_holiday, id_employee)
);


CREATE TABLE public.employee_course (
                id_employee INTEGER NOT NULL,
                id_course INTEGER NOT NULL,
                CONSTRAINT employee_course_pk PRIMARY KEY (id_employee, id_course)
);


CREATE TABLE public.employee_info (
                id_employee INTEGER NOT NULL,
                DOB DATE,
                phone VARCHAR(20),
                address VARCHAR(100),
                position employee_position,
                category employee_category,
                salary INTEGER,
                PPE DATE,
                five_year_start DATE,
                five_year_end DATE,
                course_hours_sum INTEGER,
                course_deadline_date DATE,
                CONSTRAINT employee_info_pk PRIMARY KEY (id_employee)
);
COMMENT ON COLUMN public.employee_info.PPE IS 'personal protective equipment';


CREATE TABLE public.employee_edu (
                id_employee INTEGER NOT NULL,
                id_edu INTEGER NOT NULL,
                graduation_date DATE,
                CONSTRAINT employee_edu_pk PRIMARY KEY (id_employee, id_edu)
);


ALTER TABLE public.supplier_employee ADD CONSTRAINT new_tablesupplier_sup_employee_fk
FOREIGN KEY (supplier)
REFERENCES public.supplier (id_supplier)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.storage ADD CONSTRAINT new_tablesupplier_storage_fk
FOREIGN KEY (supplier)
REFERENCES public.supplier (id_supplier)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.facility_storage ADD CONSTRAINT storage_fac_stor_fk
FOREIGN KEY (id_storage)
REFERENCES public.storage (id_storage)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.storage_phone ADD CONSTRAINT storage_stor_phone_fk
FOREIGN KEY (id_storage)
REFERENCES public.storage (id_storage)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.supplier_employee_phone ADD CONSTRAINT sup_employee_sup_phone_fk
FOREIGN KEY (id_employee)
REFERENCES public.supplier_employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.facility_info ADD CONSTRAINT facility_fac_info_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.phone ADD CONSTRAINT facility_phone_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.asset ADD CONSTRAINT facility_asset_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.inspection ADD CONSTRAINT facility_inspection_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.premises ADD CONSTRAINT facility_premises_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.device ADD CONSTRAINT facility_device_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_facility ADD CONSTRAINT facility_emp_fac_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.facility_storage ADD CONSTRAINT facility_fac_stor_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.facility_holiday ADD CONSTRAINT facility_fac_holiday_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.record ADD CONSTRAINT fac_stor_record_fk
FOREIGN KEY (id_storage, id_facility)
REFERENCES public.facility_storage (id_storage, id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_holiday ADD CONSTRAINT holiday_emp_holiday_fk
FOREIGN KEY (id_holiday)
REFERENCES public.holiday (id_holiday)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.facility_holiday ADD CONSTRAINT holiday_fac_holiday_fk
FOREIGN KEY (id_holiday)
REFERENCES public.holiday (id_holiday)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.course_info ADD CONSTRAINT kurs_kurs_opis_fk
FOREIGN KEY (id_course)
REFERENCES public.course (id_course)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_course ADD CONSTRAINT kurs_prac_kurs_fk
FOREIGN KEY (id_course)
REFERENCES public.course (id_course)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_edu ADD CONSTRAINT szkola_prac_szkola_fk
FOREIGN KEY (id_edu)
REFERENCES public.edu (id_edu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_edu ADD CONSTRAINT pracownik_prac_szkola_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_info ADD CONSTRAINT employee_emp_info_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_course ADD CONSTRAINT pracownik_prac_kurs_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_holiday ADD CONSTRAINT employee_emp_holiday_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_facility ADD CONSTRAINT employee_emp_fac_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.inspection ADD CONSTRAINT employee_inspection_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.violation ADD CONSTRAINT employee_violation_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.checkup ADD CONSTRAINT inspection_check_fk
FOREIGN KEY (id_inspection)
REFERENCES public.inspection (id_inspection)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.violation ADD CONSTRAINT check_violation_fk
FOREIGN KEY (id_checkup)
REFERENCES public.checkup (id_checkup)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contract ADD CONSTRAINT emp_fac_contract_fk
FOREIGN KEY (id_facility, id_employee)
REFERENCES public.employee_facility (id_facility, id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;