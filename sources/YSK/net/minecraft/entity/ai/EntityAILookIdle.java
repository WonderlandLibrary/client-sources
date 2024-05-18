package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAILookIdle extends EntityAIBase
{
    private double lookX;
    private double lookZ;
    private int idleTime;
    private EntityLiving idleEntity;
    
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateTask() {
        this.idleTime -= " ".length();
        this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0f, this.idleEntity.getVerticalFaceSpeed());
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.idleTime >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.idleEntity.getRNG().nextFloat() < 0.02f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityAILookIdle(final EntityLiving idleEntity) {
        this.idleEntity = idleEntity;
        this.setMutexBits("   ".length());
    }
    
    @Override
    public void startExecuting() {
        final double n = 6.283185307179586 * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(n);
        this.lookZ = Math.sin(n);
        this.idleTime = (0xB6 ^ 0xA2) + this.idleEntity.getRNG().nextInt(0x62 ^ 0x76);
    }
}
