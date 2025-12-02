package com.mycompany.poo_proyecto.model.pedido;

public enum EstadoPedido {
    CANCELADO("CANCELADO"),
    RESERVADO_WEB("RESERVA WEB"),
    PENDIENTE("PENDIENTE"),
    EN_PREPARACION("EN PREPARACIÓN"),
    LISTO("LISTO"),
    ENTREGADO("ENTREGADO");
    
    private String etiqueta;
    
    private EstadoPedido(String etiqueta){
        this.etiqueta=etiqueta; 
    }
    @Override
    public String toString(){
        return etiqueta;
    }
}
