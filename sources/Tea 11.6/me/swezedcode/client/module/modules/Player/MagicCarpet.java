package me.swezedcode.client.module.modules.Player;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Drawable;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Motion.Glide;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.AimUtils;
import me.swezedcode.client.utils.Angles;
import me.swezedcode.client.utils.AnglesUtils;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.Vector3D;
import me.swezedcode.client.utils.Wrapper;
import me.swezedcode.client.utils.block.BlockHelper;
import me.swezedcode.client.utils.block.BlockHelper.BlockData;
import me.swezedcode.client.utils.events.EventKeyPress;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventPostMotionUpdates;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.events.EventSafewalk;
import me.swezedcode.client.utils.timer.Timer;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class MagicCarpet extends Module {

	public MagicCarpet() {
		super("MagicCarpet", Keyboard.KEY_NONE, 0xFF734DFF, ModCategory.Player);
		if (!MemeNames.enabled) {
			setDisplayName(getName());
		} else {
			setDisplayName("PlaceDatBlocksUnderYourPlayar");
		}
	}

	private Vec3i vec;
	private PlayerControllerMP mp;
	private TimerUtils timerMotion = new TimerUtils();
	private Timer timer2 = new Timer();
	public BooleanValue safewalk = new BooleanValue(this, "Safewalk", "safewalk", Boolean.valueOf(true));
	public BooleanValue noswing = new BooleanValue(this, "Noswing", "noswing", Boolean.valueOf(true));
	public BooleanValue timerTower = new BooleanValue(this, "Timer Tower", "timer_tower", Boolean.valueOf(false));
	public BooleanValue slowplacing = new BooleanValue(this, "Slow placing", "slow_placing", Boolean.valueOf(false));
	public BooleanValue needblock = new BooleanValue(this, "Need block in hand", "block_in_hand",
			Boolean.valueOf(false));

	private List<Block> invalid = Arrays.asList(new Block[] { Blocks.air, Blocks.water, Blocks.fire,
			Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava });
	private TimerUtils timer = new TimerUtils();
	private BlockData blockData;
	boolean placing;
	private int slot;

	@EventListener
	public void onPre(EventMotion e) {
		if (!mc.gameSettings.keyBindJump.pressed && !ModuleUtils.getMod(Glide.class).isToggled()
				&& timerTower.getValue()) {
			mc.timer.timerSpeed = 1;
		}
		int tempSlot = getBlockSlot();
		this.blockData = null;
		this.slot = -1;
		if ((this.mc.thePlayer.getHeldItem() != null) && (!this.mc.thePlayer.isSneaking()) && tempSlot != -1
				&& mc.inGameHasFocus) {
			BlockPos blockBelow1 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D,
					this.mc.thePlayer.posZ);
			if (this.mc.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air) {
				mc.rightClickDelayTimer = 0;
				this.blockData = this.getBlockData(blockBelow1);
				this.slot = tempSlot;
				if (this.blockData != null) {
					if (TimerUtils.hD(4.9)) {
						if (mc.gameSettings.keyBindJump.pressed) {
							mc.thePlayer.motionY = 0.381;
							if (timerTower.getValue()) {
								mc.timer.timerSpeed = 2;
							}
						} else if (!mc.gameSettings.keyBindJump.pressed) {
							if (timerTower.getValue()) {
								mc.timer.timerSpeed = 1;
							}
						}
						TimerUtils.rt();
					}
					float yaw = AimUtils.getBlockRotations(this.blockData.position.getX(),
							this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[0];
					float pitch = AimUtils.getBlockRotations(this.blockData.position.getX(),
							this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[1];
					e.getLocation().setYaw(yaw);
					e.getLocation().setPitch(pitch);
				}
			}
		}
	}

	@EventListener
	public void onPost(EventPostMotionUpdates post) {
		if ((this.blockData != null) && slot != -1 && mc.inGameHasFocus) {
			mc.rightClickDelayTimer = 3;
			boolean slowPlace = slowplacing.getValue() && timer2.hasReached(300);
			boolean dohax = mc.thePlayer.inventory.currentItem != slot && !needblock.getValue();
			if (dohax)
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
			if (slowPlace) {
				if (mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld,
						mc.thePlayer.inventoryContainer.getSlot(36 + slot).getStack(), this.blockData.position,
						this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(),
								this.blockData.position.getZ()))) {
					mc.thePlayer.swingItem();
					this.setDisplayName(this.getName() + " §7"
							+ mc.thePlayer.inventoryContainer.getSlot(36 + slot).getStack().stackSize);

				}
				timer2.reset();
			}else{
				if (mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld,
						mc.thePlayer.inventoryContainer.getSlot(36 + slot).getStack(), this.blockData.position,
						this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(),
								this.blockData.position.getZ()))) {
					mc.thePlayer.swingItem();
					this.setDisplayName(this.getName() + " §7"
							+ mc.thePlayer.inventoryContainer.getSlot(36 + slot).getStack().stackSize);

				}
				timer2.reset();
			}
			if (dohax)
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
		}
	}

	@EventListener
	public void onPress(EventKeyPress e) {
		e.getKey();
		mc.gameSettings.keyBindJump.getKeyCode();
	}

	@EventListener
	public void onPre2(EventPreMotionUpdates e5) {
		if (mc.thePlayer.swingProgress <= 0 && noswing.getValue() && !mc.thePlayer.isEating()) {
			mc.thePlayer.swingProgressInt = 5;
		}
		if (!mc.gameSettings.keyBindJump.pressed) {
			mc.timer.timerSpeed = 1;
		}
	}

	@EventListener
	public void onSafewalk(EventSafewalk e) {
		if (safewalk.getValue()) {
			e.setCancelled(true);
		}
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
			ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
			if (itemStack != null && itemStack.getItem() instanceof ItemBlock)
				return i - 36;
		}
		return -1;
	}

	public BlockData getBlockData(BlockPos pos) {
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add = pos.add(-1, 0, 0);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
			return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
			return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
			return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add2 = pos.add(1, 0, 0);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
			return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
			return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
			return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add3 = pos.add(0, 0, -1);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
			return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
			return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
			return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add4 = pos.add(0, 0, 1);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
			return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
			return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
			return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockData blockData = null;

		return blockData;
	}

	@Override
	public void onDisable() {
		mc.rightClickDelayTimer = 6;
		mc.timer.timerSpeed = 1;
		setName("MagicCarpet");
	}

}