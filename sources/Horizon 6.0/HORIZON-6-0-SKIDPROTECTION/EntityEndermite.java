package HORIZON-6-0-SKIDPROTECTION;

public class EntityEndermite extends EntityMob
{
    private int Â;
    private boolean Ý;
    private static final String Ø­Ñ¢Ï­Ø­áˆº = "CL_00002219";
    
    public EntityEndermite(final World worldIn) {
        super(worldIn);
        this.Â = 0;
        this.Ý = false;
        this.à = 3;
        this.HorizonCode_Horizon_È(0.4f, 0.3f);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.1f;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(8.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(2.0);
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.silverfish.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.silverfish.hit";
    }
    
    @Override
    protected String µÊ() {
        return "mob.silverfish.kill";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return null;
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.Â = tagCompund.Ó("Lifetime");
        this.Ý = tagCompund.£á("PlayerSpawned");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Lifetime", this.Â);
        tagCompound.HorizonCode_Horizon_È("PlayerSpawned", this.Ý);
    }
    
    @Override
    public void á() {
        this.¥É = this.É;
        super.á();
    }
    
    public boolean Ø() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175496_1_) {
        this.Ý = p_175496_1_;
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (this.Ï­Ðƒà.ŠÄ) {
            for (int var1 = 0; var1 < 2; ++var1) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, this.Çªà¢ + this.ˆáƒ.nextDouble() * this.£ÂµÄ, this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, (this.ˆáƒ.nextDouble() - 0.5) * 2.0, -this.ˆáƒ.nextDouble(), (this.ˆáƒ.nextDouble() - 0.5) * 2.0, new int[0]);
            }
        }
        else {
            if (!this.ÇªÉ()) {
                ++this.Â;
            }
            if (this.Â >= 2400) {
                this.á€();
            }
        }
    }
    
    @Override
    protected boolean w_() {
        return true;
    }
    
    @Override
    public boolean µà() {
        if (super.µà()) {
            final EntityPlayer var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, 5.0);
            return var1 == null;
        }
        return false;
    }
    
    @Override
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.Ý;
    }
}
