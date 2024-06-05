package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {
   private IRegistry modelRegistry;
   private final TextureMap texMap;
   private final BlockModelShapes modelProvider;
   private IBakedModel defaultModel;

   public ModelManager(TextureMap var1) {
      this.texMap = var1;
      this.modelProvider = new BlockModelShapes(this);
   }

   public void onResourceManagerReload(IResourceManager var1) {
      ModelBakery var2 = new ModelBakery(var1, this.texMap, this.modelProvider);
      this.modelRegistry = var2.setupModelRegistry();
      this.defaultModel = (IBakedModel)this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
      this.modelProvider.reloadModels();
   }

   public IBakedModel a(ModelResourceLocation var1) {
      return this.defaultModel;
   }

   public IBakedModel getMissingModel() {
      return this.defaultModel;
   }

   public TextureMap getTextureMap() {
      return this.texMap;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.modelProvider;
   }
}
