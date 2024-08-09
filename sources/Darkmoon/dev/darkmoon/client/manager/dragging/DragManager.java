package dev.darkmoon.client.manager.dragging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.darkmoon.client.module.Module;
import lombok.Getter;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class DragManager {
    @Getter
    private static final HashMap<String, Draggable> draggables = new HashMap<>();
    public static final File dragablesFile = new File(System.getenv("SystemDrive") + "\\DarkMoon\\elements.dm");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public void init() throws IOException {
        if (!dragablesFile.exists()) {
            dragablesFile.createNewFile();
        } else {
            load();
        }
    }

    public void save() {
        try {
            Files.write(dragablesFile.toPath(), GSON.toJson(draggables.values()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void load() {
        try {
            Draggable[] loadedDraggables = GSON.fromJson(new String(Files.readAllBytes(dragablesFile.toPath()), StandardCharsets.UTF_8), Draggable[].class);
            for (Draggable draggable : loadedDraggables) {
                if (draggable == null) return;
                Draggable currentDrag = draggables.get(draggable.getName());
                if (currentDrag == null) continue;
                currentDrag.setX(draggable.getX());
                currentDrag.setY(draggable.getY());
                draggables.put(draggable.getName(), currentDrag);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Draggable create(Module module, String name, int x, int y) {
        Draggable draggable = new Draggable(module, name, x, y);
        draggables.put(name, draggable);
        return draggable;
    }
}
