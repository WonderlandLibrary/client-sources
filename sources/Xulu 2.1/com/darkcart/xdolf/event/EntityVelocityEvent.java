package com.darkcart.xdolf.event;

import net.minecraft.network.play.server.SPacketEntityVelocity;


public class EntityVelocityEvent extends EventCancellable {
  @Getter
  @Setter
  private int entityID;
  @Getter
  @Setter
  private SPacketEntityVelocity velocityPacket;

  public EntityVelocityEvent(SPacketEntityVelocity velocityPacket) {
    this.entityID = velocityPacket.getEntityID();
    this.velocityPacket = velocityPacket;
  }
}