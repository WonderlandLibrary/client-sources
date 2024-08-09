/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class SliderComponent
extends Component {
    private final SliderSetting setting;
    private float anim;
    private boolean drag;
    private boolean hovered = false;

    public SliderComponent(SliderSetting sliderSetting) {
        this.setting = sliderSetting;
        this.setHeight(18.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        super.render(matrixStack, f, f2);
        Fonts.montserrat.drawText(matrixStack, this.setting.getName(), this.getX() + 5.0f, this.getY() + 2.25f + 1.0f, ColorUtils.rgb(255, 255, 255), 5.5f, 0.05f);
        Fonts.montserrat.drawText(matrixStack, String.valueOf(this.setting.get()), this.getX() + this.getWidth() - 5.0f - Fonts.montserrat.getWidth(String.valueOf(this.setting.get()), 5.5f), this.getY() + 2.25f + 1.0f, ColorUtils.rgb(255, 255, 255), 5.5f, 0.05f);
        DisplayUtils.drawRoundedRect(this.getX() + 5.0f, this.getY() + 11.0f, this.getWidth() - 10.0f, 2.0f, 0.6f, ColorUtils.rgb(28, 28, 31));
        float f3 = this.anim = MathUtil.fast(this.anim, (this.getWidth() - 10.0f) * (((Float)this.setting.get()).floatValue() - this.setting.min) / (this.setting.max - this.setting.min), 20.0f);
        DisplayUtils.drawRoundedRect(this.getX() + 5.0f, this.getY() + 11.0f, f3, 2.0f, 0.6f, ColorUtils.rgb(25, 26, 50));
        DisplayUtils.drawCircle(this.getX() + 5.0f + f3, this.getY() + 12.0f, 5.0f, ColorUtils.getColor(0));
        DisplayUtils.drawShadowCircle(this.getX() + 5.0f + f3, this.getY() + 12.0f, 6.0f, ColorUtils.getColor(0));
        if (this.drag) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.glfwCreateStandardCursor(221189));
            this.setting.set(Float.valueOf((float)MathHelper.clamp(MathUtil.round((f - this.getX() - 5.0f) / (this.getWidth() - 10.0f) * (this.setting.max - this.setting.min) + this.setting.min, this.setting.increment), (double)this.setting.min, (double)this.setting.max)));
        }
        if (this.isHovered(f, f2)) {
            if (MathUtil.isHovered(f, f2, this.getX() + 5.0f, this.getY() + 10.0f, this.getWidth() - 10.0f, 3.0f)) {
                if (!this.hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.RESIZEH);
                    this.hovered = true;
                }
            } else if (this.hovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                this.hovered = false;
            }
        }
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        if (MathUtil.isHovered(f, f2, this.getX() + 5.0f, this.getY() + 10.0f, this.getWidth() - 10.0f, 3.0f)) {
            this.drag = true;
        }
        super.mouseClick(f, f2, n);
    }

    @Override
    public void mouseRelease(float f, float f2, int n) {
        this.drag = false;
        super.mouseRelease(f, f2, n);
    }

    @Override
    public boolean isVisible() {
        return (Boolean)this.setting.visible.get();
    }
}

