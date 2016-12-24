package de.SweetCode.StarCitizen;

import com.google.gson.Gson;
import difflib.DiffUtils;
import difflib.Patch;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class StarCitizenFileTracker {

    private final static Options options = new Options();

    static {

        Option path = new Option("p", "path", true, "The path to the root folder of Star Citizen.");
        path.setRequired(true);

        Option hash = new Option("h", "hash", true, "The hash algorithm used to hash directories and files.");
        hash.setRequired(true);

        Option exclude = new Option("e", "exclude", true, "All files and or folders excluded from the file tracker; separated by comma (,).");
        Option output = new Option("o", "output", true, "The path to file to put the JSON result.");
        Option compare = new Option("c", "compareTo", true, "The path to the file to compare to.");

        StarCitizenFileTracker.options.addOption(path);
        StarCitizenFileTracker.options.addOption(exclude);
        StarCitizenFileTracker.options.addOption(output);
        StarCitizenFileTracker.options.addOption(hash);
        StarCitizenFileTracker.options.addOption(compare);

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine commandLine;

        try {
            commandLine = parser.parse(StarCitizenFileTracker.options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("StarCitizen File Tracker", StarCitizenFileTracker.options);

            System.exit(1);
            return;
        }

        // options parsed
        String path = commandLine.getOptionValue("path");
        String hash = commandLine.getOptionValue("hash");
        String[] exclude = commandLine.getOptionValue("exclude").split(",");
        Optional<String> output = Optional.ofNullable(commandLine.getOptionValue("output"));
        Optional<String> compare = Optional.ofNullable(commandLine.getOptionValue("compareTo"));

        FileTracker fileTracker = new FileTracker(path, hash, Arrays.asList(exclude));
        FileHierarchy fileHierarchy = fileTracker.parse();

        //output
        String outputData = new Gson().toJson(fileHierarchy);

        //compare
        if(compare.isPresent()) {
            File compareFile = new File(compare.get());

            if(compareFile.isFile()) {
                Patch<String> patch = DiffUtils.diff(Collections.singletonList(outputData), Files.readAllLines(Paths.get(compareFile.getAbsolutePath())));
                fileHierarchy.setDeltas(patch.getDeltas());

                System.out.println("Saved deltas from comparing.");
            } else {
                System.out.println("The compareTo file is not a file or does not exist.");
            }
        }

        if(output.isPresent()) {
            File outputFile = new File(output.get());

            if (!(outputFile.exists())) {
                outputFile.createNewFile();
            }

            if (outputFile.isFile()) {
                Files.write(Paths.get(outputFile.getAbsolutePath()), outputData.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
            } else {
                System.out.println("The output path is not a file.");
                System.out.println(outputData);
            }
        } else {
            System.out.println(outputData);
        }

    }

}
