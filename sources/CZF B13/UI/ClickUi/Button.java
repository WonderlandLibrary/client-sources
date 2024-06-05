package gq.vapu.czfclient.UI.ClickUi;

import com.google.common.collect.Lists;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.Modules.Render.ClickGui;
import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.ClientUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Button {
    public Module cheat;
    public Window parent;
    public int x;
    public int y;
    public int index;
    public int remander;
    public double opacity = 0.0;
    public ArrayList<ValueButton> buttons = Lists.newArrayList();
    public boolean expand;
    CFontRenderer font = FontLoaders.GoogleSans18;

    public Button(Module cheat, int x, int y) {
        CFontRenderer font = FontLoaders.GoogleSans16;
        CFontRenderer mfont = FontLoaders.GoogleSans18;
        CFontRenderer bigfont = FontLoaders.GoogleSans24;
        this.cheat = cheat;
        this.x = x;
        this.y = y;
        int y2 = y + 14;
        for (Value v : cheat.getValues()) {
            this.buttons.add(new ValueButton(v, x + 5, y2));
            y2 += 15;
        }
        this.buttons.add(new KeyBindButton(cheat, x + 5, y2));
    }

    public void render(int mouseX, int mouseY) {
        CFontRenderer font = FontLoaders.GoogleSans16;
        CFontRenderer mfont = FontLoaders.GoogleSans18;
        CFontRenderer bigfont = FontLoaders.GoogleSans24;
        if (this.index != 0) {
            Button b2 = this.parent.buttons.get(this.index - 1);
            this.y = b2.y + 15 + (b2.expand ? 15 * b2.buttons.size() : 0);
        }
        int i = 0;
        while (i < this.buttons.size()) {
            this.buttons.get(i).y = this.y + 14 + 15 * i;
            this.buttons.get(i).x = this.x + 5;
            ++i;
        }
        Gui.drawRect(this.x - 5, this.y - 5, this.x + 85, this.y + font.getStringHeight(this.cheat.getName()) + 2,
                ClientUtil.reAlpha(1, 0.5f));
        if (this.cheat.isEnabled()) {
//			RenderUtil.R2DUtils.drawRect(this.x - 5, this.y - 5, this.x + 85,
//					(float) (this.y + font.getStringHeight(this.cheat.getName()) + 3.8),
//					new Color(234, 234, 234).getRGB());
            Gui.drawRect(this.x - 5, this.y - 5, this.x + 85, (float) (this.y + font.getStringHeight(this.cheat.getName()) + 2), ClientUtil.reAlpha(1, 0.5f));

            font.drawString(this.cheat.getName(), this.x, this.y, new Color(ClickGui.red, ClickGui.green, ClickGui.blue).getRGB());
//			mc.fontRendererObj.drawStringWithShadow(this.cheat.getName(), this.x, this.y, new Color(210, 132, 246).getRGB());
        } else {
            font.drawString(this.cheat.getName(), this.x, this.y, new Color(255, 255, 255).getRGB());
//			mc.fontRendererObj.drawStringWithShadow(this.cheat.getName(), this.x, this.y, new Color(255, 255, 255).getRGB());
        }
        if (!this.expand && this.buttons.size() > 1) {
            //FontLoaders.NovICON20.drawString("G", this.x + 76, this.y + 1, new Color(108, 108, 108).getRGB());
        }
        if (this.expand) {
            this.buttons.forEach(b -> b.render(mouseX, mouseY));
        }
    }

    public void key(char typedChar, int keyCode) {
        this.buttons.forEach(b -> b.key(typedChar, keyCode));
    }

    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6
                && mouseY < this.y + font.getStringHeight(this.cheat.getName()) + 4) {
            if (button == 0) {
                this.cheat.setEnabled(!this.cheat.isEnabled());
            }
            if (button == 1 && !this.buttons.isEmpty()) {
                boolean bl = this.expand = !this.expand;
            }
        }
        if (this.expand) {
            this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
        }
    }

    public void setParent(Window parent) {
        this.parent = parent;
        int i = 0;
        while (i < this.parent.buttons.size()) {
            if (this.parent.buttons.get(i) == this) {
                this.index = i;
                this.remander = this.parent.buttons.size() - i;
                break;
            }
            ++i;
        }
    }
}
