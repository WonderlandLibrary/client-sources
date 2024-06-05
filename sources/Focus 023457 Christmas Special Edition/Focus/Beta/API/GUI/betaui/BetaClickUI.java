package Focus.Beta.API.GUI.betaui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class BetaClickUI extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 20, 51, 180).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
