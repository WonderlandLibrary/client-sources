package net.minecraft.src;

final class DispenserBehaviorArrow extends BehaviorProjectileDispense
{
    @Override
    protected IProjectile getProjectileEntity(final World par1World, final IPosition par2IPosition) {
        final EntityArrow var3 = new EntityArrow(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        var3.canBePickedUp = 1;
        return var3;
    }
}
