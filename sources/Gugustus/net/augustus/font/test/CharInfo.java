package net.augustus.font.test;

import org.lwjgl.util.vector.Vector2f;

public class CharInfo {
   public int sourceX;
   public int sourceY;
   public int width;
   public int height;
   public Vector2f[] textureCoordinates = new Vector2f[4];

   public CharInfo(int sourceX, int sourceY, int width, int height) {
      this.sourceX = sourceX;
      this.sourceY = sourceY;
      this.width = width;
      this.height = height;
   }

   public void calculateTextureCoordinates(int fontWith, int fontHeight) {
      float x0 = (float)this.sourceX / (float)fontWith;
      float x1 = (float)(this.sourceX + this.width) / (float)fontWith;
      float y0 = (float)this.sourceY / (float)fontHeight;
      float y1 = (float)(this.sourceY + this.height) / (float)fontHeight;
      this.textureCoordinates[0] = new Vector2f(x0, y0);
      this.textureCoordinates[1] = new Vector2f(x0, y1);
      this.textureCoordinates[2] = new Vector2f(x1, y0);
      this.textureCoordinates[3] = new Vector2f(x1, y1);
   }
}
