package net.silentclient.client.emotes.bobj;

public class BOBJKeyframe {
   public int frame;
   public float value;
   public BOBJKeyframe.Interpolation interpolation = BOBJKeyframe.Interpolation.LINEAR;
   public float leftX;
   public float leftY;
   public float rightX;
   public float rightY;

   public BOBJKeyframe(int i, float f) {
      this.frame = i;
      this.value = f;
   }

   public BOBJKeyframe(int i, float f, String s) {
      this(i, f);
      this.interpolation = interpolationFromString(s);
   }

   public BOBJKeyframe(int i, float f, String s, float f1, float f2, float f3, float f4) {
      this(i, f, s);
      this.leftX = f1;
      this.leftY = f2;
      this.rightX = f3;
      this.rightY = f4;
   }

   public static BOBJKeyframe parse(String[] astring) {
      if (astring.length == 8) {
         float f = Float.parseFloat(astring[4]);
         float f1 = Float.parseFloat(astring[5]);
         float f2 = Float.parseFloat(astring[6]);
         float f3 = Float.parseFloat(astring[7]);
         return new BOBJKeyframe(Integer.parseInt(astring[1]), Float.parseFloat(astring[2]), astring[3], f, f1, f2, f3);
      } else {
         return astring.length == 4
                 ? new BOBJKeyframe(Integer.parseInt(astring[1]), Float.parseFloat(astring[2]), astring[3])
                 : (astring.length == 3 ? new BOBJKeyframe(Integer.parseInt(astring[1]), Float.parseFloat(astring[2])) : null);
      }
   }

   public static BOBJKeyframe.Interpolation interpolationFromString(String s) {
      return s.equals("CONSTANT")
              ? BOBJKeyframe.Interpolation.CONSTANT
              : (s.equals("BEZIER") ? BOBJKeyframe.Interpolation.BEZIER : BOBJKeyframe.Interpolation.LINEAR);
   }

   public static float lerp(float f, float f1, float f2) {
      return f + (f1 - f) * f2;
   }

   public float interpolate(float f, BOBJKeyframe bobjkeyframe) {
      return this.interpolation.interpolate(this, f, bobjkeyframe);
   }

   public enum Interpolation {
      CONSTANT {
         @Override
         public float interpolate(BOBJKeyframe bobjkeyframe, float var2, BOBJKeyframe var3) {
            return bobjkeyframe.value;
         }
      },
      LINEAR {
         @Override
         public float interpolate(BOBJKeyframe bobjkeyframe, float f, BOBJKeyframe bobjkeyframe1) {
            return BOBJKeyframe.lerp(bobjkeyframe.value, bobjkeyframe1.value, f);
         }
      },
      BEZIER {
         @Override
         public float interpolate(BOBJKeyframe bobjkeyframe, float f, BOBJKeyframe bobjkeyframe1) {
            float f1 = BOBJKeyframe.lerp(bobjkeyframe.value, bobjkeyframe.rightY, f);
            float f2 = BOBJKeyframe.lerp(bobjkeyframe.rightY, bobjkeyframe1.leftY, f);
            float f3 = BOBJKeyframe.lerp(bobjkeyframe1.leftY, bobjkeyframe1.value, f);
            float f4 = BOBJKeyframe.lerp(f1, f2, f);
            float f5 = BOBJKeyframe.lerp(f2, f3, f);
            return BOBJKeyframe.lerp(f4, f5, f);
         }
      };

      Interpolation() {
      }

      public abstract float interpolate(BOBJKeyframe var1, float var2, BOBJKeyframe var3);
   }
}
