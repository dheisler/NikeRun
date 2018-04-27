package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest
{
    private static final String activityId = "aShortActivityId";
    private static final String startTime = "2017-04-30T18:01:50Z";
    private static final String endTime = "2017-04-30T18:50:31.166Z";

    @BeforeEach
    public void setup()
    {

    }

    @Test
    public void addActivityTypeOther()
    {
        Activity activity = getActivity(activityId, Activity.OTHER);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        assertEquals(Activity.OTHER, activity.getActivityType());
        assertEquals(startTime, activity.getStartTime());
        assertEquals(endTime, activity.getEndTime());
    }


    @Test
    public void addActivityTypeTraining()
    {
        Activity activity = getActivity(activityId, Activity.TRAINING);
        assertEquals(Activity.TRAINING, activity.getActivityType());
    }

    @Test
    public void addActivityTypeRun()
    {
        Activity activity = getActivity(activityId, Activity.RUN);
        assertEquals(Activity.RUN, activity.getActivityType());
    }


    private Activity getActivity(String activityId, int other)
    {
        return new Activity(activityId, other);
    }

}