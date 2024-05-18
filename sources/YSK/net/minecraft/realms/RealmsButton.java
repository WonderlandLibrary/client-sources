package net.minecraft.realms;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class RealmsButton
{
    private GuiButtonRealmsProxy proxy;
    protected static final ResourceLocation WIDGETS_LOCATION;
    private static final String[] I;
    
    public void render(final int n, final int n2) {
        this.proxy.drawButton(Minecraft.getMinecraft(), n, n2);
    }
    
    public void blit(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawTexturedModalRect(n, n2, n3, n4, n5, n6);
    }
    
    public int getYImage(final boolean b) {
        return this.proxy.func_154312_c(b);
    }
    
    static {
        I();
        WIDGETS_LOCATION = new ResourceLocation(RealmsButton.I["".length()]);
    }
    
    public int getWidth() {
        return this.proxy.getButtonWidth();
    }
    
    public RealmsButton(final int n, final int n2, final int n3, final String s) {
        this.proxy = new GuiButtonRealmsProxy(this, n, n2, n3, s);
    }
    
    public boolean active() {
        return this.proxy.getEnabled();
    }
    
    public GuiButton getProxy() {
        return this.proxy;
    }
    
    public RealmsButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        this.proxy = new GuiButtonRealmsProxy(this, n, n2, n3, s, n4, n5);
    }
    
    public void renderBg(final int n, final int n2) {
    }
    
    public int getHeight() {
        return this.proxy.func_175232_g();
    }
    
    public void msg(final String text) {
        this.proxy.setText(text);
    }
    
    public void active(final boolean enabled) {
        this.proxy.setEnabled(enabled);
    }
    
    public void released(final int n, final int n2) {
    }
    
    public int y() {
        return this.proxy.getPositionY();
    }
    
    public void clicked(final int n, final int n2) {
    }
    
    public int id() {
        return this.proxy.getId();
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0015=<\u0005 \u0013=7^2\u00141k\u0006<\u0005?!\u0005&O(*\u0016", "aXDqU");
    }
}
