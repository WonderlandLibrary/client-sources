package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import com.google.gson.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public static class Deserializer implements JsonDeserializer
{
    public ModelBlockDefinition func_178336_a(final JsonElement p_178336_1_, final Type p_178336_2_, final JsonDeserializationContext p_178336_3_) {
        final JsonObject var4 = p_178336_1_.getAsJsonObject();
        final List var5 = this.func_178334_a(p_178336_3_, var4);
        return new ModelBlockDefinition((Collection)var5);
    }
    
    protected List func_178334_a(final JsonDeserializationContext p_178334_1_, final JsonObject p_178334_2_) {
        final JsonObject var3 = JsonUtils.getJsonObject(p_178334_2_, "variants");
        final ArrayList var4 = Lists.newArrayList();
        for (final Map.Entry var6 : var3.entrySet()) {
            var4.add(this.func_178335_a(p_178334_1_, var6));
        }
        return var4;
    }
    
    protected Variants func_178335_a(final JsonDeserializationContext p_178335_1_, final Map.Entry p_178335_2_) {
        final String var3 = p_178335_2_.getKey();
        final ArrayList var4 = Lists.newArrayList();
        final JsonElement var5 = (JsonElement)p_178335_2_.getValue();
        if (var5.isJsonArray()) {
            for (final JsonElement var7 : var5.getAsJsonArray()) {
                var4.add(p_178335_1_.deserialize(var7, (Type)Variant.class));
            }
        }
        else {
            var4.add(p_178335_1_.deserialize(var5, (Type)Variant.class));
        }
        return new Variants(var3, var4);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_178336_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
