package me.kaimson.melonclient.gui.elements.settings.mode;

import me.kaimson.melonclient.gui.elements.settings.*;
import me.kaimson.melonclient.utils.*;
import java.util.function.*;
import me.kaimson.melonclient.gui.*;
import me.kaimson.melonclient.*;

public class SettingElementMode extends SettingElement
{
    private final SettingElementModeButton prev;
    private final SettingElementModeButton next;
    public int mode;
    
    public SettingElementMode(final int x, final int y, final int xOffset, final int width, final int height, final int mode, final Setting setting, final BiConsumer<Setting, SettingElement> consumer, final GuiScreen parent) {
        super(x, y, width, height, setting, consumer, parent);
        this.mode = mode;
        this.setXOffset(xOffset);
        this.front = true;
        parent.elements.add(this.prev = new SettingElementModeButton(this.getX(), this.getY(), 10, height, false, setting, (n, n1) -> {
            --this.mode;
            this.updateMode();
            return;
        }));
        parent.elements.add(this.next = new SettingElementModeButton(this.getX() + width - 10, this.getY(), 10, height, true, setting, (n, n1) -> {
            ++this.mode;
            this.updateMode();
            return;
        }));
        this.updateMode();
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        Client.titleRenderer.drawString(this.setting.getValue().get(this.mode + 1), this.getX() + (int)(this.width - Client.titleRenderer.getWidth(this.setting.getValue().get(this.mode + 1))) / 2, this.getY() + (this.height - 10) / 2, 16777215);
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
    }
    
    @Override
    public void update() {
    }
    
    private void updateMode() {
        if (this.mode == 0) {
            this.prev.enabled = false;
        }
        else {
            this.prev.enabled = true;
        }
        if (this.mode == this.setting.getValue().size() - 2) {
            this.next.enabled = false;
        }
        else {
            this.next.enabled = true;
        }
    }
}
