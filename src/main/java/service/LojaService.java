package service;

import config.DataSource;
import config.ScannerSingleton;
import model.Loja;
import repository.LojaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class LojaService {

    private Scanner scanner;
    private final LojaRepository lojaRepository;

    public LojaService() {
        this.scanner = ScannerSingleton.getInstance();
        this.lojaRepository = new LojaRepository();
    }

    public void processaDadosCadastro() {
        System.out.println("\n\n\n\n");
        System.out.println("Cadastro de Loja");
        System.out.println("----------------------------");
        System.out.println("Digite o nome da loja:");
        String nome = scanner.nextLine();
        System.out.println("Digite o enedereço da loja:");
        String endereco = scanner.nextLine();

        if (nome.isBlank() || endereco.isBlank()) {
            System.out.println("[ERRO] - Entrada inválida: valor em branco");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            processaDadosCadastro();
        }

        Loja loja = new Loja(nome, endereco);
        lojaRepository.salva(loja);
    }



}
