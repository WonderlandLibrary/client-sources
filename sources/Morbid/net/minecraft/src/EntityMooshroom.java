package net.minecraft.src;

public class EntityMooshroom extends EntityCow
{
    public EntityMooshroom(final World par1World) {
        super(par1World);
        this.texture = "/mob/redcow.png";
        this.setSize(0.9f, 1.3f);
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.itemID == Item.bowlEmpty.itemID && this.getGrowingAge() >= 0) {
            if (var2.stackSize == 1) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bowlSoup));
                return true;
            }
            if (par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bowlSoup)) && !par1EntityPlayer.capabilities.isCreativeMode) {
                par1EntityPlayer.inventory.decrStackSize(par1EntityPlayer.inventory.currentItem, 1);
                return true;
            }
        }
        if (var2 != null && var2.itemID == Item.shears.itemID && this.getGrowingAge() >= 0) {
            this.setDead();
            this.worldObj.spawnParticle("largeexplode", this.posX, this.posY + this.height / 2.0f, this.posZ, 0.0, 0.0, 0.0);
            if (!this.worldObj.isRemote) {
                final EntityCow var3 = new EntityCow(this.worldObj);
                var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                var3.setEntityHealth(this.getHealth());
                var3.renderYawOffset = this.renderYawOffset;
                this.worldObj.spawnEntityInWorld(var3);
                for (int var4 = 0; var4 < 5; ++var4) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + this.height, this.posZ, new ItemStack(Block.mushroomRed)));
                }
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }
    
    public EntityMooshroom func_94900_c(final EntityAgeable par1EntityAgeable) {
        return new EntityMooshroom(this.worldObj);
    }
    
    @Override
    public EntityCow spawnBabyAnimal(final EntityAgeable par1EntityAgeable) {
        return this.func_94900_c(par1EntityAgeable);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.func_94900_c(par1EntityAgeable);
    }
}
