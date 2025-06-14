package com.tuempresa.facturacion.acciones;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.tuempresa.facturacion.modelo.*;

public class AgregarAlCarritoAction extends ViewBaseAction {
	@Override
    public void execute() throws Exception {
        String oidProducto = getView().getValueString("oid"); // obtenemos el producto actual

        // Suponiendo que ya conoces el cliente (usuario logueado)
        Cliente cliente = XPersistence.getManager()
                .find(Cliente.class, 1); //ID quemado por ahora. Luego puedes usar usuario actual

        Pedido carrito = obtenerOCrearCarrito(cliente);

        Producto producto = XPersistence.getManager().find(Producto.class, oidProducto);

        // Crear nuevo detalle
        Detalle detalle = new Detalle();
        detalle.setCantidad(1); // por defecto 1
        detalle.setProducto(producto);
        detalle.setPrecioPorUnidad(producto.getPrecio());
        if (carrito.getDetalles() == null) {
            carrito.setDetalles(new ArrayList<>());
        }
        // Agregar al pedido (carrito)
        carrito.getDetalles().add(detalle);
        XPersistence.getManager().merge(carrito); // guardar
        addMessage("Producto agregado al carrito correctamente.");
    }
	
	private Pedido obtenerOCrearCarrito(Cliente cliente) {
        EntityManager em = XPersistence.getManager();
        List<Pedido> pedidos = em.createQuery(
                "from Pedido p where p.cliente = :cliente and p.estado = :estado", Pedido.class)
                .setParameter("cliente", cliente)
                .setParameter("estado", EstadoPedido.EN_CARRITO)
                .getResultList();

        if (!pedidos.isEmpty()) return pedidos.get(0);

        // Si no existe, lo creamos
        Pedido nuevo = new Pedido();
        nuevo.setCliente(cliente);
        nuevo.setEstado(EstadoPedido.EN_CARRITO);
        nuevo.setAnyo(Calendar.getInstance().get(Calendar.YEAR));
        nuevo.setFecha(java.time.LocalDate.now());
        nuevo.setDetalles(new ArrayList<>());

        em.persist(nuevo);
        return nuevo;
    }
}
