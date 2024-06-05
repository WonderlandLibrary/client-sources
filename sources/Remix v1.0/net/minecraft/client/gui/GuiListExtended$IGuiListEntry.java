package net.minecraft.client.gui;

public interface IGuiListEntry
{
    void setSelected(final int p0, final int p1, final int p2);
    
    void drawEntry(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
    
    boolean mousePressed(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void mouseReleased(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
}
