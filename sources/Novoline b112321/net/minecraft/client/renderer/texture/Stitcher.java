package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.renderer.texture.Stitcher$Holder;
import net.minecraft.client.renderer.texture.Stitcher$Slot;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.MathHelper;

public class Stitcher {
   private final int mipmapLevelStitcher;
   private final Set setStitchHolders = Sets.newHashSetWithExpectedSize(256);
   private final List stitchSlots = Lists.newArrayListWithCapacity(256);
   private int currentWidth;
   private int currentHeight;
   private final int maxWidth;
   private final int maxHeight;
   private final boolean forcePowerOf2;
   private final int maxTileDimension;
   private static final String j = "CL_00001054";

   public Stitcher(int var1, int var2, boolean var3, int var4, int var5) {
      this.mipmapLevelStitcher = var5;
      this.maxWidth = var1;
      this.maxHeight = var2;
      this.forcePowerOf2 = var3;
      this.maxTileDimension = var4;
   }

   public int getCurrentWidth() {
      return this.currentWidth;
   }

   public int getCurrentHeight() {
      return this.currentHeight;
   }

   public void addSprite(TextureAtlasSprite var1) {
      Stitcher$Holder var2 = new Stitcher$Holder(var1, this.mipmapLevelStitcher);
      if(this.maxTileDimension > 0) {
         var2.setNewDimension(this.maxTileDimension);
      }

      this.setStitchHolders.add(var2);
   }

   public void doStitch() {
      Stitcher$Holder[] var1 = (Stitcher$Holder[])((Stitcher$Holder[])((Stitcher$Holder[])this.setStitchHolders.toArray(new Stitcher$Holder[this.setStitchHolders.size()])));
      Arrays.sort((Object[])var1);

      for(Stitcher$Holder var5 : var1) {
         if(!this.allocateSlot(var5)) {
            String var6 = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", new Object[]{var5.getAtlasSprite().getIconName(), Integer.valueOf(var5.getAtlasSprite().getIconWidth()), Integer.valueOf(var5.getAtlasSprite().getIconHeight()), Integer.valueOf(this.currentWidth), Integer.valueOf(this.currentHeight), Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight)});
            throw new StitcherException(var5, var6);
         }
      }

      if(this.forcePowerOf2) {
         this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
         this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
      }

   }

   public List getStichSlots() {
      ArrayList var1 = Lists.newArrayList();

      for(Object var3 : this.stitchSlots) {
         ((Stitcher$Slot)var3).getAllStitchSlots(var1);
      }

      ArrayList var8 = Lists.newArrayList();

      for(Object var4 : var1) {
         Stitcher$Slot var5 = (Stitcher$Slot)var4;
         Stitcher$Holder var6 = var5.getStitchHolder();
         TextureAtlasSprite var7 = var6.getAtlasSprite();
         var7.initSprite(this.currentWidth, this.currentHeight, var5.getOriginX(), var5.getOriginY(), var6.isRotated());
         var8.add(var7);
      }

      return var8;
   }

   private static int getMipmapDimension(int var0, int var1) {
      return (var0 >> var1) + ((var0 & (1 << var1) - 1) == 0?0:1) << var1;
   }

   private boolean allocateSlot(Stitcher$Holder var1) {
      for(Object var3 : this.stitchSlots) {
         if(((Stitcher$Slot)var3).addSlot(var1)) {
            return true;
         }

         var1.rotate();
         if(((Stitcher$Slot)var3).addSlot(var1)) {
            return true;
         }

         var1.rotate();
      }

      return this.b(var1);
   }

   private boolean b(Stitcher$Holder var1) {
      int var2 = Math.min(var1.getWidth(), var1.getHeight());
      if(this.currentWidth == 0 && this.currentHeight == 0) {
         boolean var13 = true;
      } else {
         boolean var10000 = false;
      }

      if(this.forcePowerOf2) {
         int var11 = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
         int var12 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
         int var7 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + var2);
         int var8 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + var2);
         boolean var9 = var7 <= this.maxWidth;
         boolean var10 = var8 <= this.maxHeight;
         return false;
      } else {
         boolean var5 = this.currentWidth + var2 <= this.maxWidth;
         boolean var6 = this.currentHeight + var2 <= this.maxHeight;
         return false;
      }
   }

   static int access$000(int var0, int var1) {
      return getMipmapDimension(var0, var1);
   }

   private static StitcherException a(StitcherException var0) {
      return var0;
   }
}
