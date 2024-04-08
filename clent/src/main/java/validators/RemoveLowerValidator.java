package validators;

import helpers.*;
import supportive.MusicBand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class RemoveLowerValidator implements ValidatorInterface{
    LinkedHashSet<MusicBand> collection;

    public RemoveLowerValidator(LinkedHashSet collection){
    this.collection = collection;
    }
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */
    @Override
    public boolean validationCheck(String[] str) throws IOException {
        if (str.length == 1) {
            return true;
        }

        SimpleValidationWithElement check = new SimpleValidationWithElement();
        if (check.validate(str)) {
            ClassObjectCreator creator = new ClassObjectCreator(collection);
            MusicBand myMap = creator.create(str[1]);
            List<MusicBand> mb = new ArrayList<>(collection);
            List<Integer> indexes = new ArrayList<>();

            for (int i = 0; i <= collection.size() - 1; i++) {
                FindMax maxer = new FindMax();
                MusicBand maxElement = maxer.getMax(mb.get(i), myMap);
                if (maxElement != mb.get(i))
                    indexes.add(i);
                else
                    break;
            }
            /*if (indexes.size() == 0)
                System.out.println("Nothing removed. No elements that are lower than input");
            else {*/
                System.out.print("Start removing? y/n\n");
                System.out.printf("%s >>> ", CreateUser.userName);
                ContinueAction cont = new ContinueAction();
                int contAction = cont.continueAction("Removing..", "Ok.. stopped", "Action skipped. Invalid answer");
                if (contAction == 1) {
                    return true;
                }
            //}
        }
        return false;
    }

    @Override
    public String parameterCheck(String[] str) {
        if (str.length != 1)
            return str[1];
        return "";
    }
}
