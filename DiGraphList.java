import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * DiGraphList es una clase concreta que ud debe implementar
 * Los arcos son almacenados como una lista y son almacenados en un
 * arreglo donde la posicion i
 *
 * @author Les profs
 * @version 1.0
 * @since 1.6
**/
public class DiGraphList extends DiGraph {

    // arreglo de lista de los arcos, inArc[i] contine la lista
    // de los arcos que cuyo destino es el nodo i
    private List<Arc> inArcs[];
    // arreglo de lista de los arcos, outArc[i] contine la lista
    // de los arcos que cuyo fuente es el nodo i
    private List<Arc> outArcs[];

    public DiGraphList() {
        this.inArcs = null;
        this.outArcs = null;
        this.numArcs = -1;
        this.numNodes = -1;
    }

    /**
     * Crea un DiGraphList con n nodos y sin arcos.
     * @param n numero de nodos iniciales de grafo
     */
    public DiGraphList(int n) {
        inArcs = new List[n];
        outArcs = new List[n];
    }

    /**
     * Crea un DiGraphList a partir del contenido de un archivo
     * @param fileName nombre del archivo
     */
    public DiGraphList(String fileName) throws IOException
    {
        this.read(fileName);
    }

    private void fillFromFile(BufferedReader inbuff, String fileName)
                                        throws ExcepcionArchivoNoSePuedeLeer,
                                               ExcepcionFormatoIncorrecto
    {
        String linea = "";
        String[] tokens;
        int k = 2;
        while (linea != null) {
            try {
                linea = inbuff.readLine();
            } catch (IOException ioe) {
                System.out.println("Esto no deberia pasar, contacte"
                        + " al programador...");
                System.out.println("MENSAJE:" + ioe.getMessage() + "\n"
                        + "CAUSA:" + ioe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeLeer("Problema Leyendo la"
                        + "linea " + k + " del archivo \"" + fileName
                        + "\"");
            }
            tokens = linea.split(" ");
            if (tokens.length == 2) {
                if (tokens[0].matches("[0-9]+?") &&
                    tokens[1].matches("[0-9]+?")) {
                    this.addArc(new Integer(tokens[0]).intValue(),
                                new Integer(tokens[1]).intValue());
                } else {
                    throw new ExcepcionFormatoIncorrecto("En la linea " + k +
                            " del archivo \"" + fileName + "\"" +
                            " hay un error de sintaxis: Se esperaba un numero" +
                            " seguido de otro numero (numNodos numArcos) y se" +
                            " encontro: " + tokens[0] + " " + tokens[1] + "\n");
                }
            } else {
                throw new ExcepcionFormatoIncorrecto("En la linea " + k +
                            " del archivo \"" + fileName + "\" hay " +
                        "un error de sintaxis: Se esperaban dos elementos (" +
                        "numNodos numArcos), y se encontro:\n\t"+
                        tokens.toString());
            }
            k++;
        }
    }

    /**
     * Crea un DiGraphList a partir del DiGraph g
     * @param g
     */
    public DiGraphList(DiGraph g) {
        this.inArcs = new List[g.numNodes];
        this.outArcs = new List[g.numNodes];
        this.numNodes = g.numNodes;
        this.numArcs = g.numArcs;
        for (int i = 0; i < this.numNodes; i++) {
            for  (int j = 0; j < this.numNodes; j++) {
                if (g.isArc(i, j)) {
                    this.addArc(i, j);
                }
            }
        }
    }

    @Override
    public DiGraphList clone() {
        DiGraphList nuevo = new DiGraphList(this);
        return nuevo;
    }

    public void addNodes(int num) {
        List<Arc>[] arcosDeEntrada = new List[this.numNodes + num];
        List<Arc>[] arcosDeSalida = new List[this.numNodes + num];
        for (int k = 0; k < this.numNodes; k++) {
            arcosDeEntrada[k] = this.inArcs[k];
            arcosDeSalida[k] = this.outArcs[k];
        }
        this.numNodes = this.numNodes + num;
        this.inArcs = arcosDeEntrada;
        this.outArcs = arcosDeSalida;
    }

    public Arc addArc(int src, int dst) {
        Arc nuevo = new Arc(src,dst);
        if (!(this.isArc(src, dst))) {
            this.inArcs[dst].add(nuevo);
            this.outArcs[src].add(nuevo);
        }
        return (nuevo);
    }

    public Arc addArc(int src, int dst, double costo) {
        Arc nuevo = new Arc(src,dst,costo);
        if (!(this.isArc(src, dst))) {
            this.inArcs[dst].add(nuevo);
            this.outArcs[src].add(nuevo);
        }
        return (nuevo);
    }

    public List<Integer> getPredecesors(int nodeId) {
        List<Integer> predecesors = new Lista();
        Arc[] arrArcs = (Arc[])this.inArcs[nodeId].toArray();
        for (int k = 0; k < arrArcs.length; k++) {
            predecesors.add(new Integer(arrArcs[k].getSrc()));
        }
        return predecesors;
    }

    public List<Integer> getSucesors(int nodeId) {
        List<Integer> sucesors = new Lista();
        Arc[] arrArcs = (Arc[])this.outArcs[nodeId].toArray();
        for (int k = 0; k < arrArcs.length; k++) {
            sucesors.add(new Integer(arrArcs[k].getDst()));
        }
        return sucesors;
    }

    // preguntar si es asi...
    public Arc getArc(int nodoSrc, int nodoDst) {
        return new Arc(nodoSrc,nodoDst);
    }

    public void read(String fileName) throws IOException {
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
                    /* Aqui empiezan las diferencias en este constructor entre
                     * DiGraphList y DiGraphMatrix
                     */
                    this.inArcs = new List[new Integer(tokens[0]).intValue()];
                    this.outArcs = new List[new Integer(tokens[0]).intValue()];
                    /* Fin de las diferencias en este constructor entre
                     * DiGraphList y DiGraphMatrix
                     */
                    this.numNodes = new Integer(tokens[0]).intValue();
                    this.numArcs = new Integer(tokens[1]).intValue();
                    this.fillFromFile(inbuff, fileName);
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
    }

    public void write(String fileName) throws IOException {
        if ((new File(fileName)).exists() &&
            (new File(fileName)).isFile() &&
            (new File(fileName)).canWrite())
        {
            PrintStream out;
            try {
                out = new PrintStream(fileName);
                out.println(this.numNodes + " " + this.numArcs);
                for (int i = 0; i < this.numNodes; i++) {
                    Arc[] arrArcs = (Arc[])this.outArcs[i].toArray();
                    for (int j = 0; j < arrArcs.length; j++) {
                        out.println(i + " " + arrArcs[j].getDst());
                    }
                }
            } catch (FileNotFoundException fnfe) {
                System.out.println("Esto no deberia pasar, contacte" +
                        " al programador...");
                System.out.println("MENSAJE:" + fnfe.getMessage() + "\n" +
                        "CAUSA:" + fnfe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeEscribir("Problema escri" +
                        "biendo en el archivo \"" + fileName + "\"");
            }
        } else if (!(new File(fileName)).exists()) {
            throw new ExcepcionArchivoNoExiste("Problema al leer el archivo " +
                    "\"" + fileName +"\": EL ARCHIVO NO EXISTE!!!");
        } else if (!(new File(fileName)).isFile()) {
            throw new ExcepcionNoEsArchivo("Problema al leer el archivo \"" +
                    fileName +"\": NO ES UN ARCHIVO!!!");
        } else if (!(new File(fileName)).canWrite()) {
            throw new ExcepcionArchivoNoSePuedeEscribir("Problema al leer el " +
                    "archivo \"" + fileName +"\": ESTE ARCHIVO NO SE PUEDE" +
                    " LEER!!!");
        }
    }

    public int getDegree(int nodeId) {
        return this.getInDegree(nodeId) + this.getOutDegree(nodeId);
    }

    public int getOutDegree(int nodeId) {
        return this.outArcs[nodeId].size();
    }

    public int getInDegree(int nodeId) {
        return this.inArcs[nodeId].size();
    }

    public int getNumberOfNodes() {
        return this.numNodes;
    }

    public int getNumberOfArcs() {
        return this.numArcs;
    }

    public List<Arc> getOutEdges(int nodeId) {
        return this.outArcs[nodeId];
    }

    public List<Arc> getInEdges(int nodeId) {
        return this.inArcs[nodeId];
    }

    public Arc delArc(int nodeIniId, int nodeFinId) {
        Arc arco = new Arc(nodeIniId,nodeFinId);
        this.inArcs[nodeFinId].remove(arco);
        this.outArcs[nodeIniId].remove(arco);
        return arco;
    }

    public List<Arc> removeAllArcs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean reverseArc(int nodeIniId, int nodeFinId) {
        if (this.isArc(nodeIniId, nodeFinId)) {
            this.delArc(nodeIniId, nodeFinId);
            this.addArc(nodeFinId, nodeIniId);
            return true;
        } else {
            return false;
        }
    }

    public boolean reverseArcs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean equals(DiGraph g) {
        if (this.numArcs == g.numArcs && this.numNodes == g.numNodes) {
            boolean out = true;
            for (int i = 0; i < this.numNodes && out; i++) {
                Arc[] arrArcs = (Arc[])this.outArcs[i].toArray();
                for (int j = 0; j < arrArcs.length; j++) {
                    out = g.isArc(i, arrArcs[j].getDst());
                }
            }
            return out;
        } else {
            return false;
        }
    }

    /**
     * Retorna un Digraph que es la clausura transitiva de este DiGraph
     * calculada usando el algoritmo Roy-Warshal
     * 
     * @return un Digraph que es la clausura transitiva de este DiGraph
     * calculada usando el algoritmo Roy-Warshal
     */
    @Override
    public DiGraph alcance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isArc(int src, int dst) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}