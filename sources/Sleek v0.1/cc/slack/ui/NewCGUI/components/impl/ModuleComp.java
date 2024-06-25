package cc.slack.ui.NewCGUI.components.impl;

import cc.slack.features.modules.api.Module;
import cc.slack.ui.NewCGUI.components.Components;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.render.Render2DUtil;
import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class ModuleComp extends Components {
   private final Module module;
   private boolean expanded;

   public ModuleComp(Module module, Components parent, int offsetY) {
      this.module = module;
      this.setParent(parent);
      this.setOffsetY(offsetY);
   }

   public void init() {
   }

   public void update(int x, int y) {
   }

   public void draw(FontRenderer font, int mouseX, int mouseY, float partialTicks) {
      int boxPosY = this.getParent().getPosY() + this.getParent().getHeight() * this.getOffsetY();
      Gui.drawRect(this.getParent().getPosX(), boxPosY, this.getParent().getPosX() + this.getParent().getWidth(), boxPosY + this.getParent().getHeight(), (new Color(0, 0, 0, 100)).getRGB());
      font.drawStringWithShadow(this.module.getDisplayName(), (float)this.getParent().getPosX() + 5.0F, (float)boxPosY + 4.0F, Color.WHITE.getRGB());
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (Render2DUtil.mouseInArea(mouseX, mouseY, (double)this.getParent().getPosX(), (double)(this.getParent().getPosY() + this.getParent().getHeight() * this.getOffsetY()), (double)this.getParent().getWidth(), (double)this.getParent().getHeight())) {
         switch(button) {
         case 0:
            this.module.toggle();
            break;
         case 1:
            this.expanded = !this.expanded;
            PrintUtil.message(this.module.getDisplayName() + " " + this.expanded);
         }

      }
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
   }

   public void keyClicked(char typedChar, int key) {
   }

   public void close() {
   }

   public Module getModule() {
      return this.module;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public void setExpanded(boolean expanded) {
      this.expanded = expanded;
   }
}
