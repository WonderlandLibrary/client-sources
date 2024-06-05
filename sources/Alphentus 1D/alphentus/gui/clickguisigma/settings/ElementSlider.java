package alphentus.gui.clickguisigma.settings;

import alphentus.gui.clickguisigma.ModPanels;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ElementSlider extends Element {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.stem19;
    private final UnicodeFontRenderer fontRenderer2 = Init.getInstance().fontManager.myinghei22;
    private final UnicodeFontRenderer fontRenderer3 = Init.getInstance().fontManager.stem15;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    private boolean dragging;

    public ElementSlider(ModPanels modPanel, Setting setting) {
        this.modPanel = modPanel;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        double current = (setting.getCurrent() - setting.getMin()) / (setting.getMax() - setting.getMin());

        float value2 = (float) (setting.isOnlyInt() ? (int) Math.round(setting.getCurrent() * 100) / 100.0 : Math.round(setting.getCurrent() * 100) / 100.0);

        fontRenderer2.drawStringWithShadow(setting.getName(), scaledResolution.getScaledWidth() / 2 - 140, y + height / 2 - fontRenderer2.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);

        Gui.drawRect((int) x, (int) y + (int) height / 2 - 1, (int) x + (int) width, (int) y + (int) height / 2 + 2, new Color(221, 221, 221, 255).getRGB());
        Gui.drawRect((int) x, (int) y + (int) height / 2 - 1, (int) (x + current * width), (int) y + (int) height / 2 + 2, Init.getInstance().CLIENT_COLOR.getRGB());

        if (isHovering(mouseX, mouseY)) {
            fontRenderer3.drawStringWithShadow(" " + value2, x + width / 2 - fontRenderer3.getStringWidth(" " + value2) / 2, y - 5.5F, hud.textColor.getRGB(), false);
        }

        RenderUtils.polygon((int) (x + current * width) - 5.6F, y + height / 2 - 3.5F, 9, 360, true, new Color(0, 0, 0, 70));
        RenderUtils.polygon((int) (x + current * width) - 5, y + height / 2 - 3F, 8, 360, true, new Color(245, 245, 245, 255));

        updateValue(mouseX, mouseY);

        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            this.dragging = true;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void updateValue(int x, int y) {
        if (dragging) {
            float oldValue = setting.getCurrent();
            float newValue = (float) (Math.round((float) Math.max(Math.min((x - this.x) / (double) width * (setting.getMax() - setting.getMin()) + setting.getMin(), setting.getMax()), setting.getMin()) * 100) / 100.0);
            setting.setCurrent(newValue);
        }

    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x - 10 && mouseX < x + width && mouseY > y + height / 2 - 5 && mouseY < y + height / 2 + 5;
    }


    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }
}


