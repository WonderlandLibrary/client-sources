package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.gui.MapItemRenderer$Instance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

public class MapItemRenderer {
   private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
   private final TextureManager textureManager;
   private final Map loadedMaps = Maps.newHashMap();

   public MapItemRenderer(TextureManager var1) {
      this.textureManager = var1;
   }

   public void updateMapTexture(MapData var1) {
      MapItemRenderer$Instance.access$000(this.getMapRendererInstance(var1));
   }

   public void renderMap(MapData var1, boolean var2) {
      MapItemRenderer$Instance.access$100(this.getMapRendererInstance(var1), var2);
   }

   private MapItemRenderer$Instance getMapRendererInstance(MapData var1) {
      MapItemRenderer$Instance var2 = (MapItemRenderer$Instance)this.loadedMaps.get(var1.mapName);
      var2 = new MapItemRenderer$Instance(this, var1);
      this.loadedMaps.put(var1.mapName, var2);
      return var2;
   }

   public void clearLoadedMaps() {
      for(MapItemRenderer$Instance var2 : this.loadedMaps.values()) {
         this.textureManager.deleteTexture(MapItemRenderer$Instance.access$300(var2));
      }

      this.loadedMaps.clear();
   }

   static TextureManager access$400(MapItemRenderer var0) {
      return var0.textureManager;
   }

   static ResourceLocation access$500() {
      return mapIcons;
   }
}
