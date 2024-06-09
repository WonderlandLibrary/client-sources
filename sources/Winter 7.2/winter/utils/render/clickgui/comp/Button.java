/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.clickgui.comp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.render.clickgui.Component;
import winter.utils.render.clickgui.Frame;

public class Button
extends Component {
    public Frame parent;
    public Module mod;
    public int x;
    public int y;
    public int pOffset;

    public Button(Frame parent, Module mod, int pOff) {
        this.parent = parent;
        this.x = parent.x + 4;
        this.y = parent.y + 19;
        this.mod = mod;
        this.pOffset = pOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.x = this.parent.x + 4;
        this.y = this.parent.y + 19 + this.pOffset;
    }

    @Override
    public void renderComponent() {
        Render.drawBorderedRect(this.x, this.y, this.x + 92, this.y + 18, 0.5, this.mod.isEnabled() ? -14342875 : -13619152, -15658735);
        Render.drawRect((double)this.x + 0.5, (double)this.y + 0.5, (double)this.x + 91.5, this.y + 1, this.mod.isEnabled() ? -2146365167 : -12566464);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.mod.getName(), this.x + 46 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2, (float)this.y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.mouseOver(mouseX, mouseY) && mouseButton == 0) {
            this.mod.toggle();
        }
    }

    public boolean mouseOver(int mouseX, int mouseY) {
        if (mouseX > this.x && mouseY > this.y && mouseX < this.x + 92 && mouseY < this.y + 18) {
            return true;
        }
        return false;
    }
}

