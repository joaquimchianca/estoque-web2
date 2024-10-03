package controller;

import config.ScannerSingleton;
import model.Usuario;
import service.ProdutoService;

import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class ProdutoController {
    private Scanner scanner;
    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoService();
        this.scanner = ScannerSingleton.getInstance();
    }

    public void displayMenuProduto(Usuario usuario) {
        System.out.println("Menu de produto");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println(SEPARADOR);
        System.out.println("(1) - Cadastrar produto");
        System.out.println("(2) - Consultar produto");
        String command = scanner.nextLine();

        switch (command) {
            case "1": produtoService.processaDadosCadastro(); break;
            case "2": produtoService.consultaProdutos(); break;
        }
    }
}
