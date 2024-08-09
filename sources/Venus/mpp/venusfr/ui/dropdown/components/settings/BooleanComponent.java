/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class BooleanComponent
extends Component {
    private final BooleanSetting setting;
    private Animation animation = new Animation();
    private float width;
    private float height;
    private boolean hovered = false;

    public BooleanComponent(BooleanSetting booleanSetting) {
        this.setting = booleanSetting;
        this.setHeight(16.0f);
        this.animation = this.animation.animate((Boolean)booleanSetting.get() != false ? 1.0 : 0.0, 0.2, Easings.CIRC_OUT);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        super.render(matrixStack, f, f2);
        this.animation.update();
        Fonts.montserrat.drawText(matrixStack, this.setting.getName(), this.getX() + 5.0f, this.getY() + 3.25f + 1.0f, ColorUtils.rgb(255, 255, 255), 6.5f, 0.05f);
        this.width = 15.0f;
        this.height = 7.0f;
        DisplayUtils.drawRoundedRect(this.getX() + this.getWidth() - this.width - 7.0f, this.getY() + this.getHeight() / 2.0f - this.height / 2.0f, this.width, this.height, 3.0f, ColorUtils.rgb(29, 29, 31));
        int n = ColorUtils.interpolate(ColorUtils.getColor(0), ColorUtils.getColor(0), 1.0f - (float)this.animation.getValue());
        DisplayUtils.drawCircle((float)((double)(this.getX() + this.getWidth() - this.width - 7.0f + 4.0f) + 7.0 * this.animation.getValue()), this.getY() + this.getHeight() / 2.0f - this.height / 2.0f + 3.5f, 5.0f, n);
        DisplayUtils.drawShadowCircle((float)((double)(this.getX() + this.getWidth() - this.width - 7.0f + 4.0f) + 7.0 * this.animation.getValue()), this.getY() + this.getHeight() / 2.0f - this.height / 2.0f + 3.5f, 7.0f, ColorUtils.setAlpha(n, (int)(128.0 * this.animation.getValue())));
        if (this.isHovered(f, f2)) {
            if (MathUtil.isHovered(f, f2, this.getX() + this.getWidth() - this.width - 7.0f, this.getY() + this.getHeight() / 2.0f - this.height / 2.0f, this.width, this.height)) {
                if (!this.hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
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
        if (MathUtil.isHovered(f, f2, this.getX() + this.getWidth() - this.width - 7.0f, this.getY() + this.getHeight() / 2.0f - this.height / 2.0f, this.width, this.height)) {
            this.setting.set((Boolean)this.setting.get() == false);
            this.animation = this.animation.animate((Boolean)this.setting.get() != false ? 1.0 : 0.0, 0.2, Easings.CIRC_OUT);
        }
        super.mouseClick(f, f2, n);
    }

    @Override
    public boolean isVisible() {
        return (Boolean)this.setting.visible.get();
    }
}

