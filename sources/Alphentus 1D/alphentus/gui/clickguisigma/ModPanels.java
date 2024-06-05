package alphentus.gui.clickguisigma;

import alphentus.gui.clickgui.settings.ComboBox;
import alphentus.gui.clickgui.settings.KeyBindBox;
import alphentus.gui.clickguisigma.settings.*;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import alphentus.settings.Setting;
import alphentus.utils.GLUtil;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class ModPanels {

    public Mod mod;
    public int x, y, width, height;
    public HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    public final ArrayList<KeyBindBox> kbx = new ArrayList<>();
    public final ArrayList<VisibleButton> visibleButtons = new ArrayList<>();

    boolean usingSettings = false;

    public final ArrayList<Element> elements = new ArrayList<>();

    UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei19;

    public float animation = 0;
    public int scroll, scroll2;

    Translate translate;

    public int yplus;

    public ModPanels(Mod mod) {
        this.mod = mod;
        this.translate = new Translate(0, 0);
        kbx.add(new KeyBindBox(mod));
        visibleButtons.add(new VisibleButton(mod));

        for (Setting setting : Init.getInstance().settingManager.getSettingArrayList()) {

            if (setting.getSettingIdentifier().equals("CheckBox") && setting.getMod() == mod) {
                elements.add(new ElementCheckBox(this, setting));
            }
            if (setting.getSettingIdentifier().equals("Slider") && setting.getMod() == mod) {
                elements.add(new ElementSlider(this, setting));
            }
            if (setting.getSettingIdentifier().equals("ComboBox") && setting.getMod() == mod) {
                elements.add(new ElementComboBox(this, setting));
            }

        }

    }

    public void drawScreen(int mouseX, int mouseY) {

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        RenderUtils.relativeRect(x, y, x + width, y + height, hud.guiColor4.getRGB());


        if (mod.getState()) {

            if (Init.getInstance().clickGUISigma.translate.getY() == scaledResolution.getScaledHeight()) {
                if (animation < 60)
                    animation += 0.055 * RenderUtils.deltaTime;

                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                GLUtil.makeScissorBox(x, y, x + width, y + height + 1);
                RenderUtils.drawFilledCircle(x + width / 2, y + height / 2 + 1, animation, isHovering(mouseX, mouseY) ? Init.getInstance().CLIENT_COLOR.darker() : Init.getInstance().CLIENT_COLOR);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GL11.glPopMatrix();
            }

        } else {
            if (isHovering(mouseX, mouseY) && !Init.getInstance().clickGUISigma.usingSetting) {
                if (hud.dark)
                    RenderUtils.relativeRect(x, y, x + width, y + height, new Color(25, 25, 25, 255).getRGB());
                else
                    RenderUtils.relativeRect(x, y, x + width, y + height, new Color(220, 220, 220, 255).getRGB());
            }
            animation = 0;
        }


        fontRenderer.drawStringWithShadow(mod.getModuleName(), x + 10, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);

        int yPlus = 0;
        if (usingSettings) {

            for (KeyBindBox keyBindBox : this.kbx) {
                keyBindBox.setPosition(scaledResolution.getScaledWidth() / 2 - 140, 90 + yPlus - scroll, 138 * 2, 15);
                yPlus += 20;
            }

            for (VisibleButton visibleButton : this.visibleButtons) {
                visibleButton.setPosition(scaledResolution.getScaledWidth() / 2 - 140, 90 + yPlus - scroll, 138 * 2, 15);
                yPlus += 20;
            }

            for (Element element : this.elements) {
                if (element.setting.isVisible()) {

                    if (element.setting.getSettingIdentifier().equals("Slider")) {
                        element.setInformations(scaledResolution.getScaledWidth() / 2 + 50, 90 + yPlus - scroll, 97, 15);
                    } else if (element.setting.getSettingIdentifier().equals("CheckBox")) {
                        element.setInformations(scaledResolution.getScaledWidth() / 2 - 140, 90 + yPlus - scroll, 138 * 2, 15);
                    } else if (element.setting.getSettingIdentifier().equals("ComboBox")) {

                        ArrayList<String> longestString = new ArrayList<>(Arrays.asList(element.setting.getCombos()));
                        int widthCombo = 0;
                        String max = Collections.max(longestString, Comparator.comparing(String::length));
                        widthCombo = Init.getInstance().fontManager.stem19.getStringWidth(max + "   ") + Init.getInstance().fontManager.stem19.getStringWidth("V");

                        longestString.clear();

                        element.setInformations(scaledResolution.getScaledWidth() / 2 + 148, 90 + yPlus - scroll, widthCombo, 15);

                        if (element.comboextended) {
                            for (String sld : element.setting.getCombos()) {
                                if (!element.setting.getSelectedCombo().equals(sld))
                                    yPlus += 20;
                            }
                        }
                    }

                    yPlus += 20;
                }
            }
            this.yplus = yPlus;
            int mouseWheel = Mouse.getDWheel();

            if (mouseWheel > 0) {
                if (scroll2 > 0)
                    scroll2 -= 10;
            }
            if (mouseWheel < 0) {
                if (scroll2 < yPlus - scaledResolution.getScaledHeight() / 2 - 113 && yPlus + 25 > scaledResolution.getScaledHeight() - 150)
                    scroll2 += 10;
            }


            if(Init.getInstance().modManager.getModuleByClass(ClickGUI.class).settingSmoothScrolling.isState()) {
                translate.interpolate(scroll2, 0, 2);
                this.scroll = (int) translate.getX();
            }else{
                this.scroll = scroll2;
            }

        }

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Element element : this.elements) {
            element.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void initGui() {
        animation = 0;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        if (Init.getInstance().clickGUISigma.usingSetting) {
            if (usingSettings) {

                for (KeyBindBox keyBindBox : this.kbx) {
                    keyBindBox.mouseClicked(mouseX, mouseY, mouseButton);
                }

                for (VisibleButton visibleButton : this.visibleButtons) {
                    visibleButton.mouseClicked(mouseX, mouseY, mouseButton);
                }

                for (Element element : this.elements) {
                    element.mouseClicked(mouseX, mouseY, mouseButton);
                }

            }
        } else {
            if (isHovering(mouseX, mouseY) && mouseButton == 0)
                this.mod.setState(!this.mod.getState());
            if (isHovering(mouseX, mouseY) && mouseButton == 1 && !Init.getInstance().clickGUISigma.usingSetting) {
                Init.getInstance().clickGUISigma.usingSetting = true;
                usingSettings = true;
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

        if (Init.getInstance().clickGUISigma.usingSetting) {
            if (keyCode == 1) {
                Init.getInstance().clickGUISigma.usingSetting = false;
                usingSettings = false;
            } else {

                for (KeyBindBox keyBindBox : this.kbx) {
                    keyBindBox.keyTyped(typedChar, keyCode);
                }
            }
        }

    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
