package HORIZON-6-0-SKIDPROTECTION;

public class EntityMagmaCube extends EntitySlime
{
    private static final String Ø­áŒŠá = "CL_00001691";
    
    public EntityMagmaCube(final World worldIn) {
        super(worldIn);
        this.£Â = true;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.20000000298023224);
    }
    
    @Override
    public boolean µà() {
        return this.Ï­Ðƒà.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean ÐƒÂ() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), this) && this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty() && !this.Ï­Ðƒà.Ø­áŒŠá(this.£É());
    }
    
    @Override
    public int áŒŠÉ() {
        return this.ÇŽÅ() * 3;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return 1.0f;
    }
    
    @Override
    protected EnumParticleTypes Ø() {
        return EnumParticleTypes.Ñ¢á;
    }
    
    @Override
    protected EntitySlime ˆà() {
        return new EntityMagmaCube(this.Ï­Ðƒà);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.ÇŽÈ;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        final Item_1028566121 var3 = this.áŒŠÕ();
        if (var3 != null && this.ÇŽÅ() > 1) {
            int var4 = this.ˆáƒ.nextInt(4) - 2;
            if (p_70628_2_ > 0) {
                var4 += this.ˆáƒ.nextInt(p_70628_2_ + 1);
            }
            for (int var5 = 0; var5 < var4; ++var5) {
                this.HorizonCode_Horizon_È(var3, 1);
            }
        }
    }
    
    @Override
    public boolean ˆÏ() {
        return false;
    }
    
    @Override
    protected int ¥Æ() {
        return super.¥Æ() * 4;
    }
    
    @Override
    protected void Ø­à() {
        this.HorizonCode_Horizon_È *= 0.9f;
    }
    
    @Override
    protected void ŠÏ() {
        this.ˆá = 0.42f + this.ÇŽÅ() * 0.1f;
        this.áŒŠÏ = true;
    }
    
    @Override
    protected void ŠÑ¢Ó() {
        this.ˆá = 0.22f + this.ÇŽÅ() * 0.05f;
        this.áŒŠÏ = true;
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    protected boolean µÕ() {
        return true;
    }
    
    @Override
    protected int Æ() {
        return super.Æ() + 2;
    }
    
    @Override
    protected String Šáƒ() {
        return (this.ÇŽÅ() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
    }
    
    @Override
    protected boolean ÇŽ() {
        return true;
    }
}
