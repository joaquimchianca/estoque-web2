package service;

import config.ScannerSingleton;
import model.Produto;
import repository.ProdutoRepository;
import util.ConversionUtil;

import java.util.Scanner;

public class ProdutoService {

    private Scanner scanner;
    private ProdutoRepository produtoRepository;

    public ProdutoService() {
        scanner = ScannerSingleton.getInstance();
        produtoRepository = new ProdutoRepository();
    }

    public void processaDadosCadastro() {
        System.out.println("\n\n\n\n");
        System.out.println("Cadastro de produtos");
        System.out.println("----------------------------");
        System.out.println("Digite o nome do produto:");
        String nome = scanner.nextLine();

        System.out.println("Digite a descrição (opcional)");
        String descricao = scanner.nextLine();

        System.out.println("Digite o preço de custo: ");
        double precoCusto = ConversionUtil.stringToDouble(scanner.nextLine());

        System.out.println("Digite o preço de venda: ");
        double precoVenda = ConversionUtil.stringToDouble(scanner.nextLine());

        System.out.println("Digite a quantidade em estoque:");
        int quantidadeEstoque = ConversionUtil.stringToInt(scanner.nextLine());

        if (nome.isBlank()) {
            System.out.println("[ERRO] - Entrada inválida (valor em branco)");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            processaDadosCadastro();
        }

        if (precoCusto < 0 ||
            precoVenda <= 0 ||
            quantidadeEstoque < 0) {

            System.out.println("[ERRO] - Entrada inválida (número negativo)");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            processaDadosCadastro();
        }

        Produto produto = new Produto(nome, descricao, precoCusto, precoVenda, quantidadeEstoque);

        produtoRepository.salva(produto);
    }

    public void consultaProdutos() {
        System.out.println("\n\n\n\n");
        System.out.println("Consulta de produtos");
        System.out.println("----------------------------");
        System.out.println("Selecione a forma de consulta:\n");
        System.out.println("(1) - Consulta por nome");
        System.out.println("(2) - Consulta por código");
        System.out.println("(3) - Consulta por estoque");
        String command = scanner.nextLine();
        switch (command) {
            case "1": consultaProdutoPorNome(); break;
            case "2": consultaProdutoPorCodigo(); break;
            case "3": consultaProdutoPorEstoque(); break;
        }
    }

    private void consultaProdutoPorNome() {
        System.out.println("Pesquise o produto por nome:");
        produtoRepository.consultaPorNome(scanner.nextLine());
    }

    private void consultaProdutoPorCodigo() {
        System.out.println("Pesquise o produto por codigo:");
        produtoRepository.consultaPorCodigo(ConversionUtil.stringToInt(scanner.nextLine()));
    }

    private void consultaProdutoPorEstoque() {
        System.out.println("Digite a quantidade em estoque:");
        int estoque = ConversionUtil.stringToInt(scanner.nextLine());
        System.out.println("Filtrar produtos que:");
        System.out.println("(1) - Possuem quantidade em estoque maior que " + estoque);
        System.out.println("(2) - Possuem quantidade em estoque menor que " + estoque);
        System.out.println("(3) - Possuem quantidade em estoque igual a " + estoque);
        String command = scanner.nextLine();
        switch (command) {
            case "1": produtoRepository.consultaPorEstoque(estoque, "maior"); break;
            case "2": produtoRepository.consultaPorEstoque(estoque, "menor"); break;
            case "3": produtoRepository.consultaPorEstoque(estoque, "igual"); break;
        }
    }

    public void relatorioProdutosEstoque() {
        System.out.println("Qual o limite superior do estoque?");
        int limite = ConversionUtil.stringToInt(scanner.nextLine());
        produtoRepository.consultaPorEstoque(limite, "menor");
    }
}
