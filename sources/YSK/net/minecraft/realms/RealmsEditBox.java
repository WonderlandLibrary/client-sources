package net.minecraft.realms;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class RealmsEditBox
{
    private final GuiTextField editBox;
    
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setFocus(final boolean focused) {
        this.editBox.setFocused(focused);
    }
    
    public void setMaxLength(final int maxStringLength) {
        this.editBox.setMaxStringLength(maxStringLength);
    }
    
    public RealmsEditBox(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.editBox = new GuiTextField(n, Minecraft.getMinecraft().fontRendererObj, n2, n3, n4, n5);
    }
    
    public String getValue() {
        return this.editBox.getText();
    }
    
    public void setValue(final String text) {
        this.editBox.setText(text);
    }
    
    public void render() {
        this.editBox.drawTextBox();
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        this.editBox.mouseClicked(n, n2, n3);
    }
    
    public void setIsEditable(final boolean enabled) {
        this.editBox.setEnabled(enabled);
    }
    
    public boolean isFocused() {
        return this.editBox.isFocused();
    }
    
    public void tick() {
        this.editBox.updateCursorCounter();
    }
    
    public void keyPressed(final char c, final int n) {
        this.editBox.textboxKeyTyped(c, n);
    }
}
