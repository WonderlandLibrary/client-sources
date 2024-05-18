package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;

public class ModelManager implements IResourceManagerReloadListener
{
    private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;
    private IBakedModel defaultModel;
    private final BlockModelShapes modelProvider;
    private final TextureMap texMap;
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.modelRegistry = new ModelBakery(resourceManager, this.texMap, this.modelProvider).setupModelRegistry();
        this.defaultModel = this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
        this.modelProvider.reloadModels();
    }
    
    public IBakedModel getMissingModel() {
        return this.defaultModel;
    }
    
    public ModelManager(final TextureMap texMap) {
        this.texMap = texMap;
        this.modelProvider = new BlockModelShapes(this);
    }
    
    public TextureMap getTextureMap() {
        return this.texMap;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public IBakedModel getModel(final ModelResourceLocation modelResourceLocation) {
        if (modelResourceLocation == null) {
            return this.defaultModel;
        }
        final IBakedModel bakedModel = this.modelRegistry.getObject(modelResourceLocation);
        IBakedModel defaultModel;
        if (bakedModel == null) {
            defaultModel = this.defaultModel;
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            defaultModel = bakedModel;
        }
        return defaultModel;
    }
    
    public BlockModelShapes getBlockModelShapes() {
        return this.modelProvider;
    }
}
