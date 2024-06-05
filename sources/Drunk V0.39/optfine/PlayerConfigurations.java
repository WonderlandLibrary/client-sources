/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.StringUtils;
import optfine.FileDownloadThread;
import optfine.PlayerConfiguration;
import optfine.PlayerConfigurationReceiver;

public class PlayerConfigurations {
    private static Map mapConfigurations = null;

    public static void renderPlayerItems(ModelBiped p_renderPlayerItems_0_, AbstractClientPlayer p_renderPlayerItems_1_, float p_renderPlayerItems_2_, float p_renderPlayerItems_3_) {
        PlayerConfiguration playerconfiguration = PlayerConfigurations.getPlayerConfiguration(p_renderPlayerItems_1_);
        if (playerconfiguration == null) return;
        playerconfiguration.renderPlayerItems(p_renderPlayerItems_0_, p_renderPlayerItems_1_, p_renderPlayerItems_2_, p_renderPlayerItems_3_);
    }

    public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer p_getPlayerConfiguration_0_) {
        String s = PlayerConfigurations.getPlayerName(p_getPlayerConfiguration_0_);
        PlayerConfiguration playerconfiguration = (PlayerConfiguration)PlayerConfigurations.getMapConfigurations().get(s);
        if (playerconfiguration != null) return playerconfiguration;
        playerconfiguration = new PlayerConfiguration();
        PlayerConfigurations.getMapConfigurations().put(s, playerconfiguration);
        PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s);
        String s1 = "http://s.optifine.net/users/" + s + ".cfg";
        FileDownloadThread filedownloadthread = new FileDownloadThread(s1, playerconfigurationreceiver);
        filedownloadthread.start();
        return playerconfiguration;
    }

    public static synchronized void setPlayerConfiguration(String p_setPlayerConfiguration_0_, PlayerConfiguration p_setPlayerConfiguration_1_) {
        PlayerConfigurations.getMapConfigurations().put(p_setPlayerConfiguration_0_, p_setPlayerConfiguration_1_);
    }

    private static Map getMapConfigurations() {
        if (mapConfigurations != null) return mapConfigurations;
        mapConfigurations = new HashMap();
        return mapConfigurations;
    }

    public static String getPlayerName(AbstractClientPlayer p_getPlayerName_0_) {
        String s = p_getPlayerName_0_.getName();
        if (s == null) return s;
        if (s.isEmpty()) return s;
        return StringUtils.stripControlCodes(s);
    }
}

