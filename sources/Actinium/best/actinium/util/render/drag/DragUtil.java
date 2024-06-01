package best.actinium.util.render.drag;

import best.actinium.module.Module;

public class DragUtil {
    public static Draggable createDraggable(Module module, String name, float x, float y) {
        DragManager.draggables.put(name, new Draggable(module, name, x, y));
        return DragManager.draggables.get(name);
    }

    public static void setSize(float width,float height) {
        Draggable.setWidth(width);
        Draggable.setHeight(height);
    }
}
