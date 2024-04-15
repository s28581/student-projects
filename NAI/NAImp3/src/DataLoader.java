import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {

    public static List<Sample> loadData(String directory) {
        List<Sample> dataObjects = new ArrayList<>();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(directory))) {
            dirStream.forEach(path -> {
                if (Files.isDirectory(path)) {
                    String language = path.getFileName().toString();
                    List<TextVector> vectors = loadVectorsFromDirectory(path);
                    dataObjects.add(new Sample(language, (ArrayList<TextVector>) vectors));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataObjects;
    }

    private static List<TextVector> loadVectorsFromDirectory(Path directory) {
        try (Stream<Path> fileStream = Files.walk(directory)) {
            return fileStream
                    .filter(Files::isRegularFile)
                    .map(DataLoader::readAndProcessFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static TextVector readAndProcessFile(Path file) {
        try {
            String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
            return new TextVector(content);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
