package dheisler.nikerun.data;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest
{
    private static final String activityId = "aShortActivityId";
    private static final String startTime = "2017-04-30T18:01:50Z";
    private static final String endTime = "2017-04-30T18:50:31.166Z";
    private static final double distance5 = 5.011560229879804;
    private static final double distance0 = 0.8650855;
    private static final long durationOfRun = 2921;
    private static final int SECONDS_IN_A_DAY = 86400;

    @Test
    public void addActivityTypeOther()
    {
        Activity activity = createActivityGivenTimes(activityId, Activity.OTHER, 1, startTime, endTime);
        assertEquals(Activity.OTHER, activity.getActivityType());
        assertEquals(startTime, activity.getStartTimeToString());
        assertEquals(endTime, activity.getEndTimeToString());
        assertEquals(activityId, activity.getActivityId());
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
        Activity activity = createActivityGivenTimes(activityId, Activity.RUN, distance0, startTime, endTime);

        assertEquals(Activity.RUN, activity.getActivityType());
        assertFalse(activity.ranMoreThankOneK());
        activity.setDistance(distance5);
        assertTrue(activity.ranMoreThankOneK());
        assertEquals(durationOfRun, activity.getLengthOfActivityInSeconds());
    }

    @Test
    public void compareTwoActivities()
    {
        Activity oneDayAgo = createActivityForNow("first", Activity.RUN, 9, SECONDS_IN_A_DAY);
        Activity twoDaysAgo = createActivityForNow("second", Activity.RUN, 2, 2 * SECONDS_IN_A_DAY);

        assertEquals(-1, twoDaysAgo.compareTo(oneDayAgo));
        assertEquals(1, oneDayAgo.compareTo(twoDaysAgo));
        assertEquals(0, oneDayAgo.compareTo(oneDayAgo));
        assertNotEquals(0, oneDayAgo.compareTo(twoDaysAgo));
    }

    @Test
    public void getJustTheDateOfAnActivity()
    {
        Activity activity = new Activity(activityId, Activity.RUN);
        activity.setStartTime(startTime);
        LocalDate activityDate = activity.getStartDate();

        assertEquals(30, activityDate.getDayOfMonth());
        assertEquals(4, activityDate.getMonthValue());
        assertEquals(2017, activityDate.getYear());
        assertEquals(DayOfWeek.SUNDAY, activityDate.getDayOfWeek());
    }

    @Test
    public void testOneActivityOccurredTheDayBeforeAnother()
    {
        Activity now = createActivityForNow("now", Activity.RUN, 1, 0);
        Activity yesterday = createActivityForNow("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity twoDaysAgo = createActivityForNow("twoDaysAgo", Activity.RUN, 3, 2 * SECONDS_IN_A_DAY);

        assertTrue(now.wasThisActivityTheDayBefore(yesterday));
        assertFalse(now.wasThisActivityTheDayBefore(twoDaysAgo));
    }

    @Test
    public void findFirstMondayOnOrBeforeGivenActivityDate()
    {
        Activity activity = createActivityGivenTimes("testing", Activity.RUN, 1, startTime, endTime);
        LocalDate monday = activity.getBeginningOfWeekDate();
        LocalDate expected = LocalDate.of(2017, 4, 24);

        assertEquals(expected, monday);
    }

    @Test
    public void whatIsDistanceForOtherActivity()
    {
        Activity activity = new Activity("testing", Activity.OTHER);

        assertEquals(0, activity.getDistance());
    }


    private Activity createActivityForNow(String id, int type, double distance, long secondsAgoStarted)
    {
        Instant start = Instant.now().minusSeconds(secondsAgoStarted);
        Instant end = start.plusSeconds(3600); // activity lasted an hour
        Activity activity = new Activity(id, type);

        activity.setDistance(distance);
        activity.setStartTime(start.toString());
        activity.setEndTime(end.toString());

        return activity;
    }


    private Activity createActivityGivenTimes(String id, int type,
                                              double distance, String start, String end)
    {
        Activity activity = new Activity(id, type);
        activity.setDistance(distance);
        activity.setStartTime(start);
        activity.setEndTime(end);

        return activity;
    }
}