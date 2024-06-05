package ClassSub;

import cn.Hanabi.value.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.awt.*;

public class Class115 extends Class48<Value>
{
    
    
    public Class115(final Value value) {
        super(-1, -1, 0, 0, value);
    }
    
    @Override
    public void draw() {
        GL11.glTranslated((double)this.x, (double)this.y, 0.0);
        final Rectangle rectangle = new Rectangle(this.x, this.y, this.width, this.height);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        Class145.setColor(Class8.PANEL_MAIN_COLOR);
        final int n = rectangle.height - 4;
        if (((Class48<Value<Boolean>>)this).getValue().getValueState()) {
            Class145.setColor(Class8.PANEL_SECONDARY_COLOR);
            Class145.drawRect(7, 2, 2, n + 2, n + 2, Class8.PANEL_SECONDARY_COLOR.getRGB());
        }
        GL11.glLineWidth(2.0f);
        Class145.drawRect(2, 2, 2, n + 2, n + 2, Class8.PANEL_MAIN_COLOR.getRGB());
        if (rectangle.contains(Class170.calculateMouseLocation())) {
            GL11.glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
            Gui.drawRect(0, 0, rectangle.width, rectangle.height, Class145.toRGBA(new Color(0, 0, 0, Mouse.isButtonDown(0) ? 125 : 70)));
        }
        Class145.setColor(Color.white);
        this.fontRenderer.drawString(this.getValue().getValueName().split("_")[1], n + 6, rectangle.height / 2 - this.fontRenderer.FONT_HEIGHT / 2 + 1, Class145.toRGBA(Color.white));
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glTranslated((double)(-this.x), (double)(-this.y), 0.0);
    }
    
    @Override
    public boolean onMouseClick(final int n, final int n2, final int n3) {
        if (new Rectangle(this.getX(), this.getY(), this.width, this.height).contains(new Point(n, n2)) && n3 == 0) {
            ((Value)this.value).setValueState(!((Value)this.value).getValueState());
            return true;
        }
        return false;
    }
    
    @Override
    public void update() {
        this.width = this.fontRenderer.getStringWidth(((Value)this.value).getValueName().split("_")[1]) + this.fontRenderer.FONT_HEIGHT + 8;
        this.height = this.fontRenderer.FONT_HEIGHT + 4;
    }
}
