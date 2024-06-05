package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner$EnumBannerPattern;

public class TileEntityBanner extends TileEntity {
   private int baseColor;
   private NBTTagList patterns;
   private boolean field_175119_g;
   private List patternList;
   private List colorList;
   private String patternResourceLocation;

   public void setItemValues(ItemStack var1) {
      this.patterns = null;
      if(var1.hasTagCompound() && var1.getTagCompound().hasKey("BlockEntityTag", 10)) {
         NBTTagCompound var2 = var1.getTagCompound().getCompoundTag("BlockEntityTag");
         if(var2.hasKey("Patterns")) {
            this.patterns = (NBTTagList)var2.getTagList("Patterns", 10).copy();
         }

         if(var2.hasKey("Base", 99)) {
            this.baseColor = var2.getInteger("Base");
         } else {
            this.baseColor = var1.getMetadata() & 15;
         }
      } else {
         this.baseColor = var1.getMetadata() & 15;
      }

      this.patternList = null;
      this.colorList = null;
      this.patternResourceLocation = "";
      this.field_175119_g = true;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      func_181020_a(var1, this.baseColor, this.patterns);
   }

   public static void func_181020_a(NBTTagCompound var0, int var1, NBTTagList var2) {
      var0.setInteger("Base", var1);
      var0.setTag("Patterns", var2);
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.baseColor = var1.getInteger("Base");
      this.patterns = var1.getTagList("Patterns", 10);
      this.patternList = null;
      this.colorList = null;
      this.patternResourceLocation = null;
      this.field_175119_g = true;
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new S35PacketUpdateTileEntity(this.pos, 6, var1);
   }

   public int getBaseColor() {
      return this.baseColor;
   }

   public static int getBaseColor(ItemStack var0) {
      NBTTagCompound var1 = var0.getSubCompound("BlockEntityTag", false);
      return var1.hasKey("Base")?var1.getInteger("Base"):var0.getMetadata();
   }

   public static int getPatterns(ItemStack var0) {
      NBTTagCompound var1 = var0.getSubCompound("BlockEntityTag", false);
      return var1.hasKey("Patterns")?var1.getTagList("Patterns", 10).tagCount():0;
   }

   public List getPatternList() {
      this.initializeBannerData();
      return this.patternList;
   }

   public NBTTagList func_181021_d() {
      return this.patterns;
   }

   public List getColorList() {
      this.initializeBannerData();
      return this.colorList;
   }

   public String func_175116_e() {
      this.initializeBannerData();
      return this.patternResourceLocation;
   }

   private void initializeBannerData() {
      if(this.patternList == null || this.colorList == null || this.patternResourceLocation == null) {
         if(!this.field_175119_g) {
            this.patternResourceLocation = "";
         } else {
            this.patternList = Lists.newArrayList();
            this.colorList = Lists.newArrayList();
            this.patternList.add(TileEntityBanner$EnumBannerPattern.BASE);
            this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
            this.patternResourceLocation = "b" + this.baseColor;
            if(this.patterns != null) {
               for(int var1 = 0; var1 < this.patterns.tagCount(); ++var1) {
                  NBTTagCompound var2 = this.patterns.getCompoundTagAt(var1);
                  TileEntityBanner$EnumBannerPattern var3 = TileEntityBanner$EnumBannerPattern.getPatternByID(var2.getString("Pattern"));
                  this.patternList.add(var3);
                  int var4 = var2.getInteger("Color");
                  this.colorList.add(EnumDyeColor.byDyeDamage(var4));
                  this.patternResourceLocation = this.patternResourceLocation + var3.getPatternID() + var4;
               }
            }
         }
      }

   }

   public static void removeBannerData(ItemStack var0) {
      NBTTagCompound var1 = var0.getSubCompound("BlockEntityTag", false);
      if(var1.hasKey("Patterns", 9)) {
         NBTTagList var2 = var1.getTagList("Patterns", 10);
         if(var2.tagCount() > 0) {
            var2.removeTag(var2.tagCount() - 1);
            if(var2.hasNoTags()) {
               var0.getTagCompound().removeTag("BlockEntityTag");
               if(var0.getTagCompound().hasNoTags()) {
                  var0.setTagCompound((NBTTagCompound)null);
               }
            }
         }
      }

   }
}
