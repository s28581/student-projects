import Jama.*;


public class Main {
    public static void main(String[] args) {
        int size = 5;
        Node[][] plate = new Node[size + 2][size + 2];
        for (int i = 0; i < plate.length; i++) {
            plate[0][i] = new Node(i, 0, true, 200);
            plate[i][0] = new Node(0, i, true, 100);
            plate[plate.length - 1][i] = new Node(i, plate.length - 1, true, 150);
            plate[i][plate.length - 1] = new Node(plate.length - 1, i, true, 50);
        }
        printPlate(plate);
        double[][] resultOfMatrix = new double[size * size][size];
        for (int i = 1; i < plate.length - 1; i++) {
            for (int j = 1; j < plate[0].length - 1; j++) {
                resultOfMatrix[i * plate.length + j] = calculateNode(j, i, size);
            }
        }
        for (int i = 0; i < resultOfMatrix.length; i++) {
            for (int j = 0; j < resultOfMatrix[0].length; j++) {
                System.out.print(resultOfMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    static double[] calculateNode(int x, int y, int size) {
        double[] result = new double[size * size];
        result[((x) * size - 1) + (y - 1)] = 1;
        result[((x - 1) * size + 1) + (y - 1)] = 1;
        result[((x - 1) * size) + (y - 1) + 1] = 1;
        result[((x) * size) + (y - 1) - 1] = 1;
        result[((x - 1) * size) + (y - 1)] = -4;
        for (int k = y - 1; k < y + 1; k++) {
            for (int l = x - 1; l < x + 1; l++) {
                if (k - y == 0 && l - x == 0) {
                    result[k + l] = -4;
                } else {
                    result[k + l] = 1;
                }
            }
        }
        return result;
    }

    static void printPlate(Node[][] plate) {
        for (Node[] nodes : plate) {
            for (Node node : nodes) {
                if (node == null)
                    System.out.print("null ");
                else
                    System.out.print(node.temp + " ");
            }
            System.out.println();
        }
    }
}