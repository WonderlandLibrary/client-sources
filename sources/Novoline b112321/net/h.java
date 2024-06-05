package net;

import java.util.Set;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class h {
   public static double d(S08PacketPlayerPosLook var0) {
      return var0.getX();
   }

   public static double b(S08PacketPlayerPosLook var0) {
      return var0.getY();
   }

   public static double c(S08PacketPlayerPosLook var0) {
      return var0.getZ();
   }

   public static float e(S08PacketPlayerPosLook var0) {
      return var0.getYaw();
   }

   public static float a(S08PacketPlayerPosLook var0) {
      return var0.getPitch();
   }

   public static Set f(S08PacketPlayerPosLook var0) {
      return var0.func_179834_f();
   }
}
