package me.satisfactory.base.hero.clickgui.component.components.sub;

import me.satisfactory.base.hero.clickgui.component.*;
import me.satisfactory.base.hero.clickgui.component.components.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import me.satisfactory.base.module.*;

public class ModeButton extends Component
{
    int i;
    int count;
    private boolean hovered;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private Module mod;
    
    public ModeButton(final Button button, final Module mod, final int offset) {
        this.i = 0;
        this.count = 0;
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? new Color(41, 49, 62).brighter().brighter().getRGB() : new Color(41, 49, 62).brighter().getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString("Mode: " + this.mod.getMode().getName(), (this.parent.parent.getX() + 7) * 2, (this.parent.parent.getY() + this.offset + 2) * 2 + 5, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            if (this.count == this.mod.getModes().size() - 1) {
                this.count = 0;
            }
            else {
                ++this.count;
            }
            try {
                this.mod.setMode(this.mod.getModes().get(this.count));
            }
            catch (Exception e) {
                System.out.println("CRASH");
                this.count = 0;
            }
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}
