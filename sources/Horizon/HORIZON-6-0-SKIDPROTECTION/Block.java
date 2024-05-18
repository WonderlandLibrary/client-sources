package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import java.util.List;

public class Block
{
    private static final ResourceLocation_1975012498 Õ;
    public static final RegistryNamespacedDefaultedByKey HorizonCode_Horizon_È;
    public static final ObjectIntIdentityMap Â;
    private CreativeTabs à¢;
    public static final Â Ý;
    public static final Â Ø­áŒŠá;
    public static final Â Âµá€;
    public static final Â Ó;
    public static final Â à;
    public static final Â Ø;
    public static final Â áŒŠÆ;
    public static final Â áˆºÑ¢Õ;
    public static final Â ÂµÈ;
    public static final Â á;
    public static final Â ˆÏ­;
    public static final Â £á;
    public static final Â Å;
    protected boolean £à;
    protected int µà;
    protected boolean ˆà;
    protected int ¥Æ;
    protected boolean Ø­à;
    protected float µÕ;
    protected float Æ;
    protected boolean Šáƒ;
    protected boolean Ï­Ðƒà;
    protected boolean áŒŠà;
    protected double ŠÄ;
    protected double Ñ¢á;
    protected double ŒÏ;
    protected double Çªà¢;
    protected double Ê;
    protected double ÇŽÉ;
    public Â ˆá;
    public float ÇŽÕ;
    protected final Material É;
    public float áƒ;
    protected final BlockState á€;
    private IBlockState ŠÂµà;
    private String ¥à;
    private static final String Âµà = "CL_00000199";
    
    static {
        Õ = new ResourceLocation_1975012498("air");
        HorizonCode_Horizon_È = new RegistryNamespacedDefaultedByKey(Block.Õ);
        Â = new ObjectIntIdentityMap();
        Ý = new Â("stone", 1.0f, 1.0f);
        Ø­áŒŠá = new Â("wood", 1.0f, 1.0f);
        Âµá€ = new Â("gravel", 1.0f, 1.0f);
        Ó = new Â("grass", 1.0f, 1.0f);
        à = new Â("stone", 1.0f, 1.0f);
        Ø = new Â("stone", 1.0f, 1.5f);
        áŒŠÆ = new Â(1.0f, 1.0f) {
            private static final String Ø­áŒŠá = "CL_00000200";
            
            @Override
            public String HorizonCode_Horizon_È() {
                return "dig.glass";
            }
            
            @Override
            public String Â() {
                return "step.stone";
            }
        };
        áˆºÑ¢Õ = new Â("cloth", 1.0f, 1.0f);
        ÂµÈ = new Â("sand", 1.0f, 1.0f);
        á = new Â("snow", 1.0f, 1.0f);
        ˆÏ­ = new Â(1.0f, 1.0f) {
            private static final String Ø­áŒŠá = "CL_00000201";
            
            @Override
            public String HorizonCode_Horizon_È() {
                return "dig.wood";
            }
        };
        £á = new Â(0.3f, 1.0f) {
            private static final String Ø­áŒŠá = "CL_00000202";
            
            @Override
            public String HorizonCode_Horizon_È() {
                return "dig.stone";
            }
            
            @Override
            public String Â() {
                return "random.anvil_land";
            }
        };
        Å = new Â(1.0f, 1.0f) {
            private static final String Ø­áŒŠá = "CL_00002133";
            
            @Override
            public String HorizonCode_Horizon_È() {
                return "mob.slime.big";
            }
            
            @Override
            public String Â() {
                return "mob.slime.big";
            }
            
            @Override
            public String Ý() {
                return "mob.slime.small";
            }
        };
    }
    
    public static int HorizonCode_Horizon_È(final Block blockIn) {
        return Block.HorizonCode_Horizon_È.Ø­áŒŠá(blockIn);
    }
    
    public static int HorizonCode_Horizon_È(final IBlockState state) {
        return HorizonCode_Horizon_È(state.Ý()) + (state.Ý().Ý(state) << 12);
    }
    
    public static Block HorizonCode_Horizon_È(final int id) {
        return (Block)Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È(id);
    }
    
    public static IBlockState Â(final int id) {
        final int var1 = id & 0xFFF;
        final int var2 = id >> 12 & 0xF;
        return HorizonCode_Horizon_È(var1).Ý(var2);
    }
    
    public static Block HorizonCode_Horizon_È(final Item_1028566121 itemIn) {
        return (itemIn instanceof ItemBlock) ? ((ItemBlock)itemIn).ˆà() : null;
    }
    
    public static Block HorizonCode_Horizon_È(final String name) {
        final ResourceLocation_1975012498 var1 = new ResourceLocation_1975012498(name);
        if (Block.HorizonCode_Horizon_È.Ý(var1)) {
            return (Block)Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var1);
        }
        try {
            return (Block)Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È(Integer.parseInt(name));
        }
        catch (NumberFormatException var2) {
            return null;
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.£à;
    }
    
    public int Â() {
        return this.µà;
    }
    
    public boolean Ý() {
        return this.ˆà;
    }
    
    public int Ø­áŒŠá() {
        return this.¥Æ;
    }
    
    public boolean Âµá€() {
        return this.Ø­à;
    }
    
    public Material Ó() {
        return this.É;
    }
    
    public MapColor Â(final IBlockState state) {
        return this.Ó().£à();
    }
    
    public IBlockState Ý(final int meta) {
        return this.¥à();
    }
    
