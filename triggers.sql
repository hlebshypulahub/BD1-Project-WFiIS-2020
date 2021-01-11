CREATE OR REPLACE FUNCTION validate_first_name_last_name()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF LENGTH(TRIM(' ' FROM NEW.first_name)) = 0 THEN
        RAISE EXCEPTION 'Nie podano imienia!';
    ELSEIF LENGTH(TRIM(' ' FROM NEW.last_name)) = 0 THEN
        RAISE EXCEPTION 'Nie podano nazwiska!';
    END IF;
    IF (NEW.first_name !~ '^[a-żA-Ż]*$') OR
       (NEW.last_name !~ '^[a-żA-Ż]*$') THEN
        RAISE EXCEPTION 'Niepoprawne wartości imienia lub nazwiska (zawierają znaki puste, znaki numeryczne i td.).';
    END IF;
    NEW.first_name = INITCAP(NEW.first_name);
    NEW.last_name = INITCAP(NEW.last_name);
    RETURN NEW;
END;
$$;

CREATE TRIGGER valid_f_l_name
    BEFORE INSERT OR UPDATE
    ON employee
    FOR EACH ROW
EXECUTE PROCEDURE validate_first_name_last_name();