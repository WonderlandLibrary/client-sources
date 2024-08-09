/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import java.util.List;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistry;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;

public final class FluidTags {
    protected static final TagRegistry<Fluid> collection = TagRegistryManager.create(new ResourceLocation("fluid"), ITagCollectionSupplier::getFluidTags);
    public static final ITag.INamedTag<Fluid> WATER = FluidTags.makeWrapperTag("water");
    public static final ITag.INamedTag<Fluid> LAVA = FluidTags.makeWrapperTag("lava");

    private static ITag.INamedTag<Fluid> makeWrapperTag(String string) {
        return collection.createTag(string);
    }

    public static List<? extends ITag.INamedTag<Fluid>> getAllTags() {
        return collection.getTags();
    }
}

