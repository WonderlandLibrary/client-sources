package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EntitySpellParticleFX extends EntityFX
{
    private static final Random HorizonCode_Horizon_È;
    private int ÇŽá;
    private static final String Ñ¢à = "CL_00000926";
    
    static {
        HorizonCode_Horizon_È = new Random();
    }
    
    protected EntitySpellParticleFX(final World worldIn, final double p_i1229_2_, final double p_i1229_4_, final double p_i1229_6_, final double p_i1229_8_, final double p_i1229_10_, final double p_i1229_12_) {
        super(worldIn, p_i1229_2_, p_i1229_4_, p_i1229_6_, 0.5 - EntitySpellParticleFX.HorizonCode_Horizon_È.nextDouble(), p_i1229_10_, 0.5 - EntitySpellParticleFX.HorizonCode_Horizon_È.nextDouble());
        this.ÇŽá = 128;
        this.ˆá *= 0.20000000298023224;
        if (p_i1229_8_ == 0.0 && p_i1229_12_ == 0.0) {
            this.ÇŽÉ *= 0.10000000149011612;
            this.ÇŽÕ *= 0.10000000149011612;
        }
        this.Ø *= 0.75f;
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.ÇªÓ = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = (this.Ó + p_180434_3_) / this.à * 32.0f;
        var9 = MathHelper.HorizonCode_Horizon_È(var9, 0.0f, 1.0f);
        super.HorizonCode_Horizon_È(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        if (this.Ó++ >= this.à) {
            this.á€();
        }
        this.HorizonCode_Horizon_È(this.ÇŽá + (7 - this.Ó * 8 / this.à));
        this.ˆá += 0.004;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        if (this.Çªà¢ == this.ŠÄ) {
            this.ÇŽÉ *= 1.1;
            this.ÇŽÕ *= 1.1;
        }
        this.ÇŽÉ *= 0.9599999785423279;
        this.ˆá *= 0.9599999785423279;
        this.ÇŽÕ *= 0.9599999785423279;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public void Â(final int p_70589_1_) {
        this.ÇŽá = p_70589_1_;
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002585";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.Âµá€(0.15f);
            var16.HorizonCode_Horizon_È((float)p_178902_9_, (float)p_178902_11_, (float)p_178902_13_);
            return var16;
        }
    }
    
    public static class Â implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002582";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
    
    public static class Ý implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002584";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.Â(144);
            return var16;
        }
    }
    
    public static class Ø­áŒŠá implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002583";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.HorizonCode_Horizon_È((float)p_178902_9_, (float)p_178902_11_, (float)p_178902_13_);
            return var16;
        }
    }
    
    public static class Âµá€ implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002581";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.Â(144);
            final float var17 = worldIn.Å.nextFloat() * 0.5f + 0.35f;
            var16.HorizonCode_Horizon_È(1.0f * var17, 0.0f * var17, 1.0f * var17);
            return var16;
        }
    }
}
