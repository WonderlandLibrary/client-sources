package net.minecraft.src;

public class EntityDamageSource extends DamageSource
{
    protected Entity damageSourceEntity;
    
    public EntityDamageSource(final String par1Str, final Entity par2Entity) {
        super(par1Str);
        this.damageSourceEntity = par2Entity;
    }
    
    @Override
    public Entity getEntity() {
        return this.damageSourceEntity;
    }
    
    @Override
    public String getDeathMessage(final EntityLiving par1EntityLiving) {
        final ItemStack var2 = (this.damageSourceEntity instanceof EntityLiving) ? ((EntityLiving)this.damageSourceEntity).getHeldItem() : null;
        final String var3 = "death.attack." + this.damageType;
        final String var4 = String.valueOf(var3) + ".item";
        return (var2 != null && var2.hasDisplayName() && StatCollector.func_94522_b(var4)) ? StatCollector.translateToLocalFormatted(var4, par1EntityLiving.getTranslatedEntityName(), this.damageSourceEntity.getTranslatedEntityName(), var2.getDisplayName()) : StatCollector.translateToLocalFormatted(var3, par1EntityLiving.getTranslatedEntityName(), this.damageSourceEntity.getTranslatedEntityName());
    }
    
    @Override
    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLiving && !(this.damageSourceEntity instanceof EntityPlayer);
    }
}
