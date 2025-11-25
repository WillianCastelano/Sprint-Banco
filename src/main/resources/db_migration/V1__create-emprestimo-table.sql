CREATE TABLE emprestimo (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    valor_solicitado NUMERIC(10,2) NOT NULL,
    quantidade_parcelas INTEGER NOT NULL CHECK (quantidade_parcelas > 0 AND quantidade_parcelas <= 12),
    status VARCHAR(50),
    codigo_contrato VARCHAR(100),
    data_aprovacao DATE,
    valor_parcela NUMERIC(10,2)
);
