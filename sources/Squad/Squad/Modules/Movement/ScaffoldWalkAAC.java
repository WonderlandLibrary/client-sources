package Squad.Modules.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.BlockData;
import Squad.Utils.EntityHelper;
import Squad.Utils.TimeHelper;
import Squad.Utils.Wrapper;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3d;

public class ScaffoldWalkAAC extends Module {

	public ScaffoldWalkAAC() {
		super("ScaffoldWalk", Keyboard.KEY_V, 0xFFFFFF, Category.World);
	}

	TimeHelper sneak = new TimeHelper();
	static int sneakdelay = 0;
	public static float yaw;
	public static float pitch;
	private BlockData blockData = null;
	public static boolean isenabled = false;

	public void setup() {
		ArrayList<String> options1 = new ArrayList<>();
		options1.add("Hypixel");
		options1.add("AAC");
		options1.add("HypixelSpeed");

		Squad.instance.setmgr.rSetting(new Setting("ScaffoldMode", this, "AAC", options1));

		Squad.instance.setmgr.rSetting(new Setting("SprintScaffold", this, false));
	}

	@EventTarget
	public void onTick(EventUpdate e) {
		BlockPos belowPlayer = new BlockPos(Wrapper.getPlayer()).down();
		if (!Wrapper.getBlock(belowPlayer).getMaterial().isReplaceable()
				|| !(Wrapper.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemBlock))
			return;

		if (Squad.instance.setmgr.getSettingByName("SprintScaffold").getValBoolean()) {
			mc.thePlayer.setSprinting(true);
		} else {
			mc.thePlayer.setSprinting(false);
			Squad.instance.moduleManager.getModuleByName("Sprint").toggled = false;
		}
		if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("AAC")) {
			setDisplayname("ScaffoldWalk §7AAC");
			placeBlockLegit(belowPlayer);
		}
		if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("Hypixel")) {
			setDisplayname("ScaffoldWalk §7Hypixel");
			hywichsel();
		}
		if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("HypixelSpeed")) {
			setDisplayname("ScaffoldWalk §7HypixelSpeed");
			hypixel();

		}

	}

	public static boolean placeBlockLegit(BlockPos pos) {
		Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX,
				Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
		for (EnumFacing side : EnumFacing.values()) {
			BlockPos neighbor = pos.offset(side);
			EnumFacing side2 = side.getOpposite();

			if (!Wrapper.getBlock(neighbor).canCollideCheck(Wrapper.getWorld().getBlockState(neighbor), false))
				continue;

			Vec3d hitVec = new Vec3d(neighbor).addVector(0.5, 0.5, 0.5)
					.add(new Vec3d(side2.getDirectionVec()).scale(0.5));

			if (eyesPos.squareDistanceTo(hitVec) > 20.0625)
				continue;
			faceVectorPacket(hitVec);

			sneakdelay++;
			if (sneakdelay >= 4) {
				Wrapper.getPlayerController().processRightClickBlock(Wrapper.getPlayer(), Wrapper.getWorld(),
						Wrapper.getPlayer().getCurrentEquippedItem(), neighbor, side2, hitVec);
				Wrapper.getMC().rightClickDelayTimer = 4;
				Wrapper.getPlayer().swingItem();
				sneakdelay = 0;

			}

			return true;

		}

		return false;
	}

	private static void faceVectorPacket(Vec3d vec) {
		double diffX = vec.xCoord - Wrapper.getPlayer().posX;
		double diffY = vec.yCoord - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		double diffZ = vec.zCoord - Wrapper.getPlayer().posZ;

		double dist = MathHelper.sqrt2(diffX * diffX + diffZ * diffZ);

		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float) -Math.toDegrees(Math.atan2(diffY, dist));

		Wrapper.getNetHandler()
				.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Wrapper.getPlayer().onGround));
	}

	public void onEnable() {

	}

	public void onDisable() {
		mc.timer.timerSpeed = 1F;

	}

	public void hypixel() {

		this.blockData = null;
		try {
			if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("HypixelSpeed")) {
				mc.timer.timerSpeed = 1.6F;
				if ((mc.thePlayer.getHeldItem() != null) && (!mc.thePlayer.isSneaking())
						&& ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) {
					BlockPos blockBelow = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
					if (mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
						this.blockData = getBlockData1(blockBelow);
						if (this.blockData != null) {
							float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
									this.blockData.position.getY(), this.blockData.position.getZ(),
									this.blockData.face);
							mc.thePlayer.swingItem();
							mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(0, 60, true));

							if (mc.gameSettings.keyBindJump.pressed || mc.gameSettings.keyBindLeft.pressed
									|| mc.gameSettings.keyBindRight.pressed) {
								return;
							}
						}
					}
				}
			}
		} catch (Exception exe) {
		}

		{
			if (this.blockData == null) {
				return;
			}

			{

				{
					float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
							this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
					mc.thePlayer.swingItem();
					sneakdelay = 0;
					mc.thePlayer.motionX *= 2.15;
					mc.thePlayer.motionZ *= 2.15;

					try {
						if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
								mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face,
								new Vec3(this.blockData.position.getX(), this.blockData.position.getY(),
										this.blockData.position.getZ()))) {
							if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("HypixelSpeed")) {
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.onGround = true;
								final EntityPlayerSP tp1 = mc.thePlayer;
								tp1.motionX *= 0.1;
								mc.thePlayer.onGround = false;
								final EntityPlayerSP tp2 = mc.thePlayer;
								tp2.motionZ *= 0.1;
								mc.thePlayer.onGround = true;
							}
						}
					} catch (Exception ex) {
					}
				}
			}

			{
				mc.thePlayer.swingItem();

				sneakdelay = 0;
				mc.thePlayer.motionX *= 0.1;
				mc.thePlayer.motionZ *= 0.1;
				mc.thePlayer.swingItem();
				if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(),
						this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(),
								this.blockData.position.getY(), this.blockData.position.getZ()))) {
					if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("HypixelSpeed")) {
						mc.thePlayer.swingItem();
						mc.thePlayer.onGround = true;
						final EntityPlayerSP tp1 = mc.thePlayer;
						tp1.motionX *= 0.1;
						mc.thePlayer.onGround = false;
						final EntityPlayerSP tp2 = mc.thePlayer;
						tp2.motionZ *= 0.1;
						mc.thePlayer.onGround = true;
					}
				}
			}
		}

		if (this.blockData == null) {
			return;
		}
		float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(),
				this.blockData.position.getZ(), this.blockData.face);
		if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("Hypixel")) {
			mc.thePlayer.onGround = true;
			final EntityPlayerSP tp1 = mc.thePlayer;
			tp1.motionX *= 0.1;
			mc.thePlayer.swingItem();
			mc.thePlayer.onGround = false;
			final EntityPlayerSP tp2 = mc.thePlayer;
			tp2.motionZ *= 0.1;
			mc.thePlayer.onGround = true;
		}
	}

	public BlockData getBlockData1(BlockPos pos) {
		if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		return null;

	}

	public void HypixelSpeedFast() {
		this.blockData = null;
		try {
			if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("Hypixel")) {
				if ((mc.thePlayer.getHeldItem() != null) && (!mc.thePlayer.isSneaking())
						&& ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) {
					BlockPos blockBelow = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
					if (mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
						this.blockData = getBlockData1(blockBelow);
						if (this.blockData != null) {
							float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
									this.blockData.position.getY(), this.blockData.position.getZ(),
									this.blockData.face);
							mc.thePlayer.swingItem();
							mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(0, 60, true));

							if (mc.gameSettings.keyBindJump.pressed || mc.gameSettings.keyBindLeft.pressed
									|| mc.gameSettings.keyBindRight.pressed) {
								return;
							}
						}
					}
				}
			}
		} catch (Exception exe) {
		}

		{
			if (this.blockData == null) {
				return;
			}

			{

				{
					float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
							this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
					mc.thePlayer.swingItem();
					sneakdelay = 0;
					mc.thePlayer.motionX *= 2.15;
					mc.thePlayer.motionZ *= 2.15;

					try {
						if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
								mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face,
								new Vec3(this.blockData.position.getX(), this.blockData.position.getY(),
										this.blockData.position.getZ()))) {
							if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString()
									.equalsIgnoreCase("Hypixel")) {
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.onGround = true;
								final EntityPlayerSP tp1 = mc.thePlayer;
								tp1.motionX *= 0.1;
								mc.thePlayer.onGround = false;
								final EntityPlayerSP tp2 = mc.thePlayer;
								tp2.motionZ *= 0.1;
								mc.thePlayer.onGround = true;
							} 
						}
					} catch (Exception ex) {
					}
				}
			}

			{
				mc.thePlayer.swingItem();

				sneakdelay = 0;
				mc.thePlayer.motionX *= 0.1;
				mc.thePlayer.motionZ *= 0.1;
				mc.thePlayer.swingItem();
				if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(),
						this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(),
								this.blockData.position.getY(), this.blockData.position.getZ()))) {
					if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString()
							.equalsIgnoreCase("Hypixel")) {
						mc.thePlayer.swingItem();
						mc.thePlayer.onGround = true;
						final EntityPlayerSP tp1 = mc.thePlayer;
						tp1.motionX *= 0.1;
						mc.thePlayer.onGround = false;
						final EntityPlayerSP tp2 = mc.thePlayer;
						tp2.motionZ *= 0.1;
						mc.thePlayer.onGround = true;
					} else {
						mc.gameSettings.keyBindSneak.pressed = false;
					}
				}
			}
		}

		if (this.blockData == null) {
			return;
		}
		float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(), this.blockData.position.getY(),
				this.blockData.position.getZ(), this.blockData.face);
		if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("Hypixel")) {
			mc.thePlayer.onGround = true;
			final EntityPlayerSP tp1 = mc.thePlayer;
			tp1.motionX *= 0.1;
			mc.thePlayer.swingItem();
			mc.thePlayer.onGround = false;
			final EntityPlayerSP tp2 = mc.thePlayer;
			tp2.motionZ *= 0.1;
			mc.thePlayer.onGround = true;
		}
	}

	public BlockData getBlockData11(BlockPos pos) {
		if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		return null;

	}

	public void hywichsel() {
		this.blockData = null;
		try {
			if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("Hypixel")) {
				if ((mc.thePlayer.getHeldItem() != null) && (!mc.thePlayer.isSneaking())
						&& ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) {
					BlockPos blockBelow = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
					if (mc.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air) {
						this.blockData = getBlockData1(blockBelow);
						if (this.blockData != null) {
							float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
									this.blockData.position.getY(), this.blockData.position.getZ(),
									this.blockData.face);
							mc.thePlayer.swingItem();
							mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(0, 60, true));

							if (mc.gameSettings.keyBindJump.pressed || mc.gameSettings.keyBindLeft.pressed
									|| mc.gameSettings.keyBindRight.pressed) {
								return;
							}
						}
					}
				}
			}
		} catch (Exception exe) {
		}

		{
			if (this.blockData == null) {
				return;
			}

			{

				{
					float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
							this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
					mc.thePlayer.swingItem();
					sneakdelay = 0;
					mc.thePlayer.motionX *= 2.15;
					mc.thePlayer.motionZ *= 2.15;

					try {
						if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
								mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face,
								new Vec3(this.blockData.position.getX(), this.blockData.position.getY(),
										this.blockData.position.getZ()))) {
							if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString()
									.equalsIgnoreCase("Hypixel")) {
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.swingItem();
								mc.thePlayer.onGround = true;
								final EntityPlayerSP tp1 = mc.thePlayer;
								tp1.motionX *= 0.1;
								mc.thePlayer.onGround = false;
								final EntityPlayerSP tp2 = mc.thePlayer;
								tp2.motionZ *= 0.1;
								mc.thePlayer.onGround = true;
							}
						}
					} catch (Exception ex) {
					}
				}
			}

			{
				mc.thePlayer.swingItem();

				sneakdelay = 0;
				mc.thePlayer.motionX *= 0.1;
				mc.thePlayer.motionZ *= 0.1;
				mc.thePlayer.swingItem();
				if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(),
						this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(),
								this.blockData.position.getY(), this.blockData.position.getZ()))) {
					if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString()
							.equalsIgnoreCase("Hypixel")) {

						mc.thePlayer.swingItem();
						mc.thePlayer.onGround = true;
						final EntityPlayerSP tp1 = mc.thePlayer;
						tp1.motionX *= 0.1;
						mc.thePlayer.onGround = false;
						final EntityPlayerSP tp2 = mc.thePlayer;
						tp2.motionZ *= 0.1;
						mc.thePlayer.onGround = true;

					}
				}
			}

			if (this.blockData == null) {
				return;
			}
			float[] values = EntityHelper.getFacingRotations(this.blockData.position.getX(),
					this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face);
			if (Squad.instance.setmgr.getSettingByName("ScaffoldMode").getValString().equalsIgnoreCase("Hypixel")) {
				mc.thePlayer.onGround = true;
				final EntityPlayerSP tp1 = mc.thePlayer;
				tp1.motionX *= 0.1;
				mc.thePlayer.swingItem();
				mc.thePlayer.onGround = false;
				final EntityPlayerSP tp2 = mc.thePlayer;
				tp2.motionZ *= 0.1;
				mc.thePlayer.onGround = true;
			}
		}
	}

	public BlockData getBlockData(BlockPos pos) {
		if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		return null;

	}


	}

