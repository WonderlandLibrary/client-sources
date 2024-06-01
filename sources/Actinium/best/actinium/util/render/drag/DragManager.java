package best.actinium.util.render.drag;

import best.actinium.Actinium;
import best.actinium.module.Module;
import best.actinium.util.IAccess;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class DragManager {
    public static HashMap<String, Draggable> draggables = new HashMap<>();
    private static final File DRAG_DATA = new File(new File(IAccess.mc.mcDataDir, Actinium.NAME), "Draggables.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setLenient().create();

    public static void saveDragData() {
        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
        }
        try {
            Files.write(DRAG_DATA.toPath(), GSON.toJson(draggables.values()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to save file");
        }
    }

    public static void loadDragData() {
        if (!DRAG_DATA.exists()) {
            System.out.println("No data found");
            return;
        }

        Draggable[] draggables;
        try {
            draggables = GSON.fromJson(new String(Files.readAllBytes(DRAG_DATA.toPath()), StandardCharsets.UTF_8), Draggable[].class);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to load");
            return;
        }

        for(Draggable draggable : draggables) {
            if(!DragManager.draggables.containsKey(draggable.getName())) continue;
            Draggable currentDrag = DragManager.draggables.get(draggable.getName());
            currentDrag.setXPos(draggable.getXPos());
            currentDrag.setYPos(draggable.getYPos());
            DragManager.draggables.put(draggable.getName(), currentDrag);
        }
    }
}
