package me.valk.agway.modules.movement;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.other.EventKeyPress;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.event.events.player.EventSafewalk;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.AimUtils;
import me.valk.utils.TimerUtils;
import me.valk.utils.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module {
	private TimerUtils timerMotion = new TimerUtils();
	public boolean safewalk = true;

	public Scaffold() {
		super(new ModData("Scaffold", 0, new Color(165, 103, 157)), ModType.PLAYER);
	}

	private List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water,
			Blocks.lava, Blocks.flowing_lava, Blocks.anvil, Blocks.chest, Blocks.bed, Blocks.ender_chest,
			Blocks.trapped_chest);

	private TimerUtils timer = new TimerUtils();
	private BlockData blockData;
	boolean placing;
	private int slot;

	@EventListener
	public void onPre(EventMotion event) {
		if (event.getType() == EventType.PRE) {
			int tempSlot = getBlockSlot();
			this.blockData = null;
			this.slot = -1;
			if (!p.isSneaking() && tempSlot != -1) {
				double x2 = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
				double z2 = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
				double xOffset = this.mc.thePlayer.movementInput.moveForward * 0.4 * x2
						+ this.mc.thePlayer.movementInput.moveStrafe * 0.4 * z2;
				double zOffset = this.mc.thePlayer.movementInput.moveForward * 0.4 * z2
						- this.mc.thePlayer.movementInput.moveStrafe * 0.4 * x2;
				double x = mc.thePlayer.posX + xOffset, y = mc.thePlayer.posY - 1, z = mc.thePlayer.posZ + zOffset;
				BlockPos blockBelow1 = new BlockPos(x, y, z);
				if (mc.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air) {
					this.blockData = this.getBlockData(blockBelow1, invalid);
					this.slot = tempSlot;
					if (this.blockData != null) {
						event.getLocation()
								.setYaw(AimUtils.getBlockRotations(this.blockData.position.getX(),
										this.blockData.position.getY(), this.blockData.position.getZ(),
										this.blockData.face)[0]);
						event.getLocation()
								.setPitch(AimUtils.getBlockRotations(this.blockData.position.getX(),
										this.blockData.position.getY(), this.blockData.position.getZ(),
										this.blockData.face)[1]);
					}
				}
			}
		}
	}

	public static BlockData getBlockData(final BlockPos pos, final List list) {
		return list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, -1, 0))
				.getBlock())
						? (list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0))
								.getBlock())
										? (list
												.contains(Minecraft.getMinecraft().theWorld
														.getBlockState(pos.add(1, 0, 0))
														.getBlock())
																? (list.contains(
																		Minecraft.getMinecraft().theWorld
																				.getBlockState(pos.add(0, 0, -1))
																				.getBlock())
																						? (list.contains(Minecraft
																								.getMinecraft().theWorld
																										.getBlockState(
																												pos
																														.add(0, 0,
																																1))
																										.getBlock())
																												? null
																												: new BlockData(
																														pos.add(0,
																																0,
																																1),
																														EnumFacing.NORTH))
																						: new BlockData(
																								pos.add(0, 0, -1),
																								EnumFacing.SOUTH))
																: new BlockData(pos.add(1, 0, 0), EnumFacing.WEST))
										: new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST))
						: new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
	}

	@EventListener
	public void onPress(EventKeyPress e) {
		e.getKey();
		mc.gameSettings.keyBindJump.getKeyCode();
	}

	@EventListener
	public void onPost(EventPlayerUpdate post) {
		post.getType();
		if ((post.getType() == EventType.POST) && (this.blockData != null) && (this.timer.hasReached(75L))
				&& slot != -1) {
			mc.rightClickDelayTimer = 3;
			boolean dohax = p.inventory.currentItem != slot;
			if (dohax)
				p.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
			if (mc.playerController.func_178890_a(p, mc.theWorld, p.inventoryContainer.getSlot(36 + slot).getStack(),
					this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(),
							this.blockData.position.getY(), this.blockData.position.getZ()))) {
				p.swingItem();

			}
			if (dohax)
				p.sendQueue.addToSendQueue(new C09PacketHeldItemChange(p.inventory.currentItem));
		}
	}

	@EventListener
	public void onSafewalk(EventSafewalk e) {
		e.setCancelled(true);

	}

	public static class BlockData {
		public BlockPos position;
		public EnumFacing face;

		public BlockData(BlockPos position, EnumFacing face) {
			this.position = position;
			this.face = face;
		}
	}

	public static Block getBlock(int x, int y, int z) {
		return Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	private int getBlockSlot() {
		for (int i = 36; i < 45; i++) {
			ItemStack itemStack = p.inventoryContainer.getSlot(i).getStack();
			if (itemStack != null && itemStack.getItem() instanceof ItemBlock)
				return i - 36;
		}
		return -1;
	}
}