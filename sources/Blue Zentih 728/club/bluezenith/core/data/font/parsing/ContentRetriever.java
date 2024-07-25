package club.bluezenith.core.data.font.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ContentRetriever {

    public static String readConfigFile(File fullPath) {
        StringBuilder result = new StringBuilder();
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;

            while ((line = reader.readLine()) != null)
                if(!line.startsWith("//"))
                    result.append(line.replace("\n", "").replace(" ", ""));

            reader.close();
        } catch (IOException exception) {
            result = null;
        }
        return result != null ? result.toString() : null;
    }
}
