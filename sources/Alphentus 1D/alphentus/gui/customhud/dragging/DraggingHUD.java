package alphentus.gui.customhud.dragging;

import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.init.Init;
import alphentus.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 14.08.2020.
 */
public class DraggingHUD extends GuiScreen {

    private DraggingUtil draggingUtil = Init.getInstance().draggingUtil;
    private ArrayList<ValueTab> valueTabs = new ArrayList<>();
    private ScaledResolution sr;

    public DraggingHUD() {
        for (ValueTab valueTab : ValueTab.values()) {
            valueTabs.add(valueTab);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = Gui.getScaledResolution();

        for (ValueTab valueTab : valueTabs) {
            DraggingElement draggingElement = new DraggingElement(valueTab);
            draggingElement.drawScreen(mouseX, mouseY, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ValueTab valueTab : valueTabs) {
            DraggingElement draggingElement = new DraggingElement(valueTab);
            draggingElement.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (ValueTab valueTab : valueTabs) {
            DraggingElement draggingElement = new DraggingElement(valueTab);
            draggingElement.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
}