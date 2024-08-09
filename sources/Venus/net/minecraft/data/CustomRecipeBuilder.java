/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class CustomRecipeBuilder {
    private final SpecialRecipeSerializer<?> serializer;

    public CustomRecipeBuilder(SpecialRecipeSerializer<?> specialRecipeSerializer) {
        this.serializer = specialRecipeSerializer;
    }

    public static CustomRecipeBuilder customRecipe(SpecialRecipeSerializer<?> specialRecipeSerializer) {
        return new CustomRecipeBuilder(specialRecipeSerializer);
    }

    public void build(Consumer<IFinishedRecipe> consumer, String string) {
        consumer.accept(new IFinishedRecipe(){
            final String val$id;
            final CustomRecipeBuilder this$0;
            {
                this.this$0 = customRecipeBuilder;
                this.val$id = string;
            }

            @Override
            public void serialize(JsonObject jsonObject) {
            }

            @Override
            public IRecipeSerializer<?> getSerializer() {
                return this.this$0.serializer;
            }

            @Override
            public ResourceLocation getID() {
                return new ResourceLocation(this.val$id);
            }

            @Override
            @Nullable
            public JsonObject getAdvancementJson() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementID() {
                return new ResourceLocation("");
            }
        });
    }
}

