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
    private final int field535;
    private final int field536;
    private final Category field538;
    public List field532;
    public int field533;
    public int field534;
    public Color field537;
    public boolean field539;
    public boolean field540;
    int field541;
    int field542;
    float field545;
    private int field543;
    private int field544;

    public CategoryComponent(final int x, final int y, final int width, final int height, final Category category) {
        this.field532 = new ArrayList();
        this.field533 = x;
        this.field534 = y;
        this.field535 = width;
        this.field539 = false;
        this.field540 = true;
        this.field536 = height;
        final int n = 0;
        this.field538 = category;
        this.field543 = x;
        this.field544 = y;
        final int n2 = n;
        int offset = height;
        final Iterator iterator = new ArrayList(Argon.INSTANCE.getModuleManager().getModulesInCategory(category)).iterator();
        while (iterator.hasNext()) {
            this.field532.add(new ModuleComponent(this, (Module) iterator.next(), offset));
            offset += height;
            if (n2 != 0) {
                break;
            }
        }
    }

    public void method435(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        final int n = 0;
        final int n2 = n;
        if (n2 == 0) {
            if (ClickGui.msaaSetting.getValue()) {
                GL11.glEnable(32925);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
            }
            this.field545 = delta;
        }
        CategoryComponent class52 = this;
        Label_0109:
        {
            if (n2 == 0) {
                if (this.field537 == null) {
                    this.field537 = new Color(0, 0, 0, 0);
                    if (n2 == 0) {
                        break Label_0109;
                    }
                }
                class52 = this;
            }
            class52.field537 = new Color(0, 0, 0, this.field537.getAlpha());
        }
        final int alpha = this.field537.getAlpha();
        final int increment = 255;
        if (n2 == 0) {
            if (alpha != increment) {
                this.field537 = ColorUtil.method523(0.05f, 255, this.field537);
            }
            RenderUtil.method416(context.getMatrices(), this.field537, this.field543, this.field544, this.field543 + this.field535, this.field544 + this.field536, ClickGui.roundnessSetting.getValueInt(), ClickGui.roundnessSetting.getValueInt(), 0.0, 0.0, 50.0);
            this.field532.indexOf(this.field532.get(0));
        }
        Authentication.method331(alpha, increment);
        context.fill(this.field543, this.field544 + (this.field536 - 2), this.field543 + this.field535, this.field544 + this.field536, Authentication.method331(255, this.field532.indexOf(this.field532.get(0))).getRGB());
        FontRenderer.drawText(this.field538.name, context, this.field543 + this.field535 / 2 - FontRenderer.getTextWidth(this.field538.name) / 2, this.field544 + 6, Color.WHITE.getRGB());
        this.method440(delta);
        final Iterator iterator = this.field532.iterator();
        while (iterator.hasNext()) {
            ((ModuleComponent) iterator.next()).method289(context, mouseX, mouseY, delta);
            if (n2 != 0) {
                return;
            }
            if (n2 != 0) {
                break;
            }
        }
        final boolean method42 = ClickGui.msaaSetting.getValue();
        if (n2 == 0) {
            if (!method42) {
                return;
            }
            GL11.glDisable(2848);
        }
        GL11.glDisable(method42 ? 1 : 0);
    }

    public void method436(final int keyCode, final int scanCode, final int modifiers) {
        final int n = 0;
        final Iterator iterator = this.field532.iterator();
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
        for (Object object : this.field532)
            ((ModuleComponent) object).method294();
        this.field539 = false;
    }

    public void method438(final double mouseX, final double mouseY, final int button) {
        if (this.method445(mouseX, mouseY)) {
            switch (button) {
                case 0: {
                    this.field539 = true;
                    this.field541 = (int) (mouseX - this.field533);
                    this.field542 = (int) (mouseY - this.field534);
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
            final Iterator iterator = this.field532.iterator();
            while (iterator.hasNext()) {
                ((ModuleComponent) iterator.next()).method293(mouseX, mouseY, button);
            }
        }
    }

    public void method439(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        if (this.field540) {
            final Iterator iterator = this.field532.iterator();
            while (iterator.hasNext()) {
                ((ModuleComponent) iterator.next()).method292(mouseX, mouseY, button, deltaX, deltaY);
            }
        }
    }

    public void method440(final float delta) {
        final int n = 0;
        final Iterator iterator = this.field532.iterator();
        final int n2 = n;
        while (iterator.hasNext()) {
            final int n3 = this.field532.indexOf(iterator.next()) - 1;
            if (n3 != -1) {
                final ModuleComponent class97 = (ModuleComponent) this.field532.get(n3);
                final AnimationUtil field457 = class97.field457;
                int field458 = this.field536;
                final ModuleComponent class98 = class97;
                if (class98.field452)
                    field458 += (int) (field457.animate(0.9 * delta, this.field536) * class97.field448.size());
                class98.field451 = class97.field451 + field458;
            }
            if (n2 != 0) {
                break;
            }
        }
    }

    public void method441(final double mouseX, final double mouseY, final int button) {
        if (button == 0 && this.field539) {
            this.field539 = false;
        }
        final Iterator iterator = this.field532.iterator();
        while (iterator.hasNext()) {
            ((ModuleComponent) iterator.next()).drawSettings(mouseX, mouseY, button);
        }
    }

    public void method442(final double mouseX, final double mouseY, final double horizontalAmount, final double verticalAmount) {
        this.field543 = this.field533;
        this.field544 = this.field534;
        this.field544 += (int) (verticalAmount * 20.0);
        this.method449((int) (this.field534 + verticalAmount * 20.0));
    }

    public int method443() {
        return this.field543;
    }

    public int method444() {
        return this.field544;
    }

    public boolean method445(final double mouseX, final double mouseY) {
        return mouseX > this.field533 && mouseX < this.field533 + this.field535 && mouseY > this.field534 && mouseY < this.field534 + this.field536;
    }

    public boolean method446(final double mouseX, final double mouseY) {
        return mouseX > this.field543 && mouseX < this.field543 + this.field535 && mouseY > this.field544 && mouseY < this.field544 + this.field536;
    }

    public void method447(final double mouseX, final double mouseY, final float delta) {
        this.field543 = this.field533;
        final int n = 0;
        this.field544 = this.field534;
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
                    n6 = this.field533;
                    break Label_0067;
                }
                n5 = this.field543;
            }
            n6 = n5;
        }
        class120.field533 = (int) MathHelper.lerp(n3, n6, mouseX - this.field541);
        final double n7 = 0.3 * delta;
        int n9;
        final int n8 = n9 = (this.method445(mouseX, mouseY) ? 1 : 0);
        double n10 = 0.0;
        Label_0116:
        {
            if (n2 == 0) {
                if (n8 != 0) {
                    n10 = this.field534;
                    break Label_0116;
                }
                n9 = this.field544;
            }
            n10 = n9;
        }
        this.field534 = (int) MathHelper.lerp(n7, n10, mouseY - this.field542);
    }

    public void method448(final int x) {
        this.field533 = x;
    }

    public void method449(final int y) {
        this.field534 = y;
    }

    public int method450() {
        return this.field535;
    }

    public int method451() {
        return this.field536;
    }
}
