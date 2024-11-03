package net.silentclient.client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.HatData;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.utils.types.PlayerResponse;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Players {
    public static final Map<String, PlayerResponse.Account> playersCache = new HashMap<>();
    public static boolean isLoading = false;

    public static void reload() {
        Client.logger.info("Clearing players cache");
		playersCache.clear();
    }

    public static String getPlayerStatus(boolean isMainPlayer, String name, UUID id, AbstractClientPlayer playerRow) {
        AbstractClientPlayerExt player = (AbstractClientPlayerExt) playerRow;
        if(player.silent$getNameClear().toLowerCase().equals(Client.getInstance().getAccount().getUsername()) && !playersCache.containsKey(name.toLowerCase())) {
            playersCache.put(name.toLowerCase(), Client.getInstance().getAccount());
        }
        if(playersCache.containsKey(name.toLowerCase())) {
            PlayerResponse.Account result = playersCache.get(name.toLowerCase());
            if(result == null) {
                return "false";
            }

            if(result.getSelectedCape() != 0) {
                player.silent$setCape(Client.getInstance().getCosmetics().getCapeById(result.getSelectedCape()));
                player.silent$setCapeShoulders(Client.getInstance().getCosmetics().getCapeShoulders(result.getSelectedCape()));
            } else {
                player.silent$setCape(null);
                player.silent$setCapeShoulders(null);
            }


            if(result.getSelectedWings() != 0) {
                player.silent$setWings(Client.getInstance().getCosmetics().getWingsById(result.getSelectedWings()));
            } else {
                player.silent$setWings(null);
            }

            if(result.getSelectedHat() != 0 && Client.getInstance().getCosmetics().getHatById(result.getSelectedHat()) != null) {
                player.silent$setHat(new HatData(Client.getInstance().getCosmetics().getHatById(result.getSelectedHat()).getTexture(), Client.getInstance().getCosmetics().getHatById(result.getSelectedHat()).getModel()));
            } else {
                player.silent$setHat(null);
            }

            if(result.getSelectedNeck() != 0 && Client.getInstance().getCosmetics().getHatById(result.getSelectedNeck()) != null) {
                player.silent$setNeck(new HatData(Client.getInstance().getCosmetics().getHatById(result.getSelectedNeck()).getTexture(), Client.getInstance().getCosmetics().getHatById(result.getSelectedNeck()).getModel()));
            } else {
                player.silent$setNeck(null);
            }

            if(result.getSelectedMask() != 0 && Client.getInstance().getCosmetics().getHatById(result.getSelectedMask()) != null) {
                player.silent$setMask(new HatData(Client.getInstance().getCosmetics().getHatById(result.getSelectedMask()).getTexture(), Client.getInstance().getCosmetics().getHatById(result.getSelectedMask()).getModel()));
            } else {
                player.silent$setMask(null);
            }

            if(result.getSelectedShield() != 0 && Client.getInstance().getCosmetics().getShieldById(result.getSelectedShield()) != null) {
                player.silent$setShield(Client.getInstance().getCosmetics().getShieldById(result.getSelectedShield()));
            } else {
                player.silent$setShield(null);
            }

            if(result.getSelectedIcon() != 0) {
                player.silent$setPlayerIcon(Client.getInstance().getCosmetics().getIconById(result.getSelectedIcon()));
            } else {
                player.silent$setPlayerIcon(Client.getInstance().getCosmetics().getDefaultIcon());
            }


            if(result.getSelectedBandana() != 0) {
                player.silent$setBandana(Client.getInstance().getCosmetics().getBandanaById(result.getSelectedBandana()));
            } else {
                player.silent$setBandana(null);
            }

            player.silent$setShoulders(result.getCapeShoulders());
            player.silent$setCapeType(result.getCapeType());

            player.silent$setAccount(result);

            return result.isOnline() ? "true" : "false";
        } else {
            if(isLoading) {
                return "false";
            }
            Players.isLoading = true;
            Client.logger.info("Loading Account of " + name.toLowerCase());
            playersCache.put(name.toLowerCase(), null);
            (new Thread("loadingAccountOf"+name) {
                public void run() {
                    PlayerResponse response = getAccount(name.toLowerCase());
                    if(response != null && response.getAccount() != null) {
                        playersCache.put(response.getAccount().getUsername().toLowerCase(), response.getAccount());
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Client.logger.catching(e);
                    }
                    Players.isLoading = false;
                }
            }).start();
        }
        return "false";
    }

    public static void handleAccount(PlayerResponse.Account account) {

    }

    public static PlayerResponse getAccount(String name) {
        try {
            String content = Requests.get("https://api.silentclient.net/account/"+name);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            PlayerResponse response = gson.fromJson(content, PlayerResponse.class);
            return response;
        } catch (Exception e) {
            Client.logger.catching(e);
            return null;
        }
    }

    public static void register() {
        try {
            String content = Requests.post("https://api.silentclient.net/account/set_online", new JSONObject().put("online", true).toString());

            Client.logger.info("STARTING > registering-player > response: " + content);
        } catch (Exception e) {
            Client.logger.catching(e);
            Client.logger.info("STARTING > registering-player > ERROR: " + e.getMessage());
        }
    }

    public static void unregister() {
        try {
            String content = Requests.post("https://api.silentclient.net/account/set_online", new JSONObject().put("online", false).toString());

            Client.logger.info("STOPPING > unregistering-player > response: " + content.toString());
        } catch (Exception e) {
            Client.logger.catching(e);
            Client.logger.error("STOPPING > unregistering-player > ERROR: " + e.getMessage());
        }
    }
}