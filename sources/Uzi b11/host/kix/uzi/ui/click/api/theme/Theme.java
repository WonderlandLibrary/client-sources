package host.kix.uzi.ui.click.api.theme;

import java.util.HashMap;
import java.util.Map;

import host.kix.uzi.ui.click.api.component.Component;
import host.kix.uzi.ui.click.api.panel.Panel;

/**
 * @author Marc
 * @since 7/20/2016
 */
public abstract class Theme {

    /**
     * The name of the theme.
     */
    private String name;

    /**
     * The width and the height of the theme's panels.
     */
    private int width, height;

    /**
     * A collection that maps a component class to a certain
     * UI implementation.
     */
    protected Map<Class<? extends Component>, ComponentUI> themedComponents;

    public Theme(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.themedComponents = new HashMap<>();

        this.loadComponents();
    }

    /**
     * Map all components to their respective UIs.
     */
    public abstract void loadComponents();

    /**
     * This method should be used to draw the panel.
     *
     * @param panel panel to draw
     * @param x     the x coordinate of the mouse
     * @param y     the y coordinate of the mouse
     */
    public abstract void drawPanel(Panel panel, int x, int y);

    /**
     * This method should be used to be draw the component.
     *
     * @param component component to draw
     * @param x         the x coordinate of the mouse
     * @param y         the y coordinate of the mouse
     */
    public abstract void drawComponent(Component component, int x, int y);

    /**
     * This method should be used to handle mouse clicks for the panel.
     *
     * @param panel  panel to handle
     * @param x      the x coordinate of the mouse
     * @param y      the y coordinate of the mouse
     * @param button the button id of the mouse input
     */
    public abstract void mouseClicked(Panel panel, int x, int y, int button);

    /**
     * This method should be used to handle mouse releases for the panel.
     *
     * @param panel  panel to handle
     * @param x      the x coordinate of the mouse
     * @param y      the y coordinate of the mouse
     * @param button the button id of the mouse input
     */
    public void mouseReleased(Panel panel, int x, int y, int button) {
        panel.setDragging(false);

        if (panel.isOpen())
            panel.getComponents().forEach(c -> c.mouseReleased(x, y, button));
    }

    /**
     * When called, this method will handle key input.
     *
     * @param panel     panel to handle
     * @param typedChar the typed character
     * @param keyCode   the key code of the pressed key
     */
    public void keyTyped(Panel panel, char typedChar, int keyCode) {
        if (panel.isOpen())
            panel.getComponents().forEach(c -> c.keyTyped(typedChar, keyCode));
    }

    /**
     * Returns the name of the theme.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the width of the theme's panels.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the theme's panels.
     *
     * @return
     */
    public int getHeight() {
        return height;
    }
}
