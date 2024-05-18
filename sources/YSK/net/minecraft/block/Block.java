package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;

public class Block
{
    public static final SoundType soundTypeGlass;
    protected double maxY;
    protected int lightOpacity;
    public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> blockRegistry;
    public float blockParticleGravity;
    protected float blockHardness;
    protected final Material blockMaterial;
    private IBlockState defaultBlockState;
    protected double minX;
    public static final SoundType soundTypeSnow;
    public static final SoundType soundTypeLadder;
    public static final SoundType soundTypeAnvil;
    protected final BlockState blockState;
    protected boolean fullBlock;
    public static final SoundType soundTypeSand;
    protected double maxX;
    private CreativeTabs displayOnCreativeTab;
    public static final SoundType SLIME_SOUND;
    private static final ResourceLocation AIR_ID;
    protected double maxZ;
    public static final SoundType soundTypeWood;
    public SoundType stepSound;
    public static final SoundType soundTypePiston;
    protected boolean needsRandomTick;
    public static final SoundType soundTypeGrass;
    private static final String[] I;
    public static final SoundType soundTypeCloth;
    public static final SoundType soundTypeMetal;
    protected double minY;
    protected boolean useNeighborBrightness;
    protected double minZ;
    public static final SoundType soundTypeStone;
    public static final SoundType soundTypeGravel;
    protected final MapColor field_181083_K;
    protected boolean translucent;
    protected boolean enableStats;
    protected boolean isBlockContainer;
    public float slipperiness;
    public static final ObjectIntIdentityMap BLOCK_STATE_IDS;
    protected int lightValue;
    private String unlocalizedName;
    protected float blockResistance;
    
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty["".length()]);
    }
    
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState();
    }
    
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, Vec3 addVector, Vec3 addVector2) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        addVector = addVector.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        addVector2 = addVector2.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        Vec3 intermediateWithXValue = addVector.getIntermediateWithXValue(addVector2, this.minX);
        Vec3 intermediateWithXValue2 = addVector.getIntermediateWithXValue(addVector2, this.maxX);
        Vec3 intermediateWithYValue = addVector.getIntermediateWithYValue(addVector2, this.minY);
        Vec3 intermediateWithYValue2 = addVector.getIntermediateWithYValue(addVector2, this.maxY);
        Vec3 intermediateWithZValue = addVector.getIntermediateWithZValue(addVector2, this.minZ);
        Vec3 intermediateWithZValue2 = addVector.getIntermediateWithZValue(addVector2, this.maxZ);
        if (!this.isVecInsideYZBounds(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }
        if (!this.isVecInsideYZBounds(intermediateWithXValue2)) {
            intermediateWithXValue2 = null;
        }
        if (!this.isVecInsideXZBounds(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }
        if (!this.isVecInsideXZBounds(intermediateWithYValue2)) {
            intermediateWithYValue2 = null;
        }
        if (!this.isVecInsideXYBounds(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }
        if (!this.isVecInsideXYBounds(intermediateWithZValue2)) {
            intermediateWithZValue2 = null;
        }
        Vec3 vec3 = null;
        if (intermediateWithXValue != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithXValue) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithXValue;
        }
        if (intermediateWithXValue2 != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithXValue2) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithXValue2;
        }
        if (intermediateWithYValue != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithYValue) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithYValue;
        }
        if (intermediateWithYValue2 != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithYValue2) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithYValue2;
        }
        if (intermediateWithZValue != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithZValue) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithZValue;
        }
        if (intermediateWithZValue2 != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithZValue2) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithZValue2;
        }
        if (vec3 == null) {
            return null;
        }
        EnumFacing enumFacing = null;
        if (vec3 == intermediateWithXValue) {
            enumFacing = EnumFacing.WEST;
        }
        if (vec3 == intermediateWithXValue2) {
            enumFacing = EnumFacing.EAST;
        }
        if (vec3 == intermediateWithYValue) {
            enumFacing = EnumFacing.DOWN;
        }
        if (vec3 == intermediateWithYValue2) {
            enumFacing = EnumFacing.UP;
        }
        if (vec3 == intermediateWithZValue) {
            enumFacing = EnumFacing.NORTH;
        }
        if (vec3 == intermediateWithZValue2) {
            enumFacing = EnumFacing.SOUTH;
        }
        return new MovingObjectPosition(vec3.addVector(blockPos.getX(), blockPos.getY(), blockPos.getZ()), enumFacing, blockPos);
    }
    
    public Block setCreativeTab(final CreativeTabs displayOnCreativeTab) {
        this.displayOnCreativeTab = displayOnCreativeTab;
        return this;
    }
    
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
    }
    
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
    }
    
    public boolean isFullBlock() {
        return this.fullBlock;
    }
    
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ);
    }
    
    public int tickRate(final World world) {
        return 0xBF ^ 0xB5;
    }
    
    public int getLightValue() {
        return this.lightValue;
    }
    
    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }
    
    public int getLightOpacity() {
        return this.lightOpacity;
    }
    
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable();
    }
    
    private boolean isVecInsideXYBounds(final Vec3 vec3) {
        int n;
        if (vec3 == null) {
            n = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY) {
            n = " ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getStateFromMeta(n4);
    }
    
    public boolean hasTileEntity() {
        return this.isBlockContainer;
    }
    
    public boolean getUseNeighborBrightness() {
        return this.useNeighborBrightness;
    }
    
    protected void dropXpOnBlockBreak(final World world, final BlockPos blockPos, int i) {
        if (!world.isRemote) {
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (i > 0) {
                final int xpSplit = EntityXPOrb.getXPSplit(i);
                i -= xpSplit;
                world.spawnEntityInWorld(new EntityXPOrb(world, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, xpSplit));
            }
        }
    }
    
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.DOWN && this.minY > 0.0) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.UP && this.maxY < 1.0) {
            n = " ".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.NORTH && this.minZ > 0.0) {
            n = " ".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.SOUTH && this.maxZ < 1.0) {
            n = " ".length();
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.WEST && this.minX > 0.0) {
            n = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.EAST && this.maxX < 1.0) {
            n = " ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (blockAccess.getBlockState(blockPos).getBlock().isOpaqueCube()) {
            n = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public boolean isVisuallyOpaque() {
        if (this.blockMaterial.blocksMovement() && this.isFullCube()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
    }
    
    private boolean isVecInsideXZBounds(final Vec3 vec3) {
        int n;
        if (vec3 == null) {
            n = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else if (vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ) {
            n = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public final double getBlockBoundsMaxX() {
        return this.maxX;
    }
    
    public void onLanded(final World world, final Entity entity) {
        entity.motionY = 0.0;
    }
    
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
    }
    
    private boolean isVecInsideYZBounds(final Vec3 vec3) {
        int n;
        if (vec3 == null) {
            n = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else if (vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ) {
            n = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    protected Block setBlockUnbreakable() {
        this.setHardness(-1.0f);
        return this;
    }
    
    @Override
    public String toString() {
        return Block.I[0x95 ^ 0x86] + Block.blockRegistry.getNameForObject(this) + Block.I[0x78 ^ 0x6C];
    }
    
    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }
    
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return blockState;
    }
    
    protected Block setLightLevel(final float n) {
        this.lightValue = (int)(15.0f * n);
        return this;
    }
    
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return "".length();
    }
    
    private static void registerBlock(final int n, final String s, final Block block) {
        registerBlock(n, new ResourceLocation(s), block);
    }
    
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState;
    }
    
    public static int getStateId(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        return getIdFromBlock(block) + (block.getMetaFromState(blockState) << (0x43 ^ 0x4F));
    }
    
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return "".length() != 0;
    }
    
    public Block setUnlocalizedName(final String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }
    
    protected final void setBlockBounds(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.minX = n;
        this.minY = n2;
        this.minZ = n3;
        this.maxX = n4;
        this.maxY = n5;
        this.maxZ = n6;
    }
    
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
    }
    
    static {
        I();
        AIR_ID = new ResourceLocation(Block.I["".length()]);
        blockRegistry = new RegistryNamespacedDefaultedByKey<ResourceLocation, Block>(Block.AIR_ID);
        BLOCK_STATE_IDS = new ObjectIntIdentityMap();
        soundTypeStone = new SoundType(Block.I[" ".length()], 1.0f, 1.0f);
        soundTypeWood = new SoundType(Block.I["  ".length()], 1.0f, 1.0f);
        soundTypeGravel = new SoundType(Block.I["   ".length()], 1.0f, 1.0f);
        soundTypeGrass = new SoundType(Block.I[0x57 ^ 0x53], 1.0f, 1.0f);
        soundTypePiston = new SoundType(Block.I[0xAE ^ 0xAB], 1.0f, 1.0f);
        soundTypeMetal = new SoundType(Block.I[0x1E ^ 0x18], 1.0f, 1.5f);
        soundTypeGlass = new SoundType(1.0f, 1.0f) {
            private static final String[] I;
            
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
                    if (2 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String getBreakSound() {
                return Block$1.I["".length()];
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("4=%Z\u0012<51\u0007", "PTBtu");
                Block$1.I[" ".length()] = I("5;\u0012\tj5;\u0018\u0017!", "FOwyD");
            }
            
            static {
                I();
            }
            
            @Override
            public String getPlaceSound() {
                return Block$1.I[" ".length()];
            }
        };
        soundTypeCloth = new SoundType(Block.I[0x2F ^ 0x27], 1.0f, 1.0f);
        soundTypeSand = new SoundType(Block.I[0x62 ^ 0x6B], 1.0f, 1.0f);
        soundTypeSnow = new SoundType(Block.I[0x6E ^ 0x64], 1.0f, 1.0f);
        soundTypeLadder = new SoundType(1.0f, 1.0f) {
            private static final String[] I;
            
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
            
            @Override
            public String getBreakSound() {
                return Block$2.I["".length()];
            }
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("),,G\u0013\"*/", "MEKid");
            }
        };
        soundTypeAnvil = new SoundType(0.3f, 1.0f) {
            private static final String[] I;
            
            @Override
            public String getBreakSound() {
                return Block$3.I["".length()];
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u001d!&`*\r'/+", "yHANY");
                Block$3.I[" ".length()] = I("\u0000'\u001b\u0005\u0016\u001fh\u0014\u000f\u000f\u001b**\r\u0018\u001c\"", "rFuay");
            }
            
            static {
                I();
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
                    if (2 == -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String getPlaceSound() {
                return Block$3.I[" ".length()];
            }
        };
        SLIME_SOUND = new SoundType(1.0f, 1.0f) {
            private static final String[] I;
            
            @Override
            public String getStepSound() {
                return Block$4.I["  ".length()];
            }
            
            @Override
            public String getBreakSound() {
                return Block$4.I["".length()];
            }
            
            @Override
            public String getPlaceSound() {
                return Block$4.I[" ".length()];
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
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("\"\u001b&y\u0005#\u001d)2X-\u001d#", "OtDWv");
                Block$4.I[" ".length()] = I("\u0014#\u000eF$\u0015%\u0001\ry\u001b%\u000b", "yLlhW");
                Block$4.I["  ".length()] = I("+=\u0010k\u001a*;\u001f G5?\u0013)\u0005", "FRrEi");
            }
        };
    }
    
    public float getAmbientOcclusionLightValue() {
        float n;
        if (this.isBlockNormalCube()) {
            n = 0.2f;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = 1.0f;
        }
        return n;
    }
    
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return vec3;
    }
    
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(this);
    }
    
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return "".length() != 0;
    }
    
    public int getRenderColor(final IBlockState blockState) {
        return 11272663 + 7757224 - 16459913 + 14207241;
    }
    
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return "".length();
    }
    
    protected Block disableStats() {
        this.enableStats = ("".length() != 0);
        return this;
    }
    
    public BlockState getBlockState() {
        return this.blockState;
    }
    
    public boolean isTranslucent() {
        return this.translucent;
    }
    
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            final int quantityDroppedWithBonus = this.quantityDroppedWithBonus(n2, world.rand);
            int i = "".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (i < quantityDroppedWithBonus) {
                if (world.rand.nextFloat() <= n) {
                    final Item itemDropped = this.getItemDropped(blockState, world.rand, n2);
                    if (itemDropped != null) {
                        spawnAsEntity(world, blockPos, new ItemStack(itemDropped, " ".length(), this.damageDropped(blockState)));
                    }
                }
                ++i;
            }
        }
    }
    
    public float getBlockHardness(final World world, final BlockPos blockPos) {
        return this.blockHardness;
    }
    
    public int getBlockColor() {
        return 11015076 + 4711912 - 6313907 + 7364134;
    }
    
    protected Block setTickRandomly(final boolean needsRandomTick) {
        this.needsRandomTick = needsRandomTick;
        return this;
    }
    
    public void fillWithRain(final World world, final BlockPos blockPos) {
    }
    
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        entity.fall(n, 1.0f);
    }
    
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return this.isCollidable();
    }
    
    public boolean canReplace(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final ItemStack itemStack) {
        return this.canPlaceBlockOnSide(world, blockPos, enumFacing);
    }
    
    private static void I() {
        (I = new String[170 + 163 - 188 + 266])["".length()] = I(" 0\u0006", "AYtfa");
        Block.I[" ".length()] = I(")6\u0001\u0000 ", "ZBnnE");
        Block.I["  ".length()] = I("\u001a&(\b", "mIGlg");
        Block.I["   ".length()] = I(",;\u00054\u0012'", "KIdBw");
        Block.I[0x3D ^ 0x39] = I("\u0013$\u0015\u00179", "tVtdJ");
        Block.I[0x9F ^ 0x9A] = I("\u0005\u0017>\u0018\u0004", "vcQva");
        Block.I[0x4B ^ 0x4D] = I(":\u000e\u0007<\u0010", "IzhRu");
        Block.I[0x5A ^ 0x5D] = I("69-\u001e\u000b", "EMBpn");
        Block.I[0x29 ^ 0x21] = I("\u0001)\u001e\u0005'", "bEqqO");
        Block.I[0x83 ^ 0x8A] = I("\u001b--!", "hLCEQ");
        Block.I[0x85 ^ 0x8F] = I("#\t\u001e\u000e", "PgqyZ");
        Block.I[0xD ^ 0x6] = I("#\u0015\u00062 =", "OtbVE");
        Block.I[0x0 ^ 0xC] = I("$\u001f\u0018\u001c+", "EqnuG");
        Block.I[0x6C ^ 0x61] = I("*+\u001f.5", "YGvCP");
        Block.I[0x76 ^ 0x78] = I("\n\f/q3n\b/90n\u000b.!g:\fa5( \u0015$$3n", "NcAVG");
        Block.I[0xB6 ^ 0xB9] = I("O\u001a(5\u000fO\u0011'\"\u000bO\u001c(\"\u0005AVg", "oxIVd");
        Block.I[0x36 ^ 0x26] = I("\u0013\u00023,\u001a\u0012)\u0015*\u0006\u0004", "wmgEv");
        Block.I[0xA0 ^ 0xB1] = I("~\"7+\u0017", "PLVFr");
        Block.I[0x60 ^ 0x72] = I("\u001c&\u0018\u001cX", "hOtyv");
        Block.I[0x71 ^ 0x62] = I("\u0018>,\u0011=!", "ZRCrV");
        Block.I[0x20 ^ 0x34] = I(".", "SRXbL");
        Block.I[0x22 ^ 0x37] = I("\u0002\u001e=", "cwOKz");
        Block.I[0x7A ^ 0x6C] = I("\"3\u0016)\u0000", "QGyGe");
        Block.I[0x45 ^ 0x52] = I("\u0017\u0013\u001e#\u001f", "dgqMz");
        Block.I[0x55 ^ 0x4D] = I("-!;9\u0015", "JSZJf");
        Block.I[0x86 ^ 0x9F] = I("\u000f*76\u001b", "hXVEh");
        Block.I[0x7E ^ 0x64] = I("\u0000;?\u0004", "dRMpF");
        Block.I[0x5E ^ 0x45] = I("\u0015\u0001\u0005 ", "qhwTI");
        Block.I[0x42 ^ 0x5E] = I("\u0016\u001e=(\u0002\u0007\u0018;%\f", "ejRFg");
        Block.I[0x46 ^ 0x5B] = I("!\u001d5\u0010\"'\u0001#\u001d '", "BrWrN");
        Block.I[0xB2 ^ 0xAC] = I("\u0000%>%", "wJQAw");
        Block.I[0x55 ^ 0x4A] = I("\u0018\u001d.\u000b\u0004\u001b", "hqOeo");
        Block.I[0xAA ^ 0x8A] = I("=\u0011\t\u001c% \u0017", "NpypL");
        Block.I[0x12 ^ 0x33] = I(">\u0006\u0012\u000b>#\u0000", "MgbgW");
        Block.I[0x1D ^ 0x3F] = I(":'%\u000b+;)", "XBAyD");
        Block.I[0x6A ^ 0x49] = I("%\u00127\u0014\u0000$\u001c", "GwSfo");
        Block.I[0x1 ^ 0x25] = I("\b\";<\u0006\u0000)\u000b<\u000e\u001a+&", "nNTKo");
        Block.I[0x93 ^ 0xB6] = I(" 1!?8", "WPUZJ");
        Block.I[0x29 ^ 0xF] = I("\u0007\u0016\u0001\u0012\u001d", "pwuwo");
        Block.I[0xB3 ^ 0x94] = I("'0$-\u001a", "PQPHh");
        Block.I[0x23 ^ 0xB] = I("-\u001c!9?%\u0017\u0011\"7=\u0011", "KpNNV");
        Block.I[0x8F ^ 0xA6] = I("6\u0002< ", "ZcJAi");
        Block.I[0x4E ^ 0x64] = I("%-\u001f\u001b", "ILizR");
        Block.I[0x77 ^ 0x5C] = I("\u0019 \u0019'", "uAoFM");
        Block.I[0xD ^ 0x21] = I("\u0000'\u001d-", "sFsIB");
        Block.I[0x5F ^ 0x72] = I("\u0017&>\t", "dGPms");
        Block.I[0x6C ^ 0x42] = I(".'7\u001e1%", "IUVhT");
        Block.I[0x13 ^ 0x3C] = I("\u0016'\u0002=\u0017\u001d", "qUcKr");
        Block.I[0xA7 ^ 0x97] = I("\u000e&=+\n\u0006;4", "iIQOU");
        Block.I[0x9C ^ 0xAD] = I("\u000b$\u0011\n\"\b2", "dVtMM");
        Block.I[0x2 ^ 0x30] = I("&\u0007\b+\u001b \u0007\u0002", "OugED");
        Block.I[0x91 ^ 0xA2] = I("\u0004\u0011\u001115\u0004\r", "kctxG");
        Block.I[0x3D ^ 0x9] = I("\n\u0004+55\u0006\u0019/", "ikJYj");
        Block.I[0x7E ^ 0x4B] = I("';\u00002#)%", "HIeqL");
        Block.I[0xA6 ^ 0x90] = I("\t;\f", "eTkEe");
        Block.I[0x55 ^ 0x62] = I("#$*", "OKMsT");
        Block.I[0x9 ^ 0x31] = I("\u000b\u0016\u00040+\u0014", "gseFN");
        Block.I[0x65 ^ 0x5C] = I("=3\u0016<\"\"", "QVwJG");
        Block.I[0x62 ^ 0x58] = I(">\u0007+\b\u0011(", "MwDfv");
        Block.I[0x6E ^ 0x55] = I("=\u0001$\u001a\u001f+", "NqKtx");
        Block.I[0xBF ^ 0x83] = I(",\u0019\u000e\u0004\u001f", "Kuowl");
        Block.I[0x24 ^ 0x19] = I("?+\u00132\u0004", "XGrAw");
        Block.I[0x32 ^ 0xC] = I("\t'5\u00029:)7\u000e", "eFEkJ");
        Block.I[0x31 ^ 0xE] = I("\u0002\u001b\b#\r\u001d\u0000\u001e", "mimol");
        Block.I[0x4D ^ 0xD] = I("8\u0002\n\u0005\u0002\u000b\u0001\u0016\u0003\u0012?", "Tczlq");
        Block.I[0xA ^ 0x4B] = I("8+\"\b;\u0016&=\u0002#", "ZGMkP");
        Block.I[0xFC ^ 0xBE] = I("%\u0019%\u0003\n/\u00033\u0001", "ApVso");
        Block.I[0xC6 ^ 0x85] = I("\u000b\u001a6&,\u0001\u0000 $", "osEVI");
        Block.I[0x4C ^ 0x8] = I("\u00077\u0018\u001c \u00009\u0018\u001d", "tVvxs");
        Block.I[0x29 ^ 0x6C] = I("\u001e\n\n<+\u0019\u0004\n=", "mkdXX");
        Block.I[0xE2 ^ 0xA4] = I("-%\u0000\u0011\u001b/%\u0017\u001f", "CJtty");
        Block.I[0xE2 ^ 0xA5] = I("(&\u0006\u0005*\u0007?\u001a\u000f\"", "ESulI");
        Block.I[0x8F ^ 0xC7] = I("*/5", "HJQoq");
        Block.I[0x8C ^ 0xC5] = I("\u0010?#", "rZGDn");
        Block.I[0xF9 ^ 0xB3] = I("\u0016 ?/\u0004\u001f\u0010!*\b\u001d", "qOSKa");
        Block.I[0x3B ^ 0x70] = I("\u000f66+\u0015\u0006\u000b;&\u001c", "hYZOp");
        Block.I[0x77 ^ 0x3B] = I("\u00057\u0002&1\u0015=\u0004\u001c \u0000;\u001a", "aRvCR");
        Block.I[0xE3 ^ 0xAE] = I("-\u001d\u00114\u001b=\u0017\u0017\u0003\u0019 \u0014", "IxeQx");
        Block.I[0xF3 ^ 0xBD] = I("\u0016\"+\u0004)\u001c\t2\u000e1\u00119,", "eVBgB");
        Block.I[0x35 ^ 0x7A] = I("\u0001\u0000 \u001f\t\u001f:'\u0002\u0005\u001a\u0010\u0011\n\u0015\u0014", "qiSkf");
        Block.I[0x50 ^ 0x0] = I("!\r\u0016", "Vhtpu");
        Block.I[0x35 ^ 0x64] = I("-\u0016#", "ZsANa");
        Block.I[0x77 ^ 0x25] = I("55!\u0014535>\u000b", "ATMxR");
        Block.I[0x61 ^ 0x32] = I("\u00122/\u00040\u001420\u001b", "fSChW");
        Block.I[0xF4 ^ 0xA0] = I("\u0003 \u00034;\u00126\n", "gEbPY");
        Block.I[0x29 ^ 0x7C] = I("\u0010\u0013\u00111+\u0001\u0005\u0018", "tvpUI");
        Block.I[0x25 ^ 0x73] = I("23$\r\u001e,", "BZWyq");
        Block.I[0x22 ^ 0x75] = I("\u0003*5\u00118\u001d\u0001'\u00162", "sCFeW");
        Block.I[0xE2 ^ 0xBA] = I(":1:\r\t$\u0007!\u001c\u0007.", "JXIyf");
        Block.I[0x67 ^ 0x3E] = I("\u0012\u0018*\u0013\u0003\f38\u0014\t", "bqYgl");
        Block.I[0xFA ^ 0xA0] = I("\u0007<\u0000\"", "pSoNF");
        Block.I[0x9E ^ 0xC5] = I("\u001a/\f$\u001e", "yCcPv");
        Block.I[0x2B ^ 0x77] = I("\u0004*:\u001d\u001b\u001a\u001c,\u0011\u0000\u0011-:\u0000\u001b\u001a", "tCIit");
        Block.I[0x60 ^ 0x3D] = I("\u000e\u00109*)\u0000*3*)\u0000\u0010'", "wuUFF");
        Block.I[0xD1 ^ 0x8F] = I("(\u0002\u001f:\u000e<_", "NnpMk");
        Block.I[0x48 ^ 0x17] = I("\u0005=\u001d=,\u001b7\u000e\u00078", "wXybJ");
        Block.I[0x29 ^ 0x49] = I("26\u0016?5&h", "TZyHP");
        Block.I[0x5B ^ 0x3A] = I("\b0\u0012\r\u0018\n*\f", "eEaej");
        Block.I[0xD4 ^ 0xB6] = I("+'=9(\u00168'=.;:=#", "IURNF");
        Block.I[0x70 ^ 0x13] = I("\u0006\u0012\u0007)0\u0004\b\u0019", "kgtAB");
        Block.I[0xDB ^ 0xBF] = I(";\u0004\u0017\u00167<\u0012\u001b;5&\f", "IasIZ");
        Block.I[0x1D ^ 0x78] = I(".\u00004\n\n+\u00037\r>", "IoXnU");
        Block.I[0x4E ^ 0x28] = I(";+<18\u001e(?6", "YGSRS");
        Block.I[0xB ^ 0x6C] = I("(\u0018\u000b\b\u0016#\u0006\u000b\u0005\"", "AjdfI");
        Block.I[0x19 ^ 0x71] = I(";9\r(\u0001\u0010'\r%", "YUbKj");
        Block.I[0xAA ^ 0xC3] = I("\u0012\u0005\u00115\u0014\u00135\u0017#\u0017\u0018\u000f;$\u0014\u0017\b", "vjdWx");
        Block.I[0xCB ^ 0xA1] = I("1\u0015\n\u0006'\u0011\r\u0004\n", "BaehB");
        Block.I[0x4D ^ 0x26] = I("\u0011&#\u0007)=! \b.", "bRLiL");
        Block.I[0x79 ^ 0x15] = I("6%-;=\u0016=#7", "EQBUX");
        Block.I[0x67 ^ 0xA] = I("\u0012\u0010%\u0007\r", "pbLdf");
        Block.I[0xF3 ^ 0x9D] = I("*9\u001b38\u0017)\u001e?0#", "HKrPS");
        Block.I[0x60 ^ 0xF] = I("\r\u001d:", "ysNzU");
        Block.I[0x7C ^ 0xC] = I(" \u0014\u000e", "TzzAg");
        Block.I[0x12 ^ 0x63] = I(";)<(41#?%", "YFSCG");
        Block.I[0xDF ^ 0xAD] = I("7 !=4=*\"0", "UONVG");
        Block.I[0xC5 ^ 0xB6] = I("\u0015\u001b\u001a!\u0015'\u0017\u00060\u000e\u0014\u0011\u001a&\u0003\u0016\u0011", "xtiRl");
        Block.I[0x59 ^ 0x2D] = I("*%\b\u000b\r\u0014>\u0014\u0016", "YQgeh");
        Block.I[0xF8 ^ 0x8D] = I("\u0019&+%,\u001f%6", "vDXLH");
        Block.I[0x51 ^ 0x27] = I(")-7\u000b!/.*", "FODbE");
        Block.I[0xC7 ^ 0xB0] = I("\u0016\u001b761", "btEUY");
        Block.I[0xCB ^ 0xB3] = I("\u0015\f4\u000b\u0006", "acFhn");
        Block.I[0x30 ^ 0x49] = I("4\u001c\u001c,", "RunIL");
        Block.I[0x73 ^ 0x9] = I("%#\u001d+", "CJoNO");
        Block.I[0xC9 ^ 0xB2] = I("=\u001b7\f? \u0015\"=)\"", "PtUSL");
        Block.I[0x49 ^ 0x35] = I("\u0006)0\u0011\u0019\n1<'\u001b", "kFRBi");
        Block.I[0xDF ^ 0xA2] = I("(1,\f\u000731.!\u0007", "GPGSt");
        Block.I[0xBF ^ 0xC1] = I("8\u0004\u0014?48'\u001a9\"", "KpuVF");
        Block.I[86 + 29 - 95 + 107] = I("+\u0019\u0004<.", "HqaOZ");
        Block.I[7 + 46 - 23 + 98] = I("\u0004\u001b \u0000;", "gsEsO");
        Block.I[7 + 59 + 34 + 29] = I("\u0004\u0015\"\u0005 \u0019\u001e#)#\u001f\u0002#", "vpFvT");
        Block.I[126 + 48 - 97 + 53] = I("01\u0017?3-:\u0016\b21 ", "BTsLG");
        Block.I[124 + 101 - 107 + 13] = I("\u0015\u00029\u001d\u001a\u001f\u000f\u0007\u001f\u0007\u0014", "qkXpu");
        Block.I[10 + 38 - 11 + 95] = I("\u001a\b5(\u001b\u0014\u0017?\u0002\u0016", "uzPlr");
        Block.I[125 + 51 - 109 + 66] = I("/\u001d\u0018?\u001f%\u0010&0\u001c$\u0017\u0012", "KtyRp");
        Block.I[71 + 117 - 123 + 69] = I("\u0007\u000e\u0017\u0012\u0012!\u000b\u0019\u001c\u0016\u000b\u0006", "ebxqy");
        Block.I[18 + 62 + 14 + 41] = I("\u0016%/'%\u001c9)\u001e%\u00145\"$", "uWNAQ");
        Block.I[14 + 119 - 95 + 98] = I("'\t9\u001d\u00045\b(\u001e", "PfKvf");
        Block.I[81 + 43 - 37 + 50] = I("\u0010*\u00147\u0002", "gBqVv");
        Block.I[122 + 68 - 77 + 25] = I("\u0010\"\u001a\t4", "sPuyG");
        Block.I[96 + 130 - 117 + 30] = I("$\u0003\",\u000e#\f4", "BbPAb");
        Block.I[98 + 70 - 53 + 25] = I("\u0011287<\u0016=.", "wSJZP");
        Block.I[83 + 99 - 132 + 91] = I("\u0005$\u0004\u0000\b\u00004", "cQvni");
        Block.I[114 + 118 - 208 + 118] = I("\u0001\u001d<7\n\u0004\r", "ghNYk");
        Block.I[60 + 30 - 38 + 91] = I("\u0005\u0002\u0001*\r\u001c\u0019\u001b\u0014\b\f", "ikuuk");
        Block.I[48 + 78 - 61 + 79] = I(",\u0019\u0003'\f)\t", "JlqIm");
        Block.I[94 + 61 - 71 + 61] = I("=\u001f\u0013\u001d\t'\u0005\u0015,\u001e'\f\u001c", "Nkrsm");
        Block.I[45 + 36 + 62 + 3] = I("$.\b$", "WGoJx");
        Block.I[100 + 113 - 134 + 68] = I(":\u0002\u0001\u0006*#2\n\r ?", "MmnbO");
        Block.I[126 + 79 - 104 + 47] = I("\t\u001e\u001b\u0007+\f\u001a", "mqtud");
        Block.I[24 + 9 - 26 + 142] = I("4).\u0007\u0007*", "XHJcb");
        Block.I[61 + 33 - 31 + 87] = I("\u0004$%*=\u001a", "hEANX");
        Block.I[115 + 104 - 128 + 60] = I("\u0007\t>&", "uhWJB");
        Block.I[41 + 6 + 1 + 104] = I("\u0001\u0004\u000e\r", "segal");
        Block.I[81 + 150 - 118 + 40] = I("8\u0017\u0006=*\u0014\u0010\u001d2&9\u0010", "KciSO");
        Block.I[139 + 37 - 153 + 131] = I("\u001a \u00188&\u001a\u0007\r>:\f", "iTyQT");
        Block.I[136 + 1 + 13 + 5] = I("\u000f5!\u0007\u0007\u000b=*\u0005", "xTMkX");
        Block.I[100 + 16 - 39 + 79] = I("\u0006'$%", "uNCKm");
        Block.I[134 + 9 - 24 + 38] = I("!7<!:", "MRJDH");
        Block.I[131 + 104 - 166 + 89] = I("\u0004\u001d\u0012\u000e\u0001", "hxdks");
        Block.I[123 + 45 - 142 + 133] = I("2?5:'\u001e;(112>(1\u001d1'; '", "AKZTB");
        Block.I[6 + 44 + 21 + 89] = I("\u00131?\u0010\u0004\u00161?3\u001b\u00027?0\u0003\f-?", "cCZcw");
        Block.I[159 + 46 - 171 + 127] = I(">1=6\u00193,=*", "WCRXF");
        Block.I[130 + 11 - 35 + 56] = I("\t<7\u001f\"\u001f<6", "mSXmk");
        Block.I[123 + 143 - 119 + 16] = I("\u000e#\u000b\u0005\n\u0017\u0013\u0014\u0013\n\n?\u0011\u0013\n&<\b\u0000\u001b\u001c", "yLdao");
        Block.I[46 + 50 - 88 + 156] = I("\u0004\u0003\u0013\u000b\u001f\u0001\u0003\u0013(\u0000\u0015\u0005\u0013/\u0003\u001b\u0015", "tqvxl");
        Block.I[72 + 122 - 109 + 80] = I("\u000b(\u0002\u0001\r\u0016#\u0003-\u0016\u000b(", "yMfry");
        Block.I[78 + 50 + 26 + 12] = I("$+\u0004?#/*\u0015\u0002(.", "KYamF");
        Block.I[116 + 52 - 165 + 164] = I("\u001a-#'=\u0013 $\f \u0018!\b\u0017=\u0013", "vDWxO");
        Block.I[63 + 80 - 39 + 64] = I("5<\u00158\u0000>=\u0004\u0005\u000b?", "ZNpje");
        Block.I[93 + 25 - 7 + 58] = I("1\u0001\u0000\u000f\f\u001b\u001d\t\u0002\u000b0\u0000\u0002\u0003'0\u0000\u001e\u0005\u0010", "Dolfx");
        Block.I[154 + 9 - 44 + 51] = I("8\f\u000e\t\u0012\"\u0006", "VczNs");
        Block.I[143 + 28 - 124 + 124] = I(" \b\u001e6\u001f=\u0003\u001f\u001a\u001f=\u001f\u0019-", "RmzEk");
        Block.I[163 + 92 - 109 + 26] = I("\u0006\u001f\u001f1\r\u001c\u0015", "hpkvl");
        Block.I[34 + 3 + 117 + 19] = I("\u0016\u0013\u001f\u0005\u0001:\u0005\u0005\u001f\u0010\n\t", "egpkd");
        Block.I[54 + 26 + 33 + 61] = I("\u001a,>\f\u001b\u0016", "xYJxt");
        Block.I[161 + 55 - 144 + 103] = I("\u0017\u001d\u000b0/\b\u0012\u001d\"\u0002", "dsdGp");
        Block.I[55 + 4 + 17 + 100] = I("?\f?\u0000", "LbPwc");
        Block.I[147 + 132 - 174 + 72] = I("?:1", "VYTWM");
        Block.I[24 + 127 - 0 + 27] = I(">\u0019\n", "WzoJG");
        Block.I[125 + 100 - 91 + 45] = I("\u0012\n+&", "adDQx");
        Block.I[79 + 21 + 23 + 57] = I("?\u0005,\u001d", "LkCjv");
        Block.I[52 + 69 + 28 + 32] = I("\u0015-0&\u0018\u0005", "vLSRm");
        Block.I[94 + 181 - 257 + 164] = I("\u000e0\u000f\u001a\f\u001e", "mQlny");
        Block.I[159 + 27 - 119 + 116] = I("9\"\u000f\u001b", "ZNnbA");
        Block.I[122 + 3 - 46 + 105] = I("+% (", "HIAQb");
        Block.I[166 + 173 - 243 + 89] = I("\u0011\u000e&\u001d\u0004", "ckCyw");
        Block.I[13 + 42 + 109 + 22] = I("4$\u000766", "FAbRE");
        Block.I[6 + 78 - 12 + 115] = I("\t=\u0002\u0004:\f0", "cHiaX");
        Block.I[57 + 166 - 111 + 76] = I(";%\u0011/1>(", "QPzJS");
        Block.I[21 + 41 + 127 + 0] = I("\u0013\n\u00181+", "uovRN");
        Block.I[111 + 115 - 152 + 116] = I("\u0001\t?*\r", "glQIh");
        Block.I[161 + 46 - 152 + 136] = I("7-\u0002#\u0007.6", "GXoSl");
        Block.I[144 + 123 - 228 + 153] = I("\b\u0005?;\u0018\u0011\u001e", "xpRKs");
        Block.I[146 + 160 - 205 + 92] = I("\u00162\r&\u0003\n%\u0018-\r", "xWyNf");
        Block.I[168 + 8 + 17 + 1] = I("#\u0003\u0018(\u0013$\u0005\u001f", "KftDa");
        Block.I[143 + 99 - 239 + 192] = I(":\u0005\u0016\u001c\u001c:\u000b\r\u0014", "IjcpC");
        Block.I[167 + 98 - 102 + 33] = I("\u001c\u0012\u0004.6\u0015\u0019\f", "twhBE");
        Block.I[73 + 3 + 54 + 67] = I("?\u0001\u0016\u0019\u001d,\u0002\u0017\u000b", "Xmynn");
        Block.I[19 + 98 - 10 + 91] = I(")\u00116$\u001d\"\u001d<", "ExQLi");
        Block.I[101 + 172 - 107 + 33] = I("'\t?\u001c\n;", "WfMhk");
        Block.I[55 + 84 - 37 + 98] = I(":\u000b3\u001e/&", "JdAjN");
        Block.I[194 + 24 - 19 + 2] = I("\u0007!\u001c\u0014\u0016\u001e%\u0018 \u000f\u0005", "kHhKf");
        Block.I[88 + 98 - 117 + 133] = I("\u0002+5$\u0012\u00032*=\t", "nBATg");
        Block.I[20 + 134 - 40 + 89] = I("7#-\u0014", "TBFqX");
        Block.I[160 + 17 - 171 + 198] = I("\")\u0007\u001d", "AHlxo");
        Block.I[168 + 73 - 210 + 174] = I("\u0014'\u0000\b%\u0004;\u0015\u0003\r\u0013,\u0000\u00023\u0015,\u0002", "aIpgR");
        Block.I[1 + 46 - 41 + 200] = I("\b\u0006\f2\u000f", "locVj");
        Block.I[176 + 83 - 55 + 3] = I("\u0012\u0000%\u00040\u0007\u000b\r\u0013'\u0012\n3\u0015'\u0010", "boRaB");
        Block.I[156 + 87 - 96 + 61] = I(")+.6\u0003", "MBARf");
        Block.I[178 + 142 - 124 + 13] = I("\u00043.>>\u0012#\u00100<\u00164<", "wGOWP");
        Block.I[58 + 96 - 131 + 187] = I("!\u0015\u0011\u0011>7\u00057\u00141!\u0012", "RapxP");
        Block.I[183 + 101 - 146 + 73] = I("\u001c9 \u0004,\u0007$3", "hKAtH");
        Block.I[201 + 138 - 259 + 132] = I(";=\u0000\u001c\u000b  \u0013", "OOalo");
        Block.I[167 + 209 - 223 + 60] = I("\f\r>9:\u0004\u0010\u000f/)\u0006", "abPJN");
        Block.I[95 + 206 - 207 + 120] = I("\u00078\u000b0\u000e\u000f%67\u0015\u00042 $\u001d", "jWeCz");
        Block.I[0 + 37 + 64 + 114] = I("\u001f\u0018\u00029\u000b\u000e\u001e\u00044\u0005\u001f\u0001\u00028\u001a\u0004", "llmWn");
        Block.I[9 + 160 - 20 + 67] = I("\u0015=7\f!\u0004;1\u0001/", "fIXbD");
        Block.I[175 + 56 - 81 + 67] = I(",7\u001b6#\u0011(\u00012%<*\u001b,\u0012,)\u001b\"&", "NEtAM");
        Block.I[215 + 199 - 387 + 191] = I("\u0017\u001b%?+\u0015\u0001;", "znVWY");
        Block.I[0 + 149 - 6 + 76] = I("'\f)\u0005> \u001a%(<:\u0004\u00128?:\n&", "UiMZS");
        Block.I[192 + 34 - 109 + 103] = I("\u001a6%\u0005!\u0018,;", "wCVmS");
        Block.I[52 + 133 + 20 + 16] = I("'+$>\u001a,89#", "NYKPE");
        Block.I[115 + 171 - 128 + 64] = I(")\u0007>)\r\u0006\u0010?$", "ObPJh");
        Block.I[184 + 207 - 202 + 34] = I("!\u00041\u001c\u0007\u0019\u00181\u0001\u0011", "FhPot");
        Block.I[71 + 61 + 67 + 25] = I("\u0007$39\u0000\u001f-)$", "sLZWG");
        Block.I[140 + 36 + 37 + 12] = I("\u0002\b$;#", "omHTM");
        Block.I[210 + 75 - 184 + 125] = I("%\t#,<\u0017\u000e#,1#", "HlOCR");
        Block.I[61 + 209 - 173 + 130] = I(")>\f%:0%>&%<&", "YKaUQ");
        Block.I[191 + 19 - 160 + 178] = I("=\u0006(\u001e*$\u001d\u0016\u001a$ ", "MsEnA");
        Block.I[86 + 213 - 298 + 228] = I("\u0019\u001d..&+\u000b6$%", "txBAH");
        Block.I[170 + 76 - 177 + 161] = I("\u0000\u0005,4\u0011\u0019\u001e\u00120\u001f\u001d", "ppADz");
        Block.I[131 + 195 - 175 + 80] = I("\u0004#='", "rJSBw");
        Block.I[116 + 198 - 302 + 220] = I("\u0002\u0005>\u000b", "tlPnD");
        Block.I[154 + 221 - 185 + 43] = I("\u0014-+\".-/$5.", "rHEAK");
        Block.I[124 + 52 - 124 + 182] = I("\u0007\u0007\n*+&\u0003\u0010,", "abdIN");
        Block.I[3 + 168 + 33 + 31] = I(" \n<\u0002;\u001d\u000b!\u000090\u000b", "BxUaP");
        Block.I[136 + 218 - 268 + 150] = I("*\u0013;\f\u001b*%(\f\n2", "YgZei");
        Block.I[61 + 192 - 99 + 83] = I("\"\u00156\u001a\u0014\u000e\u0003+\u001d\u0012:>*\u0000\u00108\u0013*", "QaYtq");
        Block.I[60 + 52 - 73 + 199] = I("\u0003\u0005\u0011$8\u0003\"\u0004\"$\u00153\u0002$)\u001b\"\u001d\"%\u0004\u0019", "pqpMJ");
        Block.I[41 + 5 + 39 + 154] = I(";\u0014+-#?\u0018%", "VmHHO");
        Block.I[92 + 115 - 131 + 164] = I("\u00148(\b>", "yAKmR");
        Block.I[195 + 15 - 8 + 39] = I(" \u0019\u0016\u0010&;\u0011\u000e\f", "WxbuT");
        Block.I[62 + 112 + 39 + 29] = I("\u00071\u00120\u0016\u001c9\n,", "pPfUd");
        Block.I[69 + 98 - 52 + 128] = I("\u0014\u000e\u0017\u0010-\b)\u0011\u0011+\u0011", "zkcxH");
        Block.I[202 + 138 - 230 + 134] = I("*?\u001d\u0010\u00126\u0005\u000b\n\u001e'1", "DZixw");
        Block.I[40 + 97 - 3 + 111] = I("\r\u0003\u00069\u001f\u00119\u0010#\u0013\u0000\r-7\u001f\r\u0005\u0017", "cfrQz");
        Block.I[226 + 177 - 267 + 110] = I("\u0006\u0000\u0015\u001f\u0011\u001a#\u0004\u0019\u0017\r", "heawt");
        Block.I[15 + 6 + 42 + 184] = I("('\u0017$44\u001d\u0001>8%)<?%'+\u0011?", "FBcLQ");
        Block.I[66 + 74 - 96 + 204] = I("?\u0010\u0012\u001a\u0011?*\u0016\u0007\u000b)\u00161\u0001\n/\u000f", "Ldssc");
        Block.I[134 + 48 - 149 + 216] = I("\u0017\u0001\u0013\u000f\u0004\u000b;\u0010\u0006\u0013\r", "ydgga");
        Block.I[125 + 38 + 62 + 25] = I("'\n\u001d=5;<\u001d4<\"", "IoiUP");
        Block.I[224 + 102 - 79 + 4] = I("7\u00051$,<\u001f;\"*\r\u001f3.!7", "RkRLM");
        Block.I[52 + 28 + 149 + 23] = I("0\b.\u0018\u0000;\u0012 \u0015\u000f!2,\u0012\r0", "UfMpa");
        Block.I[37 + 232 - 207 + 191] = I("65\u0000!1: :%,5)\u0001", "TGeVX");
        Block.I[9 + 165 - 61 + 141] = I(",\u00194?& \f\u0002<. \u000f", "NkQHO");
        Block.I[175 + 201 - 171 + 50] = I("\u0012\u0019?\u0018>\u0003\u0017$", "qxJtZ");
        Block.I[209 + 141 - 253 + 159] = I(":\u001b\u001b>\u0002+\u0015\u0000", "YznRf");
        Block.I[70 + 63 + 82 + 42] = I("\u001d\u00167\u0013=\u0017\n'-!", "xxSLM");
        Block.I[122 + 98 - 71 + 109] = I("0\u001e\u000696:\u0002\u0016\u0007*\n\u0016\u0010\u0007+0", "UpbfF");
        Block.I[122 + 166 - 116 + 87] = I("\u0004\t!\u0002\u0019\u0013\u0013$>0\u0013\u0006(7", "agERv");
        Block.I[148 + 61 - 141 + 192] = I(" \"\f\n)1#\u00060", "ELhUZ");
        Block.I[159 + 153 - 296 + 245] = I("\u001c\n\u000b0\f8\u0016\r*\f", "kbbDi");
        Block.I[241 + 194 - 296 + 123] = I("\u000b\u000b\f\u000f\u0019\u0001&\b\u000f\u0011", "oymhv");
        Block.I[227 + 82 - 186 + 140] = I(",03\u0010\"&\u00075\u0010", "HBRwM");
        Block.I[0 + 234 - 60 + 90] = I("\u001c+3<\u0017\u0001 2\u0010\u000f\u000f#'", "nNWOc");
        Block.I[235 + 223 - 274 + 81] = I("*,%\u001a\u00077'$%\u001a?!5", "XIAis");
        Block.I[165 + 113 - 272 + 260] = I("?+\u000e\n\u00116&\t!\f='%9\u0002>2", "SBzUc");
        Block.I[136 + 19 + 52 + 60] = I("%\u00001\u001d28\u000b0\"/0\r!", "WeUnF");
        Block.I[147 + 64 - 0 + 57] = I("6\n\u00022.7:\u0000?-6\u0000\u0019\u000f1>\u0004\u0015", "RewPB");
        Block.I[211 + 193 - 191 + 56] = I(".+\u001b2\u000b5%\u0016", "YDtVX");
        Block.I[226 + 45 - 107 + 106] = I("::\u000e\u0005\u001c#\n\u0012\r\u0018/", "MUaay");
        Block.I[54 + 75 + 17 + 125] = I("\u000e><\u0013#\u001501", "yQSwp");
        Block.I[226 + 117 - 126 + 55] = I("\u0001(\u0002+\u0019", "bGaDx");
        Block.I[228 + 222 - 295 + 118] = I("4\n\u0010\r$", "WesbE");
        Block.I[91 + 169 - 192 + 206] = I("\u0015\n\"(\u0019\u0012\u0004\")5\u0015\u001f-%\u0018\u0015", "fkLLj");
        Block.I[166 + 246 - 245 + 108] = I("\u001b-1\u000b$\u001b\n1\f2;-?\f3", "hYPbV");
        Block.I[50 + 206 - 31 + 51] = I("\f\t\u0001?\u0000\u0005\u0000;\"\u0013\f", "iddMa");
        Block.I[76 + 60 + 103 + 38] = I("\u001f*\u00100&\u0015*\u0014\u0019/", "pXuuK");
        Block.I[79 + 227 - 292 + 264] = I("\u0013//\u001f\u0011)\"#\u001f\u0010\u0002", "vAKzc");
        Block.I[43 + 71 - 56 + 221] = I("\u0004%\u001c\u0004?\"#\u001d\u00129", "aKxaM");
        Block.I[185 + 188 - 283 + 190] = I("%\u001d:\u0001\u00168\u001d6.\t>\u00008", "QoSqa");
        Block.I[227 + 32 - 26 + 48] = I("\u0005 \u0000:\u0004\u0018 \f\u0019<\u0004 \n/", "qRiJS");
        Block.I[167 + 175 - 282 + 222] = I("\u0007\u0019\u001d\u00118\u001a\u0019\u0011", "sktaO");
        Block.I[244 + 216 - 424 + 247] = I("\u0011$*\u0018\u0000\f$&", "eVChW");
        Block.I[129 + 140 - 117 + 132] = I("):19' 3\u000b)*#4?", "LWTKF");
        Block.I[133 + 144 - 127 + 135] = I("\u000b\u000b\u0006/*,\n\f> \u0005\u0003", "igiLA");
        Block.I[111 + 83 + 81 + 11] = I("\u001f\u0017\u001e\u0000-\t8\u001f\u0001/\u0005\u0015\u001f", "lgluN");
        Block.I[282 + 71 - 351 + 285] = I("=\r1\u001a;=.?\u001c-\u001d\t\"\u0006*+", "NyPsI");
        Block.I[42 + 268 - 67 + 45] = I(":/#\u0006\r\u00075%\u0004\f*5", "XFQee");
        Block.I[185 + 277 - 312 + 139] = I("4%\t\f'4\u0006\u0007\n1\u00058\u001a\u0006=", "GQheU");
        Block.I[134 + 179 - 104 + 81] = I("('\u0000\u0003\u0018'\r\u001d\u0010\u0015+ \u001d", "BRndt");
        Block.I[143 + 132 - 273 + 289] = I("%\u0012-.\u001b%1#(\r\u001c\u0013\" \u00053", "VfLGi");
        Block.I[192 + 17 + 49 + 34] = I("&!\u00075\u0000+*5:\r*-\u0001", "ENjXa");
        Block.I[99 + 21 - 26 + 199] = I(".\u001a\u0001>\u0018#\u0011.?\u0016.\u001e", "MulSy");
        Block.I[257 + 248 - 247 + 36] = I("\u0003.\u0014!\u001e\u000f", "aKuBq");
        Block.I[156 + 138 - 216 + 217] = I("!<\u000e7)-", "CYoTF");
        Block.I[48 + 37 + 51 + 160] = I("\u00107!\u0005\u001c\u0016+7\b\u001e\u0016\u00074\u0006\u001c\u001f", "sXCgp");
        Block.I[122 + 127 - 96 + 144] = I("6\u000b&#803%-8", "UdDAT");
        Block.I[27 + 87 + 177 + 7] = I(">(&\u0016\u000e*\u001b9\u000e\u001f", "XDIak");
        Block.I[122 + 262 - 366 + 281] = I("\u0007!\u001a#\u000e\u0013\u001d\u001a ", "aMuTk");
        Block.I[157 + 201 - 201 + 143] = I("\u0005\u0007\u001a?$\u0012\u0015", "ffhMK");
        Block.I[102 + 17 + 169 + 13] = I("\u001a\u000b<&5\r\u0019", "yjNTZ");
        Block.I[180 + 66 - 96 + 152] = I("\u0006;&(5\u00191!", "vTRIA");
        Block.I[95 + 283 - 289 + 214] = I("<-%\u0014\u001d#'\"", "LBQui");
        Block.I[203 + 249 - 356 + 208] = I(".5\u001e\u0000\u000e7\u0005\u0013\u0011\u001f-5\u001f", "YZqdk");
        Block.I[108 + 39 - 23 + 181] = I("\u000e4\u0006.\u0001\u0002", "lArZn");
        Block.I[9 + 257 - 71 + 111] = I("\u0018\u0018\u0006\u001c\u001d", "ksspq");
        Block.I[149 + 85 - 193 + 266] = I("' 9\"?", "TKLNS");
        Block.I[287 + 220 - 408 + 209] = I("\u0002!\u0000\u0001-", "cOvhA");
        Block.I[125 + 19 - 8 + 173] = I("\u0014\u0018$(\u000e", "uvRAb");
        Block.I[31 + 116 - 25 + 188] = I("\u0001\u00012\u001d(\u0010\u0017\f\u000e0\u0010\u0000'", "usSmX");
        Block.I[305 + 178 - 481 + 309] = I(";\u0012<\u00165\f\b8\u0015", "XzYeA");
        Block.I[257 + 307 - 459 + 207] = I("+=\u0005\u0000\u0015\u0018#\u0007\u0001\u0006/ \u0007\f>7&\u0007\u001b\u00122&\u00077\u0011+5\u0016\r", "GTbha");
        Block.I[247 + 241 - 367 + 192] = I("<\u000e3\u0004\u001b?\u000e>3\u001f*\u001f?<\u001f\"\f2\u0017", "KkZcs");
        Block.I[310 + 209 - 464 + 259] = I("<\b\u00070\u001d\u000b\u001a\u0003/\u0003<\u0019\u0003\";$\u001f\u00035\u0017!\u001f\u0003\u0019\u00148\f\u0012#", "TmfFd");
        Block.I[191 + 146 - 182 + 160] = I("\u001f\r#\f,\u001c\r.;(\t\u001c/4,\r\t<\u0012", "hhJkD");
        Block.I[31 + 150 - 112 + 247] = I("\u0004\u0003$&>\u0014\u001f1-\u0016\u0012\u000299(\u0003\f &;", "qmTII");
        Block.I[231 + 63 - 63 + 86] = I(")\u0001\u001c\u001948\u000f\u0005\u0006'", "JnqiU");
        Block.I[119 + 232 - 184 + 151] = I("<\r/5%)\u0006\u000738!\u00129\"68\r*", "LbXPW");
        Block.I[7 + 257 - 169 + 224] = I("*\u0004?'\n;\n&8\u0019", "IkRWk");
        Block.I[15 + 241 + 8 + 56] = I(",\f\u0015\u00189/\u0005\u0018+4-\u0019\t\u0017$'\u001f", "HmltP");
        Block.I[195 + 137 - 297 + 286] = I("*\u0007\u00017?7\f\u0000\u001b)4\r\u0006/", "XbeDK");
        Block.I[40 + 2 + 266 + 14] = I("\u0013)<\u0007\n# 7\u0017\u0015\u001e+6", "qESda");
        Block.I[290 + 277 - 446 + 202] = I("\"\u0011\"\u001a;);,\u001a*", "SdChO");
        Block.I[89 + 17 + 102 + 116] = I("\u001e\u0011\u0002\r#\u0002\u0005\u0003\u00044\u0004\u000e", "ptveF");
        Block.I[298 + 0 - 168 + 195] = I("+\u0000\u0018\n+1", "CohzN");
        Block.I[114 + 139 - 25 + 98] = I("\n?\u0002\u0007\u000b\u0010", "bPrwn");
        Block.I[136 + 78 - 211 + 324] = I("\u0014\u0002\u0016\u000b\u0013\u001f5\u001b\u0016\u0004\u000e", "ewwyg");
        Block.I[55 + 131 + 60 + 82] = I("\u001e\u001a#=>\u00150 #%\f\u0004", "ooBOJ");
        Block.I[192 + 139 - 307 + 305] = I("\u0016\u00001(7\u001d*#.\"\u000e\u0007#", "guPZC");
        Block.I[82 + 264 - 212 + 196] = I("\u000b\r\u0000\u001b\u0007\u000b(\u0014\u0013\u0007\f\u0003", "xyaru");
        Block.I[86 + 8 + 108 + 129] = I("\n\u001a\u001f\r'\n\r\u0004\u0016\u000e\u0019\u0018\u0002\b", "kykdQ");
        Block.I[294 + 179 - 350 + 209] = I("\u00073\u0017:#\u0007$\f!\u0007\u00079\u000f", "fPcSU");
        Block.I[304 + 93 - 155 + 91] = I("7\u001b\u0019\u0006%6\u001b", "SivvU");
        Block.I[173 + 139 - 149 + 171] = I("\"\u0004\u001d9\u0015#\u0004", "FvrIe");
        Block.I[41 + 221 - 200 + 273] = I("?'5\u0019$)7\u000b\u0018+>71\u001e/(\f7\u001c+5", "LSTpJ");
        Block.I[195 + 58 - 153 + 236] = I("\u0000-\u0003);\u00023\u00065\u001d\u0006%1$\u0012\n/\u00074", "cAbPs");
        Block.I[111 + 174 - 16 + 68] = I("$\u0013\u00051%2\u0003;?'6\u0014\u0017\u0007;6\t\u0001", "WgdXK");
        Block.I[29 + 291 - 114 + 132] = I("\u001d\u000b\u001c\r\u001d\u001d\u0002\u001c\r+\r$\u0019\u0002=\u001a", "icucN");
        Block.I[237 + 217 - 141 + 26] = I(":*'03%}", "VOFFV");
        Block.I[114 + 293 - 386 + 319] = I("\u0003!&2(\u001c", "oDGDM");
        Block.I[199 + 59 - 64 + 147] = I(":'\u0015^", "VHrlh");
        Block.I[238 + 4 - 177 + 277] = I("\u001c80", "pWWcw");
        Block.I[274 + 169 - 142 + 42] = I("\u000e)\u00002\"\u000e\u0015\u0012%*\u00068\u0012", "oJaQK");
        Block.I[206 + 338 - 465 + 265] = I("1\u001e\b*\u00181=\u0006,\u000e\u0003\t\b \u0003#", "BjiCj");
        Block.I[251 + 330 - 573 + 337] = I("#\t\u0005\u00134(\t\u001c'\u00183\t\u001e\n\u0018", "Ghwxk");
        Block.I[266 + 261 - 264 + 83] = I("9063\u00189\u001385\u000e\u000e%%1%+/", "JDWZj");
        Block.I[234 + 56 + 21 + 36] = I(">\u0006\u0002.)", "MjkCL");
        Block.I[67 + 22 + 108 + 151] = I("\u0010\u0003\u0013;\u001f", "cozVz");
        Block.I[37 + 39 + 32 + 241] = I("6\u0007\u00144\u00181\u0014", "TffFq");
        Block.I[177 + 176 - 96 + 93] = I("\u00147\u001f+#\u0013$", "vVmYJ");
        Block.I[125 + 41 + 170 + 15] = I("\b?\u0002=%\u0015?\f#\u001e\u000e\"\u001f", "aMmSz");
        Block.I[209 + 249 - 446 + 340] = I("\u0011;>\u0000\u0017\n(!\n,\u0017;", "xIQnC");
        Block.I[51 + 69 - 85 + 318] = I("6(-6\n'(-+\u0002", "FZDEg");
        Block.I[243 + 49 - 261 + 323] = I(" \u0014(\u0016\u001b1\u0014(\u000b\u0013", "PfAev");
        Block.I[290 + 191 - 425 + 299] = I("4\u0003\u0019\u001a?&\b\f !)", "GfxES");
        Block.I[170 + 227 - 244 + 203] = I("\u000b\u0016\u0011'\u0016\u0016\u0007\u0015\u0019\u0019", "xspkw");
        Block.I[1 + 353 - 320 + 323] = I("!\u000e!7;%\u0000;\u0003", "IoXhY");
        Block.I[10 + 102 + 221 + 25] = I("\t%\u0000\u0013-\u000e'\u0012", "aDyQA");
        Block.I[300 + 139 - 255 + 175] = I("\u0012\u000b\u001e15\u0005", "qjlAP");
        Block.I[274 + 327 - 284 + 43] = I("\r)>8\u001a\u001b4!1-", "zFQTY");
        Block.I[233 + 262 - 411 + 277] = I("*(\u001a\u0002\u0011,,\f9\u0017.(\u0011", "BIhft");
        Block.I[320 + 278 - 277 + 41] = I("\">\t1,  \f-\n$6", "ARhHd");
        Block.I[169 + 64 - 35 + 165] = I("\n\u0007 \u0004:\u000b\u0004.\u000b\u000e", "ihAhe");
        Block.I[151 + 224 - 118 + 107] = I("\b)\u0002\u0019-)*\f\u0016", "jEmzF");
        Block.I[91 + 284 - 242 + 232] = I("&\u0006',!28-$!", "VgDGD");
        Block.I[361 + 110 - 220 + 115] = I("1\u0010\u0012\u001d1;\u0018\u0012)", "XswMP");
        Block.I[135 + 133 - 72 + 171] = I("\r%%\u0015#\f\u0015 \u001b.\u0007>", "iJPwO");
        Block.I[261 + 357 - 465 + 215] = I("\u0007 \"\u001a!\u001d:$+'\u0015:-\u00117", "tTCtE");
        Block.I[357 + 49 - 137 + 100] = I(")#\u0018\u0004\u000f9", "KBvjj");
        Block.I[2 + 118 - 70 + 320] = I("<2'6>)2%4\u00049", "KSKZa");
        Block.I[231 + 243 - 434 + 331] = I("-\u000e\u001f\u0002==", "OoqlX");
        Block.I[0 + 342 - 264 + 294] = I("7;)'-42$\u0014 6.5(0<(\u000f\"*%?\"?!7", "SZPKD");
        Block.I[336 + 150 - 155 + 42] = I("7)\u0010*'+('\r)+)", "ELtyF");
        Block.I[290 + 101 - 195 + 178] = I("4.7\u0016\u0012'%7:\u0015)%6", "FKSIa");
        Block.I[163 + 15 - 121 + 318] = I("5.+\u000b\u0001&%+'\u0006(%*\u000b\u00013*&&\u0001", "GKOTr");
        Block.I[142 + 214 + 19 + 1] = I("\u0000:\u00030(\u0000\u001c\u0007=\t\u0012 \u0006\n.\u001c \u0007", "sNbYZ");
        Block.I[13 + 19 + 323 + 22] = I("\u0000\u001f\u001b\r8\u0001/\u001d\u001b;\n\u00151\u001c8\u0005\u0012\\", "dpnoT");
        Block.I[122 + 91 - 112 + 277] = I("\u0012\r\u0018,42\u0015\u0016 c", "aywBQ");
        Block.I[196 + 329 - 448 + 302] = I("5\u0017\u000b\u0006\u0017\u0019\u0010\b\t\u0010t", "Fcdhr");
        Block.I[303 + 164 - 151 + 64] = I("%\u001a\f\u0000\u001c\u0005\u0002\u0002\fK", "Vncny");
        Block.I[147 + 124 - 265 + 375] = I(";5;;\u0010-\u001a/+\u001d+ \u0016)\u0012< ", "HEINs");
        Block.I[332 + 199 - 491 + 342] = I("2'\u001f\u000f$$\u0011\b\u0014$$\u0010\f\u000e\"", "AWmzG");
        Block.I[4 + 98 + 165 + 116] = I(";\u0011\b90\u0006\u001e\u001f4;<'\u001d;,<", "YxzZX");
        Block.I[203 + 145 - 281 + 317] = I("4#:;%\u0010/&;(\u0011+<=", "VJHXM");
        Block.I[108 + 339 - 375 + 313] = I(";\u001c\u0006+\u000346\u000e)\u00012\f7+\u000e%\f", "QihLo");
        Block.I[183 + 295 - 310 + 218] = I("\t/)$%\u0006\u001c\"-*\u0006\u001d&7,", "cZGCI");
        Block.I[227 + 372 - 539 + 327] = I("\u000e\t\u0001\u0007\u001c\u0005\t\u00183%\u000f\u0006\u0010\t\u001c\r\t\u0007\t", "jhslC");
        Block.I[75 + 301 + 9 + 3] = I("\r\u0014;\f<\b\u001e\u000f\u0002\u001d\n\u0010\u000e\u0006\u0007\f", "iuIgs");
        Block.I[49 + 213 - 163 + 290] = I("\u0012%\u0000!\u0010\u0012\u0019\u0007'\u0017\u0010#>%\u0018\u0007#", "sFaBy");
        Block.I[67 + 31 + 171 + 121] = I("(\u0015\u0013+\u000e(0\u0017&\u0004,1\u0013<\u0002", "IvrHg");
        Block.I[192 + 51 - 150 + 298] = I("'&=\u0011\t1\t)\u0001\u000473", "TVOdj");
        Block.I[357 + 22 + 12 + 1] = I("\u0014&\u0015\u000f(\u0002\u0010\u0002\u0014(\u0002", "gVgzK");
        Block.I[1 + 123 + 218 + 51] = I("6%\u0002\u0014%\u000b*\u0015\u0019.1", "TLpwM");
        Block.I[391 + 13 - 298 + 288] = I("\u0001\r\"\u0013\u0011%\u0001>\u0013\u001c", "cdPpy");
        Block.I[186 + 229 - 283 + 263] = I("=3(\u001d\b2\u0019 \u001f\n4#", "WFFzd");
        Block.I[11 + 382 - 233 + 236] = I("\u0013\u001a=6\u001a\u001c)6?\u0015\u001c", "yoSQv");
        Block.I[314 + 228 - 516 + 371] = I("*61?\u0007!6(\u000b>+9 1", "NWCTX");
        Block.I[173 + 102 + 66 + 57] = I("\u0007\t:\u0012#\u0002\u0003\u000e\u001c\u0002\u0000\r", "chHyl");
        Block.I[99 + 130 - 85 + 255] = I("#\u0004+/3#8,)4!\u0002", "BgJLZ");
        Block.I[318 + 85 - 32 + 29] = I("\u00150+*<\u0015\u0015/'6\u0011", "tSJIU");
        Block.I[124 + 195 + 4 + 78] = I("!=:\u0007\u00137\u0012,\u001d\u001f ", "RMHrp");
        Block.I[90 + 392 - 244 + 164] = I("\"\u00167\u0004#6\u000b-\u0015\u0015", "FyXvp");
        Block.I[241 + 222 - 393 + 333] = I("\t=3&\u001240.*\b", "kTAEz");
        Block.I[224 + 25 + 31 + 124] = I("\u000e\r)\u0006&\u0003\u0010%\u001c", "jbFtd");
        Block.I[254 + 207 - 285 + 229] = I("\u001f8)\u0013\u0002\u0010\u0012#\u001b\u0001\u0007", "uMGtn");
        Block.I[141 + 298 - 416 + 383] = I("2'\u001f>\u001c#&\u0017 3", "VHpLV");
        Block.I[253 + 32 - 170 + 292] = I("\r2\u00046-\r\u000e\u0001:+\u001e", "lQeUD");
        Block.I[267 + 27 - 225 + 339] = I("\f\u0000\n\u00159\u000b\u000e\u0006\u000e\u0019", "hoegx");
        Block.I[186 + 67 - 144 + 300] = I("\u0013,8*\n\u0018,!\u001e1\u0018\"8", "wMJAU");
        Block.I[44 + 27 + 183 + 156] = I("\t\u0015\u001f5)\f\b\u001b\b\f\u0006", "mzpGm");
    }
    
    public boolean requiresUpdates() {
        return " ".length() != 0;
    }
    
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return this.damageDropped(world.getBlockState(blockPos));
    }
    
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
    }
    
    public final IBlockState getDefaultState() {
        return this.defaultBlockState;
    }
    
    public String getUnlocalizedName() {
        return Block.I[0x7F ^ 0x6D] + this.unlocalizedName;
    }
    
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return this.quantityDropped(random);
    }
    
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
    }
    
    public int getMobilityFlag() {
        return this.blockMaterial.getMaterialMobility();
    }
    
    public final double getBlockBoundsMinX() {
        return this.minX;
    }
    
    public float getPlayerRelativeBlockHardness(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos) {
        final float blockHardness = this.getBlockHardness(world, blockPos);
        float n;
        if (blockHardness < 0.0f) {
            n = 0.0f;
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else if (!entityPlayer.canHarvestBlock(this)) {
            n = entityPlayer.getToolDigEfficiency(this) / blockHardness / 100.0f;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            n = entityPlayer.getToolDigEfficiency(this) / blockHardness / 30.0f;
        }
        return n;
    }
    
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        final AxisAlignedBB collisionBoundingBox = this.getCollisionBoundingBox(world, blockPos, blockState);
        if (collisionBoundingBox != null && axisAlignedBB.intersectsWith(collisionBoundingBox)) {
            list.add(collisionBoundingBox);
        }
    }
    
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        return "".length() != 0;
    }
    
    public boolean isFullCube() {
        return " ".length() != 0;
    }
    
    public static void registerBlocks() {
        registerBlock("".length(), Block.AIR_ID, new BlockAir().setUnlocalizedName(Block.I[0xAA ^ 0xBF]));
        registerBlock(" ".length(), Block.I[0xD0 ^ 0xC6], new BlockStone().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0xA ^ 0x1D]));
        registerBlock("  ".length(), Block.I[0x2D ^ 0x35], new BlockGrass().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0x2C ^ 0x35]));
        registerBlock("   ".length(), Block.I[0xA8 ^ 0xB2], new BlockDirt().setHardness(0.5f).setStepSound(Block.soundTypeGravel).setUnlocalizedName(Block.I[0x91 ^ 0x8A]));
        final Block setCreativeTab = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x53 ^ 0x4F]).setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(0x8F ^ 0x8B, Block.I[0x2E ^ 0x33], setCreativeTab);
        final Block setUnlocalizedName = new BlockPlanks().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[0x20 ^ 0x3E]);
        registerBlock(0x60 ^ 0x65, Block.I[0x73 ^ 0x6C], setUnlocalizedName);
        registerBlock(0x3F ^ 0x39, Block.I[0x19 ^ 0x39], new BlockSapling().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0x91 ^ 0xB0]));
        registerBlock(0x1E ^ 0x19, Block.I[0xAF ^ 0x8D], new Block(Material.rock).setBlockUnbreakable().setResistance(6000000.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x7C ^ 0x5F]).disableStats().setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0xBF ^ 0xB7, Block.I[0x97 ^ 0xB3], new BlockDynamicLiquid(Material.water).setHardness(100.0f).setLightOpacity("   ".length()).setUnlocalizedName(Block.I[0x56 ^ 0x73]).disableStats());
        registerBlock(0x3 ^ 0xA, Block.I[0x79 ^ 0x5F], new BlockStaticLiquid(Material.water).setHardness(100.0f).setLightOpacity("   ".length()).setUnlocalizedName(Block.I[0x89 ^ 0xAE]).disableStats());
        registerBlock(0x44 ^ 0x4E, Block.I[0x7F ^ 0x57], new BlockDynamicLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setUnlocalizedName(Block.I[0x5 ^ 0x2C]).disableStats());
        registerBlock(0x36 ^ 0x3D, Block.I[0x87 ^ 0xAD], new BlockStaticLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setUnlocalizedName(Block.I[0x73 ^ 0x58]).disableStats());
        registerBlock(0x8C ^ 0x80, Block.I[0x3C ^ 0x10], new BlockSand().setHardness(0.5f).setStepSound(Block.soundTypeSand).setUnlocalizedName(Block.I[0x7E ^ 0x53]));
        registerBlock(0xA8 ^ 0xA5, Block.I[0x11 ^ 0x3F], new BlockGravel().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setUnlocalizedName(Block.I[0x98 ^ 0xB7]));
        registerBlock(0x7A ^ 0x74, Block.I[0xA8 ^ 0x98], new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x7F ^ 0x4E]));
        registerBlock(0x8D ^ 0x82, Block.I[0x66 ^ 0x54], new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0xF7 ^ 0xC4]));
        registerBlock(0x30 ^ 0x20, Block.I[0x23 ^ 0x17], new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x87 ^ 0xB2]));
        registerBlock(0x38 ^ 0x29, Block.I[0x29 ^ 0x1F], new BlockOldLog().setUnlocalizedName(Block.I[0x41 ^ 0x76]));
        registerBlock(0x83 ^ 0x91, Block.I[0x16 ^ 0x2E], new BlockOldLeaf().setUnlocalizedName(Block.I[0x6D ^ 0x54]));
        registerBlock(0x97 ^ 0x84, Block.I[0x8C ^ 0xB6], new BlockSponge().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0x38 ^ 0x3]));
        registerBlock(0x62 ^ 0x76, Block.I[0xB6 ^ 0x8A], new BlockGlass(Material.glass, "".length() != 0).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[0xB0 ^ 0x8D]));
        registerBlock(0x8E ^ 0x9B, Block.I[0x50 ^ 0x6E], new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x15 ^ 0x2A]));
        registerBlock(0x21 ^ 0x37, Block.I[0x6A ^ 0x2A], new Block(Material.iron, MapColor.lapisColor).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0xEA ^ 0xAB]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x1 ^ 0x16, Block.I[0x7D ^ 0x3F], new BlockDispenser().setHardness(3.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0xC2 ^ 0x81]));
        final Block setUnlocalizedName2 = new BlockSandStone().setStepSound(Block.soundTypePiston).setHardness(0.8f).setUnlocalizedName(Block.I[0x4D ^ 0x9]);
        registerBlock(0x15 ^ 0xD, Block.I[0x5F ^ 0x1A], setUnlocalizedName2);
        registerBlock(0x1A ^ 0x3, Block.I[0x23 ^ 0x65], new BlockNote().setHardness(0.8f).setUnlocalizedName(Block.I[0xCD ^ 0x8A]));
        registerBlock(0x6E ^ 0x74, Block.I[0x1A ^ 0x52], new BlockBed().setStepSound(Block.soundTypeWood).setHardness(0.2f).setUnlocalizedName(Block.I[0x8A ^ 0xC3]).disableStats());
        registerBlock(0x19 ^ 0x2, Block.I[0x25 ^ 0x6F], new BlockRailPowered().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[0x28 ^ 0x63]));
        registerBlock(0x9C ^ 0x80, Block.I[0x1E ^ 0x52], new BlockRailDetector().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[0x43 ^ 0xE]));
        registerBlock(0x46 ^ 0x5B, Block.I[0x7A ^ 0x34], new BlockPistonBase(" ".length() != 0).setUnlocalizedName(Block.I[0x8C ^ 0xC3]));
        registerBlock(0x69 ^ 0x77, Block.I[0x1D ^ 0x4D], new BlockWeb().setLightOpacity(" ".length()).setHardness(4.0f).setUnlocalizedName(Block.I[0xD6 ^ 0x87]));
        registerBlock(0x74 ^ 0x6B, Block.I[0xC5 ^ 0x97], new BlockTallGrass().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0xEA ^ 0xB9]));
        registerBlock(0xA4 ^ 0x84, Block.I[0xF5 ^ 0xA1], new BlockDeadBush().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0xFD ^ 0xA8]));
        registerBlock(0x2B ^ 0xA, Block.I[0x1C ^ 0x4A], new BlockPistonBase("".length() != 0).setUnlocalizedName(Block.I[0xD2 ^ 0x85]));
        registerBlock(0x7 ^ 0x25, Block.I[0x73 ^ 0x2B], new BlockPistonExtension().setUnlocalizedName(Block.I[0x6 ^ 0x5F]));
        registerBlock(0x22 ^ 0x1, Block.I[0xD5 ^ 0x8F], new BlockColored(Material.cloth).setHardness(0.8f).setStepSound(Block.soundTypeCloth).setUnlocalizedName(Block.I[0xEC ^ 0xB7]));
        registerBlock(0xB4 ^ 0x90, Block.I[0x64 ^ 0x38], new BlockPistonMoving());
        registerBlock(0x6D ^ 0x48, Block.I[0xE0 ^ 0xBD], new BlockYellowFlower().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0x2A ^ 0x74]));
        registerBlock(0x4 ^ 0x22, Block.I[0xE3 ^ 0xBC], new BlockRedFlower().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0x5C ^ 0x3C]));
        final Block setUnlocalizedName3 = new BlockMushroom().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setLightLevel(0.125f).setUnlocalizedName(Block.I[0x7E ^ 0x1F]);
        registerBlock(0x46 ^ 0x61, Block.I[0x15 ^ 0x77], setUnlocalizedName3);
        final Block setUnlocalizedName4 = new BlockMushroom().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0x3A ^ 0x59]);
        registerBlock(0xB1 ^ 0x99, Block.I[0xF ^ 0x6B], setUnlocalizedName4);
        registerBlock(0xB8 ^ 0x91, Block.I[0x1F ^ 0x7A], new Block(Material.iron, MapColor.goldColor).setHardness(3.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[0x48 ^ 0x2E]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x7E ^ 0x54, Block.I[0x31 ^ 0x56], new Block(Material.iron, MapColor.ironColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[0xED ^ 0x85]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x5A ^ 0x71, Block.I[0xE6 ^ 0x8F], new BlockDoubleStoneSlab().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x19 ^ 0x73]));
        registerBlock(0xE ^ 0x22, Block.I[0x22 ^ 0x49], new BlockHalfStoneSlab().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x2D ^ 0x41]));
        final Block setCreativeTab2 = new Block(Material.rock, MapColor.redColor).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0xEE ^ 0x83]).setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(0x4C ^ 0x61, Block.I[0xF0 ^ 0x9E], setCreativeTab2);
        registerBlock(0x3C ^ 0x12, Block.I[0xE0 ^ 0x8F], new BlockTNT().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[0xA ^ 0x7A]));
        registerBlock(0xB ^ 0x24, Block.I[0x49 ^ 0x38], new BlockBookshelf().setHardness(1.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[0x26 ^ 0x54]));
        registerBlock(0x48 ^ 0x78, Block.I[0xCD ^ 0xBE], new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0x56 ^ 0x22]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x34 ^ 0x5, Block.I[0x5D ^ 0x28], new BlockObsidian().setHardness(50.0f).setResistance(2000.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[0xB0 ^ 0xC6]));
        registerBlock(0x4C ^ 0x7E, Block.I[0x7E ^ 0x9], new BlockTorch().setHardness(0.0f).setLightLevel(0.9375f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[0x14 ^ 0x6C]));
        registerBlock(0x7A ^ 0x49, Block.I[0x41 ^ 0x38], new BlockFire().setHardness(0.0f).setLightLevel(1.0f).setStepSound(Block.soundTypeCloth).setUnlocalizedName(Block.I[0x32 ^ 0x48]).disableStats());
        registerBlock(0x4A ^ 0x7E, Block.I[0xD3 ^ 0xA8], new BlockMobSpawner().setHardness(5.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[0x54 ^ 0x28]).disableStats());
        registerBlock(0x95 ^ 0xA0, Block.I[0xC6 ^ 0xBB], new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK)).setUnlocalizedName(Block.I[0x66 ^ 0x18]));
        registerBlock(0x9 ^ 0x3F, Block.I[105 + 13 - 95 + 104], new BlockChest("".length()).setHardness(2.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[37 + 99 - 29 + 21]));
        registerBlock(0xBF ^ 0x88, Block.I[113 + 113 - 120 + 23], new BlockRedstoneWire().setHardness(0.0f).setStepSound(Block.soundTypeStone).setUnlocalizedName(Block.I[25 + 58 - 0 + 47]).disableStats());
        registerBlock(0x3E ^ 0x6, Block.I[67 + 31 - 8 + 41], new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[15 + 124 - 74 + 67]));
        registerBlock(0x0 ^ 0x39, Block.I[9 + 16 + 55 + 53], new Block(Material.iron, MapColor.diamondColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[23 + 92 - 49 + 68]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x67 ^ 0x5D, Block.I[47 + 77 - 39 + 50], new BlockWorkbench().setHardness(2.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[85 + 23 - 104 + 132]));
        registerBlock(0x9E ^ 0xA5, Block.I[33 + 104 - 60 + 60], new BlockCrops().setUnlocalizedName(Block.I[62 + 68 - 65 + 73]));
        final Block setUnlocalizedName5 = new BlockFarmland().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setUnlocalizedName(Block.I[52 + 66 - 29 + 50]);
        registerBlock(0xA7 ^ 0x9B, Block.I[93 + 56 - 93 + 84], setUnlocalizedName5);
        registerBlock(0x18 ^ 0x25, Block.I[62 + 139 - 142 + 82], new BlockFurnace("".length() != 0).setHardness(3.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[73 + 141 - 138 + 66]).setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(0x35 ^ 0xB, Block.I[36 + 126 - 33 + 14], new BlockFurnace(" ".length() != 0).setHardness(3.5f).setStepSound(Block.soundTypePiston).setLightLevel(0.875f).setUnlocalizedName(Block.I[117 + 87 - 82 + 22]));
        registerBlock(0x66 ^ 0x59, Block.I[135 + 59 - 99 + 50], new BlockStandingSign().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[6 + 83 + 25 + 32]).disableStats());
        registerBlock(0xDE ^ 0x9E, Block.I[60 + 138 - 96 + 45], new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[39 + 52 - 75 + 132]).disableStats());
        registerBlock(0x2F ^ 0x6E, Block.I[89 + 28 + 19 + 13], new BlockLadder().setHardness(0.4f).setStepSound(Block.soundTypeLadder).setUnlocalizedName(Block.I[146 + 122 - 153 + 35]));
        registerBlock(0x3 ^ 0x41, Block.I[110 + 144 - 230 + 127], new BlockRail().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[113 + 22 - 33 + 50]));
        registerBlock(0xCE ^ 0x8D, Block.I[81 + 50 - 106 + 128], new BlockStairs(setCreativeTab.getDefaultState()).setUnlocalizedName(Block.I[112 + 93 - 170 + 119]));
        registerBlock(0x1C ^ 0x58, Block.I[132 + 78 - 80 + 25], new BlockWallSign().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[64 + 42 + 13 + 37]).disableStats());
        registerBlock(0x15 ^ 0x50, Block.I[14 + 97 - 108 + 154], new BlockLever().setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[21 + 110 - 30 + 57]));
        registerBlock(0xE9 ^ 0xAF, Block.I[43 + 23 + 37 + 56], new BlockPressurePlate(Material.rock, BlockPressurePlate.Sensitivity.MOBS).setHardness(0.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[130 + 115 - 208 + 123]));
        registerBlock(0xE3 ^ 0xA4, Block.I[49 + 59 - 16 + 69], new BlockDoor(Material.iron).setHardness(5.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[25 + 74 + 61 + 2]).disableStats());
        registerBlock(0x62 ^ 0x2A, Block.I[34 + 119 - 135 + 145], new BlockPressurePlate(Material.wood, BlockPressurePlate.Sensitivity.EVERYTHING).setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[4 + 91 - 80 + 149]));
        registerBlock(0x29 ^ 0x60, Block.I[41 + 101 - 46 + 69], new BlockRedstoneOre("".length() != 0).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[19 + 88 - 54 + 113]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x8B ^ 0xC1, Block.I[35 + 29 - 21 + 124], new BlockRedstoneOre(" ".length() != 0).setLightLevel(0.625f).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[45 + 105 - 69 + 87]));
        registerBlock(0xE2 ^ 0xA9, Block.I[3 + 11 + 89 + 66], new BlockRedstoneTorch("".length() != 0).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[81 + 17 - 74 + 146]));
        registerBlock(0x40 ^ 0xC, Block.I[45 + 65 + 52 + 9], new BlockRedstoneTorch(" ".length() != 0).setHardness(0.0f).setLightLevel(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[39 + 65 - 78 + 146]).setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(0x1 ^ 0x4C, Block.I[74 + 121 - 34 + 12], new BlockButtonStone().setHardness(0.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[68 + 16 - 9 + 99]));
        registerBlock(0xF5 ^ 0xBB, Block.I[38 + 65 - 81 + 153], new BlockSnow().setHardness(0.1f).setStepSound(Block.soundTypeSnow).setUnlocalizedName(Block.I[105 + 150 - 234 + 155]).setLightOpacity("".length()));
        registerBlock(0x61 ^ 0x2E, Block.I[90 + 139 - 219 + 167], new BlockIce().setHardness(0.5f).setLightOpacity("   ".length()).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[118 + 164 - 140 + 36]));
        registerBlock(0x79 ^ 0x29, Block.I[147 + 108 - 221 + 145], new BlockSnowBlock().setHardness(0.2f).setStepSound(Block.soundTypeSnow).setUnlocalizedName(Block.I[163 + 36 - 181 + 162]));
        registerBlock(0x75 ^ 0x24, Block.I[115 + 28 - 2 + 40], new BlockCactus().setHardness(0.4f).setStepSound(Block.soundTypeCloth).setUnlocalizedName(Block.I[23 + 74 + 25 + 60]));
        registerBlock(0x76 ^ 0x24, Block.I[158 + 97 - 141 + 69], new BlockClay().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setUnlocalizedName(Block.I[83 + 11 + 10 + 80]));
        registerBlock(0x5B ^ 0x8, Block.I[62 + 82 - 76 + 117], new BlockReed().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[105 + 141 - 174 + 114]).disableStats());
        registerBlock(0x2 ^ 0x56, Block.I[140 + 18 - 13 + 42], new BlockJukebox().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[64 + 7 + 31 + 86]));
        registerBlock(0x32 ^ 0x67, Block.I[13 + 133 - 106 + 149], new BlockFence(Material.wood, BlockPlanks.EnumType.OAK.func_181070_c()).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[144 + 5 + 28 + 13]));
        final Block setUnlocalizedName6 = new BlockPumpkin().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[14 + 164 - 61 + 74]);
        registerBlock(0x4B ^ 0x1D, Block.I[39 + 59 + 53 + 41], setUnlocalizedName6);
        registerBlock(0xC7 ^ 0x90, Block.I[182 + 48 - 130 + 93], new BlockNetherrack().setHardness(0.4f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[181 + 130 - 145 + 28]));
        registerBlock(0xF3 ^ 0xAB, Block.I[124 + 70 - 159 + 160], new BlockSoulSand().setHardness(0.5f).setStepSound(Block.soundTypeSand).setUnlocalizedName(Block.I[99 + 141 - 237 + 193]));
        registerBlock(0x70 ^ 0x29, Block.I[194 + 108 - 113 + 8], new BlockGlowstone(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setLightLevel(1.0f).setUnlocalizedName(Block.I[109 + 69 - 138 + 158]));
        registerBlock(0x1B ^ 0x41, Block.I[138 + 26 - 36 + 71], new BlockPortal().setHardness(-1.0f).setStepSound(Block.soundTypeGlass).setLightLevel(0.75f).setUnlocalizedName(Block.I[45 + 184 - 41 + 12]));
        registerBlock(0xE7 ^ 0xBC, Block.I[162 + 89 - 233 + 183], new BlockPumpkin().setHardness(1.0f).setStepSound(Block.soundTypeWood).setLightLevel(1.0f).setUnlocalizedName(Block.I[188 + 25 - 100 + 89]));
        registerBlock(0xCD ^ 0x91, Block.I[179 + 147 - 209 + 86], new BlockCake().setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName(Block.I[111 + 80 - 162 + 175]).disableStats());
        registerBlock(0x45 ^ 0x18, Block.I[61 + 113 - 136 + 167], new BlockRedstoneRepeater("".length() != 0).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[154 + 143 - 188 + 97]).disableStats());
        registerBlock(0x6F ^ 0x31, Block.I[69 + 92 - 137 + 183], new BlockRedstoneRepeater(" ".length() != 0).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[69 + 127 - 37 + 49]).disableStats());
        registerBlock(0x6D ^ 0x32, Block.I[12 + 2 + 174 + 21], new BlockStainedGlass(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[74 + 158 - 169 + 147]));
        registerBlock(0xD1 ^ 0xB1, Block.I[170 + 13 - 67 + 95], new BlockTrapDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[160 + 195 - 269 + 126]).disableStats());
        registerBlock(0xE7 ^ 0x86, Block.I[138 + 131 - 164 + 108], new BlockSilverfish().setHardness(0.75f).setUnlocalizedName(Block.I[20 + 123 + 13 + 58]));
        final Block setUnlocalizedName7 = new BlockStoneBrick().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[118 + 128 - 168 + 137]);
        registerBlock(0x4F ^ 0x2D, Block.I[136 + 11 - 109 + 178], setUnlocalizedName7);
        registerBlock(0xDD ^ 0xBE, Block.I[129 + 94 - 142 + 136], new BlockHugeMushroom(Material.wood, MapColor.dirtColor, setUnlocalizedName3).setHardness(0.2f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[187 + 2 - 116 + 145]));
        registerBlock(0xE1 ^ 0x85, Block.I[63 + 98 - 36 + 94], new BlockHugeMushroom(Material.wood, MapColor.redColor, setUnlocalizedName4).setHardness(0.2f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[21 + 119 - 69 + 149]));
        registerBlock(0xC4 ^ 0xA1, Block.I[132 + 43 - 137 + 183], new BlockPane(Material.iron, " ".length() != 0).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[207 + 41 - 177 + 151]));
        registerBlock(0xE ^ 0x68, Block.I[210 + 10 - 162 + 165], new BlockPane(Material.glass, "".length() != 0).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[69 + 143 - 27 + 39]));
        final Block setUnlocalizedName8 = new BlockMelon().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[197 + 0 - 192 + 220]);
        registerBlock(0x6C ^ 0xB, Block.I[4 + 86 + 112 + 24], setUnlocalizedName8);
        registerBlock(0xFE ^ 0x96, Block.I[35 + 76 - 19 + 135], new BlockStem(setUnlocalizedName6).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[85 + 100 - 165 + 208]));
        registerBlock(0x37 ^ 0x5E, Block.I[45 + 137 - 97 + 144], new BlockStem(setUnlocalizedName8).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[170 + 35 - 48 + 73]));
        registerBlock(0x2C ^ 0x46, Block.I[197 + 77 - 180 + 137], new BlockVine().setHardness(0.2f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[61 + 174 - 153 + 150]));
        registerBlock(0x2E ^ 0x45, Block.I[35 + 11 - 35 + 222], new BlockFenceGate(BlockPlanks.EnumType.OAK).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[231 + 208 - 434 + 229]));
        registerBlock(0x77 ^ 0x1B, Block.I[71 + 10 + 58 + 96], new BlockStairs(setCreativeTab2.getDefaultState()).setUnlocalizedName(Block.I[7 + 170 + 17 + 42]));
        registerBlock(0xF1 ^ 0x9C, Block.I[225 + 134 - 355 + 233], new BlockStairs(setUnlocalizedName7.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT)).setUnlocalizedName(Block.I[211 + 192 - 183 + 18]));
        registerBlock(0xC1 ^ 0xAF, Block.I[4 + 199 + 22 + 14], new BlockMycelium().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[19 + 93 + 19 + 109]));
        registerBlock(0x1 ^ 0x6E, Block.I[9 + 178 - 153 + 207], new BlockLilyPad().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[86 + 121 - 10 + 45]));
        final Block setCreativeTab3 = new BlockNetherBrick().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[44 + 56 - 22 + 165]).setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(0x4B ^ 0x3B, Block.I[83 + 127 - 197 + 231], setCreativeTab3);
        registerBlock(0x54 ^ 0x25, Block.I[202 + 188 - 194 + 49], new BlockFence(Material.rock, MapColor.netherrackColor).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[151 + 25 - 163 + 233]));
        registerBlock(0x42 ^ 0x30, Block.I[146 + 224 - 156 + 33], new BlockStairs(setCreativeTab3.getDefaultState()).setUnlocalizedName(Block.I[35 + 169 + 16 + 28]));
        registerBlock(0xCD ^ 0xBE, Block.I[208 + 156 - 327 + 212], new BlockNetherWart().setUnlocalizedName(Block.I[58 + 140 - 121 + 173]));
        registerBlock(0xD9 ^ 0xAD, Block.I[6 + 55 + 90 + 100], new BlockEnchantmentTable().setHardness(5.0f).setResistance(2000.0f).setUnlocalizedName(Block.I[73 + 113 + 42 + 24]));
        registerBlock(0x1D ^ 0x68, Block.I[192 + 125 - 169 + 105], new BlockBrewingStand().setHardness(0.5f).setLightLevel(0.125f).setUnlocalizedName(Block.I[77 + 29 + 5 + 143]));
        registerBlock(0x14 ^ 0x62, Block.I[193 + 62 - 153 + 153], new BlockCauldron().setHardness(2.0f).setUnlocalizedName(Block.I[236 + 45 - 40 + 15]));
        registerBlock(0x1A ^ 0x6D, Block.I[110 + 159 - 17 + 5], new BlockEndPortal(Material.portal).setHardness(-1.0f).setResistance(6000000.0f));
        registerBlock(0xE2 ^ 0x9A, Block.I[26 + 122 + 51 + 59], new BlockEndPortalFrame().setStepSound(Block.soundTypeGlass).setLightLevel(0.125f).setHardness(-1.0f).setUnlocalizedName(Block.I[117 + 20 + 9 + 113]).setResistance(6000000.0f).setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(0x40 ^ 0x39, Block.I[197 + 149 - 209 + 123], new Block(Material.rock, MapColor.sandColor).setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[142 + 200 - 86 + 5]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(0x75 ^ 0xF, Block.I[135 + 12 - 72 + 187], new BlockDragonEgg().setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundTypePiston).setLightLevel(0.125f).setUnlocalizedName(Block.I[45 + 240 - 184 + 162]));
        registerBlock(0x3D ^ 0x46, Block.I[8 + 52 + 57 + 147], new BlockRedstoneLight("".length() != 0).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[85 + 256 - 326 + 250]).setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(0x47 ^ 0x3B, Block.I[19 + 97 - 18 + 168], new BlockRedstoneLight(" ".length() != 0).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[238 + 246 - 218 + 1]));
        registerBlock(0xF5 ^ 0x88, Block.I[202 + 209 - 263 + 120], new BlockDoubleWoodSlab().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[101 + 206 - 298 + 260]));
        registerBlock(0x54 ^ 0x2A, Block.I[238 + 209 - 234 + 57], new BlockHalfWoodSlab().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[245 + 210 - 442 + 258]));
        registerBlock(63 + 67 - 26 + 23, Block.I[265 + 77 - 275 + 205], new BlockCocoa().setHardness(0.2f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[200 + 254 - 310 + 129]));
        registerBlock(118 + 45 - 126 + 91, Block.I[113 + 165 - 19 + 15], new BlockStairs(setUnlocalizedName2.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH)).setUnlocalizedName(Block.I[14 + 166 + 87 + 8]));
        registerBlock(23 + 29 - 44 + 121, Block.I[35 + 128 - 126 + 239], new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[223 + 166 - 186 + 74]));
        registerBlock(57 + 76 - 67 + 64, Block.I[37 + 121 - 9 + 129], new BlockEnderChest().setHardness(22.5f).setResistance(1000.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[209 + 171 - 178 + 77]).setLightLevel(0.5f));
        registerBlock(38 + 128 - 58 + 23, Block.I[192 + 11 + 32 + 45], new BlockTripWireHook().setUnlocalizedName(Block.I[119 + 226 - 150 + 86]));
        registerBlock(82 + 107 - 103 + 46, Block.I[190 + 101 - 24 + 15], new BlockTripWire().setUnlocalizedName(Block.I[261 + 247 - 328 + 103]));
        registerBlock(69 + 61 - 123 + 126, Block.I[90 + 17 - 12 + 189], new Block(Material.iron, MapColor.emeraldColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[89 + 251 - 199 + 144]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(73 + 90 - 81 + 52, Block.I[71 + 70 - 76 + 221], new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE)).setUnlocalizedName(Block.I[175 + 187 - 176 + 101]));
        registerBlock(124 + 66 - 163 + 108, Block.I[263 + 198 - 317 + 144], new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH)).setUnlocalizedName(Block.I[193 + 105 - 119 + 110]));
        registerBlock(66 + 32 + 3 + 35, Block.I[166 + 252 - 378 + 250], new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE)).setUnlocalizedName(Block.I[264 + 176 - 279 + 130]));
        registerBlock(51 + 27 + 26 + 33, Block.I[198 + 241 - 176 + 29], new BlockCommandBlock().setBlockUnbreakable().setResistance(6000000.0f).setUnlocalizedName(Block.I[108 + 217 - 179 + 147]));
        registerBlock(131 + 7 - 112 + 112, Block.I[228 + 173 - 203 + 96], new BlockBeacon().setUnlocalizedName(Block.I[227 + 257 - 439 + 250]).setLightLevel(1.0f));
        registerBlock(27 + 31 + 46 + 35, Block.I[143 + 194 - 67 + 26], new BlockWall(setCreativeTab).setUnlocalizedName(Block.I[221 + 31 - 147 + 192]));
        registerBlock(132 + 39 - 101 + 70, Block.I[13 + 219 - 101 + 167], new BlockFlowerPot().setHardness(0.0f).setStepSound(Block.soundTypeStone).setUnlocalizedName(Block.I[173 + 127 - 128 + 127]));
        registerBlock(58 + 107 - 122 + 98, Block.I[150 + 189 - 179 + 140], new BlockCarrot().setUnlocalizedName(Block.I[180 + 190 - 157 + 88]));
        registerBlock(130 + 64 - 84 + 32, Block.I[64 + 23 - 21 + 236], new BlockPotato().setUnlocalizedName(Block.I[138 + 159 - 156 + 162]));
        registerBlock(74 + 79 - 107 + 97, Block.I[172 + 257 - 364 + 239], new BlockButtonWood().setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[263 + 225 - 187 + 4]));
        registerBlock(68 + 99 - 81 + 58, Block.I[244 + 149 - 325 + 238], new BlockSkull().setHardness(1.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[228 + 186 - 350 + 243]));
        registerBlock(85 + 133 - 117 + 44, Block.I[156 + 48 + 4 + 100], new BlockAnvil().setHardness(5.0f).setStepSound(Block.soundTypeAnvil).setResistance(2000.0f).setUnlocalizedName(Block.I[137 + 148 - 256 + 280]));
        registerBlock(122 + 129 - 140 + 35, Block.I[298 + 184 - 341 + 169], new BlockChest(" ".length()).setHardness(2.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[261 + 127 - 371 + 294]));
        registerBlock(120 + 48 - 123 + 102, Block.I[280 + 223 - 298 + 107], new BlockPressurePlateWeighted(Material.iron, 0x4 ^ 0xB, MapColor.goldColor).setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[3 + 303 - 216 + 223]));
        registerBlock(46 + 30 - 53 + 125, Block.I[193 + 273 - 224 + 72], new BlockPressurePlateWeighted(Material.iron, 44 + 89 - 125 + 142).setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[228 + 313 - 230 + 4]));
        registerBlock(67 + 107 - 36 + 11, Block.I[211 + 40 - 205 + 270], new BlockRedstoneComparator("".length() != 0).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[142 + 64 + 53 + 58]).disableStats());
        registerBlock(126 + 114 - 107 + 17, Block.I[40 + 205 - 101 + 174], new BlockRedstoneComparator(" ".length() != 0).setHardness(0.0f).setLightLevel(0.625f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[207 + 8 - 95 + 199]).disableStats());
        registerBlock(147 + 47 - 184 + 141, Block.I[30 + 151 - 4 + 143], new BlockDaylightDetector((boolean)("".length() != 0)));
        registerBlock(6 + 23 + 102 + 21, Block.I[38 + 92 + 175 + 16], new BlockCompressedPowered(Material.iron, MapColor.tntColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[224 + 149 - 260 + 209]).setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(146 + 65 - 199 + 141, Block.I[206 + 263 - 340 + 194], new BlockOre(MapColor.netherrackColor).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[105 + 315 - 345 + 249]));
        registerBlock(44 + 39 + 41 + 30, Block.I[2 + 40 - 33 + 316], new BlockHopper().setHardness(3.0f).setResistance(8.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[229 + 299 - 227 + 25]));
        final Block setUnlocalizedName9 = new BlockQuartz().setStepSound(Block.soundTypePiston).setHardness(0.8f).setUnlocalizedName(Block.I[32 + 17 + 64 + 214]);
        registerBlock(135 + 149 - 168 + 39, Block.I[184 + 319 - 340 + 165], setUnlocalizedName9);
        registerBlock(104 + 78 - 127 + 101, Block.I[288 + 256 - 466 + 251], new BlockStairs(setUnlocalizedName9.getDefaultState().withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.DEFAULT)).setUnlocalizedName(Block.I[111 + 10 + 65 + 144]));
        registerBlock(112 + 113 - 85 + 17, Block.I[201 + 177 - 302 + 255], new BlockRailPowered().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[132 + 288 - 189 + 101]));
        registerBlock(23 + 117 - 15 + 33, Block.I[210 + 206 - 253 + 170], new BlockDropper().setHardness(3.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[277 + 16 - 40 + 81]));
        registerBlock(70 + 125 - 173 + 137, Block.I[123 + 110 - 178 + 280], new BlockColored(Material.rock).setHardness(1.25f).setResistance(7.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[169 + 292 - 131 + 6]));
        registerBlock(19 + 159 - 70 + 52, Block.I[178 + 6 - 54 + 207], new BlockStainedGlassPane().setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[302 + 172 - 254 + 118]));
        registerBlock(37 + 2 + 16 + 106, Block.I[47 + 51 + 57 + 184], new BlockNewLeaf().setUnlocalizedName(Block.I[333 + 36 - 29 + 0]));
        registerBlock(74 + 91 - 69 + 66, Block.I[190 + 334 - 274 + 91], new BlockNewLog().setUnlocalizedName(Block.I[161 + 164 - 281 + 298]));
        registerBlock(10 + 130 - 41 + 64, Block.I[260 + 229 - 228 + 82], new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA)).setUnlocalizedName(Block.I[302 + 40 - 96 + 98]));
        registerBlock(38 + 141 - 166 + 151, Block.I[41 + 41 + 243 + 20], new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK)).setUnlocalizedName(Block.I[272 + 10 - 55 + 119]));
        registerBlock(54 + 162 - 162 + 111, Block.I[171 + 181 - 276 + 271], new BlockSlime().setUnlocalizedName(Block.I[144 + 82 + 30 + 92]).setStepSound(Block.SLIME_SOUND));
        registerBlock(64 + 25 + 38 + 39, Block.I[115 + 281 - 337 + 290], new BlockBarrier().setUnlocalizedName(Block.I[51 + 110 - 136 + 325]));
        registerBlock(22 + 10 + 1 + 134, Block.I[18 + 120 + 29 + 184], new BlockTrapDoor(Material.iron).setHardness(5.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName(Block.I[312 + 161 - 358 + 237]).disableStats());
        registerBlock(154 + 57 - 137 + 94, Block.I[210 + 282 - 449 + 310], new BlockPrismarine().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[148 + 217 - 180 + 169]));
        registerBlock(90 + 42 - 73 + 110, Block.I[298 + 112 - 74 + 19], new BlockSeaLantern(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setLightLevel(1.0f).setUnlocalizedName(Block.I[56 + 146 + 150 + 4]));
        registerBlock(112 + 80 - 160 + 138, Block.I[320 + 220 - 318 + 135], new BlockHay().setHardness(0.5f).setStepSound(Block.soundTypeGrass).setUnlocalizedName(Block.I[280 + 280 - 305 + 103]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(3 + 51 + 61 + 56, Block.I[46 + 83 + 222 + 8], new BlockCarpet().setHardness(0.1f).setStepSound(Block.soundTypeCloth).setUnlocalizedName(Block.I[269 + 286 - 552 + 357]).setLightOpacity("".length()));
        registerBlock(128 + 5 - 25 + 64, Block.I[219 + 337 - 368 + 173], new BlockHardenedClay().setHardness(1.25f).setResistance(7.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[68 + 276 - 182 + 200]));
        registerBlock(15 + 161 - 22 + 19, Block.I[207 + 105 - 281 + 332], new Block(Material.rock, MapColor.blackColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[247 + 216 - 245 + 146]).setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(30 + 165 - 43 + 22, Block.I[69 + 89 + 199 + 8], new BlockPackedIce().setHardness(0.5f).setStepSound(Block.soundTypeGlass).setUnlocalizedName(Block.I[262 + 27 - 138 + 215]));
        registerBlock(116 + 151 - 230 + 138, Block.I[146 + 172 - 138 + 187], new BlockDoublePlant());
        registerBlock(11 + 5 + 52 + 108, Block.I[247 + 286 - 395 + 230], new BlockBanner.BlockBannerStanding().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[30 + 339 - 289 + 289]).disableStats());
        registerBlock(63 + 147 - 198 + 165, Block.I[358 + 268 - 279 + 23], new BlockBanner.BlockBannerHanging().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[53 + 321 - 101 + 98]).disableStats());
        registerBlock(166 + 22 - 102 + 92, Block.I[231 + 194 - 248 + 195], new BlockDaylightDetector((boolean)(" ".length() != 0)));
        final Block setUnlocalizedName10 = new BlockRedSandstone().setStepSound(Block.soundTypePiston).setHardness(0.8f).setUnlocalizedName(Block.I[12 + 43 - 11 + 329]);
        registerBlock(72 + 147 - 143 + 103, Block.I[153 + 269 - 73 + 25], setUnlocalizedName10);
        registerBlock(100 + 114 - 35 + 1, Block.I[77 + 34 + 173 + 91], new BlockStairs(setUnlocalizedName10.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH)).setUnlocalizedName(Block.I[20 + 234 + 43 + 79]));
        registerBlock(64 + 144 - 194 + 167, Block.I[141 + 128 - 134 + 242], new BlockDoubleStoneSlabNew().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[198 + 13 - 22 + 189]));
        registerBlock(124 + 41 - 77 + 94, Block.I[339 + 100 - 402 + 342], new BlockHalfStoneSlabNew().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(Block.I[314 + 186 - 240 + 120]));
        registerBlock(127 + 2 - 30 + 84, Block.I[19 + 65 + 26 + 271], new BlockFenceGate(BlockPlanks.EnumType.SPRUCE).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[188 + 57 - 188 + 325]));
        registerBlock(150 + 133 - 281 + 182, Block.I[381 + 372 - 552 + 182], new BlockFenceGate(BlockPlanks.EnumType.BIRCH).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[358 + 105 - 234 + 155]));
        registerBlock(105 + 182 - 256 + 154, Block.I[116 + 233 - 92 + 128], new BlockFenceGate(BlockPlanks.EnumType.JUNGLE).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[84 + 187 + 4 + 111]));
        registerBlock(143 + 143 - 283 + 183, Block.I[151 + 179 - 100 + 157], new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[14 + 150 - 42 + 266]));
        registerBlock(108 + 178 - 123 + 24, Block.I[167 + 227 - 325 + 320], new BlockFenceGate(BlockPlanks.EnumType.ACACIA).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[144 + 277 - 160 + 129]));
        registerBlock(62 + 122 - 144 + 148, Block.I[347 + 336 - 457 + 165], new BlockFence(Material.wood, BlockPlanks.EnumType.SPRUCE.func_181070_c()).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[377 + 227 - 475 + 263]));
        registerBlock(48 + 175 - 152 + 118, Block.I[123 + 293 - 280 + 257], new BlockFence(Material.wood, BlockPlanks.EnumType.BIRCH.func_181070_c()).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[245 + 60 - 198 + 287]));
        registerBlock(160 + 184 - 264 + 110, Block.I[310 + 177 - 256 + 164], new BlockFence(Material.wood, BlockPlanks.EnumType.JUNGLE.func_181070_c()).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[25 + 395 - 72 + 48]));
        registerBlock(17 + 82 + 48 + 44, Block.I[196 + 180 - 166 + 187], new BlockFence(Material.wood, BlockPlanks.EnumType.DARK_OAK.func_181070_c()).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[202 + 273 - 440 + 363]));
        registerBlock(36 + 30 + 62 + 64, Block.I[39 + 51 + 14 + 295], new BlockFence(Material.wood, BlockPlanks.EnumType.ACACIA.func_181070_c()).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[160 + 71 - 140 + 309]));
        registerBlock(160 + 32 - 115 + 116, Block.I[322 + 292 - 246 + 33], new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[388 + 154 - 349 + 209]).disableStats());
        registerBlock(95 + 47 - 85 + 137, Block.I[290 + 377 - 349 + 85], new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[325 + 297 - 486 + 268]).disableStats());
        registerBlock(32 + 54 - 72 + 181, Block.I[344 + 330 - 353 + 84], new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[103 + 28 - 112 + 387]).disableStats());
        registerBlock(39 + 141 - 127 + 143, Block.I[20 + 374 - 208 + 221], new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[316 + 203 - 424 + 313]).disableStats());
        registerBlock(114 + 100 - 158 + 141, Block.I[307 + 339 - 299 + 62], new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName(Block.I[406 + 203 - 600 + 401]).disableStats());
        Block.blockRegistry.validateKey();
        final Iterator<Block> iterator = (Iterator<Block>)Block.blockRegistry.iterator();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Block block = iterator.next();
            if (block.blockMaterial == Material.air) {
                block.useNeighborBrightness = ("".length() != 0);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                continue;
            }
            else {
                int useNeighborBrightness = "".length();
                final boolean b = block instanceof BlockStairs;
                final boolean b2 = block instanceof BlockSlab;
                int n;
                if (block == setUnlocalizedName5) {
                    n = " ".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                final int n2 = n;
                final boolean translucent = block.translucent;
                int n3;
                if (block.lightOpacity == 0) {
                    n3 = " ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final int n4 = n3;
                if (b || b2 || n2 != 0 || translucent || n4 != 0) {
                    useNeighborBrightness = " ".length();
                }
                block.useNeighborBrightness = (useNeighborBrightness != 0);
            }
        }
        final Iterator<Block> iterator2 = (Iterator<Block>)Block.blockRegistry.iterator();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final Block block2 = iterator2.next();
            final Iterator iterator3 = block2.getBlockState().getValidStates().iterator();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (iterator3.hasNext()) {
                final IBlockState blockState = iterator3.next();
                Block.BLOCK_STATE_IDS.put(blockState, Block.blockRegistry.getIDForObject(block2) << (0x70 ^ 0x74) | block2.getMetaFromState(blockState));
            }
        }
    }
    
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        int n = "".length();
        final Item itemFromBlock = Item.getItemFromBlock(this);
        if (itemFromBlock != null && itemFromBlock.getHasSubtypes()) {
            n = this.getMetaFromState(blockState);
        }
        return new ItemStack(itemFromBlock, " ".length(), n);
    }
    
    public int quantityDropped(final Random random) {
        return " ".length();
    }
    
    public boolean getEnableStats() {
        return this.enableStats;
    }
    
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.updateTick(world, blockPos, blockState, random);
    }
    
    public boolean isCollidable() {
        return " ".length() != 0;
    }
    
    public boolean isOpaqueCube() {
        return " ".length() != 0;
    }
    
    protected Block setResistance(final float n) {
        this.blockResistance = n * 3.0f;
        return this;
    }
    
    public final double getBlockBoundsMinY() {
        return this.minY;
    }
    
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }
    
    public Block setLightOpacity(final int lightOpacity) {
        this.lightOpacity = lightOpacity;
        return this;
    }
    
    public boolean isBlockNormalCube() {
        if (this.blockMaterial.blocksMovement() && this.isFullCube()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public final double getBlockBoundsMaxY() {
        return this.maxY;
    }
    
    public static void spawnAsEntity(final World world, final BlockPos blockPos, final ItemStack itemStack) {
        if (!world.isRemote && world.getGameRules().getBoolean(Block.I[0xB9 ^ 0xA9])) {
            final float n = 0.5f;
            final EntityItem entityItem = new EntityItem(world, blockPos.getX() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), blockPos.getY() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), blockPos.getZ() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), itemStack);
            entityItem.setDefaultPickupDelay();
            world.spawnEntityInWorld(entityItem);
        }
    }
    
    public static Block getBlockFromName(final String s) {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        if (Block.blockRegistry.containsKey(resourceLocation)) {
            return Block.blockRegistry.getObject(resourceLocation);
        }
        try {
            return Block.blockRegistry.getObjectById(Integer.parseInt(s));
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }
    
    public static Block getBlockFromItem(final Item item) {
        Block block;
        if (item instanceof ItemBlock) {
            block = ((ItemBlock)item).getBlock();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            block = null;
        }
        return block;
    }
    
    public boolean func_181623_g() {
        if (!this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, BlockPos down) {
        final Block block = blockAccess.getBlockState(down).getBlock();
        final int combinedLight = blockAccess.getCombinedLight(down, block.getLightValue());
        if (combinedLight == 0 && block instanceof BlockSlab) {
            down = down.down();
            return blockAccess.getCombinedLight(down, blockAccess.getBlockState(down).getBlock().getLightValue());
        }
        return combinedLight;
    }
    
    public final void dropBlockAsItem(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        this.dropBlockAsItemWithChance(world, blockPos, blockState, 1.0f, n);
    }
    
    public int getRenderType() {
        return "   ".length();
    }
    
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
    }
    
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(this);
    }
    
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
        entityPlayer.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(entityPlayer)) {
            final ItemStack stackedBlock = this.createStackedBlock(blockState);
            if (stackedBlock != null) {
                spawnAsEntity(world, blockPos, stackedBlock);
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
        }
        else {
            this.dropBlockAsItem(world, blockPos, blockState, EnchantmentHelper.getFortuneModifier(entityPlayer));
        }
    }
    
    public static int getIdFromBlock(final Block block) {
        return Block.blockRegistry.getIDForObject(block);
    }
    
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return this.canPlaceBlockAt(world, blockPos);
    }
    
    private static void registerBlock(final int n, final ResourceLocation resourceLocation, final Block block) {
        Block.blockRegistry.register(n, resourceLocation, block);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean canProvidePower() {
        return "".length() != 0;
    }
    
    protected Block setStepSound(final SoundType stepSound) {
        this.stepSound = stepSound;
        return this;
    }
    
    protected boolean canSilkHarvest() {
        if (this.isFullCube() && !this.isBlockContainer) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isAssociatedBlock(final Block block) {
        if (this == block) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return 4041617 + 593697 - 3084713 + 15226614;
    }
    
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
    }
    
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState != null && !blockState.getPropertyNames().isEmpty()) {
            throw new IllegalArgumentException(Block.I[0x1B ^ 0x15] + blockState + Block.I[0x1F ^ 0x10]);
        }
        return "".length();
    }
    
    protected Block setHardness(final float blockHardness) {
        this.blockHardness = blockHardness;
        if (this.blockResistance < blockHardness * 5.0f) {
            this.blockResistance = blockHardness * 5.0f;
        }
        return this;
    }
    
    public final int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.colorMultiplier(blockAccess, blockPos, "".length());
    }
    
    protected final void setDefaultState(final IBlockState defaultBlockState) {
        this.defaultBlockState = defaultBlockState;
    }
    
    public MapColor getMapColor(final IBlockState blockState) {
        return this.field_181083_K;
    }
    
    public Block(final Material blockMaterial, final MapColor field_181083_K) {
        this.enableStats = (" ".length() != 0);
        this.stepSound = Block.soundTypeStone;
        this.blockParticleGravity = 1.0f;
        this.slipperiness = 0.6f;
        this.blockMaterial = blockMaterial;
        this.field_181083_K = field_181083_K;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.fullBlock = this.isOpaqueCube();
        int length;
        if (this.isOpaqueCube()) {
            length = 16 + 74 + 88 + 77;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        this.lightOpacity = length;
        int translucent;
        if (blockMaterial.blocksLight()) {
            translucent = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            translucent = " ".length();
        }
        this.translucent = (translucent != 0);
        this.blockState = this.createBlockState();
        this.setDefaultState(this.blockState.getBaseState());
    }
    
    public final double getBlockBoundsMaxZ() {
        return this.maxZ;
    }
    
    public boolean isFlowerPot() {
        return "".length() != 0;
    }
    
    public boolean isBlockSolid(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return blockAccess.getBlockState(blockPos).getBlock().getMaterial().isSolid();
    }
    
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
    }
    
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.NONE;
    }
    
    public static IBlockState getStateById(final int n) {
        return getBlockById(n & 3007 + 2058 - 3150 + 2180).getStateFromMeta(n >> (0x48 ^ 0x44) & (0x18 ^ 0x17));
    }
    
    public static boolean isEqualTo(final Block block, final Block block2) {
        int n;
        if (block != null && block2 != null) {
            if (block == block2) {
                n = " ".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                n = (block.isAssociatedBlock(block2) ? 1 : 0);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
    }
    
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ);
    }
    
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return "".length();
    }
    
    public float getExplosionResistance(final Entity entity) {
        return this.blockResistance / 5.0f;
    }
    
    public boolean hasComparatorInputOverride() {
        return "".length() != 0;
    }
    
    public int damageDropped(final IBlockState blockState) {
        return "".length();
    }
    
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + Block.I[0x6 ^ 0x17]);
    }
    
    public void setBlockBoundsForItemRender() {
    }
    
    public boolean canDropFromExplosion(final Explosion explosion) {
        return " ".length() != 0;
    }
    
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        int n;
        if (this.blockMaterial.blocksMovement()) {
            n = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public boolean isNormalCube() {
        if (this.blockMaterial.isOpaque() && this.isFullCube() && !this.canProvidePower()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected Block(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    public final double getBlockBoundsMinZ() {
        return this.minZ;
    }
    
    public Material getMaterial() {
        return this.blockMaterial;
    }
    
    public static Block getBlockById(final int n) {
        return Block.blockRegistry.getObjectById(n);
    }
    
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
    }
    
    public enum EnumOffsetType
    {
        XZ(EnumOffsetType.I[" ".length()], " ".length());
        
        private static final String[] I;
        
        NONE(EnumOffsetType.I["".length()], "".length()), 
        XYZ(EnumOffsetType.I["  ".length()], "  ".length());
        
        private static final EnumOffsetType[] ENUM$VALUES;
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u0017\b\u001c\u000b", "YGRNt");
            EnumOffsetType.I[" ".length()] = I("\u0016.", "NtyTG");
            EnumOffsetType.I["  ".length()] = I("\u0015\u000b.", "MRtDh");
        }
        
        private EnumOffsetType(final String s, final int n) {
        }
        
        static {
            I();
            final EnumOffsetType[] enum$VALUES = new EnumOffsetType["   ".length()];
            enum$VALUES["".length()] = EnumOffsetType.NONE;
            enum$VALUES[" ".length()] = EnumOffsetType.XZ;
            enum$VALUES["  ".length()] = EnumOffsetType.XYZ;
            ENUM$VALUES = enum$VALUES;
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class SoundType
    {
        private static final String[] I;
        public final float frequency;
        public final float volume;
        public final String soundName;
        
        public SoundType(final String soundName, final float volume, final float frequency) {
            this.soundName = soundName;
            this.volume = volume;
            this.frequency = frequency;
        }
        
        public String getPlaceSound() {
            return this.getBreakSound();
        }
        
        public float getFrequency() {
            return this.frequency;
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
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("),!C", "MEFmC");
            SoundType.I[" ".length()] = I("0\u001e#\n{", "CjFzU");
        }
        
        static {
            I();
        }
        
        public String getStepSound() {
            return SoundType.I[" ".length()] + this.soundName;
        }
        
        public String getBreakSound() {
            return SoundType.I["".length()] + this.soundName;
        }
        
        public float getVolume() {
            return this.volume;
        }
    }
}
