package com.client.glowclient;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class yc extends GuiButton
{
    private int H;
    private final GuiButton f;
    private boolean M;
    private int G;
    private final GuiTextField d;
    private static final int L = 12;
    private String A;
    private static final int B = 0;
    private final GuiButton b;
    
    public int getValue() {
        final String text;
        if ((text = this.d.getText()).length() == 0 || text.equals("-")) {
            return 0;
        }
        return Integer.parseInt(text);
    }
    
    public void setPosition(final int n, final int n2) {
        this.d.x = n + 1;
        this.d.y = n2 + 1;
        this.f.x = n + this.width - 24;
        this.f.y = n2;
        this.b.x = n + this.width - 12;
        this.b.y = n2;
    }
    
    public void drawButton(final Minecraft minecraft, final int n, final int n2, final float n3) {
        if (this.visible) {
            this.d.drawTextBox();
            this.f.drawButton(minecraft, n, n2, n3);
            this.b.drawButton(minecraft, n, n2, n3);
        }
    }
    
    public void setMaximum(final int h) {
        this.H = h;
    }
    
    public void setMinimum(final int g) {
        this.G = g;
    }
    
    public int getMinimum() {
        return this.G;
    }
    
    public yc(final FontRenderer fontRenderer, final int n, final int n2, final int n3, final int n4, final int n5) {
        final int value = 0;
        final boolean m = false;
        final int h = 0;
        final int g = Integer.MIN_VALUE;
        final int n6 = 0;
        super(n, n6, 0, n4, n5, "");
        this.A = String.valueOf(n6);
        this.G = g;
        this.H = h;
        this.M = m;
        this.d = new GuiTextField(0, fontRenderer, n2 + 1, n3 + 1, n4 - 24 - 2, n5 - 2);
        this.b = new GuiButton(1, n2 + n4 - 24, n3, 12, n5, "-");
        this.f = new GuiButton(2, n2 + n4 - 12, n3, 12, n5, "+");
        this.setValue(value);
    }
    
    public void updateCursorCounter() {
        this.d.updateCursorCounter();
    }
    
    public void setEnabled(final boolean b) {
        this.enabled = b;
        this.d.setEnabled(b);
        this.f.enabled = b;
        this.b.enabled = b;
    }
    
    public yc(final FontRenderer fontRenderer, final int n, final int n2, final int n3, final int n4) {
        this(fontRenderer, n, n2, n3, n4, 20);
    }
    
    public int getMaximum() {
        return this.H;
    }
    
    public void setValue(int n) {
        yc yc;
        if (n > this.H) {
            yc = this;
            n = this.H;
        }
        else {
            if (n < this.G) {
                n = this.G;
            }
            yc = this;
        }
        yc.d.setText(String.valueOf(n));
    }
    
    public boolean isFocused() {
        return this.d.isFocused();
    }
    
    public boolean keyTyped(final char c, int cursorPosition) {
        if (!this.d.isFocused()) {
            return false;
        }
        final int cursorPosition2 = this.d.getCursorPosition();
        this.d.textboxKeyTyped(c, cursorPosition);
        final String text = this.d.getText();
        cursorPosition = this.d.getCursorPosition();
        if (text.length() == 0 || text.equals("-")) {
            return true;
        }
        try {
            long long1 = Long.parseLong(text);
            boolean b = false;
            long n;
            if (long1 > this.H) {
                long1 = this.H;
                b = true;
                n = long1;
            }
            else {
                if (long1 < this.G) {
                    long1 = this.G;
                    b = true;
                }
                n = long1;
            }
            final String value;
            if (!(value = String.valueOf(n)).equals(this.A) || b) {
                this.d.setText(String.valueOf(long1));
                this.d.setCursorPosition(cursorPosition);
            }
            this.A = value;
            return true;
        }
        catch (NumberFormatException ex) {
            final boolean b2 = false;
            this.d.setText(this.A);
            this.d.setCursorPosition(cursorPosition2);
            return b2;
        }
    }
    
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (this.M && !this.d.isFocused()) {
            final boolean b = true;
            this.M = false;
            return b;
        }
        this.M = this.d.isFocused();
        return this.b.mousePressed(minecraft, n, n2) || this.f.mousePressed(minecraft, n, n2);
    }
    
    public yc(final FontRenderer fontRenderer, final int n, final int n2, final int n3) {
        this(fontRenderer, n, n2, n3, 100, 20);
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        this.d.mouseClicked(n, n2, n3);
        if (this.f.mousePressed(minecraft, n, n2)) {
            this.setValue(this.getValue() + 1);
        }
        if (this.b.mousePressed(minecraft, n, n2)) {
            this.setValue(this.getValue() - 1);
        }
    }
}
