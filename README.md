# NikeRun
A small programming project for a Nike software engineering position

They told me to have fun with it.  I am.

4/27/2018
After trying to write a data structure to hold the json data using tdd, I realized, I did not need to store all the information.  So holding off on tdd and just getting this done.  I need to find another toy problem to become better with tdd.

4/28/2018
Decided to use an ArrayList to hold all the activities for each runner.  Since uploading all data at once, will be able to sort the activities by start date once at the end of the upload.  In "production" the activites would be added to the end of the ArrayList as they came in.  In a perfect scenario, a runner runs would be spaced out long enough to not have to worry about a newer run being attributed to a runner before an older run.  I do realize there are cases, where this may not be true.

I am falling into a rabbit hole dealing with how to represent the start and end times.  I need to figure out how to pull out the previous and next days date from an Instant.  I need to convert the instant into a LocalDateTime using LocalDateTime.ofInstant().  Will do this tomorrow.  ZoneId needed as well.
