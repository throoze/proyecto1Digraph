/**
 * DiGraphList es una clase comcreta que ud debe implementar
 * Los arcos son almacenados como una lista y son almacenados en un
 * arreglo donde la posicion i
 *
 * @author Les profs
 * @version 1.0
 * @since 1.6
**/

import java.io.IOException;

public class DiGraphList extends DiGraph {

    // arreglo de lista de los arcos, inArc[i] contine la lista
    // de los arcos que cuyo destino es el nodo i
  private List<Arc> inArcs[];
    // arreglo de lista de los arcos, outArc[i] contine la lista
    // de los arcos que cuyo fuente es el nodo i
  private List<Arc> outArcs[];

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
   public DiGraphList(String fileName) {

   }

   /**
    * Crea un DiGraphList a partir del DiGraph g
    * @param g
    */
   public DiGraphList(DiGraph g) {

   }

   @Override
   public DiGraphList clone() {
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