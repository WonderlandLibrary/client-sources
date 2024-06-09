package host.kix.uzi.ui.click.api.component.impl;

import host.kix.uzi.ui.click.impl.GuiClick;
import host.kix.uzi.ui.click.api.component.Component;

/**
 * @author Marc
 * @since 7/20/2016
 */
public class Button extends Component {

    /**
     * The state of the button.
     */
    private boolean enabled;

    public Button(GuiClick parent, String label, boolean enabled, int x, int y, int width, int height) {
        super(parent, label, x, y, width, height);

        this.enabled = enabled;
    }

    @Override
    public void draw(int x, int y) {
        parent.getTheme().drawComponent(this, x, y);
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if (isHovering(x, y)) {
            switch (button) {
                case 0:
                    toggle();
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    /**
     * Returns the enabled state of the button.
     *
     * @return enabled state of the button
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled state to the specified state.
     *
     * @param enabled the new state
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the enabled tate of the button to the opposite state.
     */
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }

}
