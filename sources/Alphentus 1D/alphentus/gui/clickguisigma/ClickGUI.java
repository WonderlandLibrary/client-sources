package alphentus.gui.clickguisigma;

import alphentus.config.DrawConfigManager;
import alphentus.gui.clickgui.settings.KeyBindBox;
import alphentus.gui.clickguisigma.settings.Element;
import alphentus.gui.clickguisigma.settings.VisibleButton;
import alphentus.gui.customhud.CustomHUD;
import alphentus.init.Init;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.GLUtil;
import alphentus.utils.RenderUtils;
import alphentus.utils.Scrollbar;
import alphentus.utils.Translate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class ClickGUI extends GuiScreen {

    public ArrayList<Panels> panels = new ArrayList<>();

    int startX = 40;
    int startY = 20;
    public HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    public boolean exist = false;

    Translate translate;
    Translate translateSetting;

    private boolean usingBlur = false;

    Scrollbar scrollbar = new Scrollbar();

    public boolean usingSetting = false;

    public ClickGUI() {
        for (ModCategory modCategory : ModCategory.values()) {
            panels.add(new Panels(modCategory, startX, startY));
            this.startX += 95;
        }
        this.translate = new Translate(0, 0);
        this.translateSetting = new Translate(0, 0);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (exist) {

        } else {
            if (!usingSetting)
                translate.interpolate(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 6);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(scaledResolution.getScaledWidth() / 2, -1000, 0);
        GL11.glScaled(translate.getX() / scaledResolution.getScaledWidth(), translate.getY() / scaledResolution.getScaledHeight(), 0);
        GL11.glTranslatef(-scaledResolution.getScaledWidth() / 2, 1000, 0);


        // Opens Configmenu
        RenderUtils.drawRoundedRect(scaledResolution.getScaledWidth() - 50, scaledResolution.getScaledHeight() - 30, 40, 20, 10, hud.guiColor1);
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 40, scaledResolution.getScaledHeight() - 15 - 2, 5, hud.guiColor3);
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 27, scaledResolution.getScaledHeight() - 15 - 2, 5, hud.guiColor3);
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 14, scaledResolution.getScaledHeight() - 15 - 2, 5, hud.guiColor3);

        // Opens CustomHUD
        RenderUtils.drawRoundedRect(scaledResolution.getScaledWidth() - 50, scaledResolution.getScaledHeight() - 70, 40, 20, 10, hud.guiColor1);
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 40, scaledResolution.getScaledHeight() - 55 - 2, 5, hud.guiColor3);
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 27, scaledResolution.getScaledHeight() - 55 - 2, 5, hud.guiColor3);
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 14, scaledResolution.getScaledHeight() - 55 - 2, 5, hud.guiColor3);


        for (Panels panels : this.panels) {
            panels.drawScreen(mouseX, mouseY);
        }

        GL11.glPopMatrix();
        if (usingSetting) {
            for (Panels panels : this.panels) {
                for (ModPanels modPanels : panels.modPanels) {
                    if (modPanels.usingSettings) {
                        RenderUtils.relativeRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(0, 0, 0, 125).getRGB());
                        Init.getInstance().blurUtil.blurWholeScreen(3);


                        translate.interpolate(scaledResolution.getScaledWidth() - 7, scaledResolution.getScaledHeight() - 7, 6);
                        translateSetting.interpolate(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 8);

                        GL11.glPushMatrix();
                        GL11.glTranslatef(scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 2, 0);
                        GL11.glScaled(translateSetting.getX() / scaledResolution.getScaledWidth(), translateSetting.getY() / scaledResolution.getScaledHeight(), 0);
                        GL11.glTranslatef(-scaledResolution.getScaledWidth() / 2, -scaledResolution.getScaledHeight() / 2, 0);

                        RenderUtils.drawRoundedRect(scaledResolution.getScaledWidth() / 2 - 150, 50, 150 * 2, scaledResolution.getScaledHeight() - 100, 10, hud.guiColor4);
                        Init.getInstance().fontManager.myinghei35.drawStringWithShadow(modPanels.mod.getModuleName(), scaledResolution.getScaledWidth() / 2 - 140, 60, hud.textColor.getRGB(), false);

                        GL11.glPushMatrix();
                        GL11.glEnable(GL11.GL_SCISSOR_TEST);
                        GLUtil.makeScissorBox(scaledResolution.getScaledWidth() / 2 - 150, 80, scaledResolution.getScaledWidth() / 2 + 155, scaledResolution.getScaledHeight() - 55);

                        if (modPanels.yplus + 25 > scaledResolution.getScaledHeight() - 150) {
                            scrollbar.setInformation(scaledResolution.getScaledWidth() / 2 - 147, 80, (scaledResolution.getScaledHeight() - 55) - 80, modPanels.scroll, modPanels.yplus - scaledResolution.getScaledHeight() / 2 - 113, 0);
                            scrollbar.drawScrollBar(mouseX, mouseY);
                        }

                        for (KeyBindBox keyBindBox : modPanels.kbx) {
                            keyBindBox.drawScreen(mouseX, mouseY, partialTicks);
                        }

                        for (VisibleButton visibleButton : modPanels.visibleButtons) {
                            visibleButton.drawScreen(mouseX, mouseY, partialTicks);
                        }


                        for (Element element : modPanels.elements) {
                            if (element.setting.isVisible())
                                element.drawScreen(mouseX, mouseY);
                        }

                        GL11.glDisable(GL11.GL_SCISSOR_TEST);
                        GL11.glPopMatrix();

                        GL11.glPopMatrix();
                    } else {
                    }
                }
            }
        } else {
            this.translateSetting = new Translate(0, 0);
        }
        if (exist) {
            translate.interpolate(0, 0, 2);
            if (translate.getY() < 300) {
                exist = false;
                this.mc.displayGuiScreen((GuiScreen) null);
                if (this.mc.currentScreen == null) {
                    this.mc.setIngameFocus();
                }
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        for (Panels panels : this.panels) {
            panels.mouseClicked(mouseX, mouseY, mouseButton);
        }
        RenderUtils.drawRoundedRect(scaledResolution.getScaledWidth() - 50, scaledResolution.getScaledHeight() - 30, 40, 20, 10, Color.WHITE);

        if (mouseButton == 0 && mouseX > scaledResolution.getScaledWidth() - 50 && mouseX < scaledResolution.getScaledWidth() && mouseY > scaledResolution.getScaledHeight() - 30 && mouseY < scaledResolution.getScaledHeight() - 10)
            mc.displayGuiScreen(new DrawConfigManager());

        if (mouseButton == 0 && mouseX > scaledResolution.getScaledWidth() - 50 && mouseX < scaledResolution.getScaledWidth() && mouseY > scaledResolution.getScaledHeight() - 70 && mouseY < scaledResolution.getScaledHeight() - 50)
            mc.displayGuiScreen(new CustomHUD());

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panels panels : this.panels) {
            panels.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (Panels panels : this.panels) {
            for (ModPanels modPanels : panels.modPanels) {
                if (usingSetting && modPanels.usingSettings) {
                    modPanels.keyTyped(typedChar, keyCode);
                    return;
                }
            }
        }

        if (keyCode == 1 && !usingSetting)
            exist = true;

        if (exist)
            return;

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        this.translate = new Translate(0, 0);
        for (Panels panels : this.panels) {
            for (ModPanels modPanels : panels.modPanels) {
                modPanels.animation = 0;
            }
        }
        super.initGui();
    }
}
