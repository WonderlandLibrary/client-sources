/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.packets.EntityPackets1_19_1;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage1_19_1;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.NonceStorage;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ReceivedMessagesStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.EntityPackets;
import com.viaversion.viaversion.util.CipherUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_19To1_19_1
extends BackwardsProtocol<ClientboundPackets1_19_1, ClientboundPackets1_19, ServerboundPackets1_19_1, ServerboundPackets1_19> {
    public static final int SYSTEM_CHAT_ID = 1;
    public static final int GAME_INFO_ID = 2;
    private static final UUID ZERO_UUID = new UUID(0L, 0L);
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final EntityPackets1_19_1 entityRewriter = new EntityPackets1_19_1(this);
    private final TranslatableRewriter<ClientboundPackets1_19_1> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19_1>(this);

    public Protocol1_19To1_19_1() {
        super(ClientboundPackets1_19_1.class, ClientboundPackets1_19.class, ServerboundPackets1_19_1.class, ServerboundPackets1_19.class);
    }

    @Override
    protected void registerPackets() {
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.ACTIONBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_1.TITLE_SUBTITLE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_19_1.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_19_1.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19_1.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_19_1.OPEN_WINDOW);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_19_1.COMBAT_KILL);
        this.translatableRewriter.registerPing();
        this.entityRewriter.register();
        this.registerClientbound(ClientboundPackets1_19_1.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(1::lambda$register$0);
                this.handler(Protocol1_19To1_19_1.access$000(this.this$0).worldTrackerHandlerByKey());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ChatRegistryStorage chatRegistryStorage = packetWrapper.user().get(ChatRegistryStorage1_19_1.class);
                chatRegistryStorage.clear();
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                ListTag listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:chat_type")).get("value");
                for (Tag tag : listTag) {
                    CompoundTag compoundTag2 = (CompoundTag)tag;
                    NumberTag numberTag = (NumberTag)compoundTag2.get("id");
                    chatRegistryStorage.addChatType(numberTag.asInt(), compoundTag2);
                }
                compoundTag.put("minecraft:chat_type", EntityPackets.CHAT_REGISTRY.clone());
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.PLAYER_CHAT, ClientboundPackets1_19.SYSTEM_CHAT, this::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_19_1.SYSTEM_CHAT, this::lambda$registerPackets$1);
        this.registerServerbound(ServerboundPackets1_19.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
                this.create(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_19To1_19_1.access$100());
                this.map(Type.BOOLEAN);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ReceivedMessagesStorage receivedMessagesStorage = packetWrapper.user().get(ReceivedMessagesStorage.class);
                receivedMessagesStorage.resetUnacknowledgedCount();
                packetWrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, receivedMessagesStorage.lastSignatures());
                packetWrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_19To1_19_1.access$100());
                }
                packetWrapper.passthrough(Type.BOOLEAN);
                ReceivedMessagesStorage receivedMessagesStorage = packetWrapper.user().get(ReceivedMessagesStorage.class);
                receivedMessagesStorage.resetUnacknowledgedCount();
                packetWrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, receivedMessagesStorage.lastSignatures());
                packetWrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.SERVER_DATA, new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.OPTIONAL_COMPONENT);
                this.map(Type.OPTIONAL_STRING);
                this.map(Type.BOOLEAN);
                this.read(Type.BOOLEAN);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(5::lambda$register$0);
                this.create(Type.OPTIONAL_PROFILE_KEY, null);
                this.create(Type.OPTIONAL_UUID, null);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ProfileKey profileKey = packetWrapper.read(Type.OPTIONAL_PROFILE_KEY);
                if (profileKey == null) {
                    packetWrapper.user().put(new NonceStorage(null));
                }
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.user().get(NonceStorage.class) != null) {
                    return;
                }
                byte[] byArray = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                byte[] byArray2 = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                packetWrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(byArray, byArray2)));
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                NonceStorage nonceStorage = packetWrapper.user().remove(NonceStorage.class);
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                packetWrapper.write(Type.BOOLEAN, true);
                if (bl) {
                    packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    return;
                }
                if (nonceStorage == null || nonceStorage.nonce() == null) {
                    throw new IllegalArgumentException("Server sent nonce is missing");
                }
                packetWrapper.read(Type.LONG);
                packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), ClientboundLoginPackets.CUSTOM_QUERY.getId(), new PacketHandlers(this){
            final Protocol1_19To1_19_1 this$0;
            {
                this.this$0 = protocol1_19To1_19_1;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.equals("velocity:player_info")) {
                    byte[] byArray = packetWrapper.passthrough(Type.REMAINING_BYTES);
                    if (byArray.length == 1 && byArray[0] > 1) {
                        byArray[0] = 1;
                    } else if (byArray.length == 0) {
                        byArray = new byte[]{1};
                        packetWrapper.set(Type.REMAINING_BYTES, 0, byArray);
                    } else {
                        ViaBackwards.getPlatform().getLogger().warning("Received unexpected data in velocity:player_info (length=" + byArray.length + ")");
                    }
                }
            }
        });
        this.cancelClientbound(ClientboundPackets1_19_1.CUSTOM_CHAT_COMPLETIONS);
        this.cancelClientbound(ClientboundPackets1_19_1.DELETE_CHAT_MESSAGE);
        this.cancelClientbound(ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ChatRegistryStorage1_19_1());
        userConnection.put(new ReceivedMessagesStorage());
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_19Types.PLAYER));
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_19_1> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    public EntityPackets1_19_1 getEntityRewriter() {
        return this.entityRewriter;
    }

    public static @Nullable JsonElement decorateChatMessage(ChatRegistryStorage chatRegistryStorage, int n, JsonElement jsonElement, @Nullable JsonElement jsonElement2, JsonElement jsonElement3) {
        Object object;
        Cloneable cloneable;
        Object object2;
        CompoundTag compoundTag = chatRegistryStorage.chatType(n);
        if (compoundTag == null) {
            ViaBackwards.getPlatform().getLogger().warning("Chat message has unknown chat type id " + n + ". Message: " + jsonElement3);
            return null;
        }
        if ((compoundTag = (CompoundTag)((CompoundTag)compoundTag.get("element")).get("chat")) == null) {
            return null;
        }
        String string = (String)((Tag)compoundTag.get("translation_key")).getValue();
        TranslatableComponent.Builder builder = Component.translatable().key(string);
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("style");
        if (compoundTag2 != null) {
            object2 = Style.style();
            cloneable = (StringTag)compoundTag2.get("color");
            if (cloneable != null && (object = NamedTextColor.NAMES.value(((StringTag)cloneable).getValue())) != null) {
                object2.color(NamedTextColor.NAMES.value(((StringTag)cloneable).getValue()));
            }
            for (String object3 : TextDecoration.NAMES.keys()) {
                if (!compoundTag2.contains(object3)) continue;
                object2.decoration(TextDecoration.NAMES.value(object3), ((ByteTag)compoundTag2.get(object3)).asByte() == 1);
            }
            builder.style(object2.build());
        }
        if ((object2 = (ListTag)compoundTag.get("parameters")) != null) {
            cloneable = new ArrayList();
            object = ((ListTag)object2).iterator();
            while (object.hasNext()) {
                Tag tag = (Tag)object.next();
                JsonElement jsonElement4 = null;
                switch ((String)tag.getValue()) {
                    case "sender": {
                        jsonElement4 = jsonElement;
                        break;
                    }
                    case "content": {
                        jsonElement4 = jsonElement3;
                        break;
                    }
                    case "target": {
                        Preconditions.checkNotNull(jsonElement2, "Target name is null");
                        jsonElement4 = jsonElement2;
                        break;
                    }
                    default: {
                        ViaBackwards.getPlatform().getLogger().warning("Unknown parameter for chat decoration: " + tag.getValue());
                    }
                }
                if (jsonElement4 == null) continue;
                cloneable.add(GsonComponentSerializer.gson().deserializeFromTree(jsonElement4));
            }
            builder.args((List<? extends ComponentLike>)((Object)cloneable));
        }
        return GsonComponentSerializer.gson().serializeToTree((Component)builder.build());
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        JsonElement jsonElement = packetWrapper.passthrough(Type.COMPONENT);
        this.translatableRewriter.processText(jsonElement);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        packetWrapper.write(Type.VAR_INT, bl ? 2 : 1);
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        Object object;
        Object object2;
        packetWrapper.read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
        PlayerMessageSignature playerMessageSignature = packetWrapper.read(Type.PLAYER_MESSAGE_SIGNATURE);
        if (!playerMessageSignature.uuid().equals(ZERO_UUID) && playerMessageSignature.signatureBytes().length != 0) {
            object2 = packetWrapper.user().get(ReceivedMessagesStorage.class);
            ((ReceivedMessagesStorage)object2).add(playerMessageSignature);
            if (((ReceivedMessagesStorage)object2).tickUnacknowledged() > 64) {
                ((ReceivedMessagesStorage)object2).resetUnacknowledgedCount();
                object = packetWrapper.create(ServerboundPackets1_19_1.CHAT_ACK);
                object.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, ((ReceivedMessagesStorage)object2).lastSignatures());
                object.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                object.sendToServer(Protocol1_19To1_19_1.class);
            }
        }
        object2 = packetWrapper.read(Type.STRING);
        object = null;
        JsonElement jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
        if (jsonElement != null) {
            object = jsonElement;
        }
        packetWrapper.read(Type.LONG);
        packetWrapper.read(Type.LONG);
        packetWrapper.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
        JsonElement jsonElement2 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
        if (jsonElement2 != null) {
            object = jsonElement2;
        }
        if (object == null) {
            object = GsonComponentSerializer.gson().serializeToTree(Component.text((String)object2));
        }
        if ((n = packetWrapper.read(Type.VAR_INT).intValue()) == 2) {
            packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
        }
        int n2 = packetWrapper.read(Type.VAR_INT);
        JsonElement jsonElement3 = packetWrapper.read(Type.COMPONENT);
        JsonElement jsonElement4 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
        jsonElement = Protocol1_19To1_19_1.decorateChatMessage(packetWrapper.user().get(ChatRegistryStorage1_19_1.class), n2, jsonElement3, jsonElement4, (JsonElement)object);
        if (jsonElement == null) {
            packetWrapper.cancel();
            return;
        }
        this.translatableRewriter.processText(jsonElement);
        packetWrapper.write(Type.COMPONENT, jsonElement);
        packetWrapper.write(Type.VAR_INT, 1);
    }

    static EntityPackets1_19_1 access$000(Protocol1_19To1_19_1 protocol1_19To1_19_1) {
        return protocol1_19To1_19_1.entityRewriter;
    }

    static byte[] access$100() {
        return EMPTY_BYTES;
    }
}

