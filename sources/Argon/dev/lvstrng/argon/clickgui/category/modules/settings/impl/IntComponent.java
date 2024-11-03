// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category.modules.settings.impl;

import dev.lvstrng.argon.auth.Authentication;
import dev.lvstrng.argon.clickgui.category.modules.ModuleComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.SettingComponent;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.RandomUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public final class IntComponent extends SettingComponent {

    private final IntSetting field63;
    public boolean field61;
    public double field62;
    public Color field64;
    public Color field65;
    private Color field66;
    private Color field67;
    private Color field68;

    public IntComponent(final ModuleComponent parent, final Setting setting, final int offset) {
        super(parent, setting, offset);
        this.field63 = (IntSetting) setting;
    }

    @Override
    public void method56() {
        final int n = 0;
        this.field67 = Authentication.method331(0, this.field39.field448.indexOf(this)).darker();
        final int n2 = n;
        this.field68 = Authentication.method331(0, this.field39.field448.indexOf(this) + 1).darker();
        IntComponent class16 = this;
        Label_0140:
        {
            if (n2 == 0) {
                if (this.field64 == null) {
                    this.field64 = new Color(this.field67.getRed(), this.field67.getGreen(), this.field67.getBlue(), 0);
                    if (n2 == 0) {
                        break Label_0140;
                    }
                }
                class16 = this;
            }
            class16.field64 = new Color(this.field67.getRed(), this.field67.getGreen(), this.field67.getBlue(), this.field64.getAlpha());
        }
        IntComponent class17 = this;
        Label_0227:
        {
            if (n2 == 0) {
                if (this.field65 == null) {
                    this.field65 = new Color(this.field68.getRed(), this.field68.getGreen(), this.field68.getBlue(), 0);
                    if (n2 == 0) {
                        break Label_0227;
                    }
                }
                class17 = this;
            }
            class17.field65 = new Color(this.field68.getRed(), this.field68.getGreen(), this.field68.getBlue(), this.field65.getAlpha());
        }
        final int alpha = this.field64.getAlpha();
        final int n3 = 255;
        IntComponent class18 = null;
        Label_0290:
        {
            if (n2 == 0) {
                if (alpha != n3) {
                    this.field64 = ColorUtil.method523(0.05f, 255, this.field64);
                }
                class18 = this;
                if (n2 != 0) {
                    break Label_0290;
                }
                this.field65.getAlpha();
            }
            if (alpha != n3) {
                this.field65 = ColorUtil.method523(0.05f, 255, this.field65);
            }
            class18 = this;
        }
        class18.method56();
    }

    @Override
    public void method46(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        final int n = 0;
        super.method46(context, mouseX, mouseY, delta);
        this.field62 = (this.field63.getValue() - this.field63.getMin()) / (this.field63.getMax() - this.field63.getMin()) * this.method51();
        context.fillGradient(this.method49(), this.method50() + this.field41 + this.method53() + 25, (int) (this.method49() + this.field62), this.method50() + this.field41 + this.method53() + this.method52(), this.field64.getRGB(), this.field65.getRGB());
        FontRenderer.drawText(this.field63.getName() + "" + this.field63.getValue(), context, this.method49() + 5, this.method50() + this.method53() + this.field41 + 6, new Color(245, 245, 245, 255).getRGB());
        final int n2 = n;
        int field539;
        final int n3 = field539 = (this.field39.field449.field539 ? 1 : 0);
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
        IntComponent class16 = this;
        Label_0297:
        {
            if (n2 == 0) {
                if (this.field66 == null) {
                    this.field66 = new Color(255, 255, 255, n4);
                    if (n2 == 0) {
                        break Label_0297;
                    }
                }
                class16 = this;
            }
            class16.field66 = new Color(255, 255, 255, this.field66.getAlpha());
        }
        IntComponent class17 = this;
        Label_0329:
        {
            if (n2 == 0) {
                if (this.field66.getAlpha() == n4) {
                    break Label_0329;
                }
                class17 = this;
            }
            class17.field66 = ColorUtil.method523(0.05f, n4, this.field66);
        }
        context.fill(this.method49(), this.method50() + this.method53() + this.field41, this.method49() + this.method51(), this.method50() + this.method53() + this.field41 + this.method52(), this.field66.getRGB());
    }

    @Override
    public void method54() {
        this.field64 = null;
        this.field65 = null;
        super.method54();
    }

    private void method70(final double n) {
        this.field63.setValue(RandomUtil.method401(MathHelper.clamp((n - this.method49()) / this.method51(), 0.0, 1.0) * (this.field63.getMax() - this.field63.getMin()) + this.field63.getMin(), this.field63.getIncrement()));
    }

    @Override
    public void method44(final double mouseX, final double mouseY, final int button) {
        if (this.method55(mouseX, mouseY) && button == 0) {
            this.field61 = true;
            this.method70(mouseX);
        }
        super.method44(mouseX, mouseY, button);
    }

    @Override
    public void method57(final double mouseX, final double mouseY, final int button) {
        if (this.field61 && button == 0) {
            this.field61 = false;
        }
        super.method57(mouseX, mouseY, button);
    }

    @Override
    public void method58(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        if (this.field61) {
            this.method70(mouseX);
        }
        super.method58(mouseX, mouseY, button, deltaX, deltaY);
    }
}
