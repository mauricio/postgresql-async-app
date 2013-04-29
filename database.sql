CREATE TABLE messages
(
  id bigserial NOT NULL,
  content character varying(255) NOT NULL,
  moment date NOT NULL,
  CONSTRAINT bigserial_column_pkey PRIMARY KEY (id )
);