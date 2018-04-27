package dheisler.nikerun.data;

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
}
