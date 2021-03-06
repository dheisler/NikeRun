/**
 * author Debbie Heisler
 *
 * This class should be broken up so that it does not open the file itself.
 * It should only be dealing with a string containing JSON.
 *
 * This class should connect to a datasource interface, so that it can parse
 * from anywhere.  It is set up like this due to time constraints.
 */
package dheisler.nikerun.util;

import dheisler.nikerun.data.Activity;
import dheisler.nikerun.data.Runner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ParseRunnerFromJsonFile
{

    /**
     *
     * @param fileName JSON file to open
     * @return file parsed into Runner object
     */
    public static Runner parseFile(String fileName)
    {
        JSONObject jsonObject = getJSONObjectFromFile(fileName);

        String runnerId = (String) jsonObject.get("user_id");
        Runner runner = new Runner(runnerId);

        String activityID = (String) jsonObject.get("activity_id");
        String type = (String) jsonObject.get("type");
        String start = (String) jsonObject.get("start");
        String end = (String) jsonObject.get("end");
        Object distance =  jsonObject.get("distance");
        int typeAsInt = getTypeAsInt(type);

        Activity activity = new Activity(activityID, typeAsInt);
        activity.setStartTime(start);
        activity.setEndTime(end);

        if (distance != null)
        {
            activity.setDistance(Double.valueOf(distance.toString()));
        }

        runner.addActivity(activity);

        return runner;
    }

    private static int getTypeAsInt(String type)
    {
        int theType = Activity.RUN;
        type = type.trim().toLowerCase();

        if (type.equals("training"))
        {
            theType = Activity.TRAINING;
        }
        else if (type.equals("other"))
        {
            theType = Activity.OTHER;
        }
        else if (type.equals("cycle"))
        {
            theType = Activity.CYCLE;
        }

        return theType;
    }

    private static JSONObject getJSONObjectFromFile(String fileName)
    {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try
        {
            jsonObject = (JSONObject) parser.parse(new FileReader(fileName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
