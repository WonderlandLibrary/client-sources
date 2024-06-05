package alphentus.gui.clickgui.settings;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 30/07/2020.
 */
public class ComboBox {

    private final Setting setting;
    private int x, y, width, height;
    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.stem19;
    private final UnicodeFontRenderer fontRenderer2 = Init.getInstance().fontManager.myinghei22;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    private boolean extended;
    private final Translate translate;

    public ComboBox(Setting setting) {
        this.setting = setting;
        translate = new Translate(0, 0);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());


        fontRenderer.drawStringWithShadow(setting.getName(), x, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, -1);
        Gui.drawRect(x + fontRenderer.getStringWidth(setting.getName() + " "), y, x + fontRenderer.getStringWidth(setting.getName() + " ") + width, y + height, new Color(42, 42, 49, 240).getRGB());
        fontRenderer.drawStringWithShadow(setting.getSelectedCombo(), x + fontRenderer.getStringWidth(setting.getName() + " ") + 2, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, -1);

        double xMid = (double) (x + fontRenderer.getStringWidth(setting.getName() + " ") + width - fontRenderer.getStringWidth("V") - 2 + fontRenderer.getStringWidth("V") / 2);
        double yMid = (double) (this.y + height / 2 + 0.5F);


        GL11.glPushMatrix();


        if (extended) {
            translate.interpolate(0, 180, 4);
        } else {
            translate.interpolate(0, 0, 4);
        }
        GL11.glTranslated(xMid, yMid, 0.0D);
        GL11.glRotated(translate.getY(), 0, 0, 1);
        GL11.glTranslated(-xMid, -yMid, 0.0D);
        fontRenderer.drawStringWithShadow("V", x + fontRenderer.getStringWidth(setting.getName() + " ") + width - fontRenderer.getStringWidth("V") - 2, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, -1);


        GL11.glPopMatrix();

        if (!extended)
            return;


        int yPlus = height;
        for (String sld : setting.getCombos()) {
            if (!setting.getSelectedCombo().equals(sld)) {
                Gui.drawRect(x + fontRenderer.getStringWidth(setting.getName() + " "), y + yPlus, x + fontRenderer.getStringWidth(setting.getName() + " ") + width, y + height + yPlus, new Color(42, 42, 49, 240).getRGB());
                fontRenderer.drawStringWithShadow(sld, x + fontRenderer.getStringWidth(setting.getName() + " ") + 2, y + yPlus + height / 2 - fontRenderer.FONT_HEIGHT / 2, -1);
                yPlus += height;
            }
        }


    }

    public int getY() {
        return y;
    }

    public boolean isExtended() {
        return extended;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            extended = !extended;

        // SELECTING

        if (!extended)
            return;


            int yPlus = height;
            for (String sld : setting.getCombos()) {
                if (!setting.getSelectedCombo().equals(sld)) {
                    if (mouseX > x + fontRenderer.getStringWidth(setting.getName() + " ") && mouseX < x + fontRenderer.getStringWidth(setting.getName() + " ") + width && mouseY > y + yPlus && mouseY < y + yPlus + height && mouseButton == 0) {
                        setting.setSelectedCombo(sld);
                    }
                    yPlus += height;
                }
            }


    }


    public Setting getSetting() {
        return setting;
    }

    public boolean isHovering(int mouseX, int mouseY) {
            return mouseX > x && mouseX < x + fontRenderer.getStringWidth(setting.getName() + " ") + width && mouseY > y && mouseY < y + height;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
