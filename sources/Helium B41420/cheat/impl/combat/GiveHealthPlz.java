package rip.helium.cheat.impl.combat;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerJumpEvent;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.utils.property.impl.StringsProperty;

public class GiveHealthPlz extends Cheat
{
    public StringsProperty mode;

    public GiveHealthPlz() {
        super("GiveHealthPlz", "yes", CheatCategory.COMBAT);
        this.mode = new StringsProperty("Mode", "", null, false, true, new String[] { "epic" }, new Boolean[] { true });
    }

    @Collect
    public void onUpdate(final PlayerUpdateEvent e) {
        if (this.mc.thePlayer.getHealth() < 14.0f && this.mc.thePlayer.isCollidedVertically) {
            for (int i = 0; i < 10; ++i) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
            }
        }
    }

}