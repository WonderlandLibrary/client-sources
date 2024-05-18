package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.ArrayList;

public class CustomSky
{
    private static CustomSkyLayer[][] HorizonCode_Horizon_È;
    
    static {
        CustomSky.HorizonCode_Horizon_È = null;
    }
    
    public static void HorizonCode_Horizon_È() {
        CustomSky.HorizonCode_Horizon_È = null;
    }
    
    public static void Â() {
        HorizonCode_Horizon_È();
        if (Config.áŒŠáŠ()) {
            CustomSky.HorizonCode_Horizon_È = Ý();
        }
    }
    
    private static CustomSkyLayer[][] Ý() {
        final CustomSkyLayer[][] wsls = new CustomSkyLayer[10][0];
        final String prefix = "mcpatcher/sky/world";
        int lastWorldId = -1;
        for (int worldCount = 0; worldCount < wsls.length; ++worldCount) {
            final String wslsTrim = String.valueOf(prefix) + worldCount + "/sky";
            final ArrayList i = new ArrayList();
            for (int sls = 1; sls < 1000; ++sls) {
                final String path = String.valueOf(wslsTrim) + sls + ".properties";
                try {
                    final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(path);
                    final InputStream in = Config.HorizonCode_Horizon_È(e);
                    if (in == null) {
                        break;
                    }
                    final Properties props = new Properties();
                    props.load(in);
                    Config.HorizonCode_Horizon_È("CustomSky properties: " + path);
                    final String defSource = String.valueOf(wslsTrim) + sls + ".png";
                    final CustomSkyLayer sl = new CustomSkyLayer(props, defSource);
                    if (sl.HorizonCode_Horizon_È(path)) {
                        final ResourceLocation_1975012498 locSource = new ResourceLocation_1975012498(sl.HorizonCode_Horizon_È);
                        final ITextureObject tex = TextureUtils.HorizonCode_Horizon_È(locSource);
                        if (tex == null) {
                            Config.Ø­áŒŠá("CustomSky: Texture not found: " + locSource);
                        }
                        else {
                            sl.Â = tex.HorizonCode_Horizon_È();
                            i.add(sl);
                            in.close();
                        }
                    }
                }
                catch (FileNotFoundException var20) {
                    break;
                }
                catch (IOException var16) {
                    var16.printStackTrace();
                }
            }
            if (i.size() > 0) {
                final CustomSkyLayer[] var17 = i.toArray(new CustomSkyLayer[i.size()]);
                wsls[worldCount] = var17;
                lastWorldId = worldCount;
            }
        }
        if (lastWorldId < 0) {
            return null;
        }
        int worldCount = lastWorldId + 1;
        final CustomSkyLayer[][] var18 = new CustomSkyLayer[worldCount][0];
        for (int var19 = 0; var19 < var18.length; ++var19) {
            var18[var19] = wsls[var19];
        }
        return var18;
    }
    
    public static void HorizonCode_Horizon_È(final World world, final TextureManager re, final float celestialAngle, final float rainBrightness) {
        if (CustomSky.HorizonCode_Horizon_È != null && Config.ÇªØ­().Ý >= 8) {
            final int dimId = world.£à.µà();
            if (dimId >= 0 && dimId < CustomSky.HorizonCode_Horizon_È.length) {
                final CustomSkyLayer[] sls = CustomSky.HorizonCode_Horizon_È[dimId];
                if (sls != null) {
                    final long time = world.Ï­Ðƒà();
                    final int timeOfDay = (int)(time % 24000L);
                    for (int i = 0; i < sls.length; ++i) {
                        final CustomSkyLayer sl = sls[i];
                        if (sl.HorizonCode_Horizon_È(timeOfDay)) {
                            sl.HorizonCode_Horizon_È(timeOfDay, celestialAngle, rainBrightness);
                        }
                    }
                    Blender.HorizonCode_Horizon_È(rainBrightness);
                }
            }
        }
    }
    
    public static boolean HorizonCode_Horizon_È(final World world) {
        if (CustomSky.HorizonCode_Horizon_È == null) {
            return false;
        }
        if (Config.ÇªØ­().Ý < 8) {
            return false;
        }
        final int dimId = world.£à.µà();
        if (dimId >= 0 && dimId < CustomSky.HorizonCode_Horizon_È.length) {
            final CustomSkyLayer[] sls = CustomSky.HorizonCode_Horizon_È[dimId];
            return sls != null && sls.length > 0;
        }
        return false;
    }
}
