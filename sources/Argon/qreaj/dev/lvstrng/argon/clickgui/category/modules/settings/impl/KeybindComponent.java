// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category.modules.settings.impl;

import dev.lvstrng.argon.clickgui.category.modules.ModuleComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.SettingComponent;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.KeybindSetting;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.KeybindTranslator;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class KeybindComponent extends SettingComponent {

    public KeybindSetting field32;
    private Color field33;

    public KeybindComponent(final ModuleComponent parent, final Setting setting, final int offset) {
        super(parent, setting, offset);
        this.field32 = (KeybindSetting) setting;
    }


    @Override
    public void method44(final double mouseX, final double mouseY, final int button) {
        if (this.method55(mouseX, mouseY) && button == 0) {
            this.field32.getListening();
            this.field32.setListening(true);
        }
        super.method44(mouseX, mouseY, button);
    }

    @Override
    public void method45(final int keyCode, final int scanCode, final int modifiers) {
        if (this.field32.isListening() && keyCode != 256) {
            this.field39.field450.setKeybind(keyCode);
            this.field32.setKey(keyCode);
            this.field32.setListening(false);
        }
        if (this.field32.getKey() == 259) {
            this.field39.field450.setKeybind(0);
            this.field32.setKey(0);
            this.field32.setListening(false);
        }
        if (this.field32.getKey() == 256) {
            this.field39.field450.setKeybind(this.field39.field450.getKeybind());
            this.field32.setKey(this.field32.getKey());
            this.field32.setListening(false);
        }
        super.method45(keyCode, scanCode, modifiers);
    }

    @Override
    public void method46(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        final int n = 0;
        super.method46(context, mouseX, mouseY, delta);
        final int n2 = this.method49() + 6;
        final int n3 = n;
        Setting class8;
        final KeybindSetting class7 = (KeybindSetting) (class8 = this.field32);
        Label_0157:
        {
            Label_0114:
            {
                if (n3 == 0) {
                    if (class7.isListening()) {
                        break Label_0114;
                    }
                    class8 = this.field40;
                }
                FontRenderer.drawText(class8.getName() + ": " + KeybindTranslator.method395(this.field32.getKey()), context, n2, this.method50() + this.method53() + this.field41 + 6, new Color(245, 245, 245, 255).getRGB());
                if (n3 == 0) {
                    break Label_0157;
                }
            }
            FontRenderer.drawText("Listening...", context, n2, this.method50() + this.method53() + this.field41 + 6, new Color(245, 245, 245, 255).getRGB());
        }
        int field539;
        final int n4 = field539 = (this.field39.field449.field539 ? 1 : 0);
        if (n3 == 0) {
            if (n4 != 0) {
                return;
            }
            final boolean method55;
            field539 = ((method55 = this.method55(mouseX, mouseY)) ? 1 : 0);
        }
        if (n3 == 0) {
            if (n4 != 0) {
                field539 = 30;
            } else {
                field539 = 0;
            }
        }
        final int n5 = field539;
        KeybindComponent class9 = this;
        Label_0259:
        {
            if (n3 == 0) {
                if (this.field33 == null) {
                    this.field33 = new Color(255, 255, 255, n5);
                    if (n3 == 0) {
                        break Label_0259;
                    }
                }
                class9 = this;
            }
            class9.field33 = new Color(255, 255, 255, this.field33.getAlpha());
        }
        KeybindComponent class10 = this;
        Label_0291:
        {
            if (n3 == 0) {
                if (this.field33.getAlpha() == n5) {
                    break Label_0291;
                }
                class10 = this;
            }
            class10.field33 = ColorUtil.method523(0.05f, n5, this.field33);
        }
        context.fill(this.method49(), this.method50() + this.method53() + this.field41, this.method49() + this.method51(), this.method50() + this.method53() + this.field41 + this.method52(), this.field33.getRGB());
    }
}
