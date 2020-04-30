package com.rlsp.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "produto")
@Indexed // Mostra que  entidade sera INDEXIDA
@Analyzer(impl=BrazilianAnalyzer.class)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	/**
	 * Faz a INDEXACAO usando esse campo
	 * (store = Store.NO) ==> nao armazena em disco e tem que ir no DB para buscar a informacao indexada
	 * (store = Store.YES) ==> armazena em disco e nao precisa ir no DB para buscar informacao indexada
	 * (store = Store.COMPRESS) ==> armazena em disco(comprimido) e nao precisa ir no DB para buscar informacao indexada,
	 * 		mas gastara tempo de CPU para descompactar  
	 */
	@Field (store = Store.NO)
	private String nome;
	
	@Field //Faz a INDEXACAO usando esse campo
	private String descricao;
	
	private BigDecimal valor;
	
	@IndexedEmbedded
	@ManyToOne
	@JoinColumn(name="codigo_fabricante")
	private Fabricante fabricante;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
