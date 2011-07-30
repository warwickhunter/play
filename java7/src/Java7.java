import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Experiments with Java 7 features.
 *
 * @author Warwick Hunter
 * @since  2011-07-30
 */
public class Java7 {

    private static final String THIS_DIR = System.getProperty("user.home") + "/dev/play/java7/src";

    public static void main(String... args) {
        Java7 j7 = new Java7();
        j7.tryWithAutoCloseable("/etc/fstab");
        j7.diamondOperatorAndSwitchOnStrings();
        j7.fileCopy();
        j7.configFileChangeDetector();
    }

    /**
     * Demonstrate the use of the try with auto closeable
     * @param file the file to copy
     */
    private void tryWithAutoCloseable(String file) {
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
    private void diamondOperatorAndSwitchOnStrings() {
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

    class FindImages extends SimpleFileVisitor<Path> {
        private int m_count = 0;
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.getFileName().toString().endsWith(".png")) {
                System.out.println(file.toString());
                m_count++;
            }
            return (m_count < 10) ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
        }
    }

    /** Use some of the new IO methods to copy a file */
    private void fileCopy() {
        try {
            Path src = Paths.get(THIS_DIR + "/config.properties");
            Path dst  = Paths.get(THIS_DIR + "/config.properties.new");
            Files.copy(src, dst, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            tryWithAutoCloseable(dst.toAbsolutePath().toString());

            Path usr = Paths.get(System.getProperty("user.home"));
            Files.walkFileTree(usr, new FindImages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Watch for changes in a configuration file and then reload the configuration */
    private void configFileChangeDetector() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path config = Paths.get(THIS_DIR);
            config.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            fileCopy(); // Trigger a file change
            for (boolean isMonitoring = true; isMonitoring; ) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path modifiedFile = (Path)event.context();
                    System.out.printf("Modified %s%n", modifiedFile.getFileName());
                    isMonitoring = false;
                }
                key.reset();
            }
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }

}
