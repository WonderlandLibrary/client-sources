/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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

    public void registerComponentPacket(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerComponentPacket$0);
    }

    @Deprecated
    public void registerChatMessage(C c) {
        this.registerComponentPacket(c);
    }

    public void registerBossBar(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ComponentRewriter this$0;
            {
                this.this$0 = componentRewriter;
            }

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0 || n == 3) {
                    this.this$0.processText(packetWrapper.passthrough(Type.COMPONENT));
                }
            }
        });
    }

    public void registerCombatEvent(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerCombatEvent$1);
    }

    public void registerTitle(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerTitle$2);
    }

    public JsonElement processText(String string) {
        try {
            JsonElement jsonElement = JsonParser.parseString(string);
            this.processText(jsonElement);
            return jsonElement;
        } catch (JsonSyntaxException jsonSyntaxException) {
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().severe("Error when trying to parse json: " + string);
                throw jsonSyntaxException;
            }
            return new JsonPrimitive(string);
        }
    }

    public void processText(JsonElement jsonElement) {
        JsonObject jsonObject;
        JsonElement jsonElement2;
        JsonElement jsonElement3;
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return;
        }
        if (jsonElement.isJsonArray()) {
            this.processAsArray(jsonElement);
            return;
        }
        if (jsonElement.isJsonPrimitive()) {
            this.handleText(jsonElement.getAsJsonPrimitive());
            return;
        }
        JsonObject jsonObject2 = jsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = jsonObject2.getAsJsonPrimitive("text");
        if (jsonPrimitive != null) {
            this.handleText(jsonPrimitive);
        }
        if ((jsonElement3 = jsonObject2.get("translate")) != null) {
            this.handleTranslate(jsonObject2, jsonElement3.getAsString());
            jsonElement2 = jsonObject2.get("with");
            if (jsonElement2 != null) {
                this.processAsArray(jsonElement2);
            }
        }
        if ((jsonElement2 = jsonObject2.get("extra")) != null) {
            this.processAsArray(jsonElement2);
        }
        if ((jsonObject = jsonObject2.getAsJsonObject("hoverEvent")) != null) {
            this.handleHoverEvent(jsonObject);
        }
    }

    protected void handleText(JsonPrimitive jsonPrimitive) {
    }

    protected void handleTranslate(JsonObject jsonObject, String string) {
    }

    protected void handleHoverEvent(JsonObject jsonObject) {
        JsonObject jsonObject2;
        String string = jsonObject.getAsJsonPrimitive("action").getAsString();
        if (string.equals("show_text")) {
            JsonElement jsonElement = jsonObject.get("value");
            this.processText(jsonElement != null ? jsonElement : jsonObject.get("contents"));
        } else if (string.equals("show_entity") && (jsonObject2 = jsonObject.getAsJsonObject("contents")) != null) {
            this.processText(jsonObject2.get("name"));
        }
    }

    private void processAsArray(JsonElement jsonElement) {
        for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
            this.processText(jsonElement2);
        }
    }

    public <T extends Protocol<C, ?, ?, ?>> T getProtocol() {
        return (T)this.protocol;
    }

    private void lambda$registerTitle$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        if (n >= 0 && n <= 2) {
            this.processText(packetWrapper.passthrough(Type.COMPONENT));
        }
    }

    private void lambda$registerCombatEvent$1(PacketWrapper packetWrapper) throws Exception {
        if (packetWrapper.passthrough(Type.VAR_INT) == 2) {
            packetWrapper.passthrough(Type.VAR_INT);
            packetWrapper.passthrough(Type.INT);
            this.processText(packetWrapper.passthrough(Type.COMPONENT));
        }
    }

    private void lambda$registerComponentPacket$0(PacketWrapper packetWrapper) throws Exception {
        this.processText(packetWrapper.passthrough(Type.COMPONENT));
    }
}

