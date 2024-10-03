package repository;

import config.DataSource;
import model.Loja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LojaRepository {

    public void salva(Loja loja) {
        String query = "INSERT INTO loja (nome, endereco) VALUES (?, ?)";
        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, loja.getNome());
            stmt.setString(2, loja.getEndereco());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Loja cadastrada.\n\n\n\n");
        }
    }

}
