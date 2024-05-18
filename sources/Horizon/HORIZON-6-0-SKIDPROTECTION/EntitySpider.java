package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EntitySpider extends EntityMob
{
    private static final String Â = "CL_00001699";
    
    public EntitySpider(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(1.4f, 0.9f);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.HorizonCode_Horizon_È);
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAILeapAtTarget(this, 0.4f));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new HorizonCode_Horizon_È(EntityPlayer.class));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new HorizonCode_Horizon_È(EntityIronGolem.class));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIWander(this, 0.8));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new Â(EntityPlayer.class));
        this.á.HorizonCode_Horizon_È(3, new Â(EntityIronGolem.class));
    }
    
    @Override
    protected PathNavigate Â(final World worldIn) {
        return new PathNavigateClimber(this, worldIn);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, new Byte((byte)0));
    }
    
    @Override
    public void á() {
        super.á();
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.HorizonCode_Horizon_È(this.¥à);
        }
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(16.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.30000001192092896);
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.spider.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.spider.say";
    }
    
    @Override
    protected String µÊ() {
        return "mob.spider.death";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.spider.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.ˆá;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        super.HorizonCode_Horizon_È(p_70628_1_, p_70628_2_);
        if (p_70628_1_ && (this.ˆáƒ.nextInt(3) == 0 || this.ˆáƒ.nextInt(1 + p_70628_2_) > 0)) {
            this.HorizonCode_Horizon_È(Items.ÇªÂ, 1);
        }
    }
    
    @Override
    public boolean i_() {
        return this.Ø();
    }
    
    @Override
    public void ¥Ä() {
    }
    
    @Override
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.Ý;
    }
    
    @Override
    public boolean Â(final PotionEffect p_70687_1_) {
        return p_70687_1_.HorizonCode_Horizon_È() != Potion.µÕ.É && super.Â(p_70687_1_);
    }
    
    public boolean Ø() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70839_1_) {
        byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70839_1_) {
            var2 |= 0x1;
        }
        else {
            var2 &= 0xFFFFFFFE;
        }
        this.£Ó.Â(16, var2);
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        Object p_180482_2_2 = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        if (this.Ï­Ðƒà.Å.nextInt(100) == 0) {
            final EntitySkeleton var3 = new EntitySkeleton(this.Ï­Ðƒà);
            var3.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, 0.0f);
            var3.HorizonCode_Horizon_È(p_180482_1_, null);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var3);
            var3.HorizonCode_Horizon_È((Entity)this);
        }
        if (p_180482_2_2 == null) {
            p_180482_2_2 = new Ý();
            if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá && this.Ï­Ðƒà.Å.nextFloat() < 0.1f * p_180482_1_.Â()) {
                ((Ý)p_180482_2_2).HorizonCode_Horizon_È(this.Ï­Ðƒà.Å);
            }
        }
        if (p_180482_2_2 instanceof Ý) {
            final int var4 = ((Ý)p_180482_2_2).HorizonCode_Horizon_È;
            if (var4 > 0 && Potion.HorizonCode_Horizon_È[var4] != null) {
                this.HorizonCode_Horizon_È(new PotionEffect(var4, Integer.MAX_VALUE));
            }
        }
        return (IEntityLivingData)p_180482_2_2;
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.65f;
    }
    
    class HorizonCode_Horizon_È extends EntityAIAttackOnCollide
    {
        private static final String áŒŠÆ = "CL_00002197";
        
        public HorizonCode_Horizon_È(final Class p_i45819_2_) {
            super(EntitySpider.this, p_i45819_2_, 1.0, true);
        }
        
        @Override
        public boolean Â() {
            final float var1 = this.Â.Â(1.0f);
            if (var1 >= 0.5f && this.Â.ˆÐƒØ().nextInt(100) == 0) {
                this.Â.Â((EntityLivingBase)null);
                return false;
            }
            return super.Â();
        }
        
        @Override
        protected double HorizonCode_Horizon_È(final EntityLivingBase p_179512_1_) {
            return 4.0f + p_179512_1_.áŒŠ;
        }
    }
    
    class Â extends EntityAINearestAttackableTarget
    {
        private static final String Ø = "CL_00002196";
        
        public Â(final Class p_i45818_2_) {
            super(EntitySpider.this, p_i45818_2_, true);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            final float var1 = this.Âµá€.Â(1.0f);
            return var1 < 0.5f && super.HorizonCode_Horizon_È();
        }
    }
    
    public static class Ý implements IEntityLivingData
    {
        public int HorizonCode_Horizon_È;
        private static final String Â = "CL_00001700";
        
        public void HorizonCode_Horizon_È(final Random p_111104_1_) {
            final int var2 = p_111104_1_.nextInt(5);
            if (var2 <= 1) {
                this.HorizonCode_Horizon_È = Potion.Ý.É;
            }
            else if (var2 <= 2) {
                this.HorizonCode_Horizon_È = Potion.à.É;
            }
            else if (var2 <= 3) {
                this.HorizonCode_Horizon_È = Potion.á.É;
            }
            else if (var2 <= 4) {
                this.HorizonCode_Horizon_È = Potion.£à.É;
            }
        }
    }
}
