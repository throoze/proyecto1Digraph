/**
 * DiGraphMatrix es una clase concreta que ud debe implementar
 *
 * @author Les profs
 * @version 1.0
 * @since 1.6
**/

import java.io.IOException;

/**
 *
 * @author eduardo
 */
public class DiGraphMatrix extends DiGraph {

    // estructura de la matriz de adyacencias que se debe utilizar
   private boolean matrix[][];

   /**
    * Crea un DiGraphMatrix con n nodos y sin arcos
    * @param n
    */
   public DiGraphMatrix(int n) {
      matrix = new boolean[n][n];
      numNodes = n;
   }

   /**
    * Crea un DiGraphMatrix a partir del contenido del archivo.
    *
    * @param fileName nombre del archivo
    */
   
   public DiGraphMatrix(String fileName) {

   }

   /**
    * Crea un DiGraphMatrix a partir del DiGraph g
    * 
    * @param g el grafo fuente.
    */
   public DiGraphMatrix(DiGraph g) {

   }

   @Override
   public DiGraphMatrix clone() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

    public void addNodes(int num) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Arc addArc(int src, int dst) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Arc addArc(int src, int dst, double costo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Integer> getPredecesors(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Integer> getSucesors(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Arc getArc(int nodoSrc, int nodoDst) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void read(String fileName) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void write(String fileName) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDegree(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getOutDegree(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getInDegree(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNumberOfNodes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNumberOfArcs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Arc> getOutEdges(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Arc> getInEdges(int nodeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Arc delArc(int nodeIniId, int nodeFinId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Arc> removeAllArcs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean reverseArc(int nodeIniId, int nodeFinId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean reverseArcs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean equals(DiGraph g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Retorna un Digraph que es la clausura transitiva de este DiGraph
     * calculada usando el algoritmo Roy-Warshal
     *
     * @return un Digraph que es la clausura transitiva de este DiGraph
     * calculada usando el algoritmo Roy-Warshal
     */

    public DiGraph royWarshall() {
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
