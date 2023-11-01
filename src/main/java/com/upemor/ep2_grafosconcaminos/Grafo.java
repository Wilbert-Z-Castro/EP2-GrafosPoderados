/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upemor.ep2_grafosconcaminos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author WILBERT
 */


@Getter
@Setter
public class Grafo<TipoClave>{
    
    private String nombre;
    private List<Vertice<TipoClave>> vertices;
    
    public Grafo(){
        vertices = new LinkedList<>();
    }
    //contructor para crear una copia del grafo
    // buena pratica usar nombres en ingles 
   
    

    @Override
    public String toString(){
        String cadena = "";
            cadena += nombre+" -> [";
            for(Vertice v:vertices) cadena += v+"[benaodso]";
            cadena += "]";
        return cadena.replace(",]","]");
    }
    
    public void agregarVertice(Vertice vertice){
        vertices.add(vertice);
    }
    
    public void mostrtarListaAdyacencias(){
        System.out.println("\nLista de adyacencias");
        for(Vertice v:vertices){
            System.out.println(v.listaDeAdyacencia()+"["+v.getPadre()+","+v.getCostoV()+"]");
        }
        System.out.println("");
    }
    public void mostrtarClavesT3(){
        String cadena = "";
        System.out.println("\nLista de Routers");
        for(Vertice v:vertices){
            if(v.getTipoEquipo()==3){
                cadena +="ID:"+v.getClave()+",Nombre:"+v.getNombre()+",";
            } 
        }
        System.out.println(cadena+"");
    }
    public void mostrtarClavesT1(){
        String cadena = "";
        System.out.println("\nLista de Host");
        for(Vertice v:vertices){
            if(v.getTipoEquipo()==1){
                cadena +="ID:"+v.getClave()+",Nombre:"+v.getNombre()+",";
            } 
        }
        System.out.println(cadena+"");
    }
    
    public boolean recorridoEnAnchura(Vertice<TipoClave> inicio){
        boolean check=false;
        Queue<Vertice<TipoClave>> cola = new LinkedList<>();
        inicio.setVisitado(true);
        cola.add(inicio);
        System.out.println(" inicio->");
        Vertice<TipoClave> actual = null;
        while(!cola.isEmpty()){
            actual = cola.poll();
            System.out.print(actual+"->");
            for(Arista arista:actual.getAdyacencias()){
                if(!arista.getVertice().isVisitado()){
                    arista.getVertice().setVisitado(true);
                    cola.add(arista.getVertice());
                }
            }
        }
        for(Vertice<TipoClave> vertice: vertices){
            if(vertice.isVisitado()){
                vertice.setVisitado(false);
            }else{
                check=true;
            }
            
        }
        System.out.println("fin");
        return check;
    }
    
    public boolean recorridoEnProfundidad(Vertice<TipoClave> inicio){
        boolean check=false;
        Stack<Vertice<TipoClave>> cola = new Stack<>();
        inicio.setVisitado(true);
        cola.add(inicio);
        System.out.println("inicio->");
        Vertice<TipoClave> actual;
        while(!cola.isEmpty()){
            actual = cola.pop();
            System.out.print(actual+"->");
            
            for(int i=actual.getAdyacencias().size()-1;i>=0;i--){
                Arista arista = actual.getAdyacencias().get(i);
                if(!arista.getVertice().isVisitado()){
                    arista.getVertice().setVisitado(true);
                    cola.add(arista.getVertice());
                }   
            }
        }
        for(Vertice<TipoClave> vertice: vertices){
            if(vertice.isVisitado()){
                vertice.setVisitado(false);  
            }else{
                check=true;
            }
        }
        System.out.println("fin");
        return check;
    }
   
    
    //metodo para ver los datos del grafo
    public int ShowGrafo() {
        int t1=0,t2=0,t3=0;
    for (Vertice<TipoClave> vertice : vertices) {
            if(vertice.getTipoEquipo()==1){
                t1=t1+1;
            }else{
                if(vertice.getTipoEquipo()==2){
                    t2=t2+1;
                }else{
                    t3=t3+1;
                }
            }
       }
        System.out.println("Numero total de host:" +vertices.size());
        System.out.println("Numero de host de tipo Router:"+t1);
        System.out.println("Numero de host de tipo Switch:"+t2);
        System.out.println("Numero de host de tipo Host:"+t3);
        return vertices.size();
    
    }
    
