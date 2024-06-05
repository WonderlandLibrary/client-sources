package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer {
   public TextureMetadataSection deserialize(JsonElement param1, Type param2, JsonDeserializationContext param3) throws JsonParseException {
      // $FF: Couldn't be decompiled
   }

   public String getSectionName() {
      return "texture";
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
