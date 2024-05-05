public class Node {
    int x;
    int y;
    double temp;
    boolean isExtreme;

    public Node(int x, int y, boolean isExtreme) {
        this.isExtreme = isExtreme;
        this.y = y;
        this.x = x;
    }

    public Node(int x, int y, boolean isExtreme, double temp) {
        this.x = x;
        this.y = y;
        this.temp = temp;
        this.isExtreme = isExtreme;
    }
}
