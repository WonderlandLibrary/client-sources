package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;

public class BlockFaceUV
{
    public float[] HorizonCode_Horizon_È;
    public final int Â;
    private static final String Ý = "CL_00002505";
    
    public BlockFaceUV(final float[] p_i46228_1_, final int p_i46228_2_) {
        this.HorizonCode_Horizon_È = p_i46228_1_;
        this.Â = p_i46228_2_;
    }
    
    public float HorizonCode_Horizon_È(final int p_178348_1_) {
        if (this.HorizonCode_Horizon_È == null) {
            throw new NullPointerException("uvs");
        }
        final int var2 = this.Ø­áŒŠá(p_178348_1_);
        return (var2 != 0 && var2 != 1) ? this.HorizonCode_Horizon_È[2] : this.HorizonCode_Horizon_È[0];
    }
    
    public float Â(final int p_178346_1_) {
        if (this.HorizonCode_Horizon_È == null) {
            throw new NullPointerException("uvs");
        }
        final int var2 = this.Ø­áŒŠá(p_178346_1_);
        return (var2 != 0 && var2 != 3) ? this.HorizonCode_Horizon_È[3] : this.HorizonCode_Horizon_È[1];
    }
    
    private int Ø­áŒŠá(final int p_178347_1_) {
        return (p_178347_1_ + this.Â / 90) % 4;
    }
    
    public int Ý(final int p_178345_1_) {
        return (p_178345_1_ + (4 - this.Â / 90)) % 4;
    }
    
    public void HorizonCode_Horizon_È(final float[] p_178349_1_) {
        if (this.HorizonCode_Horizon_È == null) {
            this.HorizonCode_Horizon_È = p_178349_1_;
        }
    }
    
    static class HorizonCode_Horizon_È implements JsonDeserializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002504";
        
        public BlockFaceUV HorizonCode_Horizon_È(final JsonElement p_178293_1_, final Type p_178293_2_, final JsonDeserializationContext p_178293_3_) {
            final JsonObject var4 = p_178293_1_.getAsJsonObject();
            final float[] var5 = this.Â(var4);
            final int var6 = this.HorizonCode_Horizon_È(var4);
            return new BlockFaceUV(var5, var6);
        }
        
        protected int HorizonCode_Horizon_È(final JsonObject p_178291_1_) {
            final int var2 = JsonUtils.HorizonCode_Horizon_È(p_178291_1_, "rotation", 0);
            if (var2 >= 0 && var2 % 90 == 0 && var2 / 90 <= 3) {
                return var2;
            }
            throw new JsonParseException("Invalid rotation " + var2 + " found, only 0/90/180/270 allowed");
        }
        
        private float[] Â(final JsonObject p_178292_1_) {
            if (!p_178292_1_.has("uv")) {
                return null;
            }
            final JsonArray var2 = JsonUtils.ÂµÈ(p_178292_1_, "uv");
            if (var2.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + var2.size());
            }
            final float[] var3 = new float[4];
            for (int var4 = 0; var4 < var3.length; ++var4) {
                var3[var4] = JsonUtils.Ý(var2.get(var4), "uv[" + var4 + "]");
            }
            return var3;
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
}
