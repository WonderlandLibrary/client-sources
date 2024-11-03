package net.silentclient.client.emotes.animation.model;

public class AnimatorHeldItemConfig {
   public String boneName = "";
   public float x;
   public float y;
   public float z;
   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   public float rotateX;
   public float rotateY;
   public float rotateZ;

   public AnimatorHeldItemConfig(String s) {
      this.boneName = s;
   }

   public AnimatorHeldItemConfig clone() {
      AnimatorHeldItemConfig animatorhelditemconfig = new AnimatorHeldItemConfig(this.boneName);
      animatorhelditemconfig.x = this.x;
      animatorhelditemconfig.y = this.y;
      animatorhelditemconfig.z = this.z;
      animatorhelditemconfig.scaleX = this.scaleX;
      animatorhelditemconfig.scaleY = this.scaleY;
      animatorhelditemconfig.scaleZ = this.scaleZ;
      animatorhelditemconfig.rotateX = this.rotateX;
      animatorhelditemconfig.rotateY = this.rotateY;
      animatorhelditemconfig.rotateZ = this.rotateZ;
      return animatorhelditemconfig;
   }
}
