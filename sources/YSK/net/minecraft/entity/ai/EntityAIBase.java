package net.minecraft.entity.ai;

public abstract class EntityAIBase
{
    private int mutexBits;
    
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void startExecuting() {
    }
    
    public abstract boolean shouldExecute();
    
    public boolean isInterruptible() {
        return " ".length() != 0;
    }
    
    public void resetTask() {
    }
    
    public int getMutexBits() {
        return this.mutexBits;
    }
    
    public boolean continueExecuting() {
        return this.shouldExecute();
    }
    
    public void updateTask() {
    }
    
    public void setMutexBits(final int mutexBits) {
        this.mutexBits = mutexBits;
    }
}
