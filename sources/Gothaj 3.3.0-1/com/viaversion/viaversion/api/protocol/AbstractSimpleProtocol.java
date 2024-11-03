package com.viaversion.viaversion.api.protocol;

public abstract class AbstractSimpleProtocol
   extends AbstractProtocol<SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes>
   implements SimpleProtocol {
   protected AbstractSimpleProtocol() {
      super(null, null, null, null);
   }
}
