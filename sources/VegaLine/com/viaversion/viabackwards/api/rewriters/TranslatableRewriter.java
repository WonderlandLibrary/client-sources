/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
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
            HashMap<String, String> versionMappings = new HashMap<String, String>();
            TRANSLATABLES.put(entry.getKey(), versionMappings);
            for (Map.Entry<String, JsonElement> translationEntry : entry.getValue().getAsJsonObject().entrySet()) {
                versionMappings.put(translationEntry.getKey(), translationEntry.getValue().getAsString());
            }
        }
    }

    public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol) {
        this(protocol, protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol, String sectionIdentifier) {
        super(protocol);
        Map<String, String> newTranslatables = TRANSLATABLES.get(sectionIdentifier);
        if (newTranslatables == null) {
            ViaBackwards.getPlatform().getLogger().warning("Error loading " + sectionIdentifier + " translatables!");
            this.newTranslatables = new HashMap<String, String>();
        } else {
            this.newTranslatables = newTranslatables;
        }
    }

    public void registerPing() {
        this.protocol.registerClientbound(State.LOGIN, 0, 0, wrapper -> this.processText(wrapper.passthrough(Type.COMPONENT)));
    }

    public void registerDisconnect(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> this.processText(wrapper.passthrough(Type.COMPONENT)));
    }

    public void registerLegacyOpenWindow(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(wrapper -> TranslatableRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
            }
        });
    }

    public void registerOpenWindow(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> TranslatableRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
            }
        });
    }

    public void registerTabList(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            this.processText(wrapper.passthrough(Type.COMPONENT));
            this.processText(wrapper.passthrough(Type.COMPONENT));
        });
    }

    public void registerCombatKill(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(wrapper -> TranslatableRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
            }
        });
    }

    public void registerCombatKill1_20(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> TranslatableRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
            }
        });
    }

    @Override
    protected void handleTranslate(JsonObject root, String translate) {
        String newTranslate = this.mappedTranslationKey(translate);
        if (newTranslate != null) {
            root.addProperty("translate", newTranslate);
        }
    }

    public @Nullable String mappedTranslationKey(String translationKey) {
        return this.newTranslatables.get(translationKey);
    }
}

