// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category.modules.settings.impl;

import dev.lvstrng.argon.auth.Authentication;
import dev.lvstrng.argon.clickgui.category.modules.ModuleComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.SettingComponent;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public final class BooleanComponent extends SettingComponent {

    private final BooleanSetting field51;
    private Color field52;

    public BooleanComponent(final ModuleComponent parent, final Setting setting, final int offset) {
        super(parent, setting, offset);
        this.field51 = (BooleanSetting) setting;
    }


    @Override
    public void method46(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        final int n = 0;
        super.method46(context, mouseX, mouseY, delta);
        final int n2 = n;
        FontRenderer.drawText(this.field51.getName(), context, this.method49() + 31, this.method50() + this.method53() + this.field41 + 6, new Color(245, 245, 245, 255).getRGB());
        context.fillGradient(this.method49() + 5, this.method50() + this.method53() + this.field41 + 5, this.method49() + 25, this.method50() + this.method53() + this.field41 + this.method52() - 5, Authentication.method331(255, this.field39.field448.indexOf(this)).getRGB(), Authentication.method331(255, this.field39.field448.indexOf(this) + 1).getRGB());
        DrawContext drawContext = context;
        final int n3 = this.method49() + 7;
        final int n4 = this.method50() + this.method53() + this.field41 + 7;
        final int n5 = this.method49() + 23;
        final int n6 = this.method50() + this.method53() + this.field41 + this.method52() - 7;
        final Color darkGray = Color.darkGray;
        int n11 = 0;
        Label_0334:
        {
            if (n2 == 0) {
                context.fill(n3, n4, n5, n6, darkGray.getRGB());
                drawContext = context;
                final int n7 = this.method49() + 9;
                final int n8 = this.method50() + this.method53() + this.field41 + 9;
                final int n9 = this.method49() + 21;
                final int n10 = this.method50() + this.method53() + this.field41 + this.method52() - 9;
                if (this.field51.getValue()) {
                    n11 = Authentication.method331(255, this.field39.field448.indexOf(this)).getRGB();
                    break Label_0334;
                }
                final Color darkGray2 = Color.darkGray;
            }
            n11 = darkGray.getRGB();
        }
        final int method42 = this.field51.getValue() ? 1 : 0;
        if (n2 == 0) {
            if (method42 != 0) {
                Authentication.method331(255, this.field39.field448.indexOf(this) + 1).getRGB();
            } else {
                Color.darkGray.getRGB();
            }
        }
        drawContext.fillGradient(n3, n4, n5, n6, n11, method42);
        int field539;
        final int n12 = field539 = (this.field39.field449.field539 ? 1 : 0);
        if (n2 == 0) {
            if (n12 != 0) {
                return;
            }
            final boolean method43;
            field539 = ((method43 = this.method55(mouseX, mouseY)) ? 1 : 0);
        }
        if (n2 == 0) {
            if (n12 != 0) {
                field539 = 30;
            } else {
                field539 = 0;
            }
        }
        final int n13 = field539;
        BooleanComponent class14 = this;
        Label_0486:
        {
            if (n2 == 0) {
                if (this.field52 == null) {
                    this.field52 = new Color(255, 255, 255, n13);
                    if (n2 == 0) {
                        break Label_0486;
                    }
                }
                class14 = this;
            }
            class14.field52 = new Color(255, 255, 255, this.field52.getAlpha());
        }
        BooleanComponent class15 = this;
        Label_0518:
        {
            if (n2 == 0) {
                if (this.field52.getAlpha() == n13) {
                    break Label_0518;
                }
                class15 = this;
            }
            class15.field52 = ColorUtil.method523(0.05f, n13, this.field52);
        }
        context.fill(this.method49(), this.method50() + this.method53() + this.field41, this.method49() + this.method51(), this.method50() + this.method53() + this.field41 + this.method52(), this.field52.getRGB());
    }

    @Override
    public void method44(final double mouseX, final double mouseY, final int button) {
        if (this.method55(mouseX, mouseY) && button == 0) {
            this.field51.toggle();
        }
        super.method44(mouseX, mouseY, button);
    }
}
