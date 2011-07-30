/**
 * Copyright Warwick Hunter 2011. All rights reserved.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Experiments with Java 7 features.
 *
 * @author Warwick Hunter
 * @since  2011-07-30
 */
public class Java7Language {

    public static void main(String... args) {
        tryWithAutoCloseable("/etc/fstab");
        diamondOperatorAndSwitchOnStrings();
        Java7NewIO.fileCopy();
        Java7NewIO.configFileChangeDetector();
        Java7ForkJoin.forkJoin();
    }

    /**
     * Demonstrate the use of the try with auto closeable
     * @param file the file to copy
     */
    public static void tryWithAutoCloseable(String file) {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fileReader);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Use the diamond operator and switch on strings */
    public static void diamondOperatorAndSwitchOnStrings() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        for (String value : list) {
            switch (value) {
                case "foo":
                    System.out.printf("Hello %s%n", value);
                    break;
                case "bar":
                    System.out.printf("Goodbye %s%n", value);
                    break;
            }
        }
    }
}
