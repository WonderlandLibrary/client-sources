package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {
	public EntityMinecartChest(World worldIn) {
		super(worldIn);
	}

	public EntityMinecartChest(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189681_a(DataFixer p_189681_0_) {
		EntityMinecartContainer.func_189680_b(p_189681_0_, "MinecartChest");
	}

	@Override
	public void killMinecart(DamageSource source) {
		super.killMinecart(source);

		if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			this.dropItemWithOffset(Item.getItemFromBlock(Blocks.CHEST), 1, 0.0F);
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public EntityMinecart.Type getType() {
		return EntityMinecart.Type.CHEST;
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH);
	}

	@Override
	public int getDefaultDisplayTileOffset() {
		return 8;
	}

	@Override
	public String getGuiID() {
		return "minecraft:chest";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		this.addLoot(playerIn);
		return new ContainerChest(playerInventory, this, playerIn);
	}
}
