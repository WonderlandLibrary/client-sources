package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_17;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.KeybindComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.ScoreComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.SelectorComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.BlockNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.EntityNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.StorageNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.utils.JsonUtils;
import java.lang.reflect.Type;

public class TextDeserializer_v1_17 implements JsonDeserializer<ATextComponent> {
   public ATextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      if (json.isJsonPrimitive()) {
         return new StringComponent(json.getAsString());
      } else if (json.isJsonArray()) {
         JsonArray array = json.getAsJsonArray();
         ATextComponent component = null;

         for (JsonElement element : array) {
            ATextComponent serializedElement = this.deserialize(element, element.getClass(), context);
            if (component == null) {
               component = serializedElement;
            } else {
               component.append(serializedElement);
            }
         }

         return component;
      } else if (!json.isJsonObject()) {
         throw new JsonParseException("Don't know how to turn " + json + " into a Component");
      } else {
         JsonObject rawComponent = json.getAsJsonObject();
         ATextComponent component;
         if (rawComponent.has("text")) {
            component = new StringComponent(JsonUtils.getString(rawComponent, "text"));
         } else if (rawComponent.has("translate")) {
            String translate = JsonUtils.getString(rawComponent, "translate");
            if (rawComponent.has("with")) {
               JsonArray with = rawComponent.getAsJsonArray("with");
               Object[] args = new Object[with.size()];

               for (int i = 0; i < with.size(); i++) {
                  ATextComponent elementx = this.deserialize(with.get(i), typeOfT, context);
                  args[i] = elementx;
                  if (elementx instanceof StringComponent) {
                     StringComponent stringComponent = (StringComponent)elementx;
                     if (stringComponent.getStyle().isEmpty() && stringComponent.getSiblings().isEmpty()) {
                        args[i] = stringComponent.getText();
                     }
                  }
               }

               component = new TranslationComponent(translate, args);
            } else {
               component = new TranslationComponent(translate);
            }
         } else if (rawComponent.has("score")) {
            JsonObject score = rawComponent.getAsJsonObject("score");
            if (!score.has("name") || !score.has("objective")) {
               throw new JsonParseException("A score component needs at least a name and an objective");
            }

            component = new ScoreComponent(JsonUtils.getString(score, "name"), JsonUtils.getString(score, "objective"));
         } else if (rawComponent.has("selector")) {
            if (rawComponent.has("separator")) {
               component = new SelectorComponent(
                  JsonUtils.getString(rawComponent, "selector"), this.deserialize(rawComponent.get("separator"), typeOfT, context)
               );
            } else {
               component = new SelectorComponent(JsonUtils.getString(rawComponent, "selector"), null);
            }
         } else if (rawComponent.has("keybind")) {
            component = new KeybindComponent(JsonUtils.getString(rawComponent, "keybind"));
         } else {
            if (!rawComponent.has("nbt")) {
               throw new JsonParseException("Don't know how to turn " + json + " into a Component");
            }

            String nbt = JsonUtils.getString(rawComponent, "nbt");
            boolean interpret = JsonUtils.getBoolean(rawComponent, "interpret", false);
            ATextComponent separator = null;
            if (rawComponent.has("separator")) {
               separator = this.deserialize(rawComponent.get("separator"), typeOfT, context);
            }

            if (rawComponent.has("block")) {
               component = new BlockNbtComponent(nbt, interpret, separator, JsonUtils.getString(rawComponent, "block"));
            } else if (rawComponent.has("entity")) {
               component = new EntityNbtComponent(nbt, interpret, separator, JsonUtils.getString(rawComponent, "entity"));
            } else {
               if (!rawComponent.has("storage")) {
                  throw new JsonParseException("Don't know how to turn " + json + " into a Component");
               }

               component = new StorageNbtComponent(nbt, interpret, separator, Identifier.of(JsonUtils.getString(rawComponent, "storage")));
            }
         }

         if (rawComponent.has("extra")) {
            JsonArray extra = rawComponent.getAsJsonArray("extra");
            if (extra.isEmpty()) {
               throw new JsonParseException("Unexpected empty array of components");
            }

            for (JsonElement elementx : extra) {
               component.append(this.deserialize(elementx, typeOfT, context));
            }
         }

         Style newStyle = context.deserialize(rawComponent, Style.class);
         if (newStyle != null) {
            component.setStyle(newStyle);
         }

         return component;
      }
   }
}
