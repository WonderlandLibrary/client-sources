package net.minecraft.src;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity indirectEntity;
    
    public EntityDamageSourceIndirect(final String par1Str, final Entity par2Entity, final Entity par3Entity) {
        super(par1Str, par2Entity);
        this.indirectEntity = par3Entity;
    }
    
    @Override
    public Entity getSourceOfDamage() {
        return this.damageSourceEntity;
    }
    
    @Override
    public Entity getEntity() {
        return this.indirectEntity;
    }
    
    @Override
    public String getDeathMessage(final EntityLiving par1EntityLiving) {
        final String var2 = (this.indirectEntity == null) ? this.damageSourceEntity.getTranslatedEntityName() : this.indirectEntity.getTranslatedEntityName();
        final ItemStack var3 = (this.indirectEntity instanceof EntityLiving) ? ((EntityLiving)this.indirectEntity).getHeldItem() : null;
        final String var4 = "death.attack." + this.damageType;
        final String var5 = String.valueOf(var4) + ".item";
        return (var3 != null && var3.hasDisplayName() && StatCollector.func_94522_b(var5)) ? StatCollector.translateToLocalFormatted(var5, par1EntityLiving.getTranslatedEntityName(), var2, var3.getDisplayName()) : StatCollector.translateToLocalFormatted(var4, par1EntityLiving.getTranslatedEntityName(), var2);
    }
}
