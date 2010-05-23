/**
 * Representa el objeto Lista(E), una lista de elementos del tipo E. implementa
 * la interfaz List(E).
 * @author victor
 */
public class Lista<E> implements List<E>{

    private Nodo head;
    private Nodo tail;

    public Lista() {
        this.head = new Nodo();
        this.tail = this.head.next;
    }

    public boolean add(E element) {
        Nodo nuevo = new Nodo(this.tail,element);
        this.tail.next = nuevo;
        return true;
    }

    public boolean add(int index, E element) {
        Nodo aux = this.head;
        for (int k = 0; k <= index && aux.next != null; k++){
            aux = aux.next;
        }
        if (aux.next != null) {
            Nodo nuevo = new Nodo(aux.prev, element, aux.next);
            aux.prev.next = nuevo;
            aux.next.prev = nuevo;
        } else {
            Nodo nuevo = new Nodo(aux.prev, element);
            aux.prev.next = nuevo;
        }
        return true;
    }

    public void clear() {
        this.head = new Nodo();
        this.tail = new Nodo();
    }

    @Override
    public List clone() {
        List<E> laux = new Lista();
        Nodo aux = this.head;
        while (aux != null) {
            laux.add((E)aux.elem);
            aux =  aux.next;
        }
        return laux;
    }

    public boolean contains(Object o) {
        Nodo aux = this.head;
        while (aux.next != null) {
            if (aux.elem.equals(o)) {
                return true;
            }
            aux =  aux.next;
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
        Object[] lista = this.toArray();
        return (E) lista[index];
    }

    public int indexOf(Object o) {
        Object[] lista = this.toArray();
        for ( int k = 0; k < lista.length; k++) {
            if ( lista[k].equals(o) ) {
                return k;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public E remove(int index) {
        Nodo aux = this.head;
        for (int k = 0; k <= index && aux.next != null; k++){
            aux = aux.next;
        }
        aux.prev.next = aux.next;
        aux.next.prev = aux.prev;
        return (E) aux.elem;
    }

    public boolean remove(Object o) {
        Nodo aux = this.head;
        while ( aux.next != null || !aux.next.elem.equals(o)) {
            aux = aux.next;
        }
        if (aux.next.elem.equals(o)) {
            aux = aux.next;
            aux.prev.next = aux.next;
            aux.next.prev = aux.prev;
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        Nodo aux = this.head;
        int k = 0;
        while (aux.next != null){
            aux = aux.next;
            k++;
        }
        return k;
    }

    public Object[] toArray() {
        Object[] lista = new Object[this.size()];
        Nodo aux = this.head;
        for (int k = 0; k < lista.length; k++) {
            lista[k] = aux.next.elem;
        }
        return lista;
    }

    @Override
    public String toString() {
        Object[] lista = this.toArray();
        String s = "";
        for (int k = 0; k < lista.length; k++) {
            s = s +lista[k].toString() + "\n";
        }
        return s;
    }

    public Lista<E> concat(Lista<E> lista) {
        Lista<E> laux = new Lista();
        Nodo aux = this.head;
        while (aux != null) {
            laux.add((E)aux.elem);
            aux =  aux.next;
        }
        aux = lista.head;
        while (aux != null) {
            laux.add((E)aux.elem);
            aux =  aux.next;
        }
        return laux;
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
    }

    // Funciones auxiliares:

    public static void main (String[] args) {
        System.out.println("\n\n\t\tBienvenido al programa de prueba de" +
                " Lista("+args[0]+")!!!\n\n");
        System.out.println("\t\t\tPodras trabajar maximo con 2 listas\n\n");
        int opcion = 0;
        do {
            System.out.println("\t\t\tMENU:\n");
            System.out.println("01)Inicializar la primera Lista("+args[0]+")");
            System.out.println("02)Inicializar la segunda Lista("+args[0]+")");
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
            opcion = Console.readInt("\nQue desea hacer???\n\n\t>> ");
        } while (opcion < 1 || 23 < opcion);
    }
}