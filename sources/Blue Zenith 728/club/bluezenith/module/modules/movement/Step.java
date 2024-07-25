package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.StepEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@SuppressWarnings("unused")
public class Step extends Module {

	private final ModeValue mode = new ModeValue("Mode", "Vanilla", true, null, "Vanilla", "NCP").setIndex(1);

	public Step() {
		super("Step", ModuleCategory.MOVEMENT);
	}

	private int ticksPassed = 0;
	private double ypos = 0;
	private double oldMotionX = 0;
	private double oldMotionZ = 0;
	private boolean resetTimer = false;
	private float oldTimer = 1;

	@Listener
	public void sex(MoveEvent e) {
		if (e.isPost()) return;
		if (resetTimer) {
			mc.timer.timerSpeed = 1f;
			resetTimer = false;
		}
		if(!canStep()) {
			player.stepHeight = 0.625F;
			return;
		}
		if (player.isCollidedHorizontally && player.onGround) {
			player.stepHeight = 1.1F;
			//player.motionY = 0.42;
			ticksPassed = 0;
			//mc.timer.timerSpeed = 0.3f;
		}
		ticksPassed++;
	}

	@Listener
	public void onConfirm(StepEvent event) {
		if(player == null) return;
		if ((player.getEntityBoundingBox().minY - player.posY) <= 0.625f) return;

		if (mode.is("NCP")) {
			if ((player.getEntityBoundingBox().minY - player.posY) < 1.1f) {
				oldTimer = mc.timer.timerSpeed;
				mc.timer.timerSpeed = 0.333f;
				resetTimer = true;
				double d6 = 0.41999998688698;
				double d7 = 0.7531999805212;

				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + d6, player.posZ, false));
				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + d7, player.posZ, false));
			}
			/*else if ((player.getEntityBoundingBox().minY - player.posY) < 1.6f) {
				oldTimer = mc.timer.timerSpeed;
				mc.timer.timerSpeed = 0.166f;
				resetTimer = true;

				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 0.42, player.posZ, false));
				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 0.753, player.posZ, false));
				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 1.001, player.posZ, false));
				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 1.084, player.posZ, false));
				PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 1.006, player.posZ, false));
			}*/
		}

		player.stepHeight = 0.625F;
		//mc.timer.timerSpeed = 1F;
	}

	boolean canStep() {
		/*final float yaw = player.rotationYaw;
		final double x = -sin(yaw) * 0.4;
		final double z = cos(yaw) * 0.4;*/
		return world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(player.motionX, 1.0014, player.motionX)).isEmpty() || world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(player.motionX, 1.5014, player.motionX)).isEmpty();
	}

	@Override
	public String getTag() {
		return this.mode.get();
	}
}
