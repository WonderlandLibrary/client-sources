/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.AdvancementTranslations;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class ChatPackets1_12
extends RewriterBase<Protocol1_11_1To1_12> {
    public static final ComponentRewriter<ClientboundPackets1_12> COMPONENT_REWRITER = new ComponentRewriter<ClientboundPackets1_12>(){

        @Override
        public void processText(JsonElement jsonElement) {
            super.processText(jsonElement);
            if (jsonElement == null || !jsonElement.isJsonObject()) {
                return;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement jsonElement2 = jsonObject.remove("keybind");
            if (jsonElement2 == null) {
                return;
            }
            jsonObject.addProperty("text", jsonElement2.getAsString());
        }

        @Override
        protected void handleTranslate(JsonObject jsonObject, String string) {
            String string2 = AdvancementTranslations.get(string);
            if (string2 != null) {
                jsonObject.addProperty("translate", string2);
            }
        }
    };

    public ChatPackets1_12(Protocol1_11_1To1_12 protocol1_11_1To1_12) {
        super(protocol1_11_1To1_12);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.CHAT_MESSAGE, ChatPackets1_12::lambda$registerPackets$0);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        JsonElement jsonElement = packetWrapper.passthrough(Type.COMPONENT);
        COMPONENT_REWRITER.processText(jsonElement);
    }
}

