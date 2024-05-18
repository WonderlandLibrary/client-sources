package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;

public class ItemCameraTransforms
{
    public static final ItemCameraTransforms HorizonCode_Horizon_È;
    public final ItemTransformVec3f Â;
    public final ItemTransformVec3f Ý;
    public final ItemTransformVec3f Ø­áŒŠá;
    public final ItemTransformVec3f Âµá€;
    private static final String Ó = "CL_00002482";
    
    static {
        HorizonCode_Horizon_È = new ItemCameraTransforms(ItemTransformVec3f.HorizonCode_Horizon_È, ItemTransformVec3f.HorizonCode_Horizon_È, ItemTransformVec3f.HorizonCode_Horizon_È, ItemTransformVec3f.HorizonCode_Horizon_È);
    }
    
    public ItemCameraTransforms(final ItemTransformVec3f p_i46213_1_, final ItemTransformVec3f p_i46213_2_, final ItemTransformVec3f p_i46213_3_, final ItemTransformVec3f p_i46213_4_) {
        this.Â = p_i46213_1_;
        this.Ý = p_i46213_2_;
        this.Ø­áŒŠá = p_i46213_3_;
        this.Âµá€ = p_i46213_4_;
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("NONE", 0, "NONE", 0), 
        Â("THIRD_PERSON", 1, "THIRD_PERSON", 1), 
        Ý("FIRST_PERSON", 2, "FIRST_PERSON", 2), 
        Ø­áŒŠá("HEAD", 3, "HEAD", 3), 
        Âµá€("GUI", 4, "GUI", 4);
        
        private static final Â[] Ó;
        private static final String à = "CL_00002480";
        
        static {
            Ø = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€ };
            Ó = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€ };
        }
        
        private Â(final String s, final int n, final String p_i46212_1_, final int p_i46212_2_) {
        }
    }
    
    static class HorizonCode_Horizon_È implements JsonDeserializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002481";
        
        public ItemCameraTransforms HorizonCode_Horizon_È(final JsonElement p_178352_1_, final Type p_178352_2_, final JsonDeserializationContext p_178352_3_) {
            final JsonObject var4 = p_178352_1_.getAsJsonObject();
            ItemTransformVec3f var5 = ItemTransformVec3f.HorizonCode_Horizon_È;
            ItemTransformVec3f var6 = ItemTransformVec3f.HorizonCode_Horizon_È;
            ItemTransformVec3f var7 = ItemTransformVec3f.HorizonCode_Horizon_È;
            ItemTransformVec3f var8 = ItemTransformVec3f.HorizonCode_Horizon_È;
            if (var4.has("thirdperson")) {
                var5 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("thirdperson"), (Type)ItemTransformVec3f.class);
            }
            if (var4.has("firstperson")) {
                var6 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("firstperson"), (Type)ItemTransformVec3f.class);
            }
            if (var4.has("head")) {
                var7 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("head"), (Type)ItemTransformVec3f.class);
            }
            if (var4.has("gui")) {
                var8 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("gui"), (Type)ItemTransformVec3f.class);
            }
            return new ItemCameraTransforms(var5, var6, var7, var8);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
}
