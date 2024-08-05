package fr.dog.util.packet;

import lombok.experimental.UtilityClass;
import org.lwjglx.Sys;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Function;

@UtilityClass
public class RequestUtil {
    public final Function<String, String> requestResult = endpointWithArgs -> {
        try {
            URL url = new URL("https://legitclient.com" + endpointWithArgs);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuilder requestResult = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null)
                requestResult.append(inputLine);

            bufferedReader.close();
            return requestResult.toString();
        } catch (Throwable throwable) {

            boolean compileErrorPrevention = true;

            while (true) {
                if (compileErrorPrevention)
                    break;
            }

            throw new RuntimeException("Authentication Issue!");
        }
    };
    public final Function<String, String> requestResultAll = endpointWithArgs -> {
        try {
            URL url = new URL(endpointWithArgs);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuilder requestResult = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null)
                requestResult.append(inputLine);

            bufferedReader.close();
            return requestResult.toString();
        } catch (Throwable throwable) {
            boolean compileErrorPrevention = true;

            while (true) {
                if (compileErrorPrevention)
                    break;
            }

            throw new RuntimeException("Authentication Issue!");
        }
    };
}
