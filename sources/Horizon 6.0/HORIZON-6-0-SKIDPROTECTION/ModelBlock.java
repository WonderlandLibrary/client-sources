package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.HashMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import java.util.Iterator;
import java.util.Collections;
import java.io.StringReader;
import java.io.Reader;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;

public class ModelBlock
{
    private static final Logger Ó;
    static final Gson HorizonCode_Horizon_È;
    private final List à;
    private final boolean Ø;
    private final boolean áŒŠÆ;
    private ItemCameraTransforms áˆºÑ¢Õ;
    public String Â;
    protected final Map Ý;
    protected ModelBlock Ø­áŒŠá;
    protected ResourceLocation_1975012498 Âµá€;
    private static final String ÂµÈ = "CL_00002503";
    
    static {
        Ó = LogManager.getLogger();
        HorizonCode_Horizon_È = new GsonBuilder().registerTypeAdapter((Type)ModelBlock.class, (Object)new Â()).registerTypeAdapter((Type)BlockPart.class, (Object)new BlockPart.HorizonCode_Horizon_È()).registerTypeAdapter((Type)BlockPartFace.class, (Object)new BlockPartFace.HorizonCode_Horizon_È()).registerTypeAdapter((Type)BlockFaceUV.class, (Object)new BlockFaceUV.HorizonCode_Horizon_È()).registerTypeAdapter((Type)ItemTransformVec3f.class, (Object)new ItemTransformVec3f.HorizonCode_Horizon_È()).registerTypeAdapter((Type)ItemCameraTransforms.class, (Object)new ItemCameraTransforms.HorizonCode_Horizon_È()).create();
    }
    
    public static ModelBlock HorizonCode_Horizon_È(final Reader p_178307_0_) {
        return (ModelBlock)ModelBlock.HorizonCode_Horizon_È.fromJson(p_178307_0_, (Class)ModelBlock.class);
    }
    
    public static ModelBlock HorizonCode_Horizon_È(final String p_178294_0_) {
        return HorizonCode_Horizon_È(new StringReader(p_178294_0_));
    }
    
    protected ModelBlock(final List p_i46225_1_, final Map p_i46225_2_, final boolean p_i46225_3_, final boolean p_i46225_4_, final ItemCameraTransforms p_i46225_5_) {
        this(null, p_i46225_1_, p_i46225_2_, p_i46225_3_, p_i46225_4_, p_i46225_5_);
    }
    
    protected ModelBlock(final ResourceLocation_1975012498 p_i46226_1_, final Map p_i46226_2_, final boolean p_i46226_3_, final boolean p_i46226_4_, final ItemCameraTransforms p_i46226_5_) {
        this(p_i46226_1_, Collections.emptyList(), p_i46226_2_, p_i46226_3_, p_i46226_4_, p_i46226_5_);
    }
    
    private ModelBlock(final ResourceLocation_1975012498 p_i46227_1_, final List p_i46227_2_, final Map p_i46227_3_, final boolean p_i46227_4_, final boolean p_i46227_5_, final ItemCameraTransforms p_i46227_6_) {
        this.Â = "";
        this.à = p_i46227_2_;
        this.áŒŠÆ = p_i46227_4_;
        this.Ø = p_i46227_5_;
        this.Ý = p_i46227_3_;
        this.Âµá€ = p_i46227_1_;
        this.áˆºÑ¢Õ = p_i46227_6_;
    }
    
    public List HorizonCode_Horizon_È() {
        return this.ÂµÈ() ? this.Ø­áŒŠá.HorizonCode_Horizon_È() : this.à;
    }
    
    private boolean ÂµÈ() {
        return this.Ø­áŒŠá != null;
    }
    
    public boolean Â() {
        return this.ÂµÈ() ? this.Ø­áŒŠá.Â() : this.áŒŠÆ;
    }
    
    public boolean Ý() {
        return this.Ø;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Âµá€ == null || (this.Ø­áŒŠá != null && this.Ø­áŒŠá.Ø­áŒŠá());
    }
    
    public void HorizonCode_Horizon_È(final Map p_178299_1_) {
        if (this.Âµá€ != null) {
            this.Ø­áŒŠá = p_178299_1_.get(this.Âµá€);
        }
    }
    
    public boolean Â(final String p_178300_1_) {
        return !"missingno".equals(this.Ý(p_178300_1_));
    }
    
    public String Ý(String p_178308_1_) {
        if (!this.Ø­áŒŠá(p_178308_1_)) {
            p_178308_1_ = String.valueOf('#') + p_178308_1_;
        }
        return this.HorizonCode_Horizon_È(p_178308_1_, new HorizonCode_Horizon_È(null));
    }
    
