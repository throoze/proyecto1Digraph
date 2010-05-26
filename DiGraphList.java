import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    // Constructores:

    public DiGraphList() {
        this.inArcs = null;
        this.outArcs = null;
        this.numArcs = 0;
        this.numNodes = 0;
    }

    /**
     * Crea un DiGraphList con n nodos y sin arcos.
     * @param n numero de nodos iniciales de grafo
     */
    public DiGraphList(int n) {
        this.inArcs = new List[n];
        this.outArcs = new List[n];
        for (int i = 0; i < n; i++) {
            this.inArcs[i] = new Lista();
            this.outArcs[i] = new Lista();
        }
        this.numNodes = n;
        this.numArcs = 0;
    }

    /**
     * Crea un DiGraphList a partir del contenido de un archivo
     * @param fileName nombre del archivo
     */
    public DiGraphList(String fileName) throws IOException {
        this();
        this.read(fileName);
    }

    /**
     * Crea un DiGraphList a partir del DiGraph g
     * @param g
     */
    public DiGraphList(DiGraph g) {
        this();
        this.inArcs = new List[g.numNodes];
        this.outArcs = new List[g.numNodes];
        for (int i = 0; i < g.numNodes; i++) {
            this.inArcs[i] = new Lista();
            this.outArcs[i] = new Lista();
        }
        this.numNodes = g.numNodes;
        for (int i = 0; i < this.numNodes; i++) {
            for  (int j = 0; j < this.numNodes; j++) {
                if (g.isArc(i, j)) {
                    this.addArc(i, j);
                }
            }
        }
    }

    // Métodos:

    public Arc addArc(int src, int dst) {
        if (!(this.isArc(src, dst))) {
            Arc nuevo = new Arc(src,dst);
            this.inArcs[dst].add(nuevo);
            this.outArcs[src].add(nuevo);
            this.numArcs++;
            return (nuevo);
        } else {
            return null;
        }
    }

    public Arc addArc(int src, int dst, double costo) {
        if (!(this.isArc(src, dst))) {
            Arc nuevo = new Arc(src,dst,costo);
            this.inArcs[dst].add(nuevo);
            this.outArcs[src].add(nuevo);
            this.numArcs++;
            return (nuevo);
        } else {
            return null;
        }
    }

    public void addNodes(int num) {
        if (0 < num) {
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
        DiGraphList salida = this.clone();
        for (int i = 0; i < salida.numNodes; i++) {
            salida.addArc(i, i);
        }
        int flag;
        int counter = 0;
        do {
            boolean stop = false;
            flag = salida.numArcs;
            for (int a = 0; a < salida.numNodes && !stop; a++) {
                for (int i = 0; i < salida.outArcs[a].size() && !stop; i++) {
                    int b = salida.outArcs[a].get(i).getDst();
                    for (int j = 0; j < salida.outArcs[b].size() && !stop; j++){
                        int c = salida.outArcs[b].get(j).getDst();
                        if (!salida.isArc(a, c)) {
                            salida.addArc(a, c);
                            counter++;
                            stop = true;
                        }
                    }
                }
            }
        } while (salida.numArcs > flag);
        return salida;
    }

    @Override
    public DiGraphList clone() {
        DiGraphList nuevo = new DiGraphList(this);
        return nuevo;
    }

    public Arc delArc(int nodeIniId, int nodeFinId) {
        if (this.isArc(nodeIniId, nodeFinId)) {
            Arc arco = new Arc(nodeIniId, nodeFinId);
            this.inArcs[nodeFinId].remove(arco);
            this.outArcs[nodeIniId].remove(arco);
            return arco;
        } else {
            return null;
        }
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

    // preguntar si es asi...
    public Arc getArc(int nodoSrc, int nodoDst) {
        if (this.isArc(nodoSrc, nodoDst)) {
            return (new Arc(nodoSrc,nodoDst));
        } else {
            return null;
        }
    }

    public int getDegree(int nodeId) {
        return this.getInDegree(nodeId) + this.getOutDegree(nodeId);
    }

    public int getInDegree(int nodeId) {
        return this.inArcs[nodeId].size();
    }

    public List<Arc> getInEdges(int nodeId) {
        return this.inArcs[nodeId];
    }

    public int getNumberOfArcs() {
        return this.numArcs;
    }

    public int getNumberOfNodes() {
        return this.numNodes;
    }

    public int getOutDegree(int nodeId) {
        return this.outArcs[nodeId].size();
    }

    public List<Arc> getOutEdges(int nodeId) {
        return this.outArcs[nodeId];
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

    //Acordarse de borrar el monton de comentarios...
    @Override
    public boolean isArc(int src, int dst) {
        return this.outArcs[src].contains(new Arc(src,dst));
        /*
        if (0 <= src && src < this.outArcs.length &&
            0 <= dst && dst < this.outArcs.length){
            Arc arco = new Arc(src,dst);
            System.out.println("El tamano de outArcs es: "+this.outArcs.length);
            System.out.println("src es: "+src);
            for (int i = 0; i < this.outArcs[src].size(); i++) {
                if (this.outArcs[src].get(i).equals(arco)) {
                    return true;
                }
            }

            // Otra posibilidad

            Object[] arreglo = this.outArcs[src].toArray();
            for (int i = 0; i < arreglo.length; i++) {
                if (((Arc)arreglo[i]).equals(new Arc(src, dst))) {
                    return true;
                }
            }
        }
        return false;
         *
         */
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
                    for (int k = 0; k < this.inArcs.length; k++) {
                        this.inArcs[k] = new Lista();
                        this.outArcs[k] = new Lista();
                    }
                    /* Fin de las diferencias en este constructor entre
                     * DiGraphList y DiGraphMatrix
                     */
                    this.numNodes = new Integer(tokens[0]).intValue();
                    int nArcos = new Integer(tokens[1]).intValue();
                    this.fillFromFile(inbuff, fileName, nArcos);
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

    public List<Arc> removeAllArcs() {
        List<Arc> lista = new Lista();
        for (int i = 0; i < this.numNodes; i++) {
            Arc[] arrArcs = (Arc[])this.outArcs[i].toArray();
            for (int j = 0; j < arrArcs.length; j++) {
                lista.add(arrArcs[j]);
            }
        }
        this.inArcs = new List[this.numNodes];
        this.outArcs = new List[this.numNodes];
        return lista;
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
        List<Arc> arcos = this.removeAllArcs();
        while (!arcos.isEmpty()) {
            Arc arco = arcos.remove(0);
            this.addArc(arco.getDst(), arco.getSrc());
        }
        return true;
    }

    @Override
    public String toString() {
        String string = this.numNodes + " " + this.numArcs;
        for (int i = 0; i < this.numNodes; i++) {
            for (int j = 0; j < this.outArcs[i].size(); j++) {
                string += "\n" + this.outArcs[i].get(j).getSrc() + " " +
                        this.outArcs[i].get(j).getDst();
            }
        }
        return string;
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
                    /* A partir de aqui es diferente entre DiGraphList y
                     * DiGraphMatrix
                     */
                    Object[] arrArcs = this.outArcs[i].toArray();
                    for (int j = 0; j < arrArcs.length; j++) {
                        out.println(i + " " + ((Arc)arrArcs[j]).getDst());
                    }
                    // Fin de las diferencias
                }
            } catch (FileNotFoundException fnfe) {
                System.out.println("Esto no deberia pasar, contacte" +
                        " al programador...");
                System.out.println("MENSAJE:" + fnfe.getMessage() + "\n" +
                        "CAUSA:" + fnfe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeEscribir("\nProblema" +
                        " escribiendo en el archivo \"" + fileName + "\"");
            }
        } else if (!(new File(fileName)).exists()) {
            throw new ExcepcionArchivoNoExiste("\nProblema al leer el" +
                    " archivo \"" + fileName +"\":\n\tEL ARCHIVO NO" +
                    " EXISTE!!!\n");
        } else if (!(new File(fileName)).isFile()) {
            throw new ExcepcionNoEsArchivo("\nProblema al leer el" +
                    " archivo \"" + fileName +"\":\n\tNO ES UN ARCHIVO!!!\n");
        } else if (!(new File(fileName)).canWrite()) {
            throw new ExcepcionArchivoNoSePuedeEscribir("\nProblema al leer" +
                    " el archivo \"" + fileName +"\":\n\tESTE ARCHIVO NO SE" +
                    " PUEDE LEER!!!\n");
        }
    }

    // METODOS PRIVADOR AUXILIARES:

    private void fillFromFile(BufferedReader inbuff, String fileName, int nArc)
                                throws ExcepcionArchivoNoSePuedeLeer,
                                       ExcepcionFormatoIncorrecto,
                                       ExcepcionArcoRepetido,
                                       ExcepcionInconsistenciaNumeroDeNodos,
                                       ExcepcionInconsistenciaNumeroDeArcos
    {
        String linea = "";
        String[] tokens;
        int k = 2;
        try {
            linea = inbuff.readLine();
        } catch (IOException ioe) {
            System.out.println("Esto no deberia pasar, contacte"
                    + " al programador...");
            System.out.println("MENSAJE:" + ioe.getMessage() + "\n"
                    + "CAUSA:" + ioe.getCause().toString() + "\n");
            throw new ExcepcionArchivoNoSePuedeLeer("\nProblema Leyendo la"
                        + "linea " + k + " del archivo \"" + fileName
                        + "\"");
        }
        while (linea != null) {
            tokens = linea.split(" ");
            if (tokens.length == 2) {
                if (tokens[0].matches("[0-9]+?") &&
                    tokens[1].matches("[0-9]+?")) {
                    int src = (new Integer(tokens[0]).intValue());
                    int dst = (new Integer(tokens[1]).intValue());
                    if ((src < 0 || this.numNodes <= src)) {
                        throw new ExcepcionInconsistenciaNumeroDeNodos("\nEl " +
                                "grafo de entrada tiene un numero de nodos " +
                                "distinto al indicado al principio del ar" +
                                "chivo:\nEncontrado \"" + tokens[0] + "\" en" +
                                " la linea " + k + ", y se esperaba en el" +
                                " intervalo [0 - " + nArc + "]");
                    } else if (dst < 0 || this.numNodes <= dst) {
                        throw new ExcepcionInconsistenciaNumeroDeNodos("\nEl " +
                                "grafo de entrada tiene un numero de nodos " +
                                "distinto al indicado al principio del ar" +
                                "chivo:\nEncontrado \"" + tokens[1] + "\" en" +
                                " la linea " + k + ", y se esperaba en el" +
                                " intervalo [0 - " + nArc + "]");
                    }
                    this.addArc(src,dst);
                } else {
                    throw new ExcepcionFormatoIncorrecto("\nEn la linea " + k +
                            " del archivo \"" + fileName + "\"" +
                            " hay un error de sintaxis:\nSe esperaba un numero"+
                            " seguido de otro numero (src dst) y se encontró" +
                            ": \n\t\"" + tokens[0] + " " + tokens[1] + "\"\n");
                }
            } else {
                throw new ExcepcionFormatoIncorrecto("\nEn la linea " + k +
                        " del archivo \"" + fileName + "\" hay" +
                        " un error de sintaxis:\nSe esperaban dos elementos" +
                        " (src dst), y se encontro:\n\t\"" + linea + "\"\n");
            }
            k++;
            try {
                linea = inbuff.readLine();
            } catch (IOException ioe) {
                System.out.println("Esto no deberia pasar, contacte"
                        + " al programador...");
                System.out.println("MENSAJE:" + ioe.getMessage() + "\n"
                        + "CAUSA:" + ioe.getCause().toString() + "\n");
                throw new ExcepcionArchivoNoSePuedeLeer("\nProblema Leyendo la"
                        + "linea " + k + " del archivo \"" + fileName + "\"");
            }
        }
        if (linea == null && k-2 != nArc) {
            throw new ExcepcionInconsistenciaNumeroDeArcos("El grafo de entra" +
                    "da tiene menos arcos que los indicados al principio del " +
                    "archivo:\nTiene " + (k-2) + " arco(s), y se esperaba(n) " +
                    nArc + " arco(s)");
        }
    }
}