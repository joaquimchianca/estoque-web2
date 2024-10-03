package controller;

import config.ScannerSingleton;
import model.Usuario;
import service.EstoqueService;
import service.ProdutoService;
import service.UsuarioService;

import java.sql.SQLOutput;
import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class RelatorioController {

    private Scanner scanner;

    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;

    public RelatorioController() {
        scanner = ScannerSingleton.getInstance();
        produtoService = new ProdutoService();
        estoqueService = new EstoqueService();
    }

    public void displayMenuRelatorios(Usuario usuario) {
        System.out.println("Menu de relatórios");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println(SEPARADOR);
        System.out.println("(1) - Consulta de operações de estoque em intervalo de tempo");
        System.out.println("(2) - Consulta de produtos com baixo estoque");
        String command = scanner.nextLine();
        switch (command) {
            case "2": produtoService.relatorioProdutosEstoque(); break;
            case "1": estoqueService.imprimeRelatorioOperacoesEstoque(); break;
        }
    }
}
