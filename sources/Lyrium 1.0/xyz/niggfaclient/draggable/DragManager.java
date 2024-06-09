// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.draggable;

import com.google.gson.GsonBuilder;
import xyz.niggfaclient.Client;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import java.io.File;
import java.util.HashMap;

public class DragManager
{
    public static HashMap<String, Dragging> draggables;
    private static final File DRAG_DATA;
    private static final Gson GSON;
    
    public static void saveDragData() {
        if (!DragManager.DRAG_DATA.exists()) {
            DragManager.DRAG_DATA.getParentFile().mkdirs();
        }
        try {
            Files.write(DragManager.DRAG_DATA.toPath(), DragManager.GSON.toJson(DragManager.draggables.values()).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to save draggables");
        }
    }
    
    public static void loadDragData() {
        if (!DragManager.DRAG_DATA.exists()) {
            System.out.println("No dragg data found");
            return;
        }
        Dragging[] draggings;
        try {
            draggings = DragManager.GSON.fromJson(new String(Files.readAllBytes(DragManager.DRAG_DATA.toPath()), StandardCharsets.UTF_8), Dragging[].class);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to load draggables");
            return;
        }
        for (final Dragging dragging : draggings) {
            final Dragging currentDrag = DragManager.draggables.get(dragging.getName());
            currentDrag.setX(dragging.getX());
            currentDrag.setY(dragging.getY());
            DragManager.draggables.put(dragging.getName(), currentDrag);
        }
    }
    
    static {
        DragManager.draggables = new HashMap<String, Dragging>();
        DRAG_DATA = new File(Client.getInstance().folder, "draggables.json");
        GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }
}
