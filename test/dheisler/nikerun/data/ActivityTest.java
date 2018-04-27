package dheisler.nikerun.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest
{
    private static String activityId = "aShortActivityId";
    private Activity activity;

    @BeforeEach
    public void setup()
    {
        activity = new Activity(activityId);
    }

    @Test
    public void testRunWithActifityId()
    {
        assertNotNull(activity);
    }

}