package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Iterator;

public abstract class EntityMinecart extends Entity implements IWorldNameable
{
    private boolean HorizonCode_Horizon_È;
    private String Â;
    private static final int[][][] Ý;
    private int Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    private double Ø;
    private double áŒŠÆ;
    private double áˆºÑ¢Õ;
    private double ÂµÈ;
    private double á;
    private static final String ˆÏ­ = "CL_00001670";
    
    static {
        Ý = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
    }
    
    public EntityMinecart(final World worldIn) {
        super(worldIn);
        this.Ø­à = true;
        this.HorizonCode_Horizon_È(0.98f, 0.7f);
    }
    
    public static EntityMinecart HorizonCode_Horizon_È(final World worldIn, final double p_180458_1_, final double p_180458_3_, final double p_180458_5_, final HorizonCode_Horizon_È p_180458_7_) {
        switch (Â.HorizonCode_Horizon_È[p_180458_7_.ordinal()]) {
            case 1: {
                return new EntityMinecartChest(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
            case 2: {
                return new EntityMinecartFurnace(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
            case 3: {
                return new EntityMinecartTNT(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
            case 4: {
                return new EntityMinecartMobSpawner(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
            case 5: {
                return new EntityMinecartHopper(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
            case 6: {
                return new EntityMinecartCommandBlock(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
            default: {
                return new EntityMinecartEmpty(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
            }
        }
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(17, new Integer(0));
        this.£Ó.HorizonCode_Horizon_È(18, new Integer(1));
        this.£Ó.HorizonCode_Horizon_È(19, new Float(0.0f));
        this.£Ó.HorizonCode_Horizon_È(20, new Integer(0));
        this.£Ó.HorizonCode_Horizon_È(21, new Integer(6));
        this.£Ó.HorizonCode_Horizon_È(22, (Object)(byte)0);
    }
    
    @Override
    public AxisAlignedBB à(final Entity entityIn) {
        return entityIn.£à() ? entityIn.£É() : null;
    }
    
    @Override
    public AxisAlignedBB t_() {
        return null;
    }
    
    @Override
    public boolean £à() {
        return true;
    }
    
    public EntityMinecart(final World worldIn, final double p_i1713_2_, final double p_i1713_4_, final double p_i1713_6_) {
        this(worldIn);
        this.Ý(p_i1713_2_, p_i1713_4_, p_i1713_6_);
        this.ÇŽÉ = 0.0;
        this.ˆá = 0.0;
        this.ÇŽÕ = 0.0;
        this.áŒŠà = p_i1713_2_;
        this.ŠÄ = p_i1713_4_;
        this.Ñ¢á = p_i1713_6_;
    }
    
    @Override
    public double £Â() {
        return this.£ÂµÄ * 0.5 - 0.20000000298023224;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.Ï­Ðƒà.ŠÄ || this.ˆáŠ) {
            return true;
        }
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        this.Ý(-this.µÕ());
        this.Â(10);
        this.Ï();
        this.Ý(this.¥Æ() + amount * 10.0f);
        final boolean var3 = source.áˆºÑ¢Õ() instanceof EntityPlayer && ((EntityPlayer)source.áˆºÑ¢Õ()).áˆºáˆºáŠ.Ø­áŒŠá;
        if (var3 || this.¥Æ() > 40.0f) {
            if (this.µÕ != null) {
                this.µÕ.HorizonCode_Horizon_È((Entity)null);
            }
            if (var3 && !this.j_()) {
                this.á€();
            }
            else {
                this.Â(source);
            }
        }
        return true;
    }
    
    public void Â(final DamageSource p_94095_1_) {
        this.á€();
        final ItemStack var2 = new ItemStack(Items.ÇªÔ, 1);
        if (this.Â != null) {
            var2.HorizonCode_Horizon_È(this.Â);
        }
        this.HorizonCode_Horizon_È(var2, 0.0f);
    }
    
    @Override
    public void Œà() {
        this.Ý(-this.µÕ());
        this.Â(10);
        this.Ý(this.¥Æ() + this.¥Æ() * 10.0f);
    }
    
    @Override
    public boolean Ô() {
        return !this.ˆáŠ;
    }
    
    @Override
    public void á€() {
        super.á€();
    }
    
    @Override
    public void á() {
        if (this.Ø­à() > 0) {
            this.Â(this.Ø­à() - 1);
        }
        if (this.¥Æ() > 0.0f) {
            this.Ý(this.¥Æ() - 1.0f);
        }
        if (this.Çªà¢ < -64.0) {
            this.Âµà();
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà instanceof WorldServer) {
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("portal");
            final MinecraftServer var1 = ((WorldServer)this.Ï­Ðƒà).áˆºáˆºÈ();
            final int var2 = this.à¢();
            if (this.ˆÓ) {
                if (var1.ŠÄ()) {
                    if (this.Æ == null && this.¥Ä++ >= var2) {
                        this.¥Ä = var2;
                        this.áŒŠáŠ = this.Ï­Ô();
                        byte var3;
                        if (this.Ï­Ðƒà.£à.µà() == -1) {
                            var3 = 0;
                        }
                        else {
                            var3 = -1;
                        }
                        this.áŒŠÆ(var3);
                    }
                    this.ˆÓ = false;
                }
            }
            else {
                if (this.¥Ä > 0) {
                    this.¥Ä -= 4;
                }
                if (this.¥Ä < 0) {
                    this.¥Ä = 0;
                }
            }
            if (this.áŒŠáŠ > 0) {
                --this.áŒŠáŠ;
            }
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
        }
        if (this.Ï­Ðƒà.ŠÄ) {
            if (this.Ø­áŒŠá > 0) {
                final double var4 = this.ŒÏ + (this.Âµá€ - this.ŒÏ) / this.Ø­áŒŠá;
                final double var5 = this.Çªà¢ + (this.Ó - this.Çªà¢) / this.Ø­áŒŠá;
                final double var6 = this.Ê + (this.à - this.Ê) / this.Ø­áŒŠá;
                final double var7 = MathHelper.à(this.Ø - this.É);
                this.É += (float)(var7 / this.Ø­áŒŠá);
                this.áƒ += (float)((this.áŒŠÆ - this.áƒ) / this.Ø­áŒŠá);
                --this.Ø­áŒŠá;
                this.Ý(var4, var5, var6);
                this.Â(this.É, this.áƒ);
            }
            else {
                this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
                this.Â(this.É, this.áƒ);
            }
        }
        else {
            this.áŒŠà = this.ŒÏ;
            this.ŠÄ = this.Çªà¢;
            this.Ñ¢á = this.Ê;
            this.ˆá -= 0.03999999910593033;
            final int var8 = MathHelper.Ý(this.ŒÏ);
            int var2 = MathHelper.Ý(this.Çªà¢);
            final int var9 = MathHelper.Ý(this.Ê);
            if (BlockRailBase.áŒŠÆ(this.Ï­Ðƒà, new BlockPos(var8, var2 - 1, var9))) {
                --var2;
            }
            final BlockPos var10 = new BlockPos(var8, var2, var9);
            final IBlockState var11 = this.Ï­Ðƒà.Â(var10);
            if (BlockRailBase.áŒŠÆ(var11)) {
                this.HorizonCode_Horizon_È(var10, var11);
                if (var11.Ý() == Blocks.Ø­à¢) {
                    this.HorizonCode_Horizon_È(var8, var2, var9, (boolean)var11.HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà));
                }
            }
            else {
                this.µà();
            }
            this.È();
            this.áƒ = 0.0f;
            final double var12 = this.áŒŠà - this.ŒÏ;
            final double var13 = this.Ñ¢á - this.Ê;
            if (var12 * var12 + var13 * var13 > 0.001) {
                this.É = (float)(Math.atan2(var13, var12) * 180.0 / 3.141592653589793);
                if (this.HorizonCode_Horizon_È) {
                    this.É += 180.0f;
                }
            }
            final double var14 = MathHelper.à(this.É - this.á€);
            if (var14 < -170.0 || var14 >= 170.0) {
                this.É += 180.0f;
                this.HorizonCode_Horizon_È = !this.HorizonCode_Horizon_È;
            }
            this.Â(this.É, this.áƒ);
            for (final Entity var16 : this.Ï­Ðƒà.Â(this, this.£É().Â(0.20000000298023224, 0.0, 0.20000000298023224))) {
                if (var16 != this.µÕ && var16.£à() && var16 instanceof EntityMinecart) {
                    var16.Ó(this);
                }
            }
            if (this.µÕ != null && this.µÕ.ˆáŠ) {
                if (this.µÕ.Æ == this) {
                    this.µÕ.Æ = null;
                }
                this.µÕ = null;
            }
            this.Ø­Âµ();
        }
    }
    
    protected double ˆÏ­() {
        return 0.4;
    }
    
    public void HorizonCode_Horizon_È(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
    }
    
    protected void µà() {
        final double var1 = this.ˆÏ­();
        this.ÇŽÉ = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ, -var1, var1);
        this.ÇŽÕ = MathHelper.HorizonCode_Horizon_È(this.ÇŽÕ, -var1, var1);
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.5;
            this.ˆá *= 0.5;
            this.ÇŽÕ *= 0.5;
        }
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        if (!this.ŠÂµà) {
            this.ÇŽÉ *= 0.949999988079071;
            this.ˆá *= 0.949999988079071;
            this.ÇŽÕ *= 0.949999988079071;
        }
    }
    
    protected void HorizonCode_Horizon_È(final BlockPos p_180460_1_, final IBlockState p_180460_2_) {
        this.Ï­à = 0.0f;
        final Vec3 var3 = this.ÂµÈ(this.ŒÏ, this.Çªà¢, this.Ê);
        this.Çªà¢ = p_180460_1_.Â();
        boolean var4 = false;
        boolean var5 = false;
        final BlockRailBase var6 = (BlockRailBase)p_180460_2_.Ý();
        if (var6 == Blocks.ÇŽÉ) {
            var4 = (boolean)p_180460_2_.HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà);
            var5 = !var4;
        }
        final double var7 = 0.0078125;
        final BlockRailBase.HorizonCode_Horizon_È var8 = (BlockRailBase.HorizonCode_Horizon_È)p_180460_2_.HorizonCode_Horizon_È(var6.È());
        switch (EntityMinecart.Â.Â[var8.ordinal()]) {
            case 1: {
                this.ÇŽÉ -= 0.0078125;
                ++this.Çªà¢;
                break;
            }
            case 2: {
                this.ÇŽÉ += 0.0078125;
                ++this.Çªà¢;
                break;
            }
            case 3: {
                this.ÇŽÕ += 0.0078125;
                ++this.Çªà¢;
                break;
            }
            case 4: {
                this.ÇŽÕ -= 0.0078125;
                ++this.Çªà¢;
                break;
            }
        }
        final int[][] var9 = EntityMinecart.Ý[var8.Â()];
        double var10 = var9[1][0] - var9[0][0];
        double var11 = var9[1][2] - var9[0][2];
        final double var12 = Math.sqrt(var10 * var10 + var11 * var11);
        final double var13 = this.ÇŽÉ * var10 + this.ÇŽÕ * var11;
        if (var13 < 0.0) {
            var10 = -var10;
            var11 = -var11;
        }
        double var14 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        if (var14 > 2.0) {
            var14 = 2.0;
        }
        this.ÇŽÉ = var14 * var10 / var12;
        this.ÇŽÕ = var14 * var11 / var12;
        if (this.µÕ instanceof EntityLivingBase) {
            final double var15 = ((EntityLivingBase)this.µÕ).Ï­áˆºÓ;
            if (var15 > 0.0) {
                final double var16 = -Math.sin(this.µÕ.É * 3.1415927f / 180.0f);
                final double var17 = Math.cos(this.µÕ.É * 3.1415927f / 180.0f);
                final double var18 = this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ;
                if (var18 < 0.01) {
                    this.ÇŽÉ += var16 * 0.1;
                    this.ÇŽÕ += var17 * 0.1;
                    var5 = false;
                }
            }
        }
        if (var5) {
            final double var15 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            if (var15 < 0.03) {
                this.ÇŽÉ *= 0.0;
                this.ˆá *= 0.0;
                this.ÇŽÕ *= 0.0;
            }
            else {
                this.ÇŽÉ *= 0.5;
                this.ˆá *= 0.0;
                this.ÇŽÕ *= 0.5;
            }
        }
        double var15 = 0.0;
        final double var16 = p_180460_1_.HorizonCode_Horizon_È() + 0.5 + var9[0][0] * 0.5;
        final double var17 = p_180460_1_.Ý() + 0.5 + var9[0][2] * 0.5;
        final double var18 = p_180460_1_.HorizonCode_Horizon_È() + 0.5 + var9[1][0] * 0.5;
        final double var19 = p_180460_1_.Ý() + 0.5 + var9[1][2] * 0.5;
        var10 = var18 - var16;
        var11 = var19 - var17;
        if (var10 == 0.0) {
            this.ŒÏ = p_180460_1_.HorizonCode_Horizon_È() + 0.5;
            var15 = this.Ê - p_180460_1_.Ý();
        }
        else if (var11 == 0.0) {
            this.Ê = p_180460_1_.Ý() + 0.5;
            var15 = this.ŒÏ - p_180460_1_.HorizonCode_Horizon_È();
        }
        else {
            final double var20 = this.ŒÏ - var16;
            final double var21 = this.Ê - var17;
            var15 = (var20 * var10 + var21 * var11) * 2.0;
        }
        this.ŒÏ = var16 + var10 * var15;
        this.Ê = var17 + var11 * var15;
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        double var20 = this.ÇŽÉ;
        double var21 = this.ÇŽÕ;
        if (this.µÕ != null) {
            var20 *= 0.75;
            var21 *= 0.75;
        }
        final double var22 = this.ˆÏ­();
        var20 = MathHelper.HorizonCode_Horizon_È(var20, -var22, var22);
        var21 = MathHelper.HorizonCode_Horizon_È(var21, -var22, var22);
        this.HorizonCode_Horizon_È(var20, 0.0, var21);
        if (var9[0][1] != 0 && MathHelper.Ý(this.ŒÏ) - p_180460_1_.HorizonCode_Horizon_È() == var9[0][0] && MathHelper.Ý(this.Ê) - p_180460_1_.Ý() == var9[0][2]) {
            this.Ý(this.ŒÏ, this.Çªà¢ + var9[0][1], this.Ê);
        }
        else if (var9[1][1] != 0 && MathHelper.Ý(this.ŒÏ) - p_180460_1_.HorizonCode_Horizon_È() == var9[1][0] && MathHelper.Ý(this.Ê) - p_180460_1_.Ý() == var9[1][2]) {
            this.Ý(this.ŒÏ, this.Çªà¢ + var9[1][1], this.Ê);
        }
        this.ˆà();
        final Vec3 var23 = this.ÂµÈ(this.ŒÏ, this.Çªà¢, this.Ê);
        if (var23 != null && var3 != null) {
            final double var24 = (var3.Â - var23.Â) * 0.05;
            var14 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            if (var14 > 0.0) {
                this.ÇŽÉ = this.ÇŽÉ / var14 * (var14 + var24);
                this.ÇŽÕ = this.ÇŽÕ / var14 * (var14 + var24);
            }
            this.Ý(this.ŒÏ, var23.Â, this.Ê);
        }
        final int var25 = MathHelper.Ý(this.ŒÏ);
        final int var26 = MathHelper.Ý(this.Ê);
        if (var25 != p_180460_1_.HorizonCode_Horizon_È() || var26 != p_180460_1_.Ý()) {
            var14 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            this.ÇŽÉ = var14 * (var25 - p_180460_1_.HorizonCode_Horizon_È());
            this.ÇŽÕ = var14 * (var26 - p_180460_1_.Ý());
        }
        if (var4) {
            final double var27 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            if (var27 > 0.01) {
                final double var28 = 0.06;
                this.ÇŽÉ += this.ÇŽÉ / var27 * var28;
                this.ÇŽÕ += this.ÇŽÕ / var27 * var28;
            }
            else if (var8 == BlockRailBase.HorizonCode_Horizon_È.Â) {
                if (this.Ï­Ðƒà.Â(p_180460_1_.Ø()).Ý().Ø()) {
                    this.ÇŽÉ = 0.02;
                }
                else if (this.Ï­Ðƒà.Â(p_180460_1_.áŒŠÆ()).Ý().Ø()) {
                    this.ÇŽÉ = -0.02;
                }
            }
            else if (var8 == BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                if (this.Ï­Ðƒà.Â(p_180460_1_.Ó()).Ý().Ø()) {
                    this.ÇŽÕ = 0.02;
                }
                else if (this.Ï­Ðƒà.Â(p_180460_1_.à()).Ý().Ø()) {
                    this.ÇŽÕ = -0.02;
                }
            }
        }
    }
    
    protected void ˆà() {
        if (this.µÕ != null) {
            this.ÇŽÉ *= 0.996999979019165;
            this.ˆá *= 0.0;
            this.ÇŽÕ *= 0.996999979019165;
        }
        else {
            this.ÇŽÉ *= 0.9599999785423279;
            this.ˆá *= 0.0;
            this.ÇŽÕ *= 0.9599999785423279;
        }
    }
    
    @Override
    public void Ý(final double x, final double y, final double z) {
        this.ŒÏ = x;
        this.Çªà¢ = y;
        this.Ê = z;
        final float var7 = this.áŒŠ / 2.0f;
        final float var8 = this.£ÂµÄ;
        this.HorizonCode_Horizon_È(new AxisAlignedBB(x - var7, y, z - var7, x + var7, y + var8, z + var7));
    }
    
    public Vec3 HorizonCode_Horizon_È(double p_70495_1_, double p_70495_3_, double p_70495_5_, final double p_70495_7_) {
        final int var9 = MathHelper.Ý(p_70495_1_);
        int var10 = MathHelper.Ý(p_70495_3_);
        final int var11 = MathHelper.Ý(p_70495_5_);
        if (BlockRailBase.áŒŠÆ(this.Ï­Ðƒà, new BlockPos(var9, var10 - 1, var11))) {
            --var10;
        }
        final IBlockState var12 = this.Ï­Ðƒà.Â(new BlockPos(var9, var10, var11));
        if (BlockRailBase.áŒŠÆ(var12)) {
            final BlockRailBase.HorizonCode_Horizon_È var13 = (BlockRailBase.HorizonCode_Horizon_È)var12.HorizonCode_Horizon_È(((BlockRailBase)var12.Ý()).È());
            p_70495_3_ = var10;
            if (var13.Ý()) {
                p_70495_3_ = var10 + 1;
            }
            final int[][] var14 = EntityMinecart.Ý[var13.Â()];
            double var15 = var14[1][0] - var14[0][0];
            double var16 = var14[1][2] - var14[0][2];
            final double var17 = Math.sqrt(var15 * var15 + var16 * var16);
            var15 /= var17;
            var16 /= var17;
            p_70495_1_ += var15 * p_70495_7_;
            p_70495_5_ += var16 * p_70495_7_;
            if (var14[0][1] != 0 && MathHelper.Ý(p_70495_1_) - var9 == var14[0][0] && MathHelper.Ý(p_70495_5_) - var11 == var14[0][2]) {
                p_70495_3_ += var14[0][1];
            }
            else if (var14[1][1] != 0 && MathHelper.Ý(p_70495_1_) - var9 == var14[1][0] && MathHelper.Ý(p_70495_5_) - var11 == var14[1][2]) {
                p_70495_3_ += var14[1][1];
            }
            return this.ÂµÈ(p_70495_1_, p_70495_3_, p_70495_5_);
        }
        return null;
    }
    
    public Vec3 ÂµÈ(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
        final int var7 = MathHelper.Ý(p_70489_1_);
        int var8 = MathHelper.Ý(p_70489_3_);
        final int var9 = MathHelper.Ý(p_70489_5_);
        if (BlockRailBase.áŒŠÆ(this.Ï­Ðƒà, new BlockPos(var7, var8 - 1, var9))) {
            --var8;
        }
        final IBlockState var10 = this.Ï­Ðƒà.Â(new BlockPos(var7, var8, var9));
        if (BlockRailBase.áŒŠÆ(var10)) {
            final BlockRailBase.HorizonCode_Horizon_È var11 = (BlockRailBase.HorizonCode_Horizon_È)var10.HorizonCode_Horizon_È(((BlockRailBase)var10.Ý()).È());
            final int[][] var12 = EntityMinecart.Ý[var11.Â()];
            double var13 = 0.0;
            final double var14 = var7 + 0.5 + var12[0][0] * 0.5;
            final double var15 = var8 + 0.0625 + var12[0][1] * 0.5;
            final double var16 = var9 + 0.5 + var12[0][2] * 0.5;
            final double var17 = var7 + 0.5 + var12[1][0] * 0.5;
            final double var18 = var8 + 0.0625 + var12[1][1] * 0.5;
            final double var19 = var9 + 0.5 + var12[1][2] * 0.5;
            final double var20 = var17 - var14;
            final double var21 = (var18 - var15) * 2.0;
            final double var22 = var19 - var16;
            if (var20 == 0.0) {
                p_70489_1_ = var7 + 0.5;
                var13 = p_70489_5_ - var9;
            }
            else if (var22 == 0.0) {
                p_70489_5_ = var9 + 0.5;
                var13 = p_70489_1_ - var7;
            }
            else {
                final double var23 = p_70489_1_ - var14;
                final double var24 = p_70489_5_ - var16;
                var13 = (var23 * var20 + var24 * var22) * 2.0;
            }
            p_70489_1_ = var14 + var20 * var13;
            p_70489_3_ = var15 + var21 * var13;
            p_70489_5_ = var16 + var22 * var13;
            if (var21 < 0.0) {
                ++p_70489_3_;
            }
            if (var21 > 0.0) {
                p_70489_3_ += 0.5;
            }
            return new Vec3(p_70489_1_, p_70489_3_, p_70489_5_);
        }
        return null;
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        if (tagCompund.£á("CustomDisplayTile")) {
            final int var2 = tagCompund.Ó("DisplayData");
            if (tagCompund.Â("DisplayTile", 8)) {
                final Block var3 = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("DisplayTile"));
                if (var3 == null) {
                    this.HorizonCode_Horizon_È(Blocks.Â.¥à());
                }
                else {
                    this.HorizonCode_Horizon_È(var3.Ý(var2));
                }
            }
            else {
                final Block var3 = Block.HorizonCode_Horizon_È(tagCompund.Ó("DisplayTile"));
                if (var3 == null) {
                    this.HorizonCode_Horizon_È(Blocks.Â.¥à());
                }
                else {
                    this.HorizonCode_Horizon_È(var3.Ý(var2));
                }
            }
            this.ÂµÈ(tagCompund.Ó("DisplayOffset"));
        }
        if (tagCompund.Â("CustomName", 8) && tagCompund.áˆºÑ¢Õ("CustomName").length() > 0) {
            this.Â = tagCompund.áˆºÑ¢Õ("CustomName");
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        if (this.áŒŠà()) {
            tagCompound.HorizonCode_Horizon_È("CustomDisplayTile", true);
            final IBlockState var2 = this.Æ();
            final ResourceLocation_1975012498 var3 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(var2.Ý());
            tagCompound.HorizonCode_Horizon_È("DisplayTile", (var3 == null) ? "" : var3.toString());
            tagCompound.HorizonCode_Horizon_È("DisplayData", var2.Ý().Ý(var2));
            tagCompound.HorizonCode_Horizon_È("DisplayOffset", this.Šáƒ());
        }
        if (this.Â != null && this.Â.length() > 0) {
            tagCompound.HorizonCode_Horizon_È("CustomName", this.Â);
        }
    }
    
    @Override
    public void Ó(final Entity entityIn) {
        if (!this.Ï­Ðƒà.ŠÄ && !entityIn.ÇªÓ && !this.ÇªÓ && entityIn != this.µÕ) {
            if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer) && !(entityIn instanceof EntityIronGolem) && this.à() == EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È && this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ > 0.01 && this.µÕ == null && entityIn.Æ == null) {
                entityIn.HorizonCode_Horizon_È(this);
            }
            double var2 = entityIn.ŒÏ - this.ŒÏ;
            double var3 = entityIn.Ê - this.Ê;
            double var4 = var2 * var2 + var3 * var3;
            if (var4 >= 9.999999747378752E-5) {
                var4 = MathHelper.HorizonCode_Horizon_È(var4);
                var2 /= var4;
                var3 /= var4;
                double var5 = 1.0 / var4;
                if (var5 > 1.0) {
                    var5 = 1.0;
                }
                var2 *= var5;
                var3 *= var5;
                var2 *= 0.10000000149011612;
                var3 *= 0.10000000149011612;
                var2 *= 1.0f - this.áˆºÏ;
                var3 *= 1.0f - this.áˆºÏ;
                var2 *= 0.5;
                var3 *= 0.5;
                if (entityIn instanceof EntityMinecart) {
                    final double var6 = entityIn.ŒÏ - this.ŒÏ;
                    final double var7 = entityIn.Ê - this.Ê;
                    final Vec3 var8 = new Vec3(var6, 0.0, var7).HorizonCode_Horizon_È();
                    final Vec3 var9 = new Vec3(MathHelper.Â(this.É * 3.1415927f / 180.0f), 0.0, MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f)).HorizonCode_Horizon_È();
                    final double var10 = Math.abs(var8.Â(var9));
                    if (var10 < 0.800000011920929) {
                        return;
                    }
                    double var11 = entityIn.ÇŽÉ + this.ÇŽÉ;
                    double var12 = entityIn.ÇŽÕ + this.ÇŽÕ;
                    if (((EntityMinecart)entityIn).à() == EntityMinecart.HorizonCode_Horizon_È.Ý && this.à() != EntityMinecart.HorizonCode_Horizon_È.Ý) {
                        this.ÇŽÉ *= 0.20000000298023224;
                        this.ÇŽÕ *= 0.20000000298023224;
                        this.à(entityIn.ÇŽÉ - var2, 0.0, entityIn.ÇŽÕ - var3);
                        entityIn.ÇŽÉ *= 0.949999988079071;
                        entityIn.ÇŽÕ *= 0.949999988079071;
                    }
                    else if (((EntityMinecart)entityIn).à() != EntityMinecart.HorizonCode_Horizon_È.Ý && this.à() == EntityMinecart.HorizonCode_Horizon_È.Ý) {
                        entityIn.ÇŽÉ *= 0.20000000298023224;
                        entityIn.ÇŽÕ *= 0.20000000298023224;
                        entityIn.à(this.ÇŽÉ + var2, 0.0, this.ÇŽÕ + var3);
                        this.ÇŽÉ *= 0.949999988079071;
                        this.ÇŽÕ *= 0.949999988079071;
                    }
                    else {
                        var11 /= 2.0;
                        var12 /= 2.0;
                        this.ÇŽÉ *= 0.20000000298023224;
                        this.ÇŽÕ *= 0.20000000298023224;
                        this.à(var11 - var2, 0.0, var12 - var3);
                        entityIn.ÇŽÉ *= 0.20000000298023224;
                        entityIn.ÇŽÕ *= 0.20000000298023224;
                        entityIn.à(var11 + var2, 0.0, var12 + var3);
                    }
                }
                else {
                    this.à(-var2, 0.0, -var3);
                    entityIn.à(var2 / 4.0, 0.0, var3 / 4.0);
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        this.Âµá€ = p_180426_1_;
        this.Ó = p_180426_3_;
        this.à = p_180426_5_;
        this.Ø = p_180426_7_;
        this.áŒŠÆ = p_180426_8_;
        this.Ø­áŒŠá = p_180426_9_ + 2;
        this.ÇŽÉ = this.áˆºÑ¢Õ;
        this.ˆá = this.ÂµÈ;
        this.ÇŽÕ = this.á;
    }
    
    @Override
    public void áŒŠÆ(final double x, final double y, final double z) {
        this.ÇŽÉ = x;
        this.áˆºÑ¢Õ = x;
        this.ˆá = y;
        this.ÂµÈ = y;
        this.ÇŽÕ = z;
        this.á = z;
    }
    
    public void Ý(final float p_70492_1_) {
        this.£Ó.Â(19, p_70492_1_);
    }
    
    public float ¥Æ() {
        return this.£Ó.Ø­áŒŠá(19);
    }
    
    public void Â(final int p_70497_1_) {
        this.£Ó.Â(17, p_70497_1_);
    }
    
    public int Ø­à() {
        return this.£Ó.Ý(17);
    }
    
    public void Ý(final int p_70494_1_) {
        this.£Ó.Â(18, p_70494_1_);
    }
    
    public int µÕ() {
        return this.£Ó.Ý(18);
    }
    
    public abstract HorizonCode_Horizon_È à();
    
    public IBlockState Æ() {
        return this.áŒŠà() ? Block.Â(this.É().Ý(20)) : this.Ø();
    }
    
    public IBlockState Ø() {
        return Blocks.Â.¥à();
    }
    
    public int Šáƒ() {
        return this.áŒŠà() ? this.É().Ý(21) : this.Ï­Ðƒà();
    }
    
    public int Ï­Ðƒà() {
        return 6;
    }
    
    public void HorizonCode_Horizon_È(final IBlockState p_174899_1_) {
        this.É().Â(20, Block.HorizonCode_Horizon_È(p_174899_1_));
        this.HorizonCode_Horizon_È(true);
    }
    
    public void ÂµÈ(final int p_94086_1_) {
        this.É().Â(21, p_94086_1_);
        this.HorizonCode_Horizon_È(true);
    }
    
    public boolean áŒŠà() {
        return this.É().HorizonCode_Horizon_È(22) == 1;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_94096_1_) {
        this.É().Â(22, (byte)(byte)(p_94096_1_ ? 1 : 0));
    }
    
    @Override
    public void à(final String p_96094_1_) {
        this.Â = p_96094_1_;
    }
    
    @Override
    public String v_() {
        return (this.Â != null) ? this.Â : super.v_();
    }
    
    @Override
    public boolean j_() {
        return this.Â != null;
    }
    
    @Override
    public String Šà() {
        return this.Â;
    }
    
    @Override
    public IChatComponent Ý() {
        if (this.j_()) {
            final ChatComponentText var2 = new ChatComponentText(this.Â);
            var2.à().HorizonCode_Horizon_È(this.Ñ¢Ç());
            var2.à().HorizonCode_Horizon_È(this.£áŒŠá().toString());
            return var2;
        }
        final ChatComponentTranslation var3 = new ChatComponentTranslation(this.v_(), new Object[0]);
        var3.à().HorizonCode_Horizon_È(this.Ñ¢Ç());
        var3.à().HorizonCode_Horizon_È(this.£áŒŠá().toString());
        return var3;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("RIDEABLE", 0, "RIDEABLE", 0, 0, "MinecartRideable"), 
        Â("CHEST", 1, "CHEST", 1, 1, "MinecartChest"), 
        Ý("FURNACE", 2, "FURNACE", 2, 2, "MinecartFurnace"), 
        Ø­áŒŠá("TNT", 3, "TNT", 3, 3, "MinecartTNT"), 
        Âµá€("SPAWNER", 4, "SPAWNER", 4, 4, "MinecartSpawner"), 
        Ó("HOPPER", 5, "HOPPER", 5, 5, "MinecartHopper"), 
        à("COMMAND_BLOCK", 6, "COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
        
        private static final Map Ø;
        private final int áŒŠÆ;
        private final String áˆºÑ¢Õ;
        private static final HorizonCode_Horizon_È[] ÂµÈ;
        private static final String á = "CL_00002226";
        
        static {
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            Ø = Maps.newHashMap();
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø.put(var4.HorizonCode_Horizon_È(), var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45847_1_, final int p_i45847_2_, final int p_i45847_3_, final String p_i45847_4_) {
            this.áŒŠÆ = p_i45847_3_;
            this.áˆºÑ¢Õ = p_i45847_4_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.áŒŠÆ;
        }
        
        public String Â() {
            return this.áˆºÑ¢Õ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final int p_180038_0_) {
            final HorizonCode_Horizon_È var1 = HorizonCode_Horizon_È.Ø.get(p_180038_0_);
            return (var1 == null) ? HorizonCode_Horizon_È.HorizonCode_Horizon_È : var1;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00002227";
        
        static {
            Â = new int[BlockRailBase.HorizonCode_Horizon_È.values().length];
            try {
                EntityMinecart.Â.Â[BlockRailBase.HorizonCode_Horizon_È.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                EntityMinecart.Â.Â[BlockRailBase.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                EntityMinecart.Â.Â[BlockRailBase.HorizonCode_Horizon_È.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                EntityMinecart.Â.Â[BlockRailBase.HorizonCode_Horizon_È.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            HorizonCode_Horizon_È = new int[EntityMinecart.HorizonCode_Horizon_È.values().length];
            try {
                EntityMinecart.Â.HorizonCode_Horizon_È[EntityMinecart.HorizonCode_Horizon_È.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                EntityMinecart.Â.HorizonCode_Horizon_È[EntityMinecart.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                EntityMinecart.Â.HorizonCode_Horizon_È[EntityMinecart.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                EntityMinecart.Â.HorizonCode_Horizon_È[EntityMinecart.HorizonCode_Horizon_È.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                EntityMinecart.Â.HorizonCode_Horizon_È[EntityMinecart.HorizonCode_Horizon_È.Ó.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                EntityMinecart.Â.HorizonCode_Horizon_È[EntityMinecart.HorizonCode_Horizon_È.à.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
        }
    }
}
