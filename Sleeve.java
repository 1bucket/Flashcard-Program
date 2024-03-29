import java.util.ArrayList;

public class Sleeve {
    private String sleevePath;
    private String name;

    // constructors
    public Sleeve() {
        this(null, null);
    }

    public Sleeve(String newPath, String newName) {
        sleevePath = newPath;
        name = newName;
    }

    // setters
    public void setName(String newName) {
        name = newName;
    }
    public void setPath(String newPath) {
        sleevePath = newPath;
    }
    // getters
    public String getName() {
        return name;
    }
    public String getPath() {
        return sleevePath;
    }

    public boolean equals(Sleeve sleeve) {
        return sleevePath.equals(sleeve.getPath()) &&
               name.equals(sleeve.getName());
    }
    // toString
    public String toString() {
        return "This Sleeve is named " + getName() +
               " and is located at " + getPath();
    }
}