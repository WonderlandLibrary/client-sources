/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class CookingRecipeSerializer<T extends AbstractCookingRecipe>
implements IRecipeSerializer<T> {
    private final int cookingTime;
    private final IFactory<T> factory;

    public CookingRecipeSerializer(IFactory<T> iFactory, int n) {
        this.cookingTime = n;
        this.factory = iFactory;
    }

    @Override
    public T read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        String string = JSONUtils.getString(jsonObject, "group", "");
        JsonElement jsonElement = JSONUtils.isJsonArray(jsonObject, "ingredient") ? JSONUtils.getJsonArray(jsonObject, "ingredient") : JSONUtils.getJsonObject(jsonObject, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(jsonElement);
        String string2 = JSONUtils.getString(jsonObject, "result");
        ResourceLocation resourceLocation2 = new ResourceLocation(string2);
        ItemStack itemStack = new ItemStack(Registry.ITEM.getOptional(resourceLocation2).orElseThrow(() -> CookingRecipeSerializer.lambda$read$0(string2)));
        float f = JSONUtils.getFloat(jsonObject, "experience", 0.0f);
        int n = JSONUtils.getInt(jsonObject, "cookingtime", this.cookingTime);
        return this.factory.create(resourceLocation, string, ingredient, itemStack, f, n);
    }

    @Override
    public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        String string = packetBuffer.readString(Short.MAX_VALUE);
        Ingredient ingredient = Ingredient.read(packetBuffer);
        ItemStack itemStack = packetBuffer.readItemStack();
        float f = packetBuffer.readFloat();
        int n = packetBuffer.readVarInt();
        return this.factory.create(resourceLocation, string, ingredient, itemStack, f, n);
    }

    @Override
    public void write(PacketBuffer packetBuffer, T t) {
        packetBuffer.writeString(((AbstractCookingRecipe)t).group);
        ((AbstractCookingRecipe)t).ingredient.write(packetBuffer);
        packetBuffer.writeItemStack(((AbstractCookingRecipe)t).result);
        packetBuffer.writeFloat(((AbstractCookingRecipe)t).experience);
        packetBuffer.writeVarInt(((AbstractCookingRecipe)t).cookTime);
    }

    @Override
    public void write(PacketBuffer packetBuffer, IRecipe iRecipe) {
        this.write(packetBuffer, (T)((AbstractCookingRecipe)iRecipe));
    }

    @Override
    public IRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        return this.read(resourceLocation, packetBuffer);
    }

    @Override
    public IRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        return this.read(resourceLocation, jsonObject);
    }

    private static IllegalStateException lambda$read$0(String string) {
        return new IllegalStateException("Item: " + string + " does not exist");
    }

    static interface IFactory<T extends AbstractCookingRecipe> {
        public T create(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4, float var5, int var6);
    }
}

