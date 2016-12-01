package de.SweetCode.StarCitizen;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Stream;

public class FileTracker {

    private final static int BUFFER_SIZE = (1024 * 8);

    private final File rootDir;
    private final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

    private final List<String> exclude;

    private FileHierarchy fileHierarchy;

    /**
     * @param rootDir The root directory.
     */
    public FileTracker(String rootDir, List<String> exclude) throws NoSuchAlgorithmException {

        this.rootDir = new File(rootDir);

        this.exclude = exclude;

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
            this.fileHierarchy = new FileHierarchy(directory, null);
            currentNode = this.fileHierarchy.getRoot();

        }

        FileHierarchyNode finalCurrentNode = currentNode;

        Stream.of(directory.listFiles())
            .forEach(e -> {

                String displayPath = e.getAbsolutePath().replace(this.rootDir.getAbsolutePath(), "");

                if(this.exclude.stream().filter(displayPath::startsWith).findFirst().isPresent()) {
                    System.out.println("Skipped excluded file: " + displayPath);
                    return;
                }

                // Lets generate hashes...
                String hash = null;

                if(e.isFile()) {
                    try {
                        hash = this.hash(e);
                        System.out.println(displayPath+ " (" + hash + ")");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                FileHierarchyNode node = new FileHierarchyNode(e, hash); //@TODO hash for files...
                finalCurrentNode.add(node);

                // next level
                if(e.isDirectory()) {
                    this.handle(e, node);
                }

            });
    }

    private final String hash(File file) throws IOException {

        this.messageDigest.reset();
        DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file), this.messageDigest);

        byte[] buffer = new byte[BUFFER_SIZE];
        while (digestInputStream.read(buffer, 0, BUFFER_SIZE) != -1);

        return HexBin.encode(this.messageDigest.digest());

    }

}
