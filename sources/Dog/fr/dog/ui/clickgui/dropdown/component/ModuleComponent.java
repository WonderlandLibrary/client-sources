package fr.dog.ui.clickgui.dropdown.component;

import fr.dog.Dog;
import fr.dog.module.Module;
import fr.dog.property.Property;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.theme.Theme;
import fr.dog.ui.clickgui.dropdown.component.setting.PropertyComponent;
import fr.dog.ui.clickgui.dropdown.component.setting.impl.BooleanPropertyComponent;
import fr.dog.ui.clickgui.dropdown.component.setting.impl.ModePropertyComponent;
import fr.dog.ui.clickgui.dropdown.component.setting.impl.NumberPropertyComponent;
import fr.dog.ui.framework.Component;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleComponent extends Component {
    private final Module module;
    private final List<PropertyComponent<?>> properties = new ArrayList<>();

    private final Animation hoverAnimation = new Animation(Easing.EASE_IN_OUT_QUAD, 500);
    private final Animation openAnimation = new Animation(Easing.EASE_IN_OUT_QUAD, 500);

    private final Color background = new Color(40, 40, 40);

    public ModuleComponent(final Module module) {
        this.module = module;

        int index = 0;
        for (Property<?> property : module.getPropertyList()) {
            if (property instanceof BooleanProperty booleanProperty)
                this.properties.add(new BooleanPropertyComponent(index, booleanProperty));
            if (property instanceof NumberProperty numberProperty)
                this.properties.add(new NumberPropertyComponent(index, numberProperty));
            if (property instanceof ModeProperty modeProperty)
                this.properties.add(new ModePropertyComponent(index, modeProperty));
            ++index;
        }

        this.height = 15;
    }

    public void render(int mouseX, int mouseY) {
        boolean hovered = isHovered(mouseX, mouseY);

        RenderUtil.drawRect(x, y, width, height, background);

        if (!hoverAnimation.isFinished() || hovered) {
            RenderUtil.drawRect(x, y, width, height, new Color(30, 30, 30, (int) (255 * hoverAnimation.getValue())));
        }

        Theme theme = Dog.getInstance().getThemeManager().getCurrentTheme();

        if (module.isEnabled())
            RenderUtil.horizontalGradient(x, y, width, height, theme.getColor1(), theme.getColor2());

        TTFFontRenderer openSansRegular16 = Fonts.getOpenSansMedium(14);

        openSansRegular16.drawString(module.getName(),
                x + height / 2 - openSansRegular16.getHeight(module.getName()) / 2F,
                y + height / 2 - openSansRegular16.getHeight(module.getName()) / 2F, Color.WHITE.getRGB());

        if (expanded || !openAnimation.isFinished()) {
            float newY = 0;

            for (PropertyComponent<?> component : this.properties) {
                if (!component.getProperty().isVisible())
                    continue;

                component.setX(x);
                component.setY(y + height + newY * (float) openAnimation.getValue());

                component.setWidth(width);

                component.render(mouseX, mouseY);
                newY += component.getHeight();
            }
        }

        hoverAnimation.run(hovered ? 1.0F : 0.0F);
        openAnimation.run(expanded ? 1.0F : 0.0F);
    }

    public boolean click(int mouseX, int mouseY, int button) {
        super.click(mouseX, mouseY, button);
        if (hovered)
            switch (button) {
                case 0 -> module.toggle();
                case 1 -> expanded = !expanded;
            }

        if (!expanded)
            return hovered;

        properties.stream()
                .filter(component -> component.getProperty().isVisible())
                .forEach(component -> component.click(mouseX, mouseY, button));

        return hovered;
    }

    public void release(int mouseX, int mouseY, int state) {
        properties.stream()
                .filter(component -> component.getProperty().isVisible())
                .forEach(component -> component.release(mouseX, mouseY, state));
    }

    public void type(char typedChar, int keyCode) {
        properties.stream()
                .filter(component -> component.getProperty().isVisible())
                .forEach(component -> component.type(typedChar, keyCode));
    }

    public float getCalculatedHeight() {
        float totalHeight = 0;

        for (PropertyComponent<?> component : this.properties) {
            if (!component.getProperty().isVisible())
                continue;

            totalHeight += component.getHeight();
        }

        return height + (float) (totalHeight * openAnimation.getValue());
    }
}
