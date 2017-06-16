package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Simulacion simulacion = new Simulacion();
        simulacion.tamannoMatriz = 10;
        simulacion.probabilidadMutacion = 7;
        simulacion.esperanzaVida = 100;
        simulacion.tamannoPoblacion = 10000;
        simulacion.maximoGeneraciones = 1000;
        ArrayList<Integer> numerosNecesarios = new ArrayList<Integer>();
        ArrayList<Matriz> poblacion = new ArrayList<Matriz>();
        ArrayList<Matriz> bucket = new ArrayList<Matriz>();
        int tamannoMatriz = simulacion.tamannoMatriz;

        for(int i = 1; i <= tamannoMatriz * tamannoMatriz; i++)
        {
            numerosNecesarios.add(i);
        }

        int tamanoPoblacion = 2500; //Tamanno inicial

        //Se define constante magica
        int constanteMagica = tamannoMatriz * (tamannoMatriz * tamannoMatriz
                + 1) / 2;

        //Realiza la creacion de poblacion segun el porcentaje deseado;
        poblacion = simulacion.crearPoblacion(numerosNecesarios,tamanoPoblacion,tamannoMatriz,constanteMagica);

        //Resultado que debe dar el fitness para que sea correcto
        int resultadoFinal = tamannoMatriz + tamannoMatriz + 2;
        System.out.println("Resultado Final: ");

        simulacion.generacion = 0;

        while(simulacion.generacion < simulacion.maximoGeneraciones){

            System.out.println("Ronda #" + simulacion.generacion);
            int fitnessActual = 0;
            int fitnessMaximo = 0;
            boolean encontrado = false;

            simulacion.sumaFitness = 0;

            for(int i = 0; i < poblacion.size(); i++){ //Se debe usar poblacion.size()
                poblacion.get(i).edad++;

                if(poblacion.get(i).edad == simulacion.esperanzaVida + 1){
                    //simulacion.sumaFitness -= fitnessActual;
                    poblacion.remove(i);
                    //qDebug("Individuo borrado");
                    i--; //Retrocede el contador para que evalue al siguiente
                    continue;
                }


                fitnessActual = poblacion.get(i).fitness;
                simulacion.sumaFitness += fitnessActual;

             /*if (fitnessActual > 6){
                 cout<<"Fitness en la pos "<<i<<" = "<<fitnessActual<<endl;
             }*/

                if (resultadoFinal == fitnessActual){
                    //Aqui se debe presentar en pantalla la solucion
                    simulacion.cuadradoMagico = poblacion.get(i);
                    poblacion.get(i).toStringA();
                    encontrado = true;
                    break;
                } else {
                    if(fitnessMaximo < poblacion.get(i).getFitness())
                    {
                        fitnessMaximo = poblacion.get(i).getFitness();
                    }
                }
            }

            tamanoPoblacion = poblacion.size();
            simulacion.tamannoPoblacion = poblacion.size();
            //Seleccion Natural
            bucket = simulacion.seleccionNatural(poblacion, tamanoPoblacion,
                    fitnessMaximo);

            //Generaciones
            poblacion = simulacion.cruzarPoblacion(tamanoPoblacion, bucket.size(),
                    bucket, poblacion,
                    constanteMagica,
                    simulacion.probabilidadMutacion
            );
            tamanoPoblacion =  poblacion.size();
            while(tamanoPoblacion > simulacion.maximoPoblacion)
            {
                int indice = (int)Math.random() % tamanoPoblacion;
                poblacion.remove(indice);
                tamanoPoblacion = poblacion.size();
            }

            simulacion.tamannoPoblacion = poblacion.size();
            System.out.println(poblacion.size() + '\n' + "end");
            simulacion.generacion++;
        }

    }
}
