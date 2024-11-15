// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category.modules.settings;

import dev.lvstrng.argon.clickgui.category.modules.ModuleComponent;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public abstract class SettingComponent {

    public MinecraftClient field38;
    public ModuleComponent field39;
    public Setting field40;
    public int field41;
    public Color field42;
    int field44;
    int field45;
    int field46;
    int field47;

    public SettingComponent(final ModuleComponent parent, final Setting setting, final int offset) {
        this.field38 = MinecraftClient.getInstance();
        this.field39 = parent;
        this.field40 = setting;
        this.field41 = offset;
        this.field44 = this.method49();
        this.field45 = this.method50() + this.method53() + offset;
        this.field46 = this.method49() + this.method51();
        this.field47 = this.method50() + this.method53() + offset + this.method52();
    }

    public int method49() {
        return this.field39.parent.privX();
    }

    public int method50() {
        return this.field39.parent.privY();
    }

    public int method51() {
        return this.field39.parent.method450();
    }

    public int method52() {
        return this.field39.parent.method451();
    }

    public int method53() {
        return this.field39.offset;
    }

    public void method46(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        this.field44 = this.method49();
        this.field45 = this.method50() + this.method53() + this.field41;
        this.field46 = this.method49() + this.method51();
        final int n = 0;
        this.field47 = this.method50() + this.method53() + this.field41 + this.method52();
        final int n2 = n;
        context.fill(this.field44, this.field45, this.field46, this.field47, this.field42.getRGB());
        SettingComponent class13 = this;
        SettingComponent class14 = this;
        SettingComponent class15 = this;
        if (n2 == 0) {
            if (!this.method55(mouseX, mouseY)) {
                return;
            }
            class13 = this;
            class14 = this;
            class15 = this;
        }
        if (n2 == 0) {
            if (class15.field40.getDesc() == null) {
                return;
            }
            class13 = this;
            class14 = this;
        }
        if (n2 == 0) {
            if (class14.field39.parent.field539) {
                return;
            }
            class13 = this;
        }
        final CharSequence method21 = class13.field40.getDesc();
        final int method22 = FontRenderer.getTextWidth(method21);
        final int x = this.field38.getWindow().getWidth() / 2 - method22 / 2;
        RenderUtil.method419(context.getMatrices(), new Color(100, 100, 100, 100), x - 5, this.field38.getWindow().getHeight() / 2 + 294, x + method22 + 5, this.field38.getWindow().getHeight() / 2 + 318, 3.0, 10.0);
        FontRenderer.drawText(method21, context, x, this.field38.getWindow().getHeight() / 2 + 300, Color.WHITE.getRGB());
    }

    public void method54() {
        this.field42 = null;
    }

    public void method45(final int keyCode, final int scanCode, final int modifiers) {
    }

    public boolean method55(final double mouseX, final double mouseY) {
        return mouseX > this.method49() && mouseX < this.method49() + this.method51() && mouseY > this.field41 + this.method53() + this.method50() && mouseY < this.field41 + this.method53() + this.method50() + this.method52();
    }

    public void method56() {
        if (this.field42 == null) {
            this.field42 = new Color(0, 0, 0, 0);
        } else {
            this.field42 = new Color(0, 0, 0, this.field42.getAlpha());
        }
        if (this.field42.getAlpha() != 120) {
            this.field42 = ColorUtil.method523(0.05f, 120, this.field42);
        }
    }

    public void method44(final double mouseX, final double mouseY, final int button) {
    }

    public void method57(final double mouseX, final double mouseY, final int button) {
    }

    public void method58(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
    }

    public void method59(final int x) {
        this.field44 = x;
    }

    public int method60() {
        return this.field44;
    }

    public void method61(final int y) {
        this.field45 = y;
    }

    public int method62() {
        return this.field45;
    }

    public void method63(final int width) {
        this.field46 = width;
    }

    public int method64() {
        return this.field46;
    }

    public void method65(final int height) {
        this.field47 = height;
    }

    public int method66() {
        return this.field47;
    }
}
