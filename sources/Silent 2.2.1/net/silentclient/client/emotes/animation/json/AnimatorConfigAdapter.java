package net.silentclient.client.emotes.animation.json;

import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import net.silentclient.client.emotes.animation.model.AnimatorConfig;
import net.silentclient.client.emotes.animation.model.AnimatorHeldItemConfig;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class AnimatorConfigAdapter implements JsonDeserializer<AnimatorConfig> {
   public static String[] toStringArray(JsonArray jsonarray) {
      ArrayList<String> arraylist = new ArrayList<>();
      int i = 0;

      for (int j = jsonarray.size(); i < j; ++i) {
         JsonElement jsonelement = jsonarray.get(i);
         if (jsonelement.isJsonPrimitive()) {
            arraylist.add(jsonelement.getAsString());
         }
      }

      return arraylist.toArray(new String[arraylist.size()]);
   }

   public AnimatorConfig deserialize(JsonElement jsonelement, Type var2, JsonDeserializationContext jsondeserializationcontext) {
      if (!jsonelement.isJsonObject()) {
         return null;
      } else {
         JsonObject jsonobject = jsonelement.getAsJsonObject();
         AnimatorConfig animatorconfig = new AnimatorConfig();
         if (jsonobject.has("name")) {
            animatorconfig.name = jsonobject.get("name").getAsString();
         }

         if (jsonobject.has("primaryMesh")) {
            animatorconfig.primaryMesh = jsonobject.get("primaryMesh").getAsString();
         }

         if (jsonobject.has("scale")) {
            animatorconfig.scale = jsonobject.get("scale").getAsFloat();
         }

         if (jsonobject.has("scaleGui")) {
            animatorconfig.scaleGui = jsonobject.get("scaleGui").getAsFloat();
         }

         if (jsonobject.has("scaleItems")) {
            animatorconfig.scaleItems = jsonobject.get("scaleItems").getAsFloat();
         }

         if (jsonobject.has("renderHeldItems")) {
            animatorconfig.renderHeldItems = jsonobject.get("renderHeldItems").getAsBoolean();
         }

         if (jsonobject.has("leftHands")) {
            this.addHeldConfig(animatorconfig.leftHands, jsonobject.get("leftHands"), jsondeserializationcontext);
         }

         if (jsonobject.has("rightHands")) {
            this.addHeldConfig(animatorconfig.rightHands, jsonobject.get("rightHands"), jsondeserializationcontext);
         }

         if (jsonobject.has("head")) {
            animatorconfig.head = jsonobject.get("head").getAsString();
         }

         if (jsonobject.has("meshes")) {
            animatorconfig.meshes = jsondeserializationcontext.deserialize(jsonobject.get("meshes"), (new TypeToken<Map<String, AnimationMeshConfig>>() {
            }).getType());
         }

         return animatorconfig;
      }
   }

   private void addHeldConfig(Map<String, AnimatorHeldItemConfig> map, JsonElement jsonelement, JsonDeserializationContext jsondeserializationcontext) {
      map.clear();
      if (jsonelement.isJsonArray()) {
         for (String s : toStringArray(jsonelement.getAsJsonArray())) {
            map.put(s, new AnimatorHeldItemConfig(s));
         }
      } else if (jsonelement.isJsonObject()) {
         for (Entry entry : ((JsonObject) jsonelement).entrySet()) {
            AnimatorHeldItemConfig animatorhelditemconfig = jsondeserializationcontext.deserialize(
                    (JsonElement) entry.getValue(), AnimatorHeldItemConfig.class
            );
            animatorhelditemconfig.boneName = (String) entry.getKey();
            map.put(animatorhelditemconfig.boneName, animatorhelditemconfig);
         }
      }
   }
}
