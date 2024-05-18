package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Iterator;

public class EntityFallingBlock extends Entity
{
    private IBlockState Ø­áŒŠá;
    public int HorizonCode_Horizon_È;
    public boolean Â;
    private boolean Âµá€;
    private boolean Ó;
    private int à;
    private float Ø;
    public NBTTagCompound Ý;
    private static final String áŒŠÆ = "CL_00001668";
    
    public EntityFallingBlock(final World worldIn) {
        super(worldIn);
        this.Â = true;
        this.à = 40;
        this.Ø = 2.0f;
    }
    
    public EntityFallingBlock(final World worldIn, final double p_i45848_2_, final double p_i45848_4_, final double p_i45848_6_, final IBlockState p_i45848_8_) {
        super(worldIn);
        this.Â = true;
        this.à = 40;
        this.Ø = 2.0f;
        this.Ø­áŒŠá = p_i45848_8_;
        this.Ø­à = true;
        this.HorizonCode_Horizon_È(0.98f, 0.98f);
        this.Ý(p_i45848_2_, p_i45848_4_, p_i45848_6_);
        this.ÇŽÉ = 0.0;
        this.ˆá = 0.0;
        this.ÇŽÕ = 0.0;
        this.áŒŠà = p_i45848_2_;
        this.ŠÄ = p_i45848_4_;
        this.Ñ¢á = p_i45848_6_;
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    public boolean Ô() {
        return !this.ˆáŠ;
    }
    
    @Override
    public void á() {
        final Block var1 = this.Ø­áŒŠá.Ý();
        if (var1.Ó() == Material.HorizonCode_Horizon_È) {
            this.á€();
        }
        else {
            this.áŒŠà = this.ŒÏ;
            this.ŠÄ = this.Çªà¢;
            this.Ñ¢á = this.Ê;
            if (this.HorizonCode_Horizon_È++ == 0) {
                final BlockPos var2 = new BlockPos(this);
                if (this.Ï­Ðƒà.Â(var2).Ý() == var1) {
                    this.Ï­Ðƒà.Ø(var2);
                }
                else if (!this.Ï­Ðƒà.ŠÄ) {
                    this.á€();
                    return;
                }
            }
            this.ˆá -= 0.03999999910593033;
            this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
            this.ÇŽÉ *= 0.9800000190734863;
            this.ˆá *= 0.9800000190734863;
            this.ÇŽÕ *= 0.9800000190734863;
            if (!this.Ï­Ðƒà.ŠÄ) {
                final BlockPos var2 = new BlockPos(this);
                if (this.ŠÂµà) {
                    this.ÇŽÉ *= 0.699999988079071;
                    this.ÇŽÕ *= 0.699999988079071;
                    this.ˆá *= -0.5;
                    if (this.Ï­Ðƒà.Â(var2).Ý() != Blocks.¥à) {
                        this.á€();
                        if (!this.Âµá€ && this.Ï­Ðƒà.HorizonCode_Horizon_È(var1, var2, true, EnumFacing.Â, null, null) && !BlockFalling.áˆºÑ¢Õ(this.Ï­Ðƒà, var2.Âµá€()) && this.Ï­Ðƒà.HorizonCode_Horizon_È(var2, this.Ø­áŒŠá, 3)) {
                            if (var1 instanceof BlockFalling) {
                                ((BlockFalling)var1).áŒŠÆ(this.Ï­Ðƒà, var2);
                            }
                            if (this.Ý != null && var1 instanceof ITileEntityProvider) {
                                final TileEntity var3 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var2);
                                if (var3 != null) {
                                    final NBTTagCompound var4 = new NBTTagCompound();
                                    var3.Â(var4);
                                    for (final String var6 : this.Ý.Âµá€()) {
                                        final NBTBase var7 = this.Ý.HorizonCode_Horizon_È(var6);
                                        if (!var6.equals("x") && !var6.equals("y") && !var6.equals("z")) {
                                            var4.HorizonCode_Horizon_È(var6, var7.Â());
                                        }
                                    }
                                    var3.HorizonCode_Horizon_È(var4);
                                    var3.ŠÄ();
                                }
                            }
                        }
                        else if (this.Â && !this.Âµá€ && this.Ï­Ðƒà.Çªà¢().Â("doTileDrops")) {
                            this.HorizonCode_Horizon_È(new ItemStack(var1, 1, var1.Ø­áŒŠá(this.Ø­áŒŠá)), 0.0f);
                        }
                    }
                }
                else if ((this.HorizonCode_Horizon_È > 100 && !this.Ï­Ðƒà.ŠÄ && (var2.Â() < 1 || var2.Â() > 256)) || this.HorizonCode_Horizon_È > 600) {
                    if (this.Â && this.Ï­Ðƒà.Çªà¢().Â("doTileDrops")) {
                        this.HorizonCode_Horizon_È(new ItemStack(var1, 1, var1.Ø­áŒŠá(this.Ø­áŒŠá)), 0.0f);
                    }
                    this.á€();
                }
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        final Block var3 = this.Ø­áŒŠá.Ý();
        if (this.Ó) {
            final int var4 = MathHelper.Ó(distance - 1.0f);
            if (var4 > 0) {
                final ArrayList var5 = Lists.newArrayList((Iterable)this.Ï­Ðƒà.Â(this, this.£É()));
                final boolean var6 = var3 == Blocks.ÇªÅ;
                final DamageSource var7 = var6 ? DamageSource.£á : DamageSource.Å;
                for (final Entity var9 : var5) {
                    var9.HorizonCode_Horizon_È(var7, Math.min(MathHelper.Ø­áŒŠá(var4 * this.Ø), this.à));
                }
                if (var6 && this.ˆáƒ.nextFloat() < 0.05000000074505806 + var4 * 0.05) {
                    int var10 = (int)this.Ø­áŒŠá.HorizonCode_Horizon_È(BlockAnvil.à¢);
                    if (++var10 > 2) {
                        this.Âµá€ = true;
                    }
                    else {
                        this.Ø­áŒŠá = this.Ø­áŒŠá.HorizonCode_Horizon_È(BlockAnvil.à¢, var10);
                    }
                }
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        final Block var2 = (this.Ø­áŒŠá != null) ? this.Ø­áŒŠá.Ý() : Blocks.Â;
        final ResourceLocation_1975012498 var3 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(var2);
        tagCompound.HorizonCode_Horizon_È("Block", (var3 == null) ? "" : var3.toString());
        tagCompound.HorizonCode_Horizon_È("Data", (byte)var2.Ý(this.Ø­áŒŠá));
        tagCompound.HorizonCode_Horizon_È("Time", (byte)this.HorizonCode_Horizon_È);
        tagCompound.HorizonCode_Horizon_È("DropItem", this.Â);
        tagCompound.HorizonCode_Horizon_È("HurtEntities", this.Ó);
        tagCompound.HorizonCode_Horizon_È("FallHurtAmount", this.Ø);
        tagCompound.HorizonCode_Horizon_È("FallHurtMax", this.à);
        if (this.Ý != null) {
            tagCompound.HorizonCode_Horizon_È("TileEntityData", this.Ý);
        }
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        final int var2 = tagCompund.Ø­áŒŠá("Data") & 0xFF;
        if (tagCompund.Â("Block", 8)) {
            this.Ø­áŒŠá = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("Block")).Ý(var2);
        }
        else if (tagCompund.Â("TileID", 99)) {
            this.Ø­áŒŠá = Block.HorizonCode_Horizon_È(tagCompund.Ó("TileID")).Ý(var2);
        }
        else {
            this.Ø­áŒŠá = Block.HorizonCode_Horizon_È(tagCompund.Ø­áŒŠá("Tile") & 0xFF).Ý(var2);
        }
        this.HorizonCode_Horizon_È = (tagCompund.Ø­áŒŠá("Time") & 0xFF);
        final Block var3 = this.Ø­áŒŠá.Ý();
        if (tagCompund.Â("HurtEntities", 99)) {
            this.Ó = tagCompund.£á("HurtEntities");
            this.Ø = tagCompund.Ø("FallHurtAmount");
            this.à = tagCompund.Ó("FallHurtMax");
        }
        else if (var3 == Blocks.ÇªÅ) {
            this.Ó = true;
        }
        if (tagCompund.Â("DropItem", 99)) {
            this.Â = tagCompund.£á("DropItem");
        }
        if (tagCompund.Â("TileEntityData", 10)) {
            this.Ý = tagCompund.ˆÏ­("TileEntityData");
        }
        if (var3 == null || var3.Ó() == Material.HorizonCode_Horizon_È) {
            this.Ø­áŒŠá = Blocks.£á.¥à();
        }
    }
    
    public World à() {
        return this.Ï­Ðƒà;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_145806_1_) {
        this.Ó = p_145806_1_;
    }
    
    @Override
    public boolean ÇªØ­() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final CrashReportCategory category) {
        super.HorizonCode_Horizon_È(category);
        if (this.Ø­áŒŠá != null) {
            final Block var2 = this.Ø­áŒŠá.Ý();
            category.HorizonCode_Horizon_È("Immitating block ID", Block.HorizonCode_Horizon_È(var2));
            category.HorizonCode_Horizon_È("Immitating block data", var2.Ý(this.Ø­áŒŠá));
        }
    }
    
    public IBlockState Ø() {
        return this.Ø­áŒŠá;
    }
}
