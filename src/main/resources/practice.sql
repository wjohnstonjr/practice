--
-- PostgreSQL database dump
--

-- Dumped from database version 14.19
-- Dumped by pg_dump version 17.0

-- Started on 2026-02-18 10:24:43

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 9 (class 2615 OID 17007)
-- Name: practice; Type: SCHEMA; Schema: -; Owner: tnp
--

CREATE SCHEMA IF NOT EXISTS practice;

ALTER SCHEMA practice OWNER TO tnp;

SET default_tablespace = '';

SET default_table_access_method = heap;

DROP TABLE IF exists practice.customer;
DROP TABLE IF exists practice.address;

--
-- TOC entry 223 (class 1259 OID 17030)
-- Name: address; Type: TABLE; Schema: practice; Owner: tnp
--

CREATE TABLE practice.address (
    id integer NOT NULL,
    street character varying(255),
    city character varying(255),
    state character varying(255),
    zip character varying(255)
);


ALTER TABLE practice.address OWNER TO tnp;

--
-- TOC entry 222 (class 1259 OID 17029)
-- Name: address_id_seq; Type: SEQUENCE; Schema: practice; Owner: tnp
--

ALTER TABLE practice.address ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME practice.address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 225 (class 1259 OID 17038)
-- Name: customer; Type: TABLE; Schema: practice; Owner: tnp
--

CREATE TABLE practice.customer (
    id integer NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    address_id integer
);


ALTER TABLE practice.customer OWNER TO tnp;

--
-- TOC entry 224 (class 1259 OID 17037)
-- Name: customer_id_seq; Type: SEQUENCE; Schema: practice; Owner: tnp
--

ALTER TABLE practice.customer ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME practice.customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3440 (class 0 OID 17030)
-- Dependencies: 223
-- Data for Name: address; Type: TABLE DATA; Schema: practice; Owner: tnp
--

INSERT INTO practice.address OVERRIDING SYSTEM VALUE VALUES (1, '88 UGL Road', 'Hewitt', 'NJ', '07421');
INSERT INTO practice.address OVERRIDING SYSTEM VALUE VALUES (2, '37 Flanders Road', 'Hewitt', 'NJ', '07421');
INSERT INTO practice.address OVERRIDING SYSTEM VALUE VALUES (3, '11 Boulevard East', 'Keyport', 'NJ', '07735');


--
-- TOC entry 3442 (class 0 OID 17038)
-- Dependencies: 225
-- Data for Name: customer; Type: TABLE DATA; Schema: practice; Owner: tnp
--

INSERT INTO practice.customer OVERRIDING SYSTEM VALUE VALUES (1, 'B.J.', 'Johnston', 1);
INSERT INTO practice.customer OVERRIDING SYSTEM VALUE VALUES (2, 'Jeanne', 'Johnston', 1);
INSERT INTO practice.customer OVERRIDING SYSTEM VALUE VALUES (3, 'Ryan', 'johnston', 2);
INSERT INTO practice.customer OVERRIDING SYSTEM VALUE VALUES (4, 'Shannon', 'Johnston', 3);
INSERT INTO practice.customer OVERRIDING SYSTEM VALUE VALUES (5, 'Mark', 'Aaron', 3);
INSERT INTO practice.customer OVERRIDING SYSTEM VALUE VALUES (6, 'Liam', 'Johnston', 3);


--
-- TOC entry 3448 (class 0 OID 0)
-- Dependencies: 222
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: practice; Owner: tnp
--

SELECT pg_catalog.setval('practice.address_id_seq', 3, true);


--
-- TOC entry 3449 (class 0 OID 0)
-- Dependencies: 224
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: practice; Owner: tnp
--

SELECT pg_catalog.setval('practice.customer_id_seq', 6, true);


--
-- TOC entry 3296 (class 2606 OID 17036)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: practice; Owner: tnp
--

ALTER TABLE ONLY practice.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- TOC entry 3298 (class 2606 OID 17044)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: practice; Owner: tnp
--

ALTER TABLE ONLY practice.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 3299 (class 2606 OID 17045)
-- Name: customer customer_address_id_fkey; Type: FK CONSTRAINT; Schema: practice; Owner: tnp
--

ALTER TABLE ONLY practice.customer
    ADD CONSTRAINT customer_address_id_fkey FOREIGN KEY (address_id) REFERENCES practice.address(id);


-- Completed on 2026-02-18 10:24:44

--
-- PostgreSQL database dump complete
--

