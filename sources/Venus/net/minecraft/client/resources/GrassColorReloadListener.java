/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.resources.ColorMapLoader;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GrassColors;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GrassColorReloadListener
extends ReloadListener<int[]> {
    private static final ResourceLocation GRASS_LOCATION = new ResourceLocation("textures/colormap/grass.png");

    @Override
    protected int[] prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        try {
            return ColorMapLoader.loadColors(iResourceManager, GRASS_LOCATION);
        } catch (IOException iOException) {
            throw new IllegalStateException("Failed to load grass color texture", iOException);
        }
    }

    @Override
    protected void apply(int[] nArray, IResourceManager iResourceManager, IProfiler iProfiler) {
        GrassColors.setGrassBiomeColorizer(nArray);
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((int[])object, iResourceManager, iProfiler);
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }
}

