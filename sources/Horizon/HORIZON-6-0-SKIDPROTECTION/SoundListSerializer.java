package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.Validate;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;

public class SoundListSerializer implements JsonDeserializer
{
    private static final String HorizonCode_Horizon_È = "CL_00001124";
    
    public SoundList HorizonCode_Horizon_È(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        final JsonObject var4 = JsonUtils.Âµá€(p_deserialize_1_, "entry");
        final SoundList var5 = new SoundList();
        var5.HorizonCode_Horizon_È(JsonUtils.HorizonCode_Horizon_È(var4, "replace", false));
        final SoundCategory var6 = SoundCategory.HorizonCode_Horizon_È(JsonUtils.HorizonCode_Horizon_È(var4, "category", SoundCategory.HorizonCode_Horizon_È.HorizonCode_Horizon_È()));
        var5.HorizonCode_Horizon_È(var6);
        Validate.notNull((Object)var6, "Invalid category", new Object[0]);
        if (var4.has("sounds")) {
            final JsonArray var7 = JsonUtils.ÂµÈ(var4, "sounds");
            for (int var8 = 0; var8 < var7.size(); ++var8) {
                final JsonElement var9 = var7.get(var8);
                final SoundList.HorizonCode_Horizon_È var10 = new SoundList.HorizonCode_Horizon_È();
                if (JsonUtils.HorizonCode_Horizon_È(var9)) {
                    var10.HorizonCode_Horizon_È(JsonUtils.HorizonCode_Horizon_È(var9, "sound"));
                }
                else {
                    final JsonObject var11 = JsonUtils.Âµá€(var9, "sound");
                    var10.HorizonCode_Horizon_È(JsonUtils.Ó(var11, "name"));
                    if (var11.has("type")) {
                        final SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È var12 = SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È(JsonUtils.Ó(var11, "type"));
                        Validate.notNull((Object)var12, "Invalid type", new Object[0]);
                        var10.HorizonCode_Horizon_È(var12);
                    }
                    if (var11.has("volume")) {
                        final float var13 = JsonUtils.Ø(var11, "volume");
                        Validate.isTrue(var13 > 0.0f, "Invalid volume", new Object[0]);
                        var10.HorizonCode_Horizon_È(var13);
                    }
                    if (var11.has("pitch")) {
                        final float var13 = JsonUtils.Ø(var11, "pitch");
                        Validate.isTrue(var13 > 0.0f, "Invalid pitch", new Object[0]);
                        var10.Â(var13);
                    }
                    if (var11.has("weight")) {
                        final int var14 = JsonUtils.áŒŠÆ(var11, "weight");
                        Validate.isTrue(var14 > 0, "Invalid weight", new Object[0]);
                        var10.HorizonCode_Horizon_È(var14);
                    }
                    if (var11.has("stream")) {
                        var10.HorizonCode_Horizon_È(JsonUtils.à(var11, "stream"));
                    }
                }
                var5.HorizonCode_Horizon_È().add(var10);
            }
        }
        return var5;
    }
}
