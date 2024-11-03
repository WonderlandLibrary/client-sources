package net.silentclient.client.utils.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.utils.reply.AbstractReply;

public class FriendsResponse extends AbstractReply {
    public ArrayList<Request> requests;
    public ArrayList<Friend> friends;

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public int getRequestCount() {
        int count = 0;

        for(Request request : getRequests()) {
            if(request.isIncoming()) {
                count++;
            }
        }

        return count;
    }

    public class Friend {
        public int id;
        public String username;
        public int selected_icon;
        public int is_online;
        public boolean plus_icon;
        public String current_server;

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public StaticResourceLocation getIcon() {
            if(selected_icon == 0) {
                return Client.getInstance().getCosmetics().getDefaultIcon();
            }

            return Client.getInstance().getCosmetics().getIconById(selected_icon);
        }

        public boolean isOnline() {
            return is_online == 1;
        }

        public String getCurrentServer() {
            return current_server;
        }

        public void cancelRequest() {
            try {
                URL url = new URL("https://api.silentclient.net/friends/cancel_request");
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "SilentClient");
                con.setRequestProperty("Authorization", "Bearer " + Client.getInstance().getUserData().getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"id\": " + id + "}";
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                Client.getInstance().updateFriendsList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Request {
        public int id;
        public String username;
        public int selected_icon;
        public int plus_icon;
        public boolean incoming;

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public StaticResourceLocation getIcon() {
            if(selected_icon == 0) {
                return Client.getInstance().getCosmetics().getDefaultIcon();
            }

            return Client.getInstance().getCosmetics().getIconById(selected_icon);
        }

        public boolean isIncoming() {
            return incoming;
        }

        public void acceptRequest() {
            try {
                URL url = new URL("https://api.silentclient.net/friends/accept_request");
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "SilentClient");
                con.setRequestProperty("Authorization", "Bearer " + Client.getInstance().getUserData().getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"id\": " + id + "}";
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                Client.getInstance().updateFriendsList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancelRequest() {
            try {
                URL url = new URL("https://api.silentclient.net/friends/cancel_request");
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "SilentClient");
                con.setRequestProperty("Authorization", "Bearer " + Client.getInstance().getUserData().getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"id\": " + id + "}";
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                Client.getInstance().updateFriendsList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
