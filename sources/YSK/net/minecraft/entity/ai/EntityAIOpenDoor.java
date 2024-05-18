package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
    boolean closeDoor;
    int closeDoorTemporisation;
    
    @Override
    public boolean continueExecuting() {
        if (this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void resetTask() {
        if (this.closeDoor) {
            this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, "".length() != 0);
        }
    }
    
    @Override
    public void updateTask() {
        this.closeDoorTemporisation -= " ".length();
        super.updateTask();
    }
    
    @Override
    public void startExecuting() {
        this.closeDoorTemporisation = (0x97 ^ 0x83);
        this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, " ".length() != 0);
    }
    
    public EntityAIOpenDoor(final EntityLiving theEntity, final boolean closeDoor) {
        super(theEntity);
        this.theEntity = theEntity;
        this.closeDoor = closeDoor;
    }
}
