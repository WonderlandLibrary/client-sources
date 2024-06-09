/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.click.component.components.sub;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.utils.Render;
import winter.utils.file.FileManager;
import winter.utils.render.click.component.Component;
import winter.utils.render.click.component.Frame;
import winter.utils.render.click.component.components.Button;
import winter.utils.value.types.NumberValue;

public class Slider
extends Component {
    private boolean hovered;
    private NumberValue val;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging = false;
    private double renderWidth;

    public Slider(NumberValue value, Button button, int offset) {
        this.val = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Render.drawBorderedRect(this.parent.parent.getX() + 2, (double)(this.parent.parent.getY() + this.offset) + 0.5, this.parent.parent.getX() + this.parent.parent.getWidth() * 1 - 2, (double)(this.parent.parent.getY() + this.offset) + 13.5, 0.5, this.hovered ? -11513776 : -12237499, -13421773);
        int drag = (int)(this.val.getValue() / this.val.getMax() * (double)this.parent.parent.getWidth());
        Gui.drawRect(this.parent.parent.getX() + 2.5, this.parent.parent.getY() + this.offset + 1, (double)(this.parent.parent.getX() + (int)this.renderWidth) - 0.5, this.parent.parent.getY() + this.offset + 13, this.hovered ? -15620650 : -15820860);
        GL11.glPushMatrix();
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        Minecraft.getMinecraft().fontRendererObj.drawStringShadowed(String.valueOf(this.val.getName()) + ": " + this.val.getValue(), (float)this.parent.parent.getX() * 1.25f + 5.0f, (float)(this.parent.parent.getY() + this.offset + 2) * 1.25f + 2.5f, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        double diff = Math.min(this.parent.parent.getWidth() - 2, Math.max(0, mouseX - this.x));
        double min = this.val.getMin();
        double max = this.val.getMax();
        this.renderWidth = (double)(this.parent.parent.getWidth() - 2) * (this.val.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.val.setVal(this.val.getMin());
            } else {
                double newValue = Slider.roundToPlace(diff / (double)(this.parent.parent.getWidth() - 2) * (max - min) + min, 2);
                this.val.setVal(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
        if (Client.fileManager != null) {
            Client.fileManager.saveFiles();
        }
    }

    public boolean isMouseOnButtonD(int x2, int y2) {
        if (x2 > this.x && x2 < this.x + (this.parent.parent.getWidth() / 2 + 1) && y2 > this.y && y2 < this.y + 14) {
            return true;
        }
        return false;
    }

    public boolean isMouseOnButtonI(int x2, int y2) {
        if (x2 > this.x + this.parent.parent.getWidth() / 2 && x2 < this.x + this.parent.parent.getWidth() && y2 > this.y && y2 < this.y + 12) {
            return true;
        }
        return false;
    }
}

