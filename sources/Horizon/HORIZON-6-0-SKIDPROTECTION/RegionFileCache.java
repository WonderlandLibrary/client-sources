package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import com.google.common.collect.Maps;
import java.util.Map;

public class RegionFileCache
{
    private static final Map HorizonCode_Horizon_È;
    private static final String Â = "CL_00000383";
    
    static {
        HorizonCode_Horizon_È = Maps.newHashMap();
    }
    
    public static synchronized RegionFile HorizonCode_Horizon_È(final File worldDir, final int chunkX, final int chunkZ) {
        final File var3 = new File(worldDir, "region");
        final File var4 = new File(var3, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
        final RegionFile var5 = RegionFileCache.HorizonCode_Horizon_È.get(var4);
        if (var5 != null) {
            return var5;
        }
        if (!var3.exists()) {
            var3.mkdirs();
        }
        if (RegionFileCache.HorizonCode_Horizon_È.size() >= 256) {
            HorizonCode_Horizon_È();
        }
        final RegionFile var6 = new RegionFile(var4);
        RegionFileCache.HorizonCode_Horizon_È.put(var4, var6);
        return var6;
    }
    
    public static synchronized void HorizonCode_Horizon_È() {
        for (final RegionFile var2 : RegionFileCache.HorizonCode_Horizon_È.values()) {
            try {
                if (var2 == null) {
                    continue;
                }
                var2.HorizonCode_Horizon_È();
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
        }
        RegionFileCache.HorizonCode_Horizon_È.clear();
    }
    
    public static DataInputStream Â(final File worldDir, final int chunkX, final int chunkZ) {
        final RegionFile var3 = HorizonCode_Horizon_È(worldDir, chunkX, chunkZ);
        return var3.HorizonCode_Horizon_È(chunkX & 0x1F, chunkZ & 0x1F);
    }
    
    public static DataOutputStream Ý(final File worldDir, final int chunkX, final int chunkZ) {
        final RegionFile var3 = HorizonCode_Horizon_È(worldDir, chunkX, chunkZ);
        return var3.Â(chunkX & 0x1F, chunkZ & 0x1F);
    }
}
