package net.silentclient.client.emotes.bobj;

import org.joml.Matrix4f;

import java.util.*;

public class BOBJArmature {
   public String name;
   public String action = "";
   public Map<String, BOBJBone> bones = new HashMap<>();
   public List<BOBJBone> orderedBones = new ArrayList<>();
   public Matrix4f[] matrices;
   public boolean enabled = false;
   private boolean initialized;

   public BOBJArmature(String s) {
      this.name = s;
   }

   public void addBone(BOBJBone bobjbone) {
      this.bones.put(bobjbone.name, bobjbone);
      this.orderedBones.add(bobjbone);
   }

   public void initArmature() {
      if (!this.initialized) {
         for (BOBJBone bobjbone : this.bones.values()) {
            if (!bobjbone.parent.isEmpty()) {
               bobjbone.parentBone = this.bones.get(bobjbone.parent);
               bobjbone.relBoneMat.set(bobjbone.parentBone.boneMat).invert().mul(bobjbone.boneMat);
            } else {
               bobjbone.relBoneMat.set(bobjbone.boneMat);
            }
         }

         this.orderedBones.sort(Comparator.comparingInt(bobjbone -> bobjbone.index));
         this.matrices = new Matrix4f[this.orderedBones.size()];
         this.initialized = true;
      }
   }

   public void setupMatrices() {
      for (BOBJBone bobjbone : this.orderedBones) {
         this.matrices[bobjbone.index] = bobjbone.compute();
      }
   }
}
