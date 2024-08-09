package xyz.mathax.mathaxclient.utils.network.api;

import org.json.JSONObject;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.network.capes.Cape;
import xyz.mathax.mathaxclient.utils.network.capes.Capes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {
    public String username;

    public List<UUID> minecraftAccounts = new ArrayList<>();

    public List<Cape> capes = new ArrayList<>();

    public Cape selectedCape;

    public Account(String token) {
        login(token);
    }

    private void login(String token) {
        if (token == null || token.isBlank()) {
            return;
        }

        JSONObject json = Api.getJSON("/account/info", token);
        if (json == null || !json.has("username")) {
            Api.loggedAccount = null;
            return;
        }

        if (json.has("error")) {
            MatHax.LOG.error("Error logging into account: " + json.get("error"));
            Api.loggedAccount = null;
            return;
        }

        username = json.getString("username");

        if (json.has("minecraft-accounts") && JSONUtils.isValidJSONArray(json, "minecraft-accounts")) {
            json.getJSONArray("minecraft-accounts").forEach(minecraftAccount -> {
                if (!(minecraftAccount instanceof UUID uuid)) {
                    return;
                }

                minecraftAccounts.add(uuid);
            });
        }

        if (json.has("capes") && JSONUtils.isValidJSONArray(json, "capes")) {
            json.getJSONArray("capes").forEach(capeObject -> {
                if (!(capeObject instanceof JSONObject capeJson)) {
                    return;
                }

                if (capeJson.has("name")) {
                    Cape cape = Capes.getCape(capeJson.getString("name"));
                    capes.add(cape);

                    if (capeJson.has("selected") && capeJson.getBoolean("selected")) {
                        selectedCape = cape;

                        minecraftAccounts.forEach(uuid -> Capes.selectPlayerCape(uuid, selectedCape));
                    }
                }
            });
        }
    }
}
