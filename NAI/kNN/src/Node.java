import java.util.*;

public class Node {
    private final double[] coordinates;
    private String group;

    static double calculateDistance(Node a, Node b) {
        double distance = 0;
        for (int i = 0; i < a.coordinates.length; i++) {
            distance += Math.pow((a.coordinates[i] - b.coordinates[i]), 2);
        }
        return distance;
    }
    public Node(String line) {
        String[] parts = line.split(";");
        coordinates = new double[parts.length - 1];
        for (int i = 0; i < parts.length - 1; i++) {
            coordinates[i] = Double.parseDouble(parts[i]);
        }
        group = parts[parts.length - 1];
    }

    static String KNN(ArrayList<Node> nodes, Node newNode, int k) {
        Node[] nearestNodes = new Node[k];
        for(int i = 0; i < k; i++){
            nearestNodes[i] = nodes.get(i);
        }
        for (Node node : nodes) {
            double currentDistance = calculateDistance(node, newNode);
            int maxNearestNodeIndex = selectMaxDistanceIndex(nearestNodes, newNode);
            double maxNearestNodeDistance = calculateDistance(node, nearestNodes[maxNearestNodeIndex]);
            if (maxNearestNodeDistance > currentDistance) {
                nearestNodes[maxNearestNodeIndex] = node;
            }
        }
        Map<String, Integer> nearestNodesGroups = new HashMap<>();
        for (Node n : nearestNodes) {
            if (nearestNodesGroups.containsKey(n.group)) {
                nearestNodesGroups.replace(n.group, nearestNodesGroups.get(n.group) + 1);
            } else{
                nearestNodesGroups.put(n.group,1);
            }
        }
        int maxCount = 0;
        for(String s : nearestNodesGroups.keySet()){
            if(nearestNodesGroups.get(s) > maxCount){
                newNode.group = s;
                maxCount = nearestNodesGroups.get(s);
            }
        }
        return newNode.group;
    }

    static int selectMaxDistanceIndex(Node[] nodes, Node newNode) {
        int maxIndex = 0;
        double maxDistance = calculateDistance(nodes[0], newNode);
        for (int i = 1; i < nodes.length; i++) {
            double tmpMaxDistance = calculateDistance(nodes[i], newNode);
            if (tmpMaxDistance > maxDistance) {
                maxDistance = tmpMaxDistance;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public double[] getCoordinates(){
        return coordinates;
    }
    public String getGroup() {
        return group;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Double d : coordinates) {
            result.append(d).append(" ");
        }
        result.append(group);
        return result.toString();
    }
}
