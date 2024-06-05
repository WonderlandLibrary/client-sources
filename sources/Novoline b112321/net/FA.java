package net;

import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import net.FJ;
import net.I6;
import net.Ua;
import net.Uj;
import net.aJ1;
import net.a_E;
import net.as0;
import net.gZ;
import net.t8;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

public final class FA extends FJ {
   private static final Map j = new Object2ObjectOpenHashMap();

   public FA(@NotNull gZ var1) {
      super(var1, "bind", "Binds key for module", (Iterable)Arrays.asList(new String[]{"b", "binding", "binds", "keybind", "setbind"}));
   }

   public void b(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 2) {
         this.a("Binds help:", ".bind", new Uj[]{aJ1.a("(module) (key)", "sets keyBind to module")});
         this.a();
         this.a((Ua)aJ1.a("TIP: ", EnumChatFormatting.GRAY).c("Use ").b("\"none\"", EnumChatFormatting.YELLOW).c(" to unbind!"));
      } else {
         String var3 = var1[0];
         String var4 = var1[1].toUpperCase(Locale.ROOT);
         Integer var5 = (Integer)j.getOrDefault(var4, (Object)null);
         this.f("Key was not found!");
      }
   }

   @Nullable
   public List a(@NotNull String[] var1) {
      switch(var1.length) {
      case 1:
         I6 var2 = this.h.d().e();
         ObjectCollection var3 = var2.values();
         return this.a(var3.stream().map(t8::b).map(as0::j), var1[0], true);
      case 2:
         return this.a(j.keySet(), var1[1], true);
      default:
         return null;
      }
   }

   public void a(@NotNull as0 var1, ProtocolPathEntry var2) {
      var1.a(var2);
      this.h.d().c().b();
   }

   static {
      j.put("MOUSE4", Integer.valueOf(-4));
      j.put("MOUSE3", Integer.valueOf(-3));

      try {
         for(Field var10 : Keyboard.class.getFields()) {
            if(var10.getName().startsWith("KEY_")) {
               var10.setAccessible(true);
               j.put(var10.getName().substring(4).toUpperCase(Locale.ROOT), Integer.valueOf(((Integer)var10.get((Object)null)).intValue()));
            }
         }
      } catch (IllegalAccessException var11) {
         var11.printStackTrace();
      }

   }

   private static IllegalAccessException a(IllegalAccessException var0) {
      return var0;
   }
}
