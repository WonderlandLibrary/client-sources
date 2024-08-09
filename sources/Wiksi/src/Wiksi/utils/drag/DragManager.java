package src.Wiksi.utils.drag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DragManager {
    public static final Logger logger = Logger.getLogger(DragManager.class.getName());
    public static LinkedHashMap<String, Dragging> draggables = new LinkedHashMap<>();

    public static final File DRAG_DATA = new File(Minecraft.getInstance().gameDir, "\\Wiksi\\files\\drags.cfg");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static void save() {
        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(DRAG_DATA);
            writer.write(GSON.toJson(draggables.values()));
            writer.close();

            Files.writeString(DRAG_DATA.toPath(), GSON.toJson(draggables.values()));
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Not Found IOException", ex);
        }
    }

    public static void load() {
        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
            return;
        }
        Dragging[] draggings;

        try {
            draggings = GSON.fromJson(Files.readString(DRAG_DATA.toPath()), Dragging[].class);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Not Found IOException", ex);
            return;
        }
        for (Dragging dragging : draggings) {
            if (dragging == null) return;
            Dragging currentDrag = draggables.get(dragging.getName());
            if (currentDrag == null) continue;
            currentDrag.setX(dragging.getX());
            currentDrag.setY(dragging.getY());
            draggables.put(dragging.getName(), currentDrag);
        }
    }

}