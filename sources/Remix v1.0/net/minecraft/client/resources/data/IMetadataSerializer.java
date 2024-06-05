package net.minecraft.client.resources.data;

import net.minecraft.util.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class IMetadataSerializer
{
    private final IRegistry metadataSectionSerializerRegistry;
    private final GsonBuilder gsonBuilder;
    private Gson gson;
    
    public IMetadataSerializer() {
        this.metadataSectionSerializerRegistry = new RegistrySimple();
        (this.gsonBuilder = new GsonBuilder()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.Serializer());
        this.gsonBuilder.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer());
        this.gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
    }
    
    public void registerMetadataSectionType(final IMetadataSectionSerializer p_110504_1_, final Class p_110504_2_) {
        this.metadataSectionSerializerRegistry.putObject(p_110504_1_.getSectionName(), new Registration(p_110504_1_, p_110504_2_, null));
        this.gsonBuilder.registerTypeAdapter((Type)p_110504_2_, (Object)p_110504_1_);
        this.gson = null;
    }
    
    public IMetadataSection parseMetadataSection(final String p_110503_1_, final JsonObject p_110503_2_) {
        if (p_110503_1_ == null) {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        }
        if (!p_110503_2_.has(p_110503_1_)) {
            return null;
        }
        if (!p_110503_2_.get(p_110503_1_).isJsonObject()) {
            throw new IllegalArgumentException("Invalid metadata for '" + p_110503_1_ + "' - expected object, found " + p_110503_2_.get(p_110503_1_));
        }
        final Registration var3 = (Registration)this.metadataSectionSerializerRegistry.getObject(p_110503_1_);
        if (var3 == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + p_110503_1_ + "'");
        }
        return (IMetadataSection)this.getGson().fromJson((JsonElement)p_110503_2_.getAsJsonObject(p_110503_1_), var3.field_110500_b);
    }
    
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }
        return this.gson;
    }
    
    class Registration
    {
        final IMetadataSectionSerializer field_110502_a;
        final Class field_110500_b;
        
        private Registration(final IMetadataSectionSerializer p_i1305_2_, final Class p_i1305_3_) {
            this.field_110502_a = p_i1305_2_;
            this.field_110500_b = p_i1305_3_;
        }
        
        Registration(final IMetadataSerializer this$0, final IMetadataSectionSerializer p_i1306_2_, final Class p_i1306_3_, final Object p_i1306_4_) {
            this(this$0, p_i1306_2_, p_i1306_3_);
        }
    }
}
