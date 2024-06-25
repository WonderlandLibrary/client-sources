package cc.slack.ui.clickGUI.component.components.sub;

import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.ui.clickGUI.component.Component;
import cc.slack.ui.clickGUI.component.components.Button;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.MathUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Slider extends Component {
   private boolean hovered;
   private final NumberValue set;
   private final Button parent;
   private int offset;
   private int x;
   private int y;
   private boolean dragging = false;
   private double renderWidth;

   public Slider(NumberValue value, Button button, int offset) {
      this.set = value;
      this.parent = button;
      this.x = button.parent.getX() + button.parent.getWidth();
      this.y = button.parent.getY() + button.offset;
      this.offset = offset;
   }

   public void renderComponent() {
      Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
      Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 12, this.hovered ? -11184811 : -12303292);
      Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      mc.getFontRenderer().drawStringWithShadow(this.set.getName() + ": " + this.set.getValue(), (float)(this.parent.parent.getX() * 2 + 15), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
      GL11.glPopMatrix();
   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
      double diff = (double)Math.min(88, Math.max(0, mouseX - this.x));
      double min = this.set.getMinimum().doubleValue();
      double max = this.set.getMaximum().doubleValue();
      Number value = (Number)this.set.getValue();
      this.renderWidth = 88.0D * (value.doubleValue() - min) / (max - min);
      if (this.dragging) {
         if (diff == 0.0D) {
            this.set.setValue(this.set.getMinimum());
         } else {
            double roundMath = diff / 88.0D * (max - min) + min;
            if (this.set.getMinimum() instanceof Integer) {
               this.set.setValue((int)MathUtil.round(roundMath, 0));
            } else if (this.set.getMinimum() instanceof Double) {
               this.set.setValue(MathUtil.round(MathUtil.roundToDecimalPlace(roundMath, this.set.getIncrement().doubleValue()), 2));
            } else if (this.set.getMinimum() instanceof Float) {
               this.set.setValue((float)MathUtil.round(MathUtil.roundToDecimalPlace(roundMath, this.set.getIncrement().doubleValue()), 2));
            }
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
         this.dragging = true;
      }

      if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
         this.dragging = true;
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
      this.dragging = false;
   }

   public boolean isMouseOnButtonD(int x, int y) {
      return x > this.x && x < this.x + this.parent.parent.getWidth() / 2 + 1 && y > this.y && y < this.y + 12;
   }

   public boolean isMouseOnButtonI(int x, int y) {
      return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
   }
}
