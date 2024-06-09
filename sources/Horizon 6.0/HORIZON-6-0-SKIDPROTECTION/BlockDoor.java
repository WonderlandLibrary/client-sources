package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;

public class BlockDoor extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    public static final PropertyEnum ŠÂµà;
    public static final PropertyBool ¥à;
    public static final PropertyEnum Âµà;
    private static final String Ç = "CL_00000230";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyBool.HorizonCode_Horizon_È("open");
        ŠÂµà = PropertyEnum.HorizonCode_Horizon_È("hinge", Â.class);
        ¥à = PropertyBool.HorizonCode_Horizon_È("powered");
        Âµà = PropertyEnum.HorizonCode_Horizon_È("half", HorizonCode_Horizon_È.class);
    }
    
    protected BlockDoor(final Material p_i45402_1_) {
        super(p_i45402_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockDoor.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockDoor.à¢, false).HorizonCode_Horizon_È(BlockDoor.ŠÂµà, Â.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockDoor.¥à, false).HorizonCode_Horizon_È(BlockDoor.Âµà, HorizonCode_Horizon_È.Â));
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return à(Âµá€(blockAccess, pos));
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.Ý(worldIn, pos);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, state);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.áˆºÑ¢Õ(Âµá€(access, pos));
    }
    
    private void áˆºÑ¢Õ(final int p_150011_1_) {
        final float var2 = 0.1875f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final EnumFacing var3 = Ó(p_150011_1_);
        final boolean var4 = à(p_150011_1_);
        final boolean var5 = áŒŠÆ(p_150011_1_);
        if (var4) {
            if (var3 == EnumFacing.Ó) {
                if (!var5) {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
                }
                else {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
                }
            }
            else if (var3 == EnumFacing.Ø­áŒŠá) {
                if (!var5) {
                    this.HorizonCode_Horizon_È(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
                }
            }
            else if (var3 == EnumFacing.Âµá€) {
                if (!var5) {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
                }
            }
            else if (var3 == EnumFacing.Ý) {
                if (!var5) {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
                }
                else {
                    this.HorizonCode_Horizon_È(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        }
        else if (var3 == EnumFacing.Ó) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
        }
        else if (var3 == EnumFacing.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
        }
        else if (var3 == EnumFacing.Âµá€) {
            this.HorizonCode_Horizon_È(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (var3 == EnumFacing.Ý) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (this.É == Material.Ó) {
            return true;
        }
        final BlockPos var9 = (state.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.Â) ? pos : pos.Âµá€();
        final IBlockState var10 = pos.equals(var9) ? state : worldIn.Â(var9);
        if (var10.Ý() != this) {
            return false;
        }
        state = var10.Â(BlockDoor.à¢);
        worldIn.HorizonCode_Horizon_È(var9, state, 2);
        worldIn.Â(var9, pos);
        worldIn.HorizonCode_Horizon_È(playerIn, ((boolean)state.HorizonCode_Horizon_È(BlockDoor.à¢)) ? 1003 : 1006, pos, 0);
        return true;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176512_2_, final boolean p_176512_3_) {
        final IBlockState var4 = worldIn.Â(p_176512_2_);
        if (var4.Ý() == this) {
            final BlockPos var5 = (var4.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.Â) ? p_176512_2_ : p_176512_2_.Âµá€();
            final IBlockState var6 = (p_176512_2_ == var5) ? var4 : worldIn.Â(var5);
            if (var6.Ý() == this && (boolean)var6.HorizonCode_Horizon_È(BlockDoor.à¢) != p_176512_3_) {
                worldIn.HorizonCode_Horizon_È(var5, var6.HorizonCode_Horizon_È(BlockDoor.à¢, p_176512_3_), 2);
                worldIn.Â(var5, p_176512_2_);
                worldIn.HorizonCode_Horizon_È(null, p_176512_3_ ? 1003 : 1006, p_176512_2_, 0);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (state.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final BlockPos var5 = pos.Âµá€();
            final IBlockState var6 = worldIn.Â(var5);
            if (var6.Ý() != this) {
                worldIn.Ø(pos);
            }
            else if (neighborBlock != this) {
                this.HorizonCode_Horizon_È(worldIn, var5, var6, neighborBlock);
            }
        }
        else {
            boolean var7 = false;
            final BlockPos var8 = pos.Ø­áŒŠá();
            final IBlockState var9 = worldIn.Â(var8);
            if (var9.Ý() != this) {
                worldIn.Ø(pos);
                var7 = true;
            }
            if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€())) {
                worldIn.Ø(pos);
                var7 = true;
                if (var9.Ý() == this) {
                    worldIn.Ø(var8);
                }
            }
            if (var7) {
                if (!worldIn.ŠÄ) {
                    this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
                }
            }
            else {
                final boolean var10 = worldIn.áŒŠà(pos) || worldIn.áŒŠà(var8);
                if ((var10 || neighborBlock.áŒŠà()) && neighborBlock != this && var10 != (boolean)var9.HorizonCode_Horizon_È(BlockDoor.¥à)) {
                    worldIn.HorizonCode_Horizon_È(var8, var9.HorizonCode_Horizon_È(BlockDoor.¥à, var10), 2);
                    if (var10 != (boolean)state.HorizonCode_Horizon_È(BlockDoor.à¢)) {
                        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockDoor.à¢, var10), 2);
                        worldIn.Â(pos, pos);
                        worldIn.HorizonCode_Horizon_È(null, var10 ? 1003 : 1006, pos, 0);
                    }
                }
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return (state.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? null : this.È();
    }
    
    @Override
    public MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Vec3 start, final Vec3 end) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, start, end);
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return pos.Â() < 255 && (World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && super.Ø­áŒŠá(worldIn, pos) && super.Ø­áŒŠá(worldIn, pos.Ø­áŒŠá()));
    }
    
    @Override
    public int ˆá() {
        return 1;
    }
    
    public static int Âµá€(final IBlockAccess p_176515_0_, final BlockPos p_176515_1_) {
        final IBlockState var2 = p_176515_0_.Â(p_176515_1_);
        final int var3 = var2.Ý().Ý(var2);
        final boolean var4 = Ø(var3);
        final IBlockState var5 = p_176515_0_.Â(p_176515_1_.Âµá€());
        final int var6 = var5.Ý().Ý(var5);
        final int var7 = var4 ? var6 : var3;
        final IBlockState var8 = p_176515_0_.Â(p_176515_1_.Ø­áŒŠá());
        final int var9 = var8.Ý().Ý(var8);
        final int var10 = var4 ? var3 : var9;
        final boolean var11 = (var10 & 0x1) != 0x0;
        final boolean var12 = (var10 & 0x2) != 0x0;
        return Âµá€(var7) | (var4 ? 8 : 0) | (var11 ? 16 : 0) | (var12 ? 32 : 0);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return this.È();
    }
    
    private Item_1028566121 È() {
        return (this == Blocks.ŠÓ) ? Items.ŠÓ : ((this == Blocks.Œà) ? Items.Ðƒá : ((this == Blocks.Ðƒá) ? Items.ˆÏ : ((this == Blocks.ˆÏ) ? Items.áˆºÇŽØ : ((this == Blocks.áˆºÇŽØ) ? Items.ÇªÂµÕ : ((this == Blocks.ÇªÂµÕ) ? Items.áŒŠÏ : Items.Œà)))));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
        final BlockPos var5 = pos.Âµá€();
        if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá && state.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.HorizonCode_Horizon_È && worldIn.Â(var5).Ý() == this) {
            worldIn.Ø(var5);
        }
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (state.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.Â) {
            final IBlockState var4 = worldIn.Â(pos.Ø­áŒŠá());
            if (var4.Ý() == this) {
                state = state.HorizonCode_Horizon_È(BlockDoor.ŠÂµà, var4.HorizonCode_Horizon_È(BlockDoor.ŠÂµà)).HorizonCode_Horizon_È(BlockDoor.¥à, var4.HorizonCode_Horizon_È(BlockDoor.¥à));
            }
        }
        else {
            final IBlockState var4 = worldIn.Â(pos.Âµá€());
            if (var4.Ý() == this) {
                state = state.HorizonCode_Horizon_È(BlockDoor.Õ, var4.HorizonCode_Horizon_È(BlockDoor.Õ)).HorizonCode_Horizon_È(BlockDoor.à¢, var4.HorizonCode_Horizon_È(BlockDoor.à¢));
            }
        }
        return state;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return ((meta & 0x8) > 0) ? this.¥à().HorizonCode_Horizon_È(BlockDoor.Âµà, HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockDoor.ŠÂµà, ((meta & 0x1) > 0) ? Â.Â : Â.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockDoor.¥à, (meta & 0x2) > 0) : this.¥à().HorizonCode_Horizon_È(BlockDoor.Âµà, HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockDoor.Õ, EnumFacing.Â(meta & 0x3).à()).HorizonCode_Horizon_È(BlockDoor.à¢, (meta & 0x4) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3;
        if (state.HorizonCode_Horizon_È(BlockDoor.Âµà) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 = (var2 | 0x8);
            if (state.HorizonCode_Horizon_È(BlockDoor.ŠÂµà) == Â.Â) {
                var3 |= 0x1;
            }
            if (state.HorizonCode_Horizon_È(BlockDoor.¥à)) {
                var3 |= 0x2;
            }
        }
        else {
            var3 = (var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockDoor.Õ)).Ó().Ý());
            if (state.HorizonCode_Horizon_È(BlockDoor.à¢)) {
                var3 |= 0x4;
            }
        }
        return var3;
    }
    
    protected static int Âµá€(final int p_176510_0_) {
        return p_176510_0_ & 0x7;
    }
    
    public static boolean Ó(final IBlockAccess p_176514_0_, final BlockPos p_176514_1_) {
        return à(Âµá€(p_176514_0_, p_176514_1_));
    }
    
    public static EnumFacing à(final IBlockAccess p_176517_0_, final BlockPos p_176517_1_) {
        return Ó(Âµá€(p_176517_0_, p_176517_1_));
    }
    
    public static EnumFacing Ó(final int p_176511_0_) {
        return EnumFacing.Â(p_176511_0_ & 0x3).à();
    }
    
    protected static boolean à(final int p_176516_0_) {
        return (p_176516_0_ & 0x4) != 0x0;
    }
    
    protected static boolean Ø(final int p_176518_0_) {
        return (p_176518_0_ & 0x8) != 0x0;
    }
    
    protected static boolean áŒŠÆ(final int p_176513_0_) {
        return (p_176513_0_ & 0x10) != 0x0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockDoor.Âµà, BlockDoor.Õ, BlockDoor.à¢, BlockDoor.ŠÂµà, BlockDoor.¥à });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("UPPER", 0, "UPPER", 0), 
        Â("LOWER", 1, "LOWER", 1);
        
        private static final HorizonCode_Horizon_È[] Ý;
        private static final String Ø­áŒŠá = "CL_00002124";
        
        static {
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45726_1_, final int p_i45726_2_) {
        }
        
        @Override
        public String toString() {
            return this.HorizonCode_Horizon_È();
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return (this == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? "upper" : "lower";
        }
    }
    
    public enum Â implements IStringSerializable
    {
        HorizonCode_Horizon_È("LEFT", 0, "LEFT", 0), 
        Â("RIGHT", 1, "RIGHT", 1);
        
        private static final Â[] Ý;
        private static final String Ø­áŒŠá = "CL_00002123";
        
        static {
            Âµá€ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
            Ý = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
        }
        
        private Â(final String s, final int n, final String p_i45725_1_, final int p_i45725_2_) {
        }
        
        @Override
        public String toString() {
            return this.HorizonCode_Horizon_È();
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return (this == Â.HorizonCode_Horizon_È) ? "left" : "right";
        }
    }
}
