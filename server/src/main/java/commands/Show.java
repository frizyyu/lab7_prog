package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.DBManipulator;
import helpers.MusicBandDbManipulator;
import helpers.ServerToClient;
import supportive.MusicBand;
import helpers.ZonedDateTimeTypeAdapter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * This class for show collection
 *
 * @author frizyy
 */
public class Show implements CommandInterface {
    private final LinkedHashSet<MusicBand> collection;
    public Show(LinkedHashSet collection) {
        this.collection = collection;
    }
    /**
     * Execute command
     *
     * @param args null, because command hasn`t got arguments
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        /*MusicBandDbManipulator mbdbm = new MusicBandDbManipulator(dbm);
        String res = mbdbm.selectAll();
        if (res != null)
            return res;
        return "Collection is empty";*/
        if (collection.isEmpty())
            return "Collection is empty";
        else {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                    .create();
            List<MusicBand> mb = new ArrayList<>(collection);
            LinkedHashSet<String> res = new LinkedHashSet<>();
            for (int i = 0; i <= collection.size() - 1; i++){
                res.add(String.format("%s", gson.toJson(mb.get(i))));
            }
            //System.out.println(res.stream().reduce("", (n, m) -> n + (m + "\n"), (n1, n2) -> n1 + n2).trim());
            return res.stream().reduce("", (n, m) -> n + (m + "\n"), (n1, n2) -> n1 + n2).trim();
        }
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Shows all the elements of the collection in a string representation\nusage: show";
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
