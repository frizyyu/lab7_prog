package commands;

import supportive.MusicBand;

import java.io.IOException;

/**
 * Class for print help
 *
 * @author frizyy
 */
public class Help implements CommandInterface {
    /**
     * Execute command
     *
     * @param args null, because command hasn`t got arguments
     * @return
     * @throws IOException if happened some strange
     */
    @Override
    public String execute(String args) throws IOException {
        return """
                List of available commands:
                
                help: output help for available commands
                info: output information about the collection to the standard output stream (type, initialization date, number of elements, etc.)
                show: output to the standard output stream all elements of the collection in a string representation
                add {element}: add a new element to the collection
                update id {element}: update the value of the element a collection whose id is equal to the specified
                remove_by_id id: remove an item from the collection by its id
                clear: clear the collection
                save: save the collection to a file
                execute_script file_name: Read and execute the script from the specified file. The script contains commands in the same form as they are entered by the user interactively.
                exit: terminate the program (without saving to a file)
                add_if_max {element}: add a new element to the collection if its value exceeds the value of the largest element of this collection
                remove_greater {element}: remove from the collection all elements exceeding the specified
                remove_lower {element}: remove from the collection all elements smaller than the specified one
                average_of_albums_count: print the average value of the albumsCount field for all items in the collection
                filter_by_albums_count albumsCount: output elements whose albumsCount field value is equal to the specified one
                filter_contains_name name: output elements whose name field value contains the specified substring""";
        //return args;
    }

    /**
     * Description of command
     *
     * @return
     */
    @Override
    public String description() {
        return "the command displays help for available commands.\nusage: help";
        //return null;
    }

    @Override
    public String executeWithObject(String args, MusicBand musicBand) throws IOException {
        return null;
    }
}
