package de.SweetCode.StarCitizen;

import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class StarCitizenFileTracker {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        FileTracker fileTracker = new FileTracker("E:\\Program Files\\Cloud Imperium Games", new ArrayList<String>() {{

            this.add("\\Patcher\\");

            this.add("\\StarCitizen\\Public\\ScreenShots\\");
            this.add("\\StarCitizen\\Public\\LogBackups\\");
            this.add("\\StarCitizen\\Public\\\\client.crt");
            this.add("\\StarCitizen\\Public\\system.cfg");
            this.add("\\StarCitizen\\Public\\Controls\\");
            this.add("\\StarCitizen\\Public\\loginData.cfg");
            this.add("\\StarCitizen\\Public\\Game.log");

            this.add("\\StarCitizen\\Public\\USER\\Database\\");
            this.add("\\StarCitizen\\Public\\USER\\Profiles\\");
            this.add("\\StarCitizen\\Public\\USER\\SavedGames\\");
            this.add("\\StarCitizen\\Public\\USER\\game.cfg");



        }});
        FileHierarchy fileHierarchy = fileTracker.parse();

        System.out.println(new Gson().toJson(fileHierarchy));

    }

}
