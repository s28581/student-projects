import java.util.ArrayList;
import java.util.List;

public class TextVector {
    private double[] asciiValues;

    public TextVector(String text) {
        this.asciiValues = this.calculateAsciiValues(text);
    }

    private double[] calculateAsciiValues(String text) {
        System.out.println(text);
        text = text.toLowerCase();
        double[] values = new double[26];
        int count;
        char sign;
        double total;

        if (!text.isEmpty()) {
            for (int i = 0; i < 26; ++i) {
                sign = (char) (i + (int)'a');
                count = 0;
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == sign) {
                        count++;
                    }
                }
                values[i] = count;
                total = text.length();
                values[i] = values[i] / total;
                System.out.print((char) (i + 'a') + ": " + values[i] + " ");
            }
        }
        System.out.println();

        return values;
    }

    public List<Double> getTextVector() {
        List<Double> list = new ArrayList<>();
        for (double value : asciiValues) {
            list.add(value);
        }
        return list;
    }
}

