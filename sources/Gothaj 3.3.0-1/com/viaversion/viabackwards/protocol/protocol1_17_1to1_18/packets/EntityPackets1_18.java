package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;

public final class EntityPackets1_18 extends EntityRewriter<ClientboundPackets1_18, Protocol1_17_1To1_18> {
   public EntityPackets1_18(Protocol1_17_1To1_18 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_17.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.read(Type.VAR_INT);
            this.handler(EntityPackets1_18.this.worldDataTrackerHandler(1));
            this.handler(wrapper -> {
               CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               CompoundTag biomeRegistry = registry.get("minecraft:worldgen/biome");
               ListTag biomes = biomeRegistry.get("value");

               for (Tag biome : biomes.getValue()) {
                  CompoundTag biomeCompound = ((CompoundTag)biome).get("element");
                  StringTag category = biomeCompound.get("category");
                  if (category.getValue().equals("mountain")) {
                     category.setValue("extreme_hills");
                  }

                  biomeCompound.put("depth", new FloatTag(0.125F));
                  biomeCompound.put("scale", new FloatTag(0.05F));
               }

               EntityPackets1_18.this.<EntityTracker>tracker(wrapper.user()).setBiomesSent(biomes.size());
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.STRING);
            this.handler(EntityPackets1_18.this.worldDataTrackerHandler(0));
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         meta.setMetaType(Types1_17.META_TYPES.byId(meta.metaType().typeId()));
         MetaType type = meta.metaType();
         if (type == Types1_17.META_TYPES.particleType) {
            Particle particle = meta.value();
            if (particle.getId() == 3) {
               Particle.ParticleData<?> data = particle.getArguments().remove(0);
               int blockState = (Integer)data.getValue();
               if (blockState == 7786) {
                  particle.setId(3);
               } else {
                  particle.setId(2);
               }

               return;
            }

            this.rewriteParticle(particle);
         }
      });
      this.registerMetaTypeHandler(
         Types1_17.META_TYPES.itemType, null, null, null, Types1_17.META_TYPES.componentType, Types1_17.META_TYPES.optionalComponentType
      );
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_17.getTypeFromId(typeId);
   }
}
