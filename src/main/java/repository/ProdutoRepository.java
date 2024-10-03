package repository;

import config.DataSource;
import model.Produto;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoRepository {

    private EstoqueRepository estoqueRepository;

    public ProdutoRepository() {
        estoqueRepository = new EstoqueRepository();
    }

    public void salva(Produto produto) {
        String query = "INSERT INTO produto (nome, descricao, preco_custo, preco_venda, quantidade_estoque, loja_id)" +
                "VALUES (?,?,?,?,?,?)";

        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPrecoCusto());
            stmt.setDouble(4, produto.getPrecoVenda());
            stmt.setInt(5, produto.getQuantidade());
            //set id da loja
            stmt.setInt(6, 1);

            int rows = stmt.executeUpdate();

            if (DatabaseUtil.ok(rows)) {
                System.out.println("[OK] - Produto cadastrado com sucesso.\n\n\n\n");
            } else {
                System.out.println("[ERRO] - Erro ao cadastrar produto.\n\n\n\n");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void consultaPorNome(String nome) {
        String query = "SELECT * FROM produto WHERE nome ilike ?";

        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("ID, NOME, QTD, PREÇO (R$)");
            System.out.println("-----------------------------");
            while (rs.next()) {
                int id = rs.getInt("produto_id");
                String nome_produto = rs.getString("nome");
                int quantidade = rs.getInt("quantidade_estoque");
                double precoVenda = rs.getDouble("preco_venda");
                System.out.println(id + ", " + nome_produto + ", "
                                    + quantidade + ", " + precoVenda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void consultaPorCodigo(int id) {
        String query = "SELECT * FROM produto WHERE produto_id = ?";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("ID, NOME, QTD, PREÇO (R$)");
            System.out.println("-----------------------------");
            while (rs.next()) {
//                int id = rs.getInt("produto_id");
                String nome_produto = rs.getString("nome");
                int quantidade = rs.getInt("quantidade_estoque");
                double precoVenda = rs.getDouble("preco_venda");
                System.out.println(id + ", " + nome_produto + ", "
                        + quantidade + ", " + precoVenda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void consultaPorEstoque(int estoque, String operador) {
        String query = "";
        if (operador.equalsIgnoreCase("maior")) {
            query = "SELECT * FROM produto WHERE quantidade_estoque > ?";
        } else if (operador.equalsIgnoreCase("menor")) {
            query = "SELECT * FROM produto WHERE quantidade_estoque < ?";
        } else if (operador.equalsIgnoreCase("igual")) {
            query = "SELECT * FROM produto WHERE quantidade_estoque = ?";
        }

        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, estoque);
            ResultSet rs = stmt.executeQuery();
            System.out.println("ID, NOME, QTD, PREÇO (R$)");
            System.out.println("-----------------------------");
            while (rs.next()) {
                int id = rs.getInt("produto_id");
                String nome_produto = rs.getString("nome");
                int quantidade = rs.getInt("quantidade_estoque");
                double precoVenda = rs.getDouble("preco_venda");
                System.out.println(id + ", " + nome_produto + ", "
                        + quantidade + ", " + precoVenda);
            }
            Thread.sleep(5000);
            System.out.println("\n\n\n\n");
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
