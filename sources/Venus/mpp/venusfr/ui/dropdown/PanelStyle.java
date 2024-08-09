/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.ui.dropdown.DropDown;
import mpp.venusfr.ui.dropdown.Panel;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class PanelStyle
extends Panel {
    private final ResourceLocation brushIcon = new ResourceLocation("venusfr/images/hud/brush.png");
    float max = 0.0f;

    public PanelStyle(Category category) {
        super(category);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        float f3 = 25.0f;
        float f4 = 8.0f;
        this.setAnimatedScrool(MathUtil.fast(this.getAnimatedScrool(), this.getScroll(), 10.0f));
        DisplayUtils.drawRoundedRect(this.x, this.y + 19.0f, 105.0f, 175.0f, 13.0f, ColorUtils.rgba(25, 26, 40, 255));
        Fonts.montserrat.drawText(matrixStack, "Theme Editor", this.x + 10.0f, this.y + f3 / 2.0f - Fonts.montserrat.getHeight(f4) / 2.0f + 18.0f, ColorUtils.rgb(255, 255, 255), f4 - 1.5f, 0.1f);
        DisplayUtils.drawImage(this.brushIcon, this.x + 105.0f - 20.0f, this.y + f3, 10.0f, 10.0f, -1);
        if (this.max > 220.0f - f3 + 40.0f) {
            this.setScroll(MathHelper.clamp(this.getScroll(), -this.max + 220.0f - f3 - 10.0f, 0.0f));
            this.setAnimatedScrool(MathHelper.clamp(this.getAnimatedScrool(), -this.max + 220.0f - f3 - 10.0f, 0.0f));
        } else {
            this.setScroll(0.0f);
            this.setAnimatedScrool(0.0f);
        }
        float f5 = (float)DropDown.getAnimation().getValue() * DropDown.scale;
        float f6 = (1.0f - f5) / 2.0f;
        float f7 = this.getHeight();
        float f8 = this.getX() + this.getWidth() * f6;
        float f9 = this.getY() + 25.0f + f7 * f6;
        float f10 = this.getWidth() * f5;
        float f11 = f7 * f5 - 56.0f;
        f8 = f8 * f5 + ((float)Minecraft.getInstance().getMainWindow().getScaledWidth() - f10) * f6;
        Scissor.push();
        Scissor.setFromComponentCoordinates(f8, f9, f10, f11);
        int n = 1;
        boolean bl = false;
        float f12 = this.x + 5.0f;
        float f13 = this.y + f3 + 5.0f + (float)n + this.getAnimatedScrool();
        float f14 = 12.0f;
        for (Style style : venusfr.getInstance().getStyleManager().getStyleList()) {
            if (MathUtil.isHovered(f, f2, f12 + 5.0f, f13, 85.0f, f14)) {
                bl = true;
            }
            if (venusfr.getInstance().getStyleManager().getCurrentStyle() == style) {
                Fonts.montserrat.drawText(matrixStack, style.getStyleName(), f12 + 0.75f + 52.5f - 28.0f, f13 + f14 / 2.0f + 7.0f - Fonts.montserrat.getHeight(6.0f) / 2.0f, style.getFirstColor().getRGB(), 6.0f, 0.05f);
                DisplayUtils.drawRoundedRect(f12 + 5.0f, f13 + 7.0f, 12.5f, f14, 10.0f, style.getFirstColor().getRGB());
                DisplayUtils.drawShadow(f12 + 5.0f, f13 + 7.5f, 12.5f, f14, 10, style.getFirstColor().getRGB());
            }
            DisplayUtils.drawRoundedRect(f12 + 5.0f, f13 + 7.5f, 12.5f, f14, 2.0f, style.getFirstColor().getRGB());
            Fonts.montserrat.drawText(matrixStack, style.getStyleName(), f12 + 0.75f + 52.5f - 28.0f, f13 + f14 / 2.0f + 7.0f - Fonts.montserrat.getHeight(6.0f) / 2.0f, -1, 6.0f, 0.05f);
            f13 += 5.0f + f14;
            ++n;
        }
        if (MathUtil.isHovered(f, f2, f12, f13, 105.0f, f7)) {
            if (bl) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
            } else {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            }
        }
        Scissor.unset();
        Scissor.pop();
        this.max = (float)(n * venusfr.getInstance().getStyleManager().getStyleList().size()) * 1.21f;
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        float f3 = 25.0f;
        int n2 = 0;
        float f4 = this.getX() + 5.0f;
        float f5 = this.getY() + (float)n2 + f3 + 5.0f + this.getAnimatedScrool();
        for (Style style : venusfr.getInstance().getStyleManager().getStyleList()) {
            float f6 = f5 + 7.5f;
            float f7 = 12.0f;
            if (MathUtil.isHovered(f, f2, f4 + 5.0f, f6, 12.5f, f7)) {
                venusfr.getInstance().getStyleManager().setCurrentStyle(style);
            }
            f5 += 5.0f + f7;
            ++n2;
        }
    }

    @Override
    public void mouseRelease(float f, float f2, int n) {
    }

    public ResourceLocation getBrushIcon() {
        return this.brushIcon;
    }

    @Override
    public float getMax() {
        return this.max;
    }
}

