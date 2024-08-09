/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class MultiBoxComponent
extends Component {
    final ModeListSetting setting;
    float width = 0.0f;
    float heightPadding = 0.0f;
    float spacing = 2.0f;

    public MultiBoxComponent(ModeListSetting modeListSetting) {
        this.setting = modeListSetting;
        this.setHeight(22.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        super.render(matrixStack, f, f2);
        DisplayUtils.drawShadow(this.getX() + 5.0f, this.getY() + 9.0f, this.width + 5.0f, 10.0f + this.heightPadding, 10, ColorUtils.rgba(25, 26, 40, 45));
        DisplayUtils.drawRoundedRect(this.getX() + 5.0f, this.getY() + 9.0f, this.width + 5.0f, 10.0f + this.heightPadding, 2.0f, ColorUtils.rgba(25, 26, 40, 45));
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean bl = false;
        boolean bl2 = false;
        for (BooleanSetting booleanSetting : (List)this.setting.get()) {
            float f5 = Fonts.montserrat.getWidth(booleanSetting.getName(), 5.5f, 0.05f) + 2.0f;
            float f6 = Fonts.montserrat.getHeight(5.5f) + 1.0f;
            if (f3 + f5 + this.spacing >= this.getWidth() - 10.0f) {
                f3 = 0.0f;
                f4 += f6 + this.spacing;
                bl = true;
            }
            if (MathUtil.isHovered(f, f2, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, f5, f6)) {
                bl2 = true;
            }
            if (((Boolean)booleanSetting.get()).booleanValue()) {
                Fonts.montserrat.drawText(matrixStack, booleanSetting.getName(), this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, ColorUtils.rgba(255, 255, 255, 255), 5.5f, 0.07f);
                DisplayUtils.drawRoundedRect(this.getX() + 7.0f + f3, this.getY() + 10.5f + f4, f5 + 0.8f, f6 + 0.8f, 1.5f, ColorUtils.setAlpha(ColorUtils.getColor(0), 165));
            } else {
                Fonts.montserrat.drawText(matrixStack, booleanSetting.getName(), this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, ColorUtils.rgba(255, 255, 255, 255), 5.5f, 0.05f);
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
        this.heightPadding = f4;
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (BooleanSetting booleanSetting : (List)this.setting.get()) {
            float f5 = Fonts.montserrat.getWidth(booleanSetting.getName(), 5.5f, 0.05f) + 2.0f;
            float f6 = Fonts.montserrat.getHeight(5.5f) + 1.0f;
            if (f3 + f5 + this.spacing >= this.getWidth() - 10.0f) {
                f3 = 0.0f;
                f4 += f6 + this.spacing;
            }
            if (MathUtil.isHovered(f, f2, this.getX() + 8.0f + f3, this.getY() + 11.5f + f4, f5, f6)) {
                booleanSetting.set((Boolean)booleanSetting.get() == false);
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

