package helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExecuteScriptReader {
    public String read(String fileName) throws IOException {
        FileInputStream inputStream;
        String readedFile = String.format("%s|", fileName);
        inputStream = new FileInputStream(new File(fileName).getAbsolutePath());
        BufferedInputStream buffInputStr = null;
        buffInputStr = new BufferedInputStream(inputStream);

        while (buffInputStr.available() > 0) {

            char c = (char) buffInputStr.read();

            readedFile += c;
        }
        inputStream.close();
        return readedFile;
    }
}
