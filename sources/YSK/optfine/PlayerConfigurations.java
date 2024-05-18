package optfine;

import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class PlayerConfigurations
{
    private static Map mapConfigurations;
    private static final String[] I;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("/\u0018> |hC9~)7\u0018#6/)\td>#3C?##5\u001fe", "GlJPF");
        PlayerConfigurations.I[" ".length()] = I("{2\u0007\u0002", "UQaeh");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        final PlayerConfiguration playerConfiguration = getPlayerConfiguration(abstractClientPlayer);
        if (playerConfiguration != null) {
            playerConfiguration.renderPlayerItems(modelBiped, abstractClientPlayer, n, n2);
        }
    }
    
    public static String getPlayerName(final AbstractClientPlayer abstractClientPlayer) {
        String s = abstractClientPlayer.getName();
        if (s != null && !s.isEmpty()) {
            s = StringUtils.stripControlCodes(s);
        }
        return s;
    }
    
    private static Map getMapConfigurations() {
        if (PlayerConfigurations.mapConfigurations == null) {
            PlayerConfigurations.mapConfigurations = new HashMap();
        }
        return PlayerConfigurations.mapConfigurations;
    }
    
    public static synchronized void setPlayerConfiguration(final String s, final PlayerConfiguration playerConfiguration) {
        getMapConfigurations().put(s, playerConfiguration);
    }
    
    public static synchronized PlayerConfiguration getPlayerConfiguration(final AbstractClientPlayer abstractClientPlayer) {
        final String playerName = getPlayerName(abstractClientPlayer);
        PlayerConfiguration playerConfiguration = getMapConfigurations().get(playerName);
        if (playerConfiguration == null) {
            playerConfiguration = new PlayerConfiguration();
            getMapConfigurations().put(playerName, playerConfiguration);
            new FileDownloadThread(PlayerConfigurations.I["".length()] + playerName + PlayerConfigurations.I[" ".length()], new PlayerConfigurationReceiver(playerName)).start();
        }
        return playerConfiguration;
    }
    
    static {
        I();
        PlayerConfigurations.mapConfigurations = null;
    }
}
