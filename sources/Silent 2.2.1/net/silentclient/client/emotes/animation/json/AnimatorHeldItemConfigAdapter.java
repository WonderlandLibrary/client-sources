package net.silentclient.client.emotes.animation.json;

import net.silentclient.client.emotes.animation.model.AnimatorHeldItemConfig;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class AnimatorHeldItemConfigAdapter implements JsonDeserializer<AnimatorHeldItemConfig> {
   public AnimatorHeldItemConfig deserialize(JsonElement jsonelement, Type var2, JsonDeserializationContext var3) {
      if (!jsonelement.isJsonObject()) {
         return null;
      } else {
         JsonObject jsonobject = (JsonObject) jsonelement;
         AnimatorHeldItemConfig animatorhelditemconfig = new AnimatorHeldItemConfig("");
         if (jsonobject.has("x")) {
            animatorhelditemconfig.x = jsonobject.get("x").getAsFloat();
         }

         if (jsonobject.has("y")) {
            animatorhelditemconfig.y = jsonobject.get("y").getAsFloat();
         }

         if (jsonobject.has("z")) {
            animatorhelditemconfig.z = jsonobject.get("z").getAsFloat();
         }

         if (jsonobject.has("sx")) {
            animatorhelditemconfig.scaleX = jsonobject.get("sx").getAsFloat();
         }

         if (jsonobject.has("sy")) {
            animatorhelditemconfig.scaleY = jsonobject.get("sy").getAsFloat();
         }

         if (jsonobject.has("sz")) {
            animatorhelditemconfig.scaleZ = jsonobject.get("sz").getAsFloat();
         }

         if (jsonobject.has("rx")) {
            animatorhelditemconfig.rotateX = jsonobject.get("rx").getAsFloat();
         }

         if (jsonobject.has("ry")) {
            animatorhelditemconfig.rotateY = jsonobject.get("ry").getAsFloat();
         }

         if (jsonobject.has("rz")) {
            animatorhelditemconfig.rotateZ = jsonobject.get("rz").getAsFloat();
         }

         return animatorhelditemconfig;
      }
   }
}
