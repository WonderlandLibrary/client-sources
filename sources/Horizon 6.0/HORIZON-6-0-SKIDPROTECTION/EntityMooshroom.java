package HORIZON-6-0-SKIDPROTECTION;

public class EntityMooshroom extends EntityCow
{
    private static final String ŒÂ = "CL_00001645";
    
    public EntityMooshroom(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.9f, 1.3f);
        this.Ø­Ñ¢Ï­Ø­áˆº = Blocks.Œáƒ;
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.ŠÄ && this.à() >= 0) {
            if (var2.Â == 1) {
                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, new ItemStack(Items.Ñ¢á));
                return true;
            }
            if (p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new ItemStack(Items.Ñ¢á)) && !p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Â(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, 1);
                return true;
            }
        }
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºà && this.à() >= 0) {
            this.á€();
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Â, this.ŒÏ, this.Çªà¢ + this.£ÂµÄ / 2.0f, this.Ê, 0.0, 0.0, 0.0, new int[0]);
            if (!this.Ï­Ðƒà.ŠÄ) {
                final EntityCow var3 = new EntityCow(this.Ï­Ðƒà);
                var3.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
                var3.áˆºÑ¢Õ(this.Ï­Ä());
                var3.¥É = this.¥É;
                if (this.j_()) {
                    var3.à(this.Šà());
                }
                this.Ï­Ðƒà.HorizonCode_Horizon_È(var3);
                for (int var4 = 0; var4 < 5; ++var4) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityItem(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢ + this.£ÂµÄ, this.Ê, new ItemStack(Blocks.áŠ)));
                }
                var2.HorizonCode_Horizon_È(1, p_70085_1_);
                this.HorizonCode_Horizon_È("mob.sheep.shear", 1.0f, 1.0f);
            }
            return true;
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    public EntityMooshroom Ý(final EntityAgeable p_90011_1_) {
        return new EntityMooshroom(this.Ï­Ðƒà);
    }
}
