package net.minecraft.client.gui;

import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public abstract class GuiResourcePackList extends GuiListExtended
{
    protected final List<ResourcePackListEntry> field_148204_l;
    protected final Minecraft mc;
    
    public List<ResourcePackListEntry> getList() {
        return this.field_148204_l;
    }
    
    @Override
    protected int getSize() {
        return this.getList().size();
    }
    
    @Override
    protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
        final String string = new StringBuilder().append(EnumChatFormatting.UNDERLINE).append(EnumChatFormatting.BOLD).append(this.getListHeader()).toString();
        this.mc.fontRendererObj.drawString(string, n + this.width / "  ".length() - this.mc.fontRendererObj.getStringWidth(string) / "  ".length(), Math.min(this.top + "   ".length(), n2), 6095566 + 286536 + 2086795 + 8308318);
    }
    
    public GuiResourcePackList(final Minecraft mc, final int n, final int n2, final List<ResourcePackListEntry> field_148204_l) {
        super(mc, n, n2, 0xB8 ^ 0x98, n2 - (0x35 ^ 0x2) + (0x8 ^ 0xC), 0x44 ^ 0x60);
        this.mc = mc;
        this.field_148204_l = field_148204_l;
        this.field_148163_i = ("".length() != 0);
        this.setHasListHeader(" ".length() != 0, (int)(mc.fontRendererObj.FONT_HEIGHT * 1.5f));
    }
    
    @Override
    public ResourcePackListEntry getListEntry(final int n) {
        return this.getList().get(n);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.right - (0x83 ^ 0x85);
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.getListEntry(n);
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getListWidth() {
        return this.width;
    }
    
    protected abstract String getListHeader();
}
