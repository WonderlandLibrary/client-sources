package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.KeypressEvent;
import host.kix.uzi.events.RenderGameOverlayEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.addons.theme.HudGui;
import host.kix.uzi.module.modules.movement.Flight;
import host.kix.uzi.utilities.minecraft.NahrFont;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.module.Module;
import host.kix.uzi.ui.tab.TabGui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import com.darkmagician6.eventapi.SubscribeEvent;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Kix on 2/3/2017.
 */
public class Overlay extends Module {
    public static Value<Boolean> arrayList = new Value<>("ArrayList", true);
    public static Value<Boolean> watermark = new Value<>("Watermark", true);
    public static Value<Boolean> coordinates = new Value<>("Coordinates", true);
    public static Value<Boolean> potionStatus = new Value<Boolean>("Potions", true);
    public static Value<Boolean> edge = new Value<>("Lowercase", false);
    public static Value<Boolean> tabbedGui = new Value<>("TabGui", true);
    private static HudGui themeHandler;

    public Overlay() {
        super("Overlay", 0, Category.RENDER);
        setHidden(true);
        add(arrayList);
        add(coordinates);
        add(potionStatus);
        add(watermark);
        add(edge);
        add(tabbedGui);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("overlay") {
            @Override
            public void loadFile() {
                try {
                    final BufferedReader reader = new BufferedReader(new FileReader(getFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String[] arguments = line.split(":");
                        if (arguments.length == 2) {
                            final Value value = findGivenValue(arguments[0]);
                            if (value != null) {
                                if (value.getValue() instanceof Boolean) {
                                    value.setValue(Boolean.parseBoolean(arguments[1]));
                                } else if (value.getValue() instanceof Integer) {
                                    value.setValue(Integer.parseInt(arguments[1]));
                                } else if (value.getValue() instanceof Double) {
                                    value.setValue(Double.parseDouble(arguments[1]));
                                } else if (value.getValue() instanceof Float) {
                                    value.setValue(Float.parseFloat(arguments[1]));
                                } else if (value.getValue() instanceof Long) {
                                    value.setValue(Long.parseLong(arguments[1]));
                                } else if (value.getValue() instanceof String) {
                                    value.setValue(arguments[1]);
                                }
                            }
                        }
                    }
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void saveFile() {
                try {
                    final BufferedWriter writer = new BufferedWriter(new FileWriter(
                            getFile()));
                    for (final Value val : getValues()) {
                        writer.write(val.getName().toLowerCase() + ":"
                                + val.getValue());
                        writer.newLine();
                    }
                    writer.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        if (themeHandler == null) {
            themeHandler = new HudGui();
        }
        themeHandler.render();
    }

    @SubscribeEvent
    public void onKeyPress(KeypressEvent e) {
        if (mc.currentScreen == null && Overlay.tabbedGui.getValue())
            TabGui.onKey(e.getKey());
    }

    public static HudGui getThemeHandler() {
        if (themeHandler != null) {
            return themeHandler;
        } else {
            return null;
        }
    }
}
