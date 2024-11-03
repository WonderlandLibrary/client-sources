package net.silentclient.client.emotes.animation;

import net.silentclient.client.emotes.bobj.BOBJLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Animation {
   public String name;
   public BOBJLoader.BOBJData data;
   public List<AnimationMesh> meshes;

   public Animation(String string, BOBJLoader.BOBJData data) {
      this.name = string;
      this.data = data;
      this.meshes = new ArrayList<>();
   }

   public void reload(BOBJLoader.BOBJData bOBJData) {
      this.data = bOBJData;
      this.delete();
      this.init();
   }

   public void init() {
      Map<String, BOBJLoader.CompiledData> map = BOBJLoader.loadMeshes(this.data);

      for (Entry<String, BOBJLoader.CompiledData> entry : map.entrySet()) {
         BOBJLoader.CompiledData bobjloader$compileddata = entry.getValue();
         AnimationMesh animationmesh = new AnimationMesh(this, entry.getKey(), bobjloader$compileddata);
         this.meshes.add(animationmesh);
         this.meshes
                 .sort((animationmesh1, animationmesh2) -> animationmesh1.name.startsWith("prop_") ? (animationmesh2.name.startsWith("prop_") ? 0 : 1) : -1);
         this.data.release();
      }
   }

   public void delete() {
      for (AnimationMesh animationMesh : this.meshes) {
         animationMesh.delete();
      }

      this.meshes.clear();
   }
}
