package HORIZON-6-0-SKIDPROTECTION;

public class EntityDamageSource extends DamageSource
{
    protected Entity µà;
    private boolean ˆà;
    private static final String ¥Æ = "CL_00001522";
    
    public EntityDamageSource(final String p_i1567_1_, final Entity p_i1567_2_) {
        super(p_i1567_1_);
        this.ˆà = false;
        this.µà = p_i1567_2_;
    }
    
    public EntityDamageSource Æ() {
        this.ˆà = true;
        return this;
    }
    
    public boolean Šáƒ() {
        return this.ˆà;
    }
    
    @Override
    public Entity áˆºÑ¢Õ() {
        return this.µà;
    }
    
    @Override
    public IChatComponent Â(final EntityLivingBase p_151519_1_) {
        final ItemStack var2 = (this.µà instanceof EntityLivingBase) ? ((EntityLivingBase)this.µà).Çª() : null;
        final String var3 = "death.attack." + this.£à;
        final String var4 = String.valueOf(var3) + ".item";
        return (var2 != null && var2.¥Æ() && StatCollector.Ý(var4)) ? new ChatComponentTranslation(var4, new Object[] { p_151519_1_.Ý(), this.µà.Ý(), var2.Çªà¢() }) : new ChatComponentTranslation(var3, new Object[] { p_151519_1_.Ý(), this.µà.Ý() });
    }
    
    @Override
    public boolean ˆà() {
        return this.µà != null && this.µà instanceof EntityLivingBase && !(this.µà instanceof EntityPlayer);
    }
}
