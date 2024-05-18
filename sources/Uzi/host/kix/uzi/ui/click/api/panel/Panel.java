package host.kix.uzi.ui.click.api.panel;

import host.kix.uzi.ui.click.impl.GuiClick;
import host.kix.uzi.ui.click.api.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marc
 * @since 7/20/2016
 */
public abstract class Panel {

	/**
	 * An instance of the parent class.
	 */
	private final GuiClick parent;

	/**
	 * The display name (or label) of the panel.
	 */
	private String label;

	/**
	 * The x and y coordinates of the panel.
	 */
	private int x, y;

	/**
	 * The width and the height of the panel.
	 */
	private int width, height;

	/**
	 * The previous x and previous y positions of the panel, i.e., where it
	 * started being dragged.
	 */
	private int fromX, fromY;

	/**
	 * Whether the panel is open.
	 */
	private boolean open;

	/**
	 * Whether the panel is being dragged.
	 */
	private boolean dragging;

	/**
	 * List of components of which the panel contains.
	 */
	private List<Component> components;

	public Panel(GuiClick parent, String label, int x, int y, int width, int height) {
		this.parent = parent;
		this.label = label;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.components = new ArrayList<>();

		setup();
	}

	/**
	 * Checks if the panel will have components
	 */
	
	public boolean hasComponents() {
		return !components.isEmpty();
	}
	
	/**
	 * Called when the panel is instantiated.
	 */
	protected abstract void setup();

	/**
	 * When called, this method will draw the panel and manage dragging.
	 *
	 * @param x
	 *            the x coordinate of the mouse
	 * @param y
	 *            the y coordinate of the mouse
	 */
	public void drawPanel(int x, int y) {
		if (dragging) {
			this.x = x + this.fromX;
			this.y = y + this.fromY;
		}

		parent.getTheme().drawPanel(this, x, y);
	}

	/**
	 * When called, this method will handle mouse clicking and manage dragging.
	 *
	 * @param x
	 *            the x coordinate of the mouse
	 * @param y
	 *            the y coordinate of the mouse
	 * @param button
	 *            the button id of the mouse input
	 */
	public void mouseClicked(int x, int y, int button) {
		if (isHovering(x, y)) {
			if (button == 0) {
				this.fromX = (this.x - x);
				this.fromY = (this.y - y);

				this.dragging = true;

				for (Panel panel : this.parent.getPanels()) {
					if (panel == this)
						continue;

					panel.dragging = false;
				}
			}

			this.parent.unregisterPanel(this);
			this.parent.registerPanel(this);
		}

		parent.getTheme().mouseClicked(this, x, y, button);
	}

	/**
	 * When called, this method will handle mouse releases.
	 *
	 * @param x
	 *            the x coordinate of the mouse
	 * @param y
	 *            the y coordinate of the mouse
	 * @param button
	 *            the button id of the mouse input
	 */
	public void mouseReleased(int x, int y, int button) {
		this.parent.getTheme().mouseReleased(this, x, y, button);
	}

	/**
	 * When called, this method will handle key input.
	 *
	 * @param typedChar
	 *            the typed character
	 * @param keyCode
	 *            the key code of the pressed key
	 */
	public void keyTyped(char typedChar, int keyCode) {
		this.parent.getTheme().keyTyped(this, typedChar, keyCode);
	}

	/**
	 * Determines whether the user is hovering over the defined area.
	 *
	 * @param x
	 *            the x coordinate of the mouse
	 * @param y
	 *            the y coordinate of the mouse
	 * @return whether the user is hovering over the defined area
	 */
	public boolean isHovering(int x, int y) {
		return x > this.x && y > this.y && x < this.x + width && y < this.y + height;
	}

	/**
	 * Returns the label of the panel.
	 *
	 * @return label of the panel
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns the x coordinate of the panel.
	 *
	 * @return x coordinate of the panel
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the panel to the specified x coordinate.
	 *
	 * @param x
	 *            to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of the panel.
	 *
	 * @return y coordinate of the panel
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the panel to the specified y coordinate.
	 *
	 * @param y
	 *            to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the width of the panel.
	 *
	 * @return width of the panel
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the panel to the specified width.
	 *
	 * @param width
	 *            to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the height of the panel.
	 *
	 * @return height of the panel
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the panel to the specified height.
	 *
	 * @param height
	 *            to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns whether the panel is open.
	 *
	 * @return whether the panel is open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Sets the open state to the provided parameter.
	 *
	 * @param open
	 *            state to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * Returns whether the panel is dragging.
	 *
	 * @return whether the panel is dragging
	 */
	public boolean isDragging() {
		return dragging;
	}

	/**
	 * Sets the dragging state of the panel to the specified state.
	 *
	 * @param dragging
	 *            to set
	 */
	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	/**
	 * Returns a list of components in the panel.
	 *
	 * @return components in the panel
	 */
	public List<Component> getComponents() {
		return components;
	}

}
