import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Stack<E> extends ArrayList<E> {
    public Stack() {
        super();
    }

    public Stack(ArrayList<E> newList) {
        this();
        for (E element : newList) {
            super.add(element);
        }
    }

    public boolean add(E element) {
        super.add(0, element);
        return true;
    }

    public E peek() {
        return size() > 0 ? get(0) : null;
    }

    public E pop() {
        return remove(0);
    }

    public ArrayList<E> removeDupes(ArrayList<E> mainList, ArrayList<E> checkList) {
        Stack<E> check = new Stack(checkList);
        for (int index = 0; mainList.size() > 0 && check.size() > 0; index++) {
            if (index == mainList.size()) {
                check.pop();
                index = 0;
            }
            if (mainList.get(index).equals(check.peek())) {
                check.pop();
                mainList.remove(index);
                index = 0;
            }
            // System.out.println("working...");
        }
        return mainList;
    }

    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<String>();
        for (int index = 0; index < 5; index++) {
            test.add(index + "");
        }
        for (String element : test) {
            System.out.print(element + " ");
        }
        System.out.println();
        Stack<String> testStack = new Stack<String>(test);
        for (String stackElem : testStack) {
            System.out.print(stackElem + " ");
        }
        System.out.println();
        test.remove(0);
        for (String element : test) {
            System.out.print(element + " ");
        }
        System.out.println(test.size());

    }
}