package com.tuempresa.facturacion.calculadora;
import static org.openxava.jpa.XPersistence.getManager;

import java.math.*;

import org.openxava.calculators.*;

import com.tuempresa.facturacion.modelo.*;

import lombok.*;

public class CalculadorDescuentoPorUnidad implements ICalculator {
	@Getter @Setter
    int numeroProducto;

    @Getter @Setter
    int cantidad;

    @Override
    public Object calculate() throws Exception {
        Producto producto = getManager().find(Producto.class, numeroProducto);
        if (producto == null || producto.getDescuento() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal descuento = producto.getDescuento(); // % como 10.00
        int stock = producto.getStock();

        if (stock > 100) {
            if (cantidad <= 3) {
                return descuento.divide(new BigDecimal(2)); // mitad del descuento
            } else {
                return descuento; // descuento completo
            }
        } else {
            if (cantidad >= 6) {
                return descuento; // descuento completo
            } else if (cantidad > 4) {
                return descuento.divide(new BigDecimal(2)); // mitad del descuento
            } else {
                return BigDecimal.ZERO;
            }
        }
    }
}
