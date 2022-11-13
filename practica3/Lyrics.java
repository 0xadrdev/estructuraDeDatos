package practica3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lyrics {

    private Map<String, List<Integer>> wordsPositions;

    
    public Set<String> getMapKeys() {
        return wordsPositions.keySet();
    }

    public Integer[][] getMapValues() {
       Integer[][] resultado = new Integer[wordsPositions.keySet().size()][];
       int pos=0;
       for (String s:wordsPositions.keySet()) {
           List<Integer> valor = wordsPositions.get(s);
           resultado[pos] = new Integer[valor.size()];
           valor.toArray(resultado[pos]);
           pos++;

       }
       return resultado;
    }

    //Completar constructor.
    //filename es el nombre del fichero
    //Debe crear un Mapa con las palabras del fichero y las posiciones que ocupan (empezando a contar en 1)
    public Lyrics(String filename) {
        Scanner sc = null;

        try {
            sc= new Scanner (new File(filename));

        } catch(FileNotFoundException e){
            System.out.println("Error al abrir el fichero");
            System.exit(0);
        }

        //Ejercicio 1 - Constructor
        wordsPositions = new TreeMap<>();
        int contadorPalabras = 1;


        while (sc.hasNextLine()) {
            //Lee la siguiente línea del fichero y la convierte en mayúscualas
            String line = sc.nextLine().toUpperCase(); 
            
            //Divide la línea en palabras
            String[] words = line.split(" "); 
            
            for (int i = 0; i < words.length; i++) {
                List<Integer> lista = new ArrayList<Integer>(); 
                if (wordsPositions.containsKey(words[i])) {
                    lista.addAll(wordsPositions.get(words[i]));
                    if (i == words.length - 1) {
                        lista.add(-contadorPalabras);
                    } else {
                        lista.add(contadorPalabras);
                    }
                } else {
                    if (i == words.length - 1) {
                        lista.add(-contadorPalabras);
                    } else {
                        lista.add(contadorPalabras);
                    }
                }
                contadorPalabras++;    
                wordsPositions.put(words[i],lista);
            }
            // lista.clear();
            // if (wordsPositions.containsKey(words[words.length - 1])) {
            // // List<Integer> lista = new ArrayList<Integer>(); 
            //     lista.addAll(wordsPositions.get(words[words.length - 1]));
            // }
            // wordsPositions.put(words[words.length - 1],lista);
            // contadorPalabras++;    



            //.....

        }
        sc.close();

    }

    //Ejercicio 2: devuelve el número de palabras distintas
    public int differentWords() {
        return wordsPositions.size();

    }

    //Ejercicio 3: Devuelve el conjunto de palabras distintas ordenado alfabéticamente
    public Set<String> listWords() {
        return wordsPositions.keySet();
    }

    //Ejercicio 4: Devuelve el número de palabras distintas en el texto
    public int totalWords() {
        int totalPalabras = 0;
        
        Collection<List<Integer>> coleccion = wordsPositions.values();
        for (List<Integer> listaPosiciones : coleccion) {
            totalPalabras += listaPosiciones.size();
        }

        return totalPalabras;
    }

    //Ejercicio 5: Palabras usadas al menos n veces
    public Set<String> wordsUsedOver (int n) {
        Set<String> palabras = new HashSet<String>();
        for (Map.Entry<String, List<Integer>> entry: wordsPositions.entrySet()) {
            if (entry.getValue().size() >= n) {
                palabras.add(entry.getKey());
            }
        }
        return palabras;
    }

    //Ejercicio 6: Devuelve el conjunto de palabras que se usan para terminar versos
    public Set<String> terminalWords() {
        Set<String> palabras = new HashSet<String>();
        for (Map.Entry<String, List<Integer>> entry: wordsPositions.entrySet()) {
            for (Integer posicion: entry.getValue()) {
                if (posicion < 0) {
                    palabras.add(entry.getKey());
                }
            }
        }
        return palabras;
    }

    //Ejercicio 7: Devuelve un StringBuilder con el texto en versos, listo para ser
    //mostrado por pantalla
    public StringBuilder displayLyrics() {
        String[] palabras = new String[totalWords()];
        for (Map.Entry<String, List<Integer>> entry: wordsPositions.entrySet()) {
            for (Integer posicion: entry.getValue()) {
                if (posicion < 0) {
                    palabras[-posicion - 1] = entry.getKey() + "\n";
                } else {
                    palabras[posicion - 1] = entry.getKey() + " ";
                }
            }
        }
        StringBuilder out = new StringBuilder();
        for (String palabra : palabras) {
            out.append(palabra);
        }
        return out;
    }

    //Ejercicio 8: Cambiar todas las ocurrencias de una palabra por otra
    //Devuelve el número de palabras cambiadas
    //Si la palabra origin, no está, no cambia nada
    //La palabra target puede ser una palabra que ya esté en el texto o no
    public int changeWord (String source, String target) {
        int totalCambiadas = 0;
        List<Integer> listaPosiciones = new ArrayList<Integer>();
        for (Map.Entry<String, List<Integer>> entry: wordsPositions.entrySet()) {
            String palabra = entry.getKey();
            if (palabra.equals(source) && !source.equals(target)) {
                if (wordsPositions.containsKey(target)) {
                    listaPosiciones.addAll(wordsPositions.get(target));
                }
                listaPosiciones.addAll(entry.getValue());
                totalCambiadas = entry.getValue().size();
            }
        }
        if (listaPosiciones.size() > 0) {
            wordsPositions.put(target, listaPosiciones);
            wordsPositions.remove(source);
        }
        return totalCambiadas;
    }
}
