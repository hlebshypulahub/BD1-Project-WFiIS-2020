CREATE VIEW employee_data_view
AS
SELECT e.id_employee,
       ed.role,
       e.first_name,
       e.last_name,
       ei.dob,
       ei.category,
       ei.position,
       ei.ppe,
       ei.salary,
       ei.phone,
       ei.address,
       ei.course_hours_sum
FROM employee e
         JOIN employee_data ed on e.id_employee = ed.id_employee
         JOIN employee_info ei ON e.id_employee = ei.id_employee;
		
		
		
CREATE VIEW facility_data_view
AS
SELECT f.id_facility, f.name, fi.status
FROM facility f
         JOIN facility_info fi on f.id_facility = fi.id_facility;
		
		
		
CREATE VIEW employee_enums_view AS
SELECT t.typname, e.enumlabel
FROM pg_type t,
     pg_enum e
WHERE t.oid = e.enumtypid
  AND typname IN ('employee_role', 'employee_position', 'employee_category');
  
  
  
CREATE VIEW fill_facility_view AS SELECT CONCAT(fi.status, '|', f.name) AS data
FROM facility f
         JOIN facility_info fi on f.id_facility = fi.id_facility
GROUP BY fi.status, f.name;



CREATE VIEW employee_resposibility AS
SELECT v.id_employee, v.description, v.correction_term, f.name
FROM violation v
         JOIN checkup c on v.id_checkup = c.id_checkup
         JOIN inspection i on c.id_inspection = i.id_inspection
         JOIN facility f on i.id_facility = f.id_facility;
		 
		 
		 
		 
CREATE VIEW inspection_data_view AS
SELECT i.id_inspection, i.id_facility, e.first_name, e.last_name, i.date, i.description
FROM inspection i
         JOIN employee e on e.id_employee = i.id_employee;