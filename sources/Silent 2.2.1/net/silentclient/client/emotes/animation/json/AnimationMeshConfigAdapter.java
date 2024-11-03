package net.silentclient.client.emotes.animation.json;

import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class AnimationMeshConfigAdapter implements JsonDeserializer<AnimationMeshConfig> {
   public AnimationMeshConfig deserialize(JsonElement jsonelement, Type var2, JsonDeserializationContext var3) {
      if (!jsonelement.isJsonObject()) {
         return null;
      } else {
         JsonObject jsonobject = jsonelement.getAsJsonObject();
         AnimationMeshConfig animationmeshconfig = new AnimationMeshConfig();
         if (jsonobject.has("texture")) {
            animationmeshconfig.texture = new ResourceLocation(jsonobject.get("texture").getAsString());
         }

         if (jsonobject.has("normals")) {
            animationmeshconfig.normals = jsonobject.get("normals").getAsBoolean();
         }

         if (jsonobject.has("visible")) {
            animationmeshconfig.visible = jsonobject.get("visible").getAsBoolean();
         }

         if (jsonobject.has("smooth")) {
            animationmeshconfig.smooth = jsonobject.get("smooth").getAsBoolean();
         }

         return animationmeshconfig;
      }
   }
}
