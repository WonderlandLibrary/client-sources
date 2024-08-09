/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class SpriteUploader
extends ReloadListener<AtlasTexture.SheetData>
implements AutoCloseable {
    private final AtlasTexture textureAtlas;
    private final String prefix;

    public SpriteUploader(TextureManager textureManager, ResourceLocation resourceLocation, String string) {
        this.prefix = string;
        this.textureAtlas = new AtlasTexture(resourceLocation);
        textureManager.loadTexture(this.textureAtlas.getTextureLocation(), this.textureAtlas);
    }

    protected abstract Stream<ResourceLocation> getResourceLocations();

    protected TextureAtlasSprite getSprite(ResourceLocation resourceLocation) {
        return this.textureAtlas.getSprite(this.resolveLocation(resourceLocation));
    }

    private ResourceLocation resolveLocation(ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getNamespace(), this.prefix + "/" + resourceLocation.getPath());
    }

    @Override
    protected AtlasTexture.SheetData prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        iProfiler.startTick();
        iProfiler.startSection("stitching");
        AtlasTexture.SheetData sheetData = this.textureAtlas.stitch(iResourceManager, this.getResourceLocations().map(this::resolveLocation), iProfiler, 0);
        iProfiler.endSection();
        iProfiler.endTick();
        return sheetData;
    }

    @Override
    protected void apply(AtlasTexture.SheetData sheetData, IResourceManager iResourceManager, IProfiler iProfiler) {
        iProfiler.startTick();
        iProfiler.startSection("upload");
        this.textureAtlas.upload(sheetData);
        iProfiler.endSection();
        iProfiler.endTick();
    }

    @Override
    public void close() {
        this.textureAtlas.clear();
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((AtlasTexture.SheetData)object, iResourceManager, iProfiler);
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }
}

