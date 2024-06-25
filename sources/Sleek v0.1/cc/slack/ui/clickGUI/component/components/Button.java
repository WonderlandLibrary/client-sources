package cc.slack.ui.clickGUI.component.components;

import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.ui.clickGUI.component.Component;
import cc.slack.ui.clickGUI.component.Frame;
import cc.slack.ui.clickGUI.component.components.sub.Checkbox;
import cc.slack.ui.clickGUI.component.components.sub.Keybind;
import cc.slack.ui.clickGUI.component.components.sub.ModeButton;
import cc.slack.ui.clickGUI.component.components.sub.Slider;
import cc.slack.utils.client.mc;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Button extends Component {
   public Module mod;
   public Frame parent;
   public int offset;
   private boolean isHovered;
   private final ArrayList<Component> subcomponents;
   public boolean open;

   public Button(Module mod, Frame parent, int offset) {
      this.mod = mod;
      this.parent = parent;
      this.offset = offset;
      this.subcomponents = new ArrayList();
      this.open = false;
      int opY = offset + 12;
      if (mod.getSetting() != null) {
         Iterator var5 = mod.getSetting().iterator();

         while(var5.hasNext()) {
            Value value = (Value)var5.next();
            if (value instanceof ModeValue) {
               this.subcomponents.add(new ModeButton((ModeValue)value, this, mod, opY));
               opY += 12;
            }

            if (value instanceof NumberValue) {
               this.subcomponents.add(new Slider((NumberValue)value, this, opY));
               opY += 12;
            }

            if (value instanceof BooleanValue) {
               this.subcomponents.add(new Checkbox((BooleanValue)value, this, opY));
               opY += 12;
            }
         }
      }

      this.subcomponents.add(new Keybind(this, opY));
   }

   public void setOff(int newOff) {
      this.offset = newOff;
      int opY = this.offset + 12;

      for(Iterator var3 = this.subcomponents.iterator(); var3.hasNext(); opY += 12) {
         Component comp = (Component)var3.next();
         comp.setOff(opY);
      }

   }

   public void renderComponent() {
      Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggle() ? (new Color(-14540254)).darker().getRGB() : -14540254) : (this.mod.isToggle() ? (new Color(14, 14, 14)).getRGB() : -15658735));
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      mc.getFontRenderer().drawStringWithShadow(this.mod.getName(), (float)((this.parent.getX() + 2) * 2), (float)((this.parent.getY() + this.offset + 2) * 2 + 4), this.mod.isToggle() ? 10066329 : -1);
      if (this.subcomponents.size() > 2) {
         mc.getFontRenderer().drawStringWithShadow(this.open ? "-" : "+", (float)((this.parent.getX() + this.parent.getWidth() - 10) * 2), (float)((this.parent.getY() + this.offset + 2) * 2 + 4), -1);
      }

      GL11.glPopMatrix();
      if (this.open && !this.subcomponents.isEmpty()) {
         this.subcomponents.forEach(Component::renderComponent);
         Gui.drawRect(this.parent.getX() + 2, this.parent.getY() + this.offset + 12, this.parent.getX() + 3, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, -379081);
      }

   }

   public int getHeight() {
      return this.open ? 12 * (this.subcomponents.size() + 1) : 12;
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.isHovered = this.isMouseOnButton(mouseX, mouseY);
      if (!this.subcomponents.isEmpty()) {
         this.subcomponents.forEach((comp) -> {
            comp.updateComponent(mouseX, mouseY);
         });
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY)) {
         switch(button) {
         case 0:
            this.mod.toggle();
            break;
         case 1:
            this.open = !this.open;
            this.parent.refresh();
         }
      }

      this.subcomponents.forEach((comp) -> {
         comp.mouseClicked(mouseX, mouseY, button);
      });
   }

   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
      this.subcomponents.forEach((comp) -> {
         comp.mouseReleased(mouseX, mouseY, mouseButton);
      });
   }

   public void keyTyped(char typedChar, int key) {
      this.subcomponents.forEach((comp) -> {
         comp.keyTyped(typedChar, key);
      });
   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
   }
}
