package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest
{
    private static final String activityId = "aShortActivityId";
    private static final String startTime = "2017-04-30T18:01:50Z";
    private static final String endTime = "2017-04-30T18:50:31.166Z";
    private static final double distance5 = 5.011560229879804;
    private static final double distance0 = 0.8650855;

    @BeforeEach
    public void setup()
    {

    }

    @Test
    public void addActivityTypeOther()
    {
        Activity activity = getActivity(activityId, Activity.OTHER);
        addStartEndTimeToActivity(activity);
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
        addStartEndTimeToActivity(activity);
        activity.setDistance(distance0);
        assertFalse(activity.ranMoreThankOneK());
        activity.setDistance(distance5);
        assertTrue(activity.ranMoreThankOneK());
    }

    private Activity getActivity(String activityId, int type)
    {
        return new Activity(activityId, type);
    }

    private void addStartEndTimeToActivity(Activity activity)
    {
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
    }

}