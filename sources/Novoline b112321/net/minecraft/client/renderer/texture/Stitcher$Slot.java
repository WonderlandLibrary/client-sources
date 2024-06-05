package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.texture.Stitcher$Holder;

public class Stitcher$Slot {
   private final int originX;
   private final int originY;
   private final int width;
   private final int height;
   private List subSlots;
   private Stitcher$Holder holder;
   private static final String b = "CL_00001056";

   public Stitcher$Slot(int var1, int var2, int var3, int var4) {
      this.originX = var1;
      this.originY = var2;
      this.width = var3;
      this.height = var4;
   }

   public Stitcher$Holder getStitchHolder() {
      return this.holder;
   }

   public int getOriginX() {
      return this.originX;
   }

   public int getOriginY() {
      return this.originY;
   }

   public boolean addSlot(Stitcher$Holder var1) {
      if(this.holder != null) {
         return false;
      } else {
         int var2 = var1.getWidth();
         int var3 = var1.getHeight();
         if(var2 <= this.width && var3 <= this.height) {
            if(var2 == this.width && var3 == this.height) {
               this.holder = var1;
               return true;
            } else {
               if(this.subSlots == null) {
                  this.subSlots = Lists.newArrayListWithCapacity(1);
                  this.subSlots.add(new Stitcher$Slot(this.originX, this.originY, var2, var3));
                  int var4 = this.width - var2;
                  int var5 = this.height - var3;
                  int var6 = Math.max(this.height, var4);
                  int var7 = Math.max(this.width, var5);
                  if(var6 >= var7) {
                     this.subSlots.add(new Stitcher$Slot(this.originX, this.originY + var3, var2, var5));
                     this.subSlots.add(new Stitcher$Slot(this.originX + var2, this.originY, var4, this.height));
                  } else {
                     this.subSlots.add(new Stitcher$Slot(this.originX + var2, this.originY, var4, var3));
                     this.subSlots.add(new Stitcher$Slot(this.originX, this.originY + var3, this.width, var5));
                  }
               }

               for(Object var9 : this.subSlots) {
                  if(((Stitcher$Slot)var9).addSlot(var1)) {
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

   public void getAllStitchSlots(List var1) {
      if(this.holder != null) {
         var1.add(this);
      } else if(this.subSlots != null) {
         for(Object var3 : this.subSlots) {
            ((Stitcher$Slot)var3).getAllStitchSlots(var1);
         }
      }

   }

   public String toString() {
      return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
   }
}
