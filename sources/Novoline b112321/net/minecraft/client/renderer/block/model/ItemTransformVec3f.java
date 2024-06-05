package net.minecraft.client.renderer.block.model;

import org.lwjgl.util.vector.Vector3f;

public class ItemTransformVec3f {
   public static final ItemTransformVec3f DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f rotation;
   public final Vector3f translation;
   public final Vector3f scale;

   public ItemTransformVec3f(Vector3f var1, Vector3f var2, Vector3f var3) {
      this.rotation = new Vector3f(var1);
      this.translation = new Vector3f(var2);
      this.scale = new Vector3f(var3);
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         ItemTransformVec3f var2 = (ItemTransformVec3f)var1;
         return this.rotation.equals(var2.rotation) && this.scale.equals(var2.scale) && this.translation.equals(var2.translation);
      }
   }

   public int hashCode() {
      int var1 = this.rotation.hashCode();
      var1 = 31 * var1 + this.translation.hashCode();
      var1 = 31 * var1 + this.scale.hashCode();
      return var1;
   }
}
