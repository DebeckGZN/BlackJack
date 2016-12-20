package info.androidhive.blackjackbeer.models;

import java.util.ArrayList;
import java.util.Objects;

public class Produto {

    private String nome;
    private float preco;
    private String categoria; // ex.:cerveja, drink, etc
    public String descricao;
    private boolean situacao; //pendente ou retirado;
    private boolean disponibilidade;
    private String image;
    private String codigo;

    public Produto(String nome, float preco, String categoria, boolean disponibilidade){
        this.categoria = categoria;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.nome = nome;
        this.preco =preco;
        this.situacao = false;
    }
    /*
    public String gerarCodigo(){
        return null;
    }

    public void assocCodigo(Usuario usuario, Produto produto){
        int i=0;
        for(i = 0 ; i<usuario.comprados.size(); i++){
            if(usuario.comprados.get(i).equals(produto)){
                codigo = gerarCodigo();
            }
        }
    }

    /*public static void ordenarProdutos(MyAdapter adapter, ArrayList<Produto> result, String categoria){
        adapter.clear();
        for (Produto produto :  result){
            if(produto.categoria.equals(categoria))
                adapter.add(produto);
        }
    }
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (Float.floatToIntBits(this.preco) != Float.floatToIntBits(other.preco)) {
            return false;
        }
        if (this.situacao != other.situacao) {
            return false;
        }
        if (this.disponibilidade != other.disponibilidade) {
            return false;
        }
        /*
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        */
        return true;
    }

    public String getNome() {
        return nome;
    }

    public float getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setSituacao() {
        this.situacao = true;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

}
