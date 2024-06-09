package host.kix.uzi.ui.click.api.component;


import host.kix.uzi.ui.click.impl.GuiClick;

/**
 * @author Marc
 * @author 7/20/2016
 */
public abstract class Component {

    /**
     * An instance of GuiClick
     */
    protected final GuiClick parent;

    /**
     * The display name (or label) of the component.
     */
    private String label;

    /**
     * The x and y coordinate of the component.
     */
    private int x, y;

    /**
     * The width and height of the component.
     */
    private int width, height;

    /**
     * The visibility of the component.
     */
    private boolean visible = true;

    public Component(GuiClick parent, String label, int x, int y, int width, int height) {
        this.parent = parent;
        this.label = label;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Determines whether the user is hovering over the defined area.
     *
     * @param x the x coordinate of the mouse
     * @param y the y coordinate of the mouse
     * @return whether the user is hovering over the defined area
     */
    public boolean isHovering(int x, int y) {
        return x > this.x && y > this.y && x < this.x + width && y < this.y + height;
    }

    /**
     * When called, this method will draw the component.
     *
     * @param x the x coordinate of the mouse
     * @param y the y coordinate of the mouse
     */
    public abstract void draw(int x, int y);


    /**
     * When called, this method will handle mouse clicking.
     *
     * @param x      the x coordinate of the mouse
     * @param y      the y coordinate of the mouse
     * @param button the button id of the mouse input
     */
    public abstract void mouseClicked(int x, int y, int button);


    /**
     * When called, this method will handle mouse releases.
     *
     * @param x      the x coordinate of the mouse
     * @param y      the y coordinate of the mouse
     * @param button the button id of the mouse input
     */
    public abstract void mouseReleased(int x, int y, int button);

    /**
     * When called, this method will handle key input.
     *
     * @param typedChar the typed character
     * @param keyCode   the key code of the pressed key
     */
    public abstract void keyTyped(char typedChar, int keyCode);

    /**
     * Sets the size of the component to the provided parameters.
     *
     * @param x      the new x
     * @param y      the new y
     * @param width  the new width
     * @param height the new height
     */
    public void setSize(int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Returns the label of the component.
     *
     * @return label of the component
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the component to the specified label.
     *
     * @param label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the x coordinate of the component.
     *
     * @return x coordinate of the component
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the component to the specified x coordinate.
     *
     * @param x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of the component.
     *
     * @return y coordinate of the component
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the component to the specified y coordinate.
     *
     * @param y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the width of the component.
     *
     * @return width of the component
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the component to the specified width.
     *
     * @param width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the component.
     *
     * @return height of the component
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the component to the specified height.
     *
     * @param height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the visibility of the component.
     *
     * @return visibility of the component
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the component to the specified visibility.
     *
     * @param visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
