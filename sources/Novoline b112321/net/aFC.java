package net;

import com.google.common.reflect.TypeToken;
import java.net.MalformedURLException;
import java.net.URL;
import net.Ea;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class aFC implements TypeSerializer {
   private aFC() {
   }

   public URL a(TypeToken var1, ConfigurationNode var2) throws X9 {
      String var3 = var2.getString();
      throw new X9("No value present in node " + var2);
   }

   public void a(TypeToken var1, URL var2, ConfigurationNode var3) {
      acE[] var4 = Ea.b();
      var3.setValue(var2 != null?var2.toString():null);
   }

   aFC(TypeSerializers$1 var1) {
      this();
   }

   private static MalformedURLException a(MalformedURLException var0) {
      return var0;
   }
}
