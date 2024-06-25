package cc.slack.ui.clickGUI.component.components.sub;

import cc.slack.ui.clickGUI.component.Component;
import cc.slack.ui.clickGUI.component.components.Button;
import cc.slack.utils.client.mc;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Keybind extends Component {
   private boolean hovered;
   private boolean binding;
   private final Button parent;
   private int offset;
   private int x;
   private int y;

   public Keybind(Button button, int offset) {
      this.parent = button;
      this.x = button.parent.getX() + button.parent.getWidth();
      this.y = button.parent.getY() + button.offset;
      this.offset = offset;
   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void renderComponent() {
      Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
      Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      mc.getFontRenderer().drawStringWithShadow(this.binding ? "Press a key..." : "Key: " + Keyboard.getKeyName(this.parent.mod.getKey()), (float)((this.parent.parent.getX() + 7) * 2), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
      GL11.glPopMatrix();
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButton(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
         this.binding = !this.binding;
      }

   }

   public void keyTyped(char typedChar, int key) {
      if (this.binding) {
         this.parent.mod.setKey(key);
         this.binding = false;
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
   }
}
