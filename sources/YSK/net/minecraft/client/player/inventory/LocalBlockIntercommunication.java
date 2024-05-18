package net.minecraft.client.player.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class LocalBlockIntercommunication implements IInteractionObject
{
    private String guiID;
    private IChatComponent displayName;
    
    @Override
    public IChatComponent getDisplayName() {
        return this.displayName;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException();
    }
    
    public LocalBlockIntercommunication(final String guiID, final IChatComponent displayName) {
        this.guiID = guiID;
        this.displayName = displayName;
    }
    
    @Override
    public String getName() {
        return this.displayName.getUnformattedText();
    }
    
    @Override
    public String getGuiID() {
        return this.guiID;
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
    
    @Override
    public boolean hasCustomName() {
        return " ".length() != 0;
    }
}
