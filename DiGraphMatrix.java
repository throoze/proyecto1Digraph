import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * DiGraphMatrix es una clase concreta que representa un digrafo usando la
 * estructura de la matriz de adyacencias.
 *
 * @author Les profs
 * @author Victor De Ponte, 05-38087
 * @author Karina Valera, 06-40414
 * @version 2.0
 * @since 1.6
**/
public class DiGraphMatrix extends DiGraph {

    // Modelo de representación:
    // estructura de la matriz de adyacencias que se debe utilizar
    private boolean matrix[][];
    
    // Constructores:

    /**
     * Crea un DiGraphMatrix vacio.
     * <b>Pre</b>: {@code true;}
     * <b>Post</b>: este DiGraphMatrix está vacio.
     */
    public DiGraphMatrix() {
        this.matrix = null;
        this.numArcs = 0;
        this.numNodes = 0;
    }
    
    /**
     * Crea un DiGraphMatrix con n nodos y sin arcos
     * <b>Pre</b>: {@code n} &lt; {@code 0}
     * <b>Post</b>: este DiGraphMatrix tiene {@code n} nodos y ningún arco.
     * @param n el número de nodos con los que se inicializa este DiGraphMatrix.
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
     * <blockquote>
     * <p><b>Sintaxis</b>:</p>
     * <p>numNodos numArcos</p>
     * <p>nodoSrc nodoDst</p>
     * <p>nodoSrc nodoDst</p>
     * <p>nodoSrc nodoDst</p>
     * <p>   .       .   </p>
     * <p>   .       .   </p>
     * <p>   .       .   </p>
     * <p>nodoSrc nodoDst</p>
     * </blockquote>
     * <b>Pre</b>: {@code fileName} debe existir, ser un archivo, poder leerse,
     * no puede tener errores de formato ni inconsistencias en el número de
     * nodos o arcos.
     * <b>Post</b>: Este DiGraphMatrix se inicializa exitosamente con el DiGraph
     * representado en el archivo {@code fileName}.
     * @param fileName Nombre del archivo a leer
     * @throws IOException En caso de que {@code fileName} no exista, no sea un
     * archivo, no se pueda leer, tenga un error de formato, o alguna
     * inconsistencia en cuanto al numero de arcos o el numero de nodos
     */
    public DiGraphMatrix(String fileName) throws IOException {
        this(0);
        this.read(fileName);
    }
    
