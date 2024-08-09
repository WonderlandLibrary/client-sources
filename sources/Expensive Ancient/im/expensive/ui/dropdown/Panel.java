package im.expensive.ui.dropdown;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import im.expensive.Expensive;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.ui.dropdown.components.ModuleComponent;
import im.expensive.ui.dropdown.impl.Component;
import im.expensive.ui.dropdown.impl.IBuilder;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.Stencil;
import im.expensive.utils.render.font.Fonts;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;

@Getter
@Setter
public class Panel implements IBuilder {

    private final Category category;
    protected float x;
    protected float y;
    protected final float width = 135;
    protected final float height = 747 / 2f;

    private List<ModuleComponent> modules = new ArrayList<>();
    private float scroll, animatedScrool;


    public Panel(Category category) {
        this.category = category;

        for (Function function : Expensive.getInstance().getFunctionRegistry().getFunctions()) {
            if (function.getCategory() == category) {
                ModuleComponent component = new ModuleComponent(function);
                component.setPanel(this);
                modules.add(component);
            }
        }

    }

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {

        animatedScrool = MathUtil.fast(animatedScrool, scroll, 10);
        float header = 55 / 2f;
        float headerFont = 9;

        DisplayUtils.drawRoundedRect(x, y, width, header, new Vector4f(7, 0, 7, 0),
                ColorUtils.rgba(23, 23, 23, (int) (255 * 0.43)));

        DisplayUtils.drawRoundedRect(x, y, width, height, new Vector4f(7, 7, 7, 7),
                ColorUtils.rgba(17, 17, 17, (int) (255 * 0.65)));

        DisplayUtils.drawRectHorizontalW(x, y, width, header,
                ColorUtils.rgba(17, 17, 17, 64), ColorUtils.rgba(17, 17, 17, 0));

        DisplayUtils.drawRectVerticalW(x, y + header, width, 0.5f, ColorUtils.rgb(24, 24, 30),
                ColorUtils.rgb(32, 34, 40));

        Fonts.montserrat.drawCenteredText(stack, category.name(), x + width / 2f,
                y + header / 2f - Fonts.montserrat.getHeight(headerFont) / 2f - 1, ColorUtils.rgb(161, 164, 177),
                headerFont, 0.1f);

        drawComponents(stack, mouseX, mouseY);

        drawOutline();
        DisplayUtils.drawRoundedRect(x, y + height - header, width, header, new Vector4f(0, 7, 0, 7),
                new Vector4i(Color.TRANSLUCENT, ColorUtils.rgba(23, 23, 23, (int) (255 * 0.33)), Color.TRANSLUCENT,
                        ColorUtils.rgba(23, 23, 23, (int) (255 * 0.33))));

    }

    protected void drawOutline() {
        Stencil.initStencilToWrite();

        DisplayUtils.drawRoundedRect(x + 0.5f, y + 0.5f, width - 1, height - 1, new Vector4f(6.5F, 6.5F, 6.5F, 6.5F),
                ColorUtils.rgba(23, 23, 23, (int) (255 * 0.33)));

        Stencil.readStencilBuffer(0);

        DisplayUtils.drawRoundedRect(x, y, width, height,
                new Vector4f(6.5f, 6.5f, 6.5f, 6.5f),
                new Vector4i(ColorUtils.rgb(48, 53, 60), ColorUtils.rgb(0, 0, 0), ColorUtils.rgb(48, 53, 60),
                        ColorUtils.rgb(0, 0, 0)));

        Stencil.uninitStencilBuffer();
    }

    float max = 0;

    private void drawComponents(MatrixStack stack, float mouseX, float mouseY) {
        float animationValue = (float) DropDown.getAnimation().getValue() * DropDown.scale;

        float halfAnimationValueRest = (1 - animationValue) / 2f;
        float height = getHeight();
        float testX = getX() + (getWidth() * halfAnimationValueRest);
        float testY = getY() + 55 / 2f + (height * halfAnimationValueRest);
        float testW = getWidth() * animationValue;
        float testH = height * animationValue;

        testX = testX * animationValue + ((Minecraft.getInstance().getMainWindow().getScaledWidth() - testW) *
                halfAnimationValueRest);

        Scissor.push();
        Scissor.setFromComponentCoordinates(testX, testY, testW, testH);
        float offset = 0;
        float header = 55 / 2f;

        if (max > height - header - 10) {
            scroll = MathHelper.clamp(scroll, -max + height - header - 10, 0);
            animatedScrool = MathHelper.clamp(animatedScrool, -max + height - header - 10, 0);
        }
        else {
            scroll = 0;
            animatedScrool = 0;
        }
        for (ModuleComponent component : modules) {
            component.setX(getX() + 5);
            component.setY(getY() + header + offset + 6 + animatedScrool);
            component.setWidth(getWidth() - 10);
            component.setHeight(20);
            component.animation.update();
            if (component.animation.getValue() > 0) {
                float componentOffset = 0;
                for (Component component2 : component.getComponents()) {
                    if (component2.isVisible())
                        componentOffset += component2.getHeight();
                }
                componentOffset *= component.animation.getValue();
                component.setHeight(component.getHeight() + componentOffset);
            }
            component.render(stack, mouseX, mouseY);
            offset += component.getHeight() + 3.5f;
        }
        max = offset;

        Scissor.unset();
        Scissor.pop();

    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int button) {
        for (ModuleComponent component : modules) {
            component.mouseClick(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {
        for (ModuleComponent component : modules) {
            component.keyPressed(key, scanCode, modifiers);
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        for (ModuleComponent component : modules) {
            component.charTyped(codePoint, modifiers);
        }
    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int button) {
        for (ModuleComponent component : modules) {
            component.mouseRelease(mouseX, mouseY, button);
        }
    }

}
