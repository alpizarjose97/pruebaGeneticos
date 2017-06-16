package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collector;

/**
 * Created by Toño_PC on 15/6/2017.
 */
public class Matriz {
    public int tamanno;
    public int fitness;
    public int edad;
    public ArrayList<ArrayList<Integer>> matrizDatos;


    public Matriz(int tama, ArrayList<Integer> numeros, int constanteMagica) {
        this.tamanno = tama;
        this.edad = 0;
        int tamRandom = tamanno*tamanno-1;

        for(int i = 0;i<tama;i++)
        {
            //Controla las columnas
            ArrayList<Integer> filas  = new ArrayList<Integer>();

            for(int j = 0;j<tama;j++)
            {
                int indice;
                if(tamRandom == 0)
                {
                    indice = 0;
                }
                else
                {
                    indice = ((int)Math.random() % tamRandom);
                }
                int numeroM = numeros.get(indice);
                Collections.swap(numeros,indice,tamRandom);
                filas.add(numeroM);
                tamRandom--;
            }
            this.matrizDatos.add(filas);
        }
        this.fitness = this.funcionFitness(constanteMagica);
    }

    int funcionFitness(int numeroMagico)
    {
        int score = 0;
        //Saca el score de las filas
        int resul;
        for(int i =0;i<this.tamanno;i++)
        {
            resul = 0;
            for(int j =0;j<this.tamanno;j++)
            {
                resul += this.matrizDatos.get(i).get(j);
            }
            if(numeroMagico == resul)
            {
                score++;
            }
        }


        //Saca el score de las columnas

        for(int i =0;i<this.tamanno;i++)
        {
            resul = 0;
            for(int j =0;j<this.tamanno;j++)
            {
                resul += this.matrizDatos.get(j).get(i);
            }
            if(numeroMagico == resul)
            {
                score++;
            }
        }

        //Saca el score de las diagonales
        //Diagonal
        resul = 0;
        for(int i =0;i<this.tamanno;i++)
        {
            resul += this.matrizDatos.get(i).get(i);
        }
        if(numeroMagico == resul)
        {
            score++;
        }
        //Diagonal inversa
        resul = 0;
        int temp = this.tamanno-1;
        for(int i =0;i<this.tamanno;i++)
        {

            resul += this.matrizDatos.get(i).get(temp);
            temp--;
        }
        if(numeroMagico == resul)
        {
            score++;
        }

        return score;


    }


    void mutacion() {
        int indice1 = (int) Math.random() % this.tamanno;
        int indice2 = (int) Math.random() % this.tamanno;

        int indice3 = (int) Math.random() % this.tamanno;
        int indice4 = (int) Math.random() % this.tamanno;

        int num1 = this.matrizDatos.get(indice1).get(indice2);
        int num2 = this.matrizDatos.get(indice3).get(indice4);

        this.matrizDatos.get(indice1).set(indice2, num2);

        this.matrizDatos.get(indice3).set(indice4, num1);
    }
    
    public int getTamanno() {
        return tamanno;
    }

    public void setTamanno(int tamanno) {
        this.tamanno = tamanno;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public ArrayList<ArrayList<Integer>> getMatrizDatos() {
        return matrizDatos;
    }

    public void setMatrizDatos(ArrayList<ArrayList<Integer>> matrizDatos) {
        this.matrizDatos = matrizDatos;
    }


}
