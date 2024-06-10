package me.kaimson.melonclient.gui.elements.settings;

import java.util.function.*;
import me.kaimson.melonclient.gui.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.*;
import org.lwjgl.input.*;
import me.kaimson.melonclient.gui.utils.*;

public class SettingElementKeybind extends SettingElement
{
    public int keycode;
    public boolean selection;
    private int bgFade;
    
    public SettingElementKeybind(final int x, final int y, final int width, final int height, final Setting setting, final BiConsumer<Setting, SettingElement> consumer, final GuiScreen parent) {
        super(x, y, width, height, setting, consumer, parent);
        this.selection = false;
        this.bgFade = 50;
        this.front = true;
        this.keycode = ((KeyBinding)setting.getObject()).getKeyCode();
        this.width = (int)Math.max(10.0f, Client.titleRenderer.getWidth(Keyboard.getKeyName(this.keycode)) + 4.0f);
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        Client.titleRenderer.drawCenteredString(this.selection ? "><" : Keyboard.getKeyName(this.keycode), this.getX() + this.width / 2.0f - 1.0f, (float)this.getY(), 16777215);
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 2.0f, Client.getMainColor(this.bgFade));
        GLRectUtils.drawRoundedOutline(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 2.0f, 2.0f, Client.getMainColor(255));
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
    
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.enabled && this.hovered) {
            this.selection = true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.selection) {
            if (keyCode == 1) {
                this.selection = false;
                return;
            }
            this.keycode = keyCode;
            this.width = (int)Math.max(10.0f, Client.titleRenderer.getWidth(Keyboard.getKeyName(keyCode)) + 4.0f);
            if (this.consumer != null) {
                this.consumer.accept(this.setting, this);
            }
            this.selection = false;
        }
    }
}
