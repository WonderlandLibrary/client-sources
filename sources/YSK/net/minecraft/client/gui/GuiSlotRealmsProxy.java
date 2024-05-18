package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class GuiSlotRealmsProxy extends GuiSlot
{
    private final RealmsScrolledSelectionList selectionList;
    
    public int func_154339_l() {
        return super.mouseY;
    }
    
    public int func_154337_m() {
        return super.mouseX;
    }
    
    @Override
    protected void drawBackground() {
        this.selectionList.renderBackground();
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.selectionList.isSelectedItem(n);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.selectionList.getScrollbarPosition();
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
    
    public int func_154338_k() {
        return super.width;
    }
    
    @Override
    protected int getSize() {
        return this.selectionList.getItemCount();
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.selectionList.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected int getContentHeight() {
        return this.selectionList.getMaxPosition();
    }
    
    public GuiSlotRealmsProxy(final RealmsScrolledSelectionList selectionList, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.selectionList = selectionList;
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.selectionList.selectItem(n, b, n2, n3);
    }
}
