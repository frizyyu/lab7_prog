package helpers;

import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * This class for confirmation of the action
 *
 * @author frizyy
 */
public class ContinueAction {
    /**
     * Execute method
     * @param done output if action has been continued
     * @param notDone output if action hasn`t been continued
     * @param passed output if user input is strange
     * @return int. 1 if continue, -1 if not, else 0
     */
    public int continueAction(String done, String notDone, String passed){
        Scanner input = new Scanner(System.in);
        String YorN = input.nextLine();
        if (YorN.equals("y")){
            if (done != null)
                System.out.println(done);
            return 1;
        } else if (YorN.equals("n")){
            if (notDone != null)
                System.out.println(notDone);
            return -1;
        }
        else{
            if (passed != null)
                System.out.println(passed);
            return 0;
            }
    }
}
