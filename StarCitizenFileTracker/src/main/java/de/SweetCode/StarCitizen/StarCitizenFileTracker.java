package de.SweetCode.StarCitizen;

import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class StarCitizenFileTracker {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        FileTracker fileTracker = new FileTracker("E:\\Program Files\\Cloud Imperium Games", new ArrayList<String>() {{

            this.add("ScreenShots");
            this.add("loginData.json");
            this.add("LogBackups");
            this.add("client.crt");
            this.add("system.cfg");
            this.add("Patcher");

        }});
        FileHierarchy fileHierarchy = fileTracker.parse();

        System.out.println(new Gson().toJson(fileHierarchy));

    }

}
