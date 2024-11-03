package net.silentclient.client.emotes.animation.model;

import net.silentclient.client.emotes.animation.AnimationMeshConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AnimatorConfig {
   public String name = "";
   public String primaryMesh = "";
   public float scale = 1.0F;
   public float scaleGui = 1.0F;
   public float scaleItems = 1.0F;
   public boolean renderHeldItems = true;
   public Map<String, AnimatorHeldItemConfig> leftHands = new HashMap<>();
   public Map<String, AnimatorHeldItemConfig> rightHands = new HashMap<>();
   public String head = "head";
   public Map<String, AnimationMeshConfig> meshes = new HashMap<>();

   public void copy(AnimatorConfig animatorconfig) {
      this.name = animatorconfig.name;
      this.primaryMesh = animatorconfig.primaryMesh;
      this.scale = animatorconfig.scale;
      this.scaleGui = animatorconfig.scaleGui;
      this.scaleItems = animatorconfig.scaleItems;
      this.renderHeldItems = animatorconfig.renderHeldItems;
      this.head = animatorconfig.head;
      this.leftHands.clear();
      this.rightHands.clear();
      this.meshes.clear();

      for (Entry<String, AnimatorHeldItemConfig> entry : animatorconfig.leftHands.entrySet()) {
         this.leftHands.put(entry.getKey(), entry.getValue().clone());
      }

      for (Entry<String, AnimatorHeldItemConfig> entry1 : animatorconfig.rightHands.entrySet()) {
         this.rightHands.put(entry1.getKey(), entry1.getValue().clone());
      }

      for (Entry<String, AnimationMeshConfig> entry2 : animatorconfig.meshes.entrySet()) {
         this.meshes.put(entry2.getKey(), entry2.getValue().clone());
      }
   }
}
