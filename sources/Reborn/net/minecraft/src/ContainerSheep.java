package net.minecraft.src;

class ContainerSheep extends Container
{
    final EntitySheep field_90034_a;
    
    ContainerSheep(final EntitySheep par1EntitySheep) {
        this.field_90034_a = par1EntitySheep;
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return false;
    }
}
