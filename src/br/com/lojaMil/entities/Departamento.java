package br.com.lojaMil.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Departamento implements Serializable {

	private static final long serialVersionUID = 1369043938408242498L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idDepartamento;
	
	@NotNull
	@Length(min = 2, max = 80)
	private String descricao;
	
	@OneToMany(mappedBy = "departamento")
    private List<Categoria> categorias;

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}
