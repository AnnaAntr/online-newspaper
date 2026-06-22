--
-- PostgreSQL database dump
--

\restrict HhnYp4brJI0bHtla2Nc3SKuzzrXT8CAtTnJc2wOPXwC2S9bNGylXjiECQhfk8uj

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: article; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.article (
    id integer NOT NULL,
    content text,
    created_at timestamp without time zone,
    image_url character varying(255),
    title character varying(255) NOT NULL
);


ALTER TABLE public.article OWNER TO postgres;

--
-- Name: article_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.article_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.article_id_seq OWNER TO postgres;

--
-- Name: article_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.article_id_seq OWNED BY public.article.id;


--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    surname character varying(255) NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- Name: person_comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person_comment (
    id integer NOT NULL,
    content character varying(1000) NOT NULL,
    created_at timestamp without time zone,
    article_id integer,
    person_id integer
);


ALTER TABLE public.person_comment OWNER TO postgres;

--
-- Name: person_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.person_comment_id_seq OWNER TO postgres;

--
-- Name: person_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_comment_id_seq OWNED BY public.person_comment.id;


--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.person_id_seq OWNER TO postgres;

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- Name: person_like; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person_like (
    id integer NOT NULL,
    article_id integer,
    person_id integer
);


ALTER TABLE public.person_like OWNER TO postgres;

--
-- Name: person_like_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_like_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.person_like_id_seq OWNER TO postgres;

--
-- Name: person_like_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_like_id_seq OWNED BY public.person_like.id;


--
-- Name: article id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article ALTER COLUMN id SET DEFAULT nextval('public.article_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- Name: person_comment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_comment ALTER COLUMN id SET DEFAULT nextval('public.person_comment_id_seq'::regclass);


--
-- Name: person_like id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_like ALTER COLUMN id SET DEFAULT nextval('public.person_like_id_seq'::regclass);


--
-- Data for Name: article; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.article (id, content, created_at, image_url, title) FROM stdin;
1	some content for article 1	2026-05-18 19:26:00	some_image_url_1	article_1
2	some content for article 2	2026-05-17 19:56:40	some_image_url_2	article_2
3	some content for article 3	2026-05-19 12:18:09	some_image_url_3	article_3
4	some content for article 4	2026-05-19 14:02:34	some_image_url_4	article_4
5	some content for article 5	2026-04-16 20:11:51	some_image_url_5	article_5
6	some content for article 6	2026-05-12 17:03:27	some_image_url_6	article_6
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (id, email, name, password, surname) FROM stdin;
1	ivanov@test.com	ivan	$2a$10$tF5iPoQ8rff/iWlrmIWAGefoCx6xBG/IhVJa879TDoh1G7pyropFm	ivanov
2	petrov@test.com	petr	$2a$10$p0tHhPZX35xdSKSbXytwyeXpxgfbYHyngByye3AvFm75LGzI9YLny	petrov
\.


--
-- Data for Name: person_comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person_comment (id, content, created_at, article_id, person_id) FROM stdin;
3	test comment	2026-06-15 10:53:24.056	3	2
4	another test comment	2026-06-15 11:02:55.571	3	2
5	one more comment	2026-06-15 12:53:26.262	3	2
6	comment to check paging	2026-06-15 12:53:52.496	3	2
\.


--
-- Data for Name: person_like; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person_like (id, article_id, person_id) FROM stdin;
2	3	2
\.


--
-- Name: article_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.article_id_seq', 6, true);


--
-- Name: person_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_comment_id_seq', 6, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 2, true);


--
-- Name: person_like_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_like_id_seq', 2, true);


--
-- Name: article article_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);


--
-- Name: person_comment person_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_comment
    ADD CONSTRAINT person_comment_pkey PRIMARY KEY (id);


--
-- Name: person_like person_like_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_like
    ADD CONSTRAINT person_like_pkey PRIMARY KEY (id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: person uk_fwmwi44u55bo4rvwsv0cln012; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT uk_fwmwi44u55bo4rvwsv0cln012 UNIQUE (email);


--
-- Name: person_comment fk46lj3xpwl2dy29xdsn73yg10u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_comment
    ADD CONSTRAINT fk46lj3xpwl2dy29xdsn73yg10u FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- Name: person_comment fke614gy40w7osc8o8w0g1okf6v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_comment
    ADD CONSTRAINT fke614gy40w7osc8o8w0g1okf6v FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: person_like fkkjubga7d3cssf6957wdgeees4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_like
    ADD CONSTRAINT fkkjubga7d3cssf6957wdgeees4 FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: person_like fkn4a5089qqfhlbg0o706spwdyv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_like
    ADD CONSTRAINT fkn4a5089qqfhlbg0o706spwdyv FOREIGN KEY (article_id) REFERENCES public.article(id);


--
-- PostgreSQL database dump complete
--

\unrestrict HhnYp4brJI0bHtla2Nc3SKuzzrXT8CAtTnJc2wOPXwC2S9bNGylXjiECQhfk8uj

