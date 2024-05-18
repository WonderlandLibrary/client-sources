package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;

public abstract class EntityMob extends EntityCreature implements IMob
{
    private static final String[] I;
    
    @Override
    protected String getSplashSound() {
        return EntityMob.I[" ".length()];
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    public EntityMob(final World world) {
        super(world);
        this.experienceValue = (0xF ^ 0xA);
    }
    
    @Override
    protected String getHurtSound() {
        return EntityMob.I["  ".length()];
    }
    
    private static void I() {
        (I = new String[0x27 ^ 0x21])["".length()] = I("%2?1o*<! (.6|'6+>", "BSRTA");
        EntityMob.I[" ".length()] = I("\u0015\u00174'|\u001a\u0019*6;\u001e\u0013w1%\u001b\u001bw1\"\u001e\u0017**", "rvYBR");
        EntityMob.I["  ".length()] = I("\u0001\u0007\u001e$\\\u000e\t\u00005\u001b\n\u0003])\u0007\u0014\u0012", "ffsAr");
        EntityMob.I["   ".length()] = I("(\u0010>?e'\u001e .\"#\u0014}>\"*", "OqSZK");
        EntityMob.I[0xB2 ^ 0xB6] = I("#\u000f#,z,\u0001===(\u000b`!!6\u001a`/5(\u0002`+=#", "DnNIT");
        EntityMob.I[0x46 ^ 0x43] = I("\u0006\u0011\u0007/A\t\u001f\u0019>\u0006\r\u0015D\"\u001a\u0013\u0004D,\u000e\r\u001cD9\u0002\u0000\u001c\u0006", "apjJo");
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        if (this.getBrightness(1.0f) > 0.5f) {
            this.entityAge += "  ".length();
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected String getFallSoundString(final int n) {
        String s;
        if (n > (0x6 ^ 0x2)) {
            s = EntityMob.I[0x34 ^ 0x30];
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            s = EntityMob.I[0x53 ^ 0x56];
        }
        return s;
    }
    
    @Override
    protected boolean canDropLoot() {
        return " ".length() != 0;
    }
    
    protected boolean isValidLightLevel() {
        final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, blockPos) > this.rand.nextInt(0x44 ^ 0x64)) {
            return "".length() != 0;
        }
        int n = this.worldObj.getLightFromNeighbors(blockPos);
        if (this.worldObj.isThundering()) {
            final int skylightSubtracted = this.worldObj.getSkylightSubtracted();
            this.worldObj.setSkylightSubtracted(0x20 ^ 0x2A);
            n = this.worldObj.getLightFromNeighbors(blockPos);
            this.worldObj.setSkylightSubtracted(skylightSubtracted);
        }
        if (n <= this.rand.nextInt(0x88 ^ 0x80)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        return 0.5f - this.worldObj.getLightBrightness(blockPos);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        float n = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int length = "".length();
        if (entity instanceof EntityLivingBase) {
            n += EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)entity).getCreatureAttribute());
            length += EnchantmentHelper.getKnockbackModifier(this);
        }
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), n);
        if (attackEntity) {
            if (length > 0) {
                entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * length * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * length * 0.5f);
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }
            final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
            if (fireAspectModifier > 0) {
                entity.setFire(fireAspectModifier * (0xB ^ 0xF));
            }
            this.applyEnchantments(this, entity);
        }
        return attackEntity;
    }
    
    static {
        I();
    }
    
    @Override
    protected String getSwimSound() {
        return EntityMob.I["".length()];
    }
    
    @Override
    protected String getDeathSound() {
        return EntityMob.I["   ".length()];
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (super.attackEntityFrom(damageSource, n)) {
            final Entity entity = damageSource.getEntity();
            int n2;
            if (this.riddenByEntity != entity && this.ridingEntity != entity) {
                n2 = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n2 = " ".length();
            }
            return n2 != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }
}
