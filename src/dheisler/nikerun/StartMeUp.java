/**
 * author Debbie Heisler
 *
 */
package dheisler.nikerun;

import dheisler.nikerun.data.Runner;
import dheisler.nikerun.util.ParseRunnerFromJsonFile;

import org.json.simple.JSONObject;

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

        Runner runner = runners.get(runnerToGenerateInformation);

        String output = getCalculationsAsJsonString(runner);

        System.out.println(output);
    }

    private static HashMap<String, Runner> populateRunners()
    {
        HashMap<String, Runner> runners = new HashMap<String, Runner>();
        ArrayList<String> listOfFiles = getAllFilesInDirectory();

        for (String file: listOfFiles)
        {
            Runner runner = ParseRunnerFromJsonFile.parseFile(file);
            String userId = runner.getUserId();

            if (runners.containsKey(userId))
            {
                Runner mappedRunner = runners.get(userId);
                mappedRunner.addActivity(runner.getActivities()[0]);
            }
            else
            {
                runners.put(userId, runner);
            }
        }

        for (Runner anotherRunner: runners.values())
        {
            anotherRunner.sortActivities();
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

    public static String getCalculationsAsJsonString(Runner runner)
    {
        boolean moreThan1K = runner.ranMoreThan1kmInSingleRun();
        int numberOf3day1K = runner.getNumberOfTimesRanMoreThan1km3DaysInARow();
        int numberOf10KWeeks = runner.getNumberOfTimesRan10KInCalendarWeek();
        long numberOfSeconds = runner.getNumberOfSecondsSpentOnActivities();

        return createJSONObjectString(runner.getUserId(), moreThan1K, numberOf3day1K, numberOf10KWeeks, numberOfSeconds);
    }

    private static String createJSONObjectString(String userId, boolean moreThan1K,
                                                 int numberOf3day1K, int numberOf10KWeeks, long numberOfSeconds)
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", userId);
        jsonObject.put("ran_more_than_1k", moreThan1K);
        jsonObject.put("number_of_3_day_more_than_1k", numberOf3day1K);
        jsonObject.put("number_of_10K_weeks", numberOf10KWeeks);
        jsonObject.put("number_of_total_seconds_in_activities", numberOfSeconds);

        return jsonObject.toString();
    }
}
