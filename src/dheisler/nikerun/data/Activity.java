/**
 * @author Debbie Heisler
 *
 * This class contains the data to represent one activity.  Not all of the
 * possible fields for an activity are represented.  They were skipped for
 * brevity since they are not part of the coding challenge questions.
 *
 */

package dheisler.nikerun.data;

import java.time.*;
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

    public int compareTo(Activity other)
    {
        Instant otherStart = convertStringToInstant(other.getStartTimeToString());

        if (startTime.equals(otherStart))
            return 0;
        else if (startTime.isBefore(otherStart))
            return -1;
        else
            return 1;
    }

    public boolean wasThisActivityTheDayBefore(Activity yesterday)
    {
        LocalDate dateBefore = yesterday.getStartDate();
        LocalDate mydateOneDayBefore = getStartDate().minusDays(1);

        return mydateOneDayBefore.equals(dateBefore);
    }

    public long getLengthOfActivityInSeconds()
    {
        return Duration.between(startTime, endTime).getSeconds();
    }

    /**
     * Gets the date which began the current week based on the date of the start time.
     * @return The first Monday on or before the start time of this Activity
     */
    public LocalDate getBeginningOfWeekDate()
    {
        LocalDate date = getStartDate();
        DayOfWeek dow = date.getDayOfWeek();
        LocalDate monday = null;

        switch(dow)
        {
            case MONDAY:
                monday = date;
                break;
            case TUESDAY:
                monday = date.minusDays(1);
                break;
            case WEDNESDAY:
                monday = date.minusDays(2);
                break;
            case THURSDAY:
                monday = date.minusDays(3);
                break;
            case FRIDAY:
                monday = date.minusDays(4);
                break;
            case SATURDAY:
                monday = date.minusDays(5);
                break;
            case SUNDAY:
                monday = date.minusDays(6);
        }

        return monday;
    }

    public int getActivityType()
    {
        return type;
    }

    public String getActivityId()
    {
        return activityId;
    }

    public String getStartTimeToString()
    {
        return startTime.toString();
    }

    public LocalDate getStartDate()
    {
        return convertInstantToLocalDate(startTime);
    }

    public String getEndTimeToString()
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

    private LocalDate convertInstantToLocalDate(Instant time)
    {
        return (LocalDateTime.ofInstant(time, ZoneOffset.UTC)).toLocalDate();
    }
}
