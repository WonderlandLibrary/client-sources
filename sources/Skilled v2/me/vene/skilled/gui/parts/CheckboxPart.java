 package me.vene.skilled.gui.parts;

 import java.awt.Color;
 import me.vene.skilled.gui.component.Component;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.utilities.RenderUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import org.lwjgl.opengl.GL11;
 
public class CheckboxPart extends Component
{
    private BooleanValue op;
    private ModulesPart parent;
    private int offset;
    private int x;
    private int y;
    
    public CheckboxPart(final BooleanValue option, final ModulesPart modulesPart, final int offset) {
        this.op = option;
        this.parent = modulesPart;
        this.x = modulesPart.parent.getX() + modulesPart.parent.getWidth();
        this.y = modulesPart.parent.getY() + modulesPart.offset;
        this.offset = offset;
    }
    
    @Override
    public void render() {
        Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, ClickGUI.getModuleColor());
        Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() + 12 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, ClickGUI.getModuleColor());
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawString(StringRegistry.register(this.op.getName()), this.parent.parent.getX() + 15, this.parent.parent.getY() + this.offset, -1);
        GL11.glPopMatrix();
        Gui.drawRect(this.parent.parent.getX() + 4, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 12, this.parent.parent.getY() + this.offset + 8, new Color(0, 0, 0, 255).getRGB());
        if (this.op.getState()) {
            RenderUtil.drawCheckmark((float)(this.parent.parent.getX() + 4.5), (float)(this.parent.parent.getY() + this.offset + 3), -1);
        }
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        final boolean hovered = this.isMouseOnButton(mouseX, mouseY);
        if (hovered && button == 0 && this.parent.open) {
            this.op.toggle();
        }
    }
    
    private boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 105 && y > this.y && y < this.y + 12;
    }
}
