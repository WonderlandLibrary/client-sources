package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    private static final String[] I;
    
    public EntitySnowman(final World world) {
        super(world);
        this.setSize(0.7f, 1.9f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask(" ".length(), new EntityAIArrowAttack(this, 1.25, 0x1D ^ 0x9, 10.0f));
        this.tasks.addTask("  ".length(), new EntityAIWander(this, 1.0));
        this.tasks.addTask("   ".length(), new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0x75 ^ 0x71, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityLiving.class, 0x55 ^ 0x5F, (boolean)(" ".length() != 0), (boolean)("".length() != 0), IMob.mobSelector));
    }
    
    @Override
    public float getEyeHeight() {
        return 1.7f;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            final int floor_double = MathHelper.floor_double(this.posX);
            final int floor_double2 = MathHelper.floor_double(this.posY);
            final int floor_double3 = MathHelper.floor_double(this.posZ);
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1.0f);
            }
            if (this.worldObj.getBiomeGenForCoords(new BlockPos(floor_double, "".length(), floor_double3)).getFloatTemperature(new BlockPos(floor_double, floor_double2, floor_double3)) > 1.0f) {
                this.attackEntityFrom(DamageSource.onFire, 1.0f);
            }
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i < (0x2A ^ 0x2E)) {
                final int floor_double4 = MathHelper.floor_double(this.posX + (i % "  ".length() * "  ".length() - " ".length()) * 0.25f);
                final int floor_double5 = MathHelper.floor_double(this.posY);
                final int floor_double6 = MathHelper.floor_double(this.posZ + (i / "  ".length() % "  ".length() * "  ".length() - " ".length()) * 0.25f);
                final BlockPos blockPos = new BlockPos(floor_double4, floor_double5, floor_double6);
                if (this.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(new BlockPos(floor_double4, "".length(), floor_double6)).getFloatTemperature(blockPos) < 0.8f && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, blockPos)) {
                    this.worldObj.setBlockState(blockPos, Blocks.snow_layer.getDefaultState());
                }
                ++i;
            }
        }
    }
    
    static {
        I();
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int nextInt = this.rand.nextInt(0xBE ^ 0xAE);
        int i = "".length();
        "".length();
        if (4 < 1) {
            throw null;
        }
        while (i < nextInt) {
            this.dropItem(Items.snowball, " ".length());
            ++i;
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("5\u0004)\"\u0007*K%)\u001f", "GeGFh");
    }
    
    @Override
    protected Item getDropItem() {
        return Items.snowball;
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        final EntitySnowball entitySnowball = new EntitySnowball(this.worldObj, this);
        final double n2 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - 1.100000023841858;
        final double n3 = entityLivingBase.posX - this.posX;
        final double n4 = n2 - entitySnowball.posY;
        final double n5 = entityLivingBase.posZ - this.posZ;
        entitySnowball.setThrowableHeading(n3, n4 + MathHelper.sqrt_double(n3 * n3 + n5 * n5) * 0.2f, n5, 1.6f, 12.0f);
        this.playSound(EntitySnowman.I["".length()], 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(entitySnowball);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
}
