package optfine;

import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class PlayerConfigurations
{
    private static Map mapConfigurations;
    
    public static void renderPlayerItems(final ModelBiped p_renderPlayerItems_0_, final AbstractClientPlayer p_renderPlayerItems_1_, final float p_renderPlayerItems_2_, final float p_renderPlayerItems_3_) {
        final PlayerConfiguration playerconfiguration = getPlayerConfiguration(p_renderPlayerItems_1_);
        if (playerconfiguration != null) {
            playerconfiguration.renderPlayerItems(p_renderPlayerItems_0_, p_renderPlayerItems_1_, p_renderPlayerItems_2_, p_renderPlayerItems_3_);
        }
    }
    
    public static synchronized PlayerConfiguration getPlayerConfiguration(final AbstractClientPlayer p_getPlayerConfiguration_0_) {
        final String s = getPlayerName(p_getPlayerConfiguration_0_);
        PlayerConfiguration playerconfiguration = getMapConfigurations().get(s);
        if (playerconfiguration == null) {
            playerconfiguration = new PlayerConfiguration();
            getMapConfigurations().put(s, playerconfiguration);
            final PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s);
            final String s2 = "http://s.optifine.net/users/" + s + ".cfg";
            final FileDownloadThread filedownloadthread = new FileDownloadThread(s2, playerconfigurationreceiver);
            filedownloadthread.start();
        }
        return playerconfiguration;
    }
    
    public static synchronized void setPlayerConfiguration(final String p_setPlayerConfiguration_0_, final PlayerConfiguration p_setPlayerConfiguration_1_) {
        getMapConfigurations().put(p_setPlayerConfiguration_0_, p_setPlayerConfiguration_1_);
    }
    
    private static Map getMapConfigurations() {
        if (PlayerConfigurations.mapConfigurations == null) {
            PlayerConfigurations.mapConfigurations = new HashMap();
        }
        return PlayerConfigurations.mapConfigurations;
    }
    
    public static String getPlayerName(final AbstractClientPlayer p_getPlayerName_0_) {
        String s = p_getPlayerName_0_.getName();
        if (s != null && !s.isEmpty()) {
            s = StringUtils.stripControlCodes(s);
        }
        return s;
    }
    
    static {
        PlayerConfigurations.mapConfigurations = null;
    }
}
