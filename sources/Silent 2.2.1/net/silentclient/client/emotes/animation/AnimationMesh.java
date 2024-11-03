package net.silentclient.client.emotes.animation;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.bobj.BOBJLoader;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class AnimationMesh {
   public Animation owner;
   public String name;
   public BOBJLoader.CompiledData data;
   public BOBJArmature armature;
   public FloatBuffer vertices;
   public FloatBuffer normals;
   public FloatBuffer textcoords;
   public IntBuffer indices;
   public int vertexBuffer;
   public int normalBuffer;
   public int upNormalBuffer;
   public int texcoordBuffer;
   public int indexBuffer;

   public AnimationMesh(Animation owner, String name, BOBJLoader.CompiledData data) {
      this.owner = owner;
      this.name = name;
      this.data = data;
      (this.armature = this.data.mesh.armature).initArmature();
      this.init();
   }

   private void init() {
      this.vertices = BufferUtils.createFloatBuffer(this.data.posData.length);
      ((Buffer) this.vertices.put(this.data.posData)).flip();
      this.normals = BufferUtils.createFloatBuffer(this.data.normData.length);
      ((Buffer) this.normals.put(this.data.normData)).flip();
      this.textcoords = BufferUtils.createFloatBuffer(this.data.texData.length);
      ((Buffer) this.textcoords.put(this.data.texData)).flip();
      this.indices = BufferUtils.createIntBuffer(this.data.indexData.length);
      ((Buffer) this.indices.put(this.data.indexData)).flip();
      float[] array = new float[this.data.normData.length];

      for (int i = 0; i < this.data.normData.length / 3; ++i) {
         array[i * 3] = 0.0F;
         array[i * 3 + 1] = 1.0F;
         array[i * 3 + 2] = 0.0F;
      }

      FloatBuffer buffer = BufferUtils.createFloatBuffer(this.data.normData.length);
      ((Buffer) buffer.put(array)).flip();
      GL15.glBindBuffer(34962, this.vertexBuffer = GL15.glGenBuffers());
      GL15.glBufferData(34962, this.vertices, 35048);
      GL15.glBindBuffer(34962, this.normalBuffer = GL15.glGenBuffers());
      GL15.glBufferData(34962, this.normals, 35044);
      GL15.glBindBuffer(34962, this.upNormalBuffer = GL15.glGenBuffers());
      GL15.glBufferData(34962, buffer, 35044);
      GL15.glBindBuffer(34962, this.texcoordBuffer = GL15.glGenBuffers());
      GL15.glBufferData(34962, this.textcoords, 35044);
      GL15.glBindBuffer(34963, this.indexBuffer = GL15.glGenBuffers());
      GL15.glBufferData(34963, this.indices, 35044);
      GL15.glBindBuffer(34962, 0);
      GL15.glBindBuffer(34963, 0);
   }

   public void delete() {
      GL15.glDeleteBuffers(this.vertexBuffer);
      GL15.glDeleteBuffers(this.normalBuffer);
      GL15.glDeleteBuffers(this.upNormalBuffer);
      GL15.glDeleteBuffers(this.texcoordBuffer);
      GL15.glDeleteBuffers(this.indexBuffer);
      this.vertices = null;
      this.normals = null;
      this.textcoords = null;
      this.indices = null;
   }

   public float[] mesh() {
      Vector4f vector = new Vector4f();
      Vector4f vector1 = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
      float[] posData = this.data.posData;
      float[] array = new float[posData.length];
      Matrix4f[] matrices = this.armature.matrices;

      for (int i = 0; i < array.length / 4; ++i) {
         int n = 0;

         for (int j = 0; j < 4; ++j) {
            float n2 = this.data.weightData[i * 4 + j];
            if (n2 > 0.0F) {
               int n3 = this.data.boneIndexData[i * 4 + j];
               vector.set(posData[i * 4], posData[i * 4 + 1], posData[i * 4 + 2], 1.0F);
               matrices[n3].transform(vector);
               vector1.add(vector.mul(n2));
               ++n;
            }
         }

         if (n == 0) {
            vector1.set(posData[i * 4], posData[i * 4 + 1], posData[i * 4 + 2], 1.0F);
         }

         array[i * 4] = vector1.x;
         array[i * 4 + 1] = vector1.y;
         array[i * 4 + 2] = vector1.z;
         array[i * 4 + 3] = vector1.w;
         vector1.set(0.0F, 0.0F, 0.0F, 0.0F);
      }

      return array;
   }

   public void update() {
      ((Buffer) this.vertices).clear();
      ((Buffer) this.vertices.put(this.mesh())).flip();
      GL15.glBindBuffer(34962, this.vertexBuffer);
      GL15.glBufferData(34962, this.vertices, 35048);
   }

   public void render(AnimationMeshConfig config) {
      if (config == null || config.visible) {
         if (config != null) {
            config.bindTexture();
            int color = config.color;
            GL11.glColor4f((float) (color >> 16 & 0xFF) / 255.0F, (float) (color >> 8 & 0xFF) / 255.0F, (float) (color & 0xFF) / 255.0F, 1.0F);
         }

         GL15.glBindBuffer(34962, this.vertexBuffer);
         GL11.glVertexPointer(4, 5126, 0, 0L);
         GL15.glBindBuffer(34962, config != null && config.normals ? this.normalBuffer : this.upNormalBuffer);
         GL11.glNormalPointer(5126, 0, 0L);
         GL15.glBindBuffer(34962, this.texcoordBuffer);
         GL11.glTexCoordPointer(2, 5126, 0, 0L);
         GL11.glEnableClientState(32884);
         GL11.glEnableClientState(32885);
         GL11.glEnableClientState(32888);
         GL15.glBindBuffer(34963, this.indexBuffer);
         GL11.glDrawElements(4, this.data.indexData.length, 5125, 0L);
         GL15.glBindBuffer(34962, 0);
         GL11.glDisableClientState(32884);
         GL11.glDisableClientState(32885);
         GL11.glDisableClientState(32888);
      }
   }
}
