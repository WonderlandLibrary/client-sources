package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ProtocolPipelineImpl extends AbstractSimpleProtocol implements ProtocolPipeline {
   private final UserConnection userConnection;
   private final List<Protocol> protocolList = new CopyOnWriteArrayList<>();
   private final Set<Class<? extends Protocol>> protocolSet = new HashSet<>();
   private List<Protocol> reversedProtocolList = new CopyOnWriteArrayList<>();
   private int baseProtocols;

   public ProtocolPipelineImpl(UserConnection userConnection) {
      this.userConnection = userConnection;
      userConnection.getProtocolInfo().setPipeline(this);
      this.registerPackets();
   }

   @Override
   protected synchronized void registerPackets() {
      Protocol<?, ?, ?, ?> baseProtocol = Via.getManager().getProtocolManager().getBaseProtocol();
      this.protocolList.add(baseProtocol);
      this.reversedProtocolList.add(baseProtocol);
      this.protocolSet.add((Class<? extends Protocol>)baseProtocol.getClass());
      this.baseProtocols++;
   }

   @Override
   public void init(UserConnection userConnection) {
      throw new UnsupportedOperationException("ProtocolPipeline can only be initialized once");
   }

   @Override
   public synchronized void add(Protocol protocol) {
      if (protocol.isBaseProtocol()) {
         this.protocolList.add(this.baseProtocols, protocol);
         this.reversedProtocolList.add(this.baseProtocols, protocol);
         this.baseProtocols++;
      } else {
         this.protocolList.add(protocol);
         this.reversedProtocolList.add(0, protocol);
      }

      this.protocolSet.add((Class<? extends Protocol>)protocol.getClass());
      protocol.init(this.userConnection);
   }

   @Override
   public synchronized void add(Collection<Protocol> protocols) {
      this.protocolList.addAll(protocols);

      for (Protocol protocol : protocols) {
         protocol.init(this.userConnection);
         this.protocolSet.add((Class<? extends Protocol>)protocol.getClass());
      }

      this.refreshReversedList();
   }

   private synchronized void refreshReversedList() {
      List<Protocol> protocols = new ArrayList<>(this.protocolList.subList(0, this.baseProtocols));
      List<Protocol> additionalProtocols = new ArrayList<>(this.protocolList.subList(this.baseProtocols, this.protocolList.size()));
      Collections.reverse(additionalProtocols);
      protocols.addAll(additionalProtocols);
      this.reversedProtocolList = new CopyOnWriteArrayList<>(protocols);
   }

   @Override
   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
      int originalID = packetWrapper.getId();
      DebugHandler debugHandler = Via.getManager().debugHandler();
      if (debugHandler.enabled() && !debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
         this.logPacket(direction, state, packetWrapper, originalID);
      }

      packetWrapper.apply(direction, state, 0, this.protocolListFor(direction));
      super.transform(direction, state, packetWrapper);
      if (debugHandler.enabled() && debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
         this.logPacket(direction, state, packetWrapper, originalID);
      }
   }

   private List<Protocol> protocolListFor(Direction direction) {
      return Collections.unmodifiableList(direction == Direction.SERVERBOUND ? this.protocolList : this.reversedProtocolList);
   }

   private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int originalID) {
      String actualUsername = packetWrapper.user().getProtocolInfo().getUsername();
      String username = actualUsername != null ? actualUsername + " " : "";
      Via.getPlatform()
         .getLogger()
         .log(
            Level.INFO,
            "{0}{1} {2}: {3} ({4}) -> {5} ({6}) [{7}] {8}",
            new Object[]{
               username,
               direction,
               state,
               originalID,
               AbstractSimpleProtocol.toNiceHex(originalID),
               packetWrapper.getId(),
               AbstractSimpleProtocol.toNiceHex(packetWrapper.getId()),
               Integer.toString(this.userConnection.getProtocolInfo().getProtocolVersion()),
               packetWrapper
            }
         );
   }

   @Override
   public boolean contains(Class<? extends Protocol> protocolClass) {
      return this.protocolSet.contains(protocolClass);
   }

   @Nullable
   @Override
   public <P extends Protocol> P getProtocol(Class<P> pipeClass) {
      for (Protocol protocol : this.protocolList) {
         if (protocol.getClass() == pipeClass) {
            return (P)protocol;
         }
      }

      return null;
   }

   @Override
   public List<Protocol> pipes() {
      return Collections.unmodifiableList(this.protocolList);
   }

   @Override
   public List<Protocol> reversedPipes() {
      return Collections.unmodifiableList(this.reversedProtocolList);
   }

   @Override
   public boolean hasNonBaseProtocols() {
      for (Protocol protocol : this.protocolList) {
         if (!protocol.isBaseProtocol()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public synchronized void cleanPipes() {
      this.protocolList.clear();
      this.reversedProtocolList.clear();
      this.protocolSet.clear();
      this.baseProtocols = 0;
      this.registerPackets();
   }

   @Override
   public String toString() {
      return "ProtocolPipelineImpl{protocolList=" + this.protocolList + '}';
   }
}
