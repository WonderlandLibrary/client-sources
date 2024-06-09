package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class IMetadataSerializer
{
    private final IRegistry HorizonCode_Horizon_È;
    private final GsonBuilder Â;
    private Gson Ý;
    private static final String Ø­áŒŠá = "CL_00001101";
    
    public IMetadataSerializer() {
        this.HorizonCode_Horizon_È = new RegistrySimple();
        (this.Â = new GsonBuilder()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.HorizonCode_Horizon_È());
        this.Â.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.HorizonCode_Horizon_È());
        this.Â.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
    }
    
    public void HorizonCode_Horizon_È(final IMetadataSectionSerializer p_110504_1_, final Class p_110504_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_110504_1_.HorizonCode_Horizon_È(), new HorizonCode_Horizon_È(p_110504_1_, p_110504_2_, null));
        this.Â.registerTypeAdapter((Type)p_110504_2_, (Object)p_110504_1_);
        this.Ý = null;
    }
    
    public IMetadataSection HorizonCode_Horizon_È(final String p_110503_1_, final JsonObject p_110503_2_) {
        if (p_110503_1_ == null) {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        }
        if (!p_110503_2_.has(p_110503_1_)) {
            return null;
        }
        if (!p_110503_2_.get(p_110503_1_).isJsonObject()) {
            throw new IllegalArgumentException("Invalid metadata for '" + p_110503_1_ + "' - expected object, found " + p_110503_2_.get(p_110503_1_));
        }
        final HorizonCode_Horizon_È var3 = (HorizonCode_Horizon_È)this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_110503_1_);
        if (var3 == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + p_110503_1_ + "'");
        }
        return (IMetadataSection)this.HorizonCode_Horizon_È().fromJson((JsonElement)p_110503_2_.getAsJsonObject(p_110503_1_), var3.Â);
    }
    
    private Gson HorizonCode_Horizon_È() {
        if (this.Ý == null) {
            this.Ý = this.Â.create();
        }
        return this.Ý;
    }
    
    class HorizonCode_Horizon_È
    {
        final IMetadataSectionSerializer HorizonCode_Horizon_È;
        final Class Â;
        private static final String Ø­áŒŠá = "CL_00001103";
        
        private HorizonCode_Horizon_È(final IMetadataSectionSerializer p_i1305_2_, final Class p_i1305_3_) {
            this.HorizonCode_Horizon_È = p_i1305_2_;
            this.Â = p_i1305_3_;
        }
        
        HorizonCode_Horizon_È(final IMetadataSerializer metadataSerializer, final IMetadataSectionSerializer p_i1306_2_, final Class p_i1306_3_, final Object p_i1306_4_) {
            this(metadataSerializer, p_i1306_2_, p_i1306_3_);
        }
    }
}
