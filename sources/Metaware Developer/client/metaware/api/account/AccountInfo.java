package client.metaware.api.account;

import client.metaware.api.utils.MinecraftUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thealtening.auth.service.AlteningServiceType;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class AccountInfo implements MinecraftUtil {
    private String uuid, username;
    private StringBuilder capes = new StringBuilder("None");
    private long hypixelBanMillis, mineplexBanMillis;
    private AccountStatus hypixelBanned, mineplexBanned;
    private AccountRank hypixelRank = AccountRank.DEFAULT, mineplexRank = AccountRank.DEFAULT;
    private double hypixelLevel, mineplexLevel;
    private AlteningServiceType type;

    public AccountInfo(AlteningServiceType type) {
        this.type = type;
    }

    public CompletableFuture<Boolean> parse() {
        CompletableFuture<Boolean> toComplete = new CompletableFuture<>();
        reset();
        username = mc.session.getUsername();
        if(username != null) {
            HttpUtil.getUUIDString(username).whenCompleteAsync((uuid, throwable) -> {
                this.uuid = uuid;
                toComplete.complete(true);
            });
        }
        if(uuid != null) {
            JsonParser parser = new JsonParser();
            CompletableFuture<HttpUtil.HttpResponse> mineconResponse = HttpUtil.asyncHttpsConnection("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            CompletableFuture<HttpUtil.HttpResponse> optifineResponse = HttpUtil.asyncHttpsConnection("http://s.optifine.net/capes/" + username + ".png");
            CompletableFuture<HttpUtil.HttpResponse> hypixelRankResponse = HttpUtil.asyncHttpsConnection("https://api.slothpixel.me/api/players/" + uuid);
            CompletableFuture<HttpUtil.HttpResponse> hypixelLevelResponse = HttpUtil.asyncHttpsConnection("https://api.slothpixel.me/api/players/" + uuid);
            CompletableFuture<HttpUtil.HttpResponse> mineplexRankResponse = HttpUtil.asyncHttpsConnection("https://www.mineplex.com/players/" + username);
            CompletableFuture<HttpUtil.HttpResponse> mineplexLevelResponse = HttpUtil.asyncHttpsConnection("https://www.mineplex.com/players/" + username);
            mineconResponse.whenCompleteAsync((response, throwable) -> {
                String decodedResponse = new String(Base64.getDecoder().decode(response.content()));
                if(decodedResponse.contains("CAPE")) capes.append(",").append("Minecon");
            });
            optifineResponse.whenCompleteAsync((response, throwable) -> {
                if(response.response() == 200) capes.append(",").append("Optifine");
            });
            hypixelRankResponse.whenCompleteAsync((response, throwable) -> {
                JsonObject hypixelObject = parser.parse(response.content()).getAsJsonObject();
                hypixelRank = AccountRank.valueOf(hypixelObject.get("rank").getAsString());
            });
            hypixelLevelResponse.whenCompleteAsync((response, throwable) -> {
                JsonObject hypixelObject = parser.parse(response.content()).getAsJsonObject();
                hypixelLevel = hypixelObject.get("level").getAsDouble();
            });
            mineplexRankResponse.whenCompleteAsync((response, throwable) -> {
                for(AccountRank accountRank : AccountRank.values()) {
                    if(response.content().contains(accountRank.toString()))
                        mineplexRank = accountRank;
                }
            });
            mineplexLevelResponse.whenCompleteAsync((response, throwable) -> {
                if(response.content().contains("Level")) {
                    String start = response.content().substring(response.content().indexOf("Level "));
                    mineplexLevel = Integer.parseInt(start.substring("Level ".length(), start.indexOf("<")));
                }
            });
        }
        return toComplete;
    }

    private void reset() {
        username = "";
        capes = new StringBuilder("None");
        hypixelRank = AccountRank.DEFAULT;
        mineplexRank = AccountRank.DEFAULT;
        hypixelLevel = 0;
        mineplexLevel = 0;
    }


    public long hypixelBanMillis() {
        return hypixelBanMillis;
    }
    public void hypixelBanMillis(long hypixelBanMillis) {
        this.hypixelBanMillis = hypixelBanMillis;
    }
    public long mineplexBanMillis() {
        return mineplexBanMillis;
    }
    public void mineplexBanMillis(long mineplexBanMillis) {
        this.mineplexBanMillis = mineplexBanMillis;
    }
    public String uuid() {
        return uuid;
    }
    public String username() {
        return username;
    }
    public StringBuilder capes() {
        return capes;
    }
    public AccountStatus hypixelBanned() {
        return hypixelBanned;
    }
    public AccountStatus mineplexBanned() {
        return mineplexBanned;
    }
    public AccountRank hypixelRank() {
        return hypixelRank;
    }
    public AccountRank mineplexRank() {
        return mineplexRank;
    }
    public double hypixelLevel() {
        return hypixelLevel;
    }
    public double mineplexLevel() {
        return mineplexLevel;
    }
    public AlteningServiceType type() {
        return type;
    }
    public void uuid(String uuid) {
        this.uuid = uuid;
    }
    public void username(String username) {
        this.username = username;
    }
    public void capes(StringBuilder capes) {
        this.capes = capes;
    }
    public void hypixelBanned(AccountStatus hypixelBanned) {
        this.hypixelBanned = hypixelBanned;
    }
    public void mineplexBanned(AccountStatus mineplexBanned) {
        this.mineplexBanned = mineplexBanned;
    }
    public void hypixelRank(AccountRank hypixelRank) {
        this.hypixelRank = hypixelRank;
    }
    public void mineplexRank(AccountRank mineplexRank) {
        this.mineplexRank = mineplexRank;
    }
    public void hypixelLevel(double hypixelLevel) {
        this.hypixelLevel = hypixelLevel;
    }
    public void mineplexLevel(double mineplexLevel) {
        this.mineplexLevel = mineplexLevel;
    }
    public void type(AlteningServiceType type) {
        this.type = type;
    }
}
