package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockFire extends Block
{
    public static final PropertyInteger Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    public static final PropertyBool Âµà;
    public static final PropertyBool Ç;
    public static final PropertyBool È;
    public static final PropertyInteger áŠ;
    private final Map ˆáŠ;
    private final Map áŒŠ;
    private static final String £ÂµÄ = "CL_00000245";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 15);
        à¢ = PropertyBool.HorizonCode_Horizon_È("flip");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("alt");
        ¥à = PropertyBool.HorizonCode_Horizon_È("north");
        Âµà = PropertyBool.HorizonCode_Horizon_È("east");
        Ç = PropertyBool.HorizonCode_Horizon_È("south");
        È = PropertyBool.HorizonCode_Horizon_È("west");
        áŠ = PropertyInteger.HorizonCode_Horizon_È("upper", 0, 2);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final int var4 = pos.HorizonCode_Horizon_È();
        final int var5 = pos.Â();
        final int var6 = pos.Ý();
        if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && !Blocks.Ô.Âµá€(worldIn, pos.Âµá€())) {
            final boolean var7 = (var4 + var5 + var6 & 0x1) == 0x1;
            final boolean var8 = (var4 / 2 + var5 / 2 + var6 / 2 & 0x1) == 0x1;
            int var9 = 0;
            if (this.Âµá€(worldIn, pos.Ø­áŒŠá())) {
                var9 = (var7 ? 1 : 2);
            }
            return state.HorizonCode_Horizon_È(BlockFire.¥à, this.Âµá€(worldIn, pos.Ó())).HorizonCode_Horizon_È(BlockFire.Âµà, this.Âµá€(worldIn, pos.áŒŠÆ())).HorizonCode_Horizon_È(BlockFire.Ç, this.Âµá€(worldIn, pos.à())).HorizonCode_Horizon_È(BlockFire.È, this.Âµá€(worldIn, pos.Ø())).HorizonCode_Horizon_È(BlockFire.áŠ, var9).HorizonCode_Horizon_È(BlockFire.à¢, var8).HorizonCode_Horizon_È(BlockFire.ŠÂµà, var7);
        }
        return this.¥à();
    }
    
    protected BlockFire() {
        super(Material.Å);
        this.ˆáŠ = Maps.newIdentityHashMap();
        this.áŒŠ = Maps.newIdentityHashMap();
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockFire.Õ, 0).HorizonCode_Horizon_È(BlockFire.à¢, false).HorizonCode_Horizon_È(BlockFire.ŠÂµà, false).HorizonCode_Horizon_È(BlockFire.¥à, false).HorizonCode_Horizon_È(BlockFire.Âµà, false).HorizonCode_Horizon_È(BlockFire.Ç, false).HorizonCode_Horizon_È(BlockFire.È, false).HorizonCode_Horizon_È(BlockFire.áŠ, 0));
        this.HorizonCode_Horizon_È(true);
    }
    
    public static void È() {
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.à, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ŒÓ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ÇŽÊ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ŠáˆºÂ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ø­Ñ¢Ï­Ø­áˆº, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ŒÂ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ï­Ï, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ŠØ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ˆÐƒØ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.¥É, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.£ÇªÓ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ÂµÕ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Š, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ø­Ñ¢á€, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ñ¢Ó, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.áˆºÏ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ŠÏ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.£Ô, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ˆ, 5, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.¥Æ, 5, 5);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ø­à, 5, 5);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.µÕ, 30, 60);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Æ, 30, 60);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ï­à, 30, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ñ¢Â, 15, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.áƒ, 60, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, 60, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Âµà, 60, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.Ç, 60, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.á€, 60, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ŠÂµà, 30, 60);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ÇŽà, 15, 100);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ÐƒÉ, 5, 5);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.ÂµÊ, 60, 20);
        Blocks.Ô.HorizonCode_Horizon_È(Blocks.áˆºÂ, 60, 20);
    }
    
    public void HorizonCode_Horizon_È(final Block p_180686_1_, final int p_180686_2_, final int p_180686_3_) {
        this.ˆáŠ.put(p_180686_1_, p_180686_2_);
        this.áŒŠ.put(p_180686_1_, p_180686_3_);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 30;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, IBlockState state, final Random rand) {
        if (worldIn.Çªà¢().Â("doFireTick")) {
            if (!this.Ø­áŒŠá(worldIn, pos)) {
                worldIn.Ø(pos);
            }
            final Block var5 = worldIn.Â(pos.Âµá€()).Ý();
            boolean var6 = var5 == Blocks.áŒŠÔ;
            if (worldIn.£à instanceof WorldProviderEnd && var5 == Blocks.áŒŠÆ) {
                var6 = true;
            }
            if (!var6 && worldIn.ˆá() && this.áŒŠÆ(worldIn, pos)) {
                worldIn.Ø(pos);
            }
            else {
                final int var7 = (int)state.HorizonCode_Horizon_È(BlockFire.Õ);
                if (var7 < 15) {
                    state = state.HorizonCode_Horizon_È(BlockFire.Õ, var7 + rand.nextInt(3) / 2);
                    worldIn.HorizonCode_Horizon_È(pos, state, 4);
                }
                worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn) + rand.nextInt(10));
                if (!var6) {
                    if (!this.áˆºÑ¢Õ(worldIn, pos)) {
                        if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) || var7 > 3) {
                            worldIn.Ø(pos);
                        }
                        return;
                    }
                    if (!this.Âµá€((IBlockAccess)worldIn, pos.Âµá€()) && var7 == 15 && rand.nextInt(4) == 0) {
                        worldIn.Ø(pos);
                        return;
                    }
                }
                final boolean var8 = worldIn.Çªà¢(pos);
                byte var9 = 0;
                if (var8) {
                    var9 = -50;
                }
                this.HorizonCode_Horizon_È(worldIn, pos.áŒŠÆ(), 300 + var9, rand, var7);
                this.HorizonCode_Horizon_È(worldIn, pos.Ø(), 300 + var9, rand, var7);
                this.HorizonCode_Horizon_È(worldIn, pos.Âµá€(), 250 + var9, rand, var7);
                this.HorizonCode_Horizon_È(worldIn, pos.Ø­áŒŠá(), 250 + var9, rand, var7);
                this.HorizonCode_Horizon_È(worldIn, pos.Ó(), 300 + var9, rand, var7);
                this.HorizonCode_Horizon_È(worldIn, pos.à(), 300 + var9, rand, var7);
                for (int var10 = -1; var10 <= 1; ++var10) {
                    for (int var11 = -1; var11 <= 1; ++var11) {
                        for (int var12 = -1; var12 <= 4; ++var12) {
                            if (var10 != 0 || var12 != 0 || var11 != 0) {
                                int var13 = 100;
                                if (var12 > 1) {
                                    var13 += (var12 - 1) * 100;
                                }
                                final BlockPos var14 = pos.Â(var10, var12, var11);
                                final int var15 = this.ÂµÈ(worldIn, var14);
                                if (var15 > 0) {
                                    int var16 = (var15 + 40 + worldIn.ŠÂµà().HorizonCode_Horizon_È() * 7) / (var7 + 30);
                                    if (var8) {
                                        var16 /= 2;
                                    }
                                    if (var16 > 0 && rand.nextInt(var13) <= var16 && (!worldIn.ˆá() || !this.áŒŠÆ(worldIn, var14))) {
                                        int var17 = var7 + rand.nextInt(5) / 4;
                                        if (var17 > 15) {
                                            var17 = 15;
                                        }
                                        worldIn.HorizonCode_Horizon_È(var14, state.HorizonCode_Horizon_È(BlockFire.Õ, var17), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    protected boolean áŒŠÆ(final World worldIn, final BlockPos p_176537_2_) {
        return worldIn.ŒÏ(p_176537_2_) || worldIn.ŒÏ(p_176537_2_.Ø()) || worldIn.ŒÏ(p_176537_2_.áŒŠÆ()) || worldIn.ŒÏ(p_176537_2_.Ó()) || worldIn.ŒÏ(p_176537_2_.à());
    }
    
    @Override
    public boolean á€() {
        return false;
    }
    
    private int Ý(final Block p_176532_1_) {
        final Integer var2 = this.áŒŠ.get(p_176532_1_);
        return (var2 == null) ? 0 : var2;
    }
    
    private int Ø­áŒŠá(final Block p_176534_1_) {
        final Integer var2 = this.ˆáŠ.get(p_176534_1_);
        return (var2 == null) ? 0 : var2;
    }
    
    private void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176536_2_, final int p_176536_3_, final Random p_176536_4_, final int p_176536_5_) {
        final int var6 = this.Ý(worldIn.Â(p_176536_2_).Ý());
        if (p_176536_4_.nextInt(p_176536_3_) < var6) {
            final IBlockState var7 = worldIn.Â(p_176536_2_);
            if (p_176536_4_.nextInt(p_176536_5_ + 10) < 5 && !worldIn.ŒÏ(p_176536_2_)) {
                int var8 = p_176536_5_ + p_176536_4_.nextInt(5) / 4;
                if (var8 > 15) {
                    var8 = 15;
                }
                worldIn.HorizonCode_Horizon_È(p_176536_2_, this.¥à().HorizonCode_Horizon_È(BlockFire.Õ, var8), 3);
            }
            else {
                worldIn.Ø(p_176536_2_);
            }
            if (var7.Ý() == Blocks.Ñ¢Â) {
                Blocks.Ñ¢Â.Â(worldIn, p_176536_2_, var7.HorizonCode_Horizon_È(BlockTNT.Õ, true));
            }
        }
    }
    
    private boolean áˆºÑ¢Õ(final World worldIn, final BlockPos p_176533_2_) {
        for (final EnumFacing var6 : EnumFacing.values()) {
            if (this.Âµá€((IBlockAccess)worldIn, p_176533_2_.HorizonCode_Horizon_È(var6))) {
                return true;
            }
        }
        return false;
    }
    
    private int ÂµÈ(final World worldIn, final BlockPos p_176538_2_) {
        if (!worldIn.Ø­áŒŠá(p_176538_2_)) {
            return 0;
        }
        int var3 = 0;
        for (final EnumFacing var7 : EnumFacing.values()) {
            var3 = Math.max(this.Ø­áŒŠá(worldIn.Â(p_176538_2_.HorizonCode_Horizon_È(var7)).Ý()), var3);
        }
        return var3;
    }
    
    @Override
    public boolean £à() {
        return false;
    }
    
    public boolean Âµá€(final IBlockAccess p_176535_1_, final BlockPos p_176535_2_) {
        return this.Ø­áŒŠá(p_176535_1_.Â(p_176535_2_).Ý()) > 0;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) || this.áˆºÑ¢Õ(worldIn, pos);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && !this.áˆºÑ¢Õ(worldIn, pos)) {
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (worldIn.£à.µà() > 0 || !Blocks.µÐƒáƒ.áŒŠÆ(worldIn, pos)) {
            if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && !this.áˆºÑ¢Õ(worldIn, pos)) {
                worldIn.Ø(pos);
            }
            else {
                worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn) + worldIn.Å.nextInt(10));
            }
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (rand.nextInt(24) == 0) {
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, "fire.fire", 1.0f + rand.nextFloat(), rand.nextFloat() * 0.7f + 0.3f, false);
        }
        if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && !Blocks.Ô.Âµá€((IBlockAccess)worldIn, pos.Âµá€())) {
            if (Blocks.Ô.Âµá€((IBlockAccess)worldIn, pos.Ø())) {
                for (int var5 = 0; var5 < 2; ++var5) {
                    final double var6 = pos.HorizonCode_Horizon_È() + rand.nextDouble() * 0.10000000149011612;
                    final double var7 = pos.Â() + rand.nextDouble();
                    final double var8 = pos.Ý() + rand.nextDouble();
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
                }
            }
            if (Blocks.Ô.Âµá€((IBlockAccess)worldIn, pos.áŒŠÆ())) {
                for (int var5 = 0; var5 < 2; ++var5) {
                    final double var6 = pos.HorizonCode_Horizon_È() + 1 - rand.nextDouble() * 0.10000000149011612;
                    final double var7 = pos.Â() + rand.nextDouble();
                    final double var8 = pos.Ý() + rand.nextDouble();
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
                }
            }
            if (Blocks.Ô.Âµá€((IBlockAccess)worldIn, pos.Ó())) {
                for (int var5 = 0; var5 < 2; ++var5) {
                    final double var6 = pos.HorizonCode_Horizon_È() + rand.nextDouble();
                    final double var7 = pos.Â() + rand.nextDouble();
                    final double var8 = pos.Ý() + rand.nextDouble() * 0.10000000149011612;
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
                }
            }
            if (Blocks.Ô.Âµá€((IBlockAccess)worldIn, pos.à())) {
                for (int var5 = 0; var5 < 2; ++var5) {
                    final double var6 = pos.HorizonCode_Horizon_È() + rand.nextDouble();
                    final double var7 = pos.Â() + rand.nextDouble();
                    final double var8 = pos.Ý() + 1 - rand.nextDouble() * 0.10000000149011612;
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
                }
            }
            if (Blocks.Ô.Âµá€((IBlockAccess)worldIn, pos.Ø­áŒŠá())) {
                for (int var5 = 0; var5 < 2; ++var5) {
                    final double var6 = pos.HorizonCode_Horizon_È() + rand.nextDouble();
                    final double var7 = pos.Â() + 1 - rand.nextDouble() * 0.10000000149011612;
                    final double var8 = pos.Ý() + rand.nextDouble();
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
        else {
            for (int var5 = 0; var5 < 3; ++var5) {
                final double var6 = pos.HorizonCode_Horizon_È() + rand.nextDouble();
                final double var7 = pos.Â() + rand.nextDouble() * 0.5 + 0.5;
                final double var8 = pos.Ý() + rand.nextDouble();
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return MapColor.Ó;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockFire.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockFire.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockFire.Õ, BlockFire.¥à, BlockFire.Âµà, BlockFire.Ç, BlockFire.È, BlockFire.áŠ, BlockFire.à¢, BlockFire.ŠÂµà });
    }
}
