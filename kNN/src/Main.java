import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        System.out.println("Insert k value:");
        int k = Integer.parseInt(userInput.nextLine());
        System.out.println("Insert train set path");
        String trainSetPath = userInput.nextLine();
        System.out.println("Insert test set path");
        String testSetPath = userInput.nextLine();
        /*
        String trainSetPath = args[0];
        String testSetPath = args[1];
        int k = Integer.parseInt(args[2]);
        */
        ArrayList<Node> trainSet = createNodeArrayList(trainSetPath);
        ArrayList<Node> testSet = createNodeArrayList(testSetPath);
        ArrayList<String> answerGroups = new ArrayList<>();
        for(int i = 0; i < testSet.size(); i++){
            answerGroups.add(testSet.get(i).getGroup());
        }
        ArrayList<String> signedGroups = new ArrayList<>();
        for (int i = 0; i < testSet.size(); i++) {
            Node testedNode = testSet.get(i);
            Node.KNN(trainSet, testedNode, k);
            signedGroups.add(testedNode.getGroup());
            System.out.println(i + ". " + testedNode + " expectation: " + answerGroups.get(i));
        }
        double correctAnswers = 0;
        for (int i = 0; i < signedGroups.size(); i++) {
            if (signedGroups.get(i).equals(answerGroups.get(i))) {
                correctAnswers += 1;
            }
        }
        System.out.println("Accuracy: " + Math.round((correctAnswers / (double) answerGroups.size() * 100) * 100.0) / 100.0 + "%");

        while (true) {
            System.out.println("Insert new vector with " + testSet.get(0).getCoordinates().length + " values separated by \";\", q for quit");
            String line = userInput.nextLine();
            if (line.equals("q")) {
                return;
            }
            Node node = new Node(line + ";unknown group");
            System.out.println("Group for this vector: " + Node.KNN(trainSet,node,k));
        }
    }

    static ArrayList<Node> createNodeArrayList(String path){
        ArrayList<Node> result= new ArrayList<>();
        File trainSetFile = new File(path);
        Scanner sc1;
        try {
            sc1 = new Scanner(trainSetFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc1.hasNext()) {
            result.add(new Node(sc1.nextLine()));
        }
        return result;
    }
}