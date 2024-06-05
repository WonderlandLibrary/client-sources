package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "AntiVid", renderName = "Void Anti Rectum Simulator", category = Category.COMBAT)
public class AntiVoidf extends Module {

    private final DoubleProperty distance = new DoubleProperty("Distance", 2.5f, 1.0f, 10.0f, 0.05f);

    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener = event -> {
        if(moduleCheckMovement()) return;
      if(event.isPre() && MovementUtils.isOverVoid()){
          if(mc.thePlayer.fallDistance >= distance.getValue()) {
              PacketUtil.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(event.getPosX(), event.getPosY() + 11 + StrictMath.random(), event.getPosZ(), event.getYaw(), event.getPitch(), false));
              mc.thePlayer.fallDistance = 0;
          }
      }
    };
}
