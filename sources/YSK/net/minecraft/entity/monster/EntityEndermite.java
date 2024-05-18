package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class EntityEndermite extends EntityMob
{
    private int lifetime;
    private boolean playerSpawned;
    private static final String[] I;
    
    public EntityEndermite(final World world) {
        super(world);
        this.lifetime = "".length();
        this.playerSpawned = ("".length() != 0);
        this.experienceValue = "   ".length();
        this.setSize(0.4f, 0.3f);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, (boolean)("".length() != 0)));
        this.tasks.addTask("   ".length(), new EntityAIWander(this, 1.0));
        this.tasks.addTask(0xAB ^ 0xAC, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0xBD ^ 0xB5, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)(" ".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
    }
    
    @Override
    protected String getLivingSound() {
        return EntityEndermite.I["".length()];
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return " ".length() != 0;
    }
    
    public boolean isSpawnedByPlayer() {
        return this.playerSpawned;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityEndermite.I[0x44 ^ 0x42], this.lifetime);
        nbtTagCompound.setBoolean(EntityEndermite.I[0xAF ^ 0xA8], this.playerSpawned);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityEndermite.I["   ".length()], 0.15f, 1.0f);
    }
    
    static {
        I();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }
    
    @Override
    protected String getHurtSound() {
        return EntityEndermite.I[" ".length()];
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.lifetime = nbtTagCompound.getInteger(EntityEndermite.I[0xBF ^ 0xBB]);
        this.playerSpawned = nbtTagCompound.getBoolean(EntityEndermite.I[0x1A ^ 0x1F]);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (!super.getCanSpawnHere()) {
            return "".length() != 0;
        }
        if (this.worldObj.getClosestPlayerToEntity(this, 5.0) == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    private static void I() {
        (I = new String[0x4 ^ 0xC])["".length()] = I("9\u00051H2=\u0006%\u000332\u0003 \u000eo'\u000b*", "TjSfA");
        EntityEndermite.I[" ".length()] = I("\u0003)\u0013z>\u0007*\u00071?\b/\u0002<c\u0006/\u0005", "nFqTM");
        EntityEndermite.I["  ".length()] = I("\u0000\u0004\u0014^\u0017\u0004\u0007\u0000\u0015\u0016\u000b\u0002\u0005\u0018J\u0006\u0002\u001a\u001c", "mkvpd");
        EntityEndermite.I["   ".length()] = I("\"\u001d4^8&\u001e \u00159)\u001b%\u0018e<\u00063\u0000", "OrVpK");
        EntityEndermite.I[0x4D ^ 0x49] = I("\u000b,\u0004\"\u0012.(\u0007", "GEbGf");
        EntityEndermite.I[0x75 ^ 0x70] = I("\u0019\u0001\u000f5\t;>\u001e-\u001b'\b\n", "ImnLl");
        EntityEndermite.I[0xC3 ^ 0xC5] = I("\u001a.\u0016*!?*\u0015", "VGpOU");
        EntityEndermite.I[0xA9 ^ 0xAE] = I("\u0016\u0015\u0016=\u00174*\u0007%\u0005(\u001c\u0013", "FywDr");
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public void setSpawnedByPlayer(final boolean playerSpawned) {
        this.playerSpawned = playerSpawned;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < "  ".length()) {
                this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0, new int["".length()]);
                ++i;
            }
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            if (!this.isNoDespawnRequired()) {
                this.lifetime += " ".length();
            }
            if (this.lifetime >= 2319 + 609 - 2640 + 2112) {
                this.setDead();
            }
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 0.1f;
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    protected String getDeathSound() {
        return EntityEndermite.I["  ".length()];
    }
}
