/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
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
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_12_2To1_13
extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_12_1, ServerboundPackets1_13, ServerboundPackets1_12_1> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final EntityPackets1_13 entityRewriter = new EntityPackets1_13(this);
    private final BlockItemPackets1_13 blockItemPackets = new BlockItemPackets1_13(this);
    private final TranslatableRewriter<ClientboundPackets1_13> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_13>(this, (BackwardsProtocol)this){
        final Protocol1_12_2To1_13 this$0;
        {
            this.this$0 = protocol1_12_2To1_13;
            super(backwardsProtocol);
        }

        @Override
        protected void handleTranslate(JsonObject jsonObject, String string) {
            String string2 = this.mappedTranslationKey(string);
            if (string2 != null || (string2 = this.this$0.getMappingData().getTranslateMappings().get(string)) != null) {
                jsonObject.addProperty("translate", string2);
            }
        }
    };
    private final TranslatableRewriter<ClientboundPackets1_13> translatableToLegacyRewriter = new TranslatableRewriter<ClientboundPackets1_13>(this, (BackwardsProtocol)this){
        final Protocol1_12_2To1_13 this$0;
        {
            this.this$0 = protocol1_12_2To1_13;
            super(backwardsProtocol);
        }

        @Override
        protected void handleTranslate(JsonObject jsonObject, String string) {
            String string2 = this.mappedTranslationKey(string);
            if (string2 != null || (string2 = this.this$0.getMappingData().getTranslateMappings().get(string)) != null) {
                jsonObject.addProperty("translate", Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().getOrDefault(string2, string2));
            }
        }
    };

    public Protocol1_12_2To1_13() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_12_1.class, ServerboundPackets1_13.class, ServerboundPackets1_12_1.class);
    }

    @Override
    protected void registerPackets() {
        this.executeAsyncAfterLoaded(Protocol1_13To1_12_2.class, Protocol1_12_2To1_13::lambda$registerPackets$0);
        this.translatableRewriter.registerPing();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_13.BOSSBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT_MESSAGE);
        this.translatableRewriter.registerLegacyOpenWindow(ClientboundPackets1_13.OPEN_WINDOW);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_13.DISCONNECT);
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
    public void init(UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        userConnection.put(new BackwardsBlockStorage());
        userConnection.put(new TabCompleteStorage());
        if (ViaBackwards.getConfig().isFix1_13FacePlayer() && !userConnection.has(PlayerPositionStorage1_13.class)) {
            userConnection.put(new PlayerPositionStorage1_13());
        }
    }

    @Override
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

    public String jsonToLegacy(String string) {
        if (string.isEmpty()) {
            return "";
        }
        try {
            return this.jsonToLegacy(JsonParser.parseString(string));
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public String jsonToLegacy(@Nullable JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return "";
        }
        this.translatableToLegacyRewriter.processText(jsonElement);
        try {
            Component component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(jsonElement);
            return LegacyComponentSerializer.legacySection().serialize(component);
        } catch (Exception exception) {
            ViaBackwards.getPlatform().getLogger().warning("Error converting json text to legacy: " + jsonElement);
            exception.printStackTrace();
            return "";
        }
    }

    @Override
    public com.viaversion.viabackwards.api.data.BackwardsMappings getMappingData() {
        return this.getMappingData();
    }

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }

    private static void lambda$registerPackets$0() {
        MAPPINGS.load();
        PaintingMapping.init();
        Via.getManager().getProviders().register(BackwardsBlockEntityProvider.class, new BackwardsBlockEntityProvider());
    }
}

