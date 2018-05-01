package dheisler.nikerun;

import dheisler.nikerun.data.Runner;
import dheisler.nikerun.util.ParseRunnerFromJsonFile;
import jdk.nashorn.internal.scripts.JS;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class StartMeUp
{
    public static void main(String args[])
    {
        HashMap<String, Runner> runners = populateRunners();

        String runnerToGenerateInformation = args[0];
    }

    private static HashMap<String, Runner> populateRunners()
    {
        HashMap<String, Runner> runners = new HashMap<String, Runner>();
        ArrayList<String> listOfFiles = getAllFilesInDirectory();

        for (String file: listOfFiles)
        {
            Runner runner = ParseRunnerFromJsonFile.parseFile(file);

            if (runners.containsKey(runner.getUserId()))
            {
                Runner mappedRunner = runners.get(runner.getUserId());
               // mappedRunner.addActivity(runner.get);
            }
        }


        return runners;
    }

    /**
     * Note this is custom to my setup.  I have a /data directory at the same
     * level as /src and /test which contains the .json files.  I have not put
     * them on github.
     * @return ArrayList<String> of all regular files in ./data directory
     */
    private static ArrayList<String> getAllFilesInDirectory()
    {
        ArrayList<String> allRegularFiles = new ArrayList<String>();
        try
        {
            Files.list(Paths.get("./data"))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> allRegularFiles.add(filePath.toString()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return allRegularFiles;
    }
}
