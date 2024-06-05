package net;

import java.util.Iterator;
import java.util.Map.Entry;
import net.X9;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMapper$FieldData;

public class axb {
   private final Object a;
   final ObjectMapper b;

   protected axb(ObjectMapper var1, Object var2) {
      this.b = var1;
      this.a = var2;
   }

   public Object b(ConfigurationNode var1) throws X9 {
      X9.b();
      Iterator var3 = ObjectMapper.access$000(this.b).entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         ConfigurationNode var5 = var1.getNode(new Object[]{var4.getKey()});
         ((ObjectMapper$FieldData)var4.getValue()).a(this.a, var5);
      }

      return this.a;
   }

   public void a(ConfigurationNode var1) throws X9 {
      X9.b();
      Iterator var3 = ObjectMapper.access$000(this.b).entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         ConfigurationNode var5 = var1.getNode(new Object[]{var4.getKey()});
         ((ObjectMapper$FieldData)var4.getValue()).b(this.a, var5);
      }

   }

   public Object a() {
      return this.a;
   }

   private static X9 a(X9 var0) {
      return var0;
   }
}
