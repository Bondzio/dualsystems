CREATE DATABASE dualsystems
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

-- Table: public.exchangerate

-- DROP TABLE public.exchangerate;

CREATE TABLE public.exchangerate
(
    id bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    huftoeurmultiplier numeric(24,12) NOT NULL,
    CONSTRAINT exchangerate_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.exchangerate
    OWNER to postgres;
    


-- Table: public.invoice

-- DROP TABLE public.invoice;

CREATE TABLE public.invoice
(
    id bigint NOT NULL,
    comment character varying(200) COLLATE pg_catalog."default",
    customername character varying(100) COLLATE pg_catalog."default" NOT NULL,
    duedate timestamp without time zone NOT NULL,
    issuedate timestamp without time zone NOT NULL,
    CONSTRAINT invoice_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.invoice
    OWNER to postgres;
    


-- Table: public.invoiceitem

-- DROP TABLE public.invoiceitem;

CREATE TABLE public.invoiceitem
(
    id bigint NOT NULL,
    productname character varying(100) COLLATE pg_catalog."default" NOT NULL,
    quantity numeric(24,12) NOT NULL,
    unit character varying(100) COLLATE pg_catalog."default",
    unitprice numeric(24,12) NOT NULL,
    invoice_id bigint NOT NULL,
    CONSTRAINT invoiceitem_pkey PRIMARY KEY (id),
    CONSTRAINT fkm1qstfhfwpv8oip90w8fdxfl3 FOREIGN KEY (invoice_id)
        REFERENCES public.invoice (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.invoiceitem
    OWNER to postgres;



-- Table: public.invoice_invoiceitem

-- DROP TABLE public.invoice_invoiceitem;

CREATE TABLE public.invoice_invoiceitem
(
    invoice_id bigint NOT NULL,
    items_id bigint NOT NULL,
    CONSTRAINT uk_u0jcl7l1k3cj9q6t2yfvp0gr UNIQUE (items_id),
    CONSTRAINT fkokg6b7yeuwvtp9xq3dk4ujtyk FOREIGN KEY (items_id)
        REFERENCES public.invoiceitem (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkpat06isffk5qao9yi4em3t4g2 FOREIGN KEY (invoice_id)
        REFERENCES public.invoice (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.invoice_invoiceitem
    OWNER to postgres;



