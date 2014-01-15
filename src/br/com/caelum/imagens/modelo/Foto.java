package br.com.caelum.imagens.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Foto implements Serializable {
	@Id
	@GeneratedValue
	private long id;
	@Lob
	private byte[] imagem;
	private String descricao;
	@ManyToOne
	private Produto produto;
	
	public long getId() {
		return id;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public String getDescricao() {
		return descricao;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
