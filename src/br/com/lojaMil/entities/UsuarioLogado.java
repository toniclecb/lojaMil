package br.com.lojaMil.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;


@Component
@SessionScoped
public class UsuarioLogado {

    private Usuario usuario;
	private Pedido pedido;

    public Usuario getUser() {
        return usuario;
    }

    public void login(Usuario user) {
        this.usuario = user;
    }

    public void logout() {
        this.usuario = null;
    }

    public boolean isLogged(){
    	return this.usuario != null;
    }

    /**
     * Adiciona um novo pedidoItem ao pedido
     * @param pedidoItem
     * @param prod 
     */
	public void compraItem(PedidoItem pedidoItem, Produto prod) {
		// se nao existe pedido nessa sessao do usuario, cria
		if (pedido == null){
			pedido = new Pedido();
			pedido.setData(new Date());
			pedido.setFinalizado(0);
		}
		// inicia lista de pedidoitem caso nao tenha iniciado
		if (pedido.getPedidoItems() == null){
			pedido.setPedidoItems(new ArrayList<PedidoItem>(1));
		}
		// adiciona o pedidoitem
		pedidoItem.setPedido(pedido);
		pedidoItem.setProduto(prod);
		pedidoItem.setValor(prod.getPrecoVenda().
				multiply(BigDecimal.valueOf(pedidoItem.getQuantidade())));
		pedido.getPedidoItems().add(pedidoItem);
	}

	public int getPedidoItensSize() {
		if (pedido == null || pedido.getPedidoItems() == null)
			return 0;
		else
			return pedido.getPedidoItems().size();
	}

	public Pedido getPedido() {
		if (pedido == null){
			pedido = new Pedido();
			pedido.setData(new Date());
			pedido.setFinalizado(0);
		}
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
