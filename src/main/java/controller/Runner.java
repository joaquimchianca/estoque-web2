package controller;

import config.ScannerSingleton;
import model.Papel;
import model.Usuario;
import util.OutputUtil;

import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class Runner {

    private Scanner scanner;

    private final LojaController lojaController;
    private final UsuarioController usuarioController;
    private final EstoqueController estoqueController;
    private final ProdutoController produtoController;
    private final RelatorioController relatorioController;

    public Runner() {
        scanner = ScannerSingleton.getInstance();

        lojaController = new LojaController();
        usuarioController = new UsuarioController();
        estoqueController = new EstoqueController();
        produtoController = new ProdutoController();
        relatorioController = new RelatorioController();
    }

    public void displayLogin() {
        Usuario usuario;

        try {
            usuario = usuarioController.displayLogin();

            if (usuario.getPapel() == Papel.ADMINISTRADOR) {
                OutputUtil.limpaTela();
                displayMenuAdministrador(true, usuario);
            }

            if (usuario.getPapel() == Papel.GERENTE) {
                OutputUtil.limpaTela();
                displayMenuGerente(true, usuario);
            }

            if (usuario.getPapel() == Papel.FUNCIONARIO) {
                OutputUtil.limpaTela();
                displayMenuFuncionario(true, usuario);
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void displayMenuAdministrador(boolean estaLogado, Usuario usuario) {
        while(estaLogado) {
            System.out.println("Menu do Administrador");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println(SEPARADOR);
            System.out.println("(1) - Menu de lojas");
            System.out.println("(2) - Menu de usuários");
            System.out.println("(3) - Menu de estoque");
            System.out.println("(4) - Menu de produtos");
            System.out.println("(5) - Menu de relatórios");
            System.out.println("(0) - Logout");
            System.out.println(SEPARADOR);
            String command = scanner.nextLine();
            switch (command) {
                case "1": lojaController.displayMenuLoja(usuario); break;
                case "2": usuarioController.displayMenuUsuario(usuario); break;
                case "3": estoqueController.displayMenuEstoque(usuario); break;
                case "4": produtoController.displayMenuProduto(usuario); break;
                case "5": relatorioController.displayMenuRelatorios(usuario); break;
                case "0": estaLogado = false; break;
            }
        }
        OutputUtil.limpaTela();
        displayLogin();
    }

    private void displayMenuGerente(boolean estaLogado, Usuario usuario) {
        while(estaLogado) {
            System.out.println("Menu do Gerente");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println(SEPARADOR);
            System.out.println("(1) - Menu de estoque");
            System.out.println("(2) - Menu de produtos");
            System.out.println("(3) - Menu de relatórios");
            System.out.println("(0) - Logout");
            String command = scanner.nextLine();
            switch (command) {
                case "1": estoqueController.displayMenuEstoque(usuario); break;
                case "2": produtoController.displayMenuProduto(usuario); break;
                case "3": relatorioController.displayMenuRelatorios(usuario); break;
                case "0": estaLogado = false; break;
            }
        }
        OutputUtil.limpaTela();
        displayLogin();
    }

    private void displayMenuFuncionario(boolean estaLogado, Usuario usuario) {
        while(estaLogado) {
            System.out.println("Menu do Funcionario");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println(SEPARADOR);
            System.out.println("(1) - Menu de estoque");
            System.out.println("(2) - Menu de produtos");
            System.out.println("(0) - Logout");
            String command = scanner.nextLine();
            switch (command) {
                case "1": estoqueController.displayMenuEstoque(usuario); break;
                case "2": produtoController.displayMenuProduto(usuario); break;
                case "0": estaLogado = false; break;
            }
        }
        OutputUtil.limpaTela();
        displayLogin();
    }
}
