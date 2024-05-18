package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class TabGui extends Module {
    public TabGui() {
        super("TabGUI", Category.RENDER);
        setDrawn(false);
    }

    Category[] categories = Category.values();
    int y;
    int modY;
    public static int selected = 0;
    public static boolean extended = false;
    public static int selectedMod = 0;
    public static Module currentMod = null;
    public void onRender(){
        if(selected < 0) selected = 0;
        if(selected > categories.length - 1) selected = categories.length - 1;
        y = 2;
        modY = 2;
        currentMod = null;
        for(Category c : categories){
            if(categories[selected].equals(c)) Gui.drawRect(2, y, 62, y + 12, Rainbow.getIntWithOpacity(100));
            else Gui.drawRect(2, y, 62, y + 12, 0x66111111);
            FontUtils.drawStringWithShadow(false, c.name(), 4, y + 2, 0xffffffff);
            y += 12;
        }
        if(extended){
            ArrayList<Module> mods = ModuleManager.getModulesInCategory(categories[selected]);
            if(selectedMod < 0) selectedMod = 0;
            if(selectedMod > mods.size() - 1) selectedMod = mods.size() - 1;
            int width = 60;
            for(Module m : mods){
                int newWidth = FontUtils.getStringWidth(false, m.getName()) + 2;
                if(newWidth > width) width = newWidth;
            }
            for(Module m : mods){
                int color = mods.get(selectedMod).equals(m) ? Rainbow.getIntWithOpacity(100) : m.isEnabled() ? 0x66116611 : 0x66111111;
                int yy = modY;
                if(mods.get(selectedMod).equals(m)) Gui.drawRect(64, yy, 64 + width, yy + 12, color);
                else Gui.drawRect(64, yy,  64 + width, yy + 12, color);
                FontUtils.drawStringWithShadow(false, m.getName(), 66, yy + 2, 0xffffffff);
                if(mods.get(selectedMod).equals(m)) currentMod = m;
                modY += 12;
            }
        }
    }
}
