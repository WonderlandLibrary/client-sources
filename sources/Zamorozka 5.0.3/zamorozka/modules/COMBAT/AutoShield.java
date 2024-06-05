package zamorozka.modules.COMBAT;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.MouseAttackEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.AimUtil;
import zamorozka.ui.MouseUtilis;
import zamorozka.ui.TimeHelper;
import zamorozka.ui.TimerHelper;

public class AutoShield extends Module {

	EntityLivingBase target = KillAura.target;

	public AutoShield() {
		super("AutoShield", 0, Category.COMBAT);
	}

	@EventTarget
	public void onPreMotion(EventPreMotionUpdates event) {
		try {
		if (AimUtil.isAimAtMe(target) && target != null) {
			blockByShield(true);
		} else {
			if (!(mc.player.isBowing() || mc.player.isEating() || mc.player.isDrinking())) {
				blockByShield(false);
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void blockByShield(boolean state) {
		if (mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
			mc.gameSettings.keyBindUseItem.pressed = state;
		}
	}

	public void onDisable() {
		this.blockByShield(false);
		super.onDisable();
	}
}