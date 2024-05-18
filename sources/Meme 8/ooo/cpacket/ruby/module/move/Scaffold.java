package ooo.cpacket.ruby.module.move;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.util.BlockData;
import ooo.cpacket.ruby.util.EntityHelper;
import ooo.cpacket.ruby.util.SpeedUtils;
import ooo.cpacket.ruby.util.Timer;

public class Scaffold extends Module {

	private BlockData blockData;
	private List<Block> blacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava,
			Blocks.flowing_lava, Blocks.anvil, Blocks.hopper, Blocks.furnace);
	private Timer time;
	private Timer time2;
	private int currentItem = -1;
	private float sync = 0.0f;

	public Scaffold(String name, int key, Category category) {
		super(name, key, category);
		this.addModes("Mineplex", "NCP", "AAC");
		this.addNumberOption("MineplexSpeed", 0.6, 0.1, 1.0);
	}

	@Override
	public void onEnable() {
		mc.timer.timerSpeed = 1.0f;
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
	}

	public void onPacket(EventPacket e) {
	}

	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (Scaffold.mc.thePlayer.getHeldItem() == null) {
			return;
		}
		if (blockData != null) {
			float rot[] = EntityHelper.getFacingRotations(this.blockData.position.getX(),
					this.blockData.position.getY() - 1, this.blockData.position.getZ(), this.blockData.face);
			e.setYaw(rot[0]);
			e.setPitch(mc.gameSettings.keyBindJump.pressed ? 90.0F : 81.9F);
		}
		for (int i = 0; i < 9; ++i) {
			if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(36 + i).getHasStack()
					&& Scaffold.mc.thePlayer.inventoryContainer.getSlot(36 + i).getStack()
							.getItem() instanceof ItemBlock) {
				final int currItem = Scaffold.mc.thePlayer.inventory.currentItem;
				final int nextItem = i;
				if (!(Scaffold.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
					Scaffold.mc.thePlayer.inventory.currentItem = nextItem;
				}
			}
		}
		if (Scaffold.mc.thePlayer.getHeldItem() != null
				&& Scaffold.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
			final BlockPos blockBelow = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0,
					Scaffold.mc.thePlayer.posZ);
			if (Scaffold.mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
				this.blockData = this.getBlockData(blockBelow);
				if (this.blockData != null) {
					mc.thePlayer.swingItem();
					if (mc.thePlayer.ticksExisted % 2 == 0) {					}
					if (Scaffold.mc.playerController.func_178890_a(Scaffold.mc.thePlayer, Scaffold.mc.theWorld,
							Scaffold.mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face,
							new Vec3(this.blockData.position.getX(), this.blockData.position.getY(),
									this.blockData.position.getZ()))) {
						if (mc.thePlayer.movementInput.jump) {
							e.setPitch(83.4F);
						}
						else {
							mc.timer.timerSpeed = 1.0f;
						}
					}
				}
			}
		}
	}

	public void onMove(EventRawMove e) {
		if (this.isMode("Mineplex")) {
			SpeedUtils.setMoveSpeed(e, this.blockData != null ? this.getDouble("MineplexSpeed") : 0.2873);
		}
	}

	public BlockData getBlockData(final BlockPos pos) {
		if (Scaffold.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (Scaffold.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (Scaffold.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		return null;
	}

}
