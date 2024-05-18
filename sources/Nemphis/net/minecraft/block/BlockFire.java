/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;

public class BlockFire
extends Block {
    public static final PropertyInteger field_176543_a = PropertyInteger.create("age", 0, 15);
    public static final PropertyBool field_176540_b = PropertyBool.create("flip");
    public static final PropertyBool field_176544_M = PropertyBool.create("alt");
    public static final PropertyBool field_176545_N = PropertyBool.create("north");
    public static final PropertyBool field_176546_O = PropertyBool.create("east");
    public static final PropertyBool field_176541_P = PropertyBool.create("south");
    public static final PropertyBool field_176539_Q = PropertyBool.create("west");
    public static final PropertyInteger field_176542_R = PropertyInteger.create("upper", 0, 2);
    private final Map field_149849_a = Maps.newIdentityHashMap();
    private final Map field_149848_b = Maps.newIdentityHashMap();
    private static final String __OBFID = "CL_00000245";

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int var4 = pos.getX();
        int var5 = pos.getY();
        int var6 = pos.getZ();
        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) && !Blocks.fire.func_176535_e(worldIn, pos.offsetDown())) {
            boolean var7 = (var4 + var5 + var6 & 1) == 1;
            boolean var8 = (var4 / 2 + var5 / 2 + var6 / 2 & 1) == 1;
            int var9 = 0;
            if (this.func_176535_e(worldIn, pos.offsetUp())) {
                var9 = var7 ? 1 : 2;
            }
            return state.withProperty(field_176545_N, Boolean.valueOf(this.func_176535_e(worldIn, pos.offsetNorth()))).withProperty(field_176546_O, Boolean.valueOf(this.func_176535_e(worldIn, pos.offsetEast()))).withProperty(field_176541_P, Boolean.valueOf(this.func_176535_e(worldIn, pos.offsetSouth()))).withProperty(field_176539_Q, Boolean.valueOf(this.func_176535_e(worldIn, pos.offsetWest()))).withProperty(field_176542_R, Integer.valueOf(var9)).withProperty(field_176540_b, Boolean.valueOf(var8)).withProperty(field_176544_M, Boolean.valueOf(var7));
        }
        return this.getDefaultState();
    }

    protected BlockFire() {
        super(Material.fire);
        this.setDefaultState(this.blockState.getBaseState().withProperty(field_176543_a, Integer.valueOf(0)).withProperty(field_176540_b, Boolean.valueOf(false)).withProperty(field_176544_M, Boolean.valueOf(false)).withProperty(field_176545_N, Boolean.valueOf(false)).withProperty(field_176546_O, Boolean.valueOf(false)).withProperty(field_176541_P, Boolean.valueOf(false)).withProperty(field_176539_Q, Boolean.valueOf(false)).withProperty(field_176542_R, Integer.valueOf(0)));
        this.setTickRandomly(true);
    }

    public static void func_149843_e() {
        Blocks.fire.func_180686_a(Blocks.planks, 5, 20);
        Blocks.fire.func_180686_a(Blocks.double_wooden_slab, 5, 20);
        Blocks.fire.func_180686_a(Blocks.wooden_slab, 5, 20);
        Blocks.fire.func_180686_a(Blocks.oak_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.spruce_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.birch_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.jungle_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.dark_oak_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.acacia_fence_gate, 5, 20);
        Blocks.fire.func_180686_a(Blocks.oak_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.spruce_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.birch_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.jungle_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.dark_oak_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.acacia_fence, 5, 20);
        Blocks.fire.func_180686_a(Blocks.oak_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.birch_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.spruce_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.jungle_stairs, 5, 20);
        Blocks.fire.func_180686_a(Blocks.log, 5, 5);
        Blocks.fire.func_180686_a(Blocks.log2, 5, 5);
        Blocks.fire.func_180686_a(Blocks.leaves, 30, 60);
        Blocks.fire.func_180686_a(Blocks.leaves2, 30, 60);
        Blocks.fire.func_180686_a(Blocks.bookshelf, 30, 20);
        Blocks.fire.func_180686_a(Blocks.tnt, 15, 100);
        Blocks.fire.func_180686_a(Blocks.tallgrass, 60, 100);
        Blocks.fire.func_180686_a(Blocks.double_plant, 60, 100);
        Blocks.fire.func_180686_a(Blocks.yellow_flower, 60, 100);
        Blocks.fire.func_180686_a(Blocks.red_flower, 60, 100);
        Blocks.fire.func_180686_a(Blocks.deadbush, 60, 100);
        Blocks.fire.func_180686_a(Blocks.wool, 30, 60);
        Blocks.fire.func_180686_a(Blocks.vine, 15, 100);
        Blocks.fire.func_180686_a(Blocks.coal_block, 5, 5);
        Blocks.fire.func_180686_a(Blocks.hay_block, 60, 20);
        Blocks.fire.func_180686_a(Blocks.carpet, 60, 20);
    }

    public void func_180686_a(Block p_180686_1_, int p_180686_2_, int p_180686_3_) {
        this.field_149849_a.put(p_180686_1_, p_180686_2_);
        this.field_149848_b.put(p_180686_1_, p_180686_3_);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public int tickRate(World worldIn) {
        return 30;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            Block var5;
            boolean var6;
            if (!this.canPlaceBlockAt(worldIn, pos)) {
                worldIn.setBlockToAir(pos);
            }
            boolean bl = var6 = (var5 = worldIn.getBlockState(pos.offsetDown()).getBlock()) == Blocks.netherrack;
            if (worldIn.provider instanceof WorldProviderEnd && var5 == Blocks.bedrock) {
                var6 = true;
            }
            if (!var6 && worldIn.isRaining() && this.func_176537_d(worldIn, pos)) {
                worldIn.setBlockToAir(pos);
            } else {
                int var7 = (Integer)state.getValue(field_176543_a);
                if (var7 < 15) {
                    state = state.withProperty(field_176543_a, Integer.valueOf(var7 + rand.nextInt(3) / 2));
                    worldIn.setBlockState(pos, state, 4);
                }
                worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));
                if (!var6) {
                    if (!this.func_176533_e(worldIn, pos)) {
                        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) || var7 > 3) {
                            worldIn.setBlockToAir(pos);
                        }
                        return;
                    }
                    if (!this.func_176535_e(worldIn, pos.offsetDown()) && var7 == 15 && rand.nextInt(4) == 0) {
                        worldIn.setBlockToAir(pos);
                        return;
                    }
                }
                boolean var8 = worldIn.func_180502_D(pos);
                int var9 = 0;
                if (var8) {
                    var9 = -50;
                }
                this.func_176536_a(worldIn, pos.offsetEast(), 300 + var9, rand, var7);
                this.func_176536_a(worldIn, pos.offsetWest(), 300 + var9, rand, var7);
                this.func_176536_a(worldIn, pos.offsetDown(), 250 + var9, rand, var7);
                this.func_176536_a(worldIn, pos.offsetUp(), 250 + var9, rand, var7);
                this.func_176536_a(worldIn, pos.offsetNorth(), 300 + var9, rand, var7);
                this.func_176536_a(worldIn, pos.offsetSouth(), 300 + var9, rand, var7);
                int var10 = -1;
                while (var10 <= 1) {
                    int var11 = -1;
                    while (var11 <= 1) {
                        int var12 = -1;
                        while (var12 <= 4) {
                            if (var10 != 0 || var12 != 0 || var11 != 0) {
                                int var15;
                                BlockPos var14;
                                int var13 = 100;
                                if (var12 > 1) {
                                    var13 += (var12 - 1) * 100;
                                }
                                if ((var15 = this.func_176538_m(worldIn, var14 = pos.add(var10, var12, var11))) > 0) {
                                    int var16 = (var15 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (var7 + 30);
                                    if (var8) {
                                        var16 /= 2;
                                    }
                                    if (!(var16 <= 0 || rand.nextInt(var13) > var16 || worldIn.isRaining() && this.func_176537_d(worldIn, var14))) {
                                        int var17 = var7 + rand.nextInt(5) / 4;
                                        if (var17 > 15) {
                                            var17 = 15;
                                        }
                                        worldIn.setBlockState(var14, state.withProperty(field_176543_a, Integer.valueOf(var17)), 3);
                                    }
                                }
                            }
                            ++var12;
                        }
                        ++var11;
                    }
                    ++var10;
                }
            }
        }
    }

    protected boolean func_176537_d(World worldIn, BlockPos p_176537_2_) {
        if (!(worldIn.func_175727_C(p_176537_2_) || worldIn.func_175727_C(p_176537_2_.offsetWest()) || worldIn.func_175727_C(p_176537_2_.offsetEast()) || worldIn.func_175727_C(p_176537_2_.offsetNorth()) || worldIn.func_175727_C(p_176537_2_.offsetSouth()))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean requiresUpdates() {
        return false;
    }

    private int func_176532_c(Block p_176532_1_) {
        Integer var2 = (Integer)this.field_149848_b.get(p_176532_1_);
        return var2 == null ? 0 : var2;
    }

    private int func_176534_d(Block p_176534_1_) {
        Integer var2 = (Integer)this.field_149849_a.get(p_176534_1_);
        return var2 == null ? 0 : var2;
    }

    private void func_176536_a(World worldIn, BlockPos p_176536_2_, int p_176536_3_, Random p_176536_4_, int p_176536_5_) {
        int var6 = this.func_176532_c(worldIn.getBlockState(p_176536_2_).getBlock());
        if (p_176536_4_.nextInt(p_176536_3_) < var6) {
            IBlockState var7 = worldIn.getBlockState(p_176536_2_);
            if (p_176536_4_.nextInt(p_176536_5_ + 10) < 5 && !worldIn.func_175727_C(p_176536_2_)) {
                int var8 = p_176536_5_ + p_176536_4_.nextInt(5) / 4;
                if (var8 > 15) {
                    var8 = 15;
                }
                worldIn.setBlockState(p_176536_2_, this.getDefaultState().withProperty(field_176543_a, Integer.valueOf(var8)), 3);
            } else {
                worldIn.setBlockToAir(p_176536_2_);
            }
            if (var7.getBlock() == Blocks.tnt) {
                Blocks.tnt.onBlockDestroyedByPlayer(worldIn, p_176536_2_, var7.withProperty(BlockTNT.field_176246_a, Boolean.valueOf(true)));
            }
        }
    }

    private boolean func_176533_e(World worldIn, BlockPos p_176533_2_) {
        EnumFacing[] var3 = EnumFacing.values();
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            EnumFacing var6 = var3[var5];
            if (this.func_176535_e(worldIn, p_176533_2_.offset(var6))) {
                return true;
            }
            ++var5;
        }
        return false;
    }

    private int func_176538_m(World worldIn, BlockPos p_176538_2_) {
        if (!worldIn.isAirBlock(p_176538_2_)) {
            return 0;
        }
        int var3 = 0;
        EnumFacing[] var4 = EnumFacing.values();
        int var5 = var4.length;
        int var6 = 0;
        while (var6 < var5) {
            EnumFacing var7 = var4[var6];
            var3 = Math.max(this.func_176534_d(worldIn.getBlockState(p_176538_2_.offset(var7)).getBlock()), var3);
            ++var6;
        }
        return var3;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    public boolean func_176535_e(IBlockAccess p_176535_1_, BlockPos p_176535_2_) {
        if (this.func_176534_d(p_176535_1_.getBlockState(p_176535_2_).getBlock()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) && !this.func_176533_e(worldIn, pos)) {
            return false;
        }
        return true;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) && !this.func_176533_e(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(worldIn, pos)) {
            if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) && !this.func_176533_e(worldIn, pos)) {
                worldIn.setBlockToAir(pos);
            } else {
                worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (rand.nextInt(24) == 0) {
            worldIn.playSound((float)pos.getX() + 0.5f, (float)pos.getY() + 0.5f, (float)pos.getZ() + 0.5f, "fire.fire", 1.0f + rand.nextFloat(), rand.nextFloat() * 0.7f + 0.3f, false);
        }
        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) && !Blocks.fire.func_176535_e(worldIn, pos.offsetDown())) {
            int var5;
            double var6;
            double var8;
            double var10;
            if (Blocks.fire.func_176535_e(worldIn, pos.offsetWest())) {
                var5 = 0;
                while (var5 < 2) {
                    var6 = (double)pos.getX() + rand.nextDouble() * 0.10000000149011612;
                    var8 = (double)pos.getY() + rand.nextDouble();
                    var10 = (double)pos.getZ() + rand.nextDouble();
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0, 0.0, 0.0, new int[0]);
                    ++var5;
                }
            }
            if (Blocks.fire.func_176535_e(worldIn, pos.offsetEast())) {
                var5 = 0;
                while (var5 < 2) {
                    var6 = (double)(pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612;
                    var8 = (double)pos.getY() + rand.nextDouble();
                    var10 = (double)pos.getZ() + rand.nextDouble();
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0, 0.0, 0.0, new int[0]);
                    ++var5;
                }
            }
            if (Blocks.fire.func_176535_e(worldIn, pos.offsetNorth())) {
                var5 = 0;
                while (var5 < 2) {
                    var6 = (double)pos.getX() + rand.nextDouble();
                    var8 = (double)pos.getY() + rand.nextDouble();
                    var10 = (double)pos.getZ() + rand.nextDouble() * 0.10000000149011612;
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0, 0.0, 0.0, new int[0]);
                    ++var5;
                }
            }
            if (Blocks.fire.func_176535_e(worldIn, pos.offsetSouth())) {
                var5 = 0;
                while (var5 < 2) {
                    var6 = (double)pos.getX() + rand.nextDouble();
                    var8 = (double)pos.getY() + rand.nextDouble();
                    var10 = (double)(pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612;
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0, 0.0, 0.0, new int[0]);
                    ++var5;
                }
            }
            if (!Blocks.fire.func_176535_e(worldIn, pos.offsetUp())) return;
            var5 = 0;
            while (var5 < 2) {
                var6 = (double)pos.getX() + rand.nextDouble();
                var8 = (double)(pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612;
                var10 = (double)pos.getZ() + rand.nextDouble();
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0, 0.0, 0.0, new int[0]);
                ++var5;
            }
            return;
        } else {
            int var5 = 0;
            while (var5 < 3) {
                double var6 = (double)pos.getX() + rand.nextDouble();
                double var8 = (double)pos.getY() + rand.nextDouble() * 0.5 + 0.5;
                double var10 = (double)pos.getZ() + rand.nextDouble();
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0, 0.0, 0.0, new int[0]);
                ++var5;
            }
        }
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return MapColor.tntColor;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(field_176543_a, Integer.valueOf(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (Integer)state.getValue(field_176543_a);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, field_176543_a, field_176545_N, field_176546_O, field_176541_P, field_176539_Q, field_176542_R, field_176540_b, field_176544_M);
    }
}

