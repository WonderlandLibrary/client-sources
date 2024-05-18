package HORIZON-6-0-SKIDPROTECTION;

public class EntitySlime extends EntityLiving implements IMob
{
    public float HorizonCode_Horizon_È;
    public float Â;
    public float Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001698";
    
    public EntitySlime(final World worldIn) {
        super(worldIn);
        this.Ø = new Âµá€();
        this.ÂµÈ.HorizonCode_Horizon_È(1, new Ý());
        this.ÂµÈ.HorizonCode_Horizon_È(2, new HorizonCode_Horizon_È());
        this.ÂµÈ.HorizonCode_Horizon_È(3, new Â());
        this.ÂµÈ.HorizonCode_Horizon_È(5, new Ø­áŒŠá());
        this.á.HorizonCode_Horizon_È(1, new EntityAIFindEntityNearestPlayer(this));
        this.á.HorizonCode_Horizon_È(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)1);
    }
    
    protected void HorizonCode_Horizon_È(final int p_70799_1_) {
        this.£Ó.Â(16, (byte)p_70799_1_);
        this.HorizonCode_Horizon_È(0.51000005f * p_70799_1_, 0.51000005f * p_70799_1_);
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È((double)(p_70799_1_ * p_70799_1_));
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.2f + 0.1f * p_70799_1_);
        this.áˆºÑ¢Õ(this.ÇŽÊ());
        this.à = p_70799_1_;
    }
    
    public int ÇŽÅ() {
        return this.£Ó.HorizonCode_Horizon_È(16);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Size", this.ÇŽÅ() - 1);
        tagCompound.HorizonCode_Horizon_È("wasOnGround", this.Ø­áŒŠá);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        int var2 = tagCompund.Ó("Size");
        if (var2 < 0) {
            var2 = 0;
        }
        this.HorizonCode_Horizon_È(var2 + 1);
        this.Ø­áŒŠá = tagCompund.£á("wasOnGround");
    }
    
    protected EnumParticleTypes Ø() {
        return EnumParticleTypes.É;
    }
    
    protected String Šáƒ() {
        return "mob.slime." + ((this.ÇŽÅ() > 1) ? "big" : "small");
    }
    
    @Override
    public void á() {
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.HorizonCode_Horizon_È && this.ÇŽÅ() > 0) {
            this.ˆáŠ = true;
        }
        this.Â += (this.HorizonCode_Horizon_È - this.Â) * 0.5f;
        this.Ý = this.Â;
        super.á();
        if (this.ŠÂµà && !this.Ø­áŒŠá) {
            for (int var1 = this.ÇŽÅ(), var2 = 0; var2 < var1 * 8; ++var2) {
                final float var3 = this.ˆáƒ.nextFloat() * 3.1415927f * 2.0f;
                final float var4 = this.ˆáƒ.nextFloat() * 0.5f + 0.5f;
                final float var5 = MathHelper.HorizonCode_Horizon_È(var3) * var1 * 0.5f * var4;
                final float var6 = MathHelper.Â(var3) * var1 * 0.5f * var4;
                final World var7 = this.Ï­Ðƒà;
                final EnumParticleTypes var8 = this.Ø();
                final double var9 = this.ŒÏ + var5;
                final double var10 = this.Ê + var6;
                var7.HorizonCode_Horizon_È(var8, var9, this.£É().Â, var10, 0.0, 0.0, 0.0, new int[0]);
            }
            if (this.ÇŽ()) {
                this.HorizonCode_Horizon_È(this.Šáƒ(), this.ˆÂ(), ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.HorizonCode_Horizon_È = -0.5f;
        }
        else if (!this.ŠÂµà && this.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È = 1.0f;
        }
        this.Ø­áŒŠá = this.ŠÂµà;
        this.Ø­à();
    }
    
    protected void Ø­à() {
        this.HorizonCode_Horizon_È *= 0.6f;
    }
    
    protected int ¥Æ() {
        return this.ˆáƒ.nextInt(20) + 10;
    }
    
    protected EntitySlime ˆà() {
        return new EntitySlime(this.Ï­Ðƒà);
    }
    
    @Override
    public void áˆºÑ¢Õ(final int p_145781_1_) {
        if (p_145781_1_ == 16) {
            final int var2 = this.ÇŽÅ();
            this.HorizonCode_Horizon_È(0.51000005f * var2, 0.51000005f * var2);
            this.É = this.ÂµÕ;
            this.¥É = this.ÂµÕ;
            if (this.£ÂµÄ() && this.ˆáƒ.nextInt(20) == 0) {
                this.Ä();
            }
        }
        super.áˆºÑ¢Õ(p_145781_1_);
    }
    
    @Override
    public void á€() {
        final int var1 = this.ÇŽÅ();
        if (!this.Ï­Ðƒà.ŠÄ && var1 > 1 && this.Ï­Ä() <= 0.0f) {
            for (int var2 = 2 + this.ˆáƒ.nextInt(3), var3 = 0; var3 < var2; ++var3) {
                final float var4 = (var3 % 2 - 0.5f) * var1 / 4.0f;
                final float var5 = (var3 / 2 - 0.5f) * var1 / 4.0f;
                final EntitySlime var6 = this.ˆà();
                if (this.j_()) {
                    var6.à(this.Šà());
                }
                if (this.ÇªÉ()) {
                    var6.ˆÈ();
                }
                var6.HorizonCode_Horizon_È(var1 / 2);
                var6.Â(this.ŒÏ + var4, this.Çªà¢ + 0.5, this.Ê + var5, this.ˆáƒ.nextFloat() * 360.0f, 0.0f);
                this.Ï­Ðƒà.HorizonCode_Horizon_È(var6);
            }
        }
        super.á€();
    }
    
    @Override
    public void Ó(final Entity entityIn) {
        super.Ó(entityIn);
        if (entityIn instanceof EntityIronGolem && this.µÕ()) {
            this.Âµá€((EntityLivingBase)entityIn);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer entityIn) {
        if (this.µÕ()) {
            this.Âµá€((EntityLivingBase)entityIn);
        }
    }
    
    protected void Âµá€(final EntityLivingBase p_175451_1_) {
        final int var2 = this.ÇŽÅ();
        if (this.µà(p_175451_1_) && this.Âµá€(p_175451_1_) < 0.6 * var2 * 0.6 * var2 && p_175451_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), this.Æ())) {
            this.HorizonCode_Horizon_È("mob.attack", 1.0f, (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
            this.HorizonCode_Horizon_È(this, p_175451_1_);
        }
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.625f * this.£ÂµÄ;
    }
    
    protected boolean µÕ() {
        return this.ÇŽÅ() > 1;
    }
    
    protected int Æ() {
        return this.ÇŽÅ();
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.slime." + ((this.ÇŽÅ() > 1) ? "big" : "small");
    }
    
    @Override
    protected String µÊ() {
        return "mob.slime." + ((this.ÇŽÅ() > 1) ? "big" : "small");
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return (this.ÇŽÅ() == 1) ? Items.£É : null;
    }
    
    @Override
    public boolean µà() {
        final Chunk var1 = this.Ï­Ðƒà.à(new BlockPos(MathHelper.Ý(this.ŒÏ), 0, MathHelper.Ý(this.Ê)));
        if (this.Ï­Ðƒà.ŒÏ().Ø­à() == WorldType.Ø­áŒŠá && this.ˆáƒ.nextInt(4) != 1) {
            return false;
        }
        if (this.Ï­Ðƒà.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È) {
            final BiomeGenBase var2 = this.Ï­Ðƒà.Ý(new BlockPos(MathHelper.Ý(this.ŒÏ), 0, MathHelper.Ý(this.Ê)));
            if (var2 == BiomeGenBase.Æ && this.Çªà¢ > 50.0 && this.Çªà¢ < 70.0 && this.ˆáƒ.nextFloat() < 0.5f && this.ˆáƒ.nextFloat() < this.Ï­Ðƒà.ˆÏ­() && this.Ï­Ðƒà.ˆÏ­(new BlockPos(this)) <= this.ˆáƒ.nextInt(8)) {
                return super.µà();
            }
            if (this.ˆáƒ.nextInt(10) == 0 && var1.HorizonCode_Horizon_È(987234911L).nextInt(10) == 0 && this.Çªà¢ < 40.0) {
                return super.µà();
            }
        }
        return false;
    }
    
    @Override
    protected float ˆÂ() {
        return 0.4f * this.ÇŽÅ();
    }
    
    @Override
    public int áˆºà() {
        return 0;
    }
    
    protected boolean ¥Ðƒá() {
        return this.ÇŽÅ() > 0;
    }
    
    protected boolean ÇŽ() {
        return this.ÇŽÅ() > 2;
    }
    
    @Override
    protected void ŠÏ() {
        this.ˆá = 0.41999998688697815;
        this.áŒŠÏ = true;
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        int var3 = this.ˆáƒ.nextInt(3);
        if (var3 < 2 && this.ˆáƒ.nextFloat() < 0.5f * p_180482_1_.Â()) {
            ++var3;
        }
        final int var4 = 1 << var3;
        this.HorizonCode_Horizon_È(var4);
        return super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
    }
    
    class HorizonCode_Horizon_È extends EntityAIBase
    {
        private EntitySlime Â;
        private int Ý;
        private static final String Ø­áŒŠá = "CL_00002202";
        
        public HorizonCode_Horizon_È() {
            this.Â = EntitySlime.this;
            this.HorizonCode_Horizon_È(2);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            final EntityLivingBase var1 = this.Â.Ñ¢Ó();
            return var1 != null && var1.Œ();
        }
        
        @Override
        public void Âµá€() {
            this.Ý = 300;
            super.Âµá€();
        }
        
        @Override
        public boolean Â() {
            final EntityLivingBase var1 = this.Â.Ñ¢Ó();
            boolean b;
            if (var1 == null) {
                b = false;
            }
            else if (!var1.Œ()) {
                b = false;
            }
            else {
                final int ý = this.Ý - 1;
                this.Ý = ý;
                b = (ý > 0);
            }
            return b;
        }
        
        @Override
        public void Ø­áŒŠá() {
            this.Â.HorizonCode_Horizon_È(this.Â.Ñ¢Ó(), 10.0f, 10.0f);
            ((Âµá€)this.Â.ŒÏ()).HorizonCode_Horizon_È(this.Â.É, this.Â.µÕ());
        }
    }
    
    class Â extends EntityAIBase
    {
        private EntitySlime Â;
        private float Ý;
        private int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002198";
        
        public Â() {
            this.Â = EntitySlime.this;
            this.HorizonCode_Horizon_È(2);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Â.Ñ¢Ó() == null && (this.Â.ŠÂµà || this.Â.£ÂµÄ() || this.Â.ÇŽá€());
        }
        
        @Override
        public void Ø­áŒŠá() {
            final int ø­áŒŠá = this.Ø­áŒŠá - 1;
            this.Ø­áŒŠá = ø­áŒŠá;
            if (ø­áŒŠá <= 0) {
                this.Ø­áŒŠá = 40 + this.Â.ˆÐƒØ().nextInt(60);
                this.Ý = this.Â.ˆÐƒØ().nextInt(360);
            }
            ((Âµá€)this.Â.ŒÏ()).HorizonCode_Horizon_È(this.Ý, false);
        }
    }
    
    class Ý extends EntityAIBase
    {
        private EntitySlime Â;
        private static final String Ý = "CL_00002201";
        
        public Ý() {
            this.Â = EntitySlime.this;
            this.HorizonCode_Horizon_È(5);
            ((PathNavigateGround)EntitySlime.this.Š()).Ø­áŒŠá(true);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Â.£ÂµÄ() || this.Â.ÇŽá€();
        }
        
        @Override
        public void Ø­áŒŠá() {
            if (this.Â.ˆÐƒØ().nextFloat() < 0.8f) {
                this.Â.ÇŽÉ().HorizonCode_Horizon_È();
            }
            ((Âµá€)this.Â.ŒÏ()).HorizonCode_Horizon_È(1.2);
        }
    }
    
    class Ø­áŒŠá extends EntityAIBase
    {
        private EntitySlime Â;
        private static final String Ý = "CL_00002200";
        
        public Ø­áŒŠá() {
            this.Â = EntitySlime.this;
            this.HorizonCode_Horizon_È(5);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return true;
        }
        
        @Override
        public void Ø­áŒŠá() {
            ((Âµá€)this.Â.ŒÏ()).HorizonCode_Horizon_È(1.0);
        }
    }
    
    class Âµá€ extends EntityMoveHelper
    {
        private float Ø;
        private int áŒŠÆ;
        private EntitySlime áˆºÑ¢Õ;
        private boolean ÂµÈ;
        private static final String á = "CL_00002199";
        
        public Âµá€() {
            super(EntitySlime.this);
            this.áˆºÑ¢Õ = EntitySlime.this;
        }
        
        public void HorizonCode_Horizon_È(final float p_179920_1_, final boolean p_179920_2_) {
            this.Ø = p_179920_1_;
            this.ÂµÈ = p_179920_2_;
        }
        
        public void HorizonCode_Horizon_È(final double p_179921_1_) {
            this.Âµá€ = p_179921_1_;
            this.Ó = true;
        }
        
        @Override
        public void Ý() {
            this.HorizonCode_Horizon_È.É = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.É, this.Ø, 30.0f);
            this.HorizonCode_Horizon_È.ÂµÕ = this.HorizonCode_Horizon_È.É;
            this.HorizonCode_Horizon_È.¥É = this.HorizonCode_Horizon_È.É;
            if (!this.Ó) {
                this.HorizonCode_Horizon_È.Âµá€(0.0f);
            }
            else {
                this.Ó = false;
                if (this.HorizonCode_Horizon_È.ŠÂµà) {
                    this.HorizonCode_Horizon_È.áŒŠÆ((float)(this.Âµá€ * this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Âµá€()));
                    if (this.áŒŠÆ-- <= 0) {
                        this.áŒŠÆ = this.áˆºÑ¢Õ.¥Æ();
                        if (this.ÂµÈ) {
                            this.áŒŠÆ /= 3;
                        }
                        this.áˆºÑ¢Õ.ÇŽÉ().HorizonCode_Horizon_È();
                        if (this.áˆºÑ¢Õ.¥Ðƒá()) {
                            this.áˆºÑ¢Õ.HorizonCode_Horizon_È(this.áˆºÑ¢Õ.Šáƒ(), this.áˆºÑ¢Õ.ˆÂ(), ((this.áˆºÑ¢Õ.ˆÐƒØ().nextFloat() - this.áˆºÑ¢Õ.ˆÐƒØ().nextFloat()) * 0.2f + 1.0f) * 0.8f);
                        }
                    }
                    else {
                        final EntitySlime áˆºÑ¢Õ = this.áˆºÑ¢Õ;
                        final EntitySlime áˆºÑ¢Õ2 = this.áˆºÑ¢Õ;
                        final float n = 0.0f;
                        áˆºÑ¢Õ2.Ï­áˆºÓ = n;
                        áˆºÑ¢Õ.£áƒ = n;
                        this.HorizonCode_Horizon_È.áŒŠÆ(0.0f);
                    }
                }
                else {
                    this.HorizonCode_Horizon_È.áŒŠÆ((float)(this.Âµá€ * this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Âµá€()));
                }
            }
        }
    }
}
