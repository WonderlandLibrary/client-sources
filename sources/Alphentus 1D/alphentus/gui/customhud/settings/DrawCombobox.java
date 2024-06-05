package alphentus.gui.customhud.settings;

import alphentus.gui.customhud.settings.settings.Value;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatComponentText;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class DrawCombobox {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei21;
    private final HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);
    private final Minecraft mc = Minecraft.getMinecraft();
    private int x, y, width, height;
    private final Value value;

    public DrawCombobox(Value value, int x, int y, int width, int height) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(float mouseX, float mouseY, float partialTicks) {
        // Draws the Name of the ComboBox
        fontRenderer.drawStringWithShadow(value.getValueName(), Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(value.getValueName()) / 2, y + 3, hud.textColor.getRGB(), false);

        // Draws the Actual ComboBox
        Gui.drawRect(Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(value.getCurrentMode()) / 2 - 3, y + height + 1, Gui.getScaledResolution().getScaledWidth() / 2 + fontRenderer.getStringWidth(value.getCurrentMode()) / 2 + 4, y + height * 2 - 1, hud.guiColor1.getRGB());
        fontRenderer.drawStringWithShadow(value.getCurrentMode(), Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(value.getCurrentMode()) / 2, y + 3 + height, hud.textColor.getRGB(), false);

        // Blue line
        if (value.isExtended())
            Gui.drawRect(Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(value.getCurrentMode()) / 2 - 3, y + height * 2 - 1, Gui.getScaledResolution().getScaledWidth() / 2 + fontRenderer.getStringWidth(value.getCurrentMode()) / 2 + 4, y + height * 2, Init.getInstance().CLIENT_COLOR.getRGB());

        // Draws all Modes if extended
        if (value.isExtended()) {
            int y = this.y + height;
            for (String s : value.getModes()) {
                Gui.drawRect(Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(s) / 2 - 3, y + height, Gui.getScaledResolution().getScaledWidth() / 2 + fontRenderer.getStringWidth(s) / 2 + 4, y + height * 2, hud.guiColor1.getRGB());
                fontRenderer.drawStringWithShadow(s, Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(s) / 2, y + 3 + height, value.getCurrentMode().equals(s) ? Init.getInstance().CLIENT_COLOR.getRGB() : hud.textColor.getRGB(), false);
                y += height;
            }
        }
    }

    public void mouseClicked(float mouseX, float mouseY, float mouseButton) {
        if (mouseButton == 0 && mouseX >= Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(value.getCurrentMode()) / 2 - 3 && mouseX <= Gui.getScaledResolution().getScaledWidth() / 2 + fontRenderer.getStringWidth(value.getCurrentMode()) / 2 + 4 && mouseY >= y + height + 1 && mouseY <= y + height * 2 - 1) {
            value.toggleExtended();
        }

        if (value.isExtended()) {
            int y = this.y + height;
            for (String s : value.getModes()) {
                if (mouseButton == 0 && mouseX >= Gui.getScaledResolution().getScaledWidth() / 2 - fontRenderer.getStringWidth(s) / 2 - 3 && mouseX <= Gui.getScaledResolution().getScaledWidth() / 2 + fontRenderer.getStringWidth(s) / 2 + 4 && mouseY >= y + height && mouseY <= y + height * 2) {
                    value.setCurrentMode(s);
                }
                y += height;
            }
        }
    }

}