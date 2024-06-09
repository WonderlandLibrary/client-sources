package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;

public class EntityPigZombie extends EntityZombie
{
    private static final UUID Ý;
    private static final AttributeModifier Ø­Ñ¢Ï­Ø­áˆº;
    private int ŒÂ;
    private int Ï­Ï;
    private UUID ŠØ;
    private static final String ˆÐƒØ = "CL_00001693";
    
    static {
        Ý = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        Ø­Ñ¢Ï­Ø­áˆº = new AttributeModifier(EntityPigZombie.Ý, "Attacking speed boost", 0.05, 0).HorizonCode_Horizon_È(false);
    }
    
    public EntityPigZombie(final World worldIn) {
        super(worldIn);
        this.£Â = true;
    }
    
    @Override
    public void Ý(final EntityLivingBase p_70604_1_) {
        super.Ý(p_70604_1_);
        if (p_70604_1_ != null) {
            this.ŠØ = p_70604_1_.£áŒŠá();
        }
    }
    
    @Override
    protected void Ø() {
        this.á.HorizonCode_Horizon_È(1, new HorizonCode_Horizon_È());
        this.á.HorizonCode_Horizon_È(2, new Â());
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(EntityPigZombie.Â).HorizonCode_Horizon_È(0.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.23000000417232513);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(5.0);
    }
    
    @Override
    public void á() {
        super.á();
    }
    
    @Override
    protected void ˆØ() {
        final IAttributeInstance var1 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
        if (this.ÇŽÅ()) {
            if (!this.h_() && !var1.HorizonCode_Horizon_È(EntityPigZombie.Ø­Ñ¢Ï­Ø­áˆº)) {
                var1.Â(EntityPigZombie.Ø­Ñ¢Ï­Ø­áˆº);
            }
            --this.ŒÂ;
        }
        else if (var1.HorizonCode_Horizon_È(EntityPigZombie.Ø­Ñ¢Ï­Ø­áˆº)) {
            var1.Ý(EntityPigZombie.Ø­Ñ¢Ï­Ø­áˆº);
        }
        if (this.Ï­Ï > 0 && --this.Ï­Ï == 0) {
            this.HorizonCode_Horizon_È("mob.zombiepig.zpigangry", this.ˆÂ() * 2.0f, ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        if (this.ŒÂ > 0 && this.ŠØ != null && this.Çªà() == null) {
            final EntityPlayer var2 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŠØ);
            this.Ý((EntityLivingBase)var2);
            this.Ñ¢Ó = var2;
            this.Ø­Æ = this.¥Å();
        }
        super.ˆØ();
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
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Anger", (short)this.ŒÂ);
        if (this.ŠØ != null) {
            tagCompound.HorizonCode_Horizon_È("HurtBy", this.ŠØ.toString());
        }
        else {
            tagCompound.HorizonCode_Horizon_È("HurtBy", "");
        }
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ŒÂ = tagCompund.Âµá€("Anger");
        final String var2 = tagCompund.áˆºÑ¢Õ("HurtBy");
        if (var2.length() > 0) {
            this.ŠØ = UUID.fromString(var2);
            final EntityPlayer var3 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŠØ);
            this.Ý((EntityLivingBase)var3);
            if (var3 != null) {
                this.Ñ¢Ó = var3;
                this.Ø­Æ = this.¥Å();
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        final Entity var3 = source.áˆºÑ¢Õ();
        if (var3 instanceof EntityPlayer) {
            this.Â(var3);
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    private void Â(final Entity p_70835_1_) {
        this.ŒÂ = 400 + this.ˆáƒ.nextInt(400);
        this.Ï­Ï = this.ˆáƒ.nextInt(40);
        if (p_70835_1_ instanceof EntityLivingBase) {
            this.Ý((EntityLivingBase)p_70835_1_);
        }
    }
    
    public boolean ÇŽÅ() {
        return this.ŒÂ > 0;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.zombiepig.zpig";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.zombiepig.zpighurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.zombiepig.zpigdeath";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(2 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.ŠØ, 1);
        }
        for (int var3 = this.ˆáƒ.nextInt(2 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.Œáƒ, 1);
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        return false;
    }
    
    @Override
    protected void áˆºáˆºáŠ() {
        this.HorizonCode_Horizon_È(Items.ÂµÈ, 1);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final DifficultyInstance p_180481_1_) {
        this.HorizonCode_Horizon_È(0, new ItemStack(Items.ŒÏ));
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        this.ˆÏ­(false);
        return p_180482_2_;
    }
    
    class HorizonCode_Horizon_È extends EntityAIHurtByTarget
    {
        private static final String Â = "CL_00002206";
        
        public HorizonCode_Horizon_È() {
            super(EntityPigZombie.this, true, new Class[0]);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final EntityCreature p_179446_1_, final EntityLivingBase p_179446_2_) {
            super.HorizonCode_Horizon_È(p_179446_1_, p_179446_2_);
            if (p_179446_1_ instanceof EntityPigZombie) {
                ((EntityPigZombie)p_179446_1_).Â(p_179446_2_);
            }
        }
    }
    
    class Â extends EntityAINearestAttackableTarget
    {
        private static final String Ø = "CL_00002207";
        
        public Â() {
            super(EntityPigZombie.this, EntityPlayer.class, true);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return ((EntityPigZombie)this.Âµá€).ÇŽÅ() && super.HorizonCode_Horizon_È();
        }
    }
}
