package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {
   private final BlockModelShapes field_174957_c;
   private static final String __OBFID = "CL_00002388";
   private final TextureMap field_174956_b;
   private IBakedModel defaultModel;
   private IRegistry modelRegistry;

   public IBakedModel getModel(ModelResourceLocation var1) {
      if (var1 == null) {
         return this.defaultModel;
      } else {
         IBakedModel var2 = (IBakedModel)this.modelRegistry.getObject(var1);
         return var2 == null ? this.defaultModel : var2;
      }
   }

   public TextureMap func_174952_b() {
      return this.field_174956_b;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.field_174957_c;
   }

   public ModelManager(TextureMap var1) {
      this.field_174956_b = var1;
      this.field_174957_c = new BlockModelShapes(this);
   }

   public void onResourceManagerReload(IResourceManager var1) {
      ModelBakery var2 = new ModelBakery(var1, this.field_174956_b, this.field_174957_c);
      this.modelRegistry = var2.setupModelRegistry();
      this.defaultModel = (IBakedModel)this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
      this.field_174957_c.func_178124_c();
   }

   public IBakedModel getMissingModel() {
      return this.defaultModel;
   }
}
