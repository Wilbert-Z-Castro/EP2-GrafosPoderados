/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.upemor.ep2_grafosconcaminos;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author WILBERT
 */
public class EP2_GrafosConCaminos {

    public static void main(String[] args) {
        System.out.println("Hello World!");
       menu();
    }
    public static void menu(){
        Grafo<Integer> grafo;
        grafo = new Grafo<>();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        int Nnodos=0,inicio,fin;
//
        while (!salir) {
            try{
                System.out.println("Menú:");
                System.out.println("1. Cargar grafo desde archivo");
                System.out.println("2. Realizar otra acción");
                System.out.println("0. Salir");
                System.out.print("Selecciona una opción: ");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        scanner.nextLine();
                        System.out.print("Ingrese el nombre del archivo a cargar: ");
                        String nombreArchivo = scanner.nextLine();
                        grafo.cargarGrafoDesdeArchivo(nombreArchivo);
                        System.out.println("Grafo cargado exitosamente.");
                        grafo.mostrtarListaAdyacencias();
                        grafo.ShowGrafo();
                        break;
                    case 2:
                        try{
                            grafo.mostrtarClavesT3();
                            System.out.println("Ingrese ID del nodo que quiere empezar: ");
                            inicio=scanner.nextInt();
                            int Nnodos2=0;
                            Nnodos=0;
                            for(Vertice v : grafo.getVertices()){    
                                System.out.println("vertice clave:"+v.getClave());
                                if(v.getClave().equals(inicio)){

                                    grafo.mostrtarClavesT1();
                                    System.out.println("Ingrese ID del nodo al que quiere llegar: ");
                                    fin=scanner.nextInt();

                                    for(Vertice u:grafo.getVertices()){
                                        if(u.getClave().equals(fin)){
                                            grafo.arbolGeneradorPorAnchura(grafo.getVertices().get(Nnodos),grafo.getVertices().get(Nnodos2));
                                        }
                                        Nnodos2+=1;
                                    }

                                }
                                Nnodos+=1;
                            }
                        }catch(InputMismatchException e){
                            scanner.nextLine();
                            System.out.println("ERROR, debes ingresa un numero entero");

                        }
//grafo.mostrtarListaAdyacencias();

                        break;
                    case 3:
                        System.out.println("Datos del grafo:");
                        grafo.mostrtarListaAdyacencias();
                        grafo.ShowGrafo();
                        break;
                    case 4:
                            int i=0;
                            for(Vertice tmp:grafo.getVertices()){
                                System.out.println("valor de i: "+i);
                                grafo.arbolGeneradorPorAnchura(grafo.getVertices().get(0),grafo.getVertices().get(i));
                                i++;
                            }
                            
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            }catch(InputMismatchException e){
                scanner.nextLine();
                System.out.println("ERROR, debes ingresa un numero entero");
            }    
        }
    }
}
