package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_6;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TextDeserializer_v1_6 implements JsonDeserializer<ATextComponent> {
   public ATextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      ATextComponent component = null;
      Style style = new Style();
      JsonObject rawComponent = json.getAsJsonObject();
      JsonElement text = rawComponent.get("text");
      JsonElement translate = rawComponent.get("translate");
      JsonElement color = rawComponent.get("color");
      JsonElement bold = rawComponent.get("bold");
      JsonElement italic = rawComponent.get("italic");
      JsonElement underlined = rawComponent.get("underlined");
      JsonElement obfuscated = rawComponent.get("obfuscated");
      if (color != null && color.isJsonPrimitive()) {
         TextFormatting formatting = TextFormatting.getByName(color.getAsString());
         if (formatting == null) {
            throw new JsonParseException("Given color (" + color.getAsString() + ") is not a valid selection");
         }

         style.setFormatting(formatting);
      }

      if (bold != null && bold.isJsonPrimitive()) {
         style.setBold(Boolean.valueOf(bold.getAsString()));
      }

      if (italic != null && italic.isJsonPrimitive()) {
         style.setItalic(Boolean.valueOf(italic.getAsString()));
      }

      if (underlined != null && underlined.isJsonPrimitive()) {
         style.setUnderlined(Boolean.valueOf(underlined.getAsString()));
      }

      if (obfuscated != null && obfuscated.isJsonPrimitive()) {
         style.setObfuscated(Boolean.valueOf(obfuscated.getAsString()));
      }

      if (text != null) {
         if (text.isJsonArray()) {
            for (JsonElement element : text.getAsJsonArray()) {
               if (element.isJsonPrimitive()) {
                  if (component == null) {
                     component = new StringComponent(element.getAsString());
                  } else {
                     component.append(element.getAsString());
                  }
               } else if (element.isJsonObject()) {
                  if (component == null) {
                     component = this.deserialize(element, typeOfT, context);
                  } else {
                     component.append(this.deserialize(element, typeOfT, context));
                  }
               }
            }
         } else if (text.isJsonPrimitive()) {
            component = new StringComponent(text.getAsString());
         }
      } else if (translate != null && translate.isJsonPrimitive()) {
         JsonElement using = rawComponent.get("using");
         if (using != null) {
            if (using.isJsonArray()) {
               JsonArray array = using.getAsJsonArray();
               List<Object> args = new ArrayList<>();

               for (JsonElement elementx : array) {
                  if (elementx.isJsonPrimitive()) {
                     args.add(elementx.getAsString());
                  } else {
                     args.add(this.deserialize(elementx, typeOfT, context));
                  }
               }

               component = new TranslationComponent(translate.getAsString(), args);
            } else if (using.isJsonPrimitive()) {
               component = new TranslationComponent(translate.getAsString(), using.getAsString());
            }
         } else {
            component = new TranslationComponent(translate.getAsString());
         }
      }

      if (component != null) {
         component.setStyle(style);
      }

      return component;
   }
}
