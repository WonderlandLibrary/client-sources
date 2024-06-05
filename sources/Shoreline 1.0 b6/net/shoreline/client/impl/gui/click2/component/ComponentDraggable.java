package net.shoreline.client.impl.gui.click2.component;

import net.minecraft.util.math.Vec2f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

/**
 * @author xgraza
 * @since 03/30/24
 */
public abstract class ComponentDraggable extends AbstractComponent {

    private boolean dragState;
    private float draggedX, draggedY;

    /**
     * Updates the drag position if the component is being dragged
     * @param mouse the mouse coordinate vector
     */
    protected void updatePosition(final Vec2f mouse) {
        if (dragState) {
            setX(draggedX + mouse.x);
            setY(draggedY + mouse.y);
        }
    }

    @Override
    public void mouseClicked(Vec2f mouse, int button) {
        if (button == GLFW_MOUSE_BUTTON_1 && isMouseOver(mouse)) {
            dragState = true;
            draggedX = getX() - mouse.x;
            draggedY = getY() - mouse.y;
        }
    }

    @Override
    public void mouseReleased(Vec2f mouse, int button) {
        if (button == GLFW_MOUSE_BUTTON_1) {
            dragState = false;
        }
    }
}
