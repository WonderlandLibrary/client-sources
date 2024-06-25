package cc.slack.ui.clickGUI.component.components.sub;

import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.ui.clickGUI.component.Component;
import cc.slack.ui.clickGUI.component.components.Button;
import cc.slack.utils.client.mc;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Checkbox extends Component {
   private boolean hovered;
   private final BooleanValue op;
   private Button parent;
   private int offset;
   private int x;
   private int y;

   public Checkbox(BooleanValue option, Button button, int offset) {
      this.op = option;
      this.parent = button;
      this.x = button.parent.getX() + button.parent.getWidth();
      this.y = button.parent.getY() + button.offset;
      this.offset = offset;
   }

   public void renderComponent() {
      Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
      Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      mc.getFontRenderer().drawStringWithShadow(this.op.getName(), (float)((this.parent.parent.getX() + 10 + 4) * 2 + 5), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 4), -1);
      GL11.glPopMatrix();
      Gui.drawRect(this.parent.parent.getX() + 3 + 4, this.parent.parent.getY() + this.offset + 3, this.parent.parent.getX() + 9 + 4, this.parent.parent.getY() + this.offset + 9, -6710887);
      if ((Boolean)this.op.getValue()) {
         Gui.drawRect(this.parent.parent.getX() + 4 + 4, this.parent.parent.getY() + this.offset + 4, this.parent.parent.getX() + 8 + 4, this.parent.parent.getY() + this.offset + 8, -10066330);
      }

   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButton(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
         this.op.setValue(!(Boolean)this.op.getValue());
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
   }
}
