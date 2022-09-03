package com.crio.jukebox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.jukebox.appConfig.ApplicationConfig;
import com.crio.jukebox.commands.CommandInvoker;


public class App {
    // To run the application  ./gradlew run --args="INPUT_FILE=jukebox-input.txt"
    public static void main(String[] args) {
        List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));

        //System.out.println(commandLineArgs);
        
        String expectedSequence = "INPUT_FILE";
        String actualSequence = commandLineArgs.stream()
                .map(a -> a.split("=")[0])
                .collect(Collectors.joining("$"));
        if(expectedSequence.equals(actualSequence)){
            run(commandLineArgs);
        }


        // ApplicationConfig applicationConfig = new ApplicationConfig();
        // CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        
        // List<String>tokens = new ArrayList<>();
        // tokens.add("LOAD-DATA");
        // tokens.add("songss.csv");

        // List<String> tokens1 = new ArrayList<>();
        // tokens1.add("CREATE-USER");
        // tokens1.add("any-name");

        // List<String> tokens2 = new ArrayList<>();
        // tokens2.add("CREATE-PLAYLIST");
        // tokens2.add("1");
        // tokens2.add("MY_PLAYLIST_1");
        // tokens2.add("1");
        // tokens2.add("4");
        // tokens2.add("5");
        // tokens2.add("6");

        // List<String> tokens22 = new ArrayList<>();
        // tokens22.add("CREATE-PLAYLIST");
        // tokens22.add("1");
        // tokens22.add("MY_PLAYLIST_2");
        // tokens22.add("1");
        

        // List<String> tokens222 = new ArrayList<>();
        // tokens222.add("DELETE-PLAYLIST");
        // tokens222.add("1");
        // tokens222.add("2");

        // List<String> tokensa = new ArrayList<>();
        // tokensa.add("PLAY-PLAYLIST");
        // tokensa.add("1");
        // tokensa.add("1");

        // List<String> tokensaa = new ArrayList<>();
        // tokensaa.add("MODIFY-PLAYLIST");
        // tokensaa.add("ADD-SONG");
        // tokensaa.add("1");
        // tokensaa.add("1");
        // tokensaa.add("7");

        // List<String> tokensaaa = new ArrayList<>();
        // tokensaaa.add("MODIFY-PLAYLIST");
        // tokensaaa.add("DELETE-SONG");
        // tokensaaa.add("1");
        // tokensaaa.add("1");
        // tokensaaa.add("7");

        // List<String> tokensq = new ArrayList<>();
        // tokensq.add("PLAY-SONG");
        // tokensq.add("1");
        // tokensq.add("5");

        // List<String> tokensqq = new ArrayList<>();
        // tokensqq.add("PLAY-SONG");
        // tokensqq.add("1");
        // tokensqq.add("NEXT");

        // List<String> tokensqqq = new ArrayList<>();
        // tokensqqq.add("PLAY-SONG");
        // tokensqqq.add("1");
        // tokensqqq.add("NEXT");

        // List<String> tokensk = new ArrayList<>();
        // tokensk.add("PLAY-SONG");
        // tokensk.add("1");
        // tokensk.add("BACK");

        // List<String> tokenskk = new ArrayList<>();
        // tokenskk.add("PLAY-SONG");
        // tokenskk.add("1");
        // tokenskk.add("BACK");

        // List<String> tokensqh = new ArrayList<>();
        // tokensqh.add("PLAY-SONG");
        // tokensqh.add("1");
        // tokensqh.add("55");

        // commandInvoker.executeCommand(tokens.get(0), tokens);
        // commandInvoker.executeCommand(tokens1.get(0), tokens1);
        // commandInvoker.executeCommand(tokens2.get(0), tokens2);
        // commandInvoker.executeCommand(tokens22.get(0), tokens22);
        // commandInvoker.executeCommand(tokens222.get(0), tokens222);
        // commandInvoker.executeCommand(tokensa.get(0), tokensa);
        // commandInvoker.executeCommand(tokensaa.get(0), tokensaa);
        // commandInvoker.executeCommand(tokensaaa.get(0), tokensaaa);
        // commandInvoker.executeCommand(tokensq.get(0), tokensq);
        // commandInvoker.executeCommand(tokensqq.get(0), tokensqq);
        // commandInvoker.executeCommand(tokensqqq.get(0), tokensqqq);
        // commandInvoker.executeCommand(tokensk.get(0), tokensk);
        // commandInvoker.executeCommand(tokenskk.get(0), tokenskk);
        // commandInvoker.executeCommand(tokensqh.get(0), tokensqh);
    }

    public static void run(List<String> commandLineArgs) {
        // Complete the final logic to run the complete program.
        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader reader;
        String inputFile = commandLineArgs.get(0).split("=")[1];
        commandLineArgs.remove(0);

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(0), tokens);
                // read next line
                line = reader.readLine();
                //System.out.println(tokens.get(0));
            }
            reader.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
