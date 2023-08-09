Swagger:http://localhost:8080/api/swagger-ui/index.html#/skill-controller/getAllSkillModels

neste projeto utilizei o Lombok em alguma classes para o codigo ficar mais limpo: caso tenho algum erro pelas configurações do spring : aqui segue um material de apoio: https://www.youtube.com/watch?v=W0ywxkvc4_M

voce poder criar o banco pelo aplication properties mais aqui segue o sql: -- Tabela "roles"
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Tabela "skill"
CREATE TABLE skill (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255),
    url_imagem VARCHAR(255)
);

-- Tabela "users"
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    login VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

-- Tabela "user_roles" (tabela de junção entre "users" e "roles")
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Tabela "user_skill"
CREATE TABLE user_skill (
    user_id BIGINT,
    skill_id BIGINT,
    level BIGINT,
    PRIMARY KEY (user_id, skill_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (skill_id) REFERENCES skill (id)
);

INSERT INTO skill (description, name, url_imagem)
VALUES ('Linguagem de programação', 'Python', 'https://img2.gratispng.com/20180806/fv/kisspng-python-scalable-vector-graphics-logo-javascript-cl-coderpete-game-development-5b6819307ca155.2506144815335488485105.jpg'),
       ('Linguagem de programação', 'C++', 'https://e7.pngegg.com/pngimages/724/306/png-clipart-c-logo-c-programming-language-icon-letter-c-blue-logo.png'),
       ('Linguagem de programação', 'C#', 'https://img2.gratispng.com/20180831/iua/kisspng-c-programming-language-logo-microsoft-visual-stud-atlas-portfolio-5b89919299aab1.1956912415357423546294.jpg'),
       ('teste unitario','Jest','https://static.javatpoint.com/blog/images/jest-framework.png')

       
       INSERT INTO roles (name)
VALUES ('ROLE_ADM');
