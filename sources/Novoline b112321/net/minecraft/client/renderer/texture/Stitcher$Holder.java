package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class Stitcher$Holder implements Comparable {
   private final TextureAtlasSprite theTexture;
   private final int width;
   private final int height;
   private final int mipmapLevelHolder;
   private boolean rotated;
   private float scaleFactor = 1.0F;
   private static final String d = "CL_00001055";

   public Stitcher$Holder(TextureAtlasSprite var1, int var2) {
      this.theTexture = var1;
      this.width = var1.getIconWidth();
      this.height = var1.getIconHeight();
      this.mipmapLevelHolder = var2;
      this.rotated = Stitcher.access$000(this.height, var2) > Stitcher.access$000(this.width, var2);
   }

   public TextureAtlasSprite getAtlasSprite() {
      return this.theTexture;
   }

   public int getWidth() {
      return this.rotated?Stitcher.access$000((int)((float)this.height * this.scaleFactor), this.mipmapLevelHolder):Stitcher.access$000((int)((float)this.width * this.scaleFactor), this.mipmapLevelHolder);
   }

   public int getHeight() {
      return this.rotated?Stitcher.access$000((int)((float)this.width * this.scaleFactor), this.mipmapLevelHolder):Stitcher.access$000((int)((float)this.height * this.scaleFactor), this.mipmapLevelHolder);
   }

   public void rotate() {
      this.rotated = !this.rotated;
   }

   public boolean isRotated() {
      return this.rotated;
   }

   public void setNewDimension(int var1) {
      if(this.width > var1 && this.height > var1) {
         this.scaleFactor = (float)var1 / (float)Math.min(this.width, this.height);
      }

   }

   public String toString() {
      return "Holder{width=" + this.width + ", height=" + this.height + ", name=" + this.theTexture.getIconName() + '}';
   }

   public int compareTo(Stitcher$Holder var1) {
      int var2;
      if(this.getHeight() == var1.getHeight()) {
         if(this.getWidth() == var1.getWidth()) {
            if(this.theTexture.getIconName() == null) {
               return var1.theTexture.getIconName() == null?0:-1;
            }

            return this.theTexture.getIconName().compareTo(var1.theTexture.getIconName());
         }

         var2 = this.getWidth() < var1.getWidth()?1:-1;
      } else {
         var2 = this.getHeight() < var1.getHeight()?1:-1;
      }

      return var2;
   }

   public int compareTo(Object var1) {
      return this.compareTo((Stitcher$Holder)var1);
   }
}
