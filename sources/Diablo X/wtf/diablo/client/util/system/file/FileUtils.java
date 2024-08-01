package wtf.diablo.client.util.system.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    private FileUtils() {
    }

    // i think i stole from g8
    public static String readInputStream(final InputStream inputStream)
    {
        final StringBuilder stringBuilder = new StringBuilder();

        try
        {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
