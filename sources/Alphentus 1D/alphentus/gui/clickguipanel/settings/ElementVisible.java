package alphentus.gui.clickguipanel.settings;

import alphentus.gui.clickguipanel.panel.Element;
import alphentus.mod.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public class ElementVisible extends Element {

    private ScaledResolution sr;
    private Mod mod;

    public ElementVisible(Mod mod) {
        this.mod = mod;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        Gui.drawRect(x, y, x + width, y + height, 0xFF202020);
        fr.drawStringWithShadow("Visible: " + mod.isVisible(), x + width / 2 - fr.getStringWidth("Visible: " + mod.isVisible()) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            mod.setVisible(!mod.isVisible());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}