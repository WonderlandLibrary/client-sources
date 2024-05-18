package net.minecraft.src;

final class DispenserBehaviorEgg extends BehaviorProjectileDispense
{
    @Override
    protected IProjectile getProjectileEntity(final World par1World, final IPosition par2IPosition) {
        return new EntityEgg(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
