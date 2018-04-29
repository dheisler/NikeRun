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

    public void sortActivities()
    {
        // sort based on start time of activity
    }
}
