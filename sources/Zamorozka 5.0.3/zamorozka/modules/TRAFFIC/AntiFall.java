package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventMove2;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Timerr;

public class AntiFall extends Module {

	Timer2 timer = new Timer2();

	@Override
	public void setup() {
		Zamorozka.instance.settingsManager.rSetting(new Setting("ArmorStandCheck", this, false));
		Zamorozka.instance.settingsManager.rSetting(new Setting("SneakCheck", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("TPDelay", this, 0, 0, 5000, false));
		Zamorozka.instance.settingsManager.rSetting(new Setting("FallDistancer", this, 3.3, 1.0, 30.0, false));
	}

	public AntiFall() {
		super("AntiTrap", 0, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if ((mc.gameSettings.keyBindSneak.isKeyDown() && Zamorozka.settingsManager.getSettingByName("SneakCheck").getValBoolean()))
			return;
		double ff = Zamorozka.settingsManager.getSettingByName("FallDistancer").getValDouble();
		if (Zamorozka.settingsManager.getSettingByName("ArmorStandCheck").getValBoolean()) {
			for (Object o : mc.world.loadedEntityList) {
				Entity e = (Entity) o;
				if (o != null) {
					if (o instanceof EntityArmorStand) {
						if (mc.player.getDistanceToEntity(e) <= ff) {
							float lg = (float) Zamorozka.settingsManager.getSettingByName("TPDelay").getValDouble();
							if (timer.check(lg)) {
								mc.player.jump();
								timer.reset();
							}
						}
					}
				}
			}
		}
		BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - ff, mc.player.posZ);
		Block block = mc.world.getBlockState(blockPos).getBlock();
		if (Block.getIdFromBlock(block) == 58 || Block.getIdFromBlock(block) == 154 || Block.getIdFromBlock(block) == 107 || Block.getIdFromBlock(block) == 23 || Block.getIdFromBlock(block) == 30 || Block.getIdFromBlock(block) == 61
				|| Block.getIdFromBlock(block) == 213 || Block.getIdFromBlock(block) == 131 || Block.getIdFromBlock(block) == 132) {
			float lg = (float) Zamorozka.settingsManager.getSettingByName("TPDelay").getValDouble();
			if (timer.check(lg)) {
				mc.player.jump();
				timer.reset();
			}
		}
	}
}