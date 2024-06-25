package cc.slack.ui.clickGUI.component.components.sub;

import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.ui.clickGUI.component.Component;
import cc.slack.ui.clickGUI.component.components.Button;
import cc.slack.utils.client.mc;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class ModeButton extends Component {
   private boolean hovered;
   private final Button parent;
   private final ModeValue set;
   private int offset;
   private int x;
   private int y;
   private final Module mod;
   private int modeIndex;

   public ModeButton(ModeValue set, Button button, Module mod, int offset) {
      this.set = set;
      this.parent = button;
      this.mod = mod;
      this.x = button.parent.getX() + button.parent.getWidth();
      this.y = button.parent.getY() + button.offset;
      this.offset = offset;
      this.modeIndex = 0;
   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void renderComponent() {
      Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
      Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      String prefix = this.set.getName() == null ? "Mode: " : this.set.getName() + " Mode: ";
      mc.getFontRenderer().drawStringWithShadow(prefix + this.set.getValue().toString(), (float)((this.parent.parent.getX() + 7) * 2), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
      GL11.glPopMatrix();
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButton(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && this.parent.open) {
         int maxIndex = this.set.getModes().length - 1;
         switch(button) {
         case 0:
            if (this.modeIndex + 1 > maxIndex) {
               this.modeIndex = 0;
            } else {
               ++this.modeIndex;
            }
            break;
         case 1:
            if (this.modeIndex - 1 < 0) {
               this.modeIndex = maxIndex;
            } else {
               --this.modeIndex;
            }
         }

         this.set.setValueFromString(this.set.getModes()[this.modeIndex].toString());
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
   }
}
