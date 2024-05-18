package net.minecraft.tileentity;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public abstract class TileEntityLockable extends TileEntity implements ILockableContainer, IInteractionObject
{
    private LockCode code;
    
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public TileEntityLockable() {
        this.code = LockCode.EMPTY_CODE;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.code = LockCode.fromNBT(nbtTagCompound);
    }
    
    @Override
    public LockCode getLockCode() {
        return this.code;
    }
    
    @Override
    public boolean isLocked() {
        if (this.code != null && !this.code.isEmpty()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        if (this.code != null) {
            this.code.toNBT(nbtTagCompound);
        }
    }
    
    @Override
    public void setLockCode(final LockCode code) {
        this.code = code;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
}
