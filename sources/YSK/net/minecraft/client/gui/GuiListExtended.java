package net.minecraft.client.gui;

import net.minecraft.client.*;

public abstract class GuiListExtended extends GuiSlot
{
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        if (this.isMouseYWithinSlotBounds(n2)) {
            final int slotIndexFromScreenCoords = this.getSlotIndexFromScreenCoords(n, n2);
            if (slotIndexFromScreenCoords >= 0 && this.getListEntry(slotIndexFromScreenCoords).mousePressed(slotIndexFromScreenCoords, n, n2, n3, n - (this.left + this.width / "  ".length() - this.getListWidth() / "  ".length() + "  ".length()), n2 - (this.top + (0xB4 ^ 0xB0) - this.getAmountScrolled() + slotIndexFromScreenCoords * this.slotHeight + this.headerPadding))) {
                this.setEnabled("".length() != 0);
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final IGuiListEntry listEntry = this.getListEntry(n);
        final int listWidth = this.getListWidth();
        int n7;
        if (this.getSlotIndexFromScreenCoords(n5, n6) == n) {
            n7 = " ".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            n7 = "".length();
        }
        listEntry.drawEntry(n, n2, n3, listWidth, n4, n5, n6, n7 != 0);
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return "".length() != 0;
    }
    
    public GuiListExtended(final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(minecraft, n, n2, n3, n4, n5);
    }
    
    public abstract IGuiListEntry getListEntry(final int p0);
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
    }
    
    public boolean mouseReleased(final int n, final int n2, final int n3) {
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.getSize()) {
            this.getListEntry(i).mouseReleased(i, n, n2, n3, n - (this.left + this.width / "  ".length() - this.getListWidth() / "  ".length() + "  ".length()), n2 - (this.top + (0x7A ^ 0x7E) - this.getAmountScrolled() + i * this.slotHeight + this.headerPadding));
            ++i;
        }
        this.setEnabled(" ".length() != 0);
        return "".length() != 0;
    }
    
    @Override
    protected void func_178040_a(final int n, final int n2, final int n3) {
        this.getListEntry(n).setSelected(n, n2, n3);
    }
    
    public interface IGuiListEntry
    {
        boolean mousePressed(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
        
        void setSelected(final int p0, final int p1, final int p2);
        
        void drawEntry(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
        
        void mouseReleased(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    }
}
