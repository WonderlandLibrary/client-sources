// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category.modules.settings.impl;

import dev.lvstrng.argon.clickgui.category.modules.ModuleComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.SettingComponent;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public final class EnumComponent extends SettingComponent {

    public final EnumSetting field55;
    private Color field56;

    public EnumComponent(final ModuleComponent parent, final Setting setting, final int offset) {
        super(parent, setting, offset);
        this.field55 = (EnumSetting) setting;
    }

    @Override
    public void method46(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        final int n = 0;
        super.method46(context, mouseX, mouseY, delta);
        final int n2 = n;
        final int x = this.method49() + 6;
        FontRenderer.drawText(this.field55.getName() + ": ", context, x, this.method50() + this.method53() + this.field41 + 6, new Color(245, 245, 245, 255).getRGB());
        FontRenderer.drawText(this.field55.current().name(), context, x + FontRenderer.getTextWidth(this.field55.getName() + ": "), this.method50() + this.method53() + this.field41 + 6, new Color(245, 245, 245, 255).getRGB());
        int field539;
        final int n3 = field539 = (this.field39.parent.field539 ? 1 : 0);
        if (n2 == 0) {
            if (n3 != 0) {
                return;
            }
            final boolean method55;
            field539 = ((method55 = this.method55(mouseX, mouseY)) ? 1 : 0);
        }
        if (n2 == 0) {
            if (n3 != 0) {
                field539 = 30;
            } else {
                field539 = 0;
            }
        }
        final int n4 = field539;
        EnumComponent class15 = this;
        Label_0262:
        {
            if (n2 == 0) {
                if (this.field56 == null) {
                    this.field56 = new Color(255, 255, 255, n4);
                    if (n2 == 0) {
                        break Label_0262;
                    }
                }
                class15 = this;
            }
            class15.field56 = new Color(255, 255, 255, this.field56.getAlpha());
        }
        EnumComponent class16 = this;
        Label_0294:
        {
            if (n2 == 0) {
                if (this.field56.getAlpha() == n4) {
                    break Label_0294;
                }
                class16 = this;
            }
            class16.field56 = ColorUtil.method523(0.05f, n4, this.field56);
        }
        context.fill(this.method49(), this.method50() + this.method53() + this.field41, this.method49() + this.method51(), this.method50() + this.method53() + this.field41 + this.method52(), this.field56.getRGB());
    }

    @Override
    public void method44(final double mouseX, final double mouseY, final int button) {
        if (this.method55(mouseX, mouseY) && button == 0) {
            this.field55.next();
        }
        super.method44(mouseX, mouseY, button);
    }
}
