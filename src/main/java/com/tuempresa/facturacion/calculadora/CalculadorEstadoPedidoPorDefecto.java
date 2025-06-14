package com.tuempresa.facturacion.calculadora;

import org.openxava.calculators.*;

import com.tuempresa.facturacion.modelo.*;

public class CalculadorEstadoPedidoPorDefecto implements ICalculator {
	 @Override
	    public Object calculate() throws Exception {
	        return EstadoPedido.EN_CARRITO;
	    }
}
