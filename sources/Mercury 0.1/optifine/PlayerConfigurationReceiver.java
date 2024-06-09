/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import optifine.Config;
import optifine.IFileDownloadListener;
import optifine.PlayerConfiguration;
import optifine.PlayerConfigurationParser;
import optifine.PlayerConfigurations;

public class PlayerConfigurationReceiver
implements IFileDownloadListener {
    private String player = null;

    public PlayerConfigurationReceiver(String player) {
        this.player = player;
    }

    @Override
    public void fileDownloadFinished(String url, byte[] bytes, Throwable exception) {
        if (bytes != null) {
            try {
                String e2 = new String(bytes, "ASCII");
                JsonParser jp2 = new JsonParser();
                JsonElement je = jp2.parse(e2);
                PlayerConfigurationParser pcp = new PlayerConfigurationParser(this.player);
                PlayerConfiguration pc2 = pcp.parsePlayerConfiguration(je);
                if (pc2 != null) {
                    pc2.setInitialized(true);
                    PlayerConfigurations.setPlayerConfiguration(this.player, pc2);
                }
            }
            catch (Exception var9) {
                Config.dbg("Error parsing configuration: " + url + ", " + var9.getClass().getName() + ": " + var9.getMessage());
            }
        }
    }
}

