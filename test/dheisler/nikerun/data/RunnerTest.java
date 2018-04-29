package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest
{
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
        Activity activity = createAndPopulateRun("first", Activity.RUN, .2, 0);
        runner.addActivity(activity);
        activity = createAndPopulateRun("second", Activity.RUN, 5, 86400);
        runner.addActivity(activity);

        assertEquals(2, runner.getNumberofActivities());
    }

    @Test
    public void testAddingTwoActivitiesToOneRunnerAreSortedCorrectly()
    {
        Activity now = createAndPopulateRun("first", Activity.RUN, .2, 0);
        Activity yesterday = createAndPopulateRun("second", Activity.RUN, 5, 86400);
        runner.addActivity(now);
        runner.addActivity(yesterday);


        Activity[] activities = runner.getArrayOfActivities();
        assertEquals(now, activities[0]);
        assertEquals(yesterday, activities[1]);

        runner.sortActivities();

        activities = runner.getArrayOfActivities();
        assertEquals(yesterday, activities[0]);
        assertEquals(now, activities[1]);
    }

    private Activity createAndPopulateRun(String id, int type, double distance, long secondsAgoStarted)
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