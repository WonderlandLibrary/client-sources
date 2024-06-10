package me.kaimson.melonclient.gui.settings;

import me.kaimson.melonclient.features.*;
import java.util.*;
import me.kaimson.melonclient.gui.*;
import org.lwjgl.opengl.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.utils.*;

public class GuiSettings extends SettingsBase
{
    private int row;
    private int gap;
    
    public GuiSettings(final axu parentScreen) {
        super(parentScreen);
    }
    
    @Override
    public void b() {
        this.row = 1;
        this.gap = this.getLayoutWidth(this.getMainWidth() / 6) / 8;
        this.elements.clear();
        this.components.clear();
        SettingsManager.INSTANCE.settings.forEach(setting -> {
            this.addSetting(setting, this.l / 2 - this.getWidth() / 2 + 35, (int)this.getRowHeight(this.row, 17));
            ++this.row;
            return;
        });
        super.b();
        this.registerScroll(new GuiModules.Scroll(SettingsManager.INSTANCE.settings, this.l, this.m, this.m / 2 - this.getHeight() / 2, this.m / 2 + this.getHeight() / 2, 17, this.l / 2 + this.getWidth() / 2 - 4, 1));
    }
    
    @Override
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawBackground();
        final avr sr = new avr(this.j);
        final int x = (this.l / 2 - this.getWidth() / 2) * sr.e();
        final int y = (this.m / 2 - this.getHeight() / 2 + 1) * sr.e();
        final int xWidth = (this.l / 2 + this.getWidth() / 2) * sr.e() - x;
        final int yHeight = (this.m / 2 + this.getHeight() / 2) * sr.e() - y;
        this.scissorFunc(sr, x, y, xWidth, yHeight);
        super.a(mouseX, mouseY, partialTicks);
        GL11.glDisable(3089);
    }
    
    @Override
    public void m() {
        super.m();
        GeneralConfig.INSTANCE.saveConfig();
    }
    
    private double getRowHeight(double row, final int buttonHeight) {
        --row;
        return this.m / 2 - this.getHeight() / 2 + 5 + row * buttonHeight;
    }
    
    private int getLayoutWidth(final int margin) {
        return this.l / 2 + this.getWidth() / 2 - margin - (this.l / 2 - this.getWidth() / 2 + 18 + margin);
    }
    
    private int getMainWidth() {
        return this.l / 2 + this.getWidth() / 2 - (this.l / 2 - this.getWidth() / 2 + 16);
    }
}
