package net.minecraft.src;

import java.util.*;
import java.io.*;

public class AchievementMap
{
    public static AchievementMap instance;
    private Map guidMap;
    
    static {
        AchievementMap.instance = new AchievementMap();
    }
    
    private AchievementMap() {
        this.guidMap = new HashMap();
        try {
            final BufferedReader var1 = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));
            String var2;
            while ((var2 = var1.readLine()) != null) {
                final String[] var3 = var2.split(",");
                final int var4 = Integer.parseInt(var3[0]);
                this.guidMap.put(var4, var3[1]);
            }
            var1.close();
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }
    
    public static String getGuid(final int par0) {
        return AchievementMap.instance.guidMap.get(par0);
    }
}
