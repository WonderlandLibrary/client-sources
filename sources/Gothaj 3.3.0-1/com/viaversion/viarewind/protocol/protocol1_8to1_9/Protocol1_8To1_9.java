package com.viaversion.viarewind.protocol.protocol1_8to1_9;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.packets.ScoreboardPackets;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.rewriter.ReplacementItemRewriter1_8;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import com.viaversion.viarewind.utils.Ticker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Protocol1_8To1_9 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> {
   private final ReplacementItemRewriter<Protocol1_8To1_9> itemRewriter = new ReplacementItemRewriter1_8(this);
   private final MetadataRewriter metadataRewriter = new MetadataRewriter(this);
   public Queue<PacketWrapper> animationsToSend = new ConcurrentLinkedQueue<>();
   public static final Set<String> VALID_ATTRIBUTES = new HashSet<>();
   public static final ValueTransformer<Double, Integer> TO_OLD_INT = new ValueTransformer<Double, Integer>(Type.INT) {
      public Integer transform(PacketWrapper wrapper, Double inputValue) {
         return (int)(inputValue * 32.0);
      }
   };
   public static final ValueTransformer<Float, Byte> DEGREES_TO_ANGLE = new ValueTransformer<Float, Byte>(Type.BYTE) {
      public Byte transform(PacketWrapper packetWrapper, Float degrees) throws Exception {
         return (byte)((int)(degrees / 360.0F * 256.0F));
      }
   };

   public Protocol1_8To1_9() {
      super(ClientboundPackets1_9.class, ClientboundPackets1_8.class, ServerboundPackets1_9.class, ServerboundPackets1_8.class);
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
   }

   @Override
   public void init(UserConnection userConnection) {
      Ticker.init();
      userConnection.put(new Windows(userConnection));
      userConnection.put(new EntityTracker(userConnection, this));
      userConnection.put(new Levitation(userConnection));
      userConnection.put(new PlayerPosition(userConnection));
      userConnection.put(new Cooldown(userConnection));
      userConnection.put(new BlockPlaceDestroyTracker(userConnection));
      userConnection.put(new BossBarStorage(userConnection));
      userConnection.put(new ClientWorld(userConnection));
   }

   public ReplacementItemRewriter<Protocol1_8To1_9> getItemRewriter() {
      return this.itemRewriter;
   }

   public MetadataRewriter getMetadataRewriter() {
      return this.metadataRewriter;
   }

   static {
      VALID_ATTRIBUTES.add("generic.maxHealth");
      VALID_ATTRIBUTES.add("generic.followRange");
      VALID_ATTRIBUTES.add("generic.knockbackResistance");
      VALID_ATTRIBUTES.add("generic.movementSpeed");
      VALID_ATTRIBUTES.add("generic.attackDamage");
      VALID_ATTRIBUTES.add("horse.jumpStrength");
      VALID_ATTRIBUTES.add("zombie.spawnReinforcements");
   }
}
