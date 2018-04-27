package dheisler.nikerun.data;

public class Activity
{
    public static final int RUN = 0;
    public static final int TRAINING = 1;
    public static final int OTHER = 2;

    private String activityId;
    private int type;
    private String startTime;
    private String endTime;

    public Activity(String activityId, int type)
    {
        this.activityId = activityId;
        this.type = type;
    }

    public int getActivityType()
    {
        return type;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
}
