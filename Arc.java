/**
 * Arc es una clase que permite almancenar la informacion de los arcos de un
 * grafo
 *
 * @author Les profs
 * @author Victor De Ponte, 05-38087
 * @author Karina Valera, 06-40414
 * @version 2.0
 */

public class Arc {
    
    private String id = "";
    private double cost;
    private int src = -1;
    private int dst = -1;

    public Arc() {
        super();
    }

    /**
     * Crea un arco entre los nodos src y dst
     *
     * @param src nodo origen del arco
     * @param dst nodo destino del arco
     */
    public Arc(int src, int dst) {
       this.src = src;
       this.dst = dst;
    }

    public Arc(int src, int dst, double cost) {
       this.src = src;
       this.dst = dst;
       this.cost = cost;
    }

    public Arc(int src, int dst, double cost, String id) {
       this.src = src;
       this.dst = dst;
       this.cost = cost;
       this.id = id;
    }

    /**
     * Retorna un nuevo {@code Arc} con el mismo fuente y el mismo destino que
     * este Arc.
     *
     * @return una lista con los mismos elementosque esta lista
     * @see java.lang.Cloneable
     */
    @Override
    public Object clone() {
       return new Arc(src, dst);
    }

    /**
     * Indica si el Arc a es igual a este Arc
     *
     * @param a Arc con el que se desea comparar.
     * @return true si los fuentes y destinos de los dos arcos son iguales.
     */
    @Override
    public boolean equals(Object a) {
       if (a instanceof Arc) {
           Arc nuevo = (Arc) a;
           return (this.dst == nuevo.dst && this.src == nuevo.src);
       } else {
           return false;
       }
    }

    /**
     * Pertmite obtener el costo de un arco: de ir de arco fuente al arco
     * destino.
     *
     * @return costo del Arco.
     */
    public double getCost() {
       return cost;
    }

    /**
     * Pertmite establecer el costo a este Arc.
     *
     * @param el costo a ser asignado a este Arco.
     */
    public void setCost(double c) {
       cost = c;
    }

    /**
     * Devuelve la etiqueta del arco.
     * @return La etiqueta del arco
     * @since 2.0
     */
    public String getId() {
        return this.id;
    }

    /**
     * Permite establecer la etiqueta del arco {@code this}.
     * @param s Nueva etiqueta para el arco.
     * @since 2.0
     */
    public void setId(String s) {
        this.id = s;
    }

    public int getSrc() {
        return this.src;
    }

    public void setSrc(int fuente) {
        this.src = fuente;
    }

    public int getDst() {
        return this.dst;
    }

    public void setDst(int destino) {
        this.dst = destino;
    }

    /**
     * Retorna la representacion en String del Arc
     * 
     * @return la representacion en String de este Arc
     */
    @Override
    public String toString() {
       return "(" + src + ", "+ dst+")";
    }
}