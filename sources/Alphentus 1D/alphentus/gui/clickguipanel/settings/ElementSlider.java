package alphentus.gui.clickguipanel.settings;

import alphentus.gui.clickguipanel.panel.Element;
import alphentus.init.Init;
import alphentus.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public class ElementSlider extends Element {

    private ScaledResolution sr;
    private boolean dragging;

    public ElementSlider(Setting setting) {
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        double currentValue = (setting.getCurrent() - setting.getMin()) / (setting.getMax() - setting.getMin());

        String s = setting.getName();
        // Slider Background
        Gui.drawRect(x, y, x + width, y + height, 0xFF202020);
        // Slider
        Gui.drawRect(x, y + height - 3, x + width, y + height - 1, 0xFF151515);
        Gui.drawRect(x, y + height - 3, (int) (x + currentValue * width), y + height - 1, Init.getInstance().CLIENT_COLOR.getRGB());
        // Slider Text
        fr.drawStringWithShadow(s, x + 3, y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);
        fr.drawStringWithShadow(setting.getCurrent() + "", x + width - 3 - fr.getStringWidth(setting.getCurrent() + ""), y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);

        updateValues(mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updateValues(int mouseX, int mouseY) {
        if (dragging) {
            float min = setting.getMin();
            float max = setting.getMax();
            float newValue = (float) (Math.round((float) Math.max(Math.min((mouseX - x) / (double) width * (max - min) + min, max), min) * 100) / 100.0);
            setting.setCurrent(newValue);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY >= y + height - 3 && mouseY <= y + height - 1;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }

}