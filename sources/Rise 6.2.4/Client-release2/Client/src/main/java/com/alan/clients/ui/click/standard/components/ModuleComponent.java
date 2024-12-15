package com.alan.clients.ui.click.standard.components;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.Module;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.click.standard.components.value.ValueComponent;
import com.alan.clients.ui.click.standard.components.value.impl.BoundsNumberValueComponent;
import com.alan.clients.ui.click.standard.components.value.impl.NumberValueComponent;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.ui.click.standard.screen.impl.SearchScreen;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.localization.Localization;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
import lombok.Getter;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;
import java.util.ArrayList;

@Getter
public class ModuleComponent implements Accessor {

    public Module module;
    public Vector2f scale = getClickGUI().getModuleDefaultScale();
    public boolean expanded;
    public ArrayList<ValueComponent> valueList = new ArrayList<>();
    public Vector2d position;
    public double opacity;
    public StopWatch stopwatch = new StopWatch();
    public Animation hoverAnimation = new Animation(Easing.LINEAR, 50);
    public Animation opening = new Animation(Easing.LINEAR, 200);
    public Animation settingOpacity = new Animation(Easing.LINEAR, 5000);
    public boolean mouseDown;

    public ModuleComponent(final Module module) {
        this.module = module;

        opening.setValue(scale.y);
        settingOpacity.setValue(0);

        module.getAllValues().forEach(value -> {
            ValueComponent component = value.createUIComponent();
            if (component != null) valueList.add(component);
        });
    }

    public void draw(final Vector2d position, final int mouseX, final int mouseY, final float partialTicks) {
        this.position = position;

        /* Allows for settings to be drawn */
        float defaultHeight = 38;
        float height = defaultHeight;

        boolean render = !(position == null || position.y + scale.y < getClickGUI().position.y || position.y > getClickGUI().position.y + getClickGUI().scale.y);

        handle:
        {
            if (!render) break handle;

            final RiseClickGUI clickGUI = this.getClickGUI();

            // Main module background
            RenderUtil.roundedRectangle(position.x, position.y, scale.x, scale.y, 6, Colors.OVERLAY.get());
            final Color fontColor = Colors.TEXT.getWithAlpha(module.isEnabled() ? 255 : 200);

            // Hover animation
            final boolean overModule = GUIUtil.mouseOver(position.x, position.y, scale.x, this.scale.y, mouseX, mouseY);

            hoverAnimation.run(overModule ? mouseDown ? 35 : 20 : 0);

            // Main module background HOVER OVERLAY
            RenderUtil.roundedRectangle(position.x, position.y, scale.x, scale.y, 6, ColorUtil.withAlpha(Color.BLACK, (int) hoverAnimation.getValue()));

            // Draw the module's category if the user is searching
            if (clickGUI.getRenderedScreen() instanceof SearchScreen) {
                Fonts.MAIN.get(15, Weight.REGULAR).draw("(" + Localization.get(module.getModuleInfo().category().getName()) + ")",
                        (float) (position.getX() + Fonts.MAIN.get(20, Weight.REGULAR).width(this.module.getName()) + 10F),
                        (float) position.getY() + 10, ColorUtil.withAlpha(fontColor, 64).hashCode());
            }

            // Draw module name
            Fonts.MAIN.get(20, Weight.REGULAR).draw(this.module.getName(), (float) position.x + 6f, (float) position.y + 8,
                    module.isEnabled() ? getTheme().getAccentColor(new Vector2d(0, position.y / 5)).getRGB() : fontColor.getRGB());

            // Draw module category
            Fonts.MAIN.get(15, Weight.REGULAR).draw(Localization.get(module.getModuleInfo().description()), (float) position.x + 6f,
                    (float) position.y + 25, ColorUtil.withAlpha(fontColor, 70).hashCode());

            scale = new Vector2f(getClickGUI().moduleDefaultScale.x, height);
        }

        if (!opening.isFinished() || expanded) {
            for (final ValueComponent valueComponent : this.getValueList()) {
                if (valueComponent.getValue() != null && valueComponent.getValue().getHideIf() != null && valueComponent.getValue().getHideIf().getAsBoolean()) {
                    continue;
                }

                if (valueComponent.getValue().getInternalHideIf() != null && valueComponent.getValue().getInternalHideIf().getAsBoolean()) {
                    continue;
                }

                valueComponent.setOpacity(valueComponent.position == null ? 0 : valueComponent.position.y < position.y + opening.getValue() + 15 ? (int) settingOpacity.getValue() : 0);
                valueComponent.setOpacity(valueComponent.getValue().getHideIf() == null ? valueComponent.getOpacity() : Math.max(valueComponent.getOpacity() - 40, 0));

                if (render) {
                    valueComponent.draw(new Vector2d(position.x + 6f +
                            (valueComponent.getValue().getHideIf() == null ? 0 : 10) +
                            (valueComponent.getValue().getInternalHideIf() == null ? 0 : 10),
                            (float) (position.y + height + 1)), mouseX, mouseY, partialTicks);
                }

                height = (float) (height + valueComponent.getHeight());
            }

            // This makes the expanded category look better
            height -= 1;
        }

        opening.setDuration(Math.min((long) height * 3, 450));
        opening.setEasing(Easing.EASE_OUT_EXPO);
        opening.run(expanded ? height : defaultHeight);
        scale.y = (float) opening.getValue();

        settingOpacity.setDuration(expanded ? opening.getDuration() / 2 : opening.getDuration() / 3);
        settingOpacity.run(expanded ? 255 : 0);

    }

