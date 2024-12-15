package com.alan.clients.command.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;
import com.alan.clients.module.impl.other.Spotify;
import com.alan.clients.util.chat.ChatUtil;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SpotifyCmd extends Command {
    public SpotifyCmd() {
        super("command.spotify.description", "spotify");
    }
    public String auth(String client_id_, String client_secret_) {
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("grant_type=client_credentials&client_id=" + client_id_ + "&client_secret=" + client_secret_);
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            JSONObject obj = new JSONObject(response);
            return obj.getString("access_token");
        } catch (Exception e) {
            ChatUtil.display("Spotify auth error");
        }
        return "error";
    }
    @Override
    public void execute(final String[] args) {
        new Thread(() -> {
            if (args.length < 2) {
                error(".spotify <reload/auth>");
            } else {
                switch (args[1]) {
                    case "reload":
                        Client.INSTANCE.getModuleManager().get(Spotify.class).getSpotifyInfo();
                        break;
                    case "auth":
                        if (args.length != 4) {
                            error(".spotify auth client_id client_secret");
                        } else {
                            String authtoken = auth(args[2], args[3]);
                            if (authtoken != "error") {
                                ChatUtil.display("Logged in");
                                Client.INSTANCE.getModuleManager().get(Spotify.class).auth_token = authtoken;
                                Client.INSTANCE.getModuleManager().get(Spotify.class).client_id = args[2];
                                Client.INSTANCE.getModuleManager().get(Spotify.class).client_secret = args[3];
                            }
                            break;
                        }
                }
            }
        }).start();
    }
}
