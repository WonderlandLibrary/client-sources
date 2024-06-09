package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import java.util.Map;

public class FriendManager
{
    public static Map<String, String> HorizonCode_Horizon_È;
    
    static {
        FriendManager.HorizonCode_Horizon_È = new HashMap<String, String>();
    }
    
    public static void HorizonCode_Horizon_È(final String IngameName, final String DisplayName) {
        FriendManager.HorizonCode_Horizon_È.put(IngameName, DisplayName);
    }
    
    public static void HorizonCode_Horizon_È(final String IngameName) {
        FriendManager.HorizonCode_Horizon_È.remove(IngameName);
    }
    
    public static void Â(final String IngameName, final String NewDisplayName) {
        FriendManager.HorizonCode_Horizon_È.remove(IngameName);
        FriendManager.HorizonCode_Horizon_È.put(IngameName, NewDisplayName);
    }
}
