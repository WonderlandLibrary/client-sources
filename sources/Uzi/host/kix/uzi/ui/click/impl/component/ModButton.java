package host.kix.uzi.ui.click.impl.component;

import host.kix.uzi.ui.click.impl.GuiClick;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.module.Module;
import org.lwjgl.input.Keyboard;

import host.kix.uzi.ui.click.api.component.Component;
import host.kix.uzi.ui.click.api.component.impl.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marc
 * @since 7/20/2016
 */
public class ModButton extends Button {

    /**
     * An instance of the toggleable mod.
     */
    private Module toggleable;

    /**
     * List of sub components for each mod.
     */
    private List<Component> subComponents;

    /**
     * If a player is key binding a mod.
     */
    private boolean binding;

    public ModButton(GuiClick parent, Module toggleable, int x, int y, int width, int height) {
        super(parent, toggleable.getName(), toggleable.isEnabled(), x, y, width, height);

        this.toggleable = toggleable;
        subComponents = new ArrayList<>();

        for (Value value : toggleable.getValues()) {
            Component subComponent = null;
            if (!(value.getValue() instanceof String)) {
                if (value.getValue() instanceof Boolean) {
                    subComponent = new ValueButton(parent, value, x, y, width, height);
                    subComponents.add(subComponent);
                } else if (value.getValue() instanceof Number) {
                    subComponent = new ValueSlider(parent, value, x, y, width, height);
                    subComponents.add(subComponent);
                } else if (value.getValue() instanceof Enum) {
                    subComponent = new ValueSpinner(parent, value, (Enum) value.getValue(), x, y, width, height);
                    subComponents.add(subComponent);
                }
                subComponent.setVisible(false);
            }
        }
    }

    @Override
    public void draw(int x, int y) {
        parent.getTheme().drawComponent(this, x, y);
    }


    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);

        if (isHovering(x, y)) {
            switch (button) {
                case 1:
                    subComponents.forEach(component -> {
                        component.setVisible((!component.isVisible()));
                    });
                    break;
                case 2:
                    binding = !binding;
                    break;
            }
        }

        subComponents.forEach(component -> {
            if (component.isVisible())
                component.mouseClicked(x, y, button);
        });
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        subComponents.forEach(component -> {
            if (component.isVisible())
                component.mouseReleased(x, y, button);
        });
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (binding) {
            if (keyCode == Keyboard.KEY_SPACE)
                toggleable.setBind(Keyboard.KEY_NONE);
            else
                toggleable.setBind(keyCode);

            binding = false;
        }
    }

    @Override
    public void toggle() {
        toggleable.setEnabled(!toggleable.isEnabled());
    }

    @Override
    public boolean isEnabled() {
        return toggleable.isEnabled();
    }

    /**
     * Determines whether the user is key binding a mod.
     *
     * @return binding
     */
    public boolean isBinding() {
        return binding;
    }


    /**
     * Returns the toggleable mod.
     *
     * @return toggleable
     */
    public Module getMod() {
        return toggleable;
    }

    /**
     * Returns the sub components of the mod.
     *
     * @return s
     */
    public List<Component> getSubComponents() {
        return subComponents;
    }
}
