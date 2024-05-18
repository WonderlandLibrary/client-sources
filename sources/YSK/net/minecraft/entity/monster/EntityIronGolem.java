package net.minecraft.entity.monster;

import net.minecraft.village.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.ai.*;

public class EntityIronGolem extends EntityGolem
{
    private static final String[] I;
    private int homeCheckTimer;
    private int holdRoseTick;
    Village villageObj;
    private int attackTimer;
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityIronGolem.I["".length()], this.isPlayerCreated());
    }
    
    @Override
    protected String getHurtSound() {
        return EntityIronGolem.I[0xB9 ^ 0xBD];
    }
    
    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }
    
    private static void I() {
        (I = new String[0x4C ^ 0x4B])["".length()] = I("##\b>\u000e\u0001\f\u001b\"\n\u0007*\r", "sOiGk");
        EntityIronGolem.I[" ".length()] = I("\u0013'\u0010\n\n1\b\u0003\u0016\u000e7.\u0015", "CKqso");
        EntityIronGolem.I["  ".length()] = I(">\u00024}8!\u000284>?\b;}%;\u001f9$", "SmVSQ");
        EntityIronGolem.I["   ".length()] = I("\u0005'\u0007o9\u001a'\u000b&?\u0004-\bo$\u0000:\n6", "hHeAP");
        EntityIronGolem.I[0x4 ^ 0x0] = I("\u0019\n\u0006C\u0000\u0006\n\n\n\u0006\u0018\u0000\tC\u0001\u001d\u0011", "tedmi");
        EntityIronGolem.I[0xB8 ^ 0xBD] = I(")\u0006\u0004j$6\u0006\b#\"(\f\u000bj)!\b\u0012,", "DifDM");
        EntityIronGolem.I[0xD ^ 0xB] = I("\u0007 4^!\u0018 8\u0017'\u0006*;^?\u000b#=", "jOVpH");
    }
    
    public EntityIronGolem(final World world) {
        super(world);
        this.setSize(1.4f, 2.9f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask(" ".length(), new EntityAIAttackOnCollide(this, 1.0, (boolean)(" ".length() != 0)));
        this.tasks.addTask("  ".length(), new EntityAIMoveTowardsTarget(this, 0.9, 32.0f));
        this.tasks.addTask("   ".length(), new EntityAIMoveThroughVillage(this, 0.6, (boolean)(" ".length() != 0)));
        this.tasks.addTask(0x48 ^ 0x4C, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(0xB7 ^ 0xB2, new EntityAILookAtVillager(this));
        this.tasks.addTask(0x81 ^ 0x87, new EntityAIWander(this, 0.6));
        this.tasks.addTask(0x66 ^ 0x61, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0x23 ^ 0x2B, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIDefendVillage(this));
        this.targetTasks.addTask("  ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("   ".length(), new AINearestAttackableTargetNonCreeper<Object>(this, EntityLiving.class, 0x18 ^ 0x12, (boolean)("".length() != 0), (boolean)(" ".length() != 0), IMob.VISIBLE_MOB_SELECTOR));
    }
    
    @Override
    protected String getDeathSound() {
        return EntityIronGolem.I[0x8F ^ 0x8A];
    }
    
    static {
        I();
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int nextInt = this.rand.nextInt("   ".length());
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < nextInt) {
            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.red_flower), " ".length(), BlockFlower.EnumFlowerType.POPPY.getMeta());
            ++i;
        }
        final int n2 = "   ".length() + this.rand.nextInt("   ".length());
        int j = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (j < n2) {
            this.dropItem(Items.iron_ingot, " ".length());
            ++j;
        }
    }
    
    public Village getVillage() {
        return this.villageObj;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        this.attackTimer = (0x4B ^ 0x41);
        this.worldObj.setEntityState(this, (byte)(0x22 ^ 0x26));
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (0x45 ^ 0x42) + this.rand.nextInt(0x4C ^ 0x43));
        if (attackEntity) {
            entity.motionY += 0.4000000059604645;
            this.applyEnchantments(this, entity);
        }
        this.playSound(EntityIronGolem.I["  ".length()], 1.0f, 1.0f);
        return attackEntity;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    protected int decreaseAirSupply(final int n) {
        return n;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setPlayerCreated(nbtTagCompound.getBoolean(EntityIronGolem.I[" ".length()]));
    }
    
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityIronGolem.I[0x8E ^ 0x88], 1.0f, 1.0f);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x7C ^ 0x78)) {
            this.attackTimer = (0x8 ^ 0x2);
            this.playSound(EntityIronGolem.I["   ".length()], 1.0f, 1.0f);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (b == (0xAC ^ 0xA7)) {
            this.holdRoseTick = 228 + 161 - 235 + 246;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public boolean isPlayerCreated() {
        if ((this.dataWatcher.getWatchableObjectByte(0xA0 ^ 0xB0) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void updateAITasks() {
        final int homeCheckTimer = this.homeCheckTimer - " ".length();
        this.homeCheckTimer = homeCheckTimer;
        if (homeCheckTimer <= 0) {
            this.homeCheckTimer = (0xEE ^ 0xA8) + this.rand.nextInt(0x9C ^ 0xAE);
            this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this), 0xE4 ^ 0xC4);
            if (this.villageObj == null) {
                this.detachHome();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                this.setHomePosAndDistance(this.villageObj.getCenter(), (int)(this.villageObj.getVillageRadius() * 0.6f));
            }
        }
        super.updateAITasks();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            this.attackTimer -= " ".length();
        }
        if (this.holdRoseTick > 0) {
            this.holdRoseTick -= " ".length();
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7 && this.rand.nextInt(0x20 ^ 0x25) == 0) {
            final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ)));
            if (blockState.getBlock().getMaterial() != Material.air) {
                final World worldObj = this.worldObj;
                final EnumParticleTypes block_CRACK = EnumParticleTypes.BLOCK_CRACK;
                final double n = this.posX + (this.rand.nextFloat() - 0.5) * this.width;
                final double n2 = this.getEntityBoundingBox().minY + 0.1;
                final double n3 = this.posZ + (this.rand.nextFloat() - 0.5) * this.width;
                final double n4 = 4.0 * (this.rand.nextFloat() - 0.5);
                final double n5 = 0.5;
                final double n6 = (this.rand.nextFloat() - 0.5) * 4.0;
                final int[] array = new int[" ".length()];
                array["".length()] = Block.getStateId(blockState);
                worldObj.spawnParticle(block_CRACK, n, n2, n3, n4, n5, n6, array);
            }
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0xC ^ 0x1C, (byte)"".length());
    }
    
    public void setPlayerCreated(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x30 ^ 0x20);
        if (b) {
            this.dataWatcher.updateObject(0x71 ^ 0x61, (byte)(watchableObjectByte | " ".length()));
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x6D ^ 0x7D, (byte)(watchableObjectByte & -"  ".length()));
        }
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null) {
            this.villageObj.setReputationForPlayer(this.attackingPlayer.getName(), -(0x44 ^ 0x41));
        }
        super.onDeath(damageSource);
    }
    
    public void setHoldingRose(final boolean b) {
        int length;
        if (b) {
            length = 310 + 345 - 265 + 10;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        this.holdRoseTick = length;
        this.worldObj.setEntityState(this, (byte)(0x2C ^ 0x27));
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
    
    @Override
    protected void collideWithEntity(final Entity entity) {
        if (entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(0xA8 ^ 0xBC) == 0) {
            this.setAttackTarget((EntityLivingBase)entity);
        }
        super.collideWithEntity(entity);
    }
    
    static class AINearestAttackableTargetNonCreeper<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
    {
        static double access$0(final AINearestAttackableTargetNonCreeper aiNearestAttackableTargetNonCreeper) {
            return aiNearestAttackableTargetNonCreeper.getTargetDistance();
        }
        
        static boolean access$1(final AINearestAttackableTargetNonCreeper aiNearestAttackableTargetNonCreeper, final EntityLivingBase entityLivingBase, final boolean b) {
            return aiNearestAttackableTargetNonCreeper.isSuitableTarget(entityLivingBase, b);
        }
        
        public AINearestAttackableTargetNonCreeper(final EntityCreature entityCreature, final Class<T> clazz, final int n, final boolean b, final boolean b2, final Predicate<? super T> predicate) {
            super(entityCreature, clazz, n, b, b2, predicate);
            this.targetEntitySelector = (com.google.common.base.Predicate<? super T>)new Predicate<T>(this, predicate, entityCreature) {
                final AINearestAttackableTargetNonCreeper this$1;
                private final Predicate val$p_i45858_6_;
                private final EntityCreature val$creature;
                
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
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public boolean apply(final T t) {
                    if (this.val$p_i45858_6_ != null && !this.val$p_i45858_6_.apply((Object)t)) {
                        return "".length() != 0;
                    }
                    if (t instanceof EntityCreeper) {
                        return "".length() != 0;
                    }
                    if (t instanceof EntityPlayer) {
                        double access$0 = AINearestAttackableTargetNonCreeper.access$0(this.this$1);
                        if (t.isSneaking()) {
                            access$0 *= 0.800000011920929;
                        }
                        if (t.isInvisible()) {
                            float armorVisibility = ((EntityPlayer)t).getArmorVisibility();
                            if (armorVisibility < 0.1f) {
                                armorVisibility = 0.1f;
                            }
                            access$0 *= 0.7f * armorVisibility;
                        }
                        if (t.getDistanceToEntity(this.val$creature) > access$0) {
                            return "".length() != 0;
                        }
                    }
                    return AINearestAttackableTargetNonCreeper.access$1(this.this$1, t, "".length() != 0);
                }
                
                public boolean apply(final Object o) {
                    return this.apply((EntityLivingBase)o);
                }
            };
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
                if (2 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
