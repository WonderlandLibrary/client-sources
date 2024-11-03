package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Protocol1_19_4To1_19_3
   extends AbstractProtocol<ClientboundPackets1_19_3, ClientboundPackets1_19_4, ServerboundPackets1_19_3, ServerboundPackets1_19_4> {
   public static final MappingData MAPPINGS = new MappingData();
   private final EntityPackets entityRewriter = new EntityPackets(this);
   private final InventoryPackets itemRewriter = new InventoryPackets(this);

   public Protocol1_19_4To1_19_3() {
      super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_4.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      new TagRewriter<>(this).registerGeneric(ClientboundPackets1_19_3.TAGS);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_19_3.STATISTICS);
      SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerSound(ClientboundPackets1_19_3.ENTITY_SOUND);
      soundRewriter.register1_19_3Sound(ClientboundPackets1_19_3.SOUND);
      (new CommandRewriter<ClientboundPackets1_19_3>(this) {
         @Override
         public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
            if (argumentType.equals("minecraft:time")) {
               wrapper.write(Type.INT, 0);
            } else {
               super.handleArgument(wrapper, argumentType);
            }
         }
      }).registerDeclareCommands1_19(ClientboundPackets1_19_3.DECLARE_COMMANDS);
      this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, wrapper -> {
         JsonElement element = wrapper.read(Type.OPTIONAL_COMPONENT);
         if (element != null) {
            wrapper.write(Type.COMPONENT, element);
         } else {
            wrapper.write(Type.COMPONENT, ComponentUtil.emptyJsonComponent());
         }

         String iconBase64 = wrapper.read(Type.OPTIONAL_STRING);
         byte[] iconBytes = null;
         if (iconBase64 != null && iconBase64.startsWith("data:image/png;base64,")) {
            iconBytes = Base64.getDecoder().decode(iconBase64.substring("data:image/png;base64,".length()).getBytes(StandardCharsets.UTF_8));
         }

         wrapper.write(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE, iconBytes);
      });
   }

   @Override
   protected void onMappingDataLoaded() {
      super.onMappingDataLoaded();
      EntityTypes1_19_4.initialize(this);
      Types1_19_4.PARTICLE
         .filler(this)
         .reader("block", ParticleType.Readers.BLOCK)
         .reader("block_marker", ParticleType.Readers.BLOCK)
         .reader("dust", ParticleType.Readers.DUST)
         .reader("falling_dust", ParticleType.Readers.BLOCK)
         .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
         .reader("item", ParticleType.Readers.ITEM1_13_2)
         .reader("vibration", ParticleType.Readers.VIBRATION1_19)
         .reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE)
         .reader("shriek", ParticleType.Readers.SHRIEK);
   }

   @Override
   public void init(UserConnection user) {
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_4.PLAYER));
      user.put(new PlayerVehicleTracker());
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityPackets getEntityRewriter() {
      return this.entityRewriter;
   }

   public InventoryPackets getItemRewriter() {
      return this.itemRewriter;
   }
}
