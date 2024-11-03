package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

public class PlayerPackets1_14 extends RewriterBase<Protocol1_13_2To1_14> {
   public PlayerPackets1_14(Protocol1_13_2To1_14 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_14.SERVER_DIFFICULTY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.read(Type.BOOLEAN);
            this.handler(wrapper -> {
               byte difficulty = wrapper.get(Type.UNSIGNED_BYTE, 0).byteValue();
               wrapper.user().get(DifficultyStorage.class).setDifficulty(difficulty);
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_14.OPEN_SIGN_EDITOR, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_14, Type.POSITION1_8);
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_8, Type.POSITION1_14);
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.PLAYER_DIGGING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_8, Type.POSITION1_14);
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int type = wrapper.get(Type.VAR_INT, 0);
               if (type == 0) {
                  wrapper.passthrough(Type.STRING);
               } else if (type == 1) {
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.write(Type.BOOLEAN, false);
                  wrapper.write(Type.BOOLEAN, false);
                  wrapper.write(Type.BOOLEAN, false);
                  wrapper.write(Type.BOOLEAN, false);
               }
            });
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8, Type.POSITION1_14);
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8, Type.POSITION1_14);
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.UPDATE_SIGN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8, Type.POSITION1_14);
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_13.PLAYER_BLOCK_PLACEMENT, wrapper -> {
         Position position = wrapper.read(Type.POSITION1_8);
         int face = wrapper.read(Type.VAR_INT);
         int hand = wrapper.read(Type.VAR_INT);
         float x = wrapper.read(Type.FLOAT);
         float y = wrapper.read(Type.FLOAT);
         float z = wrapper.read(Type.FLOAT);
         wrapper.write(Type.VAR_INT, hand);
         wrapper.write(Type.POSITION1_14, position);
         wrapper.write(Type.VAR_INT, face);
         wrapper.write(Type.FLOAT, x);
         wrapper.write(Type.FLOAT, y);
         wrapper.write(Type.FLOAT, z);
         wrapper.write(Type.BOOLEAN, false);
      });
   }
}
