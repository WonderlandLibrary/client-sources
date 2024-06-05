/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import java.util.Iterator;

public class ComponentRewriter {
    protected final Protocol protocol;

    public ComponentRewriter(Protocol protocol) {
        this.protocol = protocol;
    }

    public ComponentRewriter() {
        this.protocol = null;
    }

    public void registerComponentPacket(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
            }
        });
    }

    @Deprecated
    public void registerChatMessage(ClientboundPacketType packetType) {
        this.registerComponentPacket(packetType);
    }

    public void registerBossBar(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Type.VAR_INT, 0);
                    if (action != 0) {
                        if (action != 3) return;
                    }
                    ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerCombatEvent(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    if (wrapper.passthrough(Type.VAR_INT) != 2) return;
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.INT);
                    ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public void registerTitle(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int action = wrapper.passthrough(Type.VAR_INT);
                    if (action < 0) return;
                    if (action > 2) return;
                    ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
    }

    public JsonElement processText(String value) {
        try {
            JsonElement root = JsonParser.parseString(value);
            this.processText(root);
            return root;
        }
        catch (JsonSyntaxException e) {
            if (!Via.getManager().isDebug()) return new JsonPrimitive(value);
            Via.getPlatform().getLogger().severe("Error when trying to parse json: " + value);
            throw e;
        }
    }

    public void processText(JsonElement element) {
        JsonObject hoverEvent;
        JsonElement extra;
        JsonElement translate;
        if (element == null) return;
        if (element.isJsonNull()) {
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
        if ((hoverEvent = object.getAsJsonObject("hoverEvent")) == null) return;
        this.handleHoverEvent(hoverEvent);
    }

    protected void handleText(JsonPrimitive text) {
    }

    protected void handleTranslate(JsonObject object, String translate) {
    }

    protected void handleHoverEvent(JsonObject hoverEvent) {
        String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
        if (!action.equals("show_text")) {
            if (!action.equals("show_entity")) return;
            JsonObject contents = hoverEvent.getAsJsonObject("contents");
            if (contents == null) return;
            this.processText(contents.get("name"));
            return;
        }
        JsonElement value = hoverEvent.get("value");
        this.processText(value != null ? value : hoverEvent.get("contents"));
    }

    private void processAsArray(JsonElement element) {
        Iterator<JsonElement> iterator = element.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            JsonElement jsonElement = iterator.next();
            this.processText(jsonElement);
        }
    }

    public <T extends Protocol> T getProtocol() {
        return (T)this.protocol;
    }
}

