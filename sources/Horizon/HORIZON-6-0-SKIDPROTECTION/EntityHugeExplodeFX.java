package HORIZON-6-0-SKIDPROTECTION;

public class EntityHugeExplodeFX extends EntityFX
{
    private int HorizonCode_Horizon_È;
    private int ÇŽá;
    private static final String Ñ¢à = "CL_00000911";
    
    protected EntityHugeExplodeFX(final World worldIn, final double p_i1214_2_, final double p_i1214_4_, final double p_i1214_6_, final double p_i1214_8_, final double p_i1214_10_, final double p_i1214_12_) {
        super(worldIn, p_i1214_2_, p_i1214_4_, p_i1214_6_, 0.0, 0.0, 0.0);
        this.ÇŽá = 8;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
    }
    
    @Override
    public void á() {
        for (int var1 = 0; var1 < 6; ++var1) {
            final double var2 = this.ŒÏ + (this.ˆáƒ.nextDouble() - this.ˆáƒ.nextDouble()) * 4.0;
            final double var3 = this.Çªà¢ + (this.ˆáƒ.nextDouble() - this.ˆáƒ.nextDouble()) * 4.0;
            final double var4 = this.Ê + (this.ˆáƒ.nextDouble() - this.ˆáƒ.nextDouble()) * 4.0;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Â, var2, var3, var4, this.HorizonCode_Horizon_È / this.ÇŽá, 0.0, 0.0, new int[0]);
        }
        ++this.HorizonCode_Horizon_È;
        if (this.HorizonCode_Horizon_È == this.ÇŽá) {
            this.á€();
        }
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 1;
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002597";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityHugeExplodeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