    public void arbolGeneradorPorAnchura(Vertice<TipoClave> inicio, Vertice<TipoClave> destino) {
        PriorityQueue<Vertice<TipoClave>> cola = new PriorityQueue<>(new Comparator<Vertice<TipoClave>>() {
            @Override
            public int compare(Vertice<TipoClave> v1, Vertice<TipoClave> v2) {
                return Double.compare(v1.getCostoV(), v2.getCostoV());
            }
        });

        inicio.setVisitado(true);
        inicio.setPadre(null);
        inicio.setCostoV(0.0);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            Vertice<TipoClave> actual = cola.poll();
            System.out.print("Vértice actual: " + actual + " ");

            if (actual == destino) {
                // Hemos encontrado el destino, puedes detener el proceso aquí
                break;
            }

            for (Arista arista : actual.getAdyacencias()) {
                Vertice<TipoClave> verticeHijo = arista.getVertice();
                //pedimos que sea 
                double costoArista = ((AristaPonderado) arista).getCosto();
                double nuevoCosto = actual.getCostoV() + costoArista;

                if (!verticeHijo.isVisitado() || nuevoCosto < verticeHijo.getCostoV()) {
                    verticeHijo.setPadre(actual);
                    verticeHijo.setVisitado(true);
                    verticeHijo.setCostoV(nuevoCosto);
                    cola.add(verticeHijo);
                }
            }
        }

        // Mostrar el camino desde el destino hasta el inicio
        //Lista para guardar momentarinamente 
        List<Vertice<TipoClave>> camino = new ArrayList<>();
        Vertice<TipoClave> nodo = destino;
        //hacemos el mismo recorido que hizo el profe
        while (nodo != null) {
            
            camino.add(nodo);
            nodo = nodo.getPadre();
        }
        //metodo para no tener que ir haciendo el declemento 
        Collections.reverse(camino);
        System.out.println("\nCamino más corto:");
        double totalc=0;
        for (Vertice<TipoClave> v : camino) {
            System.out.print(v +"->" );
            totalc=v.getCostoV();
        }
        System.out.println("El costo total del recorrido es: "+totalc);
    }
    
    //por si las dudas
