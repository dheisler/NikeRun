/**
 * author Debbie Heisler
 *
 * This class gets all the files containing the runner json in the data
 * directory.
 *
 * If this were a larger project and/or I had more time.  I would split this
 * methods out into better classes (and use interfaces) that would allow one
 * to read from more options than just a file in a directory.
 */
package dheisler.nikerun.util;

import dheisler.nikerun.data.Runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadDataUtility
{
    public static HashMap<String, Runner> populateRunners()
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
}
