package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.base.Predicate;
import java.util.Random;

public class BlockChest extends BlockContainer
{
    public static final PropertyDirection Õ;
    private final Random ŠÂµà;
    public final int à¢;
    private static final String ¥à = "CL_00000214";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
    }
    
    protected BlockChest(final int type) {
        super(Material.Ø­áŒŠá);
        this.ŠÂµà = new Random();
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockChest.Õ, EnumFacing.Ý));
        this.à¢ = type;
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
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
    public int ÂµÈ() {
        return 2;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        if (access.Â(pos.Ó()).Ý() == this) {
            this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (access.Â(pos.à()).Ý() == this) {
            this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
        }
        else if (access.Â(pos.Ø()).Ý() == this) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (access.Â(pos.áŒŠÆ()).Ý() == this) {
            this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
        }
        else {
            this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state);
        for (final EnumFacing var5 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final BlockPos var6 = pos.HorizonCode_Horizon_È(var5);
            final IBlockState var7 = worldIn.Â(var6);
            if (var7.Ý() == this) {
                this.Âµá€(worldIn, var6, var7);
            }
        }
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockChest.Õ, placer.ˆà¢());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final EnumFacing var6 = EnumFacing.Â(MathHelper.Ý(placer.É * 4.0f / 360.0f + 0.5) & 0x3).Âµá€();
        state = state.HorizonCode_Horizon_È(BlockChest.Õ, var6);
        final BlockPos var7 = pos.Ó();
        final BlockPos var8 = pos.à();
        final BlockPos var9 = pos.Ø();
        final BlockPos var10 = pos.áŒŠÆ();
        final boolean var11 = this == worldIn.Â(var7).Ý();
        final boolean var12 = this == worldIn.Â(var8).Ý();
        final boolean var13 = this == worldIn.Â(var9).Ý();
        final boolean var14 = this == worldIn.Â(var10).Ý();
        if (!var11 && !var12 && !var13 && !var14) {
            worldIn.HorizonCode_Horizon_È(pos, state, 3);
        }
        else if (var6.á() == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È && (var11 || var12)) {
            if (var11) {
                worldIn.HorizonCode_Horizon_È(var7, state, 3);
            }
            else {
                worldIn.HorizonCode_Horizon_È(var8, state, 3);
            }
            worldIn.HorizonCode_Horizon_È(pos, state, 3);
        }
        else if (var6.á() == EnumFacing.HorizonCode_Horizon_È.Ý && (var13 || var14)) {
            if (var13) {
                worldIn.HorizonCode_Horizon_È(var9, state, 3);
            }
            else {
                worldIn.HorizonCode_Horizon_È(var10, state, 3);
            }
            worldIn.HorizonCode_Horizon_È(pos, state, 3);
        }
        if (stack.¥Æ()) {
            final TileEntity var15 = worldIn.HorizonCode_Horizon_È(pos);
            if (var15 instanceof TileEntityChest) {
                ((TileEntityChest)var15).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
    
    public IBlockState Âµá€(final World worldIn, final BlockPos p_176455_2_, IBlockState p_176455_3_) {
        if (worldIn.ŠÄ) {
            return p_176455_3_;
        }
        final IBlockState var4 = worldIn.Â(p_176455_2_.Ó());
        final IBlockState var5 = worldIn.Â(p_176455_2_.à());
        final IBlockState var6 = worldIn.Â(p_176455_2_.Ø());
        final IBlockState var7 = worldIn.Â(p_176455_2_.áŒŠÆ());
        EnumFacing var8 = (EnumFacing)p_176455_3_.HorizonCode_Horizon_È(BlockChest.Õ);
        final Block var9 = var4.Ý();
        final Block var10 = var5.Ý();
        final Block var11 = var6.Ý();
        final Block var12 = var7.Ý();
        if (var9 != this && var10 != this) {
            final boolean var13 = var9.HorizonCode_Horizon_È();
            final boolean var14 = var10.HorizonCode_Horizon_È();
            if (var11 == this || var12 == this) {
                final BlockPos var15 = (var11 == this) ? p_176455_2_.Ø() : p_176455_2_.áŒŠÆ();
                final IBlockState var16 = worldIn.Â(var15.Ó());
                final IBlockState var17 = worldIn.Â(var15.à());
                var8 = EnumFacing.Ø­áŒŠá;
                EnumFacing var18;
                if (var11 == this) {
                    var18 = (EnumFacing)var6.HorizonCode_Horizon_È(BlockChest.Õ);
                }
                else {
                    var18 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockChest.Õ);
                }
                if (var18 == EnumFacing.Ý) {
                    var8 = EnumFacing.Ý;
                }
                final Block var19 = var16.Ý();
                final Block var20 = var17.Ý();
                if ((var13 || var19.HorizonCode_Horizon_È()) && !var14 && !var20.HorizonCode_Horizon_È()) {
                    var8 = EnumFacing.Ø­áŒŠá;
                }
                if ((var14 || var20.HorizonCode_Horizon_È()) && !var13 && !var19.HorizonCode_Horizon_È()) {
                    var8 = EnumFacing.Ý;
                }
            }
        }
        else {
            final BlockPos var21 = (var9 == this) ? p_176455_2_.Ó() : p_176455_2_.à();
            final IBlockState var22 = worldIn.Â(var21.Ø());
            final IBlockState var23 = worldIn.Â(var21.áŒŠÆ());
            var8 = EnumFacing.Ó;
            EnumFacing var24;
            if (var9 == this) {
                var24 = (EnumFacing)var4.HorizonCode_Horizon_È(BlockChest.Õ);
            }
            else {
                var24 = (EnumFacing)var5.HorizonCode_Horizon_È(BlockChest.Õ);
            }
            if (var24 == EnumFacing.Âµá€) {
                var8 = EnumFacing.Âµá€;
            }
            final Block var25 = var22.Ý();
            final Block var26 = var23.Ý();
            if ((var11.HorizonCode_Horizon_È() || var25.HorizonCode_Horizon_È()) && !var12.HorizonCode_Horizon_È() && !var26.HorizonCode_Horizon_È()) {
                var8 = EnumFacing.Ó;
            }
            if ((var12.HorizonCode_Horizon_È() || var26.HorizonCode_Horizon_È()) && !var11.HorizonCode_Horizon_È() && !var25.HorizonCode_Horizon_È()) {
                var8 = EnumFacing.Âµá€;
            }
        }
        p_176455_3_ = p_176455_3_.HorizonCode_Horizon_È(BlockChest.Õ, var8);
        worldIn.HorizonCode_Horizon_È(p_176455_2_, p_176455_3_, 3);
        return p_176455_3_;
    }
    
    public IBlockState Ó(final World worldIn, final BlockPos p_176458_2_, final IBlockState p_176458_3_) {
        EnumFacing var4 = null;
        for (final EnumFacing var6 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final IBlockState var7 = worldIn.Â(p_176458_2_.HorizonCode_Horizon_È(var6));
            if (var7.Ý() == this) {
                return p_176458_3_;
            }
            if (!var7.Ý().HorizonCode_Horizon_È()) {
                continue;
            }
            if (var4 != null) {
                var4 = null;
                break;
            }
            var4 = var6;
        }
        if (var4 != null) {
            return p_176458_3_.HorizonCode_Horizon_È(BlockChest.Õ, var4.Âµá€());
        }
        EnumFacing var8 = (EnumFacing)p_176458_3_.HorizonCode_Horizon_È(BlockChest.Õ);
        if (worldIn.Â(p_176458_2_.HorizonCode_Horizon_È(var8)).Ý().HorizonCode_Horizon_È()) {
            var8 = var8.Âµá€();
        }
        if (worldIn.Â(p_176458_2_.HorizonCode_Horizon_È(var8)).Ý().HorizonCode_Horizon_È()) {
            var8 = var8.Ó();
        }
        if (worldIn.Â(p_176458_2_.HorizonCode_Horizon_È(var8)).Ý().HorizonCode_Horizon_È()) {
            var8 = var8.Âµá€();
        }
        return p_176458_3_.HorizonCode_Horizon_È(BlockChest.Õ, var8);
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        int var3 = 0;
        final BlockPos var4 = pos.Ø();
        final BlockPos var5 = pos.áŒŠÆ();
        final BlockPos var6 = pos.Ó();
        final BlockPos var7 = pos.à();
        if (worldIn.Â(var4).Ý() == this) {
            if (this.áˆºÑ¢Õ(worldIn, var4)) {
                return false;
            }
            ++var3;
        }
        if (worldIn.Â(var5).Ý() == this) {
            if (this.áˆºÑ¢Õ(worldIn, var5)) {
                return false;
            }
            ++var3;
        }
        if (worldIn.Â(var6).Ý() == this) {
            if (this.áˆºÑ¢Õ(worldIn, var6)) {
                return false;
            }
            ++var3;
        }
        if (worldIn.Â(var7).Ý() == this) {
            if (this.áˆºÑ¢Õ(worldIn, var7)) {
                return false;
            }
            ++var3;
        }
        return var3 <= 1;
    }
    
    private boolean áˆºÑ¢Õ(final World worldIn, final BlockPos p_176454_2_) {
        if (worldIn.Â(p_176454_2_).Ý() != this) {
            return false;
        }
        for (final EnumFacing var4 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (worldIn.Â(p_176454_2_.HorizonCode_Horizon_È(var4)).Ý() == this) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
        final TileEntity var5 = worldIn.HorizonCode_Horizon_È(pos);
        if (var5 instanceof TileEntityChest) {
            var5.ˆà();
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
        if (var4 instanceof IInventory) {
            InventoryHelper.HorizonCode_Horizon_È(worldIn, pos, (IInventory)var4);
            worldIn.Âµá€(pos, this);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final ILockableContainer var9 = this.áŒŠÆ(worldIn, pos);
        if (var9 != null) {
            playerIn.HorizonCode_Horizon_È((IInventory)var9);
        }
        return true;
    }
    
    public ILockableContainer áŒŠÆ(final World worldIn, final BlockPos p_180676_2_) {
        final TileEntity var3 = worldIn.HorizonCode_Horizon_È(p_180676_2_);
        if (!(var3 instanceof TileEntityChest)) {
            return null;
        }
        Object var4 = var3;
        if (this.ÂµÈ(worldIn, p_180676_2_)) {
            return null;
        }
        for (final EnumFacing var6 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final BlockPos var7 = p_180676_2_.HorizonCode_Horizon_È(var6);
            final Block var8 = worldIn.Â(var7).Ý();
            if (var8 == this) {
                if (this.ÂµÈ(worldIn, var7)) {
                    return null;
                }
                final TileEntity var9 = worldIn.HorizonCode_Horizon_È(var7);
                if (!(var9 instanceof TileEntityChest)) {
                    continue;
                }
                if (var6 != EnumFacing.Âµá€ && var6 != EnumFacing.Ý) {
                    var4 = new InventoryLargeChest("container.chestDouble", (ILockableContainer)var4, (ILockableContainer)var9);
                }
                else {
                    var4 = new InventoryLargeChest("container.chestDouble", (ILockableContainer)var9, (ILockableContainer)var4);
                }
            }
        }
        return (ILockableContainer)var4;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityChest();
    }
    
    @Override
    public boolean áŒŠà() {
        return this.à¢ == 1;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        if (!this.áŒŠà()) {
            return 0;
        }
        int var5 = 0;
        final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
        if (var6 instanceof TileEntityChest) {
            var5 = ((TileEntityChest)var6).á;
        }
        return MathHelper.HorizonCode_Horizon_È(var5, 0, 15);
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return (side == EnumFacing.Â) ? this.HorizonCode_Horizon_È(worldIn, pos, state, side) : 0;
    }
    
    private boolean ÂµÈ(final World worldIn, final BlockPos p_176457_2_) {
        return this.á(worldIn, p_176457_2_) || this.ˆÏ­(worldIn, p_176457_2_);
    }
    
    private boolean á(final World worldIn, final BlockPos p_176456_2_) {
        return worldIn.Â(p_176456_2_.Ø­áŒŠá()).Ý().Ø();
    }
    
    private boolean ˆÏ­(final World worldIn, final BlockPos p_176453_2_) {
        for (final Entity var4 : worldIn.HorizonCode_Horizon_È(EntityOcelot.class, new AxisAlignedBB(p_176453_2_.HorizonCode_Horizon_È(), p_176453_2_.Â() + 1, p_176453_2_.Ý(), p_176453_2_.HorizonCode_Horizon_È() + 1, p_176453_2_.Â() + 2, p_176453_2_.Ý() + 1))) {
            final EntityOcelot var5 = (EntityOcelot)var4;
            if (var5.áˆºÕ()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        return Container.Â(this.áŒŠÆ(worldIn, pos));
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(meta);
        if (var2.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var2 = EnumFacing.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockChest.Õ, var2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumFacing)state.HorizonCode_Horizon_È(BlockChest.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockChest.Õ });
    }
}
