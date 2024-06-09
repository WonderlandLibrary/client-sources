package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PlayerConfigurationReceiver implements IFileDownloadListener
{
    private String HorizonCode_Horizon_È;
    
    public PlayerConfigurationReceiver(final String player) {
        this.HorizonCode_Horizon_È = null;
        this.HorizonCode_Horizon_È = player;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String url, final byte[] bytes, final Throwable exception) {
        if (bytes != null) {
            try {
                final String e = new String(bytes, "ASCII");
                final JsonParser jp = new JsonParser();
                final JsonElement je = jp.parse(e);
                final PlayerConfigurationParser pcp = new PlayerConfigurationParser(this.HorizonCode_Horizon_È);
                final PlayerConfiguration pc = pcp.HorizonCode_Horizon_È(je);
                if (pc != null) {
                    pc.HorizonCode_Horizon_È(true);
                    PlayerConfigurations.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, pc);
                }
            }
            catch (Exception var9) {
                var9.printStackTrace();
            }
        }
    }
}
