package net.minecraft.client.player.inventory;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer
{
    private Map<Integer, Integer> field_174895_b;
    private String guiID;
    
    @Override
    public boolean isLocked() {
        return "".length() != 0;
    }
    
    @Override
    public int getField(final int n) {
        int n2;
        if (this.field_174895_b.containsKey(n)) {
            n2 = this.field_174895_b.get(n);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2;
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
    }
    
    @Override
    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException();
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setField(final int n, final int n2) {
        this.field_174895_b.put(n, n2);
    }
    
    public ContainerLocalMenu(final String guiID, final IChatComponent chatComponent, final int n) {
        super(chatComponent, n);
        this.field_174895_b = (Map<Integer, Integer>)Maps.newHashMap();
        this.guiID = guiID;
    }
    
    @Override
    public int getFieldCount() {
        return this.field_174895_b.size();
    }
    
    @Override
    public String getGuiID() {
        return this.guiID;
    }
}
