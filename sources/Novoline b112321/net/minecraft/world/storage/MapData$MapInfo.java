package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.world.storage.MapData;

public class MapData$MapInfo {
   public final EntityPlayer entityplayerObj;
   private boolean field_176105_d;
   private int minX;
   private int minY;
   private int maxX;
   private int maxY;
   private int field_176109_i;
   public int field_82569_d;
   final MapData this$0;

   public MapData$MapInfo(MapData var1, EntityPlayer var2) {
      this.this$0 = var1;
      this.field_176105_d = true;
      this.minX = 0;
      this.minY = 0;
      this.maxX = 127;
      this.maxY = 127;
      this.entityplayerObj = var2;
   }

   public Packet getPacket(ItemStack var1) {
      if(this.field_176105_d) {
         this.field_176105_d = false;
         return new S34PacketMaps(var1.getMetadata(), this.this$0.scale, this.this$0.mapDecorations.values(), this.this$0.colors, this.minX, this.minY, this.maxX + 1 - this.minX, this.maxY + 1 - this.minY);
      } else {
         return this.field_176109_i++ % 5 == 0?new S34PacketMaps(var1.getMetadata(), this.this$0.scale, this.this$0.mapDecorations.values(), this.this$0.colors, 0, 0, 0, 0):null;
      }
   }

   public void update(int var1, int var2) {
      if(this.field_176105_d) {
         this.minX = Math.min(this.minX, var1);
         this.minY = Math.min(this.minY, var2);
         this.maxX = Math.max(this.maxX, var1);
         this.maxY = Math.max(this.maxY, var2);
      } else {
         this.field_176105_d = true;
         this.minX = var1;
         this.minY = var2;
         this.maxX = var1;
         this.maxY = var2;
      }

   }
}
