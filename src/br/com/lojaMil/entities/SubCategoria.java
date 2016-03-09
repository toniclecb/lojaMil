package br.com.lojaMil.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class SubCategoria implements Serializable {

	private static final long serialVersionUID = -7338041232516110049L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idSubCategoria;
	
	@NotNull
	@Length(min = 2, max = 80)
	private String descricao;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idCategoria")
	private Categoria categoria;
	
	public Long getIdSubCategoria() {
		return idSubCategoria;
	}

	public void setIdSubCategoria(Long idSubCategoria) {
		this.idSubCategoria = idSubCategoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
