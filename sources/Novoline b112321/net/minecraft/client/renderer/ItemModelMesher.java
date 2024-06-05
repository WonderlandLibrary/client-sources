package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemModelMesher {
   private final Map simpleShapes = Maps.newHashMap();
   private final Map simpleShapesCache = Maps.newHashMap();
   private final Map shapers = Maps.newHashMap();
   private final ModelManager modelManager;

   public ItemModelMesher(ModelManager var1) {
      this.modelManager = var1;
   }

   public TextureAtlasSprite getParticleIcon(Item var1) {
      return this.getParticleIcon(var1, 0);
   }

   public TextureAtlasSprite getParticleIcon(Item var1, int var2) {
      return this.getItemModel(new ItemStack(var1, 1, var2)).getParticleTexture();
   }

   public IBakedModel getItemModel(ItemStack var1) {
      Item var2 = var1.getItem();
      IBakedModel var3 = this.getItemModel(var2, this.getMetadata(var1));
      ItemMeshDefinition var4 = (ItemMeshDefinition)this.shapers.get(var2);
      var3 = this.modelManager.a(var4.getModelLocation(var1));
      var3 = this.modelManager.getMissingModel();
      return var3;
   }

   protected int getMetadata(ItemStack var1) {
      return var1.isItemStackDamageable()?0:var1.getMetadata();
   }

   protected IBakedModel getItemModel(Item var1, int var2) {
      return (IBakedModel)this.simpleShapesCache.get(Integer.valueOf(this.getIndex(var1, var2)));
   }

   private int getIndex(Item var1, int var2) {
      return Item.b(var1) << 16 | var2;
   }

   public void register(Item var1, int var2, ModelResourceLocation var3) {
      this.simpleShapes.put(Integer.valueOf(this.getIndex(var1, var2)), var3);
      this.simpleShapesCache.put(Integer.valueOf(this.getIndex(var1, var2)), this.modelManager.a(var3));
   }

   public void register(Item var1, ItemMeshDefinition var2) {
      this.shapers.put(var1, var2);
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public void rebuildCache() {
      this.simpleShapesCache.clear();

      for(Entry var2 : this.simpleShapes.entrySet()) {
         this.simpleShapesCache.put(var2.getKey(), this.modelManager.a((ModelResourceLocation)var2.getValue()));
      }

   }
}
