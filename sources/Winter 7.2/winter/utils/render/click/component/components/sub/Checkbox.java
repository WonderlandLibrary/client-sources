/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.click.component.components.sub;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.utils.Render;
import winter.utils.file.FileManager;
import winter.utils.render.click.component.Component;
import winter.utils.render.click.component.Frame;
import winter.utils.render.click.component.components.Button;
import winter.utils.value.types.BooleanValue;

public class Checkbox
extends Component {
    private boolean hovered;
    private BooleanValue op;
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

    @Override
    public void renderComponent() {
        Render.drawBorderedRect(this.parent.parent.getX() + 2, (double)(this.parent.parent.getY() + this.offset) + 0.5, this.parent.parent.getX() + this.parent.parent.getWidth() * 1 - 2, (double)(this.parent.parent.getY() + this.offset) + 13.5, 0.5, this.hovered ? (this.op.isEnabled() ? -15620650 : -11513776) : (this.op.isEnabled() ? -15820860 : -12237499), -13421773);
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawStringShadowed(this.op.getName(), this.parent.parent.getX() + 4, (float)(this.parent.parent.getY() + this.offset + 2) + 1.0f, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.op.toggle();
            if (Client.fileManager != null) {
                Client.fileManager.saveFiles();
            }
        }
    }

    public boolean isMouseOnButton(int x2, int y2) {
        if (x2 > this.x && x2 < this.x + 88 && y2 > this.y && y2 < this.y + 14) {
            return true;
        }
        return false;
    }
}

