package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import javax.vecmath.Vector3f;

public class ItemTransformVec3f
{
    public static final ItemTransformVec3f HorizonCode_Horizon_È;
    public final Vector3f Â;
    public final Vector3f Ý;
    public final Vector3f Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002484";
    
    static {
        HorizonCode_Horizon_È = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    public ItemTransformVec3f(final Vector3f p_i46214_1_, final Vector3f p_i46214_2_, final Vector3f p_i46214_3_) {
        this.Â = new Vector3f(p_i46214_1_);
        this.Ý = new Vector3f(p_i46214_2_);
        this.Ø­áŒŠá = new Vector3f(p_i46214_3_);
    }
    
    static class HorizonCode_Horizon_È implements JsonDeserializer
    {
        private static final Vector3f HorizonCode_Horizon_È;
        private static final Vector3f Â;
        private static final Vector3f Ý;
        private static final String Ø­áŒŠá = "CL_00002483";
        
        static {
            HorizonCode_Horizon_È = new Vector3f(0.0f, 0.0f, 0.0f);
            Â = new Vector3f(0.0f, 0.0f, 0.0f);
            Ý = new Vector3f(1.0f, 1.0f, 1.0f);
        }
        
        public ItemTransformVec3f HorizonCode_Horizon_È(final JsonElement p_178359_1_, final Type p_178359_2_, final JsonDeserializationContext p_178359_3_) {
            final JsonObject var4 = p_178359_1_.getAsJsonObject();
            final Vector3f var5 = this.HorizonCode_Horizon_È(var4, "rotation", ItemTransformVec3f.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            final Vector3f var6 = this.HorizonCode_Horizon_È(var4, "translation", ItemTransformVec3f.HorizonCode_Horizon_È.Â);
            var6.scale(0.0625f);
            MathHelper.HorizonCode_Horizon_È(var6.x, -1.5, 1.5);
            MathHelper.HorizonCode_Horizon_È(var6.y, -1.5, 1.5);
            MathHelper.HorizonCode_Horizon_È(var6.z, -1.5, 1.5);
            final Vector3f var7 = this.HorizonCode_Horizon_È(var4, "scale", ItemTransformVec3f.HorizonCode_Horizon_È.Ý);
            MathHelper.HorizonCode_Horizon_È(var7.x, -1.5, 1.5);
            MathHelper.HorizonCode_Horizon_È(var7.y, -1.5, 1.5);
            MathHelper.HorizonCode_Horizon_È(var7.z, -1.5, 1.5);
            return new ItemTransformVec3f(var5, var6, var7);
        }
        
        private Vector3f HorizonCode_Horizon_È(final JsonObject p_178358_1_, final String p_178358_2_, final Vector3f p_178358_3_) {
            if (!p_178358_1_.has(p_178358_2_)) {
                return p_178358_3_;
            }
            final JsonArray var4 = JsonUtils.ÂµÈ(p_178358_1_, p_178358_2_);
            if (var4.size() != 3) {
                throw new JsonParseException("Expected 3 " + p_178358_2_ + " values, found: " + var4.size());
            }
            final float[] var5 = new float[3];
            for (int var6 = 0; var6 < var5.length; ++var6) {
                var5[var6] = JsonUtils.Ý(var4.get(var6), String.valueOf(p_178358_2_) + "[" + var6 + "]");
            }
            return new Vector3f(var5);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
}
