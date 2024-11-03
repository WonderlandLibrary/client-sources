package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.gson.JsonElement;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class LastResourcePack implements StorableObject {
   private final String url;
   private final String hash;
   private final boolean required;
   private final JsonElement prompt;

   public LastResourcePack(String url, String hash, boolean required, @Nullable JsonElement prompt) {
      this.url = url;
      this.hash = hash;
      this.required = required;
      this.prompt = prompt;
   }

   public String url() {
      return this.url;
   }

   public String hash() {
      return this.hash;
   }

   public boolean required() {
      return this.required;
   }

   @Nullable
   public JsonElement prompt() {
      return this.prompt;
   }

   @Override
   public boolean clearOnServerSwitch() {
      return false;
   }
}
