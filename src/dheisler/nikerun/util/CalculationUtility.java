/**
 * author Debbie Heisler
 *
 * I realize these two methods kind of go together and kind of don't go together.
 * They are in this class together to clean up the StartMeUp class.  In a larger
 * project I would expect more calculations and more data output options, so these
 * would be in different classes with different purposes.
 *
 * The JSON keys are
 * user_id
 * ran_more_than_1k         person ran more than 1k in an activity
 * number_of_3_day_more_than_1k number of times person ran 3 days in a row
 * number_of_10k_weeks      number of time person ran more than 10k in a week
 * total_seconds_in_activities total number of seconds person spent on any activity.
 */
package dheisler.nikerun.util;

import dheisler.nikerun.data.Runner;
import org.json.simple.JSONObject;

public class CalculationUtility
{
    /**
     * This class takes a runner and determines if they ran more than 1k in a run,
     * the number of times they ran more than 1k three days in a row (days are
     * counted once in once streak), the number of times they ran more than
     * 10k in a week, and the total number of seconds they spent on activities.
     *
     * @param runner
     * @return JSON string containing the user id and the calculations
     */
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
        jsonObject.put("number_of_10k_weeks", numberOf10KWeeks);
        jsonObject.put("total_seconds_in_activities", numberOfSeconds);

        return jsonObject.toString();
    }
}
