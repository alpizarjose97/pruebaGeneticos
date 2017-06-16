package com.company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * Created by Toño_PC on 16/6/2017.
 */
public class Simulacion {
    /**
     * {@inheritDoc}
     */
    public double probabilidadMutacion;
    public int tamannoMatriz;
    public int maximoPoblacion;
    public int maximoGeneraciones;
    public int generacion;
    public int esperanzaVida;

    public long sumaFitness;
    public long tamannoPoblacion;

    Matriz cuadradoMagico;


    public Simulacion()
    {
        generacion = 0;
        sumaFitness = 0;
        tamannoPoblacion = 0;
        esperanzaVida = 1;
        cuadradoMagico = null;
    }

    public double calcularFitnessPromedio(){
        return (((double) sumaFitness / (double) tamannoPoblacion) /
                ((double)(2*tamannoMatriz + 2))) * 100;
    }

    public ArrayList<Matriz> crearPoblacion(ArrayList<Integer> numerosNecesarios,
                                            int tamaPoblacion, int tamMatriz,
                                            int constanteMagica)
    {
        ArrayList<Matriz> poblacion = new ArrayList<>();
        for(int i = 0;i<tamaPoblacion;i++)
        {

            Matriz nuevaMatriz = new Matriz(tamMatriz, numerosNecesarios,
                    constanteMagica);
            poblacion.add(nuevaMatriz);
        }
        return poblacion;
    }

    public ArrayList<Matriz> seleccionNatural(ArrayList<Matriz> poblacion, int porcentaje, int fitnessMaximo)
    {
        ArrayList<Matriz> bucket = new ArrayList<>();
        for(int i = 0;i<porcentaje;i++)
        {
            int fitnessMatriz = poblacion.get(i).fitness;
            double div = (double)fitnessMatriz / (double)fitnessMaximo;
            double porFitness = Math.floor(div * 100.00);
            for(int j = 0; j < porFitness; j++)
            {
                bucket.add(poblacion.get(i));
            }
        }
        return bucket;
    }


    ArrayList<Matriz> cruzarPoblacion(int tamPoblacion,int tamBucket,
                                      ArrayList<Matriz> bucket,
                                      ArrayList<Matriz> poblacion,int numeroMagico,
                                               double probMutacion)
    {

        for(int i = 0; i<tamPoblacion; i++)
        {
            Random rng = new Random();
            int a = rng.nextInt(tamBucket);
            int b = rng.nextInt(tamBucket);

            Matriz padre = bucket.get(a);
            Matriz madre = bucket.get(b);
            int tamUniversal = padre.getTamanno();

            ArrayList<Integer> vFather = pasarAVector(padre);
            ArrayList<Integer> vMother = pasarAVector(madre);

            ArrayList<ArrayList<Integer>> vHijos = new ArrayList<ArrayList<Integer>>();
            vHijos = cruzar(vFather,vMother);

            ArrayList<Integer> hijo1 = vHijos.get(0);
            ArrayList<Integer> hijo2 = vHijos.get(1);

            //delete vHijos;

            Matriz hijoMatriz = new Matriz();
            hijoMatriz.setMatrizDatos(pasarAMatriz(hijo1));


            int numeroRandom = (int)Math.random() % 100000 + 1;

        /*int random = (int)Math.random();
        double division = (1.00/(double)probMutacion)*100;
        int entero = floor(division);
        int numeroRan = random % entero;
        numeroRan++;*/
            if (numeroRandom < probMutacion * 1000)
            {
                hijoMatriz.mutacion();
            }
            hijoMatriz.fitness = hijoMatriz.funcionFitness(numeroMagico);
            poblacion.add(0,hijoMatriz);

            Matriz  hijaMatriz = new Matriz();
            hijaMatriz.setMatrizDatos(pasarAMatriz(hijo2));
            numeroRandom = (int)Math.random() % 100000 + 1;
        /*division = (1.00 / (double)probMutacion)*100;
        entero = floor(division);
        numeroRan = random % entero;
        numeroRan++;*/
            if (numeroRandom < probMutacion * 1000)
            {
                hijaMatriz.mutacion();
            }
            hijaMatriz.fitness = hijaMatriz.funcionFitness(numeroMagico);
            poblacion.add(0,hijaMatriz);
        }
        return poblacion;
    }

