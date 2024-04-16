import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

    }
    public ArrayList<InputObject> importData(String path){
        ArrayList<InputObject> result = new ArrayList<>();
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String[] attributesWithGroup = sc.next().split(",");
                ArrayList<String> attributes = new ArrayList<>();
                for(int i = 0; i < attributesWithGroup.length - 1;i++){
                    attributes.add(attributesWithGroup[i]);
                }
                InputObject io = new InputObject(attributes,attributesWithGroup[attributesWithGroup.length - 1]);
                result.add(io);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}