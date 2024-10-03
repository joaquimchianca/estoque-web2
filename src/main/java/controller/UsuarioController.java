package controller;

import config.ScannerSingleton;
import model.Usuario;
import service.UsuarioService;
import util.OutputUtil;

import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class UsuarioController {

    private Scanner scanner;
    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.scanner = ScannerSingleton.getInstance();
        this.usuarioService = new UsuarioService();
    }

    public void displayMenuUsuario(Usuario usuario) {
        OutputUtil.limpaTela();
        System.out.println("Menu de usuários");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("(1) - Cadastrar usuário");
        System.out.println("(2) - Remover usuário");
        System.out.println("(3) - Editar usuário");
        String command = scanner.nextLine();
        switch (command) {
            case "1": usuarioService.cadastrarUsuario(); break;
            case "2": usuarioService.removerUsuario(); break;
            case "3": usuarioService.editarUsuario(); break;
        }
        OutputUtil.limpaTela();
    }

    public Usuario displayLogin() {
        System.out.println("Login");
        System.out.println(SEPARADOR);
        System.out.println("Digite seu email:");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha:");
        String senha = scanner.nextLine();
        return usuarioService.login(email, senha);
    }
}
