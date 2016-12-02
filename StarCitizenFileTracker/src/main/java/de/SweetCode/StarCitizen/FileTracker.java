package de.SweetCode.StarCitizen;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Stream;

public class FileTracker {

    private final static int BUFFER_SIZE = (1024 * 8);

    private final File rootDir;
    private final MessageDigest messageDigest;

    private final List<String> exclude;

    private FileHierarchy fileHierarchy;

    /**
     * @param rootDir The root directory.
     */
    public FileTracker(String rootDir, String hash, List<String> exclude) throws NoSuchAlgorithmException {

        this.rootDir = new File(rootDir);
        this.messageDigest =  MessageDigest.getInstance(hash);

        this.exclude = exclude;
        System.out.println(rootDir);
        if(!(this.rootDir.exists()) || !(this.rootDir.isDirectory())) {
            throw new IllegalArgumentException("The given rootDir path is not a directory or does not exist.");
        }

    }

    public FileHierarchy parse() {

        this.handle(this.rootDir, null);
        return this.fileHierarchy;

    }

    private void handle(File directory, FileHierarchyNode currentNode) {

        // if the current file hierarchy object doesn't exist
        if(this.fileHierarchy == null) {

            // @TODO also hashes for folders... maybe later, just not yet :)
            this.fileHierarchy = new FileHierarchy(directory, this.hash(directory));
            currentNode = this.fileHierarchy.getRoot();

        }


        FileHierarchyNode finalCurrentNode = currentNode;

        Stream.of(directory.listFiles())
            .forEach(e -> {

                if(this.exclude(e)) {
                    return;
                }

                // Lets generate hashes...
                String hash = this.hash(e);
                System.out.println(pathAsString(e) + " (" + hash + ")");

                FileHierarchyNode node = new FileHierarchyNode(e, hash);
                finalCurrentNode.add(node);

                // next level
                if(e.isDirectory()) {
                    this.handle(e, node);
                }

            });
    }

    /**
     * Creates the hash of a file and directory.
     * @param file The file or directory to be hashed.
     * @return Returns a SHA-256 hash string.
     */
    private String hash(File file)  {

        try {

            this.messageDigest.reset();

            if(file.isFile()) {

                DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file), this.messageDigest);

                byte[] buffer = new byte[BUFFER_SIZE];
                while (digestInputStream.read(buffer, 0, BUFFER_SIZE) != -1) ;


            } else if(file.isDirectory()) {

                StringBuilder builder = new StringBuilder();
                this.hashDirectory(file, builder);
                this.messageDigest.update(builder.toString().getBytes(Charset.forName("UTF-8")));


            }

            return HexBin.encode(this.messageDigest.digest());

        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }

    }

    /**
     * Checks if a file should be excluded.
     * @param file
     * @return
     */
    private boolean exclude(File file) {

        String path = pathAsString(file);
        boolean value = this.exclude.stream().filter(path::startsWith).findFirst().isPresent();

        if(value) {
            System.out.println("Skipped excluded file: " + path);
        }

        return value;
    }

    /**
     * Returns the path of the file relative to the start directory.
     * @param file
     * @return
     */
    private String pathAsString(File file) {

        if(file == null) {
            return "null";
        }

        return file.getAbsolutePath().replace(this.rootDir.getAbsolutePath(), "");

    }

    /**
     * Builds a string of a directory to create unique hash of it.
     * @param directory The directory to start in.
     * @param builder The StringBuilder containing the string that is going to be hashed.
     */
    private void hashDirectory(File directory, StringBuilder builder) {

        Stream.of(directory.listFiles()).forEachOrdered(e -> {

            builder.append(e.getName()).append(e.length());

            if(e.isDirectory()) {
                this.hashDirectory(e, builder);
            }

        });

    }

}
