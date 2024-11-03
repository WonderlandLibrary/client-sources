package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.AdvancementTranslations;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class ChatPackets1_12 extends RewriterBase<Protocol1_11_1To1_12> {
   public static final ComponentRewriter<ClientboundPackets1_12> COMPONENT_REWRITER = new ComponentRewriter<ClientboundPackets1_12>(
      null, ComponentRewriter.ReadType.JSON
   ) {
      @Override
      public void processText(JsonElement element) {
         super.processText(element);
         if (element != null && element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            JsonElement keybind = object.remove("keybind");
            if (keybind != null) {
               object.addProperty("text", keybind.getAsString());
            }
         }
      }

      @Override
      protected void handleTranslate(JsonObject object, String translate) {
         String text = AdvancementTranslations.get(translate);
         if (text != null) {
            object.addProperty("translate", text);
         }
      }
   };

   public ChatPackets1_12(Protocol1_11_1To1_12 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_12.CHAT_MESSAGE, wrapper -> {
         JsonElement element = wrapper.passthrough(Type.COMPONENT);
         COMPONENT_REWRITER.processText(element);
      });
   }
}
