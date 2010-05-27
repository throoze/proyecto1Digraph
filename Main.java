import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Programa principal que se encargará de recibir un DiGraph representado en un
 * archivo de texto plano según el formato establecido en la cátedra indicado en
 * el enunciado, y calcular el DiGraph que contiene los mismos nodos y los
 * mismos arcos que el DiGraph de entrada, unudo con los arcos transitivos y
 * reflexivos (Alcance). Formato:
 * numNodos numArcos
 * nodoSrc nodoDst
 * nodoSrc nodoDst
 * nodoSrc nodoDst
 * nodoSrc nodoDst
 * nodoSrc nodoDst
 *    .       .
 *    .       .
 *    .       .
 * nodoSrc nodoDst
 *
 * @author Victor De Ponte, 05-38087
 * @author Karina Valera, 06-40414
 * @version 2.0
 * @since 1.6
 */
public class Main {

    /**
     * Lee la primera linea del archivo {@code fileName}, y basado en el numero
     * de Nodos y el numero de Arcos, decide si utilizar la implementación
     * DiGraphMatrix (en caso de haber mas arcos que nodos), o la implementación
     * DiGraphList (en caso de haber mas nodos que arcos).
     * <b>pre</b>: <i>comportamiento normal</i>: que el archivo {@code fileName}
     * exista, sea un archivo, se pueda leer, no tenga errores de formato ni
     * inconsistencias en cuanto al numero de nodos y de arcos;
     * <i>comportamiento excepcional</i>: lo contrario.
     * <b>post</b>: <i>comportamiento normal</i>: devuelve {@code 1} si el
     * digrafo del archivo {@code fileName} tiene mas nodos que arcos, 0 si
     * tiene mas arcos que nodos y -1 en caso de que no haya podido decidir.
     * <i>comportamiento excepcional</i>: Arroja la correspondiente excepción de
     * acuerdo a lo mencionado en la sección <b>Throws</b>.
     *
     * @param fileName Nombre del archivo a leer
     * @return devuelve {@code 1} si el digrafo del archivo {@code fileName}
     * tiene mas nodos que arcos, 0 si tiene mas arcos que nodos y -1 en caso de
     * que no haya podido decidir.
     * @throws IOException En caso de que {@code fileName} no exista, no sea un
     * archivo, no se pueda leer, tenga un error de formato, o alguna
     * inconsistencia en cuanto al numero de arcos o el numero de nodos
     */
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
                throw new ExcepcionArchivoNoSePuedeLeer("\nProblema Leyendo" +
                        " el archivo \"" + fileName + "\" al momento de crear" +
                        " el buffer lector...\n");
            }
            String linea = null;
            try {
                 linea = inbuff.readLine();
            } catch (IOException ioe) {
                System.out.println("Esto no deberia pasar, contacte" +
                        " al programador...");
                System.out.println("MENSAJE:" + ioe.getMessage() + "\n" +
                        "CAUSA:" + ioe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeLeer("\nProblema Leyendo" +
                        " la primera linea del archivo \"" + fileName + "\"");
            }
            String[] tokens = linea.split(" ");
            if (tokens.length == 2) {
                if (tokens[0].matches("[0-9]+?") &&
                    tokens[1].matches("[0-9]+?")) {
                    int nNodos = (new Integer(tokens[0]).intValue());
                    int nArcos = (new Integer(tokens[1]).intValue());
                    return (nNodos > nArcos ? 1 : 0);
                    // 0 = DiGraphMatrix, 1 = DiGraphList
                } else {
                    throw new ExcepcionFormatoIncorrecto("\nEn la primera" +
                            " linea hay un error de sintaxis:\nSe esperaba un" +
                            " numero seguido de otro numero (numNodos" +
                            " numArcos) y se encontró:\n\t\"" + tokens[0] + " "+
                            tokens[1] + "\"\n");
                }
            } else {
                throw new ExcepcionFormatoIncorrecto("\nEn la primera linea" +
                        " hay un error de sintaxis:\nSe esperaban dos" +
                        " elementos (numNodos numArcos), y se encontro:\n\t" +
                        "\"" + linea + "\"");
            }
        } else if (!(new File(fileName)).exists()) {
            throw new ExcepcionArchivoNoExiste("\nProblema al leer el" +
                    " archivo \"" + fileName +"\": EL ARCHIVO NO EXISTE!!!");
        } else if (!(new File(fileName)).isFile()) {
            throw new ExcepcionNoEsArchivo("\nProblema al leer el archivo \"" +
                    fileName + "\": NO ES UN ARCHIVO!!!");
        } else if (!(new File(fileName)).canRead()) {
            throw new ExcepcionArchivoNoSePuedeLeer("\nProblema al leer el ar" +
                    "chivo \"" + fileName +"\": ESTE ARCHIVO NO SE PUEDE" +
                    " LEER!!!");
        }
        return -1;
    }

    /**
     * Método main (principal), que llama a todas las funciones necesarias y
     * hace todas las verificaciones necesarias para calcular el alcance del
     * DiGraph de entrada.
     * <blockquote>
     * <p><b>Sintaxis</b>:</p>
     * <p>$ java Main inputFile [outputFile]</p>
     * </blockquote>
     * Donde {@code inputFile} es el archivo de entrada y {@code outputFile} es
     * el archivo de salida. Note que el archivo de salida es opcional. En caso
     * de no introducirlo la salida se mostrará por la salida estándar.
     * <b>pre</b>: {@code inputFile} debe existir, ser un archivo, poder leerse,
     * no puede tener errores de formato ni inconsistencias en el número de
     * nodos o arcos. En caso de introducir {@code outputFile}, éste también
     * debe existir, ser un archivo y ser escribible.
     * <b>post</b>: Si se introdujo {@code outputFile}, éste contrendrá la
     * representación del DiGraph de alcance asociado a {@code inputFile} según
     * el formato ya descrito. En caso de que {@code outputFile} no haya sido
     * introducido por el usuario, esta representación se mostrará en la salida
     * estándar.
     * @param args arreglo que recibe los parámetros del usuario (archivos de
     * entrada y salida).
     * @throws IOException En caso de que {@code fileName} no exista, no sea un
     * archivo, no se pueda leer, tenga un error de formato, o alguna
     * inconsistencia en cuanto al numero de arcos o el numero de nodos
     */
    public static void main(String[] args) throws IOException {
        if (0 < args.length) {
            DiGraph g1 = null;
            DiGraph g2;
            boolean matrix = false;
            int d = decidir(args[0]);
            if (d == 0) {
                matrix = true;
            } else if (d == 1) {
                matrix = false;
            }
            if (matrix) {
                g1 = new DiGraphMatrix(args[0]);
                System.out.println("Usando Matrix...");
            } else {
                g1 = new DiGraphList(args[0]);
                System.out.println("Usando Lista...");
            }
            g2 = g1.alcance();
            if (args.length == 2) {
                g2.write(args[1]);
            } else {
                System.out.println(g2.toString());
            }
        } else {
            System.out.println("Sistanxis:\n\n\t# java Main <inFileName>" +
                    " <outFileName>\n\nDonde 'inFileName' es el archivo que" +
                    " contiene el grafo de entrada, y 'outFileName' sera el" +
                    " archivo donde se almacenará la salida del programa." +
                    " Tanto 'inFileName' como 'outFileName' deben existir...");
        }
    }
}