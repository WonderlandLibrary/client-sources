package com.viaversion.viaversion.api.minecraft.signature.model;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class DecoratableMessage {
   private final String plain;
   private final JsonElement decorated;

   public DecoratableMessage(String plain) {
      this(plain, createLiteralText(plain));
   }

   public DecoratableMessage(String plain, JsonElement decorated) {
      this.plain = plain;
      this.decorated = decorated;
   }

   public String plain() {
      return this.plain;
   }

   public JsonElement decorated() {
      return this.decorated;
   }

   public boolean isDecorated() {
      return !this.decorated.equals(createLiteralText(this.plain));
   }

   private static JsonElement createLiteralText(String text) {
      JsonObject object = new JsonObject();
      object.addProperty("text", text);
      return object;
   }
}
