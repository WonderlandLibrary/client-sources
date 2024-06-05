package zamorozka.modules.WORLD;

import com.google.common.eventbus.Subscribe;

import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.types.EventType;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoFish extends Module {

	public AutoFish() {
		super("AutoFish", 0, Category.WORLD);
	}
	
	@EventTarget
	public void receivePacket(EventReceivePacket event) {
		if(event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if(packet.getCategory() == SoundCategory.NEUTRAL && packet.getSound() == SoundEvents.ENTITY_BOBBER_SPLASH) {
                final Minecraft mc = Minecraft.getMinecraft();

                if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod || mc.player.getHeldItemOffhand().getItem() instanceof ItemFishingRod) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }
}