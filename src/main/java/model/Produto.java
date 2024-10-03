package model;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double precoVenda;
    private double precoCusto;
    private int quantidade;

    public Produto() {}

    public Produto(String nome, String descricao, double precoVenda, double precoCusto, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
