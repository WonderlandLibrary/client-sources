package net.minecraft.entity.effect;

import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class EntityLightningBolt extends EntityWeatherEffect
{
    public long boltVertex;
    private int boltLivingTime;
    private static final String[] I;
    private int lightningState;
    
    @Override
    protected void entityInit() {
    }
    
    private static void I() {
        (I = new String[0x94 ^ 0x90])["".length()] = I("\u001d\b\u0000\f<\u001c3/\u0006%", "ygFeN");
        EntityLightningBolt.I[" ".length()] = I("\r\u001e\u0011 )\u0002\u0007]>)\r\u0007\u001b,>B\u0007\u001b<\"\b\u0016\u0001", "lssIL");
        EntityLightningBolt.I["  ".length()] = I("\u0018'+*+\u0007h 64\u0006)!+", "jFEND");
        EntityLightningBolt.I["   ".length()] = I("\u001d,\u0015(9\u001c\u0017:\" ", "yCSAK");
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.lightningState == "  ".length()) {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, EntityLightningBolt.I[" ".length()], 10000.0f, 0.8f + this.rand.nextFloat() * 0.2f);
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, EntityLightningBolt.I["  ".length()], 2.0f, 0.5f + this.rand.nextFloat() * 0.2f);
        }
        this.lightningState -= " ".length();
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else if (this.lightningState < -this.rand.nextInt(0x77 ^ 0x7D)) {
                this.boltLivingTime -= " ".length();
                this.lightningState = " ".length();
                this.boltVertex = this.rand.nextLong();
                final BlockPos blockPos = new BlockPos(this);
                if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean(EntityLightningBolt.I["   ".length()]) && this.worldObj.isAreaLoaded(blockPos, 0x23 ^ 0x29) && this.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, blockPos)) {
                    this.worldObj.setBlockState(blockPos, Blocks.fire.getDefaultState());
                }
            }
        }
        if (this.lightningState >= 0) {
            if (this.worldObj.isRemote) {
                this.worldObj.setLastLightningBolt("  ".length());
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                final double n = 3.0;
                final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - n, this.posY - n, this.posZ - n, this.posX + n, this.posY + 6.0 + n, this.posZ + n));
                int i = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
                while (i < entitiesWithinAABBExcludingEntity.size()) {
                    entitiesWithinAABBExcludingEntity.get(i).onStruckByLightning(this);
                    ++i;
                }
            }
        }
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public EntityLightningBolt(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.setLocationAndAngles(n, n2, n3, 0.0f, 0.0f);
        this.lightningState = "  ".length();
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt("   ".length()) + " ".length();
        final BlockPos blockPos = new BlockPos(this);
        if (!world.isRemote && world.getGameRules().getBoolean(EntityLightningBolt.I["".length()]) && (world.getDifficulty() == EnumDifficulty.NORMAL || world.getDifficulty() == EnumDifficulty.HARD) && world.isAreaLoaded(blockPos, 0x5B ^ 0x51)) {
            if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(world, blockPos)) {
                world.setBlockState(blockPos, Blocks.fire.getDefaultState());
            }
            int i = "".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (i < (0x12 ^ 0x16)) {
                final BlockPos add = blockPos.add(this.rand.nextInt("   ".length()) - " ".length(), this.rand.nextInt("   ".length()) - " ".length(), this.rand.nextInt("   ".length()) - " ".length());
                if (world.getBlockState(add).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(world, add)) {
                    world.setBlockState(add, Blocks.fire.getDefaultState());
                }
                ++i;
            }
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
}
