package alphentus.gui.customhud.dragging;

import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.init.Init;
import alphentus.mod.mods.hud.ArrayList;
import alphentus.mod.mods.hud.TabGUI;

/**
 * @author avox | lmao
 * @since on 14.08.2020.
 */
public class DraggingUtil {

    public boolean dragTab = false;
    public boolean dragArray = false;

    public int[] getValues(ValueTab valueTab) {
        TabGUI tabGUI = Init.getInstance().modManager.getModuleByClass(TabGUI.class);
        ArrayList arrayList = Init.getInstance().modManager.getModuleByClass(ArrayList.class);
        if (valueTab == ValueTab.TABGUI)
            return new int[] {tabGUI.x, tabGUI.y, tabGUI.width, tabGUI.wholeHeight};
        if (valueTab == ValueTab.ARRAYLIST)
            return new int[] {arrayList.x, arrayList.y, arrayList.width, arrayList.height};
        return new int[] {0, 0, 0, 0};
    }

}