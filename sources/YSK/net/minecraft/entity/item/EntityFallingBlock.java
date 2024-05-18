package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.crash.*;

public class EntityFallingBlock extends Entity
{
    private static final String[] I;
    private boolean hurtEntities;
    private boolean canSetAsBlock;
    private float fallHurtAmount;
    private IBlockState fallTile;
    private int fallHurtMax;
    public NBTTagCompound tileEntityData;
    public int fallTime;
    public boolean shouldDropItem;
    
    public EntityFallingBlock(final World world, final double prevPosX, final double prevPosY, final double prevPosZ, final IBlockState fallTile) {
        super(world);
        this.shouldDropItem = (" ".length() != 0);
        this.fallHurtMax = (0x13 ^ 0x3B);
        this.fallHurtAmount = 2.0f;
        this.fallTile = fallTile;
        this.preventEntitySpawning = (" ".length() != 0);
        this.setSize(0.98f, 0.98f);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    @Override
    public boolean canRenderOnFire() {
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        Block block;
        if (this.fallTile != null) {
            block = this.fallTile.getBlock();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            block = Blocks.air;
        }
        final Block block2 = block;
        final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(block2);
        final String s = EntityFallingBlock.I[0xB9 ^ 0xBC];
        String string;
        if (resourceLocation == null) {
            string = EntityFallingBlock.I[0xBC ^ 0xBA];
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        nbtTagCompound.setByte(EntityFallingBlock.I[0x8D ^ 0x8A], (byte)block2.getMetaFromState(this.fallTile));
        nbtTagCompound.setByte(EntityFallingBlock.I[0xD ^ 0x5], (byte)this.fallTime);
        nbtTagCompound.setBoolean(EntityFallingBlock.I[0xA9 ^ 0xA0], this.shouldDropItem);
        nbtTagCompound.setBoolean(EntityFallingBlock.I[0x87 ^ 0x8D], this.hurtEntities);
        nbtTagCompound.setFloat(EntityFallingBlock.I[0xC ^ 0x7], this.fallHurtAmount);
        nbtTagCompound.setInteger(EntityFallingBlock.I[0x75 ^ 0x79], this.fallHurtMax);
        if (this.tileEntityData != null) {
            nbtTagCompound.setTag(EntityFallingBlock.I[0x5B ^ 0x56], this.tileEntityData);
        }
    }
    
    public void setHurtEntities(final boolean hurtEntities) {
        this.hurtEntities = hurtEntities;
    }
    
    @Override
    public void onUpdate() {
        final Block block = this.fallTile.getBlock();
        if (block.getMaterial() == Material.air) {
            this.setDead();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            final int fallTime = this.fallTime;
            this.fallTime = fallTime + " ".length();
            if (fallTime == 0) {
                final BlockPos blockToAir = new BlockPos(this);
                if (this.worldObj.getBlockState(blockToAir).getBlock() == block) {
                    this.worldObj.setBlockToAir(blockToAir);
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else if (!this.worldObj.isRemote) {
                    this.setDead();
                    return;
                }
            }
            this.motionY -= 0.03999999910593033;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (!this.worldObj.isRemote) {
                final BlockPos blockPos = new BlockPos(this);
                if (this.onGround) {
                    this.motionX *= 0.699999988079071;
                    this.motionZ *= 0.699999988079071;
                    this.motionY *= -0.5;
                    if (this.worldObj.getBlockState(blockPos).getBlock() != Blocks.piston_extension) {
                        this.setDead();
                        if (!this.canSetAsBlock) {
                            if (this.worldObj.canBlockBePlaced(block, blockPos, " ".length() != 0, EnumFacing.UP, null, null) && !BlockFalling.canFallInto(this.worldObj, blockPos.down()) && this.worldObj.setBlockState(blockPos, this.fallTile, "   ".length())) {
                                if (block instanceof BlockFalling) {
                                    ((BlockFalling)block).onEndFalling(this.worldObj, blockPos);
                                }
                                if (this.tileEntityData != null && block instanceof ITileEntityProvider) {
                                    final TileEntity tileEntity = this.worldObj.getTileEntity(blockPos);
                                    if (tileEntity != null) {
                                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                                        tileEntity.writeToNBT(nbtTagCompound);
                                        final Iterator<String> iterator = this.tileEntityData.getKeySet().iterator();
                                        "".length();
                                        if (!true) {
                                            throw null;
                                        }
                                        while (iterator.hasNext()) {
                                            final String s = iterator.next();
                                            final NBTBase tag = this.tileEntityData.getTag(s);
                                            if (!s.equals(EntityFallingBlock.I["".length()]) && !s.equals(EntityFallingBlock.I[" ".length()]) && !s.equals(EntityFallingBlock.I["  ".length()])) {
                                                nbtTagCompound.setTag(s, tag.copy());
                                            }
                                        }
                                        tileEntity.readFromNBT(nbtTagCompound);
                                        tileEntity.markDirty();
                                        "".length();
                                        if (2 == 4) {
                                            throw null;
                                        }
                                    }
                                }
                            }
                            else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean(EntityFallingBlock.I["   ".length()])) {
                                this.entityDropItem(new ItemStack(block, " ".length(), block.damageDropped(this.fallTile)), 0.0f);
                                "".length();
                                if (2 <= -1) {
                                    throw null;
                                }
                            }
                        }
                    }
                }
                else if ((this.fallTime > (0x29 ^ 0x4D) && !this.worldObj.isRemote && (blockPos.getY() < " ".length() || blockPos.getY() > 205 + 204 - 186 + 33)) || this.fallTime > 570 + 475 - 777 + 332) {
                    if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean(EntityFallingBlock.I[0xC0 ^ 0xC4])) {
                        this.entityDropItem(new ItemStack(block, " ".length(), block.damageDropped(this.fallTile)), 0.0f);
                    }
                    this.setDead();
                }
            }
        }
    }
    
    public IBlockState getBlock() {
        return this.fallTile;
    }
    
    @Override
    protected void entityInit() {
    }
    
    private static void I() {
        (I = new String[0x39 ^ 0x26])["".length()] = I("\u0016", "nUqJu");
        EntityFallingBlock.I[" ".length()] = I("/", "VTvWk");
        EntityFallingBlock.I["  ".length()] = I("\"", "XchCA");
        EntityFallingBlock.I["   ".length()] = I("\u0013.\u0001='\u001e5=\u0017!\u001817", "wADSS");
        EntityFallingBlock.I[0x8E ^ 0x8A] = I(" :!\n=-!\u001d ;+%\u0017", "DUddI");
        EntityFallingBlock.I[0x32 ^ 0x37] = I("\u000e;-\n\u001e", "LWBiu");
        EntityFallingBlock.I[0x5D ^ 0x5B] = I("", "piuVo");
        EntityFallingBlock.I[0x17 ^ 0x10] = I("\u001d3\u0005\u000b", "YRqjG");
        EntityFallingBlock.I[0x6A ^ 0x62] = I("\u001e*\u0007\u0002", "JCjgP");
        EntityFallingBlock.I[0xA7 ^ 0xAE] = I("%\u001e\u0005\u00191\u0015\t\u0007", "aljix");
        EntityFallingBlock.I[0xBA ^ 0xB0] = I("!4\u0010&=\u00075\u000b&\u0011\f2", "iAbRx");
        EntityFallingBlock.I[0x55 ^ 0x5E] = I("+\u0006\u0015\r=\u0018\u0015\r \u0018\u0002\u0012\u0017\u0015", "mgyau");
        EntityFallingBlock.I[0x60 ^ 0x6C] = I("$\u0010\u001c=>\u0017\u0003\u0004\u001c\u0017\u001a", "bqpQv");
        EntityFallingBlock.I[0x18 ^ 0x15] = I("\"&\u000e\u0014\u0000\u0018;\u000b\u0005<2.\u0016\u0010", "vObqE");
        EntityFallingBlock.I[0xBE ^ 0xB0] = I("\r\"\u001f\u0003", "ICkbn");
        EntityFallingBlock.I[0xCA ^ 0xC5] = I("/\u0016\u001c-!", "mzsNJ");
        EntityFallingBlock.I[0x17 ^ 0x7] = I("\u001b\u001d85)", "YqWVB");
        EntityFallingBlock.I[0x4F ^ 0x5E] = I("\u0003\u0001\u0015\u001c\u0007\u0013", "WhyyN");
        EntityFallingBlock.I[0x5 ^ 0x17] = I("..(\u0016\u0013>", "zGDsZ");
        EntityFallingBlock.I[0xA5 ^ 0xB6] = I("\u0018\u0007\u001f\u0010", "LnsuH");
        EntityFallingBlock.I[0xA7 ^ 0xB3] = I("\u001d0*\u0007", "IYGbu");
        EntityFallingBlock.I[0x32 ^ 0x27] = I(")\u001e!\u0010(\u000f\u001f:\u0010\u0004\u0004\u0018", "akSdm");
        EntityFallingBlock.I[0x66 ^ 0x70] = I("\u0004\u0018\u00147\"\"\u0019\u000f7\u000e)\u001e", "LmfCg");
        EntityFallingBlock.I[0x3E ^ 0x29] = I("\u0010\u0007<8<#\u0014$\u0015\u00199\u0013> ", "VfPTt");
        EntityFallingBlock.I[0xA9 ^ 0xB1] = I("\u0014\u0011 \u001d\t'\u00028< *", "RpLqA");
        EntityFallingBlock.I[0xBE ^ 0xA7] = I("-%\u001a\u00019\u001d2\u0018", "iWuqp");
        EntityFallingBlock.I[0x29 ^ 0x33] = I("\u0002&'<821%", "FTHLq");
        EntityFallingBlock.I[0x81 ^ 0x9A] = I(" -4\u000e?\u001a01\u001f\u00030%,\n", "tDXkz");
        EntityFallingBlock.I[0xDD ^ 0xC1] = I("5\u0000\u000b\n#\u000f\u001d\u000e\u001b\u001f%\b\u0013\u000e", "aigof");
        EntityFallingBlock.I[0x55 ^ 0x48] = I("$\b\u000f\u001a%\f\u0011\u000b\u001d6M\u0007\u000e\u001c2\u0006E+7", "mebsQ");
        EntityFallingBlock.I[0x26 ^ 0x38] = I("((.:6\u00001*=%A'/<!\ne'26\u0000", "aECSB");
    }
    
    static {
        I();
    }
    
    public EntityFallingBlock(final World world) {
        super(world);
        this.shouldDropItem = (" ".length() != 0);
        this.fallHurtMax = (0x5B ^ 0x73);
        this.fallHurtAmount = 2.0f;
    }
    
    public World getWorldObj() {
        return this.worldObj;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final int n = nbtTagCompound.getByte(EntityFallingBlock.I[0x2F ^ 0x21]) & 136 + 72 - 157 + 204;
        if (nbtTagCompound.hasKey(EntityFallingBlock.I[0x4D ^ 0x42], 0x49 ^ 0x41)) {
            this.fallTile = Block.getBlockFromName(nbtTagCompound.getString(EntityFallingBlock.I[0x7 ^ 0x17])).getStateFromMeta(n);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (nbtTagCompound.hasKey(EntityFallingBlock.I[0x75 ^ 0x64], 0xDD ^ 0xBE)) {
            this.fallTile = Block.getBlockById(nbtTagCompound.getInteger(EntityFallingBlock.I[0x69 ^ 0x7B])).getStateFromMeta(n);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            this.fallTile = Block.getBlockById(nbtTagCompound.getByte(EntityFallingBlock.I[0x12 ^ 0x1]) & 91 + 176 - 227 + 215).getStateFromMeta(n);
        }
        this.fallTime = (nbtTagCompound.getByte(EntityFallingBlock.I[0xB7 ^ 0xA3]) & 177 + 191 - 298 + 185);
        final Block block = this.fallTile.getBlock();
        if (nbtTagCompound.hasKey(EntityFallingBlock.I[0xE ^ 0x1B], 0xE5 ^ 0x86)) {
            this.hurtEntities = nbtTagCompound.getBoolean(EntityFallingBlock.I[0x9E ^ 0x88]);
            this.fallHurtAmount = nbtTagCompound.getFloat(EntityFallingBlock.I[0x7F ^ 0x68]);
            this.fallHurtMax = nbtTagCompound.getInteger(EntityFallingBlock.I[0x5B ^ 0x43]);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (block == Blocks.anvil) {
            this.hurtEntities = (" ".length() != 0);
        }
        if (nbtTagCompound.hasKey(EntityFallingBlock.I[0x4B ^ 0x52], 0x11 ^ 0x72)) {
            this.shouldDropItem = nbtTagCompound.getBoolean(EntityFallingBlock.I[0x89 ^ 0x93]);
        }
        if (nbtTagCompound.hasKey(EntityFallingBlock.I[0x94 ^ 0x8F], 0xA4 ^ 0xAE)) {
            this.tileEntityData = nbtTagCompound.getCompoundTag(EntityFallingBlock.I[0x94 ^ 0x88]);
        }
        if (block == null || block.getMaterial() == Material.air) {
            this.fallTile = Blocks.sand.getDefaultState();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void fall(final float n, final float n2) {
        final Block block = this.fallTile.getBlock();
        if (this.hurtEntities) {
            final int ceiling_float_int = MathHelper.ceiling_float_int(n - 1.0f);
            if (ceiling_float_int > 0) {
                final ArrayList arrayList = Lists.newArrayList((Iterable)this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
                int n3;
                if (block == Blocks.anvil) {
                    n3 = " ".length();
                    "".length();
                    if (0 == 3) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final int n4 = n3;
                DamageSource damageSource;
                if (n4 != 0) {
                    damageSource = DamageSource.anvil;
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                else {
                    damageSource = DamageSource.fallingBlock;
                }
                final DamageSource damageSource2 = damageSource;
                final Iterator<Entity> iterator = arrayList.iterator();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    iterator.next().attackEntityFrom(damageSource2, Math.min(MathHelper.floor_float(ceiling_float_int * this.fallHurtAmount), this.fallHurtMax));
                }
                if (n4 != 0 && this.rand.nextFloat() < 0.05000000074505806 + ceiling_float_int * 0.05) {
                    int intValue = this.fallTile.getValue((IProperty<Integer>)BlockAnvil.DAMAGE);
                    if (++intValue > "  ".length()) {
                        this.canSetAsBlock = (" ".length() != 0);
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        this.fallTile = this.fallTile.withProperty((IProperty<Comparable>)BlockAnvil.DAMAGE, intValue);
                    }
                }
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    @Override
    public void addEntityCrashInfo(final CrashReportCategory crashReportCategory) {
        super.addEntityCrashInfo(crashReportCategory);
        if (this.fallTile != null) {
            final Block block = this.fallTile.getBlock();
            crashReportCategory.addCrashSection(EntityFallingBlock.I[0x11 ^ 0xC], Block.getIdFromBlock(block));
            crashReportCategory.addCrashSection(EntityFallingBlock.I[0x97 ^ 0x89], block.getMetaFromState(this.fallTile));
        }
    }
}
