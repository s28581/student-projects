public class Point {
    Centroid group;
    double[] coords;
    double distanceToNearestCentroid;

    public Point(double[] coords) {
        this.coords = coords;
    }
    void giveRandomGroup(Centroid[] centroids){
        group = centroids[(int) (Math.random() * 10) % centroids.length];
    }

    void calculateNearestCentroid(Centroid[] centroids) {
        double nearestCentroidDistance = 0;
        for (int i = 0; i < coords.length; i++) {
            nearestCentroidDistance += Math.pow(coords[i] - centroids[0].coords[i], 2);
        }
        for (int i = 1; i < centroids.length; i++) {
            double distance = 0;
            if (distance < nearestCentroidDistance) {
                group = centroids[i];
                distanceToNearestCentroid = distance;
                nearestCentroidDistance = distance;
            }
        }
    }

}
