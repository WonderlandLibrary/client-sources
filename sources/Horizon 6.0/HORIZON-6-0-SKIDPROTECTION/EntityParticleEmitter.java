package HORIZON-6-0-SKIDPROTECTION;

public class EntityParticleEmitter extends EntityFX
{
    private Entity HorizonCode_Horizon_È;
    private int ÇŽá;
    private int Ñ¢à;
    private EnumParticleTypes ÇªØ­;
    private static final String £áŒŠá = "CL_00002574";
    
    public EntityParticleEmitter(final World worldIn, final Entity p_i46279_2_, final EnumParticleTypes p_i46279_3_) {
        super(worldIn, p_i46279_2_.ŒÏ, p_i46279_2_.£É().Â + p_i46279_2_.£ÂµÄ / 2.0f, p_i46279_2_.Ê, p_i46279_2_.ÇŽÉ, p_i46279_2_.ˆá, p_i46279_2_.ÇŽÕ);
        this.HorizonCode_Horizon_È = p_i46279_2_;
        this.Ñ¢à = 3;
        this.ÇªØ­ = p_i46279_3_;
        this.á();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
    }
    
    @Override
    public void á() {
        for (int var1 = 0; var1 < 16; ++var1) {
            final double var2 = this.ˆáƒ.nextFloat() * 2.0f - 1.0f;
            final double var3 = this.ˆáƒ.nextFloat() * 2.0f - 1.0f;
            final double var4 = this.ˆáƒ.nextFloat() * 2.0f - 1.0f;
            if (var2 * var2 + var3 * var3 + var4 * var4 <= 1.0) {
                final double var5 = this.HorizonCode_Horizon_È.ŒÏ + var2 * this.HorizonCode_Horizon_È.áŒŠ / 4.0;
                final double var6 = this.HorizonCode_Horizon_È.£É().Â + this.HorizonCode_Horizon_È.£ÂµÄ / 2.0f + var3 * this.HorizonCode_Horizon_È.£ÂµÄ / 4.0;
                final double var7 = this.HorizonCode_Horizon_È.Ê + var4 * this.HorizonCode_Horizon_È.áŒŠ / 4.0;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ÇªØ­, false, var5, var6, var7, var2, var3 + 0.2, var4, new int[0]);
            }
        }
        ++this.ÇŽá;
        if (this.ÇŽá >= this.Ñ¢à) {
            this.á€();
        }
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
}
