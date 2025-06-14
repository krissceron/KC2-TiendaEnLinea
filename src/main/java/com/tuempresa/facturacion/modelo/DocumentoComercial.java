package com.tuempresa.facturacion.modelo;

import java.math.*;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;

import com.tuempresa.facturacion.calculadora.*;

import lombok.*;

@View(members =
"anyo, numero, fecha;" +
"datos {" +
    "cliente;" +
    "detalles;" +
    "observaciones;" +
"}"
)
@Entity 
@Getter 
@Setter
abstract public class DocumentoComercial extends Identificable {

	@Column(length=4)
	@DefaultValueCalculator(CurrentYearCalculator.class)
	int anyo;
	
	@Column(length=6)
	@DefaultValueCalculator(
	    value = CalculadorSigueinteNumeroParaAnyo.class,
	    properties = @PropertyValue(name="anyo")
	)
	int numero;
	
	@Required
	@DefaultValueCalculator(CurrentLocalDateCalculator.class)
	LocalDate fecha;
	
	@Stereotype("MEMO")
	String observaciones;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@ReferenceView("Simple")
	Cliente cliente;
	
	@ElementCollection
	@ListProperties(
	    "producto.numero, producto.descripcion, cantidad, precioPorUnidad, descuentoAplicado, importe"
	)
	private Collection<Detalle> detalles;

	
	@Digits(integer=2, fraction=0)
	BigDecimal porcentajeIVA;
	
	@ReadOnly
	@Money
	@Calculation("sum(detalles.importe) * porcentajeIVA / 100")
	BigDecimal iva;
	
	@ReadOnly
	@Money
	@Calculation("sum(detalles.importe) + iva")
	BigDecimal importeTotal;
}

