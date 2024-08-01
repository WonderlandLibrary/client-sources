package wtf.diablo.client.spotify;

import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SpotifyLocalServer {
    @SneakyThrows
    public static void createConnection(){
        HttpServer serv = HttpServer.create(new InetSocketAddress(7777),0);
        serv.createContext("/diablo/spotify", context -> {
            String dd = context.getRequestURI().getQuery().split("=")[1].replace("&state","");

            System.out.println("Create Contect Code: " +dd);
            SpotifyAPI.AUTH_CODE = dd;
            System.out.println(context.getRequestURI().getQuery());
            final String messageSuccess = context.getRequestURI().getQuery().contains("code")
                    ? "Authorized\nyou can now close and play, have fun!"
                    : "failed, contact Ai";
            context.sendResponseHeaders(200, messageSuccess.length());
            OutputStream out = context.getResponseBody();
            out.write(messageSuccess.getBytes());
            out.close();
            serv.stop(0);

        });
        serv.start();
    }
}
