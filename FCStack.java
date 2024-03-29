import java.util.ArrayList;
import java.util.Random;

public class FCStack {
    private ArrayList<Flashcard> stack;
    private boolean isJeopardized;

    // constructor
    public FCStack() {
        this(new ArrayList<Flashcard>());
    }

    public FCStack(ArrayList<Flashcard> setStack) {
        stack = setStack;
    }

    public FCStack genRandStack(int size) {
        try {
            FCStack oldStack = new FCStack(stack);
            ArrayList<Flashcard> stack = oldStack.getStack();

            if (stack.size() < size) {
                return oldStack;
            }
            else {
                ArrayList<Flashcard> randStack = new ArrayList<Flashcard>();
                while (randStack.size() < size) {
                    randStack.add(stack.remove((int) (Math.random() * stack.size())));
                }
                return new FCStack(randStack);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // methods to reorganize order of Flashcards

    // randomizes Flashcard order, O(n)
    public void randomize() {
        Random rng = new Random();
        ArrayList<Flashcard> newStack = new ArrayList<Flashcard>();
        while (stack.size() != 0) 
            newStack.add(stack.remove(rng.nextInt(stack.size())));
        stack = newStack;
        System.out.println(newStack.size());
        // for (Flashcard fc : newStack) {
        //     System.out.println(fc);
        // }
    }

    public void jeopardize() {
        for (Flashcard fc : stack) {
            String oldPrompt = fc.getPrompt();
            fc.setPrompt(fc.getResponse());
            fc.setResponse(oldPrompt);
        }
        isJeopardized = ! isJeopardized;
    }

    public void alphabetize() {
        stack = MergeSort.mergeSort(stack);
    }

    public boolean isJeopardized() {
        return isJeopardized;
    }

    // toString
    public String toString() {
        String promptList = "";
        for (int offset = 0; offset < stack.size(); offset++)
            promptList += stack.get(offset).getPrompt() + ", ";
        return promptList;
    }

    // getters
    public ArrayList<Flashcard> getStack() {
        return stack;
    }

    // for isolated testing
    public static void main(String[] args) {
        FCStack deck = new FCStack();
        // deck.getStack().add(new Flashcard("a", "b", "c", "d"));
        // deck.getStack().add(new Flashcard("e", "f", "g", "h"));
        // deck.getStack().add(new Flashcard("1", "2", "3", "4"));
        // deck.getStack().add(new Flashcard("5", "6", "7", "8"));
        deck.randomize();
        System.out.println(deck);
    }
}