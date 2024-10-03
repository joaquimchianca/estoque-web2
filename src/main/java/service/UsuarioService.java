package service;

import config.ScannerSingleton;
import model.Papel;
import model.Usuario;
import repository.UsuarioRepository;
import util.OutputUtil;

import java.util.Scanner;

import static util.OutputUtil.SEPARADOR;

public class UsuarioService {

    private Scanner scanner;
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.scanner = ScannerSingleton.getInstance();
        this.usuarioRepository = new UsuarioRepository();
    }

    public void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        System.out.println("Cadastro de usuario");
        System.out.println(SEPARADOR);
        System.out.println("Nome:");
        usuario.setNome(scanner.nextLine());

        System.out.println("Email:");
        usuario.setEmail(scanner.nextLine());

        System.out.println("Senha:");
        String senha = scanner.nextLine();
        System.out.println("Confirmação da senha: (digite-a novamente)");
        String confirmacaoSenha = scanner.nextLine();

        if ( !(senha.equals(confirmacaoSenha)) ) {
            System.out.println("[ERRO] - Senhas não coincidem.");
            throw new RuntimeException();
        }

        usuario.setSenha(senha);

        System.out.println("Escolha um papel dentro da loja para o usuário novo:");
        System.out.println("(1) - Administrador");
        System.out.println("(2) - Gerente");
        System.out.println("(3) - Funcionario");

        String papelEscolha = scanner.nextLine();
        switch (papelEscolha) {
            case "1": usuario.setPapel(Papel.ADMINISTRADOR); break;
            case "2": usuario.setPapel(Papel.GERENTE); break;
            case "3": usuario.setPapel(Papel.FUNCIONARIO); break;
            default: throw new RuntimeException();
        }

        System.out.println("\nNome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Papel: " + usuario.getPapel().toString());


        System.out.println("Salvar este usuário? (y/N)");
        String save = scanner.nextLine();

        if ( save.equalsIgnoreCase("y") ) {
            usuarioRepository.salva(usuario);
        } else if ( save.equalsIgnoreCase("n") ) {
            System.out.println("Usuario não cadastrado.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cadastrarUsuario();
        } else {
            throw new RuntimeException();
        }
    }

    public Usuario login(String email, String senha) {
        return usuarioRepository.loginERetornaPapel(email, senha);
    }

    public void removerUsuario() {
        System.out.println("Digite o email do usuário a ser removido:");
        String email = scanner.nextLine();

        Usuario usuario = usuarioRepository.buscaUsuarioPorEmail(email);

        System.out.println(SEPARADOR);
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Papel: " + usuario.getPapel().toString());

        System.out.println("\nTem certeza que deseja remover este usuário? (y/N)");
        String save = scanner.nextLine();

        if ( save.equalsIgnoreCase("y") ) {
            usuarioRepository.removerUsuario(usuario.getId());
        } else if ( save.equalsIgnoreCase("n") ) {
            System.out.println("\nOperação cancelada.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            OutputUtil.limpaTela();
        }
    }

    public void editarUsuario() {
        System.out.println("Digite o email do usuário que deseja editar:");
        String email = scanner.nextLine();
        Usuario usuario = usuarioRepository.buscaUsuarioPorEmail(email);
        String nome = null;
        String emailEditado = null;
        String senhaEditado = null;
        String papelStr = "0";
        Papel papel = Papel.SEM_PAPEL;

        System.out.println("\nDeixe em branco as informações que não deseja editar.");
        System.out.println("Editar o nome: ");
        nome = scanner.nextLine();
        System.out.println("Editar o email: ");
        emailEditado = scanner.nextLine();
        System.out.println("Editar a senha: ");
        senhaEditado = scanner.nextLine();
        System.out.println("Editar papel: ");
        System.out.println("(1) - Administrador");
        System.out.println("(2) - Gerente");
        System.out.println("(3) - Funcionario");
        System.out.println(SEPARADOR);
        papelStr = scanner.nextLine();
        switch (papelStr) {
            case "1": papel = Papel.ADMINISTRADOR; break;
            case "2": papel = Papel.GERENTE; break;
            case "3": papel = Papel.FUNCIONARIO; break;
            case "0": papel = Papel.SEM_PAPEL; break;
        }
        Usuario usuarioEditado = editarInformacoesUsuario(usuario, nome, emailEditado, senhaEditado, papel);
        usuarioRepository.editar(usuarioEditado);

        System.out.println(SEPARADOR);
        System.out.println("Nome: " + usuarioEditado.getNome());
        System.out.println("Email: " + usuarioEditado.getEmail());
        System.out.println("Senha: " + usuarioEditado.getSenha());
        System.out.println("Papel: " + usuarioEditado.getPapel().toString());

        System.out.println("\nConfirma entrada de novas informações? (y/N)");
        String save = scanner.nextLine();
        if (save.equalsIgnoreCase("y")) {
            usuarioRepository.editar(usuario);
        } else if (save.equalsIgnoreCase("n") ) {
            System.out.println("Operação cancelada.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            OutputUtil.limpaTela();
        }
    }

    private Usuario editarInformacoesUsuario(Usuario usuario, String nome, String email, String senha, Papel papel) {
        if (!(nome.isBlank() || nome == null) && !(usuario.getNome().equals(nome)) ) {
            usuario.setNome(nome);
        }

        if (!(email.isBlank() || email == null) && !(usuario.getEmail().equals(email)) ) {
            usuario.setEmail(email);
        }

        if (!(senha.isBlank() || senha == null) && !(usuario.getSenha().equals(senha)) ) {
            usuario.setSenha(senha);
        }

        if (papel != Papel.SEM_PAPEL && (usuario.getPapel() != papel)) {
            usuario.setPapel(papel);
        }
        return usuario;
    }
}
