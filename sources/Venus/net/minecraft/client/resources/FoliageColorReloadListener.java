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
import net.minecraft.world.FoliageColors;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FoliageColorReloadListener
extends ReloadListener<int[]> {
    private static final ResourceLocation FOLIAGE_LOCATION = new ResourceLocation("textures/colormap/foliage.png");

    @Override
    protected int[] prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        try {
            return ColorMapLoader.loadColors(iResourceManager, FOLIAGE_LOCATION);
        } catch (IOException iOException) {
            throw new IllegalStateException("Failed to load foliage color texture", iOException);
        }
    }

    @Override
    protected void apply(int[] nArray, IResourceManager iResourceManager, IProfiler iProfiler) {
        FoliageColors.setFoliageBiomeColorizer(nArray);
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

