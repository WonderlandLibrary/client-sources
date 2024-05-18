package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockDoublePlant extends BlockBush implements IGrowable
{
    public static final PropertyEnum Õ;
    public static final PropertyEnum à¢;
    private static final String ŠÂµà = "CL_00000231";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", Â.class);
        à¢ = PropertyEnum.HorizonCode_Horizon_È("half", HorizonCode_Horizon_È.class);
    }
    
    public BlockDoublePlant() {
        super(Material.á);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockDoublePlant.Õ, Â.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockDoublePlant.à¢, HorizonCode_Horizon_È.Â));
        this.Ý(0.0f);
        this.HorizonCode_Horizon_È(BlockDoublePlant.Ó);
        this.Â("doublePlant");
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public Â Âµá€(final IBlockAccess p_176490_1_, final BlockPos p_176490_2_) {
        IBlockState var3 = p_176490_1_.Â(p_176490_2_);
        if (var3.Ý() == this) {
            var3 = this.HorizonCode_Horizon_È(var3, p_176490_1_, p_176490_2_);
            return (Â)var3.HorizonCode_Horizon_È(BlockDoublePlant.Õ);
        }
        return Â.Ø­áŒŠá;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && worldIn.Ø­áŒŠá(pos.Ø­áŒŠá());
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.Â(pos);
        if (var3.Ý() != this) {
            return true;
        }
        final Â var4 = (Â)this.HorizonCode_Horizon_È(var3, worldIn, pos).HorizonCode_Horizon_È(BlockDoublePlant.Õ);
        return var4 == Â.Ø­áŒŠá || var4 == Â.Ý;
    }
    
    @Override
    protected void Âµá€(final World worldIn, final BlockPos p_176475_2_, final IBlockState p_176475_3_) {
        if (!this.Ó(worldIn, p_176475_2_, p_176475_3_)) {
            final boolean var4 = p_176475_3_.HorizonCode_Horizon_È(BlockDoublePlant.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            final BlockPos var5 = var4 ? p_176475_2_ : p_176475_2_.Ø­áŒŠá();
            final BlockPos var6 = var4 ? p_176475_2_.Âµá€() : p_176475_2_;
            final Object var7 = var4 ? this : worldIn.Â(var5).Ý();
            final Object var8 = var4 ? worldIn.Â(var6).Ý() : this;
            if (var7 == this) {
                worldIn.HorizonCode_Horizon_È(var5, Blocks.Â.¥à(), 3);
            }
            if (var8 == this) {
                worldIn.HorizonCode_Horizon_È(var6, Blocks.Â.¥à(), 3);
                if (!var4) {
                    this.HorizonCode_Horizon_È(worldIn, var6, p_176475_3_, 0);
                }
            }
        }
    }
    
    @Override
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        if (p_180671_3_.HorizonCode_Horizon_È(BlockDoublePlant.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            return worldIn.Â(p_180671_2_.Âµá€()).Ý() == this;
        }
        final IBlockState var4 = worldIn.Â(p_180671_2_.Ø­áŒŠá());
        return var4.Ý() == this && super.Ó(worldIn, p_180671_2_, var4);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        if (state.HorizonCode_Horizon_È(BlockDoublePlant.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            return null;
        }
        final Â var4 = (Â)state.HorizonCode_Horizon_È(BlockDoublePlant.Õ);
        return (var4 == Â.Ø­áŒŠá) ? null : ((var4 == Â.Ý) ? ((rand.nextInt(8) == 0) ? Items.¥à : null) : Item_1028566121.HorizonCode_Horizon_È(this));
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return (state.HorizonCode_Horizon_È(BlockDoublePlant.à¢) != HorizonCode_Horizon_È.HorizonCode_Horizon_È && state.HorizonCode_Horizon_È(BlockDoublePlant.Õ) != Â.Ý) ? ((Â)state.HorizonCode_Horizon_È(BlockDoublePlant.Õ)).Â() : 0;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        final Â var4 = this.Âµá€(worldIn, pos);
        return (var4 != Â.Ý && var4 != Â.Ø­áŒŠá) ? 16777215 : BiomeColorHelper.HorizonCode_Horizon_È(worldIn, pos);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176491_2_, final Â p_176491_3_, final int p_176491_4_) {
        worldIn.HorizonCode_Horizon_È(p_176491_2_, this.¥à().HorizonCode_Horizon_È(BlockDoublePlant.à¢, HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockDoublePlant.Õ, p_176491_3_), p_176491_4_);
        worldIn.HorizonCode_Horizon_È(p_176491_2_.Ø­áŒŠá(), this.¥à().HorizonCode_Horizon_È(BlockDoublePlant.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È), p_176491_4_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        worldIn.HorizonCode_Horizon_È(pos.Ø­áŒŠá(), this.¥à().HorizonCode_Horizon_È(BlockDoublePlant.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È), 2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (worldIn.ŠÄ || playerIn.áŒŠá() == null || playerIn.áŒŠá().HorizonCode_Horizon_È() != Items.áˆºà || state.HorizonCode_Horizon_È(BlockDoublePlant.à¢) != HorizonCode_Horizon_È.Â || !this.Â(worldIn, pos, state, playerIn)) {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, te);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
        if (state.HorizonCode_Horizon_È(BlockDoublePlant.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            if (worldIn.Â(pos.Âµá€()).Ý() == this) {
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    final IBlockState var5 = worldIn.Â(pos.Âµá€());
                    final Â var6 = (Â)var5.HorizonCode_Horizon_È(BlockDoublePlant.Õ);
                    if (var6 != Â.Ø­áŒŠá && var6 != Â.Ý) {
                        worldIn.Â(pos.Âµá€(), true);
                    }
                    else if (!worldIn.ŠÄ) {
                        if (playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
                            this.Â(worldIn, pos, var5, playerIn);
                            worldIn.Ø(pos.Âµá€());
                        }
                        else {
                            worldIn.Â(pos.Âµá€(), true);
                        }
                    }
                    else {
                        worldIn.Ø(pos.Âµá€());
                    }
                }
                else {
                    worldIn.Ø(pos.Âµá€());
                }
            }
        }
        else if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá && worldIn.Â(pos.Ø­áŒŠá()).Ý() == this) {
            worldIn.HorizonCode_Horizon_È(pos.Ø­áŒŠá(), Blocks.Â.¥à(), 2);
        }
        super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn);
    }
    
    private boolean Â(final World worldIn, final BlockPos p_176489_2_, final IBlockState p_176489_3_, final EntityPlayer p_176489_4_) {
        final Â var5 = (Â)p_176489_3_.HorizonCode_Horizon_È(BlockDoublePlant.Õ);
        if (var5 != Â.Ø­áŒŠá && var5 != Â.Ý) {
            return false;
        }
        p_176489_4_.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
        final int var6 = ((var5 == Â.Ý) ? BlockTallGrass.HorizonCode_Horizon_È.Â : BlockTallGrass.HorizonCode_Horizon_È.Ý).Â();
        Block.HorizonCode_Horizon_È(worldIn, p_176489_2_, new ItemStack(Blocks.áƒ, 2, var6));
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final Â var7 : Â.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        return this.Âµá€((IBlockAccess)worldIn, pos).Â();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        final Â var5 = this.Âµá€((IBlockAccess)worldIn, p_176473_2_);
        return var5 != Â.Ý && var5 != Â.Ø­áŒŠá;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return true;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        Block.HorizonCode_Horizon_È(worldIn, p_176474_3_, new ItemStack(this, 1, this.Âµá€((IBlockAccess)worldIn, p_176474_3_).Â()));
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return ((meta & 0x8) > 0) ? this.¥à().HorizonCode_Horizon_È(BlockDoublePlant.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È) : this.¥à().HorizonCode_Horizon_È(BlockDoublePlant.à¢, HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockDoublePlant.Õ, Â.HorizonCode_Horizon_È(meta & 0x7));
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (state.HorizonCode_Horizon_È(BlockDoublePlant.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final IBlockState var4 = worldIn.Â(pos.Âµá€());
            if (var4.Ý() == this) {
                state = state.HorizonCode_Horizon_È(BlockDoublePlant.Õ, var4.HorizonCode_Horizon_È(BlockDoublePlant.Õ));
            }
        }
        return state;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (state.HorizonCode_Horizon_È(BlockDoublePlant.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? 8 : ((Â)state.HorizonCode_Horizon_È(BlockDoublePlant.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockDoublePlant.à¢, BlockDoublePlant.Õ });
    }
    
    @Override
    public Block.HorizonCode_Horizon_È Âµà() {
        return Block.HorizonCode_Horizon_È.Â;
    }
    
    enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("UPPER", 0, "UPPER", 0), 
        Â("LOWER", 1, "LOWER", 1);
        
        private static final HorizonCode_Horizon_È[] Ý;
        private static final String Ø­áŒŠá = "CL_00002122";
        
        static {
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45724_1_, final int p_i45724_2_) {
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
        HorizonCode_Horizon_È("SUNFLOWER", 0, "SUNFLOWER", 0, 0, "sunflower"), 
        Â("SYRINGA", 1, "SYRINGA", 1, 1, "syringa"), 
        Ý("GRASS", 2, "GRASS", 2, 2, "double_grass", "grass"), 
        Ø­áŒŠá("FERN", 3, "FERN", 3, 3, "double_fern", "fern"), 
        Âµá€("ROSE", 4, "ROSE", 4, 4, "double_rose", "rose"), 
        Ó("PAEONIA", 5, "PAEONIA", 5, 5, "paeonia");
        
        private static final Â[] à;
        private final int Ø;
        private final String áŒŠÆ;
        private final String áˆºÑ¢Õ;
        private static final Â[] ÂµÈ;
        private static final String á = "CL_00002121";
        
        static {
            ˆÏ­ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
            à = new Â[values().length];
            ÂµÈ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
            for (final Â var4 : values()) {
                Â.à[var4.Â()] = var4;
            }
        }
        
        private Â(final String s, final int n, final String p_i45722_1_, final int p_i45722_2_, final int p_i45722_3_, final String p_i45722_4_) {
            this(s, n, p_i45722_1_, p_i45722_2_, p_i45722_3_, p_i45722_4_, p_i45722_4_);
        }
        
        private Â(final String s, final int n, final String p_i45723_1_, final int p_i45723_2_, final int p_i45723_3_, final String p_i45723_4_, final String p_i45723_5_) {
            this.Ø = p_i45723_3_;
            this.áŒŠÆ = p_i45723_4_;
            this.áˆºÑ¢Õ = p_i45723_5_;
        }
        
        public int Â() {
            return this.Ø;
        }
        
        @Override
        public String toString() {
            return this.áŒŠÆ;
        }
        
        public static Â HorizonCode_Horizon_È(int p_176938_0_) {
            if (p_176938_0_ < 0 || p_176938_0_ >= Â.à.length) {
                p_176938_0_ = 0;
            }
            return Â.à[p_176938_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.áŒŠÆ;
        }
        
        public String Ý() {
            return this.áˆºÑ¢Õ;
        }
    }
}
