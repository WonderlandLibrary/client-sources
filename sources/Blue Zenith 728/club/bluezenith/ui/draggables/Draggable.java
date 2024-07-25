package club.bluezenith.ui.draggables;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.ui.clickgui.ClickGui;

public interface Draggable {
    float getX();

    float getY();

    void moveTo(float x, float y);

    /* Clickable */
    boolean shouldBeRendered();

    boolean isMouseOver(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void draw(Render2DEvent event);

    default boolean checkMouseBounds(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        return ClickGui.i(mouseX, mouseY, x, y, x2, y2);
    }

    default String getIdentifier() {
        return null;
    }

    default void resetPosition() {

    }
}
