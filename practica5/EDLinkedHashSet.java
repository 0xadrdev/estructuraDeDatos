package practica5;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("unchecked")

public class EDLinkedHashSet<T> implements Set<T> {
    protected class Node<U>{
        U data;
        Node<U> next = null;
        Node<U> prev = null;

        Node(U data) {
            this.data = data;
        }
    }

    static final private int DEFAULT_CAPACITY = 10;
    static final private int DEFAULT_THRESHOLD = 7;

    Node<T>[] table;
    boolean[] used;
    int size = 0;
    int dirty = 0;
    int rehashThreshold;
    Node<T> first = null;
    Node<T> last = null;

    public EDLinkedHashSet() {
        table = new EDLinkedHashSet.Node[DEFAULT_CAPACITY];
        used = new boolean[DEFAULT_CAPACITY];
        rehashThreshold = DEFAULT_THRESHOLD;
    }

    public EDLinkedHashSet(Collection<T> col) {
        table = new EDLinkedHashSet.Node[DEFAULT_CAPACITY];
        used = new boolean[DEFAULT_CAPACITY];
        rehashThreshold = DEFAULT_THRESHOLD;

        addAll(col);
    }

    /**
     * Calcula un código de dispersión mayor que cero y ajustado al tamaño de <code>table</code>.
     * @param item  Un valor cualquiera, distinto de <code>null</code>.
     * @return Código de disersión ajustado al tamaño de la tabla.
     */
    int hash(T item) {
        return (item.hashCode() & Integer.MAX_VALUE) % table.length;
    }

    /**
     * Realiza la ampliación de las tabla y la redispersión de los elementos si se cumple la condición de que
     * <code>dirty > rehashThreshold</code>. La tabla  dobla su tamaño. El vector <code>used</code> y
     * <code>rehashThreshold</code> se modifican de forma análoga.
     */

    @SuppressWarnings("unchecked")

    private void rehash() {
        if (dirty >= rehashThreshold) {
            dirty = 0;

            table = new EDLinkedHashSet.Node[DEFAULT_CAPACITY * 2];
            used = new boolean[DEFAULT_CAPACITY * 2];

            rehashThreshold = rehashThreshold * 2; 

            Node actual = first; 

            while (actual != null) {
                int pos = hash((T) actual.data);
                while (used[pos]) {
                    if (pos++ == used.length - 1) pos = 0; // Si llego al ultimo vuelvo a empezar. 
                }
                table[pos] = actual; 
                used[pos] = true;
                actual = actual.next; 
                dirty++;
            }
        } 
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

	 /**
     * contains: checks if the set contains a given element
     * @param item element whose presence in this set is to be tested
     * @return true if the element is in the set, false in other case
     */
    // @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object item) {
        int pos = hash((T) item); 
        while (used[pos]) {
            if (table[pos] != null && table[pos].data.equals(item)) {
                return true;
            }
            pos = (pos + 1) % table.length;
        }
        return false;
    }

	/**
     * add(item): adds a new element to the collection
     * @param item element whose presence in this collection is to be ensured
     * @return true if the element has been added, false in other case
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(T item) {
        Node nuevoNodo = new Node(item);

        int pos = hash((T) item);
        int posLibre = -1;

        if (used[pos] == false) {
            table[pos] = nuevoNodo; 
            used[pos] = true;
            dirty++;
        } else {
            while (used[pos]) {
                if (table[pos] == null) {
                    if (posLibre == -1) {
                        posLibre = pos;
                    }
                } else if (table[pos].data.equals(item)) {
                    return false;
                }
                pos = (pos + 1) % table.length;
            }
            
            if (posLibre != -1) {
                table[posLibre] = nuevoNodo;
                used[posLibre] = true;
            } else {
                table[pos] = nuevoNodo;
                used[pos] = true;
                dirty++;
            }
        }

        if (first == null) {
            first = last = nuevoNodo; 
        } else {
            nuevoNodo.prev = last;
            last.next = nuevoNodo;
            last = nuevoNodo; 
        }
        rehash();
        size++;
        return true;
    }

	/**
     * remove(item): removes the given item from the set
     * @param item object to be removed from this set, if present
     * @return true if the item has been removed, false in other case
     */
    @Override
    public boolean remove(Object item) {
        int pos = hash((T) item);
        while (used[pos]) {
            if (table[pos] != null) {
                if (table[pos].data.equals(item)) break;
            }
            if (pos == used.length - 1) pos = -1; // Si es la ultima pos vuelvo a empezar. 
            pos++;
        } 
        if (table[pos] != null && table[pos].data.equals(item)) {
            Node actual = table[pos];
            table[pos] = null;
            size--;
            if (actual == first) {
                if (size == 0) {
                    clear();
                } else {
                    first = actual.next;
                    first.prev = null;
                }
            } else if (actual == last) {
                last = actual.prev;
                last.next = null;
            } else {
                actual.prev.next = actual.next;
                actual.next.prev = actual.prev;
            }
            return true;
        }
        return false; 
    }

	/**
     * retainAll(c) Retains only the elements in this collection that are contained in the specified collection 
     * @param c collection containing elements to be retained in this set
     * @return true if the collection has changed as a consequence of the operation, false in other case
     */
	@Override
    public boolean retainAll(Collection<?> c) {
        Node actual = first; 
        boolean esModificada = false;
        while (actual != null) {
            if (!c.contains(actual.data)) {
                esModificada = true; 
                remove(actual.data);
            }
            actual = actual.next;
        }
        return esModificada; 
    }   

    @Override
    public Iterator<T> iterator()  { throw new UnsupportedOperationException(); }


    @Override
    public Object[] toArray() {
        Object[] v = new Object[size];

        Node<T> ref = first;
        int i = 0;
        while (ref != null) {
            v[i] = ref.data;
            ref = ref.next;
            i++;
        }

        return v;
    }

    @Override
    public <T1> T1[] toArray(T1[] a)  {
        throw new UnsupportedOperationException();
    }

	

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {

        if (c == null)
            throw new NullPointerException();

        if (c.size() == 0)
            return false;

        int s = size;
        for (T item : c)
            add(item);

        return size != s;


    }

    

    @Override
    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }


    @Override
    public void clear() {
        size = dirty  = 0;
        first = last = null;

        for (int i=0; i < table.length; i++) {
            used[i] = false;
            table[i] = null;
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Table: {");
        boolean coma = false;
        for (int i = 0; i < table.length; i++){
            if (table[i] != null) {
                if (coma)
                    sb.append(", ");
                sb.append(i + ": " + table[i].data);
                coma = true;
            }
        }

        sb.append("}\n");

        sb.append("Insertion order: {");
        Node ref = first;
        coma = false;
        while (ref != null) {
            if (coma)
                sb.append(", ");
            sb.append(ref.data);
            ref = ref.next;
            coma = true;
        }
        sb.append("}\n");
        sb.append("size: " + size);
        sb.append(", capacity: " + table.length);
        sb.append(", rehashThreshold: " + rehashThreshold);

        return sb.toString();
    }

}
