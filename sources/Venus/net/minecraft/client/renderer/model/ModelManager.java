/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.SpriteMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.fluid.FluidState;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ModelManager
extends ReloadListener<ModelBakery>
implements AutoCloseable {
    private Map<ResourceLocation, IBakedModel> modelRegistry;
    @Nullable
    private SpriteMap atlases;
    private final BlockModelShapes modelProvider;
    private final TextureManager textureManager;
    private final BlockColors blockColors;
    private int maxMipmapLevel;
    private IBakedModel defaultModel;
    private Object2IntMap<BlockState> stateModelIds;

    public ModelManager(TextureManager textureManager, BlockColors blockColors, int n) {
        this.textureManager = textureManager;
        this.blockColors = blockColors;
        this.maxMipmapLevel = n;
        this.modelProvider = new BlockModelShapes(this);
    }

    public IBakedModel getModel(ModelResourceLocation modelResourceLocation) {
        return this.modelRegistry.getOrDefault(modelResourceLocation, this.defaultModel);
    }

    public IBakedModel getMissingModel() {
        return this.defaultModel;
    }

    public BlockModelShapes getBlockModelShapes() {
        return this.modelProvider;
    }

    @Override
    protected ModelBakery prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        iProfiler.startTick();
        ModelBakery modelBakery = new ModelBakery(iResourceManager, this.blockColors, iProfiler, this.maxMipmapLevel);
        iProfiler.endTick();
        return modelBakery;
    }

    @Override
    protected void apply(ModelBakery modelBakery, IResourceManager iResourceManager, IProfiler iProfiler) {
        iProfiler.startTick();
        iProfiler.startSection("upload");
        if (this.atlases != null) {
            this.atlases.close();
        }
        this.atlases = modelBakery.uploadTextures(this.textureManager, iProfiler);
        this.modelRegistry = modelBakery.getTopBakedModels();
        this.stateModelIds = modelBakery.getStateModelIds();
        this.defaultModel = this.modelRegistry.get(ModelBakery.MODEL_MISSING);
        iProfiler.endStartSection("cache");
        this.modelProvider.reloadModels();
        iProfiler.endSection();
        iProfiler.endTick();
    }

    public boolean needsRenderUpdate(BlockState blockState, BlockState blockState2) {
        int n;
        if (blockState == blockState2) {
            return true;
        }
        int n2 = this.stateModelIds.getInt(blockState);
        if (n2 != -1 && n2 == (n = this.stateModelIds.getInt(blockState2))) {
            FluidState fluidState;
            FluidState fluidState2 = blockState.getFluidState();
            return fluidState2 != (fluidState = blockState2.getFluidState());
        }
        return false;
    }

    public AtlasTexture getAtlasTexture(ResourceLocation resourceLocation) {
        return this.atlases.getAtlasTexture(resourceLocation);
    }

    @Override
    public void close() {
        if (this.atlases != null) {
            this.atlases.close();
        }
    }

    public void setMaxMipmapLevel(int n) {
        this.maxMipmapLevel = n;
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((ModelBakery)object, iResourceManager, iProfiler);
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }
}

