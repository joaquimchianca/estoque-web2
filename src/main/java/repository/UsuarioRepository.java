package repository;

import config.DataSource;
import model.Usuario;
import util.ConversionUtil;
import util.DatabaseUtil;
import util.OutputUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public void salva(Usuario usuario) {
        String query = "INSERT INTO usuario (nome, email, senha, papel, loja_id) VALUES (?, ?, ?, ?, ?)";

        String papel = usuario.getPapel().toString().toLowerCase();

        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, papel);
            stmt.setInt(5, 1);

            int rows = stmt.executeUpdate();

            if (!(DatabaseUtil.ok(rows))) {
                throw new RuntimeException("Erro ao salvar");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean login(String email, String senha) {
        String query = "SELECT senha, papel FROM usuario WHERE email = ?";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String senhaResposta = rs.getString("senha");
                    if (senha.equals(senhaResposta)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Usuario loginERetornaPapel(String email, String senha) {

        if (! login(email, senha)) {
            throw new RuntimeException("Erro ao login");
        }

        Usuario usuario = new Usuario();
        String query = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuario.setPapel(ConversionUtil.stringToPapel(rs.getString("papel")));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setId(rs.getInt("usuario_id"));
                    usuario.setLojaId(rs.getInt("loja_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public Usuario buscaUsuarioPorEmail(String email) {
        String query = "SELECT * FROM usuario WHERE email = ?";
        Usuario usuario = new Usuario();
        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuario.setId(rs.getInt("usuario_id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setLojaId(rs.getInt("loja_id"));
                    usuario.setPapel(ConversionUtil.stringToPapel(rs.getString("papel")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public void removerUsuario(int id) {
        String query = "DELETE FROM usuario WHERE usuario_id = ?";
        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editar(Usuario usuario) {
        String query = "UPDATE usuario SET nome = ?, email = ?, senha = ?, papel = ? WHERE usuario_id = ? ";

        try(Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPapel().toString().toLowerCase());
            stmt.setInt(5, usuario.getId());

            int rows = stmt.executeUpdate();
            if (!(DatabaseUtil.ok(rows))) {
                System.out.println("Erro ao atualizar");
                Thread.sleep(5000);
                OutputUtil.limpaTela();
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
