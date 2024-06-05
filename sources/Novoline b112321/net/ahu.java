package net;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.configurations.property.Property;
import cc.novoline.utils.java.Checks;
import java.lang.reflect.Field;
import net.W6;
import net.acE;

public class ahu {
   protected final String b;
   protected final Field a;

   private ahu(String var1, Field var2) {
      this.b = var1;
      this.a = var2;
   }

   public void a(AbstractModule var1, AbstractModule var2, boolean var3) throws IllegalAccessException {
      Checks.check(var2.getClass().isAssignableFrom(var1.getClass()), "cannot copy value of property from different modules");
      Property var5 = (Property)this.a.get(var1);
      ModuleHolder.d();
      Property var6 = (Property)this.a.get(var2);
      var6.set(var5.get());
      if(acE.b() == null) {
         ModuleHolder.b(new String[3]);
      }

   }

   ahu(String var1, Field var2, W6 var3) {
      this(var1, var2);
   }

   private static IllegalAccessException b(IllegalAccessException var0) {
      return var0;
   }
}
