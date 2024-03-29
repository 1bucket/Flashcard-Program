public class Flashcard {

    // fields
    // private String subject;
    // private String category;
    // private String sleeve;
    private String sleevePath;
    private String prompt;
    private String response;

    // constructor
    public Flashcard(String setPath, String setPrompt, String setResponse) {
        sleevePath = setPath;
        prompt = setPrompt;
        response = setResponse;
    }

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

    public boolean equals(Flashcard fc) {
        return sleevePath.equals(fc.getPath()) &&
               prompt.equals(fc.getPrompt()) &&
               response.equals(fc.getResponse());
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