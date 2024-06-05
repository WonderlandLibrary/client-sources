package net;

import com.google.common.reflect.TypeToken;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.DF;
import net.Ea;
import net.G3;
import net.Ug;
import net.WF;
import net.Xo;
import net.Xu;
import net.ZV;
import net.a1b;
import net.a2I;
import net.a4X;
import net.aFC;
import net.aIU;
import net.abH;
import net.aej;
import net.agJ;
import net.q7;

public class aM_ {
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
      a.a((TypeToken)TypeToken.of(URI.class), (ZV)(new Xo((Ug)null)));
      a.a((TypeToken)TypeToken.of(URL.class), (ZV)(new aFC((Ug)null)));
      a.a((TypeToken)TypeToken.of(UUID.class), (ZV)(new aej((Ug)null)));
      a.a((Predicate)(aM_::lambda$static$0), (ZV)(new abH((Ug)null)));
      a.a((Predicate)Xu.a(), (ZV)(new Xu((Ug)null)));
      a.a((TypeToken)TypeToken.of(String.class), (ZV)(new DF((Ug)null)));
      a.a((TypeToken)TypeToken.of(Boolean.class), (ZV)(new aIU((Ug)null)));
      a.a((TypeToken)(new Ug()), (ZV)(new a1b((Ug)null)));
      a.a((TypeToken)(new G3()), (ZV)(new agJ((Ug)null)));
      a.a((TypeToken)(new a4X()), (ZV)(new a2I((Ug)null)));
      a.a((TypeToken)TypeToken.of(Pattern.class), (ZV)(new q7((Ug)null)));
   }
}
