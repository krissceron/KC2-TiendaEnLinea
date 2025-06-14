package com.tuempresa.facturacion.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.tuempresa.facturacion.calculadora.*;

import lombok.*;

@Embeddable @Getter @Setter
public class Detalle {

    int cantidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Producto producto;

    @DefaultValueCalculator(
        value = CalculadorDescuentoPorUnidad.class,
        properties = {
            @PropertyValue(name = "numeroProducto", from = "producto.numero"),
            @PropertyValue(name = "cantidad", from = "cantidad")
        }
    )
    @Stereotype("PERCENTAGE")
    BigDecimal descuentoAplicado;

    @DefaultValueCalculator(
        value = CalculadorPrecioPorUnidad.class,
        properties = @PropertyValue(name = "numeroProducto", from = "producto.numero")
    )
    @Money
    BigDecimal precioPorUnidad;

    //ESTA ES LA CLAVE
    @Transient
    @Depends("precioPorUnidad, cantidad, descuentoAplicado")
    public BigDecimal getImporte() {
        if (precioPorUnidad == null || cantidad == 0) return BigDecimal.ZERO;

        BigDecimal subtotal = new BigDecimal(cantidad).multiply(precioPorUnidad);

        if (descuentoAplicado != null) {
            BigDecimal descuentoDecimal = descuentoAplicado.divide(new BigDecimal(100));
            BigDecimal montoDescuento = subtotal.multiply(descuentoDecimal);
            return subtotal.subtract(montoDescuento);
        }

        return subtotal;
    }
}
