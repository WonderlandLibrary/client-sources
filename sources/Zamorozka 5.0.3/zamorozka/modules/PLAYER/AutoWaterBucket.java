package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoWaterBucket extends Module {

	public AutoWaterBucket() {
		super("AutoWaterBucket", Keyboard.KEY_NONE, Category.PLAYER);
	}

	private long last = 0L;

	public void onUpdate() {
		if (getState()) {
			if (this.mc.player.fallDistance >= 6F && System.currentTimeMillis() - this.last > 90L) {
				Vec3d posVec = this.mc.player.getPositionVector();
				RayTraceResult result = this.mc.world.rayTraceBlocks(posVec,
						posVec.addVector(0.0D, -8.329999923706055D, 0.0D), true, true, false);
				if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
					EnumHand hand = EnumHand.MAIN_HAND;
					if (this.mc.player.getHeldItemOffhand().getItem() == Items.WATER_BUCKET) {
						hand = EnumHand.OFF_HAND;
					} else if (this.mc.player.getHeldItemMainhand().getItem() != Items.WATER_BUCKET) {
						for (int i = 0; i < 9; i++) {
							if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
								this.mc.player.inventory.currentItem = i;
								this.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, false));
								this.last = System.currentTimeMillis();

								return;
							}
						}
						return;
					}

					this.mc.player.rotationPitch = 90.0F;
					this.mc.playerController.processRightClick((EntityPlayer) this.mc.player, (World) this.mc.world,
							hand);
					this.last = System.currentTimeMillis();
				}
			}
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}