//    public void arbolGeneradorPorAnchura(Vertice<TipoClave> inicio) {
//    Queue<Vertice<TipoClave>> cola = new LinkedList<>();
//    inicio.setVisitado(true);
//    inicio.setPadre(null);
//    inicio.setCostoV(0.0);
//    cola.add(inicio);
//
//    while (!cola.isEmpty()) {
//        Vertice<TipoClave> actual = cola.poll();
//        System.out.print("Vértice actual: " + actual + " ");
//
//        for (Arista arista : actual.getAdyacencias()) {
//            Vertice<TipoClave> verticeHijo = arista.getVertice();
//            double costoArista = ((AristaPonderado) arista).getCosto();
//            double nuevoCosto = actual.getCostoV() + costoArista;
//
//            if (!verticeHijo.isVisitado() || nuevoCosto < verticeHijo.getCostoV()) {
//                arista.getVertice().setPadre(actual);
//                
//                arista.getVertice().setVisitado(true);
//                arista.getVertice().setCostoV(nuevoCosto);
//                cola.add(verticeHijo);
//
//                System.out.println("Hijo: " + arista.getVertice().getClave() + ", Costo: " + nuevoCosto);
//            }
//        }
//    }
//
//    System.out.println("Fin del recorrido en anchura con Dijkstra.");
//}
    
    //ya bien suuuu
    public void cargarGrafoDesdeArchivo(String nombreArchivo) {
    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        String primeraLinea = br.readLine();
        String[] hostsInfo = primeraLinea.split(";");
        
        for (String hostInfo : hostsInfo) {
            String[] partesHost = hostInfo.split(",");
            int id = Integer.parseInt(partesHost[0]);
            String nombre = partesHost[1];
            int tipoEquipo = Integer.parseInt(partesHost[2]);
            Vertice<Integer> vertice = new Vertice<>();
            vertice.setClave(id);
            vertice.setNombre(nombre);
            vertice.setTipoEquipo(tipoEquipo);
            this.agregarVertice(vertice);
        }

        int idVerticeActual = 0;

        String linea;
        
        while ((linea = br.readLine()) != null) {
            String[] adyacenciaInfo = linea.split(";");
            
            for (String info : adyacenciaInfo) {
                String[] partesAdyacencia = info.split(",");
                
                if (partesAdyacencia.length == 2) {
                    int idVerticeDestino = Integer.parseInt(partesAdyacencia[0]);
                    double costo = Double.parseDouble(partesAdyacencia[1]);
                    
                    if (costo > 0) {
                        AristaPonderado arista = new AristaPonderado(vertices.get(idVerticeDestino - 1), costo);
                        vertices.get(idVerticeActual).agregarAdyasencia(arista);
                    }
                }
            }
            idVerticeActual++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    //bidirecciones mal
//public void cargarGrafoDesdeArchivo(String nombreArchivo) {
//    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
//        String primeraLinea = br.readLine();
//        String[] hostsInfo = primeraLinea.split(";");
//        
//        for (String hostInfo : hostsInfo) {
//            String[] partesHost = hostInfo.split(",");
//            int id = Integer.parseInt(partesHost[0]);
//            String nombre = partesHost[1];
//            int tipoEquipo = Integer.parseInt(partesHost[2]);
//            Vertice<Integer> vertice = new Vertice<>();
//            vertice.setClave(id);
//            vertice.setNombre(nombre);
//            vertice.setTipoEquipo(tipoEquipo);
//            this.agregarVertice(vertice);
//        }
//
//        int idVerticeActual = 0;
//        String linea;
//        
//        Map<Integer, Double> costosUnidireccionales = new HashMap<>();
//        
//        while ((linea = br.readLine()) != null) {
//            String[] adyacenciaInfo = linea.split(";");
//            
//            for (String info : adyacenciaInfo) {
//                String[] partesAdyacencia = info.split(",");
//                
//                if (partesAdyacencia.length == 2) {
//                    int idVerticeDestino = Integer.parseInt(partesAdyacencia[0]);
//                    double costo = Double.parseDouble(partesAdyacencia[1]);
//                    
//                    if (costo > 0) {
//                        if (costosUnidireccionales.containsKey(idVerticeDestino) && 
//                            costosUnidireccionales.get(idVerticeDestino) == costo) {
//                            // Adyacencia bidireccional con el mismo costo, eliminar costo unidireccional
//                            costosUnidireccionales.remove(idVerticeDestino);
//                        } else {
//                            // Adyacencia unidireccional o bidireccional con costo diferente
//                            costosUnidireccionales.put(idVerticeDestino, costo);
//                        }
//                        
//                        // Agregar la adyacencia en la dirección actual
//                        AristaPonderado arista = new AristaPonderado(vertices.get(idVerticeDestino - 1), costo);
//                        vertices.get(idVerticeActual).agregarAdyasencia(arista);
//                        
//                        // Verificar si la adyacencia inversa ya existe
//                        boolean adyacenciaInversaExiste = false;
//                        for (Arista a : vertices.get(idVerticeDestino - 1).getAdyacencias()) {
//                            if (a.getVertice().equals(vertices.get(idVerticeActual))) {
//                                adyacenciaInversaExiste = true;
//                                break;
//                            }
//                        }
//                        
//                        // Si no existe la adyacencia inversa, agregarla con el mismo costo
//                        if (!adyacenciaInversaExiste) {
//                            AristaPonderado aristaInversa = new AristaPonderado(vertices.get(idVerticeActual), costo);
//                            vertices.get(idVerticeDestino - 1).agregarAdyasencia(aristaInversa);
//                        }
//                    }
//                }
//            }
//            idVerticeActual++;
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}

//    no funciona bidirecionalidad
//public void cargarGrafoDesdeArchivo(String nombreArchivo) {
//    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
//        String primeraLinea = br.readLine();
//        String[] hostsInfo = primeraLinea.split(";");
//        
//        for (String hostInfo : hostsInfo) {
//            String[] partesHost = hostInfo.split(",");
//            int id = Integer.parseInt(partesHost[0]);
//            String nombre = partesHost[1];
//            int tipoEquipo = Integer.parseInt(partesHost[2]);
//            Vertice<Integer> vertice = new Vertice<>();
//            vertice.setClave(id);
//            vertice.setNombre(nombre);
//            vertice.setTipoEquipo(tipoEquipo);
//            this.agregarVertice(vertice);
//        }
//
//        int idVerticeActual = 0;
//
//        String linea;
//        
//        while ((linea = br.readLine()) != null) {
//            String[] adyacenciaInfo = linea.split(";");
//            
//            for (String info : adyacenciaInfo) {
//                String[] partesAdyacencia = info.split(",");
//                
//                if (partesAdyacencia.length == 2) {
//                    int idVerticeDestino = Integer.parseInt(partesAdyacencia[0]);
//                    double costo = Double.parseDouble(partesAdyacencia[1]);
//                    System.out.println(""+partesAdyacencia[0]);
//                            
//                    System.out.println("costo:"+costo);
//                    if (idVerticeDestino != idVerticeActual && costo > 0) {
//                        System.out.println("idverticeDestino:"+idVerticeDestino);
//                        System.out.println("idverticeatual:"+idVerticeActual);
//                        System.out.println("costo:"+costo);
//                        AristaPonderado arista = new AristaPonderado(vertices.get(idVerticeDestino - 1), costo);
//                        System.out.println("arista clave:"+arista.getVertice().getClave());
//                        vertices.get(idVerticeActual).agregarAdyasencia(arista);
//                    }
//                }
//            }
//            idVerticeActual++;
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}


}
