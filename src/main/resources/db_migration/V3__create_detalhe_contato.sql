CREATE TABLE cliente_contato (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL,
    telefone VARCHAR(20) NOT NULL,

    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    tipo VARCHAR(50) NOT NULL

    CONSTRAINT fk_cliente_contato_user
            FOREIGN KEY (user_id)
            REFERENCES usuarios(id)
);
