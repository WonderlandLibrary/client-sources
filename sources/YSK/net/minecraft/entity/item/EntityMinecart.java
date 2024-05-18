package net.minecraft.entity.item;

import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class EntityMinecart extends Entity implements IWorldNameable
{
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
    private double velocityY;
    private static final String[] I;
    private double velocityX;
    private double minecartX;
    private double minecartPitch;
    private String entityName;
    private int turnProgress;
    private static final int[][][] matrix;
    private double minecartY;
    private static int[] $SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
    private double minecartZ;
    private double minecartYaw;
    private double velocityZ;
    private boolean isInReverse;
    
    private static void I() {
        (I = new String[0x69 ^ 0x78])["".length()] = I("(9\u0014\b\u0011%\"(\"\u0017#&\"", "LVQfe");
        EntityMinecart.I[" ".length()] = I("\u0004&\u0011<7\u0018", "tIcHV");
        EntityMinecart.I["  ".length()] = I("\n\r\t\u001e8$<\u0013\u0019'%\u0019\u0003>>%\u001d", "IxzjW");
        EntityMinecart.I["   ".length()] = I("%\u0019\u001f1(\u0000\t( 0\u0000", "aplAD");
        EntityMinecart.I[0x2A ^ 0x2E] = I("7-\u0011% \u0012=6< \u0016", "sDbUL");
        EntityMinecart.I[0x35 ^ 0x30] = I("5#\u001f2%\u001038+%\u0014", "qJlBI");
        EntityMinecart.I[0xBC ^ 0xBA] = I("\u001206\u0014)7 \u0011\r)3", "VYEdE");
        EntityMinecart.I[0x10 ^ 0x17] = I("\u0010+ (\r5;\u001c>\u0007'''", "TBSXa");
        EntityMinecart.I[0xCC ^ 0xC4] = I("\u0004\u001e\u001c>\u0007*%\u000e'\r", "GkoJh");
        EntityMinecart.I[0x6D ^ 0x64] = I("/1+2\u0000\u0001\n9+\n", "lDXFo");
        EntityMinecart.I[0x7B ^ 0x71] = I("/\u001f\u001a$,\u0001$\b=&", "ljiPC");
        EntityMinecart.I[0x15 ^ 0x1E] = I(",:\u0006$*\u0002\u000b\u001c#5\u0003.\f\u0004,\u0003*", "oOuPE");
        EntityMinecart.I[0x6B ^ 0x67] = I("\u0014\b\u001e2\u00141\u00189+\u00145", "PamBx");
        EntityMinecart.I[0x9F ^ 0x92] = I("", "wDQwh");
        EntityMinecart.I[0xCB ^ 0xC5] = I("-8?\u001a&\b(\b\u000b>\b", "iQLjJ");
        EntityMinecart.I[0x53 ^ 0x5C] = I("+\u001a\u001a!%\u000e\n&7/\u001c\u0016\u001d", "osiQI");
        EntityMinecart.I[0x3B ^ 0x2B] = I("\u0002 \u0014?+,\u001b\u0006&!", "AUgKD");
    }
    
    public IBlockState getDisplayTile() {
        IBlockState blockState;
        if (!this.hasDisplayTile()) {
            blockState = this.getDefaultDisplayTile();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            blockState = Block.getStateById(this.getDataWatcher().getWatchableObjectInt(0x41 ^ 0x55));
        }
        return blockState;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    protected void applyDrag() {
        if (this.riddenByEntity != null) {
            this.motionX *= 0.996999979019165;
            this.motionY *= 0.0;
            this.motionZ *= 0.996999979019165;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            this.motionX *= 0.9599999785423279;
            this.motionY *= 0.0;
            this.motionZ *= 0.9599999785423279;
        }
    }
    
    public abstract EnumMinecartType getMinecartType();
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        AxisAlignedBB entityBoundingBox;
        if (entity.canBePushed()) {
            entityBoundingBox = entity.getEntityBoundingBox();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            entityBoundingBox = null;
        }
        return entityBoundingBox;
    }
    
    public EntityMinecart(final World world) {
        super(world);
        this.preventEntitySpawning = (" ".length() != 0);
        this.setSize(0.98f, 0.7f);
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.velocityX = n;
        this.motionY = n2;
        this.velocityY = n2;
        this.motionZ = n3;
        this.velocityZ = n3;
    }
    
    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(0xA7 ^ 0xB4);
    }
    
    public Vec3 func_70495_a(double n, double n2, double n3, final double n4) {
        final int floor_double = MathHelper.floor_double(n);
        int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(floor_double, floor_double2 - " ".length(), floor_double3))) {
            --floor_double2;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3));
        if (BlockRailBase.isRailBlock(blockState)) {
            final BlockRailBase.EnumRailDirection enumRailDirection = blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty());
            n2 = floor_double2;
            if (enumRailDirection.isAscending()) {
                n2 = floor_double2 + " ".length();
            }
            final int[][] array = EntityMinecart.matrix[enumRailDirection.getMetadata()];
            final double n5 = array[" ".length()]["".length()] - array["".length()]["".length()];
            final double n6 = array[" ".length()]["  ".length()] - array["".length()]["  ".length()];
            final double sqrt = Math.sqrt(n5 * n5 + n6 * n6);
            final double n7 = n5 / sqrt;
            final double n8 = n6 / sqrt;
            n += n7 * n4;
            n3 += n8 * n4;
            if (array["".length()][" ".length()] != 0 && MathHelper.floor_double(n) - floor_double == array["".length()]["".length()] && MathHelper.floor_double(n3) - floor_double3 == array["".length()]["  ".length()]) {
                n2 += array["".length()][" ".length()];
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            else if (array[" ".length()][" ".length()] != 0 && MathHelper.floor_double(n) - floor_double == array[" ".length()]["".length()] && MathHelper.floor_double(n3) - floor_double3 == array[" ".length()]["  ".length()]) {
                n2 += array[" ".length()][" ".length()];
            }
            return this.func_70489_a(n, n2, n3);
        }
        return null;
    }
    
    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(0x63 ^ 0x72);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    static {
        I();
        final int[][][] matrix2 = new int[0x35 ^ 0x3F][][];
        final int length = "".length();
        final int[][] array = new int["  ".length()][];
        final int length2 = "".length();
        final int[] array2 = new int["   ".length()];
        array2["  ".length()] = -" ".length();
        array[length2] = array2;
        final int length3 = " ".length();
        final int[] array3 = new int["   ".length()];
        array3["  ".length()] = " ".length();
        array[length3] = array3;
        matrix2[length] = array;
        final int length4 = " ".length();
        final int[][] array4 = new int["  ".length()][];
        final int length5 = "".length();
        final int[] array5 = new int["   ".length()];
        array5["".length()] = -" ".length();
        array4[length5] = array5;
        final int length6 = " ".length();
        final int[] array6 = new int["   ".length()];
        array6["".length()] = " ".length();
        array4[length6] = array6;
        matrix2[length4] = array4;
        final int length7 = "  ".length();
        final int[][] array7 = new int["  ".length()][];
        final int length8 = "".length();
        final int[] array8 = new int["   ".length()];
        array8["".length()] = -" ".length();
        array8[" ".length()] = -" ".length();
        array7[length8] = array8;
        final int length9 = " ".length();
        final int[] array9 = new int["   ".length()];
        array9["".length()] = " ".length();
        array7[length9] = array9;
        matrix2[length7] = array7;
        final int length10 = "   ".length();
        final int[][] array10 = new int["  ".length()][];
        final int length11 = "".length();
        final int[] array11 = new int["   ".length()];
        array11["".length()] = -" ".length();
        array10[length11] = array11;
        final int length12 = " ".length();
        final int[] array12 = new int["   ".length()];
        array12["".length()] = " ".length();
        array12[" ".length()] = -" ".length();
        array10[length12] = array12;
        matrix2[length10] = array10;
        final int n = 0x24 ^ 0x20;
        final int[][] array13 = new int["  ".length()][];
        final int length13 = "".length();
        final int[] array14 = new int["   ".length()];
        array14["  ".length()] = -" ".length();
        array13[length13] = array14;
        final int length14 = " ".length();
        final int[] array15 = new int["   ".length()];
        array15[" ".length()] = -" ".length();
        array15["  ".length()] = " ".length();
        array13[length14] = array15;
        matrix2[n] = array13;
        final int n2 = 0xE ^ 0xB;
        final int[][] array16 = new int["  ".length()][];
        final int length15 = "".length();
        final int[] array17 = new int["   ".length()];
        array17[" ".length()] = -" ".length();
        array17["  ".length()] = -" ".length();
        array16[length15] = array17;
        final int length16 = " ".length();
        final int[] array18 = new int["   ".length()];
        array18["  ".length()] = " ".length();
        array16[length16] = array18;
        matrix2[n2] = array16;
        final int n3 = 0x9C ^ 0x9A;
        final int[][] array19 = new int["  ".length()][];
        final int length17 = "".length();
        final int[] array20 = new int["   ".length()];
        array20["  ".length()] = " ".length();
        array19[length17] = array20;
        final int length18 = " ".length();
        final int[] array21 = new int["   ".length()];
        array21["".length()] = " ".length();
        array19[length18] = array21;
        matrix2[n3] = array19;
        final int n4 = 0x52 ^ 0x55;
        final int[][] array22 = new int["  ".length()][];
        final int length19 = "".length();
        final int[] array23 = new int["   ".length()];
        array23["  ".length()] = " ".length();
        array22[length19] = array23;
        final int length20 = " ".length();
        final int[] array24 = new int["   ".length()];
        array24["".length()] = -" ".length();
        array22[length20] = array24;
        matrix2[n4] = array22;
        final int n5 = 0x73 ^ 0x7B;
        final int[][] array25 = new int["  ".length()][];
        final int length21 = "".length();
        final int[] array26 = new int["   ".length()];
        array26["  ".length()] = -" ".length();
        array25[length21] = array26;
        final int length22 = " ".length();
        final int[] array27 = new int["   ".length()];
        array27["".length()] = -" ".length();
        array25[length22] = array27;
        matrix2[n5] = array25;
        final int n6 = 0x24 ^ 0x2D;
        final int[][] array28 = new int["  ".length()][];
        final int length23 = "".length();
        final int[] array29 = new int["   ".length()];
        array29["  ".length()] = -" ".length();
        array28[length23] = array29;
        final int length24 = " ".length();
        final int[] array30 = new int["   ".length()];
        array30["".length()] = " ".length();
        array28[length24] = array30;
        matrix2[n6] = array28;
        matrix = matrix2;
    }
    
    protected double getMaximumSpeed() {
        return 0.4;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.getBoolean(EntityMinecart.I["  ".length()])) {
            final int integer = nbtTagCompound.getInteger(EntityMinecart.I["   ".length()]);
            if (nbtTagCompound.hasKey(EntityMinecart.I[0x36 ^ 0x32], 0x7E ^ 0x76)) {
                final Block blockFromName = Block.getBlockFromName(nbtTagCompound.getString(EntityMinecart.I[0x2E ^ 0x2B]));
                if (blockFromName == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
                else {
                    this.func_174899_a(blockFromName.getStateFromMeta(integer));
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
            }
            else {
                final Block blockById = Block.getBlockById(nbtTagCompound.getInteger(EntityMinecart.I[0x2D ^ 0x2B]));
                if (blockById == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                else {
                    this.func_174899_a(blockById.getStateFromMeta(integer));
                }
            }
            this.setDisplayTileOffset(nbtTagCompound.getInteger(EntityMinecart.I[0x60 ^ 0x67]));
        }
        if (nbtTagCompound.hasKey(EntityMinecart.I[0x3F ^ 0x37], 0x66 ^ 0x6E) && nbtTagCompound.getString(EntityMinecart.I[0x55 ^ 0x5C]).length() > 0) {
            this.entityName = nbtTagCompound.getString(EntityMinecart.I[0x71 ^ 0x7B]);
        }
    }
    
    public IBlockState getDefaultDisplayTile() {
        return Blocks.air.getDefaultState();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType() {
        final int[] $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType = EntityMinecart.$SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
        if ($switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType != null) {
            return $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
        }
        final int[] $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2 = new int[EnumMinecartType.values().length];
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.CHEST.ordinal()] = "  ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.COMMAND_BLOCK.ordinal()] = (0x15 ^ 0x12);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.FURNACE.ordinal()] = "   ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.HOPPER.ordinal()] = (0xB9 ^ 0xBF);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.RIDEABLE.ordinal()] = " ".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.SPAWNER.ordinal()] = (0x42 ^ 0x47);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EnumMinecartType.TNT.ordinal()] = (0x53 ^ 0x57);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        return EntityMinecart.$SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType = $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2;
    }
    
    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(0x9C ^ 0x8E);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.worldObj.isRemote || this.isDead) {
            return " ".length() != 0;
        }
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(0xCC ^ 0xC6);
        this.setBeenAttacked();
        this.setDamage(this.getDamage() + n * 10.0f);
        int n2;
        if (damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode) {
            n2 = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        if (n3 != 0 || this.getDamage() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(null);
            }
            if (n3 != 0 && !this.hasCustomName()) {
                this.setDead();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                this.killMinecart(damageSource);
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public String getName() {
        String s;
        if (this.entityName != null) {
            s = this.entityName;
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            s = super.getName();
        }
        return s;
    }
    
    public void setDisplayTileOffset(final int n) {
        this.getDataWatcher().updateObject(0xBC ^ 0xA9, n);
        this.setHasDisplayTile(" ".length() != 0);
    }
    
    public void setRollingAmplitude(final int n) {
        this.dataWatcher.updateObject(0xD4 ^ 0xC5, n);
    }
    
    public static EntityMinecart func_180458_a(final World world, final double n, final double n2, final double n3, final EnumMinecartType enumMinecartType) {
        switch ($SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType()[enumMinecartType.ordinal()]) {
            case 2: {
                return new EntityMinecartChest(world, n, n2, n3);
            }
            case 3: {
                return new EntityMinecartFurnace(world, n, n2, n3);
            }
            case 4: {
                return new EntityMinecartTNT(world, n, n2, n3);
            }
            case 5: {
                return new EntityMinecartMobSpawner(world, n, n2, n3);
            }
            case 6: {
                return new EntityMinecartHopper(world, n, n2, n3);
            }
            case 7: {
                return new EntityMinecartCommandBlock(world, n, n2, n3);
            }
            default: {
                return new EntityMinecartEmpty(world, n, n2, n3);
            }
        }
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(0x23 ^ 0x32, new Integer("".length()));
        this.dataWatcher.addObject(0x58 ^ 0x4A, new Integer(" ".length()));
        this.dataWatcher.addObject(0x72 ^ 0x61, new Float(0.0f));
        this.dataWatcher.addObject(0x5B ^ 0x4F, new Integer("".length()));
        this.dataWatcher.addObject(0x93 ^ 0x86, new Integer(0xBC ^ 0xBA));
        this.dataWatcher.addObject(0x83 ^ 0x95, (byte)"".length());
    }
    
    protected void func_180460_a(final BlockPos blockPos, final IBlockState blockState) {
        this.fallDistance = 0.0f;
        final Vec3 func_70489_a = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = blockPos.getY();
        int n = "".length();
        int n2 = "".length();
        final BlockRailBase blockRailBase = (BlockRailBase)blockState.getBlock();
        if (blockRailBase == Blocks.golden_rail) {
            n = (((boolean)blockState.getValue((IProperty<Boolean>)BlockRailPowered.POWERED)) ? 1 : 0);
            int n3;
            if (n != 0) {
                n3 = "".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                n3 = " ".length();
            }
            n2 = n3;
        }
        final BlockRailBase.EnumRailDirection enumRailDirection = blockState.getValue(blockRailBase.getShapeProperty());
        switch ($SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection()[enumRailDirection.ordinal()]) {
            case 3: {
                this.motionX -= 0.0078125;
                ++this.posY;
                "".length();
                if (-1 == 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.motionX += 0.0078125;
                ++this.posY;
                "".length();
                if (3 < -1) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.motionZ += 0.0078125;
                ++this.posY;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.motionZ -= 0.0078125;
                ++this.posY;
                break;
            }
        }
        final int[][] array = EntityMinecart.matrix[enumRailDirection.getMetadata()];
        double n4 = array[" ".length()]["".length()] - array["".length()]["".length()];
        double n5 = array[" ".length()]["  ".length()] - array["".length()]["  ".length()];
        final double sqrt = Math.sqrt(n4 * n4 + n5 * n5);
        if (this.motionX * n4 + this.motionZ * n5 < 0.0) {
            n4 = -n4;
            n5 = -n5;
        }
        double sqrt2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (sqrt2 > 2.0) {
            sqrt2 = 2.0;
        }
        this.motionX = sqrt2 * n4 / sqrt;
        this.motionZ = sqrt2 * n5 / sqrt;
        if (this.riddenByEntity instanceof EntityLivingBase && ((EntityLivingBase)this.riddenByEntity).moveForward > 0.0) {
            final double n6 = -Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            final double cos = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            if (this.motionX * this.motionX + this.motionZ * this.motionZ < 0.01) {
                this.motionX += n6 * 0.1;
                this.motionZ += cos * 0.1;
                n2 = "".length();
            }
        }
        if (n2 != 0) {
            if (Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) < 0.03) {
                this.motionX *= 0.0;
                this.motionY *= 0.0;
                this.motionZ *= 0.0;
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                this.motionX *= 0.5;
                this.motionY *= 0.0;
                this.motionZ *= 0.5;
            }
        }
        final double n7 = blockPos.getX() + 0.5 + array["".length()]["".length()] * 0.5;
        final double n8 = blockPos.getZ() + 0.5 + array["".length()]["  ".length()] * 0.5;
        final double n9 = blockPos.getX() + 0.5 + array[" ".length()]["".length()] * 0.5;
        final double n10 = blockPos.getZ() + 0.5 + array[" ".length()]["  ".length()] * 0.5;
        final double n11 = n9 - n7;
        final double n12 = n10 - n8;
        double n13;
        if (n11 == 0.0) {
            this.posX = blockPos.getX() + 0.5;
            n13 = this.posZ - blockPos.getZ();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (n12 == 0.0) {
            this.posZ = blockPos.getZ() + 0.5;
            n13 = this.posX - blockPos.getX();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n13 = ((this.posX - n7) * n11 + (this.posZ - n8) * n12) * 2.0;
        }
        this.posX = n7 + n11 * n13;
        this.posZ = n8 + n12 * n13;
        this.setPosition(this.posX, this.posY, this.posZ);
        double motionX = this.motionX;
        double motionZ = this.motionZ;
        if (this.riddenByEntity != null) {
            motionX *= 0.75;
            motionZ *= 0.75;
        }
        final double maximumSpeed = this.getMaximumSpeed();
        this.moveEntity(MathHelper.clamp_double(motionX, -maximumSpeed, maximumSpeed), 0.0, MathHelper.clamp_double(motionZ, -maximumSpeed, maximumSpeed));
        if (array["".length()][" ".length()] != 0 && MathHelper.floor_double(this.posX) - blockPos.getX() == array["".length()]["".length()] && MathHelper.floor_double(this.posZ) - blockPos.getZ() == array["".length()]["  ".length()]) {
            this.setPosition(this.posX, this.posY + array["".length()][" ".length()], this.posZ);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else if (array[" ".length()][" ".length()] != 0 && MathHelper.floor_double(this.posX) - blockPos.getX() == array[" ".length()]["".length()] && MathHelper.floor_double(this.posZ) - blockPos.getZ() == array[" ".length()]["  ".length()]) {
            this.setPosition(this.posX, this.posY + array[" ".length()][" ".length()], this.posZ);
        }
        this.applyDrag();
        final Vec3 func_70489_a2 = this.func_70489_a(this.posX, this.posY, this.posZ);
        if (func_70489_a2 != null && func_70489_a != null) {
            final double n14 = (func_70489_a.yCoord - func_70489_a2.yCoord) * 0.05;
            final double sqrt3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt3 > 0.0) {
                this.motionX = this.motionX / sqrt3 * (sqrt3 + n14);
                this.motionZ = this.motionZ / sqrt3 * (sqrt3 + n14);
            }
            this.setPosition(this.posX, func_70489_a2.yCoord, this.posZ);
        }
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.posZ);
        if (floor_double != blockPos.getX() || floor_double2 != blockPos.getZ()) {
            final double sqrt4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = sqrt4 * (floor_double - blockPos.getX());
            this.motionZ = sqrt4 * (floor_double2 - blockPos.getZ());
        }
        if (n != 0) {
            final double sqrt5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt5 > 0.01) {
                final double n15 = 0.06;
                this.motionX += this.motionX / sqrt5 * n15;
                this.motionZ += this.motionZ / sqrt5 * n15;
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else if (enumRailDirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
                if (this.worldObj.getBlockState(blockPos.west()).getBlock().isNormalCube()) {
                    this.motionX = 0.02;
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else if (this.worldObj.getBlockState(blockPos.east()).getBlock().isNormalCube()) {
                    this.motionX = -0.02;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
            }
            else if (enumRailDirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
                if (this.worldObj.getBlockState(blockPos.north()).getBlock().isNormalCube()) {
                    this.motionZ = 0.02;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                else if (this.worldObj.getBlockState(blockPos.south()).getBlock().isNormalCube()) {
                    this.motionZ = -0.02;
                }
            }
        }
    }
    
    @Override
    public String getCustomNameTag() {
        return this.entityName;
    }
    
    public int getDefaultDisplayTileOffset() {
        return 0x76 ^ 0x70;
    }
    
    @Override
    public boolean canBePushed() {
        return " ".length() != 0;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        if (this.hasCustomName()) {
            final ChatComponentText chatComponentText = new ChatComponentText(this.entityName);
            chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentText;
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        chatComponentTranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
        return chatComponentTranslation;
    }
    
    public void setDamage(final float n) {
        this.dataWatcher.updateObject(0x47 ^ 0x54, n);
    }
    
    protected void moveDerailedMinecart() {
        final double maximumSpeed = this.getMaximumSpeed();
        this.motionX = MathHelper.clamp_double(this.motionX, -maximumSpeed, maximumSpeed);
        this.motionZ = MathHelper.clamp_double(this.motionZ, -maximumSpeed, maximumSpeed);
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.949999988079071;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.949999988079071;
        }
    }
    
    public void setHasDisplayTile(final boolean b) {
        final DataWatcher dataWatcher = this.getDataWatcher();
        final int n = 0x85 ^ 0x93;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    @Override
    public void setPositionAndRotation2(final double minecartX, final double minecartY, final double minecartZ, final float n, final float n2, final int n3, final boolean b) {
        this.minecartX = minecartX;
        this.minecartY = minecartY;
        this.minecartZ = minecartZ;
        this.minecartYaw = n;
        this.minecartPitch = n2;
        this.turnProgress = n3 + "  ".length();
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    public EntityMinecart(final World world, final double prevPosX, final double prevPosY, final double prevPosZ) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }
    
    @Override
    public void applyEntityCollision(final Entity entity) {
        if (!this.worldObj.isRemote && !entity.noClip && !this.noClip && entity != this.riddenByEntity) {
            if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && this.getMinecartType() == EnumMinecartType.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && entity.ridingEntity == null) {
                entity.mountEntity(this);
            }
            final double n = entity.posX - this.posX;
            final double n2 = entity.posZ - this.posZ;
            final double n3 = n * n + n2 * n2;
            if (n3 >= 9.999999747378752E-5) {
                final double n4 = MathHelper.sqrt_double(n3);
                final double n5 = n / n4;
                final double n6 = n2 / n4;
                double n7 = 1.0 / n4;
                if (n7 > 1.0) {
                    n7 = 1.0;
                }
                final double n8 = n5 * n7;
                final double n9 = n6 * n7;
                final double n10 = n8 * 0.10000000149011612;
                final double n11 = n9 * 0.10000000149011612;
                final double n12 = n10 * (1.0f - this.entityCollisionReduction);
                final double n13 = n11 * (1.0f - this.entityCollisionReduction);
                final double n14 = n12 * 0.5;
                final double n15 = n13 * 0.5;
                if (entity instanceof EntityMinecart) {
                    if (Math.abs(new Vec3(entity.posX - this.posX, 0.0, entity.posZ - this.posZ).normalize().dotProduct(new Vec3(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)).normalize())) < 0.800000011920929) {
                        return;
                    }
                    final double n16 = entity.motionX + this.motionX;
                    final double n17 = entity.motionZ + this.motionZ;
                    if (((EntityMinecart)entity).getMinecartType() == EnumMinecartType.FURNACE && this.getMinecartType() != EnumMinecartType.FURNACE) {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(entity.motionX - n14, 0.0, entity.motionZ - n15);
                        entity.motionX *= 0.949999988079071;
                        entity.motionZ *= 0.949999988079071;
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else if (((EntityMinecart)entity).getMinecartType() != EnumMinecartType.FURNACE && this.getMinecartType() == EnumMinecartType.FURNACE) {
                        entity.motionX *= 0.20000000298023224;
                        entity.motionZ *= 0.20000000298023224;
                        entity.addVelocity(this.motionX + n14, 0.0, this.motionZ + n15);
                        this.motionX *= 0.949999988079071;
                        this.motionZ *= 0.949999988079071;
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        final double n18 = n16 / 2.0;
                        final double n19 = n17 / 2.0;
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(n18 - n14, 0.0, n19 - n15);
                        entity.motionX *= 0.20000000298023224;
                        entity.motionZ *= 0.20000000298023224;
                        entity.addVelocity(n18 + n14, 0.0, n19 + n15);
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                }
                else {
                    this.addVelocity(-n14, 0.0, -n15);
                    entity.addVelocity(n14 / 4.0, 0.0, n15 / 4.0);
                }
            }
        }
    }
    
    @Override
    public void setCustomNameTag(final String entityName) {
        this.entityName = entityName;
    }
    
    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(0xA2 ^ 0xA8);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.hasDisplayTile()) {
            nbtTagCompound.setBoolean(EntityMinecart.I[0xB6 ^ 0xBD], " ".length() != 0);
            final IBlockState displayTile = this.getDisplayTile();
            final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(displayTile.getBlock());
            final String s = EntityMinecart.I[0x21 ^ 0x2D];
            String string;
            if (resourceLocation == null) {
                string = EntityMinecart.I[0xB4 ^ 0xB9];
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                string = resourceLocation.toString();
            }
            nbtTagCompound.setString(s, string);
            nbtTagCompound.setInteger(EntityMinecart.I[0x74 ^ 0x7A], displayTile.getBlock().getMetaFromState(displayTile));
            nbtTagCompound.setInteger(EntityMinecart.I[0x4E ^ 0x41], this.getDisplayTileOffset());
        }
        if (this.entityName != null && this.entityName.length() > 0) {
            nbtTagCompound.setString(EntityMinecart.I[0xD5 ^ 0xC5], this.entityName);
        }
    }
    
    public void func_174899_a(final IBlockState blockState) {
        this.getDataWatcher().updateObject(0xA4 ^ 0xB0, Block.getStateId(blockState));
        this.setHasDisplayTile(" ".length() != 0);
    }
    
    @Override
    public void onUpdate() {
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - " ".length());
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection(EntityMinecart.I[" ".length()]);
            final MinecraftServer minecraftServer = ((WorldServer)this.worldObj).getMinecraftServer();
            final int maxInPortalTime = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (minecraftServer.getAllowNether()) {
                    if (this.ridingEntity == null) {
                        final int portalCounter = this.portalCounter;
                        this.portalCounter = portalCounter + " ".length();
                        if (portalCounter >= maxInPortalTime) {
                            this.portalCounter = maxInPortalTime;
                            this.timeUntilPortal = this.getPortalCooldown();
                            int length;
                            if (this.worldObj.provider.getDimensionId() == -" ".length()) {
                                length = "".length();
                                "".length();
                                if (1 <= -1) {
                                    throw null;
                                }
                            }
                            else {
                                length = -" ".length();
                            }
                            this.travelToDimension(length);
                        }
                    }
                    this.inPortal = ("".length() != 0);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= (0x18 ^ 0x1C);
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = "".length();
                }
            }
            if (this.timeUntilPortal > 0) {
                this.timeUntilPortal -= " ".length();
            }
            this.worldObj.theProfiler.endSection();
        }
        if (this.worldObj.isRemote) {
            if (this.turnProgress > 0) {
                final double n = this.posX + (this.minecartX - this.posX) / this.turnProgress;
                final double n2 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
                final double n3 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
                this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw) / this.turnProgress);
                this.rotationPitch += (float)((this.minecartPitch - this.rotationPitch) / this.turnProgress);
                this.turnProgress -= " ".length();
                this.setPosition(n, n2, n3);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            final int floor_double = MathHelper.floor_double(this.posX);
            int floor_double2 = MathHelper.floor_double(this.posY);
            final int floor_double3 = MathHelper.floor_double(this.posZ);
            if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(floor_double, floor_double2 - " ".length(), floor_double3))) {
                --floor_double2;
            }
            final BlockPos blockPos = new BlockPos(floor_double, floor_double2, floor_double3);
            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
            if (BlockRailBase.isRailBlock(blockState)) {
                this.func_180460_a(blockPos, blockState);
                if (blockState.getBlock() == Blocks.activator_rail) {
                    this.onActivatorRailPass(floor_double, floor_double2, floor_double3, blockState.getValue((IProperty<Boolean>)BlockRailPowered.POWERED));
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                }
            }
            else {
                this.moveDerailedMinecart();
            }
            this.doBlockCollisions();
            this.rotationPitch = 0.0f;
            final double n4 = this.prevPosX - this.posX;
            final double n5 = this.prevPosZ - this.posZ;
            if (n4 * n4 + n5 * n5 > 0.001) {
                this.rotationYaw = (float)(MathHelper.func_181159_b(n5, n4) * 180.0 / 3.141592653589793);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            final double n6 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
            if (n6 < -170.0 || n6 >= 170.0) {
                this.rotationYaw += 180.0f;
                int isInReverse;
                if (this.isInReverse) {
                    isInReverse = "".length();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    isInReverse = " ".length();
                }
                this.isInReverse = (isInReverse != 0);
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final Iterator<Entity> iterator = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224)).iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Entity entity = iterator.next();
                if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
                    entity.applyEntityCollision(this);
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                if (this.riddenByEntity.ridingEntity == this) {
                    this.riddenByEntity.ridingEntity = null;
                }
                this.riddenByEntity = null;
            }
            this.handleWaterMovement();
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Vec3 func_70489_a(double n, double n2, double n3) {
        final int floor_double = MathHelper.floor_double(n);
        int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(floor_double, floor_double2 - " ".length(), floor_double3))) {
            --floor_double2;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3));
        if (BlockRailBase.isRailBlock(blockState)) {
            final int[][] array = EntityMinecart.matrix[blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty()).getMetadata()];
            final double n4 = floor_double + 0.5 + array["".length()]["".length()] * 0.5;
            final double n5 = floor_double2 + 0.0625 + array["".length()][" ".length()] * 0.5;
            final double n6 = floor_double3 + 0.5 + array["".length()]["  ".length()] * 0.5;
            final double n7 = floor_double + 0.5 + array[" ".length()]["".length()] * 0.5;
            final double n8 = floor_double2 + 0.0625 + array[" ".length()][" ".length()] * 0.5;
            final double n9 = floor_double3 + 0.5 + array[" ".length()]["  ".length()] * 0.5;
            final double n10 = n7 - n4;
            final double n11 = (n8 - n5) * 2.0;
            final double n12 = n9 - n6;
            double n13;
            if (n10 == 0.0) {
                n = floor_double + 0.5;
                n13 = n3 - floor_double3;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if (n12 == 0.0) {
                n3 = floor_double3 + 0.5;
                n13 = n - floor_double;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                n13 = ((n - n4) * n10 + (n3 - n6) * n12) * 2.0;
            }
            n = n4 + n10 * n13;
            n2 = n5 + n11 * n13;
            n3 = n6 + n12 * n13;
            if (n11 < 0.0) {
                ++n2;
            }
            if (n11 > 0.0) {
                n2 += 0.5;
            }
            return new Vec3(n, n2, n3);
        }
        return null;
    }
    
    public boolean hasDisplayTile() {
        if (this.getDataWatcher().getWatchableObjectByte(0x86 ^ 0x90) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final float n = this.width / 2.0f;
        this.setEntityBoundingBox(new AxisAlignedBB(posX - n, posY, posZ - n, posX + n, posY + this.height, posZ + n));
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection() {
        final int[] $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection = EntityMinecart.$SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
        if ($switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection != null) {
            return $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2 = new int[BlockRailBase.EnumRailDirection.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = "   ".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = (0x16 ^ 0x13);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = (0x1B ^ 0x1D);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = (0x56 ^ 0x52);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.EAST_WEST.ordinal()] = "  ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.NORTH_EAST.ordinal()] = (0x18 ^ 0x12);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.NORTH_SOUTH.ordinal()] = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.NORTH_WEST.ordinal()] = (0x71 ^ 0x78);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.SOUTH_EAST.ordinal()] = (0xC3 ^ 0xC4);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[BlockRailBase.EnumRailDirection.SOUTH_WEST.ordinal()] = (0x4D ^ 0x45);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        return EntityMinecart.$SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection = $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2;
    }
    
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
    }
    
    public void setRollingDirection(final int n) {
        this.dataWatcher.updateObject(0x4A ^ 0x58, n);
    }
    
    @Override
    public void setDead() {
        super.setDead();
    }
    
    @Override
    public double getMountedYOffset() {
        return 0.0;
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.entityName != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getDisplayTileOffset() {
        int n;
        if (!this.hasDisplayTile()) {
            n = this.getDefaultDisplayTileOffset();
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            n = this.getDataWatcher().getWatchableObjectInt(0x6A ^ 0x7F);
        }
        return n;
    }
    
    public void killMinecart(final DamageSource damageSource) {
        this.setDead();
        if (this.worldObj.getGameRules().getBoolean(EntityMinecart.I["".length()])) {
            final ItemStack itemStack = new ItemStack(Items.minecart, " ".length());
            if (this.entityName != null) {
                itemStack.setStackDisplayName(this.entityName);
            }
            this.entityDropItem(itemStack, 0.0f);
        }
    }
    
    public enum EnumMinecartType
    {
        private static final EnumMinecartType[] ENUM$VALUES;
        private static final String[] I;
        
        SPAWNER(EnumMinecartType.I[0x36 ^ 0x3E], 0x49 ^ 0x4D, 0x50 ^ 0x54, EnumMinecartType.I[0x66 ^ 0x6F]), 
        HOPPER(EnumMinecartType.I[0xA0 ^ 0xAA], 0x18 ^ 0x1D, 0x25 ^ 0x20, EnumMinecartType.I[0x42 ^ 0x49]), 
        RIDEABLE(EnumMinecartType.I["".length()], "".length(), "".length(), EnumMinecartType.I[" ".length()]), 
        CHEST(EnumMinecartType.I["  ".length()], " ".length(), " ".length(), EnumMinecartType.I["   ".length()]);
        
        private final String name;
        private static final Map<Integer, EnumMinecartType> ID_LOOKUP;
        
        FURNACE(EnumMinecartType.I[0x84 ^ 0x80], "  ".length(), "  ".length(), EnumMinecartType.I[0x4A ^ 0x4F]), 
        TNT(EnumMinecartType.I[0x5C ^ 0x5A], "   ".length(), "   ".length(), EnumMinecartType.I[0xA6 ^ 0xA1]);
        
        private final int networkID;
        
        COMMAND_BLOCK(EnumMinecartType.I[0x48 ^ 0x44], 0x2B ^ 0x2D, 0xBA ^ 0xBC, EnumMinecartType.I[0x31 ^ 0x3C]);
        
        public String getName() {
            return this.name;
        }
        
        static {
            I();
            final EnumMinecartType[] enum$VALUES = new EnumMinecartType[0x99 ^ 0x9E];
            enum$VALUES["".length()] = EnumMinecartType.RIDEABLE;
            enum$VALUES[" ".length()] = EnumMinecartType.CHEST;
            enum$VALUES["  ".length()] = EnumMinecartType.FURNACE;
            enum$VALUES["   ".length()] = EnumMinecartType.TNT;
            enum$VALUES[0x3B ^ 0x3F] = EnumMinecartType.SPAWNER;
            enum$VALUES[0x3 ^ 0x6] = EnumMinecartType.HOPPER;
            enum$VALUES[0x9A ^ 0x9C] = EnumMinecartType.COMMAND_BLOCK;
            ENUM$VALUES = enum$VALUES;
            ID_LOOKUP = Maps.newHashMap();
            final EnumMinecartType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (!true) {
                throw null;
            }
            while (i < length) {
                final EnumMinecartType enumMinecartType = values[i];
                EnumMinecartType.ID_LOOKUP.put(enumMinecartType.getNetworkID(), enumMinecartType);
                ++i;
            }
        }
        
        private EnumMinecartType(final String s, final int n, final int networkID, final String name) {
            this.networkID = networkID;
            this.name = name;
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
                if (2 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static EnumMinecartType byNetworkID(final int n) {
            final EnumMinecartType enumMinecartType = EnumMinecartType.ID_LOOKUP.get(n);
            EnumMinecartType rideable;
            if (enumMinecartType == null) {
                rideable = EnumMinecartType.RIDEABLE;
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                rideable = enumMinecartType;
            }
            return rideable;
        }
        
        public int getNetworkID() {
            return this.networkID;
        }
        
        private static void I() {
            (I = new String[0xB ^ 0x5])["".length()] = I("\u0014;0)%\u0004>1", "Frtld");
            EnumMinecartType.I[" ".length()] = I("*?\u0000\f!\u0006$\u001a;+\u00033\u000f\u000b.\u0002", "gVniB");
            EnumMinecartType.I["  ".length()] = I("\u0013\u0019\u0006\"-", "PQCqy");
            EnumMinecartType.I["   ".length()] = I("\u001b\n\u001d+%7\u0011\u0007\r.3\u0010\u0007", "VcsNF");
            EnumMinecartType.I[0x3F ^ 0x3B] = I("\u0002\u0000;\u0016,\u0007\u0010", "DUiXm");
            EnumMinecartType.I[0x22 ^ 0x27] = I("$\b\u001d<\u0013\b\u0013\u0007\u001f\u0005\u001b\u000f\u0012:\u0015", "iasYp");
            EnumMinecartType.I[0x82 ^ 0x84] = I("3\u0007\u0005", "gIQhl");
            EnumMinecartType.I[0x64 ^ 0x63] = I("\u001b\r\u0001\".7\u0016\u001b\u0013\u0003\u0002", "VdoGM");
            EnumMinecartType.I[0x74 ^ 0x7C] = I("\u000b\u0006\b3\u0014\u001d\u0004", "XVIdZ");
            EnumMinecartType.I[0xAE ^ 0xA7] = I("\u0019\u001a>\n\u00155\u0001$<\u00065\u0004>\n\u0004", "TsPov");
            EnumMinecartType.I[0x27 ^ 0x2D] = I("?\u0001\u0013\u0013\u0016%", "wNCCS");
            EnumMinecartType.I[0xAE ^ 0xA5] = I("\u0005/%4*)4?\u0019&86.#", "HFKQI");
            EnumMinecartType.I[0x45 ^ 0x49] = I("\u001b9\n\u0014%\u00162\u0018\u001b(\u00175\f", "XvGYd");
            EnumMinecartType.I[0x6B ^ 0x66] = I("\u001a\u0007\u001f\u0016,6\u001c\u00050 :\u0003\u0010\u001d+\u0015\u0002\u001e\u0010$", "WnqsO");
        }
    }
}
