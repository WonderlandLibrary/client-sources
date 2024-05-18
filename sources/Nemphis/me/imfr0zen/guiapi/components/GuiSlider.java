/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.imfr0zen.guiapi.components;

import java.util.ArrayList;
import me.imfr0zen.guiapi.MathUtil;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.GuiComponent;
import me.imfr0zen.guiapi.listeners.ValueListener;
import org.lwjgl.input.Mouse;

public class GuiSlider
implements GuiComponent {
    private static int dragId = -1;
    private boolean wasSliding;
    private float min;
    private float max;
    private float current;
    private int c;
    private int round;
    private int id;
    private String text;
    private ArrayList<ValueListener> vallisteners = new ArrayList();

    public GuiSlider(String text, float min, float max, float current) {
        this(text, min, max, current, 0);
    }

    public GuiSlider(String text, float min, float max, float current, int round) {
        this.text = text;
        this.min = min;
        this.max = max;
        this.current = current;
        this.c = (int)(current > max ? 50.0f : current * 50.0f / max);
        this.round = round;
        this.id = me.imfr0zen.guiapi.ClickGui.currentId++;
    }

    public void addValueListener(ValueListener vallistener) {
        this.vallisteners.add(vallistener);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        int w = RenderUtil.getWidth(this.text);
        boolean hovered = RenderUtil.isHovered(posX + w + 7, posY + 1, 50, 12, mouseX, mouseY);
        if (Mouse.isButtonDown((int)0) && (dragId == this.id || dragId == -1 && hovered)) {
            if (mouseX < posX + w + 7) {
                this.current = this.min;
                this.c = 0;
            } else if (mouseX > posX + w + 57) {
                this.current = this.max;
                this.c = 50;
            } else {
                this.current = this.round == 0 ? (float)Math.round(((float)(mouseX - posX - w) - 7.0f) / 50.0f * this.max) : MathUtil.round((float)(mouseX - posX - w - 7) / 50.0f * this.max, this.round);
                this.current += this.current + this.min >= this.max ? 0.0f : this.min;
                this.c = mouseX - posX - w - 7;
            }
            dragId = this.id;
            for (ValueListener listener : this.vallisteners) {
                listener.valueUpdated(this.current);
            }
            this.wasSliding = true;
        } else if (!Mouse.isButtonDown((int)0) && this.wasSliding) {
            for (ValueListener listener : this.vallisteners) {
                listener.valueChanged(this.current);
            }
            dragId = -1;
            this.wasSliding = false;
        }
        int height = posY + 12;
        int i = posX + this.c + 11 + w;
        RenderUtil.drawRect(posX + this.c + 11 + w, posY + 1, posX + w + 61, height, 1086991464);
        RenderUtil.drawRect(posX + w + 8, posY + 1, i, height, -2134234008);
        RenderUtil.drawRect(posX + w + this.c + 8, posY + 1, i, height, -3527576);
        RenderUtil.drawString(this.text, posX + 3, posY + 3, -1);
        String value = this.round == 0 ? Integer.toString(Math.round(this.current)) : Float.toString(MathUtil.round(this.current, this.round));
        RenderUtil.drawString(value, posX + w + 64, posY + 3, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return RenderUtil.getWidth(String.valueOf(this.text) + (this.round == 0 ? Integer.toString(Math.round(this.current)) : Float.toString(MathUtil.round(this.current, this.round)))) + 68;
    }

    @Override
    public int getHeight() {
        return 15;
    }
}

