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
import java.util.Iterator;
import java.util.List;

public final class ModuleComponent {
    public List field448;
    public CategoryComponent field449;
    public Module field450;
    public int field451;
    public boolean field452;
    public int field453;
    public Color field454;
    public Color field455;
    public Color field456;
    public AnimationUtil field457;

    public ModuleComponent(final CategoryComponent parent, final Module module, final int offset) {
        final int n = 0;
        final int n2 = n;
        this.field448 = new ArrayList();
        this.field455 = Color.WHITE;
        this.field457 = new AnimationUtil(0.0);
        this.field449 = parent;
        this.field450 = module;
        this.field451 = offset;
        this.field452 = false;
        this.field453 = parent.method451();
        for (final Setting class8 : module.getSettings()) {
            boolean b4;
            boolean b3;
            boolean b2;
            final boolean b = b2 = (b3 = (b4 = (class8 instanceof BooleanSetting)));
            Label_0290:
            {
                if (n2 == 0) {
                    if (b) {
                        this.field448.add(new BooleanComponent(this, class8, this.field453));
                        if (n2 == 0) {
                            break Label_0290;
                        }
                    }
                    b3 = (b2 = (b4 = (class8 instanceof IntSetting)));
                }
                if (n2 == 0) {
                    if (b2) {
                        this.field448.add(new IntComponent(this, class8, this.field453));
                        if (n2 == 0) {
                            break Label_0290;
                        }
                    }
                    b4 = (b3 = (class8 instanceof EnumSetting));
                }
                EnumSetting class9 = null;
                Label_0261:
                {
                    if (n2 == 0) {
                        if (b3) {
                            this.field448.add(new EnumComponent(this, class8, this.field453));
                            if (n2 == 0) {
                                break Label_0290;
                            }
                        }
                        class9 = (EnumSetting) class8;
                        if (n2 != 0) {
                            break Label_0261;
                        }
                    }
                    if (!b4) {
                        break Label_0290;
                    }
                }
                this.field448.add(new KeybindComponent(this, class9, this.field453));
            }
            this.field453 += parent.method451();
            if (n2 != 0) {
                break;
            }
        }
    }

