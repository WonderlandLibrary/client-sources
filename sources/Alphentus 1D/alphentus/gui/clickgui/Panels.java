package alphentus.gui.clickgui;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.utils.GLUtil;
import alphentus.utils.fontrenderer.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox
 * @since on 30/07/2020.
 */
public class Panels {

    private final ModCategory category;
    private int x, y, width, height;
    private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("", 20, false, false, false);

    private int scroll;

    ArrayList<ModPanel> modPanels = new ArrayList<>();

    public Panels (ModCategory category) {
        this.category = category;

        for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
            if (mod.getModCategory().equals(category)) {
                modPanels.add(new ModPanel(mod));
            }
        }

    }


    public void drawScreen (int mouseX, int mouseY, float partialTicks) {

        if (Init.getInstance().clickGUI.currentCategory.equals(category.name())) {

            int yAdd = 0;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GLUtil.makeScissorBox(Init.getInstance().clickGUI.x, Init.getInstance().clickGUI.y + height, Init.getInstance().clickGUI.x + Init.getInstance().clickGUI.width, Init.getInstance().clickGUI.y + Init.getInstance().clickGUI.height);

            for (ModPanel modPanels : this.modPanels) {
                modPanels.setPosition(Init.getInstance().clickGUI.x, Init.getInstance().clickGUI.y + yAdd + height - scroll, width, 20);
                modPanels.drawScreen(mouseX, mouseY, partialTicks);
                yAdd += 20;
            }

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();

            if (mouseX > Init.getInstance().clickGUI.x && mouseX < Init.getInstance().clickGUI.x + 80 && mouseY > Init.getInstance().clickGUI.y && mouseY < Init.getInstance().clickGUI.y + Init.getInstance().clickGUI.height) {

                if (Init.getInstance().clickGUI.mouse < 0) {
                    if (scroll < yAdd - Init.getInstance().clickGUI.height + height && yAdd > Init.getInstance().clickGUI.height) {
                        scroll += 10;
                    }
                }

                if (Init.getInstance().clickGUI.mouse < 0) {
                    if (scroll > 0) {
                        scroll -= 20;
                    }


                }
            }
        }
        Gui.drawRect(x, y, x + width, y + height, new Color(47, 42, 47, 255).getRGB());

        String categoryName = category.name();

        fontRenderer.drawString(categoryName, x + width / 2 - fontRenderer.getStringWidth(categoryName) / 2, Init.getInstance().clickGUI.currentCategory.equals(categoryName) ? y + height / 2 - fontRenderer.getFontHeight() / 2 - 3 : y + height / 2 - fontRenderer.getFontHeight() / 2, -1, true);
        if (Init.getInstance().clickGUI.currentCategory.equals(categoryName)) {
            Gui.drawRect(x + 5, y + fontRenderer.getFontHeight() + 4, x + width - 5, y + fontRenderer.getFontHeight() + 5, new Color(255, 255, 255, 255).getRGB());
        }

    }

    public void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {

        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            Init.getInstance().clickGUI.currentCategory = category.name();
        }

        if (!Init.getInstance().clickGUI.currentCategory.equals(category.name()))
            return;

        for (ModPanel modPanels : this.modPanels) {
            if (mouseButton == 1)
                if (mouseX > Init.getInstance().clickGUI.x && mouseX < Init.getInstance().clickGUI.x + 80 && mouseY > Init.getInstance().clickGUI.y && mouseY < Init.getInstance().clickGUI.height)
                    modPanels.setExtended(false);

            modPanels.mouseClicked(mouseX, mouseY, mouseButton);
        }

    }

    public boolean isHovering (int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void mouseRelease () {
        for (ModPanel modPanels : this.modPanels) {
            modPanels.mouseRelease();
        }
    }

    public void keyTyped (char typedChar, int keyCode) {
        for (ModPanel modPanels : this.modPanels) {
            modPanels.keyTyped(typedChar, keyCode);
        }
    }

    public void setPosition (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
