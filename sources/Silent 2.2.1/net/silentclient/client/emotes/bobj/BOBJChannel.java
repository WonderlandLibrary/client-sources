package net.silentclient.client.emotes.bobj;

import java.util.ArrayList;
import java.util.List;

public class BOBJChannel {
   public String path;
   public int index;
   public List<BOBJKeyframe> frames = new ArrayList<>();

   public BOBJChannel(String s, int i) {
      this.path = s;
      this.index = i;
   }

   public float calculate(float f) {
      int i = this.frames.size();
      if (i <= 0) {
         return 0.0F;
      } else if (i == 1) {
         return this.frames.get(0).value;
      } else {
         BOBJKeyframe bobjkeyframe = this.frames.get(0);
         if ((float) bobjkeyframe.frame > f) {
            return bobjkeyframe.value;
         } else {
            for (int j = 0; j < i; ++j) {
               bobjkeyframe = this.frames.get(j);
               if ((float) bobjkeyframe.frame > f && j != 0) {
                  BOBJKeyframe bobjkeyframe1 = this.frames.get(j - 1);
                  float f1 = (f - (float) bobjkeyframe1.frame) / (float) (bobjkeyframe.frame - bobjkeyframe1.frame);
                  return bobjkeyframe1.interpolate(f1, bobjkeyframe);
               }
            }

            return bobjkeyframe.value;
         }
      }
   }

   public BOBJKeyframe get(float f, boolean flag) {
      int i = this.frames.size();
      if (i == 0) {
         return null;
      } else if (i == 1) {
         return this.frames.get(0);
      } else {
         BOBJKeyframe bobjkeyframe = null;

         for (int j = 0; j < i; ++j) {
            bobjkeyframe = this.frames.get(j);
            if ((float) bobjkeyframe.frame > f && j != 0) {
               return flag ? bobjkeyframe : this.frames.get(j - 1);
            }
         }

         return bobjkeyframe;
      }
   }

   public void apply(BOBJBone bobjbone, float f) {
      if (this.path.equals("location")) {
         if (this.index == 0) {
            bobjbone.x = this.calculate(f);
         } else if (this.index == 1) {
            bobjbone.y = this.calculate(f);
         } else if (this.index == 2) {
            bobjbone.z = this.calculate(f);
         }
      } else if (this.path.equals("rotation")) {
         if (this.index == 0) {
            bobjbone.rotateX = this.calculate(f);
         } else if (this.index == 1) {
            bobjbone.rotateY = this.calculate(f);
         } else if (this.index == 2) {
            bobjbone.rotateZ = this.calculate(f);
         }
      } else if (this.path.equals("scale")) {
         if (this.index == 0) {
            bobjbone.scaleX = this.calculate(f);
         } else if (this.index == 1) {
            bobjbone.scaleY = this.calculate(f);
         } else if (this.index == 2) {
            bobjbone.scaleZ = this.calculate(f);
         }
      }
   }

   public void applyInterpolate(BOBJBone bobjbone, float f, float f1) {
      float f2 = this.calculate(f);
      if (this.path.equals("location")) {
         if (this.index == 0) {
            bobjbone.x = f2 + (bobjbone.x - f2) * f1;
         } else if (this.index == 1) {
            bobjbone.y = f2 + (bobjbone.y - f2) * f1;
         } else if (this.index == 2) {
            bobjbone.z = f2 + (bobjbone.z - f2) * f1;
         }
      } else if (this.path.equals("rotation")) {
         if (this.index == 0) {
            bobjbone.rotateX = f2 + (bobjbone.rotateX - f2) * f1;
         } else if (this.index == 1) {
            bobjbone.rotateY = f2 + (bobjbone.rotateY - f2) * f1;
         } else if (this.index == 2) {
            bobjbone.rotateZ = f2 + (bobjbone.rotateZ - f2) * f1;
         }
      } else if (this.path.equals("scale")) {
         if (this.index == 0) {
            bobjbone.scaleX = f2 + (bobjbone.scaleX - f2) * f1;
         } else if (this.index == 1) {
            bobjbone.scaleY = f2 + (bobjbone.scaleY - f2) * f1;
         } else if (this.index == 2) {
            bobjbone.scaleZ = f2 + (bobjbone.scaleZ - f2) * f1;
         }
      }
   }
}
