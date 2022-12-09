CREATE TABLE roles
(
    id   SERIAL NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    role_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE roles
    ADD CONSTRAINT unique_roles_name UNIQUE (name);

ALTER TABLE users
    ADD CONSTRAINT unique_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT unique_users_nickname UNIQUE (nickname);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_roleid_roles_id FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userid_users_id FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE movie
    ALTER COLUMN picture_path SET NOT NULL;

ALTER TABLE movie
    ALTER COLUMN price SET NOT NULL;

ALTER TABLE movie
    ALTER COLUMN rating SET NOT NULL;

ALTER TABLE movie
    ALTER COLUMN votes SET NOT NULL;