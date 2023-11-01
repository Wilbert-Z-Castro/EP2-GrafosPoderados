/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upemor.ep2_grafosconcaminos;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author WILBERT
 */


/** @author cerimice **/

@Getter
@Setter
public class Vertice<TipoClave>{
    //datos perticulares del vertice ferente a id(clave), nombre y tipode equipo ->  Es el tipo del equipo (Router (1), Switch (2), Host (3))
    private TipoClave clave;
    private String nombre;
    private int TipoEquipo;
    //datos relacionadas a la realizacion del recordio del grafo
    private boolean visitado;
    private Vertice<TipoClave> padre;
    private List<Arista> adyacencias;
    private double costoV;
    public Vertice(){
        adyacencias = new LinkedList<>();
    }
    
    @Override
    public String toString(){
        return ""+clave;
    }
    //arista es la adyasencia
    public void agregarAdyasencia(Arista adyasencia){
        adyacencias.add(adyasencia);
    }
    
  
//                for(Arista v:adyacencias) cadena += v+"["+v.getVertice().getPadre()+","+v.getVertice().getCostoV()+"]";

    public String listaDeAdyacencia(){
        String cadena = "";
            cadena += clave+" -> [";
            for(Arista v:adyacencias) cadena += v+",";
            cadena += "]";
        return cadena.replace(",]","]");
    }
    
}
