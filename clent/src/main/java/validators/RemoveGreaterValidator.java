package validators;

import helpers.*;
import supportive.MusicBand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class RemoveGreaterValidator implements ValidatorInterface{
    LinkedHashSet<MusicBand> collection;
    public RemoveGreaterValidator(LinkedHashSet collection){
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
        if (str.length == 1){
            return true;
        }

        SimpleValidationWithElement check = new SimpleValidationWithElement();
        if (check.validate(str)){
            ClassObjectCreator creator = new ClassObjectCreator(collection);
            MusicBand myMap = creator.create(str[1]);
            List<MusicBand> mb = new ArrayList<>(collection);
            FindMax maxer = new FindMax();
            int ind=collection.size();
            for (int i=0; i <= collection.size() - 1; i++){
                MusicBand maxElement = maxer.getMax(mb.get(i), myMap);
                if (maxElement == mb.get(i) && ind == collection.size()) {
                    ind = i+1;
                    break;
                }

            }
            //if (ind == collection.size())
                //System.out.println("Nothing removed. No elements that are greater than input");
            //else {
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