    public void method289(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if (this.field449.method444() + this.field451 > MinecraftClient.getInstance().getWindow().getHeight()) {
            return;
        }
        final Iterator iterator = this.field448.iterator();
        while (iterator.hasNext()) {
            ((SettingComponent) iterator.next()).method56();
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
        if (this.field450.isEnabled()) {
            toColor = Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.field450.getCategory()).indexOf(this.field450));
        } else {
            toColor = Color.WHITE;
        }
        if (this.field455 != toColor) {
            this.field455 = ColorUtil.method522(0.1f, toColor, this.field455);
        }
        if (this.field449.field532.get(this.field449.field532.size() - 1) != this) {
            context.fill(this.field449.method443(), this.field449.method444() + this.field451, this.field449.method443() + this.field449.method450(), this.field449.method444() + this.field449.method451() + this.field451, this.field454.getRGB());
            context.fillGradient(this.field449.method443(), this.field449.method444() + this.field451, this.field449.method443() + 2, this.field449.method444() + this.field449.method451() + this.field451, Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.field450.getCategory()).indexOf(this.field450)).getRGB(), Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.field450.getCategory()).indexOf(this.field450) + 1).getRGB());
        } else {
            RenderUtil.method416(context.getMatrices(), this.field454, this.field449.method443(), this.field449.method444() + this.field451, this.field449.method443() + this.field449.method450(), this.field449.method444() + this.field449.method451() + this.field451, 0.0, 0.0, 3.0, this.field452 ? 0.0 : ((double) ClickGui.roundnessSetting.getValueInt()), 50.0);
            RenderUtil.method416(context.getMatrices(), Authentication.method331(255, Argon.INSTANCE.getModuleManager().getModulesInCategory(this.field450.getCategory()).indexOf(this.field450)), this.field449.method443(), this.field449.method444() + this.field451, this.field449.method443() + 2, this.field449.method444() + (this.field449.method451() - 1) + this.field451, 0.0, 0.0, this.field452 ? 0.0 : 2.0, 0.0, 50.0);
        }
        final CharSequence method109 = this.field450.getName();
        FontRenderer.drawText(method109, context, this.field449.method443() + this.field449.method450() / 2 - FontRenderer.getTextWidth(method109) / 2, this.field449.method444() + this.field451 + 8, this.field455.getRGB());
        if (!this.field449.field539) {
            final int n = this.method296(mouseX, mouseY) ? 30 : 0;
            if (this.field456 == null) {
                this.field456 = new Color(255, 255, 255, n);
            } else {
                this.field456 = new Color(255, 255, 255, this.field456.getAlpha());
            }
            if (this.field456.getAlpha() != n) {
                this.field456 = ColorUtil.method523(0.05f, n, this.field456);
            }
            context.fill(this.field449.method443(), this.field449.method444() + this.field451, this.field449.method443() + this.field449.method450(), this.field449.method444() + this.field449.method451() + this.field451, this.field456.getRGB());
        }
        if (this.field452) {
            final Iterator iterator2 = this.field448.iterator();
            while (iterator2.hasNext()) {
                ((SettingComponent) iterator2.next()).method46(context, mouseX, mouseY, delta);
            }
            for (final Object class13 : this.field448) {
                if (class13 instanceof final IntComponent class14) {
                    RenderUtil.method422(context.getMatrices(), class14.field64.brighter(), class14.method49() + Math.max(class14.field62, 2.5) - 2.5, class14.method50() + class14.field41 + class14.method53() + 27.5, 5.0, 15);
                }
            }
        } else {
            this.field457.method347(0.9 * delta);
        }
        final MinecraftClient instance = MinecraftClient.getInstance();
        if (this.method296(mouseX, mouseY) && !this.field449.field539) {
            final CharSequence method110 = this.field450.getDescription();
            final int method111 = FontRenderer.getTextWidth(method110);
            final int x = instance.getWindow().getFramebufferWidth() / 2 - method111 / 2;
            RenderUtil.method419(context.getMatrices(), new Color(100, 100, 100, 100), x - 5, instance.getWindow().getFramebufferHeight() / 2.0 + 294.0, x + method111 + 5, instance.getWindow().getFramebufferHeight() / 2.0 + 318.0, 3.0, 10.0);
            FontRenderer.drawText(method110, context, x, instance.getWindow().getFramebufferHeight() / 2 + 300, Color.WHITE.getRGB());
        }
    }

    public void method290() {
    }

    public void method291(final int keyCode, final int scanCode, final int modifiers) {
        final int n = 0;
        final Iterator iterator = this.field448.iterator();
        final int n2 = n;
        while (iterator.hasNext()) {
            ((SettingComponent) iterator.next()).method45(keyCode, scanCode, modifiers);
            if (n2 != 0) {
                break;
            }
        }
    }

    public void method292(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        if (this.field452) {
            final Iterator iterator = this.field448.iterator();
            while (iterator.hasNext()) {
                ((SettingComponent) iterator.next()).method58(mouseX, mouseY, button, deltaX, deltaY);
            }
        }
    }

    public void method293(final double mouseX, final double mouseY, final int button) {
        if (this.method296(mouseX, mouseY)) {
            if (button == 0) {
                this.field450.method100();
            }
            if (button == 1) {
                if (this.field450.getSettings().isEmpty()) {
                    return;
                }
                if (!this.field452) {
                    this.method290();
                }
                this.field452 = !this.field452;
            }
        }
        if (this.field452) {
            final Iterator iterator = this.field448.iterator();
            while (iterator.hasNext()) {
                ((SettingComponent) iterator.next()).method44(mouseX, mouseY, button);
            }
        }
    }

    public void method294() {
        final int n = 0;
        this.field456 = null;
        final int n2 = n;
        this.field454 = null;
        final Iterator iterator = this.field448.iterator();
        while (iterator.hasNext()) {
            ((SettingComponent) iterator.next()).method54();
            if (n2 != 0) {
                break;
            }
        }
    }

    public void drawSettings(final double mouseX, final double mouseY, final int button) {
        final Iterator<SettingComponent> iterator = this.field448.iterator();
        while (iterator.hasNext()) iterator.next().method57(mouseX, mouseY, button);
    }

    public boolean method296(final double mouseX, final double mouseY) {
        return mouseX > this.field449.method443() && mouseX < this.field449.method443() + this.field449.method450() && mouseY > this.field449.method444() + this.field451 && mouseY < this.field449.method444() + this.field451 + this.field449.method451();
    }
}
