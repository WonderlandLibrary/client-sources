package fr.dog.ui.clickgui.dropdown.component;

import fr.dog.Dog;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.ui.framework.Component;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.opengl.StencilUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryComponent extends Component {

    private final ModuleCategory category;

    private final List<ModuleComponent> modules = new ArrayList<>();

    private float mouseDifferenceX, mouseDifferenceY;
    private boolean dragged;

    private final Animation openAnimation = new Animation(Easing.EASE_IN_OUT_QUAD, 500);

    public CategoryComponent(final ModuleCategory category, final float x, final float y, final float width, final float height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        for (Module module : Dog.getInstance().getModuleManager().getMultipleBy(module -> module.getCategory() == category))
            modules.add(new ModuleComponent(module));
    }

    public void render(int mouseX, int mouseY) {
        TTFFontRenderer openSansBold = Fonts.getOpenSansBold(18);

        StencilUtil.renderStencil(
                () -> {
                    float totalHeight = this.height;
                    for (ModuleComponent component : modules) {
                        totalHeight += component.getCalculatedHeight();
                    }

                    RenderUtil.drawRect(x, y, width, (float) (totalHeight * openAnimation.getValue()), Color.WHITE);
                },
                () -> {
                    if (!modules.isEmpty()) {
                        float newY = y + height;

                        for (ModuleComponent component : modules) {
                            component.setX(x);
                            component.setY(newY);

                            component.setWidth(width);

                            component.render(mouseX, mouseY);

                            newY += component.getCalculatedHeight();
                        }

                        RenderUtil.verticalGradient(x, y + height, width, 10, new Color(0, 0, 0, 35), new Color(0, 0, 0, 0));
                    }
                }
        );

        RenderUtil.drawRect(x, y, width, height, new Color(30, 30, 30));

        openSansBold.drawString(category.getName(), x + height / 2 - openSansBold.getHeight(category.getName()) / 2F,
                y + height / 2 - openSansBold.getHeight(category.getName()) / 2F, Color.white.getRGB());

        if (dragged) {
            x = mouseX - mouseDifferenceX;
            y = mouseY - mouseDifferenceY;
        }

        openAnimation.run(expanded ? 1.0F : 0.0F);
    }

    public boolean click(int mouseX, int mouseY, int button) {
        super.click(mouseX, mouseY, button);
        if (hovered) {
            switch (button) {
                case 0 -> {
                    if (isHovered(mouseX, mouseY)) {
                        mouseDifferenceX = mouseX - x;
                        mouseDifferenceY = mouseY - y;

                        dragged = true;
                    }
                }
                case 1 -> expanded = !expanded;
            }
        }

        modules.forEach(component -> component.click(mouseX, mouseY, button));
        return hovered;
    }

    public void release(int mouseX, int mouseY, int state) {
        dragged = false;

        modules.forEach(component -> component.release(mouseX, mouseY, state));
    }

    public void type(char typedChar, int keyCode) {
        modules.forEach(component -> component.type(typedChar, keyCode));
    }

}