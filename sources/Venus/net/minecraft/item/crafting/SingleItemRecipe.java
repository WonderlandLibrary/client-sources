/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class SingleItemRecipe
implements IRecipe<IInventory> {
    protected final Ingredient ingredient;
    protected final ItemStack result;
    private final IRecipeType<?> type;
    private final IRecipeSerializer<?> serializer;
    protected final ResourceLocation id;
    protected final String group;

    public SingleItemRecipe(IRecipeType<?> iRecipeType, IRecipeSerializer<?> iRecipeSerializer, ResourceLocation resourceLocation, String string, Ingredient ingredient, ItemStack itemStack) {
        this.type = iRecipeType;
        this.serializer = iRecipeSerializer;
        this.id = resourceLocation;
        this.group = string;
        this.ingredient = ingredient;
        this.result = itemStack;
    }

    @Override
    public IRecipeType<?> getType() {
        return this.type;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.result.copy();
    }

    public static class Serializer<T extends SingleItemRecipe>
    implements IRecipeSerializer<T> {
        final IRecipeFactory<T> factory;

        protected Serializer(IRecipeFactory<T> iRecipeFactory) {
            this.factory = iRecipeFactory;
        }

        @Override
        public T read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            String string = JSONUtils.getString(jsonObject, "group", "");
            Ingredient ingredient = JSONUtils.isJsonArray(jsonObject, "ingredient") ? Ingredient.deserialize(JSONUtils.getJsonArray(jsonObject, "ingredient")) : Ingredient.deserialize(JSONUtils.getJsonObject(jsonObject, "ingredient"));
            String string2 = JSONUtils.getString(jsonObject, "result");
            int n = JSONUtils.getInt(jsonObject, "count");
            ItemStack itemStack = new ItemStack(Registry.ITEM.getOrDefault(new ResourceLocation(string2)), n);
            return this.factory.create(resourceLocation, string, ingredient, itemStack);
        }

        @Override
        public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            String string = packetBuffer.readString(Short.MAX_VALUE);
            Ingredient ingredient = Ingredient.read(packetBuffer);
            ItemStack itemStack = packetBuffer.readItemStack();
            return this.factory.create(resourceLocation, string, ingredient, itemStack);
        }

        @Override
        public void write(PacketBuffer packetBuffer, T t) {
            packetBuffer.writeString(((SingleItemRecipe)t).group);
            ((SingleItemRecipe)t).ingredient.write(packetBuffer);
            packetBuffer.writeItemStack(((SingleItemRecipe)t).result);
        }

        @Override
        public void write(PacketBuffer packetBuffer, IRecipe iRecipe) {
            this.write(packetBuffer, (T)((SingleItemRecipe)iRecipe));
        }

        @Override
        public IRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            return this.read(resourceLocation, packetBuffer);
        }

        @Override
        public IRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return this.read(resourceLocation, jsonObject);
        }

        static interface IRecipeFactory<T extends SingleItemRecipe> {
            public T create(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4);
        }
    }
}

