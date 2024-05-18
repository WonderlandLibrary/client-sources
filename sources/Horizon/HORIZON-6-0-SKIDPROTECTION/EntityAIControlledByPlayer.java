package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIControlledByPlayer extends EntityAIBase
{
    private final EntityLiving HorizonCode_Horizon_È;
    private final float Â;
    private float Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private static final String à = "CL_00001580";
    
    public EntityAIControlledByPlayer(final EntityLiving p_i1620_1_, final float p_i1620_2_) {
        this.HorizonCode_Horizon_È = p_i1620_1_;
        this.Â = p_i1620_2_;
        this.HorizonCode_Horizon_È(7);
    }
    
    @Override
    public void Âµá€() {
        this.Ý = 0.0f;
    }
    
    @Override
    public void Ý() {
        this.Ø­áŒŠá = false;
        this.Ý = 0.0f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.Œ() && this.HorizonCode_Horizon_È.µÕ != null && this.HorizonCode_Horizon_È.µÕ instanceof EntityPlayer && (this.Ø­áŒŠá || this.HorizonCode_Horizon_È.ÇŽÄ());
    }
    
    @Override
    public void Ø­áŒŠá() {
        final EntityPlayer var1 = (EntityPlayer)this.HorizonCode_Horizon_È.µÕ;
        final EntityCreature var2 = (EntityCreature)this.HorizonCode_Horizon_È;
        float var3 = MathHelper.à(var1.É - this.HorizonCode_Horizon_È.É) * 0.5f;
        if (var3 > 5.0f) {
            var3 = 5.0f;
        }
        if (var3 < -5.0f) {
            var3 = -5.0f;
        }
        this.HorizonCode_Horizon_È.É = MathHelper.à(this.HorizonCode_Horizon_È.É + var3);
        if (this.Ý < this.Â) {
            this.Ý += (this.Â - this.Ý) * 0.01f;
        }
        if (this.Ý > this.Â) {
            this.Ý = this.Â;
        }
        final int var4 = MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ);
        final int var5 = MathHelper.Ý(this.HorizonCode_Horizon_È.Çªà¢);
        final int var6 = MathHelper.Ý(this.HorizonCode_Horizon_È.Ê);
        float var7 = this.Ý;
        if (this.Ø­áŒŠá) {
            if (this.Âµá€++ > this.Ó) {
                this.Ø­áŒŠá = false;
            }
            var7 += var7 * 1.15f * MathHelper.HorizonCode_Horizon_È(this.Âµá€ / this.Ó * 3.1415927f);
        }
        float var8 = 0.91f;
        if (this.HorizonCode_Horizon_È.ŠÂµà) {
            var8 = this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ø­áŒŠá((float)var4), MathHelper.Ø­áŒŠá((float)var5) - 1, MathHelper.Ø­áŒŠá((float)var6))).Ý().áƒ * 0.91f;
        }
        final float var9 = 0.16277136f / (var8 * var8 * var8);
        final float var10 = MathHelper.HorizonCode_Horizon_È(var2.É * 3.1415927f / 180.0f);
        final float var11 = MathHelper.Â(var2.É * 3.1415927f / 180.0f);
        final float var12 = var2.áˆºá() * var9;
        float var13 = Math.max(var7, 1.0f);
        var13 = var12 / var13;
        final float var14 = var7 * var13;
        float var15 = -(var14 * var10);
        float var16 = var14 * var11;
        if (MathHelper.Âµá€(var15) > MathHelper.Âµá€(var16)) {
            if (var15 < 0.0f) {
                var15 -= this.HorizonCode_Horizon_È.áŒŠ / 2.0f;
            }
            if (var15 > 0.0f) {
                var15 += this.HorizonCode_Horizon_È.áŒŠ / 2.0f;
            }
            var16 = 0.0f;
        }
        else {
            var15 = 0.0f;
            if (var16 < 0.0f) {
                var16 -= this.HorizonCode_Horizon_È.áŒŠ / 2.0f;
            }
            if (var16 > 0.0f) {
                var16 += this.HorizonCode_Horizon_È.áŒŠ / 2.0f;
            }
        }
        final int var17 = MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ + var15);
        final int var18 = MathHelper.Ý(this.HorizonCode_Horizon_È.Ê + var16);
        final int var19 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.áŒŠ + 1.0f);
        final int var20 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.£ÂµÄ + var1.£ÂµÄ + 1.0f);
        final int var21 = MathHelper.Ø­áŒŠá(this.HorizonCode_Horizon_È.áŒŠ + 1.0f);
        if (var4 != var17 || var6 != var18) {
            final Block var22 = this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(new BlockPos(var4, var5, var6)).Ý();
            final boolean var23 = !this.HorizonCode_Horizon_È(var22) && (var22.Ó() != Material.HorizonCode_Horizon_È || !this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà.Â(new BlockPos(var4, var5 - 1, var6)).Ý()));
            if (var23 && WalkNodeProcessor.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.HorizonCode_Horizon_È, var17, var5, var18, var19, var20, var21, false, false, true) == 0 && 1 == WalkNodeProcessor.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.HorizonCode_Horizon_È, var4, var5 + 1, var6, var19, var20, var21, false, false, true) && 1 == WalkNodeProcessor.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.HorizonCode_Horizon_È, var17, var5 + 1, var18, var19, var20, var21, false, false, true)) {
                var2.ÇŽÉ().HorizonCode_Horizon_È();
            }
        }
        if (!var1.áˆºáˆºáŠ.Ø­áŒŠá && this.Ý >= this.Â * 0.5f && this.HorizonCode_Horizon_È.ˆÐƒØ().nextFloat() < 0.006f && !this.Ø­áŒŠá) {
            final ItemStack var24 = var1.Çª();
            if (var24 != null && var24.HorizonCode_Horizon_È() == Items.ŠÑ¢Ó) {
                var24.HorizonCode_Horizon_È(1, var1);
                if (var24.Â == 0) {
                    final ItemStack var25 = new ItemStack(Items.ÂµÕ);
                    var25.Ø­áŒŠá(var24.Å());
                    var1.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[var1.Ø­Ñ¢Ï­Ø­áˆº.Ý] = var25;
                }
            }
        }
        this.HorizonCode_Horizon_È.Ó(0.0f, var7);
    }
    
    private boolean HorizonCode_Horizon_È(final Block p_151498_1_) {
        return p_151498_1_ instanceof BlockStairs || p_151498_1_ instanceof BlockSlab;
    }
    
    public boolean Ø() {
        return this.Ø­áŒŠá;
    }
    
    public void áŒŠÆ() {
        this.Ø­áŒŠá = true;
        this.Âµá€ = 0;
        this.Ó = this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(841) + 140;
    }
    
    public boolean áˆºÑ¢Õ() {
        return !this.Ø() && this.Ý > this.Â * 0.3f;
    }
}
