package me.kaimson.melonclient.gui.elements.settings.mode;

import me.kaimson.melonclient.gui.elements.settings.*;
import me.kaimson.melonclient.utils.*;
import java.util.function.*;
import me.kaimson.melonclient.gui.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import org.lwjgl.opengl.*;

public class SettingElementModeButton extends SettingElement
{
    private final boolean next;
    
    public SettingElementModeButton(final int x, final int y, final int width, final int height, final boolean next, final Setting setting, final BiConsumer<Setting, SettingElement> consumer) {
        super(x, y, width, height, setting, consumer, null);
        this.next = next;
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        GuiUtils.setGlColor(this.enabled ? (this.hovered ? Client.getMainColor(255) : Client.getMainColor(150)) : Client.getMainColor(80));
        bfl.x();
        GL11.glLineWidth(2.0f);
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex2i(this.getX() + (this.next ? 4 : (this.width - 4)), this.getY() + 1);
        GL11.glVertex2i(this.getX() + (this.next ? (this.width - 2) : 2), this.getY() + this.height / 2);
        GL11.glVertex2i(this.getX() + (this.next ? (this.width - 2) : 2), this.getY() + this.height / 2);
        GL11.glVertex2i(this.getX() + (this.next ? 4 : (this.width - 4)), this.getY() + this.height - 1);
        GL11.glEnd();
        bfl.w();
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
    }
    
    @Override
    protected void drawString() {
    }
    
    @Override
    public void update() {
        super.update();
    }
}
