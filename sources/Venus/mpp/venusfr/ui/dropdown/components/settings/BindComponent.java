/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.client.KeyStorage;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class BindComponent
extends Component {
    final BindSetting setting;
    boolean activated;
    boolean hovered = false;

    public BindComponent(BindSetting bindSetting) {
        this.setting = bindSetting;
        this.setHeight(16.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        boolean bl;
        super.render(matrixStack, f, f2);
        Fonts.montserrat.drawText(matrixStack, this.setting.getName(), this.getX() + 5.0f, this.getY() + 3.25f + 1.0f, ColorUtils.rgb(160, 163, 175), 6.5f, 0.05f);
        String string = KeyStorage.getKey((Integer)this.setting.get());
        if (string == null || (Integer)this.setting.get() == -1) {
            string = "\u041d\u0435\u0442\u0443";
        }
        boolean bl2 = bl = Fonts.montserrat.getWidth(string, 5.5f, this.activated ? 0.1f : 0.05f) >= 16.0f;
        float f3 = bl ? this.getX() + 5.0f : this.getX() + this.getWidth() - 7.0f - Fonts.montserrat.getWidth(string, 5.5f, this.activated ? 0.1f : 0.05f);
        float f4 = this.getY() + 2.75f + 2.75f + (float)(bl ? 8 : 0);
        DisplayUtils.drawShadow(this.getX() + 5.0f, this.getY() + 9.0f, Fonts.montserrat.getWidth(string, 5.5f, this.activated ? 0.1f : 0.05f) + 4.0f, 9.5f, 10, ColorUtils.rgba(25, 26, 40, 45));
        DisplayUtils.drawRoundedRect(this.getX() + 5.0f, this.getY() + 9.0f, Fonts.montserrat.getWidth(string, 5.5f, this.activated ? 0.1f : 0.05f) + 4.0f, 9.5f, 2.0f, ColorUtils.rgba(25, 26, 40, 45));
        Fonts.montserrat.drawText(matrixStack, string, f3, f4, this.activated ? -1 : ColorUtils.rgb(255, 255, 255), 5.5f, this.activated ? 0.1f : 0.05f);
        if (this.isHovered(f, f2)) {
            if (MathUtil.isHovered(f, f2, f3 - 2.0f + 0.5f, f4 - 2.0f, Fonts.montserrat.getWidth(string, 5.5f, this.activated ? 0.1f : 0.05f) + 4.0f, 9.5f)) {
                if (!this.hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                    this.hovered = true;
                }
            } else if (this.hovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                this.hovered = false;
            }
        }
        this.setHeight(bl ? 20.0f : 16.0f);
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        if (this.activated) {
            if (n == 261) {
                this.setting.set(-1);
                this.activated = false;
                return;
            }
            this.setting.set(n);
            this.activated = false;
        }
        super.keyPressed(n, n2, n3);
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        if (this.isHovered(f, f2) && n == 0) {
            boolean bl = this.activated = !this.activated;
        }
        if (this.activated && n >= 1) {
            System.out.println(-100 + n);
            this.setting.set(-100 + n);
            this.activated = false;
        }
        super.mouseClick(f, f2, n);
    }

    @Override
    public void mouseRelease(float f, float f2, int n) {
        super.mouseRelease(f, f2, n);
    }

    @Override
    public boolean isVisible() {
        return (Boolean)this.setting.visible.get();
    }
}

