package koks.gui.clickgui.commonvalue;


import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.gui.clickgui.ClickGUI;
import koks.gui.clickgui.commonvalue.elements.Element;
import koks.gui.clickgui.commonvalue.elements.ElementCheckBox;
import koks.gui.clickgui.commonvalue.elements.ElementColorPicker;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class CommonPanel {

    public final Minecraft mc = Minecraft.getMinecraft();
    public final FontRenderer fr = mc.fontRendererObj;

    public int x, y, width, height, dragX, dragY;
    public boolean dragging, extended;

    public List<Element> elements = new ArrayList<>();

    private final CommonValue color = new CommonValue("Color", Color.PINK, x, y, 0);
    private final RenderUtils renderUtils = new RenderUtils();


    public CommonPanel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        /*
         * Adding Elements
         */

        Koks.getKoks().commonValueManager.COMMON_SETTINGS.add(color);

        Koks.getKoks().commonValueManager.COMMON_SETTINGS.forEach(commonValue -> {
            if (commonValue.getType() == CommonValue.Type.COLORPICKER)
                this.elements.add(new ElementColorPicker(commonValue));
            if (commonValue.getType() == CommonValue.Type.CHECKBOX)
                this.elements.add(new ElementCheckBox(commonValue));
        });
    }

    public void drawScreen(int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + height,  new Color(12, 12, 12, 255).getRGB());
        String s = "Common Settings";
        fr.drawStringWithShadow(s, x + width / 2 - fr.getStringWidth(s) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, -1);

        if (extended) {
            int yB = 0;
            for (Element element : this.elements) {
                if (element.setting.getType() == CommonValue.Type.COLORPICKER)
                    yB += 95;
                if (element.setting.getType() == CommonValue.Type.CHECKBOX)
                    yB += 20;
            }
            Gui.drawRect(x, y + height, x + width, y + height + yB, new Color(22, 22, 22, 255).getRGB());
            int[] y = {this.y};

            this.elements.forEach(element -> {
                if (element.setting.getType() == CommonValue.Type.COLORPICKER) {
                    element.updateValues(x, y[0] + height, 80, 100);
                    y[0] += 95;
                }
                if (element.setting.getType() == CommonValue.Type.CHECKBOX) {
                    element.updateValues(x + 2, y[0] + height, width - 2, 20);
                    y[0] += 20;
                }
                element.drawScreen(mouseX, mouseY, 1.0F);
            });
        }
        if (dragging) {
            this.x = dragX + mouseX;
            this.y = dragY + mouseY;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
        }
        if (isHovering(mouseX, mouseY) && mouseButton == 1) {
            this.extended = !this.extended;
        }
        this.elements.forEach(element -> {
            try {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (Koks.getKoks().client_color != color.getColor())
                Koks.getKoks().client_color = color.getColor();
                Koks.getKoks().shutdownClient();
        }
    }

    public void mouseRelease() {
        this.dragging = false;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

}
