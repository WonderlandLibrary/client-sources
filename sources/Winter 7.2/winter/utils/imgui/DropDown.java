/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.imgui;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import winter.utils.Render;
import winter.utils.imgui.Checkbox;
import winter.utils.imgui.Panel;

public class DropDown {
    public static final String ARROW_RIGHT = "\u25b6";
    public static final String ARROW_DOWN = "\u25bc";
    public ArrayList<Checkbox> checkboxes;
    public boolean open;
    public Panel panel;
    public String name;
    public int x;
    public int y;

    public DropDown(String name, Panel p2) {
        this.name = name;
        this.panel = p2;
        this.checkboxes = new ArrayList();
    }

    public void render(int x2, int y2) {
        this.x = x2;
        this.y = y2;
        Render.drawRect(x2, y2, (float)x2 + Panel.width - 2.0f, y2 + 12, -8947798);
        Minecraft.getMinecraft().fontRendererObj.drawString(String.valueOf(this.open ? "\u25bc" : "\u25b6") + " " + this.name, x2 + 6, y2 + 2, -1);
        int off = 0;
        if (this.open) {
            for (Checkbox check : this.checkboxes) {
                check.render(x2 + 1, y2 + off + 13);
                off += 13;
            }
        }
    }

    public void onClick(int mouseX, int mouseY, int button) {
        if (this.mouseOver(mouseX, mouseY)) {
            boolean bl2 = this.open = !this.open;
        }
        if (this.open) {
            for (Checkbox check : this.checkboxes) {
                if (button != 0) continue;
                check.onClick(mouseX, mouseY);
            }
        }
    }

    public boolean mouseOver(int mouseX, int mouseY) {
        if (mouseY > Panel.pY + 12 && (float)mouseY < (float)Panel.pY + Panel.height && mouseX > this.x && (float)mouseX < (float)this.x + Panel.width - 1.0f && mouseY > this.y && mouseY < this.y + 12) {
            return true;
        }
        return false;
    }

    public int getHeight() {
        if (this.open) {
            return 13 * (this.checkboxes.size() + 1);
        }
        return 13;
    }

    public void addCheckbox(Checkbox check) {
        this.checkboxes.add(check);
    }
}

