package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;

public class ServerSelectionList extends GuiListExtended
{
    private final List<ServerListEntryLanDetected> field_148199_m;
    private final GuiMultiplayer owner;
    private final List<ServerListEntryNormal> field_148198_l;
    private final IGuiListEntry lanScanEntry;
    private int selectedSlotIndex;
    
    @Override
    public int getListWidth() {
        return super.getListWidth() + (0xFD ^ 0xA8);
    }
    
    public void func_148194_a(final List<LanServerDetector.LanServer> list) {
        this.field_148199_m.clear();
        final Iterator<LanServerDetector.LanServer> iterator = list.iterator();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.field_148199_m.add(new ServerListEntryLanDetected(this.owner, iterator.next()));
        }
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + (0x63 ^ 0x7D);
    }
    
    @Override
    protected boolean isSelected(final int n) {
        if (n == this.selectedSlotIndex) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ServerSelectionList(final GuiMultiplayer owner, final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_148198_l = (List<ServerListEntryNormal>)Lists.newArrayList();
        this.field_148199_m = (List<ServerListEntryLanDetected>)Lists.newArrayList();
        this.lanScanEntry = new ServerListEntryLanScan();
        this.selectedSlotIndex = -" ".length();
        this.owner = owner;
    }
    
    public void setSelectedSlotIndex(final int selectedSlotIndex) {
        this.selectedSlotIndex = selectedSlotIndex;
    }
    
    public int func_148193_k() {
        return this.selectedSlotIndex;
    }
    
    @Override
    protected int getSize() {
        return this.field_148198_l.size() + " ".length() + this.field_148199_m.size();
    }
    
    @Override
    public IGuiListEntry getListEntry(int n) {
        if (n < this.field_148198_l.size()) {
            return this.field_148198_l.get(n);
        }
        n -= this.field_148198_l.size();
        if (n == 0) {
            return this.lanScanEntry;
        }
        --n;
        return this.field_148199_m.get(n);
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void func_148195_a(final ServerList list) {
        this.field_148198_l.clear();
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < list.countServers()) {
            this.field_148198_l.add(new ServerListEntryNormal(this.owner, list.getServerData(i)));
            ++i;
        }
    }
}
