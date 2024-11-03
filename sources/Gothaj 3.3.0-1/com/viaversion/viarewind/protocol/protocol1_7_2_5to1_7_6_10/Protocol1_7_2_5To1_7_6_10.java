package com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol1_7_2_5To1_7_6_10
   extends AbstractProtocol<ClientboundPackets1_7_2_5, ClientboundPackets1_7_2_5, ServerboundPackets1_7_2_5, ServerboundPackets1_7_2_5> {
   public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Type.STRING) {
      public String transform(PacketWrapper packetWrapper, String s) {
         return s.replace("-", "");
      }
   };

   public Protocol1_7_2_5To1_7_6_10() {
      super(ClientboundPackets1_7_2_5.class, ClientboundPackets1_7_2_5.class, ServerboundPackets1_7_2_5.class, ServerboundPackets1_7_2_5.class);
   }

   @Override
   protected void registerPackets() {
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING, Protocol1_7_2_5To1_7_6_10.REMOVE_DASHES);
            this.map(Type.STRING);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2_5.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.STRING, Protocol1_7_2_5To1_7_6_10.REMOVE_DASHES);
            this.map(Type.STRING);
            this.handler(packetWrapper -> {
               int size = packetWrapper.read(Type.VAR_INT);

               for (int i = 0; i < size; i++) {
                  packetWrapper.read(Type.STRING);
                  packetWrapper.read(Type.STRING);
                  packetWrapper.read(Type.STRING);
               }
            });
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Types1_7_6_10.METADATA_LIST);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2_5.TEAMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE);
            this.handler(packetWrapper -> {
               byte mode = packetWrapper.get(Type.BYTE, 0);
               if (mode == 0 || mode == 2) {
                  packetWrapper.passthrough(Type.STRING);
                  packetWrapper.passthrough(Type.STRING);
                  packetWrapper.passthrough(Type.STRING);
                  packetWrapper.passthrough(Type.BYTE);
               }

               if (mode == 0 || mode == 3 || mode == 4) {
                  List<String> entryList = new ArrayList<>();
                  int size = packetWrapper.read(Type.SHORT);

                  for (int i = 0; i < size; i++) {
                     entryList.add(packetWrapper.read(Type.STRING));
                  }

                  entryList = entryList.stream().map(it -> it.length() > 16 ? it.substring(0, 16) : it).distinct().collect(Collectors.toList());
                  packetWrapper.write(Type.SHORT, (short)entryList.size());

                  for (String entry : entryList) {
                     packetWrapper.write(Type.STRING, entry);
                  }
               }
            });
         }
      });
   }
}
