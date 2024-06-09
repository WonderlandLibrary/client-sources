package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMooshroom extends EntityCow
{
    public EntityMooshroom(World worldIn)
    {
        super(worldIn);
        this.setSize(0.9F, 1.4F);
        this.spawnableBlock = Blocks.MYCELIUM;
    }

    public static void func_189791_c(DataFixer p_189791_0_)
    {
        EntityLiving.func_189752_a(p_189791_0_, "MushroomCow");
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
    {
        if (stack != null && stack.getItem() == Items.BOWL && this.getGrowingAge() >= 0 && !player.capabilities.isCreativeMode)
        {
            if (--stack.stackSize == 0)
            {
                player.setHeldItem(hand, new ItemStack(Items.MUSHROOM_STEW));
            }
            else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MUSHROOM_STEW)))
            {
                player.dropItem(new ItemStack(Items.MUSHROOM_STEW), false);
            }

            return true;
        }
        else if (stack != null && stack.getItem() == Items.SHEARS && this.getGrowingAge() >= 0)
        {
            this.setDead();
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);

            if (!this.worldObj.isRemote)
            {
                EntityCow entitycow = new EntityCow(this.worldObj);
                entitycow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                entitycow.setHealth(this.getHealth());
                entitycow.renderYawOffset = this.renderYawOffset;

                if (this.hasCustomName())
                {
                    entitycow.setCustomNameTag(this.getCustomNameTag());
                }

                this.worldObj.spawnEntityInWorld(entitycow);

                for (int i = 0; i < 5; ++i)
                {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + (double)this.height, this.posZ, new ItemStack(Blocks.RED_MUSHROOM)));
                }

                stack.damageItem(1, player);
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
            }

            return true;
        }
        else
        {
            return super.processInteract(player, hand, stack);
        }
    }

    public EntityMooshroom createChild(EntityAgeable ageable)
    {
        return new EntityMooshroom(this.worldObj);
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_MUSHROOM_COW;
    }
}
