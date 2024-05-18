package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityJumpHelper
{
    protected boolean isJumping;
    private EntityLiving entity;
    
    public EntityJumpHelper(final EntityLiving entity) {
        this.entity = entity;
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void doJump() {
        this.entity.setJumping(this.isJumping);
        this.isJumping = ("".length() != 0);
    }
    
    public void setJumping() {
        this.isJumping = (" ".length() != 0);
    }
}
