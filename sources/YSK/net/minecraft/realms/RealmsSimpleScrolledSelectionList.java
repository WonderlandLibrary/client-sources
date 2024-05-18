package net.minecraft.realms;

import net.minecraft.client.gui.*;

public class RealmsSimpleScrolledSelectionList
{
    private final GuiSimpleScrolledSelectionListProxy proxy;
    
    public void scroll(final int n) {
        this.proxy.scrollBy(n);
    }
    
    public void selectItem(final int n, final boolean b, final int n2, final int n3) {
    }
    
    public RealmsSimpleScrolledSelectionList(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.proxy = new GuiSimpleScrolledSelectionListProxy(this, n, n2, n3, n4, n5);
    }
    
    public void renderBackground() {
    }
    
    public boolean isSelectedItem(final int n) {
        return "".length() != 0;
    }
    
    public int getMaxPosition() {
        return "".length();
    }
    
    public void mouseEvent() {
        this.proxy.handleMouseInput();
    }
    
    protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
    }
    
    protected void renderList(final int n, final int n2, final int n3, final int n4) {
    }
    
    public void renderItem(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.renderItem(n, n2, n3, n4, Tezzelator.instance, n5, n6);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int xm() {
        return this.proxy.getMouseX();
    }
    
    public int ym() {
        return this.proxy.getMouseY();
    }
    
    public void render(final int n, final int n2, final float n3) {
        this.proxy.drawScreen(n, n2, n3);
    }
    
    public int getScroll() {
        return this.proxy.getAmountScrolled();
    }
    
    public int getScrollbarPosition() {
        return this.proxy.getWidth() / "  ".length() + (0x13 ^ 0x6F);
    }
    
    public int getItemCount() {
        return "".length();
    }
    
    public int width() {
        return this.proxy.getWidth();
    }
}
