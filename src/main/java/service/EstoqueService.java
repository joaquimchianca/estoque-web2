package service;

import config.ScannerSingleton;
import model.OperacaoEstoque;
import repository.EstoqueRepository;
import util.ConversionUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private Scanner scanner;

    public EstoqueService() {
        this.estoqueRepository = new EstoqueRepository();
        this.scanner = ScannerSingleton.getInstance();
    }

    public void imprimeRelatorioOperacoesEstoque() {
        System.out.println("Qual a data de início? (yyyy-MM-dd)");
        LocalDateTime inicio = ConversionUtil.stringToLocalDateTime(scanner.nextLine());
        System.out.println("Qual a data de fim? (yyyy-MM-dd)");
        LocalDateTime fim = ConversionUtil.stringToLocalDateTime(scanner.nextLine());

        List<OperacaoEstoque> operacoes = estoqueRepository.consulta(inicio, fim);
        for (OperacaoEstoque operacao : operacoes) {
            System.out.println("Produto ID: " + operacao.getProdutoId());
            System.out.println("Quantidade: " + operacao.getQuantidade());
            System.out.println("Tipo: " + operacao.getTipoOperacao());
            System.out.println("Data: " + operacao.getDataOperacao());
            System.out.println("-------------------");
        }
    }

    public void atualizaEstoque() {
        System.out.println("Atualização de estoque");
        System.out.println("----------------------------");
        System.out.println("Selecione a operação:");
        System.out.println("\n");
        System.out.println("(1) - Entrada");
        System.out.println("(2) - Saída");
        System.out.println("(3) - Balanço\n");
        String command = scanner.nextLine();

        System.out.println("Digite o ID do produto:");
        String idStr = scanner.nextLine();
        int id = Integer.parseInt(idStr);

        switch (command) {
            case "1": entradaEstoque(id); break;
            case "2": saidaEstoque(id); break;
            case "3": balancoEstoque(id); break;
        }
    }

    private void entradaEstoque(int id) {
        System.out.println("Qual o valor de entrada?");
        String entradaStr = scanner.nextLine();
        estoqueRepository.entradaEstoque(id, ConversionUtil.stringToInt(entradaStr));
        //        int entrada = Integer.parseInt(entradaStr);
        //        produtoRepository.entradaEstoque(id, entrada);
    }

    private void saidaEstoque(int id) {
        System.out.println("Qual o valor de saída?");
        String saidaStr = scanner.nextLine();
        estoqueRepository.saidaEstoque(id, ConversionUtil.stringToInt(saidaStr));
        //        int saida = Integer.parseInt(saidaStr);

    }

    private void balancoEstoque(int id) {
        System.out.println("Qual o valor do balanço?");
        String balancoStr = scanner.nextLine();
        estoqueRepository.balancoEstoque(id, ConversionUtil.stringToInt(balancoStr));
        //        int balanco = Integer.parseInt(balancoStr);
        //        produtoRepository.balancoEstoque(id, balanco);
    }
}
