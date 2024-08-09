/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.ui.dropdown.DropDown;
import mpp.venusfr.ui.dropdown.components.ModuleComponent;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.ui.dropdown.impl.IBuilder;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;

public class Panel
implements IBuilder {
    private final Category category;
    protected float x;
    protected float y;
    protected final float width = 105.0f;
    protected final float height = 220.0f;
    private List<ModuleComponent> modules = new ArrayList<ModuleComponent>();
    private float scroll;
    private float animatedScrool;
    float max = 0.0f;

    public Panel(Category category) {
        this.category = category;
        for (Function function : venusfr.getInstance().getFunctionRegistry().getFunctions()) {
            if (function.getCategory() != category) continue;
            ModuleComponent moduleComponent = new ModuleComponent(function);
            moduleComponent.setPanel(this);
            this.modules.add(moduleComponent);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        this.animatedScrool = MathUtil.fast(this.animatedScrool, this.scroll, 10.0f);
        float f3 = 20.0f;
        float f4 = 8.0f;
        DisplayUtils.drawRoundedRect(this.x, this.y, 105.0f, 220.0f, 13.0f, ColorUtils.rgba(25, 26, 40, 255));
        DisplayUtils.drawRoundedRect(this.x + 3.8f, this.y + 3.5f, 97.0f, 213.0f, 12.0f, ColorUtils.rgba(25, 26, 40, 255));
        Fonts.montserrat.drawCenteredText(matrixStack, this.category.name(), this.x + 52.5f, this.y + f3 / 2.0f - Fonts.montserrat.getHeight(f4) / 2.0f - 1.0f + 5.0f, ColorUtils.rgb(255, 255, 255), f4, 0.1f);
        this.drawComponents(matrixStack, f, f2);
        if (this.max > 320.0f - f3 - 10.0f) {
            this.drawScrollbar(matrixStack);
        }
    }

    private void drawScrollbar(MatrixStack matrixStack) {
        float f = 20.0f;
        float f2 = 280.0f;
        float f3 = this.y + 30.0f;
        float f4 = this.max - (320.0f - f - 10.0f);
        float f5 = Math.max(20.0f, f2 * (320.0f - f - 10.0f) / this.max);
        float f6 = f3 + -this.animatedScrool / f4 * (f2 - f5);
        DisplayUtils.drawRoundedRect(this.x + 125.0f - 5.0f, f6, 2.0f, f5, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), ColorUtils.getColor(1));
    }

    private void drawComponents(MatrixStack matrixStack, float f, float f2) {
        float f3 = (float)DropDown.getAnimation().getValue() * DropDown.scale;
        float f4 = (1.0f - f3) / 2.0f;
        float f5 = this.getHeight();
        float f6 = this.getX() + this.getWidth() * f4;
        float f7 = this.getY() + 25.0f + f5 * f4;
        float f8 = this.getWidth() * f3;
        float f9 = f5 * f3 - 33.0f;
        f6 = f6 * f3 + ((float)Minecraft.getInstance().getMainWindow().getScaledWidth() - f8) * f4;
        Scissor.push();
        Scissor.setFromComponentCoordinates(f6, f7, f8, f9);
        float f10 = -1.0f;
        float f11 = 25.0f;
        if (this.max > f5 - f11 - 10.0f) {
            this.scroll = MathHelper.clamp(this.scroll, -this.max + f5 - f11 - 10.0f, 0.0f);
            this.animatedScrool = MathHelper.clamp(this.animatedScrool, -this.max + f5 - f11 - 10.0f, 0.0f);
        } else {
            this.scroll = 0.0f;
            this.animatedScrool = 0.0f;
        }
        for (ModuleComponent moduleComponent : this.modules) {
            if (venusfr.getInstance().getDropDown().searchCheck(moduleComponent.getFunction().getName())) continue;
            moduleComponent.setX(this.getX() + 5.0f);
            moduleComponent.setY(this.getY() + f11 + f10 + 6.0f + this.animatedScrool);
            moduleComponent.setWidth(this.getWidth() - 10.0f);
            moduleComponent.setHeight(15.0f);
            moduleComponent.animation.update();
            if (moduleComponent.animation.getValue() > 0.0) {
                float f12 = 0.0f;
                for (Component component : moduleComponent.getComponents()) {
                    if (!component.isVisible()) continue;
                    f12 += component.getHeight() + 5.0f;
                }
                f12 = (float)((double)f12 * moduleComponent.animation.getValue());
                moduleComponent.setHeight(moduleComponent.getHeight() + f12);
            }
            moduleComponent.render(matrixStack, f, f2);
            f10 += moduleComponent.getHeight() + 6.0f;
        }
        this.max = f10;
        Scissor.unset();
        Scissor.pop();
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        for (ModuleComponent moduleComponent : this.modules) {
            if (venusfr.getInstance().getDropDown().searchCheck(moduleComponent.getFunction().getName()) || !(f >= this.getX()) || !(f <= this.getX() + this.getWidth()) || !(f2 >= this.getY()) || !(f2 <= this.getY() + this.getHeight())) continue;
            moduleComponent.mouseClick(f, f2, n);
        }
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        for (ModuleComponent moduleComponent : this.modules) {
            moduleComponent.keyPressed(n, n2, n3);
        }
    }

    @Override
    public void charTyped(char c, int n) {
        for (ModuleComponent moduleComponent : this.modules) {
            moduleComponent.charTyped(c, n);
        }
    }

    @Override
    public void mouseRelease(float f, float f2, int n) {
        for (ModuleComponent moduleComponent : this.modules) {
            moduleComponent.mouseRelease(f, f2, n);
        }
    }

    public Category getCategory() {
        return this.category;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public List<ModuleComponent> getModules() {
        return this.modules;
    }

    public float getScroll() {
        return this.scroll;
    }

    public float getAnimatedScrool() {
        return this.animatedScrool;
    }

    public float getMax() {
        return this.max;
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public void setModules(List<ModuleComponent> list) {
        this.modules = list;
    }

    public void setScroll(float f) {
        this.scroll = f;
    }

    public void setAnimatedScrool(float f) {
        this.animatedScrool = f;
    }

    public void setMax(float f) {
        this.max = f;
    }
}

