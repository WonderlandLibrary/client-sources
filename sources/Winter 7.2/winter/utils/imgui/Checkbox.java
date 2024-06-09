/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.imgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import winter.utils.Render;
import winter.utils.imgui.CheckboxEvent;
import winter.utils.imgui.Panel;

public class Checkbox {
    private CheckboxEvent event;
    private String displayName;
    public boolean state;
    private Panel panel;
    private int x;
    private int y;

    public Checkbox(String displayName, Panel p2) {
        this.displayName = displayName;
        this.panel = p2;
        this.state = false;
    }

    public void render(int x2, int y2) {
        this.x = x2;
        this.y = y2;
        Render.drawRect(x2, y2, x2 + 12, y2 + 12, 1620679065);
        if (this.state) {
            Render.drawRect(x2 + 2, y2 + 2, x2 + 10, y2 + 10, -8947798);
        }
        Minecraft.getMinecraft().fontRendererObj.drawString(this.displayName, x2 + 14, y2 + 2, -1);
    }

    public void onClick(int mouseX, int mouseY) {
        if (this.mouseOver(mouseX, mouseY)) {
            this.state = !this.state;
            this.event.onStateChanged(this);
        }
    }

    public void setStateChangedEvent(CheckboxEvent event) {
        this.event = event;
    }

    public boolean mouseOver(int mouseX, int mouseY) {
        if (mouseY > Panel.pY + 12 && (float)mouseY < (float)Panel.pY + Panel.height && mouseX > this.x && mouseX < this.x + 12 && mouseY > this.y && mouseY < this.y + 12) {
            return true;
        }
        return false;
    }
}

