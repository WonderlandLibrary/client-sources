package net;

import com.typesafe.config.ConfigOrigin;
import java.util.List;

public class aa1 {
   public static List a(ConfigOrigin var0) {
      return var0.comments();
   }

   public static ConfigOrigin a(ConfigOrigin var0, List var1) {
      return var0.withComments(var1);
   }
}
