package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;

public class BlockPartFace
{
    public static final EnumFacing HorizonCode_Horizon_È;
    public final EnumFacing Â;
    public final int Ý;
    public final String Ø­áŒŠá;
    public final BlockFaceUV Âµá€;
    private static final String Ó = "CL_00002508";
    
    static {
        HorizonCode_Horizon_È = null;
    }
    
    public BlockPartFace(final EnumFacing p_i46230_1_, final int p_i46230_2_, final String p_i46230_3_, final BlockFaceUV p_i46230_4_) {
        this.Â = p_i46230_1_;
        this.Ý = p_i46230_2_;
        this.Ø­áŒŠá = p_i46230_3_;
        this.Âµá€ = p_i46230_4_;
    }
    
    static class HorizonCode_Horizon_È implements JsonDeserializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002507";
        
        public BlockPartFace HorizonCode_Horizon_È(final JsonElement p_178338_1_, final Type p_178338_2_, final JsonDeserializationContext p_178338_3_) {
            final JsonObject var4 = p_178338_1_.getAsJsonObject();
            final EnumFacing var5 = this.Ý(var4);
            final int var6 = this.HorizonCode_Horizon_È(var4);
            final String var7 = this.Â(var4);
            final BlockFaceUV var8 = (BlockFaceUV)p_178338_3_.deserialize((JsonElement)var4, (Type)BlockFaceUV.class);
            return new BlockPartFace(var5, var6, var7, var8);
        }
        
        protected int HorizonCode_Horizon_È(final JsonObject p_178337_1_) {
            return JsonUtils.HorizonCode_Horizon_È(p_178337_1_, "tintindex", -1);
        }
        
        private String Â(final JsonObject p_178340_1_) {
            return JsonUtils.Ó(p_178340_1_, "texture");
        }
        
        private EnumFacing Ý(final JsonObject p_178339_1_) {
            final String var2 = JsonUtils.HorizonCode_Horizon_È(p_178339_1_, "cullface", "");
            return EnumFacing.HorizonCode_Horizon_È(var2);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
}
