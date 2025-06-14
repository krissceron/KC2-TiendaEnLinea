package com.tuempresa.facturacion.acciones;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.tuempresa.facturacion.modelo.*;

public class ConvertirPedidoAFactura extends TabBaseAction  {

    @Override
    public void execute() throws Exception {

    	
    	
    	
    	

    	
    	Object[] seleccionados = getSelectedKeys();

    	if (seleccionados == null || seleccionados.length == 0) {
    	    addError("Debes seleccionar al menos un pedido desde la lista.");
    	    return;
    	}

    	Object clave = seleccionados[0];

    	if (clave instanceof Map) {
    	    clave = ((Map<?, ?>) clave).get("oid"); // Asume que el campo ID se llama "id"
    	}

    	Pedido pedido = XPersistence.getManager().find(Pedido.class, clave);


        if (pedido == null) {
            addError("No se encontró el pedido seleccionado");
            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            addError("El pedido no está en estado PENDIENTE");
            return;
        }

        // Crear la factura
        Factura factura = new Factura();
        factura.setCliente(pedido.getCliente());
        factura.setDetalles(pedido.getDetalles());
        factura.setObservaciones(pedido.getObservaciones());
        factura.setPorcentajeIVA(pedido.getPorcentajeIVA());
        factura.setFecha(pedido.getFecha()); // copia la fecha
        factura.setAnyo(pedido.getAnyo()); // probablemente es setAnyo()
        factura.setNumero(pedido.getNumero()); // copia el número

        // guarda la factura
        XPersistence.getManager().persist(factura);


        // Copiar detalles
        Collection<Detalle> nuevosDetalles = new ArrayList<>();
        for (Detalle d : pedido.getDetalles()) {
            Detalle nuevo = new Detalle();
            nuevo.setProducto(d.getProducto());
            nuevo.setCantidad(d.getCantidad());
            nuevo.setPrecioPorUnidad(d.getPrecioPorUnidad());
            nuevo.setDescuentoAplicado(d.getDescuentoAplicado());
            nuevosDetalles.add(nuevo);
        }

        factura.setDetalles(nuevosDetalles);

        XPersistence.getManager().persist(factura);

        //Opcional: cambiar estado a CONFIRMADO
        pedido.setEstado(EstadoPedido.CONFIRMADO);

        addMessage("Factura generada correctamente");
    }
}