    public int Ý(final IBlockState state) {
        if (state != null && !state.HorizonCode_Horizon_È().isEmpty()) {
            throw new IllegalArgumentException("Don't know how to convert " + state + " back into data...");
        }
        return 0;
    }
    
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state;
    }
    
    protected Block(final Material materialIn) {
        this.Šáƒ = true;
        this.ˆá = Block.Ý;
        this.ÇŽÕ = 1.0f;
        this.áƒ = 0.6f;
        this.É = materialIn;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.£à = this.Å();
        this.µà = (this.Å() ? 255 : 0);
        this.ˆà = !materialIn.Ý();
        this.á€ = this.à¢();
        this.Ø(this.á€.Â());
    }
    
    protected Block HorizonCode_Horizon_È(final Â sound) {
        this.ˆá = sound;
        return this;
    }
    
    public Block Ø­áŒŠá(final int opacity) {
        this.µà = opacity;
        return this;
    }
    
    protected Block HorizonCode_Horizon_È(final float value) {
        this.¥Æ = (int)(15.0f * value);
        return this;
    }
    
    protected Block Â(final float resistance) {
        this.Æ = resistance * 3.0f;
        return this;
    }
    
    public boolean à() {
        return this.É.Ø­áŒŠá() && this.áˆºÑ¢Õ();
    }
    
    public boolean Ø() {
        return this.É.áˆºÑ¢Õ() && this.áˆºÑ¢Õ() && !this.áŒŠà();
    }
    
    public boolean áŒŠÆ() {
        return this.É.Ø­áŒŠá() && this.áˆºÑ¢Õ();
    }
    
    public boolean áˆºÑ¢Õ() {
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return !this.É.Ø­áŒŠá();
    }
    
    public int ÂµÈ() {
        return 3;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
        return false;
    }
    
    protected Block Ý(final float hardness) {
        this.µÕ = hardness;
        if (this.Æ < hardness * 5.0f) {
            this.Æ = hardness * 5.0f;
        }
        return this;
    }
    
    protected Block á() {
        this.Ý(-1.0f);
        return this;
    }
    
    public float Â(final World worldIn, final BlockPos pos) {
        return this.µÕ;
    }
    
    protected Block HorizonCode_Horizon_È(final boolean shouldTick) {
        this.Ï­Ðƒà = shouldTick;
        return this;
    }
    
    public boolean ˆÏ­() {
        return this.Ï­Ðƒà;
    }
    
    public boolean £á() {
        return this.áŒŠà;
    }
    
    protected final void HorizonCode_Horizon_È(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        this.ŠÄ = minX;
        this.Ñ¢á = minY;
        this.ŒÏ = minZ;
        this.Çªà¢ = maxX;
        this.Ê = maxY;
        this.ÇŽÉ = maxZ;
    }
    
    public int Â(final IBlockAccess worldIn, BlockPos pos) {
        Block var3 = worldIn.Â(pos).Ý();
        final int var4 = worldIn.HorizonCode_Horizon_È(pos, var3.Ø­áŒŠá());
        if (var4 == 0 && var3 instanceof BlockSlab) {
            pos = pos.Âµá€();
            var3 = worldIn.Â(pos).Ý();
            return worldIn.HorizonCode_Horizon_È(pos, var3.Ø­áŒŠá());
        }
        return var4;
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return (side == EnumFacing.HorizonCode_Horizon_È && this.Ñ¢á > 0.0) || (side == EnumFacing.Â && this.Ê < 1.0) || (side == EnumFacing.Ý && this.ŒÏ > 0.0) || (side == EnumFacing.Ø­áŒŠá && this.ÇŽÉ < 1.0) || (side == EnumFacing.Âµá€ && this.ŠÄ > 0.0) || (side == EnumFacing.Ó && this.Çªà¢ < 1.0) || !worldIn.Â(pos).Ý().Å();
    }
    
    public boolean Â(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return worldIn.Â(pos).Ý().Ó().Â();
    }
    
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + this.ŠÄ, pos.Â() + this.Ñ¢á, pos.Ý() + this.ŒÏ, pos.HorizonCode_Horizon_È() + this.Çªà¢, pos.Â() + this.Ê, pos.Ý() + this.ÇŽÉ);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        AxisAlignedBB var7 = this.HorizonCode_Horizon_È(worldIn, pos, state);
        final EventBoundingBox event = new EventBoundingBox();
        event.HorizonCode_Horizon_È(this, pos, var7);
        if (!event.HorizonCode_Horizon_È()) {
            if (var7 != event.à()) {
                var7 = (mask = event.à());
            }
            if (var7 != null && mask.Â(var7)) {
                list.add(var7);
            }
        }
    }
    
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + this.ŠÄ, pos.Â() + this.Ñ¢á, pos.Ý() + this.ŒÏ, pos.HorizonCode_Horizon_È() + this.Çªà¢, pos.Â() + this.Ê, pos.Ý() + this.ÇŽÉ);
    }
    
    public boolean Å() {
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockState state, final boolean p_176209_2_) {
        return this.£à();
    }
    
    public boolean £à() {
        return true;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
        this.Â(worldIn, pos, state, random);
    }
    
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
    }
    
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
    }
    
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
    }
    
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 10;
    }
    
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public int HorizonCode_Horizon_È(final Random random) {
        return 1;
    }
    
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(this);
    }
    
    public float HorizonCode_Horizon_È(final EntityPlayer playerIn, final World worldIn, final BlockPos pos) {
        final float var4 = this.Â(worldIn, pos);
        return (var4 < 0.0f) ? 0.0f : (playerIn.Â(this) ? (playerIn.HorizonCode_Horizon_È(this) / var4 / 30.0f) : (playerIn.HorizonCode_Horizon_È(this) / var4 / 100.0f));
    }
    
    public final void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final int forture) {
        this.HorizonCode_Horizon_È(worldIn, pos, state, 1.0f, forture);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.ŠÄ) {
            for (int var6 = this.HorizonCode_Horizon_È(fortune, worldIn.Å), var7 = 0; var7 < var6; ++var7) {
                if (worldIn.Å.nextFloat() <= chance) {
                    final Item_1028566121 var8 = this.HorizonCode_Horizon_È(state, worldIn.Å, fortune);
                    if (var8 != null) {
                        HorizonCode_Horizon_È(worldIn, pos, new ItemStack(var8, 1, this.Ø­áŒŠá(state)));
                    }
                }
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final ItemStack stack) {
        if (!worldIn.ŠÄ && worldIn.Çªà¢().Â("doTileDrops")) {
            final float var3 = 0.5f;
            final double var4 = worldIn.Å.nextFloat() * var3 + (1.0f - var3) * 0.5;
            final double var5 = worldIn.Å.nextFloat() * var3 + (1.0f - var3) * 0.5;
            final double var6 = worldIn.Å.nextFloat() * var3 + (1.0f - var3) * 0.5;
            final EntityItem var7 = new EntityItem(worldIn, pos.HorizonCode_Horizon_È() + var4, pos.Â() + var5, pos.Ý() + var6, stack);
            var7.ˆà();
            worldIn.HorizonCode_Horizon_È(var7);
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, int amount) {
        if (!worldIn.ŠÄ) {
            while (amount > 0) {
                final int var4 = EntityXPOrb.HorizonCode_Horizon_È(amount);
                amount -= var4;
                worldIn.HorizonCode_Horizon_È(new EntityXPOrb(worldIn, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, var4));
            }
        }
    }
    
    public int Ø­áŒŠá(final IBlockState state) {
        return 0;
    }
    
    public float HorizonCode_Horizon_È(final Entity exploder) {
        return this.Æ / 5.0f;
    }
    
    public MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, Vec3 start, Vec3 end) {
        this.Ý((IBlockAccess)worldIn, pos);
        start = start.Â(-pos.HorizonCode_Horizon_È(), -pos.Â(), -pos.Ý());
        end = end.Â(-pos.HorizonCode_Horizon_È(), -pos.Â(), -pos.Ý());
        Vec3 var5 = start.HorizonCode_Horizon_È(end, this.ŠÄ);
        Vec3 var6 = start.HorizonCode_Horizon_È(end, this.Çªà¢);
        Vec3 var7 = start.Â(end, this.Ñ¢á);
        Vec3 var8 = start.Â(end, this.Ê);
        Vec3 var9 = start.Ý(end, this.ŒÏ);
        Vec3 var10 = start.Ý(end, this.ÇŽÉ);
        if (!this.HorizonCode_Horizon_È(var5)) {
            var5 = null;
        }
        if (!this.HorizonCode_Horizon_È(var6)) {
            var6 = null;
        }
        if (!this.Â(var7)) {
            var7 = null;
        }
        if (!this.Â(var8)) {
            var8 = null;
        }
        if (!this.Ý(var9)) {
            var9 = null;
        }
        if (!this.Ý(var10)) {
            var10 = null;
        }
        Vec3 var11 = null;
        if (var5 != null && (var11 == null || start.à(var5) < start.à(var11))) {
            var11 = var5;
        }
        if (var6 != null && (var11 == null || start.à(var6) < start.à(var11))) {
            var11 = var6;
        }
        if (var7 != null && (var11 == null || start.à(var7) < start.à(var11))) {
            var11 = var7;
        }
        if (var8 != null && (var11 == null || start.à(var8) < start.à(var11))) {
            var11 = var8;
        }
        if (var9 != null && (var11 == null || start.à(var9) < start.à(var11))) {
            var11 = var9;
        }
        if (var10 != null && (var11 == null || start.à(var10) < start.à(var11))) {
            var11 = var10;
        }
        if (var11 == null) {
            return null;
        }
        EnumFacing var12 = null;
        if (var11 == var5) {
            var12 = EnumFacing.Âµá€;
        }
        if (var11 == var6) {
            var12 = EnumFacing.Ó;
        }
        if (var11 == var7) {
            var12 = EnumFacing.HorizonCode_Horizon_È;
        }
        if (var11 == var8) {
            var12 = EnumFacing.Â;
        }
        if (var11 == var9) {
            var12 = EnumFacing.Ý;
        }
        if (var11 == var10) {
            var12 = EnumFacing.Ø­áŒŠá;
        }
        return new MovingObjectPosition(var11.Â(pos.HorizonCode_Horizon_È(), pos.Â(), pos.Ý()), var12, pos);
    }
    
    private boolean HorizonCode_Horizon_È(final Vec3 point) {
        return point != null && (point.Â >= this.Ñ¢á && point.Â <= this.Ê && point.Ý >= this.ŒÏ && point.Ý <= this.ÇŽÉ);
    }
    
    private boolean Â(final Vec3 point) {
        return point != null && (point.HorizonCode_Horizon_È >= this.ŠÄ && point.HorizonCode_Horizon_È <= this.Çªà¢ && point.Ý >= this.ŒÏ && point.Ý <= this.ÇŽÉ);
    }
    
    private boolean Ý(final Vec3 point) {
        return point != null && (point.HorizonCode_Horizon_È >= this.ŠÄ && point.HorizonCode_Horizon_È <= this.Çªà¢ && point.Â >= this.Ñ¢á && point.Â <= this.Ê);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Explosion explosionIn) {
    }
    
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.HorizonCode_Horizon_È;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side, final ItemStack stack) {
        return this.HorizonCode_Horizon_È(worldIn, pos, side);
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return this.Ø­áŒŠá(worldIn, pos);
    }
    
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return worldIn.Â(pos).Ý().É.áŒŠÆ();
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        return false;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn) {
    }
    
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.Ý(meta);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
    }
    
    public Vec3 HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn, final Vec3 motion) {
        return motion;
    }
    
    public void Ý(final IBlockAccess access, final BlockPos pos) {
    }
    
    public final double ˆà() {
        return this.ŠÄ;
    }
    
    public final double ¥Æ() {
        return this.Çªà¢;
    }
    
    public final double Ø­à() {
        return this.Ñ¢á;
    }
    
    public final double µÕ() {
        return this.Ê;
    }
    
    public final double Æ() {
        return this.ŒÏ;
    }
    
    public final double Šáƒ() {
        return this.ÇŽÉ;
    }
    
    public int Ï­Ðƒà() {
        return 16777215;
    }
    
    public int Âµá€(final IBlockState state) {
        return 16777215;
    }
    
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return 16777215;
    }
    
    public final int Ø­áŒŠá(final IBlockAccess worldIn, final BlockPos pos) {
        return this.HorizonCode_Horizon_È(worldIn, pos, 0);
    }
    
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return 0;
    }
    
    public boolean áŒŠà() {
        return false;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
    }
    
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return 0;
    }
    
    public void ŠÄ() {
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        playerIn.HorizonCode_Horizon_È(StatList.É[HorizonCode_Horizon_È(this)]);
        playerIn.Ý(0.025f);
        if (this.Ñ¢á() && EnchantmentHelper.Ø­áŒŠá(playerIn)) {
            final ItemStack var7 = this.Ó(state);
            if (var7 != null) {
                HorizonCode_Horizon_È(worldIn, pos, var7);
            }
        }
        else {
            final int var8 = EnchantmentHelper.Âµá€(playerIn);
            this.HorizonCode_Horizon_È(worldIn, pos, state, var8);
        }
    }
    
    protected boolean Ñ¢á() {
        return this.áˆºÑ¢Õ() && !this.áŒŠà;
    }
    
    protected ItemStack Ó(final IBlockState state) {
        int var2 = 0;
        final Item_1028566121 var3 = Item_1028566121.HorizonCode_Horizon_È(this);
        if (var3 != null && var3.Â()) {
            var2 = this.Ý(state);
        }
        return new ItemStack(var3, 1, var2);
    }
    
    public int HorizonCode_Horizon_È(final int fortune, final Random random) {
        return this.HorizonCode_Horizon_È(random);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
    }
    
    public Block Â(final String name) {
        this.¥à = name;
        return this;
    }
    
    public String ŒÏ() {
        return StatCollector.HorizonCode_Horizon_È(String.valueOf(this.Çªà¢()) + ".name");
    }
    
    public String Çªà¢() {
        return "tile." + this.¥à;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final int eventID, final int eventParam) {
        return false;
    }
    
    public boolean Ê() {
        return this.Šáƒ;
    }
    
    protected Block ÇŽÉ() {
        this.Šáƒ = false;
        return this;
    }
    
    public int ˆá() {
        return this.É.á();
    }
    
    public float ÇŽÕ() {
        return this.à() ? 0.2f : 1.0f;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance) {
        entityIn.Ø­áŒŠá(fallDistance, 1.0f);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Entity entityIn) {
        entityIn.ˆá = 0.0;
    }
    
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(this);
    }
    
    public int Ó(final World worldIn, final BlockPos pos) {
        return this.Ø­áŒŠá(worldIn.Â(pos));
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, 0));
    }
    
    public CreativeTabs É() {
        return this.à¢;
    }
    
    public Block HorizonCode_Horizon_È(final CreativeTabs tab) {
        this.à¢ = tab;
        return this;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
    }
    
    public void à(final World worldIn, final BlockPos pos) {
    }
    
    public boolean áƒ() {
        return false;
    }
    
    public boolean á€() {
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final Explosion explosionIn) {
        return true;
    }
    
    public boolean Â(final Block other) {
        return this == other;
    }
    
    public static boolean HorizonCode_Horizon_È(final Block blockIn, final Block other) {
        return blockIn != null && other != null && (blockIn == other || blockIn.Â(other));
    }
    
    public boolean Õ() {
        return false;
    }
    
    public int Ø(final World worldIn, final BlockPos pos) {
        return 0;
    }
    
    public IBlockState à(final IBlockState state) {
        return state;
    }
    
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[0]);
    }
    
    public BlockState ŠÂµà() {
        return this.á€;
    }
    
    protected final void Ø(final IBlockState state) {
        this.ŠÂµà = state;
    }
    
    public final IBlockState ¥à() {
        return this.ŠÂµà;
    }
    
    public HorizonCode_Horizon_È Âµà() {
        return Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
    }
    
    public static void Ç() {
        HorizonCode_Horizon_È(0, Block.Õ, new BlockAir().Â("air"));
        HorizonCode_Horizon_È(1, "stone", new BlockStone().Ý(1.5f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stone"));
        HorizonCode_Horizon_È(2, "grass", new BlockGrass().Ý(0.6f).HorizonCode_Horizon_È(Block.Ó).Â("grass"));
        HorizonCode_Horizon_È(3, "dirt", new BlockDirt().Ý(0.5f).HorizonCode_Horizon_È(Block.Âµá€).Â("dirt"));
        final Block var0 = new Block(Material.Âµá€).Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stonebrick").HorizonCode_Horizon_È(CreativeTabs.Â);
        HorizonCode_Horizon_È(4, "cobblestone", var0);
        final Block var2 = new BlockPlanks().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("wood");
        HorizonCode_Horizon_È(5, "planks", var2);
        HorizonCode_Horizon_È(6, "sapling", new BlockSapling().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("sapling"));
        HorizonCode_Horizon_È(7, "bedrock", new Block(Material.Âµá€).á().Â(6000000.0f).HorizonCode_Horizon_È(Block.à).Â("bedrock").ÇŽÉ().HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(8, "flowing_water", new BlockDynamicLiquid(Material.Ø).Ý(100.0f).Ø­áŒŠá(3).Â("water").ÇŽÉ());
        HorizonCode_Horizon_È(9, "water", new BlockStaticLiquid(Material.Ø).Ý(100.0f).Ø­áŒŠá(3).Â("water").ÇŽÉ());
        HorizonCode_Horizon_È(10, "flowing_lava", new BlockDynamicLiquid(Material.áŒŠÆ).Ý(100.0f).HorizonCode_Horizon_È(1.0f).Â("lava").ÇŽÉ());
        HorizonCode_Horizon_È(11, "lava", new BlockStaticLiquid(Material.áŒŠÆ).Ý(100.0f).HorizonCode_Horizon_È(1.0f).Â("lava").ÇŽÉ());
        HorizonCode_Horizon_È(12, "sand", new BlockSand().Ý(0.5f).HorizonCode_Horizon_È(Block.ÂµÈ).Â("sand"));
        HorizonCode_Horizon_È(13, "gravel", new BlockGravel().Ý(0.6f).HorizonCode_Horizon_È(Block.Âµá€).Â("gravel"));
        HorizonCode_Horizon_È(14, "gold_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreGold"));
        HorizonCode_Horizon_È(15, "iron_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreIron"));
        HorizonCode_Horizon_È(16, "coal_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreCoal"));
        HorizonCode_Horizon_È(17, "log", new BlockOldLog().Â("log"));
        HorizonCode_Horizon_È(18, "leaves", new BlockOldLeaf().Â("leaves"));
        HorizonCode_Horizon_È(19, "sponge", new BlockSponge().Ý(0.6f).HorizonCode_Horizon_È(Block.Ó).Â("sponge"));
        HorizonCode_Horizon_È(20, "glass", new BlockGlass(Material.¥Æ, false).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("glass"));
        HorizonCode_Horizon_È(21, "lapis_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreLapis"));
        HorizonCode_Horizon_È(22, "lapis_block", new BlockCompressed(MapColor.É).Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("blockLapis").HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(23, "dispenser", new BlockDispenser().Ý(3.5f).HorizonCode_Horizon_È(Block.à).Â("dispenser"));
        final Block var3 = new BlockSandStone().HorizonCode_Horizon_È(Block.à).Ý(0.8f).Â("sandStone");
        HorizonCode_Horizon_È(24, "sandstone", var3);
        HorizonCode_Horizon_È(25, "noteblock", new BlockNote().Ý(0.8f).Â("musicBlock"));
        HorizonCode_Horizon_È(26, "bed", new BlockBed().HorizonCode_Horizon_È(Block.Ø­áŒŠá).Ý(0.2f).Â("bed").ÇŽÉ());
        HorizonCode_Horizon_È(27, "golden_rail", new BlockRailPowered().Ý(0.7f).HorizonCode_Horizon_È(Block.Ø).Â("goldenRail"));
        HorizonCode_Horizon_È(28, "detector_rail", new BlockRailDetector().Ý(0.7f).HorizonCode_Horizon_È(Block.Ø).Â("detectorRail"));
        HorizonCode_Horizon_È(29, "sticky_piston", new BlockPistonBase(true).Â("pistonStickyBase"));
        HorizonCode_Horizon_È(30, "web", new BlockWeb().Ø­áŒŠá(1).Ý(4.0f).Â("web"));
        HorizonCode_Horizon_È(31, "tallgrass", new BlockTallGrass().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("tallgrass"));
        HorizonCode_Horizon_È(32, "deadbush", new BlockDeadBush().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("deadbush"));
        HorizonCode_Horizon_È(33, "piston", new BlockPistonBase(false).Â("pistonBase"));
        HorizonCode_Horizon_È(34, "piston_head", new BlockPistonExtension());
        HorizonCode_Horizon_È(35, "wool", new BlockColored(Material.£á).Ý(0.8f).HorizonCode_Horizon_È(Block.áˆºÑ¢Õ).Â("cloth"));
        HorizonCode_Horizon_È(36, "piston_extension", new BlockPistonMoving());
        HorizonCode_Horizon_È(37, "yellow_flower", new BlockYellowFlower().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("flower1"));
        HorizonCode_Horizon_È(38, "red_flower", new BlockRedFlower().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("flower2"));
        final Block var4 = new BlockMushroom().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).HorizonCode_Horizon_È(0.125f).Â("mushroom");
        HorizonCode_Horizon_È(39, "brown_mushroom", var4);
        final Block var5 = new BlockMushroom().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("mushroom");
        HorizonCode_Horizon_È(40, "red_mushroom", var5);
        HorizonCode_Horizon_È(41, "gold_block", new BlockCompressed(MapColor.ˆá).Ý(3.0f).Â(10.0f).HorizonCode_Horizon_È(Block.Ø).Â("blockGold"));
        HorizonCode_Horizon_È(42, "iron_block", new BlockCompressed(MapColor.Ø).Ý(5.0f).Â(10.0f).HorizonCode_Horizon_È(Block.Ø).Â("blockIron"));
        HorizonCode_Horizon_È(43, "double_stone_slab", new BlockDoubleStoneSlab().Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stoneSlab"));
        HorizonCode_Horizon_È(44, "stone_slab", new BlockHalfStoneSlab().Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stoneSlab"));
        final Block var6 = new Block(Material.Âµá€).Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("brick").HorizonCode_Horizon_È(CreativeTabs.Â);
        HorizonCode_Horizon_È(45, "brick_block", var6);
        HorizonCode_Horizon_È(46, "tnt", new BlockTNT().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("tnt"));
        HorizonCode_Horizon_È(47, "bookshelf", new BlockBookshelf().Ý(1.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("bookshelf"));
        HorizonCode_Horizon_È(48, "mossy_cobblestone", new Block(Material.Âµá€).Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stoneMoss").HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(49, "obsidian", new BlockObsidian().Ý(50.0f).Â(2000.0f).HorizonCode_Horizon_È(Block.à).Â("obsidian"));
        HorizonCode_Horizon_È(50, "torch", new BlockTorch().Ý(0.0f).HorizonCode_Horizon_È(0.9375f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("torch"));
        HorizonCode_Horizon_È(51, "fire", new BlockFire().Ý(0.0f).HorizonCode_Horizon_È(1.0f).HorizonCode_Horizon_È(Block.áˆºÑ¢Õ).Â("fire").ÇŽÉ());
        HorizonCode_Horizon_È(52, "mob_spawner", new BlockMobSpawner().Ý(5.0f).HorizonCode_Horizon_È(Block.Ø).Â("mobSpawner").ÇŽÉ());
        HorizonCode_Horizon_È(53, "oak_stairs", new BlockStairs(var2.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È)).Â("stairsWood"));
        HorizonCode_Horizon_È(54, "chest", new BlockChest(0).Ý(2.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("chest"));
        HorizonCode_Horizon_È(55, "redstone_wire", new BlockRedstoneWire().Ý(0.0f).HorizonCode_Horizon_È(Block.Ý).Â("redstoneDust").ÇŽÉ());
        HorizonCode_Horizon_È(56, "diamond_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreDiamond"));
        HorizonCode_Horizon_È(57, "diamond_block", new BlockCompressed(MapColor.ÇŽÕ).Ý(5.0f).Â(10.0f).HorizonCode_Horizon_È(Block.Ø).Â("blockDiamond"));
        HorizonCode_Horizon_È(58, "crafting_table", new BlockWorkbench().Ý(2.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("workbench"));
        HorizonCode_Horizon_È(59, "wheat", new BlockCrops().Â("crops"));
        final Block var7 = new BlockFarmland().Ý(0.6f).HorizonCode_Horizon_È(Block.Âµá€).Â("farmland");
        HorizonCode_Horizon_È(60, "farmland", var7);
        HorizonCode_Horizon_È(61, "furnace", new BlockFurnace(false).Ý(3.5f).HorizonCode_Horizon_È(Block.à).Â("furnace").HorizonCode_Horizon_È(CreativeTabs.Ý));
        HorizonCode_Horizon_È(62, "lit_furnace", new BlockFurnace(true).Ý(3.5f).HorizonCode_Horizon_È(Block.à).HorizonCode_Horizon_È(0.875f).Â("furnace"));
        HorizonCode_Horizon_È(63, "standing_sign", new BlockStandingSign().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("sign").ÇŽÉ());
        HorizonCode_Horizon_È(64, "wooden_door", new BlockDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("doorOak").ÇŽÉ());
        HorizonCode_Horizon_È(65, "ladder", new BlockLadder().Ý(0.4f).HorizonCode_Horizon_È(Block.ˆÏ­).Â("ladder"));
        HorizonCode_Horizon_È(66, "rail", new BlockRail().Ý(0.7f).HorizonCode_Horizon_È(Block.Ø).Â("rail"));
        HorizonCode_Horizon_È(67, "stone_stairs", new BlockStairs(var0.¥à()).Â("stairsStone"));
        HorizonCode_Horizon_È(68, "wall_sign", new BlockWallSign().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("sign").ÇŽÉ());
        HorizonCode_Horizon_È(69, "lever", new BlockLever().Ý(0.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("lever"));
        HorizonCode_Horizon_È(70, "stone_pressure_plate", new BlockPressurePlate(Material.Âµá€, BlockPressurePlate.HorizonCode_Horizon_È.Â).Ý(0.5f).HorizonCode_Horizon_È(Block.à).Â("pressurePlateStone"));
        HorizonCode_Horizon_È(71, "iron_door", new BlockDoor(Material.Ó).Ý(5.0f).HorizonCode_Horizon_È(Block.Ø).Â("doorIron").ÇŽÉ());
        HorizonCode_Horizon_È(72, "wooden_pressure_plate", new BlockPressurePlate(Material.Ø­áŒŠá, BlockPressurePlate.HorizonCode_Horizon_È.HorizonCode_Horizon_È).Ý(0.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("pressurePlateWood"));
        HorizonCode_Horizon_È(73, "redstone_ore", new BlockRedstoneOre(false).Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreRedstone").HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(74, "lit_redstone_ore", new BlockRedstoneOre(true).HorizonCode_Horizon_È(0.625f).Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreRedstone"));
        HorizonCode_Horizon_È(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).Ý(0.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("notGate"));
        HorizonCode_Horizon_È(76, "redstone_torch", new BlockRedstoneTorch(true).Ý(0.0f).HorizonCode_Horizon_È(0.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("notGate").HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá));
        HorizonCode_Horizon_È(77, "stone_button", new BlockButtonStone().Ý(0.5f).HorizonCode_Horizon_È(Block.à).Â("button"));
        HorizonCode_Horizon_È(78, "snow_layer", new BlockSnow().Ý(0.1f).HorizonCode_Horizon_È(Block.á).Â("snow").Ø­áŒŠá(0));
        HorizonCode_Horizon_È(79, "ice", new BlockIce().Ý(0.5f).Ø­áŒŠá(3).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("ice"));
        HorizonCode_Horizon_È(80, "snow", new BlockSnowBlock().Ý(0.2f).HorizonCode_Horizon_È(Block.á).Â("snow"));
        HorizonCode_Horizon_È(81, "cactus", new BlockCactus().Ý(0.4f).HorizonCode_Horizon_È(Block.áˆºÑ¢Õ).Â("cactus"));
        HorizonCode_Horizon_È(82, "clay", new BlockClay().Ý(0.6f).HorizonCode_Horizon_È(Block.Âµá€).Â("clay"));
        HorizonCode_Horizon_È(83, "reeds", new BlockReed().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("reeds").ÇŽÉ());
        HorizonCode_Horizon_È(84, "jukebox", new BlockJukebox().Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("jukebox"));
        HorizonCode_Horizon_È(85, "fence", new BlockFence(Material.Ø­áŒŠá).Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("fence"));
        final Block var8 = new BlockPumpkin().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("pumpkin");
        HorizonCode_Horizon_È(86, "pumpkin", var8);
        HorizonCode_Horizon_È(87, "netherrack", new BlockNetherrack().Ý(0.4f).HorizonCode_Horizon_È(Block.à).Â("hellrock"));
        HorizonCode_Horizon_È(88, "soul_sand", new BlockSoulSand().Ý(0.5f).HorizonCode_Horizon_È(Block.ÂµÈ).Â("hellsand"));
        HorizonCode_Horizon_È(89, "glowstone", new BlockGlowstone(Material.¥Æ).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).HorizonCode_Horizon_È(1.0f).Â("lightgem"));
        HorizonCode_Horizon_È(90, "portal", new BlockPortal().Ý(-1.0f).HorizonCode_Horizon_È(Block.áŒŠÆ).HorizonCode_Horizon_È(0.75f).Â("portal"));
        HorizonCode_Horizon_È(91, "lit_pumpkin", new BlockPumpkin().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).HorizonCode_Horizon_È(1.0f).Â("litpumpkin"));
        HorizonCode_Horizon_È(92, "cake", new BlockCake().Ý(0.5f).HorizonCode_Horizon_È(Block.áˆºÑ¢Õ).Â("cake").ÇŽÉ());
        HorizonCode_Horizon_È(93, "unpowered_repeater", new BlockRedstoneRepeater(false).Ý(0.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("diode").ÇŽÉ());
        HorizonCode_Horizon_È(94, "powered_repeater", new BlockRedstoneRepeater(true).Ý(0.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("diode").ÇŽÉ());
        HorizonCode_Horizon_È(95, "stained_glass", new BlockStainedGlass(Material.¥Æ).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("stainedGlass"));
        HorizonCode_Horizon_È(96, "trapdoor", new BlockTrapDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("trapdoor").ÇŽÉ());
        HorizonCode_Horizon_È(97, "monster_egg", new BlockSilverfish().Ý(0.75f).Â("monsterStoneEgg"));
        final Block var9 = new BlockStoneBrick().Ý(1.5f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stonebricksmooth");
        HorizonCode_Horizon_È(98, "stonebrick", var9);
        HorizonCode_Horizon_È(99, "brown_mushroom_block", new BlockHugeMushroom(Material.Ø­áŒŠá, var4).Ý(0.2f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("mushroom"));
        HorizonCode_Horizon_È(100, "red_mushroom_block", new BlockHugeMushroom(Material.Ø­áŒŠá, var5).Ý(0.2f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("mushroom"));
        HorizonCode_Horizon_È(101, "iron_bars", new BlockPane(Material.Ó, true).Ý(5.0f).Â(10.0f).HorizonCode_Horizon_È(Block.Ø).Â("fenceIron"));
        HorizonCode_Horizon_È(102, "glass_pane", new BlockPane(Material.¥Æ, false).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("thinGlass"));
        final Block var10 = new BlockMelon().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("melon");
        HorizonCode_Horizon_È(103, "melon_block", var10);
        HorizonCode_Horizon_È(104, "pumpkin_stem", new BlockStem(var8).Ý(0.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("pumpkinStem"));
        HorizonCode_Horizon_È(105, "melon_stem", new BlockStem(var10).Ý(0.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("pumpkinStem"));
        HorizonCode_Horizon_È(106, "vine", new BlockVine().Ý(0.2f).HorizonCode_Horizon_È(Block.Ó).Â("vine"));
        HorizonCode_Horizon_È(107, "fence_gate", new BlockFenceGate().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("fenceGate"));
        HorizonCode_Horizon_È(108, "brick_stairs", new BlockStairs(var6.¥à()).Â("stairsBrick"));
        HorizonCode_Horizon_È(109, "stone_brick_stairs", new BlockStairs(var9.¥à().HorizonCode_Horizon_È(BlockStoneBrick.Õ, BlockStoneBrick.HorizonCode_Horizon_È.HorizonCode_Horizon_È)).Â("stairsStoneBrickSmooth"));
        HorizonCode_Horizon_È(110, "mycelium", new BlockMycelium().Ý(0.6f).HorizonCode_Horizon_È(Block.Ó).Â("mycel"));
        HorizonCode_Horizon_È(111, "waterlily", new BlockLilyPad().Ý(0.0f).HorizonCode_Horizon_È(Block.Ó).Â("waterlily"));
        final Block var11 = new BlockNetherBrick().Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("netherBrick").HorizonCode_Horizon_È(CreativeTabs.Â);
        HorizonCode_Horizon_È(112, "nether_brick", var11);
        HorizonCode_Horizon_È(113, "nether_brick_fence", new BlockFence(Material.Âµá€).Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("netherFence"));
        HorizonCode_Horizon_È(114, "nether_brick_stairs", new BlockStairs(var11.¥à()).Â("stairsNetherBrick"));
        HorizonCode_Horizon_È(115, "nether_wart", new BlockNetherWart().Â("netherStalk"));
        HorizonCode_Horizon_È(116, "enchanting_table", new BlockEnchantmentTable().Ý(5.0f).Â(2000.0f).Â("enchantmentTable"));
        HorizonCode_Horizon_È(117, "brewing_stand", new BlockBrewingStand().Ý(0.5f).HorizonCode_Horizon_È(0.125f).Â("brewingStand"));
        HorizonCode_Horizon_È(118, "cauldron", new BlockCauldron().Ý(2.0f).Â("cauldron"));
        HorizonCode_Horizon_È(119, "end_portal", new BlockEndPortal(Material.ÇŽÉ).Ý(-1.0f).Â(6000000.0f));
        HorizonCode_Horizon_È(120, "end_portal_frame", new BlockEndPortalFrame().HorizonCode_Horizon_È(Block.áŒŠÆ).HorizonCode_Horizon_È(0.125f).Ý(-1.0f).Â("endPortalFrame").Â(6000000.0f).HorizonCode_Horizon_È(CreativeTabs.Ý));
        HorizonCode_Horizon_È(121, "end_stone", new Block(Material.Âµá€).Ý(3.0f).Â(15.0f).HorizonCode_Horizon_È(Block.à).Â("whiteStone").HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(122, "dragon_egg", new BlockDragonEgg().Ý(3.0f).Â(15.0f).HorizonCode_Horizon_È(Block.à).HorizonCode_Horizon_È(0.125f).Â("dragonEgg"));
        HorizonCode_Horizon_È(123, "redstone_lamp", new BlockRedstoneLight(false).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("redstoneLight").HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá));
        HorizonCode_Horizon_È(124, "lit_redstone_lamp", new BlockRedstoneLight(true).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("redstoneLight"));
        HorizonCode_Horizon_È(125, "double_wooden_slab", new BlockDoubleWoodSlab().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("woodSlab"));
        HorizonCode_Horizon_È(126, "wooden_slab", new BlockHalfWoodSlab().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("woodSlab"));
        HorizonCode_Horizon_È(127, "cocoa", new BlockCocoa().Ý(0.2f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("cocoa"));
        HorizonCode_Horizon_È(128, "sandstone_stairs", new BlockStairs(var3.¥à().HorizonCode_Horizon_È(BlockSandStone.Õ, BlockSandStone.HorizonCode_Horizon_È.Ý)).Â("stairsSandStone"));
        HorizonCode_Horizon_È(129, "emerald_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("oreEmerald"));
        HorizonCode_Horizon_È(130, "ender_chest", new BlockEnderChest().Ý(22.5f).Â(1000.0f).HorizonCode_Horizon_È(Block.à).Â("enderChest").HorizonCode_Horizon_È(0.5f));
        HorizonCode_Horizon_È(131, "tripwire_hook", new BlockTripWireHook().Â("tripWireSource"));
        HorizonCode_Horizon_È(132, "tripwire", new BlockTripWire().Â("tripWire"));
        HorizonCode_Horizon_È(133, "emerald_block", new BlockCompressed(MapColor.áƒ).Ý(5.0f).Â(10.0f).HorizonCode_Horizon_È(Block.Ø).Â("blockEmerald"));
        HorizonCode_Horizon_È(134, "spruce_stairs", new BlockStairs(var2.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, BlockPlanks.HorizonCode_Horizon_È.Â)).Â("stairsWoodSpruce"));
        HorizonCode_Horizon_È(135, "birch_stairs", new BlockStairs(var2.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, BlockPlanks.HorizonCode_Horizon_È.Ý)).Â("stairsWoodBirch"));
        HorizonCode_Horizon_È(136, "jungle_stairs", new BlockStairs(var2.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá)).Â("stairsWoodJungle"));
        HorizonCode_Horizon_È(137, "command_block", new BlockCommandBlock().á().Â(6000000.0f).Â("commandBlock"));
        HorizonCode_Horizon_È(138, "beacon", new BlockBeacon().Â("beacon").HorizonCode_Horizon_È(1.0f));
        HorizonCode_Horizon_È(139, "cobblestone_wall", new BlockWall(var0).Â("cobbleWall"));
        HorizonCode_Horizon_È(140, "flower_pot", new BlockFlowerPot().Ý(0.0f).HorizonCode_Horizon_È(Block.Ý).Â("flowerPot"));
        HorizonCode_Horizon_È(141, "carrots", new BlockCarrot().Â("carrots"));
        HorizonCode_Horizon_È(142, "potatoes", new BlockPotato().Â("potatoes"));
        HorizonCode_Horizon_È(143, "wooden_button", new BlockButtonWood().Ý(0.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("button"));
        HorizonCode_Horizon_È(144, "skull", new BlockSkull().Ý(1.0f).HorizonCode_Horizon_È(Block.à).Â("skull"));
        HorizonCode_Horizon_È(145, "anvil", new BlockAnvil().Ý(5.0f).HorizonCode_Horizon_È(Block.£á).Â(2000.0f).Â("anvil"));
        HorizonCode_Horizon_È(146, "trapped_chest", new BlockChest(1).Ý(2.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("chestTrap"));
        HorizonCode_Horizon_È(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted("gold_block", Material.Ó, 15).Ý(0.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("weightedPlate_light"));
        HorizonCode_Horizon_È(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted("iron_block", Material.Ó, 150).Ý(0.5f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("weightedPlate_heavy"));
        HorizonCode_Horizon_È(149, "unpowered_comparator", new BlockRedstoneComparator(false).Ý(0.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("comparator").ÇŽÉ());
        HorizonCode_Horizon_È(150, "powered_comparator", new BlockRedstoneComparator(true).Ý(0.0f).HorizonCode_Horizon_È(0.625f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("comparator").ÇŽÉ());
        HorizonCode_Horizon_È(151, "daylight_detector", new BlockDaylightDetector(false));
        HorizonCode_Horizon_È(152, "redstone_block", new BlockCompressedPowered(MapColor.Ó).Ý(5.0f).Â(10.0f).HorizonCode_Horizon_È(Block.Ø).Â("blockRedstone"));
        HorizonCode_Horizon_È(153, "quartz_ore", new BlockOre().Ý(3.0f).Â(5.0f).HorizonCode_Horizon_È(Block.à).Â("netherquartz"));
        HorizonCode_Horizon_È(154, "hopper", new BlockHopper().Ý(3.0f).Â(8.0f).HorizonCode_Horizon_È(Block.Ø).Â("hopper"));
        final Block var12 = new BlockQuartz().HorizonCode_Horizon_È(Block.à).Ý(0.8f).Â("quartzBlock");
        HorizonCode_Horizon_È(155, "quartz_block", var12);
        HorizonCode_Horizon_È(156, "quartz_stairs", new BlockStairs(var12.¥à().HorizonCode_Horizon_È(BlockQuartz.Õ, BlockQuartz.HorizonCode_Horizon_È.HorizonCode_Horizon_È)).Â("stairsQuartz"));
        HorizonCode_Horizon_È(157, "activator_rail", new BlockRailPowered().Ý(0.7f).HorizonCode_Horizon_È(Block.Ø).Â("activatorRail"));
        HorizonCode_Horizon_È(158, "dropper", new BlockDropper().Ý(3.5f).HorizonCode_Horizon_È(Block.à).Â("dropper"));
        HorizonCode_Horizon_È(159, "stained_hardened_clay", new BlockColored(Material.Âµá€).Ý(1.25f).Â(7.0f).HorizonCode_Horizon_È(Block.à).Â("clayHardenedStained"));
        HorizonCode_Horizon_È(160, "stained_glass_pane", new BlockStainedGlassPane().Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("thinStainedGlass"));
        HorizonCode_Horizon_È(161, "leaves2", new BlockNewLeaf().Â("leaves"));
        HorizonCode_Horizon_È(162, "log2", new BlockNewLog().Â("log"));
        HorizonCode_Horizon_È(163, "acacia_stairs", new BlockStairs(var2.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, BlockPlanks.HorizonCode_Horizon_È.Âµá€)).Â("stairsWoodAcacia"));
        HorizonCode_Horizon_È(164, "dark_oak_stairs", new BlockStairs(var2.¥à().HorizonCode_Horizon_È(BlockPlanks.Õ, BlockPlanks.HorizonCode_Horizon_È.Ó)).Â("stairsWoodDarkOak"));
        HorizonCode_Horizon_È(165, "slime", new BlockSlime().Â("slime").HorizonCode_Horizon_È(Block.Å));
        HorizonCode_Horizon_È(166, "barrier", new BlockBarrier().Â("barrier"));
        HorizonCode_Horizon_È(167, "iron_trapdoor", new BlockTrapDoor(Material.Ó).Ý(5.0f).HorizonCode_Horizon_È(Block.Ø).Â("ironTrapdoor").ÇŽÉ());
        HorizonCode_Horizon_È(168, "prismarine", new BlockPrismarine().Ý(1.5f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("prismarine"));
        HorizonCode_Horizon_È(169, "sea_lantern", new BlockSeaLantern(Material.¥Æ).Ý(0.3f).HorizonCode_Horizon_È(Block.áŒŠÆ).HorizonCode_Horizon_È(1.0f).Â("seaLantern"));
        HorizonCode_Horizon_È(170, "hay_block", new BlockHay().Ý(0.5f).HorizonCode_Horizon_È(Block.Ó).Â("hayBlock").HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(171, "carpet", new BlockCarpet().Ý(0.1f).HorizonCode_Horizon_È(Block.áˆºÑ¢Õ).Â("woolCarpet").Ø­áŒŠá(0));
        HorizonCode_Horizon_È(172, "hardened_clay", new BlockHardenedClay().Ý(1.25f).Â(7.0f).HorizonCode_Horizon_È(Block.à).Â("clayHardened"));
        HorizonCode_Horizon_È(173, "coal_block", new Block(Material.Âµá€).Ý(5.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("blockCoal").HorizonCode_Horizon_È(CreativeTabs.Â));
        HorizonCode_Horizon_È(174, "packed_ice", new BlockPackedIce().Ý(0.5f).HorizonCode_Horizon_È(Block.áŒŠÆ).Â("icePacked"));
        HorizonCode_Horizon_È(175, "double_plant", new BlockDoublePlant());
        HorizonCode_Horizon_È(176, "standing_banner", new BlockBanner.Â().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("banner").ÇŽÉ());
        HorizonCode_Horizon_È(177, "wall_banner", new BlockBanner.HorizonCode_Horizon_È().Ý(1.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("banner").ÇŽÉ());
        HorizonCode_Horizon_È(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
        final Block var13 = new BlockRedSandstone().HorizonCode_Horizon_È(Block.à).Ý(0.8f).Â("redSandStone");
        HorizonCode_Horizon_È(179, "red_sandstone", var13);
        HorizonCode_Horizon_È(180, "red_sandstone_stairs", new BlockStairs(var13.¥à().HorizonCode_Horizon_È(BlockRedSandstone.Õ, BlockRedSandstone.HorizonCode_Horizon_È.Ý)).Â("stairsRedSandStone"));
        HorizonCode_Horizon_È(181, "double_stone_slab2", new BlockDoubleStoneSlabNew().Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stoneSlab2"));
        HorizonCode_Horizon_È(182, "stone_slab2", new BlockHalfStoneSlabNew().Ý(2.0f).Â(10.0f).HorizonCode_Horizon_È(Block.à).Â("stoneSlab2"));
        HorizonCode_Horizon_È(183, "spruce_fence_gate", new BlockFenceGate().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("spruceFenceGate"));
        HorizonCode_Horizon_È(184, "birch_fence_gate", new BlockFenceGate().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("birchFenceGate"));
        HorizonCode_Horizon_È(185, "jungle_fence_gate", new BlockFenceGate().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("jungleFenceGate"));
        HorizonCode_Horizon_È(186, "dark_oak_fence_gate", new BlockFenceGate().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("darkOakFenceGate"));
        HorizonCode_Horizon_È(187, "acacia_fence_gate", new BlockFenceGate().Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("acaciaFenceGate"));
        HorizonCode_Horizon_È(188, "spruce_fence", new BlockFence(Material.Ø­áŒŠá).Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("spruceFence"));
        HorizonCode_Horizon_È(189, "birch_fence", new BlockFence(Material.Ø­áŒŠá).Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("birchFence"));
        HorizonCode_Horizon_È(190, "jungle_fence", new BlockFence(Material.Ø­áŒŠá).Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("jungleFence"));
        HorizonCode_Horizon_È(191, "dark_oak_fence", new BlockFence(Material.Ø­áŒŠá).Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("darkOakFence"));
        HorizonCode_Horizon_È(192, "acacia_fence", new BlockFence(Material.Ø­áŒŠá).Ý(2.0f).Â(5.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("acaciaFence"));
        HorizonCode_Horizon_È(193, "spruce_door", new BlockDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("doorSpruce").ÇŽÉ());
        HorizonCode_Horizon_È(194, "birch_door", new BlockDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("doorBirch").ÇŽÉ());
        HorizonCode_Horizon_È(195, "jungle_door", new BlockDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("doorJungle").ÇŽÉ());
        HorizonCode_Horizon_È(196, "acacia_door", new BlockDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("doorAcacia").ÇŽÉ());
        HorizonCode_Horizon_È(197, "dark_oak_door", new BlockDoor(Material.Ø­áŒŠá).Ý(3.0f).HorizonCode_Horizon_È(Block.Ø­áŒŠá).Â("doorDarkOak").ÇŽÉ());
        Block.HorizonCode_Horizon_È.Â();
        for (final Block var15 : Block.HorizonCode_Horizon_È) {
            if (var15.É == Material.HorizonCode_Horizon_È) {
                var15.Ø­à = false;
            }
            else {
                boolean var16 = false;
                final boolean var17 = var15 instanceof BlockStairs;
                final boolean var18 = var15 instanceof BlockSlab;
                final boolean var19 = var15 == var7;
                final boolean var20 = var15.ˆà;
                final boolean var21 = var15.µà == 0;
                if (var17 || var18 || var19 || var20 || var21) {
                    var16 = true;
                }
                var15.Ø­à = var16;
            }
        }
        for (final Block var15 : Block.HorizonCode_Horizon_È) {
            for (final IBlockState var23 : var15.ŠÂµà().HorizonCode_Horizon_È()) {
                final int var24 = Block.HorizonCode_Horizon_È.Ø­áŒŠá(var15) << 4 | var15.Ý(var23);
                Block.Â.HorizonCode_Horizon_È(var23, var24);
            }
        }
    }
    
    private static void HorizonCode_Horizon_È(final int id, final ResourceLocation_1975012498 textualID, final Block block_) {
        Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È(id, textualID, block_);
    }
    
    private static void HorizonCode_Horizon_È(final int id, final String textualID, final Block block_) {
        HorizonCode_Horizon_È(id, new ResourceLocation_1975012498(textualID), block_);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("NONE", 0, "NONE", 0), 
        Â("XZ", 1, "XZ", 1), 
        Ý("XYZ", 2, "XYZ", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002132";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45733_1_, final int p_i45733_2_) {
        }
    }
    
    public static class Â
    {
        public final String HorizonCode_Horizon_È;
        public final float Â;
        public final float Ý;
        private static final String Ø­áŒŠá = "CL_00000203";
        
        public Â(final String name, final float volume, final float frequency) {
            this.HorizonCode_Horizon_È = name;
            this.Â = volume;
            this.Ý = frequency;
        }
        
        public float Ø­áŒŠá() {
            return this.Â;
        }
        
        public float Âµá€() {
            return this.Ý;
        }
        
        public String HorizonCode_Horizon_È() {
            return "dig." + this.HorizonCode_Horizon_È;
        }
        
        public String Ý() {
            return "step." + this.HorizonCode_Horizon_È;
        }
        
        public String Â() {
            return this.HorizonCode_Horizon_È();
        }
    }
}
