package zamorozka.modules.PLAYER;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventChatMessage;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;

public class AntiTrap extends Module {

	double fall = 0;

	public AntiTrap() {
		super("AntiVoid", 0, Category.PLAYER);
	}
	
	@EventTarget
	public void onChatDisable(EventChatMessage event) {
		if(event.getMessage().contains("проверка,")) {
			ModuleManager.getModule(AntiTrap.class).setState(false);
			NotificationPublisher.queue("Module", "AntiVoid was disabled because of BotFilter!", NotificationType.SUCCESS);
		}
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		boolean blockUnderneath = false;
		int checkedY = 0;
		int i = 0;
		while ((double) i < this.mc.player.posY + 2.0) {
			BlockPos pos = new BlockPos(this.mc.player.posX, (double) i, this.mc.player.posZ);
			if (!(this.mc.world.getBlockState(pos).getBlock() instanceof BlockAir)) {
				blockUnderneath = true;
				checkedY = i;
			}
			++i;
		}

		if (blockUnderneath) {
			BlockPos pos = new BlockPos(this.mc.player.posX, (double) checkedY, this.mc.player.posZ);
			if (this.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid
					|| this.mc.world.getBlockState(pos).getBlock() instanceof BlockDynamicLiquid) {
				int antiLiquid = 0;
				while (antiLiquid < this.mc.player.posY) {
					BlockPos posTest = new BlockPos(this.mc.player.posX, (double) this.mc.player.posY - antiLiquid,
							this.mc.player.posZ);
					Block block = mc.world.getBlockState(posTest).getBlock();
					if (!(block instanceof BlockLiquid) && !(block instanceof BlockDynamicLiquid)
							&& !(block instanceof BlockAir)) {
						return;
					}
					antiLiquid++;
				}
			} else {
				return;
			}
		}

		if (!this.mc.player.onGround && !this.mc.player.isCollidedVertically) {

			if (mc.player.motionY < -0.08D) {
				this.fall -= mc.player.motionY;
			}

			if (this.fall > 7D) {
				this.fall = 0.0D;
				mc.player.jump();
				this.mc.player.fallDistance = 0;
			}

		} else {
			fall = 0;
		}
	}
}