    public void key(final char typedChar, final int keyCode) {
        if (position == null || position.y + scale.y < getClickGUI().position.y || position.y > getClickGUI().position.y + getClickGUI().scale.y)
            return;

        if (this.isExpanded()) {
            for (final ValueComponent valueComponent : this.getValueList()) {
                valueComponent.key(typedChar, keyCode);
            }
        }

//        final boolean overModule = GUIUtil.mouseOver(position.x, position.y, scale.x, getClickGUI().moduleDefaultScale.getY() - 3, Mouse.getMouse().x, Mouse.getMouse().y);
//        if (overModule) {
//            module.setKeyCode(keyCode);
//        }
    }

    public void click(final int mouseX, final int mouseY, final int mouseButton) {
        /* Allows the module to be toggled */
        if (position == null || position.y + scale.y < getClickGUI().position.y || position.y > getClickGUI().position.y + getClickGUI().scale.y)
            return;

        final Vector2f clickGUIPosition = getClickGUI().position;
        final Vector2f clickGUIScale = getClickGUI().scale;

        final boolean left = mouseButton == 0;
        final boolean right = mouseButton == 1;
        final boolean overClickGUI = GUIUtil.mouseOver(clickGUIPosition.x, clickGUIPosition.y, clickGUIScale.x, clickGUIScale.y, mouseX, mouseY);
        final boolean overModule = GUIUtil.mouseOver(position.x, position.y, scale.x, getClickGUI().moduleDefaultScale.getY() - 3, mouseX, mouseY);

        if (overModule && getClickGUI().overlayPresent == null) {
            mouseDown = true;

            double valueSize = 0;
            for (ValueComponent valueComponent : valueList) valueSize += valueComponent.getHeight();

            if (left) {
                if (overClickGUI) {
                    this.module.toggle();
                }
            } else if (right && this.module.getValues().size() != 0 && valueSize != 0) {
                this.expanded = !this.expanded;

                for (final ValueComponent valueComponent : this.getValueList()) {
                    if (valueComponent instanceof BoundsNumberValueComponent) {
                        final BoundsNumberValueComponent boundsNumberValueComponent = ((BoundsNumberValueComponent) valueComponent);
                        boundsNumberValueComponent.grabbed1 = boundsNumberValueComponent.grabbed2 = false;
                    } else if (valueComponent instanceof NumberValueComponent) {
                        ((NumberValueComponent) valueComponent).grabbed = false;
                    }
                }
            }
        }

        if (this.isExpanded()) {
            for (final ValueComponent valueComponent : this.getValueList()) {
                if (valueComponent.getValue() != null && valueComponent.getValue().getHideIf() != null && valueComponent.getValue().getHideIf().getAsBoolean()) {
                    continue;
                }

                if (valueComponent.click(mouseX, mouseY, mouseButton)) {
                    break;
                }
            }
        }
    }

    public void bloom() {
        if (position == null) {
            return;
        }

        if (position.y + scale.y < getClickGUI().position.y || position.y > getClickGUI().position.y + getClickGUI().scale.y)
            return;

        if (!opening.isFinished() || expanded) {
            for (final ValueComponent valueComponent : this.getValueList()) {
                if (valueComponent.getValue() != null &&
                        (valueComponent.getValue().getHideIf() == null || !valueComponent.getValue().getHideIf().getAsBoolean()) &&
                        (valueComponent.getValue().getInternalHideIf() == null || !valueComponent.getValue().getInternalHideIf().getAsBoolean())) {

                    valueComponent.bloom();
                }
            }
        }
    }

    public void released() {
        mouseDown = false;

        if (this.isExpanded()) {
            for (final ValueComponent valueComponent : this.getValueList()) {
                valueComponent.released();
            }
        }
    }
}