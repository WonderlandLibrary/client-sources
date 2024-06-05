package koks.gui.customhud;

import koks.Koks;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import koks.gui.customhud.valuehudsystem.components.CheckBox;
import koks.gui.customhud.valuehudsystem.components.ComboBox;
import koks.gui.customhud.valuehudsystem.components.ComponentValue;
import koks.gui.customhud.valuehudsystem.components.Slider;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 04:01
 */
public class ComponentSettings {

    private final List<ComponentValue> componentValueList = new ArrayList<>();
    private final Component component;
    private int x, y, width, height, dragX, dragY;
    private boolean drag;
    private final String title;

    private final RenderUtils renderUtils = new RenderUtils();
    private final FontRenderer mcFontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public ComponentSettings(Component component, String title) {
        this.component = component;
        this.title = title;
        this.x = 50;
        this.y = 50;
        Koks.getKoks().valueHUDManager.getValueHUDS().forEach(valueHUD -> {
            if (valueHUD.getComponent().equals(component)) {
                switch (valueHUD.getType()) {
                    case CHECKBOX:
                        this.getComponentValueList().add(new CheckBox(valueHUD));
                        break;
                    case COMBO:
                        this.getComponentValueList().add(new ComboBox(valueHUD));
                        break;
                    case SLIDER:
                        this.getComponentValueList().add(new Slider(valueHUD));
                        break;
                }
            }
        });
    }

    public void drawScreen(int mouseX, int mouseY) {

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB());
        mcFontRenderer.drawStringWithShadow(title, x + width / 2 - mcFontRenderer.getStringWidth(title) / 2, y + height / 2 - mcFontRenderer.FONT_HEIGHT / 2, -1);

        int[] yValues = {height};
        this.componentValueList.forEach(componentValue -> {
            componentValue.setInformation(x, y + yValues[0], width, 20);
            componentValue.drawScreen(mouseX, mouseY);

            if (componentValue instanceof ComboBox) {
                if (((ComboBox) componentValue).extended)
                    Arrays.stream(componentValue.getValue().getComboArray()).forEach(value -> yValues[0] += 15);
            }

            yValues[0] += 20;
        });

        updatePosition(mouseX, mouseY);
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (drag) {
            this.x = dragX + mouseX;
            this.y = dragY + mouseY;
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            if (mouseX > scaledResolution.getScaledWidth() - width)
                this.x = scaledResolution.getScaledWidth() - width - 2;
            if (mouseX < 1)
                this.x = 3;
            if (mouseY > scaledResolution.getScaledHeight() - height)
                this.y = scaledResolution.getScaledHeight() - height - 2;
            if (mouseY < 1)
                this.y = 3;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.drag = true;
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
        }
        this.componentValueList.forEach(componentValue -> componentValue.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void mouseReleased() {
        this.drag = false;
        this.componentValueList.forEach(ComponentValue::mouseReleased);
    }

    public void setInformation(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public List<ComponentValue> getComponentValueList() {
        return componentValueList;
    }

    public Component getComponent() {
        return component;
    }

}
