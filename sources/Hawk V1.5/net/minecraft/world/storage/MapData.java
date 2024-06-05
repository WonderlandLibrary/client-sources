package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class MapData extends WorldSavedData {
   public byte[] colors = new byte[16384];
   public byte dimension;
   private static final String __OBFID = "CL_00000577";
   public int zCenter;
   public List playersArrayList = Lists.newArrayList();
   public byte scale;
   public Map playersVisibleOnMap = Maps.newLinkedHashMap();
   private Map playersHashMap = Maps.newHashMap();
   public int xCenter;

   public void func_176053_a(int var1, int var2) {
      super.markDirty();
      Iterator var3 = this.playersArrayList.iterator();

      while(var3.hasNext()) {
         MapData.MapInfo var4 = (MapData.MapInfo)var3.next();
         var4.func_176102_a(var1, var2);
      }

   }

   public void writeToNBT(NBTTagCompound var1) {
      var1.setByte("dimension", this.dimension);
      var1.setInteger("xCenter", this.xCenter);
      var1.setInteger("zCenter", this.zCenter);
      var1.setByte("scale", this.scale);
      var1.setShort("width", (short)128);
      var1.setShort("height", (short)128);
      var1.setByteArray("colors", this.colors);
   }

   public MapData.MapInfo func_82568_a(EntityPlayer var1) {
      MapData.MapInfo var2 = (MapData.MapInfo)this.playersHashMap.get(var1);
      if (var2 == null) {
         var2 = new MapData.MapInfo(this, var1);
         this.playersHashMap.put(var1, var2);
         this.playersArrayList.add(var2);
      }

      return var2;
   }

   public void readFromNBT(NBTTagCompound var1) {
      this.dimension = var1.getByte("dimension");
      this.xCenter = var1.getInteger("xCenter");
      this.zCenter = var1.getInteger("zCenter");
      this.scale = var1.getByte("scale");
      this.scale = (byte)MathHelper.clamp_int(this.scale, 0, 4);
      short var2 = var1.getShort("width");
      short var3 = var1.getShort("height");
      if (var2 == 128 && var3 == 128) {
         this.colors = var1.getByteArray("colors");
      } else {
         byte[] var4 = var1.getByteArray("colors");
         this.colors = new byte[16384];
         int var5 = (128 - var2) / 2;
         int var6 = (128 - var3) / 2;

         for(int var7 = 0; var7 < var3; ++var7) {
            int var8 = var7 + var6;
            if (var8 >= 0 || var8 < 128) {
               for(int var9 = 0; var9 < var2; ++var9) {
                  int var10 = var9 + var5;
                  if (var10 >= 0 || var10 < 128) {
                     this.colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
                  }
               }
            }
         }
      }

   }

   public void updateVisiblePlayers(EntityPlayer var1, ItemStack var2) {
      if (!this.playersHashMap.containsKey(var1)) {
         MapData.MapInfo var3 = new MapData.MapInfo(this, var1);
         this.playersHashMap.put(var1, var3);
         this.playersArrayList.add(var3);
      }

      if (!var1.inventory.hasItemStack(var2)) {
         this.playersVisibleOnMap.remove(var1.getName());
      }

      for(int var6 = 0; var6 < this.playersArrayList.size(); ++var6) {
         MapData.MapInfo var4 = (MapData.MapInfo)this.playersArrayList.get(var6);
         if (!var4.entityplayerObj.isDead && (var4.entityplayerObj.inventory.hasItemStack(var2) || var2.isOnItemFrame())) {
            if (!var2.isOnItemFrame() && var4.entityplayerObj.dimension == this.dimension) {
               this.func_82567_a(0, var4.entityplayerObj.worldObj, var4.entityplayerObj.getName(), var4.entityplayerObj.posX, var4.entityplayerObj.posZ, (double)var4.entityplayerObj.rotationYaw);
            }
         } else {
            this.playersHashMap.remove(var4.entityplayerObj);
            this.playersArrayList.remove(var4);
         }
      }

      if (var2.isOnItemFrame()) {
         EntityItemFrame var7 = var2.getItemFrame();
         BlockPos var8 = var7.func_174857_n();
         this.func_82567_a(1, var1.worldObj, String.valueOf((new StringBuilder("frame-")).append(var7.getEntityId())), (double)var8.getX(), (double)var8.getZ(), (double)(var7.field_174860_b.getHorizontalIndex() * 90));
      }

      if (var2.hasTagCompound() && var2.getTagCompound().hasKey("Decorations", 9)) {
         NBTTagList var9 = var2.getTagCompound().getTagList("Decorations", 10);

         for(int var10 = 0; var10 < var9.tagCount(); ++var10) {
            NBTTagCompound var5 = var9.getCompoundTagAt(var10);
            if (!this.playersVisibleOnMap.containsKey(var5.getString("id"))) {
               this.func_82567_a(var5.getByte("type"), var1.worldObj, var5.getString("id"), var5.getDouble("x"), var5.getDouble("z"), var5.getDouble("rot"));
            }
         }
      }

   }

   public Packet func_176052_a(ItemStack var1, World var2, EntityPlayer var3) {
      MapData.MapInfo var4 = (MapData.MapInfo)this.playersHashMap.get(var3);
      return var4 == null ? null : var4.func_176101_a(var1);
   }

   public void func_176054_a(double var1, double var3, int var5) {
      int var6 = 128 * (1 << var5);
      int var7 = MathHelper.floor_double((var1 + 64.0D) / (double)var6);
      int var8 = MathHelper.floor_double((var3 + 64.0D) / (double)var6);
      this.xCenter = var7 * var6 + var6 / 2 - 64;
      this.zCenter = var8 * var6 + var6 / 2 - 64;
   }

   private void func_82567_a(int var1, World var2, String var3, double var4, double var6, double var8) {
      int var10 = 1 << this.scale;
      float var11 = (float)(var4 - (double)this.xCenter) / (float)var10;
      float var12 = (float)(var6 - (double)this.zCenter) / (float)var10;
      byte var13 = (byte)((int)((double)(var11 * 2.0F) + 0.5D));
      byte var14 = (byte)((int)((double)(var12 * 2.0F) + 0.5D));
      byte var15 = 63;
      byte var16;
      if (var11 >= (float)(-var15) && var12 >= (float)(-var15) && var11 <= (float)var15 && var12 <= (float)var15) {
         var8 += var8 < 0.0D ? -8.0D : 8.0D;
         var16 = (byte)((int)(var8 * 16.0D / 360.0D));
         if (this.dimension < 0) {
            int var17 = (int)(var2.getWorldInfo().getWorldTime() / 10L);
            var16 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
         }
      } else {
         if (Math.abs(var11) >= 320.0F || Math.abs(var12) >= 320.0F) {
            this.playersVisibleOnMap.remove(var3);
            return;
         }

         var1 = 6;
         var16 = 0;
         if (var11 <= (float)(-var15)) {
            var13 = (byte)((int)((double)(var15 * 2) + 2.5D));
         }

         if (var12 <= (float)(-var15)) {
            var14 = (byte)((int)((double)(var15 * 2) + 2.5D));
         }

         if (var11 >= (float)var15) {
            var13 = (byte)(var15 * 2 + 1);
         }

         if (var12 >= (float)var15) {
            var14 = (byte)(var15 * 2 + 1);
         }
      }

      this.playersVisibleOnMap.put(var3, new Vec4b((byte)var1, var13, var14, var16));
   }

   public MapData(String var1) {
      super(var1);
   }

   public class MapInfo {
      private static final String __OBFID = "CL_00000578";
      private int field_176108_h;
      public int field_82569_d;
      public final EntityPlayer entityplayerObj;
      private int field_176109_i;
      final MapData this$0;
      private int field_176103_f;
      private int field_176106_e;
      private int field_176104_g;
      private boolean field_176105_d;

      public void func_176102_a(int var1, int var2) {
         if (this.field_176105_d) {
            this.field_176106_e = Math.min(this.field_176106_e, var1);
            this.field_176103_f = Math.min(this.field_176103_f, var2);
            this.field_176104_g = Math.max(this.field_176104_g, var1);
            this.field_176108_h = Math.max(this.field_176108_h, var2);
         } else {
            this.field_176105_d = true;
            this.field_176106_e = var1;
            this.field_176103_f = var2;
            this.field_176104_g = var1;
            this.field_176108_h = var2;
         }

      }

      public Packet func_176101_a(ItemStack var1) {
         if (this.field_176105_d) {
            this.field_176105_d = false;
            return new S34PacketMaps(var1.getMetadata(), this.this$0.scale, this.this$0.playersVisibleOnMap.values(), this.this$0.colors, this.field_176106_e, this.field_176103_f, this.field_176104_g + 1 - this.field_176106_e, this.field_176108_h + 1 - this.field_176103_f);
         } else {
            return this.field_176109_i++ % 5 == 0 ? new S34PacketMaps(var1.getMetadata(), this.this$0.scale, this.this$0.playersVisibleOnMap.values(), this.this$0.colors, 0, 0, 0, 0) : null;
         }
      }

      public MapInfo(MapData var1, EntityPlayer var2) {
         this.this$0 = var1;
         this.field_176105_d = true;
         this.field_176106_e = 0;
         this.field_176103_f = 0;
         this.field_176104_g = 127;
         this.field_176108_h = 127;
         this.entityplayerObj = var2;
      }
   }
}
