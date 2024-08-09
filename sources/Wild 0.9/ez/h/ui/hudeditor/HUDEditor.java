package ez.h.ui.hudeditor;

import java.util.concurrent.*;
import ez.h.managers.*;
import java.util.*;
import java.io.*;

public class HUDEditor
{
    public static CopyOnWriteArrayList<DraggableElement> draggableRegistry;
    static File file;
    
    public static void drawAll(final float n, final float n2, final float n3) {
        if (bib.z().h == null || bib.z().f == null) {
            return;
        }
        HUDEditor.draggableRegistry.forEach(draggableElement -> draggableElement.render(n, n2, n3, true));
    }
    
    static {
        HUDEditor.file = new File(ConfigManager.path.getAbsolutePath() + "\\hudeditor.w");
        HUDEditor.draggableRegistry = new CopyOnWriteArrayList<DraggableElement>();
    }
    
    public static void loadFile() {
        try {
            if (!HUDEditor.file.exists()) {
                HUDEditor.file.createNewFile();
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(HUDEditor.file))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                final String s = trim.split(":")[1];
                final String s2 = trim.split(":")[2];
                for (final DraggableElement draggableElement : HUDEditor.draggableRegistry) {
                    if (draggableElement.name.equals(trim.split(":")[0])) {
                        draggableElement.x = Float.parseFloat(s);
                        draggableElement.y = Float.parseFloat(s2);
                    }
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void saveFile() {
        try {
            if (!HUDEditor.file.exists()) {
                HUDEditor.file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(HUDEditor.file));
            for (final DraggableElement draggableElement : HUDEditor.draggableRegistry) {
                bufferedWriter.write(draggableElement.name + ":" + draggableElement.x + ":" + draggableElement.y);
                bufferedWriter.write("\r\n");
            }
            bufferedWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
