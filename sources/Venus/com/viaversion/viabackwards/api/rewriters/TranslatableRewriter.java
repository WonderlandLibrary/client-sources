/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TranslatableRewriter<C extends ClientboundPacketType>
extends ComponentRewriter<C> {
    private static final Map<String, Map<String, String>> TRANSLATABLES = new HashMap<String, Map<String, String>>();
    private final Map<String, String> newTranslatables;

    public static void loadTranslatables() {
        JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("translation-mappings.json");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            TRANSLATABLES.put(entry.getKey(), hashMap);
            for (Map.Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
                hashMap.put(entry2.getKey(), entry2.getValue().getAsString());
            }
        }
    }

    public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> backwardsProtocol) {
        this(backwardsProtocol, backwardsProtocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> backwardsProtocol, String string) {
        super(backwardsProtocol);
        Map<String, String> map = TRANSLATABLES.get(string);
        if (map == null) {
            ViaBackwards.getPlatform().getLogger().warning("Error loading " + string + " translatables!");
            this.newTranslatables = new HashMap<String, String>();
        } else {
            this.newTranslatables = map;
        }
    }

    public void registerPing() {
        this.protocol.registerClientbound(State.LOGIN, 0, 0, this::lambda$registerPing$0);
    }

    public void registerDisconnect(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerDisconnect$1);
    }

    public void registerLegacyOpenWindow(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final TranslatableRewriter this$0;
            {
                this.this$0 = translatableRewriter;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }

    public void registerOpenWindow(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final TranslatableRewriter this$0;
            {
                this.this$0 = translatableRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }

    public void registerTabList(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerTabList$2);
    }

    public void registerCombatKill(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final TranslatableRewriter this$0;
            {
                this.this$0 = translatableRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }

    public void registerCombatKill1_20(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final TranslatableRewriter this$0;
            {
                this.this$0 = translatableRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                this.this$0.processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
    }

    @Override
    protected void handleTranslate(JsonObject jsonObject, String string) {
        String string2 = this.mappedTranslationKey(string);
        if (string2 != null) {
            jsonObject.addProperty("translate", string2);
        }
    }

    public @Nullable String mappedTranslationKey(String string) {
        return this.newTranslatables.get(string);
    }

    private void lambda$registerTabList$2(PacketWrapper packetWrapper) throws Exception {
        this.processText(packetWrapper.passthrough(Type.COMPONENT));
        this.processText(packetWrapper.passthrough(Type.COMPONENT));
    }

    private void lambda$registerDisconnect$1(PacketWrapper packetWrapper) throws Exception {
        this.processText(packetWrapper.passthrough(Type.COMPONENT));
    }

    private void lambda$registerPing$0(PacketWrapper packetWrapper) throws Exception {
        this.processText(packetWrapper.passthrough(Type.COMPONENT));
    }
}

