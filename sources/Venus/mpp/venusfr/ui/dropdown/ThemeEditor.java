/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.ui.dropdown.impl.IBuilder;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import net.minecraft.util.ResourceLocation;

public class ThemeEditor
implements IBuilder {
    private int x;
    private int y;
    private int width;
    private int height;
    private final ResourceLocation themeIcon = new ResourceLocation("venusfr/images/theme.png");
    public boolean isClickedButton = false;
    public boolean toRender = false;

    public ThemeEditor(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
    }

    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        DisplayUtils.drawImage(this.themeIcon, (float)this.x, (float)this.y, (float)this.width, (float)this.height, ColorUtils.getColor(1));
    }

    public boolean mouseClicked(double d, double d2, int n) {
        this.isClickedButton = MathUtil.isHovered((float)d, (float)d2, this.x, this.y, this.width, this.height);
        if (this.isClickedButton && this.toRender) {
            this.isClickedButton = false;
            this.toRender = false;
            return this.isClickedButton;
        }
        if (this.isClickedButton) {
            this.toRender = true;
            return this.isClickedButton;
        }
        return this.isClickedButton;
    }

    @Override
    public void mouseRelease(float f, float f2, int n) {
    }
}

