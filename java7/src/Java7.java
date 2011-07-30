import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Experiments with Java 7 features.
 *
 * @author Warwick Hunter
 * @since  2011-07-30
 */
public class Java7 {

    public static void main(String... args) {
        Java7 j7 = new Java7();
        j7.tryWithAutoCloseable();
    }

    private void tryWithAutoCloseable() {
        try (FileReader reader = new FileReader("Java7.java")) {
            String line;
            while (line = reader.readLine()) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
