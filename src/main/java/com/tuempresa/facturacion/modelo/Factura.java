package com.tuempresa.facturacion.modelo;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity 
@Getter @Setter 
@View(extendsView="super.DEFAULT", // Extiende de la vista de DocumentoComercial
members="pedidos { pedidos }" // Añadimos pedidos dentro de una pestaña
)
@View( name="SinClienteNiPedidos", // Una vista llamada SinClienteNiPedidos
members=                       // que no incluye cliente ni pedidos
    "anyo, numero, fecha;" +   // Ideal para usarse desde Pedido
    "detalles;" +
    "observaciones"
)
public class Factura extends DocumentoComercial {
    @OneToMany(mappedBy="factura")
    @CollectionView("SinClienteNiFactura") // Esta vista se usa para visualizar pedidos
    private Collection<Pedido> pedidos;
}