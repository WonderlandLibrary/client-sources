package me.r.touchgrass.file.files.deprecated;

import me.r.touchgrass.file.FileManager;
import me.r.touchgrass.ui.clickgui.ClickGui;
import me.r.touchgrass.ui.clickgui.component.Frame;

/**
 * Created by r on 03/02/2021
 */
@Deprecated
public class ClickGuiFile {

    private static final FileManager clickGuiCoord = new FileManager("clickgui", "touchgrass");

    public ClickGuiFile() {
        try {
            loadClickGui();
        } catch (Exception e) {
        }
    }

    public static void saveClickGui() {
        try {
            clickGuiCoord.clear();
            for (Frame frame : ClickGui.frames) {
                clickGuiCoord.write(frame.category.name() + ":" + frame.getX() + ":" + frame.getY() + ":" + frame.isOpen());
            }
        } catch (Exception e) {
        }
    }

    public static void loadClickGui() {
        try {
            for (String s : clickGuiCoord.read()) {
                String panelName = s.split(":")[0];
                float panelCoordX = Float.parseFloat(s.split(":")[1]);
                float panelCoordY = Float.parseFloat(s.split(":")[2]);
                boolean extended = Boolean.parseBoolean(s.split(":")[3]);
                for (Frame frame : ClickGui.frames) {
                    if (frame.category.name().equalsIgnoreCase(panelName)) {
                        frame.setX((int) panelCoordX);
                        frame.setY((int) panelCoordY);
                        frame.setOpen(extended);
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
