package commands;

import supportive.MusicBand;

import java.io.IOException;

/**
 * Interface for create commands
 *
 * @author frizyy
 */
public interface CommandInterface {
    /**
     * Execute method
     *
     * @param args string with arguments
     * @return
     * @throws IOException if happened some strange
     */
    String execute(String args) throws IOException;

    /**
     * Description of command
     *
     * @return
     */
    String description();

    String executeWithObject(String args, MusicBand musicBand) throws IOException;
}
