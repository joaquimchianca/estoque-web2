CREATE TABLE loja (
                      loja_id SERIAL PRIMARY KEY,
                      nome VARCHAR(100) NOT NULL,
                      endereco VARCHAR(255) NOT NULL
);

CREATE TABLE usuario (
                         usuario_id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         papel VARCHAR(50) NOT NULL CHECK (papel IN ('administrador', 'gerente', 'funcionario')),
                         loja_id INT NOT NULL,
                         FOREIGN KEY (loja_id) REFERENCES loja(loja_id)
);

CREATE TABLE produto (
                         produto_id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         descricao TEXT,
                         preco_custo DECIMAL(10, 2) NOT NULL,
                         preco_venda DECIMAL(10, 2) NOT NULL,
                         quantidade_estoque INT NOT NULL,
                         loja_id INT NOT NULL,
                         FOREIGN KEY (loja_id) REFERENCES loja(loja_id)
);

CREATE TABLE historico_estoque (
                                   operacao_id SERIAL PRIMARY KEY,
                                   produto_id INT NOT NULL,
                                   tipo_operacao VARCHAR(10) NOT NULL CHECK (tipo_operacao IN ('entrada', 'saida', 'balanco')),
                                   quantidade INT NOT NULL,
                                   data_operacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   loja_id INT NOT NULL,
                                   usuario_id INT NOT NULL,
                                   FOREIGN KEY (produto_id) REFERENCES produto(produto_id),
                                   FOREIGN KEY (loja_id) REFERENCES loja(loja_id),
                                   FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
);

-- usuario admin
insert into usuario (nome, email, senha, papel, loja_id)
values ('admin', 'admin', 'admin', 'administrador', 1);