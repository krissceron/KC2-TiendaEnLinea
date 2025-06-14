package com.tuempresa.facturacion.modelo;
 
import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Entity 
@Getter @Setter 
@View(name="Simple", members="numero, nombre" // Muestra �nicamente numero y nombre en la misma l�nea
)
public class Cliente {
 
    @Id  
    @Column(length=6)  
    int numero;
 
    @Column(length=50) 
    @Required 
    String nombre;//cambio kcccc
    
    
    @Embedded // As� para referenciar a una clase incrustable
    Direccion direccion; // Una referencia Java convencional
 
}