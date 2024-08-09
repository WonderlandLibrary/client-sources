/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.optifine.Config;
import net.optifine.http.IFileDownloadListener;
import net.optifine.player.PlayerConfiguration;
import net.optifine.player.PlayerConfigurationParser;
import net.optifine.player.PlayerConfigurations;

public class PlayerConfigurationReceiver
implements IFileDownloadListener {
    private String player = null;

    public PlayerConfigurationReceiver(String string) {
        this.player = string;
    }

    @Override
    public void fileDownloadFinished(String string, byte[] byArray, Throwable throwable) {
        if (byArray != null) {
            try {
                String string2 = new String(byArray, "ASCII");
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(string2);
                PlayerConfigurationParser playerConfigurationParser = new PlayerConfigurationParser(this.player);
                PlayerConfiguration playerConfiguration = playerConfigurationParser.parsePlayerConfiguration(jsonElement);
                if (playerConfiguration != null) {
                    playerConfiguration.setInitialized(false);
                    PlayerConfigurations.setPlayerConfiguration(this.player, playerConfiguration);
                }
            } catch (Exception exception) {
                Config.dbg("Error parsing configuration: " + string + ", " + exception.getClass().getName() + ": " + exception.getMessage());
            }
        }
    }
}

