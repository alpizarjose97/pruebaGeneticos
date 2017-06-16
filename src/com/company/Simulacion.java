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
        
        protected List<List<Integer>> mate(List<Integer> parent1,
                                     List<Integer> parent2,
                                     int numberOfCrossoverPoints,
                                     Random rng)
        {
            assert numberOfCrossoverPoints == 2 : "Expected number of cross-over points to be 2.";

            if (parent1.size() != parent2.size())
            {
                throw new IllegalArgumentException("Cannot perform cross-over with different length parents.");
            }

            List<Integer> offspring1 = new ArrayList<Integer>(parent1); // Use a random-access list for performance.
            List<Integer> offspring2 = new ArrayList<Integer>(parent2);

            int point1 = rng.nextInt(parent1.size());
            int point2 = rng.nextInt(parent1.size());

            int length = point2 - point1;
            if (length < 0)
            {
                length += parent1.size();
            }

            Map<Integer, Integer> mapping1 = new HashMap<Integer, Integer>(length * 2); // Big enough map to avoid re-hashing.
            Map<Integer, Integer> mapping2 = new HashMap<Integer, Integer>(length * 2);
            for (int i = 0; i < length; i++)
            {
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

            List<List<Integer>> result = new ArrayList<List<Integer>>(2);
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
                                           int mappingEnd)
        {
            for (int i = 0; i < offspring.size(); i++)
            {
                if (!isInsideMappedRegion(i, mappingStart, mappingEnd))
                {
                    int mapped = offspring.get(i);
                    while (mapping.containsKey(mapped))
                    {
                        mapped = mapping.get(mapped);
                    }
                    offspring.set(i, mapped);
                }
            }
        }


        /**
         * Checks whether a given list position is within the partially mapped
         * region used for cross-over.
         * @param position The list position to check.
         * @param startPoint The starting index (inclusive) of the mapped region.
         * @param endPoint The end index (exclusive) of the mapped region.
         * @return True if the specified position is in the mapped region, false
         * otherwise.
         */
        private boolean isInsideMappedRegion(int position,
                                             int startPoint,
                                             int endPoint)
        {
            boolean enclosed = (position < endPoint && position >= startPoint);
            boolean wrapAround = (startPoint > endPoint && (position >= startPoint || position < endPoint));
            return enclosed || wrapAround;
        }
    }
    ArrayList<Integer> pasarAVector(Matriz * matriz)
        {

        ArrayList<Integer> vSalida;
        int tam = matriz.getTamanno();
        for (int i = 0; i<tam;i++){
        for(int j = 0; j<tam;j++){

        vSalida.push_back(matriz.getMatrizDatos().at(i).at(j));
        }
        }
        return vSalida;
        }

        ArrayList<ArrayList<Integer>> pasarAMatriz(ArrayList<Integer> vector)
        {
        int dim = (int)Math.sqrt(vector.size());
        ArrayList<ArrayList<Integer>> mSalida;
        for (int i = 0; i<dim;i++){
            ArrayList<Integer> v;
            mSalida.add(v)
            for (int j = 0;j<dim;j++){
                mSalida.get(i).add(0);
            }
        }
        for (int i = 0; i<vector.size() ; i++){
        int mI = i/dim;
        int mJ = i%dim;
        mSalida[mI][mJ] = vector.at(i);
        }
        return mSalida;
        }
