package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemModelMesher {
   private final ModelManager modelManager;
   private static final String __OBFID = "CL_00002536";
   private final Map simpleShapesCache = Maps.newHashMap();
   private final Map simpleShapes = Maps.newHashMap();
   private final Map shapers = Maps.newHashMap();

   public TextureAtlasSprite getParticleIcon(Item var1) {
      return this.getParticleIcon(var1, 0);
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   protected IBakedModel getItemModel(Item var1, int var2) {
      return (IBakedModel)this.simpleShapesCache.get(this.getIndex(var1, var2));
   }

   public TextureAtlasSprite getParticleIcon(Item var1, int var2) {
      return this.getItemModel(new ItemStack(var1, 1, var2)).getTexture();
   }

   protected int getMetadata(ItemStack var1) {
      return var1.isItemStackDamageable() ? 0 : var1.getMetadata();
   }

   private int getIndex(Item var1, int var2) {
      return Item.getIdFromItem(var1) << 16 | var2;
   }

   public void rebuildCache() {
      this.simpleShapesCache.clear();
      Iterator var1 = this.simpleShapes.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         this.simpleShapesCache.put(var2.getKey(), this.modelManager.getModel((ModelResourceLocation)var2.getValue()));
      }

   }

   public IBakedModel getItemModel(ItemStack var1) {
      Item var2 = var1.getItem();
      IBakedModel var3 = this.getItemModel(var2, this.getMetadata(var1));
      if (var3 == null) {
         ItemMeshDefinition var4 = (ItemMeshDefinition)this.shapers.get(var2);
         if (var4 != null) {
            var3 = this.modelManager.getModel(var4.getModelLocation(var1));
         }
      }

      if (var3 == null) {
         var3 = this.modelManager.getMissingModel();
      }

      return var3;
   }

   public void register(Item var1, int var2, ModelResourceLocation var3) {
      this.simpleShapes.put(this.getIndex(var1, var2), var3);
      this.simpleShapesCache.put(this.getIndex(var1, var2), this.modelManager.getModel(var3));
   }

   public ItemModelMesher(ModelManager var1) {
      this.modelManager = var1;
   }

   public void register(Item var1, ItemMeshDefinition var2) {
      this.shapers.put(var1, var2);
   }
}
