package controller;

import config.ScannerSingleton;
import model.Usuario;
import service.EstoqueService;

import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class EstoqueController {

    private Scanner scanner;
    private final EstoqueService estoqueService;

    public EstoqueController() {
        scanner = ScannerSingleton.getInstance();
        estoqueService = new EstoqueService();
    }

    public void displayMenuEstoque(Usuario usuario) {
        System.out.println("Menu de estoque");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println(SEPARADOR);
        System.out.println("(1) - Atualizar estoque de produto");
        String command = scanner.nextLine();
        switch (command) {
            case "1": estoqueService.atualizaEstoque(); break;
        }
    }
}
