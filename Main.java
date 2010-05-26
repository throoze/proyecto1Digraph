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
                    return ((new Integer(tokens[0]).intValue()) >
                            (new Integer(tokens[1]).intValue()) ? 1 : 0);
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
            } else {
                g1 = new DiGraphList(args[0]);
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
                    " archivo donde se almacenará la salida del programa. Si" +
                    " 'outFileName' no existe, éste se creará...");
        }
    }
}