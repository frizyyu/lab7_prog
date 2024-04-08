package commands;

import supportive.MusicBand;

import java.io.IOException;
import java.util.LinkedHashSet;

public class ClienConnect implements CommandInterface{
    LinkedHashSet<MusicBand> collection;
    public ClienConnect(LinkedHashSet<MusicBand> collection) {
        this.collection = collection;
    }

    @Override
    public String execute(String args) throws IOException {
        //System.out.println(collection);
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
