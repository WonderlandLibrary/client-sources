package alphentus.gui.clickguisigma.settings;

import alphentus.gui.clickguisigma.ModPanels;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ElementComboBox extends Element {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.stem19;
    private final UnicodeFontRenderer fontRenderer2 = Init.getInstance().fontManager.myinghei22;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    private boolean extended;
    private final Translate translate;

    public ElementComboBox(ModPanels modPanel, Setting setting) {
        this.modPanel = modPanel;
        this.setting = setting;
        translate = new Translate(0, 0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        fontRenderer2.drawStringWithShadow(setting.getName(), scaledResolution.getScaledWidth() / 2 - 140, y + height / 2 - fontRenderer2.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);
        Gui.drawRect((int) x - (int) width - 1, (int) y, (int) x, (int) y + (int) height, extended ? hud.guiColor2.getRGB() : new Color(0, 0, 0, 0).getRed());
        RenderUtils.drawOutline(x - width - 1, y, x, y + height, 1, extended ? hud.opposite.getRGB() : new Color(0, 0, 0, 0).getRed());
        fontRenderer.drawStringWithShadow(setting.getSelectedCombo(), x - fontRenderer.getStringWidth(setting.getSelectedCombo()) - fontRenderer.getStringWidth(">") - 4F, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);

        double xMid2 = (double) (x - fontRenderer.getStringWidth(">") / 2 - 0.75F);
        double yMid2 = (double) (this.y + height / 2 + 0.75F);

        if (comboextended) {
            translate.interpolate(0, 90, 4);
        } else {
            translate.interpolate(0, 0, 4);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(xMid2, yMid2, 0.0D);
        GL11.glRotated(translate.getY(), 0, 0, 1);
        GL11.glTranslated(-xMid2, -yMid2, 0.0D);
        fontRenderer.drawStringWithShadow(">", x - fontRenderer.getStringWidth(">") - 0.5F, y + height / 2 - fontRenderer.FONT_HEIGHT / 2 - 0.5F, hud.textColor.getRGB(), false);
        GL11.glPopMatrix();

        if (!comboextended)
            return;

        int yPlus = (int) height;
        for (String sld : setting.getCombos()) {
            if (!setting.getSelectedCombo().equals(sld)) {
                Gui.drawRect((int) x - (int) width - 1, (int) y + yPlus, (int) x, (int) y + (int) height + yPlus, hud.guiColor2.getRGB());
                fontRenderer.drawStringWithShadow(sld, x - fontRenderer.getStringWidth(sld) - 4, y + yPlus + height / 2 - fontRenderer.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);
                yPlus += height;
            }
        }
        RenderUtils.drawOutline(x - width - 1, y, x, y + yPlus, 1, hud.opposite.getRGB());

        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            comboextended = !comboextended;
        if (!comboextended)
            return;
        int yPlus = (int) height;
        for (String sld : setting.getCombos()) {
            if (!setting.getSelectedCombo().equals(sld)) {
                if (mouseX > x - width - 1 && mouseX < x && mouseY > y + yPlus && mouseY < y + yPlus + height && mouseButton == 0) {
                    setting.setSelectedCombo(sld);
                }
                yPlus += height;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x - width - 1F && mouseX < x && mouseY > y && mouseY < y + height;
    }

}
