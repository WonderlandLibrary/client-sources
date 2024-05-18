package host.kix.uzi.ui.click.api.theme;

import host.kix.uzi.ui.click.api.component.Component;

/**
 * @author Marc
 * @since 7/21/2016
 */
public abstract class ComponentUI<T extends Component> {

    /**
     * An instance of the theme.
     */
    protected Theme theme;

    public ComponentUI(Theme theme) {
        this.theme = theme;
    }

    /**
     * This method should be used to draw the component.
     *
     * @param component component to draw
     * @param x         the x coordinate of the mouse
     * @param y         the y coordinate of the mouse
     */
    public abstract void draw(T component, int x, int y);

    public Theme getTheme() {
        return theme;
    }
}
