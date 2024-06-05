package net;

import org.lwjgl.opengl.EXTFramebufferObject;

public class ayn {
   public static void c(int var0) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(var0);
   }

   public static int b() {
      return EXTFramebufferObject.glGenRenderbuffersEXT();
   }

   public static void a(int var0, int var1) {
      EXTFramebufferObject.glBindRenderbufferEXT(var0, var1);
   }

   public static void b(int var0, int var1, int var2, int var3) {
      EXTFramebufferObject.glRenderbufferStorageEXT(var0, var1, var2, var3);
   }

   public static void a(int var0, int var1, int var2, int var3) {
      EXTFramebufferObject.glFramebufferRenderbufferEXT(var0, var1, var2, var3);
   }

   public static int b(int var0) {
      return EXTFramebufferObject.glCheckFramebufferStatusEXT(var0);
   }

   public static void a(int var0) {
      EXTFramebufferObject.glDeleteFramebuffersEXT(var0);
   }

   public static int a() {
      return EXTFramebufferObject.glGenFramebuffersEXT();
   }

   public static void b(int var0, int var1) {
      EXTFramebufferObject.glBindFramebufferEXT(var0, var1);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4) {
      EXTFramebufferObject.glFramebufferTexture2DEXT(var0, var1, var2, var3, var4);
   }
}
