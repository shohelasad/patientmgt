-- DROP TABLE IF EXISTS public.patient;
CREATE TABLE IF NOT EXISTS public.patient
(
    date_of_birth date NOT NULL,
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    social_security_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT patient_pkey PRIMARY KEY (id)
    );

-- DROP TABLE IF EXISTS public.visit;
CREATE TABLE IF NOT EXISTS public.visit
(
    visit_reason smallint NOT NULL,
    visit_type smallint NOT NULL,
    date_time timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    patient_id bigint,
    family_history character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT visit_pkey PRIMARY KEY (id),
    CONSTRAINT fkrban5yeabnx30seqm69jw44e FOREIGN KEY (patient_id)
    REFERENCES public.patient (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT visit_visit_reason_check CHECK (visit_reason >= 0 AND visit_reason <= 2),
    CONSTRAINT visit_visit_type_check CHECK (visit_type >= 0 AND visit_type <= 1)
    );

