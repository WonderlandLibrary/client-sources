package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class RecipesMapExtending extends ShapedRecipes
{
    public RecipesMapExtending()
    {
        super(3, 3, new ItemStack[] {new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.FILLED_MAP, 1, 32767), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER)}, new ItemStack(Items.MAP));
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        if (!super.matches(inv, worldIn))
        {
            return false;
        }
        else
        {
            ItemStack itemstack = ItemStack.field_190927_a;

            for (int i = 0; i < inv.getSizeInventory() && itemstack.func_190926_b(); ++i)
            {
                ItemStack itemstack1 = inv.getStackInSlot(i);

                if (itemstack1.getItem() == Items.FILLED_MAP)
                {
                    itemstack = itemstack1;
                }
            }

            if (itemstack.func_190926_b())
            {
                return false;
            }
            else
            {
                MapData mapdata = Items.FILLED_MAP.getMapData(itemstack, worldIn);
                return mapdata == null ? false : (this.func_190934_a(mapdata) ? false : mapdata.scale < 4);
            }
        }
    }

    private boolean func_190934_a(MapData p_190934_1_)
    {
        if (p_190934_1_.mapDecorations != null)
        {
            for (MapDecoration mapdecoration : p_190934_1_.mapDecorations.values())
            {
                if (mapdecoration.func_191179_b() == MapDecoration.Type.MANSION || mapdecoration.func_191179_b() == MapDecoration.Type.MONUMENT)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = ItemStack.field_190927_a;

        for (int i = 0; i < inv.getSizeInventory() && itemstack.func_190926_b(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (itemstack1.getItem() == Items.FILLED_MAP)
            {
                itemstack = itemstack1;
            }
        }

        itemstack = itemstack.copy();
        itemstack.func_190920_e(1);

        if (itemstack.getTagCompound() == null)
        {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        itemstack.getTagCompound().setInteger("map_scale_direction", 1);
        return itemstack;
    }
}
