package repository;

import config.DataSource;
import model.OperacaoEstoque;
import util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EstoqueRepository {

    public void entradaEstoque(int id, int entrada) {
        String query = "UPDATE produto SET quantidade_estoque = quantidade_estoque + ? WHERE " +
                "produto_id" +
                " = ?";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entrada);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            if (DatabaseUtil.ok(rows)) {
                // usuario e loja ainda fixos
                registra(id, 1, 1, entrada, "entrada");
                System.out.println("[OK] - Estoque atualizado (entrada) com sucesso.\n\n\n\n");
            } else {
                System.out.println("[ERRO] - Erro ao atualizar estoque.\n\n\n\n");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saidaEstoque(int id, int saida) {
        String query = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE " +
                "produto_id = ?";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, saida);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            if (DatabaseUtil.ok(rows)) {
                // usuario e loja ainda fixos
                registra(id, 1, 1, saida, "saida");
                System.out.println("[OK] - Estoque atualizado (saída) com sucesso.\n\n\n\n");
            } else {
                System.out.println("[ERRO] - Erro ao atualizar estoque.\n\n\n\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void balancoEstoque(int id, int balanco) {
        String query = "UPDATE produto SET quantidade_estoque = ? WHERE produto_id = ?";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, balanco);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            if (DatabaseUtil.ok(rows)) {
                // usuario e loja ainda fixos
                registra(id, 1, 1, balanco, "balanco");
                System.out.println("[OK] - Estoque atualizado (balanço) com sucesso.\n\n\n\n");
            } else {
                System.out.println("[ERRO] - Erro ao atualizar estoque.\n\n\n\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void registra(int idProduto, int idUsuario, int idLoja, int quantidade, String tipo) {
        String query = "INSERT INTO historico_estoque (produto_id, usuario_id, loja_id, quantidade, tipo_operacao)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idProduto);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idLoja);
            stmt.setInt(4, quantidade);
            stmt.setString(5, tipo);

            int rows = stmt.executeUpdate();
            if (!DatabaseUtil.ok(rows)) {
                System.out.println("[ERRO] - Problema em registrar atualização em estoque");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OperacaoEstoque> consulta(LocalDateTime start, LocalDateTime end) {
        List<OperacaoEstoque> operacoes = new ArrayList<>();

        String query = "SELECT * from historico_estoque WHERE data_operacao BETWEEN ? AND ?";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OperacaoEstoque operacao = new OperacaoEstoque();
                    operacao.setId(rs.getInt("operacao_id"));
                    operacao.setProdutoId(rs.getInt("produto_id"));
                    operacao.setUsuarioId(rs.getInt("usuario_id"));
                    operacao.setLojaId(rs.getInt("loja_id"));
                    operacao.setQuantidade(rs.getInt("quantidade"));
                    operacao.setTipoOperacao(rs.getString("tipo_operacao"));
                    operacao.setDataOperacao(rs.getTimestamp("data_operacao").toLocalDateTime());
                    operacoes.add(operacao);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return operacoes;
    }
}
