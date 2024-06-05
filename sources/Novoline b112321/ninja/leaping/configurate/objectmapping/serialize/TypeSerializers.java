package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.Ea;
import net.WF;
import net.Xo;
import net.a1b;
import net.a2I;
import net.aFC;
import net.abH;
import net.aej;
import net.agJ;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$2;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$3;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$BooleanSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$NumberSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$PatternSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$StringSerializer;

public class TypeSerializers {
   private static final Ea a = new Ea((Ea)null);

   public static Ea a() {
      return a;
   }

   public static Ea b() {
      return a.c();
   }

   private static boolean lambda$static$0(TypeToken var0) {
      return var0.getRawType().isAnnotationPresent(WF.class);
   }

   static {
      a.a((TypeToken)TypeToken.of(URI.class), new Xo((TypeSerializers$1)null));
      a.a((TypeToken)TypeToken.of(URL.class), new aFC((TypeSerializers$1)null));
      a.a((TypeToken)TypeToken.of(UUID.class), new aej((TypeSerializers$1)null));
      a.a((Predicate)(TypeSerializers::lambda$static$0), new abH((TypeSerializers$1)null));
      a.a((Predicate)TypeSerializers$NumberSerializer.getPredicate(), new TypeSerializers$NumberSerializer((TypeSerializers$1)null));
      a.a((TypeToken)TypeToken.of(String.class), new TypeSerializers$StringSerializer((TypeSerializers$1)null));
      a.a((TypeToken)TypeToken.of(Boolean.class), new TypeSerializers$BooleanSerializer((TypeSerializers$1)null));
      a.a((TypeToken)(new TypeSerializers$1()), new a1b((TypeSerializers$1)null));
      a.a((TypeToken)(new TypeSerializers$2()), new agJ((TypeSerializers$1)null));
      a.a((TypeToken)(new TypeSerializers$3()), new a2I((TypeSerializers$1)null));
      a.a((TypeToken)TypeToken.of(Pattern.class), new TypeSerializers$PatternSerializer((TypeSerializers$1)null));
   }
}
