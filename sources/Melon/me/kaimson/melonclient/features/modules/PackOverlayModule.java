package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.*;
import com.google.common.collect.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;
import java.util.*;

public class PackOverlayModule extends IModuleRenderer
{
    private int width;
    private final Setting showIcon;
    private final Setting showDescription;
    private final Setting showBackground;
    
    public PackOverlayModule() {
        super("Pack Overlay");
        new Setting(this, "Pack Options");
        this.showIcon = new Setting(this, "Show icon").setDefault(true);
        this.showDescription = new Setting(this, "Show description").setDefault(true);
        this.showBackground = new Setting(this, "Show background").setDefault(true);
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.mc.R().c().size() * 32 + (this.mc.R().c().size() - 1) * 2;
    }
    
    @Override
    public void render(final float x, final float y) {
        bfl.E();
        this.width = 0;
        int offset = 0;
        for (final bnm.a entry : Lists.reverse(this.mc.R().c())) {
            this.width = Math.max(this.width, this.mc.k.a(entry.d()) + (this.showIcon.getBoolean() ? 38 : 4));
            if (this.showDescription.getBoolean()) {
                final List<String> list = (List<String>)this.mc.k.c(entry.e(), 157);
                for (int l = 0; l < 2 && l < list.size(); ++l) {
                    this.width = Math.max(this.width, this.mc.k.a((String)list.get(l)) + (this.showIcon.getBoolean() ? 38 : 4));
                }
            }
        }
        for (final bnm.a entry : Lists.reverse(this.mc.R().c())) {
            if (this.showBackground.getBoolean()) {
                GLRectUtils.drawRect(x, y + offset, x + this.width, y + offset + 32.0f, new Color(0, 0, 0, 100).getRGB());
            }
            if (this.showIcon.getBoolean()) {
                entry.a(this.mc.P());
                bfl.l();
                bfl.a(770, 771, 1, 0);
                bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
                GuiUtils.drawModalRectWithCustomSizedTexture(x, y + offset, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
                bfl.w();
            }
            FontUtils.drawString(entry.d(), x + (this.showIcon.getBoolean() ? 36 : 2), y + (this.showDescription.getBoolean() ? 0 : 10) + offset + 1.0f, 16777215, true);
            if (this.showDescription.getBoolean()) {
                final List<String> list = (List<String>)this.mc.k.c(entry.e(), 157);
                for (int l = 0; l < 2 && l < list.size(); ++l) {
                    FontUtils.drawString(list.get(l), x + (this.showIcon.getBoolean() ? 36 : 2), y + offset + 11.0f + 10 * l, 8421504, true);
                }
            }
            offset += 34;
        }
        bfl.F();
    }
}
