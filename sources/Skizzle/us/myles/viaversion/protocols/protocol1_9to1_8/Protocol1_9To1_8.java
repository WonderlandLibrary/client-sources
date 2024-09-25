/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8;

import java.util.List;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.platform.providers.ViaProviders;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_8.ClientboundPackets1_8;
import us.myles.ViaVersion.protocols.protocol1_8.ServerboundPackets1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.packets.EntityPackets;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.packets.PlayerPackets;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.packets.SpawnPackets;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.packets.WorldPackets;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BulkChunkTranslatorProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.ClientChunks;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.InventoryTracker;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.MovementTracker;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.PlaceBlockTracker;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;

public class Protocol1_9To1_8
extends Protocol<ClientboundPackets1_8, ClientboundPackets1_9, ServerboundPackets1_8, ServerboundPackets1_9> {
    public static final ValueTransformer<String, JsonElement> FIX_JSON = new ValueTransformer<String, JsonElement>(Type.COMPONENT){

        @Override
        public JsonElement transform(PacketWrapper wrapper, String line) {
            return Protocol1_9To1_8.fixJson(line);
        }
    };

    public Protocol1_9To1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_9.class, ServerboundPackets1_8.class, ServerboundPackets1_9.class);
    }

    public static JsonElement fixJson(String line) {
        if (line == null || line.equalsIgnoreCase("null")) {
            line = "{\"text\":\"\"}";
        } else {
            if (!(line.startsWith("\"") && line.endsWith("\"") || line.startsWith("{") && line.endsWith("}"))) {
                return Protocol1_9To1_8.constructJson(line);
            }
            if (line.startsWith("\"") && line.endsWith("\"")) {
                line = "{\"text\":" + line + "}";
            }
        }
        try {
            return GsonUtil.getGson().fromJson(line, JsonObject.class);
        }
        catch (Exception e) {
            if (Via.getConfig().isForceJsonTransform()) {
                return Protocol1_9To1_8.constructJson(line);
            }
            Via.getPlatform().getLogger().warning("Invalid JSON String: \"" + line + "\" Please report this issue to the ViaVersion Github: " + e.getMessage());
            return GsonUtil.getGson().fromJson("{\"text\":\"\"}", JsonObject.class);
        }
    }

    private static JsonElement constructJson(String text) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);
        return jsonObject;
    }

    public static Item getHandItem(UserConnection info) {
        return Via.getManager().getProviders().get(HandItemProvider.class).getHandItem(info);
    }

    public static boolean isSword(int id) {
        if (id == 267) {
            return true;
        }
        if (id == 268) {
            return true;
        }
        if (id == 272) {
            return true;
        }
        if (id == 276) {
            return true;
        }
        return id == 283;
    }

    @Override
    protected void registerPackets() {
        MetadataRewriter1_9To1_8 metadataRewriter = new MetadataRewriter1_9To1_8(this);
        this.registerOutgoing(State.LOGIN, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    if (wrapper.isReadable(Type.COMPONENT, 0)) {
                        return;
                    }
                    wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(wrapper.read(Type.STRING)));
                });
            }
        });
        SpawnPackets.register(this);
        InventoryPackets.register(this);
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
    }

    @Override
    protected void register(ViaProviders providers) {
        providers.register(HandItemProvider.class, new HandItemProvider());
        providers.register(BulkChunkTranslatorProvider.class, new BulkChunkTranslatorProvider());
        providers.register(CommandBlockProvider.class, new CommandBlockProvider());
        providers.register(EntityIdProvider.class, new EntityIdProvider());
        providers.register(BossBarProvider.class, new BossBarProvider());
        providers.register(MainHandProvider.class, new MainHandProvider());
        providers.require(MovementTransmitterProvider.class);
    }

    @Override
    public boolean isFiltered(Class packetClass) {
        return Via.getManager().getProviders().get(BulkChunkTranslatorProvider.class).isFiltered(packetClass);
    }

    @Override
    protected void filterPacket(UserConnection info, Object packet, List output) throws Exception {
        output.addAll(info.get(ClientChunks.class).transformMapChunkBulk(packet));
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new EntityTracker1_9(userConnection));
        userConnection.put(new ClientChunks(userConnection));
        userConnection.put(new MovementTracker(userConnection));
        userConnection.put(new InventoryTracker(userConnection));
        userConnection.put(new PlaceBlockTracker(userConnection));
        userConnection.put(new CommandBlockStorage(userConnection));
    }
}

