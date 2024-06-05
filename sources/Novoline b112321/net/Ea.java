package net;

import cc.novoline.utils.java.Checks;
import com.google.common.reflect.TypeToken;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.acE;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection$RegisteredSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection$SerializerList;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

public class Ea {
   private final Ea c;
   private final TypeSerializerCollection$SerializerList d = new TypeSerializerCollection$SerializerList();
   private final Map a = new ConcurrentHashMap();
   private static acE[] b;

   Ea(Ea var1) {
      this.c = var1;
   }

   public TypeSerializer a(TypeToken var1) {
      b();
      var1 = ((TypeToken)Objects.requireNonNull(var1)).wrap();
      TypeSerializer var3 = (TypeSerializer)this.a.computeIfAbsent(var1, this.d);
      if(var3 == null && this.c != null) {
         var3 = this.c.a(var1);
      }

      return var3;
   }

   public Ea a(TypeToken var1, TypeSerializer var2) {
      this.d.add(new TypeSerializerCollection$RegisteredSerializer((TypeToken)Objects.requireNonNull(var1, "type"), (TypeSerializer)Objects.requireNonNull(var2)));
      this.a.clear();
      return this;
   }

   public Ea a(Predicate var1, TypeSerializer var2) {
      this.d.add(new TypeSerializerCollection$RegisteredSerializer((Predicate)Objects.requireNonNull(var1, "test"), (TypeSerializer)Objects.requireNonNull(var2, "serializer")));
      this.a.clear();
      return this;
   }

   public Ea c() {
      return new Ea(this);
   }

   public Ea a(Ea var1) {
      b();
      Checks.notNull(var1, "collection");
      Ea var3 = TypeSerializers.b();
      var3.d.addAllAbsent(var1.d);
      var3.d.addAllAbsent(this.d);
      if(acE.b() == null) {
         b(new acE[5]);
      }

      return var3;
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      b((acE[])null);
   }
}
