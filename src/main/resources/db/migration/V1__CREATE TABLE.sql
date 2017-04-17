--Insira aqui a criação das tabelas

CREATE TABLE profile (
  id serial primary key NOT NULL,
  name text
);

CREATE TABLE user_account (
  id serial primary key NOT NULL,
  name text,
  login text,
  password text,
  email text,
  id_profile int REFERENCES profile(id),
  changed_password boolean,
  state boolean
);

CREATE TABLE config(
	id serial primary key,
	key varchar not null,
	value varchar not null
);

CREATE TABLE sign_in_system (
  id serial primary key NOT NULL,
  id_user_account int REFERENCES user_account(id),
  date TIMESTAMP
);

CREATE TABLE layout (
  id serial primary key NOT NULL,
  color text,
  background text,
  logo text,
  icon text,
  login_position char
);

CREATE TABLE module(
    id serial primary key NOT NULL,
    name text,
    description text,
    icon text,
    url text,
    order_menu int,
    submenu boolean,
    id_module_parent int REFERENCES module(id)
);

CREATE TABLE profile_module (
  id_profile int REFERENCES profile(id),
  id_module int REFERENCES module(id),
  PRIMARY KEY (id_profile, id_module)
);
