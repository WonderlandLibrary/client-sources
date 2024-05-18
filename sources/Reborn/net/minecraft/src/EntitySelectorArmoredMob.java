package net.minecraft.src;

public class EntitySelectorArmoredMob implements IEntitySelector
{
    private final ItemStack field_96567_c;
    
    public EntitySelectorArmoredMob(final ItemStack par1ItemStack) {
        this.field_96567_c = par1ItemStack;
    }
    
    @Override
    public boolean isEntityApplicable(final Entity par1Entity) {
        if (!par1Entity.isEntityAlive()) {
            return false;
        }
        if (!(par1Entity instanceof EntityLiving)) {
            return false;
        }
        final EntityLiving var2 = (EntityLiving)par1Entity;
        return var2.getCurrentItemOrArmor(EntityLiving.getArmorPosition(this.field_96567_c)) == null && (var2.canPickUpLoot() || var2 instanceof EntityPlayer);
    }
}
