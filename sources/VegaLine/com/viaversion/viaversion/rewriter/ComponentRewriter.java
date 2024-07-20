/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;

public class ComponentRewriter<C extends ClientboundPacketType> {
    protected final Protocol<C, ?, ?, ?> protocol;

    public ComponentRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public ComponentRewriter() {
        this.protocol = null;
    }

    public void registerComponentPacket(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> this.processText(wrapper.passthrough(Type.COMPONENT)));
    }

    @Deprecated
    public void registerChatMessage(C packetType) {
        this.registerComponentPacket(packetType);
    }

    public void registerBossBar(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Type.VAR_INT, 0);
                    if (action == 0 || action == 3) {
                        ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
    }

    public void registerCombatEvent(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            if (wrapper.passthrough(Type.VAR_INT) == 2) {
                wrapper.passthrough(Type.VAR_INT);
                wrapper.passthrough(Type.INT);
                this.processText(wrapper.passthrough(Type.COMPONENT));
            }
        });
    }

    public void registerTitle(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int action = wrapper.passthrough(Type.VAR_INT);
            if (action >= 0 && action <= 2) {
                this.processText(wrapper.passthrough(Type.COMPONENT));
            }
        });
    }

    public JsonElement processText(String value) {
        try {
            JsonElement root = JsonParser.parseString(value);
            this.processText(root);
            return root;
        } catch (JsonSyntaxException e) {
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().severe("Error when trying to parse json: " + value);
                throw e;
            }
            return new JsonPrimitive(value);
        }
    }

    public void processText(JsonElement element) {
        JsonObject hoverEvent;
        JsonElement extra;
        JsonElement translate;
        if (element == null || element.isJsonNull()) {
            return;
        }
        if (element.isJsonArray()) {
            this.processAsArray(element);
            return;
        }
        if (element.isJsonPrimitive()) {
            this.handleText(element.getAsJsonPrimitive());
            return;
        }
        JsonObject object = element.getAsJsonObject();
        JsonPrimitive text = object.getAsJsonPrimitive("text");
        if (text != null) {
            this.handleText(text);
        }
        if ((translate = object.get("translate")) != null) {
            this.handleTranslate(object, translate.getAsString());
            JsonElement with = object.get("with");
            if (with != null) {
                this.processAsArray(with);
            }
        }
        if ((extra = object.get("extra")) != null) {
            this.processAsArray(extra);
        }
        if ((hoverEvent = object.getAsJsonObject("hoverEvent")) != null) {
            this.handleHoverEvent(hoverEvent);
        }
    }

    protected void handleText(JsonPrimitive text) {
    }

    protected void handleTranslate(JsonObject object, String translate) {
    }

    protected void handleHoverEvent(JsonObject hoverEvent) {
        JsonObject contents;
        String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
        if (action.equals("show_text")) {
            JsonElement value = hoverEvent.get("value");
            this.processText(value != null ? value : hoverEvent.get("contents"));
        } else if (action.equals("show_entity") && (contents = hoverEvent.getAsJsonObject("contents")) != null) {
            this.processText(contents.get("name"));
        }
    }

    private void processAsArray(JsonElement element) {
        for (JsonElement jsonElement : element.getAsJsonArray()) {
            this.processText(jsonElement);
        }
    }

    public <T extends Protocol<C, ?, ?, ?>> T getProtocol() {
        return (T)this.protocol;
    }
}

