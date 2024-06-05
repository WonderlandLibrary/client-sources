package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import net.af_;

final class TypeSerializerCollection$SuperTypePredicate implements Predicate {
   private static final Method SUPERTYPE_TEST;
   private final TypeToken type;

   TypeSerializerCollection$SuperTypePredicate(TypeToken var1) {
      this.type = var1;
   }

   public boolean test(TypeToken var1) {
      try {
         return ((Boolean)SUPERTYPE_TEST.invoke(this.type, new Object[]{var1})).booleanValue();
      } catch (Exception var3) {
         var3.printStackTrace();
         return false;
      }
   }

   static {
      Method var8;
      try {
         var8 = af_.b(TypeToken.class, "isSupertypeOf", new Class[]{TypeToken.class});
      } catch (NoSuchMethodException var12) {
         try {
            var8 = af_.b(TypeToken.class, "isAssignableFrom", new Class[]{TypeToken.class});
         } catch (NoSuchMethodException var11) {
            throw new RuntimeException("Unable to get TypeToken#isSupertypeOf or TypeToken#isAssignableFrom method");
         }
      }

      SUPERTYPE_TEST = var8;
   }
}
