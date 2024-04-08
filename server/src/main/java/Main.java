import helpers.CreateUsersMap;
import helpers.ServerToClient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final int BUFFER_LENGHT = 4096;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Logger logger = new helpers.Logger().getLogger();
        LinkedHashSet<supportive.MusicBand> collection = new LinkedHashSet<>();
        ServerToClient listener = new ServerToClient(InetAddress.getByName("127.0.0.1"), collection); //start server
        logger.log(Level.INFO, "SERVER STARTED");
        //ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        while (true){
            //fixedThreadPool.submit(() -> {
                //fixedThreadPool.execute(listener).get(); //listen client and send response
                //if(fixedThreadPool.isTerminated()) fixedThreadPool.shutdown();
                listener.listen();
            //}).get();
        }
    }
}
