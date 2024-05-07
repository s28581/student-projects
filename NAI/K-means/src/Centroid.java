import java.util.ArrayList;

public class Centroid {
    double[] coords;
    public Centroid(int numberOfCoords){
        this.coords = new double[numberOfCoords];
    }


    void calculateNewCentroidCoords(Centroid centroid, ArrayList<Point> data) {
        int counter = 0;
        double[] coordsSums = new double[data.getFirst().coords.length];
        for (Point p : data) {
            if (p.group == centroid) {
                for (int i = 0; i < p.coords.length; i++) {
                    coordsSums[i] += p.coords[i];
                }
                counter++;
            }
        }
        for (int i = 0; i < coordsSums.length; i++) {
            coords[i] = coordsSums[i] / (double)counter;
        }
    }
}
