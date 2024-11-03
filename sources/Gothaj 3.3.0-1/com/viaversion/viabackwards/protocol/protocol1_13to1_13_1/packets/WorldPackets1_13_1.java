package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPackets1_13_1 {
   public static void register(final Protocol1_13To1_13_1 protocol) {
      BlockRewriter<ClientboundPackets1_13> blockRewriter = BlockRewriter.legacy(protocol);
      protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         Chunk chunk = wrapper.passthrough(ChunkType1_13.forEnvironment(clientWorld.getEnvironment()));

         for (ChunkSection section : chunk.getSections()) {
            if (section != null) {
               DataPalette palette = section.palette(PaletteType.BLOCKS);

               for (int i = 0; i < palette.size(); i++) {
                  int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
                  palette.setIdByIndex(i, mappedBlockStateId);
               }
            }
         }
      });
      blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
      protocol.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               int data = wrapper.get(Type.INT, 1);
               if (id == 1010) {
                  wrapper.set(Type.INT, 1, protocol.getMappingData().getNewItemId(data));
               } else if (id == 2001) {
                  wrapper.set(Type.INT, 1, protocol.getMappingData().getNewBlockStateId(data));
               } else if (id == 2000) {
                  switch (data) {
                     case 0:
                     case 1:
                        Position pos = wrapper.get(Type.POSITION1_8, 0);
                        BlockFace relative = data == 0 ? BlockFace.BOTTOM : BlockFace.TOP;
                        wrapper.set(Type.POSITION1_8, 0, pos.getRelative(relative));
                        wrapper.set(Type.INT, 1, 4);
                        break;
                     case 2:
                        wrapper.set(Type.INT, 1, 1);
                        break;
                     case 3:
                        wrapper.set(Type.INT, 1, 7);
                        break;
                     case 4:
                        wrapper.set(Type.INT, 1, 3);
                        break;
                     case 5:
                        wrapper.set(Type.INT, 1, 5);
                  }
               }
            });
         }
      });
   }
}
