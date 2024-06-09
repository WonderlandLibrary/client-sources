package HORIZON-6-0-SKIDPROTECTION;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity ˆà;
    private static final String ¥Æ = "CL_00001523";
    
    public EntityDamageSourceIndirect(final String p_i1568_1_, final Entity p_i1568_2_, final Entity p_i1568_3_) {
        super(p_i1568_1_, p_i1568_2_);
        this.ˆà = p_i1568_3_;
    }
    
    @Override
    public Entity áŒŠÆ() {
        return this.µà;
    }
    
    @Override
    public Entity áˆºÑ¢Õ() {
        return this.ˆà;
    }
    
    @Override
    public IChatComponent Â(final EntityLivingBase p_151519_1_) {
        final IChatComponent var2 = (this.ˆà == null) ? this.µà.Ý() : this.ˆà.Ý();
        final ItemStack var3 = (this.ˆà instanceof EntityLivingBase) ? ((EntityLivingBase)this.ˆà).Çª() : null;
        final String var4 = "death.attack." + this.£à;
        final String var5 = String.valueOf(var4) + ".item";
        return (var3 != null && var3.¥Æ() && StatCollector.Ý(var5)) ? new ChatComponentTranslation(var5, new Object[] { p_151519_1_.Ý(), var2, var3.Çªà¢() }) : new ChatComponentTranslation(var4, new Object[] { p_151519_1_.Ý(), var2 });
    }
}
