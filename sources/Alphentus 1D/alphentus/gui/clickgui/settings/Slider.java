package alphentus.gui.clickgui.settings;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 30/07/2020.
 */
public class Slider {

    private final Setting setting;
    int x, y, width, height;

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.stem19;
    private final UnicodeFontRenderer fontRenderer2 = Init.getInstance().fontManager.myinghei22;
    private final UnicodeFontRenderer fontRenderer3 = Init.getInstance().fontManager.stem15;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    private boolean dragging;

    public Slider (Setting setting) {
        this.setting = setting;
    }

    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        double current = (setting.getCurrent() - setting.getMin()) / (setting.getMax() - setting.getMin());

        float value2 = (float) (setting.isOnlyInt() ? (int) Math.round(setting.getCurrent() * 100) / 100.0 : Math.round(setting.getCurrent() * 100) / 100.0);

            fontRenderer.drawStringWithShadow(setting.getName() + ": " + value2, x, y + height / 2 - fontRenderer.FONT_HEIGHT / 2 - 3, -1);

            Gui.drawRect(x, y + height - 5, (int) (x + width), y + height, new Color(42, 40, 40, 255).getRGB());
            Gui.drawRect(x, y + height - 5, (int) (x + current * width), y + height, 0x99ffffff);
        updateValue(mouseX, mouseY);


    }

    private void updateValue (int x, int y) {
        if (dragging) {
            float oldValue = setting.getCurrent();
            float newValue = (float) (Math.round((float) Math.max(Math.min((x - this.x) / (double) getWidth() * (setting.getMax() - setting.getMin()) + setting.getMin(), setting.getMax()), setting.getMin()) * 100) / 100.0);
            setting.setCurrent(newValue);
        }

    }

    public int getWidth () {
        return width;
    }

    public void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            this.dragging = true;
    }

    public boolean isHovering (int mouseX, int mouseY) {
            return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void setPosition (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Setting getSetting () {
        return setting;
    }

    public int getY () {
        return y;
    }

    public void mouseRelease () {
        this.dragging = false;
    }


}
