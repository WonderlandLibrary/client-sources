package net.minecraft.tags;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public final class FluidTags
{
    protected static final TagRegistry<Fluid> collection = TagRegistryManager.create(new ResourceLocation("fluid"), ITagCollectionSupplier::getFluidTags);
    public static final ITag.INamedTag<Fluid> WATER = makeWrapperTag("water");
    public static final ITag.INamedTag<Fluid> LAVA = makeWrapperTag("lava");

    private static ITag.INamedTag<Fluid> makeWrapperTag(String id)
    {
        return collection.createTag(id);
    }

    public static List <? extends ITag.INamedTag<Fluid >> getAllTags()
    {
        return collection.getTags();
    }
}
