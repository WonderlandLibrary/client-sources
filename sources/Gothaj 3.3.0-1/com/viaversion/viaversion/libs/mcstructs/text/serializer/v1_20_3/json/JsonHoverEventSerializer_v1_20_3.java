package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.ItemHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentCodec;
import com.viaversion.viaversion.libs.mcstructs.text.utils.CodecUtils;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import java.util.UUID;

public class JsonHoverEventSerializer_v1_20_3 implements ITypedSerializer<JsonElement, AHoverEvent> {
   private static final String ACTION = "action";
   private static final String CONTENTS = "contents";
   private static final String VALUE = "value";
   private final TextComponentCodec codec;
   private final ITypedSerializer<JsonElement, ATextComponent> textSerializer;
   private final SNbtSerializer<CompoundTag> sNbtSerializer;

   public JsonHoverEventSerializer_v1_20_3(
      TextComponentCodec codec, ITypedSerializer<JsonElement, ATextComponent> textSerializer, SNbtSerializer<CompoundTag> sNbtSerializer
   ) {
      this.codec = codec;
      this.textSerializer = textSerializer;
      this.sNbtSerializer = sNbtSerializer;
   }

   public JsonElement serialize(AHoverEvent object) {
      JsonObject out = new JsonObject();
      out.addProperty("action", object.getAction().getName());
      if (object instanceof TextHoverEvent) {
         TextHoverEvent textHoverEvent = (TextHoverEvent)object;
         out.add("contents", this.textSerializer.serialize(textHoverEvent.getText()));
      } else if (object instanceof ItemHoverEvent) {
         ItemHoverEvent itemHoverEvent = (ItemHoverEvent)object;
         JsonObject contents = new JsonObject();
         contents.addProperty("id", itemHoverEvent.getItem().get());
         if (itemHoverEvent.getCount() != 1) {
            contents.addProperty("count", itemHoverEvent.getCount());
         }

         if (itemHoverEvent.getNbt() != null) {
            try {
               contents.addProperty("tag", this.sNbtSerializer.serialize(itemHoverEvent.getNbt()));
            } catch (SNbtSerializeException var6) {
               throw new IllegalStateException("Failed to serialize nbt", var6);
            }
         }

         out.add("contents", contents);
      } else {
         if (!(object instanceof EntityHoverEvent)) {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
         }

         EntityHoverEvent entityHoverEvent = (EntityHoverEvent)object;
         JsonObject contentsx = new JsonObject();
         contentsx.addProperty("type", entityHoverEvent.getEntityType().get());
         JsonArray id = new JsonArray();
         id.add((int)(entityHoverEvent.getUuid().getMostSignificantBits() >> 32));
         id.add((int)(entityHoverEvent.getUuid().getMostSignificantBits() & 4294967295L));
         id.add((int)(entityHoverEvent.getUuid().getLeastSignificantBits() >> 32));
         id.add((int)(entityHoverEvent.getUuid().getLeastSignificantBits() & 4294967295L));
         contentsx.add("id", id);
         if (entityHoverEvent.getName() != null) {
            contentsx.add("name", this.textSerializer.serialize(entityHoverEvent.getName()));
         }

         out.add("contents", contentsx);
      }

      return out;
   }

