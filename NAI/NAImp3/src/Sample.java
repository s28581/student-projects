import java.util.ArrayList;

public class Sample {
    private String language;
    private ArrayList<TextVector> asciiVectors;

    public Sample(String language, ArrayList<TextVector> asciiVectors) {
        this.language = language;
        this.asciiVectors = asciiVectors;
    }

    public String getLanguage() {
        return language;
    }

    public ArrayList<TextVector> getAsciiVectors() {
        return asciiVectors;
    }
}

