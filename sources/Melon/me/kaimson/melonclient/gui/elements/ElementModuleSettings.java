package me.kaimson.melonclient.gui.elements;

import me.kaimson.melonclient.features.*;
import java.util.function.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.*;

public class ElementModuleSettings extends Element
{
    private final jy ICON;
    private final int textureIndex;
    private final Module module;
    private int fadeIcon;
    
    public ElementModuleSettings(final int x, final int y, final int width, final int height, final String iconLocation, final int textureIndex, final boolean shouldScissor, final Module module, final Consumer<Element> consumer) {
        super(x, y, width, height, shouldScissor, consumer);
        this.textureIndex = textureIndex;
        this.ICON = new jy("melonclient/" + iconLocation);
        this.module = module;
        this.fadeIcon = 175;
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        this.mc.P().a(this.ICON);
        final int b = 8;
        bfl.l();
        GuiUtils.setGlColor(new Color(0, 0, 0, 75).getRGB());
        GuiUtils.drawScaledCustomSizeModalRect(this.getX() + (this.width - b) / 2.0f + 0.75f, this.getY() + (this.height - b) / 2.0f + 0.75f, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
        GuiUtils.setGlColor(ModuleConfig.INSTANCE.isEnabled(this.module) ? (this.hovered ? new Color(255, 255, 255, this.fadeIcon).getRGB() : Client.getMainColor(this.fadeIcon)) : new Color(120, 120, 120, this.fadeIcon).getRGB());
        GuiUtils.a(this.getX() + (this.width - b) / 2, this.getY() + (this.height - b) / 2, 0.0f, 0.0f, b, b, b, b, (float)b, (float)b);
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
    }
    
    @Override
    public void update() {
        if (this.hovered && this.fadeIcon + 5 <= 255) {
            this.fadeIcon += 5;
        }
        else if (!this.hovered && this.fadeIcon - 5 >= 175) {
            this.fadeIcon -= 5;
        }
    }
}
