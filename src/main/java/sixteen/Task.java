package sixteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Created by parallellines on 29.09.2017.
 */
public abstract class Task<T> {

    private static final String INPUT_PATH = "src/main/resources/input/";
    private static String INPUT_FILE_NAME;

    Task(String fileName) {
        INPUT_FILE_NAME = fileName;
    }

    LinkedList<String> readFile() {
        LinkedList<String> fileContent = new LinkedList<>();
        try {
            Files.lines(Paths.get(INPUT_PATH + INPUT_FILE_NAME)).forEach(fileContent::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public abstract T solve();

}
