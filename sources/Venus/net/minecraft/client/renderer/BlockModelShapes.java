/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockModelShapes {
    private final Map<BlockState, IBakedModel> bakedModelStore = Maps.newIdentityHashMap();
    private final ModelManager modelManager;

    public BlockModelShapes(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public TextureAtlasSprite getTexture(BlockState blockState) {
        return this.getModel(blockState).getParticleTexture();
    }

    public IBakedModel getModel(BlockState blockState) {
        IBakedModel iBakedModel = this.bakedModelStore.get(blockState);
        if (iBakedModel == null) {
            iBakedModel = this.modelManager.getMissingModel();
        }
        return iBakedModel;
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public void reloadModels() {
        this.bakedModelStore.clear();
        for (Block block : Registry.BLOCK) {
            block.getStateContainer().getValidStates().forEach(this::lambda$reloadModels$0);
        }
    }

    public static ModelResourceLocation getModelLocation(BlockState blockState) {
        return BlockModelShapes.getModelLocation(Registry.BLOCK.getKey(blockState.getBlock()), blockState);
    }

    public static ModelResourceLocation getModelLocation(ResourceLocation resourceLocation, BlockState blockState) {
        return new ModelResourceLocation(resourceLocation, BlockModelShapes.getPropertyMapString(blockState.getValues()));
    }

    public static String getPropertyMapString(Map<Property<?>, Comparable<?>> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Property<?>, Comparable<?>> entry : map.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(',');
            }
            Property<?> property = entry.getKey();
            stringBuilder.append(property.getName());
            stringBuilder.append('=');
            stringBuilder.append(BlockModelShapes.getPropertyValueString(property, entry.getValue()));
        }
        return stringBuilder.toString();
    }

    private static <T extends Comparable<T>> String getPropertyValueString(Property<T> property, Comparable<?> comparable) {
        return property.getName(comparable);
    }

    private void lambda$reloadModels$0(BlockState blockState) {
        IBakedModel iBakedModel = this.bakedModelStore.put(blockState, this.modelManager.getModel(BlockModelShapes.getModelLocation(blockState)));
    }
}

