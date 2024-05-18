package host.kix.uzi.ui.tab;

import host.kix.uzi.Uzi;
import host.kix.uzi.module.Module;
import host.kix.uzi.module.addons.theme.themes.*;
import host.kix.uzi.module.modules.render.Overlay;
import host.kix.uzi.utilities.minecraft.NahrFont;
import host.kix.uzi.utilities.minecraft.RenderingMethods;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by myche on 2/5/2017.
 */
public class TabGui {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static int color = 0x903BB3DB;
    public static Value<Integer> length = new Value<Integer>("Length", 25, 0, 400);
    public static Value<Integer> spacing = new Value<Integer>("Spacing", 2, 0, 100);
    private static int selected, moduleSelected, valueSelected;
    private static boolean isOpen;
    private static boolean isValueOpen;
    private static float extended;
    private static int bg = new Color(0, 0, 0, 150).getRGB();

    public static void drawTabGui() {

        for (int i = 0; i < Module.Category.values().length; i++) {
            Module.Category cat = Module.Category.values()[i];
            int y;
            if (Overlay.watermark.getValue()) {
                y = 12;
            } else {
                y = 2;
            }
            if (Overlay.getThemeHandler().getTheme() instanceof StaminaTheme) {
                y = 18;
                color = new Color(0x717171).getRGB();
                bg = new Color(0, 0, 0, 150).getRGB();
                length.setValue(25);
            } else if (Overlay.getThemeHandler().getTheme() instanceof DirektTheme) {
                color = new Color(0x86FF51).getRGB();
                bg = new Color(0, 0, 0, 180).getRGB();
                length.setValue(50);
            } else if (Overlay.getThemeHandler().getTheme() instanceof UziTheme) {
                length.setValue(25);
                bg = new Color(0, 0, 0, 150).getRGB();
                color = 0x903BB3DB;
            } else if (Overlay.getThemeHandler().getTheme() instanceof TropicalTheme) {
                length.setValue(25);
                bg = new Color(0, 0, 0, 150).getRGB();
                color = new Color(255, 128, 0).getRGB();
            } else if (Overlay.getThemeHandler().getTheme() instanceof ExeterTheme) {
                length.setValue(25);
                bg = new Color(0, 0, 0, 125).getRGB();
                color = 0xFFC4443E;
            }
            if (Overlay.getThemeHandler().getTheme() instanceof ExeterTheme) {
                if (selected == i) {
                    RenderingMethods.drawBorderedRectReliant(3, y + i * 12, 3 + length.getValue() + mc.fontRendererObj.getStringWidth("Movement"), y + 12 + i * 12, 1.5F, color, 0xFF000000);
                } else {
                    Gui.drawRect(3, y + i * 12, 3 + length.getValue() + mc.fontRendererObj.getStringWidth("Movement"), y + 12 + i * 12, bg);
                }
            } else {
                Gui.drawRect(3, y + i * 12, 3 + length.getValue() + mc.fontRendererObj.getStringWidth("Movement"), y + 12 + i * 12, selected == i ? color : bg);
            }
            String name = cat.name().substring(0, 1) + cat.name().substring(1).toLowerCase();
            mc.fontRendererObj.drawStringWithShadow(Overlay.edge.getValue() ? name.toLowerCase() : name, 3 + spacing.getValue(), y + i * 12 + 2, 0xFFFFFF);
            extended += 0.01F * (isOpen ? 1F : -1F);
            extended = Math.max(0, extended);
            extended = Math.min(1, extended);

            if ((isOpen || extended > 0) && i == selected) for (int j = 0; j < getModsForCategory(cat).size(); j++) {
                Module mod = getModsForCategory(cat).get(j);
                if (Overlay.getThemeHandler().getTheme() instanceof ExeterTheme) {
                    if (moduleSelected == j) {
                        RenderingMethods.drawBorderedRectReliant(3 + mc.fontRendererObj.getStringWidth("Movement") + 2 + length.getValue(), y + i * 12 + j * 12, 3 + mc.fontRendererObj.getStringWidth("Movement") + 2 + length.getValue() + mc.fontRendererObj.getStringWidth("SpeedyGonzales") + 2, y + 12 + i * 12 + j * 12, 1.5F, color, 0xFF000000);
                    } else {
                        Gui.drawRect(3 + mc.fontRendererObj.getStringWidth("Movement") + 2 + length.getValue(), y + i * 12 + j * 12, 3 + mc.fontRendererObj.getStringWidth("Movement") + 2 + length.getValue() + mc.fontRendererObj.getStringWidth("SpeedyGonzales") + 2, y + 12 + i * 12 + j * 12, bg);
                    }
                    mc.fontRendererObj.drawStringWithShadow(Overlay.edge.getValue() ? mod.getName().toLowerCase() : mod.getName(), spacing.getValue() + mc.fontRendererObj.getStringWidth("Movement") + 3 + 1 + length.getValue() + 2, y + i * 12 + j * 12 + 2, mod.isEnabled() ? new Color(0xFF3131).getRGB() : new Color(195, 195, 195, 255).getRGB());
                } else {
                    Gui.drawRect(3 + mc.fontRendererObj.getStringWidth("Movement") + 2 + length.getValue(), y + i * 12 + j * 12, 3 + mc.fontRendererObj.getStringWidth("Movement") + 2 + length.getValue() + mc.fontRendererObj.getStringWidth("SpeedyGonzales") + 2, y + 12 + i * 12 + j * 12, moduleSelected == j ? color : bg);
                    mc.fontRendererObj.drawStringWithShadow(Overlay.edge.getValue() ? mod.getName().toLowerCase() : mod.getName(), spacing.getValue() + mc.fontRendererObj.getStringWidth("Movement") + 3 + 1 + length.getValue(), y + i * 12 + j * 12 + 2, mod.isEnabled() ? new Color(0xFFFFFF).getRGB() : new Color(195, 195, 195, 255).getRGB());
                }
            }
        }
    }

    public static void onKey(int key) {
        if (key == Keyboard.KEY_UP) {
            if (!isOpen) {
                selected--;
                if (selected == -1) {
                    selected = 5;
                }
            } else {
                moduleSelected--;
                if (moduleSelected <= 0) {
                    moduleSelected = 0;
                }
            }
        }
        if (key == Keyboard.KEY_DOWN) {
            if (!isOpen) {
                selected++;
                if (selected == 6) {
                    selected = 0;
                }
            } else {
                moduleSelected++;
                if (moduleSelected >= getModsForCategory(Module.Category.values()[selected]).size() - 1) {
                    moduleSelected = getModsForCategory(Module.Category.values()[selected]).size() - 1;
                }
            }
        }
        if (key == Keyboard.KEY_RIGHT) {
            isOpen = true;
        }
        if (key == Keyboard.KEY_LEFT) {
            isOpen = false;
            moduleSelected = 0;
        }
        if (key == Keyboard.KEY_RETURN) {
            if (isOpen) {
                getModsForCategory(Module.Category.values()[selected]).get(moduleSelected).toggle();
            }
        }
    }

    private static List<Module> getModsForCategory(Module.Category category) {
        List<Module> mods = new ArrayList<>();
        for (Module mod : Uzi.getInstance().getModuleManager().getContents()) {
            if (mod.getCategory() == category) {
                mods.add(mod);
            }
        }
        return mods;
    }

}
