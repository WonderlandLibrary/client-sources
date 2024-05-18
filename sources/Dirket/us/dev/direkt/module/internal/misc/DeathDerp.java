package us.dev.direkt.module.internal.misc;

import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Death Derp", category = ModCategory.MISC)
public class DeathDerp extends ToggleableModule {

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if (Wrapper.getPlayer().getHealth() == 0) {
            double rand = Math.random() * 16;

            if (rand > 8) {
                rand = Math.random();
            } else {
                rand = -Math.random();
            }
            Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX + rand, Wrapper.getPlayer().posY + 2, Wrapper.getPlayer().posZ + rand);
            Wrapper.getPlayer().rotationYaw += 20F;
            Wrapper.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        }
    });
	
}
