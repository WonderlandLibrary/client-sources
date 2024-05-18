package net.minecraft.realms;

import net.minecraft.client.gui.*;

public class RealmsClickableScrolledSelectionList
{
    private final GuiClickableScrolledSelectionListProxy proxy;
    
    public int getItemCount() {
        return "".length();
    }
    
    public int getScroll() {
        return this.proxy.getAmountScrolled();
    }
    
    public void render(final int n, final int n2, final float n3) {
        this.proxy.drawScreen(n, n2, n3);
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RealmsClickableScrolledSelectionList(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.proxy = new GuiClickableScrolledSelectionListProxy(this, n, n2, n3, n4, n5);
    }
    
    public boolean isSelectedItem(final int n) {
        return "".length() != 0;
    }
    
    public int width() {
        return this.proxy.func_178044_e();
    }
    
    protected void renderList(final int n, final int n2, final int n3, final int n4) {
    }
    
    public void customMouseEvent(final int n, final int n2, final int n3, final float n4, final int n5) {
    }
    
    public void renderItem(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.renderItem(n, n2, n3, n4, Tezzelator.instance, n5, n6);
    }
    
    public void renderBackground() {
    }
    
    public void mouseEvent() {
        this.proxy.handleMouseInput();
    }
    
    public void setLeftPos(final int slotXBoundsFromLeft) {
        this.proxy.setSlotXBoundsFromLeft(slotXBoundsFromLeft);
    }
    
    public int ym() {
        return this.proxy.func_178042_f();
    }
    
    public void selectItem(final int n, final boolean b, final int n2, final int n3) {
    }
    
    public int xm() {
        return this.proxy.func_178045_g();
    }
    
    public void itemClicked(final int n, final int n2, final int n3, final int n4, final int n5) {
    }
    
    public int getMaxPosition() {
        return "".length();
    }
    
    protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
    }
    
    public int getScrollbarPosition() {
        return this.proxy.func_178044_e() / "  ".length() + (0xD9 ^ 0xA5);
    }
    
    public void renderSelected(final int n, final int n2, final int n3, final Tezzelator tezzelator) {
    }
    
    public void scroll(final int n) {
        this.proxy.scrollBy(n);
    }
}
