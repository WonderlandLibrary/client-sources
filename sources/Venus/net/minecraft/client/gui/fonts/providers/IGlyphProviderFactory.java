/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.resources.IResourceManager;

public interface IGlyphProviderFactory {
    @Nullable
    public IGlyphProvider create(IResourceManager var1);
}

