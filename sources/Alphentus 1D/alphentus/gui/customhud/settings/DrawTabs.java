package alphentus.gui.customhud.settings;

import alphentus.gui.customhud.CustomHUD;
import alphentus.gui.customhud.settings.settings.Value;
import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class DrawTabs {

    private ArrayList<Value> checkbox = new ArrayList<>();
    private ArrayList<Value> combobox = new ArrayList<>();
    private ArrayList<Value> slider = new ArrayList<>();
    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei21;
    private final HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);
    private final Minecraft mc = Minecraft.getMinecraft();
    ValueTab valueTab;
    int x, y, width, height;

    public DrawTabs(ValueTab valueTab, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.valueTab = valueTab;

        for (Value value : Init.getInstance().valueManager.getValues()) {
            if (value.getValueTab() == valueTab) {
                if (value.getValueIdentifier().equals("CheckBox") && value.isVisible())
                    checkbox.add(value);
                if (value.getValueIdentifier().equals("ComboBox") && value.isVisible())
                    combobox.add(value);
                if (value.getValueIdentifier().equals("Slider") && value.isVisible())
                    slider.add(value);
            }
        }
    }

    public void drawScreen(float mouseX, float mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, hud.guiColor4.getRGB());
        fontRenderer.drawStringWithShadow(valueTab.name(), x + width / 2 - fontRenderer.getStringWidth(valueTab.name()) / 2, y + height - height / 4 - fontRenderer.FONT_HEIGHT, valueTab == Init.getInstance().customHUD.getSelectedTab() ? Init.getInstance().CLIENT_COLOR.getRGB() : hud.textColor.getRGB(), false);

        if (valueTab != Init.getInstance().customHUD.getSelectedTab())
            return;

        int y[] = {this.y + height, this.y + height, this.y + height};
        for (Value valueCheckbox : checkbox) {
            DrawCheckbox drawCheckbox = new DrawCheckbox(valueCheckbox, x, y[0], width, height);
            drawCheckbox.drawScreen(mouseX, mouseY, partialTicks);
            y[0] += height;
        }
        for (Value valueComboBox : combobox) {
            DrawCombobox drawCombobox = new DrawCombobox(valueComboBox, x, y[1], width, height);
            drawCombobox.drawScreen(mouseX, mouseY, partialTicks);
            if (valueComboBox.isExtended())
                y[1] += valueComboBox.getModes().length * height;
            y[1] += height * 2;
        }
        for (Value valueSlider : slider) {
            DrawSlider drawSlider = new DrawSlider(valueSlider, x, y[2], width, height);
            drawSlider.drawScreen(mouseX, mouseY, partialTicks);
            y[2] += height * 2;
        }
    }

    public void mouseClicked(float mouseX, float mouseY, float mouseButton) {
        if (mouseButton == 0 && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            Init.getInstance().customHUD.setSelectedTab(valueTab);
        }

        if (valueTab != Init.getInstance().customHUD.getSelectedTab())
            return;

        int y[] = {this.y + height, this.y + height, this.y + height};
        for (Value valueCheckbox : checkbox) {
            DrawCheckbox drawCheckbox = new DrawCheckbox(valueCheckbox, x, y[0], width, height);
            drawCheckbox.mouseClicked(mouseX, mouseY, mouseButton);
            y[0] += height;
        }
        for (Value valueComboBox : combobox) {
            DrawCombobox drawCombobox = new DrawCombobox(valueComboBox, x, y[1], width, height);
            drawCombobox.mouseClicked(mouseX, mouseY, mouseButton);
            if (valueComboBox.isExtended())
                y[1] += valueComboBox.getModes().length * height;
            y[1] += height * 2;
        }
        for (Value valueSlider : slider) {
            DrawSlider drawSlider = new DrawSlider(valueSlider, x, y[2], width, height);
            drawSlider.mouseClicked(mouseX, mouseY, mouseButton);
            y[2] += height * 2;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        int y[] = {this.y + height, this.y + height, this.y + height};
        for (Value valueSlider : slider) {
            DrawSlider drawSlider = new DrawSlider(valueSlider, x, y[2], width, height);
            drawSlider.mouseReleased(mouseX, mouseY, state);
            y[2] += height * 2;
        }
    }
}