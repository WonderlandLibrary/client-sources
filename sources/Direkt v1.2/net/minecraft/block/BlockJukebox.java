package net.minecraft.block;

import javax.annotation.Nullable;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
	public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");

	public static void func_189873_a(DataFixer p_189873_0_) {
		p_189873_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackData("RecordPlayer", new String[] { "RecordItem" }));
	}

	protected BlockJukebox() {
		super(Material.WOOD, MapColor.DIRT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HAS_RECORD, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (state.getValue(HAS_RECORD).booleanValue()) {
			this.dropRecord(worldIn, pos, state);
			state = state.withProperty(HAS_RECORD, Boolean.valueOf(false));
			worldIn.setBlockState(pos, state, 2);
			return true;
		} else {
			return false;
		}
	}

	public void insertRecord(World worldIn, BlockPos pos, IBlockState state, ItemStack recordStack) {
		if (!worldIn.isRemote) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
				((BlockJukebox.TileEntityJukebox) tileentity).setRecord(recordStack.copy());
				worldIn.setBlockState(pos, state.withProperty(HAS_RECORD, Boolean.valueOf(true)), 2);
			}
		}
	}

	private void dropRecord(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
				BlockJukebox.TileEntityJukebox blockjukebox$tileentityjukebox = (BlockJukebox.TileEntityJukebox) tileentity;
				ItemStack itemstack = blockjukebox$tileentityjukebox.getRecord();

				if (itemstack != null) {
					worldIn.playEvent(1010, pos, 0);
					worldIn.playRecord(pos, (SoundEvent) null);
					blockjukebox$tileentityjukebox.setRecord((ItemStack) null);
					float f = 0.7F;
					double d0 = worldIn.rand.nextFloat() * 0.7F + 0.15000000596046448D;
					double d1 = worldIn.rand.nextFloat() * 0.7F + 0.06000000238418579D + 0.6D;
					double d2 = worldIn.rand.nextFloat() * 0.7F + 0.15000000596046448D;
					ItemStack itemstack1 = itemstack.copy();
					EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, itemstack1);
					entityitem.setDefaultPickupDelay();
					worldIn.spawnEntityInWorld(entityitem);
				}
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		this.dropRecord(worldIn, pos, state);
		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (!worldIn.isRemote) {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new BlockJukebox.TileEntityJukebox();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
			ItemStack itemstack = ((BlockJukebox.TileEntityJukebox) tileentity).getRecord();

			if (itemstack != null) { return (Item.getIdFromItem(itemstack.getItem()) + 1) - Item.getIdFromItem(Items.RECORD_13); }
		}

		return 0;
	}

	/**
	 * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(HAS_RECORD, Boolean.valueOf(meta > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(HAS_RECORD).booleanValue() ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { HAS_RECORD });
	}

	public static class TileEntityJukebox extends TileEntity {
		private ItemStack record;

		@Override
		public void readFromNBT(NBTTagCompound compound) {
			super.readFromNBT(compound);

			if (compound.hasKey("RecordItem", 10)) {
				this.setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("RecordItem")));
			} else if (compound.getInteger("Record") > 0) {
				this.setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record"))));
			}
		}

		@Override
		public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
			super.func_189515_b(p_189515_1_);

			if (this.getRecord() != null) {
				p_189515_1_.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
			}

			return p_189515_1_;
		}

		@Nullable
		public ItemStack getRecord() {
			return this.record;
		}

		public void setRecord(@Nullable ItemStack recordStack) {
			this.record = recordStack;
			this.markDirty();
		}
	}
}
