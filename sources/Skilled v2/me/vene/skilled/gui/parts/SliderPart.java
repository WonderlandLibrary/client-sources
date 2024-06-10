 package me.vene.skilled.gui.parts;
 
 import com.ibm.icu.math.BigDecimal;
 import me.vene.skilled.gui.component.Component;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import org.lwjgl.input.Mouse;
 import org.lwjgl.opengl.GL11;

public class SliderPart extends Component
{
    private NumberValue value;
    private ModulesPart parent;
    private int offset;
    private int x;
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
        final int drag = (int)(this.value.getValue() / this.value.getMax() * 104.0);
        Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 2, this.parent.parent.getY() + this.offset + 12, ClickGUI.getModuleColor());
        Gui.drawRect(this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset + 9, this.parent.parent.getX() + 1 + drag + 1, this.parent.parent.getY() + this.offset + 11, ClickGUI.getColor());
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawString(StringRegistry.register(this.value.getName()), this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset, -1);
        Minecraft.getMinecraft().fontRendererObj.drawString(StringRegistry.register(String.valueOf(this.value.getValue())), this.parent.parent.getX() + this.parent.parent.getWidth() - 3 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf(this.value.getValue())), this.parent.parent.getY() + this.offset, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        final boolean hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        if (hovered && !this.parent.parent.isDragging && this.parent.open && Mouse.isButtonDown(0)) {
            final int drag = (int)(this.value.getMin() / this.value.getMax() * 104.0);
            final double diff = mouseX - this.x;
            final double value = this.round(MathUtil.map(diff, drag, this.parent.parent.getWidth() - 5, this.value.getMin(), this.value.getMax()));
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
    
    private double round(final double doubleValue) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue);
        bigDecimal = bigDecimal.setScale(2, 4);
        return bigDecimal.doubleValue();
    }
    
    public boolean isMouseOnButtonD(final int x, final int y) {
        return x > this.x && x < this.x + (this.parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }
    
    public boolean isMouseOnButtonI(final int x, final int y) {
        return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}
