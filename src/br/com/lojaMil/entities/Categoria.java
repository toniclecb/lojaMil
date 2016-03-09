package br.com.lojaMil.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = -1372535606031554241L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idCategoria;
	
	@NotNull
	@Length(min = 2, max = 80)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="idDepartamento")
	private Departamento departamento;

	@OneToMany(mappedBy = "categoria")
    private List<SubCategoria> subCategorias;
	
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}
}
