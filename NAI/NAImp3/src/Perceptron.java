//import java.util.Collections;
//import java.util.List;
//
//public class Perceptron {
//    private List<Double> weights;
//    private double teta;
//    private double learningConstant;
//    private int epochs;
//
//    public Perceptron(List<Double> weights, double teta, double learningConstant, int epochs){
//        this.weights = weights;
//        this.learningConstant = learningConstant;
//        this.epochs = epochs;
//        this.teta = teta;
//    }
//
//    public void training(List<Sample> trainingData,List<Sample> testData ){
//        for(int epoch = 0; epoch < epochs; epoch++){
//            for(int i = 0; i < trainingData.size(); i++){
//                int WTXoutput = calculateWTXOutput(trainingData.get(i).getAsciiVectors().get(0).getTextVector());
//                int difference = WTXoutput - (trainingData.get(i).getLanguage().equals("positive") ? 1 : 0);
//                if(difference == 1 || difference == -1) {
//                    calculateNewWeights(difference, trainingData.get(i).getAsciiVectors().get(0).getTextVector());
//                    calculateTeta(difference);
//                }
//            }
//            Collections.shuffle(trainingData);
//            System.out.println("-> Epoch: " + (epoch + 1) + " accuracy: " + accuracy(testData) * 100);
//        }
//        System.out.println();
//    }
//
//    private void calculateTeta(int difference){
//        teta = teta + difference * learningConstant * -1;
//    }
//
//    private void calculateNewWeights(int difference, List<Double> elements){
//        for(int i = 0; i < weights.size(); i++){
//            double weight = weights.get(i);
//            weight = weight + learningConstant * difference * elements.get(i);
//            weights.set(i, weight);
//        }
//    }
//
//    public int calculateWTXOutput(List<Double> elements){
//        double sum = 0;
//        for(int i = 0; i < elements.size(); i++){
//            sum = sum + elements.get(i) * weights.get(i);
//        }
//        if(sum < teta)
//            return 0;
//        return 1;
//    }
//
//    private double accuracy(List<Sample> testData){
//        int correct = 0;
//        for(int i = 0; i < testData.size(); i++){
//            int WTXOutput = calculateWTXOutput(testData.get(i).getAsciiVectors().get(0).getTextVector());
//            int expected = testData.get(i).getLanguage().equals("positive") ? 1 : 0;
//            if(expected == WTXOutput)
//                correct++;
//        }
//        return (double) correct / testData.size();
//    }
//}
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Perceptron {
    private ArrayList<Double> weights;
    private double learningRate = 0.5;
    private double threshold = 0.2;
    private boolean isTrained = false;
    private String name;
    private int accuracy = 0;

    public Perceptron() {
        weights = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            weights.add(Math.random());
        }
    }

    public int guess(ArrayList<Double> inputs) {
        return calculateNet(inputs) > threshold ? 1 : 0;
    }

    private double calculateNet(ArrayList<Double> inputs) {
        double sum = 0;
        for (int i = 0; i < weights.size(); i++) {
            sum += inputs.get(i) * weights.get(i);
        }
        return sum;
    }

    public void train(ArrayList<Double> inputs, int target) {
        int guess = guess(inputs);
        double error = target - guess;
        inputs.add(-1.0);
        weights.add(threshold);
        for (int i = 0; i < weights.size(); i++) {
            double WeightsPrim = weights.get(i) + error * inputs.get(i) * learningRate;
            weights.set(i, WeightsPrim);
        }
        threshold = weights.get(weights.size() - 1);
        weights.remove(weights.size() - 1);
    }

    public ArrayList<Double> getWeightsVector() {
        return weights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public void trained() {
        isTrained = true;
    }

    public void notTrained() {
        isTrained = false;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public double getThreshold() {
        return threshold;
    }
}
