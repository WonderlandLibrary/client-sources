package cc.slack.ui.NewCGUI.components.impl;

import cc.slack.Slack;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.ui.NewCGUI.components.Components;
import cc.slack.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class CategoryComp extends Components {
   protected final List<ModuleComp> modules = new ArrayList();
   private int dragX;
   private int dragY;
   private boolean collapsed;
   private boolean dragging;
   private Category category;

   public CategoryComp(Category category, int posX, int posY, int width, int height) {
      this.category = category;
      this.setPosX(posX);
      this.setPosY(posY);
      this.setWidth(width);
      this.setHeight(height);
   }

   public void init() {
      int offsetY = 1;
      Module[] var2 = Slack.getInstance().getModuleManager().getModulesByCategoryABC(this.category);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         ModuleComp moduleComp = new ModuleComp(module, this, offsetY);
         this.modules.add(moduleComp);
         ++offsetY;
      }

   }

   public void update(int x, int y) {
      if (this.dragging) {
         this.setPosX(MathHelper.clamp_int(x - this.dragX, 0, this.getSR().getScaledWidth() - this.getWidth()));
         this.setPosY(MathHelper.clamp_int(y - this.dragY, 0, this.getSR().getScaledHeight() - this.getHeight()));
      }

   }

   public void draw(FontRenderer font, int mouseX, int mouseY, float partialTicks) {
      Gui.drawRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight(), this.getCategory().getColorRGB());
      font.drawStringWithShadow(this.getCategory().getName(), (float)this.getPosX() + 5.0F, (float)this.getPosY() + 4.0F, Color.WHITE.getRGB());
      font.drawStringWithShadow(this.isCollapsed() ? "+" : "-", (float)(this.getPosX() + this.getWidth()) - 10.0F, (float)this.getPosY() + 4.0F, Color.WHITE.getRGB());
      if (!this.isCollapsed()) {
         this.modules.forEach((moduleComp) -> {
            moduleComp.draw(font, mouseX, mouseY, partialTicks);
         });
      }

      this.update(mouseX, mouseY);
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (!this.isCollapsed()) {
         this.modules.forEach((moduleComp) -> {
            moduleComp.mouseClicked(mouseX, mouseY, button);
         });
      }

      if (Render2DUtil.mouseInArea(mouseX, mouseY, (double)this.getPosX(), (double)this.getPosY(), (double)this.getWidth(), (double)this.getHeight())) {
         switch(button) {
         case 0:
            this.dragging = true;
            this.dragX = mouseX - this.getPosX();
            this.dragY = mouseY - this.getPosY();
            break;
         case 1:
            this.collapsed = !this.collapsed;
         }

      }
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      this.dragging = false;
   }

   public void keyClicked(char typedChar, int key) {
   }

   public void close() {
      this.dragging = false;
   }

   public List<ModuleComp> getModules() {
      return this.modules;
   }

   public int getDragX() {
      return this.dragX;
   }

   public int getDragY() {
      return this.dragY;
   }

   public boolean isCollapsed() {
      return this.collapsed;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public Category getCategory() {
      return this.category;
   }

   public void setDragX(int dragX) {
      this.dragX = dragX;
   }

   public void setDragY(int dragY) {
      this.dragY = dragY;
   }

   public void setCollapsed(boolean collapsed) {
      this.collapsed = collapsed;
   }

   public void setDragging(boolean dragging) {
      this.dragging = dragging;
   }

   public void setCategory(Category category) {
      this.category = category;
   }
}
