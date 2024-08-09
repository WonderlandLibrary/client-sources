/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ModeComponent
extends Component {
    final ModeSetting setting;
    float width = 0.0f;
    float heightplus = 0.0f;
    float spacing = 4.0f;

    public ModeComponent(ModeSetting modeSetting) {
        this.setting = modeSetting;
        this.setHeight(18.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        super.render(matrixStack, f, f2);
        Fonts.montserrat.drawText(matrixStack, this.setting.getName(), this.getX() + 5.0f, this.getY() + 2.0f, ColorUtils.rgba(255, 255, 255, 255), 5.5f, 0.05f);
        DisplayUtils.drawShadow(this.getX() + 5.0f, this.getY() + 9.0f, this.width + 5.0f, 10.0f + this.heightplus, 10, ColorUtils.rgba(25, 26, 40, 45));
        DisplayUtils.drawRoundedRect(this.getX() + 5.0f, this.getY() + 9.0f, this.width + 5.0f, 10.0f + this.heightplus, 2.0f, ColorUtils.rgba(25, 26, 40, 45));
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean bl = false;
        boolean bl2 = false;
        for (String string : this.setting.strings) {
            float f5 = Fonts.montserrat.getWidth(string, 5.5f, 0.05f) + 2.0f;
            float f6 = Fonts.montserrat.getHeight(5.5f) + 1.0f;
            if (f3 + f5 + this.spacing >= this.getWidth() - 10.0f) {
                f3 = 0.0f;
                f4 += f6 + this.spacing;
                bl = true;
            }
            if (MathUtil.isHovered(f, f2, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, f5, f6)) {
                bl2 = true;
            }
            if (string.equals(this.setting.get())) {
                Fonts.montserrat.drawText(matrixStack, string, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, ColorUtils.rgb(114, 118, 134), 5.5f, 0.07f);
                Fonts.montserrat.drawText(matrixStack, string, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, ColorUtils.rgba(255, 255, 255, 255), 5.5f, 0.07f);
                DisplayUtils.drawRoundedRect(this.getX() + 7.0f + f3, this.getY() + 10.5f + f4, f5 + 0.8f, f6 + 0.8f, 1.5f, ColorUtils.setAlpha(ColorUtils.getColor(0), 165));
            } else {
                Fonts.montserrat.drawText(matrixStack, string, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, ColorUtils.rgb(46, 47, 51), 5.5f, 0.05f);
                Fonts.montserrat.drawText(matrixStack, string, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, ColorUtils.rgba(255, 255, 255, 255), 5.5f, 0.05f);
                DisplayUtils.drawRoundedRect(this.getX() + 7.0f + f3, this.getY() + 10.5f + f4, f5 + 0.8f, f6 + 0.8f, 1.5f, ColorUtils.rgba(25, 26, 40, 165));
            }
            f3 += f5 + this.spacing;
        }
        if (this.isHovered(f, f2)) {
            if (bl2) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
            } else {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            }
        }
        this.width = bl ? this.getWidth() - 15.0f : f3;
        this.setHeight(22.0f + f4);
        this.heightplus = f4;
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (String string : this.setting.strings) {
            float f5 = Fonts.montserrat.getWidth(string, 5.5f, 0.05f) + 2.0f;
            float f6 = Fonts.montserrat.getHeight(5.5f) + 1.0f;
            if (f3 + f5 + this.spacing >= this.getWidth() - 10.0f) {
                f3 = 0.0f;
                f4 += f6 + this.spacing;
            }
            if (MathUtil.isHovered(f, f2, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, f5, f6)) {
                this.setting.set(string);
            }
            f3 += f5 + this.spacing;
        }
        super.mouseClick(f, f2, n);
    }

    @Override
    public boolean isVisible() {
        return (Boolean)this.setting.visible.get();
    }
}

