/**
 * @author Debbie Heisler
 *
 * This class contains the runing data for one runner.
 * It also contains some of the methods needed to answer the questions in
 * the coding challenge.
 */
package dheisler.nikerun.data;

import java.time.LocalDate;
import java.util.ArrayList;

public class Runner
{
    private String userId;
    private ArrayList<Activity> activities;

    public Runner(String userId)
    {
        this.userId = userId;
        activities = new ArrayList<Activity>();
    }

    public String getUserId()
    {
        return userId;
    }

    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public int getNumberofActivities()
    {
        return activities.size();
    }

    public Activity[] getArrayOfActivities()
    {
        Activity[] array = new Activity[activities.size()];
        return activities.toArray(array);
    }

    public void sortActivities()
    {
        // sort based on start time of activity
        activities.sort(
                (Activity act1, Activity act2) -> act1.compareTo(act2)
        );
    }

    public boolean ranMoreThan1kmInSingleRun()
    {
        boolean ranMoreThan1km = false;

        for (Activity activity : activities)
        {
            if (activity.ranMoreThankOneK())
            {
                ranMoreThan1km = true;
                break;
            }
        }

        return ranMoreThan1km;
    }


    public int getNumberOfSecondsSpentOnActivities()
    {
        int secondsSpent = 0;

        for (Activity activity : activities)
        {
            secondsSpent += activity.getLengthOfActivityInSeconds();
        }
        return secondsSpent;
    }

    public int getNumberOfTimesRanMoreThan1km3DaysInARow()
    {
        int numberOfTimes = 0;
        int streak = 0;
        Activity previousDay = null;

        for (Activity activity : activities)
        {
            if (activity.ranMoreThankOneK())
            {
                if ((previousDay == null) || (activity.wasThisActivityTheDayBefore(previousDay)))
                {
                    streak++;
                }
                else
                {
                    // starting a new streak with today as first day of streak
                    streak = 1;
                }

                if (streak == 3)
                {
                    numberOfTimes++;
                    resetStreak(streak, previousDay);
                }
                else
                {
                    // new streak with today as first day or in middle of streak
                    previousDay = activity;
                }
            }
            else
            {
                resetStreak(streak, previousDay);
            }
        }

        return numberOfTimes;
    }

    public int getNumberOfTimesRan10KInCalendarWeek()
    {
        int numberOfTimesMoreThan10K = 0;
        double distanceTotal = 0;
        LocalDate monday = LocalDate.now();

        for (Activity activity: activities)
        {
            if (monday.equals(activity.getBeginningOfWeekDate()))
            {
                distanceTotal += activity.getDistance();
            }
            else
            {
                // check old distance total before resetting it.
                if (distanceTotal > 10)
                {
                    numberOfTimesMoreThan10K++;
                }

                monday = activity.getBeginningOfWeekDate();
                distanceTotal = activity.getDistance();
            }
        }

        // check once more when out of activities
        if (distanceTotal > 10)
        {
            numberOfTimesMoreThan10K++;
        }


        return numberOfTimesMoreThan10K;
    }

    /**
     * Resets the streak for running more than 1k each of three days in a row.
     * @param streak
     * @param activity
     */
    private void resetStreak(int streak, Activity activity)
    {
        streak = 0;
        activity = null;
    }

    public Activity[] getActivities()
    {
        return activities.toArray(new Activity[activities.size()]);
    }
}
