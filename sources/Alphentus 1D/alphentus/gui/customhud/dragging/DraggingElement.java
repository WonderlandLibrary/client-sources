package alphentus.gui.customhud.dragging;

import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.init.Init;
import alphentus.mod.mods.hud.ArrayList;
import alphentus.mod.mods.hud.TabGUI;
import alphentus.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author avox | lmao
 * @since on 14.08.2020.
 */
public class DraggingElement {

    public boolean dragging;
    private ArrayList arrayList = Init.getInstance().modManager.getModuleByClass(ArrayList.class);
    private TabGUI tabGUI = Init.getInstance().modManager.getModuleByClass(TabGUI.class);
    private DraggingUtil draggingUtil = Init.getInstance().draggingUtil;
    private int x, y, width, height;
    private int dragX, dragY;
    private ScaledResolution sr;
    private ValueTab valueTab;

    public DraggingElement(ValueTab valueTab) {
        this.valueTab = valueTab;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = Gui.getScaledResolution();
        int[] values = draggingUtil.getValues(valueTab);
        x = values[0];
        y = values[1];
        width = values[2];
        height = values[3];

        RenderUtils.drawOutline(x, y, x + width, y + height, 1, 0xFF000000);

        if (draggingUtil.dragTab)
            tabGUI.updatePosition(mouseX, mouseY, dragX, dragY);
        if (draggingUtil.dragArray)
            arrayList.updatePosition(mouseX, mouseY, dragX, dragY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int[] values = draggingUtil.getValues(valueTab);
        x = values[0];
        y = values[1];
        width = values[2];
        height = values[3];
        if (mouseButton == 0 && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            dragX = x + mouseX;
            dragY = y + mouseY;
        }

        if (valueTab == ValueTab.TABGUI)
            draggingUtil.dragTab = true;
        if (valueTab == ValueTab.ARRAYLIST)
            draggingUtil.dragArray = true;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        draggingUtil.dragTab = false;
        draggingUtil.dragArray = false;
    }
}