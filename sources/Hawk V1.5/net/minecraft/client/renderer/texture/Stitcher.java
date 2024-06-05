package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

public class Stitcher {
   private final int maxWidth;
   private final int maxTileDimension;
   private static final String __OBFID = "CL_00001054";
   private final int maxHeight;
   private final List stitchSlots = Lists.newArrayListWithCapacity(256);
   private int currentWidth;
   private int currentHeight;
   private final int mipmapLevelStitcher;
   private final Set setStitchHolders = Sets.newHashSetWithExpectedSize(256);
   private final boolean forcePowerOf2;

   public Stitcher(int var1, int var2, boolean var3, int var4, int var5) {
      this.mipmapLevelStitcher = var5;
      this.maxWidth = var1;
      this.maxHeight = var2;
      this.forcePowerOf2 = var3;
      this.maxTileDimension = var4;
   }

   static int access$0(int var0, int var1) {
      return getMipmapDimension(var0, var1);
   }

   private boolean expandAndAllocateSlot(Stitcher.Holder var1) {
      int var2 = Math.min(var1.getWidth(), var1.getHeight());
      boolean var3 = this.currentWidth == 0 && this.currentHeight == 0;
      boolean var4;
      int var5;
      if (this.forcePowerOf2) {
         var5 = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
         int var6 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
         int var7 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + var2);
         int var8 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + var2);
         boolean var9 = var7 <= this.maxWidth;
         boolean var10 = var8 <= this.maxHeight;
         if (!var9 && !var10) {
            return false;
         }

