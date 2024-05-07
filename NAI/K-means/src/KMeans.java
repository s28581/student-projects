import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class KMeans {
    public static void main(String[] args) throws FileNotFoundException {
        int k = 2;
        double e = -1;
        double oldE = -2;
        Centroid[] centroids = new Centroid[k];

        ArrayList<Point> data = readFile("src/test.csv");
        for (int i = 0; i < k; i++) {
            centroids[i] = new Centroid(data.getFirst().coords.length);
        }for (int i = 0; i < centroids.length; i++) {
            centroids[i].calculateNewCentroidCoords(data);
        }
        for(Point p : data){
            p.giveRandomGroup(centroids);
        }
        int i = 0;
        while (Math.abs(oldE - e) > 0.00001) {
            for(Point p : data){
                p.calculateNearestCentroid(centroids);
            }
            for(Centroid c : centroids){
                c.calculateNewCentroidCoords(data);
            }
            oldE = e;
            e = calculateE(data);
            System.out.println(i++);
        }


    }

    static double calculateE(ArrayList<Point> data){
        double sum = 0;
        for(Point p : data){
            sum += Math.pow(p.distanceToNearestCentroid, 2);
        }
        return sum;
    }

    static ArrayList<Point> readFile(String path) throws FileNotFoundException {
        ArrayList<Point> data = new ArrayList<>();
        File test = new File(path);
        Scanner sc = new Scanner(test);
        while (sc.hasNext()) {
            String[] parts = sc.nextLine().split(",");
            double[] coords = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                coords[i] = Double.parseDouble(parts[i]);
            }
            data.add(new Point(coords));
        }
        return data;
    }


}
