package de.SweetCode.StarCitizen;

import difflib.Delta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHierarchy {


    private FileHierarchyNode root;
    private List<Delta<String>> deltas = new ArrayList<>();

    public FileHierarchy(File file, String hash) {

        this.root = new FileHierarchyNode(file, hash);

    }

    public FileHierarchyNode getRoot() {
        return this.root;
    }

    public List<Delta<String>> getDeltas() {
        return this.deltas;
    }

    public void setDeltas(List<Delta<String>> deltas) {
        this.deltas = deltas;
    }
}
