package net.minecraft.entity.monster;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityPigZombie extends EntityZombie
{
    private int angerLevel;
    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID;
    private int randomSoundDelay;
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER;
    private static final String[] I;
    private UUID angerTargetUUID;
    
    static {
        I();
        ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString(EntityPigZombie.I["".length()]);
        ATTACK_SPEED_BOOST_MODIFIER = new AttributeModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER_UUID, EntityPigZombie.I[" ".length()], 0.05, "".length()).setSaved("".length() != 0);
    }
    
    public boolean isAngry() {
        if (this.angerLevel > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityPigZombie.I[0xA0 ^ 0xAA];
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setShort(EntityPigZombie.I["   ".length()], (short)this.angerLevel);
        if (this.angerTargetUUID != null) {
            nbtTagCompound.setString(EntityPigZombie.I[0x64 ^ 0x60], this.angerTargetUUID.toString());
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            nbtTagCompound.setString(EntityPigZombie.I[0x24 ^ 0x21], EntityPigZombie.I[0x9C ^ 0x9A]);
        }
    }
    
    @Override
    protected void applyEntityAI() {
        this.targetTasks.addTask(" ".length(), new AIHurtByAggressor(this));
        this.targetTasks.addTask("  ".length(), new AITargetAggressor(this));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityPlayer) {
            this.becomeAngryAt(entity);
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    private static void I() {
        (I = new String[0x3 ^ 0xF])["".length()] = I("dXf{G\u0011UkcE\u0015\"gcFe#\u0013c0hYdcA\u0012Xb\f@c cyCh", "PaRNr");
        EntityPigZombie.I[" ".length()] = I("\u0012?7\u0006'8\"-\u0000d ;&\u0002 s),\b7'", "SKCgD");
        EntityPigZombie.I["  ".length()] = I(")\u000e\nE\r+\f\n\u0002\u00124\b\u000fE\r4\b\u000f\n\u0019#\u0013\u0011", "Dahkw");
        EntityPigZombie.I["   ".length()] = I("\u0002\t2\u00025", "CgUgG");
        EntityPigZombie.I[0x3B ^ 0x3F] = I("\u001f\u0019\u00186;.", "WljBy");
        EntityPigZombie.I[0x28 ^ 0x2D] = I("0'3$\t\u0001", "xRAPK");
        EntityPigZombie.I[0x26 ^ 0x20] = I("", "XaFBt");
        EntityPigZombie.I[0xA6 ^ 0xA1] = I("\u000f\"\"0\u0018", "NLEUj");
        EntityPigZombie.I[0x6B ^ 0x63] = I("'\u0004\u00168\u001b\u0016", "oqdLY");
        EntityPigZombie.I[0xB9 ^ 0xB0] = I("8\u0005\u0001L\u000e:\u0007\u0001\u000b\u0011%\u0003\u0004L\u000e%\u0003\u0004", "Ujcbt");
        EntityPigZombie.I[0xA0 ^ 0xAA] = I("9+'m4;)'*+$-\"m4$-\"+;&0", "TDECN");
        EntityPigZombie.I[0x9 ^ 0x2] = I("&\u0004+c#$\u0006+$<;\u0002.c#;\u0002.)<*\u001f!", "KkIMY");
    }
    
    private void becomeAngryAt(final Entity entity) {
        this.angerLevel = 340 + 361 - 599 + 298 + this.rand.nextInt(300 + 127 - 203 + 176);
        this.randomSoundDelay = this.rand.nextInt(0x42 ^ 0x6A);
        if (entity instanceof EntityLivingBase) {
            this.setRevengeTarget((EntityLivingBase)entity);
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase revengeTarget) {
        super.setRevengeTarget(revengeTarget);
        if (revengeTarget != null) {
            this.angerTargetUUID = revengeTarget.getUniqueID();
        }
    }
    
    static void access$0(final EntityPigZombie entityPigZombie, final Entity entity) {
        entityPigZombie.becomeAngryAt(entity);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int nextInt = this.rand.nextInt("  ".length() + n);
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < nextInt) {
            this.dropItem(Items.rotten_flesh, " ".length());
            ++i;
        }
        final int nextInt2 = this.rand.nextInt("  ".length() + n);
        int j = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (j < nextInt2) {
            this.dropItem(Items.gold_nugget, " ".length());
            ++j;
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(EntityPigZombie.reinforcementChance).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }
    
    @Override
    protected String getLivingSound() {
        return EntityPigZombie.I[0x13 ^ 0x1A];
    }
    
    @Override
    protected void addRandomDrop() {
        this.dropItem(Items.gold_ingot, " ".length());
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficultyInstance) {
        this.setCurrentItemOrArmor("".length(), new ItemStack(Items.golden_sword));
    }
    
    @Override
    public boolean isNotColliding() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        super.onInitialSpawn(difficultyInstance, entityLivingData);
        this.setVillager("".length() != 0);
        return entityLivingData;
    }
    
    @Override
    protected void updateAITasks() {
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (this.isAngry()) {
            if (!this.isChild() && !entityAttribute.hasModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
                entityAttribute.applyModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
            }
            this.angerLevel -= " ".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (entityAttribute.hasModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
            entityAttribute.removeModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
        }
        if (this.randomSoundDelay > 0 && (this.randomSoundDelay -= " ".length()) == 0) {
            this.playSound(EntityPigZombie.I["  ".length()], this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getAITarget() == null) {
            final EntityPlayer playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(playerEntityByUUID);
            this.attackingPlayer = playerEntityByUUID;
            this.recentlyHit = this.getRevengeTimer();
        }
        super.updateAITasks();
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        return "".length() != 0;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.angerLevel = nbtTagCompound.getShort(EntityPigZombie.I[0x90 ^ 0x97]);
        final String string = nbtTagCompound.getString(EntityPigZombie.I[0x1 ^ 0x9]);
        if (string.length() > 0) {
            this.angerTargetUUID = UUID.fromString(string);
            final EntityPlayer playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(playerEntityByUUID);
            if (playerEntityByUUID != null) {
                this.attackingPlayer = playerEntityByUUID;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }
    
    public EntityPigZombie(final World world) {
        super(world);
        this.isImmuneToFire = (" ".length() != 0);
    }
    
    @Override
    protected String getDeathSound() {
        return EntityPigZombie.I[0x2 ^ 0x9];
    }
    
    static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        @Override
        public boolean shouldExecute() {
            if (((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute()) {
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
                if (1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public AITargetAggressor(final EntityPigZombie entityPigZombie) {
            super(entityPigZombie, EntityPlayer.class, " ".length() != 0);
        }
    }
    
    static class AIHurtByAggressor extends EntityAIHurtByTarget
    {
        @Override
        protected void setEntityAttackTarget(final EntityCreature entityCreature, final EntityLivingBase entityLivingBase) {
            super.setEntityAttackTarget(entityCreature, entityLivingBase);
            if (entityCreature instanceof EntityPigZombie) {
                EntityPigZombie.access$0((EntityPigZombie)entityCreature, entityLivingBase);
            }
        }
        
        public AIHurtByAggressor(final EntityPigZombie entityPigZombie) {
            super(entityPigZombie, " ".length() != 0, new Class["".length()]);
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
                if (false) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
