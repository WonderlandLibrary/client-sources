package alphentus.gui.clickgui;

import alphentus.gui.clickgui.settings.CheckBox;
import alphentus.gui.clickgui.settings.ComboBox;
import alphentus.gui.clickgui.settings.KeyBindBox;
import alphentus.gui.clickgui.settings.Slider;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * @author avox
 * @since on 30/07/2020.
 */
public class ModPanel {

    private final Mod mod;
    private int x, y, width, height;
    private final FontRenderer fontRenderer = Init.getInstance().fontManager.stem20;
    private boolean extended;

    private int scroll;

    private final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private final ArrayList<ComboBox> cb = new ArrayList<>();
    private final ArrayList<Slider> sliders = new ArrayList<>();
    public final ArrayList<KeyBindBox> kbx = new ArrayList<>();


    public ModPanel(Mod mod) {
        this.mod = mod;
        kbx.add(new KeyBindBox(mod));

        for (Setting setting : Init.getInstance().settingManager.getSettingArrayList()) {

            if (setting.getSettingIdentifier().equals("CheckBox") && setting.getMod() == mod) {
                checkBoxes.add(new CheckBox(setting));
            }
            if (setting.getSettingIdentifier().equals("ComboBox") && setting.getMod() == mod) {
                cb.add(new ComboBox(setting));
            }
            if (setting.getSettingIdentifier().equals("Slider") && setting.getMod() == mod) {
                sliders.add(new Slider(setting));
            }
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


        String test = extended ? "..." : "";
        fontRenderer.drawStringWithShadow(mod.getModuleName(), x + width / 2 - fontRenderer.getStringWidth(mod.getModuleName()) / 2, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, mod.getState() ? Init.getInstance().CLIENT_COLOR.getRGB() : -1);
        fontRenderer.drawStringWithShadow(test, x + width - fontRenderer.getStringWidth(test) - 1, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, mod.getState() ? Init.getInstance().CLIENT_COLOR.getRGB() : -1);


        if (!extended)
            return;

        fontRenderer.drawStringWithShadow("Setting: " + mod.getModuleName(), x + width + 2, Init.getInstance().clickGUI.y + 25 - scroll, -1);

        int yAdd = 2 + fontRenderer.FONT_HEIGHT;

        for (KeyBindBox kbx : this.kbx) {

            kbx.setPosition(x + width + 2, Init.getInstance().clickGUI.y + 25 + yAdd - scroll, width, 15);
            kbx.drawScreen(mouseX, mouseY, partialTicks);

            yAdd += 17;
        }

        for (ComboBox cb : this.cb) {
            if (cb.getSetting().isVisible()) {

                ArrayList<String> longestString = new ArrayList<>(Arrays.asList(cb.getSetting().getCombos()));
                int widthCombo = 0;

                String max = Collections.max(longestString, Comparator.comparing(String::length));
                widthCombo = Init.getInstance().fontManager.stem19.getStringWidth(max + "   ") + Init.getInstance().fontManager.stem19.getStringWidth("V");

                longestString.clear();
                cb.setPosition(x + width + 2, Init.getInstance().clickGUI.y + 25 + yAdd - scroll, widthCombo, 15);
                cb.drawScreen(mouseX, mouseY, partialTicks);

                if (cb.isExtended()) {
                    for (String sld : cb.getSetting().getCombos()) {
                        if (!cb.getSetting().getSelectedCombo().equals(sld)) {
                            yAdd += 15;
                        }
                    }
                }

                yAdd += 17;
            }
        }


        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.getSetting().isVisible()) {
                checkBox.setPosition(x + width + 2, Init.getInstance().clickGUI.y + 25 + yAdd - scroll, width, 15);
                checkBox.drawScreen(mouseX, mouseY, partialTicks);
                yAdd += 17;
            }
        }

        for (Slider slider : this.sliders) {
            if (slider.getSetting().isVisible()) {
                slider.setPosition(x + width + 2, Init.getInstance().clickGUI.y + 25 + yAdd - scroll, width + 60, 15);
                slider.drawScreen(mouseX, mouseY, partialTicks);
                yAdd += 17;
            }
        }

        if (mouseX > Init.getInstance().clickGUI.x + 80 && mouseY < Init.getInstance().clickGUI.x + 80 + Init.getInstance().clickGUI.width && mouseY > Init.getInstance().clickGUI.y + 25 && mouseY < Init.getInstance().clickGUI.y + 25 + Init.getInstance().clickGUI.height) {
            if (Init.getInstance().clickGUI.mouse < 0) {
                if (scroll < yAdd - Init.getInstance().clickGUI.height + height / 2 + height && yAdd + 25 > Init.getInstance().clickGUI.height)
                    scroll += 10;
            }

            if (Init.getInstance().clickGUI.mouse > 0) {
                if (scroll > 0) {
                    scroll -= 10;
                }
            }
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            mod.setState(!mod.getState());
        }
        if (isHovering(mouseX, mouseY) && mouseButton == 1) {
            this.extended = !this.extended;
        }

        if (!extended)
            return;

        for (CheckBox checkBox : this.checkBoxes) {
            if (checkBox.getY() < Init.getInstance().clickGUI.height + height + height + height)

                checkBox.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for (ComboBox cb : this.cb) {
            if (cb.getY() < Init.getInstance().clickGUI.height + height + height + height)
                cb.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for (Slider slider : this.sliders) {
            if (slider.getY() < Init.getInstance().clickGUI.height + height + height + height)
                slider.mouseClicked(mouseX, mouseY, mouseButton);
        }
        for (KeyBindBox kbx : this.kbx) {
            kbx.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseRelease() {
        for (Slider slider : this.sliders) {
            slider.mouseRelease();
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        for (KeyBindBox kbx : this.kbx) {
            kbx.keyTyped(typedChar, keyCode);
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

    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
