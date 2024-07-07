import java.util.ArrayList;

public class Flashcard {
    public static final int PROMPT = 0;
    public static final int RESP = 1;
// hi
    // fields
    // private String subject;
    // private String category;
    // private String sleeve;
    private String sleevePath;
    private String prompt;
    private String response;
    private String promptImg;
    private String respImg;

    // constructor
    public Flashcard(String setPath, String setPrompt, String setResponse) {
        sleevePath = setPath;
        prompt = setPrompt;
        response = setResponse;
        promptImg = "";
        respImg = "";
    }

    public Flashcard(String setPath, String setPrompt, String setResponse, String setPromptImg, String setRespImg) {
        this(setPath, setPrompt, setResponse);
        if (setPromptImg != null) {
            promptImg = setPromptImg;
        }
        if (setRespImg != null) {
            respImg = setRespImg;
        }
        // filterImgPaths();
    }

    // private void filterImgPaths() {
    //     // filter out paths of ""
    //     for (int index = 0; index < promptImgs.size(); index += 1) {
    //         if (promptImgs.get(index) == "") {
    //             promptImgs.remove(index);
    //             index -= 1;
    //         }
    //     }
    //     for (int index = 0; index < respImgs.size(); index += 1) {
    //         if (respImgs.get(index) == "") {
    //             respImgs.remove(index);
    //             index -= 1;
    //         }
    //     }
    // }

    // toString method
    public String toString() {
        return "SleevePath: " + sleevePath + " | " + getPrompt() + " |=| " + getResponse();
    }

    // setters
    public void setPath(String newPath) {
        sleevePath = newPath;
    }

    public void setPrompt(String newPrompt) {
        prompt = newPrompt;
    }

    public void setResponse(String newResponse) {
        response = newResponse;
    }

    public void setPromptImg(String newPath) {
        if (newPath != null && newPath != "") {
            promptImg = newPath;
        }
    }

    public void setRespImg(String newPath) {
        if (newPath != null && newPath != "") {
            respImg = newPath;
        }
    }

    // public void removePromptImg(String path, int category) {
    //     if (category == PROMPT) {
    //         promptImgs.remove(path);
    //     }
    //     else if (category == RESP) {
    //         respImgs.remove(path);
    //     }
    //     else {
    //         System.out.println("Error: invalid img category for Flashcard");
    //     }
    // }

    // getters
    public String getPath() {
        return sleevePath;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getResponse() {
        return response;
    }

    public String getPromptImg() {
        return promptImg;
    }

    public String getRespImg() {
        return respImg;
    }

    public boolean equals(Flashcard fc) {
        // System.out.println(this + ",\n" + fc);
        return sleevePath.equals(fc.getPath()) &&
               prompt.equals(fc.getPrompt()) &&
               response.equals(fc.getResponse()) &&
               promptImg.equals(fc.getPromptImg()) &&
               respImg.equals(fc.getRespImg());
    }
    // for isolated testing
    public static void main(String[] args) {
        Flashcard firstCard = new Flashcard("Master/Government",  
                                            "limited government", 
                                            "principle by which the powers of the government are limited via" +
                                            " a written constitution");
        // possible way to do jeopardy mode
        System.out.println(firstCard);
        String temp = firstCard.getPrompt();
        firstCard.setPrompt(firstCard.getResponse());
        firstCard.setResponse(temp);

        // temp = firstCard.getSubject();
        // firstCard.setSubject(firstCard.getCategory());
        // firstCard.setCategory(temp);
        System.out.println(firstCard);
    }
}