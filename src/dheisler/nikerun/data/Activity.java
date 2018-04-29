/**
 * @author Debbie Heisler
 *
 * This class contains the data to represent one activity.  Not all of the
 * possible fields for an activity are represented.  They were skipped for
 * brevity since they are not part of the coding challenge questions.
 *
 */

package dheisler.nikerun.data;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Activity
{
    public static final int RUN = 0;
    public static final int TRAINING = 1;
    public static final int OTHER = 2;

    private String activityId;
    private int type;
    private Instant startTime;
    private Instant endTime;
    private double distance;
    private double speed;


    public Activity(String activityId, int type)
    {
        this.activityId = activityId;
        this.type = type;
    }

    public boolean ranMoreThankOneK()
    {
        return distance > 1;
    }

    public boolean activityStartedBefore(String time)
    {
        Instant otherTime = convertStringToInstant(time);
        return startTime.isBefore(otherTime);
    }

    public long getLengthOfActivityInSeconds()
    {
        return Duration.between(startTime, endTime).getSeconds();
    }

    public int getActivityType()
    {
        return type;
    }

    public String getActivityId()
    {
        return activityId;
    }

    public String getStartTime()
    {
        return startTime.toString();
    }

    public String getEndTime()
    {
        return endTime.toString();
    }

    public double getDistance()
    {
        return distance;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = convertStringToInstant(startTime);
    }

    public void setEndTime(String endTime)
    {
        this.endTime = convertStringToInstant(endTime);
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    private Instant convertStringToInstant(String instant)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT;
        return Instant.from(dtf.parse(instant));
    }

}
