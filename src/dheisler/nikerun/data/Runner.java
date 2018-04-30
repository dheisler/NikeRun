/**
 * @author Debbie Heisler
 *
 * This class contains the runing data for one runner.
 * It also contains some of the methods needed to answer the questions in
 * the coding challenge.
 */
package dheisler.nikerun.data;

import dheisler.nikerun.data.Activity;

import java.util.ArrayList;
import java.util.Collections;

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

    public int numberOfTimesRan1km3DaysInARow()
    {
        int numberOfTimes = 0;
        int streak = 0;
        Activity previousDay = null;

        for (Activity activity : activities)
        {
            if (activity.ranMoreThankOneK())
            {
                if (previousDay == null)
                {
                    streak++;
                }
                else
                {
                    if (activity.wasThisActivityTheDayBefore(previousDay))
                    {
                        streak++;
                    }
                    else
                    {
                        streak = 1;
                    }
                }

                if (streak == 3)
                {
                    numberOfTimes++;
                    streak = 0;
                    previousDay = null;
                }
                else
                {
                    previousDay = activity;
                }
            }
            else
            {
                streak = 0;
                previousDay = null;
            }
        }

        return numberOfTimes;
    }


}
