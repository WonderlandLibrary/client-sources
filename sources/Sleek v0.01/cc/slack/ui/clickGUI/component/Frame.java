package cc.slack.ui.clickGUI.component;

import cc.slack.Slack;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.ui.clickGUI.component.components.Button;
import cc.slack.utils.client.mc;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class Frame {
   public ArrayList<Component> components = new ArrayList();
   public Category category;
   private boolean open;
   private final int width;
   private int y;
   private int x;
   private final int height;
   private boolean isDragging;
   public int dragX;
   public int dragY;

   public Frame(Category cat) {
      this.category = cat;
      this.width = 88;
      this.x = 5;
      this.y = 5;
      this.height = 13;
      this.dragX = 0;
      this.open = false;
      this.isDragging = false;
      int tY = this.height;
      Module[] var3 = Slack.getInstance().getModuleManager().getModulesByCategory(this.category);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Module mod = var3[var5];
         Button modButton = new Button(mod, this, tY);
         this.components.add(modButton);
         tY += 12;
      }

   }

   public ArrayList<Component> getComponents() {
      return this.components;
   }

   public void setX(int newX) {
      this.x = newX;
   }

   public void setY(int newY) {
      this.y = newY;
   }

   public void setDrag(boolean drag) {
      this.isDragging = drag;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }

   public void renderFrame(FontRenderer fontRenderer) {
      Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.category.getColorRGB());
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      fontRenderer.drawStringWithShadow(this.category.name(), (float)((this.x + 2) * 2 + 5), ((float)this.y + 2.5F) * 2.0F + 5.0F, -1);
      fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (float)((this.x + this.width - 10) * 2 + 5), ((float)this.y + 2.5F) * 2.0F + 5.0F, -1);
      GL11.glPopMatrix();
      if (this.open && !this.components.isEmpty()) {
         this.components.forEach(Component::renderComponent);
      }

   }

   public void refresh() {
      int off = this.height;

      Component comp;
      for(Iterator var2 = this.components.iterator(); var2.hasNext(); off += comp.getHeight()) {
         comp = (Component)var2.next();
         comp.setOff(off);
      }

   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void updatePosition(int mouseX, int mouseY) {
      ScaledResolution sr = new ScaledResolution(mc.getMinecraft());
      if (this.isDragging) {
         this.setX(MathHelper.clamp_int(mouseX - this.dragX, 0, sr.getScaledWidth() - this.getWidth()));
         this.setY(MathHelper.clamp_int(mouseY - this.dragY, 0, sr.getScaledHeight() - this.getHeight()));
      }

   }

   public boolean isWithinHeader(int x, int y) {
      return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
   }
}