    /**
     * Crea un DiGraphMatrix a partir del DiGraph g
     * <b>Pre</b>: {@code true;}
     * <b>Post</b>: {@code this.equals(g)}
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

    /**
     * Agrega un arco a este DiGraphMatrix
     * <b>Pre</b>: Los nodos src y dst deben encontrase en el DigraphMatrix y no
     * debe existir un arco entre ellos.
     * <b>Post</b>: El DigraphMatrix contendra un nuevo arco que tendra a src y
     * dst como nodos fuente y destino respectivamente.
     *
     * @param src nodo fuente del arco
     * @param dst nodo destino del arco
     * @return El arco agregado y null en caso de que los nodos src y dst no se
     * encuentren en el DiGraphMatrix
     *
     */
    public Arc addArc(int src, int dst) {
        if ((0 <= src && src < this.numNodes) &&
            (0 <= dst && dst < this.numNodes)) {
            if (!this.isArc(src, dst)) {
                this.matrix[src][dst] = true;
                this.numArcs++;
                Arc arco = new Arc(src, dst);
                return arco;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Agrega un arco a este DiGraphMatrix
     * <b>Pre</b>: Los nodos src y dst deben encontrase en el DigraphMatrix y no
     * debe existir un arco entre ellos.
     * <b>Post</b>: El DigraphMatrix contendra un nuevo arco que tendra a src y
     * dst como nodos fuente y destino respectivamente y el costo {@code costo}.
     *
     * @param src nodo fuente del arco
     * @param dst nodo destino del arco
     * @param costo costo del arco
     * @return El arco agregado y null en caso de que los nodos src y dst no se
     * encuentren en el DiGraphMatrix
     *
     */
    public Arc addArc(int src, int dst, double costo) {
        if ((0 <= src && src < this.numNodes) &&
            (0 <= dst && dst < this.numNodes)) {
            if (!this.isArc(src, dst)) {
                this.matrix[src][dst] = true;
                Arc arco = new Arc(src, dst, costo);
                this.numArcs++;
                return arco;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Permite agregar <i>num</i> nuevos nodos a este DiGraphMatrix.
     * <b>Pre</b>: Debe existir un DigraphMatrix.
     * <b>Post</b>: El DigraphMatrix contendrá <i>num</i> nodos nuevos.
     *
     * @param num numero de nodos a agregar
     */
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
     * calculada usando el algoritmo Roy-Warshal.
     * <b>Pre</b>: Debe existir un DigraphMatrix.
     * <b>Post</b>: Se obtendra el Digraph relacionado con la matriz de
     * adyacencias del DigraphMatrix, calculada usando el algoritmo de Roy-Warshal
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
            nuevo.addArc(i, i);
        }
        // Se calculan los demás arcos transitivos
        int flag;
        do {
            flag = nuevo.numArcs;
            for (int i = 0; i < nuevo.numNodes; i++) {
                for (int j = 0; j < nuevo.numNodes; j++) {
                    if (nuevo.matrix[i][j] && i != j) {
                        for (int k = 0; k < nuevo.numNodes; k++) {
                            if (nuevo.isArc(j, k) && !nuevo.isArc(i, k)) {
                                nuevo.addArc(i, k);
                            }
                        }
                    }
                }
            }
        } while (nuevo.numArcs > flag);
        return nuevo;
    }

    /**
     * Genera una copia de este DiGraphMatrix.
     * <b>Pre</b>: Debe existir un DigraphMatrix.
     * <b>Post</b>: El DigraphMatrix tendra una copia exacta.
     *
     * @return una copia de este DiGraphMatrix.
     */
    @Override
    public DiGraphMatrix clone() {
       DiGraphMatrix nuevo = new DiGraphMatrix(this);
       return nuevo;
    }

    /**
     * Elimina un arco de este DiGraphMatrix
     * <b>Pre</b>: Los nodos fuente y destino, es decir nodeIniId y nodeFinId
     * deben existir en el DigraphMatrix.
     * <b>Post</b>: No existira arco entre los nodos nodeIniId y nodeFinId que
     * pertencen al DigraphMatrix.
     *
     * @param nodeIniId nodo fuente del arco
     * @param nodeFinId nodo destino del arco
     * @return El arco eliminado y null en caso de que los nodos nodeIniId y
     * nodeFinId no se encuentren en el DiGraphMatrix.
     */
    public Arc delArc(int nodeIniId, int nodeFinId) {
        if ((0 <= nodeIniId && nodeIniId < this.numNodes) &&
            (0 <= nodeFinId && nodeFinId < this.numNodes)) {
            if (this.isArc(nodeIniId, nodeFinId)) {
                Arc arco = new Arc(nodeIniId, nodeFinId);
                this.matrix[nodeIniId][nodeFinId] = false;
                return arco;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Determina si el DiGraph g es igual a este DiGraphMatrix
     * <b>Pre</b>: debe existir un DigraphMatrix y un Digraph g.
     * <b>Post</b>: Se obtendra true en caso de que los grafos relacionados sean
     * iguales y false en caso contrario.
     *
     * @param g el grafo con el que se quiere comparar
     * @return true si los dos DiGraph contienen los mismos nodos y los mismos
     * arcos, return false en caso contrario.
     */
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

    /**
     * Busca el Arco cuyo nodo fuente es nodoSrc y nodo destino es nodoDst.
     * <b>Pre</b>: Los nodos nodoSrc y nodoDst deben pertenecer al DigraphMatrix
     * y debe existir un arco entre ellos.
     * <b>Post</b>: Se obtendra, en caso de que exista, el arco cuyos nodos
     * fuente y destino son nodoSrc y nodoDst respectivamente.
     *
     * @param nodoSrc nodo fuente
     * @param nodoDst nodo destino
     *
     * @return el Arco cuyo nodo fuente es nodoSrc y nodo destino es nodoDst.
     */
    public Arc getArc(int nodoSrc, int nodoDst) {
        if (this.isArc(nodoSrc, nodoDst)) {
            return new Arc(nodoSrc, nodoDst);
        } else {
            return null;
        }
    }

    /**
     * Retorna el grado de un nodo en este DiGraphMatrix.
     * <b>Pre</b>: El nodo nodeId debe pertencer al DigraphMatrix
     * <b>Post</b>: Se obtendra el numero de arcos que llegan y salen de nodeId,
     * es decir el grado.
     *
     * @param nodeId identificacion del nodo
     * @return el grado del nodo nodeId en este Grafo
     */
    public int getDegree(int nodeId) {
        return this.getInDegree(nodeId) + this.getOutDegree(nodeId);
    }

    /**
     * Retorna el grado interno de un nodo en este DiGraphMatrix
     * <b>Pre</b>: El nodo nodeId debe pertenecer al DigraphMatrix
     * <b>Post</b>: Se obtendra el numero de arcos que llegan a NodeId, es decir
     * el grado interno.
     *
     * @param nodeId identificacion del nodo
     * @return el grado interno del nodo nodeId en este Grafo.
     */
    public int getInDegree(int nodeId) {
        int k = 0;
        for(int i=0; i<this.numNodes;i++){
            if(this.matrix[i][nodeId] == true ){
                k++;
            }
	}
	return k;
    }

    /**
     * Retorna la lista de arcos que tienen a nodeId como destino.
     * <b>Pre</b>: El nodoId debe pertencer al DigraphMatrix.
     * <b>Post</b>: Se obtendra la lista de arcos que tienen a nodeId como nodo
     * final.
     *
     * @param nodeId identificador del nodo
     * @return la lista de arcos que tienen a nodeId como destino.
     */
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

    /**
     * Retorna el numero de arcos en el DigraphMatrix.
     * <b>Pre</b>: Debe existir un DigraphMatrix.
     * <b>Post</b>: Se obtendra el numero de arcos que pertencen al
     * DigraphMatrix.
     *
     * @return numero de arcos que hay en el DigraphMatrix.
     */
    public int getNumberOfArcs() {
        return this.numArcs;
    }

    /**
     * Retorna el numero de nodos que hay en el DigraphMatrix.
     * <b>Pre</b>: Debe existir un DigraphMatrix.
     * <b>Post</b>: Se obtendra el numero de nodos que pertencen al
     * DigraphMatrix.
     *
     * @return numero de nodos en el grafo
     */
    public int getNumberOfNodes() {
        return this.numNodes;
    }

    /**
     * Retorna el grado externo de un nodo en este DiGraphMatrix.
     * <b>Pre</b>: El nodo nodeId debe pertenecer al DigraphMatrix.
     * <b>Post</b>: Se obtendra el numero de arcos que salen de nodeId, es decir
     * el grado externo.
     *
     * @param nodeId identificacion del nodo
     * @return el grado externo del nodo nodeId en este Grafo
     */
    public int getOutDegree(int nodeId) {

    int k = 0;
    for(int i=0; i<numNodes;i++){
	 if(this.matrix[nodeId][i] == true ){
	   k++;
	   }
	 }
	 return k;
   }

    /**
     * Retorna la lista de arcos que tienen a nodeId como fuente
     * <b>Pre</b>: El nodo nodeId debe pertenecer al DigraphMatrix.
     * <b>Post</b>:Se obtendra la lista de arcos que tienen a nodeId como nodo
     * inicial.
     *
     * @param nodeId identificador del nodo
     * @return la lista de arcos que tienen a nodeId como fuente
     */
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

    /**
     * Retorna la lista de predecesores del nodo nodeId
     * <b>Pre</b>: El nodo nodeId debe pertenecer al DigraphMatrix.
     * <b>Post</b>: Se obtendra la lista de nodos que tienen a nodeId como nodo
     * de destino.
     *
     * @param nodeId el id del nodo del que se quieren los predecesores
     * @return lista de predecesores de nodeId
     */
    public List<Integer> getPredecesors(int nodeId) {
        List<Integer> predecesors = new Lista();
        for (int k = 0; k < this.numNodes; k++) {
            if(this.matrix [k][nodeId] == true){
                predecesors.add(new Integer(k));
            }
        }
        return predecesors;
    }

    /**
     * Retorna la lista de sucesores del nodo nodeId
     * <b>Pre</b>: El nodoId debe pertenecer al DigraphMatrix.
     * <b>Post</b>: Se obtendra la lista de nodos que tienen a nodeId como nodo
     * fuente.
     *
     * @param nodeId el id del nodo del que se quieren los sucesores
     * @return lista de sucesores de nodeId
     */
    public List<Integer> getSucesors(int nodeId) {
        List<Integer> sucesors = new Lista();
        for (int k = 0; k < this.numNodes; k++) {
            if(this.matrix [nodeId][k]== true){
                sucesors.add(new Integer(k));
            }
        }
        return sucesors;
    }

    /**
     * Indica si un arco existe en este DiGraphMatrix.
     * <b>Pre</b>: Los nodos src y dst deben pertenecer al DigraphMatrix.
     * <b>Post</b>: Se obtendra true en caso de que el arco exista y false si
     * ocurre lo contrario.
     *
     * @param src el id del nodo origen del arco
     * @param dst el id del nodo destino del arco
     * @return true si exite un arco desde el nodo src hasta el nodo dst.
     * false en caso contrario
     */
    @Override
    public boolean isArc(int src, int dst) {
        boolean es = false;
        if ((0 <= src && src < this.numNodes) &&
            (0 <= dst && dst < this.numNodes)) {
            es = this.matrix[src][dst];
        }
        return es;
    }

    /**
     * Inicializa este DiGraphMatrix en el DiGraph representado en el contenido
     * del archivo {@code fileName}.
     * <blockquote>
     * <p><b>Sintaxis</b>:</p>
     * <p>numNodos numArcos</p>
     * <p>nodoSrc nodoDst</p>
     * <p>nodoSrc nodoDst</p>
     * <p>nodoSrc nodoDst</p>
     * <p>   .       .   </p>
     * <p>   .       .   </p>
     * <p>   .       .   </p>
     * <p>nodoSrc nodoDst</p>
     * </blockquote>
     * <b>Pre</b>: {@code fileName} debe existir, ser un archivo, poder leerse,
     * no puede tener errores de formato ni inconsistencias en el número de
     * nodos o arcos.
     * <b>Post</b>: Este DiGraphMatrix se inicializa exitosamente con el DiGraph
     * representado en el archivo {@code fileName}.
     *
     * @param fileName Nombre del archivo a leer
     * @throws IOException En caso de que {@code fileName} no exista, no sea un
     * archivo, no se pueda leer, tenga un error de formato, o alguna
     * inconsistencia en cuanto al numero de arcos o el numero de nodos
     */
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

    /**
     * Remueve todos los arcos de este grafo
     * <b>Pre</b>: Debe existir un DigraphMatrix, y sus nodos deben estar
     * conectados mediante arcos.
     * <b>Post</b>: Se obtendra la lista de los arcos que fueron eliminados.
     *
     * @return lista de arcos eliminados
     */
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

    /** 
     * Invierte la direccion de un arco
     * <b>Pre</b>: Los nodos nodeIniId y nodeFinId deben pertenecer al 
     * DigraphMatrix, y seran el nodo fuente y destino respectivamente en caso 
     * de que exista un arco entre ellos.
     * <b>Post</b>: Se obtendra true en caso de que el arco haya sido invertido
     * y false en caso contrario.
     * 
     * @param nodeIniId nodo fuente del arco antes de invertirlo
     * @param nodeFinId nodo destino del arco antes de invertirlo
     * @return true si el arco fue invertido, false en caso contrario
     */
    public boolean reverseArc(int nodeIniId, int nodeFinId) {
        if((0 <= nodeIniId && nodeIniId < this.numNodes) &&
            (0 <= nodeFinId && nodeFinId < this.numNodes)){
            if (this.matrix[nodeIniId][nodeFinId]) {
                this.matrix[nodeIniId][nodeFinId] = false;
                this.matrix[nodeFinId][nodeIniId] = true;
                return true;
            } else {
                return false;
            }
	} else {
            return false;
        }
    }

    /**
     * Invierte todos los arcos del DiGraphMatrix.
     * <b>Pre</b>: Debe existir un DipraphMatrix.
     * <b>Post</b>: Se obtiene true en caso de que se hayan invertido todos los
     * arcos y false en caso contrario.
     *
     * @return true si todos los arcos fueron invertidos, false en caso
     * contrario. En caso de que algun nodo no puede ser invertido, el grafo
     * debe quedar sin alteraciones.
     */
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

    /**
     * Retorna la representacion en String de este DiGraphMatrix.
     * <b>Pre</b>: Debe existir un DigraphMatrix.
     * <b>Post</b>: Se obtendra la representacion en String del DigraphMatrix.
     * 
     * @return la representacion en String de este DiGraphMatrix.
     */
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

    /**
     * Escribe la representacion de este DiGraph en el archivo {@code fileName},
     * usando el formato siguiente:
     * <blockquote>
     * <p><b>Sintaxis</b>:</p>
     * <p>numNodos numArcos</p>
     * <p>nodoSrc nodoDst</p>
     * <p>nodoSrc nodoDst</p>
     * <p>nodoSrc nodoDst</p>
     * <p>   .       .   </p>
     * <p>   .       .   </p>
     * <p>   .       .   </p>
     * <p>nodoSrc nodoDst</p>
     * </blockquote>
     * <b>pre</b>: {@code fileName} debe existir, ser un archivo, y poder
     * escribirse.
     * <b>post</b>: El archivo {@code fileName} contiene la representación de
     * este DiGraph.
     * @param fileName Archivo a escribir
     * @throws IOException En caso de que el archivo {@code fileName} no exista,
     * no sea un archivo o no se pueda escribir en el.
     */
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

    /**
     * Método auxiliar para llenar este DiGraph leyendo desde el archivo de
     * nombre {@code fileName}.
     * <b>Pre</b>: {@code fileName} debe existir, ser un archivo, poder leerse,
     * no puede tener errores de formato ni inconsistencias en el número de
     * nodos o arcos. {@code inbuff} debe estar abierto y haberse leido solo la
     * primera linea. {@code nArc} debe ser el numero de arcos leido en la
     * primera linea de {@code fileName}.
     * <b>Post</b>: Este DiGraphMatrix se inicializa exitosamente con el DiGraph
     * representado en el archivo {@code fileName}.
     * @param inbuff Buffer de lectura a travez del cual se lee {@code fileName}
     * @param fileName Nombre del archivo a leer
     * @param nArc Número de arcos que tiene este DiGraph.
     * @throws ExcepcionArchivoNoSePuedeLeer En caso de que {@code fileName} no
     * se pueda leer
     * @throws ExcepcionFormatoIncorrecto En caso de que el formato especificado
     * no se cumpla
     * @throws ExcepcionArcoRepetido En caso de que haya un arco repetido
     * @throws ExcepcionInconsistenciaNumeroDeNodos En caso de que se consiga un
     * nodo que no pertenezca a este DiGraph
     * @throws ExcepcionInconsistenciaNumeroDeArcos en caso de que haya más o
     * menos arcos que los indicados en la primera linea ({@code nArc})
     */
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

    /**
     * Copia la matriz {@code src} en la matriz {@code dst}
     * <b>Pre</b>: Ambas matrices deben tener el mismo tamaño.
     * <b>Pre</b>: la matriz {@code dst} será igual a la matrix {@code src}.
     *
     * @param src matriz origen de la copia
     * @param dst matriz destino de la copia
     */
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