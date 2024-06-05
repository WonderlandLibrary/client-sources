package alphentus.gui.clickguipanel.settings;

import alphentus.gui.clickguipanel.panel.Element;
import alphentus.init.Init;
import alphentus.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;

public class ElementCombobox extends Element {

    private ScaledResolution sr;

    public ElementCombobox(Setting setting) {
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        String s = setting.getName();
        Gui.drawRect(x, y, x + width, y + height, 0xFF202020);
        fr.drawStringWithShadow(s, x + width / 2 - fr.getStringWidth(s) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);

        int y = this.y + height;
        if (setting.isExtended()) {
            for (String modes : setting.getCombos()) {
                Gui.drawRect(x, y, x + width, y + height, 0xFF101010);
                fr.drawStringWithShadow(modes, x + width / 2 - fr.getStringWidth(modes) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, setting.getSelectedCombo().equals(modes) ? Init.getInstance().CLIENT_COLOR.getRGB() : 0xFFFFFFFF);
                y += height;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            setting.setExtended(!setting.isExtended());
        }

        int y = this.y + height;
        if (setting.isExtended()) {
            for (String modes : setting.getCombos()) {
                if (mouseButton == 0 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height)
                    setting.setSelectedCombo(modes);
                y += height;
            }
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}