package ajaksclitest;

public class LinkedList<T> {

    private class Elem {

        private T data;
        private Elem next;

        public Elem(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Elem first = null, last = null;

    public void append(T data) {
        Elem toAdd = new Elem(data);

        if (first == null) {
            first = toAdd;
        } else {
            last.next = toAdd;
        }

        last = toAdd;
    }
 
        
    public void prepend(T data) {
        Elem toAdd = new Elem(data);

        toAdd.next = first;
        first = toAdd;

        if (last == null) {
            last = toAdd;
        }
    }

    public T removeFirst() {
        if (first == null) {
            return null;
        }

        Elem toRemove = first;
        first = first.next;

        if (first == null) {
            last = null;
        }

        return toRemove.data;
    }
    /*public T removeLast() {
        if (first == null) {
            return null;
        }

        Elem toRemove = null;
        last = null;

        return ;
    }*/

    public T getFirstData() {
        if (first == null) {
            return null;
        } else {
            return first.data;
        }
    }

    public T get(int index) {
        if (first == null) {
            return null;
        }

        int currentIndex = 0;
        Elem current = first;

        while (current != null) {
            if (currentIndex == index) {
                break;
            }

            current = current.next;
            currentIndex++;
        }

        if (current == null) {
            return null;
        } else {
            return current.data;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        Elem current = first;

        while (current != null) {
            sb.append(current.data.toString()).append(" ");
            current = current.next;
        }

        return sb.toString();
    }
    
       public boolean isEmpty(){
        if(first == null){
            return true;
        }else{
            return false;
        }
    }

}
