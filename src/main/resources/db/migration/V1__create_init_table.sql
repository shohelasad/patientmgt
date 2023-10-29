
-- DROP TABLE IF EXISTS public.patient;
CREATE TABLE IF NOT EXISTS public.patient
(
    id bigint PRIMARY KEY,
    date_of_birth date NOT NULL,
    name character varying(100) NOT NULL,
    social_security_number character varying(100)  NOT NULL,
    surname character varying(100),
    CONSTRAINT patient_unique UNIQUE (social_security_number)
    );

-- DROP TABLE IF EXISTS public.visit;
CREATE TABLE IF NOT EXISTS public.visit
(
    id bigint PRIMARY KEY,
    patient_id bigint,
    visit_reason smallint NOT NULL CHECK (visit_reason >= 0 AND visit_reason <= 2),
    visit_type smallint NOT NULL CHECK (visit_type >= 0 AND visit_type <= 1),
    date_time timestamp(6) without time zone NOT NULL,
    family_history text,
    CONSTRAINT fkrban5yeabnx30seqm69jw44e FOREIGN KEY (patient_id)
    REFERENCES public.patient (id) ON UPDATE NO ACTION ON DELETE NO ACTION
    );

