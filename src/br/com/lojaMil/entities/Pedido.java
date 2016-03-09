package br.com.lojaMil.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 4972022553844643236L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idPedido;
	
	private int finalizado;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@Temporal(value=TemporalType.DATE)
	private Date data;
	
	@OneToMany(mappedBy="pedido")
	private List<PedidoItem> pedidoItems;
	
	private BigDecimal valorTotal;

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public int getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(int finalizado) {
		this.finalizado = finalizado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<PedidoItem> getPedidoItems() {
		return pedidoItems;
	}

	public void setPedidoItems(List<PedidoItem> pedidoItems) {
		this.pedidoItems = pedidoItems;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Soma o valor total do pedido e seta em this.ValorTotal
	 */
	public void calculaValorTotal() {
		BigDecimal soma = BigDecimal.ZERO;
		for (PedidoItem pi : pedidoItems) {
			BigDecimal s = pi.getValor().multiply(BigDecimal.valueOf(pi.getQuantidade()));
			soma = soma.add(s);
		}
		this.valorTotal = soma;
	}
	
}
