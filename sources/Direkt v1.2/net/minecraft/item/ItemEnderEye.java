package net.minecraft.item;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemEnderEye extends Item {
	public ItemEnderEye() {
		this.setCreativeTab(CreativeTabs.MISC);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (playerIn.canPlayerEdit(pos.offset(facing), facing, stack) && (iblockstate.getBlock() == Blocks.END_PORTAL_FRAME) && !iblockstate.getValue(BlockEndPortalFrame.EYE).booleanValue()) {
			if (worldIn.isRemote) {
				return EnumActionResult.SUCCESS;
			} else {
				worldIn.setBlockState(pos, iblockstate.withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
				worldIn.updateComparatorOutputLevel(pos, Blocks.END_PORTAL_FRAME);
				--stack.stackSize;

				for (int i = 0; i < 16; ++i) {
					double d0 = pos.getX() + ((5.0F + (itemRand.nextFloat() * 6.0F)) / 16.0F);
					double d1 = pos.getY() + 0.8125F;
					double d2 = pos.getZ() + ((5.0F + (itemRand.nextFloat() * 6.0F)) / 16.0F);
					double d3 = 0.0D;
					double d4 = 0.0D;
					double d5 = 0.0D;
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
				}

				BlockPattern.PatternHelper blockpattern$patternhelper = BlockEndPortalFrame.getOrCreatePortalShape().match(worldIn, pos);

				if (blockpattern$patternhelper != null) {
					BlockPos blockpos = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);

					for (int j = 0; j < 3; ++j) {
						for (int k = 0; k < 3; ++k) {
							worldIn.setBlockState(blockpos.add(j, 0, k), Blocks.END_PORTAL.getDefaultState(), 2);
						}
					}
				}

				return EnumActionResult.SUCCESS;
			}
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);

		if ((raytraceresult != null) && (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) && (worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.END_PORTAL_FRAME)) {
			return new ActionResult(EnumActionResult.PASS, itemStackIn);
		} else {
			if (!worldIn.isRemote) {
				BlockPos blockpos = ((WorldServer) worldIn).getChunkProvider().getStrongholdGen(worldIn, "Stronghold", new BlockPos(playerIn));

				if (blockpos != null) {
					EntityEnderEye entityendereye = new EntityEnderEye(worldIn, playerIn.posX, playerIn.posY + playerIn.height / 2.0F, playerIn.posZ);
					entityendereye.moveTowards(blockpos);
					worldIn.spawnEntityInWorld(entityendereye);
					worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F,
							0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
					worldIn.playEvent((EntityPlayer) null, 1003, new BlockPos(playerIn), 0);

					if (!playerIn.capabilities.isCreativeMode) {
						--itemStackIn.stackSize;
					}

					playerIn.addStat(StatList.getObjectUseStats(this));
					return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
				}
			}

			return new ActionResult(EnumActionResult.FAIL, itemStackIn);
		}
	}
}
