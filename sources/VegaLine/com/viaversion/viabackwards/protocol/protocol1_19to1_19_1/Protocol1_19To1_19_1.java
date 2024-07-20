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
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
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
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

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
        this.registerClientbound(ClientboundPackets1_19_1.JOIN_GAME, new PacketHandlers(){

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
                this.handler(wrapper -> {
                    ChatRegistryStorage chatTypeStorage = wrapper.user().get(ChatRegistryStorage1_19_1.class);
                    chatTypeStorage.clear();
                    CompoundTag registry = wrapper.get(Type.NBT, 0);
                    ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
                    for (Tag chatType : chatTypes) {
                        CompoundTag chatTypeCompound = (CompoundTag)chatType;
                        NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
                        chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
                    }
                    registry.put("minecraft:chat_type", EntityPackets.CHAT_REGISTRY.clone());
                });
                this.handler(Protocol1_19To1_19_1.this.entityRewriter.worldTrackerHandlerByKey());
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.PLAYER_CHAT, ClientboundPackets1_19.SYSTEM_CHAT, (PacketWrapper wrapper) -> {
            int filterMaskType;
            wrapper.read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
            PlayerMessageSignature signature = wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE);
            if (!signature.uuid().equals(ZERO_UUID) && signature.signatureBytes().length != 0) {
                ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                messagesStorage.add(signature);
                if (messagesStorage.tickUnacknowledged() > 64) {
                    messagesStorage.resetUnacknowledgedCount();
                    PacketWrapper chatAckPacket = wrapper.create(ServerboundPackets1_19_1.CHAT_ACK);
                    chatAckPacket.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    chatAckPacket.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                    chatAckPacket.sendToServer(Protocol1_19To1_19_1.class);
                }
            }
            String plainMessage = wrapper.read(Type.STRING);
            JsonElement message = null;
            JsonElement decoratedMessage = wrapper.read(Type.OPTIONAL_COMPONENT);
            if (decoratedMessage != null) {
                message = decoratedMessage;
            }
            wrapper.read(Type.LONG);
            wrapper.read(Type.LONG);
            wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
            JsonElement unsignedMessage = wrapper.read(Type.OPTIONAL_COMPONENT);
            if (unsignedMessage != null) {
                message = unsignedMessage;
            }
            if (message == null) {
                message = GsonComponentSerializer.gson().serializeToTree(Component.text(plainMessage));
            }
            if ((filterMaskType = wrapper.read(Type.VAR_INT).intValue()) == 2) {
                wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
            }
            int chatTypeId = wrapper.read(Type.VAR_INT);
            JsonElement senderName = wrapper.read(Type.COMPONENT);
            JsonElement targetName = wrapper.read(Type.OPTIONAL_COMPONENT);
            decoratedMessage = Protocol1_19To1_19_1.decorateChatMessage(wrapper.user().get(ChatRegistryStorage1_19_1.class), chatTypeId, senderName, targetName, message);
            if (decoratedMessage == null) {
                wrapper.cancel();
                return;
            }
            this.translatableRewriter.processText(decoratedMessage);
            wrapper.write(Type.COMPONENT, decoratedMessage);
            wrapper.write(Type.VAR_INT, 1);
        });
        this.registerClientbound(ClientboundPackets1_19_1.SYSTEM_CHAT, (PacketWrapper wrapper) -> {
            JsonElement content = wrapper.passthrough(Type.COMPONENT);
            this.translatableRewriter.processText(content);
            boolean overlay = wrapper.read(Type.BOOLEAN);
            wrapper.write(Type.VAR_INT, overlay ? 2 : 1);
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
                this.create(Type.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
                this.map(Type.BOOLEAN);
                this.handler(wrapper -> {
                    ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                    messagesStorage.resetUnacknowledgedCount();
                    wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.handler(wrapper -> {
                    int signatures = wrapper.passthrough(Type.VAR_INT);
                    for (int i = 0; i < signatures; ++i) {
                        wrapper.passthrough(Type.STRING);
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
                    }
                    wrapper.passthrough(Type.BOOLEAN);
                    ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                    messagesStorage.resetUnacknowledgedCount();
                    wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.SERVER_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.OPTIONAL_COMPONENT);
                this.map(Type.OPTIONAL_STRING);
                this.map(Type.BOOLEAN);
                this.read(Type.BOOLEAN);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    ProfileKey profileKey = wrapper.read(Type.OPTIONAL_PROFILE_KEY);
                    if (profileKey == null) {
                        wrapper.user().put(new NonceStorage(null));
                    }
                });
                this.create(Type.OPTIONAL_PROFILE_KEY, null);
                this.create(Type.OPTIONAL_UUID, null);
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    if (wrapper.user().get(NonceStorage.class) != null) {
                        return;
                    }
                    byte[] publicKey = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    byte[] nonce = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    wrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
                });
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    NonceStorage nonceStorage = wrapper.user().remove(NonceStorage.class);
                    boolean isNonce = wrapper.read(Type.BOOLEAN);
                    wrapper.write(Type.BOOLEAN, true);
                    if (isNonce) {
                        wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                        return;
                    }
                    if (nonceStorage == null || nonceStorage.nonce() == null) {
                        throw new IllegalArgumentException("Server sent nonce is missing");
                    }
                    wrapper.read(Type.LONG);
                    wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
                });
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), ClientboundLoginPackets.CUSTOM_QUERY.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    String identifier = wrapper.get(Type.STRING, 0);
                    if (identifier.equals("velocity:player_info")) {
                        byte[] data = wrapper.passthrough(Type.REMAINING_BYTES);
                        if (data.length == 1 && data[0] > 1) {
                            data[0] = 1;
                        } else if (data.length == 0) {
                            data = new byte[]{1};
                            wrapper.set(Type.REMAINING_BYTES, 0, data);
                        } else {
                            ViaBackwards.getPlatform().getLogger().warning("Received unexpected data in velocity:player_info (length=" + data.length + ")");
                        }
                    }
                });
            }
        });
        this.cancelClientbound(ClientboundPackets1_19_1.CUSTOM_CHAT_COMPLETIONS);
        this.cancelClientbound(ClientboundPackets1_19_1.DELETE_CHAT_MESSAGE);
        this.cancelClientbound(ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
    }

    @Override
    public void init(UserConnection user) {
        user.put(new ChatRegistryStorage1_19_1());
        user.put(new ReceivedMessagesStorage());
        this.addEntityTracker(user, new EntityTrackerBase(user, Entity1_19Types.PLAYER));
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_19_1> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    public EntityPackets1_19_1 getEntityRewriter() {
        return this.entityRewriter;
    }

    public static @Nullable JsonElement decorateChatMessage(ChatRegistryStorage chatRegistryStorage, int chatTypeId, JsonElement senderName, @Nullable JsonElement targetName, JsonElement message) {
        ListTag parameters;
        CompoundTag chatType = chatRegistryStorage.chatType(chatTypeId);
        if (chatType == null) {
            ViaBackwards.getPlatform().getLogger().warning("Chat message has unknown chat type id " + chatTypeId + ". Message: " + message);
            return null;
        }
        if ((chatType = (CompoundTag)((CompoundTag)chatType.get("element")).get("chat")) == null) {
            return null;
        }
        String translationKey = (String)((Tag)chatType.get("translation_key")).getValue();
        TranslatableComponent.Builder componentBuilder = Component.translatable().key(translationKey);
        CompoundTag style = (CompoundTag)chatType.get("style");
        if (style != null) {
            NamedTextColor textColor;
            Style.Builder styleBuilder = Style.style();
            StringTag color = (StringTag)style.get("color");
            if (color != null && (textColor = NamedTextColor.NAMES.value(color.getValue())) != null) {
                styleBuilder.color(NamedTextColor.NAMES.value(color.getValue()));
            }
            for (String key : TextDecoration.NAMES.keys()) {
                if (!style.contains(key)) continue;
                styleBuilder.decoration(TextDecoration.NAMES.value(key), ((ByteTag)style.get(key)).asByte() == 1);
            }
            componentBuilder.style(styleBuilder.build());
        }
        if ((parameters = (ListTag)chatType.get("parameters")) != null) {
            ArrayList<Component> arguments = new ArrayList<Component>();
            for (Tag element : parameters) {
                JsonElement argument = null;
                switch ((String)element.getValue()) {
                    case "sender": {
                        argument = senderName;
                        break;
                    }
                    case "content": {
                        argument = message;
                        break;
                    }
                    case "target": {
                        Preconditions.checkNotNull(targetName, "Target name is null");
                        argument = targetName;
                        break;
                    }
                    default: {
                        ViaBackwards.getPlatform().getLogger().warning("Unknown parameter for chat decoration: " + element.getValue());
                    }
                }
                if (argument == null) continue;
                arguments.add(GsonComponentSerializer.gson().deserializeFromTree(argument));
            }
            componentBuilder.args(arguments);
        }
        return GsonComponentSerializer.gson().serializeToTree((Component)componentBuilder.build());
    }
}

