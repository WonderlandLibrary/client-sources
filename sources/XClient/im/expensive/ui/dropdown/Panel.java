package im.expensive.ui.dropdown;

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
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

@Getter
@Setter
public class Panel implements IBuilder {

    private final Category category;
    protected float x;
    protected float y;
    protected final float width = 135;
    protected final float height = 547 / 2f;

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

        animatedScrool = MathUtil.fast(animatedScrool, scroll, 5);
        float header = 55 / 2f;
        float headerFont = 9;


        DisplayUtils.drawRoundedRect(x, y, width, height , 3,
                ColorUtils.rgba(10, 10, 10, (int) (200)));
        DisplayUtils.drawRoundedRect(x + 3, y + 20, width - 6, height - 23, 3,
                ColorUtils.rgba(10, 10, 10, (int) (255)));




        Fonts.sfMedium.drawCenteredText(stack, category.name(), x + width / 2f,
                y + header / 2f - Fonts.sfMedium.getHeight(headerFont) / 2f - 3, ColorUtils.rgb(161, 164, 177),
                headerFont, 0.1f);

        drawComponents(stack, mouseX, mouseY);

        drawOutline();

    }

    protected void drawOutline() {

    }

    float max = 0;

    private void drawComponents(MatrixStack stack, float mouseX, float mouseY) {
        float animationValue = (float) DropDown.getAnimation().getValue() * DropDown.scale;

        float halfAnimationValueRest = (1 - animationValue) / 2f;
        float height = getHeight();
        float testX = getX() + (getWidth() * halfAnimationValueRest);
        float testY = getY() + 41 / 2f + (height * halfAnimationValueRest);
        float testW = getWidth() * animationValue;
        float testH = (height - 25f) * animationValue;

        testX = testX * animationValue + ((Minecraft.getInstance().getMainWindow().getScaledWidth() - testW) *
                halfAnimationValueRest);

        Scissor.push();
        Scissor.setFromComponentCoordinates(testX, testY, testW, testH);
        float offset = 0;
        float header = 41 / 2f;

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
