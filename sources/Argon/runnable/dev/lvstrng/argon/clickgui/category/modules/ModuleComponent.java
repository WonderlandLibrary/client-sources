// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category.modules;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.auth.Authentication;
import dev.lvstrng.argon.clickgui.category.CategoryComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.SettingComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.impl.BooleanComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.impl.EnumComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.impl.IntComponent;
import dev.lvstrng.argon.clickgui.category.modules.settings.impl.KeybindComponent;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.impl.ClickGui;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.AnimationUtil;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ModuleComponent {
    public List<SettingComponent> settings;
    public CategoryComponent parent;
    public Module module;
    public int offset;
    public int finalOffset;
    public boolean showSettings;
    public int parentHeight;
    public Color field454;
    public Color color;
    public Color field456;
    public AnimationUtil field457;

    public ModuleComponent(final CategoryComponent parent, final Module module, final int offset) {
        this.settings = new ArrayList();
        this.color = Color.WHITE;
        this.field457 = new AnimationUtil(0.0);
        this.parent = parent;
        this.module = module;
        this.offset = offset;
        this.finalOffset = offset;
        this.showSettings = false;
        this.parentHeight = parent.method451();
        for (final Setting<?> class8 : module.getSettings()) {
            boolean b4;
            boolean b3;
            boolean b2;
            final boolean b = (class8 instanceof BooleanSetting);
            Label_0290:
            {
                if (b) {
                    this.settings.add(new BooleanComponent(this, class8, this.parentHeight));
                    break Label_0290;
                }
                b2 = (class8 instanceof IntSetting);
                if (b2) {
                    this.settings.add(new IntComponent(this, class8, this.parentHeight));
                    break Label_0290;
                }
                b4 = (b3 = (class8 instanceof EnumSetting));
                EnumSetting class9;
                if (b3) {
                    this.settings.add(new EnumComponent(this, class8, this.parentHeight));
                    break Label_0290;
                }
                class9 = (EnumSetting) class8;
                if (!b4) {
                    break Label_0290;
                }

                this.settings.add(new KeybindComponent(this, class9, this.parentHeight));
            }
            this.parentHeight += parent.method451();
        }
    }

    public void render(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if (this.parent.privY() + this.offset > MinecraftClient.getInstance().getWindow().getHeight()) {
            return;
        }
        for (SettingComponent settingComponent : this.settings) {
            settingComponent.method56();
        }
        if (this.field454 == null) {
            this.field454 = new Color(0, 0, 0, 0);
        } else {
            this.field454 = new Color(0, 0, 0, this.field454.getAlpha());
        }
        final int toAlpha = 190;
        if (this.field454.getAlpha() != toAlpha) {
            this.field454 = ColorUtil.method523(0.05f, toAlpha, this.field454);
        }
        Color toColor;
        if (this.module.isEnabled()) {
            toColor = Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module));
        } else {
            toColor = Color.WHITE;
        }
        if (this.color != toColor) {
            this.color = ColorUtil.method522(0.1f, toColor, this.color);
        }
        if (this.parent.modules.get(this.parent.modules.size() - 1) != this) {
            context.fill(this.parent.privX(), this.parent.privY() + this.offset, this.parent.privX() + this.parent.method450(), this.parent.privY() + this.parent.method451() + this.offset, this.field454.getRGB());
            context.fillGradient(this.parent.privX(), this.parent.privY() + this.offset, this.parent.privX() + 2, this.parent.privY() + this.parent.method451() + this.offset, Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module)).getRGB(), Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module) + 1).getRGB());
        } else {
            RenderUtil.method416(context.getMatrices(), this.field454, this.parent.privX(), this.parent.privY() + this.offset, this.parent.privX() + this.parent.method450(), this.parent.privY() + this.parent.method451() + this.offset, 0.0, 0.0, 3.0, this.showSettings ? 0.0 : ((double) ClickGui.roundnessSetting.getValueInt()), 50.0);
            RenderUtil.method416(context.getMatrices(), Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module)), this.parent.privX(), this.parent.privY() + this.offset, this.parent.privX() + 2, this.parent.privY() + (this.parent.method451() - 1) + this.offset, 0.0, 0.0, this.showSettings ? 0.0 : 2.0, 0.0, 50.0);
        }
        final CharSequence method109 = this.module.getName();
        FontRenderer.drawText(method109, context, this.parent.privX() + this.parent.method450() / 2 - FontRenderer.getTextWidth(method109) / 2, this.parent.privY() + this.offset + 8, this.color.getRGB());
        if (!this.parent.field539) {
            final int n = this.method296(mouseX, mouseY) ? 30 : 0;
            if (this.field456 == null) {
                this.field456 = new Color(255, 255, 255, n);
            } else {
                this.field456 = new Color(255, 255, 255, this.field456.getAlpha());
            }
            if (this.field456.getAlpha() != n) {
                this.field456 = ColorUtil.method523(0.05f, n, this.field456);
            }
            context.fill(this.parent.privX(), this.parent.privY() + this.offset, this.parent.privX() + this.parent.method450(), this.parent.privY() + this.parent.method451() + this.offset, this.field456.getRGB());
        }
        if (this.showSettings) {
            for (SettingComponent settingComponent : this.settings) {
                settingComponent.method46(context, mouseX, mouseY, delta);
            }
            for (final Object class13 : this.settings) {
                if (class13 instanceof final IntComponent class14) {
                    RenderUtil.method422(context.getMatrices(), class14.field64.brighter(), class14.method49() + Math.max(class14.field62, 2.5) - 2.5, class14.method50() + class14.field41 + class14.method53() + 27.5, 5.0, 15);
                }
            }
        } else {
            this.field457.method347(0.9 * delta);
        }
        final MinecraftClient instance = MinecraftClient.getInstance();
        if (this.method296(mouseX, mouseY) && !this.parent.field539) {
            final CharSequence method110 = this.module.getDescription();
            final int method111 = FontRenderer.getTextWidth(method110);
            final int x = instance.getWindow().getFramebufferWidth() / 2 - method111 / 2;
            RenderUtil.method419(context.getMatrices(), new Color(100, 100, 100, 100), x - 5, instance.getWindow().getFramebufferHeight() / 2.0 + 294.0, x + method111 + 5, instance.getWindow().getFramebufferHeight() / 2.0 + 318.0, 3.0, 10.0);
            FontRenderer.drawText(method110, context, x, instance.getWindow().getFramebufferHeight() / 2 + 300, Color.WHITE.getRGB());
        }
    }

    public void method290() {
    }

    public void method291(final int keyCode, final int scanCode, final int modifiers) {
        for (SettingComponent settingComponent : this.settings) {
            settingComponent.method45(keyCode, scanCode, modifiers);
        }
    }

    public void method292(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        if (this.showSettings) {
            for (SettingComponent settingComponent : this.settings) {
                settingComponent.method58(mouseX, mouseY, button, deltaX, deltaY);
            }
        }
    }

    public void method293(final double mouseX, final double mouseY, final int button) {
        if (this.method296(mouseX, mouseY)) {
            if (button == 0) {
                this.module.method100();
            }
            if (button == 1) {
                if (this.module.getSettings().isEmpty()) {
                    return;
                }
                if (!this.showSettings) {
                    this.method290();
                }
                this.showSettings = !this.showSettings;
            }
        }
        if (this.showSettings) {
            for (SettingComponent settingComponent : this.settings) {
                settingComponent.method44(mouseX, mouseY, button);
            }
        }
    }

    public void method294() {
        this.field456 = null;
        this.field454 = null;
        for (SettingComponent settingComponent : this.settings) {
            settingComponent.method54();
        }
    }

    public void drawSettings(final double mouseX, final double mouseY, final int button) {
        for (SettingComponent settingComponent : this.settings) settingComponent.method57(mouseX, mouseY, button);
    }

    public boolean method296(final double mouseX, final double mouseY) {
        return mouseX > this.parent.privX() && mouseX < this.parent.privX() + this.parent.method450() && mouseY > this.parent.privY() + this.offset && mouseY < this.parent.privY() + this.offset + this.parent.method451();
    }
}
