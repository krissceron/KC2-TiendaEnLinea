package com.tuempresa.facturacion.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.tuempresa.facturacion.calculadora.*;

import lombok.*;

@Entity
@Getter @Setter
@View(name="DEFAULT", // Vista principal
    members=
        "anyo, numero, fecha;" +
        "cliente;" +
        "detalles;" +
        "observaciones;" +
        "porcentajeIVA, iva, importeTotal;" +
        "factura;" +
        "estado"
)
@View(name="SinClienteNiFactura",
    members=
        "anyo, numero, fecha;" +
        "detalles;" +
        "observaciones"
)
@View(name="noEditarConfirmados",
    members=
        "anyo, numero, fecha;" +
        "estado;" +
        "cliente;" +
        "detalles;" +
        "observaciones"
)
public class Pedido extends DocumentoComercial {

    @ManyToOne
    @ReferenceView("SinClienteNiPedidos")
    private Factura factura;

    @Enumerated(EnumType.STRING)
    @DefaultValueCalculator(CalculadorEstadoPedidoPorDefecto.class)
    @ReadOnly(forViews="noEditarConfirmados")
    EstadoPedido estado;
}