    private String HorizonCode_Horizon_È(final String p_178302_1_, final HorizonCode_Horizon_È p_178302_2_) {
        if (!this.Ø­áŒŠá(p_178302_1_)) {
            return p_178302_1_;
        }
        if (this == p_178302_2_.Â) {
            ModelBlock.Ó.warn("Unable to resolve texture due to upward reference: " + p_178302_1_ + " in " + this.Â);
            return "missingno";
        }
        String var3 = this.Ý.get(p_178302_1_.substring(1));
        if (var3 == null && this.ÂµÈ()) {
            var3 = this.Ø­áŒŠá.HorizonCode_Horizon_È(p_178302_1_, p_178302_2_);
        }
        p_178302_2_.Â = this;
        if (var3 != null && this.Ø­áŒŠá(var3)) {
            var3 = p_178302_2_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3, p_178302_2_);
        }
        return (var3 != null && !this.Ø­áŒŠá(var3)) ? var3 : "missingno";
    }
    
    private boolean Ø­áŒŠá(final String p_178304_1_) {
        return p_178304_1_.charAt(0) == '#';
    }
    
    public ResourceLocation_1975012498 Âµá€() {
        return this.Âµá€;
    }
    
    public ModelBlock Ó() {
        return this.ÂµÈ() ? this.Ø­áŒŠá.Ó() : this;
    }
    
    public ItemTransformVec3f à() {
        return (this.Ø­áŒŠá != null && this.áˆºÑ¢Õ.Â == ItemTransformVec3f.HorizonCode_Horizon_È) ? this.Ø­áŒŠá.à() : this.áˆºÑ¢Õ.Â;
    }
    
    public ItemTransformVec3f Ø() {
        return (this.Ø­áŒŠá != null && this.áˆºÑ¢Õ.Ý == ItemTransformVec3f.HorizonCode_Horizon_È) ? this.Ø­áŒŠá.Ø() : this.áˆºÑ¢Õ.Ý;
    }
    
    public ItemTransformVec3f áŒŠÆ() {
        return (this.Ø­áŒŠá != null && this.áˆºÑ¢Õ.Ø­áŒŠá == ItemTransformVec3f.HorizonCode_Horizon_È) ? this.Ø­áŒŠá.áŒŠÆ() : this.áˆºÑ¢Õ.Ø­áŒŠá;
    }
    
    public ItemTransformVec3f áˆºÑ¢Õ() {
        return (this.Ø­áŒŠá != null && this.áˆºÑ¢Õ.Âµá€ == ItemTransformVec3f.HorizonCode_Horizon_È) ? this.Ø­áŒŠá.áˆºÑ¢Õ() : this.áˆºÑ¢Õ.Âµá€;
    }
    
    public static void Â(final Map p_178312_0_) {
        for (final ModelBlock var2 : p_178312_0_.values()) {
            try {
                for (ModelBlock var3 = var2.Ø­áŒŠá, var4 = var3.Ø­áŒŠá; var3 != var4; var3 = var3.Ø­áŒŠá, var4 = var4.Ø­áŒŠá.Ø­áŒŠá) {}
                throw new Ý();
            }
            catch (NullPointerException ex) {}
        }
    }
    
    final class HorizonCode_Horizon_È
    {
        public final ModelBlock HorizonCode_Horizon_È;
        public ModelBlock Â;
        private static final String Ø­áŒŠá = "CL_00002501";
        
        private HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = ModelBlock.this;
        }
        
        HorizonCode_Horizon_È(final ModelBlock modelBlock, final Object p_i46224_2_) {
            this(modelBlock);
        }
    }
    
    public static class Â implements JsonDeserializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00002500";
        
        public ModelBlock HorizonCode_Horizon_È(final JsonElement p_178327_1_, final Type p_178327_2_, final JsonDeserializationContext p_178327_3_) {
            final JsonObject var4 = p_178327_1_.getAsJsonObject();
            final List var5 = this.HorizonCode_Horizon_È(p_178327_3_, var4);
            final String var6 = this.Ý(var4);
            final boolean var7 = StringUtils.isEmpty((CharSequence)var6);
            final boolean var8 = var5.isEmpty();
            if (var8 && var7) {
                throw new JsonParseException("BlockModel requires either elements or parent, found neither");
            }
            if (!var7 && !var8) {
                throw new JsonParseException("BlockModel requires either elements or parent, found both");
            }
            final Map var9 = this.Â(var4);
            final boolean var10 = this.HorizonCode_Horizon_È(var4);
            ItemCameraTransforms var11 = ItemCameraTransforms.HorizonCode_Horizon_È;
            if (var4.has("display")) {
                final JsonObject var12 = JsonUtils.áˆºÑ¢Õ(var4, "display");
                var11 = (ItemCameraTransforms)p_178327_3_.deserialize((JsonElement)var12, (Type)ItemCameraTransforms.class);
            }
            return var8 ? new ModelBlock(new ResourceLocation_1975012498(var6), var9, var10, true, var11) : new ModelBlock(var5, var9, var10, true, var11);
        }
        
        private Map Â(final JsonObject p_178329_1_) {
            final HashMap var2 = Maps.newHashMap();
            if (p_178329_1_.has("textures")) {
                final JsonObject var3 = p_178329_1_.getAsJsonObject("textures");
                for (final Map.Entry var5 : var3.entrySet()) {
                    var2.put(var5.getKey(), var5.getValue().getAsString());
                }
            }
            return var2;
        }
        
        private String Ý(final JsonObject p_178326_1_) {
            return JsonUtils.HorizonCode_Horizon_È(p_178326_1_, "parent", "");
        }
        
        protected boolean HorizonCode_Horizon_È(final JsonObject p_178328_1_) {
            return JsonUtils.HorizonCode_Horizon_È(p_178328_1_, "ambientocclusion", true);
        }
        
        protected List HorizonCode_Horizon_È(final JsonDeserializationContext p_178325_1_, final JsonObject p_178325_2_) {
            final ArrayList var3 = Lists.newArrayList();
            if (p_178325_2_.has("elements")) {
                for (final JsonElement var5 : JsonUtils.ÂµÈ(p_178325_2_, "elements")) {
                    var3.add(p_178325_1_.deserialize(var5, (Type)BlockPart.class));
                }
            }
            return var3;
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.HorizonCode_Horizon_È(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
    
    public static class Ý extends RuntimeException
    {
        private static final String HorizonCode_Horizon_È = "CL_00002499";
    }
}
