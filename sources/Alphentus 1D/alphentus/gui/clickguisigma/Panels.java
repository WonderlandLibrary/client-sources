package alphentus.gui.clickguisigma;

import alphentus.gui.clickgui.ModPanel;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class Panels {

    public ModCategory modCategory;
    public int x, y, width, height, dragX, dragY;
    public boolean drag;
    public HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei25;

    public ArrayList<ModPanels> modPanels = new ArrayList<>();

    public Panels(ModCategory modCategory, int x, int y) {
        this.modCategory = modCategory;
        this.x = x;
        this.y = y;
        this.width = 90;
        this.height = 30;

        for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
            if (mod.getModCategory().equals(modCategory)) {
                modPanels.add(new ModPanels(mod));
            }
        }

    }

    public void drawScreen(int mouseX, int mouseY) {
        RenderUtils.relativeRect(x, y, x + width, y + height, hud.panelColor.getRGB());
        fontRenderer.drawStringWithShadow(modCategory.name(), x + width / 2 - fontRenderer.getStringWidth(modCategory.name()) / 2, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);

        int yModPanels = 0;
        for (ModPanels modPanels : this.modPanels) {
            modPanels.setPosition(x, y + height + yModPanels, width, 15);
            modPanels.drawScreen(mouseX, mouseY);
            yModPanels += 15;
        }

        if (drag) {
            this.x = dragX + mouseX;
            this.y = dragY + mouseY;
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

        drag = false;

        for (ModPanels modPanels : this.modPanels) {
            modPanels.mouseReleased(mouseX, mouseY, state);
        }

    }


    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ModPanels modPanels : this.modPanels) {
            modPanels.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (Init.getInstance().clickGUISigma.usingSetting)
            return;

        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.dragX = x - mouseX;
            this.dragY = y - mouseY;
            drag = true;
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

        for (ModPanels modPanels : this.modPanels) {
            modPanels.keyTyped(typedChar, keyCode);
        }

    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

}
