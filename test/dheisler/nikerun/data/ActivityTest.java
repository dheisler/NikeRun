package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest
{
    private static final String activityId = "aShortActivityId";
    private static final String startTime = "2017-04-30T18:01:50Z";
    private static final String endTime = "2017-04-30T18:50:31.166Z";
    private static final double distance5 = 5.011560229879804;
    private static final double distance0 = 0.8650855;
    private static final long durationOfRun = 2921;

    @BeforeEach
    public void setup()
    {

    }

    @Test
    public void addActivityTypeOther()
    {
        Activity activity = new Activity(activityId, Activity.OTHER);
        addStartEndTimeToActivity(activity);
        assertEquals(Activity.OTHER, activity.getActivityType());
        assertEquals(startTime, activity.getStartTime());
        assertEquals(endTime, activity.getEndTime());
    }

    @Test
    public void addActivityTypeTraining()
    {
        Activity activity = new Activity(activityId, Activity.TRAINING);
        assertEquals(Activity.TRAINING, activity.getActivityType());
    }

    @Test
    public void addActivityTypeRunAndTestSettings()
    {
        Activity activity = new Activity(activityId, Activity.RUN);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activity.setDistance(distance0);

        assertEquals(Activity.RUN, activity.getActivityType());
        assertFalse(activity.ranMoreThankOneK());
        activity.setDistance(distance5);
        assertTrue(activity.ranMoreThankOneK());
        assertTrue(activity.activityStartedBefore(endTime));
        assertEquals(durationOfRun, activity.getLengthOfActivityInSeconds());
    }

    @Test
    public void compareTwoActivities()
    {
        Activity oneDayAgo = createActivity("first", Activity.RUN, 9, 86400); // a day ago
        Activity twoDaysAgo = createActivity("second", Activity.RUN, 2, 2*(86400));  // two days ago

        assertEquals(-1, twoDaysAgo.compareTo(oneDayAgo));
        assertEquals(1, oneDayAgo.compareTo(twoDaysAgo));
        assertEquals(0, oneDayAgo.compareTo(oneDayAgo));
        assertNotEquals(0, oneDayAgo.compareTo(twoDaysAgo));
    }

    private Activity createActivity(String id, int type, double distance, long secondsAgoStarted)
    {
        Instant start = Instant.now().minusSeconds(secondsAgoStarted);
        Instant end = start.plusSeconds(3600); // activity lasted an hour
        Activity activity = new Activity(id, type);

        activity.setDistance(distance);
        activity.setStartTime(start.toString());
        activity.setEndTime(end.toString());

        return activity;
    }

    private void addStartEndTimeToActivity(Activity activity)
    {

    }

}