package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class EntityAIMate extends EntityAIBase
{
    double moveSpeed;
    private EntityAnimal targetMate;
    private EntityAnimal theAnimal;
    int spawnBabyDelay;
    private static final String[] I;
    World theWorld;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(".\u001d\u00017\u0012\u0006\u001d#,", "JrLXp");
    }
    
    static {
        I();
    }
    
    @Override
    public void updateTask() {
        this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0f, this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        this.spawnBabyDelay += " ".length();
        if (this.spawnBabyDelay >= (0x4D ^ 0x71) && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0) {
            this.spawnBaby();
        }
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void spawnBaby() {
        final EntityAgeable child = this.theAnimal.createChild(this.targetMate);
        if (child != null) {
            EntityPlayer entityPlayer = this.theAnimal.getPlayerInLove();
            if (entityPlayer == null && this.targetMate.getPlayerInLove() != null) {
                entityPlayer = this.targetMate.getPlayerInLove();
            }
            if (entityPlayer != null) {
                entityPlayer.triggerAchievement(StatList.animalsBredStat);
                if (this.theAnimal instanceof EntityCow) {
                    entityPlayer.triggerAchievement(AchievementList.breedCow);
                }
            }
            this.theAnimal.setGrowingAge(252 + 3993 - 2479 + 4234);
            this.targetMate.setGrowingAge(1475 + 4853 - 6065 + 5737);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            child.setGrowingAge(-(9500 + 23920 - 16487 + 7067));
            child.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0f, 0.0f);
            this.theWorld.spawnEntityInWorld(child);
            final Random rng = this.theAnimal.getRNG();
            int i = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (i < (0x3E ^ 0x39)) {
                this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + (rng.nextDouble() * this.theAnimal.width * 2.0 - this.theAnimal.width), this.theAnimal.posY + (0.5 + rng.nextDouble() * this.theAnimal.height), this.theAnimal.posZ + (rng.nextDouble() * this.theAnimal.width * 2.0 - this.theAnimal.width), rng.nextGaussian() * 0.02, rng.nextGaussian() * 0.02, rng.nextGaussian() * 0.02, new int["".length()]);
                ++i;
            }
            if (this.theWorld.getGameRules().getBoolean(EntityAIMate.I["".length()])) {
                this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, rng.nextInt(0xBC ^ 0xBB) + " ".length()));
            }
        }
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < (0xBA ^ 0x86)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private EntityAnimal getNearbyMate() {
        final float n = 8.0f;
        final List<Entity> entitiesWithinAABB = this.theWorld.getEntitiesWithinAABB((Class<? extends Entity>)this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand(n, n, n));
        double distanceSqToEntity = Double.MAX_VALUE;
        EntityAnimal entityAnimal = null;
        final Iterator<EntityAnimal> iterator = entitiesWithinAABB.iterator();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityAnimal entityAnimal2 = iterator.next();
            if (this.theAnimal.canMateWith(entityAnimal2) && this.theAnimal.getDistanceSqToEntity(entityAnimal2) < distanceSqToEntity) {
                entityAnimal = entityAnimal2;
                distanceSqToEntity = this.theAnimal.getDistanceSqToEntity(entityAnimal2);
            }
        }
        return entityAnimal;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theAnimal.isInLove()) {
            return "".length() != 0;
        }
        this.targetMate = this.getNearbyMate();
        if (this.targetMate != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityAIMate(final EntityAnimal theAnimal, final double moveSpeed) {
        this.theAnimal = theAnimal;
        this.theWorld = theAnimal.worldObj;
        this.moveSpeed = moveSpeed;
        this.setMutexBits("   ".length());
    }
    
    @Override
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = "".length();
    }
}
