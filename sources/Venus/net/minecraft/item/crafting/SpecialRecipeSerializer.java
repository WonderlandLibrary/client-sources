/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import java.util.function.Function;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SpecialRecipeSerializer<T extends IRecipe<?>>
implements IRecipeSerializer<T> {
    private final Function<ResourceLocation, T> field_222176_t;

    public SpecialRecipeSerializer(Function<ResourceLocation, T> function) {
        this.field_222176_t = function;
    }

    @Override
    public T read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        return (T)((IRecipe)this.field_222176_t.apply(resourceLocation));
    }

    @Override
    public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        return (T)((IRecipe)this.field_222176_t.apply(resourceLocation));
    }

    @Override
    public void write(PacketBuffer packetBuffer, T t) {
    }
}

