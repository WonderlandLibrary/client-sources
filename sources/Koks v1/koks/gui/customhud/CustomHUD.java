package koks.gui.customhud;

import koks.gui.customhud.hudcomponent.ModuleList;
import koks.gui.customhud.hudcomponent.WaterMark;
import koks.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 02:53
 */
public class CustomHUD extends GuiScreen {

    public final List<Component> componentList = new ArrayList<>();

    public CustomHUD() {
        componentList.add(new WaterMark());
        componentList.add(new ModuleList());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Gui.drawRect(0, scaledResolution.getScaledHeight() / 2 - 1, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight() / 2 + 1, -1);
        Gui.drawRect(scaledResolution.getScaledWidth() / 2 - 1, 0, scaledResolution.getScaledWidth() / 2+ 1, scaledResolution.getScaledHeight(), -1);

        componentList.forEach(component -> component.drawScreen(mouseX, mouseY));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        componentList.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        componentList.forEach(Component::mouseReleased);
        super.mouseReleased(mouseX, mouseY, state);
    }

}
