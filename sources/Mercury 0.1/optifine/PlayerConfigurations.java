/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import optifine.FileDownloadThread;
import optifine.IFileDownloadListener;
import optifine.PlayerConfiguration;
import optifine.PlayerConfigurationReceiver;

public class PlayerConfigurations {
    private static Map mapConfigurations = null;

    public static void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
        PlayerConfiguration cfg = PlayerConfigurations.getPlayerConfiguration(player);
        if (cfg != null) {
            cfg.renderPlayerItems(modelBiped, player, scale, partialTicks);
        }
    }

    public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer player) {
        String name = player.getNameClear();
        if (name == null) {
            return null;
        }
        PlayerConfiguration pc2 = (PlayerConfiguration)PlayerConfigurations.getMapConfigurations().get(name);
        if (pc2 == null) {
            pc2 = new PlayerConfiguration();
            PlayerConfigurations.getMapConfigurations().put(name, pc2);
            PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
            String url = "http://s.optifine.net/users/" + name + ".cfg";
            FileDownloadThread fdt = new FileDownloadThread(url, pcl);
            fdt.start();
        }
        return pc2;
    }

    public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc2) {
        PlayerConfigurations.getMapConfigurations().put(player, pc2);
    }

    private static Map getMapConfigurations() {
        if (mapConfigurations == null) {
            mapConfigurations = new HashMap();
        }
        return mapConfigurations;
    }
}

