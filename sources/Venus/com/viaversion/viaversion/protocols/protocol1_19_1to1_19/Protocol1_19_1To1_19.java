/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_1to1_19;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
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
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ChatDecorationResult;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.storage.ChatTypeStorage;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.storage.NonceStorage;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.util.CipherUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Protocol1_19_1To1_19
extends AbstractProtocol<ClientboundPackets1_19, ClientboundPackets1_19_1, ServerboundPackets1_19, ServerboundPackets1_19_1> {
    private static final String CHAT_REGISTRY_SNBT = "{\n  \"minecraft:chat_type\": {\n    \"type\": \"minecraft:chat_type\",\n    \"value\": [\n         {\n            \"name\":\"minecraft:chat\",\n            \"id\":1,\n            \"element\":{\n               \"chat\":{\n                  \"translation_key\":\"chat.type.text\",\n                  \"parameters\":[\n                     \"sender\",\n                     \"content\"\n                  ]\n               },\n               \"narration\":{\n                  \"translation_key\":\"chat.type.text.narrate\",\n                  \"parameters\":[\n                     \"sender\",\n                     \"content\"\n                  ]\n               }\n            }\n         }    ]\n  }\n}";
    private static final CompoundTag CHAT_REGISTRY;

    public Protocol1_19_1To1_19() {
        super(ClientboundPackets1_19.class, ClientboundPackets1_19_1.class, ServerboundPackets1_19.class, ServerboundPackets1_19_1.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_19.SYSTEM_CHAT, new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.COMPONENT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                boolean bl = n == 2;
                packetWrapper.write(Type.BOOLEAN, bl);
            }
        });
        this.registerClientbound(ClientboundPackets1_19.PLAYER_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.handler(2::lambda$register$0);
                this.read(Type.LONG);
                this.read(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
                JsonElement jsonElement2 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                int n = packetWrapper.read(Type.VAR_INT);
                packetWrapper.read(Type.UUID);
                JsonElement jsonElement3 = packetWrapper.read(Type.COMPONENT);
                JsonElement jsonElement4 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                CompoundTag compoundTag = packetWrapper.user().get(ChatTypeStorage.class).chatType(n);
                ChatDecorationResult chatDecorationResult = Protocol1_19_1To1_19.decorateChatMessage(compoundTag, n, jsonElement3, jsonElement4, jsonElement2 != null ? jsonElement2 : jsonElement);
                if (chatDecorationResult == null) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.COMPONENT, chatDecorationResult.content());
                packetWrapper.write(Type.BOOLEAN, chatDecorationResult.overlay());
            }
        });
        this.registerServerbound(ServerboundPackets1_19_1.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.map(Type.BOOLEAN);
                this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
            }
        });
        this.registerServerbound(ServerboundPackets1_19_1.CHAT_COMMAND, new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.handler(4::lambda$register$0);
                this.map(Type.BOOLEAN);
                this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                }
            }
        });
        this.cancelServerbound(ServerboundPackets1_19_1.CHAT_ACK);
        this.registerClientbound(ClientboundPackets1_19.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ChatTypeStorage chatTypeStorage = packetWrapper.user().get(ChatTypeStorage.class);
                chatTypeStorage.clear();
                CompoundTag compoundTag = packetWrapper.passthrough(Type.NBT);
                ListTag listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:chat_type")).get("value");
                for (Tag tag : listTag) {
                    CompoundTag compoundTag2 = (CompoundTag)tag;
                    NumberTag numberTag = (NumberTag)compoundTag2.get("id");
                    chatTypeStorage.addChatType(numberTag.asInt(), compoundTag2);
                }
                compoundTag.put("minecraft:chat_type", Protocol1_19_1To1_19.access$000().clone());
            }
        });
        this.registerClientbound(ClientboundPackets1_19.SERVER_DATA, new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.OPTIONAL_COMPONENT);
                this.map(Type.OPTIONAL_STRING);
                this.map(Type.BOOLEAN);
                this.create(Type.BOOLEAN, false);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(7::lambda$register$0);
                this.read(Type.OPTIONAL_UUID);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ProfileKey profileKey = packetWrapper.read(Type.OPTIONAL_PROFILE_KEY);
                packetWrapper.write(Type.OPTIONAL_PROFILE_KEY, null);
                if (profileKey == null) {
                    packetWrapper.user().put(new NonceStorage(null));
                }
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.user().has(NonceStorage.class)) {
                    return;
                }
                byte[] byArray = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                byte[] byArray2 = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                packetWrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(byArray, byArray2)));
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                NonceStorage nonceStorage = packetWrapper.user().remove(NonceStorage.class);
                if (nonceStorage.nonce() == null) {
                    return;
                }
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                packetWrapper.write(Type.BOOLEAN, true);
                if (!bl) {
                    packetWrapper.read(Type.LONG);
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
                }
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), ClientboundLoginPackets.CUSTOM_QUERY.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19 this$0;
            {
                this.this$0 = protocol1_19_1To1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(10::lambda$register$0);
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
                        Via.getPlatform().getLogger().warning("Received unexpected data in velocity:player_info (length=" + byArray.length + ")");
                    }
                }
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ChatTypeStorage());
    }

    public static @Nullable ChatDecorationResult decorateChatMessage(CompoundTag compoundTag, int n, JsonElement jsonElement, @Nullable JsonElement jsonElement2, JsonElement jsonElement3) {
        Object object;
        Cloneable cloneable;
        Object object2;
        CompoundTag compoundTag2;
        if (compoundTag == null) {
            Via.getPlatform().getLogger().warning("Chat message has unknown chat type id " + n + ". Message: " + jsonElement3);
            return null;
        }
        CompoundTag compoundTag3 = (CompoundTag)((CompoundTag)compoundTag.get("element")).get("chat");
        boolean bl = false;
        if (compoundTag3 == null) {
            compoundTag3 = (CompoundTag)((CompoundTag)compoundTag.get("element")).get("overlay");
            if (compoundTag3 == null) {
                return null;
            }
            bl = true;
        }
        if ((compoundTag2 = (CompoundTag)compoundTag3.get("decoration")) == null) {
            return new ChatDecorationResult(jsonElement3, bl);
        }
        String string = (String)((Tag)compoundTag2.get("translation_key")).getValue();
        TranslatableComponent.Builder builder = Component.translatable().key(string);
        CompoundTag compoundTag4 = (CompoundTag)compoundTag2.get("style");
        if (compoundTag4 != null) {
            object2 = Style.style();
            cloneable = (StringTag)compoundTag4.get("color");
            if (cloneable != null && (object = NamedTextColor.NAMES.value(((StringTag)cloneable).getValue())) != null) {
                object2.color(NamedTextColor.NAMES.value(((StringTag)cloneable).getValue()));
            }
            for (String object3 : TextDecoration.NAMES.keys()) {
                if (!compoundTag4.contains(object3)) continue;
                object2.decoration(TextDecoration.NAMES.value(object3), ((ByteTag)compoundTag4.get(object3)).asByte() == 1);
            }
            builder.style(object2.build());
        }
        if ((object2 = (ListTag)compoundTag2.get("parameters")) != null) {
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
                    case "team_name": {
                        Preconditions.checkNotNull(jsonElement2, "Team name is null");
                        jsonElement4 = jsonElement2;
                        break;
                    }
                    default: {
                        Via.getPlatform().getLogger().warning("Unknown parameter for chat decoration: " + tag.getValue());
                    }
                }
                if (jsonElement4 == null) continue;
                cloneable.add(GsonComponentSerializer.gson().deserializeFromTree(jsonElement4));
            }
            builder.args((List<? extends ComponentLike>)((Object)cloneable));
        }
        return new ChatDecorationResult(GsonComponentSerializer.gson().serializeToTree((Component)builder.build()), bl);
    }

    static CompoundTag access$000() {
        return CHAT_REGISTRY;
    }

    static {
        try {
            CHAT_REGISTRY = (CompoundTag)BinaryTagIO.readString(CHAT_REGISTRY_SNBT).get("minecraft:chat_type");
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}

