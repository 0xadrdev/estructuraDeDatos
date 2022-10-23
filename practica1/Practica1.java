package practica1;
import java.util.*;

public class Practica1 {


    //Dado un iterador a una colleción de elementos, devuelve un conjunto con los elementos
    //que son múltiplos de cualquier otro número de la colección inicial
    //Los 0 no se tienen en cuenta
    public static Set<Integer> multiplos (Iterator<Integer> it) {
        Set<Integer> multiplos = new HashSet<Integer>();
        Set<Integer> vistos = new HashSet<Integer>();
        while (it.hasNext()) {
            Iterator<Integer> itVistos = vistos.iterator();
            int posibleMultiplo = it.next(); 
            while (itVistos.hasNext()) {
                Integer numeroVisto = itVistos.next();
                if (posibleMultiplo != 0 && numeroVisto != 0) {
                    if (posibleMultiplo % numeroVisto == 0 && !multiplos.contains(posibleMultiplo)) {
                        multiplos.add(posibleMultiplo); 
                    } else if (numeroVisto % posibleMultiplo == 0 && !multiplos.contains(numeroVisto)) {
                        multiplos.add(numeroVisto);
                    }
                }
            }
            if (!vistos.contains(posibleMultiplo)) {
                vistos.add(posibleMultiplo);
            }
        }
        return multiplos; 
    }


    //Dados dos conjuntos de enteros, el método debe modificarlos de tal forma que al finalizar
    //en cuadrados deben quedar los elementos que son el cuadrado de otro número de cualquiera de los
    //dos conjuntos y en noCuadrados los que no son cuadrados de ninguno.

    public static void separate (Set<Integer> cuadrados, Set<Integer> noCuadrados)  { 
        Set<Integer> numeros = new HashSet<Integer>();
        numeros.addAll(cuadrados);
        numeros.addAll(noCuadrados);
        Iterator<Integer> itNumeros = numeros.iterator();
        while (itNumeros.hasNext()) {
            int posibleCuadrado = itNumeros.next();
            Iterator<Integer> it = numeros.iterator();
            boolean esCuadrado = false;
            while (it.hasNext()) {
                int numero = it.next();
                if (Math.sqrt(posibleCuadrado) == numero ) {
                    if (posibleCuadrado == numero) {
                        if (cuadrados.contains(numero) && noCuadrados.contains(numero)) {
                            esCuadrado = true;
                        }
                    } else {
                        cuadrados.add(posibleCuadrado);
                        esCuadrado = true;
                    }
                } 
            }
            if (cuadrados.contains(posibleCuadrado) && !esCuadrado) {
                cuadrados.remove(posibleCuadrado);
                if (!noCuadrados.contains(posibleCuadrado)) {
                    noCuadrados.add(posibleCuadrado);
                }
            }
        }
        noCuadrados.removeAll(cuadrados);
    }
    

        // Con un solo recorrido y 1 vistos. 
        

    //Dada un iterador al inicio de una colección de elementos donde puede haber repetidos, dividir dicha colección en el mínimo
    //número de conjuntos. El método debe devolver los conjuntos obtenidos
    //(1, 2, 1, 2, 3) --> {(1,2,3), (1,2)}
    //(2, 2, 2) --> {(2), (2), (2)}
    //(1, 2, 3, 2, 3, 4, 5, 1, 8, 2, 6) --> {(1, 2, 3, 4, 5, 8, 6), (2, 3, 1), (2)}

    public static<T> Collection<Set<T>> divideInSets (Iterator<T> it) {
        Collection<Set<T>> conjuntos = new ArrayList<Set<T>>();
        while (it.hasNext()) {
            T numero = it.next();
            if (conjuntos.size() != 0) {
                boolean numeroAñadido = false;
                for (Set<T> conjunto : conjuntos) {
                    if (!conjunto.contains(numero)) {
                        numeroAñadido = true;
                        conjunto.add(numero);
                        break;
                    } 
                } 
                if (!numeroAñadido) {
                    Set<T> subConjunto = new HashSet<T>();
                    subConjunto.add(numero);
                    conjuntos.add(subConjunto);
                }
            } else {
                Set<T> subConjunto = new HashSet<T>();
                subConjunto.add(numero);
                conjuntos.add(subConjunto);
            }
        }
        return conjuntos;
    }

    //Dado un conjunto de elementos u y una lista de conjuntos, identificar y devolver aquellos dos
    // conjuntos de la lista cuya unión forma el conjunto u.
    //Si no hay 2 conjuntos de la lista que formen u, entonces devolverá una colección vacía.
    //Si hubiera más de dos conjuntos cuya unión sea u, devolverá los primeros que encuentre.
    public static<T> Collection<Set<T>> coverageSet2 (Set<T> u,ArrayList<Set<T>> col) {
        Collection<Set<T>> coleccion = new HashSet<>();
        for (Set<T> conjunto:col) {
            Iterator<Set<T>> itCol = col.iterator();
            Set<T> posibleU = new HashSet<T>();
            posibleU.addAll(conjunto);
            while (itCol.hasNext()) {
                Set<T> conjuntoIt = itCol.next();
                posibleU.addAll(conjuntoIt);
                if (posibleU.equals(u) && !conjunto.equals(u) && !conjuntoIt.equals(u) && !conjunto.equals(conjuntoIt)) {
                    coleccion.add(conjunto);
                    coleccion.add(conjuntoIt);
                    return coleccion;
                } 
                posibleU.retainAll(conjuntoIt);
            }
        }
        return coleccion;
    }




}
