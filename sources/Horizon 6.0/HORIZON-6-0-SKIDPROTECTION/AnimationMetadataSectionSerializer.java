package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.Validate;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    private static final String HorizonCode_Horizon_È = "CL_00001107";
    
    public AnimationMetadataSection HorizonCode_Horizon_È(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        final ArrayList var4 = Lists.newArrayList();
        final JsonObject var5 = JsonUtils.Âµá€(p_deserialize_1_, "metadata section");
        final int var6 = JsonUtils.HorizonCode_Horizon_È(var5, "frametime", 1);
        if (var6 != 1) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)var6, "Invalid default frame time");
        }
        if (var5.has("frames")) {
            try {
                final JsonArray var7 = JsonUtils.ÂµÈ(var5, "frames");
                for (int var8 = 0; var8 < var7.size(); ++var8) {
                    final JsonElement var9 = var7.get(var8);
                    final AnimationFrame var10 = this.HorizonCode_Horizon_È(var8, var9);
                    if (var10 != null) {
                        var4.add(var10);
                    }
                }
            }
            catch (ClassCastException var11) {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + var5.get("frames"), (Throwable)var11);
            }
        }
        final int var12 = JsonUtils.HorizonCode_Horizon_È(var5, "width", -1);
        int var8 = JsonUtils.HorizonCode_Horizon_È(var5, "height", -1);
        if (var12 != -1) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)var12, "Invalid width");
        }
        if (var8 != -1) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)var8, "Invalid height");
        }
        final boolean var13 = JsonUtils.HorizonCode_Horizon_È(var5, "interpolate", false);
        return new AnimationMetadataSection(var4, var12, var8, var6, var13);
    }
    
    private AnimationFrame HorizonCode_Horizon_È(final int p_110492_1_, final JsonElement p_110492_2_) {
        if (p_110492_2_.isJsonPrimitive()) {
            return new AnimationFrame(JsonUtils.Ø­áŒŠá(p_110492_2_, "frames[" + p_110492_1_ + "]"));
        }
        if (p_110492_2_.isJsonObject()) {
            final JsonObject var3 = JsonUtils.Âµá€(p_110492_2_, "frames[" + p_110492_1_ + "]");
            final int var4 = JsonUtils.HorizonCode_Horizon_È(var3, "time", -1);
            if (var3.has("time")) {
                Validate.inclusiveBetween(1L, 2147483647L, (long)var4, "Invalid frame time");
            }
            final int var5 = JsonUtils.áŒŠÆ(var3, "index");
            Validate.inclusiveBetween(0L, 2147483647L, (long)var5, "Invalid frame index");
            return new AnimationFrame(var5, var4);
        }
        return null;
    }
    
    public JsonElement HorizonCode_Horizon_È(final AnimationMetadataSection p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        final JsonObject var4 = new JsonObject();
        var4.addProperty("frametime", (Number)p_serialize_1_.Ø­áŒŠá());
        if (p_serialize_1_.Â() != -1) {
            var4.addProperty("width", (Number)p_serialize_1_.Â());
        }
        if (p_serialize_1_.HorizonCode_Horizon_È() != -1) {
            var4.addProperty("height", (Number)p_serialize_1_.HorizonCode_Horizon_È());
        }
        if (p_serialize_1_.Ý() > 0) {
            final JsonArray var5 = new JsonArray();
            for (int var6 = 0; var6 < p_serialize_1_.Ý(); ++var6) {
                if (p_serialize_1_.Â(var6)) {
                    final JsonObject var7 = new JsonObject();
                    var7.addProperty("index", (Number)p_serialize_1_.Ý(var6));
                    var7.addProperty("time", (Number)p_serialize_1_.HorizonCode_Horizon_È(var6));
                    var5.add((JsonElement)var7);
                }
                else {
                    var5.add((JsonElement)new JsonPrimitive((Number)p_serialize_1_.Ý(var6)));
                }
            }
            var4.add("frames", (JsonElement)var5);
        }
        return (JsonElement)var4;
    }
    
    public String HorizonCode_Horizon_È() {
        return "animation";
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.HorizonCode_Horizon_È((AnimationMetadataSection)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
}
