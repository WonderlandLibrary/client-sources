package net.minecraft.client.gui.fonts.providers;

import net.minecraft.resources.IResourceManager;

import javax.annotation.Nullable;

public interface IGlyphProviderFactory
{
    @Nullable
    IGlyphProvider create(IResourceManager resourceManagerIn);
}
