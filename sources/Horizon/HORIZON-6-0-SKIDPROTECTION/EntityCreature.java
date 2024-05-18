package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;

public abstract class EntityCreature extends EntityLiving
{
    public static final UUID Ø­áŒŠá;
    public static final AttributeModifier Âµá€;
    private BlockPos HorizonCode_Horizon_È;
    private float Â;
    private EntityAIBase Ý;
    private boolean Ø­Ñ¢Ï­Ø­áˆº;
    private static final String ŒÂ = "CL_00001558";
    
    static {
        Ø­áŒŠá = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
        Âµá€ = new AttributeModifier(EntityCreature.Ø­áŒŠá, "Fleeing speed bonus", 2.0, 2).HorizonCode_Horizon_È(false);
    }
    
    public EntityCreature(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = BlockPos.HorizonCode_Horizon_È;
        this.Â = -1.0f;
        this.Ý = new EntityAIMoveTowardsRestriction(this, 1.0);
    }
    
    public float HorizonCode_Horizon_È(final BlockPos p_180484_1_) {
        return 0.0f;
    }
    
    @Override
    public boolean µà() {
        return super.µà() && this.HorizonCode_Horizon_È(new BlockPos(this.ŒÏ, this.£É().Â, this.Ê)) >= 0.0f;
    }
    
    public boolean ˆà() {
        return !this.áˆºÑ¢Õ.Ó();
    }
    
    public boolean ¥Æ() {
        return this.Ø­áŒŠá(new BlockPos(this));
    }
    
    public boolean Ø­áŒŠá(final BlockPos p_180485_1_) {
        return this.Â == -1.0f || this.HorizonCode_Horizon_È.Ó(p_180485_1_) < this.Â * this.Â;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_175449_1_, final int p_175449_2_) {
        this.HorizonCode_Horizon_È = p_175449_1_;
        this.Â = p_175449_2_;
    }
    
    public BlockPos Ø­à() {
        return this.HorizonCode_Horizon_È;
    }
    
    public float µÕ() {
        return this.Â;
    }
    
    public void Æ() {
        this.Â = -1.0f;
    }
    
    public boolean Šáƒ() {
        return this.Â != -1.0f;
    }
    
    @Override
    protected void Ï­Ðƒà() {
        super.Ï­Ðƒà();
        if (this.ÇŽà() && this.ŠáˆºÂ() != null && this.ŠáˆºÂ().Ï­Ðƒà == this.Ï­Ðƒà) {
            final Entity var1 = this.ŠáˆºÂ();
            this.HorizonCode_Horizon_È(new BlockPos((int)var1.ŒÏ, (int)var1.Çªà¢, (int)var1.Ê), 5);
            final float var2 = this.Ø­áŒŠá(var1);
            if (this instanceof EntityTameable && ((EntityTameable)this).áˆºÕ()) {
                if (var2 > 10.0f) {
                    this.HorizonCode_Horizon_È(true, true);
                }
                return;
            }
            if (!this.Ø­Ñ¢Ï­Ø­áˆº) {
                this.ÂµÈ.HorizonCode_Horizon_È(2, this.Ý);
                if (this.Š() instanceof PathNavigateGround) {
                    ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(false);
                }
                this.Ø­Ñ¢Ï­Ø­áˆº = true;
            }
            this.Ø­áŒŠá(var2);
            if (var2 > 4.0f) {
                this.Š().HorizonCode_Horizon_È(var1, 1.0);
            }
            if (var2 > 6.0f) {
                final double var3 = (var1.ŒÏ - this.ŒÏ) / var2;
                final double var4 = (var1.Çªà¢ - this.Çªà¢) / var2;
                final double var5 = (var1.Ê - this.Ê) / var2;
                this.ÇŽÉ += var3 * Math.abs(var3) * 0.4;
                this.ˆá += var4 * Math.abs(var4) * 0.4;
                this.ÇŽÕ += var5 * Math.abs(var5) * 0.4;
            }
            if (var2 > 10.0f) {
                this.HorizonCode_Horizon_È(true, true);
            }
        }
        else if (!this.ÇŽà() && this.Ø­Ñ¢Ï­Ø­áˆº) {
            this.Ø­Ñ¢Ï­Ø­áˆº = false;
            this.ÂµÈ.HorizonCode_Horizon_È(this.Ý);
            if (this.Š() instanceof PathNavigateGround) {
                ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
            }
            this.Æ();
        }
    }
    
    protected void Ø­áŒŠá(final float p_142017_1_) {
    }
}
