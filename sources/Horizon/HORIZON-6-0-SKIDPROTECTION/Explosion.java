package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.HashSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class Explosion
{
    private final boolean HorizonCode_Horizon_È;
    private final boolean Â;
    private final Random Ý;
    private final World Ø­áŒŠá;
    private final double Âµá€;
    private final double Ó;
    private final double à;
    private final Entity Ø;
    private final float áŒŠÆ;
    private final List áˆºÑ¢Õ;
    private final Map ÂµÈ;
    private static final String á = "CL_00000134";
    
    public Explosion(final World worldIn, final Entity p_i45752_2_, final double p_i45752_3_, final double p_i45752_5_, final double p_i45752_7_, final float p_i45752_9_, final List p_i45752_10_) {
        this(worldIn, p_i45752_2_, p_i45752_3_, p_i45752_5_, p_i45752_7_, p_i45752_9_, false, true, p_i45752_10_);
    }
    
    public Explosion(final World worldIn, final Entity p_i45753_2_, final double p_i45753_3_, final double p_i45753_5_, final double p_i45753_7_, final float p_i45753_9_, final boolean p_i45753_10_, final boolean p_i45753_11_, final List p_i45753_12_) {
        this(worldIn, p_i45753_2_, p_i45753_3_, p_i45753_5_, p_i45753_7_, p_i45753_9_, p_i45753_10_, p_i45753_11_);
        this.áˆºÑ¢Õ.addAll(p_i45753_12_);
    }
    
    public Explosion(final World worldIn, final Entity p_i45754_2_, final double p_i45754_3_, final double p_i45754_5_, final double p_i45754_7_, final float p_i45754_9_, final boolean p_i45754_10_, final boolean p_i45754_11_) {
        this.Ý = new Random();
        this.áˆºÑ¢Õ = Lists.newArrayList();
        this.ÂµÈ = Maps.newHashMap();
        this.Ø­áŒŠá = worldIn;
        this.Ø = p_i45754_2_;
        this.áŒŠÆ = p_i45754_9_;
        this.Âµá€ = p_i45754_3_;
        this.Ó = p_i45754_5_;
        this.à = p_i45754_7_;
        this.HorizonCode_Horizon_È = p_i45754_10_;
        this.Â = p_i45754_11_;
    }
    
    public void HorizonCode_Horizon_È() {
        final HashSet var1 = Sets.newHashSet();
        final boolean var2 = true;
        for (int var3 = 0; var3 < 16; ++var3) {
            for (int var4 = 0; var4 < 16; ++var4) {
                for (int var5 = 0; var5 < 16; ++var5) {
                    if (var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15) {
                        double var6 = var3 / 15.0f * 2.0f - 1.0f;
                        double var7 = var4 / 15.0f * 2.0f - 1.0f;
                        double var8 = var5 / 15.0f * 2.0f - 1.0f;
                        final double var9 = Math.sqrt(var6 * var6 + var7 * var7 + var8 * var8);
                        var6 /= var9;
                        var7 /= var9;
                        var8 /= var9;
                        float var10 = this.áŒŠÆ * (0.7f + this.Ø­áŒŠá.Å.nextFloat() * 0.6f);
                        double var11 = this.Âµá€;
                        double var12 = this.Ó;
                        double var13 = this.à;
                        final float var14 = 0.3f;
                        while (var10 > 0.0f) {
                            final BlockPos var15 = new BlockPos(var11, var12, var13);
                            final IBlockState var16 = this.Ø­áŒŠá.Â(var15);
                            if (var16.Ý().Ó() != Material.HorizonCode_Horizon_È) {
                                final float var17 = (this.Ø != null) ? this.Ø.HorizonCode_Horizon_È(this, this.Ø­áŒŠá, var15, var16) : var16.Ý().HorizonCode_Horizon_È((Entity)null);
                                var10 -= (var17 + 0.3f) * 0.3f;
                            }
                            if (var10 > 0.0f && (this.Ø == null || this.Ø.HorizonCode_Horizon_È(this, this.Ø­áŒŠá, var15, var16, var10))) {
                                var1.add(var15);
                            }
                            var11 += var6 * 0.30000001192092896;
                            var12 += var7 * 0.30000001192092896;
                            var13 += var8 * 0.30000001192092896;
                            var10 -= 0.22500001f;
                        }
                    }
                }
            }
        }
        this.áˆºÑ¢Õ.addAll(var1);
        final float var18 = this.áŒŠÆ * 2.0f;
        int var4 = MathHelper.Ý(this.Âµá€ - var18 - 1.0);
        int var5 = MathHelper.Ý(this.Âµá€ + var18 + 1.0);
        final int var19 = MathHelper.Ý(this.Ó - var18 - 1.0);
        final int var20 = MathHelper.Ý(this.Ó + var18 + 1.0);
        final int var21 = MathHelper.Ý(this.à - var18 - 1.0);
        final int var22 = MathHelper.Ý(this.à + var18 + 1.0);
        final List var23 = this.Ø­áŒŠá.Â(this.Ø, new AxisAlignedBB(var4, var19, var21, var5, var20, var22));
        final Vec3 var24 = new Vec3(this.Âµá€, this.Ó, this.à);
        for (int var25 = 0; var25 < var23.size(); ++var25) {
            final Entity var26 = var23.get(var25);
            if (!var26.ÂµÕ()) {
                final double var27 = var26.Ó(this.Âµá€, this.Ó, this.à) / var18;
                if (var27 <= 1.0) {
                    double var28 = var26.ŒÏ - this.Âµá€;
                    double var29 = var26.Çªà¢ + var26.Ðƒáƒ() - this.Ó;
                    double var30 = var26.Ê - this.à;
                    final double var31 = MathHelper.HorizonCode_Horizon_È(var28 * var28 + var29 * var29 + var30 * var30);
                    if (var31 != 0.0) {
                        var28 /= var31;
                        var29 /= var31;
                        var30 /= var31;
                        final double var32 = this.Ø­áŒŠá.HorizonCode_Horizon_È(var24, var26.£É());
                        final double var33 = (1.0 - var27) * var32;
                        var26.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), (int)((var33 * var33 + var33) / 2.0 * 8.0 * var18 + 1.0));
                        final double var34 = EnchantmentProtection.HorizonCode_Horizon_È(var26, var33);
                        final Entity entity = var26;
                        entity.ÇŽÉ += var28 * var34;
                        final Entity entity2 = var26;
                        entity2.ˆá += var29 * var34;
                        final Entity entity3 = var26;
                        entity3.ÇŽÕ += var30 * var34;
                        if (var26 instanceof EntityPlayer) {
                            this.ÂµÈ.put(var26, new Vec3(var28 * var33, var29 * var33, var30 * var33));
                        }
                    }
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean p_77279_1_) {
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Âµá€, this.Ó, this.à, "random.explode", 4.0f, (1.0f + (this.Ø­áŒŠá.Å.nextFloat() - this.Ø­áŒŠá.Å.nextFloat()) * 0.2f) * 0.7f);
        if (this.áŒŠÆ >= 2.0f && this.Â) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(EnumParticleTypes.Ý, this.Âµá€, this.Ó, this.à, 1.0, 0.0, 0.0, new int[0]);
        }
        else {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(EnumParticleTypes.Â, this.Âµá€, this.Ó, this.à, 1.0, 0.0, 0.0, new int[0]);
        }
        if (this.Â) {
            for (final BlockPos var3 : this.áˆºÑ¢Õ) {
                final Block var4 = this.Ø­áŒŠá.Â(var3).Ý();
                if (p_77279_1_) {
                    final double var5 = var3.HorizonCode_Horizon_È() + this.Ø­áŒŠá.Å.nextFloat();
                    final double var6 = var3.Â() + this.Ø­áŒŠá.Å.nextFloat();
                    final double var7 = var3.Ý() + this.Ø­áŒŠá.Å.nextFloat();
                    double var8 = var5 - this.Âµá€;
                    double var9 = var6 - this.Ó;
                    double var10 = var7 - this.à;
                    final double var11 = MathHelper.HorizonCode_Horizon_È(var8 * var8 + var9 * var9 + var10 * var10);
                    var8 /= var11;
                    var9 /= var11;
                    var10 /= var11;
                    double var12 = 0.5 / (var11 / this.áŒŠÆ + 0.1);
                    var12 *= this.Ø­áŒŠá.Å.nextFloat() * this.Ø­áŒŠá.Å.nextFloat() + 0.3f;
                    var8 *= var12;
                    var9 *= var12;
                    var10 *= var12;
                    this.Ø­áŒŠá.HorizonCode_Horizon_È(EnumParticleTypes.HorizonCode_Horizon_È, (var5 + this.Âµá€ * 1.0) / 2.0, (var6 + this.Ó * 1.0) / 2.0, (var7 + this.à * 1.0) / 2.0, var8, var9, var10, new int[0]);
                    this.Ø­áŒŠá.HorizonCode_Horizon_È(EnumParticleTypes.á, var5, var6, var7, var8, var9, var10, new int[0]);
                }
                if (var4.Ó() != Material.HorizonCode_Horizon_È) {
                    if (var4.HorizonCode_Horizon_È(this)) {
                        var4.HorizonCode_Horizon_È(this.Ø­áŒŠá, var3, this.Ø­áŒŠá.Â(var3), 1.0f / this.áŒŠÆ, 0);
                    }
                    this.Ø­áŒŠá.HorizonCode_Horizon_È(var3, Blocks.Â.¥à(), 3);
                    var4.HorizonCode_Horizon_È(this.Ø­áŒŠá, var3, this);
                }
            }
        }
        if (this.HorizonCode_Horizon_È) {
            for (final BlockPos var3 : this.áˆºÑ¢Õ) {
                if (this.Ø­áŒŠá.Â(var3).Ý().Ó() == Material.HorizonCode_Horizon_È && this.Ø­áŒŠá.Â(var3.Âµá€()).Ý().HorizonCode_Horizon_È() && this.Ý.nextInt(3) == 0) {
                    this.Ø­áŒŠá.Â(var3, Blocks.Ô.¥à());
                }
            }
        }
    }
    
    public Map Â() {
        return this.ÂµÈ;
    }
    
    public EntityLivingBase Ý() {
        return (this.Ø == null) ? null : ((this.Ø instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.Ø).à() : ((this.Ø instanceof EntityLivingBase) ? ((EntityLivingBase)this.Ø) : null));
    }
    
    public void Ø­áŒŠá() {
        this.áˆºÑ¢Õ.clear();
    }
    
    public List Âµá€() {
        return this.áˆºÑ¢Õ;
    }
}
