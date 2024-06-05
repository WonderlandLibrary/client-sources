package alphentus.gui.customhud.settings;

import alphentus.gui.customhud.settings.settings.Value;
import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatComponentText;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class DrawSlider {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei21;
    private final HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);
    private final Minecraft mc = Minecraft.getMinecraft();
    private int x, y, width, height;
    private int sliderLength = 150, sliderThickness = 4;
    private final Value value;

    public DrawSlider(Value value, int x, int y, int width, int height) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(float mouseX, float mouseY, float partialTicks) {
        this.x = 100;

        float min = value.getMinValue();
        float max = value.getMaxValue();
        float current = (value.getCurrentValue() - min) / (max -min);
        float value2 = (float) (value.isOnlyInt() ? (int) Math.round(value.getCurrentValue() * 100) / 100.0 : Math.round(value.getCurrentValue() * 100) / 100.0);

        // Slider Background and Name
        Gui.drawRect(Gui.getScaledResolution().getScaledWidth() - x - sliderLength - 3, y + height, Gui.getScaledResolution().getScaledWidth() - x - 3, y + height + sliderThickness, hud.guiColor1.getRGB());
        String s = value.isDragging() || hovered(mouseX, mouseY) ? value2 + "" : value.getValueName();
        fontRenderer.drawStringWithShadow(s, Gui.getScaledResolution().getScaledWidth() - x - 1.5F - sliderLength / 2 - fontRenderer.getStringWidth(s) / 2, y + 3, hud.textColor.getRGB(), false);

        // Current Value
        Gui.drawRect(Gui.getScaledResolution().getScaledWidth() - x - sliderLength - 3, y + height, (int) ((Gui.getScaledResolution().getScaledWidth() - x - sliderLength - 3) + current * sliderLength), y + height + sliderThickness, Init.getInstance().CLIENT_COLOR.getRGB());


        updateValues(mouseX);
    }

    public void updateValues(float mouseX) {
        this.x = 100;
        if (value.isDragging()) {
            int x = Gui.getScaledResolution().getScaledWidth() - this.x - sliderLength - 3;
            float min = value.getMinValue();
            float max = value.getMaxValue();
            float newValue = (float) (Math.round(Math.max(Math.min((mouseX - x) / width * (max - min) + min, max), min) * 100) / 100.0);
            int newValueInt = (int) (Math.round(Math.max(Math.min((mouseX - x) / width * (max - min) + min, max), min) * 100) / 100.0);
            value.setCurrentValue(value.isOnlyInt() ? newValueInt : newValue);
        }
    }

    public boolean hovered(float mouseX, float mouseY) {
        this.x = 100;
        return mouseX >= Gui.getScaledResolution().getScaledWidth() - x - sliderLength - 3 && mouseX <= Gui.getScaledResolution().getScaledWidth() - x - 3 && mouseY >= y + height && mouseY <= y + height + sliderThickness;
    }

    public void mouseClicked(float mouseX, float mouseY, float mouseButton) {
        this.x = 100;
        if (mouseButton == 0 && hovered(mouseX, mouseY)) {
            value.setDragging(true);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.x = 100;
        value.setDragging(false);
    }

}