/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.drag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mpp.venusfr.utils.drag.Dragging;
import net.minecraft.client.Minecraft;

public class DragManager {
    public static final Logger logger = Logger.getLogger(DragManager.class.getName());
    public static LinkedHashMap<String, Dragging> draggables = new LinkedHashMap();
    public static final File DRAG_DATA = new File(Minecraft.getInstance().gameDir, "\\venusfr\\files\\drags.cfg");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static void save() {
        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(DRAG_DATA);
            fileWriter.write(GSON.toJson(draggables.values()));
            fileWriter.close();
            Files.writeString((Path)DRAG_DATA.toPath(), (CharSequence)GSON.toJson(draggables.values()), (OpenOption[])new OpenOption[0]);
        } catch (IOException iOException) {
            logger.log(Level.WARNING, "Not Found IOException", iOException);
        }
    }

    public static void load() {
        Dragging[] draggingArray;
        if (!DRAG_DATA.exists()) {
            DRAG_DATA.getParentFile().mkdirs();
            return;
        }
        try {
            draggingArray = GSON.fromJson(Files.readString((Path)DRAG_DATA.toPath()), Dragging[].class);
        } catch (IOException iOException) {
            logger.log(Level.WARNING, "Not Found IOException", iOException);
            return;
        }
        for (Dragging dragging : draggingArray) {
            if (dragging == null) {
                return;
            }
            Dragging dragging2 = draggables.get(dragging.getName());
            if (dragging2 == null) continue;
            dragging2.setX(dragging.getX());
            dragging2.setY(dragging.getY());
            draggables.put(dragging.getName(), dragging2);
        }
    }
}

