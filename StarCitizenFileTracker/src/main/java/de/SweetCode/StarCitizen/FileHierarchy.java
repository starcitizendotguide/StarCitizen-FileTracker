package de.SweetCode.StarCitizen;

import java.io.File;

public class FileHierarchy {


    private FileHierarchyNode root;

    public FileHierarchy(File file, String hash) {

        this.root = new FileHierarchyNode(file, hash);

    }

    public FileHierarchyNode getRoot() {
        return this.root;
    }

}
