package commands;

import supportive.MusicBand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Class for print info about collection
 *
 * @author frizyy
 */
public class Info implements CommandInterface{
    private final LinkedHashSet<MusicBand> collection;
    public Info(LinkedHashSet collection) {
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
        try {
            List<MusicBand> mb = new ArrayList<>(collection);
            return String.format("""
                About collection:
                Type: %s
                Type of elements inside: %s
                Size: %s items
                Initialization date: %s
                """, collection.getClass().getName(), mb.get(0).getClass().getName(),collection.size(), mb.get(0).getCreationDate());
        }
        catch (IndexOutOfBoundsException e){
            return """
                About collection:
                Type: null
                Type of elements inside: null
                Size: 0 items
                Initialization date: null""";
        }
        //return args;
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "Shows information about collection\nusage: info";
        //return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
