package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ComponentRewriter<C extends ClientboundPacketType> {
   protected final Protocol<C, ?, ?, ?> protocol;
   protected final ComponentRewriter.ReadType type;

   @Deprecated
   public ComponentRewriter(Protocol<C, ?, ?, ?> protocol) {
      this(protocol, ComponentRewriter.ReadType.JSON);
   }

   public ComponentRewriter(Protocol<C, ?, ?, ?> protocol, ComponentRewriter.ReadType type) {
      this.protocol = protocol;
      this.type = type;
   }

   public void registerComponentPacket(C packetType) {
      this.protocol.registerClientbound(packetType, this::passthroughAndProcess);
   }

   public void registerBossBar(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int action = wrapper.get(Type.VAR_INT, 0);
               if (action == 0 || action == 3) {
                  ComponentRewriter.this.passthroughAndProcess(wrapper);
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

   public void registerPing() {
      this.protocol
         .registerClientbound(State.LOGIN, ClientboundLoginPackets.LOGIN_DISCONNECT, wrapper -> this.processText(wrapper.passthrough(Type.COMPONENT)));
   }

   public void registerLegacyOpenWindow(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.handler(wrapper -> ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
         }
      });
   }

   public void registerOpenWindow(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> ComponentRewriter.this.passthroughAndProcess(wrapper));
         }
      });
   }

   public void registerTabList(C packetType) {
      this.protocol.registerClientbound(packetType, wrapper -> {
         this.passthroughAndProcess(wrapper);
         this.passthroughAndProcess(wrapper);
      });
   }

   public void registerCombatKill(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.handler(wrapper -> ComponentRewriter.this.processText(wrapper.passthrough(Type.COMPONENT)));
         }
      });
   }

   public void registerCombatKill1_20(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> ComponentRewriter.this.passthroughAndProcess(wrapper));
         }
      });
   }

   public void passthroughAndProcess(PacketWrapper wrapper) throws Exception {
      switch (this.type) {
         case JSON:
            this.processText(wrapper.passthrough(Type.COMPONENT));
            break;
         case NBT:
            this.processTag(wrapper.passthrough(Type.TAG));
      }
   }

   public JsonElement processText(String value) {
      try {
         JsonElement root = JsonParser.parseString(value);
         this.processText(root);
         return root;
      } catch (JsonSyntaxException var3) {
         if (Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().severe("Error when trying to parse json: " + value);
            throw var3;
         } else {
            return new JsonPrimitive(value);
         }
      }
   }

   public void processText(JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         if (element.isJsonArray()) {
            this.processJsonArray(element.getAsJsonArray());
         } else if (element.isJsonObject()) {
            this.processJsonObject(element.getAsJsonObject());
         }
      }
   }

   protected void processJsonArray(JsonArray array) {
      for (JsonElement jsonElement : array) {
         this.processText(jsonElement);
      }
   }

   protected void processJsonObject(JsonObject object) {
      JsonElement translate = object.get("translate");
      if (translate != null && translate.isJsonPrimitive()) {
         this.handleTranslate(object, translate.getAsString());
         JsonElement with = object.get("with");
         if (with != null && with.isJsonArray()) {
            this.processJsonArray(with.getAsJsonArray());
         }
      }

      JsonElement extra = object.get("extra");
      if (extra != null && extra.isJsonArray()) {
         this.processJsonArray(extra.getAsJsonArray());
      }

      JsonElement hoverEvent = object.get("hoverEvent");
      if (hoverEvent != null && hoverEvent.isJsonObject()) {
         this.handleHoverEvent(hoverEvent.getAsJsonObject());
      }
   }

   protected void handleTranslate(JsonObject object, String translate) {
   }

   protected void handleHoverEvent(JsonObject hoverEvent) {
      JsonPrimitive actionElement = hoverEvent.getAsJsonPrimitive("action");
      if (actionElement.isString()) {
         String action = actionElement.getAsString();
         if (action.equals("show_text")) {
            JsonElement value = hoverEvent.get("value");
            this.processText(value != null ? value : hoverEvent.get("contents"));
         } else if (action.equals("show_entity")) {
            JsonElement contents = hoverEvent.get("contents");
            if (contents != null && contents.isJsonObject()) {
               this.processText(contents.getAsJsonObject().get("name"));
            }
         }
      }
   }

   public void processTag(@Nullable Tag tag) {
      if (tag != null) {
         if (tag instanceof ListTag) {
            this.processListTag((ListTag)tag);
         } else if (tag instanceof CompoundTag) {
            this.processCompoundTag((CompoundTag)tag);
         }
      }
   }

   private void processListTag(ListTag tag) {
      for (Tag entry : tag) {
         this.processTag(entry);
      }
   }

   protected void processCompoundTag(CompoundTag tag) {
      Tag translate = tag.get("translate");
      if (translate instanceof StringTag) {
         this.handleTranslate(tag, (StringTag)translate);
         Tag with = tag.get("with");
         if (with instanceof ListTag) {
            this.processListTag((ListTag)with);
         }
      }

      Tag extra = tag.get("extra");
      if (extra instanceof ListTag) {
         this.processListTag((ListTag)extra);
      }

      Tag hoverEvent = tag.get("hoverEvent");
      if (hoverEvent instanceof CompoundTag) {
         this.handleHoverEvent((CompoundTag)hoverEvent);
      }
   }

   protected void handleTranslate(CompoundTag parentTag, StringTag translateTag) {
   }

   protected void handleHoverEvent(CompoundTag hoverEventTag) {
      Tag actionTag = hoverEventTag.get("action");
      if (actionTag instanceof StringTag) {
         String action = ((StringTag)actionTag).getValue();
         if (action.equals("show_text")) {
            Tag value = hoverEventTag.get("value");
            this.processTag(value != null ? value : hoverEventTag.get("contents"));
         } else if (action.equals("show_entity")) {
            Tag contents = hoverEventTag.get("contents");
            if (contents instanceof CompoundTag) {
               this.processTag(((CompoundTag)contents).get("name"));
            }
         }
      }
   }

   public static enum ReadType {
      JSON,
      NBT;
   }
}
