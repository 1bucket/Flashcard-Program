import java.util.ArrayList;

public class MergeSort {

    public static String removeLeadArticle(String entry) {
        String temp = entry.toLowerCase();
        String[] articles = new String[] {"the ", "a ", "el ", "la ", "los ", "las ", "un ", "una ", "unos ", "unas "};
        String firstArticle = leadArticle(temp, articles);
        if (firstArticle != null) {
            entry = entry.substring(firstArticle.length()).trim();
        }
        return entry;
    }

    public static String leadArticle(String compare, String[] articles) {
        for (String art : articles) {
            if (compare.indexOf(art) == 0) {
                return art;
            }
        }
        return null;
    }

    public static ArrayList<Flashcard> merge(ArrayList<Flashcard> left, ArrayList<Flashcard> right) {
        if (left.size() == 0) return right;
        else if (right.size() == 0) return left;
        else {
            int leftOffset = 0;
            int rightOffset = 0;
            // int mergedOffset = 0;
            // ArrayList<Flashcard> merged = new int[left.length + right.length];
            ArrayList<Flashcard> merged = new ArrayList<Flashcard>();
            while (leftOffset < left.size() && rightOffset < right.size()) {
                Flashcard leftMin = left.get(leftOffset);
                String leftPrompt = leftMin.getPrompt();
                Flashcard rightMin = right.get(rightOffset);
                String rightPrompt = rightMin.getPrompt();

                if (removeLeadArticle(leftPrompt).compareTo(removeLeadArticle(rightPrompt)) < 0) {
                    merged.add(leftMin);
                    leftOffset++;
                    // mergedOffset++;
                }
                else {
                    merged.add(rightMin);
                    rightOffset++;
                    // mergedOffset++;
                }
            }
            // System.out.println("left " + leftOffset + " right " + rightOffset);
            if (leftOffset < left.size()) copyRest(left, leftOffset, merged);
            else copyRest(right, rightOffset, merged);
            return merged;
            // return new ArrayList<T>();
        }
    }

    // helper method for merge()
    public static void copyRest(ArrayList<Flashcard> start, int startOffset, ArrayList<Flashcard> dest) {
        // while (startOffset < start.length && destOffset < dest.length) {
        //     dest[destOffset] = start[startOffset];
        //     startOffset++;
        //     destOffset++;
        // }
        for (int index = startOffset; index < start.size(); index++) {
            dest.add(start.get(index));
        }
    }

    public static ArrayList<Flashcard> mergeSort(ArrayList<Flashcard> data) {
        int length = data.size();
        if (length < 2) return data;
        else return merge(
            mergeSort(copyPart(data, 0, length / 2 - 1)),
            mergeSort(copyPart(data, length / 2, length - 1))
        );
    }

    // helper method for mergeSort()
    public static ArrayList<Flashcard> copyPart(ArrayList<Flashcard> data, int start, int end) {
        // int part = new int[end - start + 1];
        ArrayList<Flashcard> part = new ArrayList<Flashcard>();
        for (int index = start; index <= end; index++) {
            part.add(data.get(index));
        }
        // int partOffset = 0;
        // while (start <= end) {
        //     part[partOffset] = data[start];
        //     start++;
        //     partOffset++;
        // }
        return part;
    }

    public static void main(String[] args) {
        ArrayList<Flashcard> test = Manage.findFCsUnder("merge test/");
        for (Flashcard fc : test) {
            System.out.println(fc);
        }
        System.out.println("--------");
        for (Flashcard fc : mergeSort(test)) {
            System.out.println(fc);
        }
        System.out.println("--- done ---");
        String whoa = "The fun";
        System.out.println(whoa);
        System.out.println(removeLeadArticle(whoa));
        System.out.println(whoa);
    }
}

