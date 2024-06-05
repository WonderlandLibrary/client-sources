package alphentus.gui.clickguipanel.settings;

import alphentus.gui.clickguipanel.panel.Element;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public class ElementCheckbox extends Element {

    private ScaledResolution sr;

    public ElementCheckbox(Setting setting) {
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        String s = setting.getName();
        Gui.drawRect(x, y, x + width, y + height, 0xFF202020);
        Gui.drawRect(x + 2, y + 5, x + height - 8, y + height - 5, setting.isState() ? Init.getInstance().CLIENT_COLOR.getRGB() : 0xFF151515);
        fr.drawStringWithShadow(s, x + 15, y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x + 2 && mouseX < x + height - 8 && mouseY > y + 5 && mouseY < y + height - 5;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            setting.setState(!setting.isState());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}