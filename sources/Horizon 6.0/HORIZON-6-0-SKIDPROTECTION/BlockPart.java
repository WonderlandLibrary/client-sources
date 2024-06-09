package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonArray;
import java.util.EnumMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import java.util.Iterator;
import java.util.Map;
import javax.vecmath.Vector3f;

public class BlockPart
{
    public final Vector3f HorizonCode_Horizon_È;
    public final Vector3f Â;
    public final Map Ý;
    public final BlockPartRotation Ø­áŒŠá;
    public final boolean Âµá€;
    private static final String Ó = "CL_00002511";
    
    public BlockPart(final Vector3f p_i46231_1_, final Vector3f p_i46231_2_, final Map p_i46231_3_, final BlockPartRotation p_i46231_4_, final boolean p_i46231_5_) {
        this.HorizonCode_Horizon_È = p_i46231_1_;
        this.Â = p_i46231_2_;
        this.Ý = p_i46231_3_;
        this.Ø­áŒŠá = p_i46231_4_;
        this.Âµá€ = p_i46231_5_;
        this.HorizonCode_Horizon_È();
    }
    
    private void HorizonCode_Horizon_È() {
        for (final Map.Entry var2 : this.Ý.entrySet()) {
            final float[] var3 = this.HorizonCode_Horizon_È(var2.getKey());
            var2.getValue().Âµá€.HorizonCode_Horizon_È(var3);
        }
    }
    
    private float[] HorizonCode_Horizon_È(final EnumFacing p_178236_1_) {
        float[] var2 = null;
        switch (BlockPart.Â.HorizonCode_Horizon_È[p_178236_1_.ordinal()]) {
            case 1:
            case 2: {
                var2 = new float[] { this.HorizonCode_Horizon_È.x, this.HorizonCode_Horizon_È.z, this.Â.x, this.Â.z };
                break;
            }
            case 3:
            case 4: {
                var2 = new float[] { this.HorizonCode_Horizon_È.x, 16.0f - this.Â.y, this.Â.x, 16.0f - this.HorizonCode_Horizon_È.y };
                break;
            }
            case 5:
            case 6: {
                var2 = new float[] { this.HorizonCode_Horizon_È.z, 16.0f - this.Â.y, this.Â.z, 16.0f - this.HorizonCode_Horizon_È.y };
                break;
            }
            default: {
                throw new NullPointerException();
            }
        }
        return var2;
    }
    
    static class HorizonCode_Horizon_È implements JsonDeserializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002509";
        
        public BlockPart HorizonCode_Horizon_È(final JsonElement p_178254_1_, final Type p_178254_2_, final JsonDeserializationContext p_178254_3_) {
            final JsonObject var4 = p_178254_1_.getAsJsonObject();
            final Vector3f var5 = this.Âµá€(var4);
            final Vector3f var6 = this.Ø­áŒŠá(var4);
            final BlockPartRotation var7 = this.HorizonCode_Horizon_È(var4);
            final Map var8 = this.HorizonCode_Horizon_È(p_178254_3_, var4);
            if (var4.has("shade") && !JsonUtils.Â(var4, "shade")) {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            final boolean var9 = JsonUtils.HorizonCode_Horizon_È(var4, "shade", true);
            return new BlockPart(var5, var6, var8, var7, var9);
        }
        
        private BlockPartRotation HorizonCode_Horizon_È(final JsonObject p_178256_1_) {
            BlockPartRotation var2 = null;
            if (p_178256_1_.has("rotation")) {
                final JsonObject var3 = JsonUtils.áˆºÑ¢Õ(p_178256_1_, "rotation");
                final Vector3f var4 = this.HorizonCode_Horizon_È(var3, "origin");
                var4.scale(0.0625f);
                final EnumFacing.HorizonCode_Horizon_È var5 = this.Ý(var3);
                final float var6 = this.Â(var3);
                final boolean var7 = JsonUtils.HorizonCode_Horizon_È(var3, "rescale", false);
                var2 = new BlockPartRotation(var4, var5, var6, var7);
            }
            return var2;
        }
        
        private float Â(final JsonObject p_178255_1_) {
            final float var2 = JsonUtils.Ø(p_178255_1_, "angle");
            if (var2 != 0.0f && MathHelper.Âµá€(var2) != 22.5f && MathHelper.Âµá€(var2) != 45.0f) {
                throw new JsonParseException("Invalid rotation " + var2 + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            return var2;
        }
        
        private EnumFacing.HorizonCode_Horizon_È Ý(final JsonObject p_178252_1_) {
            final String var2 = JsonUtils.Ó(p_178252_1_, "axis");
            final EnumFacing.HorizonCode_Horizon_È var3 = EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2.toLowerCase());
            if (var3 == null) {
                throw new JsonParseException("Invalid rotation axis: " + var2);
            }
            return var3;
        }
        
        private Map HorizonCode_Horizon_È(final JsonDeserializationContext p_178250_1_, final JsonObject p_178250_2_) {
            final Map var3 = this.Â(p_178250_1_, p_178250_2_);
            if (var3.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return var3;
        }
        
        private Map Â(final JsonDeserializationContext p_178253_1_, final JsonObject p_178253_2_) {
            final EnumMap var3 = Maps.newEnumMap((Class)EnumFacing.class);
            final JsonObject var4 = JsonUtils.áˆºÑ¢Õ(p_178253_2_, "faces");
            for (final Map.Entry var6 : var4.entrySet()) {
                final EnumFacing var7 = this.HorizonCode_Horizon_È(var6.getKey());
                var3.put(var7, p_178253_1_.deserialize((JsonElement)var6.getValue(), (Type)BlockPartFace.class));
            }
            return var3;
        }
        
        private EnumFacing HorizonCode_Horizon_È(final String p_178248_1_) {
            final EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(p_178248_1_);
            if (var2 == null) {
                throw new JsonParseException("Unknown facing: " + p_178248_1_);
            }
            return var2;
        }
        
        private Vector3f Ø­áŒŠá(final JsonObject p_178247_1_) {
            final Vector3f var2 = this.HorizonCode_Horizon_È(p_178247_1_, "to");
            if (var2.x >= -16.0f && var2.y >= -16.0f && var2.z >= -16.0f && var2.x <= 32.0f && var2.y <= 32.0f && var2.z <= 32.0f) {
                return var2;
            }
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + var2);
        }
        
        private Vector3f Âµá€(final JsonObject p_178249_1_) {
            final Vector3f var2 = this.HorizonCode_Horizon_È(p_178249_1_, "from");
            if (var2.x >= -16.0f && var2.y >= -16.0f && var2.z >= -16.0f && var2.x <= 32.0f && var2.y <= 32.0f && var2.z <= 32.0f) {
                return var2;
            }
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + var2);
        }
        
        private Vector3f HorizonCode_Horizon_È(final JsonObject p_178251_1_, final String p_178251_2_) {
            final JsonArray var3 = JsonUtils.ÂµÈ(p_178251_1_, p_178251_2_);
            if (var3.size() != 3) {
                throw new JsonParseException("Expected 3 " + p_178251_2_ + " values, found: " + var3.size());
            }
            final float[] var4 = new float[3];
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var4[var5] = JsonUtils.Ý(var3.get(var5), String.valueOf(p_178251_2_) + "[" + var5 + "]");
            }
            return new Vector3f(var4);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002510";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockPart.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockPart.Â.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockPart.Â.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockPart.Â.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockPart.Â.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockPart.Â.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