    protected ArrayList<List<Integer>> cruzar( ArrayList<Integer> parent1,
                                       ArrayList<Integer> parent2) {

        Random rng = new Random();
        ArrayList<Integer> offspring1 = new ArrayList<Integer>(parent1); // Use a random-access list for performance.
        ArrayList<Integer> offspring2 = new ArrayList<Integer>(parent2);

        int point1 = parent1.size();
        int point2 = rng.nextInt(parent1.size());

        int length = point2 - point1;
        if (length < 0) {
            length += parent1.size();
        }

        Map<Integer, Integer> mapping1 = new HashMap<Integer, Integer>(length * 2); // Big enough map to avoid re-hashing.
        Map<Integer, Integer> mapping2 = new HashMap<Integer, Integer>(length * 2);
        for (int i = 0; i < length; i++) {
            int index = (i + point1) % parent1.size();
            int item1 = offspring1.get(index);
            int item2 = offspring2.get(index);
            offspring1.set(index, item2);
            offspring2.set(index, item1);
            mapping1.put(item1, item2);
            mapping2.put(item2, item1);
        }

        checkUnmappedElements(offspring1, mapping2, point1, point2);
        checkUnmappedElements(offspring2, mapping1, point1, point2);

        ArrayList<List<Integer>> result = new ArrayList<List<Integer>>(2);
        result.add(offspring1);
        result.add(offspring2);
        return result;
    }


    /**
     * Checks elements that are outside of the partially mapped section to
     * see if there are any duplicate items in the list.  If there are, they
     * are mapped appropriately.
     */
    private void checkUnmappedElements(List<Integer> offspring,
                                       Map<Integer, Integer> mapping,
                                       int mappingStart,
                                       int mappingEnd) {
        for (int i = 0; i < offspring.size(); i++) {
            if (!isInsideMappedRegion(i, mappingStart, mappingEnd)) {
                int mapped = offspring.get(i);
                while (mapping.containsKey(mapped)) {
                    mapped = mapping.get(mapped);
                }
                offspring.set(i, mapped);
            }
        }
    }


    /**
     * Checks whether a given list position is within the partially mapped
     * region used for cross-over.
     *
     * @param position   The list position to check.
     * @param startPoint The starting index (inclusive) of the mapped region.
     * @param endPoint   The end index (exclusive) of the mapped region.
     * @return True if the specified position is in the mapped region, false
     * otherwise.
     */
    private boolean isInsideMappedRegion(int position,
                                         int startPoint,
                                         int endPoint) {
        boolean enclosed = (position < endPoint && position >= startPoint);
        boolean wrapAround = (startPoint > endPoint && (position >= startPoint || position < endPoint));
        return enclosed || wrapAround;
    }

    public ArrayList<Integer> pasarAVector(Matriz matriz)
    {

        ArrayList<Integer> vSalida = new ArrayList<Integer>();

        int tam = matriz.getTamanno();
        for (int i = 0; i<tam;i++){
            for(int j = 0; j<tam;j++){

                vSalida.add(matriz.getMatrizDatos().get(i).get(j));

            }
        }
        return vSalida;
    }

    public ArrayList<ArrayList<Integer>> pasarAMatriz(ArrayList<Integer> vector) {
        int dim = (int) Math.sqrt(vector.size());
        ArrayList<ArrayList<Integer>> mSalida  = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < dim; i++) {
            ArrayList<Integer> v = new ArrayList<Integer>();
            mSalida.add(v);
            for (int j = 0; j < dim; j++) {
                mSalida.get(i).add(0);
            }
        }
        for (int i = 0; i < vector.size(); i++) {
            int mI = i / dim;
            int mJ = i % dim;
            mSalida.get(mI).set(mJ,vector.get(i));
        }

        return mSalida;
    }
}