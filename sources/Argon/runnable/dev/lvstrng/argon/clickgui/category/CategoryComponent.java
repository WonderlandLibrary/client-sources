// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.clickgui.category;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.auth.Authentication;
import dev.lvstrng.argon.clickgui.category.modules.ModuleComponent;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.impl.ClickGui;
import dev.lvstrng.argon.utils.AnimationUtil;
import dev.lvstrng.argon.utils.ColorUtil;
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CategoryComponent {
    private final int width;
    private final int height;
    private final Category category;
    public List<ModuleComponent> modules;
    public int x;
    public int y;
    public Color field537;
    public boolean field539;
    public boolean field540;
    int field541;
    int field542;
    float field545;
    private int privX;
    private int privY;

    public CategoryComponent(final int x, final int y, final int width, final int height, final Category category) {
        this.modules = new ArrayList();
        this.x = x;
        this.y = y;
        this.width = width;
        this.field539 = false;
        this.field540 = true;
        this.height = height;
        this.category = category;
        this.privX = x;
        this.privY = y;
        int offset = height;
        for (Module module : Argon.INSTANCE.getModuleManager().getModulesInCategory(category)) {
            this.modules.add(new ModuleComponent(this, module, offset));
            offset += height;
        }
    }

    public void render(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if (ClickGui.msaaSetting.getValue()) {
            GL11.glEnable(32925);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
        }
        this.field545 = delta;
        CategoryComponent class52 = this;
        Label_0109:
        {
            if (this.field537 == null) {
                this.field537 = new Color(0, 0, 0, 0);
                break Label_0109;
            }
            class52.field537 = new Color(0, 0, 0, this.field537.getAlpha());
        }
        final int alpha = this.field537.getAlpha();
        final int increment = 255;
        if (alpha != increment) {
            this.field537 = ColorUtil.method523(0.05f, 255, this.field537);
        }
        RenderUtil.method416(context.getMatrices(), this.field537, this.privX, this.privY, this.privX + this.width, this.privY + this.height, ClickGui.roundnessSetting.getValueInt(), ClickGui.roundnessSetting.getValueInt(), 0.0, 0.0, 50.0);
        Authentication.method331(alpha, increment);
        context.fill(this.privX, this.privY + (this.height - 2), this.privX + this.width, this.privY + this.height, Authentication.method331(255, 0).getRGB());
        FontRenderer.drawText(this.category.name, context, this.privX + this.width / 2 - FontRenderer.getTextWidth(this.category.name) / 2, this.privY + 6, Color.WHITE.getRGB());
        this.calcHeight(delta);
        for (ModuleComponent module : this.modules) {
            module.render(context, mouseX, mouseY, delta);
        }
        final boolean method42 = ClickGui.msaaSetting.getValue();
        if (!method42) {
            return;
        }
//      GL11.glDisable(2848);
//      GL11.glDisable(method42 ? 1 : 0);
    }

    public void method436(final int keyCode, final int scanCode, final int modifiers) {
        final int n = 0;
        final Iterator iterator = this.modules.iterator();
        final int n2 = n;
        while (iterator.hasNext()) {
            ((ModuleComponent) iterator.next()).method291(keyCode, scanCode, modifiers);
            if (n2 != 0) {
                break;
            }
        }
    }

    public void delete() {
        this.field537 = null;
        for (Object object : this.modules)
            ((ModuleComponent) object).method294();
        this.field539 = false;
    }

    public void method438(final double mouseX, final double mouseY, final int button) {
        if (this.method445(mouseX, mouseY)) {
            switch (button) {
                case 0: {
                    this.field539 = true;
                    this.field541 = (int) (mouseX - this.x);
                    this.field542 = (int) (mouseY - this.y);
                    break;
                }
                case 1: {
                    if (!this.field539) {
                    }
                    break;
                }
            }
        }
        if (this.field540) {
            for (ModuleComponent module : this.modules) {
                module.method293(mouseX, mouseY, button);
            }
        }
    }

    public void method439(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        if (this.field540) {
            for (ModuleComponent module : this.modules) {
                module.method292(mouseX, mouseY, button, deltaX, deltaY);
            }
        }
    }

    public void calcHeight(final float delta) {
        int height = this.height;
        for (ModuleComponent module : this.modules) {
            final AnimationUtil field457 = module.field457;
            module.offset = height;
            height += this.height;
            if (module.showSettings)
                height += (int) (field457.animate(0.9 * delta, this.height) * module.settings.size());
        }
    }

    public void method441(final double mouseX, final double mouseY, final int button) {
        if (button == 0 && this.field539) {
            this.field539 = false;
        }
        for (ModuleComponent module : this.modules) {
            module.drawSettings(mouseX, mouseY, button);
        }
    }

    public void method442(final double mouseX, final double mouseY, final double horizontalAmount, final double verticalAmount) {
        this.privX = this.x;
        this.privY = this.y;
        this.privY += (int) (verticalAmount * 20.0);
        this.method449((int) (this.y + verticalAmount * 20.0));
    }

    public int privX() {
        return this.privX;
    }

    public int privY() {
        return this.privY;
    }

    public boolean method445(final double mouseX, final double mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }

    public boolean method446(final double mouseX, final double mouseY) {
        return mouseX > this.privX && mouseX < this.privX + this.width && mouseY > this.privY && mouseY < this.privY + this.height;
    }

    public void postRender(final double mouseX, final double mouseY, final float delta) {
        this.privX = this.x;
        final int n = 0;
        this.privY = this.y;
        final int n2 = n;
        CategoryComponent class120 = this;
        if (n2 == 0) {
            if (!this.field539) {
                return;
            }
            class120 = this;
        }
        final double n3 = 0.3 * delta;
        int n5;
        final int n4 = n5 = (this.method445(mouseX, mouseY) ? 1 : 0);
        double n6 = 0.0;
        Label_0067:
        {
            if (n2 == 0) {
                if (n4 != 0) {
                    n6 = this.x;
                    break Label_0067;
                }
                n5 = this.privX;
            }
            n6 = n5;
        }
        class120.x = (int) MathHelper.lerp(n3, n6, mouseX - this.field541);
        final double n7 = 0.3 * delta;
        int n9;
        final int n8 = n9 = (this.method445(mouseX, mouseY) ? 1 : 0);
        double n10 = 0.0;
        Label_0116:
        {
            if (n2 == 0) {
                if (n8 != 0) {
                    n10 = this.y;
                    break Label_0116;
                }
                n9 = this.privY;
            }
            n10 = n9;
        }
        this.y = (int) MathHelper.lerp(n7, n10, mouseY - this.field542);
    }

    public void method448(final int x) {
        this.x = x;
    }

    public void method449(final int y) {
        this.y = y;
    }

    public int method450() {
        return this.width;
    }

    public int method451() {
        return this.height;
    }
}
