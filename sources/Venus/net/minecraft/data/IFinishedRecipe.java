/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IFinishedRecipe {
    public void serialize(JsonObject var1);

    default public JsonObject getRecipeJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", Registry.RECIPE_SERIALIZER.getKey(this.getSerializer()).toString());
        this.serialize(jsonObject);
        return jsonObject;
    }

    public ResourceLocation getID();

    public IRecipeSerializer<?> getSerializer();

    @Nullable
    public JsonObject getAdvancementJson();

    @Nullable
    public ResourceLocation getAdvancementID();
}

