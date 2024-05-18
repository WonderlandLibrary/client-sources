package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class GuiButtonRealmsProxy extends GuiButton
{
    private RealmsButton realmsButton;
    
    public void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        this.realmsButton.renderBg(n, n2);
    }
    
    public GuiButtonRealmsProxy(final RealmsButton realmsButton, final int n, final int n2, final int n3, final String s, final int n4, final int n5) {
        super(n, n2, n3, n4, n5, s);
        this.realmsButton = realmsButton;
    }
    
    @Override
    public int getButtonWidth() {
        return super.getButtonWidth();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int func_154312_c(final boolean b) {
        return super.getHoverState(b);
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.realmsButton.clicked(n, n2);
        }
        return super.mousePressed(minecraft, n, n2);
    }
    
    public void setText(final String displayString) {
        super.displayString = displayString;
    }
    
    public int getHoverState(final boolean b) {
        return this.realmsButton.getYImage(b);
    }
    
    public RealmsButton getRealmsButton() {
        return this.realmsButton;
    }
    
    public int func_175232_g() {
        return this.height;
    }
    
    public int getId() {
        return super.id;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.realmsButton.released(n, n2);
    }
    
    public GuiButtonRealmsProxy(final RealmsButton realmsButton, final int n, final int n2, final int n3, final String s) {
        super(n, n2, n3, s);
        this.realmsButton = realmsButton;
    }
    
    public int getPositionY() {
        return super.yPosition;
    }
    
    public boolean getEnabled() {
        return super.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        super.enabled = enabled;
    }
}
