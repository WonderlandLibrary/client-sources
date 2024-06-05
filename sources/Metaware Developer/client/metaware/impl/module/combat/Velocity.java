package client.metaware.impl.module.combat;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModuleInfo(name = "Velocity", renderName = "Velocity", aliases = {"AntiKb", "Velocity"}, category = Category.COMBAT)
public class Velocity extends Module {

    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Cancel);
    public DoubleProperty horizontal = new DoubleProperty("Horizontal", 35, 0, 100, 1, () -> mode.getValue() == Mode.Motion);
    public DoubleProperty vertical = new DoubleProperty("Vertical", 35, 0, 100, 1, () -> mode.getValue() == Mode.Motion);

    public enum Mode{
        Motion, Cancel
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    private final Listener<PacketEvent> packetEventListener = event -> {
        setSuffix(mode.getValue() == Mode.Motion ? horizontal.getValue() + " | " + vertical.getValue() : mode.getValue().toString());
      switch(mode.getValue()){
          case Cancel:{
              if(event.getPacket() instanceof S12PacketEntityVelocity){
                  S12PacketEntityVelocity s12 = event.getPacket();
                  event.setCancelled(s12.getEntityID() == mc.thePlayer.getEntityId());
              }

              if(event.getPacket() instanceof S27PacketExplosion){
                  event.setCancelled(true);
              }
              break;
          }
          case Motion: {
              if(event.getPacket() instanceof S12PacketEntityVelocity){
                  S12PacketEntityVelocity s12 = event.getPacket();
                  int vertical = this.vertical.getValue().intValue();
                  int horizontal = this.horizontal.getValue().intValue();
                  if(s12.getEntityID() == mc.thePlayer.getEntityId()) {
                      if (vertical != 0 || horizontal != 0) {
                          s12.setMotionX(horizontal * s12.getMotionX() / 100);
                          s12.setMotionY(vertical * s12.getMotionY() / 100);
                          s12.setMotionZ(horizontal * s12.getMotionZ() / 100);
                      } else {
                          event.setCancelled(true);
                      }
                  }
              }

              if (event.getPacket() instanceof S27PacketExplosion) {
                  event.setCancelled(true);
              }
              break;
          }
      }
    };

}

