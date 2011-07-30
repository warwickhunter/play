/**
 * Copyright Warwick Hunter 2011. All rights reserved.
 */
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Experiments with Java 7 features.
 *
 * @author Warwick Hunter
 * @since  2011-07-30
 */
public class Java7NewIO {

    private static final String THIS_DIR = System.getProperty("user.home") + "/dev/play/java7/src";

    private static class FindImages extends SimpleFileVisitor<Path> {
        
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
    public static void fileCopy() {
        try {
            Path src = Paths.get(THIS_DIR + "/config.properties");
            Path dst  = Paths.get(THIS_DIR + "/config.properties.new");
            Files.copy(src, dst, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            Java7Language.tryWithAutoCloseable(dst.toAbsolutePath().toString());
            Path usr = Paths.get(System.getProperty("user.home"));
            Files.walkFileTree(usr, new FindImages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Watch for changes in a configuration file and then reload the configuration */
    public static void configFileChangeDetector() {
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
