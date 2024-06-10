package me.kaimson.melonclient.gui.elements.settings;

import me.kaimson.melonclient.gui.elements.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.gui.*;
import java.util.function.*;
import me.kaimson.melonclient.*;

public class SettingElement extends Element
{
    protected final Setting setting;
    protected final BiConsumer<Setting, SettingElement> consumer;
    protected boolean front;
    public int yOffset;
    
    public SettingElement(final int x, final int y, final int width, final int height, final Setting setting, final BiConsumer<Setting, SettingElement> consumer, final GuiScreen parent) {
        super(x, y, width, height, true, null, parent);
        this.setting = setting;
        this.consumer = consumer;
    }
    
    public SettingElement(final int x, final int y, final int width, final int height, final int xOffset, final int yOffset, final Setting setting, final BiConsumer<Setting, SettingElement> consumer, final GuiScreen parent) {
        super(x, y, width, height, true, null, parent);
        this.setting = setting;
        this.consumer = consumer;
        this.setXOffset(xOffset);
        this.setYOffset(yOffset);
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.hovered(this.mouseX = mouseX, this.mouseY = mouseY);
        this.renderBackground(partialTicks);
        this.renderElement(partialTicks);
        if (this.setting.getDescription() != null) {
            this.drawString();
        }
        if (this.enabled) {
            this.mouseDragged(mouseX, mouseY);
        }
    }
    
    protected void drawString() {
        Client.titleRenderer.drawString(this.setting.getDescription(), (float)(this.x + (this.front ? 0 : (this.width + 7))), this.getY() + (this.height - 10) / 2.0f + this.yOffset, 16777215);
    }
    
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.enabled && this.hovered && this.consumer != null) {
            this.consumer.accept(this.setting, this);
        }
        return this.enabled && this.hovered;
    }
}
