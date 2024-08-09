package fun.ellant.utils.drag;

import java.util.LinkedHashMap;
import java.util.logging.Logger;



public class DragManager {

    public static final Logger logger = Logger.getLogger(DragManager.class.getName());
    public static LinkedHashMap<String, Dragging> draggables = new LinkedHashMap<>();

    public static void load() {
    }
}
