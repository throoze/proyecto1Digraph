/**
 * Representa el objeto Lista(E), una lista de elementos del tipo E. implementa
 * la interfaz List(E).
 * @author victor
 */
public class Lista<E> implements List<E>{

    private Nodo head;
    private Nodo tail;
    private int  tam;

    public Lista() {
        this.head = new Nodo();
        this.tail = this.head;
        this.tam = 0;
    }

    public boolean add(E element) {
        if (this.isEmpty()) {
            this.head.next = new Nodo(this.head,element);
            this.head.prev = null;
            this.tail = head.next;
        } else {
            Nodo nuevo = new Nodo(this.tail,element);
            this.tail.next = nuevo;
            this.tail = nuevo;
        }
        this.tam++;
        return true;
    }

    // ESTE TIENE ERRORES
    public boolean add(int index, E element) {
        if (index <= this.tam) {
            if (index < this.tam) {
                Nodo aux = this.head;
                int k;
                for (k = -1; k < index; k++) {
                    aux = aux.next;
                }
                if (aux.next != null) {
                    Nodo nuevo = new Nodo(aux, element, aux.next);
                    aux.next.prev = nuevo;
                    aux.next = nuevo;
                } else {
                    Nodo nuevo = new Nodo(aux, element);
                    aux.next = nuevo;
                }
            } else if (index == this.tam) {
                Nodo nuevo = new Nodo(this.tail, element);
                this.tail.next = nuevo;
                this.tail = nuevo;
            }
            tam++;
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        this.head = new Nodo();
        this.tail = this.head;
        this.tam = 0;
    }

    @Override
    public List clone() {
        List<E> laux = new Lista();
        Nodo aux = this.head.next;
        while (aux != null) {
            laux.add((E)aux.elem);
            aux = aux.next;
        }
        return laux;
    }

    public boolean contains(Object o) {
        Nodo aux = this.head;
        while (aux.next != null) {
            aux =  aux.next;
            if (aux.elem.equals((E)o)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(List<E> o) {
        Object[] esta = this.toArray();
        Object[] otra = o.toArray();
        if (esta.length != otra.length) {
            return false;
        }
        for (int k = 0; k < esta.length; k++) {
            if (!(esta[k].equals(otra[k]))) {
                return false;
            }
        }
        return true;
    }

    public E get(int index) {
        if (0 <= index && index < this.size()) {
            Object[] lista = this.toArray();
            return (E) lista[index];
        } else {
            return null;
        }
    }

    public int indexOf(Object o) {
        Object[] lista = this.toArray();
        for ( int k = 0; k < lista.length; k++) {
            if ( lista[k].equals((E)o) ) {
                return k;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public E remove(int index) {
        if (index < this.tam -1) {
            Nodo aux = this.head;
            for (int k = -1; k < index && aux.next != null; k++) {
                aux = aux.next;
            }
            aux.prev.next = aux.next;
            if (aux.next != null) {
                aux.next.prev = aux.prev;
            }
            this.tam--;
            return (E) aux.elem;
        } else if (index == this.tam -1) {
            Nodo nodo = this.tail;
            this.tail = this.tail.prev;
            this.tail.next = null;
            this.tam--;
            return (E) nodo.elem;
        } else {
            return null;
        }
    }

    //ESTE TIENE ERRORES
    public boolean remove(Object o) {
        Nodo aux = this.head;
        while ( aux.next != null && !aux.elem.equals((E)o)) {
            aux = aux.next;
        }
        if (aux.elem.equals((E)o)){
            if (this.tail.equals(aux)) {
                this.tail = this.tail.prev;
                this.tail.next = null;
            } else {
                aux.prev.next = aux.next;
                aux.next.prev = aux.prev;
            }
            this.tam--;
            return true;
        }
        return false;
    }

    public int size() {
        return this.tam;
    }

    public Object[] toArray() {
        Object[] lista = new Object[this.size()];
        Nodo aux = this.head;
        for (int k = 0; k < lista.length; k++) {
            lista[k] = aux.next.elem;
            aux = aux.next;
        }
        return lista;
    }

    @Override
    public String toString() {
        Object[] lista = this.toArray();
        String s = "";
        for (int k = 0; k < lista.length; k++) {
            s += ((E)lista[k]).toString() + "\n";
        }
        return s;
    }

    private class Nodo <E>{
        public E elem;
        public Nodo next;
        public Nodo prev;

        public Nodo() {
            this.elem = null;
            this.next = null;
            this.prev = null;
        }

        public Nodo(Nodo ant, E elem) {
            this.elem = elem;
            this.prev = ant;
            this.next = null;
        }
        
        public Nodo(E elem, Nodo sig) {
            this.elem = elem;
            this.prev = null;
            this.next = sig;
        }
        
        public Nodo (Nodo ant, E elem, Nodo sig) {
            this.elem = elem;
            this.prev = ant;
            this.next = sig;
        }

        @Override
        public Nodo clone() {
            Nodo nuevo = new Nodo();
            nuevo.elem = this.elem;
            nuevo.next = this.next;
            nuevo.prev = this.prev;
            return nuevo;
        }

        @Override
        public boolean equals (Object o) {
            if (o instanceof Nodo) {
                Nodo nuevo = (Nodo) o;
                return (this.next == nuevo.next && this.prev == nuevo.prev &&
                        this.elem.equals(nuevo.elem));
            } else {
                return false;
            }
        }
    }

    // Funciones auxiliares:

    public static void main (String[] args) {
        System.out.println("\n\n\t\tBienvenido al programa de prueba de" +
                " Lista(Arc)!!!\n\n");
        System.out.println("\t\t\tPodras trabajar maximo con 2 listas\n\n");
        int opcion = 0;
        boolean exit = false;
        List<Arc> lista1 = null;
        List<Arc> lista2 = null;
        do {
            System.out.println("\t\t\tMENU:\n");
            System.out.println("01)Inicializar la primera Lista(Arc)");
            System.out.println("02)Inicializar la segunda Lista(Arc)");
            System.out.println("03)Añadir un nuevo elemento a la primera" +
                    " lista");
            System.out.println("04)Añadir un nuevo elemento a la primera" +
                    " lista, en una posicion especifica");
            System.out.println("05)Vaciar la primera lista");
            System.out.println("06)Inicializar la segunda lista como un" +
                    " \"clon\" de la primera");
            System.out.println("07)Saber si la primera lista contiene cierto" +
                    " elemento");
            System.out.println("08)Saber si la segunda lista contiene cierto" +
                    " elemento");
            System.out.println("09)Saber si la primera y la segunda lista son" +
                    " iguales");
            System.out.println("10)Obtener el elemento en cierta posicion en" +
                    " la primera lista");
            System.out.println("11)Obtener el elemento en cierta posicion en" +
                    " la segunda lista");
            System.out.println("12)Conocer la posicion de cierto elemento en" +
                    " la primera lista");
            System.out.println("13)Conocer la posicion de cierto elemento en" +
                    " la primera lista");
            System.out.println("14)Saber si la primera lista esta vacia");
            System.out.println("15)Saber si la segunda lista esta vacia");
            System.out.println("16)Eliminar el elemento en cierta posicion" +
                    " de la primera lista");
            System.out.println("17)Eliminar el elemento en cierta posicion" +
                    " de la segunda lista");
            System.out.println("18)Eliminar cierto elemento en la primera" +
                    " lista");
            System.out.println("19)Eliminar cierto elemento en la segunda" +
                    " lista");
            System.out.println("20)Saber el tamaño de la primera lista");
            System.out.println("21)Saber el tamaño de la segunda lista");
            System.out.println("22)Imprimir la lista en pantalla");
            System.out.println("23)Salir del programa");
            System.out.println("Introduzca una opcion valida [1-23]...");
            opcion = Console.readInt("\nQue desea hacer???\n\n\t>> ");
            if (opcion == 1) {
                lista1 = new Lista();
            } else if (opcion == 2) {
                lista2 = new Lista();
            } else if (opcion == 3) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                lista1.add(new Arc(nodoIni,nodoFin));
            } else if (opcion == 4) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                int posicion = Console.readInt
                        ("\nIngrese una posicion\n\t>>");
                lista1.add(posicion, new Arc(nodoIni, nodoFin));
            } else if (opcion == 5) {
                lista1.clear();
            } else if (opcion == 6) {
                lista2 = lista1.clone();
            } else if (opcion == 7) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                boolean esta = lista1.contains(new Arc(nodoIni,nodoFin));
                System.out.println("La lista " + (esta ? "SI":"NO")+
                        " contiene el elemento (" + nodoIni + ", " + nodoFin +
                        ")...");
            } else if (opcion == 8) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                boolean esta = lista2.contains(new Arc(nodoIni,nodoFin));
                System.out.println("La lista " + (esta ? "SI":"NO")+
                        " contiene el elemento (" + nodoIni + ", " + nodoFin +
                        ")...");
            } else if (opcion == 9) {
                boolean iguales = lista1.equals(lista2);
                System.out.println("La lista1 y la lista 2 " +
                        (iguales ? "SI":"NO") + " son iguales");
            } else if (opcion == 10) {
                int posicion = Console.readInt
                        ("\nIngrese una posicion\n\t>>");
                System.out.println("El elemento de la posicion "+posicion+
                        " es: "+lista1.get(posicion).toString());
            } else if (opcion == 11) {
                int posicion = Console.readInt
                        ("\nIngrese una posicion\n\t>>");
                System.out.println("El elemento de la posicion "+posicion+
                        " es: "+lista2.get(posicion).toString());
            } else if (opcion == 12) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                System.out.println("El elemento (" + nodoIni + ", " + nodoFin +
                        ") esta en la posicion: " +
                        lista1.indexOf(new Arc(nodoIni,nodoFin)));
            } else if (opcion == 13) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                System.out.println("El elemento (" + nodoIni + ", " + nodoFin +
                        ") esta en la posicion: " +
                        lista2.indexOf(new Arc(nodoIni,nodoFin)));
            } else if (opcion == 14) {
                System.out.println("\nLa primera lista " +
                        (lista1.isEmpty() ? "SI" : "NO") + " está vacia...\n");
            } else if (opcion == 15) {
                System.out.println("\nLa segunda lista " +
                        (lista2.isEmpty() ? "SI" : "NO") + " está vacia...\n");
            } else if (opcion == 16) {
                int posicion = Console.readInt
                        ("\nIngrese una posicion\n\t>>");
                Arc arco = lista1.remove(posicion);
                System.out.println("\nSe ha removido el arco: " +
                        arco.toString() + " de la primera lista...\n");
            } else if (opcion == 17) {
                int posicion = Console.readInt
                        ("\nIngrese una posicion\n\t>>");
                Arc arco = lista2.remove(posicion);
                System.out.println("\nSe ha removido el arco: " +
                        arco.toString() + " de la segunda lista...\n");
            } else if (opcion == 18) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                boolean removido = lista1.remove(new Arc(nodoIni, nodoFin));
                System.out.println("El elemento (" + nodoIni + ", " + nodoFin +
                        ") " + (removido ? "SI" : "NO") + " fue removido de " +
                        "la primera lista...\n");
            } else if (opcion == 19) {
                int nodoIni = Console.readInt
                        ("\nIngrese el nodo fuente\n\t>>");
                int nodoFin = Console.readInt
                        ("\nIngrese el nodo destino\n\t>>");
                boolean removido = lista2.remove(new Arc(nodoIni, nodoFin));
                System.out.println("El elemento (" + nodoIni + ", " + nodoFin +
                        ") " + (removido ? "SI" : "NO") + " fue removido de " +
                        "la segunda lista...\n");
            } else if (opcion == 20) {
                System.out.println("La primera lista es de tamaño: " +
                        lista1.size());
            } else if (opcion == 21) {
                System.out.println("La segunda lista es de tamaño: " +
                        lista2.size());
            } else if (opcion == 22) {
                System.out.println("\nLa Lista es:\n\n" + lista1.toString());
            } else if (opcion == 23) {
                exit = true;
            } else if (opcion < 1 || 23 < opcion) {
                System.out.println("Introduzca una opcion valida [0-23]...");
            }
        } while (!exit);

    }
}