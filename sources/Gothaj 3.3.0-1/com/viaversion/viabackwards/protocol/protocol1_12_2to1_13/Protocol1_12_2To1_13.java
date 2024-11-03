package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.SoundPackets1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.TabCompleteStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Protocol1_12_2To1_13 extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_12_1, ServerboundPackets1_13, ServerboundPackets1_12_1> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
   private final EntityPackets1_13 entityRewriter = new EntityPackets1_13(this);
   private final BlockItemPackets1_13 blockItemPackets = new BlockItemPackets1_13(this);
   private final TranslatableRewriter<ClientboundPackets1_13> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_13>(
      this, ComponentRewriter.ReadType.JSON
   ) {
      @Override
      protected void handleTranslate(JsonObject root, String translate) {
         String mappedKey = this.mappedTranslationKey(translate);
         if (mappedKey != null || (mappedKey = Protocol1_12_2To1_13.this.getMappingData().getTranslateMappings().get(translate)) != null) {
            root.addProperty("translate", mappedKey);
         }
      }
   };
   private final TranslatableRewriter<ClientboundPackets1_13> translatableToLegacyRewriter = new TranslatableRewriter<ClientboundPackets1_13>(
      this, ComponentRewriter.ReadType.JSON
   ) {
      @Override
      protected void handleTranslate(JsonObject root, String translate) {
         String mappedKey = this.mappedTranslationKey(translate);
         if (mappedKey != null || (mappedKey = Protocol1_12_2To1_13.this.getMappingData().getTranslateMappings().get(translate)) != null) {
            root.addProperty("translate", Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().getOrDefault(mappedKey, mappedKey));
         }
      }
   };

   public Protocol1_12_2To1_13() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_12_1.class, ServerboundPackets1_13.class, ServerboundPackets1_12_1.class);
   }

   @Override
   protected void registerPackets() {
      this.executeAsyncAfterLoaded(Protocol1_13To1_12_2.class, () -> {
         MAPPINGS.load();
         PaintingMapping.init();
         Via.getManager().getProviders().register(BackwardsBlockEntityProvider.class, new BackwardsBlockEntityProvider());
      });
      this.translatableRewriter.registerPing();
      this.translatableRewriter.registerBossBar(ClientboundPackets1_13.BOSSBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT_MESSAGE);
      this.translatableRewriter.registerLegacyOpenWindow(ClientboundPackets1_13.OPEN_WINDOW);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.DISCONNECT);
      this.translatableRewriter.registerCombatEvent(ClientboundPackets1_13.COMBAT_EVENT);
      this.translatableRewriter.registerTitle(ClientboundPackets1_13.TITLE);
      this.translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
      this.blockItemPackets.register();
      this.entityRewriter.register();
      new PlayerPacket1_13(this).register();
      new SoundPackets1_13(this).register();
      this.cancelClientbound(ClientboundPackets1_13.NBT_QUERY);
      this.cancelClientbound(ClientboundPackets1_13.CRAFT_RECIPE_RESPONSE);
      this.cancelClientbound(ClientboundPackets1_13.UNLOCK_RECIPES);
      this.cancelClientbound(ClientboundPackets1_13.ADVANCEMENTS);
      this.cancelClientbound(ClientboundPackets1_13.DECLARE_RECIPES);
      this.cancelClientbound(ClientboundPackets1_13.TAGS);
      this.cancelServerbound(ServerboundPackets1_12_1.CRAFT_RECIPE_REQUEST);
      this.cancelServerbound(ServerboundPackets1_12_1.RECIPE_BOOK_DATA);
   }

   @Override
   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld());
      }

      user.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(user, EntityTypes1_13.EntityType.PLAYER));
      user.put(new BackwardsBlockStorage());
      user.put(new TabCompleteStorage());
      if (ViaBackwards.getConfig().isFix1_13FacePlayer() && !user.has(PlayerPositionStorage1_13.class)) {
         user.put(new PlayerPositionStorage1_13());
      }
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public EntityPackets1_13 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPackets1_13 getItemRewriter() {
      return this.blockItemPackets;
   }

   public TranslatableRewriter<ClientboundPackets1_13> translatableRewriter() {
      return this.translatableRewriter;
   }

   public String jsonToLegacy(String value) {
      if (value.isEmpty()) {
         return "";
      } else {
         try {
            return this.jsonToLegacy(JsonParser.parseString(value));
         } catch (Exception var3) {
            var3.printStackTrace();
            return "";
         }
      }
   }

   public String jsonToLegacy(@Nullable JsonElement value) {
      if (value != null && !value.isJsonNull()) {
         this.translatableToLegacyRewriter.processText(value);
         return ComponentUtil.jsonToLegacy(value);
      } else {
         return "";
      }
   }
}