         boolean var11 = var5 != var7;
         boolean var12 = var6 != var8;
         if (var11 ^ var12) {
            var4 = !var11;
         } else {
            var4 = var9 && var5 <= var6;
         }
      } else {
         boolean var13 = this.currentWidth + var2 <= this.maxWidth;
         boolean var15 = this.currentHeight + var2 <= this.maxHeight;
         if (!var13 && !var15) {
            return false;
         }

         var4 = var13 && (var3 || this.currentWidth <= this.currentHeight);
      }

      var5 = Math.max(var1.getWidth(), var1.getHeight());
      if (MathHelper.roundUpToPowerOfTwo((!var4 ? this.currentHeight : this.currentWidth) + var5) > (!var4 ? this.maxHeight : this.maxWidth)) {
         return false;
      } else {
         Stitcher.Slot var14;
         if (var4) {
            if (var1.getWidth() > var1.getHeight()) {
               var1.rotate();
            }

            if (this.currentHeight == 0) {
               this.currentHeight = var1.getHeight();
            }

            var14 = new Stitcher.Slot(this.currentWidth, 0, var1.getWidth(), this.currentHeight);
            this.currentWidth += var1.getWidth();
         } else {
            var14 = new Stitcher.Slot(0, this.currentHeight, this.currentWidth, var1.getHeight());
            this.currentHeight += var1.getHeight();
         }

         var14.addSlot(var1);
         this.stitchSlots.add(var14);
         return true;
      }
   }

   public int getCurrentWidth() {
      return this.currentWidth;
   }

   public void doStitch() {
      Stitcher.Holder[] var1 = (Stitcher.Holder[])this.setStitchHolders.toArray(new Stitcher.Holder[this.setStitchHolders.size()]);
      Arrays.sort(var1);
      Stitcher.Holder[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Stitcher.Holder var5 = var2[var4];
         if (!this.allocateSlot(var5)) {
            String var6 = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", var5.getAtlasSprite().getIconName(), var5.getAtlasSprite().getIconWidth(), var5.getAtlasSprite().getIconHeight(), this.currentWidth, this.currentHeight, this.maxWidth, this.maxHeight);
            throw new StitcherException(var5, var6);
         }
      }

      if (this.forcePowerOf2) {
         this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
         this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
      }

   }

   public void addSprite(TextureAtlasSprite var1) {
      Stitcher.Holder var2 = new Stitcher.Holder(var1, this.mipmapLevelStitcher);
      if (this.maxTileDimension > 0) {
         var2.setNewDimension(this.maxTileDimension);
      }

      this.setStitchHolders.add(var2);
   }

   private boolean allocateSlot(Stitcher.Holder var1) {
      for(int var2 = 0; var2 < this.stitchSlots.size(); ++var2) {
         if (((Stitcher.Slot)this.stitchSlots.get(var2)).addSlot(var1)) {
            return true;
         }

         var1.rotate();
         if (((Stitcher.Slot)this.stitchSlots.get(var2)).addSlot(var1)) {
            return true;
         }

         var1.rotate();
      }

      return this.expandAndAllocateSlot(var1);
   }

   public List getStichSlots() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.stitchSlots.iterator();

      while(var2.hasNext()) {
         Stitcher.Slot var3 = (Stitcher.Slot)var2.next();
         var3.getAllStitchSlots(var1);
      }

      ArrayList var8 = Lists.newArrayList();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Stitcher.Slot var5 = (Stitcher.Slot)var4.next();
         Stitcher.Holder var6 = var5.getStitchHolder();
         TextureAtlasSprite var7 = var6.getAtlasSprite();
         var7.initSprite(this.currentWidth, this.currentHeight, var5.getOriginX(), var5.getOriginY(), var6.isRotated());
         var8.add(var7);
      }

      return var8;
   }

   private static int getMipmapDimension(int var0, int var1) {
      return (var0 >> var1) + ((var0 & (1 << var1) - 1) == 0 ? 0 : 1) << var1;
   }

   public int getCurrentHeight() {
      return this.currentHeight;
   }

   public static class Holder implements Comparable {
      private final int height;
      private float scaleFactor = 1.0F;
      private final int width;
      private final TextureAtlasSprite theTexture;
      private boolean rotated;
      private static final String __OBFID = "CL_00001055";
      private final int mipmapLevelHolder;

      public void setNewDimension(int var1) {
         if (this.width > var1 && this.height > var1) {
            this.scaleFactor = (float)var1 / (float)Math.min(this.width, this.height);
         }

      }

      public Holder(TextureAtlasSprite var1, int var2) {
         this.theTexture = var1;
         this.width = var1.getIconWidth();
         this.height = var1.getIconHeight();
         this.mipmapLevelHolder = var2;
         this.rotated = Stitcher.access$0(this.height, var2) > Stitcher.access$0(this.width, var2);
      }

      public int compareTo(Stitcher.Holder var1) {
         int var2;
         if (this.getHeight() == var1.getHeight()) {
            if (this.getWidth() == var1.getWidth()) {
               if (this.theTexture.getIconName() == null) {
                  return var1.theTexture.getIconName() == null ? 0 : -1;
               }

               return this.theTexture.getIconName().compareTo(var1.theTexture.getIconName());
            }

            var2 = this.getWidth() < var1.getWidth() ? 1 : -1;
         } else {
            var2 = this.getHeight() < var1.getHeight() ? 1 : -1;
         }

         return var2;
      }

      public TextureAtlasSprite getAtlasSprite() {
         return this.theTexture;
      }

      public int getHeight() {
         return this.rotated ? Stitcher.access$0((int)((float)this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$0((int)((float)this.height * this.scaleFactor), this.mipmapLevelHolder);
      }

      public int compareTo(Object var1) {
         return this.compareTo((Stitcher.Holder)var1);
      }

      public boolean isRotated() {
         return this.rotated;
      }

      public int getWidth() {
         return this.rotated ? Stitcher.access$0((int)((float)this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$0((int)((float)this.width * this.scaleFactor), this.mipmapLevelHolder);
      }

      public String toString() {
         return String.valueOf((new StringBuilder("Holder{width=")).append(this.width).append(", height=").append(this.height).append(", name=").append(this.theTexture.getIconName()).append('}'));
      }

      public void rotate() {
         this.rotated = !this.rotated;
      }
   }

   public static class Slot {
      private final int width;
      private static final String __OBFID = "CL_00001056";
      private final int originX;
      private final int height;
      private final int originY;
      private List subSlots;
      private Stitcher.Holder holder;

      public int getOriginX() {
         return this.originX;
      }

      public Slot(int var1, int var2, int var3, int var4) {
         this.originX = var1;
         this.originY = var2;
         this.width = var3;
         this.height = var4;
      }

      public String toString() {
         return String.valueOf((new StringBuilder("Slot{originX=")).append(this.originX).append(", originY=").append(this.originY).append(", width=").append(this.width).append(", height=").append(this.height).append(", texture=").append(this.holder).append(", subSlots=").append(this.subSlots).append('}'));
      }

      public void getAllStitchSlots(List var1) {
         if (this.holder != null) {
            var1.add(this);
         } else if (this.subSlots != null) {
            Iterator var2 = this.subSlots.iterator();

            while(var2.hasNext()) {
               Stitcher.Slot var3 = (Stitcher.Slot)var2.next();
               var3.getAllStitchSlots(var1);
            }
         }

      }

      public Stitcher.Holder getStitchHolder() {
         return this.holder;
      }

      public boolean addSlot(Stitcher.Holder var1) {
         if (this.holder != null) {
            return false;
         } else {
            int var2 = var1.getWidth();
            int var3 = var1.getHeight();
            if (var2 <= this.width && var3 <= this.height) {
               if (var2 == this.width && var3 == this.height) {
                  this.holder = var1;
                  return true;
               } else {
                  if (this.subSlots == null) {
                     this.subSlots = Lists.newArrayListWithCapacity(1);
                     this.subSlots.add(new Stitcher.Slot(this.originX, this.originY, var2, var3));
                     int var4 = this.width - var2;
                     int var5 = this.height - var3;
                     if (var5 > 0 && var4 > 0) {
                        int var6 = Math.max(this.height, var4);
                        int var7 = Math.max(this.width, var5);
                        if (var6 >= var7) {
                           this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + var3, var2, var5));
                           this.subSlots.add(new Stitcher.Slot(this.originX + var2, this.originY, var4, this.height));
                        } else {
                           this.subSlots.add(new Stitcher.Slot(this.originX + var2, this.originY, var4, var3));
                           this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + var3, this.width, var5));
                        }
                     } else if (var4 == 0) {
                        this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + var3, var2, var5));
                     } else if (var5 == 0) {
                        this.subSlots.add(new Stitcher.Slot(this.originX + var2, this.originY, var4, var3));
                     }
                  }

                  Iterator var8 = this.subSlots.iterator();

                  while(var8.hasNext()) {
                     Stitcher.Slot var9 = (Stitcher.Slot)var8.next();
                     if (var9.addSlot(var1)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public int getOriginY() {
         return this.originY;
      }
   }
}
