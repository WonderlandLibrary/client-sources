package zamorozka.modules.COMBAT;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.WORLD.FastPlace;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.PlayerController;

public class FastBow extends Module {
	public FastBow() {
		super("FastBow", 0, Category.COMBAT);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("BowTicks", this, 1.5D, 0, 5D, true));
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (getState()) {
			double ticks = Zamorozka.settingsManager.getSettingByName("BowTicks").getValDouble();
		    this.setDisplayName("FastBow §f§ " + "Ticks: " + ClientUtils.round((float)Zamorozka.settingsManager.getSettingByName("BowTicks").getValDouble(), 1));
			if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
				mc.rightClickDelayTimer = 0;
				if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
					if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= ticks) {
						mc.player.connection
								.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM,
										BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
						mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
						mc.player.stopActiveHand();
					}
				}

			}
		}
	}

	@Override
	public void onDisable() {
		mc.rightClickDelayTimer = 6;
		super.onDisable();
	}
}