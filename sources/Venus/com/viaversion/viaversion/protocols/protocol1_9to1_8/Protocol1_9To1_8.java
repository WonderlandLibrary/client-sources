/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import com.viaversion.viaversion.util.GsonUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_9To1_8
extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_9, ServerboundPackets1_8, ServerboundPackets1_9> {
    public static final ValueTransformer<String, JsonElement> FIX_JSON = new ValueTransformer<String, JsonElement>(Type.COMPONENT){

        @Override
        public JsonElement transform(PacketWrapper packetWrapper, String string) {
            return Protocol1_9To1_8.fixJson(string);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (String)object);
        }
    };
    private final MetadataRewriter1_9To1_8 metadataRewriter = new MetadataRewriter1_9To1_8(this);

    public Protocol1_9To1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_9.class, ServerboundPackets1_8.class, ServerboundPackets1_9.class);
    }

    public static JsonElement fixJson(String string) {
        if (string == null || string.equalsIgnoreCase("null")) {
            string = "{\"text\":\"\"}";
        } else {
            if (!(string.startsWith("\"") && string.endsWith("\"") || string.startsWith("{") && string.endsWith("}"))) {
                return Protocol1_9To1_8.constructJson(string);
            }
            if (string.startsWith("\"") && string.endsWith("\"")) {
                string = "{\"text\":" + string + "}";
            }
        }
        try {
            return GsonUtil.getGson().fromJson(string, JsonObject.class);
        } catch (Exception exception) {
            if (Via.getConfig().isForceJsonTransform()) {
                return Protocol1_9To1_8.constructJson(string);
            }
            Via.getPlatform().getLogger().warning("Invalid JSON String: \"" + string + "\" Please report this issue to the ViaVersion Github: " + exception.getMessage());
            return GsonUtil.getGson().fromJson("{\"text\":\"\"}", JsonObject.class);
        }
    }

    private static JsonElement constructJson(String string) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", string);
        return jsonObject;
    }

    public static Item getHandItem(UserConnection userConnection) {
        return Via.getManager().getProviders().get(HandItemProvider.class).getHandItem(userConnection);
    }

    public static boolean isSword(int n) {
        if (n == 267) {
            return false;
        }
        if (n == 268) {
            return false;
        }
        if (n == 272) {
            return false;
        }
        if (n == 276) {
            return false;
        }
        return n != 283;
    }

    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.registerClientbound(State.LOGIN, 0, 0, Protocol1_9To1_8::lambda$registerPackets$0);
        SpawnPackets.register(this);
        InventoryPackets.register(this);
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
    }

    @Override
    public void register(ViaProviders viaProviders) {
        viaProviders.register(HandItemProvider.class, new HandItemProvider());
        viaProviders.register(CommandBlockProvider.class, new CommandBlockProvider());
        viaProviders.register(EntityIdProvider.class, new EntityIdProvider());
        viaProviders.register(BossBarProvider.class, new BossBarProvider());
        viaProviders.register(MainHandProvider.class, new MainHandProvider());
        viaProviders.register(CompressionProvider.class, new CompressionProvider());
        viaProviders.register(MovementTransmitterProvider.class, new MovementTransmitterProvider());
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_9(userConnection));
        userConnection.put(new ClientChunks(userConnection));
        userConnection.put(new MovementTracker());
        userConnection.put(new InventoryTracker());
        userConnection.put(new CommandBlockStorage());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    public MetadataRewriter1_9To1_8 getEntityRewriter() {
        return this.metadataRewriter;
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        if (packetWrapper.isReadable(Type.COMPONENT, 0)) {
            return;
        }
        packetWrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(packetWrapper.read(Type.STRING)));
    }
}

