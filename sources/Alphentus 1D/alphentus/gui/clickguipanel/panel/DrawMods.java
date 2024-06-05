package alphentus.gui.clickguipanel.panel;

import alphentus.gui.clickguipanel.settings.*;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;

public class DrawMods {

    private ArrayList<ElementKeybind> elementKeybinds = new ArrayList<>();
    private ArrayList<ElementVisible> elementVisibles = new ArrayList<>();
    private ArrayList<Element> elements = new ArrayList<>();

    private Minecraft mc = Minecraft.getMinecraft();
    private FontRenderer fr = Init.getInstance().fontManager.getFont("arial", 18);
    private Mod mod;
    private int x, y, width, modHeight;
    private ScaledResolution sr;

    private boolean extendedMod;

    private int extendedY;

    public DrawMods(Mod mod) {
        this.mod = mod;

        elementKeybinds.add(new ElementKeybind(mod));
        elementVisibles.add(new ElementVisible(mod));

        for (Setting setting : Init.getInstance().settingManager.getSettingArrayList()) {
            if (setting.getMod() == mod) {
                if (setting.getSettingIdentifier().equals("CheckBox"))
                    elements.add(new ElementCheckbox(setting));
                if (setting.getSettingIdentifier().equals("ComboBox"))
                    elements.add(new ElementCombobox(setting));
                if (setting.getSettingIdentifier().equals("Slider"))
                    elements.add(new ElementSlider(setting));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        String s = mod.getModuleName();
        Gui.drawRect(x, y, x + width, y + modHeight, mod.getState() ? Init.getInstance().CLIENT_COLOR.getRGB() : 0xFF202020);
        if (isHovered(mouseX, mouseY))
            Gui.drawRect(x, y, x + width, y + modHeight, 0x99303030);
        fr.drawStringWithShadow(s, x + width / 2 - fr.getStringWidth(s) / 2, y + modHeight / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);

        int y = this.y + modHeight;
        int onlySettingsY = 0;
        int width = this.width;
        int shorten = 2;

        if (extendedMod) {
            for (Element element2 : elementKeybinds) {
                element2.setValues(x + shorten, y, width - shorten * 2, modHeight);
                element2.drawScreen(mouseX, mouseY, partialTicks);
                y += modHeight;
                onlySettingsY += modHeight;
            }

            for (Element element3 : elementVisibles) {
                element3.setValues(x + shorten, y, width - shorten * 2, modHeight);
                element3.drawScreen(mouseX, mouseY, partialTicks);
                y += modHeight;
                onlySettingsY += modHeight;
            }

            for (Element element : elements) {
                if (element.setting.isVisible()) {
                    element.setValues(x + shorten, y, width - shorten * 2, modHeight);
                    element.drawScreen(mouseX, mouseY, partialTicks);
                    y += modHeight;
                    onlySettingsY += modHeight;

                    if (element.setting.getSettingIdentifier().equals("ComboBox") && element.setting.isExtended()) {
                        y += element.setting.getCombos().length * modHeight;
                        onlySettingsY += element.setting.getCombos().length * modHeight;
                    }
                }
            }
            this.extendedY = onlySettingsY;
        }

    }

    public void setValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.modHeight = height;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + modHeight;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            mod.setState(!mod.getState());
        }
        if (isHovered(mouseX, mouseY) && mouseButton == 1)
            this.extendedMod = !this.extendedMod;

        int y = this.y;
        int width = this.width + 20;
        if (extendedMod) {
            for (Element element2 : elementKeybinds) {
                element2.mouseClicked(mouseX, mouseY, mouseButton);
            }

            for (Element element3 : elementVisibles) {
                element3.mouseClicked(mouseX, mouseY, mouseButton);
            }

            for (Element element : elements) {
                if (element.setting.isVisible()) {
                    element.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    public boolean isExtendedMod() {
        return extendedMod;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (extendedMod) {
            for (Element element2 : elementKeybinds) {
                element2.mouseReleased(mouseX, mouseY, state);
            }

            for (Element element3 : elementVisibles) {
                element3.mouseReleased(mouseX, mouseY, state);
            }

            for (Element element : elements) {
                if (element.setting.isVisible()) {
                    element.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (extendedMod) {
            for (Element element2 : elementKeybinds) {
                element2.keyTyped(typedChar, keyCode);
            }
        }
    }

    public int getExtendedY() {
        return extendedY;
    }
}