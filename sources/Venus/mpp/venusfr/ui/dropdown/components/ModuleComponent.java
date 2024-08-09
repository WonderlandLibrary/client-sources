/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.settings.Setting;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.functions.settings.impl.StringSetting;
import mpp.venusfr.ui.dropdown.components.settings.BindComponent;
import mpp.venusfr.ui.dropdown.components.settings.BooleanComponent;
import mpp.venusfr.ui.dropdown.components.settings.ModeComponent;
import mpp.venusfr.ui.dropdown.components.settings.MultiBoxComponent;
import mpp.venusfr.ui.dropdown.components.settings.SliderComponent;
import mpp.venusfr.ui.dropdown.components.settings.StringComponent;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.client.KeyStorage;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Stencil;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class ModuleComponent
extends Component {
    private final Vector4f ROUNDING_VECTOR = new Vector4f(5.0f, 5.0f, 5.0f, 5.0f);
    private final Function function;
    public Animation animation = new Animation();
    public boolean open;
    private boolean bind;
    private final ObjectArrayList<Component> components = new ObjectArrayList();
    private boolean hovered = false;

    public ModuleComponent(Function function) {
        this.function = function;
        for (Setting<?> setting : function.getSettings()) {
            Setting setting2;
            if (setting instanceof BooleanSetting) {
                setting2 = (BooleanSetting)setting;
                this.components.add(new BooleanComponent((BooleanSetting)setting2));
            }
            if (setting instanceof SliderSetting) {
                setting2 = (SliderSetting)setting;
                this.components.add(new SliderComponent((SliderSetting)setting2));
            }
            if (setting instanceof BindSetting) {
                setting2 = (BindSetting)setting;
                this.components.add(new BindComponent((BindSetting)setting2));
            }
            if (setting instanceof ModeSetting) {
                setting2 = (ModeSetting)setting;
                this.components.add(new ModeComponent((ModeSetting)setting2));
            }
            if (setting instanceof ModeListSetting) {
                setting2 = (ModeListSetting)setting;
                this.components.add(new MultiBoxComponent((ModeListSetting)setting2));
            }
            if (!(setting instanceof StringSetting)) continue;
            setting2 = (StringSetting)setting;
            this.components.add(new StringComponent((StringSetting)setting2));
        }
        this.animation = this.animation.animate(this.open ? 1.0 : 0.0, 0.3);
    }

    public void drawComponents(MatrixStack matrixStack, float f, float f2) {
        if (this.animation.getValue() > 0.0) {
            if (this.animation.getValue() > 0.1 && this.components.stream().filter(Component::isVisible).count() >= 1L) {
                DisplayUtils.drawRectVerticalW(this.getX() + 5.0f, this.getY() + 20.0f, this.getWidth() - 10.0f, 0.5, ColorUtils.rgb(42, 44, 50), ColorUtils.rgb(28, 28, 33));
            }
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(this.getX() + 0.5f, this.getY() + 0.5f, this.getWidth() - 1.0f, this.getHeight() - 1.0f, this.ROUNDING_VECTOR, ColorUtils.rgba(23, 23, 23, 84));
            Stencil.readStencilBuffer(1);
            float f3 = this.getY() + 20.0f;
            for (Component component : this.components) {
                if (!component.isVisible()) continue;
                component.setX(this.getX());
                component.setY(f3);
                component.setWidth(this.getWidth());
                component.render(matrixStack, f, f2);
                f3 += component.getHeight();
            }
            Stencil.uninitStencilBuffer();
        }
    }

    @Override
    public void mouseRelease(float f, float f2, int n) {
        for (Component component : this.components) {
            component.mouseRelease(f, f2, n);
        }
        super.mouseRelease(f, f2, n);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        int n = ColorUtils.interpolate(-1, -1, (float)this.function.getAnimation().getValue());
        int n2 = ColorUtils.interpolate(ColorUtils.rgb(61, 61, 80), ColorUtils.rgb(35, 36, 50), (float)this.function.getAnimation().getValue());
        this.function.getAnimation().update();
        super.render(matrixStack, f, f2);
        this.drawOutlinedRect(f, f2, n2);
        this.drawText(matrixStack, n);
        this.drawComponents(matrixStack, f, f2);
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        if (this.isHovered(f, f2, 20.0f)) {
            if (n == 0) {
                this.function.toggle();
            }
            if (n == 1) {
                this.open = !this.open;
                this.animation = this.animation.animate(this.open ? 1.0 : 0.0, 0.2, Easings.CIRC_OUT);
            }
            if (n == 2) {
                boolean bl = this.bind = !this.bind;
            }
        }
        if (this.isHovered(f, f2) && this.open) {
            for (Component component : this.components) {
                if (!component.isVisible()) continue;
                component.mouseClick(f, f2, n);
            }
        }
        super.mouseClick(f, f2, n);
    }

    @Override
    public void charTyped(char c, int n) {
        for (Component component : this.components) {
            if (!component.isVisible()) continue;
            component.charTyped(c, n);
        }
        super.charTyped(c, n);
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        for (Component component : this.components) {
            if (!component.isVisible()) continue;
            component.keyPressed(n, n2, n3);
        }
        if (this.bind) {
            if (n == 261 || n == 256) {
                this.function.setBind(0);
            } else {
                this.function.setBind(n);
            }
            this.bind = false;
        }
        super.keyPressed(n, n2, n3);
    }

    private void drawOutlinedRect(float f, float f2, int n) {
        Stencil.initStencilToWrite();
        DisplayUtils.drawRoundedRect(this.getX() + 2.5f, this.getY() + 0.5f, this.getWidth() - 5.0f, this.getHeight() - 1.0f, this.ROUNDING_VECTOR, n);
        Stencil.readStencilBuffer(0);
        DisplayUtils.drawRoundedRect(this.getX() + 2.0f, this.getY(), this.getWidth() - 5.0f, this.getHeight(), this.ROUNDING_VECTOR, n);
        Stencil.uninitStencilBuffer();
        DisplayUtils.drawRoundedRect(this.getX() + 2.0f, this.getY(), this.getWidth() - 5.0f, this.getHeight(), this.ROUNDING_VECTOR, n);
        if (MathUtil.isHovered(f, f2, this.getX(), this.getY(), this.getWidth(), 20.0f)) {
            if (!this.hovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                this.hovered = true;
            }
        } else if (this.hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            this.hovered = false;
        }
    }

    private void drawText(MatrixStack matrixStack, int n) {
        if (n == ColorUtils.rgb(35, 36, 50)) {
            Fonts.mslight.drawText(matrixStack, this.function.getName(), this.getX() + 6.0f, this.getY() + 6.5f - 3.0f, n, 5.0f, 0.1f);
        } else {
            Fonts.mslight.drawText(matrixStack, this.function.getName(), this.getX() + 6.0f, this.getY() + 6.5f - 3.0f, n, 7.0f, 0.1f);
        }
        if (this.components.stream().filter(Component::isVisible).count() >= 1L) {
            if (this.bind) {
                Fonts.mslight.drawText(matrixStack, this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), this.getX() + this.getWidth() - 6.0f - Fonts.mslight.getWidth(this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), 6.0f, 0.1f), this.getY() + Fonts.icons.getHeight(6.0f) + 1.0f, ColorUtils.rgb(161, 164, 177), 6.0f, 0.1f);
            } else {
                Fonts.icons.drawText(matrixStack, !this.open ? "B" : "C", this.getX() + this.getWidth() - 6.0f - Fonts.icons.getWidth(!this.open ? "B" : "C", 6.0f), this.getY() + Fonts.icons.getHeight(6.0f) + 1.0f, ColorUtils.rgb(161, 164, 177), 6.0f);
            }
        } else if (this.bind) {
            Fonts.mslight.drawText(matrixStack, this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), this.getX() + this.getWidth() - 6.0f - Fonts.mslight.getWidth(this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), 6.0f, 0.1f), this.getY() + Fonts.icons.getHeight(6.0f) + 1.0f, ColorUtils.rgb(161, 164, 177), 6.0f, 0.1f);
        }
    }

    public Vector4f getROUNDING_VECTOR() {
        return this.ROUNDING_VECTOR;
    }

    public Function getFunction() {
        return this.function;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isBind() {
        return this.bind;
    }

    public ObjectArrayList<Component> getComponents() {
        return this.components;
    }

    public boolean isHovered() {
        return this.hovered;
    }
}

