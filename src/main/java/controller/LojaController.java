package controller;

import config.ScannerSingleton;
import model.Usuario;
import service.LojaService;

import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class LojaController {

    private Scanner scanner;
    private final LojaService lojaService;

    public LojaController() {
        this.scanner = ScannerSingleton.getInstance();
        this.lojaService = new LojaService();
    }

    public void displayMenuLoja(Usuario usuario) {
        System.out.println("Menu de Loja");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println(SEPARADOR);
        System.out.println("(1) - Cadastrar loja");
        String command = scanner.nextLine();
        switch (command) {
            case "1": lojaService.processaDadosCadastro();
        }
    }
}
