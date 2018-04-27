package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testAddingOneRunToRunner()
    {
        Activity activity = new Activity(activityId, Activity.OTHER);
        runner.addActivity(activity);
        assertEquals(1, runner.getNumberofActivities());
    }
}