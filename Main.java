import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author victor
 */
public class Main {

    public static int decidir (String fileName) throws IOException {
        if ((new File(fileName)).exists() &&
            (new File(fileName)).isFile() &&
            (new File(fileName)).canRead())  {
            BufferedReader inbuff = null;
            try {
                inbuff = new BufferedReader(new FileReader(fileName));
            } catch (FileNotFoundException fnfe) {
                System.out.println("Esto no deberia pasar, contacte" +
                        " al programador...");
                System.out.println("MENSAJE:" + fnfe.getMessage() + "\n" +
                        "CAUSA:" + fnfe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeLeer("Problema Leyendo el" +
                        " archivo \"" + fileName +
                        "\" al momento de crear el buffer lector...\n");
            }
            String linea = null;
            try {
                 linea = inbuff.readLine();
            } catch (IOException ioe) {
                System.out.println("Esto no deberia pasar, contacte" +
                        " al programador...");
                System.out.println("MENSAJE:" + ioe.getMessage() + "\n" +
                        "CAUSA:" + ioe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeLeer("Problema Leyendo la" +
                        "primera linea del archivo \"" + fileName +
                        "\"");
            }
            String[] tokens = linea.split(" ");
            if (tokens.length == 2) {
                if (tokens[0].matches("[0-9]+?") &&
                    tokens[1].matches("[0-9]+?")) {
                    System.out.println("archivo leido...");
                    return ((new Integer(tokens[0]).intValue()) >
                            (new Integer(tokens[1]).intValue()) ? 1 : 0);
                    // 0 = DiGraphMatrix, 1 = DiGraphList
                } else {
                    throw new ExcepcionFormatoIncorrecto("En la primera linea" +
                            " hay un error de sintaxis: Se esperaba un numero" +
                            " seguido de otro numero (numNodos numArcos) y se" +
                            " encontro: " + tokens[0] + " " + tokens[1] + "\n");
                }
            } else {
                throw new ExcepcionFormatoIncorrecto("En la primera linea hay" +
                        "un error de sintaxis: Se esperaban dos elementos (" +
                        "numNodos numArcos), y se encontro:\n\t"+
                        tokens.toString());
            }
        } else if (!(new File(fileName)).exists()) {
            throw new ExcepcionArchivoNoExiste("Problema al leer el archivo " +
                    "\"" + fileName +"\": EL ARCHIVO NO EXISTE!!!");
        } else if (!(new File(fileName)).isFile()) {
            throw new ExcepcionNoEsArchivo("Problema al leer el archivo \"" +
                    fileName +"\": NO ES UN ARCHIVO!!!");
        } else if (!(new File(fileName)).canRead()) {
            throw new ExcepcionArchivoNoSePuedeLeer("Problema al leer el ar" +
                    "chivo \"" + fileName +"\": ESTE ARCHIVO NO SE PUEDE" +
                    " LEER!!!");
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        if (args[0] != null) {
            DiGraph g1 = null;
            DiGraph g2;
            boolean matrix = false;
            int d = decidir(args[0]);
            if (d == 0) {
                matrix = true;
                System.out.println("numNodos < numArcos, utilizando un" +
                        " DiGraphMatrix...");
            } else if (d == 1) {
                matrix = false;
                System.out.println("numArcos < numNodos, utilizando un" +
                        " DiGraphList...");
            }
            if (matrix) {
                g1 = new DiGraphMatrix(args[0]);
                System.out.println("Inicializado con DiGraphMatrix...");
            } else {
                g1 = new DiGraphList(args[0]);
                System.out.println("Inicializado con DiGraphList...");
            }
            g2 = g1.alcance();
            System.out.println("El digrafo resultante al aplicar el " +
                            "algoritmo de RoyWarshall en el digrafo de " +
                            "entrada es:\n\n");
            System.out.println(g2.toString());
        } else {
            System.out.println("Sistanxis:\n\n\t# java Main <fileName>\n\n" +
                    "Donde 'fileName es el archivo que contiene el grafo " +
                    "de entrada...'");
        }
    }
}