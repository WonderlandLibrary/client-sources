package ru.FecuritySQ.drag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class DragManager {
    public static HashMap<String, Dragging> draggables = new HashMap<>();

    public static final File DRAG_DATA = new File(Minecraft.getInstance().gameDir, "\\FecuritySQ\\drag.w");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static void saveDragData() {

        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
        }
        try {
            Files.write(DRAG_DATA.toPath(), GSON.toJson(draggables.values()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadDragData() {
        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
            return;
        }
        Dragging[] draggings;

        try {
            draggings = GSON.fromJson(new String(Files.readAllBytes(DRAG_DATA.toPath()), StandardCharsets.UTF_8), Dragging[].class);

        } catch (IOException ex) {
            ex.printStackTrace();
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