/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.Arrays;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;

public class ItemOverrideProperty {
    private ResourceLocation location;
    private float[] values;

    public ItemOverrideProperty(ResourceLocation resourceLocation, float[] fArray) {
        this.location = resourceLocation;
        this.values = (float[])fArray.clone();
        Arrays.sort(this.values);
    }

    public Integer getValueIndex(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
        Item item = itemStack.getItem();
        IItemPropertyGetter iItemPropertyGetter = ItemModelsProperties.func_239417_a_(item, this.location);
        if (iItemPropertyGetter == null) {
            return null;
        }
        float f = iItemPropertyGetter.call(itemStack, clientWorld, livingEntity);
        int n = Arrays.binarySearch(this.values, f);
        return n;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    public float[] getValues() {
        return this.values;
    }

    public String toString() {
        return "location: " + this.location + ", values: [" + Config.arrayToString(this.values) + "]";
    }
}

