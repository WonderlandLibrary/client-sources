package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventKey;
import zamorozka.event.events.EventMouse;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.Wrapper;

public class MiddleClickPearl extends Module {

	public MiddleClickPearl() {
		super("MiddleClickPearl", Keyboard.KEY_NONE, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventMouse event) {
		if (event.key == 2 && doesNextSlotHavePearl()) {
			EnumHand hand = EnumHand.MAIN_HAND;
			if (this.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL) {
				hand = EnumHand.OFF_HAND;
			} else if (this.mc.player.getHeldItemMainhand().getItem() != Items.ENDER_PEARL) {
				for (int i = 0; i < 9; i++) {
					if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
						mc.player.inventory.currentItem = i;
						mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
					} else {
						if (doesNextSlotHavePearl()) {
							this.mc.player.inventory.currentItem = EntityUtil.getSwordAtHotbar();
						}
					}
				}
			}
		}
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	private boolean doesNextSlotHavePearl() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
				return true;
			}
		}
		return false;
	}

}