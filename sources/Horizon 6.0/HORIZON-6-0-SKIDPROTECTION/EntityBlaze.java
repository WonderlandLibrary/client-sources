package HORIZON-6-0-SKIDPROTECTION;

public class EntityBlaze extends EntityMob
{
    private float Â;
    private int Ý;
    private static final String Ø­Ñ¢Ï­Ø­áˆº = "CL_00001682";
    
    public EntityBlaze(final World worldIn) {
        super(worldIn);
        this.Â = 0.5f;
        this.£Â = true;
        this.à = 10;
        this.ÂµÈ.HorizonCode_Horizon_È(4, new HorizonCode_Horizon_È());
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(6.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.23000000417232513);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(48.0);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, new Byte((byte)0));
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.blaze.breathe";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.blaze.hit";
    }
    
    @Override
    protected String µÊ() {
        return "mob.blaze.death";
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
    public void ˆÏ­() {
        if (!this.ŠÂµà && this.ˆá < 0.0) {
            this.ˆá *= 0.6;
        }
        if (this.Ï­Ðƒà.ŠÄ) {
            if (this.ˆáƒ.nextInt(24) == 0 && !this.áŠ()) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ + 0.5, this.Çªà¢ + 0.5, this.Ê + 0.5, "fire.fire", 1.0f + this.ˆáƒ.nextFloat(), this.ˆáƒ.nextFloat() * 0.7f + 0.3f, false);
            }
            for (int var1 = 0; var1 < 2; ++var1) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.ˆÏ­, this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, this.Çªà¢ + this.ˆáƒ.nextDouble() * this.£ÂµÄ, this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, 0.0, 0.0, 0.0, new int[0]);
            }
        }
        super.ˆÏ­();
    }
    
    @Override
    protected void ˆØ() {
        if (this.áŒŠ()) {
            this.HorizonCode_Horizon_È(DamageSource.Ó, 1.0f);
        }
        --this.Ý;
        if (this.Ý <= 0) {
            this.Ý = 100;
            this.Â = 0.5f + (float)this.ˆáƒ.nextGaussian() * 3.0f;
        }
        final EntityLivingBase var1 = this.Ñ¢Ó();
        if (var1 != null && var1.Çªà¢ + var1.Ðƒáƒ() > this.Çªà¢ + this.Ðƒáƒ() + this.Â) {
            this.ˆá += (0.30000001192092896 - this.ˆá) * 0.30000001192092896;
            this.áŒŠÏ = true;
        }
        super.ˆØ();
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.Çªà;
    }
    
    @Override
    public boolean ˆÏ() {
        return this.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        if (p_70628_1_) {
            for (int var3 = this.ˆáƒ.nextInt(2 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
                this.HorizonCode_Horizon_È(Items.Çªà, 1);
            }
        }
    }
    
    public boolean Ø() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70844_1_) {
        byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70844_1_) {
            var2 |= 0x1;
        }
        else {
            var2 &= 0xFFFFFFFE;
        }
        this.£Ó.Â(16, var2);
    }
    
    @Override
    protected boolean w_() {
        return true;
    }
    
    class HorizonCode_Horizon_È extends EntityAIBase
    {
        private EntityBlaze Â;
        private int Ý;
        private int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002225";
        
        public HorizonCode_Horizon_È() {
            this.Â = EntityBlaze.this;
            this.HorizonCode_Horizon_È(3);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            final EntityLivingBase var1 = this.Â.Ñ¢Ó();
            return var1 != null && var1.Œ();
        }
        
        @Override
        public void Âµá€() {
            this.Ý = 0;
        }
        
        @Override
        public void Ý() {
            this.Â.HorizonCode_Horizon_È(false);
        }
        
        @Override
        public void Ø­áŒŠá() {
            --this.Ø­áŒŠá;
            final EntityLivingBase var1 = this.Â.Ñ¢Ó();
            final double var2 = this.Â.Âµá€(var1);
            if (var2 < 4.0) {
                if (this.Ø­áŒŠá <= 0) {
                    this.Ø­áŒŠá = 20;
                    this.Â.Å(var1);
                }
                this.Â.ŒÏ().HorizonCode_Horizon_È(var1.ŒÏ, var1.Çªà¢, var1.Ê, 1.0);
            }
            else if (var2 < 256.0) {
                final double var3 = var1.ŒÏ - this.Â.ŒÏ;
                final double var4 = var1.£É().Â + var1.£ÂµÄ / 2.0f - (this.Â.Çªà¢ + this.Â.£ÂµÄ / 2.0f);
                final double var5 = var1.Ê - this.Â.Ê;
                if (this.Ø­áŒŠá <= 0) {
                    ++this.Ý;
                    if (this.Ý == 1) {
                        this.Ø­áŒŠá = 60;
                        this.Â.HorizonCode_Horizon_È(true);
                    }
                    else if (this.Ý <= 4) {
                        this.Ø­áŒŠá = 6;
                    }
                    else {
                        this.Ø­áŒŠá = 100;
                        this.Ý = 0;
                        this.Â.HorizonCode_Horizon_È(false);
                    }
                    if (this.Ý > 1) {
                        final float var6 = MathHelper.Ý(MathHelper.HorizonCode_Horizon_È(var2)) * 0.5f;
                        this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1009, new BlockPos((int)this.Â.ŒÏ, (int)this.Â.Çªà¢, (int)this.Â.Ê), 0);
                        for (int var7 = 0; var7 < 1; ++var7) {
                            final EntitySmallFireball var8 = new EntitySmallFireball(this.Â.Ï­Ðƒà, this.Â, var3 + this.Â.ˆÐƒØ().nextGaussian() * var6, var4, var5 + this.Â.ˆÐƒØ().nextGaussian() * var6);
                            var8.Çªà¢ = this.Â.Çªà¢ + this.Â.£ÂµÄ / 2.0f + 0.5;
                            this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(var8);
                        }
                    }
                }
                this.Â.Ñ¢á().HorizonCode_Horizon_È(var1, 10.0f, 10.0f);
            }
            else {
                this.Â.Š().à();
                this.Â.ŒÏ().HorizonCode_Horizon_È(var1.ŒÏ, var1.Çªà¢, var1.Ê, 1.0);
            }
            super.Ø­áŒŠá();
        }
    }
}
