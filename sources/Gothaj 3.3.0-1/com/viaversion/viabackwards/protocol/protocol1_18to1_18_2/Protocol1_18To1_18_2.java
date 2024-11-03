package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data.CommandRewriter1_18_2;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;

public final class Protocol1_18To1_18_2
   extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17> {
   public Protocol1_18To1_18_2() {
      super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
   }

   @Override
   protected void registerPackets() {
      new CommandRewriter1_18_2(this).registerDeclareCommands(ClientboundPackets1_18.DECLARE_COMMANDS);
      final PacketHandler entityEffectIdHandler = wrapper -> {
         int id = wrapper.read(Type.VAR_INT);
         if ((byte)id != id) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               ViaBackwards.getPlatform().getLogger().warning("Cannot send entity effect id " + id + " to old client");
            }

            wrapper.cancel();
         } else {
            wrapper.write(Type.BYTE, (byte)id);
         }
      };
      this.registerClientbound(ClientboundPackets1_18.ENTITY_EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(entityEffectIdHandler);
         }
      });
      this.registerClientbound(ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(entityEffectIdHandler);
         }
      });
      this.registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.handler(wrapper -> {
               CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               CompoundTag dimensionsHolder = registry.get("minecraft:dimension_type");

               for (Tag dimension : (ListTag)dimensionsHolder.get("value")) {
                  Protocol1_18To1_18_2.this.removeTagPrefix(((CompoundTag)dimension).get("element"));
               }

               Protocol1_18To1_18_2.this.removeTagPrefix(wrapper.get(Type.NAMED_COMPOUND_TAG, 1));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_18.RESPAWN, wrapper -> this.removeTagPrefix(wrapper.passthrough(Type.NAMED_COMPOUND_TAG)));
   }

   private void removeTagPrefix(CompoundTag tag) {
      Tag infiniburnTag = tag.get("infiniburn");
      if (infiniburnTag instanceof StringTag) {
         StringTag infiniburn = (StringTag)infiniburnTag;
         infiniburn.setValue(infiniburn.getValue().substring(1));
      }
   }
}
