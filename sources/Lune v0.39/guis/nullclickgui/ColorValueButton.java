package me.superskidder.lune.guis.nullclickgui;

import java.awt.Color;

import me.superskidder.lune.modules.render.HUD;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class ColorValueButton
        extends ValueButton {
    private float[] hue = new float[]{0.0f};
    private int position;
    private int color = new Color(125, 125, 125).getRGB();

    public ColorValueButton(int x, int y) {
        super(null, x, y);
        this.custom = true;
        this.position = -1111;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        float[] huee = new float[]{this.hue[0]};
        int i = this.x - 7;
        while (i < this.x - 7 + 85) {
            int color = Color.getHSBColor(huee[0] / 255.0f, 0.7f, 0.7f).getRGB();
            if (mouseX > i - 1 && mouseX < i + 1 && mouseY > this.y - 6 && mouseY < this.y + 12 && Mouse.isButtonDown((int) 0)) {
                this.color = color;
                this.position = i;
            }
            if (this.color == color) {
                this.position = i;
            }
            Gui.drawRect(i - 1, this.y - 2, i, this.y - 2 + 12, color);
            float[] arrf = huee;
            arrf[0] = arrf[0] + 4.0f;
            if (huee[0] > 255.0f) {
                huee[0] = huee[0] - 255.0f;
            }
            ++i;
        }
        Gui.drawRect(this.position, this.y - 2, this.position + 1, this.y - 2 + 12, -1);
        if (this.hue[0] > 255.0f) {
            this.hue[0] = this.hue[0] - 255.0f;
        }
        HUD.themeColor = color;
    }

    @Override
    public void key(char typedChar, int keyCode) {
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
    }
}

