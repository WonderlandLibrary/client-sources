package xyz.gucciclient.gui.parts;

import xyz.gucciclient.gui.component.*;
import xyz.gucciclient.gui.component.Component;
import xyz.gucciclient.values.*;
import xyz.gucciclient.modules.mods.render.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import xyz.gucciclient.utils.*;
import org.lwjgl.input.*;
import com.ibm.icu.math.*;

public class SliderPart extends Component
{
    private boolean hovered;
    private NumberValue value;
    private ModulesPart parent;
    private int offset;
    private int x;
    private Screen color;
    private int y;
    
    public SliderPart(final NumberValue value, final ModulesPart modulesPart, final int offset) {
        this.value = value;
        this.parent = modulesPart;
        this.x = modulesPart.parent.getX() + modulesPart.parent.getWidth();
        this.y = modulesPart.parent.getY() + modulesPart.offset;
        this.offset = offset;
    }
    
    @Override
    public void render() {
        final int drag = (int)(this.value.getValue() / this.value.getMax() * 70.0);
        Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 2, this.parent.parent.getY() + this.offset + 12, new Color(20, 20, 20, 150).getRGB());
        Gui.drawRect(this.parent.parent.getX() + 5, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 6, this.parent.parent.getY() + this.offset + 8, new Color(80, 80, 80, 120).getRGB());
        Gui.drawRect(this.parent.parent.getX() + 4, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 4 + drag + 2, this.parent.parent.getY() + this.offset + 8, new Color(255, 0, 0).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Wrapper.getMinecraft().fontRendererObj.drawString(String.valueOf(this.value.getName() + " : " + this.value.getValue()), this.parent.parent.getX() * 2 + 12, (this.parent.parent.getY() + this.offset - 1) * 2 + 6, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = (this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY));
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        if (this.hovered && this.parent.open && Mouse.isButtonDown(0)) {
            final double diff = mouseX - this.parent.parent.getX();
            final double value = this.round(diff / (this.parent.parent.getWidth() - 1) * this.value.getMax(), 1);
            this.value.setValue(value);
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            final NumberValue numberValue = this.value;
            final double value = numberValue.getValue() - 0.1;
            numberValue.setValue(Math.round(value * 10.0) / 10.0);
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            final NumberValue numberValue = this.value;
            final double value = numberValue.getValue() + 0.1;
            numberValue.setValue(Math.round(value * 10.0) / 10.0);
        }
    }
    
    private double round(final double doubleValue, final int numOfDecimals) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue);
        bigDecimal = bigDecimal.setScale(numOfDecimals, 4);
        return bigDecimal.doubleValue();
    }
    
    public boolean isMouseOnButtonD(final int x, final int y) {
        return x > this.x && x < this.x + (this.parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }
    
    public boolean isMouseOnButtonI(final int x, final int y) {
        return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}
