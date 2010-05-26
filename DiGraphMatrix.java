import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/** * DiGraphMatrix es una clase concreta que ud debe implementar
 *
 * @author Les profs
 * @version 1.0
 * @since 1.6
**/
public class DiGraphMatrix extends DiGraph {

    // estructura de la matriz de adyacencias que se debe utilizar
    private boolean matrix[][];
    
    // Constructores:

    public DiGraphMatrix() {
        this.matrix = null;
        this.numArcs = 0;
        this.numNodes = 0;
    }
    
    /**
     * Crea un DiGraphMatrix con n nodos y sin arcos
     * @param n
     */
    public DiGraphMatrix(int n) {
        this();
        matrix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j =0; j < n; j++) {
                this.matrix[i][j] = false;
            }
        }
        numNodes = n;
    }
    
    /**
     * Crea un DiGraphMatrix a partir del contenido del archivo.
     *
     * @param fileName nombre del archivo
     */
    public DiGraphMatrix(String fileName) throws IOException {
        this(0);
        this.read(fileName);
    }
    
    /**
     * Crea un DiGraphMatrix a partir del DiGraph g
     * 
     * @param g el grafo fuente.
     */
    public DiGraphMatrix(DiGraph g) {
        this(0);
        this.numNodes = g.numNodes;
        this.matrix = new boolean [g.numNodes][g.numNodes];
        for (int i = 0; i < this.numNodes; i++) {
            for (int j=0; j< this.numNodes; j++){
                if (g.isArc(i, j)){
                    this.addArc(i,j);
                } else {
                    this.matrix[i][j] = false;
                }
            }
        }
    }

    public Arc addArc(int src, int dst) {
        if (!this.isArc(src, dst)) {
            this.matrix[src][dst] = true;
            this.numArcs++;
            Arc arco = new Arc(src, dst);
            return arco;
        } else {
            return null;
        }
    }

    public Arc addArc(int src, int dst, double costo) {
        if (!this.isArc(src, dst)) {
            this.matrix[src][dst] = true;
            Arc arco = new Arc(src, dst, costo);
            this.numArcs++;
            return arco;
        } else {
            return null;
        }
    }

    @Override
    public void addNodes(int num) {
        if (0 < num) {
            DiGraphMatrix nuevo = new DiGraphMatrix(this.numNodes + num);
            for (int i = 0; i < this.numNodes; i++) {
                for (int j = 0; j < this.numNodes; j++) {
                    if (this.matrix[i][j]) {
                        nuevo.matrix[i][j] = true;
                    }
                }
            }
            this.numNodes += num;
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
        // Se trabaja sobre una copia, para no modificar el original
        DiGraphMatrix nuevo = this.clone();
        // Añadimos la diagonal principal...
        for (int i = 0; i < nuevo.numNodes; i++){
            if (!nuevo.isArc(i, i)) {
                nuevo.addArc(i, i);
            }
        }
        // Se calculan los demás arcos transitivos
        for (int i = 0; i < this.numNodes; i++){
            for (int j = 0; j < this.numNodes; j++){
	        if (nuevo.matrix[i][j] && i != j){
	            for (int k = 0; k < this.numNodes; k++){
                        if ((nuevo.matrix[i][k] || nuevo.matrix[j][k]) &&
                                !nuevo.isArc(i, k)) {
                            nuevo.addArc(i, k);
                        }
		    }
	        }
	    }
        }
        return nuevo;
    }

    @Override
    public DiGraphMatrix clone() {
       DiGraphMatrix nuevo = new DiGraphMatrix(this);
       return nuevo;
    }

    public Arc delArc(int nodeIniId, int nodeFinId) {
        if (this.isArc(nodeIniId, nodeFinId)) {
            Arc arco = new Arc(nodeIniId, nodeFinId);
            this.matrix[nodeIniId][nodeFinId] = false;
            return arco;
        } else {
            return null;
        }
    }
    
    @Override
    public boolean equals(DiGraph g) {
        boolean eq = true;
        for(int i = 0; i < this.numNodes && eq; i++) {
	    for(int j = 0; j < this.numNodes && eq; j++) {
                if (this.matrix[i][j] != ((DiGraphMatrix)g).matrix[i][j]) {
                    eq = false;
		}
            }
        }
        return eq;
    }
    
    public Arc getArc(int nodoSrc, int nodoDst) {
        if (this.isArc(nodoSrc, nodoDst)) {
            return new Arc(nodoSrc, nodoDst);
        } else {
            return null;
        }
    }
    
    public int getDegree(int nodeId) {
        return this.getInDegree(nodeId) + this.getOutDegree(nodeId);
    }
    
    public int getInDegree(int nodeId) {
        int k = 0;
        for(int i=0; i<this.numNodes;i++){
            if(this.matrix[i][nodeId] == true ){
                k++;
            }
	}
	return k;
    }
    
    @Override
    public List<Arc> getInEdges(int nodeId) {
        List<Arc> arcos = new Lista();
        for (int k = 0; k < this.numNodes; k++) {
            if(this.matrix [k][nodeId]== true){
                arcos.add(new Arc (k, nodeId));
            }
        }
        return arcos;
    }
    
    public int getNumberOfArcs() {
        return this.numArcs;
    }
    
    public int getNumberOfNodes() {
        return this.numNodes;
    }

    public int getOutDegree(int nodeId) {

    int k = 0;
    for(int i=0; i<numNodes;i++){
	 if(this.matrix[nodeId][i] == true ){
	   k++;
	   }
	 }
	 return k;
   }

    @Override
    public List<Arc> getOutEdges(int nodeId) {
        List<Arc> arcos = new Lista();
        for (int k = 0; k < this.numNodes; k++) {
            if(this.matrix [nodeId][k]== true){
                arcos.add(new Arc (nodeId, k));
            }
        }
        return arcos;
    }

    public List<Integer> getPredecesors(int nodeId) {
        List<Integer> predecesors = new Lista();
        for (int k = 0; k < this.numNodes; k++) {
            if(this.matrix [k][nodeId] == true){
                predecesors.add(new Integer(k));
            }
        }
        return predecesors;
    }

    public List<Integer> getSucesors(int nodeId) {
        List<Integer> sucesors = new Lista();
        for (int k = 0; k < this.numNodes; k++) {
            if(this.matrix [nodeId][k]== true){
                sucesors.add(new Integer(k));
            }
        }
        return sucesors;
    }

    @Override
    public boolean isArc(int src, int dst) {
        return (this.matrix[src][dst]);
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
                    /* Aqui empiezan las diferencias en este constructor entre
                     * DiGraphList y DiGraphMatrix
                     */
                    this.matrix = new boolean
                            [new Integer(tokens[0]).intValue()]
                            [new Integer(tokens[0]).intValue()];
                    /* Fin de las diferencias en este constructor entre
                     * DiGraphList y DiGraphMatrix
                     */
                    this.numNodes = new Integer(tokens[0]).intValue();
                    int nArc = new Integer(tokens[1]).intValue();
                    this.fillFromFile(inbuff, fileName, nArc);
                } else {
                    throw new ExcepcionFormatoIncorrecto("\nEn la primera" +
                            " linea hay un error de sintaxis:\nSe esperaba un" +
                            " numero seguido de otro numero (numNodos" +
                            " numArcos) y se encontro:\n\t\"" + tokens[0] +
                            " " + tokens[1] + "\"\n");
                }
            } else {
                throw new ExcepcionFormatoIncorrecto("\nEn la primera linea" +
                        " hay un error de sintaxis:\nSe esperaban dos" +
                        " elementos (numNodos numArcos), y se encontro:\n\t" +
                        "\"" + tokens.toString() + "\"");
            }
        } else if (!(new File(fileName)).exists()) {
            throw new ExcepcionArchivoNoExiste("\nProblema al leer el archivo" +
                    " \"" + fileName +"\": EL ARCHIVO NO EXISTE!!!");
        } else if (!(new File(fileName)).isFile()) {
            throw new ExcepcionNoEsArchivo("\nProblema al leer el archivo" +
                    " \"" + fileName +"\": NO ES UN ARCHIVO!!!");
        } else if (!(new File(fileName)).canRead()) {
            throw new ExcepcionArchivoNoSePuedeLeer("\nProblema al leer el" +
                    " archivo \"" + fileName +"\": ESTE ARCHIVO NO SE PUEDE" +
                    " LEER!!!");
        }
    }

    @Override
    public List<Arc> removeAllArcs() {
        List<Arc> lista = new Lista();
        for (int i = 0; i < this.numNodes; i++) {
            for (int j = 0; j < this.numNodes; j++) {
                if(this.matrix[i][j]){
                    this.matrix[i][j]= false;
                    lista.add(new Arc (i, j));
                }
            }
        }
        return lista;
    }

    public boolean reverseArc(int nodeIniId, int nodeFinId) {
        if(this.matrix[nodeIniId][nodeFinId]){
            this.matrix[nodeIniId][nodeFinId] = false;
            this.matrix[nodeFinId][nodeIniId] = true;
            return true;
	} else {
            return false;
        }
    }

    public boolean reverseArcs() {
        boolean[][] nueva = new boolean[this.numNodes][this.numNodes];
        for(int i = 0; i < this.numNodes; i++) {
            for(int j = 0; j < this.numNodes; j++) {
                if(this.matrix[i][j]) {
                    this.matrix[i][j] = false;
		    nueva[j][i] = true;
		}
            }
	}
        this.matrix = nueva;
	return true;
    }

    @Override
    public String toString() {
        String string = this.numNodes + " " + this.numArcs;
        for( int i = 0; i < this.numNodes; ++i ) {
            for( int j = 0; j < this.numNodes; ++j ) {
                string += matrix[i][j] ? "\n" + i + " " + j : "";
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
                    for (int j = 0; j < this.numNodes; j++) {
                        if (this.matrix[i][j]) {
                            out.println(i + " " + j);
                        }
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
            throw new ExcepcionNoEsArchivo("\nProblema al leer el archivo" +
                    " \"" + fileName + "\":\n\tNO ES UN ARCHIVO!!!\n");
        } else if (!(new File(fileName)).canWrite()) {
            throw new ExcepcionArchivoNoSePuedeEscribir("\nProblema al leer" +
                    " el archivo \"" + fileName +"\":\n\tESTE ARCHIVO NO SE" +
                    " PUEDE LEER!!!\n");
        }
    }

    // METODOS PRIVADOS AUXILIARES:

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
            throw new ExcepcionArchivoNoSePuedeLeer("Problema Leyendo la"
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
                                " la linea " + k + ", y " +
                                "se esperaba en el intervalo [0 - " +
                                nArc + "]");
                    } else if (dst < 0 || this.numNodes <= dst) {
                        throw new ExcepcionInconsistenciaNumeroDeNodos("\nEl " +
                                "grafo de entrada tiene un numero de nodos " +
                                "distinto al indicado al principio del ar" +
                                "chivo:\nEncontrado \"" + tokens[1] + "\" en" +
                                " la linea " + k + ", y " +
                                "se esperaba en el intervalo [0 - " +
                                nArc + "]");
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
                            " del archivo \"" + fileName + "\" hay " +
                        "un error de sintaxis:\nSe esperaban dos elementos (" +
                        "src dst), y se encontro:\n\t\"" + linea + "\"\n");
            }
            k++;
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
        }
        if (linea == null && k-2 != nArc) {
            throw new ExcepcionInconsistenciaNumeroDeArcos("El grafo de entra" +
                    "da tiene menos arcos que los indicados al principio del " +
                    "archivo:\nTiene " + (k-2) + " arco(s), y se esperaba(n) " +
                    nArc + " arco(s)");
        }
    }

    private void copy_matrix(boolean src[][], boolean dst[][]) {
        if (src.length == dst.length && src[0].length == dst[0].length) {
            for (int i = 0; i < src.length; ++i) {
                for (int j = 0; j < src[0].length; ++j) {
                    dst[i][j] = src[i][j];
                }
            }
        }
    }
}