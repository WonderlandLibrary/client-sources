package me.superskidder.lune.guis.nullclickgui;

import java.awt.Color;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.modules.Mod;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeyBindButton
extends ValueButton {
    public Mod cheat;
    public double opacity = 0.0;
    public boolean bind;

    public KeyBindButton(Mod cheat, int x, int y) {
        super(null, x, y);
        this.custom = true;
        this.bind = false;
        this.cheat = cheat;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.opacity = mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5 ? (this.opacity + 10.0 < 200.0 ? (this.opacity += 10.0) : 200.0) : (this.opacity - 6.0 > 0.0 ? (this.opacity -= 6.0) : 0.0);
        //Gui.drawRect(this.x - 9, this.y - 4, this.x - 9 + 88, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 6, new Color(120, 120, 120, (int)this.opacity).getRGB());
        FontLoaders.F14.drawStringWithShadow("Bind", this.x, this.y, -1);
        FontLoaders.F14.drawStringWithShadow(String.valueOf(this.bind ? "" : "") + Keyboard.getKeyName((int)this.cheat.getKey()), this.x + 75 - FontLoaders.F14.getStringWidth(Keyboard.getKeyName((int)this.cheat.getKey())), this.y, -1);
    }

    @Override
    public void key(char typedChar, int keyCode) {
        if (this.bind) {
            this.cheat.setKey(keyCode);
            if (keyCode == 1) {
                this.cheat.setKey(0);
            }
            ClickyUI.binding = false;
            this.bind = false;
        }
        super.key(typedChar, keyCode);
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5 && button == 0) {
            ClickyUI.binding = this.bind = !this.bind;
        }
        super.click(mouseX, mouseY, button);
    }
}

