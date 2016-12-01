package de.SweetCode.StarCitizen;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileHierarchyNode {

    private final String name;
    private final String hash;

    private final boolean isDirectory;

    private final List<FileHierarchyNode> dirs = new LinkedList<>();
    private final List<FileHierarchyNode> files = new LinkedList<>();

    /**
     * @param file The file or directory.
     * @param hash The hash of the file or directory.
     */
    public FileHierarchyNode(File file, String hash) {

        this.isDirectory = file.isDirectory();
        this.name = file.getName();
        this.hash = hash;

    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public boolean isFile() {
        return !(this.isDirectory());
    }

    public void add(FileHierarchyNode fileHierarchyNode) {

        if(fileHierarchyNode.isDirectory() && this.isDirectory()) {
            this.dirs.add(fileHierarchyNode);
        } else if(fileHierarchyNode.isFile() && this.isDirectory()) {
            this.files.add(fileHierarchyNode);
        } else {
            throw new IllegalArgumentException("This node is not a directory and therefore cannot story any additional files nor folders.");
        }

    }

}
