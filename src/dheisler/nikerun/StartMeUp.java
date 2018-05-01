/**
 * author Debbie Heisler
 *
 * This is the class that runs everything.  It takes the user_id to find from
 * the commandline.  Then prints the calculations to the screen.
 *
 * Usaage:
 * StartMeUp _runnerID_
 */
package dheisler.nikerun;

import dheisler.nikerun.data.Runner;
import dheisler.nikerun.util.LoadDataUtility;
import dheisler.nikerun.util.CalculationUtility;

import java.util.HashMap;

public class StartMeUp
{
    public static void main(String args[])
    {
        HashMap<String, Runner> runners = LoadDataUtility.populateRunners();

        String runnerToGenerateInformation = args[0];

        Runner runner = runners.get(runnerToGenerateInformation);

        String output = CalculationUtility.getCalculationsAsJsonString(runner);

        System.out.println(output);
    }
}
