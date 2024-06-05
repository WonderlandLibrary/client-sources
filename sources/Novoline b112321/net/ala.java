package net;

import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ala {
   public static AttributeModifier a(AttributeModifier var0, boolean var1) {
      return var0.setSaved(var1);
   }

   public static boolean a(AttributeModifier var0) {
      return var0.isSaved();
   }

   public static String d(AttributeModifier var0) {
      return var0.getName();
   }

   public static double e(AttributeModifier var0) {
      return var0.getAmount();
   }

   public static int b(AttributeModifier var0) {
      return var0.getOperation();
   }

   public static UUID c(AttributeModifier var0) {
      return var0.getID();
   }
}
