package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.BufferedReader;
import org.apache.commons.io.IOUtils;
import java.io.FileNotFoundException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import java.io.Reader;
import com.google.gson.JsonParser;
import com.google.common.io.Files;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class ResourceIndex
{
    private static final Logger HorizonCode_Horizon_È;
    private final Map Â;
    private static final String Ý = "CL_00001831";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public ResourceIndex(final File p_i1047_1_, final String p_i1047_2_) {
        this.Â = Maps.newHashMap();
        if (p_i1047_2_ != null) {
            final File var3 = new File(p_i1047_1_, "objects");
            final File var4 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
            BufferedReader var5 = null;
            try {
                var5 = Files.newReader(var4, Charsets.UTF_8);
                final JsonObject var6 = new JsonParser().parse((Reader)var5).getAsJsonObject();
                final JsonObject var7 = JsonUtils.HorizonCode_Horizon_È(var6, "objects", (JsonObject)null);
                if (var7 != null) {
                    for (final Map.Entry var9 : var7.entrySet()) {
                        final JsonObject var10 = var9.getValue();
                        final String var11 = var9.getKey();
                        final String[] var12 = var11.split("/", 2);
                        final String var13 = (var12.length == 1) ? var12[0] : (String.valueOf(var12[0]) + ":" + var12[1]);
                        final String var14 = JsonUtils.Ó(var10, "hash");
                        final File var15 = new File(var3, String.valueOf(var14.substring(0, 2)) + "/" + var14);
                        this.Â.put(var13, var15);
                    }
                }
            }
            catch (JsonParseException var16) {
                ResourceIndex.HorizonCode_Horizon_È.error("Unable to parse resource index file: " + var4);
            }
            catch (FileNotFoundException var17) {
                ResourceIndex.HorizonCode_Horizon_È.error("Can't find the resource index file: " + var4);
            }
            finally {
                IOUtils.closeQuietly((Reader)var5);
            }
            IOUtils.closeQuietly((Reader)var5);
        }
    }
    
    public Map HorizonCode_Horizon_È() {
        return this.Â;
    }
}
