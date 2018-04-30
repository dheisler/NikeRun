package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest
{
    private static final int SECONDS_IN_A_DAY = 86400;
    private static String userId = "auserid930";
    private static String activityId = "shortactivity";
    private Runner runner;

    @BeforeEach
    public void setup()
    {
        runner = new Runner(userId);
    }

    @Test
    public void testRunnerWithUserId()
    {
        assertNotNull(runner);
        assertEquals(userId, runner.getUserId());
    }

    @Test
    public void testAddingOneOtherActivityToOneRunner()
    {
        Activity activity = new Activity(activityId, Activity.OTHER);

        runner.addActivity(activity);

        assertEquals(1, runner.getNumberofActivities());
    }

    @Test
    public void testAddingTwoRunActivitiesToOneRunner()
    {
        Activity activity = createNewRun("first", Activity.RUN, .2, 0);
        runner.addActivity(activity);
        activity = createNewRun("second", Activity.RUN, 5, 86400);
        runner.addActivity(activity);

        assertEquals(2, runner.getNumberofActivities());
    }

    @Test
    public void testAddingThreeActivitiesToOneRunnerAreSortedCorrectly()
    {
        Activity now = createNewRun("first", Activity.RUN, .2, 0);
        Activity yesterday = createNewRun("second", Activity.RUN, 5, SECONDS_IN_A_DAY);
        Activity twoDaysAgo = createNewRun("third", Activity.RUN, 3, 2*(SECONDS_IN_A_DAY));
        runner.addActivity(now);
        runner.addActivity(yesterday);
        runner.addActivity(twoDaysAgo);


        Activity[] activities = runner.getArrayOfActivities();
        assertEquals(now, activities[0]);
        assertEquals(yesterday, activities[1]);
        assertEquals(twoDaysAgo, activities[2]);

        runner.sortActivities();

        activities = runner.getArrayOfActivities();
        assertEquals(twoDaysAgo, activities[0]);
        assertEquals(yesterday, activities[1]);
        assertEquals(now, activities[2]);
    }

    @Test
    public void testRunningMoreThan1KM()
    {
        Activity now = createNewRun("first", Activity.RUN, .2, 0);
        Activity yesterday = createNewRun("second", Activity.RUN, 5, SECONDS_IN_A_DAY);
        runner.addActivity(now);

        assertFalse(runner.ranMoreThan1kmInSingleRun());

        runner.addActivity(yesterday);
        assertTrue(runner.ranMoreThan1kmInSingleRun());
    }

    @Test
    public void testRunningAtLeast1Km2DaysInARow()
    {
        Activity today = createNewRun("today", Activity.RUN, 1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.sortActivities();

        assertEquals(0, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunningAtLeast1Km3DaysInARow()
    {
        Activity today = createNewRun("today", Activity.RUN, 1.1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity dayBeforeYesterday = createNewRun("dayBeforeYesterday", Activity.RUN, 3, 2 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(dayBeforeYesterday);
        runner.sortActivities();

        assertEquals(1, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunningAtLeast1Km4DaysInARow()
    {
        Activity today = createNewRun("today", Activity.RUN, 1.1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity dayBeforeYesterday = createNewRun("dayBeforeYesterday", Activity.RUN, 3, 2 * SECONDS_IN_A_DAY);
        Activity threeDaysAGo = createNewRun("threeDaysAgo", Activity.RUN, 2, 3 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(dayBeforeYesterday);
        runner.addActivity(threeDaysAGo);
        runner.sortActivities();

        assertEquals(1, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunningAtLeast1Km6DaysInARow()
    {
        Activity today = createNewRun("today", Activity.RUN, 1.1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity twoDaysAgo = createNewRun("dayBeforeYesterday", Activity.RUN, 3, 2 * SECONDS_IN_A_DAY);
        Activity threeDaysAgo = createNewRun("daybeforethat", Activity.RUN, 2, 3 * SECONDS_IN_A_DAY);
        Activity fourDaysAgo = createNewRun("fourDaysAgo", Activity.RUN, 5, 4 * SECONDS_IN_A_DAY);
        Activity fiveDaysAgo = createNewRun("fiveDaysAgo", Activity.RUN, 1.1, 5 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(twoDaysAgo);
        runner.addActivity(threeDaysAgo);
        runner.addActivity(fourDaysAgo);
        runner.addActivity(fiveDaysAgo);
        runner.sortActivities();

        assertEquals(2, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunningAtLeast1Km4DaysNotInARow()
    {
        Activity today = createNewRun("today", Activity.RUN, 1.1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity threeDaysAgo = createNewRun("daybeforethat", Activity.RUN, 2, 3 * SECONDS_IN_A_DAY);
        Activity fourDaysAgo = createNewRun("fourDaysAgo", Activity.RUN, 5, 4 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(threeDaysAgo);
        runner.addActivity(fourDaysAgo);
        runner.sortActivities();

        assertEquals(0, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunningAtLeast1Km5DaysNotInARow()
    {
        Activity today = createNewRun("today", Activity.RUN, 1.1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity twoDaysAgo = createNewRun("dayBeforeYesterday", Activity.RUN, 3, 2 * SECONDS_IN_A_DAY);
        Activity fourDaysAgo = createNewRun("fourDaysAgo", Activity.RUN, 5, 4 * SECONDS_IN_A_DAY);
        Activity fiveDaysAgo = createNewRun("fiveDaysAgo", Activity.RUN, 1.1, 5 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(twoDaysAgo);
        runner.addActivity(fourDaysAgo);
        runner.addActivity(fiveDaysAgo);
        runner.sortActivities();

        assertEquals(1, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunning4DaysInARowOne1Km()
    {
        Activity today = createNewRun("today", Activity.RUN, 1.1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity dayBeforeYesterday = createNewRun("dayBeforeYesterday", Activity.RUN, 1, 2 * SECONDS_IN_A_DAY);
        Activity threeDaysAGo = createNewRun("threeDaysAgo", Activity.RUN, 2, 3 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(dayBeforeYesterday);
        runner.addActivity(threeDaysAGo);
        runner.sortActivities();

        assertEquals(0, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    @Test
    public void testRunning6DaysInARowOne1Km()
    {
        Activity today = createNewRun("today", Activity.RUN, 1, 0);
        Activity yesterday = createNewRun("yesterday", Activity.RUN, 2, SECONDS_IN_A_DAY);
        Activity twoDaysAgo = createNewRun("dayBeforeYesterday", Activity.RUN, 3, 2 * SECONDS_IN_A_DAY);
        Activity threeDaysAgo = createNewRun("daybeforethat", Activity.RUN, 2, 3 * SECONDS_IN_A_DAY);
        Activity fourDaysAgo = createNewRun("fourDaysAgo", Activity.RUN, 5, 4 * SECONDS_IN_A_DAY);
        Activity fiveDaysAgo = createNewRun("fiveDaysAgo", Activity.RUN, 1.1, 5 * SECONDS_IN_A_DAY);

        runner.addActivity(today);
        runner.addActivity(yesterday);
        runner.addActivity(twoDaysAgo);
        runner.addActivity(threeDaysAgo);
        runner.addActivity(fourDaysAgo);
        runner.addActivity(fiveDaysAgo);
        runner.sortActivities();

        assertEquals(1, runner.numberOfTimesRanMoreThan1km3DaysInARow());
    }

    private Activity createNewRun(String id, int type, double distance, long secondsAgoStarted)
    {
        Activity activity = new Activity(id, type);
        Instant now = Instant.now().minusSeconds(secondsAgoStarted);
        Instant then = now.plusSeconds(3600);  // activity duration an hour

        activity.setDistance(distance);
        activity.setStartTime(now.toString());
        activity.setEndTime(then.toString());

        return activity;
    }
}