package cc.slack.ui.NewCGUI.components;

import cc.slack.utils.client.mc;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Components {
   private int posX;
   private int posY;
   private int width;
   private int height;
   private int offsetY;
   private Components parent;

   public ScaledResolution getSR() {
      return new ScaledResolution(mc.getMinecraft());
   }

   public abstract void init();

   public abstract void update(int var1, int var2);

   public abstract void draw(FontRenderer var1, int var2, int var3, float var4);

   public abstract void mouseClicked(int var1, int var2, int var3);

   public abstract void mouseReleased(int var1, int var2, int var3);

   public abstract void keyClicked(char var1, int var2);

   public abstract void close();

   public int getPosX() {
      return this.posX;
   }

   public int getPosY() {
      return this.posY;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getOffsetY() {
      return this.offsetY;
   }

   public Components getParent() {
      return this.parent;
   }

   public void setPosX(int posX) {
      this.posX = posX;
   }

   public void setPosY(int posY) {
      this.posY = posY;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public void setOffsetY(int offsetY) {
      this.offsetY = offsetY;
   }

   public void setParent(Components parent) {
      this.parent = parent;
   }
}
