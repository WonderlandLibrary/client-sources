package alphentus.gui.customhud.settings;

import alphentus.gui.customhud.settings.settings.Value;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class DrawCheckbox {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei21;
    private final HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);
    private final Minecraft mc = Minecraft.getMinecraft();
    private int x, y, width, height;
    private final Value value;

    public DrawCheckbox(Value value, int x, int y, int width, int height) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(float mouseX, float mouseY, float partialTicks) {
        this.x = 100;
        Gui.drawRect(x + 2, y + 3, x + 2 + fontRenderer.FONT_HEIGHT, y + 3 + fontRenderer.FONT_HEIGHT, value.isState() ? Init.getInstance().CLIENT_COLOR.getRGB() : hud.guiColor1.getRGB());
        fontRenderer.drawStringWithShadow(value.getValueName(), x + fontRenderer.FONT_HEIGHT + 5, y + 3, hud.textColor.getRGB(), false);
    }

    public void mouseClicked(float mouseX, float mouseY, float mouseButton) {
        this.x = 100;
        if (mouseButton == 0 && mouseX >= x + 2 && mouseX <= x + 2 + fontRenderer.FONT_HEIGHT && mouseY >= y + 3 && mouseY <= y + 3 + fontRenderer.FONT_HEIGHT)
            value.setState(!value.isState());
    }

}