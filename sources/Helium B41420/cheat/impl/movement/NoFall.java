package rip.helium.cheat.impl.movement;

import me.hippo.systems.lwjeb.annotation.Collect;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import rip.helium.ClientBase;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.SendPacketEvent;
import rip.helium.notification.mgmt.NotificationManager;
import rip.helium.utils.property.impl.BooleanProperty;
import rip.helium.utils.property.impl.StringsProperty;

public class NoFall extends Cheat {

    public NoFall() {
        super("NoFall", "Negates fall damage on hypickle.", CheatCategory.MOVEMENT);
    }

    boolean canmeme;
    
    public void onEnable() {
    }

    @Collect
    public void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
   
  
  if (this.mc.thePlayer.fallDistance >= 2.75) {
        playerUpdateEvent.setOnGround(true);
        //ClientBase.chat("Packet cancelled.");
    	}
    }
}