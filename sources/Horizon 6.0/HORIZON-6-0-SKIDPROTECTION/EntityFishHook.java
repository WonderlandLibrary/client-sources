package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Arrays;
import java.util.List;

public class EntityFishHook extends Entity
{
    private static final List Ø­áŒŠá;
    private static final List Âµá€;
    private static final List Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private Block áˆºÑ¢Õ;
    private boolean ÂµÈ;
    public int HorizonCode_Horizon_È;
    public EntityPlayer Â;
    private int á;
    private int ˆÏ­;
    private int £á;
    private int Å;
    private int £à;
    private float µà;
    public Entity Ý;
    private int ÇŽá;
    private double Ñ¢à;
    private double ÇªØ­;
    private double £áŒŠá;
    private double áˆº;
    private double Šà;
    private double áŒŠá€;
    private double ¥Ï;
    private double ˆà¢;
    private static final String Ñ¢Ç = "CL_00001663";
    
    static {
        Ø­áŒŠá = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.áŒŠ), 10).HorizonCode_Horizon_È(0.9f), new WeightedRandomFishable(new ItemStack(Items.£áŒŠá), 10), new WeightedRandomFishable(new ItemStack(Items.ŠÕ), 10), new WeightedRandomFishable(new ItemStack(Items.µÂ), 10), new WeightedRandomFishable(new ItemStack(Items.ˆá), 5), new WeightedRandomFishable(new ItemStack(Items.ÂµÕ), 2).HorizonCode_Horizon_È(0.9f), new WeightedRandomFishable(new ItemStack(Items.ŠÄ), 10), new WeightedRandomFishable(new ItemStack(Items.áŒŠà), 5), new WeightedRandomFishable(new ItemStack(Items.áŒŠÔ, 10, EnumDyeColor.£à.Ý()), 1), new WeightedRandomFishable(new ItemStack(Blocks.ˆÂ), 10), new WeightedRandomFishable(new ItemStack(Items.ŠØ), 10));
        Âµá€ = Arrays.asList(new WeightedRandomFishable(new ItemStack(Blocks.Œá), 1), new WeightedRandomFishable(new ItemStack(Items.ŒÐƒà), 1), new WeightedRandomFishable(new ItemStack(Items.Û), 1), new WeightedRandomFishable(new ItemStack(Items.Ó), 1).HorizonCode_Horizon_È(0.25f).HorizonCode_Horizon_È(), new WeightedRandomFishable(new ItemStack(Items.ÂµÕ), 1).HorizonCode_Horizon_È(0.25f).HorizonCode_Horizon_È(), new WeightedRandomFishable(new ItemStack(Items.Ñ¢Ç), 1).HorizonCode_Horizon_È());
        Ó = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.Ñ¢Ó, 1, ItemFishFood.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È()), 60), new WeightedRandomFishable(new ItemStack(Items.Ñ¢Ó, 1, ItemFishFood.HorizonCode_Horizon_È.Â.HorizonCode_Horizon_È()), 25), new WeightedRandomFishable(new ItemStack(Items.Ñ¢Ó, 1, ItemFishFood.HorizonCode_Horizon_È.Ý.HorizonCode_Horizon_È()), 2), new WeightedRandomFishable(new ItemStack(Items.Ñ¢Ó, 1, ItemFishFood.HorizonCode_Horizon_È.Ø­áŒŠá.HorizonCode_Horizon_È()), 13));
    }
    
    public static List à() {
        return EntityFishHook.Ó;
    }
    
    public EntityFishHook(final World worldIn) {
        super(worldIn);
        this.à = -1;
        this.Ø = -1;
        this.áŒŠÆ = -1;
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.ÇªÂµÕ = true;
    }
    
    public EntityFishHook(final World worldIn, final double p_i1765_2_, final double p_i1765_4_, final double p_i1765_6_, final EntityPlayer p_i1765_8_) {
        this(worldIn);
        this.Ý(p_i1765_2_, p_i1765_4_, p_i1765_6_);
        this.ÇªÂµÕ = true;
        this.Â = p_i1765_8_;
        p_i1765_8_.µÏ = this;
    }
    
    public EntityFishHook(final World worldIn, final EntityPlayer p_i1766_2_) {
        super(worldIn);
        this.à = -1;
        this.Ø = -1;
        this.áŒŠÆ = -1;
        this.ÇªÂµÕ = true;
        this.Â = p_i1766_2_;
        (this.Â.µÏ = this).HorizonCode_Horizon_È(0.25f, 0.25f);
        this.Â(p_i1766_2_.ŒÏ, p_i1766_2_.Çªà¢ + p_i1766_2_.Ðƒáƒ(), p_i1766_2_.Ê, p_i1766_2_.É, p_i1766_2_.áƒ);
        this.ŒÏ -= MathHelper.Â(this.É / 180.0f * 3.1415927f) * 0.16f;
        this.Çªà¢ -= 0.10000000149011612;
        this.Ê -= MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * 0.16f;
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        final float var3 = 0.4f;
        this.ÇŽÉ = -MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f) * var3;
        this.ÇŽÕ = MathHelper.Â(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f) * var3;
        this.ˆá = -MathHelper.HorizonCode_Horizon_È(this.áƒ / 180.0f * 3.1415927f) * var3;
        this.Ý(this.ÇŽÉ, this.ˆá, this.ÇŽÕ, 1.5f, 1.0f);
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final double distance) {
        double var3 = this.£É().HorizonCode_Horizon_È() * 4.0;
        var3 *= 64.0;
        return distance < var3 * var3;
    }
    
    public void Ý(double p_146035_1_, double p_146035_3_, double p_146035_5_, final float p_146035_7_, final float p_146035_8_) {
        final float var9 = MathHelper.HorizonCode_Horizon_È(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
        p_146035_1_ /= var9;
        p_146035_3_ /= var9;
        p_146035_5_ /= var9;
        p_146035_1_ += this.ˆáƒ.nextGaussian() * 0.007499999832361937 * p_146035_8_;
        p_146035_3_ += this.ˆáƒ.nextGaussian() * 0.007499999832361937 * p_146035_8_;
        p_146035_5_ += this.ˆáƒ.nextGaussian() * 0.007499999832361937 * p_146035_8_;
        p_146035_1_ *= p_146035_7_;
        p_146035_3_ *= p_146035_7_;
        p_146035_5_ *= p_146035_7_;
        this.ÇŽÉ = p_146035_1_;
        this.ˆá = p_146035_3_;
        this.ÇŽÕ = p_146035_5_;
        final float var10 = MathHelper.HorizonCode_Horizon_È(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
        final float n = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0 / 3.141592653589793);
        this.É = n;
        this.á€ = n;
        final float n2 = (float)(Math.atan2(p_146035_3_, var10) * 180.0 / 3.141592653589793);
        this.áƒ = n2;
        this.Õ = n2;
        this.á = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        this.Ñ¢à = p_180426_1_;
        this.ÇªØ­ = p_180426_3_;
        this.£áŒŠá = p_180426_5_;
        this.áˆº = p_180426_7_;
        this.Šà = p_180426_8_;
        this.ÇŽá = p_180426_9_;
        this.ÇŽÉ = this.áŒŠá€;
        this.ˆá = this.¥Ï;
        this.ÇŽÕ = this.ˆà¢;
    }
    
    @Override
    public void áŒŠÆ(final double x, final double y, final double z) {
        this.ÇŽÉ = x;
        this.áŒŠá€ = x;
        this.ˆá = y;
        this.¥Ï = y;
        this.ÇŽÕ = z;
        this.ˆà¢ = z;
    }
    
    @Override
    public void á() {
        super.á();
        if (this.ÇŽá > 0) {
            final double var28 = this.ŒÏ + (this.Ñ¢à - this.ŒÏ) / this.ÇŽá;
            final double var29 = this.Çªà¢ + (this.ÇªØ­ - this.Çªà¢) / this.ÇŽá;
            final double var30 = this.Ê + (this.£áŒŠá - this.Ê) / this.ÇŽá;
            final double var31 = MathHelper.à(this.áˆº - this.É);
            this.É += (float)(var31 / this.ÇŽá);
            this.áƒ += (float)((this.Šà - this.áƒ) / this.ÇŽá);
            --this.ÇŽá;
            this.Ý(var28, var29, var30);
            this.Â(this.É, this.áƒ);
        }
        else {
            if (!this.Ï­Ðƒà.ŠÄ) {
                final ItemStack var32 = this.Â.áŒŠá();
                if (this.Â.ˆáŠ || !this.Â.Œ() || var32 == null || var32.HorizonCode_Horizon_È() != Items.ÂµÕ || this.Âµá€(this.Â) > 1024.0) {
                    this.á€();
                    this.Â.µÏ = null;
                    return;
                }
                if (this.Ý != null) {
                    if (!this.Ý.ˆáŠ) {
                        this.ŒÏ = this.Ý.ŒÏ;
                        final double var33 = this.Ý.£ÂµÄ;
                        this.Çªà¢ = this.Ý.£É().Â + var33 * 0.8;
                        this.Ê = this.Ý.Ê;
                        return;
                    }
                    this.Ý = null;
                }
            }
            if (this.HorizonCode_Horizon_È > 0) {
                --this.HorizonCode_Horizon_È;
            }
            if (this.ÂµÈ) {
                if (this.Ï­Ðƒà.Â(new BlockPos(this.à, this.Ø, this.áŒŠÆ)).Ý() == this.áˆºÑ¢Õ) {
                    ++this.á;
                    if (this.á == 1200) {
                        this.á€();
                    }
                    return;
                }
                this.ÂµÈ = false;
                this.ÇŽÉ *= this.ˆáƒ.nextFloat() * 0.2f;
                this.ˆá *= this.ˆáƒ.nextFloat() * 0.2f;
                this.ÇŽÕ *= this.ˆáƒ.nextFloat() * 0.2f;
                this.á = 0;
                this.ˆÏ­ = 0;
            }
            else {
                ++this.ˆÏ­;
            }
            Vec3 var34 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
            Vec3 var35 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
            MovingObjectPosition var36 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var34, var35);
            var34 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
            var35 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
            if (var36 != null) {
                var35 = new Vec3(var36.Ý.HorizonCode_Horizon_È, var36.Ý.Â, var36.Ý.Ý);
            }
            Entity var37 = null;
            final List var38 = this.Ï­Ðƒà.Â(this, this.£É().HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ).Â(1.0, 1.0, 1.0));
            double var39 = 0.0;
            for (int var40 = 0; var40 < var38.size(); ++var40) {
                final Entity var41 = var38.get(var40);
                if (var41.Ô() && (var41 != this.Â || this.ˆÏ­ >= 5)) {
                    final float var42 = 0.3f;
                    final AxisAlignedBB var43 = var41.£É().Â(var42, var42, var42);
                    final MovingObjectPosition var44 = var43.HorizonCode_Horizon_È(var34, var35);
                    if (var44 != null) {
                        final double var45 = var34.Ó(var44.Ý);
                        if (var45 < var39 || var39 == 0.0) {
                            var37 = var41;
                            var39 = var45;
                        }
                    }
                }
            }
            if (var37 != null) {
                var36 = new MovingObjectPosition(var37);
            }
            if (var36 != null) {
                if (var36.Ø­áŒŠá != null) {
                    if (var36.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this, this.Â), 0.0f)) {
                        this.Ý = var36.Ø­áŒŠá;
                    }
                }
                else {
                    this.ÂµÈ = true;
                }
            }
            if (!this.ÂµÈ) {
                this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
                final float var46 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
                this.É = (float)(Math.atan2(this.ÇŽÉ, this.ÇŽÕ) * 180.0 / 3.141592653589793);
                this.áƒ = (float)(Math.atan2(this.ˆá, var46) * 180.0 / 3.141592653589793);
                while (this.áƒ - this.Õ < -180.0f) {
                    this.Õ -= 360.0f;
                }
                while (this.áƒ - this.Õ >= 180.0f) {
                    this.Õ += 360.0f;
                }
                while (this.É - this.á€ < -180.0f) {
                    this.á€ -= 360.0f;
                }
                while (this.É - this.á€ >= 180.0f) {
                    this.á€ += 360.0f;
                }
                this.áƒ = this.Õ + (this.áƒ - this.Õ) * 0.2f;
                this.É = this.á€ + (this.É - this.á€) * 0.2f;
                float var47 = 0.92f;
                if (this.ŠÂµà || this.¥à) {
                    var47 = 0.5f;
                }
                final byte var48 = 5;
                double var49 = 0.0;
                for (int var50 = 0; var50 < var48; ++var50) {
                    final AxisAlignedBB var51 = this.£É();
                    final double var52 = var51.Âµá€ - var51.Â;
                    final double var53 = var51.Â + var52 * var50 / var48;
                    final double var54 = var51.Â + var52 * (var50 + 1) / var48;
                    final AxisAlignedBB var55 = new AxisAlignedBB(var51.HorizonCode_Horizon_È, var53, var51.Ý, var51.Ø­áŒŠá, var54, var51.Ó);
                    if (this.Ï­Ðƒà.Â(var55, Material.Ø)) {
                        var49 += 1.0 / var48;
                    }
                }
                if (!this.Ï­Ðƒà.ŠÄ && var49 > 0.0) {
                    final WorldServer var56 = (WorldServer)this.Ï­Ðƒà;
                    int var57 = 1;
                    final BlockPos var58 = new BlockPos(this).Ø­áŒŠá();
                    if (this.ˆáƒ.nextFloat() < 0.25f && this.Ï­Ðƒà.ŒÏ(var58)) {
                        var57 = 2;
                    }
                    if (this.ˆáƒ.nextFloat() < 0.5f && !this.Ï­Ðƒà.áˆºÑ¢Õ(var58)) {
                        --var57;
                    }
                    if (this.£á > 0) {
                        --this.£á;
                        if (this.£á <= 0) {
                            this.Å = 0;
                            this.£à = 0;
                        }
                    }
                    else if (this.£à > 0) {
                        this.£à -= var57;
                        if (this.£à <= 0) {
                            this.ˆá -= 0.20000000298023224;
                            this.HorizonCode_Horizon_È("random.splash", 0.25f, 1.0f + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.4f);
                            final float var59 = MathHelper.Ý(this.£É().Â);
                            var56.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ, var59 + 1.0f, this.Ê, (int)(1.0f + this.áŒŠ * 20.0f), this.áŒŠ, 0.0, this.áŒŠ, 0.20000000298023224, new int[0]);
                            var56.HorizonCode_Horizon_È(EnumParticleTypes.à, this.ŒÏ, var59 + 1.0f, this.Ê, (int)(1.0f + this.áŒŠ * 20.0f), this.áŒŠ, 0.0, this.áŒŠ, 0.20000000298023224, new int[0]);
                            this.£á = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 10, 30);
                        }
                        else {
                            this.µà += (float)(this.ˆáƒ.nextGaussian() * 4.0);
                            final float var59 = this.µà * 0.017453292f;
                            final float var60 = MathHelper.HorizonCode_Horizon_È(var59);
                            final float var61 = MathHelper.Â(var59);
                            final double var54 = this.ŒÏ + var60 * this.£à * 0.1f;
                            final double var62 = MathHelper.Ý(this.£É().Â) + 1.0f;
                            final double var63 = this.Ê + var61 * this.£à * 0.1f;
                            if (this.ˆáƒ.nextFloat() < 0.15f) {
                                var56.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, var54, var62 - 0.10000000149011612, var63, 1, var60, 0.1, var61, 0.0, new int[0]);
                            }
                            final float var64 = var60 * 0.04f;
                            final float var65 = var61 * 0.04f;
                            var56.HorizonCode_Horizon_È(EnumParticleTypes.à, var54, var62, var63, 0, var65, 0.01, -var64, 1.0, new int[0]);
                            var56.HorizonCode_Horizon_È(EnumParticleTypes.à, var54, var62, var63, 0, -var65, 0.01, var64, 1.0, new int[0]);
                        }
                    }
                    else if (this.Å > 0) {
                        this.Å -= var57;
                        float var59 = 0.15f;
                        if (this.Å < 20) {
                            var59 += (float)((20 - this.Å) * 0.05);
                        }
                        else if (this.Å < 40) {
                            var59 += (float)((40 - this.Å) * 0.02);
                        }
                        else if (this.Å < 60) {
                            var59 += (float)((60 - this.Å) * 0.01);
                        }
                        if (this.ˆáƒ.nextFloat() < var59) {
                            final float var60 = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 0.0f, 360.0f) * 0.017453292f;
                            final float var61 = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 25.0f, 60.0f);
                            final double var54 = this.ŒÏ + MathHelper.HorizonCode_Horizon_È(var60) * var61 * 0.1f;
                            final double var62 = MathHelper.Ý(this.£É().Â) + 1.0f;
                            final double var63 = this.Ê + MathHelper.Â(var60) * var61 * 0.1f;
                            var56.HorizonCode_Horizon_È(EnumParticleTypes.Ó, var54, var62, var63, 2 + this.ˆáƒ.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0, new int[0]);
                        }
                        if (this.Å <= 0) {
                            this.µà = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 0.0f, 360.0f);
                            this.£à = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 20, 80);
                        }
                    }
                    else {
                        this.Å = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 100, 900);
                        this.Å -= EnchantmentHelper.à(this.Â) * 20 * 5;
                    }
                    if (this.£á > 0) {
                        this.ˆá -= this.ˆáƒ.nextFloat() * this.ˆáƒ.nextFloat() * this.ˆáƒ.nextFloat() * 0.2;
                    }
                }
                final double var45 = var49 * 2.0 - 1.0;
                this.ˆá += 0.03999999910593033 * var45;
                if (var49 > 0.0) {
                    var47 *= 0.9;
                    this.ˆá *= 0.8;
                }
                this.ÇŽÉ *= var47;
                this.ˆá *= var47;
                this.ÇŽÕ *= var47;
                this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("xTile", (short)this.à);
        tagCompound.HorizonCode_Horizon_È("yTile", (short)this.Ø);
        tagCompound.HorizonCode_Horizon_È("zTile", (short)this.áŒŠÆ);
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(this.áˆºÑ¢Õ);
        tagCompound.HorizonCode_Horizon_È("inTile", (var2 == null) ? "" : var2.toString());
        tagCompound.HorizonCode_Horizon_È("shake", (byte)this.HorizonCode_Horizon_È);
        tagCompound.HorizonCode_Horizon_È("inGround", (byte)(this.ÂµÈ ? 1 : 0));
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.à = tagCompund.Âµá€("xTile");
        this.Ø = tagCompund.Âµá€("yTile");
        this.áŒŠÆ = tagCompund.Âµá€("zTile");
        if (tagCompund.Â("inTile", 8)) {
            this.áˆºÑ¢Õ = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("inTile"));
        }
        else {
            this.áˆºÑ¢Õ = Block.HorizonCode_Horizon_È(tagCompund.Ø­áŒŠá("inTile") & 0xFF);
        }
        this.HorizonCode_Horizon_È = (tagCompund.Ø­áŒŠá("shake") & 0xFF);
        this.ÂµÈ = (tagCompund.Ø­áŒŠá("inGround") == 1);
    }
    
    public int Ø() {
        if (this.Ï­Ðƒà.ŠÄ) {
            return 0;
        }
        byte var1 = 0;
        if (this.Ý != null) {
            final double var2 = this.Â.ŒÏ - this.ŒÏ;
            final double var3 = this.Â.Çªà¢ - this.Çªà¢;
            final double var4 = this.Â.Ê - this.Ê;
            final double var5 = MathHelper.HorizonCode_Horizon_È(var2 * var2 + var3 * var3 + var4 * var4);
            final double var6 = 0.1;
            final Entity ý = this.Ý;
            ý.ÇŽÉ += var2 * var6;
            final Entity ý2 = this.Ý;
            ý2.ˆá += var3 * var6 + MathHelper.HorizonCode_Horizon_È(var5) * 0.08;
            final Entity ý3 = this.Ý;
            ý3.ÇŽÕ += var4 * var6;
            var1 = 3;
        }
        else if (this.£á > 0) {
            final EntityItem var7 = new EntityItem(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, this.áŒŠÆ());
            final double var8 = this.Â.ŒÏ - this.ŒÏ;
            final double var9 = this.Â.Çªà¢ - this.Çªà¢;
            final double var10 = this.Â.Ê - this.Ê;
            final double var11 = MathHelper.HorizonCode_Horizon_È(var8 * var8 + var9 * var9 + var10 * var10);
            final double var12 = 0.1;
            var7.ÇŽÉ = var8 * var12;
            var7.ˆá = var9 * var12 + MathHelper.HorizonCode_Horizon_È(var11) * 0.08;
            var7.ÇŽÕ = var10 * var12;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var7);
            this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.Â.Ï­Ðƒà, this.Â.ŒÏ, this.Â.Çªà¢ + 0.5, this.Â.Ê + 0.5, this.ˆáƒ.nextInt(6) + 1));
            var1 = 1;
        }
        if (this.ÂµÈ) {
            var1 = 2;
        }
        this.á€();
        this.Â.µÏ = null;
        return var1;
    }
    
    private ItemStack áŒŠÆ() {
        float var1 = this.Ï­Ðƒà.Å.nextFloat();
        final int var2 = EnchantmentHelper.Ó(this.Â);
        final int var3 = EnchantmentHelper.à(this.Â);
        float var4 = 0.1f - var2 * 0.025f - var3 * 0.01f;
        float var5 = 0.05f + var2 * 0.01f - var3 * 0.01f;
        var4 = MathHelper.HorizonCode_Horizon_È(var4, 0.0f, 1.0f);
        var5 = MathHelper.HorizonCode_Horizon_È(var5, 0.0f, 1.0f);
        if (var1 < var4) {
            this.Â.HorizonCode_Horizon_È(StatList.Ê);
            return ((WeightedRandomFishable)WeightedRandom.HorizonCode_Horizon_È(this.ˆáƒ, EntityFishHook.Ø­áŒŠá)).HorizonCode_Horizon_È(this.ˆáƒ);
        }
        var1 -= var4;
        if (var1 < var5) {
            this.Â.HorizonCode_Horizon_È(StatList.ÇŽÉ);
            return ((WeightedRandomFishable)WeightedRandom.HorizonCode_Horizon_È(this.ˆáƒ, EntityFishHook.Âµá€)).HorizonCode_Horizon_È(this.ˆáƒ);
        }
        final float var6 = var1 - var5;
        this.Â.HorizonCode_Horizon_È(StatList.Çªà¢);
        return ((WeightedRandomFishable)WeightedRandom.HorizonCode_Horizon_È(this.ˆáƒ, EntityFishHook.Ó)).HorizonCode_Horizon_È(this.ˆáƒ);
    }
    
    @Override
    public void á€() {
        super.á€();
        if (this.Â != null) {
            this.Â.µÏ = null;
        }
    }
}
