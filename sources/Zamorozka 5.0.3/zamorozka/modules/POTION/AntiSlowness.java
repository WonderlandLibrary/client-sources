package zamorozka.modules.POTION;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AntiSlowness extends Module {

	public AntiSlowness() {
		super("AntiSlowness", 0, Category.POTION);
		// TODO Auto-generated constructor stub
	}
	

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.isPotionActive(Potion.getPotionById(2)) && mc.player.onGround && mc.player.getActivePotionEffect(Potion.getPotionById(2)).getDuration() < 10000) {
            Potion.getPotionById(2).removeAttributesModifiersFromEntity(mc.player, mc.player.getAttributeMap(), 255);
            mc.player.setAIMoveSpeed(0.13000001F);

            for (int i = 0; i < mc.player.getActivePotionEffect(Potion.getPotionById(2)).getDuration() / 20; ++i) {
                mc.getConnection().sendPacket(new CPacketPlayer(mc.player.onGround));
            }
        }
    }
}