   public AHoverEvent deserialize(JsonElement object) {
      if (!object.isJsonObject()) {
         throw new IllegalArgumentException("Element must be a json object");
      } else {
         JsonObject obj = object.getAsJsonObject();
         HoverEventAction action = HoverEventAction.getByName(CodecUtils.requiredString(obj, "action"), false);
         if (action == null) {
            throw new IllegalArgumentException("Unknown hover event action: " + obj.get("action").getAsString());
         } else if (!action.isUserDefinable()) {
            throw new IllegalArgumentException("Hover event action is not user definable: " + action);
         } else if (obj.has("contents")) {
            switch (action) {
               case SHOW_TEXT:
                  return new TextHoverEvent(action, this.textSerializer.deserialize(obj.get("contents")));
               case SHOW_ITEM:
                  if (obj.has("contents") && CodecUtils.isString(obj.get("contents"))) {
                     return new ItemHoverEvent(action, Identifier.of(obj.get("contents").getAsString()), 1, null);
                  } else if (!obj.has("contents") || !CodecUtils.isObject(obj.get("contents"))) {
                     throw new IllegalArgumentException("Expected string or json array for 'contents' tag");
                  } else {
                     JsonObject contentsx = obj.getAsJsonObject("contents");
                     String idx = CodecUtils.requiredString(contentsx, "id");
                     Integer count = CodecUtils.optionalInt(contentsx, "count");
                     String itemTag = CodecUtils.optionalString(contentsx, "tag");

                     try {
                        return new ItemHoverEvent(
                           action, Identifier.of(idx), count == null ? 1 : count, itemTag == null ? null : this.sNbtSerializer.deserialize(itemTag)
                        );
                     } catch (Throwable var12) {
                        this.sneak(var12);
                     }
                  }
               case SHOW_ENTITY:
                  JsonObject contents = CodecUtils.requiredObject(obj, "contents");
                  Identifier type = Identifier.of(CodecUtils.requiredString(contents, "type"));
                  UUID id = this.getUUID(contents.get("id"));
                  ATextComponent name = contents.has("name") ? this.textSerializer.deserialize(contents.get("name")) : null;
                  return new EntityHoverEvent(action, type, id, name);
               default:
                  throw new IllegalArgumentException("Unknown hover event action: " + action);
            }
         } else {
            if (obj.has("value")) {
               ATextComponent value = this.textSerializer.deserialize(obj.get("value"));

               try {
                  switch (action) {
                     case SHOW_TEXT:
                        return new TextHoverEvent(action, value);
                     case SHOW_ITEM: {
                        CompoundTag parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
                        Identifier id = Identifier.of(parsed.get("id") instanceof StringTag ? parsed.<StringTag>get("id").getValue() : "");
                        int count = parsed.get("Count") instanceof ByteTag ? parsed.<ByteTag>get("Count").asByte() : 0;
                        CompoundTag itemTag = parsed.get("tag") instanceof CompoundTag ? parsed.get("tag") : null;
                        return new ItemHoverEvent(action, id, count, itemTag);
                     }
                     case SHOW_ENTITY: {
                        CompoundTag parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
                        ATextComponent name = this.codec
                           .deserializeJson(parsed.get("name") instanceof StringTag ? parsed.<StringTag>get("name").getValue() : "");
                        Identifier type = Identifier.of(parsed.get("type") instanceof StringTag ? parsed.<StringTag>get("type").getValue() : "");
                        UUID uuid = UUID.fromString(parsed.get("id") instanceof StringTag ? parsed.<StringTag>get("id").getValue() : "");
                        return new EntityHoverEvent(action, type, uuid, name);
                     }
                     default:
                        throw new IllegalArgumentException("Unknown hover event action: " + action);
                  }
               } catch (Throwable var13) {
                  this.sneak(var13);
               }
            }

            throw new IllegalArgumentException("Missing 'contents' or 'value' tag");
         }
      }
   }

   private <T extends Throwable> void sneak(Throwable t) throws T {
      throw t;
   }

   private UUID getUUID(JsonElement element) {
      if (element != null && (element.isJsonArray() || element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())) {
         if (element.isJsonPrimitive()) {
            return UUID.fromString(element.getAsString());
         } else {
            JsonArray array = element.getAsJsonArray();
            if (array.size() != 4) {
               throw new IllegalArgumentException("Expected json array with 4 elements for 'id' tag");
            } else {
               int[] ints = new int[4];

               for (int i = 0; i < ints.length; i++) {
                  JsonElement e = array.get(i);
                  if (!e.isJsonPrimitive()) {
                     throw new IllegalArgumentException("Expected json primitive for array element " + i + " of 'id' tag");
                  }

                  JsonPrimitive primitive = e.getAsJsonPrimitive();
                  if (primitive.isNumber()) {
                     ints[i] = primitive.getAsInt();
                  } else {
                     if (!primitive.isBoolean()) {
                        throw new IllegalArgumentException("Expected int for array element " + i + " of 'id' tag");
                     }

                     ints[i] = primitive.getAsBoolean() ? 1 : 0;
                  }
               }

               return new UUID((long)ints[0] << 32 | (long)ints[1] & 4294967295L, (long)ints[2] << 32 | (long)ints[3] & 4294967295L);
            }
         }
      } else {
         throw new IllegalArgumentException("Expected json array or string for 'id' tag");
      }
   }
}
