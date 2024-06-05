package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.util.*;

public static class Deserializer implements JsonDeserializer
{
    public ModelBlock func_178327_a(final JsonElement p_178327_1_, final Type p_178327_2_, final JsonDeserializationContext p_178327_3_) {
        final JsonObject var4 = p_178327_1_.getAsJsonObject();
        final List var5 = this.getModelElements(p_178327_3_, var4);
        final String var6 = this.getParent(var4);
        final boolean var7 = StringUtils.isEmpty((CharSequence)var6);
        final boolean var8 = var5.isEmpty();
        if (var8 && var7) {
            throw new JsonParseException("BlockModel requires either elements or parent, found neither");
        }
        if (!var7 && !var8) {
            throw new JsonParseException("BlockModel requires either elements or parent, found both");
        }
        final Map var9 = this.getTextures(var4);
        final boolean var10 = this.getAmbientOcclusionEnabled(var4);
        ItemCameraTransforms var11 = ItemCameraTransforms.field_178357_a;
        if (var4.has("display")) {
            final JsonObject var12 = JsonUtils.getJsonObject(var4, "display");
            var11 = (ItemCameraTransforms)p_178327_3_.deserialize((JsonElement)var12, (Type)ItemCameraTransforms.class);
        }
        return var8 ? new ModelBlock(new ResourceLocation(var6), var9, var10, true, var11) : new ModelBlock(var5, var9, var10, true, var11);
    }
    
    private Map getTextures(final JsonObject p_178329_1_) {
        final HashMap var2 = Maps.newHashMap();
        if (p_178329_1_.has("textures")) {
            final JsonObject var3 = p_178329_1_.getAsJsonObject("textures");
            for (final Map.Entry var5 : var3.entrySet()) {
                var2.put(var5.getKey(), var5.getValue().getAsString());
            }
        }
        return var2;
    }
    
    private String getParent(final JsonObject p_178326_1_) {
        return JsonUtils.getJsonObjectStringFieldValueOrDefault(p_178326_1_, "parent", "");
    }
    
    protected boolean getAmbientOcclusionEnabled(final JsonObject p_178328_1_) {
        return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(p_178328_1_, "ambientocclusion", true);
    }
    
    protected List getModelElements(final JsonDeserializationContext p_178325_1_, final JsonObject p_178325_2_) {
        final ArrayList var3 = Lists.newArrayList();
        if (p_178325_2_.has("elements")) {
            for (final JsonElement var5 : JsonUtils.getJsonObjectJsonArrayField(p_178325_2_, "elements")) {
                var3.add(p_178325_1_.deserialize(var5, (Type)BlockPart.class));
            }
        }
        return var3;
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_178327_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
