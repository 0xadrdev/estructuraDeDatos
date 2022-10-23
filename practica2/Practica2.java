package practica2;
import java.util.*;

public class Practica2 {

    //Ejericicio
    //Dada un iterador al inicio una lista de elementos, modificar la lista eliminando las apariciones repetidas. ES decir,
    //dejando una única instancia por elemento. El método devolverá true si se ha borrado algún elemento y
    //false en caso contrario

    public static<T> boolean removeDuplicates(ListIterator<T> it) {
        boolean modificada = false;
        while (it.hasNext()) {
            T elemento = it.next();
            while (it.hasNext()) { // Recorremos todos los elementos 
                T posibleDiferente = it.next();
                if (elemento.equals(posibleDiferente)) { // Y borramos todos los que son iguales. 
                    modificada = true;
                    it.remove();
                }
            }
            while (it.hasPrevious()) { // Volvemos al primer elemento diferente.
                T aux = it.previous();
                if (aux.equals(elemento)) {
                    it.next();
                    break;
                }
            }
        }
        return modificada;
    }

    //Ejercicio
    //Dado un número entero, obtener una lista con los números primos hasta ese número entero utilizando el método de la
    //criba de erastóstenes
    // n = 53 --> [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53]
    public static List<Integer> getPrimes(int n) {
        LinkedList<Integer> lista = new LinkedList<Integer>();
        if (n < 2) return lista;
        for (int i = 2; i <= n; i++) {
            lista.addLast(i);
        }
        ListIterator<Integer> it = lista.listIterator();
        while (it.hasNext()) {
            int numero = it.next();
            while (it.hasNext()) { 
                int posibleMultiplo = it.next();
                if (posibleMultiplo % numero == 0) {
                    it.remove();
                } 
            }
            if (numero * 2 > n) return lista;
            while (it.hasPrevious()) {
                int aux = it.previous();
                if (aux == numero) {
                    it.next();
                    break;
                } 
            }
        }
        return lista;
    }

    //Dado un iterador a cualquier posición de una lista, rotar los k últimos elementos
    // en sentido horario
    //Ejemplos:   [4, 3, 2, 1, 8, 3] k=3 --> [1, 8, 3, 4, 3, 2]
    //[4, 3, 2, 1, 8, 3] k=0 --> [4, 3, 2, 1, 8, 3]
    // con k = 6, se quedaría igual
    //Suponemos que k toma valores entre 0 y size

    public static<T> void rotatek (ListIterator<T> it, int k) {    
        LinkedList<T> lista = new LinkedList<T>();
        while (it.hasNext()) { // Ponemos it al final de la lista.
            it.next();
        }
        int contador = 0;
        while (contador != k) { // Buscamos el primer elemento que tenemos que borrar.
            lista.add(it.previous());
            it.remove();
            contador++;
        }
        while (it.hasPrevious()) { // Ponemos el it al principio de la lista.
            it.previous();
        }
        if (lista.size() >= 1) {
            for (int i = lista.size() - 1; i >= 0; i--) { // Recorremos la lista al reves para añadir los elementos. 
                it.add(lista.get(i));
            }
        }
    }

    //Dada una lista de números enteros representado un número (un número en cada posición)
    //y un dígito >=0, devolver el resultado de sumar al número representado ese dígito.
    //Ejemplo: Dado el  número 9997 almacenado en una lista como 9->9->9->7, y el número
    //el resultado de la suma debe devolver 1->0->0->0->0 (es decir, 10000).
    //Devolver true si la lista ha aumentado el número de elementos
    //Truco: recorrer la lista al revés, si el resultado es >10, dejar el resto y llevamos

    public static boolean addADigit (List<Integer> num, int digito) {
        boolean modificada = false; 


        return modificada;
    }

    //num1 y num2 son 2 listas que almacenan 2 números enteros dígito a digito
    //Modificar num1 para que almacene la suma de los dos números
    public static void addTwoNumbers (List<Integer> num1, List<Integer> num2) {

    }

}
