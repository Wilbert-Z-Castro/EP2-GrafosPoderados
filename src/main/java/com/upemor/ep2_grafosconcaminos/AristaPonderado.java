
package com.upemor.ep2_grafosconcaminos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

/**
 *
 * @author HP
 */
public class AristaPonderado extends Arista {
    //private long clave;
    
    private double costo;
    
    public AristaPonderado(Vertice vertice){
        this.vertice=vertice;
        this.costo=1;
    }
    
    public AristaPonderado(Vertice vertice, double costo){
        this.vertice=vertice;
        this.costo=costo;
    }
    
    @Override
    public String toString(){
        return "("+vertice +","+costo+")";
    }
    
    
    
    
    
}
