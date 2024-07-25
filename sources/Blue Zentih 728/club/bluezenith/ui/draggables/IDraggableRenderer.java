package club.bluezenith.ui.draggables;

import club.bluezenith.events.impl.Render2DEvent;

public interface IDraggableRenderer {
    void draw(Render2DEvent event);

    void doDragging(int mouseX, int mouseY);

    void addDraggable(Draggable draggable);

    void removeDraggable(Draggable draggable);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);
}
