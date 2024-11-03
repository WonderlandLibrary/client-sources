package net.silentclient.client.emotes.bobj;

import org.apache.commons.lang3.ArrayUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class BOBJLoader {
   public static List<String> readAllLines(InputStream inputStream) {
      ArrayList<String> list = new ArrayList<>();

      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

         String line;
         while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
         }

         bufferedReader.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return list;
   }

   public static void merge(BOBJLoader.BOBJData bobjloader$bobjdata, BOBJLoader.BOBJData bobjloader$bobjdata1) {
      int i = bobjloader$bobjdata.vertices.size();
      int j = bobjloader$bobjdata.normals.size();
      int k = bobjloader$bobjdata.textures.size();
      bobjloader$bobjdata.vertices.addAll(bobjloader$bobjdata1.vertices);
      bobjloader$bobjdata.normals.addAll(bobjloader$bobjdata1.normals);
      bobjloader$bobjdata.textures.addAll(bobjloader$bobjdata1.textures);
      bobjloader$bobjdata.armatures.putAll(bobjloader$bobjdata1.armatures);

      for (BOBJLoader.BOBJMesh bobjloader$bobjmesh : bobjloader$bobjdata1.meshes) {
         BOBJLoader.BOBJMesh bobjloader$bobjmesh1 = bobjloader$bobjmesh.add(i, j, k);
         bobjloader$bobjmesh1.armature = bobjloader$bobjdata.armatures.get(bobjloader$bobjmesh1.armatureName);
         bobjloader$bobjdata.meshes.add(bobjloader$bobjmesh1);
      }
   }

   public static BOBJLoader.BOBJData readData(InputStream inputStream) {
      List<String> lines = readAllLines(inputStream);
      ArrayList<BOBJLoader.Vertex> list = new ArrayList<>();
      ArrayList<Vector2f> list2 = new ArrayList();
      ArrayList<Vector3f> list3 = new ArrayList();
      ArrayList<BOBJLoader.BOBJMesh> list4 = new ArrayList<>();
      HashMap<String, BOBJAction> map = new HashMap<>();
      HashMap<String, BOBJArmature> map1 = new HashMap<>();
      BOBJLoader.BOBJMesh bobjMesh = null;
      BOBJAction bobjAction = null;
      BOBJGroup bobjGroup = null;
      BOBJChannel bobjChannel = null;
      BOBJArmature bobjArmature = null;
      BOBJLoader.Vertex vertex = null;
      int n = 0;

      for (String allLine : lines) {
         String[] split = allLine.split("\\s");
         String s = split[0];
         switch (s) {
            case "o":
               list4.add(bobjMesh = new BOBJLoader.BOBJMesh(split[1]));
               bobjArmature = null;
               vertex = null;
               break;
            case "o_arm":
               assert bobjMesh != null;

               bobjMesh.armatureName = split[1];
               break;
            case "v":
               if (vertex != null) {
                  vertex.eliminateTinyWeights();
               }

               list.add(vertex = new BOBJLoader.Vertex(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3])));
               break;
            case "vw":
               float float1 = Float.parseFloat(split[2]);
               if (float1 != 0.0F) {
                  assert vertex != null;

                  vertex.weights.add(new BOBJLoader.Weight(split[1], float1));
               }
               break;
            case "vt":
               list2.add(new Vector2f(Float.parseFloat(split[1]), Float.parseFloat(split[2])));
               break;
            case "vn":
               list3.add(new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3])));
               break;
            case "f":
               assert bobjMesh != null;

               bobjMesh.faces.add(new BOBJLoader.Face(split[1], split[2], split[3]));
               break;
            case "arm_name":
               n = 0;
               bobjArmature = new BOBJArmature(split[1]);
               map1.put(bobjArmature.name, bobjArmature);
               break;
            case "arm_action":
               assert bobjArmature != null;

               bobjArmature.action = split[1];
               break;
            case "arm_bone":
               Vector3f vector3f = new Vector3f(Float.parseFloat(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
               Matrix4f matrix4f = new Matrix4f();
               float[] array = new float[16];

               for (int i = 6; i < 22; ++i) {
                  array[i - 6] = Float.parseFloat(split[i]);
               }

               matrix4f.set(array).transpose();

               assert bobjArmature != null;

               bobjArmature.addBone(new BOBJBone(n++, split[1], split[2], vector3f, matrix4f));
               break;
            case "an":
               map.put(split[1], bobjAction = new BOBJAction(split[1]));
               break;
            case "ao":
               assert bobjAction != null;

               bobjAction.groups.put(split[1], bobjGroup = new BOBJGroup(split[1]));
               break;
            case "ag":
               assert bobjGroup != null;

               bobjGroup.channels.add(bobjChannel = new BOBJChannel(split[1], Integer.parseInt(split[2])));
               break;
            default:
               if (s.equals("kf")) {
                  assert bobjChannel != null;

                  bobjChannel.frames.add(BOBJKeyframe.parse(split));
               }
         }
      }

      return new BOBJLoader.BOBJData(list, list2, list3, list4, map, map1);
   }

   public static Map<String, BOBJLoader.CompiledData> loadMeshes(BOBJLoader.BOBJData data) {
      HashMap<String, BOBJLoader.CompiledData> map = new HashMap<>();

      for (BOBJLoader.BOBJMesh bobjMesh : data.meshes) {
         ArrayList<Integer> list = new ArrayList<>();
         List<BOBJLoader.Face> faces = bobjMesh.faces;
         int[] array = new int[faces.size() * 3 * 4];
         float[] array2 = new float[faces.size() * 3 * 4];
         float[] array3 = new float[faces.size() * 3 * 4];
         float[] array4 = new float[faces.size() * 3 * 2];
         float[] array5 = new float[faces.size() * 3 * 3];
         Arrays.fill(array, -1);
         Arrays.fill(array2, -1.0F);
         int n = 0;

         for (BOBJLoader.Face face : faces) {
            BOBJLoader.IndexGroup[] idxGroups = face.idxGroups;
            int length = idxGroups.length;

            for (int i = 0; i < length; ++i) {
               processFaceVertex(n, idxGroups[i], bobjMesh, data, list, array3, array4, array5, array2, array);
               ++n;
            }
         }

         map.put(
                 bobjMesh.name, new BOBJLoader.CompiledData(array3, array4, array5, array2, array, ArrayUtils.toPrimitive(list.toArray(new Integer[0])), bobjMesh)
         );
      }

      return map;
   }

   private static void processFaceVertex(
           int n,
           BOBJLoader.IndexGroup group,
           BOBJLoader.BOBJMesh mesh,
           BOBJLoader.BOBJData data,
           List<Integer> list,
           float[] array,
           float[] array2,
           float[] array3,
           float[] array4,
           int[] array5
   ) {
      list.add(n);
      if (group.idxPos >= 0) {
         BOBJLoader.Vertex vertex = data.vertices.get(group.idxPos);
         array[n * 4] = vertex.x;
         array[n * 4 + 1] = vertex.y;
         array[n * 4 + 2] = vertex.z;
         array[n * 4 + 3] = 1.0F;
         if (mesh != null) {
            for (int i = 0; i < Math.min(vertex.weights.size(), 4); ++i) {
               BOBJLoader.Weight weight = vertex.weights.get(i);
               BOBJBone bobjBone = mesh.armature.bones.get(weight.name);
               array4[n * 4 + i] = weight.factor;
               array5[n * 4 + i] = bobjBone.index;
            }
         }
      }

      if (group.idxTextCoord >= 0) {
         Vector2f vector2f = data.textures.get(group.idxTextCoord);
         array2[n * 2] = vector2f.x;
         array2[n * 2 + 1] = 1.0F - vector2f.y;
      }

      if (group.idxVecNormal >= 0) {
         Vector3f vector3f = data.normals.get(group.idxVecNormal);
         array3[n * 3] = vector3f.x;
         array3[n * 3 + 1] = vector3f.y;
         array3[n * 3 + 2] = vector3f.z;
      }
   }

   public static class BOBJData {
      public List<BOBJLoader.Vertex> vertices;
      public List<Vector2f> textures;
      public List<Vector3f> normals;
      public List<BOBJLoader.BOBJMesh> meshes;
      public Map<String, BOBJAction> actions;
      public Map<String, BOBJArmature> armatures;

      public BOBJData(
              List<BOBJLoader.Vertex> vertices,
              List<Vector2f> textures,
              List<Vector3f> normals,
              List<BOBJLoader.BOBJMesh> meshes,
              Map<String, BOBJAction> actions,
              Map<String, BOBJArmature> armatures
      ) {
         this.vertices = vertices;
         this.textures = textures;
         this.normals = normals;
         this.meshes = meshes;
         this.actions = actions;
         this.armatures = armatures;

         for (BOBJLoader.BOBJMesh mesh : meshes) {
            mesh.armature = armatures.get(mesh.armatureName);
         }
      }

      public void release() {
         this.vertices.clear();
         this.textures.clear();
         this.normals.clear();
         this.meshes.clear();
      }
   }

   public static class BOBJMesh {
      public String name;
      public List<BOBJLoader.Face> faces = new ArrayList<>();
      public String armatureName;
      public BOBJArmature armature;

      public BOBJMesh(String name) {
         this.name = name;
      }

      public BOBJLoader.BOBJMesh add(int i, int j, int k) {
         BOBJLoader.BOBJMesh bobjloader$bobjmesh = new BOBJLoader.BOBJMesh(this.name);
         bobjloader$bobjmesh.armatureName = this.armatureName;
         bobjloader$bobjmesh.armature = this.armature;

         for (BOBJLoader.Face bobjloader$face : this.faces) {
            bobjloader$bobjmesh.faces.add(bobjloader$face.add(i, j, k));
         }

         return bobjloader$bobjmesh;
      }
   }

   public static class CompiledData {
      public float[] posData;
      public float[] texData;
      public float[] normData;
      public float[] weightData;
      public int[] boneIndexData;
      public int[] indexData;
      public BOBJLoader.BOBJMesh mesh;

      public CompiledData(
              float[] posData, float[] texData, float[] normData, float[] weightData, int[] boneIndexData, int[] indexData, BOBJLoader.BOBJMesh mesh
      ) {
         this.posData = posData;
         this.texData = texData;
         this.normData = normData;
         this.weightData = weightData;
         this.boneIndexData = boneIndexData;
         this.indexData = indexData;
         this.mesh = mesh;
      }
   }

   public static class Face {
      public BOBJLoader.IndexGroup[] idxGroups = new BOBJLoader.IndexGroup[3];

      public Face(String s, String s1, String s2) {
         this.idxGroups[0] = this.parseLine(s);
         this.idxGroups[1] = this.parseLine(s1);
         this.idxGroups[2] = this.parseLine(s2);
      }

      public Face() {
      }

      private BOBJLoader.IndexGroup parseLine(String s) {
         BOBJLoader.IndexGroup bobjloader$indexgroup = new BOBJLoader.IndexGroup();
         String[] astring = s.split("/");
         int i = astring.length;
         bobjloader$indexgroup.idxPos = Integer.parseInt(astring[0]) - 1;
         if (i > 1) {
            String s1 = astring[1];
            if (!s1.isEmpty()) {
               bobjloader$indexgroup.idxTextCoord = Integer.parseInt(s1) - 1;
            }

            if (i > 2) {
               bobjloader$indexgroup.idxVecNormal = Integer.parseInt(astring[2]) - 1;
            }
         }

         return bobjloader$indexgroup;
      }

      public BOBJLoader.Face add(int i, int j, int k) {
         BOBJLoader.Face bobjloader$face = new BOBJLoader.Face();

         for (int l = 0; l < bobjloader$face.idxGroups.length; ++l) {
            BOBJLoader.IndexGroup bobjloader$indexgroup = this.idxGroups[l];
            bobjloader$face.idxGroups[l] = new BOBJLoader.IndexGroup(
                    bobjloader$indexgroup.idxPos + i, bobjloader$indexgroup.idxTextCoord + k, bobjloader$indexgroup.idxVecNormal + j
            );
         }

         return bobjloader$face;
      }
   }

   public static class IndexGroup {
      public static final int NO_VALUE = -1;
      public int idxPos = -1;
      public int idxTextCoord = -1;
      public int idxVecNormal = -1;

      public IndexGroup(int i, int j, int k) {
         this.idxPos = i;
         this.idxTextCoord = j;
         this.idxVecNormal = k;
      }

      public IndexGroup() {
      }
   }

   public static class Vertex {
      public float x;
      public float y;
      public float z;
      public List<BOBJLoader.Weight> weights = new ArrayList<>();

      public Vertex(float x, float y, float z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }

      public void eliminateTinyWeights() {
         this.weights.removeIf(weight -> (double) weight.factor < 0.05);
         if (this.weights.size() > 0) {
            float n = 0.0F;

            for (BOBJLoader.Weight weight1 : this.weights) {
               n += weight1.factor;
            }

            if (n < 1.0F) {
               BOBJLoader.Weight weight = this.weights.get(this.weights.size() - 1);
               weight.factor += 1.0F - n;
            }
         }
      }
   }

   public static class Weight {
      public String name;
      public float factor;

      public Weight(String name, float factor) {
         this.name = name;
         this.factor = factor;
      }
   }
}
