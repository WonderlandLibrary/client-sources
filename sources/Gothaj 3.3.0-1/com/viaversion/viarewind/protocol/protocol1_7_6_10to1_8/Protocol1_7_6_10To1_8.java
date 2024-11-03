package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.CompressionHandlerProvider;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.compression.TrackingCompressionHandlerProvider;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.rewriter.MetadataRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.rewriter.ReplacementItemRewriter1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.CompressionStatusTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorderEmulator;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.task.WorldBorderUpdateTask;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import java.util.concurrent.TimeUnit;

public class Protocol1_7_6_10To1_8 extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_7_2_5, ServerboundPackets1_8, ServerboundPackets1_7_2_5> {
   private final ReplacementItemRewriter<Protocol1_7_6_10To1_8> itemRewriter = new ReplacementItemRewriter1_7_6_10(this);
   private final MetadataRewriter metadataRewriter = new MetadataRewriter(this);

   public Protocol1_7_6_10To1_8() {
      super(ClientboundPackets1_8.class, ClientboundPackets1_7_2_5.class, ServerboundPackets1_8.class, ServerboundPackets1_7_2_5.class);
   }

   @Override
   protected void registerPackets() {
      this.itemRewriter.register();
      EntityPackets.register(this);
      InventoryPackets.register(this);
      PlayerPackets.register(this);
      ScoreboardPackets.register(this);
      SpawnPackets.register(this);
      WorldPackets.register(this);
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
            this.map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
         }
      });
      this.registerClientbound(
         State.LOGIN, ClientboundLoginPackets.LOGIN_COMPRESSION.getId(), ClientboundLoginPackets.LOGIN_COMPRESSION.getId(), new PacketHandlers() {
            @Override
            public void register() {
               this.handler(wrapper -> {
                  int threshold = wrapper.read(Type.VAR_INT);
                  Via.getManager().getProviders().get(CompressionHandlerProvider.class).onHandleLoginCompressionPacket(wrapper.user(), threshold);
                  wrapper.cancel();
               });
            }
         }
      );
      this.cancelClientbound(ClientboundPackets1_8.SET_COMPRESSION);
      this.registerClientbound(ClientboundPackets1_8.KEEP_ALIVE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT, Type.INT);
         }
      });
      this.registerServerbound(
         State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
               this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
            }
         }
      );
      this.registerServerbound(ServerboundPackets1_7_2_5.KEEP_ALIVE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT, Type.VAR_INT);
         }
      });
   }

   @Override
   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
      Via.getManager().getProviders().get(CompressionHandlerProvider.class).onTransformPacket(packetWrapper.user());
      super.transform(direction, state, packetWrapper);
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.put(new InventoryTracker(userConnection));
      userConnection.put(new EntityTracker1_7_6_10(userConnection, this.metadataRewriter));
      userConnection.put(new PlayerSessionStorage(userConnection));
      userConnection.put(new GameProfileStorage(userConnection));
      userConnection.put(new Scoreboard(userConnection));
      userConnection.put(new CompressionStatusTracker(userConnection));
      userConnection.put(new WorldBorderEmulator(userConnection));
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld());
      }
   }

   @Override
   public void register(ViaProviders providers) {
      providers.register(CompressionHandlerProvider.class, new TrackingCompressionHandlerProvider());
      if (ViaRewind.getConfig().isEmulateWorldBorder()) {
         Via.getManager().getScheduler().scheduleRepeating(new WorldBorderUpdateTask(), 0L, 50L, TimeUnit.MILLISECONDS);
      }
   }

   public ReplacementItemRewriter<Protocol1_7_6_10To1_8> getItemRewriter() {
      return this.itemRewriter;
   }

   public MetadataRewriter getMetadataRewriter() {
      return this.metadataRewriter;
   }
}
