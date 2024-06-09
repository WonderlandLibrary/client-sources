package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfigurations
{
    private static Map HorizonCode_Horizon_È;
    
    static {
        PlayerConfigurations.HorizonCode_Horizon_È = null;
    }
    
    public static void HorizonCode_Horizon_È(final ModelBiped modelBiped, final AbstractClientPlayer player, final float scale, final float partialTicks) {
        final PlayerConfiguration cfg = HorizonCode_Horizon_È(player);
        if (cfg != null) {
            cfg.HorizonCode_Horizon_È(modelBiped, player, scale, partialTicks);
        }
    }
    
    public static synchronized PlayerConfiguration HorizonCode_Horizon_È(final AbstractClientPlayer player) {
        final String name = Â(player);
        PlayerConfiguration pc = HorizonCode_Horizon_È().get(name);
        if (pc == null) {
            pc = new PlayerConfiguration();
            HorizonCode_Horizon_È().put(name, pc);
            final PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
            final String url = "http://s.optifine.net/users/" + name + ".cfg";
            final FileDownloadThread fdt = new FileDownloadThread(url, pcl);
            fdt.start();
        }
        return pc;
    }
    
    public static synchronized void HorizonCode_Horizon_È(final String player, final PlayerConfiguration pc) {
        HorizonCode_Horizon_È().put(player, pc);
    }
    
    private static Map HorizonCode_Horizon_È() {
        if (PlayerConfigurations.HorizonCode_Horizon_È == null) {
            PlayerConfigurations.HorizonCode_Horizon_È = new HashMap();
        }
        return PlayerConfigurations.HorizonCode_Horizon_È;
    }
    
    public static String Â(final AbstractClientPlayer player) {
        String username = player.v_();
        if (username != null && !username.isEmpty()) {
            username = StringUtils.HorizonCode_Horizon_È(username);
        }
        return username;
    }
}
