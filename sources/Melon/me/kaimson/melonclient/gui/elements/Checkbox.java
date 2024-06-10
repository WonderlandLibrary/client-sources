package me.kaimson.melonclient.gui.elements;

import java.util.function.*;
import java.awt.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import org.lwjgl.opengl.*;

public class Checkbox extends Element
{
    private int bgFade;
    public boolean active;
    private final String displayText;
    private final BiConsumer<Checkbox, Boolean> consumer;
    
    public Checkbox(final int x, final int y, final int width, final int height, final String displayText, final boolean active, final BiConsumer<Checkbox, Boolean> consumer) {
        super(x, y, width, height, null);
        this.bgFade = 50;
        this.displayText = displayText;
        this.active = active;
        this.consumer = consumer;
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        if (this.active) {
            this.renderCheckmark(0.5f, new Color(0, 0, 0, 50).getRGB());
            this.renderCheckmark(0.0f, new Color(255, 255, 255, 255).getRGB());
        }
        Client.textRenderer.drawString(this.displayText, this.getX() + this.width + 4, this.getY() + (this.height - 10) / 2, 16777215);
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 2.0f, Client.getMainColor(this.hovered ? 150 : 50));
        GLRectUtils.drawRoundedOutline(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 2.0f, 2.0f, Client.getMainColor(255));
    }
    
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.enabled && this.hovered) {
            this.active = !this.active;
            if (this.consumer != null) {
                this.consumer.accept(this, this.active);
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void update() {
        if (this.hovered && this.bgFade + 10 < 150) {
            this.bgFade += 10;
        }
        else if (!this.hovered && this.bgFade - 10 > 50) {
            this.bgFade -= 10;
        }
    }
    
    private void renderCheckmark(final float offset, final int color) {
        GuiUtils.setGlColor(color);
        bfl.x();
        GL11.glLineWidth(5.0f);
        GL11.glBegin(1);
        GL11.glVertex2f(this.getX() + 2 + offset, this.getY() + this.height / 2 + offset);
        GL11.glVertex2f(this.getX() + this.width / 3 + 1 + offset, this.getY() + this.height / 3 * 2 + 1 + offset);
        GL11.glVertex2f(this.getX() + this.width / 3 + 1 + offset, this.getY() + this.height / 3 * 2 + 1 + offset);
        GL11.glVertex2f(this.getX() + this.width - 2 + offset, this.getY() + 3 + offset);
        GL11.glEnd();
        bfl.w();
    }
}
