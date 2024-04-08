package helpers;
import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;

public class Logger {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("github: frizyyu");
    public org.apache.logging.log4j.Logger getLogger(){
        return logger;
    }
}
